package fun.wraq.process.system.forge;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.equip.WraqMainHandEquip;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.forge.ForgeRandomEquip;
import fun.wraq.common.impl.forge.ForgeRandomEquipUtils;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.forge.networking.ForgeC2SPacket;
import fun.wraq.render.gui.WraqScreen;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ForgeScreen extends WraqScreen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/forge.png");
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private List<ItemStack> playerStacks = new ArrayList<>();
    private List<ItemStack> products = new ArrayList<>();
    private final List<Button> reforgeButtons = new ArrayList<>();

    public ForgeScreen() {
        super(Component.translatable("menu.forge"));
    }

    protected void init() {
        Player player = mc.player;
        if (player == null) {
            return;
        }
        // 若拥有可以使用锻造碎片重铸的装备，则将这件装备放在展示界面第一位。
        ForgeEquipUtils.getPlayerInZoneItemList(player).forEach(itemStack -> {
            if ((itemStack.getItem() instanceof WraqMainHandEquip || itemStack.getItem() instanceof WraqArmor)
                    && InventoryOperation.checkPlayerHasItem(player, itemStack.getItem(), 1)) {
                playerStacks.add(InventoryOperation.findFirstItem(player, itemStack.getItem()));
            }
        });
        if (!playerStacks.isEmpty()) {
            products.addAll(playerStacks);
            ForgeEquipUtils.getPlayerInZoneItemList(player).forEach(itemStack -> {
                if (playerStacks.stream().noneMatch(stack -> stack.is(itemStack.getItem()))) {
                    products.add(itemStack);
                }
            });
        } else {
            products.addAll(ForgeEquipUtils.getPlayerInZoneItemList(player));
        }
        // 若锻造配方为空，则不显示
        products.removeIf(itemStack -> {
            Item item = itemStack.getItem();
            return item instanceof ForgeItem forgeItem
                    ? forgeItem.forgeRecipe().isEmpty() : ForgeRecipe.recipes.getOrDefault(item, List.of()).isEmpty();
        });
        this.createMenu();
    }

    private void createMenu() {
        int X = this.width / 2;
        int Y = this.height / 2;
        reforgeButtons.clear();
        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) page--;
        }).pos(X - 39 + 2, Y - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < (products.size() - 1) / 3) page++;
        }).pos(X + 20 + 2, Y - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X + 136, Y - 98).size(12, 12).build());
        for (int i = 0; i < 3; i++) {
            int xOffset = -102 + 95 * i;
            int yOffset = -36;
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("\uD83D\uDEE0尝试锻造\uD83D\uDEE0"), (p_280814_) -> {
                if (page * 3 + finalI < products.size()) {
                    ModNetworking.sendToServer(new ForgeC2SPacket(products.get(page * 3 + finalI)));
                }
            }).pos(X + xOffset - 24, Y + yOffset + 50).size(64, 16).build());
            // 重铸按钮常驻，但是否显示由当前页物品与玩家背包实时决定
            Button reforgeButton = Button.builder(Component.translatable("重铸"), (p_280814_) -> {
                if (page * 3 + finalI < products.size()
                        && canReforge(products.get(page * 3 + finalI))) {
                    ModNetworking.sendToServer(new ForgeC2SPacket(products.get(page * 3 + finalI)));
                }
            }).pos(X + xOffset - 24, Y + yOffset + 68).size(64, 16).build();
            reforgeButton.visible = false;
            this.addRenderableWidget(reforgeButton);
            reforgeButtons.add(reforgeButton);
        }
        updateReforgeButtons();
    }

    private void updateReforgeButtons() {
        for (int i = 0; i < reforgeButtons.size(); i++) {
            int index = page * 3 + i;
            reforgeButtons.get(i).visible = index < products.size()
                    && canReforge(products.get(index));
        }
    }

    /**
     * 重铸按钮显示的唯一前提：玩家物品栏里确实有这件装备。
     */
    private boolean canReforge(ItemStack stack) {
        if (mc.player == null) {
            return false;
        }
        Item item = stack.getItem();
        return (item instanceof WraqMainHandEquip || item instanceof WraqArmor)
                && InventoryOperation.checkPlayerHasItem(mc.player, item, 1);
    }

    public void tick() {
        super.tick();
        updateReforgeButtons();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int size = products.size();
        int X = this.width / 2;
        int Y = this.height / 2;
        int textureWidth = 300;
        int textureHeight = 200;
        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100,
                0, 0, 300, 200, textureWidth, textureHeight);
        guiGraphics.drawString(fontRenderer, Te.s("?锻造品质", ChatFormatting.GREEN),
                X + 112, Y - 108, 0);
        if (x > X + 112 && x < X + 112 + 28 && y > Y - 105 - 6 && y < Y - 108 + 12) {
            guiGraphics.renderComponentTooltip(fontRenderer, ForgeEquipUtils.getTierAndValueDescription(), x, y);
        }
        List<Component> hoveredComponents = null;
        ItemStack hoveredStack = null;
        for (int i = 0; i < 3; i++) {
            if (page * 3 + i < size) {
                ItemStack itemStack = products.get(page * 3 + i);
                Item item = itemStack.getItem();
                int xOffset = -102 + 95 * i;
                int yOffset = -36;
                guiGraphics.renderItem(itemStack,
                        this.width / 2 + xOffset, this.height / 2 + yOffset);
                Compute.forgingHoverName(itemStack);
                // 物品名称
                guiGraphics.drawCenteredString(fontRenderer, itemStack.getDisplayName(),
                        this.width / 2 + xOffset + 8, this.height / 2 + yOffset - 20, 0);

                // 材料合成树：物品图标下方最多 3 个子材料，更多时显示 [...]
                List<Component> materialHover = renderMaterialTree(guiGraphics, itemStack,
                        this.width / 2 + xOffset, this.height / 2 + yOffset, x, y);
                if (materialHover != null) {
                    hoveredComponents = materialHover;
                }

                boolean productHovered = x > this.width / 2 + xOffset
                        && x < this.width / 2 + xOffset + 16
                        && y > this.height / 2 + yOffset
                        && y < this.height / 2 + 16 + yOffset;
                if (productHovered && materialHover == null) {
                    // ForgeRandomEquip 新物品：显示随机属性范围预览
                    if (item instanceof ForgeRandomEquip randomEquip) {
                        List<Component> previewLines = ForgeRandomEquipUtils.getPreviewTooltipLines(randomEquip);
                        List<Component> tooltipLines = new ArrayList<>();
                        tooltipLines.add(itemStack.getDisplayName());
                        tooltipLines.add(Component.literal(""));
                        tooltipLines.addAll(previewLines);
                        tooltipLines.add(Component.literal(""));
                        tooltipLines.add(Component.literal("最终数值 = 随机值 x 锻造品质倍率(0.8x~2.0x)")
                                .withStyle(style -> style.withColor(0x888888)));
                        hoveredComponents = tooltipLines;
                    } else {
                        hoveredStack = itemStack;
                    }
                }

                // 重铸按钮悬浮提示（仅当玩家物品栏确实有该装备）
                if (canReforge(itemStack)) {
                    int btnX = this.width / 2 + xOffset - 24;
                    int btnY = this.height / 2 + yOffset + 68;
                    if (x > btnX && x < btnX + 64 && y > btnY && y < btnY + 16
                            && materialHover == null) {
                        List<Component> components = new ArrayList<>();
                        components.add(Te.s("需要消耗一个",
                                ForgeEquipUtils.getEquipPieceList()
                                        .get(ForgeEquipUtils.getEquipForgeQuality(itemStack))));
                        hoveredComponents = components;
                    }
                }
            }
        }
        guiGraphics.drawCenteredString(fontRenderer, Te.s(page + 1),
                this.width / 2 + 2, this.height / 2 - 22 + 105, 0);
        guiGraphics.drawCenteredString(fontRenderer,
                Te.s("共" + ((size - 1) / 3 + 1) + "页 " + (size) + "件物品", ChatFormatting.BLUE),
                this.width / 2 + 80, this.height / 2 - 22 + 105, 0);
        if (hoveredComponents != null) {
            super.render(p_96310_, x, y, v);
            guiGraphics.flush();
            guiGraphics.renderComponentTooltip(fontRenderer, hoveredComponents, x, y);
        } else if (hoveredStack != null) {
            super.render(p_96310_, x, y, v);
            guiGraphics.flush();
            guiGraphics.renderTooltip(font, hoveredStack, x, y);
        } else {
            super.render(p_96310_, x, y, v);
        }
    }

    /**
     * 在物品图标下方绘制“合成树”：最多三个子材料，更多时追加 [...]。
     *
     * @return 鼠标悬停在子材料或 [...] 上时返回对应 tooltip，否则 null
     */
    private List<Component> renderMaterialTree(GuiGraphics guiGraphics, ItemStack product,
                                               int iconX, int iconY, int mouseX, int mouseY) {
        List<ItemStack> materials = new ArrayList<>(recipeFor(product.getItem()));
        if (materials.isEmpty()) {
            return null;
        }
        // 有装备时优先展示装备，其余材料保持原顺序
        materials.sort(Comparator.comparingInt(stack -> isEquipment(stack.getItem()) ? 0 : 1));

        int visible = Math.min(3, materials.size());
        boolean more = materials.size() > 3;
        int ellipsisWidth = more ? fontRenderer.width("[...]") : 0;
        int centerX = iconX + 8;
        // 以“第一个子物品中心 -> 最后一个子物品/[...]中心”的横线为基准，
        // 反推起点，保证成品图标下的竖线始终落在横线正中间。
        int lastCenterOffset = more
                ? (visible - 1) * 18 + 16 + 2 + ellipsisWidth / 2
                : (visible - 1) * 18 + 8;
        int startX = centerX - (8 + lastCenterOffset) / 2;
        int childY = iconY + 30;
        int midY = iconY + 23;
        int lineColor = 0xFF2C5A7A;

        // 树形连接线：物品 -> 横向分叉 -> 各子物品
        guiGraphics.fill(centerX, iconY + 16, centerX + 1, midY, lineColor);
        int lastCenter = startX + lastCenterOffset;
        guiGraphics.fill(startX + 8, midY, lastCenter + 1, midY + 1, lineColor);

        int hoveredIndex = -1;
        for (int i = 0; i < visible; i++) {
            int childX = startX + i * 18;
            guiGraphics.fill(childX + 8, midY, childX + 9, childY, lineColor);
            ItemStack icon = materials.get(i).copy();
            icon.setCount(1);
            guiGraphics.renderItem(icon, childX, childY);
            String countText = String.valueOf(materials.get(i).getCount());
            guiGraphics.drawString(fontRenderer, countText,
                    childX + 16 - fontRenderer.width(countText), childY + 9,
                    0xFFFFFFFF, true);
            if (mouseX >= childX && mouseX < childX + 16
                    && mouseY >= childY && mouseY < childY + 16) {
                hoveredIndex = i;
            }
        }

        boolean ellipsisHovered = false;
        if (more) {
            int ellipsisX = startX + (visible - 1) * 18 + 16 + 2;
            guiGraphics.fill(ellipsisX + ellipsisWidth / 2, midY,
                    ellipsisX + ellipsisWidth / 2 + 1, childY, lineColor);
            guiGraphics.drawString(fontRenderer, "[...]", ellipsisX, childY + 4,
                    0xFF1E3A5F, false);
            ellipsisHovered = mouseX >= ellipsisX - 2 && mouseX < ellipsisX + ellipsisWidth + 2
                    && mouseY >= childY && mouseY < childY + 16;
        }

        if (hoveredIndex >= 0) {
            return materialTooltip(materials.get(hoveredIndex));
        }
        if (ellipsisHovered) {
            return remainingMaterialTooltip(materials);
        }
        return null;
    }

    private List<ItemStack> recipeFor(Item item) {
        List<ItemStack> recipe = item instanceof ForgeItem forgeItem
                ? forgeItem.forgeRecipe() : ForgeRecipe.recipes.get(item);
        return recipe == null ? List.of() : recipe;
    }

    private static boolean isEquipment(Item item) {
        return Utils.weaponList.contains(item)
                || Utils.armorList.contains(item)
                || Utils.curiosList.contains(item)
                || Utils.customizedList.contains(item);
    }

    private List<Component> materialTooltip(ItemStack material) {
        List<Component> lines = new ArrayList<>();
        int need = material.getCount();
        int have = mc.player == null ? 0 : InventoryOperation.itemStackCount(mc.player, material.getItem());
        if (have >= need) {
            lines.add(Te.s(material, " (", have, ChatFormatting.AQUA, "/", need,
                    CustomStyle.styleOfMoon, ")", " √", ChatFormatting.GREEN));
        } else {
            lines.add(Te.s(material, " (", have, ChatFormatting.AQUA, "/", need,
                    CustomStyle.styleOfMoon, ")", " -", ChatFormatting.WHITE));
        }
        if (material.is(ModItems.COLLEGE_SENIOR_EQUIP_TICKET.get())) {
            lines.add(Component.literal("这件物品可由村民兑换/灌注获取.")
                    .withStyle(ChatFormatting.AQUA));
        }
        return lines;
    }

    private List<Component> remainingMaterialTooltip(List<ItemStack> materials) {
        List<Component> lines = new ArrayList<>();
        lines.add(Component.literal("还需要").withStyle(ChatFormatting.AQUA));
        for (int i = 3; i < materials.size(); i++) {
            lines.addAll(materialTooltip(materials.get(i)));
        }
        return lines;
    }
}

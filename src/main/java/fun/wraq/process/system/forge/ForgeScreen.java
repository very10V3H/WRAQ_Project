package fun.wraq.process.system.forge;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.equip.WraqMainHandEquip;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.util.ClientUtils;
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
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ForgeScreen extends WraqScreen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/forge.png");
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private List<ItemStack> playerStacks = new ArrayList<>();
    private List<ItemStack> products = new ArrayList<>();

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
        this.createMenu();
    }

    private void createMenu() {
        int X = this.width / 2;
        int Y = this.height / 2;
        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) page--;
        }).pos(X - 39 + 2, Y - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            int size = ForgeEquipUtils.getPlayerInZoneItemList(mc.player).size();
            if (page < (size - 1) / 3) page++;
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
        }
    }

    public void tick() {
        super.tick();
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
        for (int i = 0; i < 3; i++) {
            if (page * 3 + i < size) {
                ItemStack itemStack = products.get(page * 3 + i);
                Item item = itemStack.getItem();
                int xOffset = -102 + 95 * i;
                int yOffset = -36;
                guiGraphics.renderItem(itemStack,
                        this.width / 2 + xOffset, this.height / 2 + yOffset);
                Compute.forgingHoverName(itemStack);
                if (x > this.width / 2 + xOffset && x < this.width / 2 + xOffset + 16
                        && y > this.height / 2 + yOffset && y < this.height / 2 + 16 + yOffset) {
                    guiGraphics.renderTooltip(font, itemStack, x, y);
                }
                if (!playerStacks.isEmpty() && playerStacks.contains(itemStack)) {
                    guiGraphics.drawCenteredString(fontRenderer, itemStack.getDisplayName(),
                            this.width / 2 + xOffset + 8, this.height / 2 + yOffset - 20, 0);
                    guiGraphics.drawCenteredString(fontRenderer, Te.s("「可重铸」", ChatFormatting.RED),
                            this.width / 2 + xOffset + 8, this.height / 2 + yOffset + 30, 0);
                    if (x > this.width / 2 + xOffset - 16 && x < this.width / 2 + xOffset + 32
                            && y > this.height / 2 + yOffset + 30 - 8 && y < this.height / 2 + 16 + yOffset + 30) {
                        List<Component> components = new ArrayList<>();
                        components.add(Te.s("需要消耗一个",
                                ForgeEquipUtils.getEquipPieceList()
                                        .get(ForgeEquipUtils.getEquipForgeQuality(itemStack))));
                        guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
                    }
                } else {
                    ForgeEquipUtils.setForgeQualityOnEquip(itemStack, ClientUtils.clientPlayerTick / 20 % 13);
                    guiGraphics.drawCenteredString(fontRenderer, itemStack.getDisplayName(),
                            this.width / 2 + xOffset + 8, this.height / 2 + yOffset - 20, 0);
                    guiGraphics.drawCenteredString(fontRenderer, Te.s("「材料清单」", ChatFormatting.AQUA),
                            this.width / 2 + xOffset + 8, this.height / 2 + yOffset + 30, 0);
                    if (x > this.width / 2 + xOffset - 16 && x < this.width / 2 + xOffset + 32
                            && y > this.height / 2 + yOffset + 30 - 8 && y < this.height / 2 + 16 + yOffset + 30) {
                        List<ItemStack> materialList;
                        if (item instanceof ForgeItem forgeItem) {
                            materialList = forgeItem.forgeRecipe();
                        } else {
                            materialList = ForgeRecipe.recipes.get(item);
                        }
                        List<Component> components = new ArrayList<>() {{
                            add(Te.s("「材料清单」", ChatFormatting.AQUA));
                            materialList.forEach(material -> {
                                int playerInventoryHasNum
                                        = InventoryOperation.itemStackCount(mc.player, material.getItem());
                                if (playerInventoryHasNum >= material.getCount()) {
                                    add(Te.s(material, " (", material.getCount(), ChatFormatting.AQUA, "/",
                                            material.getCount(), CustomStyle.styleOfMoon, ")",
                                            " √", ChatFormatting.GREEN));
                                } else {
                                    add(Te.s(material, " (", playerInventoryHasNum, ChatFormatting.AQUA, "/",
                                            material.getCount(), CustomStyle.styleOfMoon, ")",
                                            " -", ChatFormatting.WHITE));
                                }
                            });
                        }};
                        guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
                    }
                }
            }
        }
        guiGraphics.drawCenteredString(fontRenderer, Te.s(page + 1),
                this.width / 2 + 2, this.height / 2 - 22 + 105, 0);
        guiGraphics.drawCenteredString(fontRenderer,
                Te.s("共" + ((size - 1) / 3 + 1) + "页 " + (size) + "件物品", ChatFormatting.BLUE),
                this.width / 2 + 80, this.height / 2 - 22 + 105, 0);
        super.render(p_96310_, x, y, v);
    }
}

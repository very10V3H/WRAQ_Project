package fun.wraq.render.gui.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.Limit.ScreenCloseC2SPacket;
import fun.wraq.process.system.forge.networking.CraftC2SPacket;
import fun.wraq.process.system.forge.networking.DecomposeC2SPacket;
import fun.wraq.render.gui.blocks.ForgingBlockMenu;
import fun.wraq.series.overworld.forging.ForgingMaterial;
import fun.wraq.series.overworld.forging.ForgingStone0;
import fun.wraq.series.overworld.forging.ForgingStone1;
import fun.wraq.series.overworld.forging.ForgingStone2;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForgingBlockScreen extends AbstractContainerScreen<fun.wraq.render.gui.blocks.ForgingBlockMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Utils.MOD_ID, "textures/gui/gem_infusing_station_gui3.png");

    public ForgingBlockScreen(ForgingBlockMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    public static int doubleClick = 0;
    public static int lossRecipe = 0;

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(Button.builder(Component.translatable("分解"), (p_280814_) -> {
            ModNetworking.sendToServer(new DecomposeC2SPacket(menu.blockEntity.getBlockPos()));
        }).pos(this.width / 2 - 13, this.height / 2 - 16).size(32, 16).build());

        this.addRenderableWidget(Button.builder(Component.translatable("锻造"), (p_280814_) -> {
            ModNetworking.sendToServer(new CraftC2SPacket(menu.blockEntity.getBlockPos()));
        }).pos(this.width / 2 - 13, this.height / 2 - 64).size(32, 16).build());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float PartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());
        }
    }

    public static List<Component> components = new ArrayList<>() {{
        add(Component.literal("维瑞阿契锻造").withStyle(ChatFormatting.AQUA));
        add(Component.literal("1.强化").withStyle(ChatFormatting.AQUA));
        add(Component.literal(" 将").withStyle(ChatFormatting.WHITE).
                append(Component.literal("强化石").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("放置于左上").withStyle(ChatFormatting.WHITE)));
        add(Component.literal(" 将要强化的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("装备").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("放置于左下").withStyle(ChatFormatting.WHITE)));
        add(Component.literal(" [可选]:").withStyle(ChatFormatting.GOLD).
                append(Component.literal("可在右侧两个位置放置").withStyle(ChatFormatting.WHITE)).
                append(ModItems.ForgeProtect.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("或").withStyle(ChatFormatting.WHITE)).
                append(ModItems.ForgeEnhance2.get().getDefaultInstance().getDisplayName()));
        add(Component.literal("2.开孔").withStyle(ChatFormatting.AQUA));
        add(Component.literal("").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" 将").withStyle(ChatFormatting.WHITE)).
                append(ModItems.OpenSlot.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("放置于右上").withStyle(ChatFormatting.WHITE)));
        add(Component.literal(" 将要强化的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("装备").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("放置于右下").withStyle(ChatFormatting.WHITE)));
        add(Component.literal("3.镶嵌").withStyle(ChatFormatting.AQUA));
        add(Component.literal("").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" 将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("任意宝石").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("放置于左上").withStyle(ChatFormatting.WHITE)));
        add(Component.literal(" 将要镶嵌的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("装备").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("放置于左下").withStyle(ChatFormatting.WHITE)));
        add(Component.literal("4.拆卸宝石").withStyle(ChatFormatting.AQUA));
        add(Component.literal("").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" 将").withStyle(ChatFormatting.WHITE)).
                append(ModItems.Dismantle.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("放置于右上").withStyle(ChatFormatting.WHITE)));
        add(Component.literal(" 将要强化的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("装备").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("放置于右下").withStyle(ChatFormatting.WHITE)));
/*        add(Component.literal("5.重铸饰品").withStyle(ChatFormatting.AQUA));
        add(Component.literal("").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" 将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("四元饰品").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("或").withStyle(ChatFormatting.WHITE)).
                append(ModItems.NetherGem.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("放置于左上").withStyle(ChatFormatting.WHITE)));*/
        add(Component.literal("6.分解锻造简易装备/随机饰品").withStyle(ChatFormatting.AQUA));
        add(Component.literal("").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" 将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("锻造装备/随机饰品").withStyle(ChatFormatting.GOLD)).
                append(Component.literal("放置于中间槽位后，双击分解按钮").withStyle(ChatFormatting.WHITE)));
        add(Component.literal("7.合成高阶锻造碎片").withStyle(ChatFormatting.AQUA));
        add(Component.literal("").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" 将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("任意锻造碎片 * 4").withStyle(ChatFormatting.GOLD)).
                append(Component.literal("放置于左上").withStyle(ChatFormatting.WHITE)));
        add(Component.literal("").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" 将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("粗糙锻造碎片 * 1").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("放置于左下").withStyle(ChatFormatting.WHITE)));
        add(Component.literal("8.使用锻造碎片提高锻造品质").withStyle(ChatFormatting.AQUA));
        add(Component.literal("").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" 将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("下一阶锻造碎片 * 4").withStyle(ChatFormatting.GOLD)).
                append(Component.literal("放置于左上").withStyle(ChatFormatting.WHITE)));
        add(Component.literal("").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" 将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("要提高锻造品质的装备").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("放置于左下").withStyle(ChatFormatting.WHITE)));
    }};

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        Minecraft mc = Minecraft.getInstance();
        Font fontRenderer = mc.font;
        int X = this.width / 2;
        int Y = this.height / 2;

        int offsetX = -86;
        int offsetY = -92;
        if (lossRecipe > 0) {
            guiGraphics.drawString(fontRenderer, Component.literal("这件物品不能被分解").withStyle(ChatFormatting.AQUA), X + offsetX, Y + offsetY, 0);
        } else {
            if (doubleClick > 0) {
                guiGraphics.drawString(fontRenderer, Component.literal("再次点击以分解物品").withStyle(ChatFormatting.AQUA), X + offsetX, Y + offsetY, 0);
            } else {
                guiGraphics.drawString(fontRenderer, Component.literal("光标移动至此处可查看简单文字教程").withStyle(ChatFormatting.WHITE), X + offsetX, Y + offsetY, 0);
                if (mouseX > X + offsetX && mouseX < X + 56 && mouseY > Y + offsetY - 6 && mouseY < Y + offsetY + 12) {
                    guiGraphics.renderComponentTooltip(fontRenderer, components, mouseX, mouseY);
                }
            }
        }

        ItemStack stone = this.getMenu().blockEntity.getItemStackHandler().getStackInSlot(0);
        ItemStack equip = this.getMenu().blockEntity.getItemStackHandler().getStackInSlot(1);
        ItemStack enhancePaper = this.getMenu().blockEntity.getItemStackHandler().getStackInSlot(3);

        if ((stone.getItem() instanceof ForgingMaterial || stone.is(ModItems.worldForgeStone.get()))
                && Utils.mainHandTag.containsKey(equip.getItem())) {
            CompoundTag data = equip.getTagElement(Utils.MOD_ID);
            if (data != null) {
                int forgelevel = 0;
                double successRate = 0;
                if (data.contains("Forging")) forgelevel = data.getInt("Forging");
                if (stone.getItem() instanceof ForgingStone0) {
                    successRate = (double) (10 - forgelevel) / 10;
                    if (enhancePaper.is(ModItems.ForgeEnhance0.get())) successRate *= (1.25);
                    if (enhancePaper.is(ModItems.ForgeEnhance1.get())) successRate *= (1.5);
                    if (enhancePaper.is(ModItems.ForgeEnhance2.get())) successRate *= (2);
                }

                if (stone.getItem() instanceof ForgingStone1) {
                    successRate = (double) (20 - forgelevel) / 20;
                    if (enhancePaper.is(ModItems.ForgeEnhance0.get())) successRate *= (1.25);
                    if (enhancePaper.is(ModItems.ForgeEnhance1.get())) successRate *= (1.5);
                    if (enhancePaper.is(ModItems.ForgeEnhance2.get())) successRate *= (2);
                }

                if (stone.getItem() instanceof ForgingStone2) {
                    successRate = (double) (24 - forgelevel) / 24;
                    if (enhancePaper.is(ModItems.ForgeEnhance0.get())) successRate *= (1.25);
                    if (enhancePaper.is(ModItems.ForgeEnhance1.get())) successRate *= (1.5);
                    if (enhancePaper.is(ModItems.ForgeEnhance2.get())) successRate *= (2);
                }

                if (stone.getItem().equals(ModItems.worldForgeStone.get())) {
                    if (forgelevel < 24) {
                        successRate = 1;
                    } else {
                        int minus = 32 - forgelevel;
                        successRate = 0.01 + minus * 0.005;
                        if (enhancePaper.is(ModItems.ForgeEnhance0.get())) successRate *= (1.25);
                        if (enhancePaper.is(ModItems.ForgeEnhance1.get())) successRate *= (1.5);
                        if (enhancePaper.is(ModItems.ForgeEnhance2.get())) successRate *= (2);
                    }
                }

                if (successRate < 0) successRate = 0;
                guiGraphics.drawString(fontRenderer, Component.literal("成功率:").withStyle(ChatFormatting.WHITE).
                                append(Component.literal(String.format("%.2f%%", successRate * 100)).withStyle(ChatFormatting.AQUA))
                        , X + offsetX + 135, Y + offsetY, 0);
            }
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        ModNetworking.sendToServer(new ScreenCloseC2SPacket());
    }

    @Override
    protected void containerTick() {
        if (doubleClick > 0) doubleClick--;
        if (lossRecipe > 0) lossRecipe--;
        super.containerTick();
    }
}

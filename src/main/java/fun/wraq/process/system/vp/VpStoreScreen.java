package fun.wraq.process.system.vp;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.func.plan.SimpleTierPaper;
import fun.wraq.process.system.vp.networking.VpStoreBuyC2SPacket;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class VpStoreScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/vp_store.png");
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;

    public VpStoreScreen() {
        super(Component.translatable("menu.reputation_store0"));
    }

    protected void init() {
        this.createMenu();
    }

    private void createMenu() {

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) page--;
        }).pos(this.width / 2 - 39 + 5, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < (fun.wraq.process.system.vp.VpStore.priceMap.size() - 1) / 10) page++;
        }).pos(this.width / 2 + 20 - 4, this.height / 2 - 20 + 97).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(this.width / 2 + 136, this.height / 2 - 98).size(12, 12).build());

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                if (page * 10 + finalI < VpStore.priceMap.size()) {
                    ModNetworking.sendToServer(new VpStoreBuyC2SPacket(VpStore.getGoodsList().get(page * 10 + finalI).getDefaultInstance()));
                }
            }).pos(this.width / 2 - 35, this.height / 2 - 83 + 32 * i).size(32, 16).build());
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("购买"), (p_280814_) -> {
                if (page * 10 + 5 + finalI < VpStore.priceMap.size())
                    ModNetworking.sendToServer(new VpStoreBuyC2SPacket(VpStore.getGoodsList().get(page * 10 + 5 + finalI).getDefaultInstance()));
            }).pos(this.width / 2 - 35 + 140, this.height / 2 - 83 + 32 * i).size(32, 16).build());
        }
    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics graphics, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int yOffset = 0;
        int xOffset = -16;
        for (int i = 0; i < 5; i++) {
            if (page * 10 + i < fun.wraq.process.system.vp.VpStore.getGoodsList().size()) {
                ItemStack itemStack = fun.wraq.process.system.vp.VpStore.getGoodsList().get(page * 10 + i).getDefaultInstance();
                Item item = itemStack.getItem();

                guiGraphics.renderItem(itemStack, this.width / 2 - 100 - 17 + xOffset, this.height / 2 - 83 + 32 * i);

                if (fun.wraq.process.system.vp.VpStore.getCountMap().getOrDefault(item, 1) > 1) {
                    guiGraphics.drawCenteredString(font, Component.literal("" + fun.wraq.process.system.vp.VpStore.getCountMap().getOrDefault(item, 1)).withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100 - 17 + xOffset + 20, this.height / 2 - 83 + 32 * i, 0);
                }

                guiGraphics.drawCenteredString(font, itemStack.getDisplayName(), this.width / 2 - 74, this.height / 2 - 86 + 32 * i, 0);

                Component component = Component.literal(fun.wraq.process.system.vp.VpStore.getPriceMap().get(
                        itemStack.getItem()) + " vp").withStyle(ChatFormatting.WHITE);
                if (fun.wraq.process.system.vp.VpStore.getWorldSoul5Price().containsKey(item)) {
                    component = Component.literal(fun.wraq.process.system.vp.VpStore.getPriceMap().get(itemStack.getItem()) + " vp").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(" 或 ").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(fun.wraq.process.system.vp.VpStore.getWorldSoul5Price().get(item) + "*").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("聚星").withStyle(CustomStyle.styleOfWorld));
                }
                guiGraphics.drawCenteredString(font, component, this.width / 2 - 74, this.height / 2 - 71 + 32 * i, 0);

                if (x > this.width / 2 - 100 - 17 + xOffset && x < this.width / 2 - 100 - 17 + 16 + xOffset
                        && y > this.height / 2 - 83 + 32 * i && y < this.height / 2 - 83 + 32 * i + 16) {
                    guiGraphics.renderTooltip(font, itemStack, x, y);
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            if (page * 10 + 5 + i < fun.wraq.process.system.vp.VpStore.getGoodsList().size()) {
                ItemStack itemStack = fun.wraq.process.system.vp.VpStore.getGoodsList().get(page * 10 + 5 + i).getDefaultInstance();
                Item item = itemStack.getItem();

                guiGraphics.renderItem(itemStack,
                        this.width / 2 - 100 - 13 + 140 + xOffset, this.height / 2 - 83 + 32 * i);

                if (fun.wraq.process.system.vp.VpStore.getCountMap().getOrDefault(item, 1) > 1) {
                    guiGraphics.drawCenteredString(font, Component.literal("" + fun.wraq.process.system.vp.VpStore.getCountMap().getOrDefault(item, 1)).withStyle(ChatFormatting.WHITE),
                            this.width / 2 - 100 - 17 + xOffset + 20 + 140, this.height / 2 - 83 + 32 * i + 12, 0);
                }

                guiGraphics.drawCenteredString(font, itemStack.getDisplayName(),
                        this.width / 2 - 74 + 140, this.height / 2 - 86 + 32 * i, 0);

                Component component = Component.literal(fun.wraq.process.system.vp.VpStore.getPriceMap().get(
                        itemStack.getItem()) + " vp").withStyle(ChatFormatting.WHITE);
                if (fun.wraq.process.system.vp.VpStore.getWorldSoul5Price().containsKey(item)) {
                    component = Component.literal(fun.wraq.process.system.vp.VpStore.getPriceMap().get(itemStack.getItem()) + " vp").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(" 或 ").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(VpStore.getWorldSoul5Price().get(item) + "*").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("聚星").withStyle(CustomStyle.styleOfWorld));
                }
                guiGraphics.drawCenteredString(font, component, this.width / 2 - 74 + 144, this.height / 2 - 71 + 32 * i, 0);

                if (x > this.width / 2 - 100 - 13 + 140 + xOffset && x < this.width / 2 - 100 - 13 + 16 + 140 + xOffset
                        && y > this.height / 2 - 83 + 32 * i && y < this.height / 2 - 83 + 32 * i + 16) {
                    guiGraphics.renderTooltip(font, itemStack, x, y);
                }
            }
        }
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("当前vp: ").withStyle(ChatFormatting.LIGHT_PURPLE), this.width / 2 + 80, this.height / 2 + 86, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + VpDataHandler.clientVpValue).withStyle(ChatFormatting.WHITE), this.width / 2 + 128, this.height / 2 + 86, 0);


        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1)).withStyle(ChatFormatting.WHITE), this.width / 2, this.height / 2 - 20 + 105, 0);

        int textureWidth = 300;
        int textureHeight = 200;

        if (PlanPlayer.clientPlanTier > 0 && !(this.width / 4 < 150 && x > this.width / 2 - 150 && x < this.width / 2)) {
            List<Component> components = new ArrayList<>();
            components.add(SimpleTierPaper.getTierTitle(PlanPlayer.clientPlanTier));
            components.addAll(SimpleTierPaper.getTierDescription(PlanPlayer.clientPlanTier));
            components.add(Component.literal("计划还剩余: ").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(PlanPlayer.clientPlanLeftDate + "天").withStyle(CustomStyle.styleOfWorld)));
            guiGraphics.renderComponentTooltip(fontRenderer, components, 0, (int) (this.height / 2 / 1.5));
        }

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);
        super.render(graphics, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

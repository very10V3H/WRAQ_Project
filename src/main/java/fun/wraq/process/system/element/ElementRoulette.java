package fun.wraq.process.system.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.networking.ResonanceC2SPacket;
import fun.wraq.process.system.season.MySeason;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ElementRoulette extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/element_roulette.png");
    ResourceLocation lifeElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/life_element.png");
    ResourceLocation waterElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/water_element.png");
    ResourceLocation fireElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/fire_element.png");
    ResourceLocation stoneElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/stone_element.png");
    ResourceLocation iceElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/ice_element.png");
    ResourceLocation windElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/wind_element.png");
    ResourceLocation lightningElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/lightning_element.png");

    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;

    public ElementRoulette() {
        super(Component.translatable("menu.teamscreen"));
    }

    protected void init() {
        this.createMenu();
    }

    private void createMenu() {
        int X = this.width / 2;
        int Y = this.height / 2;
        if (this.minecraft == null) return;

        this.addRenderableWidget(Button.builder(Component.translatable("生机"), (p_280814_) -> {
            ModNetworking.sendToServer(new ResonanceC2SPacket(fun.wraq.process.system.element.Element.life));
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X - 14, Y - 89).size(28, 28).build());

        this.addRenderableWidget(Button.builder(Component.translatable("碧水"), (p_280814_) -> {
            ModNetworking.sendToServer(new ResonanceC2SPacket(fun.wraq.process.system.element.Element.water));
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X - 74, Y - 44).size(28, 28).build());

        this.addRenderableWidget(Button.builder(Component.translatable("炽焰"), (p_280814_) -> {
            ModNetworking.sendToServer(new ResonanceC2SPacket(fun.wraq.process.system.element.Element.fire));
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X + 46, Y - 44).size(28, 28).build());

        this.addRenderableWidget(Button.builder(Component.translatable("层岩"), (p_280814_) -> {
            ModNetworking.sendToServer(new ResonanceC2SPacket(fun.wraq.process.system.element.Element.stone));
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X + 46, Y + 16).size(28, 28).build());

        this.addRenderableWidget(Button.builder(Component.translatable("凛冰"), (p_280814_) -> {
            ModNetworking.sendToServer(new ResonanceC2SPacket(fun.wraq.process.system.element.Element.ice));
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X - 74, Y + 16).size(28, 28).build());

        this.addRenderableWidget(Button.builder(Component.translatable("澄风"), (p_280814_) -> {
            ModNetworking.sendToServer(new ResonanceC2SPacket(fun.wraq.process.system.element.Element.wind));
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X - 44, Y + 61).size(28, 28).build());

        this.addRenderableWidget(Button.builder(Component.translatable("怒雷"), (p_280814_) -> {
            ModNetworking.sendToServer(new ResonanceC2SPacket(fun.wraq.process.system.element.Element.lightning));
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X + 16, Y + 61).size(28, 28).build());
    }

    public void tick() {
        super.tick();
    }

    public void render(@NotNull GuiGraphics graphics, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int X = this.width / 2;
        int Y = this.height / 2;
        guiGraphics.blit(GUI_TEXTURE, X - 150, Y - 150, 0, 0, 300, 300, 300, 300);

        List<String> compressElementType = new ArrayList<>();
        List<String> compressedElementType = new ArrayList<>();

        Map<String, ResourceLocation> map = new HashMap<>() {{
            put(fun.wraq.process.system.element.Element.life, lifeElement);
            put(fun.wraq.process.system.element.Element.water, waterElement);
            put(fun.wraq.process.system.element.Element.fire, fireElement);
            put(fun.wraq.process.system.element.Element.stone, stoneElement);
            put(fun.wraq.process.system.element.Element.ice, iceElement);
            put(fun.wraq.process.system.element.Element.wind, windElement);
            put(fun.wraq.process.system.element.Element.lightning, lightningElement);
        }};

        String title = "选择元素";
        String type = "";
        // 218 125

        // -14 -89 life
        if (x > X - 14 && x < X - 14 + 28 && y > Y - 89 && y < Y - 89 + 28) {
            compressElementType.addAll(List.of(fun.wraq.process.system.element.Element.water, fun.wraq.process.system.element.Element.stone, fun.wraq.process.system.element.Element.lightning));
            compressedElementType.addAll(List.of(fun.wraq.process.system.element.Element.fire, fun.wraq.process.system.element.Element.ice, fun.wraq.process.system.element.Element.wind));
            type = fun.wraq.process.system.element.Element.life;
        }

        // -74 -44 water
        if (x > X - 74 && x < X - 74 + 28 && y > Y - 44 && y < Y - 44 + 28) {
            compressElementType.addAll(List.of(fun.wraq.process.system.element.Element.fire, fun.wraq.process.system.element.Element.wind));
            compressedElementType.addAll(List.of(fun.wraq.process.system.element.Element.life, fun.wraq.process.system.element.Element.lightning));
            type = fun.wraq.process.system.element.Element.water;
        }
        // -74 16 ice
        if (x > X - 74 && x < X - 74 + 28 && y > Y + 16 && y < Y + 16 + 28) {
            compressElementType.addAll(List.of(fun.wraq.process.system.element.Element.life, fun.wraq.process.system.element.Element.stone));
            compressedElementType.addAll(List.of(fun.wraq.process.system.element.Element.fire));
            type = fun.wraq.process.system.element.Element.ice;
        }

        // 46 -44 fire
        if (x > X + 46 && x < X + 46 + 28 && y > Y - 44 && y < Y - 44 + 28) {
            compressElementType.addAll(List.of(fun.wraq.process.system.element.Element.life, fun.wraq.process.system.element.Element.ice, fun.wraq.process.system.element.Element.wind));
            compressedElementType.addAll(List.of(fun.wraq.process.system.element.Element.water, fun.wraq.process.system.element.Element.stone, fun.wraq.process.system.element.Element.lightning));
            type = fun.wraq.process.system.element.Element.fire;
        }
        // 46 16 stone
        if (x > X + 46 && x < X + 46 + 28 && y > Y + 16 && y < Y + 16 + 28) {
            compressElementType.addAll(List.of(fun.wraq.process.system.element.Element.fire, fun.wraq.process.system.element.Element.wind));
            compressedElementType.addAll(List.of(fun.wraq.process.system.element.Element.life, fun.wraq.process.system.element.Element.ice));
            type = fun.wraq.process.system.element.Element.stone;
        }

        // -44 61 wind
        if (x > X - 44 && x < X - 44 + 28 && y > Y + 61 && y < Y + 61 + 28) {
            compressElementType.addAll(List.of(fun.wraq.process.system.element.Element.life, fun.wraq.process.system.element.Element.lightning));
            compressedElementType.addAll(List.of(fun.wraq.process.system.element.Element.water, fun.wraq.process.system.element.Element.fire, fun.wraq.process.system.element.Element.stone));
            type = fun.wraq.process.system.element.Element.wind;
        }
        // 16 61 lightning
        if (x > X + 16 && x < X + 16 + 28 && y > Y + 61 && y < Y + 61 + 28) {
            compressElementType.addAll(List.of(fun.wraq.process.system.element.Element.water, fun.wraq.process.system.element.Element.fire));
            compressedElementType.addAll(List.of(fun.wraq.process.system.element.Element.life, fun.wraq.process.system.element.Element.wind));
            type = fun.wraq.process.system.element.Element.lightning;
        }

        if (!compressElementType.isEmpty() && !type.isEmpty()) {
            for (int i = 0; i < compressElementType.size(); i++) {
                String elementType = compressElementType.get(i);
                if (i == 0)
                    guiGraphics.drawString(fontRenderer, Component.literal("▶").withStyle(ChatFormatting.GREEN), x + 12, y + 10, 0);
                guiGraphics.blit(map.get(elementType), x + i * 30 + 22, y, 0, 0, 28, 28, 28, 28);
            }
            for (int i = 1; i <= compressedElementType.size(); i++) {
                String elementType = compressedElementType.get(i - 1);
                if (i == 1)
                    guiGraphics.drawString(fontRenderer, Component.literal("▶").withStyle(ChatFormatting.RED), x - 14, y + 10, 0);
                guiGraphics.blit(map.get(elementType), x - i * 30 - 14, y, 0, 0, 28, 28, 28, 28);
            }
        }

        int yOffset = -110;
        if (y > this.height / 2 && this.width < 500) yOffset = 110;
        if (type.isEmpty()) {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(title).withStyle(ChatFormatting.AQUA), X, Y + yOffset, 0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(fun.wraq.process.system.element.Element.nameMap.get(type)).withStyle(fun.wraq.process.system.element.Element.styleMap.get(type)), X, Y + yOffset, 0);
        }

        List<Component> elementEffectContents = new ArrayList<>();
        if (MySeason.clientSeason != null) {
            elementEffectContents.add(Component.literal("").withStyle(ChatFormatting.WHITE).
                    append(MySeason.seasonComponentMap.get(MySeason.clientSeason)));
            if (fun.wraq.process.system.element.Element.clientResonanceType != null && !fun.wraq.process.system.element.Element.clientResonanceType.isEmpty()) {
                elementEffectContents.add(Component.literal("  当前共鸣:").withStyle(ChatFormatting.AQUA).
                        append(Component.literal(fun.wraq.process.system.element.Element.nameMap.get(fun.wraq.process.system.element.Element.clientResonanceType)).withStyle(fun.wraq.process.system.element.Element.styleMap.get(fun.wraq.process.system.element.Element.clientResonanceType))));
            } else {
                elementEffectContents.add(Component.literal("  当前共鸣:").withStyle(ChatFormatting.AQUA).
                        append(Component.literal("无").withStyle(ChatFormatting.WHITE)));
            }
            elementEffectContents.addAll(MySeason.getElementEffectContent(MySeason.clientSeason));
        }
        guiGraphics.renderComponentTooltip(fontRenderer, elementEffectContents, 0, 28);

        if (!StringUtils.isBlank(type)) {
            List<Component> elementReactions = new ArrayList<>();
            elementReactions.add(Te.m(fun.wraq.process.system.element.Element.nameMap.get(type), fun.wraq.process.system.element.Element.styleMap.get(type)).
                    append(Te.m("参与的反应:")));
            for (String element : fun.wraq.process.system.element.Element.elementList) {
                if (!element.equals(type)) {
                    Pair<Component, Component> pair = fun.wraq.process.system.element.Element.ReactionDescription.getReactionPair(new Pair<>(type, element));
                    elementReactions.add(Te.m(fun.wraq.process.system.element.Element.shortNameMap.get(type), fun.wraq.process.system.element.Element.styleMap.get(type)).
                            append(Te.m(" + ")).
                            append(Te.m(fun.wraq.process.system.element.Element.shortNameMap.get(element), Element.styleMap.get(element))).
                            append(Te.m(" = ")).
                            append(pair.getFirst()).
                            append(Te.m(" - ")).
                            append(pair.getSecond()));
                }
            }
            if (y < this.height / 2 && this.width < 500) {
                guiGraphics.renderComponentTooltip(fontRenderer, elementReactions, this.width, this.height);
            } else {
                guiGraphics.renderComponentTooltip(fontRenderer, elementReactions, this.width, 28);
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

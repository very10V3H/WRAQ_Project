package fun.wraq.render.hud.main;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fun.wraq.render.hud.main.HudUtils.*;

public class MobAttributesHud {
    public static final IGuiOverlay MOB_ATTRIBUTES_HUD = ((gui, poseStack, partialTick, width, height) -> {

        Minecraft mc = Minecraft.getInstance();
        Font fontRenderer = mc.font;

        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        // livingEntity attribute
        if (ClientUtils.mobAttribute != null) {
            int count = 0;
            int offsetX = 34;
            int offsetY = -8;
            if (ClientUtils.clientMobEffectMap.containsKey(ClientUtils.mobAttribute)) {
                List<ClientUtils.Effect> effectList = ClientUtils.clientMobEffectMap.get(ClientUtils.mobAttribute);
                effectList.sort((Comparator.comparingInt(ClientUtils.Effect::startTick)));
                for (ClientUtils.Effect effect : effectList) {
                    int time = (int) (12 * (1 - (double) (ClientUtils.clientPlayerTick - effect.startTick()) / effect.lastTick()));
                    if (time < 0 && !effect.forever()) continue;
                    guiGraphics.blit(new ResourceLocation(Utils.MOD_ID, "textures/" + effect.url() + ".png"),
                            count * 15 + offsetX, 60 + offsetY, 0, 0, 16, 16, 16, 16);
                    if (effect.forever()) time = 12;
                    guiGraphics.blit(ClientUtils.CdResourceLocation[time],
                            count * 15 + offsetX, 60 + offsetY, 0, 0, 16, 16, 16, 16);
                    if (effect.level() > 0) {
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + effect.level())
                                .withStyle(ChatFormatting.WHITE), count * 15 + 11 + offsetX, 68 + offsetY, 10);
                    }

                    int leftTick = effect.lastTick() - (ClientUtils.clientPlayerTick - effect.startTick());
                    if (leftTick > 0 && effect.level() == 0 && !effect.forever()) {
                        guiGraphics.drawCenteredString(fontRenderer,
                                Component.literal(leftTick >= 20 ?
                                                String.format("%.0f", leftTick / 20d) : String.format("%.1f", leftTick / 20d))
                                        .withStyle(ChatFormatting.WHITE), count * 15 + 11 + offsetX, 68 + offsetY, 10);
                    }
                    count++;
                }
                effectList.removeIf(e -> !e.forever() && ClientUtils.clientPlayerTick > e.startTick() + e.lastTick());
            }

            int standardX = 0;
            int standardY = 10;
            boolean isMob = (ClientUtils.mobAttribute.getId() == ClientUtils.mobId);
            if (isMob) {
                guiGraphics.drawString(fontRenderer, ClientUtils.mobAttribute.getDisplayName(), standardX, standardY - 8, 0);

                RenderSystem.setShaderTexture(0, ATTACK);
                guiGraphics.blit(ATTACK, standardX, standardY, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.mobAttack)).withStyle(CustomStyle.styleOfAttack),
                        standardX + 24, standardY + 3, 0);

                RenderSystem.setShaderTexture(0, DEFENCE);
                guiGraphics.blit(DEFENCE, standardX, standardY + 10, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.mobDefence)).withStyle(CustomStyle.styleOfDefence),
                        standardX + 24, standardY + 13, 0);

                RenderSystem.setShaderTexture(0, MANA_DEFENCE);
                guiGraphics.blit(MANA_DEFENCE, standardX, standardY + 20, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.mobManaDefence)).withStyle(CustomStyle.styleOfManaDefence),
                        standardX + 24, standardY + 23, 0);

                RenderSystem.setShaderTexture(0, CRIT_RATE);
                guiGraphics.blit(CRIT_RATE, standardX, standardY + 30, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.mobCritRate * 100)).withStyle(CustomStyle.styleOfCritRate),
                        standardX + 24, standardY + 33, 0);

                RenderSystem.setShaderTexture(0, DEFENCE_PENETRATION);
                guiGraphics.blit(DEFENCE_PENETRATION, standardX + 48, standardY, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.mobDefencePenetration * 100)).withStyle(CustomStyle.styleOfDefencePenetration),
                        standardX + 72, standardY + 3, 0);

                RenderSystem.setShaderTexture(0, DEFENCE_PENETRATION0);
                guiGraphics.blit(DEFENCE_PENETRATION0, standardX + 48, standardY + 10, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.mobDefencePenetration0)).withStyle(CustomStyle.styleOfDefencePenetration),
                        standardX + 72, standardY + 13, 0);

                RenderSystem.setShaderTexture(0, HEALTH_STEAL);
                guiGraphics.blit(HEALTH_STEAL, standardX + 48, standardY + 20, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.mobHealthSteal * 100)).withStyle(CustomStyle.styleOfHealthSteal),
                        standardX + 72, standardY + 23, 0);

                RenderSystem.setShaderTexture(0, CRIT_DAMAGE);
                guiGraphics.blit(CRIT_DAMAGE, standardX + 48, standardY + 30, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.mobCritDamage * 100)).withStyle(CustomStyle.styleOfCritDamage),
                        standardX + 72, standardY + 33, 0);

                Map<String, String> typeLocationMap = new HashMap<>() {{
                    put(Element.life, "life_element");
                    put(Element.water, "water_element");
                    put(Element.fire, "fire_element");
                    put(Element.stone, "stone_element");
                    put(Element.ice, "ice_element");
                    put(Element.lightning, "lightning_element");
                    put(Element.wind, "wind_element");
                }};
                Map<String, Style> typeStyleMap = new HashMap<>() {{
                    put(Element.life, CustomStyle.styleOfLife);
                    put(Element.water, CustomStyle.styleOfWater);
                    put(Element.fire, CustomStyle.styleOfFire);
                    put(Element.stone, CustomStyle.styleOfStone);
                    put(Element.ice, CustomStyle.styleOfIce);
                    put(Element.lightning, CustomStyle.styleOfLightning);
                    put(Element.wind, CustomStyle.styleOfWind);
                }};
                if (typeLocationMap.containsKey(ClientUtils.mobElementType)) {
                    guiGraphics.blit(new ResourceLocation(Utils.MOD_ID, "textures/hud/" + typeLocationMap.get(ClientUtils.mobElementType) + ".png"),
                            standardX, standardY + 40, 0, 0, 12, 12, 12, 12);
                }
                if (ClientUtils.mobElementValue > 0) {
                    guiGraphics.drawCenteredString(fontRenderer,
                            Component.literal(String.format("%.0f%%", ClientUtils.mobElementValue * 100))
                                    .withStyle(typeStyleMap.get(ClientUtils.mobElementType)),
                            standardX + 22, standardY + 43, 0);
                }
            }
        }
    });
}

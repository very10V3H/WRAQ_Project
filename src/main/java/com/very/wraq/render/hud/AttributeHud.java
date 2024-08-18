package com.very.wraq.render.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.very.wraq.common.Utils.ClientUtils;
import com.very.wraq.common.Utils.Struct.EffectTimeLast;
import com.very.wraq.common.Utils.Struct.SkillImage;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.endlessinstance.DailyEndlessInstance;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.*;

public class AttributeHud {
    private static final ResourceLocation ATTACK = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/attack0.png");
    private static final ResourceLocation DEFENCE_PENETRATION = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/breakdefence.png");
    private static final ResourceLocation COOL_DOWN = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/cooldown0.png");
    private static final ResourceLocation CRIT_DAMAGE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/critdamage0.png");
    private static final ResourceLocation CRIT_RATE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/critrate0.png");
    private static final ResourceLocation DEFENCE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/defence0.png");
    private static final ResourceLocation MANA_DAMAGE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manadamage0.png");
    private static final ResourceLocation MANA_DEFENCE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manadefence0.png");
    private static final ResourceLocation MANA_DEFENCE_PENETRATION = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manadefencebreak0.png");
    private static final ResourceLocation MANA_REPLY = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manareply0.png");
    private static final ResourceLocation SPEED = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/speed0.png");
    private static final ResourceLocation HEALTH_STEAL = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/healsteal0.png");

    private static final ResourceLocation MANA_HEALTH_STEAL = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/mana_health_steal.png");
    private static final ResourceLocation SWIFT = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/swift.png");
    private static final ResourceLocation DODGE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/dodge.png");
    private static final ResourceLocation HEALTH_RECOVER = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/healthrecover.png");

    private static final ResourceLocation EMPTYMANA = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/emptymana.png");
    private static final ResourceLocation DEFENCE_PENETRATION0 = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/breakdefence0.png");
    private static final ResourceLocation MANA_PENETRATION0 = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manabreakdefence0.png");
    private static final ResourceLocation ATTACK_RANGE_UP = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/attackrangeup.png");
    private static final ResourceLocation EXP_UP = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/expup.png");
    private static final ResourceLocation SakuraDemon = new ResourceLocation(Utils.MOD_ID,
            "textures/item/sakurasword.png");
    private static final ResourceLocation ZeusSword = new ResourceLocation(Utils.MOD_ID,
            "textures/item/zeus_sword.png");

    private static final ResourceLocation[] RESOURCE_LOCATIONS = {
            new ResourceLocation(Utils.MOD_ID, "textures/hud/emptymana.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/effectmana.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/rangemana.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/damagemana.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/breakdefencemana.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/kazemana.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/snowmana.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/lightningmana.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/gathermana.png"),
    };
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    public static final IGuiOverlay HUD_ATTRIBUTE = ((gui, poseStack, partialTick, width, height) -> {

        ItemStack mainHandStack = mc.player.getMainHandItem();
        Item mainHandItem = mainHandStack.getItem();
        int x = width / 2;
        int y = height;

        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        int xOffset = 0;

        if (Utils.sceptreTag.containsKey(mainHandItem)) {
            RenderSystem.setShaderTexture(0, MANA_DAMAGE);
            guiGraphics.blit(MANA_DAMAGE, x - 244 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.ManaDamageC)).withStyle(CustomStyle.styleOfMana), x - 219 + xOffset, y - 38, 0);
        } else {
            RenderSystem.setShaderTexture(0, ATTACK);
            guiGraphics.blit(ATTACK, x - 244 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.AttackDamageC)), x - 219 + xOffset, y - 38, 5636095);
        }

        if (Utils.sceptreTag.containsKey(mainHandItem)) {
            RenderSystem.setShaderTexture(0, MANA_DEFENCE_PENETRATION);
            guiGraphics.blit(MANA_DEFENCE_PENETRATION, x - 244 + xOffset, y - 31, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.BreakManaDefenceC * 100)), x - 219 + xOffset, y - 28, 5592575);
        } else {
            RenderSystem.setShaderTexture(0, DEFENCE_PENETRATION);
            guiGraphics.blit(DEFENCE_PENETRATION, x - 244 + xOffset, y - 31, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.BreakDefenceC * 100)), x - 219 + xOffset, y - 28, 11184810);
        }

        RenderSystem.setShaderTexture(0, CRIT_RATE);
        guiGraphics.blit(CRIT_RATE, x - 244 + xOffset, y - 21, 0, 0, 12, 12, 12, 12);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.CritRateC * 100)), x - 219 + xOffset, y - 18, 16733695);

        RenderSystem.setShaderTexture(0, CRIT_DAMAGE);
        guiGraphics.blit(CRIT_DAMAGE, x - 244 + xOffset, y - 11, 0, 0, 12, 12, 12, 12);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.CritDamageC * 100)), x - 219 + xOffset, y - 8, 5592575);

        if (Utils.swordTag.containsKey(mainHandItem)) {
            RenderSystem.setShaderTexture(0, HEALTH_RECOVER);
            guiGraphics.blit(HEALTH_RECOVER, x - 202 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.clientHealthRecover)).withStyle(CustomStyle.styleOfLife), x - 177 + xOffset, y - 38, 0);
        } else if (Utils.mainHandTag.containsKey(mainHandItem)) {
            RenderSystem.setShaderTexture(0, DODGE);
            guiGraphics.blit(DODGE, x - 202 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.clientDodgeRate * 100)).withStyle(CustomStyle.styleOfFlexible), x - 177 + xOffset, y - 38, 0);
        } else {
            RenderSystem.setShaderTexture(0, MANA_DAMAGE);
            guiGraphics.blit(MANA_DAMAGE, x - 202 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.ManaDamageC)).withStyle(CustomStyle.styleOfMana), x - 177 + xOffset, y - 38, 0);
        }

        if (Utils.swordTag.containsKey(mainHandItem) || Utils.bowTag.containsKey(mainHandItem)) {
            RenderSystem.setShaderTexture(0, DEFENCE_PENETRATION0);
            guiGraphics.blit(DEFENCE_PENETRATION0, x - 202 + xOffset, y - 31, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.BreakDefence0C)).withStyle(CustomStyle.styleOfMine), x - 177 + xOffset, y - 28, 0);
        } else {
            RenderSystem.setShaderTexture(0, MANA_PENETRATION0);
            guiGraphics.blit(MANA_PENETRATION0, x - 202 + xOffset, y - 31, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.BreakManaDefence0C)), x - 177 + xOffset, y - 28, 5592575);
        }


        RenderSystem.setShaderTexture(0, MANA_REPLY);
        guiGraphics.blit(MANA_REPLY, x - 202 + xOffset, y - 21, 0, 0, 12, 12, 12, 12);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.ManaReplyC + 5)), x - 177 + xOffset, y - 18, 16733695);

        RenderSystem.setShaderTexture(0, COOL_DOWN);
        guiGraphics.blit(COOL_DOWN, x - 202 + xOffset, y - 11, 0, 0, 12, 12, 12, 12);
        if (mc.player.isShiftKeyDown())
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", (1 - (1 / (1 + (ClientUtils.CoolDownC)))) * 100)), x - 177 + xOffset, y - 8, 5636095);
        else
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.CoolDownC * 100)), x - 177 + xOffset, y - 8, 5636095);

        if (Utils.bowTag.containsKey(mainHandItem)) {
            RenderSystem.setShaderTexture(0, SWIFT);
            guiGraphics.blit(SWIFT, x - 160 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.clientSwiftness)).withStyle(CustomStyle.styleOfFlexible), x - 135 + xOffset, y - 38, 0);
        } else if (Utils.sceptreTag.containsKey(mainHandItem)) {
            RenderSystem.setShaderTexture(0, MANA_HEALTH_STEAL);
            guiGraphics.blit(MANA_HEALTH_STEAL, x - 160 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.clientManaHealthSteal * 100)).withStyle(CustomStyle.styleOfMana), x - 135 + xOffset, y - 38, 0);
        } else {
            RenderSystem.setShaderTexture(0, HEALTH_STEAL);
            guiGraphics.blit(HEALTH_STEAL, x - 160 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.HealStealC * 100)), x - 135 + xOffset, y - 38, 16733525);
        }

        RenderSystem.setShaderTexture(0, DEFENCE);
        guiGraphics.blit(DEFENCE, x - 160 + xOffset, y - 31, 0, 0, 12, 12, 12, 12);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.DefenceC)), x - 135 + xOffset, y - 28, 11184810);

        RenderSystem.setShaderTexture(0, MANA_DEFENCE);
        guiGraphics.blit(MANA_DEFENCE, x - 160 + xOffset, y - 21, 0, 0, 12, 12, 12, 12);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.ManaDefenceC)), x - 135 + xOffset, y - 18, 5592575);

        RenderSystem.setShaderTexture(0, SPEED);
        guiGraphics.blit(SPEED, x - 160 + xOffset, y - 11, 0, 0, 12, 12, 12, 12);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.SpeedC * 100)), x - 135 + xOffset, y - 8, 5635925);

        boolean mob = false;

        // mob attribute
        if (ClientUtils.mobAttribute != null) {
            mob = true;
            if (ClientUtils.elementEffects != null) {
                if (ClientUtils.elementEffects.level != 0) {
                    guiGraphics.blit(new ResourceLocation(Utils.MOD_ID, "textures/item/" + ClientUtils.elementEffects.itemStack.getItem().toString() + ".png"), x - 22, height / 2 + 8, 0, 0, 16, 16, 16, 16);
                    guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + ClientUtils.elementEffects.level).withStyle(ChatFormatting.WHITE), x + 11 - 22, height / 2 + 16, 10);
                }
            }

            int count = 0;
            int offsetX = 0;
            int offsetY = 2;
            if (ClientUtils.clientMobEffectMap.containsKey(ClientUtils.mobAttribute)) {
                List<ClientUtils.Effect> effectList = ClientUtils.clientMobEffectMap.get(ClientUtils.mobAttribute);
                for (ClientUtils.Effect effect : effectList) {
                    int time = (int) (12 * (1 - (double) (ClientUtils.clientPlayerTick - effect.startTick()) / effect.lastTick()));
                    if (time < 0 && !effect.forever()) continue;
                    guiGraphics.blit(new ResourceLocation(Utils.MOD_ID, "textures/item/" + effect.itemStack().getItem().toString() + ".png"),
                            count * 15 + offsetX, 60 + offsetY, 0, 0, 16, 16, 16, 16);
                    if (effect.forever()) time = 12;
                    guiGraphics.blit(ClientUtils.CdResourceLocation[time], count * 15 + offsetX, 60 + offsetY, 0, 0, 16, 16, 16, 16);
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
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.mobAttack)).withStyle(CustomStyle.styleOfAttack), standardX + 20, standardY + 3, 0);

                RenderSystem.setShaderTexture(0, DEFENCE);
                guiGraphics.blit(DEFENCE, standardX, standardY + 10, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.mobDefence)).withStyle(CustomStyle.styleOfDefence), standardX + 20, standardY + 13, 0);

                RenderSystem.setShaderTexture(0, MANA_DEFENCE);
                guiGraphics.blit(MANA_DEFENCE, standardX, standardY + 20, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.mobManaDefence)).withStyle(CustomStyle.styleOfManaDefence), standardX + 20, standardY + 23, 0);

                RenderSystem.setShaderTexture(0, CRIT_RATE);
                guiGraphics.blit(CRIT_RATE, standardX, standardY + 30, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.mobCritRate)).withStyle(CustomStyle.styleOfCritRate), standardX + 20, standardY + 33, 0);

                RenderSystem.setShaderTexture(0, DEFENCE_PENETRATION);
                guiGraphics.blit(DEFENCE_PENETRATION, standardX + 48, standardY, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.mobDefencePenetration)).withStyle(CustomStyle.styleOfDefencePenetration), standardX + 68, standardY + 3, 0);

                RenderSystem.setShaderTexture(0, DEFENCE_PENETRATION0);
                guiGraphics.blit(DEFENCE_PENETRATION0, standardX + 48, standardY + 10, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.mobDefencePenetration0)).withStyle(CustomStyle.styleOfDefencePenetration), standardX + 68, standardY + 13, 0);

                RenderSystem.setShaderTexture(0, HEALTH_STEAL);
                guiGraphics.blit(HEALTH_STEAL, standardX + 48, standardY + 20, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.mobHealthSteal)).withStyle(CustomStyle.styleOfHealthSteal), standardX + 68, standardY + 23, 0);

                RenderSystem.setShaderTexture(0, CRIT_DAMAGE);
                guiGraphics.blit(CRIT_DAMAGE, standardX + 48, standardY + 30, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.mobCritDamage)).withStyle(CustomStyle.styleOfCritDamage), standardX + 68, standardY + 33, 0);

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
                if (typeLocationMap.containsKey(ClientUtils.mobElementType))
                    guiGraphics.blit(new ResourceLocation(Utils.MOD_ID, "textures/hud/" + typeLocationMap.get(ClientUtils.mobElementType) + ".png"), standardX, standardY + 40, 0, 0, 12, 12, 12, 12);
                if (ClientUtils.mobElementValue > 0)
                    guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.mobElementValue * 100)).withStyle(typeStyleMap.get(ClientUtils.mobElementType)), standardX + 20, standardY + 43, 0);
            }
        }

        // playerAttributes
        if (!mob && ClientUtils.playerAttribute != null && !ClientUtils.playerAttributeList.isEmpty()) {
            xOffset = 0;
            int yOffset = 10;
            int standardX = 0;
            int standardY = 10;
            boolean isPlayer = (ClientUtils.playerAttribute.getId() == ClientUtils.playerId);
            if (isPlayer) {
                guiGraphics.drawString(fontRenderer, ClientUtils.playerAttribute.getDisplayName(), standardX, standardY - 8, 0);

                RenderSystem.setShaderTexture(0, ATTACK);
                guiGraphics.blit(ATTACK, 0 + xOffset, 0 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(0))), 20 + xOffset, 3 + yOffset, 5636095);

                RenderSystem.setShaderTexture(0, DEFENCE_PENETRATION);
                guiGraphics.blit(DEFENCE_PENETRATION, 0 + xOffset, 10 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(1) * 100)), 20 + xOffset, 13 + yOffset, 11184810);

                RenderSystem.setShaderTexture(0, CRIT_RATE);
                guiGraphics.blit(CRIT_RATE, 0 + xOffset, 20 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(2) * 100)), 20 + xOffset, 23 + yOffset, 16733695);

                RenderSystem.setShaderTexture(0, CRIT_DAMAGE);
                guiGraphics.blit(CRIT_DAMAGE, 0 + xOffset, 30 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(3) * 100)), 20 + xOffset, 33 + yOffset, 5592575);

                RenderSystem.setShaderTexture(0, MANA_DAMAGE);
                guiGraphics.blit(MANA_DAMAGE, 40 + xOffset, 0 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(4))), 60 + xOffset, 3 + yOffset, 16733695);

                RenderSystem.setShaderTexture(0, MANA_DEFENCE_PENETRATION);
                guiGraphics.blit(MANA_DEFENCE_PENETRATION, 40 + xOffset, 10 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(5) * 100)), 60 + xOffset, 13 + yOffset, 5592575);

                RenderSystem.setShaderTexture(0, MANA_REPLY);
                guiGraphics.blit(MANA_REPLY, 40 + xOffset, 20 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(6) + 5)), 60 + xOffset, 23 + yOffset, 16733695);

                RenderSystem.setShaderTexture(0, COOL_DOWN);
                guiGraphics.blit(COOL_DOWN, 40 + xOffset, 30 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(7) * 100)), 60 + xOffset, 33 + yOffset, 5636095);

                RenderSystem.setShaderTexture(0, HEALTH_STEAL);
                guiGraphics.blit(HEALTH_STEAL, 80 + xOffset, 0 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(8) * 100)), 100 + xOffset, 3 + yOffset, 16733525);

                RenderSystem.setShaderTexture(0, DEFENCE);
                guiGraphics.blit(DEFENCE, 80 + xOffset, 10 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(9))), 100 + xOffset, 13 + yOffset, 11184810);

                RenderSystem.setShaderTexture(0, MANA_DEFENCE);
                guiGraphics.blit(MANA_DEFENCE, 80 + xOffset, 20 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(10))), 100 + xOffset, 23 + yOffset, 5592575);

                RenderSystem.setShaderTexture(0, SPEED);
                guiGraphics.blit(SPEED, 80 + xOffset, 30 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(11) * 100)), 100 + xOffset, 33 + yOffset, 5635925);

                guiGraphics.blit(DEFENCE_PENETRATION0, 120 + xOffset, 0 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(12))).withStyle(ChatFormatting.GRAY), 140 + xOffset, 3 + yOffset, 0);

                guiGraphics.blit(MANA_PENETRATION0, 120 + xOffset, 10 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.playerAttributeList.get(13))).withStyle(ChatFormatting.BLUE), 140 + xOffset, 13 + yOffset, 0);

                guiGraphics.blit(ATTACK_RANGE_UP, 120 + xOffset, 20 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.2f", ClientUtils.playerAttributeList.get(14))).withStyle(CustomStyle.styleOfSea), 140 + xOffset, 23 + yOffset, 0);

                guiGraphics.blit(EXP_UP, 120 + xOffset, 30 + yOffset, 0, 0, 12, 12, 12, 12);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.playerAttributeList.get(15) * 100)).withStyle(ChatFormatting.LIGHT_PURPLE), 140 + xOffset, 33 + yOffset, 0);
            }
        }


        if (mc.player.isShiftKeyDown()) {

            guiGraphics.blit(DEFENCE_PENETRATION0, x + 95 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.BreakDefence0C)).withStyle(ChatFormatting.GRAY), x + 120 + xOffset, y - 38, 0);

            guiGraphics.blit(MANA_PENETRATION0, x + 95 + xOffset, y - 31, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.BreakManaDefence0C)).withStyle(ChatFormatting.BLUE), x + 120 + xOffset, y - 28, 0);

            guiGraphics.blit(ATTACK_RANGE_UP, x + 95 + xOffset, y - 21, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.2f", ClientUtils.AttackRangeUpC)).withStyle(CustomStyle.styleOfSea), x + 120 + xOffset, y - 18, 0);

            guiGraphics.blit(EXP_UP, x + 95 + xOffset, y - 11, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.ExpUpC * 100)).withStyle(ChatFormatting.LIGHT_PURPLE), x + 120 + xOffset, y - 8, 0);
        }

        if (ClientUtils.IsAdjustingPower && ClientUtils.IsHandlingPower) {

            int power1 = 0, power2 = 0, power3 = 0, power4 = 0;
            Iterator<Integer> iterator = ClientUtils.PowerQueue.iterator();

            if (iterator.hasNext()) power1 = iterator.next();
            if (iterator.hasNext()) power2 = iterator.next();
            if (iterator.hasNext()) power3 = iterator.next();
            if (iterator.hasNext()) power4 = iterator.next();

            RenderSystem.setShaderTexture(0, RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power4)]);
            guiGraphics.blit(RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power4)], x - 26, y - 150, 0, 0, 12, 12, 12, 12);

            RenderSystem.setShaderTexture(0, RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power3)]);
            guiGraphics.blit(RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power3)], x + 13, y - 150, 0, 0, 12, 12, 12, 12);

            RenderSystem.setShaderTexture(0, RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power2)]);
            guiGraphics.blit(RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power2)], x - 26, y - 130, 0, 0, 12, 12, 12, 12);

            RenderSystem.setShaderTexture(0, RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power1)]);
            guiGraphics.blit(RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power1)], x + 13, y - 130, 0, 0, 12, 12, 12, 12);
        }

        int Count = 0;
        int XXOffset = 95;

        for (int i = 1; i <= 15; i++) {
            if (ClientUtils.Rune_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Rune_Image[i];
                if (skillImage.getTickTime() > 0) {
                    guiGraphics.blit(ClientUtils.RuneResourceLocation[i], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    int Time = 12 - (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    Count++;
                }
            }
        }

        if (mc.player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.SakuraDemonSword.get()) && ClientUtils.ChargedCountsSakuraDemonSword > 0) {
            guiGraphics.blit(SakuraDemon, x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (int) ClientUtils.ChargedCountsSakuraDemonSword).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 10, y - 52, 0);
            Count++;
        }

/*        if (mc.player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ZeusSword.get()) && ClientUtils.ChargedCountsZeusSword > 0) {
            guiGraphics.blit(ZeusSword,x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (int) ClientUtils.ChargedCountsZeusSword).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 10, y - 52,0);
            Count ++;
        }*/

        for (int i = 1; i <= 15; i++) {
            if (ClientUtils.Demon_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Demon_Image[i];
                if (skillImage.getTickTime() > 0) {
                    if (i == 1) guiGraphics.blit(SakuraDemon, x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    if (i == 2) guiGraphics.blit(ZeusSword, x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    if (skillImage.getNum() > 0)
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 10, y - 52, 0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    Count++;
                }
            }
        }

        if (ClientUtils.SwordSkillPoint.PointCache != null) {
            if (ClientUtils.SwordSkillsResource.resourceLocations == null) ClientUtils.SwordSkillsResource.init();
            if (ClientUtils.SwordSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.SwordSkillsResource.resourceLocations[13], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (int) ClientUtils.ChargedCountsSwordSkill12).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 10, y - 52, 0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                Count++;
            }
        }

        if (ClientUtils.ManaSkillPoint.PointCache != null) {
            if (ClientUtils.ManaSkillsResource.resourceLocations == null) ClientUtils.ManaSkillsResource.init();
            if (ClientUtils.ManaSkillPoint.Point[14] != 0) {
                guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[14], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (int) ClientUtils.ChargedCountsManaSkill13).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 10, y - 52, 0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                Count++;
            }
        }

        if (ClientUtils.ManaSkillPoint.PointCache != null) {
            if (ClientUtils.ManaSkillsResource.resourceLocations == null) ClientUtils.ManaSkillsResource.init();
            if (ClientUtils.ManaSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[13], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (int) ClientUtils.ChargedCountsManaSkill12).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 10, y - 52, 0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                Count++;
            }
        }

        if (ClientUtils.BowSkillPoint.PointCache != null) {
            if (ClientUtils.BowSkillsResource.resourceLocations == null) ClientUtils.BowSkillsResource.init();
            if (ClientUtils.BowSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.BowSkillsResource.resourceLocations[13], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (int) ClientUtils.ChargedCountsBowSkill12).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 10, y - 52, 0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                Count++;
            }
        }


        for (int i = 1; i <= 15; i++) {
            if (ClientUtils.Sword_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Sword_Image[i];
                if (skillImage.getTickTime() > 0) {
                    guiGraphics.blit(ClientUtils.SwordSkillsResource.resourceLocations[i], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    if (skillImage.getNum() > 0)
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 10, y - 52, 0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    Count++;
                }
            }
        }
        for (int i = 1; i <= 15; i++) {
            if (ClientUtils.Bow_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Bow_Image[i];
                if (skillImage.getTickTime() > 0 || i == 14 && ClientUtils.BowSkillPoint.Point[14] > 0) {
                    guiGraphics.blit(ClientUtils.BowSkillsResource.resourceLocations[i], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    if (skillImage.getNum() > 0)
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 10, y - 52, 0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    if (i == 14) Time = 12 - Time;
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    Count++;
                }
            }
        }
        for (int i = 1; i <= 15; i++) {
            if (ClientUtils.Mana_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Mana_Image[i];
                if (skillImage.getTickTime() > 0) {
                    guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[i], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    if (skillImage.getNum() > 0)
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 10, y - 52, 0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    Count++;
                }
            }
        }

        ClientUtils.effectTimeLasts.sort(new Comparator<EffectTimeLast>() {
            @Override
            public int compare(EffectTimeLast o1, EffectTimeLast o2) {
                if (o1.lastTick != o2.lastTick) {
                    return o1.lastTick - o2.lastTick;
                } else {
                    return o1.itemStack.getItem().toString().compareTo(o2.itemStack.getItem().toString());
                }
            }
        });

        for (EffectTimeLast effectTimeLast : ClientUtils.effectTimeLasts) {
            guiGraphics.blit(new ResourceLocation(Utils.MOD_ID, "textures/item/" + effectTimeLast.itemStack.getItem().toString() + ".png"), x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
            int Time = (int) Math.ceil(effectTimeLast.lastTick * 12.0f / effectTimeLast.maxTick);
            if (effectTimeLast.forever) Time = 12;
            guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
            if (effectTimeLast.level > 0)
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + effectTimeLast.level).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 11, y - 52, 10);
            if (effectTimeLast.lastTick > 0 && effectTimeLast.level == 0 && !effectTimeLast.forever)
                guiGraphics.drawCenteredString(fontRenderer,
                        Component.literal(effectTimeLast.lastTick >= 20 ? String.format("%.0f", effectTimeLast.lastTick / 20d) : String.format("%.1f", effectTimeLast.lastTick / 20d)).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 11, y - 52, 10);
            Count++;
        }

        Count++;

        for (EffectTimeLast effectTimeLast : ClientUtils.coolDownTimes) {
            guiGraphics.blit(new ResourceLocation(Utils.MOD_ID, "textures/item/" + effectTimeLast.itemStack.getItem().toString() + ".png"), x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
            int Time = (int) (12 - effectTimeLast.lastTick * 12.0f / effectTimeLast.maxTick);
            guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + Count * 15, y - 60, 0, 0, 16, 16, 16, 16);
            if (effectTimeLast.lastTick > 0) guiGraphics.drawCenteredString(fontRenderer,
                    Component.literal(effectTimeLast.lastTick >= 20 ? String.format("%.0f", effectTimeLast.lastTick / 20d) : String.format("%.1f", effectTimeLast.lastTick / 20d)).withStyle(ChatFormatting.WHITE), x + XXOffset + Count * 15 + 11, y - 52, 10);
            Count++;
        }

        Count++;

        if (DailyEndlessInstance.clientKillCount > 0 && DailyEndlessInstance.clientLastTick > 0) {
            List<Style> styleList = List.of(CustomStyle.styleOfPlain, CustomStyle.styleOfForest,
                    CustomStyle.styleOfLake, CustomStyle.styleOfVolcano, CustomStyle.styleOfPower);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(DailyEndlessInstance.clientKillCount
                                    + "!".repeat(DailyEndlessInstance.clientKillCount % 10))
                            .withStyle(styleList.get(Math.min(4, DailyEndlessInstance.clientKillCount / 50))),
                    width / 2 + 16, height / 2 - 16, 0);
        }
    });
}

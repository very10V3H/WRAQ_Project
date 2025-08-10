package fun.wraq.render.hud.main;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import static fun.wraq.render.hud.main.HudUtils.*;

public class PlayerSelfAttributesHud {
    public static final IGuiOverlay SELF_ATTRIBUTES_HUD = ((gui, poseStack, partialTick, width, height) -> {

        Minecraft mc = Minecraft.getInstance();
        Font fontRenderer = mc.font;
        ItemStack mainHandStack = mc.player.getMainHandItem();
        Item mainHandItem = mainHandStack.getItem();

        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        int xOffset = 0;
        int x = width / 2;
        int y = height;

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
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", (1 - (2 / (2 + (ClientUtils.CoolDownC)))) * 100)), x - 177 + xOffset, y - 8, 5636095);
        else
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.CoolDownC * 100)), x - 177 + xOffset, y - 8, 5636095);

        if (Utils.bowTag.containsKey(mainHandItem)) {
            RenderSystem.setShaderTexture(0, SWIFT);
            guiGraphics.blit(SWIFT, x - 160 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.clientSwiftness)).withStyle(CustomStyle.styleOfFlexible), x - 135 + xOffset, y - 38, 0);
        } else if (Utils.sceptreTag.containsKey(mainHandItem)) {
            RenderSystem.setShaderTexture(0, MANA_HEALTH_STEAL);
            guiGraphics.blit(MANA_HEALTH_STEAL, x - 160 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f‰", ClientUtils.clientManaHealthSteal * 100)).withStyle(CustomStyle.styleOfMana), x - 135 + xOffset, y - 38, 0);
        } else {
            RenderSystem.setShaderTexture(0, HEALTH_STEAL);
            guiGraphics.blit(HEALTH_STEAL, x - 160 + xOffset, y - 41, 0, 0, 12, 12, 12, 12);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f‰", ClientUtils.HealStealC * 100)), x - 135 + xOffset, y - 38, 16733525);
        }

        RenderSystem.setShaderTexture(0, DEFENCE);
        guiGraphics.blit(DEFENCE, x - 160 + xOffset, y - 31, 0, 0, 12, 12, 12, 12);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.DefenceC)), x - 135 + xOffset, y - 28, 11184810);

        RenderSystem.setShaderTexture(0, MANA_DEFENCE);
        guiGraphics.blit(MANA_DEFENCE, x - 160 + xOffset, y - 21, 0, 0, 12, 12, 12, 12);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.ManaDefenceC)), x - 135 + xOffset, y - 18, 5592575);

        RenderSystem.setShaderTexture(0, SPEED);
        guiGraphics.blit(SPEED, x - 160 + xOffset, y - 11, 0, 0, 12, 12, 12, 12);
        guiGraphics.drawCenteredString(fontRenderer, Te.s(String.format("%.0f%%", ClientUtils.SpeedC * 100),
                        ClientUtils.SpeedC >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED)
                , x - 135 + xOffset, y - 8, 5635925);

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
    });
}

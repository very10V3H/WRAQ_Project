package fun.wraq.render.hud.main;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.EffectTimeLast;
import fun.wraq.common.util.struct.SkillImage;
import fun.wraq.process.system.endlessinstance.DailyEndlessInstance;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Comparator;
import java.util.List;

public class BuffHud {
    private static final ResourceLocation SakuraDemon = new ResourceLocation(Utils.MOD_ID,
            "textures/item/sakurasword.png");
    private static final ResourceLocation ZeusSword = new ResourceLocation(Utils.MOD_ID,
            "textures/item/zeus_sword.png");

    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    public static final IGuiOverlay HUD_ATTRIBUTE = ((gui, poseStack, partialTick, width, height) -> {

        int x = width / 2;
        int y = height;

        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        if (ClientUtils.elementEffects != null) {
            if (ClientUtils.elementEffects.level != 0) {
                guiGraphics.blit(new ResourceLocation(Utils.MOD_ID, "textures/item/" + ClientUtils.elementEffects.itemStack.getItem().toString() + ".png"), x + 79, height - 60, 0, 0, 16, 16, 16, 16);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + ClientUtils.elementEffects.level).withStyle(ChatFormatting.WHITE), x + 11 + 79, height - 52, 10);
            }
        }

        int count = 0;
        int XXOffset = 95;

        for (int i = 1; i <= 15; i++) {
            if (ClientUtils.Rune_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Rune_Image[i];
                if (skillImage.getTickTime() > 0) {
                    guiGraphics.blit(ClientUtils.RuneResourceLocation[i], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    int Time = 12 - (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    count++;
                }
            }
        }

        if (mc.player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.SakuraDemonSword.get()) && ClientUtils.ChargedCountsSakuraDemonSword > 0) {
            guiGraphics.blit(SakuraDemon, x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (int) ClientUtils.ChargedCountsSakuraDemonSword).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 10, y - 52, 0);
            count++;
        }

        for (int i = 1; i <= 15; i++) {
            if (ClientUtils.Demon_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Demon_Image[i];
                if (skillImage.getTickTime() > 0) {
                    if (i == 1) guiGraphics.blit(SakuraDemon, x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    if (i == 2) guiGraphics.blit(ZeusSword, x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    if (skillImage.getNum() > 0)
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 10, y - 52, 0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    count++;
                }
            }
        }

        if (ClientUtils.SwordSkillPoint.PointCache != null) {
            if (ClientUtils.SwordSkillsResource.resourceLocations == null) ClientUtils.SwordSkillsResource.init();
            if (ClientUtils.SwordSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.SwordSkillsResource.resourceLocations[13], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (int) ClientUtils.ChargedCountsSwordSkill12).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 10, y - 52, 0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                count++;
            }
        }

        if (ClientUtils.ManaSkillPoint.PointCache != null) {
            if (ClientUtils.ManaSkillsResource.resourceLocations == null) ClientUtils.ManaSkillsResource.init();
            if (ClientUtils.ManaSkillPoint.Point[14] != 0) {
                guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[14], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (int) ClientUtils.ChargedCountsManaSkill13).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 10, y - 52, 0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                count++;
            }
        }

        if (ClientUtils.ManaSkillPoint.PointCache != null) {
            if (ClientUtils.ManaSkillsResource.resourceLocations == null) ClientUtils.ManaSkillsResource.init();
            if (ClientUtils.ManaSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[13], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (int) ClientUtils.ChargedCountsManaSkill12).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 10, y - 52, 0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                count++;
            }
        }

        if (ClientUtils.BowSkillPoint.PointCache != null) {
            if (ClientUtils.BowSkillsResource.resourceLocations == null) ClientUtils.BowSkillsResource.init();
            if (ClientUtils.BowSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.BowSkillsResource.resourceLocations[13], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (int) ClientUtils.ChargedCountsBowSkill12).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 10, y - 52, 0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                count++;
            }
        }


        for (int i = 1; i <= 15; i++) {
            if (ClientUtils.Sword_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Sword_Image[i];
                if (skillImage.getTickTime() > 0) {
                    guiGraphics.blit(ClientUtils.SwordSkillsResource.resourceLocations[i], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    if (skillImage.getNum() > 0)
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 10, y - 52, 0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    count++;
                }
            }
        }
        for (int i = 1; i <= 15; i++) {
            if (ClientUtils.Bow_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Bow_Image[i];
                if (skillImage.getTickTime() > 0 || i == 14 && ClientUtils.BowSkillPoint.Point[14] > 0) {
                    guiGraphics.blit(ClientUtils.BowSkillsResource.resourceLocations[i], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    if (skillImage.getNum() > 0)
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 10, y - 52, 0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    if (i == 14) Time = 12 - Time;
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    count++;
                }
            }
        }
        for (int i = 1; i <= 15; i++) {
            if (ClientUtils.Mana_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Mana_Image[i];
                if (skillImage.getTickTime() > 0) {
                    guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[i], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    if (skillImage.getNum() > 0)
                        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 10, y - 52, 0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
                    count++;
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
            guiGraphics.blit(new ResourceLocation(Utils.MOD_ID, "textures/item/" + effectTimeLast.itemStack.getItem().toString() + ".png"), x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
            int Time = (int) Math.ceil(effectTimeLast.lastTick * 12.0f / effectTimeLast.maxTick);
            if (effectTimeLast.forever) Time = 12;
            guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
            if (effectTimeLast.level > 0)
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + effectTimeLast.level).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 11, y - 52, 10);
            if (effectTimeLast.lastTick > 0 && effectTimeLast.level == 0 && !effectTimeLast.forever)
                guiGraphics.drawCenteredString(fontRenderer,
                        Component.literal(effectTimeLast.lastTick >= 20 ? String.format("%.0f", effectTimeLast.lastTick / 20d) : String.format("%.1f", effectTimeLast.lastTick / 20d)).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 11, y - 52, 10);
            count++;
        }

        count++;

        for (EffectTimeLast effectTimeLast : ClientUtils.coolDownTimes) {
            guiGraphics.blit(new ResourceLocation(Utils.MOD_ID, "textures/item/" + effectTimeLast.itemStack.getItem().toString() + ".png"), x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
            int Time = (int) (12 - effectTimeLast.lastTick * 12.0f / effectTimeLast.maxTick);
            guiGraphics.blit(ClientUtils.CdResourceLocation[Time], x + XXOffset + count * 15, y - 60, 0, 0, 16, 16, 16, 16);
            if (effectTimeLast.lastTick > 0) guiGraphics.drawCenteredString(fontRenderer,
                    Component.literal(effectTimeLast.lastTick >= 20 ? String.format("%.0f", effectTimeLast.lastTick / 20d) : String.format("%.1f", effectTimeLast.lastTick / 20d)).withStyle(ChatFormatting.WHITE), x + XXOffset + count * 15 + 11, y - 52, 10);
            count++;
        }

        count++;

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
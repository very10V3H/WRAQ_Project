package com.Very.very.Render.Hud;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Struct.SkillImage;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Iterator;

public class AttributeHud {
    private static final ResourceLocation ATTACK = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/attack0.png");
    private static final ResourceLocation BREAKDEFENCE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/breakdefence.png");
    private static final ResourceLocation COOLDOWN = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/cooldown0.png");
    private static final ResourceLocation CRITDAMAGE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/critdamage0.png");
    private static final ResourceLocation CRIRATE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/critrate0.png");
    private static final ResourceLocation DEFENCE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/defence0.png");
    private static final ResourceLocation MANADAMAGE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manadamage0.png");
    private static final ResourceLocation MANADEFENCE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manadefence0.png");
    private static final ResourceLocation MANADEFENCEBREAK = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manadefencebreak0.png");
    private static final ResourceLocation MANAREPLY = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manareply0.png");
    private static final ResourceLocation SPEED = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/speed0.png");
    private static final ResourceLocation HEALSTEAL = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/healsteal0.png");
    private static final ResourceLocation PLAINRUNE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/plainrune.png");
    private static final ResourceLocation FORESTRUNE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/forestrune.png");
    private static final ResourceLocation VOLCANORUNE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/volcanorune.png");
    private static final ResourceLocation MANARUNE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manarune.png");
    private static final ResourceLocation NETHERRUNE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/netherrune.png");
    private static final ResourceLocation SNOWRUNE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/snowrune.png");
    private static final ResourceLocation EMPTYMANA = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/emptymana.png");
    private static final ResourceLocation BREAKDEFENCE0 = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/breakdefence0.png");
    private static final ResourceLocation BREAKMANADEFENCE0 = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/manabreakdefence0.png");
    private static final ResourceLocation ATTACKRANGEUP = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/attackrangeup.png");
    private static final ResourceLocation EXPUP = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/expup.png");

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
        int x = width / 2;
        int y = height;
        GuiGraphics guiGraphics = new GuiGraphics(mc,mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ATTACK);

        int XOffset = 0;
        guiGraphics.blit(ATTACK,x-214+XOffset,y-41,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.AttackDamageC)),x-194+XOffset,y-38,5636095);

        RenderSystem.setShaderTexture(0,BREAKDEFENCE);
        guiGraphics.blit(BREAKDEFENCE,x-214+XOffset,y-31,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.BreakDefenceC*100)),x-194+XOffset,y-28,11184810);

        RenderSystem.setShaderTexture(0,CRIRATE);
        guiGraphics.blit(CRIRATE,x-214+XOffset,y-21,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.CritRateC*100)),x-194+XOffset,y-18,16733695);

        RenderSystem.setShaderTexture(0,CRITDAMAGE);
        guiGraphics.blit(CRITDAMAGE,x-214+XOffset,y-11,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.CritDamageC*100)),x-194+XOffset,y-8,5592575);

        RenderSystem.setShaderTexture(0,MANADAMAGE);
        guiGraphics.blit(MANADAMAGE,x-182+XOffset,y-41,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.ManaDamageC)),x-162+XOffset,y-38,16733695);

        RenderSystem.setShaderTexture(0,MANADEFENCEBREAK);
        guiGraphics.blit(MANADEFENCEBREAK,x-182+XOffset,y-31,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.BreakManaDefenceC*100)),x-162+XOffset,y-28,5592575);

        RenderSystem.setShaderTexture(0,MANAREPLY);
        guiGraphics.blit(MANAREPLY,x-182+XOffset,y-21,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.ManaReplyC+5)),x-162+XOffset,y-18,16733695);

        RenderSystem.setShaderTexture(0,COOLDOWN);
        guiGraphics.blit(COOLDOWN,x-182+XOffset,y-11,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.CoolDownC*100)),x-162+XOffset,y-8,5636095);

        RenderSystem.setShaderTexture(0,HEALSTEAL);
        guiGraphics.blit(HEALSTEAL,x-150+XOffset,y-41,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.HealStealC*100)),x-130+XOffset,y-38,16733525);

        RenderSystem.setShaderTexture(0,DEFENCE);
        guiGraphics.blit(DEFENCE,x-150+XOffset,y-31,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.DefenceC)),x-130+XOffset,y-28,11184810);

        RenderSystem.setShaderTexture(0,MANADEFENCE);
        guiGraphics.blit(MANADEFENCE,x-150+XOffset,y-21,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.ManaDefenceC)),x-130+XOffset,y-18,5592575);

        RenderSystem.setShaderTexture(0,SPEED);
        guiGraphics.blit(SPEED,x-150+XOffset,y-11,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.SpeedC*100)),x-130+XOffset,y-8,5635925);

        if (mc.player.isShiftKeyDown()) {
            RenderSystem.setShaderTexture(0,PLAINRUNE);
            guiGraphics.blit(PLAINRUNE,x+190,y-41,0,0,12,12,12,12);
            String PlainRuneName = "-";
            if(ClientUtils.PlainRune == 0) PlainRuneName = "复苏之风";
            else if(ClientUtils.PlainRune == 1) PlainRuneName = "平原恩惠";
            else if(ClientUtils.PlainRune == 2) PlainRuneName = "临危制变";
            else if(ClientUtils.PlainRune == 3) PlainRuneName = "平原加护";
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(PlainRuneName),x+220,y-38,5635925);

            RenderSystem.setShaderTexture(0,FORESTRUNE);
            guiGraphics.blit(FORESTRUNE,x+190,y-31,0,0,12,12,12,12);
            String ForestRuneName = "-";
            if(ClientUtils.ForestRune == 0) ForestRuneName = "巨像勇气";
            else if(ClientUtils.ForestRune == 1) ForestRuneName = "灵巧荆棘";
            else if(ClientUtils.ForestRune == 2) ForestRuneName = "狂野生长";
            else if(ClientUtils.ForestRune == 3) ForestRuneName = "生长汲取";
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(ForestRuneName),x+220,y-28,43520);

            RenderSystem.setShaderTexture(0,VOLCANORUNE);
            guiGraphics.blit(VOLCANORUNE,x+190,y-21,0,0,12,12,12,12);
            String VolcanoRuneName = "-";
            if(ClientUtils.VolcanoRune == 0) VolcanoRuneName = "熔岩吞没";
            else if(ClientUtils.VolcanoRune == 1) VolcanoRuneName = "坚毅不倒";
            else if(ClientUtils.VolcanoRune == 2) VolcanoRuneName = "熔岩强击";
            else if(ClientUtils.VolcanoRune == 3) VolcanoRuneName = "彻底喷发";
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(VolcanoRuneName),x+220,y-18,16777045);

            RenderSystem.setShaderTexture(0,MANARUNE);
            guiGraphics.blit(MANARUNE,x+190,y-11,0,0,12,12,12,12);
            String ManaRuneName = "-";
            if(ClientUtils.ManaRune == 0) ManaRuneName = "魔源之智";
            else if(ClientUtils.ManaRune == 1) ManaRuneName = "危险游戏";
            else if(ClientUtils.ManaRune == 2) ManaRuneName = "苍雷之怒";
            else if(ClientUtils.ManaRune == 3) ManaRuneName = "法师之威";
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(ManaRuneName),x+220,y-8,16733695);

            RenderSystem.setShaderTexture(0,NETHERRUNE);
            guiGraphics.blit(NETHERRUNE,x+140,y-11,0,0,12,12,12,12);
            String NetherRuneName = "-";
            if(ClientUtils.NetherRune == 0) NetherRuneName = "法术调制";
            else if(ClientUtils.NetherRune == 1) NetherRuneName = "法术解调";
            else if(ClientUtils.NetherRune == 2) NetherRuneName = "循环法流";
            else if(ClientUtils.NetherRune == 3) NetherRuneName = "能量涌动";
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(NetherRuneName),x+170,y-8,16733525);

            RenderSystem.setShaderTexture(0,SNOWRUNE);
            guiGraphics.blit(SNOWRUNE,x+140,y-21,0,0,12,12,12,12);
            String SnowRuneName = "-";
            if(ClientUtils.SnowRune == 0) SnowRuneName = "精雕细琢";
            else if(ClientUtils.SnowRune == 1) SnowRuneName = "轻化寒击";
            else if(ClientUtils.SnowRune == 2) SnowRuneName = "凛冬之意";
            else if(ClientUtils.SnowRune == 3) SnowRuneName = "冰霜箭矢";
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(SnowRuneName),x+170,y-18,5636095);

            guiGraphics.blit(BREAKDEFENCE0,x+100+XOffset,y-41,0,0,12,12,12,12);
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.BreakDefence0C)).withStyle(ChatFormatting.GRAY),x+120+XOffset,y-38,0);

            guiGraphics.blit(BREAKMANADEFENCE0,x+100+XOffset,y-31,0,0,12,12,12,12);
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.BreakManaDefence0C)).withStyle(ChatFormatting.BLUE),x+120+XOffset,y-28,0);

            guiGraphics.blit(ATTACKRANGEUP,x+100+XOffset,y-21,0,0,12,12,12,12);
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.2f",ClientUtils.AttackRangeUpC)).withStyle(Utils.styleOfSea),x+120+XOffset,y-18,0);

            guiGraphics.blit(EXPUP,x+100+XOffset,y-11,0,0,12,12,12,12);
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.ExpUpC*100)).withStyle(ChatFormatting.LIGHT_PURPLE),x+120+XOffset,y-8,0);
        }

        if (ClientUtils.IsAdjustingPower && ClientUtils.IsHandlingPower) {

            int power1 = 0,power2 = 0,power3 = 0,power4 = 0;
            Iterator<Integer> iterator = ClientUtils.PowerQueue.iterator();

            if (iterator.hasNext()) power1 = iterator.next();
            if (iterator.hasNext()) power2 = iterator.next();
            if (iterator.hasNext()) power3 = iterator.next();
            if (iterator.hasNext()) power4 = iterator.next();

            RenderSystem.setShaderTexture(0,RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power4)]);
            guiGraphics.blit(RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power4)],x-26,y-150,0,0,12,12,12,12);

            RenderSystem.setShaderTexture(0,RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power3)]);
            guiGraphics.blit(RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power3)],x+13,y-150,0,0,12,12,12,12);

            RenderSystem.setShaderTexture(0,RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power2)]);
            guiGraphics.blit(RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power2)],x-26,y-130,0,0,12,12,12,12);

            RenderSystem.setShaderTexture(0,RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power1)]);
            guiGraphics.blit(RESOURCE_LOCATIONS[ClientUtils.PowerInSlot.get(power1)],x+13,y-130,0,0,12,12,12,12);
        }

        int Count = 0;
        if (ClientUtils.SwordSkillPoint.PointCache != null) {
            if (ClientUtils.SwordSkillsResource.resourceLocations == null) ClientUtils.SwordSkillsResource.init();
            if (ClientUtils.SwordSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.SwordSkillsResource.resourceLocations[13],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (int) ClientUtils.ChargedCountsSwordSkill12).withStyle(ChatFormatting.WHITE),x + 120 + Count * 15 + 10, y - 52,0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                Count ++;
            }
        }

        if (ClientUtils.ManaSkillPoint.PointCache != null) {
            if (ClientUtils.ManaSkillsResource.resourceLocations == null) ClientUtils.ManaSkillsResource.init();
            if (ClientUtils.ManaSkillPoint.Point[14] != 0) {
                guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[14],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (int) ClientUtils.ChargedCountsManaSkill13).withStyle(ChatFormatting.WHITE),x + 120 + Count * 15 + 10 , y - 52,0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                Count ++;
            }
        }

        if (ClientUtils.ManaSkillPoint.PointCache != null) {
            if (ClientUtils.ManaSkillsResource.resourceLocations == null) ClientUtils.ManaSkillsResource.init();
            if (ClientUtils.ManaSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[13],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (int) ClientUtils.ChargedCountsManaSkill12).withStyle(ChatFormatting.WHITE),x + 120 + Count * 15 + 10 , y - 52,0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                Count ++;
            }
        }

        if (ClientUtils.BowSkillPoint.PointCache != null) {
            if (ClientUtils.BowSkillsResource.resourceLocations == null) ClientUtils.BowSkillsResource.init();
            if (ClientUtils.BowSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.BowSkillsResource.resourceLocations[13],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (int) ClientUtils.ChargedCountsBowSkill12).withStyle(ChatFormatting.WHITE),x + 120 + Count * 15 + 10 , y - 52,0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                Count ++;
            }
        }

        for (int i = 1; i <= 15; i ++) {
            if (ClientUtils.Sword_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Sword_Image[i];
                if (skillImage.getTickTime() > 0) {
                    guiGraphics.blit(ClientUtils.SwordSkillsResource.resourceLocations[i],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                    if (skillImage.getNum() > 0) guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE),x + 120 + Count * 15 + 10 , y - 52,0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                    Count ++;
                }
            }
        }
        for (int i = 1; i <= 15; i ++) {
            if (ClientUtils.Bow_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Bow_Image[i];
                if (skillImage.getTickTime() > 0 || i == 14 && ClientUtils.BowSkillPoint.Point[14] > 0) {
                    guiGraphics.blit(ClientUtils.BowSkillsResource.resourceLocations[i],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                    if (skillImage.getNum() > 0) guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE),x + 120 + Count * 15 + 10 , y - 52,0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    if (i == 14) Time = 12 - Time;
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                    Count ++;
                }
            }
        }
        for (int i = 1; i <= 15; i ++) {
            if (ClientUtils.Mana_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Mana_Image[i];
                if (skillImage.getTickTime() > 0) {
                    guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[i],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                    if (skillImage.getNum() > 0) guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE),x + 120 + Count * 15 + 10 , y - 52,0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time],x + 120 + Count * 15 , y - 60, 0,0,16,16,16,16);
                    Count ++;
                }
            }
        }
    });
}

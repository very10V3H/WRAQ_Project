package com.Very.very.Render.Hud;

import com.Very.very.Items.MobItem.MobArmor;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Struct.EffectTimeLast;
import com.Very.very.ValueAndTools.Utils.Struct.SkillImage;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    private static final ResourceLocation ENDRUNE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/end_rune.png");
    private static final ResourceLocation LAKERUNE = new ResourceLocation(Utils.MOD_ID,
            "textures/hud/lake_rune.png");
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
        int x = width / 2;
        int y = height;
        GuiGraphics guiGraphics = new GuiGraphics(mc,mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        int XOffset = 0;
        RenderSystem.setShaderTexture(0, ATTACK);
        guiGraphics.blit(ATTACK,x-244+XOffset,y-41,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.AttackDamageC)),x-219+XOffset,y-38,5636095);

        RenderSystem.setShaderTexture(0, DEFENCE_PENETRATION);
        guiGraphics.blit(DEFENCE_PENETRATION,x-244+XOffset,y-31,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.BreakDefenceC*100)),x-219+XOffset,y-28,11184810);

        RenderSystem.setShaderTexture(0, CRIT_RATE);
        guiGraphics.blit(CRIT_RATE,x-244+XOffset,y-21,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.CritRateC*100)),x-219+XOffset,y-18,16733695);

        RenderSystem.setShaderTexture(0, CRIT_DAMAGE);
        guiGraphics.blit(CRIT_DAMAGE,x-244+XOffset,y-11,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.CritDamageC*100)),x-219+XOffset,y-8,5592575);

        RenderSystem.setShaderTexture(0, MANA_DAMAGE);
        guiGraphics.blit(MANA_DAMAGE,x-202+XOffset,y-41,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.ManaDamageC)),x-177+XOffset,y-38,16733695);

        RenderSystem.setShaderTexture(0, MANA_DEFENCE_PENETRATION);
        guiGraphics.blit(MANA_DEFENCE_PENETRATION,x-202+XOffset,y-31,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.BreakManaDefenceC*100)),x-177+XOffset,y-28,5592575);

        RenderSystem.setShaderTexture(0, MANA_REPLY);
        guiGraphics.blit(MANA_REPLY,x-202+XOffset,y-21,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.ManaReplyC+5)),x-177+XOffset,y-18,16733695);

        RenderSystem.setShaderTexture(0, COOL_DOWN);
        guiGraphics.blit(COOL_DOWN,x-202+XOffset,y-11,0,0,12,12,12,12);
        if (mc.player.isShiftKeyDown()) guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",(1 - (1 / (1 + (ClientUtils.CoolDownC)))) * 100)),x-177+XOffset,y-8,5636095);
        else guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.CoolDownC*100)),x-177+XOffset,y-8,5636095);

        RenderSystem.setShaderTexture(0, HEALTH_STEAL);
        guiGraphics.blit(HEALTH_STEAL,x-160+XOffset,y-41,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.HealStealC*100)),x-135+XOffset,y-38,16733525);

        RenderSystem.setShaderTexture(0, DEFENCE);
        guiGraphics.blit(DEFENCE,x-160+XOffset,y-31,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.DefenceC)),x-135+XOffset,y-28,11184810);

        RenderSystem.setShaderTexture(0, MANA_DEFENCE);
        guiGraphics.blit(MANA_DEFENCE,x-160+XOffset,y-21,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.ManaDefenceC)),x-135+XOffset,y-18,5592575);

        RenderSystem.setShaderTexture(0,SPEED);
        guiGraphics.blit(SPEED,x-160+XOffset,y-11,0,0,12,12,12,12);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.SpeedC*100)),x-135+XOffset,y-8,5635925);


        // mob attribute down
        if (ClientUtils.mobAttribute != null) {
            int standardX = 0;
            int standardY = 10;
            ItemStack MobHelmet = ClientUtils.mobAttribute.getItemBySlot(EquipmentSlot.HEAD);
            MobArmor mobArmor = null;
            if (MobHelmet.getItem() instanceof MobArmor) mobArmor = (MobArmor) MobHelmet.getItem();
            if (mobArmor != null) {
                guiGraphics.drawString(fontRenderer,ClientUtils.mobAttribute.getDisplayName(),standardX,standardY - 8,0);

                MutableComponent mutableComponent;
                RenderSystem.setShaderTexture(0, ATTACK);
                guiGraphics.blit(ATTACK,standardX,standardY,0,0,12,12,12,12);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",mobArmor.AttackDamage)).withStyle(Utils.styleOfAttack),standardX + 20,standardY + 3,0);

                RenderSystem.setShaderTexture(0, DEFENCE);
                guiGraphics.blit(DEFENCE,standardX,standardY + 10,0,0,12,12,12,12);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",mobArmor.Defence)).withStyle(Utils.styleOfDefence),standardX + 20,standardY + 13,0);

                RenderSystem.setShaderTexture(0, MANA_DEFENCE);
                guiGraphics.blit(MANA_DEFENCE,standardX,standardY + 20,0,0,12,12,12,12);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",mobArmor.ManaDefence)).withStyle(Utils.styleOfManaDefence),standardX + 20,standardY + 23,0);

                RenderSystem.setShaderTexture(0, CRIT_RATE);
                guiGraphics.blit(CRIT_RATE,standardX,standardY + 30,0,0,12,12,12,12);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",mobArmor.CritRate * 100)).withStyle(Utils.styleOfCritRate),standardX + 20,standardY + 33,0);

                RenderSystem.setShaderTexture(0, DEFENCE_PENETRATION);
                guiGraphics.blit(DEFENCE_PENETRATION,standardX + 32,standardY,0,0,12,12,12,12);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",mobArmor.DefencePenetration * 100)).withStyle(Utils.styleOfDefencePenetration),standardX + 52,standardY + 3,0);

                RenderSystem.setShaderTexture(0, DEFENCE_PENETRATION0);
                guiGraphics.blit(DEFENCE_PENETRATION0,standardX + 32,standardY + 10,0,0,12,12,12,12);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",mobArmor.DefencePenetration0)).withStyle(Utils.styleOfDefencePenetration),standardX + 52,standardY + 13,0);

                RenderSystem.setShaderTexture(0, HEALTH_STEAL);
                guiGraphics.blit(HEALTH_STEAL,standardX + 32,standardY + 20,0,0,12,12,12,12);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",mobArmor.HealthSteal * 100)).withStyle(Utils.styleOfHealthSteal),standardX + 52,standardY + 23,0);

                RenderSystem.setShaderTexture(0, CRIT_DAMAGE);
                guiGraphics.blit(CRIT_DAMAGE,standardX + 32,standardY + 30,0,0,12,12,12,12);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",mobArmor.CritDamage * 100)).withStyle(Utils.styleOfCritDamage),standardX + 52,standardY + 33,0);

            }
        }


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
            guiGraphics.blit(MANARUNE,x+140,y-31,0,0,12,12,12,12);
            String ManaRuneName = "-";
            if(ClientUtils.ManaRune == 0) ManaRuneName = "魔源之智";
            else if(ClientUtils.ManaRune == 1) ManaRuneName = "危险游戏";
            else if(ClientUtils.ManaRune == 2) ManaRuneName = "苍雷之怒";
            else if(ClientUtils.ManaRune == 3) ManaRuneName = "法师之威";
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(ManaRuneName),x+170,y-28,16733695);

            RenderSystem.setShaderTexture(0,NETHERRUNE);
            guiGraphics.blit(NETHERRUNE,x+140,y-21,0,0,12,12,12,12);
            String NetherRuneName = "-";
            if(ClientUtils.NetherRune == 0) NetherRuneName = "法术调制";
            else if(ClientUtils.NetherRune == 1) NetherRuneName = "法术解调";
            else if(ClientUtils.NetherRune == 2) NetherRuneName = "循环法流";
            else if(ClientUtils.NetherRune == 3) NetherRuneName = "能量涌动";
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(NetherRuneName),x+170,y-18,16733525);

            RenderSystem.setShaderTexture(0,SNOWRUNE);
            guiGraphics.blit(SNOWRUNE,x+140,y-41,0,0,12,12,12,12);
            String SnowRuneName = "-";
            if(ClientUtils.SnowRune == 0) SnowRuneName = "精雕细琢";
            else if(ClientUtils.SnowRune == 1) SnowRuneName = "轻化寒击";
            else if(ClientUtils.SnowRune == 2) SnowRuneName = "凛冬之意";
            else if(ClientUtils.SnowRune == 3) SnowRuneName = "冰霜箭矢";
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(SnowRuneName),x+170,y-38,5636095);

            RenderSystem.setShaderTexture(0,ENDRUNE);
            guiGraphics.blit(ENDRUNE,x+140,y-11,0,0,12,12,12,12);
            String EndRuneName = "-";
            if(ClientUtils.EndRune == 0) EndRuneName = "终界跃迁";
            else if(ClientUtils.EndRune == 1) EndRuneName = "能量搬移";
            else if(ClientUtils.EndRune == 2) EndRuneName = "末影龙息";
            else if(ClientUtils.EndRune == 3) EndRuneName = "归终秘法";
            if (ClientUtils.EndRune == 3 && ClientUtils.TickCount % 100 < 50) {
                switch (ClientUtils.EndRune3Type) {
                    case 0 -> EndRuneName = "归终森林";
                    case 1 -> EndRuneName = "归终火山";
                    case 2 -> EndRuneName = "归终下界";
                    case 3 -> EndRuneName = "归终冰川";
                }
            }
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(EndRuneName).withStyle(Utils.styleOfEnd),x+170,y-8,0);

            RenderSystem.setShaderTexture(0,LAKERUNE);
            guiGraphics.blit(LAKERUNE,x+190,y-11,0,0,12,12,12,12);
            String LakeRuneName = "-";
            if(ClientUtils.LakeRune == 0) LakeRuneName = "纯净之水";
            else if(ClientUtils.LakeRune == 1) LakeRuneName = "水之张力";
            else if(ClientUtils.LakeRune == 2) LakeRuneName = "流线适应";
            else if(ClientUtils.LakeRune == 3) LakeRuneName = "热量交换";
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(LakeRuneName).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLake),x+220,y-8,0);


            guiGraphics.blit(DEFENCE_PENETRATION0,x+95+XOffset,y-41,0,0,12,12,12,12);
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.BreakDefence0C)).withStyle(ChatFormatting.GRAY),x+120+XOffset,y-38,0);

            guiGraphics.blit(MANA_PENETRATION0,x+95+XOffset,y-31,0,0,12,12,12,12);
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.BreakManaDefence0C)).withStyle(ChatFormatting.BLUE),x+120+XOffset,y-28,0);

            guiGraphics.blit(ATTACK_RANGE_UP,x+95+XOffset,y-21,0,0,12,12,12,12);
            guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.2f",ClientUtils.AttackRangeUpC)).withStyle(Utils.styleOfSea),x+120+XOffset,y-18,0);

            guiGraphics.blit(EXP_UP,x+95+XOffset,y-11,0,0,12,12,12,12);
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
        int XXOffset = 95;

        for (int i = 1; i <= 15; i ++) {
            if (ClientUtils.Rune_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Rune_Image[i];
                if (skillImage.getTickTime() > 0) {
                    guiGraphics.blit(ClientUtils.RuneResourceLocation[i],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                    int Time = 12 - (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                    Count ++;
                }
            }
        }

        if (mc.player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.SakuraDemonSword.get()) && ClientUtils.ChargedCountsSakuraDemonSword > 0) {
            guiGraphics.blit(SakuraDemon,x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (int) ClientUtils.ChargedCountsSakuraDemonSword).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 10, y - 52,0);
            Count ++;
        }

/*        if (mc.player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ZeusSword.get()) && ClientUtils.ChargedCountsZeusSword > 0) {
            guiGraphics.blit(ZeusSword,x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (int) ClientUtils.ChargedCountsZeusSword).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 10, y - 52,0);
            Count ++;
        }*/

        for (int i = 1; i <= 15; i ++) {
            if (ClientUtils.Demon_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Demon_Image[i];
                if (skillImage.getTickTime() > 0) {
                    if (i == 1) guiGraphics.blit(SakuraDemon,x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                    if (i == 2) guiGraphics.blit(ZeusSword,x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                    if (skillImage.getNum() > 0) guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 10 , y - 52,0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                    Count ++;
                }
            }
        }

        if (ClientUtils.SwordSkillPoint.PointCache != null) {
            if (ClientUtils.SwordSkillsResource.resourceLocations == null) ClientUtils.SwordSkillsResource.init();
            if (ClientUtils.SwordSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.SwordSkillsResource.resourceLocations[13],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (int) ClientUtils.ChargedCountsSwordSkill12).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 10, y - 52,0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                Count ++;
            }
        }

        if (ClientUtils.ManaSkillPoint.PointCache != null) {
            if (ClientUtils.ManaSkillsResource.resourceLocations == null) ClientUtils.ManaSkillsResource.init();
            if (ClientUtils.ManaSkillPoint.Point[14] != 0) {
                guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[14],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (int) ClientUtils.ChargedCountsManaSkill13).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 10 , y - 52,0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                Count ++;
            }
        }

        if (ClientUtils.ManaSkillPoint.PointCache != null) {
            if (ClientUtils.ManaSkillsResource.resourceLocations == null) ClientUtils.ManaSkillsResource.init();
            if (ClientUtils.ManaSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[13],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (int) ClientUtils.ChargedCountsManaSkill12).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 10 , y - 52,0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                Count ++;
            }
        }

        if (ClientUtils.BowSkillPoint.PointCache != null) {
            if (ClientUtils.BowSkillsResource.resourceLocations == null) ClientUtils.BowSkillsResource.init();
            if (ClientUtils.BowSkillPoint.Point[13] != 0) {
                guiGraphics.blit(ClientUtils.BowSkillsResource.resourceLocations[13],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + (int) ClientUtils.ChargedCountsBowSkill12).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 10 , y - 52,0);
                guiGraphics.blit(ClientUtils.CdResourceLocation[12],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                Count ++;
            }
        }


        for (int i = 1; i <= 15; i ++) {
            if (ClientUtils.Sword_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Sword_Image[i];
                if (skillImage.getTickTime() > 0) {
                    guiGraphics.blit(ClientUtils.SwordSkillsResource.resourceLocations[i],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                    if (skillImage.getNum() > 0) guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 10 , y - 52,0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                    Count ++;
                }
            }
        }
        for (int i = 1; i <= 15; i ++) {
            if (ClientUtils.Bow_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Bow_Image[i];
                if (skillImage.getTickTime() > 0 || i == 14 && ClientUtils.BowSkillPoint.Point[14] > 0) {
                    guiGraphics.blit(ClientUtils.BowSkillsResource.resourceLocations[i],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                    if (skillImage.getNum() > 0) guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 10 , y - 52,0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    if (i == 14) Time = 12 - Time;
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                    Count ++;
                }
            }
        }
        for (int i = 1; i <= 15; i ++) {
            if (ClientUtils.Mana_Image[i] != null) {
                SkillImage skillImage = ClientUtils.Mana_Image[i];
                if (skillImage.getTickTime() > 0) {
                    guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[i],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                    if (skillImage.getNum() > 0) guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + skillImage.getNum()).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 10 , y - 52,0);
                    int Time = (int) (skillImage.getTickTime() * 12.0f / skillImage.getMaxTime());
                    guiGraphics.blit(ClientUtils.CdResourceLocation[Time],x + XXOffset + Count * 15 , y - 60, 0,0,16,16,16,16);
                    Count ++;
                }
            }
        }

        for (EffectTimeLast effectTimeLast : ClientUtils.effectTimeLasts) {
            if (effectTimeLast.itemStack.is(ModItems.YuanShiRenSceptre.get())) {
                guiGraphics.renderItem(ModItems.YuanShiRenSceptre.get().getDefaultInstance(),x + XXOffset + Count * 15,y - 60);
            }
            else guiGraphics.blit(new ResourceLocation(Utils.MOD_ID,"textures/item/" + effectTimeLast.itemStack.getItem().toString() + ".png"),x + XXOffset + Count * 15,y - 60,0,0,16,16,16,16);
            int Time = (int) (effectTimeLast.TickCount * 12.0f / effectTimeLast.MaxCount);
            if (effectTimeLast.NoTime) Time = 12;
            guiGraphics.blit(ClientUtils.CdResourceLocation[Time],x + XXOffset + Count * 15, y - 60, 0,0,16,16,16,16);
            if (effectTimeLast.level > 0) guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + effectTimeLast.level).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 11 , y - 52,10);
            if (effectTimeLast.TickCount > 0 && effectTimeLast.level == 0 && !effectTimeLast.NoTime) guiGraphics.drawCenteredString(fontRenderer,
                    Component.literal(effectTimeLast.TickCount >= 20 ? String.format("%.0f",effectTimeLast.TickCount / 20d) : String.format("%.1f",effectTimeLast.TickCount / 20d)).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 11 , y - 52,10);
            Count ++;

        }

        Count ++;

        for (EffectTimeLast effectTimeLast : ClientUtils.coolDownTimes) {
            if (effectTimeLast.itemStack.is(ModItems.YuanShiRenSceptre.get())) {
                guiGraphics.renderItem(ModItems.YuanShiRenSceptre.get().getDefaultInstance(),x + XXOffset + Count * 15,y - 60);
            }
            else guiGraphics.blit(new ResourceLocation(Utils.MOD_ID,"textures/item/" + effectTimeLast.itemStack.getItem().toString() + ".png"),x + XXOffset + Count * 15,y - 60,0,0,16,16,16,16);
            int Time = (int) (12 - effectTimeLast.TickCount * 12.0f / effectTimeLast.MaxCount);
            guiGraphics.blit(ClientUtils.CdResourceLocation[Time],x + XXOffset + Count * 15, y - 60, 0,0,16,16,16,16);
            if (effectTimeLast.TickCount > 0) guiGraphics.drawCenteredString(fontRenderer,
                    Component.literal(effectTimeLast.TickCount >= 20 ? String.format("%.0f",effectTimeLast.TickCount / 20d) : String.format("%.1f",effectTimeLast.TickCount / 20d)).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 11 , y - 52,10);
            Count ++;
        }

        Count ++;

        for (EffectTimeLast effectTimeLast : ClientUtils.debuffTimes) {
            if (effectTimeLast.itemStack.is(ModItems.YuanShiRenSceptre.get())) {
                guiGraphics.renderItem(ModItems.YuanShiRenSceptre.get().getDefaultInstance(),x + XXOffset + Count * 15,y - 60);
            }
            else guiGraphics.blit(new ResourceLocation(Utils.MOD_ID,"textures/item/" + effectTimeLast.itemStack.getItem().toString() + ".png"),x + XXOffset + Count * 15,y - 60,0,0,16,16,16,16);
            int Time = (int) (effectTimeLast.TickCount * 12.0f / effectTimeLast.MaxCount);
            if (effectTimeLast.NoTime) Time = 12;
            guiGraphics.blit(ClientUtils.dCdResourceLocation[Time],x + XXOffset + Count * 15, y - 60, 0,0,16,16,16,16);
            if (effectTimeLast.level > 0) guiGraphics.drawCenteredString(fontRenderer,Component.literal("" + effectTimeLast.level).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 11 , y - 52,10);
            if (effectTimeLast.TickCount > 0 && effectTimeLast.level == 0 && !effectTimeLast.NoTime) guiGraphics.drawCenteredString(fontRenderer,
                    Component.literal(effectTimeLast.TickCount >= 20 ? String.format("%.0f",effectTimeLast.TickCount / 20d) : String.format("%.1f",effectTimeLast.TickCount / 20d)).withStyle(ChatFormatting.WHITE),x + XXOffset + Count * 15 + 11 , y - 52,10);
            Count ++;

        }

    });
}

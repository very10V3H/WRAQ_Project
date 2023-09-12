package com.Very.very.Render.Gui.Skills;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.SkillPackets.AbilityDataC2SPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.SkillDataC2SPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.SkillRequestC2SPacket;
import com.Very.very.Render.Gui.TestAndHelper.OpenSkillTreeGui;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SkillGui extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/skill.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;

    public SkillGui(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.vmd0") : Component.translatable("menu.vmd1"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.showSkillGui();
        }

    }

    private void showSkillGui() {
        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.AbilityChangeCache.Power > 0) {
                ClientUtils.AbilityChangeCache.Power --;
            }
        }).pos(this.width / 2 + 75, this.height / 2 - 17 - 68).size(12,12).build());

        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.AbilityChangeCache.Intelligent > 0) {
                ClientUtils.AbilityChangeCache.Intelligent --;
            }
        }).pos(this.width / 2 + 75, this.height / 2 - 2 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.AbilityChangeCache.Flexibility > 0) {
                ClientUtils.AbilityChangeCache.Flexibility --;
            }
        }).pos(this.width / 2 + 75, this.height / 2 + 13 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.AbilityChangeCache.Lucky > 0) {
                ClientUtils.AbilityChangeCache.Lucky --;
            }
        }).pos(this.width / 2 + 75, this.height / 2 + 28 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.AbilityChangeCache.Vitality > 0) {
                ClientUtils.AbilityChangeCache.Vitality --;
            }
        }).pos(this.width / 2 + 75, this.height / 2 + 43 - 68).size(12,12).build());

        ModNetworking.sendToServer(new SkillRequestC2SPacket());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used) {
                ClientUtils.AbilityChangeCache.Power ++;
            }
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
        }).pos(this.width / 2 + 125, this.height / 2 - 17 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used) {
                ClientUtils.AbilityChangeCache.Intelligent ++;
            }
        }).pos(this.width / 2 + 125, this.height / 2 - 2 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used) {
                ClientUtils.AbilityChangeCache.Flexibility ++;
            }
        }).pos(this.width / 2 + 125, this.height / 2 + 13 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used) {
                ClientUtils.AbilityChangeCache.Lucky ++;
            }
        }).pos(this.width / 2 + 125, this.height / 2 + 28 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used) {
                ClientUtils.AbilityChangeCache.Vitality ++;
            }
        }).pos(this.width / 2 + 125, this.height / 2 + 43 - 68).size(12,12).build());


        this.addRenderableWidget(Button.builder(Component.translatable("×"), (p_280814_) -> {
            ClientUtils.AbilityChangeCache.Clear();
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
        }).pos(this.width / 2 + 110, this.height / 2 + 72 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("○"), (p_280814_) -> {
            ModNetworking.sendToServer(new AbilityDataC2SPacket(ClientUtils.AbilityChangeCache.Power,ClientUtils.AbilityChangeCache.Intelligent,
                    ClientUtils.AbilityChangeCache.Flexibility,ClientUtils.AbilityChangeCache.Lucky,ClientUtils.AbilityChangeCache.Vitality));
            ClientUtils.AbilityChangeCache.Clear();
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
        }).pos(this.width / 2 + 125, this.height / 2 + 72 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("×"), (p_280814_) -> {
            ClientUtils.SkillChangeCache.Clear();
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
        }).pos(this.width / 2 + 110, this.height / 2 + 94 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("○"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillDataC2SPacket(ClientUtils.SkillChangeCache.Sword,ClientUtils.SkillChangeCache.Bow,
                    ClientUtils.SkillChangeCache.Mana));
            ClientUtils.SkillChangeCache.Clear();
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
        }).pos(this.width / 2 + 125, this.height / 2 + 94 - 68).size(12,12).build());


        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.SkillChangeCache.Sword > 0) {
                ClientUtils.SkillChangeCache.Sword --;
            }
        }).pos(this.width / 2 - 130, this.height / 2 + 98 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.SkillChangeCache.SumPoint() < ClientUtils.PlayerSkillPoint_Total - ClientUtils.PlayerSkillPoint_Used) {
                ClientUtils.SkillChangeCache.Sword ++;
            }
        }).pos(this.width / 2 - 95, this.height / 2 + 98 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.SkillChangeCache.Bow > 0) {
                ClientUtils.SkillChangeCache.Bow --;
            }
        }).pos(this.width / 2 - 68, this.height / 2 + 98 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.SkillChangeCache.SumPoint() < ClientUtils.PlayerSkillPoint_Total - ClientUtils.PlayerSkillPoint_Used) {
                ClientUtils.SkillChangeCache.Bow ++;
            }
        }).pos(this.width / 2 - 33, this.height / 2 + 98 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.SkillChangeCache.Mana > 0) {
                ClientUtils.SkillChangeCache.Mana --;
            }
        }).pos(this.width / 2 - 6, this.height / 2 + 98 - 68).size(12,12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            if (ClientUtils.SkillChangeCache.SumPoint() < ClientUtils.PlayerSkillPoint_Total - ClientUtils.PlayerSkillPoint_Used) {
                ClientUtils.SkillChangeCache.Mana ++;
            }
        }).pos(this.width / 2 + 29, this.height / 2 + 98 - 68).size(12,12).build());
        ModNetworking.sendToServer(new SkillRequestC2SPacket());
        this.addRenderableWidget(Button.builder(Component.literal("TREE").withStyle(Utils.styleOfPower), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            ClientUtils.SwordSkillPoint.SwordSkillPointCacheInit();
            ClientUtils.BowSkillPoint.BowSkillPointCacheInit();
            ClientUtils.ManaSkillPoint.ManaSkillPointCacheInit();
            DistExecutor.safeCallWhenOn(Dist.CLIENT,() -> OpenSkillTreeGui::new);
        }).pos(this.width / 2 - 117, this.height / 2 + 113 - 68).size(21,12).build());
        this.addRenderableWidget(Button.builder(Component.literal("TREE").withStyle(Utils.styleOfFlexible), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            ClientUtils.SwordSkillPoint.SwordSkillPointCacheInit();
            ClientUtils.BowSkillPoint.BowSkillPointCacheInit();
            ClientUtils.ManaSkillPoint.ManaSkillPointCacheInit();
            DistExecutor.safeCallWhenOn(Dist.CLIENT,() -> OpenSkillTreeGui::new);
        }).pos(this.width / 2 - 55, this.height / 2 + 113 - 68).size(21,12).build());
        this.addRenderableWidget(Button.builder(Component.literal("TREE").withStyle(Utils.styleOfIntelligent), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            ClientUtils.SwordSkillPoint.SwordSkillPointCacheInit();
            ClientUtils.BowSkillPoint.BowSkillPointCacheInit();
            ClientUtils.ManaSkillPoint.ManaSkillPointCacheInit();
            DistExecutor.safeCallWhenOn(Dist.CLIENT,() -> OpenSkillTreeGui::new);
        }).pos(this.width / 2 + 7, this.height / 2 + 113 - 68).size(21,12).build());

    }
    public void tick() {
        ClientUtils.SkillVision.Sword = ClientUtils.PlayerSkill.Sword;
        ClientUtils.SkillVision.Bow = ClientUtils.PlayerSkill.Bow;
        ClientUtils.SkillVision.Mana = ClientUtils.PlayerSkill.Mana;
        ClientUtils.AbilityVision.Power = ClientUtils.PlayerAbility.Power;
        ClientUtils.AbilityVision.Intelligent = ClientUtils.PlayerAbility.Intelligent;
        ClientUtils.AbilityVision.Flexibility = ClientUtils.PlayerAbility.Flexibility;
        ClientUtils.AbilityVision.Lucky = ClientUtils.PlayerAbility.Lucky;
        ClientUtils.AbilityVision.Vitality = ClientUtils.PlayerAbility.Vitality;
        super.tick();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float p_96313_) {
        GuiGraphics guiGraphics = new GuiGraphics(mc,mc.renderBuffers().bufferSource());

        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);

        super.render(p_96310_, x, y, p_96313_);
        guiGraphics.drawCenteredString(fontRenderer,Minecraft.getInstance().player.getName(),this.width / 2 - 100,this.height / 2 - 88,5636095);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("基础攻击").withStyle(ChatFormatting.AQUA),this.width / 2 - 116,this.height / 2 - 71,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.AttackDamageC)),this.width / 2 - 87,this.height / 2 - 71,5636095);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("护甲穿透").withStyle(ChatFormatting.GRAY),this.width / 2 - 116,this.height / 2 - 54,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.BreakDefenceC*100)),this.width / 2 - 87,this.height / 2 - 54,11184810);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE),this.width / 2 - 116,this.height / 2 - 37,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.CritRateC*100)),this.width / 2 - 87,this.height / 2 - 37,16733695);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("暴击伤害").withStyle(ChatFormatting.BLUE),this.width / 2 - 116,this.height / 2 - 20,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.CritDamageC*100)),this.width / 2 - 87,this.height / 2 - 20,5592575);


        guiGraphics.drawCenteredString(fontRenderer,Component.literal("法术攻击").withStyle(ChatFormatting.LIGHT_PURPLE),this.width / 2 - 54,this.height / 2 - 88,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.ManaDamageC)),this.width / 2 - 25,this.height / 2 - 88,16733695);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("法术穿透").withStyle(ChatFormatting.BLUE),this.width / 2 - 54,this.height / 2 - 71,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.BreakManaDefenceC*100)),this.width / 2 - 25,this.height / 2 - 71,5592575);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("法力回复").withStyle(ChatFormatting.LIGHT_PURPLE),this.width / 2 - 54,this.height / 2 - 54,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.ManaReplyC+5)),this.width / 2 - 25,this.height / 2 - 54,16733695);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("冷却缩减").withStyle(ChatFormatting.AQUA),this.width / 2 - 54,this.height / 2 - 37,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.CoolDownC*100)),this.width / 2 - 25,this.height / 2 - 37,5636095);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("移动速度").withStyle(ChatFormatting.GREEN),this.width / 2 - 54,this.height / 2 - 20,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.SpeedC*100)),this.width / 2 - 25,this.height / 2 - 20,5635925);


        guiGraphics.drawCenteredString(fontRenderer,Component.literal("生命偷取").withStyle(ChatFormatting.RED),this.width / 2 + 8,this.height / 2 - 88,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f%%",ClientUtils.HealStealC*100)),this.width / 2 + 37,this.height / 2 - 88,16733525);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("固定穿甲").withStyle(ChatFormatting.GRAY),this.width / 2 + 8,this.height / 2 - 71,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.BreakDefence0C)),this.width / 2 + 37,this.height / 2 - 71,11184810);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("基础护甲").withStyle(ChatFormatting.GRAY),this.width / 2 + 8,this.height / 2 - 54,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.DefenceC)),this.width / 2 + 37,this.height / 2 - 54,11184810);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("法术抗性").withStyle(ChatFormatting.BLUE),this.width / 2 + 8,this.height / 2 - 37,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",ClientUtils.ManaDefenceC)),this.width / 2 + 37,this.height / 2 - 37,5592575);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal("生命值").withStyle(ChatFormatting.GREEN),this.width / 2 + 8,this.height / 2 - 20,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%.0f",Minecraft.getInstance().player.getMaxHealth())),this.width / 2 + 37,this.height / 2 - 20,5635925);

        int PowerOffsetX = 100;
        int PowerOffsetY = -84;
        guiGraphics.drawCenteredString(fontRenderer,Component.literal("力量").withStyle(Utils.styleOfPower),this.width / 2 + 100, this.height / 2 - 16 - 68,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%d",ClientUtils.AbilityVision.Power + ClientUtils.AbilityChangeCache.Power)).withStyle(Utils.styleOfPower),this.width / 2 + 116,this.height / 2 - 16 - 68,0);

        int IntelligentOffsetX = 100;
        int IntelligentOffsetY = -69;
        guiGraphics.drawCenteredString(fontRenderer,Component.literal("智力").withStyle(Utils.styleOfIntelligent),this.width / 2 + 100, this.height / 2 - 1 - 68,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%d",ClientUtils.AbilityVision.Intelligent + ClientUtils.AbilityChangeCache.Intelligent)).withStyle(Utils.styleOfIntelligent),this.width / 2 + 116,this.height / 2 - 1 - 68,0);

        int FlexibleOffsetX = 100;
        int FlexibleOffsetY = -54;
        guiGraphics.drawCenteredString(fontRenderer,Component.literal("灵活").withStyle(Utils.styleOfFlexible),this.width / 2 + 100, this.height / 2 + 14 - 68,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%d",ClientUtils.AbilityVision.Flexibility + ClientUtils.AbilityChangeCache.Flexibility)).withStyle(Utils.styleOfFlexible),this.width / 2 + 116,this.height / 2 + 14 - 68,0);

        int LuckyOffsetX = 100;
        int LuckyOffsetY = - 39;
        guiGraphics.drawCenteredString(fontRenderer,Component.literal("幸运").withStyle(Utils.styleOfLucky),this.width / 2 + 100, this.height / 2 + 29 - 68,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%d",ClientUtils.AbilityVision.Lucky + ClientUtils.AbilityChangeCache.Lucky)).withStyle(Utils.styleOfLucky),this.width / 2 + 116,this.height / 2 + 29 - 68,0);

        int VitalityOffsetX = 100;
        int VitalityOffsetY = -24;
        guiGraphics.drawCenteredString(fontRenderer,Component.literal("活力").withStyle(Utils.styleOfVitality),this.width / 2 + 100, this.height / 2 + 44 - 68,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%d",ClientUtils.AbilityVision.Vitality + ClientUtils.AbilityChangeCache.Vitality)).withStyle(Utils.styleOfVitality),this.width / 2 + 116,this.height / 2 + 44 - 68,0);

        int SwordOffsetX = -100;
        int SwordOffsetY = 9;
        guiGraphics.drawCenteredString(fontRenderer,Component.literal("剑术精通").withStyle(Utils.styleOfPower),this.width / 2 - 111, this.height / 2 + 77 - 68,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%d",ClientUtils.SkillVision.Sword + ClientUtils.SkillChangeCache.Sword)).withStyle(Utils.styleOfPower),this.width / 2 - 107,this.height / 2 + 100 - 68,0);

        int BowOffsetX = -38;
        int BowOffsetY = 9;
        guiGraphics.drawCenteredString(fontRenderer,Component.literal("弓术精通").withStyle(Utils.styleOfFlexible),this.width / 2 - 49, this.height / 2 + 77 - 68,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%d",ClientUtils.SkillVision.Bow + ClientUtils.SkillChangeCache.Bow)).withStyle(Utils.styleOfFlexible),this.width / 2 - 45,this.height / 2 + 100 - 68,0);

        int ManaOffsetX = 24;
        int ManaOffsetY = 9;
        guiGraphics.drawCenteredString(fontRenderer,Component.literal("法术精通").withStyle(Utils.styleOfIntelligent),this.width / 2 + 13, this.height / 2 + 77 - 68,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%d",ClientUtils.SkillVision.Mana + ClientUtils.SkillChangeCache.Mana)).withStyle(Utils.styleOfIntelligent),this.width / 2 + 17,this.height / 2 + 100 - 68,0);

        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%d",ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used - ClientUtils.AbilityChangeCache.SumPoint())).withStyle(ChatFormatting.AQUA),this.width / 2 + 88,this.height / 2 + 6,0);
        guiGraphics.drawCenteredString(fontRenderer,Component.literal(String.format("%d",ClientUtils.PlayerSkillPoint_Total - ClientUtils.PlayerSkillPoint_Used - ClientUtils.SkillChangeCache.SumPoint())).withStyle(ChatFormatting.AQUA),this.width / 2 + 88,this.height / 2 + 28,0);

        if (x > this.width / 2 + PowerOffsetX - 10 && x < this.width / 2 + PowerOffsetX + 8 && y > this.height / 2 + PowerOffsetY - 1 && y < this.height / 2 + PowerOffsetY + 10) {
            Style style = Utils.styleOfPower;
            String Ability = "力量";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("能力 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                    append(Component.literal("▶" + Ability).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("力量是探索的基础，提升力量将使你的攻击变得更加强力！").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            components.add(Component.literal("当前").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal(Ability).withStyle(ChatFormatting.RESET).withStyle(style)).
                    append(Component.literal("能力点数属性加成:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            double ExAttack = ClientUtils.AbilityVision.Power + ClientUtils.AbilityChangeCache.Power + Math.floor((ClientUtils.AbilityVision.Power + ClientUtils.AbilityChangeCache.Power) / 10.0) * 5;
            double ExBreakDefence0 = ClientUtils.AbilityVision.Power + ClientUtils.AbilityChangeCache.Power + Math.floor((ClientUtils.AbilityVision.Power + ClientUtils.AbilityChangeCache.Power) / 10.0) * 5;
            double ExCritDamage = (ClientUtils.AbilityVision.Power + ClientUtils.AbilityChangeCache.Power + Math.floor((ClientUtils.AbilityVision.Power + ClientUtils.AbilityChangeCache.Power) / 10.0) * 5);
            Compute.EmojiDescriptionExAttackDamage(components,ExAttack);
            Compute.EmojiDescriptionBreakDefence0(components,ExBreakDefence0);
            Compute.EmojiDescriptionCritDamage(components,ExCritDamage * 0.01);
            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            components.add(Component.literal(Ability).withStyle(ChatFormatting.RESET).withStyle(style).
                    append(Component.literal("能力与属性点数关系:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("①每一点能力获得:"));
            Compute.EmojiDescriptionExAttackDamage(components,1);
            Compute.EmojiDescriptionBreakDefence0(components,1);
            Compute.EmojiDescriptionCritDamage(components, 0.01);
            components.add(Component.literal("②每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("十").withStyle(ChatFormatting.RESET).withStyle(style)).
                    append(Component.literal("点能力").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("额外获得:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
            Compute.EmojiDescriptionExAttackDamage(components,5);
            Compute.EmojiDescriptionBreakDefence0(components,5);
            Compute.EmojiDescriptionCritDamage(components, 0.05);
            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            guiGraphics.renderComponentTooltip(fontRenderer,components,x,y);
        }

        if (x > this.width / 2 + IntelligentOffsetX - 10 && x < this.width / 2 + IntelligentOffsetX + 8 && y > this.height / 2 + IntelligentOffsetY - 1 && y < this.height / 2 + IntelligentOffsetY + 10) {
            Style style = Utils.styleOfIntelligent;
            String Ability = "智力";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("能力 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                    append(Component.literal("▶" + Ability).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("创造与扭曲，智胜区区凡人。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            components.add(Component.literal("当前").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal(Ability).withStyle(ChatFormatting.RESET).withStyle(style)).
                    append(Component.literal("能力点数属性加成:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            double ExManaDamage = ClientUtils.AbilityVision.Intelligent + ClientUtils.AbilityChangeCache.Intelligent + Math.floor((ClientUtils.AbilityVision.Intelligent + ClientUtils.AbilityChangeCache.Intelligent) / 10.0) * 5;
            double ExBreakManaDefence = ClientUtils.AbilityVision.Intelligent + ClientUtils.AbilityChangeCache.Intelligent + Math.floor((ClientUtils.AbilityVision.Intelligent + ClientUtils.AbilityChangeCache.Intelligent) / 10.0) * 5;
            double ExManaReply = (ClientUtils.AbilityVision.Intelligent + ClientUtils.AbilityChangeCache.Intelligent + Math.floor((ClientUtils.AbilityVision.Intelligent + ClientUtils.AbilityChangeCache.Intelligent) / 10.0) * 5);
            Compute.EmojiDescriptionManaAttackDamage(components,ExManaDamage);
            Compute.EmojiDescriptionManaBreakDefence0(components,ExBreakManaDefence);
            Compute.EmojiDescriptionMaxMana(components,ExManaReply);
            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            components.add(Component.literal(Ability).withStyle(ChatFormatting.RESET).withStyle(style).
                    append(Component.literal("能力与属性点数关系:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("①每一点能力获得:"));
            Compute.EmojiDescriptionManaAttackDamage(components,1);
            Compute.EmojiDescriptionManaBreakDefence0(components,1);
            Compute.EmojiDescriptionMaxMana(components,1);
            components.add(Component.literal("②每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("十").withStyle(ChatFormatting.RESET).withStyle(style)).
                    append(Component.literal("点能力").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("额外获得:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
            Compute.EmojiDescriptionManaAttackDamage(components,5);
            Compute.EmojiDescriptionManaBreakDefence0(components,5);
            Compute.EmojiDescriptionMaxMana(components,5);
            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            guiGraphics.renderComponentTooltip(fontRenderer,components,x,y);
        }

        if (x > this.width / 2 + FlexibleOffsetX - 10 && x < this.width / 2 + FlexibleOffsetX + 8 && y > this.height / 2 + FlexibleOffsetY - 1 && y < this.height / 2 + FlexibleOffsetY + 10) {
            Style style = Utils.styleOfFlexible;
            String Ability = "灵活";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("能力 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                    append(Component.literal("▶" + Ability).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("距离把控与频繁施法。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            components.add(Component.literal("当前").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal(Ability).withStyle(ChatFormatting.RESET).withStyle(style)).
                    append(Component.literal("能力点数属性加成:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            double ExAttackRange = ClientUtils.AbilityVision.Flexibility + ClientUtils.AbilityChangeCache.Flexibility + Math.floor((ClientUtils.AbilityVision.Flexibility + ClientUtils.AbilityChangeCache.Flexibility) / 10.0) * 5;
            double ExMoveSpeed = ClientUtils.AbilityVision.Flexibility + ClientUtils.AbilityChangeCache.Flexibility + Math.floor((ClientUtils.AbilityVision.Flexibility + ClientUtils.AbilityChangeCache.Flexibility) / 10.0) * 5;
            double ExCoolDown = (ClientUtils.AbilityVision.Flexibility + ClientUtils.AbilityChangeCache.Flexibility + Math.floor((ClientUtils.AbilityVision.Flexibility + ClientUtils.AbilityChangeCache.Flexibility) / 10.0) * 5);
            Compute.EmojiDescriptionAttackRange(components,ExAttackRange * 0.01);
            Compute.EmojiDescriptionMovementSpeed(components,ExMoveSpeed * 0.01);
            Compute.EmojiDescriptionCoolDown(components,ExCoolDown * 0.001);
            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            components.add(Component.literal(Ability).withStyle(ChatFormatting.RESET).withStyle(style).
                    append(Component.literal("能力与属性点数关系:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("①每一点能力获得:"));
            Compute.EmojiDescriptionAttackRange(components,0.01);
            Compute.EmojiDescriptionMovementSpeed(components,0.01);
            Compute.EmojiDescriptionCoolDown(components, 0.001);
            components.add(Component.literal("②每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("十").withStyle(ChatFormatting.RESET).withStyle(style)).
                    append(Component.literal("点能力").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("额外获得:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));
            Compute.EmojiDescriptionAttackRange(components,0.05);
            Compute.EmojiDescriptionMovementSpeed(components,0.05);
            Compute.EmojiDescriptionCoolDown(components, 0.005);
            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            guiGraphics.renderComponentTooltip(fontRenderer,components,x,y);
        }

        if (x > this.width / 2 + LuckyOffsetX - 10 && x < this.width / 2 + LuckyOffsetX + 8 && y > this.height / 2 + LuckyOffsetY - 1 && y < this.height / 2 + LuckyOffsetY + 10) {
            Style style = Utils.styleOfLucky;
            String Ability = "幸运";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("能力 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                    append(Component.literal("▶" + Ability).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("时运与经验增幅。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            components.add(Component.literal("当前").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal(Ability).withStyle(ChatFormatting.RESET).withStyle(style)).
                    append(Component.literal("能力点数属性加成:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            double ExCritRate = ClientUtils.AbilityVision.Lucky + ClientUtils.AbilityChangeCache.Lucky + Math.floor((ClientUtils.AbilityVision.Lucky + ClientUtils.AbilityChangeCache.Lucky) / 10.0) * 5;
            double ExExpUp = ClientUtils.AbilityVision.Lucky + ClientUtils.AbilityChangeCache.Lucky + Math.floor((ClientUtils.AbilityVision.Lucky + ClientUtils.AbilityChangeCache.Lucky) / 10.0) * 5;
            double ExLucky = (ClientUtils.AbilityVision.Lucky + ClientUtils.AbilityChangeCache.Lucky + Math.floor((ClientUtils.AbilityVision.Lucky + ClientUtils.AbilityChangeCache.Lucky) / 10.0) * 5);

            Compute.EmojiDescriptionCritRate(components,ExCritRate * 0.001);
            Compute.EmojiDescriptionExpUp(components,ExExpUp * 0.01);
            Compute.EmojiDescriptionLuckyUp(components,ExLucky * 0.01);

            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            components.add(Component.literal(Ability).withStyle(ChatFormatting.RESET).withStyle(style).
                    append(Component.literal("能力与属性点数关系:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("①每一点能力获得:"));

            Compute.EmojiDescriptionCritRate(components,0.001);
            Compute.EmojiDescriptionExpUp(components,0.01);
            Compute.EmojiDescriptionLuckyUp(components,0.01);

            components.add(Component.literal("②每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("十").withStyle(ChatFormatting.RESET).withStyle(style)).
                    append(Component.literal("点能力").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("额外获得:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));

            Compute.EmojiDescriptionCritRate(components,0.005);
            Compute.EmojiDescriptionExpUp(components,0.05);
            Compute.EmojiDescriptionLuckyUp(components,0.05);

            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            guiGraphics.renderComponentTooltip(fontRenderer,components,x,y);
        }

        if (x > this.width / 2 + VitalityOffsetX - 10 && x < this.width / 2 + VitalityOffsetX + 8 && y > this.height / 2 + VitalityOffsetY - 1 && y < this.height / 2 + VitalityOffsetY + 10) {
            Style style = Utils.styleOfVitality;
            String Ability = "活力";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("能力 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                    append(Component.literal("▶" + Ability).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("提供生机与回复。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            components.add(Component.literal("当前").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal(Ability).withStyle(ChatFormatting.RESET).withStyle(style)).
                    append(Component.literal("能力点数属性加成:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            double ExHealReply = ClientUtils.AbilityVision.Vitality + ClientUtils.AbilityChangeCache.Vitality + Math.floor((ClientUtils.AbilityVision.Vitality + ClientUtils.AbilityChangeCache.Vitality) / 10.0) * 5;
            double ExMaxHeal = ClientUtils.AbilityVision.Vitality + ClientUtils.AbilityChangeCache.Vitality + Math.floor((ClientUtils.AbilityVision.Vitality + ClientUtils.AbilityChangeCache.Vitality) / 10.0) * 5;
            double ExHealAmplitude = (ClientUtils.AbilityVision.Vitality + ClientUtils.AbilityChangeCache.Vitality + Math.floor((ClientUtils.AbilityVision.Vitality + ClientUtils.AbilityChangeCache.Vitality) / 10.0) * 5);

            Compute.EmojiDescriptionHealthRecover(components,ExHealReply * 0.1);
            Compute.EmojiDescriptionMaxHealth(components,ExMaxHeal);
            Compute.EmojiDescriptionHealAmplification(components,ExHealAmplitude * 0.01);

            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            components.add(Component.literal(Ability).withStyle(ChatFormatting.RESET).withStyle(style).
                    append(Component.literal("能力与属性点数关系:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("①每一点能力获得:"));

            Compute.EmojiDescriptionHealthRecover(components,0.1);
            Compute.EmojiDescriptionMaxHealth(components,1);
            Compute.EmojiDescriptionHealAmplification(components,0.01);

            components.add(Component.literal("②每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                    append(Component.literal("十").withStyle(ChatFormatting.RESET).withStyle(style)).
                    append(Component.literal("点能力").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("额外获得:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)));

            Compute.EmojiDescriptionHealthRecover(components,0.5);
            Compute.EmojiDescriptionMaxHealth(components,5);
            Compute.EmojiDescriptionHealAmplification(components,0.05);

            Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
            guiGraphics.renderComponentTooltip(fontRenderer,components,x,y);
        }

        if (x > this.width / 2 + SwordOffsetX - 30 && x < this.width / 2 + SwordOffsetX + 7 && y > this.height / 2 + SwordOffsetY - 2 && y < this.height / 2 + SwordOffsetY + 9) {
            Style style = Utils.styleOfPower;
            String Skill = "剑术";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("精通 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                    append(Component.literal("◉" + Skill).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("对剑术的精通将会提升你的近战能力，使你的近战攻击更加精确、有力。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            guiGraphics.renderComponentTooltip(fontRenderer,components,x,y);
        }

        if (x > this.width / 2 + BowOffsetX - 30 && x < this.width / 2 + BowOffsetX + 7 && y > this.height / 2 + BowOffsetY - 2 && y < this.height / 2 + BowOffsetY + 9) {
            Style style = Utils.styleOfHealth;
            String Skill = "弓术";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("精通 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                    append(Component.literal("◉" + Skill).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("对弓术的精通将会使你人弓合一。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            guiGraphics.renderComponentTooltip(fontRenderer,components,x,y);
        }

        if (x > this.width / 2 + ManaOffsetX - 30 && x < this.width / 2 + ManaOffsetX + 7 && y > this.height / 2 + ManaOffsetY - 2 && y < this.height / 2 + ManaOffsetY + 9) {
            Style style = Utils.styleOfMana;
            String Skill = "法术";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("精通 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                    append(Component.literal("◉" + Skill).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("对法术的精通将使你的法术得到各个维度的强化。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            guiGraphics.renderComponentTooltip(fontRenderer,components,x,y);
        }

        if (ClientUtils.AbilityChangeCache.Power > 0) {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GOLD),this.width / 2 + PowerOffsetX - 19,this.height / 2 + PowerOffsetY + 1,0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GRAY),this.width / 2 + PowerOffsetX - 19,this.height / 2 + PowerOffsetY + 1,0);

        }

        if (ClientUtils.AbilityChangeCache.Intelligent > 0) {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GOLD),this.width / 2 + IntelligentOffsetX - 19,this.height / 2 + IntelligentOffsetY + 1,0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GRAY),this.width / 2 + IntelligentOffsetX - 19,this.height / 2 + IntelligentOffsetY + 1,0);

        }

        if (ClientUtils.AbilityChangeCache.Flexibility > 0) {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GOLD),this.width / 2 + FlexibleOffsetX - 19,this.height / 2 + FlexibleOffsetY + 1,0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GRAY),this.width / 2 + FlexibleOffsetX - 19,this.height / 2 + FlexibleOffsetY + 1,0);

        }

        if (ClientUtils.AbilityChangeCache.Lucky > 0) {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GOLD),this.width / 2 + LuckyOffsetX - 19,this.height / 2 + LuckyOffsetY + 1,0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GRAY),this.width / 2 + LuckyOffsetX - 19,this.height / 2 + LuckyOffsetY + 1,0);

        }

        if (ClientUtils.AbilityChangeCache.Vitality > 0) {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GOLD),this.width / 2 + VitalityOffsetX - 19,this.height / 2 + VitalityOffsetY + 1,0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GRAY),this.width / 2 + VitalityOffsetX - 19,this.height / 2 + VitalityOffsetY + 1,0);

        }

        int[] OffsetX = {
                PowerOffsetX,IntelligentOffsetX,FlexibleOffsetX,LuckyOffsetX,VitalityOffsetX
        };
        int[] OffsetY = {
                PowerOffsetY,IntelligentOffsetY,FlexibleOffsetY,LuckyOffsetY,VitalityOffsetY
        };

        for (int i = 0; i < 5; i++) {
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used) {
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("+").withStyle(ChatFormatting.GOLD),this.width / 2 + OffsetX[i] + 31,this.height / 2 + OffsetY[i] + 1,0);
            } else {
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("+").withStyle(ChatFormatting.GRAY),this.width / 2 + OffsetX[i] + 31,this.height / 2 + OffsetY[i] + 1,0);
            }
        }

        if (ClientUtils.SkillChangeCache.Sword > 0) {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GOLD),this.width / 2 + SwordOffsetX - 24,this.height / 2 + SwordOffsetY + 23,0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GRAY),this.width / 2 + SwordOffsetX - 24,this.height / 2 + SwordOffsetY + 23,0);
        }

        if (ClientUtils.SkillChangeCache.Bow > 0) {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GOLD),this.width / 2 + BowOffsetX - 24,this.height / 2 + BowOffsetY + 23,0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GRAY),this.width / 2 + BowOffsetX - 24,this.height / 2 + BowOffsetY + 23,0);
        }

        if (ClientUtils.SkillChangeCache.Mana > 0) {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GOLD),this.width / 2 + ManaOffsetX - 24,this.height / 2 + ManaOffsetY + 23,0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer,Component.literal("-").withStyle(ChatFormatting.GRAY),this.width / 2 + ManaOffsetX - 24,this.height / 2 + ManaOffsetY + 23,0);
        }

        int[] SkillOffsetX = {
                SwordOffsetX,BowOffsetX,ManaOffsetX
        };

        int[] SkillOffsetY = {
                SwordOffsetY,BowOffsetY,ManaOffsetY
        };

        for (int i = 0; i < 3; i++) {
            if (ClientUtils.SkillChangeCache.SumPoint() < ClientUtils.PlayerSkillPoint_Total - ClientUtils.PlayerSkillPoint_Used) {
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("+").withStyle(ChatFormatting.GOLD),this.width / 2 + SkillOffsetX[i] + 11,this.height / 2 + SkillOffsetY[i] + 23,0);
            }
            else {
                guiGraphics.drawCenteredString(fontRenderer,Component.literal("+").withStyle(ChatFormatting.GRAY),this.width / 2 + SkillOffsetX[i] + 11,this.height / 2 + SkillOffsetY[i] + 23,0);

            }
        }
    }

    @Override
    public void onClose() {
        ClientUtils.AbilityChangeCache.Clear();
        ClientUtils.SkillChangeCache.Clear();
        super.onClose();
    }
}

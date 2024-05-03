package com.very.wraq.customized.players.bow.Guang_Yi;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.customized.Customize;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class GuangYiBow extends WraqBow {

    private final double BaseDamage = Customize.AttackDamage;
    private final double DefencePenetration0 = Customize.DefencePenetration0;
    private final double CriticalHitRate = 0.25;
    private final double SpeedUp = 0.6F;

    public GuangYiBow(Properties p_40524_) {
        super(p_40524_);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,Customize.BowCritDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
        Utils.BowTag.put(this,1.0d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfMine;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("射手的专注").withStyle(style));
        components.add(Component.literal(" 双击左键进入").withStyle(style).
                append(Component.literal("架枪").withStyle(style)));
        components.add(Component.literal(" 你的箭矢会削减目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("5%")).
                append(Component.literal("持续5s，至多叠加10层").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 你距离目标的每个距离单位为你提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Swiftness("0.1")));
        components.add(Component.literal(" 如果架枪的时长超过5s，为你提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("100%")));
        components.add(Component.literal(" -跳跃将会结束架枪状态").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        Compute.DescriptionPassive(components,Component.literal("割裂").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("单次箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("会发射两枚箭矢").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 第一发箭矢转化会附带").withStyle(ChatFormatting.WHITE).
                append(Component.literal("4倍").withStyle(CustomStyle.styleOfBlackForest)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("物理伤害").withStyle(CustomStyle.styleOfBlackForest)));
        components.add(Component.literal(" 第二发箭矢转化为箭雨，对周围敌人造成").withStyle(ChatFormatting.WHITE).
                append(Component.literal("物理伤害").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("并回复").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("5%")).
                append(Compute.AttributeDescription.Health("")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚，授予对维瑞阿契做出了杰出贡献的 - Guang_Yi").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WAX_OFF);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.WAX_OFF);
        Utils.GuangYiSecondArrowDelay = 3;
    }
}

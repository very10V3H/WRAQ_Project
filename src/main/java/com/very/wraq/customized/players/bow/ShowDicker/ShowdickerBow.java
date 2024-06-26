package com.very.wraq.customized.players.bow.ShowDicker;

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

public class ShowdickerBow extends WraqBow {
    private final double BaseDamage = Customize.AttackDamage;
    private final double DefencePenetration0 = Customize.DefencePenetration0;
    private final double BreakDefence = 0.4F;
    private final double CriticalHitRate = 0.25;
    private final double SpeedUp = 0.6F;
    public ShowdickerBow(Properties p_40524_) {
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
        Style style = CustomStyle.styleOfIce;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("专注射击").withStyle(style));
        components.add(Component.literal(" 进行4次普通攻击后，你将可以连续释放8发箭矢").withStyle(ChatFormatting.WHITE));
        Compute.DescriptionPassive(components,Component.literal("切割").withStyle(style));
        components.add(Component.literal(" 你的箭矢将根据目标当前").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("，提供至多").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("4倍").withStyle(style)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("物理伤害").withStyle(style)));
        components.add(Component.literal(" 世间万物，皆系于一箭之上").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚阿瓦罗萨的寒冰之弓，授予对维瑞阿契做出了杰出贡献的 - showdicker4").withStyle(ChatFormatting.AQUA));
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
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.SNOWFLAKE);

    }
}

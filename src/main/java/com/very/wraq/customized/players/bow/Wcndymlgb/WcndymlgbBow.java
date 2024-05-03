package com.very.wraq.customized.players.bow.Wcndymlgb;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.customized.Customize;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
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

public class WcndymlgbBow extends WraqBow {
    private final double BaseDamage = Customize.AttackDamage;
    private final double DefencePenetration0 = Customize.DefencePenetration0;
    private final double CriticalHitRate = 0.25;
    private final double SpeedUp = 0.6F;
    public WcndymlgbBow(Properties p_40524_) {
        super(p_40524_);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,Customize.BowCritDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
        Utils.BowTag.put(this,1.0d);
    } // /give wcndymlgb vmd:wc_curios_paper
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfHealth;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal("精灵女王的赐福").withStyle(style));
        components.add(Component.literal(" ▶使用左键进入").withStyle(ChatFormatting.WHITE).
                append(Component.literal("赐福").withStyle(style)).
                append(Component.literal("状态").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 当你处于").withStyle(ChatFormatting.WHITE).
                append(Component.literal("赐福").withStyle(style)).
                append(Component.literal("状态时").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("你射出的箭矢将不受重力影响，并且获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("追踪效果").withStyle(style)));
        components.add(Component.literal(" 箭矢在命中目标时会为你提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("5%")).
                append(Component.literal("，持续5s，至多叠加至5层").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" -Ex.你的单次蓄力弓会额外进行两次弓箭射击，每枚箭矢拥有0.5基础伤害").withStyle(style));
        components.add(Component.literal(" ▶再次左键取消赐福状态").withStyle(ChatFormatting.WHITE));
        Compute.DescriptionPassive(components,Component.literal("森林的加护").withStyle(style));
        components.add(Component.literal(" 获得等同于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("15%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ExAttackDamage("")));
        components.add(Component.literal(" 每").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("1000")).
                append(Component.literal("会获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.DefencePenetration("5%")));
        components.add(Component.literal(" -护甲穿透属性最高可达50%").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" 这把精灵长弓射程长达四百米，传言弓弦中掺有凯兰崔尔女王的秀发，具有祝福和庇护的力量").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一把凯兰崔姆之弓，授予对维瑞阿契做出了杰出贡献的 - wcndymlgb").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        if (Utils.WcBowStatus) {
            MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                    serverPlayer, PlayerAttributes.PlayerAttackDamage(serverPlayer), true,true, ParticleTypes.COMPOSTER);
            arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 1.5f, 1.0f);
            arrow.setCritArrow(true);
            arrow.setNoGravity(true);
            WraqBow.adjustArrow(arrow, serverPlayer);
            serverPlayer.level().addFreshEntity(arrow);
            Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
            ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.COMPOSTER);
            ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);
            Wcndymlgb.WcBowAttackTick = 6;
        }
        else {
            Compute.TraceArrowShoot(serverPlayer,ParticleTypes.COMPOSTER);
        }
    }
}

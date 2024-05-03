package com.very.wraq.series.nether.Equip;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.process.element.Element;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class NetherBow extends WraqBow {
    private final double BaseDamage = 140.0d;
    private final double DefencePenetration0 = 1200;
    private final double CriticalHitRate = 0.25;
    private final double CHitDamage = 0.6;
    private final double SpeedUp = 0.3F;
    public NetherBow(Properties p_40524_) {
        super(p_40524_);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Element.FireElementValue.put(this, 1d);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.BowTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("夸塔兹长弓").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfNether,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfNether,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("夸塔兹激化箭矢").withStyle(CustomStyle.styleOfQuartz));
        components.add(Component.literal("使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("下界能量石英").withStyle(CustomStyle.styleOfNether)).
                append(Component.literal("制作而成的长弓。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("射出箭矢时，将会消耗").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("40")).
                append(Component.literal("使箭矢不会下坠，并造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%额外伤害。").withStyle(ChatFormatting.YELLOW)));
        components.add(Component.literal("所有箭矢会根据飞行距离，至多提供").withStyle(ChatFormatting.WHITE).
                append(Component.literal("100%额外伤害（在100格达到最大值）").withStyle(ChatFormatting.YELLOW)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfNether,ChatFormatting.WHITE);
        components.add(Component.literal("Nether-Bow").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.UNDERLINE));
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        if (Compute.PlayerCurrentManaNum(serverPlayer) > 40) {
            Compute.PlayerManaAddOrCost(serverPlayer, -40);
            MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
            arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
            arrow.setCritArrow(true);
            arrow.setNoGravity(true);
            WraqBow.adjustArrow(arrow, serverPlayer);
            serverPlayer.level().addFreshEntity(arrow);
            Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
            ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WITCH);
        } else {
            MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
            arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 3F, 1.0f);
            arrow.setCritArrow(true);
            WraqBow.adjustArrow(arrow, serverPlayer);
            serverPlayer.level().addFreshEntity(arrow);
            Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
            ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WAX_OFF);
        }
    }
}

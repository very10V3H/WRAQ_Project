package com.very.wraq.series.instance.Moon.Equip;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;

public class MoonBow extends BowItem {
    private final double BaseDamage = 480;
    private final double DefencePenetration0 = 3200;
    private final double CriticalHitRate = 0.25;
    private final double CHitDamage = 1.35;
    private final double SpeedUp = 0.6F;
    public MoonBow(Properties p_40524_) {
        super(p_40524_);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.BowTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfMoon1;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("行星之引").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("会将目标周围半径6内的其他敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("小幅牵引").withStyle(style)).
                append(Component.literal("至目标位置").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("尘月之风").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("将额外释放").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("两支箭矢").withStyle(CustomStyle.styleOfFlexible)));
        components.add(Component.literal(" 两支额外箭矢").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("会自动锁定自身半径30格内的目标").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMoon(components);
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.BowAttack(player,Utils.BowNumMap.get(StringUtils.BowNameString.MoonBow));
        return interactionHand.equals(InteractionHand.MAIN_HAND)
                ? InteractionResultHolder.success(player.getMainHandItem()) : InteractionResultHolder.success(player.getOffhandItem());    }

    public static void MoonBowExShoot(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MoonBow.get())) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),80,80,80));
            mobList.removeIf(mob -> mob.distanceTo(player) > 30 || mob instanceof Civil);
            Mob NearestMob = null;
            double Distance = 80;
            Mob NearerMob = null;
            for (Mob mob : mobList) {
                if (mob.distanceTo(player) < Distance) {
                    NearestMob = mob;
                    Distance = mob.distanceTo(player);
                }
            }

            if (NearestMob != null) {
                mobList.remove(NearestMob);
                Distance = 80;
                for (Mob mob : mobList) {
                    if (mob.distanceTo(player) < Distance) {
                        NearerMob = mob;
                        Distance = mob.distanceTo(player);
                    }
                }
                if (NearerMob == null) NearerMob = NearestMob;
                MyArrow myArrow = new MyArrow(EntityType.ARROW,player.level(),player,true);
                myArrow.setDeltaMovement(NearestMob.position().add(0,1,0).subtract(player.position().add(0,1.5,0)).normalize().scale(4.5));
                myArrow.moveTo(player.pick(0.5,0,false).getLocation());
                myArrow.setCritArrow(true);
                myArrow.setNoGravity(true);
                ProjectileUtil.rotateTowardsMovement(myArrow,1);
                player.level().addFreshEntity(myArrow);
                ParticleProvider.LineParticle(player.level(), (int) NearestMob.distanceTo(player),player.pick(0.5,0,false).getLocation(),NearestMob.position().add(0,1,0), ParticleTypes.FIREWORK);

                MyArrow myArrow1 = new MyArrow(EntityType.ARROW,player.level(),player,true);
                myArrow1.setDeltaMovement(NearerMob.position().add(0,1,0).subtract(player.position().add(0,1.5,0)).normalize().scale(4.5));
                myArrow.moveTo(player.pick(0.5,0,false).getLocation());
                myArrow.setCritArrow(true);
                myArrow.setNoGravity(true);
                ProjectileUtil.rotateTowardsMovement(myArrow1,1);
                player.level().addFreshEntity(myArrow1);
                ParticleProvider.LineParticle(player.level(),(int) NearerMob.distanceTo(player),player.pick(0.5,0,false).getLocation(),NearerMob.position().add(0,1,0), ParticleTypes.FIREWORK);
            }
        }
    }

    public static void Passive(Player player, Mob mob) {
        if (!player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MoonBow.get())) return;
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(),15,15,15));
        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6 || mob1.equals(mob));
        mobList.forEach(mob1 -> {
            Compute.MonsterGatherProvider(mob1,2,mob.position());
        });
    }
}

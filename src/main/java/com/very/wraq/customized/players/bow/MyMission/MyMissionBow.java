package com.very.wraq.customized.players.bow.MyMission;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.customized.Customize;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyMissionBow extends WraqBow {

    private final double BaseDamage = Customize.AttackDamage;
    private final double DefencePenetration0 = Customize.DefencePenetration0;
    private final double BreakDefence = 0.4F;
    private final double CriticalHitRate = 0.25;
    private final double SpeedUp = 0.6F;

    public MyMissionBow(Properties p_40524_) {
        super(p_40524_);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration.put(this,this.BreakDefence);
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
        Style style = CustomStyle.styleOfHealth;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("誓约").withStyle(style));
        components.add(Component.literal(" 你的箭矢将会锁定一定范围内的目标。").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 每击杀一名敌人，获得:").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExAttackDamage("200")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("200")));
        components.add(Component.literal(" 3.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("200%")));
        components.add(Component.literal(" -至多可叠加至5层，持续5s").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 若附近只有一名敌人，为你提供:").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 1.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExAttackDamage("1000")));
        components.add(Component.literal(" 2.").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("1000%")));
        Compute.DescriptionActive(components,Component.literal("誓约").withStyle(style));
        components.add(Component.literal(" 你的箭矢在命中敌人时，会存储10层数").withStyle(ChatFormatting.WHITE).
                append(Component.literal("层数").withStyle(style)).
                append(Component.literal("，你可以释放主动技能来拔出所有敌人身上的箭矢").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 每支箭矢造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamageValue("1000%")).
                append(Component.literal("并受").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")).
                append(Component.literal("增幅").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作滑板鞋的矛，授予对维瑞阿契做出了杰出贡献的 - My_mission").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    public static Map<Mob,Integer> mobArrowCountMap = new HashMap<>();
    public static int Count = 0;
    public static int CountTick = 0;

    public static boolean IsPlayer(Player player) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.MyMissionBow.get());
    }

    public static void CountAdd(Player player, Mob mob) {
        if (!IsPlayer(player)) return ;
        int TickCount = player.getServer().getTickCount();
        if (TickCount > CountTick) Count = 0;
        Count = Math.min(5,Count + 1);
        CountTick = TickCount + 100;
        Compute.EffectLastTimeSend(player, ModItems.MyMissionBow.get().getDefaultInstance(),100,Count);;
    }

    public static double ExDamage(Player player) {
        if (!IsPlayer(player)) return 0;
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),40,40,40));
        if (mobList.size() == 1) return 1000;
        return Count * 200;
    }

    public static double ExCritDamage(Player player) {
        if (!IsPlayer(player)) return 0;
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),40,40,40));
        if (mobList.size() == 1) return 10;
        return Count * 2;
    }

    public static double ExDefencePenetration0(Player player) {
        if (!IsPlayer(player)) return 0;
        return Count * 200;
    }

    public static void ArrowAdd(Player player, Mob mob) {
        if (!IsPlayer(player)) return ;
        mobArrowCountMap.put(mob,mobArrowCountMap.getOrDefault(mob,0) + 10);
        if (mobArrowCountMap.get(mob) >= 100) {
            ArrowDamage(player,mob);
            mobArrowCountMap.remove(mob);
        }
    }

    public static void ArrowDamage(Player player, Mob mob) {
        Compute.Damage.AttackDamageToMonster_AdDamage(player,mob,
                PlayerAttributes.PlayerAttackDamage(player) *
                        (1 + PlayerAttributes.PlayerCritDamage(player)) * (MyMissionCurios1.IsOn(player) ? 3000 : 1000));
    }

    public static void Active(Player player) {
        mobArrowCountMap.forEach((mob, integer) -> {
            Compute.Damage.AttackDamageToMonster_AdDamage(player,mob,
                    PlayerAttributes.PlayerAttackDamage(player) *
                            (1 + PlayerAttributes.PlayerCritDamage(player)) * integer * (MyMissionCurios1.IsOn(player) ? 30 : 10));
        });
        mobArrowCountMap.clear();
    }

    public static void RangeDamage(Player player, Mob mob) {
        if (!IsPlayer(player)) return;
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(),15,15,15));
        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6 || mob1.equals(mob));
        mobList.forEach(mob1 -> {
            Compute.Damage.AttackDamageToMonster_RateAdDamage(player,mob1,10);
        });
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                serverPlayer, PlayerAttributes.PlayerAttackDamage(serverPlayer), true, true,ParticleTypes.COMPOSTER);

        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 1.5f, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.COMPOSTER);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);
    }
}

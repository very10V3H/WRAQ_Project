package com.Very.very.Customize.Players.Lei_yan233;

import com.Very.very.CoreAttackModule.MyArrow;
import com.Very.very.Customize.Customize;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.SoundsPackets.SoundsS2CPacket;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemTier;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeiyanBow extends PickaxeItem {

    private final double BaseDamage = Customize.AttackDamage;
    private final double DefencePenetration0 = Customize.DefencePenetration0;
    private final double CriticalHitRate = 0.25;
    private final double CHitDamage = 4;
    private final double SpeedUp = 0.6F;

    public LeiyanBow(Properties p_40524_) {
        super(ItemTier.VMaterial,2,0,p_40524_);
        Utils.AttackDamage.put(this,this.BaseDamage);
        Utils.DefencePenetration0.put(this,this.DefencePenetration0);
        Utils.CritRate.put(this,this.CriticalHitRate);
        Utils.CritDamage.put(this,this.CHitDamage);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1d);
        Utils.CustomizedList.add(this);
        Utils.BowTag.put(this,1.0d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = Utils.styleOfHealth;
        Compute.ForgingHoverName(stack,Component.literal("<维瑞级>").withStyle(ChatFormatting.GOLD).append(Component.literal("破空之隼").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSky).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionActive(components,Component.literal(""));
        components.add(Component.literal(" 1.艾恩·萨维尼之权:").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 当你拥有5层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("心眼").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，每0.5s对周围所有敌人造成一次").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("2倍").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPower)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("物理伤害").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPower)).
                append(Component.literal("持续5s").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" -伤害受").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritDamage("")).
                append(Component.literal("提升").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 2.艾恩·萨维尼之力").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 当你拥有5层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("心眼").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，为你存储6支").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("贤者高阶精灵之箭").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("，在命中目标时，使目标在近5s受到的伤害提升5%").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("艾恩·萨维尼的心眼").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通箭矢攻击").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("附带").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("4倍").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPower)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("物理伤害").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfPower)).
                append(Component.literal("，并为你提供一层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("心眼").withStyle(ChatFormatting.RESET).withStyle(style)));
        components.add(Component.literal(" 每层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("心眼").withStyle(ChatFormatting.RESET).withStyle(style)).
                append(Component.literal("为你提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("85%")).
                append(Compute.AttributeDescription.AttackDamage("25%")));
        components.add(Component.literal(" ———贤者和术士有什么差别？亲爱的，就像是营火和火山的差别。").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal("      —— 瑞达尼亚的米莉加达，艾瑞图萨学院讲师").withStyle(ChatFormatting.RESET).withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作艾恩·萨维尼之赐，授予对维瑞阿契做出了杰出贡献的 - Lei_yan233").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.BowAttack(player,Utils.BowNumMap.get(StringUtils.BowNameString.LeiyanBow));
        return super.use(level, player, interactionHand);
    }

    public static boolean IsPlayer(Player player) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LeiyanBow.get());
    }

    public static int Count = 0;
    public static boolean IsSceptre = false;
    public static int Active1Tick = 0;
    public static int Active2Tick = 0;
    public static int Active2Count = 0;
    public static int CountEffectTick = 0;

    public static int SwitchCoolDown = 0;

    public static void SwitchMode(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (TickCount > SwitchCoolDown) {
            IsSceptre = !IsSceptre;
            SwitchCoolDown = TickCount + 100;
            Compute.CoolDownTimeSend(player,ModItems.LeiyanBow.get().getDefaultInstance(),100);
            if (IsSceptre) ModNetworking.sendToClient(new SoundsS2CPacket(6),(ServerPlayer) player);
            else {
                ModNetworking.sendToClient(new SoundsS2CPacket(5),(ServerPlayer) player);
                Active1Tick = 100;
            }
        }
    }

    public static double CritDamageUp(Player player) {
        if (!IsPlayer(player)) return 0;
        int TickCount = player.getServer().getTickCount();
        if (CountEffectTick > TickCount) return 4.25;
        else return Count * 0.85;
    }

    public static double ExAttackDamage(Player player) {
        if (!IsPlayer(player)) return 0;
        int TickCount = player.getServer().getTickCount();
        if (CountEffectTick > TickCount) return 1.25;
        else return Count * 0.25;
    }

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        Active1(player);
        Active2(player);
    }

    public static void CountAdd(Player player) {
        if (!IsPlayer(player)) return;
        Count = Math.min(5,Count + 1);
        if (Count == 5) {
            CountEffectTick = player.getServer().getTickCount() + 100;
            Count = 0;
            if (IsSceptre) Active1Tick = 100;
            else {
                Active2Tick = 12;
                Active2Count = 6;
            }
        }
        Compute.EffectLastTimeSend(player,ModItems.LeiyanBow.get().getDefaultInstance(),8888,Count,true);
    }

    public static void Active1(Player player) {
        if (Active1Tick > 0) {
            if (Active1Tick % 10 == 0) {
                List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,AABB.ofSize(player.position(),20,20,20));
                mobList.removeIf(mob -> mob.distanceTo(player) > 9);
                mobList.forEach(mob -> {
                    Compute.Damage.AttackDamageToMonster_AdDamage(player,mob, Compute.XpStrengthADDamage(player,2) * (1 + Compute.PlayerAttributes.PlayerCritDamage(player)));
                });
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 9, 100, ParticleTypes.COMPOSTER, 0);
                ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 9, 100, ParticleTypes.COMPOSTER, 0);

            }
            Active1Tick --;
        }
    }

    public static void Active2(Player player) {
        if (Active2Tick > 0) {
            if (Active2Tick % 2 == 0) Shoot(player,1,false);
            Active2Tick --;
        }
    }

    public static void Shoot (Player player, double rate, boolean WhetherShootByPlayer) {
        ServerPlayer serverPlayer = (ServerPlayer) player;

        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                serverPlayer, WhetherShootByPlayer,true,rate);

        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 1.5f, 1.0f);
        arrow.setCritArrow(true);
        arrow.setNoGravity(true);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.5, 0.4, 8, ParticleTypes.COMPOSTER, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.25, 0.4, 8, ParticleTypes.COMPOSTER, 0);

    }

    public static Map<Mob,Integer> Active2MobCount = new HashMap<>();
    public static Map<Mob,Integer> Active2MobTick = new HashMap<>();

    public static double ExDamage(Player player, Mob mob) {
        if (!IsPlayer(player)) return 0;
        if (Active2Count > 0) {
            Active2Count --;
            AddActive2MobCount(mob);
        }
        return Compute.XpStrengthADDamage(player,4);
    }

    public static void AddActive2MobCount(Mob mob) {
        int TickCount = mob.getServer().getTickCount();
        if (Active2MobTick.containsKey(mob) && Active2MobTick.get(mob) < TickCount) Active2MobCount.put(mob,0);
        Active2MobCount.put(mob,Math.min(5,Active2MobCount.getOrDefault(mob,0) + 1));
        Active2MobTick.put(mob,TickCount + 100);
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING,100));
    }

    public static double MobDamageEnhance(Mob mob) {
        if (!Active2MobCount.containsKey(mob)) return 0;
        int TickCount = mob.getServer().getTickCount();
        if (Active2MobTick.get(mob) > TickCount) return 0 + Active2MobCount.get(mob) * 0.05;
        return 0;
    }


}


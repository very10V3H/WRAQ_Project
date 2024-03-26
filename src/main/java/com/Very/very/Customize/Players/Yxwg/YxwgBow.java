package com.Very.very.Customize.Players.Yxwg;

import com.Very.very.Customize.Customize;
import com.Very.very.Events.FightEvents.MonsterAttackEvent;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.SoundsPackets.SoundsS2CPacket;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemTier;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class YxwgBow extends PickaxeItem {
    private final double BaseDamage = Customize.AttackDamage;
    private final double DefencePenetration0 = Customize.DefencePenetration0;
    private final double CriticalHitRate = 0.25;
    private final double CHitDamage = 4;
    private final double SpeedUp = 0.6F;
    public YxwgBow(Properties p_40524_) {
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
        Compute.DescriptionActive(components,Component.literal("幸运射击").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 下次射击将消耗").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("20%当前")).
                append(Component.literal("并触发").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("幸运效果").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLucky)).
                append(Component.literal("箭矢将获得400%基础攻击伤害。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 幸运效果").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLucky).
                append(Component.literal("将随机（不重复）赋予弓手以下增益效果:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 1.获得").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CritRate("15%")).
                append(Compute.AttributeDescription.CritDamage("250%")));
        components.add(Component.literal(" 2.使箭矢自动锁定敌人，并使敌人在0.5s内无法移动或造成伤害").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 3.使箭矢在命中目标时，为你提供等同于5%伤害值的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.HealthRecover("")));
        Compute.DescriptionPassive(components,Component.literal("生命共鸣").withStyle(ChatFormatting.RESET).withStyle(style));
        components.add(Component.literal(" 每处于战斗状态1分钟，获得").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("2%总")).
                append(Compute.AttributeDescription.CritDamage("8%")).
                append(Compute.AttributeDescription.DefencePenetration("100")).
                append(Compute.AttributeDescription.MovementSpeed("2%总")));
        components.add(Component.literal(" -至多叠加至20层，当你1分钟未处于战斗状态，会失去1层").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 是生命的顽强，还是幸运的眷顾").withStyle(ChatFormatting.RESET).withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作生命之息长弓 - 幸运之矢，授予对维瑞阿契做出了杰出贡献的 - yxwg").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.BowAttack(player,Utils.BowNumMap.get(StringUtils.BowNameString.YxwgBow));
        return super.use(level, player, interactionHand);
    }

    public static boolean ActiveFlag = false;
    public static int CritRateUpTick = 0;
    public static int HealthStealTick = 0;
    public static int ManaArrowTick = 0;

    public static int PassiveCount = 0;
    public static int LastBattleTick = 0;

    public static int ActiveTick = 0;
    public static int CurrentMode = -1;

    public static boolean IsOn(Player player) {
        return player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.YxwgBow.get());
    }

    public static void Active(Player player) {
        if (!IsOn(player)) return;
        int TickCount = player.getServer().getTickCount();
        if (TickCount > ActiveTick) {
            ActiveFlag = true;
            ActiveTick = TickCount + 200;
            Compute.CoolDownTimeSend(player,ModItems.YxwgBow.get().getDefaultInstance(),200);
            ModNetworking.sendToClient(new SoundsS2CPacket(6),(ServerPlayer) player);
        }
    }

    public static void Tick(Player player) {
        if (!IsOn(player)) return;
        int TickCount = player.getServer().getTickCount();
        if (TickCount % 1200 == 0) {
            if (TickCount < LastBattleTick + 1200) PassiveCount = Math.min(YxwgCurios2.IsPlayer(player) ? 40 : 20,PassiveCount + (YxwgCurios2.IsPlayer(player) ? 2 : 1));
            else PassiveCount = Math.max(0,PassiveCount - 1);
            Compute.EffectLastTimeSend(player, ModItems.YxwgBow.get().getDefaultInstance(),8888,PassiveCount,true);
        }
    }

    public static double ExDamage(Player player) {
        if (!IsOn(player)) return 0;
        return 0.02 * PassiveCount;
    }

    public static double ExDefencePenetration0(Player player) {
        if (!IsOn(player)) return 0;
        return 100 * PassiveCount;
    }

    public static double ExMovementSpeed(Player player) {
        if (!IsOn(player)) return 0;
        return 0.02 * PassiveCount;
    }

    public static double ExCritDamage(Player player) {
        if (!IsOn(player)) return 0;
        return 0.08 * PassiveCount;
    }

    public static double CritRateUp(Player player) {
        if (!IsOn(player)) return 0;
        int TickCount = player.getServer().getTickCount();
        if (CritRateUpTick > TickCount) return 0.15;
        return 0;
    }



    public static double CritDamageUp(Player player) {
        if (!IsOn(player)) return 0;
        int TickCount = player.getServer().getTickCount();
        if (CritRateUpTick > TickCount) return 2.5;
        return 0;
    }

    public static void EffectProvider(Player player, Mob mob) {
        if (!IsOn(player)) return ;
        int TickCount = player.getServer().getTickCount();
        if (ManaArrowTick > TickCount) {
            MonsterAttackEvent.addMobToLimitMap(mob,10);
            Compute.AddSlowDownEffect(mob,10,99);
        }
    }

    public static void HealthRecover(Player player, double damage) {
        if (!IsOn(player)) return;
        int TickCount = player.getServer().getTickCount();
        if (HealthStealTick > TickCount) Compute.PlayerHeal(player,damage * 0.05);
    }

    public static boolean IsManaArrow(Player player) {
        if (!IsOn(player)) return false;
        int TickCount = player.getServer().getTickCount();
        return ManaArrowTick > TickCount;
    }

    public static boolean ActiveEffect(Player player) {
        Random random = new Random();
        int TickCount = player.getServer().getTickCount();
        player.setHealth(player.getHealth() * 0.8f);
        ActiveFlag = false;
        int nextMode;
        do {
            nextMode = random.nextInt(3);
        } while(nextMode == CurrentMode);
        switch (nextMode) {
            case 0 -> {
                CurrentMode = 0;
                CritRateUpTick = TickCount + 200;
                Compute.EffectLastTimeSend(player,ModItems.YxwgCurios.get().getDefaultInstance(),200,1,true);
            }
            case 1 -> {
                CurrentMode = 1;
                ManaArrowTick = TickCount + 200;
                Compute.EffectLastTimeSend(player,ModItems.YxwgCurios.get().getDefaultInstance(),200,2,true);
                return true;
            }
            case 2 -> {
                CurrentMode = 2;
                HealthStealTick = TickCount + 200;
                Compute.EffectLastTimeSend(player,ModItems.YxwgCurios.get().getDefaultInstance(),200,3,true);
            }
        }
        return false;
    }

    public static void BattleTick(Player player) {
        LastBattleTick = player.getServer().getTickCount();
    }
}

package com.very.wraq.customized.players.bow.Yxwg;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class YxwgCurios2 extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public YxwgCurios2(Properties p_41383_) {
        super(p_41383_);
        Utils.AttackDamage.put(this, Attributes.AttackDamage);
        Utils.DefencePenetration0.put(this,Attributes.DefencePenetration0);
        Utils.CritDamage.put(this,Attributes.CritDamage);
        Utils.Defence.put(this,Attributes.Defence);
        Utils.CritRate.put(this,Attributes.CritRate);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfHealth;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("七宗罪").withStyle(style));
        components.add(Component.literal(" 佩戴此饰品的角色将受到").withStyle(ChatFormatting.WHITE).
                append(Component.literal("七宗罪之力").withStyle(style)).
                append(Component.literal("的庇护，每个").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("宗罪").withStyle(style)).
                append(Component.literal("都将赋予角色独特的增益效果").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 贪婪之力:").withStyle(ChatFormatting.RED).
                append(Component.literal("击败敌人后，提升").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("25%总")).
                append(Compute.AttributeDescription.CritRate("25%")).
                append(Compute.AttributeDescription.CritDamage("125%")).
                append(Component.literal(" 持续10s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 暴怒之威:").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("角色受到伤害后，获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("50%总")).
                append(Compute.AttributeDescription.CritDamage("100%")).
                append(Component.literal(" 持续5s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 懒惰之福:").withStyle(CustomStyle.styleOfHealth).
                append(Component.literal("角色静止不动5秒后，会以每秒回复").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("2%")));
        components.add(Component.literal(" 嫉妒之眼:").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("角色有10%的几率").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("闪避").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("敌人的攻击").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 傲慢之盾:").withStyle(ChatFormatting.GRAY).
                append(Component.literal("角色受到的伤害减少").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("20%").withStyle(CustomStyle.styleOfFlexible)));
        components.add(Component.literal(" 暴食之体:").withStyle(CustomStyle.styleOfMoon).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("消失时，转化为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("10000%").withStyle(style)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)).
                append(Component.literal("的效率回敬周围敌人").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 色欲之魅:").withStyle(CustomStyle.styleOfSakura).
                append(Component.literal("箭矢命中时对目标周围敌人进行小范围的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("吸引").withStyle(style)).
                append(Component.literal("，每吸引一个敌人回复").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("1%")));
        Compute.DescriptionPassive(components,Component.literal("无罪").withStyle(style));
        components.add(Component.literal(" 持有").withStyle(ChatFormatting.WHITE).
                append(Component.literal("生命之息长弓 - 幸运之矢").withStyle(CustomStyle.styleOfHealth)).
                append(Component.literal("时，将会使其被动层数上限提升至").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("41层").withStyle(CustomStyle.styleOfHealth)).
                append(Component.literal("，并且叠层效率翻倍").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 我们还是最好的兄弟，这是给你的生日礼物——永别了").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚七宗罪，授予对维瑞阿契做出了杰出贡献的 - yxwg").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = (Player) slotContext.entity();
        IsOn = true;
        Compute.AddCuriosToList(player,stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = null;
        IsOn = false;
        Compute.RemoveCuriosInList(player,stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsPlayer(Player player) {
        return Player != null && Player.equals(player) && IsOn;
    }

    public static void Tick(Player player) {
        Passive3Tick(player);
    }

    // 被动1
    public static int Passive1LastTick = 0;
    public static void Passive1(Player player) {
        if (!IsPlayer(player)) return;
        int tickCount = player.getServer().getTickCount();
        Passive1LastTick = tickCount + 200;
        Compute.EffectLastTimeSend(player, ModItems.YxwgCurios2Passive1.get().getDefaultInstance(),200);
    }

    public static double Passive1AttackDamageUp(Player player) {
        if (!IsPlayer(player)) return 1;
        if (Passive1LastTick > player.getServer().getTickCount()) return 1.25;
        return 1;
    }

    public static double Passive1ExCritRate(Player player) {
        if (!IsPlayer(player)) return 0;
        if (Passive1LastTick > player.getServer().getTickCount()) return 0.25;
        return 0;
    }

    public static double Passive1ExCritDamage(Player player) {
        if (!IsPlayer(player)) return 0;
        if (Passive1LastTick > player.getServer().getTickCount()) return 1.25;
        return 0;
    }

    // 被动2
    public static int Passive2LastTick = 0;
    public static void Passive2(Player player) {
        if (!IsPlayer(player)) return;
        Passive2LastTick = player.getServer().getTickCount() + 100;
        Compute.EffectLastTimeSend(player,ModItems.YxwgCurios2Passive2.get().getDefaultInstance(),100);
    }

    public static double Passive2AttackDamageUp(Player player) {
        if (!IsPlayer(player)) return 1;
        if (Passive2LastTick > player.getServer().getTickCount()) return 1.5;
        return 1;
    }

    public static double Passive2ExCritDamage(Player player) {
        if (!IsPlayer(player)) return 0;
        if (Passive1LastTick > player.getServer().getTickCount()) return 1;
        return 0;
    }

    // 被动3
    public static int IdleSeconds = 0;
    public static Vec3 LastSecondPos;
    public static void Passive3Tick(Player player) {
        if (!IsPlayer(player)) return;
        if (player.tickCount % 20 == 0) {
            if (LastSecondPos == null || LastSecondPos != player.position()) {
                LastSecondPos = player.position();
                IdleSeconds = 0;
            }
            else {
                IdleSeconds = Math.min(5,IdleSeconds + 1);
            }
            Compute.EffectLastTimeSend(player,ModItems.YxwgCurios2Passive3.get().getDefaultInstance(),8888,IdleSeconds,true);
        }
    }

    // 被动4
    public static double Passive4DodgeRate(Player player) {
        if (IsPlayer(player)) return 0.1;
        return 0;
    }

    // 被动5
    public static double Passive5DamageDecrease(Player player) {
        if (IsPlayer(player)) return 0.2;
        return 0;
    }

    // 被动6
    public static void Passive6(Player player, double shieldValue) {
        if (!IsPlayer(player)) return;
        List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),15,15,15));
        mobList.removeIf(mob -> mob.distanceTo(player) > 6);
        mobList.forEach(mob -> {
            Compute.Damage.DamageIgNoreDefenceToMonster(player,mob,shieldValue * 100);
        });
        ParticleProvider.DisperseParticle(player.position(), (ServerLevel) player.level(), 1, 1, 120, ParticleTypes.FIREWORK, 1);
    }

    // 被动7
    public static void Passive7Gather(Player player, Mob monster) {
        if (!IsPlayer(player)) return;
        List<Mob> mobList = monster.level().getEntitiesOfClass(Mob.class,AABB.ofSize(monster.position(),15,15,15));
        mobList.removeIf(mob -> mob.distanceTo(monster) > 6);
        mobList.forEach(mob -> {
            Compute.PlayerHeal(player,player.getMaxHealth() * 0.01);
            Compute.MonsterGatherProvider(mob,1,monster.position());
        });
    }






















}

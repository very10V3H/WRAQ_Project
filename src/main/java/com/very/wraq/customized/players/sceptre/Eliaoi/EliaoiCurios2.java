package com.very.wraq.customized.players.sceptre.Eliaoi;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Struct.PlayerTeam;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EliaoiCurios2 extends Item implements ICurioItem {

    public EliaoiCurios2(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, Attributes.ManaDamage);
        Utils.Defence.put(this,Attributes.Defence);
        Utils.ManaPenetration0.put(this, Attributes.ManaPenetration0);
        Utils.ManaRecover.put(this, Attributes.ManaRecover);
        Utils.MaxMana.put(this, Attributes.MaxMana);
        Utils.CoolDownDecrease.put(this, Attributes.CoolDown);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfMana;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("七重纱").withStyle(style));
        components.add(Component.literal(" 进入战斗时，进入").withStyle(ChatFormatting.WHITE).
                append(Component.literal("【序曲】").withStyle(style)).
                append(Component.literal("状态，对").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("燃烧状态").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("的敌人造成伤害时会施加").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("【燃烧】").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，并积累等层数的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("【热度】").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 【热度】").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("达到15层时，").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("【序曲】").withStyle(style)).
                append(Component.literal("进阶为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("【幕间曲】").withStyle(style)));
        components.add(Component.literal(" 【幕间曲】:").withStyle(style).
                append(Component.literal("赋予全队").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("【预燃】").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("状态。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("【预燃】:").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("对敌人造成伤害时会施加").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("【燃烧】").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，造成持续2s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("0.5倍").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("魔法伤害").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" 【热度】").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("达40层时，").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("【幕间曲】").withStyle(style)).
                append(Component.literal("进阶为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("【终曲】").withStyle(style)));
        components.add(Component.literal("【终曲】:").withStyle(style).
                append(Component.literal("处于该状态下时，赋予全队").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("【预燃】和【爆发力】").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("状态。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("【爆发力】:").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%伤害提升").withStyle(CustomStyle.styleOfPower)));
        Compute.DescriptionPassive(components,Component.literal("盘旋的哼唱").withStyle(style));
        components.add(Component.literal("【间奏曲】:").withStyle(style).
                append(Component.literal("你会对任意").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("【燃烧】").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("层数>=10的目标造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue("1000%")).
                append(Component.literal("，每释放三次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("【间奏曲】").withStyle(style)).
                append(Component.literal("会使下次释放造成").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue("3000%")));
        components.add(Component.literal(" 拥有40层").withStyle(ChatFormatting.WHITE).
                append(Component.literal("【热度】").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("时，基于已损失的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("")).
                append(Component.literal("提供至多").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("50%总")));
        components.add(Component.literal(" 步音与歌声，在舞台上兜转，台下无处落脚。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚四幕之内的死亡，授予对维瑞阿契做出了杰出贡献的 - Eliaoi").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        On = true;
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        On = false;
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static Player onPlayer;
    public static boolean On;

    public static boolean IsOn(Player player) {
        return onPlayer != null && onPlayer.equals(player) && On;
    }

    public static int count = 0; // 热度
    public static int countLastTick = 0;
    public static void CountAdd(Player player, Mob mob) {
        if (!IsOn(player)) return;
        if (countLastTick < player.getServer().getTickCount()) count = 0;
        Compute.IgniteMob(player,mob,40);
        if (mob.isOnFire()) count = Math.min(40,count + 1);

        countLastTick = player.getServer().getTickCount() + 100;
        Compute.EffectLastTimeSend(player, ModItems.EliaoiCurios2.get().getDefaultInstance(),100,count);
        mobCountsMap.put(mob,mobCountsMap.getOrDefault(mob,0) + 1);
        MobCountsDamage(mob);
    }

    public static void Tick(Player player) {
        if (player.tickCount % 20 == 0) GroupBuffProvider(player);
    }

    public static Map<Player,Integer> groupBuff1 = new HashMap<>();
    public static Map<Player,Integer> groupBuff2 = new HashMap<>();
    public static Map<Mob,Integer> mobCountsMap = new HashMap<>();
    public static void GroupBuffProvider(Player player) {
        if (!IsOn(player)) return;
        if (countLastTick > player.getServer().getTickCount() && count > 15 && Utils.playerTeamMap.containsKey(player)) {
            PlayerTeam playerTeam = Utils.playerTeamMap.get(player);
            List<Player> players = playerTeam.getPlayerList();
            players.forEach(player1 -> {
                groupBuff1.put(player1,player1.getServer().getTickCount() + 100);
                Compute.EffectLastTimeSend(player,ModItems.EliaoiCurios2.get().getDefaultInstance(),100);
                if (count == 40) groupBuff2.put(player1,player1.getServer().getTickCount() + 100);
            });
        }

        // fixed 24.4.17 仅在队伍中生效
        if (countLastTick > player.getServer().getTickCount() && count > 15 && !Utils.playerTeamMap.containsKey(player)) {
            groupBuff1.put(player,player.getServer().getTickCount() + 100);
            Compute.EffectLastTimeSend(player,ModItems.EliaoiCurios2.get().getDefaultInstance(),100);
            if (count == 40) groupBuff2.put(player,player.getServer().getTickCount() + 100);
        }
    }

    public static void GroupBuff1Ignite(Player player, Mob mob) {
        if (groupBuff1.containsKey(player) && groupBuff1.get(player) > player.getServer().getTickCount()) {
            Compute.IgniteMob(player,mob,40);
            Compute.Damage.LastXpStrengthDamageToMob(player,mob,0.5,40,10,false);
            mobCountsMap.put(mob,mobCountsMap.getOrDefault(mob,0) + 1);
            MobCountsDamage(mob);
        }
    }

    public static double GroupBuff2DamageEnhance(Player player) {
        if (groupBuff2.containsKey(player) && groupBuff2.get(player) > player.getServer().getTickCount()) {
            return 0.5;
        }
        return 0;
    }

    public static int DamageReleaseTimes = 0;
    public static void MobCountsDamage(Mob mob) {
        if (onPlayer != null && On) {
            if (mobCountsMap.containsKey(mob) && mobCountsMap.get(mob) >= 10) {
                mobCountsMap.put(mob,0);
                Compute.Damage.ManaDamageToMonster_RateApDamage(onPlayer,mob,DamageReleaseTimes % 3 == 0 ? 30 : 10,false);
                DamageReleaseTimes ++;
            }
        }
    }

    public static double ManaDamageEnhance(Player player) {
        if (!IsOn(player)) return 1;
        if (count >= 40 && countLastTick > player.getServer().getTickCount()) {
            return 1 + ((player.getMaxHealth() - player.getHealth()) * 0.5 / player.getMaxHealth());
        }
        return 1;
    }

}

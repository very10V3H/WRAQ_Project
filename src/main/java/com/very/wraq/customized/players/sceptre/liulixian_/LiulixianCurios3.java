package com.very.wraq.customized.players.sceptre.liulixian_;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiulixianCurios3 extends Item implements ICurioItem {

    public LiulixianCurios3(Properties p_41383_) {
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
        Style style = CustomStyle.styleOfSakura;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("九宝有名").withStyle(style));
        components.add(Component.literal(" 使装备者造成的总伤害").withStyle(ChatFormatting.WHITE).
                append(Component.literal("减少50%").withStyle(ChatFormatting.RED)).
                append(Component.literal("，并使周围半径10格内的玩家:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 一曰 ").withStyle(style).
                append(Component.literal("力:").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%总伤害加成").withStyle(ChatFormatting.RED)));

        components.add(Component.literal(" 二曰 ").withStyle(style).
                append(Component.literal("速:").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%总移速加成").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%总敏捷").withStyle(CustomStyle.styleOfFlexible)));

        components.add(Component.literal(" 三曰 ").withStyle(style).
                append(Component.literal("魂:").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("25%总")));

        components.add(Component.literal(" 四曰 ").withStyle(style).
                append(Component.literal("防:").withStyle(CustomStyle.styleOfMine)).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%受到伤害削减").withStyle(ChatFormatting.GREEN)));

        components.add(Component.literal(" 五曰 ").withStyle(style).
                append(Component.literal("攻:").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("自适应获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("25%总")).
                append(Component.literal("或").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("25%总")));

        components.add(Component.literal(" 六曰 ").withStyle(style).
                append(Component.literal("增:").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%伤害提升").withStyle(CustomStyle.styleOfPower)));

        components.add(Component.literal(" 七曰 ").withStyle(style).
                append(Component.literal("九宝真身:").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("技能与法术").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("不再消耗").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("")));

        components.add(Component.literal(" 八曰 ").withStyle(style).
                append(Component.literal("九宝神光护体:").withStyle(CustomStyle.styleOfSakura)).
                append(Component.literal("自动净化负面buff").withStyle(ChatFormatting.WHITE)));

        components.add(Component.literal(" 九曰 ").withStyle(style).
                append(Component.literal("九宝无敌神光:").withStyle(CustomStyle.styleOfMoon)).
                append(Component.literal("濒死后回到").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxHealth("25%")));
        components.add(Component.literal(" -这个效果有25s的个人冷却").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" 琉璃宝塔塔琉璃").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚『九宝琉璃塔』，授予对维瑞阿契做出了杰出贡献的 - liulixian_").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    public static Player onPlayer;
    public static boolean On;

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        On = true;
        Compute.AddCuriosToList(onPlayer,stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        onPlayer = (Player) slotContext.entity();
        On = false;
        Compute.RemoveCuriosInList(onPlayer,stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);

    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return onPlayer != null && onPlayer.equals(player) && On;
    }

    public static Map<Player,Integer> passiveTickMap = new HashMap<>();
    public static void Tick(Player player) {
        EffectClear(player);
        if (!IsOn(player)) return;
        if (player.tickCount % 40 == 0) {
            List<Player> playerList = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(),30,30,30));
            playerList.removeIf(player1 -> player1.distanceTo(player) > 10);
            playerList.forEach(player1 -> {
                passiveTickMap.put(player1,player.getServer().getTickCount() + 100);
                Compute.EffectLastTimeSend(player, ModItems.LiulixianCurios3.get().getDefaultInstance(),100);
            });
        }
    }

    public static boolean PlayerIsUnderProtect(Player player) {
        return passiveTickMap.containsKey(player) && passiveTickMap.get(player) > player.getServer().getTickCount();
    }

    public static double SelfDamageDecrease(Player player) {
        if (IsOn(player)) return - 0.5;
        return 0;
    }

    public static double FinalDamageEnhance(Player player) {
        if (PlayerIsUnderProtect(player)) return 0.25;
        return 0;
    }

    public static double MovementEnhance(Player player) {
        if (PlayerIsUnderProtect(player)) return 1.25;
        return 1;
    }

    public static double SwiftnessEnhance(Player player) {
        if (PlayerIsUnderProtect(player)) return 1.25;
        return 1;
    }

    public static double MaxManaUp(Player player) {
        if (PlayerIsUnderProtect(player)) return 1.25;
        return 1;
    }

    public static double DamageDecrease(Player player) {
        if (PlayerIsUnderProtect(player)) return 0.25;
        return 0;
    }

    public static double ExAttackDamageUp(Player player) {
        if (PlayerIsUnderProtect(player)) {
            if (Utils.SwordTag.containsKey(player.getMainHandItem().getItem())
                    || Utils.BowTag.containsKey(player.getMainHandItem().getItem())) return 1.25;
        }
        return 1;
    }

    public static double ExManaDamageUp(Player player) {
        if (PlayerIsUnderProtect(player)) {
            if (Utils.SceptreTag.containsKey(player.getMainHandItem().getItem())) return 1.25;
        }
        return 1;
    }

    public static double DamageEnhance(Player player) {
        if (!PlayerIsUnderProtect(player)) return 0;
        return 0.25;
    }

    public static boolean ManaCost(Player player) {
        return PlayerIsUnderProtect(player);
    }

    public static void EffectClear(Player player) {
        if (PlayerIsUnderProtect(player)) {
            player.removeAllEffects();
        }
    }

    public static Map<Player,Integer> passive9CdMap = new HashMap<>();
    public static boolean Rescue(Player player) {
        if (PlayerIsUnderProtect(player)) {
            if (!passive9CdMap.containsKey(player) || passive9CdMap.get(player) < player.getServer().getTickCount()) {
                passive9CdMap.put(player,player.getServer().getTickCount() + 500);
                player.setHealth((float) (player.getMaxHealth() * 0.25));
                Compute.CoolDownTimeSend(player,ModItems.LiulixianCurios3.get().getDefaultInstance(),500);
                return true;
            }
        }
        return false;
    }

}

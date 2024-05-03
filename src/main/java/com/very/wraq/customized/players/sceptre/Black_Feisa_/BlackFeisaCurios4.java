package com.very.wraq.customized.players.sceptre.Black_Feisa_;

import com.very.wraq.customized.players.bow.Shao_Feng.ShaoFengCurios;
import com.very.wraq.customized.players.bow.littleart.LittleartCurios;
import com.very.wraq.customized.players.sceptre.liulixian_.LiulixianCurios2;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.customized.PlayerFlyingSpeedSetS2CPacket;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
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

public class BlackFeisaCurios4 extends Item implements ICurioItem {

    public static Player Player;
    public static boolean IsOn;

    public BlackFeisaCurios4(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, 1728d);
        Utils.Defence.put(this, 300d);
        Utils.ManaPenetration0.put(this, 150d);
        Utils.ManaRecover.put(this, 30d);
        Utils.MaxMana.put(this, 100d);
        Utils.CoolDownDecrease.put(this, 0.3);
        Utils.CustomizedList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfMoon;
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("挑你虾线").withStyle(style));
        components.add(Component.literal(" 持有者").withStyle(ChatFormatting.WHITE).
                append(Component.literal("受到伤害翻倍").withStyle(ChatFormatting.RED)).
                append(Component.literal("，并且失去").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("50%额外")).
                append(Component.literal("，使半径7格内的玩家获得:").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 一挑:").withStyle(style).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("25%总")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("25%总")));
        components.add(Component.literal(" 二挑:").withStyle(style).
                append(Component.literal("基于装备者的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaRecover("")).
                append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ExpUp("")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MovementSpeed("")));
        components.add(Component.literal(" 三挑:").withStyle(style).
                append(Component.literal("净化负面效果").withStyle(CustomStyle.styleOfWater)));
        components.add(Component.literal(" 四挑").withStyle(style).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%总伤害加成").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 五挑:").withStyle(style).
                append(Component.literal("基于已损失的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("至多获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%伤害提升").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("40%双穿").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" 六挑:").withStyle(style).
                append(Component.literal("获得").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("飞行能力").withStyle(CustomStyle.styleOfSakura)));
        components.add(Component.literal(" 七挑:").withStyle(style).
                append(Component.literal("法术与技能将不再消耗").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("")));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚宇将军，授予对维瑞阿契做出了杰出贡献的 - Black_FeiSa_").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = (Player) slotContext.entity();
        IsOn = true;
        Compute.AddCuriosToList(player, stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Player = null;
        IsOn = false;
        Compute.RemoveCuriosInList(player, stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsPlayer(Player player) {
        return Player != null && Player.equals(player) && IsOn;
    }

    public static Map<Player,Integer> passiveTickMap = new HashMap<>();

    public static void Tick(Player player) {
        if (!IsPlayer(player)) return;
        if (player.tickCount % 40 == 0) {
            List<Player> playerList = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(),30,30,30));
            playerList.removeIf(player1 -> player1.distanceTo(player) > 7);
            playerList.forEach(player1 -> {
                passiveTickMap.put(player1,player.getServer().getTickCount() + 100);
                Compute.EffectLastTimeSend(player, ModItems.BlackFeisaCurios5.get().getDefaultInstance(),100);
            });
        }
    }


    public static boolean PlayerIsUnderProtect(Player player) {
        return passiveTickMap.containsKey(player) && passiveTickMap.get(player) > player.getServer().getTickCount() && Player != null;
    }

    public static double AttackDamageUp(Player player) {
        if (!PlayerIsUnderProtect(player)) return 1;
        return 1.25;
    }

    public static double ExpUp(Player player) {
        if (!PlayerIsUnderProtect(player)) return 0;
        return Compute.PlayerMaxManaNum(Player) * 0.25 / 100;
    }

    public static double MovementSpeed(Player player) {
        if (!PlayerIsUnderProtect(player)) return 0;
        return PlayerAttributes.PlayerManaRecover(player) * 0.25 / 100;
    }

    public static void ClearEffect(Player player) {
        if (!PlayerIsUnderProtect(player)) return;
        player.removeAllEffects();
    }

    public static double DamageEnhance(Player player) {
        if (!PlayerIsUnderProtect(player)) return 0;
        return 0.5;
    }

    public static double Penetration(Player player) {
        if (!PlayerIsUnderProtect(player)) return 0;
        return 0.4 * (1 - player.getHealth() / player.getMaxHealth());
    }

    public static double DamageEnhance1(Player player) {
        if (!PlayerIsUnderProtect(player)) return 0;
        return 1 * (1 - player.getHealth() / player.getMaxHealth());
    }

    public static void FlyingAndClearTick(Player player) {
        ClearEffect(player);
        if (player.tickCount % 50 == 0 && !player.isCreative()) {
            if (!PlayerIsUnderProtect(player) && !LiulixianCurios2.IsOn(player) && !ShaoFengCurios.isOn(player) && !LittleartCurios.isOn(player))
                ModNetworking.sendToClient(new PlayerFlyingSpeedSetS2CPacket(0), (ServerPlayer) player);
            else ModNetworking.sendToClient(new PlayerFlyingSpeedSetS2CPacket((PlayerAttributes.PlayerMovementSpeed(player) + 1) * 0.015), (ServerPlayer) player);
        }
    }
}

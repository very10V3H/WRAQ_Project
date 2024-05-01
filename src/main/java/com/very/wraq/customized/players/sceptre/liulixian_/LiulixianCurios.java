package com.very.wraq.customized.players.sceptre.liulixian_;

import com.very.wraq.customized.uniform.Attributes;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class LiulixianCurios extends Item implements ICurioItem {

    public LiulixianCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this, Attributes.ManaDamage);
        Utils.Defence.put(this, Attributes.Defence);
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
        Compute.DescriptionPassive(components,Component.literal("琉璃华曜").withStyle(style));
        components.add(Component.literal(" 以祂为中心，展开琉璃壁").withStyle(style));
        components.add(Component.literal(" 琉璃壁").withStyle(style).
                append(Component.literal("会依据祂所持武器施加不同的效果：").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 当祂持有『雾切丶琉』时：").withStyle(style));
        components.add(Component.literal(" 琉璃壁").withStyle(style).
                append(Component.literal("切换为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("杀戮").withStyle(ChatFormatting.RED)).
                append(Component.literal("形态：").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 琉璃壁").withStyle(style).
                append(Component.literal("会吞噬信徒们的部分生命力为祂所用。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 当祂持有『雾切丶琉』时：").withStyle(style));
        components.add(Component.literal(" 琉璃壁").withStyle(style).
                append(Component.literal("切换为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("守护").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("形态：").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 祂会利用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("琉璃壁").withStyle(style)).
                append(Component.literal("守护其中的信徒们，并使他们获得永生?").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" ......祂是谁?").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal(" 崇高的敬意化作一枚『璃灯华曜』，授予对维瑞阿契做出了杰出贡献的 - liulixian_").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Utils.LiuLiXian = (Player) slotContext.entity();
        Utils.LiuLiXianCore = true;
        ((Player) slotContext.entity()).getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0.5D);
        Compute.AddCuriosToList((Player) slotContext.entity(),stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Utils.LiuLiXian = null;
        Utils.LiuLiXianCore = false;
        ((Player) slotContext.entity()).getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0.0D);
        Compute.RemoveCuriosInList((Player) slotContext.entity(),stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return Utils.LiuLiXian != null && Utils.LiuLiXian.equals(player) && Utils.LiuLiXianCore;
    }

    public static int playerNum = 0;
    public static double manaDamageEnhance = 0;

    public static List<Player> protectPlayer = new ArrayList<>();

    public static double protectPlayer(Player player, double damage) {
        if (protectPlayer.contains(player) && Utils.LiuLiXian != null) {
            Utils.LiuLiXian.setHealth((float) (Utils.LiuLiXian.getHealth() - damage * 0.22));
            return 0.66;
        }
        return 1;
    }

    public static void Tick(Player player) {
        if (!IsOn(player)) return;
        Passive(player);
        Compute.BallParticle((ServerPlayer) player, player.position(),9, ModParticles.LiuliSpell.get(), 50);
    }

    public static double ManaDamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        return manaDamageEnhance;
    }

    public static void Passive(Player player) {
        if (IsOn(player) && player.tickCount % 20 == 0) {
            playerNum = 0;
            manaDamageEnhance = 0;
            List<Player> playerList = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(),25,25,25));
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get())) {
                playerList.removeIf(player1 -> player1.distanceTo(player) > 9 || player1.getHealth() / player1.getMaxHealth() < 0.5);
                playerList.forEach(player1 -> {
                    Compute.PlayerHealthDecrease(player1,player1.getMaxHealth() * 0.25,0.5);
                    manaDamageEnhance += player1.getMaxHealth() * 0.5;
                });
                playerNum = playerList.size();
            }
            else {
                playerList.removeIf(player1 -> player1.distanceTo(player) > 9);
                playerNum = playerList.size();
                playerList.forEach(player1 -> {
                    Compute.PlayerHeal(player1,player.getMaxHealth() * 0.05);
                });
                protectPlayer.clear();
                protectPlayer.addAll(playerList);
            }
        }
    }

    public static double FirstAttack(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get())) {
            return PlayerAttributes.PlayerManaDamage(player) * (1 + playerNum * 0.25) + Compute.XpStrengthAPDamage(player,0.25 + playerNum * 0.25);
        }
        else {
            return PlayerAttributes.PlayerManaDamage(player) * (1 + playerNum * 0.25);
        }
    }

    public static double SecondAttack(Player player, Mob mob) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get())) {
            Compute.Damage.LastXpStrengthDamageToMob(player,mob,1,40,10,false);
            return PlayerAttributes.PlayerManaDamage(player) * (1 + playerNum * 0.25);
        }
        else {
            List<Player> playerList = player.level().getEntitiesOfClass(Player.class, AABB.ofSize(player.position(),25,25,25));
            playerList.removeIf(player1 -> player1.distanceTo(player) > 9);
            playerList.forEach(player1 -> {
                Compute.PlayerShieldProvider(player1,60,PlayerAttributes.PlayerManaDamage(player) * (0.05 + 0.05 * playerList.size()));
            });
            return PlayerAttributes.PlayerManaDamage(player) * (1 + playerNum * 0.25);
        }
    }
}

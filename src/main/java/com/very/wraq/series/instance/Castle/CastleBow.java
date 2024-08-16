package com.very.wraq.series.instance.Castle;

import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.ForgeItem;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CastleBow extends WraqBow implements ForgeItem, ActiveItem {
    public CastleBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 1500d);
        Utils.defencePenetration0.put(this, 3600d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.35);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfCastle;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("暗影之刃").withStyle(style));
        components.add(Component.literal(" 你的箭矢攻击将使敌人被拖入暗影之中").withStyle(ChatFormatting.ITALIC).withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).append(Component.literal("箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).append(Component.literal("将附带造成伤害100%的").withStyle(ChatFormatting.WHITE)).append(Compute.AttributeDescription.ManaDamageValue("")));
        Compute.DescriptionActive(components, Component.literal("噬魔注能").withStyle(style));
        components.add(Component.literal(" 扣除自身").withStyle(ChatFormatting.WHITE).append(Compute.AttributeDescription.Health("15%当前")).append(Component.literal("，获得持续6s的").withStyle(ChatFormatting.WHITE)).append(Component.literal("25%伤害提升").withStyle(CustomStyle.styleOfPower)).append(Component.literal("以及").withStyle(ChatFormatting.WHITE)).append(Compute.AttributeDescription.DefencePenetration("1500")).append(Component.literal("与").withStyle(ChatFormatting.WHITE)).append(Compute.AttributeDescription.ManaPenetration("1500")));
        Compute.CoolDownTimeDescription(components, 15);
        components.add(Component.literal(" 多件暗黑武器的主动将会刷新持续时间，但效果将不会叠加，且共享冷却时间").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfCastle();
    }

    public static void NormalAttack(Player player, Mob mob, double damage) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CastleBow.get())) {
            Compute.Damage.ManaDamageToMonster_ApDamage_Direct(player, mob, damage, true);
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.CastleBowPiece.get(), 12));
            add(new ItemStack(ModItems.CastlePiece.get(), 192));
            add(new ItemStack(ModItems.BeaconRune.get(), 8));
            add(new ItemStack(ModItems.completeGem.get(), 26));
            add(new ItemStack(ModItems.ReputationMedal.get(), 104));
            add(new ItemStack(ModItems.RefiningGold.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 6));
        }};
    }

    @Override
    public void active(Player player) {
        Compute.playerItemCoolDown(player, this, 20);
        Compute.sendEffectLastTime(player, this, 120);
        CastleSword.CastleWeaponActiveLastTick.put(player, player.getServer().getTickCount() + 120);
        Compute.PlayerHealthDecrease(player, player.getHealth() * 0.15, Component.literal(" 被暗黑魔能吞噬了。").withStyle(CustomStyle.styleOfCastle));
    }
}

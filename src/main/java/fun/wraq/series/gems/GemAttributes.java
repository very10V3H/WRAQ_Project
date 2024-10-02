package fun.wraq.series.gems;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GemAttributes {

    public static double getPlayerCurrentAllEquipGemsValue(Player player, Map<Item, Double> map) {
        double value = 0;
        List<ItemStack> equipList = new ArrayList<>(player.getInventory().armor.stream().toList());
        if (Utils.mainHandTag.containsKey(player.getMainHandItem().getItem())) equipList.add(player.getMainHandItem());
        for (ItemStack stack : equipList) {
            value += getGemsAttributeModifier(stack.getOrCreateTagElement(Utils.MOD_ID), map);
        }
        return value;
    }

    public static double getGemsAttributeModifier(ItemStack equip, Map<Item, Double> map) {
        return getGemsAttributeModifier(equip.getOrCreateTagElement(Utils.MOD_ID), map);
    }

    public static double getGemsAttributeModifier(CompoundTag data, Map<Item, Double> map) {
        double value = 0;
        for (int i = 1; i <= 3; i++) {
            if (data.contains("newGems" + i)) {
                String gemName = data.getString("newGems" + i);
                ItemStack gem = null;

                try {
                    gem = Compute.getItemFromString(gemName);
                } catch (CommandSyntaxException e) {
                    throw new RuntimeException(e);
                }

                value += map.getOrDefault(gem.getItem(), 0d);
            }
        }
        return value;
    }

    public static double gemsAttackDamage(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.attackDamage);
    }

    public static double getPercentAttackDamageEnhance(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.percentAttackDamageEnhance);
    }

    public static double gemsMovementSpeedUp(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.movementSpeedWithoutBattle);
    }

    public static double gemsManaDamage(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.manaDamage);
    }

    public static double getPercentManaDamageEnhance(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.percentManaDamageEnhance);
    }

    public static double gemsManaRecover(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.manaRecover);
    }

    public static double gemsHealRecover(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.healthRecover);
    }

    public static double gemsMaxHealth(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.maxHealth);
    }

    public static double getPercentMaxHealthEnhance(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.percentMaxHealthEnhance);
    }

    public static double gemsDefence(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.defence);
    }

    public static double getPercentDefenceEnhance(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.percentDefenceEnhance);
    }

    public static double gemsCoolDown(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.coolDownDecrease);
    }

    public static double gemsCritDamage(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.critDamage);
    }

    public static double gemsCritRate(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.critRate);
    }

    public static double gemsHealEffectUp(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.healEffectUp);
    }

    public static double gemsManaHealthSteal(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.manaHealthSteal);
    }

    public static double gemsDefencePenetration0(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.defencePenetration0);
    }

    public static double gemsManaPenetration0(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.manaPenetration0);
    }

    public static double gemsExpUp(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.expUp);
    }

    public static double gemsLuckyUp(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.luckyUp);
    }

    public static double gemsDefencePenetration(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.defencePenetration);
    }

    public static double gemsManaPenetration(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.manaPenetration);
    }

    public static double gemsHealthSteal(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.healthSteal);
    }

    public static double gemsManaDefence(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.manaDefence);
    }

    public static double getPercentManaDefenceEnhance(CompoundTag data) {
        return getGemsAttributeModifier(data, Utils.percentManaDefenceEnhance);
    }
}

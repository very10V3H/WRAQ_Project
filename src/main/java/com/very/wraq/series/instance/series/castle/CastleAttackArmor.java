package com.very.wraq.series.instance.series.castle;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.core.AttackEvent;
import com.very.wraq.projectiles.ForgeItem;
import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class CastleAttackArmor extends WraqArmor implements ForgeItem {
    private static final Style style = CustomStyle.styleOfCastle;

    public static Map<Integer, Double> attributeIndexValueMap = new HashMap<>() {{
        put(0, 6144d);
        put(1, 750d);
        put(2, 800d);
        put(3, 800d);
        put(4, 0.55);
    }};

    public CastleAttackArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 6144d);
        Utils.attackDamage.put(this, 750d);
        Utils.defence.put(this, 800d);
        Utils.manaDefence.put(this, 800d);
        Utils.critDamage.put(this, 0.55);
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.45);
            Utils.maxHealth.put(this, 3072d);
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfCastle;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("暗影打击").withStyle(style));
        components.add(Component.literal(" 进行").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通近战攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("后，0.2s后你会进行一次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("暗影打击").withStyle(style)));
        components.add(Component.literal(" 暗影打击").withStyle(style).
                append(Component.literal("被视为拥有15%基础伤害值的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通近战攻击").withStyle(CustomStyle.styleOfPower)));
        Compute.DescriptionPassive(components, Component.literal("灵魂痛击").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通近战攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("附带").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("2倍").withStyle(CustomStyle.styleOfSea)).
                append(Component.literal("等级强度").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" -多件暗影城堡防具能线性提升伤害值百分比/伤害值").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfCastle();
    }

    public static WeakHashMap<Player, Integer> playerDoubleAttackTick = new WeakHashMap<>();

    public static void Tick(Player player) {
        int ArmorCount = Compute.ArmorCount.CastleAttack(player);
        if (ArmorCount == 0) return;
        int TickCount = player.getServer().getTickCount();
        if (playerDoubleAttackTick.containsKey(player) && playerDoubleAttackTick.get(player) == TickCount) {
            ExAttack(player, ArmorCount * 0.15);
        }
    }

    public static void NormalAttack(Player player) {
        int ArmorCount = Compute.ArmorCount.CastleAttack(player);
        if (ArmorCount == 0) return;
        int TickCount = player.getServer().getTickCount();
        playerDoubleAttackTick.put(player, TickCount + 4);
    }

    public static void ExAttack(Player player, double rate) {
        AttackEvent.module(player, rate);
    }

    public static double ExIgnoreDefenceDamage(Player player) {
        int ArmorCount = Compute.ArmorCount.CastleAttack(player);
        if (ArmorCount == 0) return 0;
        return Compute.XpStrengthADDamage(player, 2) * ArmorCount;
    }

    public static String attributeType = "attributeType"; // 锁定类型
    public static String attributeRate = "attributeRate"; // 锁定倍率
    public static String attributeTimes = "attributeTimes";

    public static void ForgeArmor(ItemStack itemStack, boolean lockType, boolean lockRate) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Random random = new Random();
        data.putInt(attributeTimes, data.getInt(attributeTimes) + 1);
        if (!lockType) data.putInt(attributeType, random.nextInt(5));
        if (!lockRate) {

            if (data.getInt(attributeTimes) == 5) {
                data.putDouble(attributeRate, random.nextDouble(0.25, 0.75));
                if (random.nextDouble() < 0.2) {
                    data.putDouble(attributeRate, random.nextDouble(0.65, 1));

                } // 高品质
            } else if (data.getInt(attributeTimes) == 10) {
                data.putDouble(attributeRate, random.nextDouble(0.25, 0.75));
                if (random.nextDouble() < 0.4) {
                    data.putDouble(attributeRate, random.nextDouble(0.65, 1));
                }
            } else {
                data.putDouble(attributeRate, random.nextDouble(0.25, 0.75));
                if (random.nextDouble() < 0.05) {
                    data.putDouble(attributeRate, random.nextDouble(0.65, 1));
                }
            }

        }
        if (data.getInt(attributeTimes) == 10) data.putInt(attributeTimes, 0);
    }

    public static void ForgeAttributeDescription(List<Component> components, ItemStack itemStack) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Compute.DescriptionPassive(components, Component.literal("蕴魔注能：").withStyle(CustomStyle.styleOfCastleCrystal));
        if (!data.contains(attributeType)) {
            components.add(Component.literal(" 暂未注能..").withStyle(CustomStyle.styleOfCastleCrystal));
            return;
        }
        int type = data.getInt(attributeType);
        Component[] components1 = {
                Compute.AttributeDescription.MaxHealth(""),
                Compute.AttributeDescription.AttackDamage(""),
                Compute.AttributeDescription.Defence(""),
                Compute.AttributeDescription.ManaDefence(""),
                Compute.AttributeDescription.CritDamage("")
        };
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(components1[type]).
                append(Component.literal(" + ").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("" + String.format("%.2f%%", data.getDouble(attributeRate) * 100))));

        components.add(Component.literal(" 目前已注能的次数为:").withStyle(ChatFormatting.WHITE).
                append(Component.literal("" + data.getInt(attributeTimes)).withStyle(CustomStyle.styleOfCastleCrystal))); // forge times

    }

    public static String ExMaxHealth = "ExManHealth";
    public static String ExAttackDamage = "ExAttackDamage";
    public static String ExDefence = "ExDefence";
    public static String ExManaDefence = "ExManaDefence";
    public static String ExCritDamage = "ExCritDamage";

    public static Map<String, Integer> attributeNumMap = new HashMap<>() {{
        put(ExMaxHealth, 0);
        put(ExAttackDamage, 1);
        put(ExDefence, 2);
        put(ExManaDefence, 3);
        put(ExCritDamage, 4);
    }};

    public static double ExAttributeValue(Player player, String attributeName) {
        double value = 0;
        ItemStack[] itemStacks = {
                player.getItemBySlot(EquipmentSlot.HEAD),
                player.getItemBySlot(EquipmentSlot.CHEST),
                player.getItemBySlot(EquipmentSlot.LEGS),
                player.getItemBySlot(EquipmentSlot.FEET)
        };
        for (ItemStack itemStack : itemStacks) {
            if (itemStack.getItem() instanceof CastleAttackArmor) {
                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                if (!data.contains(attributeType)) return 0;

                int type = data.getInt(attributeType);
                if (attributeNumMap.get(attributeName) != type) return 0;

                return attributeIndexValueMap.get(type) * data.getDouble(attributeRate);
            }
        }
        return 0;
    }


    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.CastleArmorPiece.get(), 24));
            add(new ItemStack(ModItems.CastlePiece.get(), 128));
            add(new ItemStack(ModItems.BlazeRune.get(), 8));
            add(new ItemStack(ModItems.completeGem.get(), 26));
            add(new ItemStack(ModItems.ReputationMedal.get(), 104));
            add(new ItemStack(ModItems.RefiningIron.get(), 6));
            add(new ItemStack(ModItems.RefiningCopper.get(), 6));
            add(new ItemStack(ModItems.WorldSoul3.get(), 6));
        }};
    }
}

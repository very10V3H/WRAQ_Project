package fun.wraq.series.instance.series.castle;


import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.onshoot.OnShootManaArrowEquip;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.mixture.WraqMixture;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class CastleManaArmor extends WraqArmor implements ForgeItem, OnShootManaArrowEquip {
    private static final Style style = CustomStyle.styleOfCastle;

    public static Map<Integer, Double> attributeIndexValueMap = new HashMap<>() {{
        put(0, 5120d);
        put(1, 2048d);
        put(2, 400d);
        put(3, 400d);
        put(4, 0.5);
    }};

    public CastleManaArmor(ModArmorMaterials Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        if (type.equals(Type.HELMET)) {
            Utils.percentHealthRecover.put(this, 0.008);
            Utils.healthRecover.put(this, 100d);
            Utils.defence.put(this, 30d);
            Utils.manaDefence.put(this, 15d);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, 65d);
            Utils.manaDefence.put(this, 30d);
            Utils.maxHealth.put(this, 12000d);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 24000d);
            Utils.defence.put(this, 30d);
            Utils.manaDefence.put(this, 15d);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.12);
            Utils.maxHealth.put(this, 12000d);
        }
        Utils.levelRequire.put(this, 180);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfCastle;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("暗影打击", style));
        components.add(Te.s(" 普通法球攻击", CustomStyle.styleOfMana,
                "将附带1枚", "25%基础伤害", CustomStyle.styleOfPower, "的",
                "额外法球", CustomStyle.styleOfMana));
        Compute.DescriptionPassive(components, Component.literal("灵魂痛击").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("附带").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.exTrueDamage("50%")));
        components.add(Component.literal(" -多件暗影城堡防具能线性提升伤害值百分比/伤害值").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfCastle();
    }

    public static double ExIgnoreDefenceDamage(Player player) {
        int ArmorCount = SuitCount.getCastleManaSuitCount(player);
        if (ArmorCount == 0) return 0;
        return PlayerAttributes.manaDamage(player) * 0.5 * ArmorCount;
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
                ComponentUtils.AttributeDescription.maxHealth(""),
                ComponentUtils.AttributeDescription.manaDamage(""),
                ComponentUtils.AttributeDescription.defence(""),
                ComponentUtils.AttributeDescription.manaDefence(""),
                ComponentUtils.AttributeDescription.releaseSpeed("")
        };
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(components1[type]).
                append(Component.literal(" + ").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("" + String.format("%.2f%%", data.getDouble(attributeRate) * 100))));

        components.add(Component.literal(" 目前已注能的次数为:").withStyle(ChatFormatting.WHITE).
                append(Component.literal("" + data.getInt(attributeTimes)).withStyle(CustomStyle.styleOfCastleCrystal))); // forge limitTimes

    }

    public static String ExMaxHealth = "ExManHealth";
    public static String ExManaDamage = "ExManaDamage";
    public static String ExDefence = "ExDefence";
    public static String ExManaDefence = "ExManaDefence";
    public static String ExCoolDownDecrease = "ExCoolDownDecrease";

    public static Map<String, Integer> attributeNumMap = new HashMap<>() {{
        put(ExMaxHealth, 0);
        put(ExManaDamage, 1);
        put(ExDefence, 2);
        put(ExManaDefence, 3);
        put(ExCoolDownDecrease, 4);
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
            if (itemStack.getItem() instanceof CastleManaArmor) {
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
            add(new ItemStack(ModItems.CASTLE_ARMOR_PIECE.get(), 24));
            add(new ItemStack(ModItems.CASTLE_PIECE.get(), 128));
            add(new ItemStack(ModItems.TREE_RUNE.get(), 24));
        }};
    }

    @Override
    public void onShoot(Player player) {
        WraqMixture.addExShoot(player, SuitCount.getCastleManaSuitCount(player) * 0.25);
    }
}

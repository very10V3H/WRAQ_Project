package fun.wraq.series.instance.series.castle;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class CastleCurios extends Item implements ICurioItem, RandomCurios {

    public CastleCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
        Utils.levelRequire.put(this, 140);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        Style style = CustomStyle.styleOfCastle;
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        randomPassiveText(components, stack);
        Compute.LevelRequire(components, Utils.levelRequire.get(this));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfCastle(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Compute.AddCuriosToList(player, stack);
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Compute.RemoveCuriosInList(player, stack);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static Map<String, Double> attributeValueMap = new HashMap<>() {{
        put(StringUtils.CuriosAttribute.attackDamage, 200d);
        put(StringUtils.CuriosAttribute.manaDamage, 400d);
        put(StringUtils.CuriosAttribute.maxHealth, 800d);
        put(StringUtils.CuriosAttribute.defence, 6d);
        put(StringUtils.CuriosAttribute.manaDefence, 5d);
        put(StringUtils.CuriosAttribute.defencePenetration0, 3d);
        put(StringUtils.CuriosAttribute.manaPenetration0, 3d);
        put(StringUtils.CuriosAttribute.coolDown, 0.3);
        put(StringUtils.CuriosAttribute.manaRecover, 30d);
        put(StringUtils.CuriosAttribute.maxMana, 50d);
        put(StringUtils.CuriosAttribute.swiftnessUp, 1.5);
        put(StringUtils.CuriosAttribute.critDamage, 0.4);
        put(StringUtils.CuriosAttribute.expUp, 0.5);
        put(StringUtils.CuriosAttribute.critRate, 0.1);
        put(StringUtils.CuriosAttribute.healthSteal, 0.1);
        put(StringUtils.CuriosAttribute.defencePenetration, 0.15);
        put(StringUtils.CuriosAttribute.movementSpeed, 0.5);
        put(StringUtils.CuriosAttribute.commonMovementSpeed, 0.25);
        put(StringUtils.CuriosAttribute.healthRecover, 50d);
        put(StringUtils.CuriosAttribute.percentHealthRecover, 0.05d);
        put(StringUtils.CuriosAttribute.healEffectUp, 0.5);
        put(StringUtils.CuriosAttribute.manaPenetration, 0.15);
        put(StringUtils.CuriosAttribute.manaHealthSteal, 0.1);
    }};

    public static void randomAttributeProvide(ItemStack itemStack, int attributeNum, double rate) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = new ArrayList<>(attributeValueMap.keySet());
        Random random = new Random();
        for (int i = 0; i < attributeNum; i++) {
            String Attribute = attributeList.get(random.nextInt(attributeList.size()));
            data.putDouble(Attribute, data.getDouble(Attribute) + random.nextDouble(0.25, 1) * rate);
        }
    }

    public static void randomAttackAttributeProvide(ItemStack itemStack, int attributeNum, double rate) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = new ArrayList<>() {{
            String[] strings = {
                    StringUtils.CuriosAttribute.attackDamage,
                    StringUtils.CuriosAttribute.manaDamage,
                    StringUtils.CuriosAttribute.defencePenetration0,
                    StringUtils.CuriosAttribute.manaPenetration0,
                    StringUtils.CuriosAttribute.critRate,
                    StringUtils.CuriosAttribute.critDamage,
                    StringUtils.CuriosAttribute.healthSteal,
                    StringUtils.CuriosAttribute.manaHealthSteal,
                    StringUtils.CuriosAttribute.defencePenetration,
                    StringUtils.CuriosAttribute.manaPenetration,
            };
            addAll(List.of(strings));
        }};
        Random random = new Random();
        for (int i = 0; i < attributeNum; i++) {
            String attribute = attributeList.get(random.nextInt(attributeList.size()));
            data.putDouble(attribute, data.getDouble(attribute) + random.nextDouble(0.25, 1) * rate);
        }
    }

    public static void randomDefenceAttributeProvide(ItemStack itemStack, int attributeNum, double rate) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = new ArrayList<>() {{
            String[] strings = {
                    StringUtils.CuriosAttribute.maxHealth,
                    StringUtils.CuriosAttribute.defence,
                    StringUtils.CuriosAttribute.manaDefence,
                    StringUtils.CuriosAttribute.healthRecover,
                    StringUtils.CuriosAttribute.healEffectUp
            };
            addAll(List.of(strings));
        }};
        Random random = new Random();
        for (int i = 0; i < attributeNum; i++) {
            String attribute = attributeList.get(random.nextInt(attributeList.size()));
            data.putDouble(attribute, data.getDouble(attribute) + random.nextDouble(0.25, 1) * rate);
        }
    }

    public static void randomFunctionAttributeProvide(ItemStack itemStack, int attributeNum, double rate) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = new ArrayList<>() {{
            String[] strings = {
                    StringUtils.CuriosAttribute.coolDown,
                    StringUtils.CuriosAttribute.manaRecover,
                    StringUtils.CuriosAttribute.maxMana,
                    StringUtils.CuriosAttribute.swiftnessUp,
                    StringUtils.CuriosAttribute.expUp,
                    StringUtils.CuriosAttribute.movementSpeed,
                    StringUtils.CuriosAttribute.commonMovementSpeed
            };
            addAll(List.of(strings));
        }};
        Random random = new Random();
        for (int i = 0; i < attributeNum; i++) {
            String attribute = attributeList.get(random.nextInt(attributeList.size()));
            data.putDouble(attribute, data.getDouble(attribute) + random.nextDouble(0.25, 1) * rate);
        }
    }

    @Override
    public void setAttribute(ItemStack stack) {
        CastleCurios.randomAttributeProvide(stack, 6, rate());
    }

    @Override
    public double rate() {
        return 1;
    }

    public static class RandomPassiveName {
        public static String Passive1 = "CastlePassive1";
        public static String Passive2 = "CastlePassive2";
        public static String Passive1Value = "CastlePassive1Value";
        public static String Passive2Value = "CastlePassive2Value";
    }

    public static double[] passiveValues = {
            0.1, 0.3, 0.15, 0.15, 0.15, 0.15
    };

    public static void randomPassiveText(List<Component> components, ItemStack itemStack) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        if (data.contains(RandomPassiveName.Passive1)) {
            int type = data.getInt(RandomPassiveName.Passive1);
            double rate = data.getDouble(RandomPassiveName.Passive1Value);
            switch (type) {
                case 0 -> {
                    Compute.DescriptionPassive(components, Component.literal("锐利").withStyle(CustomStyle.styleOfSea));
                    components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("伤害提升").withStyle(ChatFormatting.WHITE)));
                }
                case 1 -> {
                    Compute.DescriptionPassive(components, Component.literal("坚硬").withStyle(ChatFormatting.GRAY));
                    components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfHealth)).
                            append(Component.literal("伤害减免").withStyle(ChatFormatting.WHITE)));
                }
                case 2 -> {
                    Compute.DescriptionPassive(components, Component.literal("扇风").withStyle(CustomStyle.styleOfKaze));
                    components.add(Component.literal(" 对正在受").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("火焰影响").withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("的目标造成的伤害，将获得").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("伤害提升").withStyle(ChatFormatting.WHITE)));
                }
                case 3 -> {
                    Compute.DescriptionPassive(components, Component.literal("破冰").withStyle(CustomStyle.styleOfSnow));
                    components.add(Component.literal(" 对正在受").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("减速影响").withStyle(CustomStyle.styleOfSnow)).
                            append(Component.literal("的目标造成的伤害，将获得").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("伤害提升").withStyle(ChatFormatting.WHITE)));
                }
                case 4 -> {
                    Compute.DescriptionPassive(components, Component.literal("凌弱").withStyle(CustomStyle.styleOfSky));
                    components.add(Component.literal(" 对低于").withStyle(ChatFormatting.WHITE).
                            append(Compute.AttributeDescription.Health("40%")).
                            append(Component.literal("的目标造成的伤害，将获得").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("伤害提升").withStyle(ChatFormatting.WHITE)));
                }
                case 5 -> {
                    Compute.DescriptionPassive(components, Component.literal("克强").withStyle(CustomStyle.styleOfLightingIsland));
                    components.add(Component.literal(" 对高于于").withStyle(ChatFormatting.WHITE).
                            append(Compute.AttributeDescription.Health("40%")).
                            append(Component.literal("的目标造成的伤害，将获得").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("伤害提升").withStyle(ChatFormatting.WHITE)));
                }
            }
        }
        if (data.contains(RandomPassiveName.Passive2)) {
            int type = data.getInt(RandomPassiveName.Passive2);
            double rate = data.getDouble(RandomPassiveName.Passive2Value);
            switch (type) {
                case 0 -> {
                    Compute.DescriptionPassive(components, Component.literal("锐利").withStyle(CustomStyle.styleOfSea));
                    components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("伤害提升").withStyle(ChatFormatting.WHITE)));
                }
                case 1 -> {
                    Compute.DescriptionPassive(components, Component.literal("坚硬").withStyle(ChatFormatting.GRAY));
                    components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfHealth)).
                            append(Component.literal("伤害减免").withStyle(ChatFormatting.WHITE)));
                }
                case 2 -> {
                    Compute.DescriptionPassive(components, Component.literal("扇风").withStyle(CustomStyle.styleOfKaze));
                    components.add(Component.literal(" 对正在受").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("火焰影响").withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("的目标造成的伤害，将获得").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("伤害提升").withStyle(ChatFormatting.WHITE)));
                }
                case 3 -> {
                    Compute.DescriptionPassive(components, Component.literal("破冰").withStyle(CustomStyle.styleOfSnow));
                    components.add(Component.literal(" 对正在受").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("减速影响").withStyle(CustomStyle.styleOfSnow)).
                            append(Component.literal("的目标造成的伤害，将获得").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("伤害提升").withStyle(ChatFormatting.WHITE)));
                }
                case 4 -> {
                    Compute.DescriptionPassive(components, Component.literal("凌弱").withStyle(CustomStyle.styleOfSky));
                    components.add(Component.literal(" 对低于").withStyle(ChatFormatting.WHITE).
                            append(Compute.AttributeDescription.Health("40%")).
                            append(Component.literal("的目标造成的伤害，将获得").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("伤害提升").withStyle(ChatFormatting.WHITE)));
                }
                case 5 -> {
                    Compute.DescriptionPassive(components, Component.literal("克强").withStyle(CustomStyle.styleOfLightingIsland));
                    components.add(Component.literal(" 对高于于").withStyle(ChatFormatting.WHITE).
                            append(Compute.AttributeDescription.Health("40%")).
                            append(Component.literal("的目标造成的伤害，将获得").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(String.format("%.1f%%", passiveValues[type] * rate * 100)).withStyle(CustomStyle.styleOfPower)).
                            append(Component.literal("伤害提升").withStyle(ChatFormatting.WHITE)));
                }
            }
        }
    }

    public static double DamageDecrease(Player player) {
        return Passive2DamageDecrease(player);
    }

    public static double DamageEnhance(Player player, Mob mob) {
        double damageEnhance = 0;
        damageEnhance += Passive1DamageEnhance(player);
        damageEnhance += Passive3DamageEnhance(player, mob);
        damageEnhance += Passive4DamageEnhance(player, mob);
        damageEnhance += Passive5DamageEnhance(player, mob);
        damageEnhance += Passive6DamageEnhance(player, mob);
        return damageEnhance;
    }

    public static void RandomPassiveProvide(ItemStack itemStack) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        if (!data.contains(RandomPassiveName.Passive1)) {
            Random random = new Random();
            data.putInt(RandomPassiveName.Passive1, random.nextInt(6));
            data.putDouble(RandomPassiveName.Passive1Value, random.nextDouble(0.25, 1));
            data.putInt(RandomPassiveName.Passive2, random.nextInt(6));
            data.putDouble(RandomPassiveName.Passive2Value, random.nextDouble(0.25, 1));
        }
    }

    public static double Passive1DamageEnhance(Player player) {
        return DamageEnhance(player, 0);
    }

    public static double Passive2DamageDecrease(Player player) {
        return DamageEnhance(player, 1);
    }

    public static double Passive3DamageEnhance(Player player, Mob mob) {
        if (mob.getRemainingFireTicks() > 0) return DamageEnhance(player, 2);
        return 0;
    } // 受火焰影响的目标

    public static double Passive4DamageEnhance(Player player, Mob mob) {
        if (mob.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) return DamageEnhance(player, 3);
        return 0;
    } // 受减速影响的目标

    public static double Passive5DamageEnhance(Player player, Mob mob) {
        if (mob.getHealth() / mob.getMaxHealth() <= 0.4) return DamageEnhance(player, 4);
        return 0;
    } // 凌弱

    public static double Passive6DamageEnhance(Player player, Mob mob) {
        if (mob.getHealth() / mob.getMaxHealth() > 0.4) return DamageEnhance(player, 5);
        return 0;
    } // 克强

    public static double DamageEnhance(Player player, int index) {
        AtomicReference<Double> damageEnhance = new AtomicReference<>((double) 0);
        if (Utils.playerCuriosListMap.containsKey(player)) {
            List<ItemStack> itemStackList = Utils.playerCuriosListMap.get(player);
            List<Item> itemList = new ArrayList<>();
            itemStackList.forEach(itemStack -> {
                if (itemStack.getItem() instanceof CastleCurios) {
                    if (!itemList.contains(itemStack.getItem())
                            && (!Utils.levelRequire.containsKey(itemStack.getItem()) || player.experienceLevel >= Utils.levelRequire.get(itemStack.getItem()))) {
                        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        if (data.contains(RandomPassiveName.Passive1) && data.getInt(RandomPassiveName.Passive1) == index) {
                            damageEnhance.set(damageEnhance.get() + passiveValues[index] * data.getDouble(RandomPassiveName.Passive1Value));
                        }
                        if (data.contains(RandomPassiveName.Passive2) && data.getInt(RandomPassiveName.Passive2) == index) {
                            damageEnhance.set(damageEnhance.get() + passiveValues[index] * data.getDouble(RandomPassiveName.Passive2Value));
                        }
                        itemList.add(itemStack.getItem());
                    }
                }
            });
        }
        return damageEnhance.get();
    }
}

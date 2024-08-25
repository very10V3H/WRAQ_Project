package com.very.wraq.series.instance.castle;

import com.very.wraq.projectiles.RandomCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
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
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        randomPassiveText(components, stack);
        Compute.LevelRequire(components, Utils.levelRequire.get(this));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfCastle(components);
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

    public static Map<String, Double> AttributeValueMap = new HashMap<>() {{
        put(StringUtils.CuriosAttribute.AttackDamage, 200d);
        put(StringUtils.CuriosAttribute.ManaDamage, 400d);
        put(StringUtils.CuriosAttribute.MaxHealth, 800d);
        put(StringUtils.CuriosAttribute.Defence, 600d);
        put(StringUtils.CuriosAttribute.ManaDefence, 500d);
        put(StringUtils.CuriosAttribute.DefencePenetration0, 300d);
        put(StringUtils.CuriosAttribute.ManaPenetration0, 300d);
        put(StringUtils.CuriosAttribute.CoolDown, 0.3);
        put(StringUtils.CuriosAttribute.ManaRecover, 30d);
        put(StringUtils.CuriosAttribute.MaxMana, 50d);
        put(StringUtils.CuriosAttribute.SwiftnessUp, 1.5);
        put(StringUtils.CuriosAttribute.CritDamage, 0.4);
        put(StringUtils.CuriosAttribute.ExpUp, 0.5);
        put(StringUtils.CuriosAttribute.CritRate, 0.1);
        put(StringUtils.CuriosAttribute.HealthSteal, 0.1);
        put(StringUtils.CuriosAttribute.DefencePenetration, 0.15);
        put(StringUtils.CuriosAttribute.MovementSpeed, 0.5);
        put(StringUtils.CuriosAttribute.HealthRecover, 50d);
        put(StringUtils.CuriosAttribute.HealEffectUp, 0.5);
        put(StringUtils.CuriosAttribute.ManaPenetration, 0.15);
        put(StringUtils.CuriosAttribute.ManaHealthSteal, 0.1);
    }};

    public static void randomAttributeProvide(ItemStack itemStack, int attributeNum, double rate) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = new ArrayList<>(AttributeValueMap.keySet());
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
                    StringUtils.CuriosAttribute.AttackDamage,
                    StringUtils.CuriosAttribute.ManaDamage,
                    StringUtils.CuriosAttribute.DefencePenetration0,
                    StringUtils.CuriosAttribute.ManaPenetration0,
                    StringUtils.CuriosAttribute.CritRate,
                    StringUtils.CuriosAttribute.CritDamage,
                    StringUtils.CuriosAttribute.HealthSteal,
                    StringUtils.CuriosAttribute.ManaHealthSteal,
                    StringUtils.CuriosAttribute.DefencePenetration,
                    StringUtils.CuriosAttribute.ManaPenetration,
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
                    StringUtils.CuriosAttribute.MaxHealth,
                    StringUtils.CuriosAttribute.Defence,
                    StringUtils.CuriosAttribute.ManaDefence,
                    StringUtils.CuriosAttribute.HealthRecover,
                    StringUtils.CuriosAttribute.HealEffectUp
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
                    StringUtils.CuriosAttribute.CoolDown,
                    StringUtils.CuriosAttribute.ManaRecover,
                    StringUtils.CuriosAttribute.MaxMana,
                    StringUtils.CuriosAttribute.SwiftnessUp,
                    StringUtils.CuriosAttribute.ExpUp,
                    StringUtils.CuriosAttribute.MovementSpeed
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
        CastleCurios.randomAttributeProvide(stack, 6, 1);
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

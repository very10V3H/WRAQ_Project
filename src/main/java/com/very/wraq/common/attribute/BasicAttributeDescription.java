package com.very.wraq.common.attribute;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Either;
import com.very.wraq.common.Compute;
import com.very.wraq.common.fast.Te;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.customized.WraqUniformCurios;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.forge.ForgeEquipUtils;
import com.very.wraq.projectiles.RandomCurios;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.render.toolTip.NewTooltip;
import com.very.wraq.render.toolTip.TraditionalTooltip;
import com.very.wraq.series.gems.GemAttributes;
import com.very.wraq.series.gems.WraqGem;
import com.very.wraq.series.instance.series.castle.CastleCurios;
import com.very.wraq.series.worldsoul.SoulEquipAttribute;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicAttributeDescription {

    public static void NewAttributeDescription(RenderTooltipEvent.GatherComponents event) throws CommandSyntaxException {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        if (localPlayer == null) return;
        int index = 4;
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof WraqCurios) index = 5;
        if (!(itemStack.getItem() instanceof WraqCurios || itemStack.getItem() instanceof WraqUniformCurios)
                && itemStack.getTagElement(Utils.MOD_ID) == null && !Utils.offHandTag.containsKey(itemStack.getItem()))
            return;
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Item item = itemStack.getItem();
        if (itemStack.is(ModItems.ManageSword.get())) return;

        if (data.contains(StringUtils.ForgeLevel)) {
            int forgeLevel = data.getInt(StringUtils.ForgeLevel);

            if (data.contains(StringUtils.QingMingForgePaper)) ++forgeLevel;
            if (data.contains(StringUtils.LabourDayForgePaper)) ++forgeLevel;

            Style[] styles = {CustomStyle.styleOfMine, CustomStyle.styleOfGold,
                    Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE), CustomStyle.styleOfWorld};

            Style style = styles[Math.min(3, Math.max(0, (forgeLevel - 1) / 8))];

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(Component.literal(" 强化等级 ").withStyle(CustomStyle.styleOfPower).
                    append(Component.literal("" + forgeLevel).withStyle(style)), TraditionalTooltip.forge)));
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.attackDamage, Utils.xpLevelAttackDamage,
                StringUtils.CuriosAttribute.xpLevelAttackDamage, "物理攻击", Style.EMPTY.applyFormat(ChatFormatting.AQUA),
                "%.0f", false, itemStack, event.getTooltipElements(), localPlayer, true, Style.EMPTY.applyFormat(ChatFormatting.YELLOW));

        if (Utils.attackDamage.containsKey(item) || data.contains(StringUtils.RandomAttribute.attackDamage)
                || data.contains(StringUtils.CuriosAttribute.attackDamage)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTime = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 物理攻击").withStyle(ChatFormatting.AQUA).
                            append(Component.literal(" " + String.format("%.0f", SoulEquipAttribute.BaseAttribute.SoulSword.AttackDamage)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.AttackDamage)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTime + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.attackDamage)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTime = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 物理攻击").withStyle(ChatFormatting.AQUA).
                            append(Component.literal(" " + String.format("%.0f", SoulEquipAttribute.BaseAttribute.SoulBow.AttackDamage)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.AttackDamage)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTime + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.attackDamage)));
                }
            } else {
                double baseDamage;
                if (Utils.attackDamage.containsKey(item) || data.contains(StringUtils.CuriosAttribute.attackDamage)) {
                    if (Utils.attackDamage.containsKey(item))
                        baseDamage = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.attackDamage);
                    else if (item instanceof RandomCurios)
                        baseDamage = data.getDouble(StringUtils.CuriosAttribute.attackDamage) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.attackDamage);
                    else baseDamage = data.getInt(StringUtils.CuriosAttribute.attackDamage);
                } else
                    baseDamage = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.attackDamage);

                if (baseDamage != 0) {
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 物理攻击").withStyle(ChatFormatting.AQUA).
                            append(Component.literal(" " + String.format("%.0f", baseDamage)).withStyle(ChatFormatting.WHITE)));

                    handleProficiency(data, baseDamage, mutableComponent);
                    handleForge(data, baseDamage, mutableComponent);
                    handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.attackDamage, mutableComponent);

                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.attackDamage)));
                }
            }
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.manaDamage, Utils.xpLevelManaDamage,
                StringUtils.CuriosAttribute.xpLevelManaDamage, "魔法攻击", Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE),
                "%.0f", false, itemStack, event.getTooltipElements(), localPlayer, true, Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE));

        if (Utils.manaDamage.containsKey(item) || data.contains(StringUtils.RandomAttribute.manaDamage)
                || data.contains(StringUtils.CuriosAttribute.manaDamage)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal(" " + String.format("%.0f", SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaAttackDamage)).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaAttackDamage)).withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaDamage)));
            } else {
                double baseDamage;
                if (Utils.manaDamage.containsKey(item) || data.contains(StringUtils.CuriosAttribute.manaDamage)) {
                    if (Utils.manaDamage.containsKey(item))
                        baseDamage = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.manaDamage);
                    else if (item instanceof RandomCurios)
                        baseDamage = data.getDouble(StringUtils.CuriosAttribute.manaDamage) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.manaDamage);
                    else baseDamage = data.getInt(StringUtils.CuriosAttribute.manaDamage);
                } else
                    baseDamage = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.manaDamage);

                if (baseDamage != 0) {
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 法术攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal(" " + String.format("%.0f", baseDamage)).withStyle(ChatFormatting.WHITE)));

                    handleProficiency(data, baseDamage, mutableComponent);
                    handleForge(data, baseDamage, mutableComponent);
                    handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.manaDamage, mutableComponent);

                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaDamage)));
                }
            }
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.defence, Utils.xpLevelDefence,
                StringUtils.CuriosAttribute.xpLevelDefence, "基础护甲", Style.EMPTY.applyFormat(ChatFormatting.GRAY),
                "%.0f", false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.defence.containsKey(item) || data.contains(StringUtils.RandomAttribute.defence)
                || data.contains(StringUtils.CuriosAttribute.defence)) {
            double defence;
            if (Utils.defence.containsKey(item) || data.contains(StringUtils.CuriosAttribute.defence)) {
                if (Utils.defence.containsKey(item))
                    defence = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.defence);
                else if (item instanceof RandomCurios)
                    defence = data.getDouble(StringUtils.CuriosAttribute.defence) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.defence);
                else defence = data.getInt(StringUtils.CuriosAttribute.defence);
            } else defence = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.defence);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 基础护甲").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+" + String.format("%.0f", defence)).withStyle(ChatFormatting.WHITE)));

            handleForge(data, defence, mutableComponent);
            handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.defence, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.defence)));
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.manaDefence, Utils.xpLevelManaDefence,
                StringUtils.CuriosAttribute.xpLevelManaDefence, "魔法抗性", Style.EMPTY.applyFormat(ChatFormatting.BLUE),
                "%.0f", false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.manaDefence.containsKey(item) || data.contains(StringUtils.CuriosAttribute.manaDefence)) {
            double manaDefence = 0;
            if (Utils.manaDefence.containsKey(item))
                manaDefence = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.manaDefence);
            else if (item instanceof RandomCurios)
                manaDefence = data.getDouble(StringUtils.CuriosAttribute.manaDefence) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.manaDefence);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 魔法抗性").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+" + String.format("%.0f", manaDefence)).withStyle(ChatFormatting.WHITE)));

            handleForge(data, manaDefence, mutableComponent);
            handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.manaDefence, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaDefence)));
        }

        if (Utils.maxHealth.containsKey(item) || data.contains(StringUtils.RandomAttribute.maxHealth)
                || data.contains(StringUtils.CuriosAttribute.maxHealth)) {
            double maxHealth;
            if (Utils.maxHealth.containsKey(item) || data.contains(StringUtils.CuriosAttribute.maxHealth)) {
                if (Utils.maxHealth.containsKey(item))
                    maxHealth = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.maxHealth);
                else if (item instanceof RandomCurios)
                    maxHealth = data.getDouble(StringUtils.CuriosAttribute.maxHealth) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.maxHealth);
                else
                    maxHealth = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.maxHealth);
            } else
                maxHealth = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.maxHealth);
            MutableComponent mutableComponent = Component.literal("");
            if (maxHealth > 0) {
                mutableComponent.append(Component.literal(" 最大生命值").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("+" + String.format("%.0f", maxHealth)).withStyle(ChatFormatting.WHITE)));
            }

            if (maxHealth < 0) {
                mutableComponent.append(Component.literal(" 最大生命值").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("-" + String.format("%.0f", -maxHealth)).withStyle(ChatFormatting.RED)));

            }

            double ExHealth = 0;
            if (data.contains(StringUtils.ForgeLevel)) ExHealth = Compute.forgingValue(data, maxHealth);

            if (ExHealth > 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExHealth)).withStyle(ChatFormatting.GREEN).
                        append(Component.literal("⮅").withStyle(CustomStyle.styleOfPower)));
            }
            if (ExHealth < 0) {
                mutableComponent.append(Component.literal(" - " + String.format("%.0f", -(ExHealth))).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
            }

            handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.maxHealth, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.maxHealth)));
        }

        if (Utils.healthRecover.containsKey(item) || data.contains(StringUtils.CuriosAttribute.healthRecover)
                || data.contains(StringUtils.RandomAttribute.healthRecover)) {
            double healthRecover;
            if (Utils.healthRecover.containsKey(item)) healthRecover = Utils.healthRecover.get(item);
            else if (item instanceof RandomCurios)
                healthRecover = data.getDouble(StringUtils.CuriosAttribute.healthRecover) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.healthRecover);
            else healthRecover = data.getInt(StringUtils.CuriosAttribute.healthRecover);
            if (data.contains(StringUtils.RandomAttribute.healthRecover))
                healthRecover += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.healthRecover);

            MutableComponent mutableComponent = Component.literal("");
            if (healthRecover > 0) {
                mutableComponent.append(Component.literal(" 生命回复").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("+" + String.format("%.1f", healthRecover)).withStyle(ChatFormatting.WHITE)));
            } else {
                mutableComponent.append(Component.literal(" 生命回复").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("-" + String.format("%.1f", -healthRecover)).withStyle(ChatFormatting.RED)));
            }

            handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.healthRecover, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.healthRecover)));
        }

        if (Utils.defencePenetration.containsKey(item) || data.contains(StringUtils.CuriosAttribute.defencePenetration)) {

            double DefencePenetration;
            if (Utils.defencePenetration.containsKey(item)) DefencePenetration = Utils.defencePenetration.get(item);
            else if (item instanceof RandomCurios)
                DefencePenetration = data.getDouble(StringUtils.CuriosAttribute.defencePenetration) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.defencePenetration);
            else DefencePenetration = data.getInt(StringUtils.CuriosAttribute.defencePenetration);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+" + String.format("%.0f%%", DefencePenetration * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.defencePenetration, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.defencePenetration)));

        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.defencePenetration0, Utils.xpLevelDefencePenetration0,
                StringUtils.CuriosAttribute.xpLevelDefencePenetration0, "护甲穿透", Style.EMPTY.applyFormat(ChatFormatting.GRAY),
                "%.0f", false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.defencePenetration0.containsKey(item) || data.contains(StringUtils.CuriosAttribute.defencePenetration0)
                || data.contains(StringUtils.RandomAttribute.defencePenetration0)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("+" + String.format("%.0f", SoulEquipAttribute.BaseAttribute.SoulSword.DefencePenetration0)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.DefencePenetration0)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.defencePenetration0)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("+" + String.format("%.0f", SoulEquipAttribute.BaseAttribute.SoulBow.DefencePenetration0)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.DefencePenetration0)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.defencePenetration0)));
                }
            } else {
                double defencePenetration0;
                if (Utils.defencePenetration0.containsKey(item))
                    defencePenetration0 = Utils.defencePenetration0.get(item);
                else if (item instanceof RandomCurios)
                    defencePenetration0 = data.getDouble(StringUtils.CuriosAttribute.defencePenetration0) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.defencePenetration0);
                else defencePenetration0 = data.getInt(StringUtils.CuriosAttribute.defencePenetration0);
                if (data.contains(StringUtils.RandomAttribute.defencePenetration0))
                    defencePenetration0 += data.getDouble(StringUtils.RandomAttribute.defencePenetration0);

                if (defencePenetration0 != 0) {
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("+" + String.format("%.0f", defencePenetration0)).withStyle(ChatFormatting.WHITE)));

                    handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.defencePenetration0, mutableComponent);

                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.defencePenetration0)));
                }
            }
        }

        if (Utils.critRate.containsKey(item) || data.contains(StringUtils.CuriosAttribute.critRate)
                || data.contains(StringUtils.RandomAttribute.critRate)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal("+" + String.format("%.1f%%", SoulEquipAttribute.BaseAttribute.SoulSword.CritRate * 100)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.1f%%", SoulEquipAttribute.ForgingAddition.CritRate * 100)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.critRate)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal("+" + String.format("%.1f%%", SoulEquipAttribute.BaseAttribute.SoulBow.CritRate * 100)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.1f%%", SoulEquipAttribute.ForgingAddition.CritRate * 100)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.critRate)));
                }
            } else {
                double critRate;
                if (Utils.critRate.containsKey(item)) critRate = Utils.critRate.get(item);
                else if (item instanceof RandomCurios)
                    critRate = data.getDouble(StringUtils.CuriosAttribute.critRate) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.critRate);
                else critRate = data.getInt(StringUtils.CuriosAttribute.critRate);
                if (data.contains(StringUtils.RandomAttribute.critRate))
                    critRate += data.getDouble(StringUtils.RandomAttribute.critRate);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.1f%%", critRate * 100)).withStyle(ChatFormatting.WHITE)));

                handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.critRate, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.critRate)));
            }
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.critDamage, Utils.xpLevelCritDamage,
                StringUtils.CuriosAttribute.xpLevelCritDamage, "暴击伤害", Style.EMPTY.applyFormat(ChatFormatting.BLUE),
                "%.0f%%", true, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.critDamage.containsKey(item) || data.contains(StringUtils.CuriosAttribute.critDamage)
                || data.contains(StringUtils.RandomAttribute.critDamage)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 暴击伤害").withStyle(ChatFormatting.BLUE).
                            append(Component.literal("+" + String.format("%.0f%%", SoulEquipAttribute.BaseAttribute.SoulBow.CritDamage * 100)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.CritDamage * 100)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.critDamage)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 暴击伤害").withStyle(ChatFormatting.BLUE).
                            append(Component.literal("+" + String.format("%.0f%%", SoulEquipAttribute.BaseAttribute.SoulBow.CritDamage * 100)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.CritDamage * 100)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.critDamage)));
                }
            } else {
                double critDamage;
                if (Utils.critDamage.containsKey(item)) critDamage = Utils.critDamage.get(item);
                else if (item instanceof RandomCurios)
                    critDamage = data.getDouble(StringUtils.CuriosAttribute.critDamage) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.critDamage);
                else critDamage = data.getInt(StringUtils.CuriosAttribute.critDamage);
                if (data.contains(StringUtils.RandomAttribute.critDamage))
                    critDamage += data.getDouble(StringUtils.RandomAttribute.critDamage);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 暴击伤害").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+" + String.format("%.0f%%", critDamage * 100)).withStyle(ChatFormatting.WHITE)));

                handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.critDamage, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.critDamage)));
            }
        }

        if (Utils.healthSteal.containsKey(item) || data.contains(StringUtils.CuriosAttribute.healthSteal)
                || data.contains(StringUtils.RandomAttribute.healthSteal)) {
            if (itemStack.is(ModItems.SoulSword.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 生命偷取").withStyle(ChatFormatting.RED).
                            append(Component.literal("+" + String.format("%.0f‰", SoulEquipAttribute.BaseAttribute.SoulSword.HealthSteal * 100)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f‰", SoulEquipAttribute.ForgingAddition.HealthSteal * 100)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.healthSteal)));
                }
            } else {
                double healSteal;
                if (Utils.healthSteal.containsKey(item)) healSteal = Utils.healthSteal.get(item);
                else if (item instanceof RandomCurios)
                    healSteal = data.getDouble(StringUtils.CuriosAttribute.healthSteal) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.healthSteal);
                else healSteal = data.getInt(StringUtils.CuriosAttribute.healthSteal);
                if (data.contains(StringUtils.RandomAttribute.healthSteal))
                    healSteal += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.healthSteal);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 生命偷取").withStyle(ChatFormatting.RED).
                        append(Component.literal("+" + String.format("%.0f‰", healSteal * 100)).withStyle(ChatFormatting.WHITE)));

                handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.healthSteal, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.healthSteal)));

            }
        }

        if (Utils.manaCost.containsKey(item)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                        append(Component.literal(" " + String.format("%.0f", SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaCost)).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("- " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaCost)).withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaCost)));
            } else {
                double ManaCost = Utils.manaCost.get(item);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                        append(Component.literal(" " + String.format("%.0f", ManaCost)).withStyle(ChatFormatting.WHITE)));
                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaCost)));
            }
        }

        if (Utils.maxMana.containsKey(item) || data.contains(StringUtils.CuriosAttribute.maxMana)
                || data.contains(StringUtils.CuriosAttribute.maxMana)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.0f", SoulEquipAttribute.BaseAttribute.SoulSceptre.MaxMana)).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ 16 x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.maxMana)));
            } else {
                double maxMana = 0;
                if (Utils.maxMana.containsKey(item)) maxMana = Utils.maxMana.get(item);
                else if (item instanceof RandomCurios)
                    maxMana = data.getDouble(StringUtils.CuriosAttribute.maxMana) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.maxMana);
                if (data.contains(StringUtils.RandomAttribute.maxMana))
                    maxMana = data.getDouble(StringUtils.RandomAttribute.maxMana);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.0f", maxMana)).withStyle(ChatFormatting.WHITE)));

                handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.maxMana, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.maxMana)));
            }
        }

        if (Utils.manaPenetration.containsKey(item) || data.contains(StringUtils.CuriosAttribute.manaPenetration)) {
            double ManaPenetration;
            if (Utils.manaPenetration.containsKey(item)) ManaPenetration = Utils.manaPenetration.get(item);
            else if (item instanceof RandomCurios)
                ManaPenetration = data.getDouble(StringUtils.CuriosAttribute.manaPenetration) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.manaPenetration);
            else ManaPenetration = data.getInt(StringUtils.CuriosAttribute.manaPenetration);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+" + String.format("%.0f%%", ManaPenetration * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.manaPenetration, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaPenetration)));
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.manaPenetration0, Utils.xpLevelManaPenetration0,
                StringUtils.CuriosAttribute.xpLevelManaPenetration0, "魔法穿透", Style.EMPTY.applyFormat(ChatFormatting.BLUE),
                "%.0f", false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.manaPenetration0.containsKey(item) || data.contains(StringUtils.CuriosAttribute.manaPenetration0)
                || data.contains(StringUtils.RandomAttribute.manaPenetration0)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+" + String.format("%.0f", SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaPenetration0)).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaPenetration0)).withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaPenetration0)));
            } else {
                double manaPenetration0;
                if (Utils.manaPenetration0.containsKey(item)) manaPenetration0 = Utils.manaPenetration0.get(item);
                else if (item instanceof RandomCurios)
                    manaPenetration0 = data.getDouble(StringUtils.CuriosAttribute.manaPenetration0) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.manaPenetration0);
                else manaPenetration0 = data.getInt(StringUtils.CuriosAttribute.manaPenetration0);
                if (data.contains(StringUtils.RandomAttribute.manaPenetration0))
                    manaPenetration0 += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.manaPenetration0);

                if (manaPenetration0 != 0) {
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                            append(Component.literal("+" + String.format("%.0f", manaPenetration0)).withStyle(ChatFormatting.WHITE)));

                    handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.manaPenetration0, mutableComponent);

                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaPenetration0)));
                }
            }
        }

        if (Utils.manaRecover.containsKey(item) || data.contains(StringUtils.CuriosAttribute.manaRecover)
                || data.contains(StringUtils.RandomAttribute.manaRecover)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.0f", SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaRecover)).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaRecover)).withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaRecover)));
            } else {
                double manaRecover;
                if (Utils.manaRecover.containsKey(item)) manaRecover = Utils.manaRecover.get(item);
                else if (item instanceof RandomCurios)
                    manaRecover = data.getDouble(StringUtils.CuriosAttribute.manaRecover) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.manaRecover);
                else manaRecover = data.getInt(StringUtils.CuriosAttribute.manaRecover);
                if (data.contains(StringUtils.RandomAttribute.manaRecover))
                    manaRecover += data.getDouble(StringUtils.RandomAttribute.manaRecover);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.0f", manaRecover)).withStyle(ChatFormatting.WHITE)));

                handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.manaRecover, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaRecover)));
            }
        }

        if (Utils.coolDownDecrease.containsKey(item) || data.contains(StringUtils.CuriosAttribute.coolDown)
                || data.contains(StringUtils.RandomAttribute.coolDown)) {
            double coolDown;
            if (Utils.coolDownDecrease.containsKey(item)) coolDown = Utils.coolDownDecrease.get(item);
            else if (item instanceof RandomCurios)
                coolDown = data.getDouble(StringUtils.CuriosAttribute.coolDown) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.coolDown);
            else coolDown = data.getInt(StringUtils.CuriosAttribute.coolDown);
            if (data.contains(StringUtils.RandomAttribute.coolDown))
                coolDown = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.coolDown);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 技能急速").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" " + String.format("%.0f", coolDown * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.coolDown, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.releaseSpeed)));
        }
        if (Utils.movementSpeedCommon.containsKey(item)) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 移动速度").withStyle(CustomStyle.styleOfFlexible).
                    append(Component.literal(" " + String.format("%.0f%%", Utils.movementSpeedCommon.get(item) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.movementSpeed)));
        }
        if (Utils.movementSpeedWithoutBattle.containsKey(item) || data.contains(StringUtils.CuriosAttribute.movementSpeed)
                || data.contains(StringUtils.RandomAttribute.movementSpeed)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())
                    || itemStack.is(ModItems.SoulSceptre.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 脱战移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("+" + String.format("%.0f%%", SoulEquipAttribute.BaseAttribute.SoulSword.MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.movementSpeed)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 脱战移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("+" + String.format("%.0f%%", SoulEquipAttribute.BaseAttribute.SoulBow.MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.movementSpeed)));
                }
                if (itemStack.is(ModItems.SoulSceptre.get())) {
                    int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 脱战移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("+" + String.format("%.0f%%", SoulEquipAttribute.BaseAttribute.SoulSceptre.MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.movementSpeed)));
                }
            } else {
                double MovementSpeed;
                if (Utils.movementSpeedWithoutBattle.containsKey(item))
                    MovementSpeed = Utils.movementSpeedWithoutBattle.get(item);
                else if (item instanceof RandomCurios)
                    MovementSpeed = data.getDouble(StringUtils.CuriosAttribute.movementSpeed) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.movementSpeed);
                else MovementSpeed = data.getInt(StringUtils.CuriosAttribute.movementSpeed);
                if (data.contains(StringUtils.RandomAttribute.movementSpeed))
                    MovementSpeed += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.movementSpeed);

                MutableComponent mutableComponent = Component.literal("");

                if (MovementSpeed > 0) {
                    mutableComponent.append(Component.literal(" 脱战移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("+" + String.format("%.0f%%", MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)));

                }
                if (MovementSpeed < 0) {
                    mutableComponent.append(Component.literal(" 脱战移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("-" + String.format("%.0f%%", -MovementSpeed * 100)).withStyle(ChatFormatting.RED)));

                }

                handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.movementSpeed, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.movementSpeed)));
            }
        }
        if (Utils.expUp.containsKey(item) || data.contains(StringUtils.CuriosAttribute.expUp)) {
            double ExpUp;
            if (item instanceof RandomCurios)
                ExpUp = data.getDouble(StringUtils.CuriosAttribute.expUp) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.expUp);
            else ExpUp = Utils.expUp.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+" + String.format("%.0f%%", ExpUp * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.expUp, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.expUp)));
        }
        if (Utils.swiftnessUp.containsKey(item) || data.contains(StringUtils.CuriosAttribute.swiftnessUp)) {
            double SwiftnessUp;
            if (item instanceof RandomCurios)
                SwiftnessUp = data.getDouble(StringUtils.CuriosAttribute.swiftnessUp) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.swiftnessUp);
            else SwiftnessUp = Utils.swiftnessUp.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 迅捷加成").withStyle(CustomStyle.styleOfFlexible).
                    append(Component.literal("+" + String.format("%.1f", SwiftnessUp)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.swiftnessUp, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.swiftnessUp)));
        }
        if (Utils.manaHealthSteal.containsKey(item) || data.contains(StringUtils.CuriosAttribute.manaHealthSteal)) {
            double ManaHealSteal;
            if (Utils.manaHealthSteal.containsKey(item)) ManaHealSteal = Utils.manaHealthSteal.get(item);
            else if (item instanceof RandomCurios)
                ManaHealSteal = data.getDouble(StringUtils.CuriosAttribute.manaHealthSteal) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.manaHealthSteal);
            else ManaHealSteal = data.getInt(StringUtils.CuriosAttribute.manaHealthSteal);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 法术吸血").withStyle(CustomStyle.styleOfBloodMana).
                    append(Component.literal("+" + String.format("%.0f‰", ManaHealSteal * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.manaHealthSteal, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaHealthSteal)));
        }
        if (Utils.healEffectUp.containsKey(item) || data.contains(StringUtils.CuriosAttribute.healEffectUp)) {
            double HealEffectUp;
            if (Utils.healEffectUp.containsKey(item)) HealEffectUp = Utils.healEffectUp.get(item);
            else if (item instanceof RandomCurios)
                HealEffectUp = data.getDouble(StringUtils.CuriosAttribute.healEffectUp) * CastleCurios.attributeValueMap.get(StringUtils.CuriosAttribute.healEffectUp);
            else HealEffectUp = data.getInt(StringUtils.CuriosAttribute.healEffectUp);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 治疗强度").withStyle(CustomStyle.styleOfHealth).
                    append(Component.literal("+" + String.format("%.0f%%", HealEffectUp * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.CuriosAttribute.healEffectUp, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.healthStrength)));
        }
        if (Utils.luckyUp.containsKey(item) || data.contains(StringUtils.CuriosAttribute.LuckyUp)) {
            double LuckyUp;
            if (Utils.luckyUp.containsKey(item)) LuckyUp = Utils.luckyUp.get(item);
            else LuckyUp = data.getInt(StringUtils.CuriosAttribute.LuckyUp);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 幸运加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal(" " + String.format("%.0f%%", LuckyUp * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.luckyUp)));
        }

        if (Element.LifeElementValue.containsKey(item)) {
            double value = Element.LifeElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化生机元素强度").withStyle(CustomStyle.styleOfLife).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.lifeElement)));
        }

        if (Element.WaterElementValue.containsKey(item)) {
            double value = Element.WaterElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化碧水元素强度").withStyle(CustomStyle.styleOfWater).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.waterElement)));
        }

        if (Element.FireElementValue.containsKey(item)) {
            double value = Element.FireElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化炽焰元素强度").withStyle(CustomStyle.styleOfFire).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.fireElement)));
        }

        if (Element.StoneElementValue.containsKey(item)) {
            double value = Element.StoneElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化层岩元素强度").withStyle(CustomStyle.styleOfStone).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.stoneElement)));
        }

        if (Element.IceElementValue.containsKey(item)) {
            double value = Element.IceElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化凛冰元素强度").withStyle(CustomStyle.styleOfIce).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.iceElement)));
        }

        if (Element.LightningElementValue.containsKey(item)) {
            double value = Element.LightningElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化怒雷元素强度").withStyle(CustomStyle.styleOfLightning).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.lightningElement)));
        }

        if (Element.WindElementValue.containsKey(item)) {
            double value = Element.WindElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化澄风元素强度").withStyle(CustomStyle.styleOfWind).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.windElement)));
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.attackDamage, Utils.percentAttackDamageEnhance,
                StringUtils.CuriosAttribute.percentAttackDamage, "物理攻击",
                Style.EMPTY.applyFormat(ChatFormatting.AQUA), "%.0f%%", true, itemStack, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.defence, Utils.percentDefenceEnhance,
                StringUtils.CuriosAttribute.percentDefenceEnhance, "基础护甲",
                Style.EMPTY.applyFormat(ChatFormatting.GRAY), "%.0f%%", true, itemStack, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.maxHealth, Utils.percentMaxHealthEnhance,
                StringUtils.CuriosAttribute.percentMaxHealthEnhance, "最大生命值",
                Style.EMPTY.applyFormat(ChatFormatting.GREEN), "%.0f%%", true, itemStack, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.manaDamage, Utils.percentManaDamageEnhance,
                StringUtils.CuriosAttribute.percentManaDamageEnhance, "魔法攻击",
                CustomStyle.styleOfMana, "%.0f%%", true, itemStack, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.manaDefence, Utils.percentManaDefenceEnhance,
                StringUtils.CuriosAttribute.percentManaDefenceEnhance, "魔法抗性",
                Style.EMPTY.applyFormat(ChatFormatting.BLUE), "%.0f%%", true, itemStack, event.getTooltipElements());

        // 以下为新版宝石内容提示
        List<WraqGem> gemList = WraqGem.getEquipContainGemList(itemStack);
        if (!gemList.isEmpty() || data.getInt("newSlot") > 0) {
            index ++;
            if (Screen.hasAltDown()) {
                event.getTooltipElements().add(index++, Either.right(new TraditionalTooltip.MyTooltip(Component.literal("─").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("───────────────────").withStyle(ChatFormatting.LIGHT_PURPLE).
                                append(Component.literal("─").withStyle(ChatFormatting.WHITE))), -1)));
                event.getTooltipElements().add(index++, Either.right(new TraditionalTooltip.MyTooltip(Component.literal("γ-宝石属性:").withStyle(ChatFormatting.LIGHT_PURPLE), -1)));
                for (WraqGem wraqGem : gemList) {
                    event.getTooltipElements().add(index++, Either.right(new TraditionalTooltip.MyTooltip(Component.literal("「").withStyle(ChatFormatting.AQUA).
                            append(Component.literal("◈").withStyle(wraqGem.getHoverStyle())).
                            append(Component.literal("」").withStyle(ChatFormatting.AQUA)).
                            append(wraqGem.getDefaultInstance().getDisplayName()), -1)));
                    List<WraqGem.AttributeMapValue> list = wraqGem.getAttributeMapValues();
                    for (WraqGem.AttributeMapValue attributeMapValue : list) {
                        ToolTipParameter toolTipParameter = toolTipParameterMap.get(System.identityHashCode(attributeMapValue.attributeMap()));
                        Component component = Component.literal(" " + toolTipParameter.attributeName).withStyle(toolTipParameter.style).
                                append(Component.literal((attributeMapValue.value() > 0 ? "+" : "") + String.format(toolTipParameter.valueFormat,
                                        attributeMapValue.value() * (toolTipParameter.isPercent ? 100 : 1))).
                                        withStyle(attributeMapValue.value() > 0 ? ChatFormatting.WHITE : ChatFormatting.RED));
                        event.getTooltipElements().add(index++, Either.right(new NewTooltip.MyNewTooltip(component, toolTipParameter.resourceLocation)));
                    }
                }
                for (int i = 0 ; i < data.getInt("newSlot") ; i ++) {
                    event.getTooltipElements().add(index++, Either.right(new TraditionalTooltip.MyTooltip(Component.literal("「").withStyle(ChatFormatting.AQUA).
                            append(Component.literal(" ")).
                            append(Component.literal("」").withStyle(ChatFormatting.AQUA)).
                            append(Component.literal(" 待镶嵌").withStyle(CustomStyle.styleOfMine)), -1)));
                }
            } else {
                MutableComponent component = Component.literal(" ");
                for (WraqGem wraqGem : gemList) {
                    component.append(Component.literal("「").withStyle(ChatFormatting.AQUA).
                            append(Component.literal("◈").withStyle(wraqGem.getHoverStyle())).
                            append(Component.literal("」").withStyle(ChatFormatting.AQUA)));
                }
                event.getTooltipElements().add(index++, Either.right(new TraditionalTooltip.MyTooltip(component, -1)));
                event.getTooltipElements().add(index++, Either.right(new TraditionalTooltip.MyTooltip(Component.literal(" [按住ALT查看宝石属性]").withStyle(ChatFormatting.LIGHT_PURPLE), -1)));
            }
        }
    }

    public record ToolTipParameter(String attributeName, Style style, String valueFormat,
                            boolean isPercent, ResourceLocation resourceLocation) {}

    public static Map<Integer, ToolTipParameter> toolTipParameterMap = new HashMap<>() {{
        put(System.identityHashCode(Utils.percentAttackDamageEnhance), new ToolTipParameter("物理攻击",
                Style.EMPTY.applyFormat(ChatFormatting.AQUA), "%.0f%%", true, TraditionalTooltip.attackDamage));
        put(System.identityHashCode(Utils.percentDefenceEnhance), new ToolTipParameter("基础护甲",
                Style.EMPTY.applyFormat(ChatFormatting.GRAY), "%.0f%%", true, TraditionalTooltip.defence));
        put(System.identityHashCode(Utils.percentManaDamageEnhance), new ToolTipParameter("魔法攻击",
                CustomStyle.styleOfMana, "%.0f%%", true, TraditionalTooltip.manaDamage));
        put(System.identityHashCode(Utils.percentMaxHealthEnhance), new ToolTipParameter("最大生命值",
                Style.EMPTY.applyFormat(ChatFormatting.GREEN), "%.0f%%", true, TraditionalTooltip.maxHealth));
        put(System.identityHashCode(Utils.percentManaDefenceEnhance), new ToolTipParameter("魔法抗性",
                Style.EMPTY.applyFormat(ChatFormatting.BLUE), "%.0f%%", true, TraditionalTooltip.manaDefence));
        put(System.identityHashCode(Utils.attackDamage), new ToolTipParameter("物理攻击",
                Style.EMPTY.applyFormat(ChatFormatting.AQUA), "%.0f", false, TraditionalTooltip.attackDamage));
        put(System.identityHashCode(Utils.movementSpeedWithoutBattle), new ToolTipParameter("脱战移动速度",
                CustomStyle.styleOfFlexible, "%.0f%%", true, TraditionalTooltip.movementSpeed));
        put(System.identityHashCode(Utils.movementSpeedCommon), new ToolTipParameter("移动速度",
                CustomStyle.styleOfFlexible, "%.0f%%", true, TraditionalTooltip.movementSpeed));
        put(System.identityHashCode(Utils.manaDamage), new ToolTipParameter("魔法攻击",
                CustomStyle.styleOfMana, "%.0f", false, TraditionalTooltip.manaDamage));
        put(System.identityHashCode(Utils.manaRecover), new ToolTipParameter("法力回复",
                CustomStyle.styleOfMana, "%.0f", false, TraditionalTooltip.manaRecover));
        put(System.identityHashCode(Utils.healthRecover), new ToolTipParameter("生命回复",
                CustomStyle.styleOfLife, "%.0f", false, TraditionalTooltip.healthRecover));
        put(System.identityHashCode(Utils.maxHealth), new ToolTipParameter("最大生命值",
                Style.EMPTY.applyFormat(ChatFormatting.GREEN), "%.0f", false, TraditionalTooltip.maxHealth));
        put(System.identityHashCode(Utils.defence), new ToolTipParameter("基础护甲",
                Style.EMPTY.applyFormat(ChatFormatting.GRAY), "%.0f", false, TraditionalTooltip.defence));
        put(System.identityHashCode(Utils.coolDownDecrease), new ToolTipParameter("技能急速",
                Style.EMPTY.applyFormat(ChatFormatting.AQUA), "%.0f", true, TraditionalTooltip.releaseSpeed));
        put(System.identityHashCode(Utils.critDamage), new ToolTipParameter("暴击伤害",
                Style.EMPTY.applyFormat(ChatFormatting.BLUE), "%.0f%%", true, TraditionalTooltip.critDamage));
        put(System.identityHashCode(Utils.critRate), new ToolTipParameter("暴击几率",
                Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE), "%.0f%%", true, TraditionalTooltip.critRate));
        put(System.identityHashCode(Utils.healEffectUp), new ToolTipParameter("治疗强度",
                CustomStyle.styleOfLife, "%.0f%%", true, TraditionalTooltip.healthStrength));
        put(System.identityHashCode(Utils.manaHealthSteal), new ToolTipParameter("法术吸血",
                CustomStyle.styleOfBloodMana, "%.0f%%", true, TraditionalTooltip.manaHealthSteal));
        put(System.identityHashCode(Utils.defencePenetration0), new ToolTipParameter("护甲穿透",
                Style.EMPTY.applyFormat(ChatFormatting.GRAY), "%.0f", false, TraditionalTooltip.defencePenetration0));
        put(System.identityHashCode(Utils.manaPenetration0), new ToolTipParameter("法术穿透",
                Style.EMPTY.applyFormat(ChatFormatting.BLUE), "%.0f", false, TraditionalTooltip.manaPenetration0));
        put(System.identityHashCode(Utils.expUp), new ToolTipParameter("经验加成",
                Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE), "%.0f%%", true, TraditionalTooltip.expUp));
        put(System.identityHashCode(Utils.defencePenetration), new ToolTipParameter("护甲穿透",
                Style.EMPTY.applyFormat(ChatFormatting.GRAY), "%.0f%%", true, TraditionalTooltip.defencePenetration));
        put(System.identityHashCode(Utils.manaPenetration), new ToolTipParameter("魔法穿透",
                Style.EMPTY.applyFormat(ChatFormatting.BLUE), "%.0f%%", true, TraditionalTooltip.manaPenetration));
        put(System.identityHashCode(Utils.healthSteal), new ToolTipParameter("生命偷取",
                Style.EMPTY.applyFormat(ChatFormatting.RED), "%.0f%%", true, TraditionalTooltip.healthSteal));
        put(System.identityHashCode(Utils.manaDefence), new ToolTipParameter("魔法抗性",
                Style.EMPTY.applyFormat(ChatFormatting.BLUE), "%.0f", false, TraditionalTooltip.manaDefence));
    }};

    // 新的属性描述模板，仅需按照参数进行配置即可，但是需要注意的是，仅接受不能被强化增幅的属性。
    public static int newAttributeCommonDescriptionTemplate(int index, ResourceLocation resourceLocation, Map<Item, Double> map,
                                                            String curiosAttributeTag, String attributeName,
                                                            Style style, String valueFormat, boolean isPercent,
                                                            ItemStack itemStack,
                                                            List<Either<FormattedText, TooltipComponent>> components) {
        Item item = itemStack.getItem();
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        if (map.containsKey(item) || data.contains(curiosAttributeTag)) {
            double value;

            if (map.containsKey(item))
                value = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, map);
            else if (item instanceof RandomCurios)
                value = data.getDouble(curiosAttributeTag) * CastleCurios.attributeValueMap.get(curiosAttributeTag);
            else value = data.getInt(curiosAttributeTag);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" " + attributeName).withStyle(style).
                    append(Component.literal((value > 0 ? "+" : "") + String.format(valueFormat, value * (isPercent ? 100 : 1)))
                            .withStyle(value > 0 ? ChatFormatting.WHITE : ChatFormatting.RED)));

            double gemsValue = GemAttributes.getGemsAttributeModifier(data, map);

            if (gemsValue != 0) {
                mutableComponent.append(Component.literal(" + " + String.format(valueFormat, gemsValue * (isPercent ? 100 : 1))).withStyle(style));
            }

            index++;
            components.add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, resourceLocation)));
        }

        return index;
    }

    public static int descriptionXpLevelAttributeTemplate(int index, ResourceLocation resourceLocation,
                                                          Map<Item, Double> map, String curiosAttributeTag,
                                                          String attributeName, Style style, String valueFormat,
                                                          boolean isPercent, ItemStack itemStack,
                                                          List<Either<FormattedText, TooltipComponent>> components,
                                                          LocalPlayer localPlayer, boolean acceptForgeAndProficiency,
                                                          Style forgeValueStyle) {
        Item item = itemStack.getItem();
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        if (map.containsKey(item) || data.contains(curiosAttributeTag)) {
            double value;
            int xpLevel = localPlayer.experienceLevel;
            if (map.containsKey(item)) {
                value = map.get(item);
            } else {
                if (item instanceof RandomCurios) {
                    value = data.getDouble(curiosAttributeTag) * CastleCurios.attributeValueMap.get(curiosAttributeTag);
                } else {
                    value = data.getDouble(curiosAttributeTag);
                }
            }

            MutableComponent mutableComponent = Component.literal("");
            double totalValue = value * xpLevel;
            if (localPlayer.tickCount % 60 < 30) {
                mutableComponent.append(Component.literal(" " + attributeName).withStyle(style).
                        append(Component.literal((value > 0 ? "+" : "")
                                        + String.format(valueFormat, value * (isPercent ? 100 : 1)))
                                .withStyle(value > 0 ? ChatFormatting.WHITE : ChatFormatting.RED)).
                        append(Component.literal(" x ").withStyle(ChatFormatting.DARK_PURPLE)).
                        append(Component.literal(xpLevel + "").withStyle(ChatFormatting.LIGHT_PURPLE)));
            } else {
                mutableComponent.append(Component.literal(" " + attributeName).withStyle(style).
                        append(Component.literal((totalValue > 0 ? "+" : "") + String.format(valueFormat, totalValue * (isPercent ? 100 : 1)))
                                .withStyle(totalValue > 0 ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.RED)));
            }
            if (acceptForgeAndProficiency) {
                double exForgingValue = 0;
                if (data.contains(StringUtils.ForgeLevel)) {
                    exForgingValue = Compute.forgingValue(data, totalValue);
                }
                double exProficiencyValue = 0;
                if (data.contains(StringUtils.KillCount.KillCount)) {
                    exProficiencyValue = totalValue * 0.5 *
                            Math.min(1, (data.getInt(StringUtils.KillCount.KillCount) / 100000.0));
                }
                ChatFormatting[] chatFormattings = {
                        ChatFormatting.GREEN, ChatFormatting.AQUA, ChatFormatting.YELLOW,
                        ChatFormatting.LIGHT_PURPLE, ChatFormatting.RED
                };

                if (exProficiencyValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", exProficiencyValue)).
                            withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt(StringUtils.KillCount.KillCount) / 20000, 4)]));
                }

                if (exForgingValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", exForgingValue)).withStyle(forgeValueStyle)).
                            append(Component.literal("⮅").withStyle(CustomStyle.styleOfPower));
                }
            }

            index++;
            components.add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, resourceLocation)));
        }

        return index;
    }

    public static void BasicAttributeCommonDescription(List<Component> components, ItemStack itemStack) {
        int forgeQuality = ForgeEquipUtils.getForgeQualityOnEquip(itemStack);
        if (forgeQuality != -1) {
            components.add(Component.literal("").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("θ-锻造品质: ").withStyle(CustomStyle.styleOfGold)).
                    append(ForgeEquipUtils.description.get(forgeQuality)));
        }
    }

    private static void handleProficiency(CompoundTag data, double baseValue, MutableComponent mutableComponent) {
        double ExDamageProficiency = 0;
        if (data.contains(StringUtils.KillCount.KillCount))
            ExDamageProficiency = baseValue * 0.5 * Math.min(1, (data.getInt(StringUtils.KillCount.KillCount) / 100000.0));
        ChatFormatting[] chatFormattings = {
                ChatFormatting.GREEN,
                ChatFormatting.AQUA,
                ChatFormatting.YELLOW,
                ChatFormatting.LIGHT_PURPLE,
                ChatFormatting.RED
        };
        if (ExDamageProficiency != 0) {
            mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageProficiency)).
                    withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt(StringUtils.KillCount.KillCount) / 20000, 4)]));
        }
    }

    private static void handleForge(CompoundTag data, double baseValue, MutableComponent mutableComponent) {
        double ExDamageForging = 0;
        if (data.contains(StringUtils.ForgeLevel)) ExDamageForging = Compute.forgingValue(data, baseValue);
        if (ExDamageForging != 0) {
            mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageForging)).withStyle(ChatFormatting.YELLOW)).
                    append(Component.literal("⮅").withStyle(CustomStyle.styleOfPower));
        }
    }

    private static void handleRandomAttributeRate(ItemStack equip, String attributeType, MutableComponent mutableComponent) {
        Item curios = equip.getItem();
        if (curios instanceof RandomCurios randomCurios) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            double fullRate = randomCurios.rate();
            double rate = data.getDouble(attributeType);
            double percent = rate / fullRate;

            Style[] styles = new Style[]{CustomStyle.styleOfPlain, CustomStyle.styleOfWater,
                    CustomStyle.styleOfVolcano, CustomStyle.styleOfPower, Style.EMPTY.applyFormat(ChatFormatting.RED)};
            mutableComponent.append(Te.m(" [").
                    append(Te.m(String.format("%.2f%%", percent * 100), styles[Math.min(4, (int) (percent / 0.3))])).
                    append(Te.m("]")));
        }
    }
}

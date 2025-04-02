package fun.wraq.common.attribute;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Either;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.WraqPickaxe;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.forge.ForgeTemplate;
import fun.wraq.render.gui.illustrate.Illustrate;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.render.toolTip.NewTooltip;
import fun.wraq.render.toolTip.TraditionalTooltip;
import fun.wraq.series.gems.WraqGem;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import fun.wraq.series.worldsoul.SoulEquipAttribute;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        if (event.getTooltipElements().size() < 5) return;
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Item item = itemStack.getItem();

        if (data.contains(StringUtils.ForgeLevel)) {
            int forgeLevel = data.getInt(StringUtils.ForgeLevel);

            if (data.contains(StringUtils.QingMingForgePaper)) ++forgeLevel;
            if (data.contains(StringUtils.LabourDayForgePaper)) ++forgeLevel;

            Style[] styles = {CustomStyle.styleOfMine, CustomStyle.styleOfGold,
                    Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE), CustomStyle.styleOfWorld};

            Style style = styles[Math.min(3, Math.max(0, (forgeLevel - 1) / 8))];

            index++;
            if (item instanceof ForgeTemplate) {
                --index;
            }
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(
                    Te.s(" 强化等级 ", CustomStyle.styleOfPower, "" + forgeLevel, style,
                            data.contains(StringUtils.QingMingForgePaper) ? "「清符+1」" : "", CustomStyle.styleOfLife),
                    TraditionalTooltip.forge)));
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.attackDamage, Utils.xpLevelAttackDamage,
                StringUtils.RandomCuriosAttribute.xpLevelAttackDamage, "物理攻击", Style.EMPTY.applyFormat(ChatFormatting.AQUA),
                1, false, itemStack, event.getTooltipElements(), localPlayer, true, Style.EMPTY.applyFormat(ChatFormatting.YELLOW));

        if (Utils.attackDamage.containsKey(item) || data.contains(StringUtils.RandomAttribute.attackDamage)
                || data.contains(StringUtils.RandomCuriosAttribute.attackDamage)) {
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
                if (Utils.attackDamage.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.attackDamage)) {
                    if (Utils.attackDamage.containsKey(item))
                        baseDamage = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.attackDamage);
                    else if (item instanceof RandomCurios)
                        baseDamage = data.getDouble(StringUtils.RandomCuriosAttribute.attackDamage) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.attackDamage);
                    else baseDamage = data.getInt(StringUtils.RandomCuriosAttribute.attackDamage);
                } else
                    baseDamage = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.attackDamage);

                if (baseDamage != 0) {
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 物理攻击").withStyle(ChatFormatting.AQUA).
                            append(Component.literal(" " + String.format("%.0f", baseDamage)).withStyle(ChatFormatting.WHITE)));

                    handleExBaseAttributeValue(itemStack, mutableComponent, Utils.attackDamage);
                    handleForge(data, baseDamage, mutableComponent);
                    handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.attackDamage, mutableComponent);

                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.attackDamage)));
                }
            }
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.attackDamage, Utils.percentAttackDamageEnhance,
                StringUtils.RandomCuriosAttribute.percentAttackDamage, "物理攻击",
                Style.EMPTY.applyFormat(ChatFormatting.AQUA), 1, true, itemStack, false, null, event.getTooltipElements());

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.manaDamage, Utils.xpLevelManaDamage,
                StringUtils.RandomCuriosAttribute.xpLevelManaDamage, "魔法攻击", Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE),
                1, false, itemStack, event.getTooltipElements(), localPlayer, true, Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE));

        if (Utils.manaDamage.containsKey(item) || data.contains(StringUtils.RandomAttribute.manaDamage)
                || data.contains(StringUtils.RandomCuriosAttribute.manaDamage)) {
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
                if (Utils.manaDamage.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.manaDamage)) {
                    if (Utils.manaDamage.containsKey(item))
                        baseDamage = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.manaDamage);
                    else if (item instanceof RandomCurios)
                        baseDamage = data.getDouble(StringUtils.RandomCuriosAttribute.manaDamage) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.manaDamage);
                    else baseDamage = data.getInt(StringUtils.RandomCuriosAttribute.manaDamage);
                } else
                    baseDamage = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.manaDamage);

                if (baseDamage != 0) {
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 法术攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal(" " + String.format("%.0f", baseDamage)).withStyle(ChatFormatting.WHITE)));

                    handleExBaseAttributeValue(itemStack, mutableComponent, Utils.manaDamage);
                    handleForge(data, baseDamage, mutableComponent);
                    handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.manaDamage, mutableComponent);

                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaDamage)));
                }
            }
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.manaDamage, Utils.percentManaDamageEnhance,
                StringUtils.RandomCuriosAttribute.percentManaDamageEnhance, "魔法攻击",
                CustomStyle.styleOfMana, 1, true, itemStack,
                false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.attackSpeed, Utils.attackSpeedEnhance,
                "", "攻击速度",
                CustomStyle.styleOfFlexible, 0, true, itemStack,
                false, null, event.getTooltipElements());

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.defence, Utils.xpLevelDefence,
                StringUtils.RandomCuriosAttribute.xpLevelDefence, "基础护甲", Style.EMPTY.applyFormat(ChatFormatting.GRAY),
                1, false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.defence.containsKey(item) || data.contains(StringUtils.RandomAttribute.defence)
                || data.contains(StringUtils.RandomCuriosAttribute.defence)) {
            double defence;
            if (Utils.defence.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.defence)) {
                if (Utils.defence.containsKey(item))
                    defence = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.defence);
                else if (item instanceof RandomCurios)
                    defence = data.getDouble(StringUtils.RandomCuriosAttribute.defence) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.defence);
                else defence = data.getInt(StringUtils.RandomCuriosAttribute.defence);
            } else defence = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.defence);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 基础护甲").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+" + getDecimal(defence, 1)).withStyle(ChatFormatting.WHITE)));

            handleExBaseAttributeValue(itemStack, mutableComponent, Utils.defence);
            handleForge(data, defence, mutableComponent);
            handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.defence, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.defence)));
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.defence, Utils.percentDefenceEnhance,
                StringUtils.RandomCuriosAttribute.percentDefenceEnhance, "基础护甲",
                Style.EMPTY.applyFormat(ChatFormatting.GRAY), 1, true, itemStack,
                false, null, event.getTooltipElements());

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.manaDefence, Utils.xpLevelManaDefence,
                StringUtils.RandomCuriosAttribute.xpLevelManaDefence, "魔法抗性", Style.EMPTY.applyFormat(ChatFormatting.BLUE),
                1, false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.manaDefence.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.manaDefence)) {
            double manaDefence = 0;
            if (Utils.manaDefence.containsKey(item))
                manaDefence = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.manaDefence);
            else if (item instanceof RandomCurios)
                manaDefence = data.getDouble(StringUtils.RandomCuriosAttribute.manaDefence) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.manaDefence);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 魔法抗性").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+" + getDecimal(manaDefence, 1)).withStyle(ChatFormatting.WHITE)));

            handleForge(data, manaDefence, mutableComponent);
            handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.manaDefence, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaDefence)));
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.manaDefence, Utils.percentManaDefenceEnhance,
                StringUtils.RandomCuriosAttribute.percentManaDefenceEnhance, "魔法抗性",
                Style.EMPTY.applyFormat(ChatFormatting.BLUE), 1, true, itemStack, false, null, event.getTooltipElements());

        if (Utils.maxHealth.containsKey(item) || data.contains(StringUtils.RandomAttribute.maxHealth)
                || data.contains(StringUtils.RandomCuriosAttribute.maxHealth)) {
            double maxHealth;
            if (Utils.maxHealth.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.maxHealth)) {
                if (Utils.maxHealth.containsKey(item))
                    maxHealth = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.maxHealth);
                else if (item instanceof RandomCurios)
                    maxHealth = data.getDouble(StringUtils.RandomCuriosAttribute.maxHealth) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.maxHealth);
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

            handleExBaseAttributeValue(itemStack, mutableComponent, Utils.maxHealth);
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

            handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.maxHealth, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.maxHealth)));
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.maxHealth, Utils.percentMaxHealthEnhance,
                StringUtils.RandomCuriosAttribute.percentMaxHealthEnhance, "最大生命值",
                Style.EMPTY.applyFormat(ChatFormatting.GREEN), 1, true, itemStack, false,
                null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.healthRecover, Utils.healthRecover,
                StringUtils.RandomCuriosAttribute.healthRecover, "生命回复",
                Style.EMPTY.applyFormat(ChatFormatting.GREEN), 1, false, itemStack, true,
                CustomStyle.styleOfLife, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.healthRecover, Utils.percentHealthRecover,
                StringUtils.RandomCuriosAttribute.percentHealthRecover, "生命回复",
                Style.EMPTY.applyFormat(ChatFormatting.GREEN), 2, true, itemStack, false,
                CustomStyle.styleOfLife, event.getTooltipElements());

        if (Utils.defencePenetration.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.defencePenetration)) {

            double DefencePenetration;
            if (Utils.defencePenetration.containsKey(item)) DefencePenetration = Utils.defencePenetration.get(item);
            else if (item instanceof RandomCurios)
                DefencePenetration = data.getDouble(StringUtils.RandomCuriosAttribute.defencePenetration) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.defencePenetration);
            else DefencePenetration = data.getInt(StringUtils.RandomCuriosAttribute.defencePenetration);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+" + String.format("%.0f%%", DefencePenetration * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.defencePenetration, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.defencePenetration)));

        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.defencePenetration0, Utils.xpLevelDefencePenetration0,
                StringUtils.RandomCuriosAttribute.xpLevelDefencePenetration0, "护甲穿透", Style.EMPTY.applyFormat(ChatFormatting.GRAY),
                1, false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.defencePenetration0.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.defencePenetration0)
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
                    defencePenetration0 = data.getDouble(StringUtils.RandomCuriosAttribute.defencePenetration0) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.defencePenetration0);
                else defencePenetration0 = data.getInt(StringUtils.RandomCuriosAttribute.defencePenetration0);
                if (data.contains(StringUtils.RandomAttribute.defencePenetration0))
                    defencePenetration0 += data.getDouble(StringUtils.RandomAttribute.defencePenetration0);

                if (defencePenetration0 != 0) {
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("+" + getDecimal(defencePenetration0, 1))
                                    .withStyle(ChatFormatting.WHITE)));

                    handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.defencePenetration0, mutableComponent);

                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.defencePenetration0)));
                }
            }
        }

        if (Utils.critRate.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.critRate)
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
                    critRate = data.getDouble(StringUtils.RandomCuriosAttribute.critRate) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.critRate);
                else critRate = data.getInt(StringUtils.RandomCuriosAttribute.critRate);
                if (data.contains(StringUtils.RandomAttribute.critRate))
                    critRate += data.getDouble(StringUtils.RandomAttribute.critRate);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.1f%%", critRate * 100)).withStyle(ChatFormatting.WHITE)));

                handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.critRate, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.critRate)));
            }
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.critDamage, Utils.xpLevelCritDamage,
                StringUtils.RandomCuriosAttribute.xpLevelCritDamage, "暴击伤害", Style.EMPTY.applyFormat(ChatFormatting.BLUE),
                0, true, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.critDamage.containsKey(item)
                || data.contains(StringUtils.RandomCuriosAttribute.critDamage)
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
                if (Utils.critDamage.containsKey(item)) critDamage =
                        ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.critDamage, null, false);
                else if (item instanceof RandomCurios)
                    critDamage = data.getDouble(StringUtils.RandomCuriosAttribute.critDamage)
                            * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.critDamage);
                else critDamage = data.getInt(StringUtils.RandomCuriosAttribute.critDamage);
                if (data.contains(StringUtils.RandomAttribute.critDamage))
                    critDamage += data.getDouble(StringUtils.RandomAttribute.critDamage);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 暴击伤害").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+" + String.format("%.0f%%", critDamage * 100)).withStyle(ChatFormatting.WHITE)));

                handleExBaseAttributeValue(itemStack, mutableComponent, Utils.critDamage, 0, true);
                handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.critDamage, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.critDamage)));
            }
        }

        if (Utils.healthSteal.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.healthSteal)
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
                    healSteal = data.getDouble(StringUtils.RandomCuriosAttribute.healthSteal) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.healthSteal);
                else healSteal = data.getInt(StringUtils.RandomCuriosAttribute.healthSteal);
                if (data.contains(StringUtils.RandomAttribute.healthSteal))
                    healSteal += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.healthSteal);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 生命偷取").withStyle(ChatFormatting.RED).
                        append(Component.literal("+" + String.format("%.0f‰", healSteal * 100)).withStyle(ChatFormatting.WHITE)));

                handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.healthSteal, mutableComponent);

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

        if (Utils.maxMana.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.maxMana)
                || data.contains(StringUtils.RandomCuriosAttribute.maxMana)) {
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
                    maxMana = data.getDouble(StringUtils.RandomCuriosAttribute.maxMana) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.maxMana);
                if (data.contains(StringUtils.RandomAttribute.maxMana))
                    maxMana = data.getDouble(StringUtils.RandomAttribute.maxMana);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.0f", maxMana)).withStyle(ChatFormatting.WHITE)));

                handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.maxMana, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.maxMana)));
            }
        }

        if (Utils.manaPenetration.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.manaPenetration)) {
            double ManaPenetration;
            if (Utils.manaPenetration.containsKey(item)) ManaPenetration = Utils.manaPenetration.get(item);
            else if (item instanceof RandomCurios)
                ManaPenetration = data.getDouble(StringUtils.RandomCuriosAttribute.manaPenetration) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.manaPenetration);
            else ManaPenetration = data.getInt(StringUtils.RandomCuriosAttribute.manaPenetration);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+" + String.format("%.0f%%", ManaPenetration * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.manaPenetration, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaPenetration)));
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.manaPenetration0, Utils.xpLevelManaPenetration0,
                StringUtils.RandomCuriosAttribute.xpLevelManaPenetration0, "魔法穿透", Style.EMPTY.applyFormat(ChatFormatting.BLUE),
                1, false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.manaPenetration0.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.manaPenetration0)
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
                    manaPenetration0 = data.getDouble(StringUtils.RandomCuriosAttribute.manaPenetration0) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.manaPenetration0);
                else manaPenetration0 = data.getInt(StringUtils.RandomCuriosAttribute.manaPenetration0);
                if (data.contains(StringUtils.RandomAttribute.manaPenetration0))
                    manaPenetration0 += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.manaPenetration0);

                if (manaPenetration0 != 0) {
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                            append(Component.literal("+" + getDecimal(manaPenetration0, 1)).withStyle(ChatFormatting.WHITE)));

                    handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.manaPenetration0, mutableComponent);

                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaPenetration0)));
                }
            }
        }

        if (Utils.manaRecover.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.manaRecover)
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
                    manaRecover = data.getDouble(StringUtils.RandomCuriosAttribute.manaRecover) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.manaRecover);
                else manaRecover = data.getInt(StringUtils.RandomCuriosAttribute.manaRecover);
                if (data.contains(StringUtils.RandomAttribute.manaRecover))
                    manaRecover += data.getDouble(StringUtils.RandomAttribute.manaRecover);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.0f", manaRecover)).withStyle(ChatFormatting.WHITE)));

                handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.manaRecover, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaRecover)));
            }
        }

        if (Utils.coolDownDecrease.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.coolDown)
                || data.contains(StringUtils.RandomAttribute.coolDown)) {
            double coolDown;
            if (Utils.coolDownDecrease.containsKey(item)) coolDown = Utils.coolDownDecrease.get(item);
            else if (item instanceof RandomCurios)
                coolDown = data.getDouble(StringUtils.RandomCuriosAttribute.coolDown) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.coolDown);
            else coolDown = data.getInt(StringUtils.RandomCuriosAttribute.coolDown);
            if (data.contains(StringUtils.RandomAttribute.coolDown))
                coolDown = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.coolDown);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 技能急速").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" " + String.format("%.0f", coolDown * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.coolDown, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.releaseSpeed)));
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.movementSpeed, Utils.movementSpeedCommon,
                StringUtils.RandomCuriosAttribute.commonMovementSpeed, "移动速度",
                Style.EMPTY.applyFormat(ChatFormatting.GREEN), 0, true, itemStack, true, CustomStyle.styleOfFlexible, event.getTooltipElements());

        if (Utils.movementSpeedWithoutBattle.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.movementSpeed)
                || data.contains(StringUtils.RandomAttribute.movementSpeedWithoutBattle)) {
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
                    MovementSpeed = data.getDouble(StringUtils.RandomCuriosAttribute.movementSpeed) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.movementSpeed);
                else MovementSpeed = data.getInt(StringUtils.RandomCuriosAttribute.movementSpeed);
                if (data.contains(StringUtils.RandomAttribute.movementSpeedWithoutBattle))
                    MovementSpeed += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.movementSpeedWithoutBattle);

                MutableComponent mutableComponent = Component.literal("");

                if (MovementSpeed > 0) {
                    mutableComponent.append(Component.literal(" 脱战移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("+" + String.format("%.0f%%", MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)));

                }
                if (MovementSpeed < 0) {
                    mutableComponent.append(Component.literal(" 脱战移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("-" + String.format("%.0f%%", -MovementSpeed * 100)).withStyle(ChatFormatting.RED)));

                }

                handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.movementSpeed, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.movementSpeed)));
            }
        }
        if (Utils.expUp.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.expUp)) {
            double ExpUp;
            if (item instanceof RandomCurios)
                ExpUp = data.getDouble(StringUtils.RandomCuriosAttribute.expUp) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.expUp);
            else ExpUp = Utils.expUp.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+" + String.format("%.0f%%", ExpUp * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.expUp, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.expUp)));
        }
        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.swiftnessUp,
                Utils.swiftnessUp, StringUtils.RandomCuriosAttribute.swiftnessUp, "迅捷加成",
                CustomStyle.styleOfFlexible, 1, false, itemStack, false, null, event.getTooltipElements());
        if (Utils.manaHealthSteal.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.manaHealthSteal)) {
            double ManaHealSteal;
            if (Utils.manaHealthSteal.containsKey(item)) ManaHealSteal = Utils.manaHealthSteal.get(item);
            else if (item instanceof RandomCurios)
                ManaHealSteal = data.getDouble(StringUtils.RandomCuriosAttribute.manaHealthSteal) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.manaHealthSteal);
            else ManaHealSteal = data.getInt(StringUtils.RandomCuriosAttribute.manaHealthSteal);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 法术吸血").withStyle(CustomStyle.styleOfBloodMana).
                    append(Component.literal("+" + String.format("%.0f‰", ManaHealSteal * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.manaHealthSteal, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaHealthSteal)));
        }
        if (Utils.healingAmplification.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.healEffectUp)) {
            double HealEffectUp;
            if (Utils.healingAmplification.containsKey(item)) HealEffectUp = Utils.healingAmplification.get(item);
            else if (item instanceof RandomCurios)
                HealEffectUp = data.getDouble(StringUtils.RandomCuriosAttribute.healEffectUp) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomCuriosAttribute.healEffectUp);
            else HealEffectUp = data.getInt(StringUtils.RandomCuriosAttribute.healEffectUp);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 治疗强度").withStyle(CustomStyle.styleOfHealth).
                    append(Component.literal("+" + String.format("%.0f%%", HealEffectUp * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomCuriosAttribute.healEffectUp, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.healthStrength)));
        }
        if (Utils.luckyUp.containsKey(item) || data.contains(StringUtils.RandomCuriosAttribute.LuckyUp)) {
            double LuckyUp;
            if (Utils.luckyUp.containsKey(item)) LuckyUp = Utils.luckyUp.get(item);
            else LuckyUp = data.getInt(StringUtils.RandomCuriosAttribute.LuckyUp);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 幸运加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal(" " + String.format("%.0f%%", LuckyUp * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.luckyUp)));
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.stoneElement, WraqPickaxe.mineSpeed,
                "EmptyTypeAttribute", "挖掘速度",
                Style.EMPTY.applyFormat(ChatFormatting.GRAY), 0, true, itemStack,
                false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.toughness, Utils.toughness,
                StringUtils.RandomCuriosAttribute.toughness, "韧性",
                CustomStyle.styleOfEnd, 1, true, itemStack,
                false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.element, Utils.elementStrength,
                null, "元素强度",
                CustomStyle.styleOfWorld, 0, true, itemStack,
                false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index,
                TraditionalTooltip.finalDamageEnhance, Utils.finalDamageEnhance,
                StringUtils.RandomCuriosAttribute.finalDamageEnhance, "最终伤害加成",
                CustomStyle.styleOfDemon, 1, true, itemStack,
                false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index,
                TraditionalTooltip.attackDamageEnhance, Utils.attackDamageEnhance,
                StringUtils.RandomCuriosAttribute.attackDamageEnhance, "物理伤害加成",
                CustomStyle.styleOfPower, 1, true, itemStack,
                false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index,
                TraditionalTooltip.manaDamageEnhance, Utils.manaDamageEnhance,
                StringUtils.RandomCuriosAttribute.manaDamageEnhance, "魔法伤害加成",
                CustomStyle.styleOfMana, 1, true, itemStack,
                false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index,
                TraditionalTooltip.commonDamageEnhance, Utils.commonDamageEnhance,
                StringUtils.RandomCuriosAttribute.commonDamageEnhance, "普通伤害加成",
                CustomStyle.styleOfMoon, 1, true, itemStack,
                false, null, event.getTooltipElements());

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
                    if (!wraqGem.getAttributeMapValues().isEmpty()) {
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
                    if (wraqGem instanceof WraqPassiveGem wraqPassiveGem) {
                        for (int i = 0; i < wraqPassiveGem.getAdditionDescription().size(); i++) {
                            event.getTooltipElements().add(index + i, Either.left(wraqPassiveGem.getAdditionDescription().get(i)));
                        }
                        index += wraqPassiveGem.getAdditionDescription().size();
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
                for (int i = 0 ; i < data.getInt("newSlot") ; i ++) {
                    component.append(Component.literal("「").withStyle(ChatFormatting.AQUA).
                            append(Component.literal(" ")).
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
        put(System.identityHashCode(Utils.healingAmplification), new ToolTipParameter("治疗强度",
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
        put(System.identityHashCode(WraqPickaxe.mineSpeed), new ToolTipParameter("挖掘速度",
                Style.EMPTY.applyFormat(ChatFormatting.GRAY), "%.0f%%", true, TraditionalTooltip.stoneElement));
        put(System.identityHashCode(Utils.elementStrength), new ToolTipParameter("元素强度",
                CustomStyle.styleOfWorld, "%.0f%%", true, TraditionalTooltip.element));
    }};

    // 新的属性描述模板，仅需按照参数进行配置即可，但是需要注意的是，仅接受不能被强化增幅的属性。
    public static int newAttributeCommonDescriptionTemplate(int index, ResourceLocation resourceLocation, Map<Item, Double> map,
                                                            String curiosAttributeTag, String attributeName,
                                                            Style style, int decimalScale, boolean isPercent,
                                                            ItemStack itemStack, boolean computeForge,
                                                            Style forgeValueStyle,
                                                            List<Either<FormattedText, TooltipComponent>> components) {
        Item item = itemStack.getItem();
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        double traditionalEquipBaseValue
                = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, map, null, computeForge);

        if (map.containsKey(item) || data.contains(curiosAttributeTag)
                || traditionalEquipBaseValue != 0) {
            double value;

            if (map.containsKey(item) || traditionalEquipBaseValue != 0) {
                value = traditionalEquipBaseValue;
            }
            else if (item instanceof RandomCurios)
                value = data.getDouble(curiosAttributeTag) * RandomCuriosAttributesUtil.attributeValueMap.get(curiosAttributeTag);
            else value = data.getInt(curiosAttributeTag);

            String percent = isPercent ? "%" : "";
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" " + attributeName).withStyle(style).
                    append(Component.literal((value > 0 ? "+" : "") + getDecimal(value * (isPercent ? 100 : 1), decimalScale) + percent)
                            .withStyle(value > 0 ? ChatFormatting.WHITE : ChatFormatting.RED)));

            handleExBaseAttributeValue(itemStack, mutableComponent, map, decimalScale, isPercent);

            if (computeForge) {
                double exForgingValue = 0;
                if (data.contains(StringUtils.ForgeLevel)) {
                    exForgingValue = Compute.forgingValue(data, value);
                }

                if (exForgingValue != 0) {
                    mutableComponent.append(Component.literal(" + " + getDecimal(exForgingValue * (isPercent ? 100 : 1), decimalScale) + percent).withStyle(forgeValueStyle)).
                            append(Component.literal("⮅").withStyle(CustomStyle.styleOfPower));
                }
            }

            handleRandomAttributeRate(itemStack, curiosAttributeTag, mutableComponent);

            index++;
            components.add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, resourceLocation)));
        }

        return index;
    }

    public static int descriptionXpLevelAttributeTemplate(int index, ResourceLocation resourceLocation,
                                                          Map<Item, Double> map, String curiosAttributeTag,
                                                          String attributeName, Style style, int decimalScale,
                                                          boolean isPercent, ItemStack itemStack,
                                                          List<Either<FormattedText, TooltipComponent>> components,
                                                          LocalPlayer localPlayer, boolean acceptForge,
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
                    value = data.getDouble(curiosAttributeTag) * RandomCuriosAttributesUtil.attributeValueMap.get(curiosAttributeTag);
                } else {
                    value = data.getDouble(curiosAttributeTag);
                }
            }

            MutableComponent mutableComponent = Component.literal("");
            double totalValue = value * xpLevel;
            if (localPlayer.tickCount % 60 < 30) {
                mutableComponent.append(Component.literal(" " + attributeName).withStyle(style).
                        append(Component.literal((value > 0 ? "+" : "")
                                        + getDecimal(value * (isPercent ? 100 : 1), 1)
                                        + (isPercent ? "%" : ""))
                                .withStyle(value > 0 ? ChatFormatting.WHITE : ChatFormatting.RED)).
                        append(Component.literal(" x ").withStyle(ChatFormatting.DARK_PURPLE)).
                        append(Component.literal(xpLevel + "").withStyle(ChatFormatting.LIGHT_PURPLE)));
            } else {
                mutableComponent.append(Component.literal(" " + attributeName).withStyle(style).
                        append(Component.literal((totalValue > 0 ? "+" : "")
                                        + getDecimal(totalValue * (isPercent ? 100 : 1), 1)
                                        + (isPercent ? "%" : ""))
                                .withStyle(totalValue > 0 ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.RED)));
            }
            if (acceptForge) {
                double exForgingValue = 0;
                if (data.contains(StringUtils.ForgeLevel)) {
                    exForgingValue = Compute.forgingValue(data, totalValue);
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
                    append(ForgeEquipUtils.getDescription(forgeQuality)));
            if (itemStack.getTagElement(Utils.MOD_ID) != null
                    && itemStack.getTagElement(Utils.MOD_ID).contains(Illustrate.DISPLAY_FLAG)
                    && !Utils.offHandTag.containsKey(itemStack.getItem())) {
                components.add(Te.m(" 按住左CTRL停止品质滚动", ChatFormatting.AQUA));
            }
        }
    }

    private static void handleExBaseAttributeValue(ItemStack itemStack, MutableComponent mutableComponent, Map<Item, Double> map) {
        handleExBaseAttributeValue(itemStack, mutableComponent, map, 0, false);
    }

    private static void handleExBaseAttributeValue(ItemStack itemStack, MutableComponent mutableComponent,
                                                   Map<Item, Double> map, int decimalScale, boolean isPercent) {
        double exBaseAttributeValue = ExBaseAttributeValueEquip.getExBaseAttributeValue(itemStack, map);
        if (exBaseAttributeValue != 0 && itemStack.getItem() instanceof ExBaseAttributeValueEquip equip) {
            mutableComponent.append(Te.s("(", equip.getQuoteStyle(),
                    getDecimal(exBaseAttributeValue * (isPercent ? 100 : 1), decimalScale)
                            + (isPercent ? "%" : ""), equip.getExValueStyle(), ")", equip.getQuoteStyle()));
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
            double rate = data.getDouble(attributeType);
            if (rate == 0) {
                return;
            }
            double fullRate = randomCurios.fullRate();
            double fullRateGetByTag = RandomCurios.getFullRateByTag(equip);
            if (fullRateGetByTag != 0) {
                fullRate = fullRateGetByTag;
            }
            double percent = rate / fullRate;
            Style[] styles = new Style[]{CustomStyle.styleOfPlain, CustomStyle.styleOfWater,
                    CustomStyle.styleOfVolcano, CustomStyle.styleOfPower, Style.EMPTY.applyFormat(ChatFormatting.RED)};
            mutableComponent.append(Te.m(" [").
                    append(Te.m(String.format("%.2f%%", percent * 100), styles[Math.min(4, (int) (percent / 0.3))])).
                    append(Te.m("]")));
        }
    }

    public static String getDecimal(double value, int scale) {
        if (Math.abs(value) >= 10) return String.format("%.0f", value);
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP).stripTrailingZeros().toString();
    }
}

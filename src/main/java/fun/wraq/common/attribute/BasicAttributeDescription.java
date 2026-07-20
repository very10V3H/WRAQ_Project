package fun.wraq.common.attribute;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Either;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.*;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.forge.ForgeRandomEquip;
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
import fun.wraq.series.dragon.SilverDragonBloodWeapon;
import fun.wraq.series.events.ForgePaper;
import fun.wraq.series.events._7shade.SevenShadePiece;
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
        if (localPlayer == null) {
            return;
        }
        int index = 4;
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        if (item instanceof WraqCurios) index = 5;
        if (item instanceof SevenShadePiece) index = 6;
        if (!(item instanceof WraqCurios || item instanceof WraqUniformCurios
                || item instanceof SevenShadePiece || item instanceof WraqPassiveEquip
                || item instanceof WraqPickaxe || item instanceof WraqMainHandEquip
                || item instanceof WraqArmor)
                && itemStack.getTagElement(Utils.MOD_ID) == null
                && !Utils.offHandTag.containsKey(item)) {
            return;
        }
        if (event.getTooltipElements().size() < 5) {
            return;
        }
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

        if (Screen.hasAltDown() || Screen.hasShiftDown() || Screen.hasControlDown()) {
            event.setMaxWidth(-1);
        } else {
            event.setMaxWidth(168);
        }

        if (data.contains(StringUtils.ForgeLevel)) {
            int forgeLevel = data.getInt(StringUtils.ForgeLevel);
            for (ForgePaper forgePaper : ForgePaper.forgePapers) {
                if (data.contains(forgePaper.getTag())) {
                    ++forgeLevel;
                }
            }
            Style[] styles = {CustomStyle.styleOfMine, CustomStyle.styleOfGold,
                    Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE), CustomStyle.styleOfWorld};
            Style style = styles[Math.min(3, Math.max(0, (forgeLevel - 1) / 8))];
            index++;
            if (item instanceof ForgeTemplate) {
                --index;
            }
            MutableComponent component = Te.s(" 强化等级 ", CustomStyle.styleOfPower, "" + forgeLevel, style);
            for (ForgePaper forgePaper : ForgePaper.forgePapers) {
                if (data.contains(forgePaper.getTag())) {
                    component.append(forgePaper.getExForgeLevelDescription());
                }
            }
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(component,
                    TraditionalTooltip.forge)));
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.attackDamage, Utils.xpLevelAttackDamage,
                StringUtils.RandomAttributes.xpLevelAttackDamage, "物理攻击", Style.EMPTY.applyFormat(ChatFormatting.AQUA),
                1, false, itemStack, event.getTooltipElements(), localPlayer, true, Style.EMPTY.applyFormat(ChatFormatting.YELLOW));

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.attackDamage, Utils.attackDamage,
                StringUtils.RandomAttributes.attackDamage, "物理攻击",
                Style.EMPTY.applyFormat(ChatFormatting.AQUA), 1, false, itemStack, true,
                Style.EMPTY.applyFormat(ChatFormatting.YELLOW), event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.attackDamage, Utils.percentAttackDamageEnhance,
                StringUtils.RandomAttributes.percentAttackDamage,
                "物理攻击", Style.EMPTY.applyFormat(ChatFormatting.AQUA), 1, true, itemStack, false, null, event.getTooltipElements());

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.manaDamage, Utils.xpLevelManaDamage,
                StringUtils.RandomAttributes.xpLevelManaDamage, "魔法攻击", Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE),
                1, false, itemStack, event.getTooltipElements(), localPlayer, true, Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE));

        if (Utils.manaDamage.containsKey(item) || data.contains(StringUtils.RandomAttributes.manaDamage)
                || data.contains(StringUtils.RandomAttributes.manaDamage)) {
            if (itemStack.is(ModItems.SOUL_SCEPTRE.get())) {
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
                if (Utils.manaDamage.containsKey(item) || data.contains(StringUtils.RandomAttributes.manaDamage)) {
                    if (Utils.manaDamage.containsKey(item))
                        baseDamage = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.manaDamage);
                    else if (item instanceof RandomCurios)
                        baseDamage = data.getDouble(StringUtils.RandomAttributes.manaDamage) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.manaDamage);
                    else baseDamage = data.getInt(StringUtils.RandomAttributes.manaDamage);
                } else
                    baseDamage = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttributes.manaDamage);

                if (baseDamage != 0) {
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 法术攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal(" " + String.format("%.0f", baseDamage)).withStyle(ChatFormatting.WHITE)));

                    handleExBaseAttributeValue(itemStack, mutableComponent, Utils.manaDamage);
                    handleForge(data, baseDamage, mutableComponent);
                    handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.manaDamage, mutableComponent);

                    index++;
                    event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaDamage)));
                }
            }
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.manaDamage, Utils.percentManaDamageEnhance,
                StringUtils.RandomAttributes.percentManaDamageEnhance,
                "魔法攻击", CustomStyle.styleOfMana, 1, true,
                itemStack, false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.attackSpeed, Utils.attackSpeedEnhance,
                StringUtils.RandomAttributes.attackSpeedEnhance,
                "攻击速度", CustomStyle.styleOfFlexible, 0, true,
                itemStack, false, null, event.getTooltipElements());

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.defence, Utils.xpLevelDefence,
                StringUtils.RandomAttributes.xpLevelDefence, "基础护甲", Style.EMPTY.applyFormat(ChatFormatting.GRAY),
                1, false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.defence, Utils.defence,
                StringUtils.RandomAttributes.defence, "基础护甲",
                Style.EMPTY.applyFormat(ChatFormatting.GRAY), 1, false, itemStack, true,
                CustomStyle.styleOfStone, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.defence, Utils.percentDefenceEnhance,
                StringUtils.RandomAttributes.percentDefenceEnhance,
                "基础护甲", Style.EMPTY.applyFormat(ChatFormatting.GRAY), 1, true,
                itemStack, false, null, event.getTooltipElements());

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.manaDefence, Utils.xpLevelManaDefence,
                StringUtils.RandomAttributes.xpLevelManaDefence, "魔法抗性", Style.EMPTY.applyFormat(ChatFormatting.BLUE),
                1, false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.manaDefence.containsKey(item) || data.contains(StringUtils.RandomAttributes.manaDefence)) {
            double manaDefence = 0;
            if (Utils.manaDefence.containsKey(item))
                manaDefence = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.manaDefence);
            else if (item instanceof RandomCurios)
                manaDefence = data.getDouble(StringUtils.RandomAttributes.manaDefence) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.manaDefence);

            MutableComponent mutableComponent = Component.literal("");
            if (manaDefence > 0) {
                mutableComponent.append(Component.literal(" 魔法抗性").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+" + getDecimal(manaDefence, 1)).withStyle(ChatFormatting.WHITE)));
            }
            if (manaDefence < 0) {
                mutableComponent.append(Te.s(" 魔法抗性", ChatFormatting.BLUE,
                        "-" + getDecimal(-manaDefence, 1), ChatFormatting.RED));
            }

            handleForge(data, manaDefence, mutableComponent);
            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.manaDefence, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaDefence)));
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.manaDefence, Utils.percentManaDefenceEnhance,
                StringUtils.RandomAttributes.percentManaDefenceEnhance,
                "魔法抗性", Style.EMPTY.applyFormat(ChatFormatting.BLUE), 1, true, itemStack, false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.maxHealth, Utils.maxHealth,
                StringUtils.RandomAttributes.maxHealth, "最大生命值",
                Style.EMPTY.applyFormat(ChatFormatting.GREEN), 1, false, itemStack, true,
                CustomStyle.styleOfLife, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.maxHealth, Utils.percentMaxHealthEnhance,
                StringUtils.RandomAttributes.percentMaxHealthEnhance,
                "最大生命值", Style.EMPTY.applyFormat(ChatFormatting.GREEN), 1, true, itemStack,
                false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.healthRecover, Utils.healthRecover,
                StringUtils.RandomAttributes.healthRecover,
                "生命回复", Style.EMPTY.applyFormat(ChatFormatting.GREEN), 1, false, itemStack,
                true, CustomStyle.styleOfLife, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.healthRecover, Utils.percentHealthRecover,
                StringUtils.RandomAttributes.percentHealthRecover,
                "生命回复", Style.EMPTY.applyFormat(ChatFormatting.GREEN), 2, true, itemStack,
                false, CustomStyle.styleOfLife, event.getTooltipElements());

        if (Utils.defencePenetration.containsKey(item) || data.contains(StringUtils.RandomAttributes.defencePenetration)) {

            double DefencePenetration;
            if (Utils.defencePenetration.containsKey(item)) DefencePenetration = Utils.defencePenetration.get(item);
            else if (item instanceof RandomCurios)
                DefencePenetration = data.getDouble(StringUtils.RandomAttributes.defencePenetration) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.defencePenetration);
            else DefencePenetration = data.getInt(StringUtils.RandomAttributes.defencePenetration);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+" + String.format("%.0f%%", DefencePenetration * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.defencePenetration, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.defencePenetration)));

        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.defencePenetration0, Utils.xpLevelDefencePenetration0,
                StringUtils.RandomAttributes.xpLevelDefencePenetration0, "护甲穿透", Style.EMPTY.applyFormat(ChatFormatting.GRAY),
                1, false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.defencePenetration0.containsKey(item) || data.contains(StringUtils.RandomAttributes.defencePenetration0)
                || data.contains(StringUtils.RandomAttributes.defencePenetration0)) {
            double defencePenetration0;
            if (Utils.defencePenetration0.containsKey(item))
                defencePenetration0 = Utils.defencePenetration0.get(item);
            else if (item instanceof RandomCurios)
                defencePenetration0 = data.getDouble(StringUtils.RandomAttributes.defencePenetration0) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.defencePenetration0);
            else defencePenetration0 = data.getInt(StringUtils.RandomAttributes.defencePenetration0);
            if (data.contains(StringUtils.RandomAttributes.defencePenetration0))
                defencePenetration0 += data.getDouble(StringUtils.RandomAttributes.defencePenetration0);

            if (defencePenetration0 != 0) {
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                        append(Component.literal("+" + getDecimal(defencePenetration0, 1))
                                .withStyle(ChatFormatting.WHITE)));

                handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.defencePenetration0, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.defencePenetration0)));
            }
        }

        if (Utils.critRate.containsKey(item) || data.contains(StringUtils.RandomAttributes.critRate)
                || data.contains(StringUtils.RandomAttributes.critRate)) {
            double critRate;
            if (Utils.critRate.containsKey(item)) critRate = Utils.critRate.get(item);
            else if (item instanceof RandomCurios)
                critRate = data.getDouble(StringUtils.RandomAttributes.critRate) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.critRate);
            else critRate = data.getInt(StringUtils.RandomAttributes.critRate);
            if (data.contains(StringUtils.RandomAttributes.critRate))
                critRate += data.getDouble(StringUtils.RandomAttributes.critRate);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+" + String.format("%.1f%%", critRate * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.critRate, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.critRate)));
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.critDamage, Utils.xpLevelCritDamage,
                StringUtils.RandomAttributes.xpLevelCritDamage, "暴击伤害", Style.EMPTY.applyFormat(ChatFormatting.BLUE),
                0, true, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.critDamage.containsKey(item)
                || data.contains(StringUtils.RandomAttributes.critDamage)
                || data.contains(StringUtils.RandomAttributes.critDamage)) {
            double critDamage;
            if (Utils.critDamage.containsKey(item)) critDamage =
                    ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.critDamage, null, false);
            else if (item instanceof RandomCurios)
                critDamage = data.getDouble(StringUtils.RandomAttributes.critDamage)
                        * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.critDamage);
            else critDamage = data.getInt(StringUtils.RandomAttributes.critDamage);
            if (data.contains(StringUtils.RandomAttributes.critDamage))
                critDamage += data.getDouble(StringUtils.RandomAttributes.critDamage);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 暴击伤害").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+" + String.format("%.0f%%", critDamage * 100)).withStyle(ChatFormatting.WHITE)));

            handleExBaseAttributeValue(itemStack, mutableComponent, Utils.critDamage, 0, true);
            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.critDamage, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.critDamage)));
        }

        if (Utils.healthSteal.containsKey(item) || data.contains(StringUtils.RandomAttributes.healthSteal)
                || data.contains(StringUtils.RandomAttributes.healthSteal)) {
            double healSteal;
            if (Utils.healthSteal.containsKey(item)) healSteal = Utils.healthSteal.get(item);
            else if (item instanceof RandomCurios)
                healSteal = data.getDouble(StringUtils.RandomAttributes.healthSteal) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.healthSteal);
            else healSteal = data.getInt(StringUtils.RandomAttributes.healthSteal);
            if (data.contains(StringUtils.RandomAttributes.healthSteal))
                healSteal += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttributes.healthSteal);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 生命偷取").withStyle(ChatFormatting.RED).
                    append(Component.literal("+" + String.format("%.0f‰", healSteal * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.healthSteal, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.healthSteal)));
        }

        if (Utils.manaCost.containsKey(item)) {
            double ManaCost = Utils.manaCost.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                    append(Component.literal(" " + String.format("%.0f", ManaCost)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaCost)));
        }

        if (Utils.maxMana.containsKey(item) || data.contains(StringUtils.RandomAttributes.maxMana)
                || data.contains(StringUtils.RandomAttributes.maxMana)) {
            double maxMana = 0;
            if (Utils.maxMana.containsKey(item)) maxMana = Utils.maxMana.get(item);
            else if (item instanceof RandomCurios)
                maxMana = data.getDouble(StringUtils.RandomAttributes.maxMana) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.maxMana);
            if (data.contains(StringUtils.RandomAttributes.maxMana))
                maxMana = data.getDouble(StringUtils.RandomAttributes.maxMana);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+" + String.format("%.0f", maxMana)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.maxMana, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.maxMana)));
        }

        if (Utils.manaPenetration.containsKey(item) || data.contains(StringUtils.RandomAttributes.manaPenetration)) {
            double ManaPenetration;
            if (Utils.manaPenetration.containsKey(item)) ManaPenetration = Utils.manaPenetration.get(item);
            else if (item instanceof RandomCurios)
                ManaPenetration = data.getDouble(StringUtils.RandomAttributes.manaPenetration) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.manaPenetration);
            else ManaPenetration = data.getInt(StringUtils.RandomAttributes.manaPenetration);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+" + String.format("%.0f%%", ManaPenetration * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.manaPenetration, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaPenetration)));
        }

        index = descriptionXpLevelAttributeTemplate(index, TraditionalTooltip.manaPenetration0, Utils.xpLevelManaPenetration0,
                StringUtils.RandomAttributes.xpLevelManaPenetration0, "魔法穿透", Style.EMPTY.applyFormat(ChatFormatting.BLUE),
                1, false, itemStack, event.getTooltipElements(), localPlayer, false, Style.EMPTY);

        if (Utils.manaPenetration0.containsKey(item) || data.contains(StringUtils.RandomAttributes.manaPenetration0)
                || data.contains(StringUtils.RandomAttributes.manaPenetration0)) {
            double manaPenetration0;
            if (Utils.manaPenetration0.containsKey(item)) manaPenetration0 = Utils.manaPenetration0.get(item);
            else if (item instanceof RandomCurios)
                manaPenetration0 = data.getDouble(StringUtils.RandomAttributes.manaPenetration0) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.manaPenetration0);
            else manaPenetration0 = data.getInt(StringUtils.RandomAttributes.manaPenetration0);
            if (data.contains(StringUtils.RandomAttributes.manaPenetration0))
                manaPenetration0 += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttributes.manaPenetration0);

            if (manaPenetration0 != 0) {
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+" + getDecimal(manaPenetration0, 1)).withStyle(ChatFormatting.WHITE)));

                handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.manaPenetration0, mutableComponent);

                index++;
                event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaPenetration0)));
            }
        }

/*        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.manaRecover, Utils.percentManaRecover,
                StringUtils.RandomCuriosAttribute.percentManaRecoverEnhance, "法力回复",
                Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE), 1, true, itemStack,
                false, CustomStyle.styleOfMana, event.getTooltipElements());*/

        if (Utils.manaRecover.containsKey(item) || data.contains(StringUtils.RandomAttributes.manaRecover)
                || data.contains(StringUtils.RandomAttributes.manaRecover)) {
            double manaRecover;
            if (Utils.manaRecover.containsKey(item)) manaRecover = Utils.manaRecover.get(item);
            else if (item instanceof RandomCurios)
                manaRecover = data.getDouble(StringUtils.RandomAttributes.manaRecover) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.manaRecover);
            else manaRecover = data.getInt(StringUtils.RandomAttributes.manaRecover);
            if (data.contains(StringUtils.RandomAttributes.manaRecover))
                manaRecover += data.getDouble(StringUtils.RandomAttributes.manaRecover);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+" + String.format("%.0f", manaRecover)).withStyle(ChatFormatting.WHITE)));
            handleExBaseAttributeValue(itemStack, mutableComponent, Utils.manaRecover);
            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.manaRecover, mutableComponent);
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaRecover)));
        }

        if (Utils.coolDownDecrease.containsKey(item) || data.contains(StringUtils.RandomAttributes.coolDown)
                || data.contains(StringUtils.RandomAttributes.coolDown)) {
            double coolDown;
            if (Utils.coolDownDecrease.containsKey(item)) coolDown = Utils.coolDownDecrease.get(item);
            else if (item instanceof RandomCurios)
                coolDown = data.getDouble(StringUtils.RandomAttributes.coolDown) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.coolDown);
            else coolDown = data.getInt(StringUtils.RandomAttributes.coolDown);
            if (data.contains(StringUtils.RandomAttributes.coolDown))
                coolDown = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttributes.coolDown);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 技能急速").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" " + String.format("%.0f", coolDown * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.coolDown, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.releaseSpeed)));
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.movementSpeed, Utils.movementSpeedCommon,
                StringUtils.RandomAttributes.commonMovementSpeed,
                "移动速度", Style.EMPTY.applyFormat(ChatFormatting.GREEN), 0, true,
                itemStack, true, CustomStyle.styleOfFlexible, event.getTooltipElements());

        if (Utils.movementSpeedWithoutBattle.containsKey(item) || data.contains(StringUtils.RandomAttributes.movementSpeed)
                || data.contains(StringUtils.RandomAttributes.movementSpeedWithoutBattle)) {
            double MovementSpeed;
            if (Utils.movementSpeedWithoutBattle.containsKey(item))
                MovementSpeed = Utils.movementSpeedWithoutBattle.get(item);
            else if (item instanceof RandomCurios)
                MovementSpeed = data.getDouble(StringUtils.RandomAttributes.movementSpeed) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.movementSpeed);
            else MovementSpeed = data.getInt(StringUtils.RandomAttributes.movementSpeed);
            if (data.contains(StringUtils.RandomAttributes.movementSpeedWithoutBattle))
                MovementSpeed += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttributes.movementSpeedWithoutBattle);

            MutableComponent mutableComponent = Component.literal("");

            if (MovementSpeed > 0) {
                mutableComponent.append(Component.literal(" 脱战移动速度").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("+" + String.format("%.0f%%", MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)));

            }
            if (MovementSpeed < 0) {
                mutableComponent.append(Component.literal(" 脱战移动速度").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("-" + String.format("%.0f%%", -MovementSpeed * 100)).withStyle(ChatFormatting.RED)));

            }

            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.movementSpeed, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.movementSpeed)));
        }
        if (Utils.expUp.containsKey(item) || data.contains(StringUtils.RandomAttributes.expUp)) {
            double ExpUp;
            if (item instanceof RandomCurios)
                ExpUp = data.getDouble(StringUtils.RandomAttributes.expUp) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.expUp);
            else ExpUp = Utils.expUp.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+" + String.format("%.0f%%", ExpUp * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.expUp, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.expUp)));
        }
        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.swiftnessUp,
                Utils.swiftnessUp, StringUtils.RandomAttributes.swiftnessUp,
                "迅捷加成", CustomStyle.styleOfFlexible, 1, false, itemStack, false, null, event.getTooltipElements());
        if (Utils.manaHealthSteal.containsKey(item) || data.contains(StringUtils.RandomAttributes.manaHealthSteal)) {
            double ManaHealSteal;
            if (Utils.manaHealthSteal.containsKey(item)) ManaHealSteal = Utils.manaHealthSteal.get(item);
            else if (item instanceof RandomCurios)
                ManaHealSteal = data.getDouble(StringUtils.RandomAttributes.manaHealthSteal) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.manaHealthSteal);
            else ManaHealSteal = data.getInt(StringUtils.RandomAttributes.manaHealthSteal);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 法术吸血").withStyle(CustomStyle.styleOfBloodMana).
                    append(Component.literal("+" + String.format("%.0f‰", ManaHealSteal * 100)).withStyle(ChatFormatting.WHITE)));

            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.manaHealthSteal, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.manaHealthSteal)));
        }
        if (Utils.healingAmplification.containsKey(item) || data.contains(StringUtils.RandomAttributes.healEffectUp)) {
            double healingAmplification;
            if (Utils.healingAmplification.containsKey(item)) healingAmplification = Utils.healingAmplification.get(item);
            else if (item instanceof RandomCurios)
                healingAmplification = data.getDouble(StringUtils.RandomAttributes.healEffectUp) * RandomCuriosAttributesUtil.attributeValueMap.get(StringUtils.RandomAttributes.healEffectUp);
            else healingAmplification = data.getInt(StringUtils.RandomAttributes.healEffectUp);

            MutableComponent mutableComponent = Component.literal("");

            if (healingAmplification > 0) {
                mutableComponent.append(Te.s(" 治疗强度", CustomStyle.styleOfHealth,
                        "+" + String.format("%.0f%%", healingAmplification * 100)));
            }
            else {
                mutableComponent.append(Te.s(" 治疗强度", CustomStyle.styleOfHealth,
                        "-" + String.format("%.0f%%", -healingAmplification * 100), ChatFormatting.RED));
            }

            handleRandomAttributeRate(itemStack, StringUtils.RandomAttributes.healEffectUp, mutableComponent);

            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.healthStrength)));
        }
        if (Utils.luckyUp.containsKey(item) || data.contains(StringUtils.RandomAttributes.LuckyUp)) {
            double LuckyUp;
            if (Utils.luckyUp.containsKey(item)) LuckyUp = Utils.luckyUp.get(item);
            else LuckyUp = data.getInt(StringUtils.RandomAttributes.LuckyUp);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 幸运加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal(" " + String.format("%.0f%%", LuckyUp * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.luckyUp)));
        }

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.stoneElement, WraqPickaxe.mineSpeed,
                "EmptyTypeAttribute",
                "挖掘速度", Style.EMPTY.applyFormat(ChatFormatting.GRAY), 0, true,
                itemStack, false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.toughness, Utils.toughness,
                StringUtils.RandomAttributes.toughness,
                "韧性", CustomStyle.styleOfEnd, 1, true,
                itemStack, false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index, TraditionalTooltip.element, Utils.elementStrength,
                null,
                "元素强度", CustomStyle.styleOfWorld, 0, true,
                itemStack, false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index,
                TraditionalTooltip.finalDamageEnhance, Utils.finalDamageEnhance,
                StringUtils.RandomAttributes.finalDamageEnhance,
                "最终伤害加成", CustomStyle.styleOfDemon, 1, true,
                itemStack, false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index,
                TraditionalTooltip.attackDamageEnhance, Utils.attackDamageEnhance,
                StringUtils.RandomAttributes.attackDamageEnhance,
                "物理伤害加成", CustomStyle.styleOfPower, 1, true,
                itemStack, false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index,
                TraditionalTooltip.manaDamageEnhance, Utils.manaDamageEnhance,
                StringUtils.RandomAttributes.manaDamageEnhance,
                "魔法伤害加成", CustomStyle.styleOfMana, 1, true,
                itemStack, false, null, event.getTooltipElements());

        index = newAttributeCommonDescriptionTemplate(index,
                TraditionalTooltip.commonDamageEnhance, Utils.commonDamageEnhance,
                StringUtils.RandomAttributes.commonDamageEnhance,
                "普通伤害加成", CustomStyle.styleOfMoon, 1, true,
                itemStack, false, null, event.getTooltipElements());

        if (Element.lifeElementValue.containsKey(item)) {
            double value = Element.lifeElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化生机元素强度").withStyle(CustomStyle.styleOfLife).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.lifeElement)));
        }

        if (Element.waterElementValue.containsKey(item)) {
            double value = Element.waterElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化碧水元素强度").withStyle(CustomStyle.styleOfWater).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.waterElement)));
        }

        if (Element.fireElementValue.containsKey(item)) {
            double value = Element.fireElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化炽焰元素强度").withStyle(CustomStyle.styleOfFire).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.fireElement)));
        }

        if (Element.stoneElementValue.containsKey(item)) {
            double value = Element.stoneElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化层岩元素强度").withStyle(CustomStyle.styleOfStone).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.stoneElement)));
        }

        if (Element.iceElementValue.containsKey(item)) {
            double value = Element.iceElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化凛冰元素强度").withStyle(CustomStyle.styleOfIce).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.iceElement)));
        }

        if (Element.lightningElementValue.containsKey(item)) {
            double value = Element.lightningElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化怒雷元素强度").withStyle(CustomStyle.styleOfLightning).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new NewTooltip.MyNewTooltip(mutableComponent, TraditionalTooltip.lightningElement)));
        }

        if (Element.windElementValue.containsKey(item)) {
            double value = Element.windElementValue.get(item);
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
                            if (toolTipParameterMap.containsKey(System.identityHashCode(attributeMapValue.attributeMap()))) {
                                ToolTipParameter toolTipParameter = toolTipParameterMap.get(System.identityHashCode(attributeMapValue.attributeMap()));
                                Component component = Component.literal(" " + toolTipParameter.attributeName).withStyle(toolTipParameter.style).
                                        append(Component.literal((attributeMapValue.value() > 0 ? "+" : "") + String.format(toolTipParameter.valueFormat,
                                                        attributeMapValue.value() * (toolTipParameter.isPercent ? 100 : 1))).
                                                withStyle(attributeMapValue.value() > 0 ? ChatFormatting.WHITE : ChatFormatting.RED));
                                event.getTooltipElements().add(index++, Either.right(new NewTooltip.MyNewTooltip(component, toolTipParameter.resourceLocation)));
                            }
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
        put(System.identityHashCode(Utils.percentHealthRecover), new ToolTipParameter("生命回复",
                CustomStyle.styleOfLife, "%.1f%%", true, TraditionalTooltip.healthRecover));
        put(System.identityHashCode(Utils.commonDamageEnhance), new ToolTipParameter("普通伤害加成",
                CustomStyle.styleOfMoon, "%.0f%%", true, TraditionalTooltip.commonDamageEnhance));
    }};

    // 新的属性描述模板，仅需按照参数进行配置即可
    public static int newAttributeCommonDescriptionTemplate(int index, ResourceLocation resourceLocation,
                                                            Map<Item, Double> map,
                                                            String randomAttributeTagKey, String attributeName,
                                                            Style style, int decimalScale, boolean isPercent,
                                                            ItemStack itemStack, boolean computeTier,
                                                            Style forgeValueStyle,
                                                            List<Either<FormattedText, TooltipComponent>> components) {
        Item item = itemStack.getItem();
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        double traditionalEquipBaseValue
                = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, map, null, computeTier);
        double exBaseAttributeValue = ExBaseAttributeValueEquip.getExBaseAttributeValue(itemStack, map);
        if (map.containsKey(item) || data.contains(randomAttributeTagKey)
                || traditionalEquipBaseValue != 0 || exBaseAttributeValue != 0) {
            double value;
            if (map.containsKey(item) || traditionalEquipBaseValue != 0) {
                value = traditionalEquipBaseValue;
            } else if (item instanceof RandomCurios) {
                value = data.getDouble(randomAttributeTagKey)
                        * RandomCuriosAttributesUtil.attributeValueMap.get(randomAttributeTagKey);
            } else if (item instanceof ForgeRandomEquip) {
                value = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, randomAttributeTagKey);
            }
            else {
                value = data.getInt(randomAttributeTagKey);
            }
            String percent = isPercent ? "%" : "";
            MutableComponent mutableComponent = Component.literal("");
            if (value == 0 && exBaseAttributeValue == 0) {
                return index;
            }
            mutableComponent.append(Component.literal(" " + attributeName).withStyle(style));
            if (value != 0) {
                mutableComponent.append(Component.literal((value > 0 ? "+" : "")
                                + getDecimal(value * (isPercent ? 100 : 1), decimalScale) + percent)
                        .withStyle(value > 0 ? ChatFormatting.WHITE : ChatFormatting.RED));
            }
            handleExBaseAttributeValue(itemStack, mutableComponent, map, decimalScale, isPercent);
            if (computeTier && value > 0) {
                double exForgingValue = 0;
                if (data.contains(StringUtils.ForgeLevel)) {
                    exForgingValue = Compute.forgingValue(data, value);
                }
                // 移动速度属性强化效能减半
                if (map.equals(Utils.movementSpeedCommon)) {
                    exForgingValue /= 2;
                }
                if (exForgingValue != 0) {
                    mutableComponent.append(Component.literal(" + " + getDecimal(exForgingValue * (isPercent ? 100 : 1), decimalScale) + percent).withStyle(forgeValueStyle)).
                            append(Component.literal("⮅").withStyle(CustomStyle.styleOfPower));
                }
            }
            handleRandomAttributeRate(itemStack, randomAttributeTagKey, mutableComponent);
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
        int forgeQuality = ForgeEquipUtils.getEquipForgeQuality(itemStack);
        if (itemStack.getItem() instanceof SilverDragonBloodWeapon) {
            return;
        }
        if (forgeQuality != -1 && !(itemStack.getItem() instanceof WraqOffHandItem)) {
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
        double exDamageForging = 0;
        if (data.contains(StringUtils.ForgeLevel)) {
            exDamageForging = Compute.forgingValue(data, baseValue);
        }
        if (exDamageForging > 0) {
            mutableComponent.append(Te.s(" + " + String.format("%.0f", exDamageForging), ChatFormatting.YELLOW,
                    "(", CustomStyle.styleOfMoon,
                    String.format("%.0f%%", Compute.getForgingValueRate(data) * 100), ChatFormatting.YELLOW,
                    ")", CustomStyle.styleOfMoon,
                    "⮅", CustomStyle.styleOfPower));
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
            if (fullRate != 0) {
                double percent = rate / fullRate;
                Style[] styles = new Style[]{CustomStyle.styleOfPlain, CustomStyle.styleOfWater,
                        CustomStyle.styleOfVolcano, CustomStyle.styleOfPower, Style.EMPTY.applyFormat(ChatFormatting.RED)};
                mutableComponent.append(Te.m(" [").
                        append(Te.m(String.format("%.2f%%", percent * 100), styles[Math.min(4, (int) (percent / 0.3))])).
                        append(Te.m("]")));
            }
        }
    }

    public static String getDecimal(double value, int scale) {
        if (Math.abs(value) >= 10) return String.format("%.0f", value);
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP).stripTrailingZeros().toString();
    }
}

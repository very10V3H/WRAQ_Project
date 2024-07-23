package com.very.wraq.common;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.forge.ForgeEquipUtils;
import com.very.wraq.projectiles.RandomCurios;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.projectiles.WraqUniformCurios;
import com.very.wraq.render.toolTip.MyClientTooltip;
import com.very.wraq.series.instance.Castle.CastleCurios;
import com.very.wraq.series.worldsoul.SoulEquipAttribute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ModItems;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;

import java.util.List;

import static com.very.wraq.common.Compute.*;

public class BasicAttributeDescription {

    public static void NewAttributeDescription(RenderTooltipEvent.GatherComponents event) {
        int index = 4;
        int type = 0;
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof WraqCurios) index = 5;
        if (!(itemStack.getItem() instanceof WraqCurios || itemStack.getItem() instanceof WraqUniformCurios)
                && itemStack.getTagElement(Utils.MOD_ID) == null && !Utils.offHandTag.containsKey(itemStack.getItem()))
            return;
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Item item = itemStack.getItem();
        if (itemStack.is(ModItems.ManageSword.get())) return;

/*        if (itemStack.getItem() instanceof PurpleArmor && !data0.contains(StringUtils.RandomAttribute.AttackDamage)) {
            event.getTooltipElements().add(5, Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "50 - 100" + "攻击力").withStyle(ChatFormatting.YELLOW),0)));
            event.getTooltipElements().add(6,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "300 - 400" + "魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE),1)));
            event.getTooltipElements().add(7,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "50 - 100" + "护甲").withStyle(ChatFormatting.GRAY),2)));
            event.getTooltipElements().add(8,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "400 - 800" + "最大生命值").withStyle(ChatFormatting.GREEN),3)));
        }

        if (itemStack.getItem() instanceof IceArmor && !data0.contains(StringUtils.RandomAttribute.AttackDamage)) {
            event.getTooltipElements().add(5, Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "100 - 140" + "攻击力").withStyle(ChatFormatting.YELLOW),0)));
            event.getTooltipElements().add(6,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "600 - 800" + "魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE),1)));
            event.getTooltipElements().add(7,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "100 - 200" + "护甲").withStyle(ChatFormatting.GRAY),2)));
            event.getTooltipElements().add(8,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "800 - 1600" + "最大生命值").withStyle(ChatFormatting.GREEN),3)));
        }*/

        if (Utils.attackDamage.containsKey(item) || data.contains(StringUtils.RandomAttribute.attackDamage)
                || data.contains(StringUtils.CuriosAttribute.AttackDamage)) {
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
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
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
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
                }
            } else {
                double BaseDamage;
                if (Utils.attackDamage.containsKey(item) || data.contains(StringUtils.CuriosAttribute.AttackDamage)) {
                    if (Utils.attackDamage.containsKey(item))
                        BaseDamage = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.attackDamage);
                    else if (item instanceof RandomCurios)
                        BaseDamage = data.getDouble(StringUtils.CuriosAttribute.AttackDamage) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.AttackDamage);
                    else BaseDamage = data.getInt(StringUtils.CuriosAttribute.AttackDamage);
                } else
                    BaseDamage = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.attackDamage);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 物理攻击").withStyle(ChatFormatting.AQUA).
                        append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)));


                double ExDamageForging = 0;
                if (data.contains("Forging")) ExDamageForging = ForgingValue(data, BaseDamage);
                double ExDamageProficiency = 0;
                if (data.contains("KillCount"))
                    ExDamageProficiency = BaseDamage * 0.5 * Math.min(1, (data.getInt("KillCount") / 100000.0));
                ChatFormatting[] chatFormattings = {
                        ChatFormatting.GREEN,
                        ChatFormatting.AQUA,
                        ChatFormatting.YELLOW,
                        ChatFormatting.LIGHT_PURPLE,
                        ChatFormatting.RED
                };

                double GemsValue = gemsAttackDamage(data);

                if (ExDamageProficiency != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageProficiency)).
                            withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000, 4)]));
                }

                if ((ExDamageForging + GemsValue) != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageForging + GemsValue)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW));
                }

                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            }
        } else type++;

        if (Utils.manaDamage.containsKey(item) || data.contains(StringUtils.RandomAttribute.manaDamage)
                || data.contains(StringUtils.CuriosAttribute.ManaDamage)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal(" " + String.format("%.0f", SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaAttackDamage)).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaAttackDamage)).withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            } else {
                double BaseDamage;
                if (Utils.manaDamage.containsKey(item) || data.contains(StringUtils.CuriosAttribute.ManaDamage)) {
                    if (Utils.manaDamage.containsKey(item))
                        BaseDamage = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.manaDamage);
                    else if (item instanceof RandomCurios)
                        BaseDamage = data.getDouble(StringUtils.CuriosAttribute.ManaDamage) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaDamage);
                    else BaseDamage = data.getInt(StringUtils.CuriosAttribute.ManaDamage);
                } else
                    BaseDamage = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.manaDamage);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 法术攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal(" " + String.format("%.0f", BaseDamage)).withStyle(ChatFormatting.WHITE)));


                double ExDamageForging = 0;
                if (data.contains("Forging")) ExDamageForging = ForgingValue(data, BaseDamage);
                double ExDamageProficiency = 0;
                if (data.contains("KillCount"))
                    ExDamageProficiency = BaseDamage * 0.5 * Math.min(1, (data.getInt("KillCount") / 100000.0));
                ChatFormatting[] chatFormattings = {
                        ChatFormatting.GREEN,
                        ChatFormatting.AQUA,
                        ChatFormatting.YELLOW,
                        ChatFormatting.LIGHT_PURPLE,
                        ChatFormatting.RED
                };

                double GemsValue = gemsManaDamage(data);

                if (ExDamageProficiency != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageProficiency)).
                            withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000, 4)]));
                }

                if ((ExDamageForging + GemsValue) != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageForging + GemsValue)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
                }

                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            }
        } else type++;

        if (Utils.defence.containsKey(item) || data.contains(StringUtils.RandomAttribute.defence)
                || data.contains(StringUtils.CuriosAttribute.Defence)) {
            double Defence;
            if (Utils.defence.containsKey(item) || data.contains(StringUtils.CuriosAttribute.Defence)) {
                if (Utils.defence.containsKey(item))
                    Defence = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.defence);
                else if (item instanceof RandomCurios)
                    Defence = data.getDouble(StringUtils.CuriosAttribute.Defence) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.Defence);
                else Defence = data.getInt(StringUtils.CuriosAttribute.Defence);
            } else Defence = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.defence);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 基础护甲").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+" + String.format("%.0f", Defence)).withStyle(ChatFormatting.WHITE)));


            double ExDamageForging = 0;
            if (data.contains("Forging")) ExDamageForging = ForgingValue(data, Defence);

            double GemsValue = gemsDefence(data);

            if ((ExDamageForging + GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageForging + GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
            }

            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Utils.manaDefence.containsKey(item) || data.contains(StringUtils.CuriosAttribute.ManaDefence)) {
            double ManaDefence = 0;
            if (Utils.manaDefence.containsKey(item))
                ManaDefence = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.manaDefence);
            else if (item instanceof RandomCurios)
                ManaDefence = data.getDouble(StringUtils.CuriosAttribute.ManaDefence) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaDefence);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 魔法抗性").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+" + String.format("%.0f", ManaDefence)).withStyle(ChatFormatting.WHITE)));


            double ExDamageForging = 0;
            if (data.contains("Forging")) ExDamageForging = ForgingValue(data, ManaDefence);

            double GemsValue = gemsManaDefence(data);

            if ((ExDamageForging + GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageForging + GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_BLUE));
            }

            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Utils.maxHealth.containsKey(item) || data.contains(StringUtils.RandomAttribute.maxHealth)
                || data.contains(StringUtils.CuriosAttribute.MaxHealth)) {
            double maxHealth;
            if (Utils.maxHealth.containsKey(item) || data.contains(StringUtils.CuriosAttribute.MaxHealth)) {
                if (Utils.maxHealth.containsKey(item))
                    maxHealth = ForgeEquipUtils.getTraditionalEquipBaseValue(itemStack, Utils.maxHealth);
                else if (item instanceof RandomCurios)
                    maxHealth = data.getDouble(StringUtils.CuriosAttribute.MaxHealth) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.MaxHealth);
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
            if (data.contains("Forging")) ExHealth = ForgingValue(data, maxHealth);

            double GemsValue = gemsMaxHealth(data);

            if ((ExHealth + GemsValue) > 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExHealth + GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
            }
            if ((ExHealth + GemsValue) < 0) {
                mutableComponent.append(Component.literal(" - " + String.format("%.0f", -(ExHealth + GemsValue))).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
            }
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Utils.healthRecover.containsKey(item) || data.contains(StringUtils.CuriosAttribute.HealthRecover)
                || data.contains(StringUtils.RandomAttribute.healthRecover)) {
            double healthRecover;
            if (Utils.healthRecover.containsKey(item)) healthRecover = Utils.healthRecover.get(item);
            else if (item instanceof RandomCurios)
                healthRecover = data.getDouble(StringUtils.CuriosAttribute.HealthRecover) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.HealthRecover);
            else healthRecover = data.getInt(StringUtils.CuriosAttribute.HealthRecover);
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


            double GemsValue = gemsHealRecover(data);

            if ((GemsValue) > 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.1f", GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
            }
            if (GemsValue < 0) {
                mutableComponent.append(Component.literal(" - " + String.format("%.1f", -GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
            }
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Utils.defencePenetration.containsKey(item) || data.contains(StringUtils.CuriosAttribute.DefencePenetration)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())) {
/*                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("+"+String.format("%.0f%%",SoulEquipAttribute.BaseAttribute.SoulSword.DefencePenetration*100)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.DefencePenetration * 100)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("+"+String.format("%.0f%%",SoulEquipAttribute.BaseAttribute.SoulBow.DefencePenetration*100)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.DefencePenetration * 100)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }*/
            } else {
                double DefencePenetration;
                if (Utils.defencePenetration.containsKey(item)) DefencePenetration = Utils.defencePenetration.get(item);
                else if (item instanceof RandomCurios)
                    DefencePenetration = data.getDouble(StringUtils.CuriosAttribute.DefencePenetration) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.DefencePenetration);
                else DefencePenetration = data.getInt(StringUtils.CuriosAttribute.DefencePenetration);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                        append(Component.literal("+" + String.format("%.0f%%", DefencePenetration * 100)).withStyle(ChatFormatting.WHITE)));

                double GemsValue = gemsDefencePenetration(data);

                if (GemsValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                }
                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            }
        } else type++;

        if (Utils.defencePenetration0.containsKey(item) || data.contains(StringUtils.CuriosAttribute.DefencePenetration0)
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
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
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
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
                }
            } else {
                double defencePenetration0;
                if (Utils.defencePenetration0.containsKey(item))
                    defencePenetration0 = Utils.defencePenetration0.get(item);
                else if (item instanceof RandomCurios)
                    defencePenetration0 = data.getDouble(StringUtils.CuriosAttribute.DefencePenetration0) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.DefencePenetration0);
                else defencePenetration0 = data.getInt(StringUtils.CuriosAttribute.DefencePenetration0);
                if (data.contains(StringUtils.RandomAttribute.defencePenetration0))
                    defencePenetration0 += data.getDouble(StringUtils.RandomAttribute.defencePenetration0);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                        append(Component.literal("+" + String.format("%.0f", defencePenetration0)).withStyle(ChatFormatting.WHITE)));

                double GemsValue = gemsDefencePenetration0(data);

                if (GemsValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", GemsValue)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                }
                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            }
        } else type++;

        if (Utils.critRate.containsKey(item) || data.contains(StringUtils.CuriosAttribute.CritRate)
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
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
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
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
                }
            } else {
                double critRate;
                if (Utils.critRate.containsKey(item)) critRate = Utils.critRate.get(item);
                else if (item instanceof RandomCurios)
                    critRate = data.getDouble(StringUtils.CuriosAttribute.CritRate) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.CritRate);
                else critRate = data.getInt(StringUtils.CuriosAttribute.CritRate);
                if (data.contains(StringUtils.RandomAttribute.critRate))
                    critRate += data.getDouble(StringUtils.RandomAttribute.critRate);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.1f%%", critRate * 100)).withStyle(ChatFormatting.WHITE)));

                double GemsValue = gemsCritRate(data);

                if ((GemsValue) != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.1f%%", GemsValue * 100)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
                }

                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            }
        } else type++;

        if (Utils.critDamage.containsKey(item) || data.contains(StringUtils.CuriosAttribute.CritDamage)
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
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
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
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
                }
            } else {
                double critDamage;
                if (Utils.critDamage.containsKey(item)) critDamage = Utils.critDamage.get(item);
                else if (item instanceof RandomCurios)
                    critDamage = data.getDouble(StringUtils.CuriosAttribute.CritDamage) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.CritDamage);
                else critDamage = data.getInt(StringUtils.CuriosAttribute.CritDamage);
                if (data.contains(StringUtils.RandomAttribute.critDamage))
                    critDamage += data.getDouble(StringUtils.RandomAttribute.critDamage);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 暴击伤害").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+" + String.format("%.0f%%", critDamage * 100)).withStyle(ChatFormatting.WHITE)));


                double GemsValue = gemsCritDamage(data);
                if ((GemsValue) != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE));
                }

                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            }
        } else type++;

        if (Utils.healthSteal.containsKey(item) || data.contains(StringUtils.CuriosAttribute.HealthSteal)
                || data.contains(StringUtils.RandomAttribute.healthSteal)) {
            if (itemStack.is(ModItems.SoulSword.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 生命偷取").withStyle(ChatFormatting.RED).
                            append(Component.literal("+" + String.format("%.0f‱", SoulEquipAttribute.BaseAttribute.SoulSword.HealthSteal * 100)).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f‱", SoulEquipAttribute.ForgingAddition.HealthSteal * 100)).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                    index++;
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
                }
            } else {

                double healSteal;
                if (Utils.healthSteal.containsKey(item)) healSteal = Utils.healthSteal.get(item);
                else if (item instanceof RandomCurios)
                    healSteal = data.getDouble(StringUtils.CuriosAttribute.HealthSteal) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.HealthSteal);
                else healSteal = data.getInt(StringUtils.CuriosAttribute.HealthSteal);
                if (data.contains(StringUtils.RandomAttribute.healthSteal))
                    healSteal += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.healthSteal);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 生命偷取").withStyle(ChatFormatting.RED).
                        append(Component.literal("+" + String.format("%.0f‱", healSteal * 100)).withStyle(ChatFormatting.WHITE)));


                double GemsValue = gemsMaxHealth(data);

                if (GemsValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f‱", GemsValue)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
                }


                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));

            }
        } else type++;

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
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            } else {
                double ManaCost = Utils.manaCost.get(item);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                        append(Component.literal(" " + String.format("%.0f", ManaCost)).withStyle(ChatFormatting.WHITE)));
                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            }
        } else type++;

        if (Utils.maxMana.containsKey(item) || data.contains(StringUtils.CuriosAttribute.MaxMana)
                || data.contains(StringUtils.CuriosAttribute.MaxMana)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.0f", SoulEquipAttribute.BaseAttribute.SoulSceptre.MaxMana)).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ 16 x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            } else {
                double maxMana = 0;
                if (Utils.maxMana.containsKey(item)) maxMana = Utils.maxMana.get(item);
                else if (item instanceof RandomCurios)
                    maxMana = data.getDouble(StringUtils.CuriosAttribute.MaxMana) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.MaxMana);
                if (data.contains(StringUtils.RandomAttribute.maxMana))
                    maxMana = data.getDouble(StringUtils.RandomAttribute.maxMana);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.0f", maxMana)).withStyle(ChatFormatting.WHITE)));
                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));

            }
        } else type++;

        if (Utils.manaPenetration.containsKey(item) || data.contains(StringUtils.CuriosAttribute.ManaPenetration)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
/*
                int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+"+String.format("%.0f%%",SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaPenetration * 100)).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.ManaPenetration * 100)).withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal(" x [" + ForgeTimes + "]").withStyle(CustomStyle.styleOfWorld)));
                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
*/
            } else {
                double ManaPenetration;
                if (Utils.manaPenetration.containsKey(item)) ManaPenetration = Utils.manaPenetration.get(item);
                else if (item instanceof RandomCurios)
                    ManaPenetration = data.getDouble(StringUtils.CuriosAttribute.ManaPenetration) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaPenetration);
                else ManaPenetration = data.getInt(StringUtils.CuriosAttribute.ManaPenetration);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+" + String.format("%.0f%%", ManaPenetration * 100)).withStyle(ChatFormatting.WHITE)));


                double GemsValue = gemsManaPenetration(data);

                if (GemsValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
                }

                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            }
        } else type++;

        if (Utils.manaPenetration0.containsKey(item) || data.contains(StringUtils.CuriosAttribute.ManaPenetration0)
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
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            } else {
                double manaPenetration0;
                if (Utils.manaPenetration0.containsKey(item)) manaPenetration0 = Utils.manaPenetration0.get(item);
                else if (item instanceof RandomCurios)
                    manaPenetration0 = data.getDouble(StringUtils.CuriosAttribute.ManaPenetration0) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaPenetration0);
                else manaPenetration0 = data.getInt(StringUtils.CuriosAttribute.ManaPenetration0);
                if (data.contains(StringUtils.RandomAttribute.manaPenetration0))
                    manaPenetration0 += ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.manaPenetration0);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+" + String.format("%.0f", manaPenetration0)).withStyle(ChatFormatting.WHITE)));

                double GemsValue = gemsManaPenetration0(data);

                if (GemsValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", GemsValue)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
                }

                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            }
        } else type++;

        if (Utils.manaRecover.containsKey(item) || data.contains(StringUtils.CuriosAttribute.ManaRecover)
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
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            } else {
                double manaRecover;
                if (Utils.manaRecover.containsKey(item)) manaRecover = Utils.manaRecover.get(item);
                else if (item instanceof RandomCurios)
                    manaRecover = data.getDouble(StringUtils.CuriosAttribute.ManaRecover) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaRecover);
                else manaRecover = data.getInt(StringUtils.CuriosAttribute.ManaRecover);
                if (data.contains(StringUtils.RandomAttribute.manaRecover))
                    manaRecover += data.getDouble(StringUtils.RandomAttribute.manaRecover);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+" + String.format("%.0f", manaRecover)).withStyle(ChatFormatting.WHITE)));


                double GemsValue = gemsManaRecover(data);

                if ((GemsValue) != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", GemsValue)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
                }

                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            }
        } else type++;

        if (Utils.coolDownDecrease.containsKey(item) || data.contains(StringUtils.CuriosAttribute.CoolDown)
                || data.contains(StringUtils.RandomAttribute.coolDown)) {
            double coolDown;
            if (Utils.coolDownDecrease.containsKey(item)) coolDown = Utils.coolDownDecrease.get(item);
            else if (item instanceof RandomCurios)
                coolDown = data.getDouble(StringUtils.CuriosAttribute.CoolDown) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.CoolDown);
            else coolDown = data.getInt(StringUtils.CuriosAttribute.CoolDown);
            if (data.contains(StringUtils.RandomAttribute.coolDown))
                coolDown = ForgeEquipUtils.getRandomEquipBaseValue(itemStack, StringUtils.RandomAttribute.coolDown);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 技能急速").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" " + String.format("%.0f", coolDown * 100)).withStyle(ChatFormatting.WHITE)));


            double GemsValue = gemsCoolDown(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", GemsValue * 100)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
            }

            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (Utils.movementSpeedCommon.containsKey(item)) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 移动速度").withStyle(CustomStyle.styleOfFlexible).
                    append(Component.literal(" " + String.format("%.0f%%", Utils.movementSpeedCommon.get(item) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (Utils.movementSpeedWithoutBattle.containsKey(item) || data.contains(StringUtils.CuriosAttribute.MovementSpeed)
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
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
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
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
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
                    event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
                }
            } else {
                double MovementSpeed;
                if (Utils.movementSpeedWithoutBattle.containsKey(item)) MovementSpeed = Utils.movementSpeedWithoutBattle.get(item);
                else if (item instanceof RandomCurios)
                    MovementSpeed = data.getDouble(StringUtils.CuriosAttribute.MovementSpeed) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.MovementSpeed);
                else MovementSpeed = data.getInt(StringUtils.CuriosAttribute.MovementSpeed);
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


                double GemsValue = gemsMovementSpeedUp(data);

                if ((GemsValue) != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
                }

                index++;
                event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
            }
        } else type++;
        if (!Utils.attackDamage.containsKey(item) && gemsAttackDamage(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 物理攻击").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" " + String.format("%.0f", gemsAttackDamage(data))).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (!Utils.critRate.containsKey(item) && gemsCritRate(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+" + String.format("%.1f%%", gemsCritRate(data) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.critDamage.containsKey(item) && gemsCritDamage(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 暴击伤害").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+" + String.format("%.0f%%", gemsCritDamage(data) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.manaDamage.containsKey(item) && gemsManaDamage(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal(" " + String.format("%.0f", gemsManaDamage(data))).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.manaRecover.containsKey(item) && gemsManaRecover(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+" + String.format("%.1f", gemsManaRecover(data))).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.defence.containsKey(item) && gemsDefence(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 基础护甲").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+" + String.format("%.0f", gemsDefence(data))).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.maxHealth.containsKey(item) && gemsMaxHealth(data) != 0) {
            MutableComponent mutableComponent = Component.literal("");
            if (gemsMaxHealth(data) > 0) {
                mutableComponent.append(Component.literal(" 最大生命值").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("+" + String.format("%.0f", gemsMaxHealth(data))).withStyle(ChatFormatting.WHITE)));
            } else {
                mutableComponent.append(Component.literal(" 最大生命值").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("-" + String.format("%.0f", -gemsMaxHealth(data))).withStyle(ChatFormatting.RED)));

            }
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.healthRecover.containsKey(item) && gemsHealRecover(data) != 0) {
            MutableComponent mutableComponent = Component.literal("");
            if (gemsHealRecover(data) > 0) {
                mutableComponent.append(Component.literal(" 生命回复").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("+" + String.format("%.1f", gemsHealRecover(data))).withStyle(ChatFormatting.WHITE)));
            } else {
                mutableComponent.append(Component.literal(" 生命回复").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("-" + String.format("%.1f", -gemsHealRecover(data))).withStyle(ChatFormatting.RED)));

            }
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.coolDownDecrease.containsKey(item) && gemsCoolDown(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 技能急速").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" " + String.format("%.0f", gemsCoolDown(data) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.movementSpeedWithoutBattle.containsKey(item) && gemsMovementSpeedUp(data) != 0) {
            MutableComponent mutableComponent = Component.literal("");
            if (gemsMovementSpeedUp(data) > 0) {
                mutableComponent.append(Component.literal(" 移动速度").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("+" + String.format("%.0f%%", gemsMovementSpeedUp(data) * 100)).withStyle(ChatFormatting.WHITE)));
            } else {
                mutableComponent.append(Component.literal(" 移动速度").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("-" + String.format("%.0f%%", -gemsMovementSpeedUp(data) * 100)).withStyle(ChatFormatting.RED)));

            }
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (Utils.expUp.containsKey(item) || data.contains(StringUtils.CuriosAttribute.ExpUp)) {
            double ExpUp;
            if (item instanceof RandomCurios)
                ExpUp = data.getDouble(StringUtils.CuriosAttribute.ExpUp) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ExpUp);
            else ExpUp = Utils.expUp.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+" + String.format("%.0f%%", ExpUp * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (Utils.swiftnessUp.containsKey(item) || data.contains(StringUtils.CuriosAttribute.SwiftnessUp)) {
            double SwiftnessUp;
            if (item instanceof RandomCurios)
                SwiftnessUp = data.getDouble(StringUtils.CuriosAttribute.SwiftnessUp) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.SwiftnessUp);
            else SwiftnessUp = Utils.swiftnessUp.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 迅捷加成").withStyle(CustomStyle.styleOfFlexible).
                    append(Component.literal("+" + String.format("%.1f", SwiftnessUp)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (Utils.manaHealthSteal.containsKey(item) || data.contains(StringUtils.CuriosAttribute.ManaHealthSteal)) {
            double ManaHealSteal;
            if (Utils.manaHealthSteal.containsKey(item)) ManaHealSteal = Utils.manaHealthSteal.get(item);
            else if (item instanceof RandomCurios)
                ManaHealSteal = data.getDouble(StringUtils.CuriosAttribute.ManaHealthSteal) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaHealthSteal);
            else ManaHealSteal = data.getInt(StringUtils.CuriosAttribute.ManaHealthSteal);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 法术吸血").withStyle(CustomStyle.styleOfBloodMana).
                    append(Component.literal("+" + String.format("%.0f‱", ManaHealSteal * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.manaHealthSteal.containsKey(item) && gemsManaHealthSteal(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 法术吸血").withStyle(CustomStyle.styleOfBloodMana).
                    append(Component.literal("+" + String.format("%.0f‱", gemsManaHealthSteal(data) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (Utils.healEffectUp.containsKey(item) || data.contains(StringUtils.CuriosAttribute.HealEffectUp)) {

            double HealEffectUp;
            if (Utils.healEffectUp.containsKey(item)) HealEffectUp = Utils.healEffectUp.get(item);
            else if (item instanceof RandomCurios)
                HealEffectUp = data.getDouble(StringUtils.CuriosAttribute.HealEffectUp) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.HealEffectUp);
            else HealEffectUp = data.getInt(StringUtils.CuriosAttribute.HealEffectUp);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 治疗强度").withStyle(CustomStyle.styleOfHealth).
                    append(Component.literal("+" + String.format("%.0f%%", HealEffectUp * 100)).withStyle(ChatFormatting.WHITE)));

            double GemsValue = gemsHealEffectUp(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
            }

            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.healEffectUp.containsKey(item) && gemsHealEffectUp(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 治疗强度").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+" + String.format("%.0f%%", gemsHealEffectUp(data) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.defencePenetration0.containsKey(item) && gemsDefencePenetration0(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+" + String.format("%.0f", gemsDefencePenetration0(data))).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.manaPenetration0.containsKey(item) && gemsManaPenetration0(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 法术穿透").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+" + String.format("%.0f", gemsManaPenetration0(data))).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.expUp.containsKey(item) && gemsExpUp(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+" + String.format("%.0f%%", gemsExpUp(data) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Utils.luckyUp.containsKey(item) || data.contains(StringUtils.CuriosAttribute.LuckyUp)) {
            double LuckyUp;
            if (Utils.luckyUp.containsKey(item)) LuckyUp = Utils.luckyUp.get(item);
            else LuckyUp = data.getInt(StringUtils.CuriosAttribute.LuckyUp);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 幸运加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal(" " + String.format("%.0f%%", LuckyUp * 100)).withStyle(ChatFormatting.WHITE)));

            double GemsValue = gemsLuckyUp(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
            }

            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (!Utils.luckyUp.containsKey(item) && gemsLuckyUp(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 幸运加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+" + String.format("%.0f%%", gemsLuckyUp(data) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (!Utils.defencePenetration.containsKey(item) && gemsDefencePenetration(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+" + String.format("%.0f%%", gemsDefencePenetration(data) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.manaPenetration.containsKey(item) && gemsManaPenetration(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+" + String.format("%.0f%%", gemsManaPenetration(data) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
        if (!Utils.healthSteal.containsKey(item) && gemsHealthSteal(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 生命偷取").withStyle(ChatFormatting.RED).
                    append(Component.literal("+" + String.format("%.0f‱", gemsHealthSteal(data) * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (!Utils.manaDefence.containsKey(item) && gemsManaDefence(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 魔法抗性").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+" + String.format("%.0f", gemsManaDefence(data))).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Element.LifeElementValue.containsKey(item)) {
            double value = Element.LifeElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化生机元素强度").withStyle(CustomStyle.styleOfLife).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Element.WaterElementValue.containsKey(item)) {
            double value = Element.WaterElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化碧水元素强度").withStyle(CustomStyle.styleOfWater).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Element.FireElementValue.containsKey(item)) {
            double value = Element.FireElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化炽焰元素强度").withStyle(CustomStyle.styleOfFire).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Element.StoneElementValue.containsKey(item)) {
            double value = Element.StoneElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化层岩元素强度").withStyle(CustomStyle.styleOfStone).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Element.IceElementValue.containsKey(item)) {
            double value = Element.IceElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化凛冰元素强度").withStyle(CustomStyle.styleOfIce).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Element.LightningElementValue.containsKey(item)) {
            double value = Element.LightningElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化怒雷元素强度").withStyle(CustomStyle.styleOfLightning).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;

        if (Element.WindElementValue.containsKey(item)) {
            double value = Element.WindElementValue.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 归一化澄风元素强度").withStyle(CustomStyle.styleOfWind).
                    append(Component.literal("+" + String.format("%.0f%%", value * 100)).withStyle(ChatFormatting.WHITE)));
            index++;
            event.getTooltipElements().add(index, Either.right(new MyClientTooltip.MyTooltip(mutableComponent, type++)));
        } else type++;
    }

    public static void BasicAttributeCommonDescription(List<Component> components, ItemStack itemStack) {
        int forgeQuality = ForgeEquipUtils.getForgeQualityOnEquip(itemStack);
        if (forgeQuality != -1) {
            components.add(Component.literal("").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("θ-锻造品质: ").withStyle(CustomStyle.styleOfGold)).
                    append(ForgeEquipUtils.description.get(forgeQuality)));
        }
/*        CompoundTag data0 = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Item item = itemStack.getItem();
        if (itemStack.getItem() instanceof PurpleArmor && !data0.contains(StringUtils.RandomAttribute.AttackDamage)) {
            components.add(Compute.AttributeDescription.AttackDamage("50 - 70"));
            components.add(Compute.AttributeDescription.ManaDamage("75 - 95"));
            components.add(Compute.AttributeDescription.Defence("50 - 100"));
            components.add(Compute.AttributeDescription.MaxHealth("400 - 800"));
        }
        if (itemStack.getItem() instanceof IceArmor && !data0.contains(StringUtils.RandomAttribute.AttackDamage)) {
            components.add(Compute.AttributeDescription.AttackDamage("100 - 140"));
            components.add(Compute.AttributeDescription.ManaDamage("150 - 190"));
            components.add(Compute.AttributeDescription.Defence("100 - 200"));
            components.add(Compute.AttributeDescription.MaxHealth("800 - 1600"));
        }

        if (Utils.BaseDamage.containsKey(item) || data0.contains(StringUtils.RandomAttribute.AttackDamage)) {
            double BaseDamage;
            if (Utils.BaseDamage.containsKey(item)) BaseDamage = Utils.BaseDamage.get(item);
            else BaseDamage = data0.getInt(StringUtils.RandomAttribute.AttackDamage);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(Utils.Emoji.Sword + " 基础攻击").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" "+String.format("%.0f",BaseDamage)).withStyle(ChatFormatting.WHITE)));


            double ExDamageForging = 0;
            if (data.contains("Forging")) ExDamageForging = ForgingValue(data.getInt("Forging"),BaseDamage);
            double ExDamageProficiency = 0;
            if (data.contains("KillCount")) ExDamageProficiency = BaseDamage * 0.5 * Math.min(1,(data.getInt("KillCount") / 100000.0));
            ChatFormatting[] chatFormattings = {
                    ChatFormatting.GREEN,
                    ChatFormatting.AQUA,
                    ChatFormatting.YELLOW,
                    ChatFormatting.LIGHT_PURPLE,
                    ChatFormatting.RED
            };

            double GemsValue = ItemBaseDamageGems(data);

            if (ExDamageProficiency != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageProficiency)).
                        withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000,4)]));
            }

            if ((ExDamageForging + GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageForging + GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW));
            }

            components.add(mutableComponent);
        }

        if (Utils.ManaDamage.containsKey(item) || data0.contains(StringUtils.RandomAttribute.ManaDamage)) {
            double BaseDamage;
            if (Utils.ManaDamage.containsKey(item)) BaseDamage = Utils.ManaDamage.get(item);
            else BaseDamage = data0.getInt(StringUtils.RandomAttribute.ManaDamage);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(Utils.Emoji.Mana + " 法术攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal(" "+String.format("%.0f",BaseDamage)).withStyle(ChatFormatting.WHITE)));


            double ExDamageForging = 0;
            if (data.contains("Forging")) ExDamageForging = ForgingValue(data.getInt("Forging"),BaseDamage);
            double ExDamageProficiency = 0;
            if (data.contains("KillCount")) ExDamageProficiency = BaseDamage * 0.5 * Math.min(1,(data.getInt("KillCount") / 100000.0));
            ChatFormatting[] chatFormattings = {
                    ChatFormatting.GREEN,
                    ChatFormatting.AQUA,
                    ChatFormatting.YELLOW,
                    ChatFormatting.LIGHT_PURPLE,
                    ChatFormatting.RED
            };

            double GemsValue = ItemManaDamageGems(data);

            if (ExDamageProficiency != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageProficiency)).
                        withStyle(ChatFormatting.RESET).withStyle(chatFormattings[Math.min(data.getInt("KillCount") / 20000,4)]));
            }

            if ((ExDamageForging + GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageForging + GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
            }

            components.add(mutableComponent);
        }

        if (Utils.Defence.containsKey(item) || data0.contains(StringUtils.RandomAttribute.Defence)) {
            double Defence;
            if (Utils.Defence.containsKey(item)) Defence = Utils.Defence.get(item);
            else Defence = data0.getInt(StringUtils.RandomAttribute.Defence);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(Utils.Emoji.Defence + " 基础护甲").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+"+String.format("%.0f",Defence)).withStyle(ChatFormatting.WHITE)));


            double ExDamageForging = 0;
            if (data.contains("Forging")) ExDamageForging = ForgingValue(data.getInt("Forging"),Defence);

            double GemsValue = ItemDefenceGems(data);

            if ((ExDamageForging + GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageForging + GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
            }

            components.add(mutableComponent);
        }

        if (Utils.MaxHealth.containsKey(item) || data0.contains(StringUtils.RandomAttribute.MaxHealth)) {
            double MaxHealth;
            if (Utils.MaxHealth.containsKey(item)) MaxHealth = Utils.MaxHealth.get(item);
            else MaxHealth = data0.getInt(StringUtils.RandomAttribute.MaxHealth);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(Utils.Emoji.Health + " 最大生命值").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+"+String.format("%.0f",MaxHealth)).withStyle(ChatFormatting.WHITE)));



            double ExHealth = 0;
            if (data.contains("Forging")) ExHealth = ForgingValue(data.getInt("Forging"),MaxHealth);

            double GemsValue = ItemMaxHealthGems(data);

            if ((ExHealth + GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExHealth + GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
            }
            components.add(mutableComponent);
        }

        if (Utils.HealthRecover.containsKey(item)) {
            double HealthReplay = Utils.HealthRecover.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(Utils.Emoji.HealthRecover + " 生命回复").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+"+String.format("%.1f",HealthReplay)).withStyle(ChatFormatting.WHITE)));



            double GemsValue = ItemHealReplyGems(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.1f", GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
            }
            components.add(mutableComponent);
        }

        if (Utils.DefencePenetration.containsKey(item)) {
            EmojiDescriptionDefencePenetration(components,Utils.DefencePenetration.get(item));
        }

        if (Utils.DefencePenetration0.containsKey(item)) {
            EmojiDescriptionDefencePenetration0(components,Utils.DefencePenetration0.get(item));
        }

        if (Utils.CriticalHitRate.containsKey(item)) {
            double CriticalHitRate = Utils.CriticalHitRate.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(Utils.Emoji.CritRate + " 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+"+String.format("%.1f%%",CriticalHitRate * 100)).withStyle(ChatFormatting.WHITE)));



            double GemsValue = ItemCritRateGems(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.1f%%", GemsValue * 100)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
            }

            components.add(mutableComponent);
        }

        if (Utils.CritDamage.containsKey(item)) {
            double CritDamage = Utils.CritDamage.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(Utils.Emoji.CritDamage + " 暴击伤害").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+"+String.format("%.0f%%",CritDamage * 100)).withStyle(ChatFormatting.WHITE)));



            double GemsValue = ItemCritDamageGems(data);
            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE));
            }

            components.add(mutableComponent);
        }

        if (Utils.HealSteal.containsKey(item)) {
            EmojiDescriptionHealSteal(components,Utils.HealSteal.get(item));
        }

        if (Utils.ManaCost.containsKey(item)) {
            ManaCostDescription(components,Utils.ManaCost.get(item));
        }

        if (Utils.MaxMana.containsKey(item)) {
            EmojiDescriptionMaxMana(components,Utils.MaxMana.get(item));
        }

        if (Utils.ManaDefencePenetration.containsKey(item)) {
            EmojiDescriptionManaPenetration(components,Utils.ManaDefencePenetration.get(item));
        }

        if (Utils.ManaDefencePenetration0.containsKey(item)) {
            EmojiDescriptionManaPenetration0(components,Utils.ManaDefencePenetration0.get(item));
        }

        if (Utils.ManaRecover.containsKey(item)) {
            double ManaRecover = Utils.ManaRecover.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(Utils.Emoji.ManaRecover + " 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+"+String.format("%.0f",ManaRecover)).withStyle(ChatFormatting.WHITE)));



            double GemsValue = ItemManaRecoverGems(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
            }

            components.add(mutableComponent);
        }

        if (Utils.CoolDownDecrease.containsKey(item)) {
            double CoolDown = Utils.CoolDownDecrease.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(Utils.Emoji.Sword + " 冷却缩减").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" "+String.format("%.1f%%",CoolDown * 100)).withStyle(ChatFormatting.WHITE)));



            double GemsValue = ItemCoolDownGems(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.1f%%", GemsValue * 100)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
            }

            components.add(mutableComponent);
        }

        if (Utils.MovementSpeed.containsKey(item)) {
            double MovementSpeed = Utils.MovementSpeed.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(Utils.Emoji.Speed + " 移动速度").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+"+String.format("%.0f%%",MovementSpeed * 100)).withStyle(ChatFormatting.WHITE)));



            double GemsValue = ItemMovementSpeedUpGems(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
            }

            components.add(mutableComponent);
        }


        if (!Utils.BaseDamage.containsKey(item) && ItemBaseDamageGems(data) > 0) {
            EmojiDescriptionBaseAttackDamage(components,ItemBaseDamageGems(data));
        }
        if (!Utils.CriticalHitRate.containsKey(item) && ItemCritRateGems(data) > 0) {
            EmojiDescriptionCritRate(components,ItemCritRateGems(data));
        }
        if (!Utils.CritDamage.containsKey(item) && ItemCritDamageGems(data) > 0) {
            EmojiDescriptionCritDamage(components,ItemCritDamageGems(data));
        }
        if (!Utils.ManaDamage.containsKey(item) && ItemManaDamageGems(data) > 0) {
            EmojiDescriptionManaAttackDamage(components,ItemManaDamageGems(data));
        }
        if (!Utils.ManaRecover.containsKey(item) && ItemManaRecoverGems(data) > 0) {
            EmojiDescriptionManaRecover(components,ItemManaRecoverGems(data));
        }
        if (!Utils.Defence.containsKey(item) && ItemDefenceGems(data) > 0) {
            EmojiDescriptionDefence(components,ItemDefenceGems(data));
        }
        if (!Utils.MaxHealth.containsKey(item) && ItemMaxHealthGems(data) > 0) {
            EmojiDescriptionMaxHealth(components,ItemMaxHealthGems(data));
        }
        if (!Utils.HealthRecover.containsKey(item) && ItemHealReplyGems(data) > 0) {
            EmojiDescriptionHealthRecover(components,ItemHealReplyGems(data));
        }
        if (!Utils.CoolDownDecrease.containsKey(item) && ItemCoolDownGems(data) > 0) {
            EmojiDescriptionCoolDown(components,ItemCoolDownGems(data));
        }
        if (!Utils.MovementSpeed.containsKey(item) && ItemMovementSpeedUpGems(data) > 0) {
            EmojiDescriptionMovementSpeed(components,ItemMovementSpeedUpGems(data));
        }*/

    }

}

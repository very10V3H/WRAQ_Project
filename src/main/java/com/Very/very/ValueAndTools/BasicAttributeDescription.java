package com.Very.very.ValueAndTools;

import com.Very.very.Render.ToolTip.MyClientTooltip;
import com.Very.very.Series.InstanceSeries.Castle.CastleCurios;
import com.Very.very.Series.InstanceSeries.Ice.IceArmor;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.MineWorker.PurpleArmor;
import com.Very.very.Series.WorldSoulSeries.SoulEquipAttribute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import com.mojang.datafixers.util.Either;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;

import java.util.List;

import static com.Very.very.ValueAndTools.Compute.*;

public class BasicAttributeDescription {

    public static void NewAttributeDescription(RenderTooltipEvent.GatherComponents event) {
        int index = 4;
        int type = 0;
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getTagElement(Utils.MOD_ID) == null && !Utils.OffHandTag.containsKey(itemStack.getItem())) return;
        CompoundTag data0 = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        Item item = itemStack.getItem();
        if (itemStack.is(ModItems.ManageSword.get())) return;
        if (itemStack.getItem() instanceof PurpleArmor && !data0.contains(StringUtils.RandomAttribute.AttackDamage)) {
            event.getTooltipElements().add(5, Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "50 - 100" + "攻击力").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW),0)));
            event.getTooltipElements().add(6,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "300 - 400" + "魔法攻击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),1)));
            event.getTooltipElements().add(7,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "50 - 100" + "护甲").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),2)));
            event.getTooltipElements().add(8,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "400 - 800" + "最大生命值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),3)));
        }
        if (itemStack.getItem() instanceof IceArmor && !data0.contains(StringUtils.RandomAttribute.AttackDamage)) {
            event.getTooltipElements().add(5, Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "100 - 140" + "攻击力").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW),0)));
            event.getTooltipElements().add(6,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "600 - 800" + "魔法攻击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),1)));
            event.getTooltipElements().add(7,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "100 - 200" + "护甲").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),2)));
            event.getTooltipElements().add(8,Either.right(new MyClientTooltip.MyTooltip(Component.literal(" " + "800 - 1600" + "最大生命值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),3)));
        }

        if (Utils.AttackDamage.containsKey(item) || data0.contains(StringUtils.RandomAttribute.AttackDamage)
                || data0.contains(StringUtils.CuriosAttribute.AttackDamage)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTime = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 物理攻击").withStyle(ChatFormatting.AQUA).
                            append(Component.literal(" "+String.format("%.0f",SoulEquipAttribute.BaseAttribute.SoulSword.AttackDamage)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.AttackDamage)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTime + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTime = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 物理攻击").withStyle(ChatFormatting.AQUA).
                            append(Component.literal(" "+String.format("%.0f",SoulEquipAttribute.BaseAttribute.SoulBow.AttackDamage)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.AttackDamage)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTime + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
            }
            else {
                double BaseDamage;
                if (Utils.AttackDamage.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.AttackDamage)) {
                    if (Utils.AttackDamage.containsKey(item)) BaseDamage = Utils.AttackDamage.get(item);
                    else if (item instanceof CastleCurios) BaseDamage = data0.getDouble(StringUtils.CuriosAttribute.AttackDamage) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.AttackDamage);
                    else BaseDamage = data0.getInt(StringUtils.CuriosAttribute.AttackDamage);
                }
                else BaseDamage = data0.getInt(StringUtils.RandomAttribute.AttackDamage);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 物理攻击").withStyle(ChatFormatting.AQUA).
                        append(Component.literal(" "+String.format("%.0f",BaseDamage)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
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

                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
        } else type ++;

        if (Utils.ManaDamage.containsKey(item) || data0.contains(StringUtils.RandomAttribute.ManaDamage)
                || data0.contains(StringUtils.CuriosAttribute.ManaDamage)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal(" "+String.format("%.0f",SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaAttackDamage)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaAttackDamage)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                        append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
            else {
                double BaseDamage;
                if (Utils.ManaDamage.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.ManaDamage)) {
                    if (Utils.ManaDamage.containsKey(item)) BaseDamage = Utils.ManaDamage.get(item);
                    else if (item instanceof CastleCurios) BaseDamage = data0.getDouble(StringUtils.CuriosAttribute.ManaDamage) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaDamage);
                    else BaseDamage = data0.getInt(StringUtils.CuriosAttribute.ManaDamage);
                }
                else BaseDamage = data0.getInt(StringUtils.RandomAttribute.ManaDamage);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 法术攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal(" "+String.format("%.0f",BaseDamage)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
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

                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
        } else type ++;

        if (Utils.Defence.containsKey(item) || data0.contains(StringUtils.RandomAttribute.Defence)
                || data0.contains(StringUtils.CuriosAttribute.Defence)) {
            double Defence;
            if (Utils.Defence.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.Defence)) {
                if (Utils.Defence.containsKey(item)) Defence = Utils.Defence.get(item);
                else if (item instanceof CastleCurios) Defence = data0.getDouble(StringUtils.CuriosAttribute.Defence) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.Defence);
                else Defence = data0.getInt(StringUtils.CuriosAttribute.Defence);
            }
            else Defence = data0.getInt(StringUtils.RandomAttribute.Defence);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 基础护甲").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+"+String.format("%.0f",Defence)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            double ExDamageForging = 0;
            if (data.contains("Forging")) ExDamageForging = ForgingValue(data.getInt("Forging"),Defence);

            double GemsValue = ItemDefenceGems(data);

            if ((ExDamageForging + GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageForging + GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
            }

            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;

        if (Utils.ManaDefence.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.ManaDefence)) {
            double ManaDefence = 0;
            if (Utils.ManaDefence.containsKey(item)) ManaDefence = Utils.ManaDefence.get(item);
            else if (item instanceof CastleCurios) ManaDefence = data0.getDouble(StringUtils.CuriosAttribute.ManaDefence) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaDefence);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 魔法抗性").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+"+String.format("%.0f",ManaDefence)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            double ExDamageForging = 0;
            if (data.contains("Forging")) ExDamageForging = ForgingValue(data.getInt("Forging"),ManaDefence);

            double GemsValue = ItemManaDefenceGems(data);

            if ((ExDamageForging + GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExDamageForging + GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_BLUE));
            }

            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;

        if (Utils.MaxHealth.containsKey(item) || data0.contains(StringUtils.RandomAttribute.MaxHealth)
                || data0.contains(StringUtils.CuriosAttribute.MaxHealth)) {
            double MaxHealth;
            if (Utils.MaxHealth.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.MaxHealth)) {
                if (Utils.MaxHealth.containsKey(item)) MaxHealth = Utils.MaxHealth.get(item);
                else if (item instanceof CastleCurios) MaxHealth = data0.getDouble(StringUtils.CuriosAttribute.MaxHealth) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.MaxHealth);
                else MaxHealth = data0.getInt(StringUtils.CuriosAttribute.MaxHealth);
            }
            else MaxHealth = data0.getInt(StringUtils.RandomAttribute.MaxHealth);
            MutableComponent mutableComponent = Component.literal("");
            if (MaxHealth > 0) {
                mutableComponent.append(Component.literal(  " 最大生命值").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("+"+String.format("%.0f",MaxHealth)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }

            if (MaxHealth < 0) {
                mutableComponent.append(Component.literal(  " 最大生命值").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("-"+String.format("%.0f",-MaxHealth)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)));

            }

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

            double ExHealth = 0;
            if (data.contains("Forging")) ExHealth = ForgingValue(data.getInt("Forging"),MaxHealth);

            double GemsValue = ItemMaxHealthGems(data);

            if ((ExHealth + GemsValue) > 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", ExHealth + GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
            }
            if ((ExHealth + GemsValue) < 0) {
                mutableComponent.append(Component.literal(" - " + String.format("%.0f", -(ExHealth + GemsValue))).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
            }
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;

        if (Utils.HealthRecover.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.HealthRecover)) {
            double HealthReplay;
            if (Utils.HealthRecover.containsKey(item)) HealthReplay = Utils.HealthRecover.get(item);
            else if (item instanceof CastleCurios) HealthReplay = data0.getDouble(StringUtils.CuriosAttribute.HealthRecover) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.HealthRecover);
            else HealthReplay = data0.getInt(StringUtils.CuriosAttribute.HealthRecover);

            MutableComponent mutableComponent = Component.literal("");
            if (HealthReplay > 0) {
                mutableComponent.append(Component.literal(  " 生命回复").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("+"+String.format("%.1f",HealthReplay)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
            else {
                mutableComponent.append(Component.literal(  " 生命回复").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("-"+String.format("%.1f",-HealthReplay)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)));
            }

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

            double GemsValue = ItemHealRecoverGems(data);

            if ((GemsValue) > 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.1f", GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
            }
            if (GemsValue < 0) {
                mutableComponent.append(Component.literal(" - " + String.format("%.1f", -GemsValue)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
            }
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;

        if (Utils.DefencePenetration.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.DefencePenetration)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())) {
/*                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("+"+String.format("%.0f%%",SoulEquipAttribute.BaseAttribute.SoulSword.DefencePenetration*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.DefencePenetration * 100)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("+"+String.format("%.0f%%",SoulEquipAttribute.BaseAttribute.SoulBow.DefencePenetration*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.DefencePenetration * 100)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }*/
            }
            else {
                double DefencePenetration;
                if (Utils.DefencePenetration.containsKey(item)) DefencePenetration = Utils.DefencePenetration.get(item);
                else if (item instanceof CastleCurios) DefencePenetration = data0.getDouble(StringUtils.CuriosAttribute.DefencePenetration) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.DefencePenetration);
                else DefencePenetration = data0.getInt(StringUtils.CuriosAttribute.DefencePenetration);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 护甲穿透").withStyle(ChatFormatting.GRAY).
                        append(Component.literal("+"+String.format("%.0f%%",DefencePenetration * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                double GemsValue = ItemDefencePenetrationGems(data);

                if (GemsValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                }
                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
        } else type ++;

        if (Utils.DefencePenetration0.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.DefencePenetration0)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("+"+String.format("%.0f",SoulEquipAttribute.BaseAttribute.SoulSword.DefencePenetration0)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.DefencePenetration0)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 护甲穿透").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("+"+String.format("%.0f",SoulEquipAttribute.BaseAttribute.SoulBow.DefencePenetration0)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.DefencePenetration0)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
            }
            else {
                double DefencePenetration;
                if (Utils.DefencePenetration0.containsKey(item)) DefencePenetration = Utils.DefencePenetration0.get(item);
                else if (item instanceof CastleCurios) DefencePenetration = data0.getDouble(StringUtils.CuriosAttribute.DefencePenetration0) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.DefencePenetration0);
                else DefencePenetration = data0.getInt(StringUtils.CuriosAttribute.DefencePenetration0);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 护甲穿透").withStyle(ChatFormatting.GRAY).
                        append(Component.literal("+"+String.format("%.0f",DefencePenetration)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                double GemsValue = ItemDefencePenetration0Gems(data);

                if (GemsValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", GemsValue)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                }
                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
        } else type ++;

        if (Utils.CritRate.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.CritRate)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal("+"+String.format("%.1f%%",SoulEquipAttribute.BaseAttribute.SoulSword.CritRate*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.1f%%", SoulEquipAttribute.ForgingAddition.CritRate * 100)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                            append(Component.literal("+"+String.format("%.1f%%",SoulEquipAttribute.BaseAttribute.SoulBow.CritRate*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.1f%%", SoulEquipAttribute.ForgingAddition.CritRate * 100)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
            }
            else {
                double CriticalHitRate;
                if (Utils.CritRate.containsKey(item)) CriticalHitRate = Utils.CritRate.get(item);
                else if (item instanceof CastleCurios) CriticalHitRate = data0.getDouble(StringUtils.CuriosAttribute.CritRate) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.CritRate);
                else CriticalHitRate = data0.getInt(StringUtils.CuriosAttribute.CritRate);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+"+String.format("%.1f%%",CriticalHitRate * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

                double GemsValue = ItemCritRateGems(data);

                if ((GemsValue) != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.1f%%", GemsValue * 100)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
                }

                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
        } else type ++;

        if (Utils.CritDamage.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.CritDamage)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 暴击伤害").withStyle(ChatFormatting.BLUE).
                            append(Component.literal("+"+String.format("%.0f%%",SoulEquipAttribute.BaseAttribute.SoulBow.CritDamage * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.CritDamage * 100)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 暴击伤害").withStyle(ChatFormatting.BLUE).
                            append(Component.literal("+"+String.format("%.0f%%",SoulEquipAttribute.BaseAttribute.SoulBow.CritDamage * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.CritDamage * 100)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
            }
            else {
                double CritDamage;
                if (Utils.CritDamage.containsKey(item)) CritDamage = Utils.CritDamage.get(item);
                else if (item instanceof CastleCurios) CritDamage = data0.getDouble(StringUtils.CuriosAttribute.CritDamage) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.CritDamage);
                else CritDamage = data0.getInt(StringUtils.CuriosAttribute.CritDamage);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 暴击伤害").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+"+String.format("%.0f%%",CritDamage * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

                double GemsValue = ItemCritDamageGems(data);
                if ((GemsValue) != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE));
                }

                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
        } else type ++;

        if (Utils.HealthSteal.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.HealthSteal)) {
            if (itemStack.is(ModItems.SoulSword.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 生命偷取").withStyle(ChatFormatting.RED).
                            append(Component.literal("+"+String.format("%.0f‱",SoulEquipAttribute.BaseAttribute.SoulSword.HealthSteal * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f‱", SoulEquipAttribute.ForgingAddition.HealthSteal * 100)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
            }
            else {

                double HealSteal;
                if (Utils.HealthSteal.containsKey(item)) HealSteal = Utils.HealthSteal.get(item);
                else if (item instanceof CastleCurios) HealSteal = data0.getDouble(StringUtils.CuriosAttribute.HealthSteal) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.HealthSteal);
                else HealSteal = data0.getInt(StringUtils.CuriosAttribute.HealthSteal);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 生命偷取").withStyle(ChatFormatting.RED).
                        append(Component.literal("+"+String.format("%.0f‱",HealSteal*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                double GemsValue = ItemMaxHealthGems(data);

                if (GemsValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f‱", GemsValue)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED));
                }


                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));

            }
        } else type ++;

        if (Utils.ManaCost.containsKey(item)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                        append(Component.literal(" "+String.format("%.0f",SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaCost)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("- " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaCost)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                        append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
            else {
                double ManaCost = Utils.ManaCost.get(item);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 法力消耗").withStyle(ChatFormatting.DARK_PURPLE).
                        append(Component.literal(" "+String.format("%.0f",ManaCost)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
        } else type ++;

        if (Utils.MaxMana.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.MaxMana)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+"+String.format("%.0f",SoulEquipAttribute.BaseAttribute.SoulSceptre.MaxMana)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ 16 x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
            else {
                double MaxMana;
                if (Utils.MaxMana.containsKey(item)) MaxMana = Utils.MaxMana.get(item);
                else if (item instanceof CastleCurios) MaxMana = data0.getDouble(StringUtils.CuriosAttribute.MaxMana) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.MaxMana);
                else MaxMana = data0.getInt(StringUtils.CuriosAttribute.MaxMana);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 最大法力值").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+"+String.format("%.0f",MaxMana)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));

            }
        } else type ++;

        if (Utils.ManaPenetration.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.ManaPenetration)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
/*
                int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+"+String.format("%.0f%%",SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaPenetration * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.ManaPenetration * 100)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                        append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
*/
            }
            else {
                double ManaPenetration;
                if (Utils.ManaPenetration.containsKey(item)) ManaPenetration = Utils.ManaPenetration.get(item);
                else if (item instanceof CastleCurios) ManaPenetration = data0.getDouble(StringUtils.CuriosAttribute.ManaPenetration) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaPenetration);
                else ManaPenetration = data0.getInt(StringUtils.CuriosAttribute.ManaPenetration);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 魔法穿透").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+"+String.format("%.0f%%",ManaPenetration*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                double GemsValue = ItemManaPenetrationGems(data);

                if (GemsValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
                }

                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
        } else type ++;

        if (Utils.ManaPenetration0.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.ManaPenetration0)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 魔法穿透").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+"+String.format("%.0f",SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaPenetration0)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaPenetration0)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                        append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
            else {
                double ManaPenetration0;
                if (Utils.ManaPenetration0.containsKey(item)) ManaPenetration0 = Utils.ManaPenetration0.get(item);
                else if (item instanceof CastleCurios) ManaPenetration0 = data0.getDouble(StringUtils.CuriosAttribute.ManaPenetration0) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaPenetration0);
                else ManaPenetration0 = data0.getInt(StringUtils.CuriosAttribute.ManaPenetration0);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 魔法穿透").withStyle(ChatFormatting.BLUE).
                        append(Component.literal("+"+String.format("%.0f",ManaPenetration0)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                double GemsValue = ItemManaPenetration0Gems(data);

                if (GemsValue != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", GemsValue)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
                }

                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
        } else type ++;

        if (Utils.ManaRecover.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.ManaRecover)) {
            if (itemStack.is(ModItems.SoulSceptre.get())) {
                int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(" 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+"+String.format("%.0f",SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaRecover)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("  ")).
                        append(Component.literal("+ " + String.format("%.0f", SoulEquipAttribute.ForgingAddition.ManaRecover)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                        append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
            else {
                double ManaRecover;
                if (Utils.ManaRecover.containsKey(item)) ManaRecover = Utils.ManaRecover.get(item);
                else if (item instanceof CastleCurios) ManaRecover = data0.getDouble(StringUtils.CuriosAttribute.ManaRecover) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaRecover);
                else ManaRecover = data0.getInt(StringUtils.CuriosAttribute.ManaRecover);

                MutableComponent mutableComponent = Component.literal("");
                mutableComponent.append(Component.literal(  " 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal("+"+String.format("%.0f",ManaRecover)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

                double GemsValue = ItemManaRecoverGems(data);

                if ((GemsValue) != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f", GemsValue)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
                }

                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
        } else type ++;

        if (Utils.CoolDownDecrease.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.CoolDown)) {

            double CoolDown;
            if (Utils.CoolDownDecrease.containsKey(item)) CoolDown = Utils.CoolDownDecrease.get(item);
            else if (item instanceof CastleCurios) CoolDown = data0.getDouble(StringUtils.CuriosAttribute.CoolDown) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.CoolDown);
            else CoolDown = data0.getInt(StringUtils.CuriosAttribute.CoolDown);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 技能急速").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" "+String.format("%.0f",CoolDown * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

            double GemsValue = ItemCoolDownGems(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f", GemsValue * 100)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
            }

            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;

        if (Utils.MovementSpeed.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.MovementSpeed)) {
            if (itemStack.is(ModItems.SoulSword.get()) || itemStack.is(ModItems.SoulBow.get())
                    || itemStack.is(ModItems.SoulSceptre.get())) {
                if (itemStack.is(ModItems.SoulSword.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("+"+String.format("%.0f%%",SoulEquipAttribute.BaseAttribute.SoulSword.MovementSpeed*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
                if (itemStack.is(ModItems.SoulBow.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("+"+String.format("%.0f%%",SoulEquipAttribute.BaseAttribute.SoulBow.MovementSpeed*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
                if (itemStack.is(ModItems.SoulSceptre.get())) {
                    int ForgeTimes = data0.getInt(StringUtils.SoulEquipForge);
                    MutableComponent mutableComponent = Component.literal("");
                    mutableComponent.append(Component.literal(" 移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("+"+String.format("%.0f%%",SoulEquipAttribute.BaseAttribute.SoulSceptre.MovementSpeed*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("  ")).
                            append(Component.literal("+ " + String.format("%.0f%%", SoulEquipAttribute.ForgingAddition.MovementSpeed * 100)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)).
                            append(Component.literal(" x [" + ForgeTimes + "]").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfWorld)));
                    index ++;
                    event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
                }
            }
            else {
                double MovementSpeed;
                if (Utils.MovementSpeed.containsKey(item)) MovementSpeed = Utils.MovementSpeed.get(item);
                else if (item instanceof CastleCurios) MovementSpeed = data0.getDouble(StringUtils.CuriosAttribute.MovementSpeed) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.MovementSpeed);
                else MovementSpeed = data0.getInt(StringUtils.CuriosAttribute.MovementSpeed);

                MutableComponent mutableComponent = Component.literal("");

                if (MovementSpeed > 0) {
                    mutableComponent.append(Component.literal(  " 移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("+"+String.format("%.0f%%",MovementSpeed * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                }
                if (MovementSpeed < 0) {
                    mutableComponent.append(Component.literal(  " 移动速度").withStyle(ChatFormatting.GREEN).
                            append(Component.literal("-"+String.format("%.0f%%",-MovementSpeed * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)));

                }

                CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

                double GemsValue = ItemMovementSpeedUpGems(data);

                if ((GemsValue) != 0) {
                    mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                            withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
                }

                index ++;
                event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
            }
        } else type ++;

        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        if (!Utils.AttackDamage.containsKey(item) && ItemBaseDamageGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 物理攻击").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" "+String.format("%.0f",ItemBaseDamageGems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.CritRate.containsKey(item) && ItemCritRateGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+"+String.format("%.1f%%",ItemCritRateGems(data)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.CritDamage.containsKey(item) && ItemCritDamageGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 暴击伤害").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+"+String.format("%.0f%%",ItemCritDamageGems(data) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.ManaDamage.containsKey(item) && ItemManaDamageGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal(" "+String.format("%.0f",ItemManaDamageGems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.ManaRecover.containsKey(item) && ItemManaRecoverGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 法力回复").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+"+String.format("%.1f",ItemManaRecoverGems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.Defence.containsKey(item) && ItemDefenceGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 基础护甲").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+"+String.format("%.0f",ItemDefenceGems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.MaxHealth.containsKey(item) && ItemMaxHealthGems(data) != 0) {
            MutableComponent mutableComponent = Component.literal("");
            if (ItemMaxHealthGems(data) > 0) {
                mutableComponent.append(Component.literal(  " 最大生命值").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("+"+String.format("%.0f",ItemMaxHealthGems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
            else {
                mutableComponent.append(Component.literal(  " 最大生命值").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("-"+String.format("%.0f",-ItemMaxHealthGems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)));

            }
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.HealthRecover.containsKey(item) && ItemHealRecoverGems(data) != 0) {
            MutableComponent mutableComponent = Component.literal("");
            if (ItemHealRecoverGems(data) > 0) {
                mutableComponent.append(Component.literal(  " 生命回复").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("+"+String.format("%.1f", ItemHealRecoverGems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
            else {
                mutableComponent.append(Component.literal(  " 生命回复").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("-"+String.format("%.1f", -ItemHealRecoverGems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)));

            }
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.CoolDownDecrease.containsKey(item) && ItemCoolDownGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 技能急速").withStyle(ChatFormatting.AQUA).
                    append(Component.literal(" "+String.format("%.0f",ItemCoolDownGems(data) * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.MovementSpeed.containsKey(item) && ItemMovementSpeedUpGems(data) != 0) {
            MutableComponent mutableComponent = Component.literal("");
            if (ItemMovementSpeedUpGems(data) > 0) {
                mutableComponent.append(Component.literal(  " 移动速度").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("+"+String.format("%.0f%%",ItemMovementSpeedUpGems(data)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
            else {
                mutableComponent.append(Component.literal(  " 移动速度").withStyle(ChatFormatting.GREEN).
                        append(Component.literal("-"+String.format("%.0f%%",-ItemMovementSpeedUpGems(data)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)));

            }
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (Utils.ExpUp.containsKey(item)) {
            double ExpUp = Utils.ExpUp.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+"+String.format("%.0f%%",ExpUp * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (Utils.SwiftnessUp.containsKey(item)) {
            double SwiftnessUp = Utils.SwiftnessUp.get(item);
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 迅捷加成").withStyle(Utils.styleOfFlexible).
                    append(Component.literal("+"+String.format("%.1f",SwiftnessUp)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (Utils.ManaHealthSteal.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.ManaHealthSteal)) {

            double ManaHealSteal;
            if (Utils.ManaHealthSteal.containsKey(item)) ManaHealSteal = Utils.ManaHealthSteal.get(item);
            else if (item instanceof CastleCurios) ManaHealSteal = data0.getDouble(StringUtils.CuriosAttribute.ManaHealthSteal) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.ManaHealthSteal);
            else ManaHealSteal = data0.getInt(StringUtils.CuriosAttribute.ManaHealthSteal);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(" 法术吸血").withStyle(Utils.styleOfBloodMana).
                    append(Component.literal("+"+String.format("%.0f‱",ManaHealSteal * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.ManaHealthSteal.containsKey(item) && ItemManaHealthStealGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 法术吸血").withStyle(Utils.styleOfBloodMana).
                    append(Component.literal("+"+String.format("%.0f‱",ItemManaHealthStealGems(data)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (Utils.HealEffectUp.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.HealEffectUp)) {

            double HealEffectUp;
            if (Utils.HealEffectUp.containsKey(item)) HealEffectUp = Utils.HealEffectUp.get(item);
            else if (item instanceof CastleCurios) HealEffectUp = data0.getDouble(StringUtils.CuriosAttribute.HealEffectUp) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.HealEffectUp);
            else HealEffectUp = data0.getInt(StringUtils.CuriosAttribute.HealEffectUp);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 治疗强度").withStyle(Utils.styleOfHealth).
                    append(Component.literal("+"+String.format("%.0f%%",HealEffectUp * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            double GemsValue = ItemHealStrengthGems(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
            }

            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.HealEffectUp.containsKey(item) && ItemHealStrengthGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 治疗强度").withStyle(ChatFormatting.GREEN).
                    append(Component.literal("+"+String.format("%.0f%%",ItemHealStrengthGems(data)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.DefencePenetration0.containsKey(item) && ItemDefencePenetration0Gems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 护甲穿透").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+"+String.format("%.0f",ItemDefencePenetration0Gems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.ManaPenetration0.containsKey(item) && ItemManaPenetration0Gems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 法术穿透").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+"+String.format("%.0f",ItemManaPenetration0Gems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.ExpUp.containsKey(item) && ItemExpUpGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 经验加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+"+String.format("%.0f%%",ItemExpUpGems(data)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;

        if (Utils.LuckyUp.containsKey(item) || data0.contains(StringUtils.CuriosAttribute.LuckyUp)) {
            double LuckyUp;
            if (Utils.LuckyUp.containsKey(item)) LuckyUp = Utils.LuckyUp.get(item);
            else if (item instanceof CastleCurios) LuckyUp = data0.getDouble(StringUtils.CuriosAttribute.LuckyUp) * CastleCurios.AttributeValueMap.get(StringUtils.CuriosAttribute.LuckyUp);
            else LuckyUp = data0.getInt(StringUtils.CuriosAttribute.LuckyUp);

            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 幸运加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal(" "+String.format("%.0f%%",LuckyUp * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            double GemsValue = ItemLuckyUpGems(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
            }

            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;

        if (!Utils.LuckyUp.containsKey(item) && ItemLuckyUpGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 幸运加成").withStyle(ChatFormatting.LIGHT_PURPLE).
                    append(Component.literal("+"+String.format("%.0f%%",ItemLuckyUpGems(data)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;

        if (!Utils.DefencePenetration.containsKey(item) && ItemDefencePenetrationGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 护甲穿透").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("+"+String.format("%.0f%%",ItemDefencePenetrationGems(data)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.ManaPenetration.containsKey(item) && ItemManaPenetrationGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 魔法穿透").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+"+String.format("%.0f%%",ItemManaPenetrationGems(data)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.HealthSteal.containsKey(item) && ItemHealthStealGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 生命偷取").withStyle(ChatFormatting.RED).
                    append(Component.literal("+"+String.format("%.0f‱",ItemHealthStealGems(data)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
        if (!Utils.ManaDefence.containsKey(item) && ItemManaDefenceGems(data) > 0) {
            MutableComponent mutableComponent = Component.literal("");
            mutableComponent.append(Component.literal(  " 魔法抗性").withStyle(ChatFormatting.BLUE).
                    append(Component.literal("+"+String.format("%.0f", ItemManaDefenceGems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            index ++;
            event.getTooltipElements().add(index,Either.right(new MyClientTooltip.MyTooltip(mutableComponent,type ++)));
        } else type ++;
    }

    public static void BasicAttributeCommonDescription(List<Component> components, ItemStack itemStack) {
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
                    append(Component.literal(" "+String.format("%.0f",BaseDamage)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
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
                    append(Component.literal(" "+String.format("%.0f",BaseDamage)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
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
                    append(Component.literal("+"+String.format("%.0f",Defence)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
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
                    append(Component.literal("+"+String.format("%.0f",MaxHealth)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

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
                    append(Component.literal("+"+String.format("%.1f",HealthReplay)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

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
                    append(Component.literal("+"+String.format("%.1f%%",CriticalHitRate * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

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
                    append(Component.literal("+"+String.format("%.0f%%",CritDamage * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

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
                    append(Component.literal("+"+String.format("%.0f",ManaRecover)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

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
                    append(Component.literal(" "+String.format("%.1f%%",CoolDown * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

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
                    append(Component.literal("+"+String.format("%.0f%%",MovementSpeed * 100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);

            double GemsValue = ItemMovementSpeedUpGems(data);

            if ((GemsValue) != 0) {
                mutableComponent.append(Component.literal(" + " + String.format("%.0f%%", GemsValue * 100)).
                        withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
            }

            components.add(mutableComponent);
        }

        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
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

package fun.wraq.series.overworld.divine.equip.weapon;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.damage.OnCauseFinalDamageEquip;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.impl.onkill.OnKillEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.AttackEvent;
import fun.wraq.core.ManaAttackModule;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface DivineWeaponCommon extends OnKillEffectEquip, InCuriosOrEquipSlotAttributesModify, ActiveItem,
        ForgeItem, OnCauseFinalDamageEquip {

    double getTransformRate();

    Style style = CustomStyle.DIVINE_STYLE;

    static List<Component> getCommonDescription(ItemStack stack, double upperLimitRate, int maxCount, boolean isAd,
                                                double maxActiveDistance) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionActive(components, Te.s("透体圣光", style));
        components.add(Te.s(" 释放一道", "圣光", style,
                " 长度可及:", String.format("%.0f", maxActiveDistance), style));
        components.add(Te.s(" 对路径的敌人造成一次", isAd ? "普攻伤害" : "法球伤害",
                isAd ? CustomStyle.styleOfPower : CustomStyle.styleOfMana));
        ComponentUtils.getStableCoolDownTimeDescription(components, 2);
        components.add(Te.s(" 圣光", CustomStyle.DIVINE_STYLE, "若击杀怪物，则", "无冷却时间", ChatFormatting.AQUA));
        ComponentUtils.descriptionPassive(components, Te.s("光域剥离", style));
        components.add(Te.s(" 手持该武器对怪物造成伤害，会使其", "显形", style));
        ComponentUtils.descriptionPassive(components, Te.s("神圣之力", style));
        components.add(Te.s(" 将除", "当前共鸣", ChatFormatting.AQUA, "以外的元素", "以", "20%", style, "的效率"));
        components.add(Te.s(" 转化为", "当前元素", ChatFormatting.AQUA, "的", "归一化元素强度", style));
        ComponentUtils.descriptionPassive(components, Te.s("圣光恩赐", style));
        Component countName = ComponentUtils.getRightAngleQuote("圣光恩赐", style);
        components.add(Te.s(" 击杀怪物将会受", countName, style));
        components.add(Te.s(" ", countName, "将存储在这件物品内"));
        components.add(Te.s(" ", countName, "至多为你提供:"));
        components.add(Te.s(ComponentUtils.AttributeDescription.getElementStrength(String.format("%.0f%%", upperLimitRate * 100)),
                "与", isAd ?
                        ComponentUtils.AttributeDescription.attackDamage(String.format("%.0f%%", upperLimitRate * 100))
                        : ComponentUtils.AttributeDescription.manaDamage(String.format("%.0f%%", upperLimitRate * 100))));
        int count = getDivineCount(stack);
        components.add(Te.s(" 圣光充盈度:"
                + String.format("%.0f%%", Math.min(count, maxCount) * 100.0 / maxCount), style,
                "(" + count + ")", ChatFormatting.GRAY, " ".repeat(4),
                ComponentUtils.getProgressBar(20, count, maxCount, style)));
        components.add(Te.s(" 在收集", maxCount + "层", style, countName, "后达最大值"));
        components.add(Te.s(" ", countName, "每日将会清空", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        return components;
    }

    List<Item> weaponList = new ArrayList<>();

    static void active(Player player, double distance) {
        Vec3 finalPos = Compute.getPickLocationIgnoreBlock(player, distance);
        DivineUtils.createDivineParticle(player, player.getEyePosition(), finalPos);
        Item mainHandItem = player.getMainHandItem().getItem();
        Set<Mob> mobs = Compute.getPlayerRayMobList(player, 0.5, 0.5, distance)
                .stream().filter(LivingEntity::isAlive).collect(Collectors.toSet());
        mobs.forEach(mob -> {
            if (mainHandItem instanceof WraqSword) {
                AttackEvent.attackToMonster(mob, player, 1, true,
                        AttackEvent.crit(player, mob, false));
            } else if (mainHandItem instanceof WraqBow) {
                MyArrow.causeDamage(player, mob, 1);
            } else if (mainHandItem instanceof WraqSceptre) {
                ManaAttackModule.causeBaseAttack(player, mob, 1, true);
            }
        });
        if (mobs.stream().noneMatch(LivingEntity::isDeadOrDying)) {
            weaponList.forEach(item -> {
                player.getCooldowns().addCooldown(item, 40);
            });
        }
    }

    String DIVINE_COUNT_DATA_KEY = "DivineCount";
    String DIVINE_COUNT_DATE_KEY = "DivineCountDate";

    static int getDivineCount(ItemStack stack) {
        return stack.getOrCreateTagElement(Utils.MOD_ID).getInt(DIVINE_COUNT_DATA_KEY);
    }

    static void setDivineCount(ItemStack stack, int divineCount) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putInt(DIVINE_COUNT_DATA_KEY, divineCount);
    }

    static Calendar getDivineCountDate(ItemStack stack) {
        if (!stack.getOrCreateTagElement(Utils.MOD_ID).contains(DIVINE_COUNT_DATE_KEY)) {
            return null;
        }
        try {
            return Compute.StringToCalendar(stack.getOrCreateTagElement(Utils.MOD_ID)
                    .getString(DIVINE_COUNT_DATE_KEY));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static void setDivineCountDate(ItemStack stack, Calendar date) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putString(DIVINE_COUNT_DATE_KEY, Compute.CalendarToString(date));
    }

    static void addDivineCount(ItemStack stack) {
        setDivineCount(stack, getDivineCount(stack) + 1);
        if (getDivineCountDate(stack) == null) {
            setDivineCountDate(stack, Calendar.getInstance());
        }
        if (getDivineCountDate(stack).get(Calendar.DATE) != Calendar.getInstance().get(Calendar.DATE)) {
            setDivineCount(stack, 0);
            setDivineCountDate(stack, Calendar.getInstance());
        }
    }

    static double getElementExceptOneElementValue(Player player, String exceptElement) {
        double value = 0;
        for (String s : Element.elementList) {
            if (!s.equals(exceptElement)) {
                value += ElementValue.getElementValueJudgeByType(player, s);
            }
        }
        return value;
    }

    static void onKillMob(Player player, Mob mob) {
        ParticleProvider.createBallDisperseParticle(ParticleTypes.END_ROD, (ServerLevel) player.level(),
                mob.getEyePosition(),0.25, 6);
    }

    static double getEnhanceElementValue(Player player, String type) {
        Item mainHandItem = player.getMainHandItem().getItem();
        if (mainHandItem instanceof DivineWeaponCommon divineWeaponCommon) {
            if (Element.getResonanceType(player) != null && Element.getResonanceType(player).equals(type)) {
                String resonanceType = Element.getResonanceType(player);
                double value = DivineWeaponCommon.getElementExceptOneElementValue(player, resonanceType);
                return value * divineWeaponCommon.getTransformRate();
            }
        }
        return 0;
    }

    static void onCauseDamageCommon(Player player, Mob mob) {
        Item item = player.getMainHandItem().getItem();
        if (item instanceof DivineWeaponCommon) {
            DivineUtils.addManifestOnMob(mob, Tick.s(10), item);
        }
    }
}

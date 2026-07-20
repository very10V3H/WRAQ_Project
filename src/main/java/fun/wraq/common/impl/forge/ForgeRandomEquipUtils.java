package fun.wraq.common.impl.forge;

import fun.wraq.common.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * AI-Generated, 2025-01-11
 * <p>
 * 锻造随机属性的工具类，负责从属性池中加权选取属性并将随机值写入物品的 NBT。
 */
public class ForgeRandomEquipUtils {

    /**
     * 标记 NBT 键名，用于标识该物品已通过锻造获得了随机属性。
     */
    public static final String FORGE_RANDOM_TAG = "forgeRandomEquip";

    /**
     * 为指定物品堆随机赋予属性。
     * <p>
     * 根据 {@link ForgeAttributeEntry} 的配置分三阶段处理：
     * <ol>
     *   <li><b>必带属性</b>（mandatory=true）→ 必定出现</li>
     *   <li><b>独立概率属性</b>（probability &lt; 1.0）→ 按各自概率独立判定</li>
     *   <li><b>权重池属性</b>（probability &gt;= 1.0 且 non-mandatory）
     *       → 按 weight 加权选取 min~max 个</li>
     * </ol>
     *
     * @param stack 要赋予随机属性的物品堆
     * @param equip 实现了 {@link ForgeRandomEquip} 的物品
     */
    public static void rollAttributes(ItemStack stack, ForgeRandomEquip equip) {
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        data.putBoolean(FORGE_RANDOM_TAG, true);
        Random random = new Random();

        List<ForgeAttributeEntry> mandatoryEntries = new ArrayList<>();
        List<ForgeAttributeEntry> probabilityEntries = new ArrayList<>();
        List<ForgeAttributeEntry> poolEntries = new ArrayList<>();

        for (ForgeAttributeEntry entry : equip.getForgeAttributePool()) {
            if (entry.mandatory()) {
                mandatoryEntries.add(entry);
            } else if (entry.probability() < 1.0) {
                probabilityEntries.add(entry);
            } else {
                poolEntries.add(entry);
            }
        }

        // Phase 1: 必带属性 → 全部出现
        for (ForgeAttributeEntry entry : mandatoryEntries) {
            rollOneAttribute(data, random, entry);
        }

        // Phase 2: 独立概率属性 → 各自独立判定
        for (ForgeAttributeEntry entry : probabilityEntries) {
            if (random.nextDouble() < entry.probability()) {
                rollOneAttribute(data, random, entry);
            }
        }

        // Phase 3: 权重池属性 → 按 min/max count 选取
        if (!poolEntries.isEmpty()) {
            int count = equip.minRandomAttributes() == equip.maxRandomAttributes()
                    ? equip.minRandomAttributes()
                    : random.nextInt(equip.minRandomAttributes(), equip.maxRandomAttributes() + 1);
            List<ForgeAttributeEntry> selected = selectWeightedRandom(
                    poolEntries, count, equip.distinctRandomAttributes(), random);
            for (ForgeAttributeEntry entry : selected) {
                rollOneAttribute(data, random, entry);
            }
        }
    }

    private static void rollOneAttribute(CompoundTag data, Random random, ForgeAttributeEntry entry) {
        double value = random.nextDouble(entry.minValue(), entry.maxValue());
        data.putDouble(entry.nbtKey(), value);
    }

    /**
     * 清除物品上所有锻造随机属性（用于重铸时重新 roll）。
     *
     * @param stack 要清除的物品堆
     * @param equip 实现了 {@link ForgeRandomEquip} 的物品
     */
    public static void clearAttributes(ItemStack stack, ForgeRandomEquip equip) {
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        for (ForgeAttributeEntry entry : equip.getForgeAttributePool()) {
            data.remove(entry.nbtKey());
        }
        data.remove(FORGE_RANDOM_TAG);
    }

    /**
     * 加权随机选取算法。
     * <p>
     * 每次从剩余池中根据权重概率选取一个属性。如果 {@code distinct} 为 true，
     * 选中的属性会被移除（不放回抽样）；否则可重复选取（放回抽样）。
     * <p>
     * 若 {@code distinct} 为 true 且 {@code count} 大于池大小，则取池大小作为实际选取数。
     *
     * @param pool     属性池
     * @param count    期望选取的数量
     * @param distinct 是否不允许重复
     * @param random   随机数生成器
     * @return 选取的属性条目列表
     */
    private static List<ForgeAttributeEntry> selectWeightedRandom(
            List<ForgeAttributeEntry> pool, int count, boolean distinct, Random random) {

        if (pool.isEmpty()) return List.of();

        int actualCount = distinct ? Math.min(count, pool.size()) : count;
        List<ForgeAttributeEntry> result = new ArrayList<>(actualCount);
        List<ForgeAttributeEntry> working = distinct ? new ArrayList<>(pool) : null;

        for (int i = 0; i < actualCount; i++) {
            List<ForgeAttributeEntry> source = distinct ? working : pool;
            double totalWeight = 0;
            for (ForgeAttributeEntry entry : source) {
                totalWeight += entry.weight();
            }
            if (totalWeight <= 0) break;

            double r = random.nextDouble() * totalWeight;
            double cumulative = 0;
            int chosenIndex = 0;
            for (int j = 0; j < source.size(); j++) {
                cumulative += source.get(j).weight();
                if (r < cumulative) {
                    chosenIndex = j;
                    break;
                }
            }

            ForgeAttributeEntry chosen = source.get(chosenIndex);
            result.add(chosen);
            if (distinct && working != null) {
                working.remove(chosenIndex);
            }
        }
        return result;
    }

    // ========== 预览工具提示 ==========

    private static final Map<String, String> ATTRIBUTE_DISPLAY_NAMES = Map.ofEntries(
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.attackDamage, "物理攻击"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.manaDamage, "法术攻击"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.defence, "基础护甲"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.maxHealth, "最大生命值"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.critRate, "暴击率"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.critDamage, "暴击伤害"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.healthSteal, "生命偷取"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.healthRecover, "生命回复"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.coolDown, "冷却缩减"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.movementSpeedWithoutBattle, "移动速度(脱战)"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.manaRecover, "法力回复"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.defencePenetration0, "护甲穿透"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.manaPenetration0, "魔法穿透"),
            Map.entry(fun.wraq.common.util.StringUtils.RandomAttributes.maxMana, "最大法力")
    );

    /**
     * @return 属性 NBT 键对应的中文显示名，未知键返回原字符串
     */
    public static String getDisplayName(String nbtKey) {
        return ATTRIBUTE_DISPLAY_NAMES.getOrDefault(nbtKey, nbtKey);
    }

    /**
     * 格式化数值，整数不显示小数，小数保留两位。
     */
    private static String formatValue(double value) {
        if (value == (long) value) return String.valueOf((long) value);
        return String.format("%.2f", value);
    }

    /**
     * 生成用于 ForgeScreen 预览的随机属性范围工具提示行。
     *
     * @param equip 实现了 {@link ForgeRandomEquip} 的物品
     * @return 工具提示行列表
     */
    public static List<Component> getPreviewTooltipLines(ForgeRandomEquip equip) {
        List<Component> lines = new ArrayList<>();
        lines.add(Component.literal("═══ 随机属性预览 ═══")
                .withStyle(style -> style.withColor(0xFFAA00)));

        // 统计三种模式的数量
        int mandatoryCount = 0, probCount = 0, poolCount = 0;
        for (ForgeAttributeEntry e : equip.getForgeAttributePool()) {
            if (e.mandatory()) mandatoryCount++;
            else if (e.probability() < 1.0) probCount++;
            else poolCount++;
        }

        if (mandatoryCount > 0) {
            lines.add(Component.literal(" ✔ 必带 " + mandatoryCount + " 个属性")
                    .withStyle(style -> style.withColor(0x55FF55)));
        }
        if (probCount > 0) {
            lines.add(Component.literal(" ◆ " + probCount + " 个属性各自独立概率出现")
                    .withStyle(style -> style.withColor(0xFFAA00)));
        }
        if (poolCount > 0) {
            String countDesc = equip.minRandomAttributes() == equip.maxRandomAttributes()
                    ? String.valueOf(equip.minRandomAttributes())
                    : equip.minRandomAttributes() + "~" + equip.maxRandomAttributes();
            lines.add(Component.literal(" ◇ 从 " + poolCount + " 个属性中选 " + countDesc + " 个")
                    .withStyle(style -> style.withColor(0xAAAAAA)));
        }
        lines.add(Component.literal(""));

        for (ForgeAttributeEntry entry : equip.getForgeAttributePool()) {
            String name = getDisplayName(entry.nbtKey());
            String range = "[" + formatValue(entry.minValue()) + " ~ " + formatValue(entry.maxValue()) + "]";

            MutableComponent line = Component.literal(" ");
            // 前缀标记
            if (entry.mandatory()) {
                line.append(Component.literal("✔ ").withStyle(style -> style.withColor(0x55FF55)));
            } else if (entry.probability() < 1.0) {
                int pct = (int) Math.round(entry.probability() * 100);
                line.append(Component.literal("◆" + pct + "% ").withStyle(style -> style.withColor(0xFFAA00)));
            } else {
                line.append(Component.literal("  "));
            }

            line.append(Component.literal(name).withStyle(style -> style.withColor(0x55FFFF)));
            line.append(Component.literal(": "));
            line.append(Component.literal(range).withStyle(style -> style.withColor(0xFFFFFF)));

            // 百分比类属性额外显示百分数
            if (isPercentAttribute(entry.nbtKey())) {
                String pctRange = " [" + formatValue(entry.minValue() * 100) + "% ~ "
                        + formatValue(entry.maxValue() * 100) + "%]";
                line.append(Component.literal(pctRange).withStyle(style -> style.withColor(0xCCCCCC)));
            }
            lines.add(line);
        }
        lines.add(Component.literal("══════════════════")
                .withStyle(style -> style.withColor(0xFFAA00)));
        return lines;
    }

    private static boolean isPercentAttribute(String nbtKey) {
        return nbtKey.equals(fun.wraq.common.util.StringUtils.RandomAttributes.critRate)
                || nbtKey.equals(fun.wraq.common.util.StringUtils.RandomAttributes.critDamage)
                || nbtKey.equals(fun.wraq.common.util.StringUtils.RandomAttributes.healthSteal)
                || nbtKey.equals(fun.wraq.common.util.StringUtils.RandomAttributes.healthRecover)
                || nbtKey.equals(fun.wraq.common.util.StringUtils.RandomAttributes.manaRecover);
    }
}

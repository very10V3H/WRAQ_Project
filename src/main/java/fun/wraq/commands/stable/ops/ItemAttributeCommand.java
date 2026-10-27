package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * AI-Generated, 2026-10-27
 * 运行时修改 Utils 第 45~101 行装备属性表的命令。
 * <p>
 * 所有修改直接作用于 Utils 的静态 Map，立即参与后续属性计算；
 * 使用 reset 恢复命令修改前的值，服务器停止时自动恢复。
 */
public final class ItemAttributeCommand {

    private static final Map<String, Field> FIELDS = buildFields();
    private static final Map<String, String> ALIASES = buildAliases();

    /** 记录命令修改前的原值，用于 reset/关服恢复。 */
    private static final Map<Item, Map<String, Optional<Double>>> SNAPSHOTS = new HashMap<>();

    private ItemAttributeCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Utils.MOD_ID)
                .then(Commands.literal("itemattr")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("fields")
                                .executes(ItemAttributeCommand::fields))
                        .then(Commands.literal("list")
                                .then(Commands.argument("item", StringArgumentType.string())
                                        .suggests(ItemAttributeCommand::suggestItems)
                                        .executes(ItemAttributeCommand::list)))
                        .then(Commands.literal("get")
                                .then(Commands.argument("item", StringArgumentType.string())
                                        .suggests(ItemAttributeCommand::suggestItems)
                                        .then(Commands.argument("attribute", StringArgumentType.string())
                                                .suggests(ItemAttributeCommand::suggestFields)
                                                .executes(ItemAttributeCommand::get))))
                        .then(Commands.literal("set")
                                .then(Commands.argument("item", StringArgumentType.string())
                                        .suggests(ItemAttributeCommand::suggestItems)
                                        .then(Commands.argument("attribute", StringArgumentType.string())
                                                .suggests(ItemAttributeCommand::suggestFields)
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(ItemAttributeCommand::set)))))
                        .then(Commands.literal("reset")
                                .then(Commands.argument("item", StringArgumentType.string())
                                        .suggests(ItemAttributeCommand::suggestItems)
                                        .executes(ItemAttributeCommand::resetItem)
                                        .then(Commands.argument("attribute", StringArgumentType.string())
                                                .suggests(ItemAttributeCommand::suggestFields)
                                                .executes(ItemAttributeCommand::resetField))))));
    }

    private static int fields(CommandContext<CommandSourceStack> context) {
        for (Field field : FIELDS.values()) {
            String suffix = field.integer() ? " (整数)" : "";
            context.getSource().sendSuccess(() -> Te.s(field.name(), suffix), false);
        }
        return FIELDS.size();
    }

    private static int list(CommandContext<CommandSourceStack> context) {
        Item item = resolveItem(StringArgumentType.getString(context, "item"));
        if (item == null) {
            context.getSource().sendFailure(Te.s("未知物品: ",
                    StringArgumentType.getString(context, "item")));
            return 0;
        }
        context.getSource().sendSuccess(() -> Te.s("物品属性: ", item.getDescription()), false);
        int count = 0;
        for (Field field : FIELDS.values()) {
            Double value = field.get(item);
            if (value != null) {
                String text = field.name() + " = " + format(value);
                context.getSource().sendSuccess(() -> Te.s("  ", text), false);
                count++;
            }
        }
        if (count == 0) {
            context.getSource().sendSuccess(() -> Te.s("  （没有属性记录）"), false);
        }
        return count;
    }

    private static int get(CommandContext<CommandSourceStack> context) {
        Item item = resolveItem(StringArgumentType.getString(context, "item"));
        if (item == null) {
            context.getSource().sendFailure(Te.s("未知物品: ",
                    StringArgumentType.getString(context, "item")));
            return 0;
        }
        Field field = findField(StringArgumentType.getString(context, "attribute"));
        if (field == null) {
            context.getSource().sendFailure(Te.s("未知属性字段: ",
                    StringArgumentType.getString(context, "attribute")));
            return 0;
        }
        Double value = field.get(item);
        if (value == null) {
            context.getSource().sendSuccess(() -> Te.s(field.name(), " = 未设置"), false);
        } else {
            context.getSource().sendSuccess(() -> Te.s(field.name(), " = ", format(value)), false);
        }
        return 1;
    }

    private static int set(CommandContext<CommandSourceStack> context) {
        Item item = resolveItem(StringArgumentType.getString(context, "item"));
        if (item == null) {
            context.getSource().sendFailure(Te.s("未知物品: ",
                    StringArgumentType.getString(context, "item")));
            return 0;
        }
        Field field = findField(StringArgumentType.getString(context, "attribute"));
        if (field == null) {
            context.getSource().sendFailure(Te.s("未知属性字段: ",
                    StringArgumentType.getString(context, "attribute")));
            return 0;
        }
        double value = DoubleArgumentType.getDouble(context, "value");
        if (!Double.isFinite(value)) {
            context.getSource().sendFailure(Te.s("属性值必须是有限数值。"));
            return 0;
        }
        if (field.integer()) {
            if (value != Math.rint(value) || value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
                context.getSource().sendFailure(Te.s(field.name(), " 是整数字段，请输入整数。"));
                return 0;
            }
        }
        snapshot(item, field);
        field.set(item, value);
        context.getSource().sendSuccess(() -> Te.s("已设置 ", item.getDescription(), " 的 ",
                field.name(), " = ", format(value), "；运行时立即生效。"), true);
        return 1;
    }

    private static int resetItem(CommandContext<CommandSourceStack> context) {
        Item item = resolveItem(StringArgumentType.getString(context, "item"));
        if (item == null) {
            context.getSource().sendFailure(Te.s("未知物品: ",
                    StringArgumentType.getString(context, "item")));
            return 0;
        }
        boolean changed = restoreItem(item);
        context.getSource().sendSuccess(() -> Te.s("已清除 ", item.getDescription(),
                " 的命令属性修改", changed ? "。" : "（没有需要清除的修改）。"), true);
        return 1;
    }

    private static int resetField(CommandContext<CommandSourceStack> context) {
        Item item = resolveItem(StringArgumentType.getString(context, "item"));
        if (item == null) {
            context.getSource().sendFailure(Te.s("未知物品: ",
                    StringArgumentType.getString(context, "item")));
            return 0;
        }
        Field field = findField(StringArgumentType.getString(context, "attribute"));
        if (field == null) {
            context.getSource().sendFailure(Te.s("未知属性字段: ",
                    StringArgumentType.getString(context, "attribute")));
            return 0;
        }
        boolean changed = restoreField(item, field);
        context.getSource().sendSuccess(() -> Te.s("已清除 ", item.getDescription(), " 的 ",
                field.name(), " 修改", changed ? "。" : "（没有需要清除的修改）。"), true);
        return 1;
    }

    /** 服务器停止时恢复所有命令修改。 */
    public static void clearOverrides() {
        for (Item item : new ArrayList<>(SNAPSHOTS.keySet())) {
            restoreItem(item);
        }
        SNAPSHOTS.clear();
    }

    private static void snapshot(Item item, Field field) {
        SNAPSHOTS.computeIfAbsent(item, key -> new HashMap<>())
                .putIfAbsent(field.name(), Optional.ofNullable(field.get(item)));
    }

    private static boolean restoreItem(Item item) {
        Map<String, Optional<Double>> itemSnapshots = SNAPSHOTS.remove(item);
        if (itemSnapshots == null) {
            return false;
        }
        itemSnapshots.forEach((name, original) -> {
            Field field = FIELDS.get(name);
            if (field != null) {
                restore(field, item, original);
            }
        });
        return true;
    }

    private static boolean restoreField(Item item, Field field) {
        Map<String, Optional<Double>> itemSnapshots = SNAPSHOTS.get(item);
        if (itemSnapshots == null || !itemSnapshots.containsKey(field.name())) {
            return false;
        }
        Optional<Double> original = itemSnapshots.remove(field.name());
        if (itemSnapshots.isEmpty()) {
            SNAPSHOTS.remove(item);
        }
        restore(field, item, original);
        return true;
    }

    private static void restore(Field field, Item item, Optional<Double> original) {
        if (original.isPresent()) {
            field.set(item, original.get());
        } else {
            field.remove(item);
        }
    }

    private static Field findField(String raw) {
        if (raw == null) {
            return null;
        }
        String normalized = raw.trim().toLowerCase(Locale.ROOT);
        String alias = ALIASES.get(normalized);
        if (alias != null) {
            return FIELDS.get(alias);
        }
        for (String name : FIELDS.keySet()) {
            if (name.toLowerCase(Locale.ROOT).equals(normalized)) {
                return FIELDS.get(name);
            }
        }
        return null;
    }

    private static Item resolveItem(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        String id = raw.trim();
        ResourceLocation location = ResourceLocation.tryParse(
                id.contains(":") ? id : Utils.MOD_ID + ":" + id);
        if (location == null) {
            return null;
        }
        Item item = ForgeRegistries.ITEMS.getValue(location);
        return item == Items.AIR ? null : item;
    }

    private static java.util.concurrent.CompletableFuture<com.mojang.brigadier.suggestion.Suggestions> suggestItems(
            CommandContext<CommandSourceStack> context,
            com.mojang.brigadier.suggestion.SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(
                ForgeRegistries.ITEMS.getKeys().stream()
                        .map(ResourceLocation::toString)
                        .toList(), builder);
    }

    private static java.util.concurrent.CompletableFuture<com.mojang.brigadier.suggestion.Suggestions> suggestFields(
            CommandContext<CommandSourceStack> context,
            com.mojang.brigadier.suggestion.SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(FIELDS.keySet(), builder);
    }

    private static String format(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1_000_000_000D) {
            return Long.toString((long) value);
        }
        return String.format(Locale.ROOT, "%.4f", value);
    }

    private static Map<String, Field> buildFields() {
        Map<String, Field> fields = new LinkedHashMap<>();
        addDouble(fields, "attackDamage", Utils.attackDamage);
        addDouble(fields, "xpLevelAttackDamage", Utils.xpLevelAttackDamage);
        addDouble(fields, "percentAttackDamageEnhance", Utils.percentAttackDamageEnhance);
        addDouble(fields, "critRate", Utils.critRate);
        addDouble(fields, "critDamage", Utils.critDamage);
        addDouble(fields, "xpLevelCritDamage", Utils.xpLevelCritDamage);
        addDouble(fields, "defence", Utils.defence);
        addDouble(fields, "xpLevelDefence", Utils.xpLevelDefence);
        addDouble(fields, "percentDefenceEnhance", Utils.percentDefenceEnhance);
        addDouble(fields, "defencePenetration", Utils.defencePenetration);
        addDouble(fields, "defencePenetration0", Utils.defencePenetration0);
        addDouble(fields, "xpLevelDefencePenetration0", Utils.xpLevelDefencePenetration0);
        addDouble(fields, "attackRangeUp", Utils.attackRangeUp);
        addDouble(fields, "swiftnessUp", Utils.swiftnessUp);
        addDouble(fields, "movementSpeedWithoutBattle", Utils.movementSpeedWithoutBattle);
        addDouble(fields, "movementSpeedCommon", Utils.movementSpeedCommon);
        addDouble(fields, "healthSteal", Utils.healthSteal);
        addDouble(fields, "expUp", Utils.expUp);
        addDouble(fields, "maxHealth", Utils.maxHealth);
        addDouble(fields, "percentMaxHealthEnhance", Utils.percentMaxHealthEnhance);
        addDouble(fields, "healthRecover", Utils.healthRecover);
        addDouble(fields, "healingAmplification", Utils.healingAmplification);
        addDouble(fields, "coolDownDecrease", Utils.coolDownDecrease);
        addDouble(fields, "manaDamage", Utils.manaDamage);
        addDouble(fields, "xpLevelManaDamage", Utils.xpLevelManaDamage);
        addDouble(fields, "percentManaDamageEnhance", Utils.percentManaDamageEnhance);
        addDouble(fields, "manaDefence", Utils.manaDefence);
        addDouble(fields, "xpLevelManaDefence", Utils.xpLevelManaDefence);
        addDouble(fields, "percentManaDefenceEnhance", Utils.percentManaDefenceEnhance);
        addDouble(fields, "manaPenetration", Utils.manaPenetration);
        addDouble(fields, "manaPenetration0", Utils.manaPenetration0);
        addDouble(fields, "xpLevelManaPenetration0", Utils.xpLevelManaPenetration0);
        addDouble(fields, "manaCost", Utils.manaCost);
        addDouble(fields, "manaRecover", Utils.manaRecover);
        addDouble(fields, "maxMana", Utils.maxMana);
        addDouble(fields, "manaHealthSteal", Utils.manaHealthSteal);
        addDouble(fields, "critDamageDecrease", Utils.critDamageDecrease);
        addDouble(fields, "luckyUp", Utils.luckyUp);
        addDouble(fields, "toughness", Utils.toughness);
        addDouble(fields, "percentHealthRecover", Utils.percentHealthRecover);
        addDouble(fields, "attackSpeedEnhance", Utils.attackSpeedEnhance);
        addDouble(fields, "elementStrength", Utils.elementStrength);
        addDouble(fields, "finalDamageEnhance", Utils.finalDamageEnhance);
        addDouble(fields, "attackDamageEnhance", Utils.attackDamageEnhance);
        addDouble(fields, "manaDamageEnhance", Utils.manaDamageEnhance);
        addDouble(fields, "commonDamageEnhance", Utils.commonDamageEnhance);
        addDouble(fields, "percentManaRecover", Utils.percentManaRecover);

        addDouble(fields, "mainHandTag", Utils.mainHandTag);
        addDouble(fields, "offHandTag", Utils.offHandTag);
        addDouble(fields, "shieldTag", Utils.shieldTag);
        addDouble(fields, "armorTag", Utils.armorTag);
        addDouble(fields, "powerTag", Utils.powerTag);
        addDouble(fields, "mobLevel", Utils.mobLevel);
        addDouble(fields, "passiveEquipTag", Utils.passiveEquipTag);

        addInteger(fields, "levelRequire", Utils.levelRequire);
        addInteger(fields, "gemsTag", Utils.gemsTag);
        return fields;
    }

    private static void addDouble(Map<String, Field> fields, String name, Map<Item, Double> map) {
        fields.put(name, new DoubleField(name, map));
    }

    private static void addInteger(Map<String, Field> fields, String name, Map<Item, Integer> map) {
        fields.put(name, new IntegerField(name, map));
    }

    private static Map<String, String> buildAliases() {
        Map<String, String> aliases = new HashMap<>();
        aliases.put("attack", "attackDamage");
        aliases.put("atk", "attackDamage");
        aliases.put("damage", "attackDamage");
        aliases.put("crit", "critRate");
        aliases.put("critdmg", "critDamage");
        aliases.put("health", "maxHealth");
        aliases.put("hp", "maxHealth");
        aliases.put("maxhp", "maxHealth");
        aliases.put("speed", "movementSpeedCommon");
        aliases.put("mana", "maxMana");
        aliases.put("maxmana", "maxMana");
        return aliases;
    }

    private interface Field {
        String name();

        boolean integer();

        Double get(Item item);

        void set(Item item, double value);

        void remove(Item item);
    }

    private record DoubleField(String name, Map<Item, Double> map) implements Field {
        @Override
        public boolean integer() {
            return false;
        }

        @Override
        public Double get(Item item) {
            return map.get(item);
        }

        @Override
        public void set(Item item, double value) {
            map.put(item, value);
        }

        @Override
        public void remove(Item item) {
            map.remove(item);
        }
    }

    private record IntegerField(String name, Map<Item, Integer> map) implements Field {
        @Override
        public boolean integer() {
            return true;
        }

        @Override
        public Double get(Item item) {
            Integer value = map.get(item);
            return value == null ? null : value.doubleValue();
        }

        @Override
        public void set(Item item, double value) {
            map.put(item, (int) value);
        }

        @Override
        public void remove(Item item) {
            map.remove(item);
        }
    }
}

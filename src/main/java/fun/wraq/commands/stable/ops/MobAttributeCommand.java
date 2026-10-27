package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.events.mob.attribute.MobAttributeOverride;
import fun.wraq.events.mob.attribute.MobSpawnControllerAttributeOverrides;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * AI-Generated, 2026-10-27
 * MobSpawnController 普通刷怪控制器的运行时属性调整命令。
 * <p>
 * 修改只保存在内存中，并在下一次刷新时生效；不会修改已经生成的怪物。
 */
public final class MobAttributeCommand {

    private MobAttributeCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Utils.MOD_ID)
                .then(Commands.literal("mobattr")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("list")
                                .executes(MobAttributeCommand::list))
                        .then(Commands.literal("info")
                                .then(Commands.argument("controller", StringArgumentType.string())
                                        .suggests(MobAttributeCommand::suggestControllers)
                                        .executes(MobAttributeCommand::info)))
                        .then(Commands.literal("set")
                                .then(Commands.argument("controller", StringArgumentType.string())
                                        .suggests(MobAttributeCommand::suggestControllers)
                                        .then(Commands.argument("attribute", StringArgumentType.string())
                                                .suggests(MobAttributeCommand::suggestFields)
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(MobAttributeCommand::set)))))
                        .then(Commands.literal("reset")
                                .then(Commands.argument("controller", StringArgumentType.string())
                                        .suggests(MobAttributeCommand::suggestControllers)
                                        .executes(MobAttributeCommand::resetController)
                                        .then(Commands.argument("attribute", StringArgumentType.string())
                                                .suggests(MobAttributeCommand::suggestFields)
                                                .executes(MobAttributeCommand::resetField))))));
    }

    private static int list(CommandContext<CommandSourceStack> context) {
        List<MobSpawnController> controllers = allControllers();
        if (controllers.isEmpty()) {
            context.getSource().sendFailure(Te.s("当前没有可调整的 MobSpawnController。"));
            return 0;
        }
        for (MobSpawnController controller : controllers) {
            MobAttributeOverride override = MobSpawnControllerAttributeOverrides.get(
                    controller.getAttributeId());
            String suffix = override == null || override.isEmpty()
                    ? "" : " [已有覆盖: " + overrideFieldCount(override) + " 项]";
            context.getSource().sendSuccess(() -> Te.s(controller.getAttributeId(), " - ",
                    controller.mobName, suffix), false);
        }
        return controllers.size();
    }

    private static int info(CommandContext<CommandSourceStack> context) {
        String requested = StringArgumentType.getString(context, "controller");
        MobSpawnController controller = findController(requested);
        if (controller == null) {
            context.getSource().sendFailure(Te.s("未知 MobSpawnController: ", requested));
            return 0;
        }
        MobAttributes base = controller.getMobAttributes();
        if (base == null) {
            context.getSource().sendFailure(Te.s("该控制器没有可用的基础属性: ", controller.getAttributeId()));
            return 0;
        }
        MobAttributeOverride override = MobSpawnControllerAttributeOverrides.get(
                controller.getAttributeId());
        context.getSource().sendSuccess(() -> Te.s("刷怪属性 ", controller.getAttributeId(),
                " - ", controller.mobName), false);
        for (String field : MobAttributeOverride.FIELDS) {
            double baseValue = getBaseValue(base, field);
            Double overrideValue = override == null ? null : override.get(field);
            String line = overrideValue == null
                    ? field + " = " + format(baseValue)
                    : field + " = " + format(overrideValue) + " (基础 " + format(baseValue) + ")";
            context.getSource().sendSuccess(() -> Te.s("  ", line), false);
        }
        return 1;
    }

    private static int set(CommandContext<CommandSourceStack> context) {
        String requested = StringArgumentType.getString(context, "controller");
        MobSpawnController controller = findController(requested);
        if (controller == null) {
            context.getSource().sendFailure(Te.s("未知 MobSpawnController: ", requested));
            return 0;
        }
        String field = MobAttributeOverride.normalizeField(
                StringArgumentType.getString(context, "attribute"));
        if (field == null) {
            context.getSource().sendFailure(Te.s("未知属性字段: ",
                    StringArgumentType.getString(context, "attribute")));
            return 0;
        }
        double value = DoubleArgumentType.getDouble(context, "value");
        MobSpawnControllerAttributeOverrides.set(controller.getAttributeId(), field, value);
        context.getSource().sendSuccess(() -> Te.s("已设置 ", controller.getAttributeId(), " 的 ",
                field, " = ", format(MobAttributeOverride.validateValue(field, value)),
                "；下一次刷新生效，当前怪物不变。"), true);
        return 1;
    }

    private static int resetController(CommandContext<CommandSourceStack> context) {
        String requested = StringArgumentType.getString(context, "controller");
        MobSpawnController controller = findController(requested);
        if (controller == null) {
            context.getSource().sendFailure(Te.s("未知 MobSpawnController: ", requested));
            return 0;
        }
        boolean removed = MobSpawnControllerAttributeOverrides.reset(controller.getAttributeId());
        context.getSource().sendSuccess(() -> Te.s("已清除 ", controller.getAttributeId(),
                " 的全部属性覆盖", removed ? "，下一次刷新生效。" : "（原本没有覆盖）。"), true);
        return 1;
    }

    private static int resetField(CommandContext<CommandSourceStack> context) {
        String requested = StringArgumentType.getString(context, "controller");
        MobSpawnController controller = findController(requested);
        if (controller == null) {
            context.getSource().sendFailure(Te.s("未知 MobSpawnController: ", requested));
            return 0;
        }
        String field = MobAttributeOverride.normalizeField(
                StringArgumentType.getString(context, "attribute"));
        if (field == null) {
            context.getSource().sendFailure(Te.s("未知属性字段: ",
                    StringArgumentType.getString(context, "attribute")));
            return 0;
        }
        boolean removed = MobSpawnControllerAttributeOverrides.reset(
                controller.getAttributeId(), field);
        context.getSource().sendSuccess(() -> Te.s("已清除 ", controller.getAttributeId(), " 的 ",
                field, " 覆盖", removed ? "，下一次刷新生效。" : "（原本没有覆盖）。"), true);
        return 1;
    }

    private static List<MobSpawnController> allControllers() {
        List<MobSpawnController> controllers = new ArrayList<>(MobSpawn.getAllControllers(true));
        controllers.sort(Comparator.comparing(MobSpawnController::getAttributeId));
        return controllers;
    }

    private static MobSpawnController findController(String requested) {
        if (requested == null || requested.isBlank()) {
            return null;
        }
        String id = requested.trim();
        return allControllers().stream()
                .filter(controller -> controller.getAttributeId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private static java.util.concurrent.CompletableFuture<com.mojang.brigadier.suggestion.Suggestions> suggestControllers(
            CommandContext<CommandSourceStack> context,
            com.mojang.brigadier.suggestion.SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(
                allControllers().stream().map(MobSpawnController::getAttributeId).toList(), builder);
    }

    private static java.util.concurrent.CompletableFuture<com.mojang.brigadier.suggestion.Suggestions> suggestFields(
            CommandContext<CommandSourceStack> context,
            com.mojang.brigadier.suggestion.SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(MobAttributeOverride.FIELDS, builder);
    }

    private static int overrideFieldCount(MobAttributeOverride override) {
        int count = 0;
        for (String field : MobAttributeOverride.FIELDS) {
            if (override.get(field) != null) {
                count++;
            }
        }
        return count;
    }

    private static String format(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1_000_000_000D) {
            return Long.toString((long) value);
        }
        return String.format(Locale.ROOT, "%.3f", value);
    }

    private static double getBaseValue(MobAttributes attributes, String field) {
        return switch (field) {
            case "attackDamage" -> attributes.attackDamage;
            case "defence" -> attributes.defence;
            case "manaDefence" -> attributes.manaDefence;
            case "critRate" -> attributes.critRate;
            case "critDamage" -> attributes.critDamage;
            case "defencePenetration" -> attributes.defencePenetration;
            case "defencePenetration0" -> attributes.defencePenetration0;
            case "healthSteal" -> attributes.healthSteal;
            case "maxHealth" -> attributes.maxHealth;
            case "movementSpeed" -> attributes.movementSpeed;
            case "healthRecover" -> attributes.healthRecover;
            case "percentHealthRecover" -> attributes.percentHealthRecover;
            default -> 0;
        };
    }
}

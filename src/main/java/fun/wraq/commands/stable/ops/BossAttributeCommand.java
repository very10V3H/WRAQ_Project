package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.attribute.BossAttributeOverrides;
import fun.wraq.events.mob.attribute.MobAttributeOverride;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
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
 * 领主级怪物（NoTeamInstance）运行时属性调整命令。
 * <p>
 * 修改只保存在内存中，并在下一次召唤时生效；不会修改已经生成的 Boss。
 */
public final class BossAttributeCommand {

    private BossAttributeCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Utils.MOD_ID)
                .then(Commands.literal("bossattr")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("list")
                                .executes(BossAttributeCommand::list))
                        .then(Commands.literal("info")
                                .then(Commands.argument("profile", StringArgumentType.string())
                                        .suggests(BossAttributeCommand::suggestProfiles)
                                        .executes(BossAttributeCommand::info)))
                        .then(Commands.literal("set")
                                .then(Commands.argument("profile", StringArgumentType.string())
                                        .suggests(BossAttributeCommand::suggestProfiles)
                                        .then(Commands.argument("attribute", StringArgumentType.string())
                                                .suggests(BossAttributeCommand::suggestFields)
                                                .then(Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(BossAttributeCommand::set)))))
                        .then(Commands.literal("reset")
                                .then(Commands.argument("profile", StringArgumentType.string())
                                        .suggests(BossAttributeCommand::suggestProfiles)
                                        .executes(BossAttributeCommand::resetProfile)
                                        .then(Commands.argument("attribute", StringArgumentType.string())
                                                .suggests(BossAttributeCommand::suggestFields)
                                                .executes(BossAttributeCommand::resetField))))));
    }

    private static int list(CommandContext<CommandSourceStack> context) {
        List<Profile> profiles = allProfiles();
        if (profiles.isEmpty()) {
            context.getSource().sendFailure(Te.s("当前没有可调整的领主级怪物。"));
            return 0;
        }
        for (Profile profile : profiles) {
            MobAttributeOverride override = BossAttributeOverrides.get(profile.id());
            String suffix = override == null || override.isEmpty()
                    ? "" : " [已有覆盖: " + overrideFieldCount(override) + " 项]";
            context.getSource().sendSuccess(() -> Te.s(profile.id(), " - ",
                    profile.label(), suffix), false);
        }
        return profiles.size();
    }

    private static int info(CommandContext<CommandSourceStack> context) {
        String requested = StringArgumentType.getString(context, "profile");
        Profile profile = findProfile(requested);
        if (profile == null) {
            context.getSource().sendFailure(Te.s("未知领主属性 profile: ", requested));
            return 0;
        }
        MobAttributes base = profile.instance().getMainMobAttributes();
        if (base == null) {
            context.getSource().sendFailure(Te.s("该领主没有可用的基础属性: ", profile.id()));
            return 0;
        }
        MobAttributeOverride override = BossAttributeOverrides.get(profile.id());
        context.getSource().sendSuccess(() -> Te.s("领主属性 ", profile.id(), " - ", profile.label()), false);
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
        String requested = StringArgumentType.getString(context, "profile");
        Profile profile = findProfile(requested);
        if (profile == null) {
            context.getSource().sendFailure(Te.s("未知领主属性 profile: ", requested));
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
        BossAttributeOverrides.set(profile.id(), field, value);
        context.getSource().sendSuccess(() -> Te.s("已设置 ", profile.id(), " 的 ", field,
                " = ", format(MobAttributeOverride.validateValue(field, value)),
                "；下一次召唤生效，当前 Boss 不变。"), true);
        return 1;
    }

    private static int resetProfile(CommandContext<CommandSourceStack> context) {
        String requested = StringArgumentType.getString(context, "profile");
        Profile profile = findProfile(requested);
        if (profile == null) {
            context.getSource().sendFailure(Te.s("未知领主属性 profile: ", requested));
            return 0;
        }
        boolean removed = BossAttributeOverrides.reset(profile.id());
        context.getSource().sendSuccess(() -> Te.s("已清除 ", profile.id(), " 的全部属性覆盖",
                removed ? "，下一次召唤生效。" : "（原本没有覆盖）。"), true);
        return 1;
    }

    private static int resetField(CommandContext<CommandSourceStack> context) {
        String requested = StringArgumentType.getString(context, "profile");
        Profile profile = findProfile(requested);
        if (profile == null) {
            context.getSource().sendFailure(Te.s("未知领主属性 profile: ", requested));
            return 0;
        }
        String field = MobAttributeOverride.normalizeField(
                StringArgumentType.getString(context, "attribute"));
        if (field == null) {
            context.getSource().sendFailure(Te.s("未知属性字段: ",
                    StringArgumentType.getString(context, "attribute")));
            return 0;
        }
        boolean removed = BossAttributeOverrides.reset(profile.id(), field);
        context.getSource().sendSuccess(() -> Te.s("已清除 ", profile.id(), " 的 ", field,
                " 覆盖", removed ? "，下一次召唤生效。" : "（原本没有覆盖）。"), true);
        return 1;
    }

    private static List<Profile> allProfiles() {
        List<Profile> result = new ArrayList<>();
        for (NoTeamInstance instance : NoTeamInstanceModule.getAllInstance()) {
            instance.getAttributeProfiles().forEach((role, label) ->
                    result.add(new Profile(instance, role,
                            instance.getProfileId(role), label)));
        }
        result.sort(Comparator.comparing(Profile::id));
        return result;
    }

    private static Profile findProfile(String requested) {
        if (requested == null || requested.isBlank()) {
            return null;
        }
        String id = requested.trim();
        String instanceId = id;
        String role = "main";
        int dot = id.lastIndexOf('.');
        if (dot > 0 && dot < id.length() - 1) {
            instanceId = id.substring(0, dot);
            role = id.substring(dot + 1);
        }
        for (Profile profile : allProfiles()) {
            if (profile.instance().getInstanceId().equals(instanceId)
                    && profile.role().equals(role)) {
                return profile;
            }
        }
        return null;
    }

    private static java.util.concurrent.CompletableFuture<com.mojang.brigadier.suggestion.Suggestions> suggestProfiles(
            CommandContext<CommandSourceStack> context,
            com.mojang.brigadier.suggestion.SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(allProfiles().stream().map(Profile::id).toList(), builder);
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

    private record Profile(NoTeamInstance instance, String role, String id, Component label) {
    }
}

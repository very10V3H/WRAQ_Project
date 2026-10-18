package fun.wraq.process.system.missions.mainline;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.missions.mainline.MainQuestStage.Stage;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

/**
 * AI-Generated, 2026-10-18
 * 主线 GM 命令（仅管理员，权限等级 2）：
 * <pre>
 *   /vmd main info [player]             查看主线阶段、状态与目标
 *   /vmd main set &lt;stageId&gt; [player]    跳到指定阶段（之前阶段标记完成）
 *   /vmd main complete [player]         强制完成当前阶段（跳过世界目标）
 *   /vmd main reset [player]            清空主线并从第一章重新开始
 *   /vmd main list                      列出全部阶段 id / 标题
 * </pre>
 * 不写 player 时操作命令执行者自己；控制台执行时必须指定在线玩家。
 */
public final class MainQuestCommand {

    private MainQuestCommand() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(Utils.MOD_ID).then(Commands.literal("main")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("info")
                        .executes(MainQuestCommand::info)
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(MainQuestCommand::info)))
                .then(Commands.literal("complete")
                        .executes(MainQuestCommand::complete)
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(MainQuestCommand::complete)))
                .then(Commands.literal("reset")
                        .executes(MainQuestCommand::reset)
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(MainQuestCommand::reset)))
                .then(Commands.literal("list").executes(MainQuestCommand::list))
                .then(Commands.literal("set")
                        .then(Commands.argument("stage", StringArgumentType.string())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(
                                        MainQuestData.STAGES.stream().map(Stage::id).toList(), builder))
                                .executes(MainQuestCommand::set)
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(MainQuestCommand::set))))));
    }

    /** 取命令目标：有 player 参数时用参数，否则用命令执行者。 */
    private static ServerPlayer target(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean hasPlayerArgument = context.getNodes().stream()
                .anyMatch(node -> "player".equals(node.getNode().getName()));
        if (hasPlayerArgument) {
            return EntityArgument.getPlayer(context, "player");
        }
        return context.getSource().getPlayerOrException();
    }

    private static int info(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = target(context);
        Stage stage = MainQuestHelper.current(player);
        if (stage == null) {
            context.getSource().sendSuccess(() -> Te.s(player.getName(), " 当前没有进行中的主线（可能已全部完成）"), false);
            return 1;
        }
        String status = MainQuestHelper.status(player, stage);
        context.getSource().sendSuccess(() -> Te.s(player.getName(), " 主线: ", stage.title(), " [", status, "]",
                "\n目标: ", MainQuestHelper.objectiveText(player, stage)), false);
        return 1;
    }

    private static int set(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String id = StringArgumentType.getString(context, "stage");
        Stage stage = MainQuestData.byId(id);
        if (stage == null) {
            context.getSource().sendFailure(Te.s("未知主线阶段: ", id));
            return 0;
        }
        ServerPlayer player = target(context);
        MainQuestHelper.forceSetStage(player, stage);
        context.getSource().sendSuccess(() -> Te.s("已将 ", player.getName(), " 的主线设置为: ", stage.title()), false);
        return 1;
    }

    private static int complete(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = target(context);
        if (!MainQuestHelper.forceComplete(player)) {
            context.getSource().sendFailure(Te.s(player.getName(), " 当前没有可完成的主线阶段"));
            return 0;
        }
        context.getSource().sendSuccess(() -> Te.s("已强制完成 ", player.getName(), " 的当前主线阶段"), false);
        return 1;
    }

    private static int reset(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = target(context);
        MainQuestHelper.reset(player);
        Stage current = MainQuestHelper.current(player);
        context.getSource().sendSuccess(() -> Te.s("已重置 ", player.getName(), " 的主线，当前阶段: ",
                current == null ? "无" : current.title()), false);
        return 1;
    }

    private static int list(CommandContext<CommandSourceStack> context) {
        for (Stage stage : MainQuestData.STAGES) {
            context.getSource().sendSuccess(() -> Te.s(stage.id(), " - ", stage.title()), false);
        }
        return MainQuestData.STAGES.size();
    }
}

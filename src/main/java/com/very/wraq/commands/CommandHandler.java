package com.very.wraq.commands;

import com.very.wraq.commands.changeable.*;
import com.very.wraq.commands.stable.*;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommandHandler {
    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {
/*        CommandDispatcher<CommandSourceStack> dispatcher0 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd0 = dispatcher0.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("login").then(
                                Commands.argument("passwd", StringArgumentType.greedyString())
                                        //.requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                        .executes(LoginCommand.instance)
                        )
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher1 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd1 = dispatcher1.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("register").then(
                                Commands.argument("passwd",StringArgumentType.greedyString())
                                        .executes(RegisterCommand.instance)
                        )
                )
        );*/
        CommandDispatcher<CommandSourceStack> dispatcher2 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd2 = dispatcher2.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("info")
                                .executes(InfoCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher3 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd3 = dispatcher3.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("zs")
                                .executes(ZsCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher4 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd4 = dispatcher4.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("killCount")
                                .executes(killCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher5 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd5 = dispatcher5.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("sell").then(
                                Commands.argument("price", StringArgumentType.string())
                                        .executes(SellCommand.instance)
                        )
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher6 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd6 = dispatcher6.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("prefix")
                                .executes(prefixCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher7 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd7 = dispatcher7.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("prefix").then(
                                Commands.argument("num", StringArgumentType.string())
                                        .executes(prefixChooseCommand.instance)
                        )

                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher8 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd8 = dispatcher8.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("confirm")
                                .executes(ConfirmCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher9 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd9 = dispatcher9.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("delete")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                .executes(deleteCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher10 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd10 = dispatcher10.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("ignore").then(
                                Commands.argument("type", StringArgumentType.string())
                                        .executes(IgnoreMSGCommand.instance)
                        )

                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher11 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd11 = dispatcher11.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("clear")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(ClearSkillCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher12 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd12 = dispatcher12.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("villager").then(
                                Commands.argument("type", StringArgumentType.string())
                                        .executes(VillagerSummonCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher13 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd13 = dispatcher13.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("info").then(
                                Commands.argument("name", StringArgumentType.string())
                                        .executes(InfoInquireCommand.instance)
                                        .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        )
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher14 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd14 = dispatcher14.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("text").then(
                                Commands.argument("name", StringArgumentType.string())
                                        .executes(TextCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))

                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher15 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd15 = dispatcher15.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("mine")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(MineSummonCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher16 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd16 = dispatcher16.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("xpclear")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(XpClearCommand.instance)
                )
        );

        CommandDispatcher<CommandSourceStack> dispatcher17 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd17 = dispatcher17.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("bow")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                .executes(BowCommand.instance)
                )
        );

        CommandDispatcher<CommandSourceStack> dispatcher18 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd18 = dispatcher18.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("ding").then(
                                Commands.argument("name", GameProfileArgument.gameProfile())
                                        .executes(DingCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))

                )
        );

        CommandDispatcher<CommandSourceStack> dispatcher19 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd19 = dispatcher19.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("dingding").then(
                                Commands.argument("name", GameProfileArgument.gameProfile())
                                        .executes(DingDingCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher20 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd20 = dispatcher20.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("giant")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(GiantCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher21 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd21 = dispatcher21.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("log")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                .executes(LogCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher22 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd22 = dispatcher22.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("transform")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(TransformCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher23 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd23 = dispatcher23.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("xp").then(
                                Commands.argument("level", StringArgumentType.string())
                                        .executes(XpCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher24 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd24 = dispatcher24.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("recycle")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                .executes(RecycleCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher25 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd25 = dispatcher25.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("dps").then(
                                Commands.argument("second", StringArgumentType.string())
                                        .executes(DpsCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher26 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd26 = dispatcher26.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("daily")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(ResetDailyCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher27 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd27 = dispatcher27.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("forge").then(
                                Commands.argument("level", StringArgumentType.string())
                                        .executes(ForgeCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher28 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd28 = dispatcher28.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("compensate").then(
                                Commands.argument("type", StringArgumentType.string())
                                        .executes(CompensateCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher29 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd29 = dispatcher29.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("particle").then(
                                Commands.argument("level", StringArgumentType.string())
                                        .executes(ParticleCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher30 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd30 = dispatcher30.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("info1")
                                .executes(Info1Command.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher31 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd31 = dispatcher31.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("speed").then(
                                Commands.argument("rate", StringArgumentType.string())
                                        .executes(SpeedCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher32 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd32 = dispatcher32.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("stop")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(StopCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher33 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd33 = dispatcher33.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("pay").then(
                                Commands.argument("name", GameProfileArgument.gameProfile()).then(
                                        Commands.argument("num", StringArgumentType.string())
                                                        .executes(PayCommand.instance)
                                        )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher34 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd34 = dispatcher34.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("Tag").then(
                                Commands.argument("type", StringArgumentType.string()).then(
                                        Commands.argument("key", StringArgumentType.string()).then(
                                                        Commands.argument("value", StringArgumentType.string())
                                                                .executes(TagCommand.instance)
                                                )
                                )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher35 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd35 = dispatcher35.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("debug")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                .executes(DebugCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher36 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd36 = dispatcher36.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("playerTag").then(
                                Commands.argument("type", StringArgumentType.string()).then(
                                        Commands.argument("player", GameProfileArgument.gameProfile()).then(
                                                Commands.argument("key", StringArgumentType.string()).then(
                                                        Commands.argument("value", StringArgumentType.string())
                                                                .executes(PlayerTagCommand.instance)
                                                )
                                        )
                                )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher37 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd37 = dispatcher37.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("resetTag")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                .executes(ResetTagCommand.instance)
                )
        );
    }
}

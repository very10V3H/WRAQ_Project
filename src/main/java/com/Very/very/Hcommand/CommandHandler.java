package com.Very.very.Hcommand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.core.net.Priority;

@Mod.EventBusSubscriber
public class CommandHandler {
    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event)
    {
        CommandDispatcher<CommandSourceStack> dispatcher0 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd0 = dispatcher0.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("login").then(
                                Commands.argument("passwd", StringArgumentType.string())
                                        //.requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                        .executes(LoginCommand.instance)
                        )
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher1 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd1 = dispatcher1.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("register").then(
                                Commands.argument("passwd0", StringArgumentType.string()).then(
                                        Commands.argument("passwd1",StringArgumentType.string())
                                        .executes(RegisterCommand.instance)
                                )
                        )
                )
        );
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
                                Commands.argument("price",StringArgumentType.string())
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
                                Commands.argument("num",StringArgumentType.string())
                                        .executes(prefixChooseCommand.instance)
                        )

                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher8 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd8 = dispatcher8.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("confirm")
                                        .executes(confirmCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher9 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd9 = dispatcher9.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("delete")
                                .executes(deleteCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher10 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd10 = dispatcher10.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("ignore").then(
                                Commands.argument("type",StringArgumentType.string())
                                        .executes(IgnoreMSGCommand.instance)
                        )

                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher11 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd11 = dispatcher11.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("clear")
                                        .executes(ClearSkillCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher12 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd12 = dispatcher12.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("villager").then(
                                Commands.argument("type",StringArgumentType.string())
                                        .executes(VillagerSummonCommand.instance)
                        )

                )
        );
    }
}

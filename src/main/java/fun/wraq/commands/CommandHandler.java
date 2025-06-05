package fun.wraq.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fun.wraq.commands.changeable.*;
import fun.wraq.commands.stable.ops.*;
import fun.wraq.commands.stable.players.*;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.bank.BankOperationCommand;
import fun.wraq.process.system.bank.GetGoldenBeansCommand;
import fun.wraq.process.system.bonuschest.BonusInfoCommand;
import fun.wraq.process.system.cooking.CookingOperationCommand;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustmentOperationCommand;
import fun.wraq.process.system.estate.*;
import fun.wraq.process.system.profession.ProfessionOperationCommand;
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
                                Commands.argument("price", IntegerArgumentType.integer()).then(
                                        Commands.argument("type", IntegerArgumentType.integer())
                                                .executes(SellCommand.instance)
                                )
                        )
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher6 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd6 = dispatcher6.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("prefix")
                                .executes(PrefixCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher7 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd7 = dispatcher7.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("prefix").then(
                                Commands.argument("num", StringArgumentType.string())
                                        .executes(PrefixChooseCommand.instance)
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
                                .executes(DeleteCommand.instance)
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
        CommandDispatcher<CommandSourceStack> dispatcher16 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd16 = dispatcher16.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("xpclear")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(XpClearCommand.instance)
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
                        Commands.literal("compensate")
                                .executes(CompensateCommand.instance)
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(0))
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
                                Commands.argument("eachTierValue", StringArgumentType.string())
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
        CommandDispatcher<CommandSourceStack> dispatcher38 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd38 = dispatcher38.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("road").then(
                                Commands.argument("type", StringArgumentType.string())
                                        .executes(RoadCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher39 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd39 = dispatcher39.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("supply")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                .executes(SupplyCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher40 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd40 = dispatcher40.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("clearVillager")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(ClearVillagerCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher41 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd41 = dispatcher41.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("summonVillager").then(
                                Commands.argument("type", StringArgumentType.string()).then(
                                        Commands.argument("offerType", StringArgumentType.string()).then(
                                                Commands.argument("origin", StringArgumentType.string()).then(
                                                        Commands.argument("bound", StringArgumentType.string())
                                                                .executes(SummonVillagerCommand.instance)
                                                )
                                        )
                                )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher42 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd42 = dispatcher42.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("season")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                .executes(SeasonCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher43 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd43 = dispatcher43.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("inquiryTag").then(
                                Commands.argument("type", StringArgumentType.string()).then(
                                        Commands.argument("player", GameProfileArgument.gameProfile()).then(
                                                Commands.argument("key", StringArgumentType.string()).
                                                        executes(InquiryTagCommand.instance)
                                        )
                                )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher44 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd44 = dispatcher44.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("uniform")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(UniformCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher45 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd45 = dispatcher45.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("customPrefix").then(
                                Commands.argument("prefix", StringArgumentType.string()).then(
                                        Commands.argument("color", StringArgumentType.string())
                                                .executes(CustomPrefixCommand.instance)
                                )
                        )
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher46 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd46 = dispatcher46.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("quickDecompose")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                .executes(QuickDecomposeCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher47 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd47 = dispatcher47.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("copy").then(
                                Commands.argument("name", GameProfileArgument.gameProfile())
                                        .executes(CopyCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher48 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd48 = dispatcher48.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("qs").then(
                                Commands.argument("type", StringArgumentType.string())
                                        .executes(QSCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher49 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd49 = dispatcher49.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("readMobAttributesFromCSVFile")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(ReadMobAttributeFromCSVFileCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher50 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd50 = dispatcher50.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("equipAttribute").then(
                                Commands.argument("equip", StringArgumentType.string()).then(
                                        Commands.argument("attribute", StringArgumentType.string()).then(
                                                Commands.argument("value", DoubleArgumentType.doubleArg())
                                                        .executes(EquipAttributeCommand.instance)
                                        )
                                )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher51 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd51 = dispatcher51.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("curios").then(
                                Commands.argument("type", StringArgumentType.string()).then(
                                        Commands.argument("num", IntegerArgumentType.integer(1, 36))
                                                .executes(CuriosCommand.instance)
                                )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher52 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd52 = dispatcher52.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("bonusInfo")
                                .executes(BonusInfoCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher53 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd53 = dispatcher53.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("resetAccount")
                                .executes(ResetAccountCommand.instance)
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher54 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd54 = dispatcher54.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("quickUseDisplay").then(
                                Commands.argument("mode", IntegerArgumentType.integer(-1, 1))
                                        .executes(QuickUseDisplayCommand.instance)
                        )
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher55 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd55 = dispatcher55.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("resetTowerData")
                                .executes(ResetTowerDataCommand.instance)
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher56 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd56 = dispatcher56.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("fixBackpack")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                                .executes(FixBackpackCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher57 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd57 = dispatcher57.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("idCard")
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                                .executes(IdCardCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher58 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd58 = dispatcher58.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("progressUnlock").then(
                                Commands.argument("name", GameProfileArgument.gameProfile())
                                        .executes(ProgressUnlockCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher59 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd59 = dispatcher59.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("setOpAttributes").then(
                                Commands.argument("key", StringArgumentType.string()).then(
                                        Commands.argument("num", DoubleArgumentType.doubleArg())
                                                .executes(SetOpAttributesCommand.instance)
                                )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher60 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd60 = dispatcher60.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("rankData").then(
                                Commands.argument("player", GameProfileArgument.gameProfile()).then(
                                        Commands.argument("rank", StringArgumentType.string()).then(
                                                Commands.argument("description", StringArgumentType.string())
                                                        .executes(RankDataCommand.instance)
                                        )
                                )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher61 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd61 = dispatcher61.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("rankQuery").then(
                                Commands.argument("player", GameProfileArgument.gameProfile())
                                        .executes(RankQueryCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher62 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd62 = dispatcher62.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("rankItem").then(
                                Commands.argument("player", GameProfileArgument.gameProfile()).then(
                                        Commands.argument("rankOrigin", StringArgumentType.string()).then(
                                                Commands.argument("rankBound", StringArgumentType.string()).then(
                                                        Commands.argument("itemType", IntegerArgumentType.integer(0, 2))
                                                                .executes(RankItemCommand.instance)
                                                )
                                        )
                                )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher63 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd63 = dispatcher63.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("randomEvent").then(
                                Commands.argument("operation", StringArgumentType.string())
                                        .executes(RandomEventOperationCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher64 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd64 = dispatcher64.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("guide").then(
                                Commands.argument("stage", StringArgumentType.string())
                                        .executes(GuideStageSetCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher65 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd65 = dispatcher65.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("tietou")
                                .executes(TietouCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher66 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd66 = dispatcher66.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("setTietou").then(
                                Commands.argument("key", StringArgumentType.string()).then(
                                        Commands.argument("num", DoubleArgumentType.doubleArg())
                                                .executes(SetTietouCommand.instance)
                                )
                        )
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher67 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd67 = dispatcher67.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("reason").then(
                                Commands.argument("player", GameProfileArgument.gameProfile()).then(
                                        Commands.argument("num", IntegerArgumentType.integer())
                                                .executes(ReasonCommand.instance)
                                )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher68 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd68 = dispatcher68.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("entrustment").then(
                                Commands.argument("operation", StringArgumentType.string())
                                        .executes(MobKillEntrustmentOperationCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher69 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd69 = dispatcher69.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("openScreen").then(
                                Commands.argument("type", IntegerArgumentType.integer())
                                        .executes(OpenScreenOperationCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher70 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd70 = dispatcher70.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("closeGuide")
                                .executes(GuideHudCloseCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher71 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd71 = dispatcher71.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("profession").then(
                                Commands.argument("operation", StringArgumentType.string())
                                        .executes(ProfessionOperationCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher72 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd72 = dispatcher72.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("bank").then(
                                Commands.argument("operation", StringArgumentType.string())
                                        .executes(BankOperationCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher73 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd73 = dispatcher73.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("getGoldenBeans").then(
                                Commands.argument("num", IntegerArgumentType.integer())
                                        .executes(GetGoldenBeansCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher74 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd74 = dispatcher74.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("removeRecords")
                                .executes(RemoveManaTowerRecordCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher75 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd75 = dispatcher75.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("showRecords")
                                .executes(ShowManaTowerRecordCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher76 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd76 = dispatcher76.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("clearEstate").then(
                                Commands.argument("player", GameProfileArgument.gameProfile())
                                        .executes(ClearEstateCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher77 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd77 = dispatcher77.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("clearEstateById").then(
                                Commands.argument("playerId", StringArgumentType.string())
                                        .executes(ClearEstateByPlayerIdCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(0))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher78 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd78 = dispatcher78.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("resetEstateSignBlockText")
                                .executes(ResetEstateSignBlockTextCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher79 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd79 = dispatcher79.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("clearRealEstate").then(
                                Commands.argument("player", GameProfileArgument.gameProfile())
                                        .executes(ClearRealEstateCommand.instance)
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher80 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd80 = dispatcher80.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("sellEstate")
                                .executes(SellEstateCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher81 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd81 = dispatcher81.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("sellRealEstate")
                                .executes(SellRealEstateCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher82 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd82 = dispatcher82.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("cooking").then(
                                Commands.argument("operation", StringArgumentType.string())
                                        .executes(CookingOperationCommand.instance)
                        )
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher83 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd83 = dispatcher83.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("estateQuery")
                                .executes(QueryEstateCommand.instance)
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher84 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd84 = dispatcher84.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("specificPlayerTag").then(
                                Commands.argument("player", GameProfileArgument.gameProfile()).then(
                                        Commands.argument("topKey", StringArgumentType.string()).then(
                                                Commands.argument("type", StringArgumentType.string()).then(
                                                        Commands.argument("key", StringArgumentType.string()).then(
                                                                Commands.argument("value", StringArgumentType.string())
                                                                        .executes(SpecificPlayerTagCommand.instance)
                                                        )
                                                )
                                        )
                                )
                        ).requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher85 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd85 = dispatcher85.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("clearBounding")
                                .executes(ClearBoundingCommand.instance)
                                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher86 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd86 = dispatcher86.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("bounding")
                                .executes(BoundingCommand.instance)
                )
        );
        CommandDispatcher<CommandSourceStack> dispatcher87 = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd87 = dispatcher87.register(
                Commands.literal(Utils.MOD_ID).then(
                        Commands.literal("setWeeklyStoreIssueCount").then(
                                Commands.argument("count", IntegerArgumentType.integer())
                                        .executes(WeeklyStoreIssueCountCommand.instance)
                        )
                )
        );
    }
}

package fun.wraq.process.system.bank;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.commands.stable.ops.SummonVillagerCommand;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;

public class BankOperationCommand implements Command<CommandSourceStack> {
    public static BankOperationCommand instance = new BankOperationCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        String operation = StringArgumentType.getString(context, "operation");
        switch (operation) {
            case "villager" -> {
                SummonVillagerCommand.summonVillager(player, VillagerType.SAVANNA, VillagerProfession.LIBRARIAN,
                        "联合银行职员", CustomStyle.styleOfGold);
            }
            case "tryToGetDividends" -> {
                BondDividends.tryToGetDividends(player);
            }
            case "getDividends" -> {
                BondDividends.getDividends(player);
            }
            case "tryToGetGoldenBeans" -> {
                Bank.tryToGetGoldenBeans(player);
            }
        }
        return 0;
    }
}

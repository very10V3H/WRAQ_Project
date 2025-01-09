package fun.wraq.process.system.profession;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.commands.stable.ops.SummonVillagerCommand;
import fun.wraq.process.system.profession.pet.allay.AllayPetPlayerData;
import fun.wraq.process.system.profession.smith.SmithPlayerData;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;

public class ProfessionOperationCommand implements Command<CommandSourceStack> {
    public static ProfessionOperationCommand instance = new ProfessionOperationCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        String operation = StringArgumentType.getString(context, "operation");
        switch (operation) {
            case "allayStore" -> {
                AllayPetPlayerData.openAllayStore(player);
            }
            case "allayTier" -> {
                AllayPetPlayerData.tryToIncrementAllayTier(player);
            }
            case "incrementAllayTier" -> {
                AllayPetPlayerData.incrementAllayTier(player);
            }
            case "smithVillager" -> {
                SummonVillagerCommand.summonVillager(player, VillagerType.PLAINS, VillagerProfession.TOOLSMITH,
                        "工匠学者", CustomStyle.styleOfStone);
            }
            case "smithTier" -> {
                SmithPlayerData.tryToIncrementAllayTier(player);
            }
            case "incrementSmithTier" -> {
                SmithPlayerData.incrementAllayTier(player);
            }
        }
        return 0;
    }
}

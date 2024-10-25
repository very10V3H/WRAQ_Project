package fun.wraq.process.system.bonuschest;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;


public class BonusInfoCommand implements Command<CommandSourceStack> {
    public static BonusInfoCommand instance = new BonusInfoCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        player.sendSystemMessage(Te.s("奖励箱明细:"));
        for (int i = 0 ; i <= BonusChestInfo.Util.MAX_ZONE_NUM ; i ++) {
            player.sendSystemMessage(Te.s(BonusChestInfo.Util.ZONE_NAME_MAP.get(i), "区域",
                    " " + BonusChestPlayerData.getZoneCount(player, i)));
        }
        for (int i = 0 ; i <= 3 ; i ++) {
            player.sendSystemMessage(Te.s(BonusChestInfo.Util.TIER_NAME_MAP.get(i),
                    " " + BonusChestPlayerData.getTierCount(player, i)));
        }
        return 0;
    }
}

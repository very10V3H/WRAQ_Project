package fun.wraq.process.system.estate;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class ClearEstateByPlayerIdCommand implements Command<CommandSourceStack> {
    public static ClearEstateByPlayerIdCommand instance = new ClearEstateByPlayerIdCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) {
            return 0;
        }
        String playerId = StringArgumentType.getString(context, "playerId");
        EstateServerSavedData savedData = EstateServerSavedData.getInstance(Tick.server.overworld());
        List<String> infoStringList = savedData.getEstateInfoStringList();
        infoStringList.stream()
                .map(EstateServerData::getDataFromString)
                .filter(serverData -> serverData.ownerId.equals(playerId))
                .forEach(serverData -> {
                    int serial = serverData.serial;
                    EstateInfo estateInfo = EstateInfo.values()[serial];
                    EstateUtil.broad(Te.s(estateInfo.estateName.getString(), CustomStyle.styleOfGold,
                            "已重新开始售卖!"));
                    EstateUtil.resetSignBlockText(estateInfo);
                    EstateServerData.removeEstateServerData(serial);
                });
        return 0;
    }
}

package fun.wraq.process.system.estate;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;


public class SellRealEstateCommand implements Command<CommandSourceStack> {
    public static SellRealEstateCommand instance = new SellRealEstateCommand();

    public static Map<String, Boolean> confirmMap = new HashMap<>();
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) {
            return 0;
        }
        int serial = EstatePlayerData.getRealEstateSerial(player);
        if (serial == -1) {
            EstateUtil.sendMSG(player, Te.s("暂时没有资产可供出售."));
            return 0;
        }
        if (!confirmMap.containsKey(Name.get(player))) {
            confirmMap.put(Name.get(player), true);
            EstateInfo estateInfo = EstateInfo.values()[serial];
            int price = (int) (estateInfo.price * 0.5);
            EstateUtil.sendMSG(player, Te.s("再次输入相同指令，以", price + "GB", ChatFormatting.GOLD,
                    "的价格出售", estateInfo.estateName.getString(), ChatFormatting.GOLD));
        } else {
            confirmMap.remove(Name.get(player));
            EstateUtil.sellRealEstate(player);
        }
        return 0;
    }
}

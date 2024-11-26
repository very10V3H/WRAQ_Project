package fun.wraq.commands.stable.ops;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.rank.RankData;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Collection;

public class RankItemCommand implements Command<CommandSourceStack> {
    public static RankItemCommand instance = new RankItemCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Collection<GameProfile> gameProfile = GameProfileArgument.getGameProfiles(context, "player");
        String rankOrigin = StringArgumentType.getString(context, "rankOrigin");
        String rankBound = StringArgumentType.getString(context, "rankBound");
        int itemType = IntegerArgumentType.getInteger(context, "itemType");

        if (!RankData.rankNameMap.containsKey(rankOrigin)) {
            RankData.sendFormatMSG(player, Te.s("rankOrigin不存在"));
            return 0;
        }
        if (!RankData.rankNameMap.containsKey(rankBound)) {
            RankData.sendFormatMSG(player, Te.s("rankBound不存在"));
            return 0;
        }

        int origin = RankData.rankSerialList.indexOf(rankOrigin);
        int bound = RankData.rankSerialList.indexOf(rankBound);
        for (GameProfile profile : gameProfile) {
            ServerPlayer target = player.getServer().getPlayerList().getPlayer(profile.getId());
            if (target == null) {
                RankData.sendFormatMSG(player, Te.s("玩家似乎不在线"));
                return 0;
            }
            // target -> player
            int itemNum = 0;
            Item item;
            switch (itemType) {
                case 0 -> {
                    if (origin == 1) ++origin;
                    for (int i = origin ; i <= bound ; i ++) {
                        itemNum += i * 100;
                    }
                }
                case 1, 2 -> {
                    for (int i = origin ; i <= bound ; i ++) {
                        itemNum += i * 5;
                    }
                }
            }
            switch (itemType) {
                case 0 -> item = ModItems.worldSoul5.get();
                case 1 -> item = ModItems.REVELATION_HEART.get();
                case 2 -> item = ModItems.KillPaperLootL.get();
                default -> item = Items.AIR;
            }
            InventoryOperation.itemStackGiveWithMSGByBatch(target, new ItemStack(item, itemNum));
            RankData.sendFormatMSG(player, Te.s("已给予", target.getDisplayName(),
                    item.getDefaultInstance().getDisplayName(), " * " + itemNum, ChatFormatting.AQUA));
            RankData.sendFormatMSG(target, Te.s("已获得",
                    item.getDefaultInstance().getDisplayName(), " * " + itemNum, ChatFormatting.AQUA));
        }
        return 0;
    }
}

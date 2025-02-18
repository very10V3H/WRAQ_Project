package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.*;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.files.MarketItemInfo;
import fun.wraq.process.system.market.MarketInfo;
import fun.wraq.process.system.wayPoints.MyWayPoint;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class SellCommand implements Command<CommandSourceStack> {

    public static SellCommand instance = new SellCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayer();
        int price = IntegerArgumentType.getInteger(context, "price");
        int type = IntegerArgumentType.getInteger(context, "type");
        if (price <= 0 || price > 10000000) {
            Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                    Component.literal("需要一个合理的价格喔").withStyle(ChatFormatting.WHITE));
            return 0;
        }
        int count = (int) Utils.marketItemInfos
                .stream().filter(marketItemInfo -> marketItemInfo.playerName.equals(player.getName().getString()))
                .count();
        if (count >= 15 && !player.isCreative()) {
            Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                    Component.literal("超出了限制的出售数量(15)。"));
            return 0;
        }
        if (type != 0 && type != 1) {
            Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                    Te.s("货币类型参数(最后一个参数<type>)需要 0 - VB 或 1 - 金豆."));
            return 0;
        }
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        Item item = itemStack.getItem();
        if (isAllowedSold(item)) {
            InventoryCheck.removeOwnerTag(player, itemStack);
        }
        if (!player.isCreative() && (itemStack.is(Items.AIR) || !MarketItemInfo.itemCanBeSold(itemStack)
                || InventoryCheck.containOwnerTag(itemStack))) {
            Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                    Component.literal("这件物品似乎不可以出售。"));
            return 0;
        }
        MarketItemInfo marketItemInfo = new MarketItemInfo(player.getName().getString(), itemStack, price, type);
        Utils.marketItemInfos.add(marketItemInfo);
        player.setItemInHand(InteractionHand.MAIN_HAND, Items.AIR.getDefaultInstance());
        Compute.sendFormatMSG(player, Component.literal("市场").withStyle(ChatFormatting.GOLD),
                Component.literal("上架成功。").withStyle(ChatFormatting.WHITE));
        Compute.formatBroad(player.level(), Component.literal("市场").withStyle(ChatFormatting.GOLD),
                Te.s(player.getDisplayName(), "以 ", price, MarketInfo.getTypeDescription(type),
                        ChatFormatting.GOLD, " 的价格出售了 ", itemStack.getDisplayName()));
        LogUtils.getLogger().info("市场 {} 以 {} 出售了 {}", player.getName().getString(),
                price + MarketInfo.getType(type), itemStack);
        computeCommission(player, price, type);
        return 0;
    }

    public static void computeCommission(ServerPlayer player, int price, int type) {
        if (!(player.level().dimension().equals(Level.OVERWORLD)
                && player.position().distanceTo(MyWayPoint.VillageWayPoint.SUN_RISE_ISLAND.pos) < 100)) {
            Compute.sendFormatMSG(player, Te.s("市场", ChatFormatting.GOLD),
                    Te.s("在", "旭升岛中心区域", CustomStyle.styleOfSunIsland, "外交易物品，需要扣除 ",
                            "1000VB", ChatFormatting.GOLD, " + 10%价格(1金豆 + 500VB)", "手续费。"));
            double commissionCharge = 1000;
            if (type == 1) {
                commissionCharge += price * 500;
            } else {
                commissionCharge += price * 0.1;
            }
            Compute.VBExpenseAndMSGSend(player, commissionCharge);
        }
    }

    public boolean isAllowedSold(Item item) {
        return !InventoryCheck.getBoundingList().contains(item)
                && (item instanceof WraqMainHandEquip
                || item instanceof WraqArmor
                || item instanceof WraqCurios
                || item instanceof WraqOffHandItem
                || item instanceof WraqPassiveEquip
                || item instanceof WraqPickaxe);
    }
}

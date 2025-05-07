package fun.wraq.process.system.cooking;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CookingVillager {
    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("烹饪", CustomStyle.MUSHROOM_STYLE), content);
    }

    public static Map<String, ItemStack> cookingEntrustmentContentMap = new HashMap<>();
    public static Map<String, Integer> acceptTickMap = new HashMap<>();
    public static Map<String, Integer> nextAllowAcceptTickMap = new HashMap<>();

    public static void onPlayerInteractWithVillager(Player player) {
        Style style = CustomStyle.MUSHROOM_STYLE;
        if (cookingEntrustmentContentMap.containsKey(Name.get(player))) {
            ItemStack stack = cookingEntrustmentContentMap.get(Name.get(player));
            if (InventoryOperation.removeItem(player, stack.getItem(), stack.getCount())) {
                submit(player);
            } else {
                sendMSG(player, Te.s("怎么样，饭做好了吗？", player));
                Compute.sendBlankLine(player, 3);
                player.sendSystemMessage(Te.s(" ".repeat(4),
                        Te.c(Te.s("「取消委托」", style),
                                "/vmd cooking cancelEntrustment",
                                Te.s("点击以取消正在进行的烹饪委托")),
                        " ".repeat(4)));
            }
        } else {
            sendMSG(player, Te.s("肚肚打雷了，你能帮我点份外卖吃吗？", player));
            Compute.sendBlankLine(player, 3);
            player.sendSystemMessage(Te.s(" ".repeat(4),
                    Te.c(Te.s("「出售食物」", style),
                            "/vmd cooking sellAllFood",
                            Te.s("点击将背包中的所有烹饪食物出售")),
                    " ".repeat(4),
                    Te.c(Te.s("「接取委托」", style),
                            "/vmd cooking acceptEntrustment",
                            Te.s("点击以尝试取出接取烹饪委托")),
                    " ".repeat(4)));
        }
        Compute.sendBlankLine(player, 4);
        MySound.soundToNearPlayer(player, SoundEvents.VILLAGER_AMBIENT);
    }

    public static void submit(Player player) {
        ItemStack stack = cookingEntrustmentContentMap.get(Name.get(player));
        int acceptTick = acceptTickMap.get(Name.get(player));
        Compute.VBIncomeAndMSGSend(player, CookingValue.getMealSellValue(stack) * 1.4);
        InventoryOperation.giveItemStackWithMSG(player, ModItems.GOLDEN_BEANS.get(), 1);
        InventoryOperation.giveItemStackWithMSG(player, ModItems.FOOD_COIN.get(), 1);
        CookingPlayerData.incrementFinishedTimesCount(player);
        cookingEntrustmentContentMap.remove(Name.get(player));
        acceptTickMap.remove(Name.get(player));
        sendMSG(player, Te.s("五麦！你已经累计为老八做了",
                CookingPlayerData.getFinishedTimesCount(player) + "次", CustomStyle.MUSHROOM_STYLE, "美味佳肴了!"));
        MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
        Compute.givePercentExpToPlayer(player, 0.1, 0, player.experienceLevel);
    }

    public static void acceptEntrustment(Player player) {
        if (cookingEntrustmentContentMap.containsKey(Name.get(player))) {
            sendMSG(player, Te.s("当前已有一个正在进行的烹饪委托."));
            return;
        }
        if (!player.isCreative() && nextAllowAcceptTickMap.getOrDefault(Name.get(player), 0) > Tick.get()) {
            sendMSG(player, Te.s("当前还不能接取烹饪委托."));
            return;
        }
        List<Item> cookingProducts = CookingItems.getCookingProducts();
        Item item = cookingProducts.get(RandomUtils.nextInt(0, cookingProducts.size()));
        ItemStack requireProduct = new ItemStack(item, 4);
        cookingEntrustmentContentMap.put(Name.get(player), requireProduct);
        sendMSG(player, Te.s("已接取烹饪委托: 烹饪 ",
                item, " * " + requireProduct.getCount(), CustomStyle.MUSHROOM_STYLE, "后交给老八!"));
        acceptTickMap.put(Name.get(player), Tick.get());
    }

    public static void cancelEntrustment(Player player) {
        sendMSG(player, Te.s("已取消烹饪委托，在", "5min", ChatFormatting.AQUA, "后方可接受下一个委托."));
        nextAllowAcceptTickMap.put(Name.get(player), Tick.get() + Tick.min(5));
        cookingEntrustmentContentMap.remove(Name.get(player));
    }
}

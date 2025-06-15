package fun.wraq.series.crystal;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OriginStone extends WraqItem {

    public OriginStone(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s("还未切开的宝石原石，右键切开概率获得:"));
        components.add(Te.s(" · 紫水晶", ChatFormatting.LIGHT_PURPLE, " 3%"));
        components.add(Te.s(" · 红宝石", ChatFormatting.RED, " 6%"));
        components.add(Te.s(" · 黄宝石", ChatFormatting.YELLOW, " 13%"));
        components.add(Te.s(" · 蓝宝石", ChatFormatting.BLUE, " 26%"));
        components.add(Te.s(" · 绿宝石", ChatFormatting.GREEN, " 52%"));
        components.add(Te.s("可能获得的品质有:"));
        components.add(Te.s(" · 万里挑一", ChatFormatting.LIGHT_PURPLE, " 3%"));
        components.add(Te.s(" · 千载难逢", ChatFormatting.RED, " 6%"));
        components.add(Te.s(" · 稀有", ChatFormatting.YELLOW, " 13%"));
        components.add(Te.s(" · 罕见", ChatFormatting.BLUE, " 26%"));
        components.add(Te.s(" · 普通", ChatFormatting.GREEN, " 52%"));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public static Map<String, Integer> valueMap = new HashMap<>();
    public static Map<String, Integer> expiredTickMap = new HashMap<>();
    public static Map<String, Calendar> startRecordTimeMap = new HashMap<>();

    public static final String DATA_KEY = "ORIGIN_STONE_PROFIT_DATA";

    public static int getProfit(Player player) {
        return player.getPersistentData().getInt(DATA_KEY);
    }

    public static void addProfit(Player player, int profit) {
        player.getPersistentData().putInt(DATA_KEY, getProfit(player) + profit);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.playerItemUseWithRecord(player);
            int typeIndex;
            int tierIndex;
            double typeRandomNum = RandomUtils.nextDouble(0, 1);
            typeIndex = getIndex(typeRandomNum);
            double tierRandomNum = RandomUtils.nextDouble(0, 1);
            tierIndex = getIndex(tierRandomNum);
            Item crystal = CrystalItem.map.get(typeIndex).get(tierIndex);
            InventoryOperation.giveItemStackWithMSG(player, crystal);
            int singleProfit = (int) (Math.pow(2, typeIndex + tierIndex) - 7);
            if (expiredTickMap.getOrDefault(Name.get(player), 0) < Tick.get()) {
                expiredTickMap.put(Name.get(player), Tick.get() + Tick.min(1));
                valueMap.put(Name.get(player), singleProfit);
                startRecordTimeMap.put(Name.get(player), Calendar.getInstance());
            } else {
                expiredTickMap.put(Name.get(player), Tick.get() + Tick.min(1));
                valueMap.compute(Name.get(player),
                        (k, v) -> v == null ? singleProfit : v + singleProfit);
            }
            SimpleDateFormat tmpDate = new SimpleDateFormat("HH:mm:ss");
            Compute.sendFormatMSG(player, Te.s("赌石", ChatFormatting.LIGHT_PURPLE),
                    Te.s("自", tmpDate.format(startRecordTimeMap.get(Name.get(player)).getTime()),
                            "开始的折合利润为: ", (valueMap.get(Name.get(player)) * 4) + "w",
                            valueMap.get(Name.get(player)) >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED));
            addProfit(player, singleProfit * 4);
            Compute.sendFormatMSG(player, Te.s("赌石", ChatFormatting.LIGHT_PURPLE),
                    Te.s("当前总的折合利润为: ", getProfit(player) + "w",
                            getProfit(player) >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED));
        }
        return super.use(level, player, interactionHand);
    }

    public static int getIndex(double randomNum) {
        int index;
        if (randomNum < 1d/31) {
            index = 4;
        } else if (randomNum < 1d/31 + 2d/31) {
            index = 3;
        } else if (randomNum < 1d/31 + 2d/31 + 4d/31) {
            index = 2;
        } else if (randomNum < 1d/31 + 2d/31 + 4d/31 + 8d/31) {
            index = 1;
        } else {
            index = 0;
        }
        return index;
    }
}

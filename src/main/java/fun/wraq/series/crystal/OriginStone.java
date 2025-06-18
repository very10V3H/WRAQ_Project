package fun.wraq.series.crystal;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.gui.villagerTrade.MyVillagerData;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
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
        components.add(Te.s("当拥有500w+VB时，才能购买/使用该物品."));
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
            if (Compute.getCurrentVB(player) < 5000000) {
                Compute.sendFormatMSG(player, Te.s("赌石", ChatFormatting.LIGHT_PURPLE),
                        Te.s("需要至少拥有500w的保底资金才能进行赌石."));
            } else {
                Compute.playerItemUseWithRecord(player);
                int typeIndex;
                int tierIndex;
                double typeRandomNum = RandomUtils.nextDouble(0, 1);
                typeIndex = getIndex(typeRandomNum);
                double tierRandomNum = RandomUtils.nextDouble(0, 1);
                tierIndex = getIndex(tierRandomNum);
                Item crystal = CrystalItem.map.get(typeIndex).get(tierIndex);
                int singleProfit = (int) (Math.pow(2, typeIndex + tierIndex) - 7);
                if (singleProfit > 100) {
                    Compute.formatBroad(Te.s("赌石", ChatFormatting.LIGHT_PURPLE),
                            Te.s(player, "通过赌石获得了", crystal));
                }
                InventoryOperation.giveItemStackWithMSG(player, crystal);
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
                sendMSG(player, Te.s("自", tmpDate.format(startRecordTimeMap.get(Name.get(player)).getTime()),
                        "开始的折合利润为: ", (valueMap.get(Name.get(player)) * 4) + "w",
                        valueMap.get(Name.get(player)) >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED));
                addProfit(player, singleProfit * 4);
                sendMSG(player, Te.s("当前总的折合利润为: ", getProfit(player) + "w",
                        getProfit(player) >= 0 ? ChatFormatting.GREEN : ChatFormatting.RED));
            }
        }
        return super.use(level, player, interactionHand);
    }

    public static Item getCrystalRandomly() {
        double typeRandomNum = RandomUtils.nextDouble(0, 1);
        int typeIndex = getIndex(typeRandomNum);
        double tierRandomNum = RandomUtils.nextDouble(0, 1);
        int tierIndex = getIndex(tierRandomNum);
        return CrystalItem.map.get(typeIndex).get(tierIndex);
    }

    public static int getIndex(double randomNum) {
        int index;
        if (randomNum < 1d / 31) {
            index = 4;
        } else if (randomNum < 1d / 31 + 2d / 31) {
            index = 3;
        } else if (randomNum < 1d / 31 + 2d / 31 + 4d / 31) {
            index = 2;
        } else if (randomNum < 1d / 31 + 2d / 31 + 4d / 31 + 8d / 31) {
            index = 1;
        } else {
            index = 0;
        }
        return index;
    }

    public static final String VILLAGER_NAME = "多彩宝石商人";

    public static void setVillagerData() {
        MyVillagerData.setMyVillagerData(VILLAGER_NAME,
                "crystal", CustomStyle.styleOfYSR1, VillagerType.PLAINS,
                VillagerProfession.LIBRARIAN, List.of());
    }

    public static void onPlayerRightClick(Player player) {
        Compute.sendFormatMSG(player, Te.s("赌石", ChatFormatting.LIGHT_PURPLE),
                Te.s("你也对各种颜色的宝石感兴趣吗？"));
        Compute.sendBlankLine(player, 3);
        player.sendSystemMessage(Te.s(" ".repeat(4),
                Te.c(Te.s("「宝石交易」", CustomStyle.styleOfGold),
                        "/vmd crystal trade",
                        Te.s("向宝石商人出售/购买宝石")),
                " ".repeat(4),
                Te.c(Te.s("「购买原石」", CustomStyle.styleOfGold),
                        "/vmd crystal buyStone",
                        Te.s("购买", CrystalItems.ORIGIN_STONE.get(), "(一块28w VB)", ChatFormatting.GOLD)),
                " ".repeat(4)));
        Compute.sendBlankLine(player, 4);
        MySound.soundToNearPlayer(player, SoundEvents.VILLAGER_AMBIENT);
    }

    public static void tryToBuy(Player player) {
        int discountNum = InventoryOperation.itemStackCount(player, CrystalItems.ORIGIN_STONE_DISCOUNT.get());
        if (discountNum >= 20) {
            InventoryOperation.removeItem(player, CrystalItems.ORIGIN_STONE_DISCOUNT.get(), 20);
            sendMSG(player, Te.s("本次使用20张", CrystalItems.ORIGIN_STONE_DISCOUNT.get(),
                    "直接兑换了", CrystalItems.ORIGIN_STONE.get()));
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(CrystalItems.ORIGIN_STONE.get()));
            return;
        }
        if (Compute.getCurrentVB(player) < 5280000) {
            sendMSG(player, Te.s("需要至少拥有500w的保底资金才能进行赌石."));
            return;
        }
        Compute.VBExpenseAndMSGSend(player, 280000 - discountNum * 14000);
        InventoryOperation.removeItem(player, CrystalItems.ORIGIN_STONE_DISCOUNT.get(), discountNum);
        sendMSG(player, Te.s("本次使用了" + discountNum + "张",
                CrystalItems.ORIGIN_STONE_DISCOUNT.get(), "折扣了", discountNum * 14000 + "VB", ChatFormatting.GOLD));
        InventoryOperation.giveItemStackWithMSG(player, new ItemStack(CrystalItems.ORIGIN_STONE.get()));
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("赌石", ChatFormatting.LIGHT_PURPLE), content);
    }
}

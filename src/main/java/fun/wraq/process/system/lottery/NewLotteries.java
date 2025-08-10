package fun.wraq.process.system.lottery;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.customized.UniformItems;
import fun.wraq.customized.uniform.attack.normal.WraqAttackUniformCurios;
import fun.wraq.customized.uniform.bow.normal.WraqBowUniformCurios;
import fun.wraq.customized.uniform.element.WraqElementUniformCurios;
import fun.wraq.customized.uniform.mana.normal.WraqManaUniformCurios;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.system.lottery.networking.LotteryRewardTimeS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.dragon.SilverDragonItems;
import fun.wraq.series.events.labourDay.LabourDayOldCoin;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.*;

public class NewLotteries extends Item {

    public static List<Item> lotteryItems = new ArrayList<>();
    public static Map<String, Integer> clientRewardTimes = new HashMap<>();

    public static Map<Item, Integer> getRewardSerial = new HashMap<>();

    public static void setGetRewardSerial() {
        getRewardSerial.put(ModItems.SWORD_LOTTERY.get(), 2);
        getRewardSerial.put(ModItems.BOW_LOTTERY.get(), 2);
        getRewardSerial.put(ModItems.SCEPTRE_LOTTERY.get(), 2);
        getRewardSerial.put(ModItems.SWORD_LOTTERY_1.get(), 2);
        getRewardSerial.put(ModItems.BOW_LOTTERY_1.get(), 2);
        getRewardSerial.put(ModItems.SCEPTRE_LOTTERY_1.get(), 2);
        getRewardSerial.put(SilverDragonItems.SILVER_DRAGON_SWORD_LOTTERY.get(), 2);
        getRewardSerial.put(SilverDragonItems.SILVER_DRAGON_BOW_LOTTERY.get(), 2);
        getRewardSerial.put(SilverDragonItems.SILVER_DRAGON_SCEPTRE_LOTTERY.get(), 2);
    }

    public static Map<Item, Integer> getGetRewardSerial() {
        if (getRewardSerial.isEmpty()) setGetRewardSerial();
        return getRewardSerial;
    }

    public static Map<Item, Integer> guaranteeTimes = new HashMap<>();

    public static void setGuaranteeTimes() {
        guaranteeTimes.put(ModItems.SWORD_LOTTERY.get(), 90);
        guaranteeTimes.put(ModItems.BOW_LOTTERY.get(), 90);
        guaranteeTimes.put(ModItems.SCEPTRE_LOTTERY.get(), 90);
        guaranteeTimes.put(ModItems.SWORD_LOTTERY_1.get(), 90);
        guaranteeTimes.put(ModItems.BOW_LOTTERY_1.get(), 90);
        guaranteeTimes.put(ModItems.SCEPTRE_LOTTERY_1.get(), 90);
        guaranteeTimes.put(SilverDragonItems.SILVER_DRAGON_SWORD_LOTTERY.get(), 90);
        guaranteeTimes.put(SilverDragonItems.SILVER_DRAGON_BOW_LOTTERY.get(), 90);
        guaranteeTimes.put(SilverDragonItems.SILVER_DRAGON_SCEPTRE_LOTTERY.get(), 90);
    }

    public static Map<Item, Double> guaranteeRange = new HashMap<>();

    public static void setGuaranteeRange() {
        guaranteeRange.put(ModItems.SWORD_LOTTERY.get(), 0.01);
        guaranteeRange.put(ModItems.BOW_LOTTERY.get(), 0.01);
        guaranteeRange.put(ModItems.SCEPTRE_LOTTERY.get(), 0.01);
        guaranteeRange.put(ModItems.SWORD_LOTTERY_1.get(), 0.01);
        guaranteeRange.put(ModItems.BOW_LOTTERY_1.get(), 0.01);
        guaranteeRange.put(ModItems.SCEPTRE_LOTTERY_1.get(), 0.01);
        guaranteeRange.put(SilverDragonItems.SILVER_DRAGON_SWORD_LOTTERY.get(), 0.01);
        guaranteeRange.put(SilverDragonItems.SILVER_DRAGON_BOW_LOTTERY.get(), 0.01);
        guaranteeRange.put(SilverDragonItems.SILVER_DRAGON_SCEPTRE_LOTTERY.get(), 0.01);
    }

    public record Loot(ItemStack itemStack, double rate) {
    }

    protected List<Loot> loots;
    protected final Item KEY;
    protected final Item followItem;
    protected final boolean isFoiled;
    protected List<Component> description;
    protected double notifyRate = 0.01;

    public NewLotteries(Properties properties, List<Loot> loots, Item key, Item followItem, boolean isFoiled) {
        super(properties);
        this.loots = loots;

        // 调整未满100%概率
        double frontRate = 0;
        for (int i = 0; i < loots.size() - 1; i++) frontRate += loots.get(i).rate;
        loots.set(loots.size() - 1, new Loot(loots.get(loots.size() - 1).itemStack, 1 - frontRate));

        lotteryItems.add(this);
        this.KEY = key;
        this.followItem = followItem;
        this.isFoiled = isFoiled;
    }

    public NewLotteries(Properties properties, List<Loot> loots, Item key) {
        this(properties, loots, key, null, false);
    }

    public NewLotteries(Properties properties, List<Loot> loots) {
        this(properties, loots, null, null, false);
    }

    public NewLotteries(Properties properties, List<Loot> loots, double notifyRate) {
        this(properties, loots, null, null, false);
        this.notifyRate = notifyRate;
    }

    public NewLotteries(Properties properties, List<Loot> loots, boolean isFoiled, List<Component> description) {
        this(properties, loots, null, null, isFoiled);
        this.description = description;
    }

    private int lootSerialNum(double range) {
        SecureRandom rand = new SecureRandom();
        double fullRate = rand.nextDouble(range);
        for (int i = 0; i < loots.size(); i++) {
            Loot loot = loots.get(i);
            fullRate -= loot.rate;
            if (fullRate <= 0) return i;
        }
        return loots.size() - 1;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(""));
        if (KEY != null) {
            components.add(Te.s(" 开启需要消耗一个", KEY));
        }
        components.add(Component.literal(" 可能获得的奖励有:").withStyle(ChatFormatting.GOLD));
        Item item = itemStack.getItem();
        if (getRewardSerial.isEmpty()) setGetRewardSerial();
        if (guaranteeTimes.isEmpty()) setGuaranteeTimes();
        if (guaranteeRange.isEmpty()) setGuaranteeRange();
        double rateSum = 0;
        for (int i = 0; i < loots.size(); i++) {
            Loot loot = loots.get(i);
            rateSum += loot.rate;
            ChatFormatting style = ChatFormatting.AQUA;
            if (getRewardSerial.containsKey(item) && i < getRewardSerial.get(item)) style = ChatFormatting.RED;
            double rate = loot.rate;
            if (i == loots.size() - 1) {
                rate = 1 - (rateSum - loot.rate);
            }
            components.add(Component.literal(" " + (i + 1) + ".").withStyle(style).
                    append(loot.itemStack.getDisplayName()).
                    append(Component.literal(" *" + loot.itemStack.getCount()).withStyle(style)).
                    append(Component.literal(" 「" + String.format("%.2f%%", rate * 100) + "」").withStyle(style)));
        }
        if (guaranteeTimes.containsKey(item)) {
            components.add(Component.literal(""));
            components.add(Component.literal(" 第" + guaranteeTimes.get(item) + "次必定抽取极品奖励").withStyle(ChatFormatting.GOLD));
        }
        if (getUniformLotteryItems().contains(this) && ClientUtils.serverTime != null) {
            int date = 0;
            try {
                date = Compute.StringToCalendar(ClientUtils.serverTime).get(Calendar.DAY_OF_YEAR);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            components.add(Te.s(" 距离下次极品轮换还有 ", ChatFormatting.GOLD,
                    (date / 7 + 1) * 7 - date + " 天", ChatFormatting.AQUA));
        }
        String itemString = this.toString();
        if (followItem != null) {
            itemString = followItem.toString();
        }
        if (guaranteeTimes.containsKey(item) && clientRewardTimes.containsKey(itemString)) {
            int times = clientRewardTimes.get(itemString);
            components.add(Te.s(" 距离上次获得极品，你已经开启了 ", CustomStyle.styleOfStone,
                    String.valueOf(clientRewardTimes.get(itemString)), CustomStyle.styleOfWorld,
                    " 次", CustomStyle.styleOfStone));
            double rate = 0.01 / (1 - times * 0.005);
            if (times + 1 == guaranteeTimes.get(this)) {
                rate = 1;
            }
            components.add(Te.s(" 下一次抽取极品的概率估计: ", CustomStyle.styleOfStone,
                    String.format("%.2f%%", rate * 100), CustomStyle.styleOfGold));
            if (followItem != null) {
                components.add(Te.s(" 这个补给包与", followItem, "共享抽取累计", CustomStyle.styleOfStone));
            }
        }
        if (description != null) {
            components.add(Te.s(" 获取方式:", ChatFormatting.AQUA));
            components.addAll(description);
        }
        super.appendHoverText(itemStack, p_41422_, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) {
            if (KEY != null) {
                if (InventoryOperation.checkItemRemoveIfHas(player, List.of(new ItemStack(KEY)))) {
                    singleUse(player);
                } else {
                    Compute.sendFormatMSG(player, Te.s("奖励箱", ChatFormatting.GOLD),
                            Te.s("开启此奖励箱需要消耗一个", KEY));
                }
            } else {
                singleUse(player);
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return isFoiled;
    }

    public void singleUse(Player player) {
        ItemStack mainHandStack = player.getMainHandItem();
        Item lottery = mainHandStack.getItem();
        if (followItem != null) {
            lottery = followItem;
        }

        if (getRewardSerial.isEmpty()) setGetRewardSerial();
        if (guaranteeTimes.isEmpty()) setGuaranteeTimes();
        if (guaranteeRange.isEmpty()) setGuaranteeRange();

        boolean recordFlag = guaranteeTimes.containsKey(lottery);

        // 记录总开箱数

        if (recordFlag) {
            addPlayerOpenLotteryTimes(player, lottery);
        }

        int times = 0;
        if (recordFlag) {
            times = addPlayerRewardTimes(player, lottery);
            Compute.sendFormatMSG(player, Component.literal("礼盒").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("这是第").withStyle(ChatFormatting.WHITE).
                            append(Component.literal("" + times).withStyle(ChatFormatting.LIGHT_PURPLE)).
                            append(Component.literal("次抽取该礼盒").withStyle(ChatFormatting.WHITE)));
        }

        Compute.playerItemUseWithRecord(player);

        double range = 1;

        // 概率递增与保底
        if (getRewardSerial.containsKey(lottery)) {
            range -= times * 0.005;
            if (times == guaranteeTimes.get(lottery)) range = guaranteeRange.get(lottery);
        }

        int serialNum = lootSerialNum(range);

        // 中签次数清零
        if (recordFlag && getRewardSerial.containsKey(lottery)) {
            if (serialNum < getRewardSerial.get(lottery)) {
                setPlayerRewardTimes(player, lottery, 0);
                addPlayerLotteryWinTimes(player, lottery);
            }
        }

        // 提供奖励
        Loot loot = loots.get(serialNum);
        ItemStack itemStack = loot.itemStack;
        ItemStack reward = new ItemStack(itemStack.getItem(), itemStack.getCount());
        if (reward.getItem() instanceof RandomCurios randomCurios) {
            randomCurios.setAttribute(reward);
        }
        if ((getRewardSerial.containsKey(lottery) && serialNum < getRewardSerial.get(lottery))
                || loot.rate <= notifyRate) {
            Compute.formatBroad(player.level(), Component.literal("礼盒").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal(" 通过 ").withStyle(ChatFormatting.WHITE)).
                            append(this.getDefaultInstance().getDisplayName()).
                            append(Component.literal(" 获得了 ").withStyle(ChatFormatting.WHITE)).
                            append(reward.getDisplayName()).
                            append(Component.literal(" *" + reward.getCount()).withStyle(ChatFormatting.AQUA)).
                            append(Component.literal(guaranteeRange.containsKey(lottery) ? " (" + times + ")" : "").withStyle(ChatFormatting.GRAY)));
        }
        InventoryCheck.addOwnerTagToItemStack(player, reward);
        InventoryOperation.giveItemStack(player, reward);
    }

    public void setLoots(List<Loot> loots) {
        this.loots = loots;
    }

    public static List<Loot> getCurrentDateLotteryLoots(int date, int indexOffset, List<Item> curioList) {
        List<Loot> lootList = new ArrayList<>();
        int index = (date / 7 * 2 + indexOffset) % curioList.size();
        Item curio = curioList.get(index);
        index = (date / 7) % WraqElementUniformCurios.ELEMENT_CURIOS.size();
        Item elementCurio = WraqElementUniformCurios.ELEMENT_CURIOS.get(index);
        if (LabourDayOldCoin.isInActivityDate()) {
            elementCurio = UniformItems.LABOUR_DAY_UNIFORM_CURIO.get();
        }
        lootList.add(new Loot(curio.getDefaultInstance(), 0.005));
        lootList.add(new Loot(elementCurio.getDefaultInstance(), 0.005));
        lootList.add(new Loot(new ItemStack(ModItems.WORLD_FORGE_STONE.get()), 0.1));
        lootList.add(new Loot(new ItemStack(ModItems.FORGE_ENHANCE_3.get()), 0.1));
        lootList.add(new Loot(new ItemStack(ModItems.EQUIP_PIECE_5.get()), 0.1));
        lootList.add(new Loot(new ItemStack(ModItems.KILL_PAPER_LOOT.get(), 4), 0.2));
        lootList.add(new Loot(new ItemStack(ModItems.COMPLETE_GEM.get()), 0.1));
        lootList.add(new Loot(new ItemStack(ModItems.REPUTATION_MEDAL.get()), 0.1));
        lootList.add(new Loot(new ItemStack(ModItems.GOLD_COIN_BAG.get(), 4), 0.1));
        return lootList;
    }

    public static List<Item> getUniformLotteryItems() {
        return List.of(
                ModItems.SWORD_LOTTERY.get(), ModItems.SWORD_LOTTERY_1.get(),
                ModItems.BOW_LOTTERY.get(), ModItems.BOW_LOTTERY_1.get(),
                ModItems.SCEPTRE_LOTTERY.get(), ModItems.SCEPTRE_LOTTERY_1.get()
        );
    }

    public static int lastSetLootsDate = -1;
    public static void setCurrentLotteryLoots(int date) {
        if (lastSetLootsDate != date) {
            lastSetLootsDate = date;
            List<Item> lotteries = getUniformLotteryItems();
            for (int i = 0; i < lotteries.size(); i++) {
                NewLotteries item = (NewLotteries) lotteries.get(i);
                if (i <= 1) {
                    item.setLoots(getCurrentDateLotteryLoots(date,
                            i % 2, WraqAttackUniformCurios.ATTACK_CURIOS));
                } else if (i <= 3) {
                    item.setLoots(getCurrentDateLotteryLoots(date,
                            i % 2, WraqBowUniformCurios.BOW_CURIOS));
                } else {
                    item.setLoots(getCurrentDateLotteryLoots(date,
                            i % 2, WraqManaUniformCurios.MANA_CURIOS));
                }
            }
        }
    }

    public static Map<String, Map<String, Integer>> playerLotteryData = new HashMap<>();

    public static Map<String, Integer> getPlayerLotteryData(String name) {
        if (!playerLotteryData.containsKey(name)) playerLotteryData.put(name, new HashMap<>());
        return playerLotteryData.get(name);
    }

    public static String getItemRewardTimesString(Item item) {
        return item.toString();
    }

    public static String getItemOpenTimesKey(String s) {
        return s + "_openTimes";
    }

    public static String getItemOpenTimesKey(Item item) {
        return getItemOpenTimesKey(item.toString());
    }

    public static String getItemWinTimesKey(String s) {
        return s + "_winTimes";
    }

    public static String getItemWinTimesKey(Item item) {
        return getItemWinTimesKey(item.toString());
    }

    public static int addPlayerRewardTimes(Player player, Item item) {
        int times = getPlayerRewardTimes(player, item);
        setPlayerRewardTimes(player, item, times + 1);
        sendLotteryRewardTimes(player);
        return times + 1;
    }

    public static void setPlayerRewardTimes(Player player, Item item, int times) {
        PlanPlayer.getLotteryData(player).putInt(getItemRewardTimesString(item), times);
        sendLotteryRewardTimes(player);
    }

    public static int getPlayerRewardTimes(Player player, Item item) {
        return PlanPlayer.getLotteryData(player).getInt(getItemRewardTimesString(item));
    }

    public static int addPlayerOpenLotteryTimes(Player player, Item item) {
        int times = getPlayerOpenLotteryTimes(player, item);
        setPlayerOpenLotteryTimes(player, item, times + 1);
        sendLotteryRewardTimes(player);
        return times + 1;
    }

    public static void setPlayerOpenLotteryTimes(Player player, Item item, int times) {
        PlanPlayer.getLotteryData(player).putInt(getItemOpenTimesKey(item), times);
        sendLotteryRewardTimes(player);
    }

    public static int getPlayerOpenLotteryTimes(Player player, Item item) {
        return PlanPlayer.getLotteryData(player).getInt(getItemOpenTimesKey(item));
    }

    public static int addPlayerLotteryWinTimes(Player player, Item item) {
        int times = getPlayerLotteryWinTimes(player, item);
        setPlayerLotteryWinTimes(player, item, times + 1);
        sendLotteryRewardTimes(player);
        return times + 1;
    }

    public static void setPlayerLotteryWinTimes(Player player, Item item, int times) {
        PlanPlayer.getLotteryData(player).putInt(getItemWinTimesKey(item), times);
        sendLotteryRewardTimes(player);
    }

    public static int getPlayerLotteryWinTimes(Player player, Item item) {
        return PlanPlayer.getLotteryData(player).getInt(getItemWinTimesKey(item));
    }

    public static void sendLotteryRewardTimes(Player player) {
        Map<String, Integer> rewardTimes = new HashMap<>();
        lotteryItems.forEach(item -> {
            rewardTimes.put(item.toString(), getPlayerRewardTimes(player, item));
        });
        ModNetworking.sendToClient(new LotteryRewardTimeS2CPacket(rewardTimes), (ServerPlayer) player);
    }
}

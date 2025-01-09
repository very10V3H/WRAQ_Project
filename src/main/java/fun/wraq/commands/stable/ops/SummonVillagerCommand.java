package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.blocks.blocks.brew.BrewingRecipe;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustment;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SummonVillagerCommand implements Command<CommandSourceStack> {
    public static SummonVillagerCommand instance = new SummonVillagerCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayer();
        String offerType = StringArgumentType.getString(context, "offerType");
        String type = StringArgumentType.getString(context, "type");
        String originS = StringArgumentType.getString(context, "origin");
        String boundS = StringArgumentType.getString(context, "bound");
        int origin = Integer.parseInt(originS);
        int bound = Integer.parseInt(boundS);
        MerchantOffers offers = null;
        if (offerType.equals("food")) offers = getFoodOffers(origin, bound);
        if (offerType.equals("ore")) offers = getOreOffers(origin, bound);
        if (offerType.equals("brew")) offers = getBrewOffers(origin, bound);
        if (offerType.equals("fish")) offers = getFishOffers(origin, bound);
        Map<String, VillagerType> villagerTypeMap = new HashMap<>() {{
            put("plain", VillagerType.PLAINS);
            put("jungle", VillagerType.JUNGLE);
            put("swamp", VillagerType.SWAMP);
            put("desert", VillagerType.DESERT);
            put("snow", VillagerType.SNOW);
        }};
        Map<String, VillagerProfession> villagerProfessionMap = new HashMap<>() {{
            put("food", VillagerProfession.FARMER);
            put("ore", VillagerProfession.TOOLSMITH);
            put("brew", VillagerProfession.CLERIC); // MASON 石匠 FLETCHER 制箭师 NITWIT 傻子 CLERIC 牧师
            put("fish", VillagerProfession.FISHERMAN);
        }};
        if (offers != null) {
            Level level = player.level();
            VillagerType villagerType = villagerTypeMap.get(type);
            VillagerProfession villagerProfession = villagerProfessionMap.get(offerType);
            Villager villager = new Villager(EntityType.VILLAGER, level);
            VillagerData villagerData = new VillagerData(villagerType, villagerProfession, 5);
            villager.setVillagerData(villagerData);
            villager.setOffers(offers);
            villager.moveTo(player.position());
            level.addFreshEntity(villager);
        } else {
            player.sendSystemMessage(Component.literal("检查参数"));
        }
        return 0;
    }

    public static MerchantOffers getFoodOffers(int origin, int bound) {
        MerchantOffers offers = new MerchantOffers();
        Random random = new Random();
        List<MerchantOffer> merchantOffers = List.of(
                new MerchantOffer(new ItemStack(ModItems.copperCoin.get(), random.nextInt(origin, bound)),
                        new ItemStack(Items.COOKIE, 1),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(ModItems.copperCoin.get(), random.nextInt(origin, bound)),
                        new ItemStack(Items.BREAD, 1),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(ModItems.copperCoin.get(), random.nextInt(origin, bound)),
                        new ItemStack(Items.BAKED_POTATO, 1),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(ModItems.copperCoin.get(), random.nextInt(origin, bound)),
                        new ItemStack(Items.PUMPKIN_PIE, 1),
                        Integer.MAX_VALUE, 0, 0)
        );
        offers.addAll(merchantOffers);
        return offers;
    }

    public static MerchantOffers getOreOffers(int origin, int bound) {
        MerchantOffers offers = new MerchantOffers();
        Random random = new Random();
        List<MerchantOffer> merchantOffers = List.of(
                new MerchantOffer(new ItemStack(ModItems.silverCoin.get(), random.nextInt(origin, bound)),
                        new ItemStack(Items.RAW_IRON, 1),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(ModItems.silverCoin.get(), random.nextInt(origin, bound)),
                        new ItemStack(Items.RAW_COPPER, 1),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(ModItems.silverCoin.get(), random.nextInt(origin * 2, bound * 2)),
                        new ItemStack(Items.RAW_GOLD, 1),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(ModItems.silverCoin.get(), random.nextInt(origin * 3, bound * 3)),
                        new ItemStack(Items.DIAMOND, 1),
                        Integer.MAX_VALUE, 0, 0)
        );
        offers.addAll(merchantOffers);
        return offers;
    }

    public static MerchantOffers getBrewOffers(int origin, int bound) {
        MerchantOffers offers = new MerchantOffers();
        Random random = new Random();
        if (BrewingRecipe.basicPotionList.isEmpty()) BrewingRecipe.setBasicPotionList();
        for (Item item : BrewingRecipe.basicPotionList) {
            if (random.nextBoolean()) {
                offers.add(new MerchantOffer(new ItemStack(ModItems.GOLD_COIN.get(), random.nextInt(origin, bound)),
                        new ItemStack(item, 1),
                        Integer.MAX_VALUE, 0, 0));
            }
        }
        return offers;
    }

    public static MerchantOffers getFishOffers(int origin, int bound) {
        MerchantOffers offers = new MerchantOffers();
        Random random = new Random();
        ItemStack fishRod = new ItemStack(Items.FISHING_ROD);
        fishRod.enchant(Enchantments.FISHING_SPEED, random.nextInt(1, 4));
        fishRod.enchant(Enchantments.UNBREAKING, random.nextInt(1, 5));
        List<MerchantOffer> merchantOffers = List.of(
                new MerchantOffer(new ItemStack(ModItems.GOLD_COIN.get(), random.nextInt(origin * 4, bound * 4)),
                        fishRod,
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(ModItems.silverCoin.get(), random.nextInt(origin, bound)),
                        new ItemStack(Items.FISHING_ROD, 1),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(ModItems.silverCoin.get(), random.nextInt(origin, bound)),
                        new ItemStack(Items.COOKED_COD, 1),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(ModItems.silverCoin.get(), random.nextInt(origin, bound)),
                        new ItemStack(Items.COOKED_SALMON, 1),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(Items.COD, 8),
                        new ItemStack(ModItems.silverCoin.get(), random.nextInt(origin, bound)),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(Items.SALMON, 8),
                        new ItemStack(ModItems.silverCoin.get(), random.nextInt(origin, bound)),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(Items.TROPICAL_FISH, 2),
                        new ItemStack(ModItems.silverCoin.get(), random.nextInt(origin, bound)),
                        Integer.MAX_VALUE, 0, 0),
                new MerchantOffer(new ItemStack(Items.PUFFERFISH, 2),
                        new ItemStack(ModItems.silverCoin.get(), random.nextInt(origin, bound)),
                        Integer.MAX_VALUE, 0, 0)
        );
        offers.addAll(merchantOffers);
        return offers;
    }

    public static void summonVillager(Player player, VillagerType villagerType, VillagerProfession villagerProfession,
                                      String villagerName, Style style) {
        if (player.isCreative()) {
            Vec3 pos = player.pick(5, 0, true).getLocation();
            Level level = player.level();
            Villager villager = new Villager(EntityType.VILLAGER, level);
            VillagerData villagerData = new VillagerData(villagerType, villagerProfession, 5);
            villager.setVillagerData(villagerData);
            villager.moveTo(pos.x, pos.y, pos.z);
            villager.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
            villager.setCustomName(Te.s(villagerName, style));
            villager.setCustomNameVisible(true);
            villager.setInvulnerable(true);
            level.addFreshEntity(villager);
        } else {
            MobKillEntrustment.sendMSG(player, Te.s("仅op使用"));
        }
    }
}

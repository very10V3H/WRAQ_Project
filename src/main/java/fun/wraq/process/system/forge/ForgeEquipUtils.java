package fun.wraq.process.system.forge;

import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.overworld.chapter7.C7Items;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ForgeEquipUtils {

    public record Zone(int boundaryX1, int boundaryZ1, int boundaryX2, int boundaryZ2) {
    }

    public static Map<Zone, List<ItemStack>> zoneForgeItemListMap = new HashMap<>();
    public static Map<Item, List<Component>> itemForgePlaceMap = new HashMap<>();

    public static final Zone PLAIN_VILLAGE = new Zone(828, 261, 650, 120);
    public static final Component PLAIN_VILLAGE_NAME = Te.s("平原村", CustomStyle.styleOfPlain);
    public static final Zone FOREST_VILLAGE = new Zone(1159, 103, 1010, -2);
    public static final Component FOREST_VILLAGE_NAME = Te.s("雨林村", CustomStyle.styleOfForest);
    public static final Zone LAKE_VILLAGE = new Zone(942, -375, 842, -458);
    public static final Component LAKE_VILLAGE_NAME = Te.s("海岸村", CustomStyle.styleOfLake);
    public static final Zone VOLCANO_VILLAGE = new Zone(2631, -476, 2512, -600);
    public static final Component VOLCANO_VILLAGE_NAME = Te.s("火山村", CustomStyle.styleOfVolcano);
    public static final Zone SNOW_VILLAGE = new Zone(1395, -1509, 1251, -1702);
    public static final Component SNOW_VILLAGE_NAME = Te.s("北洋村", CustomStyle.styleOfSnow);
    public static final Zone BIRCH_VILLAGE = new Zone(2021, 1767, 1814, 1592);
    public static final Component BIRCH_VILLAGE_NAME = Te.s("沙岸村", CustomStyle.styleOfSunIsland);
    public static final Zone SAKURA_VILLAGE = new Zone(2445, 1799, 2353, 1719);
    public static final Component SAKURA_VILLAGE_NAME = Te.s("绯樱村", CustomStyle.styleOfSakura);
    public static final Zone SKY_CITY = new Zone(1013, 64, 900, -42);
    public static final Component SKY_CITY_NAME = Te.s("天空城", CustomStyle.styleOfSky);
    public static final Zone XUNNAN_VILLAGE = new Zone(1268, -1024, 1066, -1132);
    public static final Component XUNNAN_VILLAGE_NAME = Te.s("薰楠村", CustomStyle.styleOfJacaranda);
    public static final Zone XUNXI_VILLAGE = new Zone(1093, -1241, 970, -1358);
    public static final Component XUNXI_VILLAGE_NAME = Te.s("薰曦村", CustomStyle.styleOfJacaranda);
    public static final Zone MOONTAIN_STRONG_HOLD = new Zone(1937, -898, 1889, -962);
    public static final Component MOONTAIN_STRONG_HOLD_NAME = Te.s("望山据点", CustomStyle.styleOfMoontain);
    public static final Zone DIVINE_ISLAND = new Zone(2715, 931, 1973, 209);
    public static final Component DIVINE_ISLAND_NAME = Te.s("圣光岛", CustomStyle.DIVINE_STYLE);
    public static final Zone NORTH_VILLAGE = new Zone(1789, 1922, 1659, 1803);
    public static final Component NORTH_VILLAGE_NAME = Te.s("北望村", CustomStyle.BUNKER_STYLE);

    public static final Map<Zone, Component> zoneNameMap = new HashMap<>() {{
        put(PLAIN_VILLAGE, PLAIN_VILLAGE_NAME);
        put(FOREST_VILLAGE, FOREST_VILLAGE_NAME);
        put(LAKE_VILLAGE, LAKE_VILLAGE_NAME);
        put(VOLCANO_VILLAGE, VOLCANO_VILLAGE_NAME);
        put(SNOW_VILLAGE, SNOW_VILLAGE_NAME);
        put(BIRCH_VILLAGE, BIRCH_VILLAGE_NAME);
        put(SAKURA_VILLAGE, SAKURA_VILLAGE_NAME);
        put(SKY_CITY, SKY_CITY_NAME);
        put(XUNNAN_VILLAGE, XUNNAN_VILLAGE_NAME);
        put(XUNXI_VILLAGE, XUNXI_VILLAGE_NAME);
        put(MOONTAIN_STRONG_HOLD, MOONTAIN_STRONG_HOLD_NAME);
        put(DIVINE_ISLAND, DIVINE_ISLAND_NAME);
        put(NORTH_VILLAGE, NORTH_VILLAGE_NAME);
    }};

    public static void setZoneForgeItemListMap() {
        List<Item> plain = List.of(
                ModItems.PLAIN_HELMET.get(),
                ModItems.PLAIN_CHEST.get(),
                ModItems.PLAIN_LEGGINGS.get(),
                ModItems.PLAIN_BOOTS.get(),
                ModItems.KAZE_SWORD_0.get(),
                ModItems.KAZE_BOOTS.get(),
                ModItems.PLAIN_SWORD_0.get(),
                ModItems.PLAIN_BOW_0.get(),
                ModItems.LIFE_SCEPTRE_0.get()
        );

        zoneForgeItemListMap.put(PLAIN_VILLAGE, new ArrayList<>() {{
            plain.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> forest = List.of(
                ModItems.FOREST_SWORD_0.get(),
                ModItems.FOREST_BOW_0.get(),
                ModItems.FOREST_HELMET.get(),
                ModItems.FOREST_CHEST.get(),
                ModItems.FOREST_LEGGINGS.get(),
                ModItems.FOREST_BOOTS.get(),
                ModItems.LAKE_SWORD_0.get(),
                ModItems.LAKE_BOW_0.get(),
                ModItems.LAKE_SCEPTRE_0.get(),
                ModItems.LAKE_HELMET.get(),
                ModItems.LAKE_CHEST.get(),
                ModItems.LAKE_LEGGINGS.get(),
                ModItems.LAKE_BOOTS.get(),
                ModItems.MINE_SWORD_0.get(),
                ModItems.MINE_BOW_0.get(),
                ModItems.MINE_HELMET.get(),
                ModItems.MINE_CHEST.get(),
                ModItems.MINE_LEGGINGS.get(),
                ModItems.MINE_BOOTS.get(),
                ModItems.VOLCANO_SWORD_0.get(),
                ModItems.VOLCANO_BOW_0.get(),
                ModItems.VOLCANO_HELMET.get(),
                ModItems.VOLCANO_CHEST.get(),
                ModItems.VOLCANO_LEGGINGS.get(),
                ModItems.VOLCANO_BOOTS.get()
        );
        zoneForgeItemListMap.put(FOREST_VILLAGE, new ArrayList<>() {{
            forest.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> lake = List.of(
                ModItems.LAKE_SWORD_0.get(),
                ModItems.LAKE_HELMET.get(),
                ModItems.LAKE_CHEST.get(),
                ModItems.LAKE_LEGGINGS.get(),
                ModItems.LAKE_BOOTS.get(),
                ModItems.SLIME_BOOTS.get()
        );
        zoneForgeItemListMap.put(LAKE_VILLAGE, new ArrayList<>() {{
            lake.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> volcano = List.of(
                ModItems.VOLCANO_SWORD_0.get(),
                ModItems.VOLCANO_BOW_0.get(),
                ModItems.VOLCANO_HELMET.get(),
                ModItems.VOLCANO_CHEST.get(),
                ModItems.VOLCANO_LEGGINGS.get(),
                ModItems.VOLCANO_BOOTS.get(),
                C7Items.BONE_IMP_KNIFE.get()
        );
        zoneForgeItemListMap.put(VOLCANO_VILLAGE, new ArrayList<>() {{
            volcano.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> snow = List.of(
                ModItems.SNOW_SWORD_0.get(),
                ModItems.SNOW_HELMET.get(),
                ModItems.SNOW_CHEST.get(),
                ModItems.SNOW_LEGGINGS.get(),
                ModItems.SNOW_BOOTS.get(),
                ModItems.ICE_SWORD.get(),
                ModItems.ICE_SWORD_E.get(),
                ModItems.ICE_BOW.get(),
                ModItems.ICE_BOW_E.get(),
                ModItems.ICE_SCEPTRE.get(),
                ModItems.ICE_SCEPTRE_E.get(),
                ModItems.ICE_HELMET.get(), ModItems.ICE_CHEST.get(),
                ModItems.ICE_LEGGINGS.get(), ModItems.ICE_BOOTS.get(),
                ModItems.CASTLE_SWORD.get(),
                ModItems.CASTLE_SWORD_E.get(),
                ModItems.CASTLE_BOW.get(),
                ModItems.CASTLE_BOW_E.get(),
                ModItems.CASTLE_SCEPTRE.get(),
                ModItems.CASTLE_SCEPTRE_E.get(),
                ModItems.CASTLE_ATTACK_HELMET.get(), ModItems.CASTLE_ATTACK_CHEST.get(), ModItems.CASTLE_ATTACK_LEGGINGS.get(), ModItems.CASTLE_ATTACK_BOOTS.get(),
                ModItems.CASTLE_SWIFT_HELMET.get(), ModItems.CASTLE_SWIFT_CHEST.get(), ModItems.CASTLE_SWIFT_LEGGINGS.get(), ModItems.CASTLE_SWIFT_BOOTS.get(),
                ModItems.CASTLE_MANA_HELMET.get(), ModItems.CASTLE_MANA_CHEST.get(), ModItems.CASTLE_MANA_LEGGINGS.get(), ModItems.CASTLE_MANA_BOOTS.get()
        );
        zoneForgeItemListMap.put(SNOW_VILLAGE, new ArrayList<>() {{
            snow.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> birch = List.of(
                ModItems.SEA_SWORD_0.get(),
                ModItems.HUSK_SWORD_0.get(),
                ModItems.SEA_BOW.get(),
                ModItems.SHIP_SWORD.get(),
                ModItems.SHIP_BOW.get(),
                ModItems.SHIP_SCEPTRE.get(),
                ModItems.LIGHTNING_HELMET.get(),
                ModItems.LIGHTNING_CHEST.get(),
                ModItems.LIGHTNING_LEGGINGS.get(),
                ModItems.LIGHTNING_BOOTS.get(),
                HarbingerItems.HARBINGER_SWORD.get(),
                HarbingerItems.HARBINGER_BOW.get(),
                HarbingerItems.HARBINGER_SCEPTRE.get()
        );
        zoneForgeItemListMap.put(BIRCH_VILLAGE, new ArrayList<>() {{
            birch.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> sakura = List.of(
                ModItems.SAKURA_SWORD.get(),
                ModItems.SAKURA_BOW.get(),
                ModItems.GOLDEN_SHIELD.get(),
                ModItems.GOLDEN_KNIFE.get(),
                ModItems.MANA_SHIELD.get(),
                ModItems.MANA_KNIFE.get(),
                ModItems.EARTH_MANA_HELMET.get(),
                ModItems.EARTH_MANA_CHEST.get(),
                ModItems.EARTH_MANA_LEGGINGS.get(),
                ModItems.EARTH_MANA_BOOTS.get(),
                ModItems.BLOOD_MANA_HELMET.get(),
                ModItems.BLOOD_MANA_CHEST.get(),
                ModItems.BLOOD_MANA_LEGGINGS.get(),
                ModItems.BLOOD_MANA_BOOTS.get(),
                ModItems.DEVIL_ATTACK_CHEST.get(),
                ModItems.DEVIL_SWIFT_BOOTS.get(),
                ModItems.DEVIL_MANA_HELMET.get(),
                ModItems.TABOO_ATTACK_LEGGINGS.get(),
                ModItems.TABOO_SWIFT_HELMET.get(),
                ModItems.TABOO_MANA_BOOTS.get()
        );
        zoneForgeItemListMap.put(SAKURA_VILLAGE, new ArrayList<>() {{
            sakura.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> sky = List.of(
                ModItems.SKY_BOW.get(),
                ModItems.SKY_SWORD.get(),
                ModItems.SKY_HELMET.get(),
                ModItems.SKY_CHEST.get(),
                ModItems.SKY_LEGGINGS.get(),
                ModItems.SKY_BOOTS.get(),
                ModItems.NETHER_SHIELD.get(),
                ModItems.WITHER_SWORD_0.get(),
                ModItems.PIGLIN_HELMET_0.get(),
                ModItems.WITHER_BOW_0.get(),
                ModItems.MAGMA_SCEPTRE_0.get(),
                ModItems.NETHER_SWORD.get(),
                ModItems.NETHER_SWORD_E.get(),
                ModItems.NETHER_SCEPTRE.get(),
                ModItems.NETHER_SCEPTRE_E.get(),
                ModItems.NETHER_KNIFE.get(),
                ModItems.NETHER_KNIFE_E.get(),
                ModItems.NETHER_HELMET.get(),
                ModItems.NETHER_CHEST.get(),
                ModItems.NETHER_LEGGINGS.get(),
                ModItems.NETHER_BOOTS.get(),
                ModItems.NETHER_MANA_HELMET.get(),
                ModItems.NETHER_MANA_CHEST.get(),
                ModItems.NETHER_MANA_LEGGINGS.get(),
                ModItems.NETHER_MANA_BOOTS.get(),
                ModItems.STAR_BOTTLE.get(),
                C7Items.VD_SWORD.get(),
                C7Items.VD_BOW.get(),
                C7Items.VD_SCEPTRE.get()
        );
        zoneForgeItemListMap.put(SKY_CITY, new ArrayList<>() {{
            sky.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> xunNan = List.of(
                ModItems.MOON_SWORD.get(),
                ModItems.MOON_SWORD_E.get(),
                ModItems.MOON_BOW.get(),
                ModItems.MOON_BOW_E.get(),
                ModItems.MOON_SCEPTRE.get(),
                ModItems.MOON_SCEPTRE_E.get(),
                ModItems.MOON_SHIELD.get(),
                ModItems.MOON_KNIFE.get(),
                ModItems.MOON_BOOK.get(),
                ModItems.MOON_BELT.get(),
                ModItems.MOON_HELMET.get(),
                ModItems.MOON_LEGGINGS.get()
        );
        zoneForgeItemListMap.put(XUNNAN_VILLAGE, new ArrayList<>() {{
            xunNan.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> xunXi = List.of(
                ModItems.PURPLE_IRON_HELMET.get(),
                ModItems.PURPLE_IRON_CHEST.get(),
                ModItems.PURPLE_IRON_LEGGINGS.get(),
                ModItems.PURPLE_IRON_BOOTS.get()
        );
        zoneForgeItemListMap.put(XUNXI_VILLAGE, new ArrayList<>() {{
            xunXi.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> moontain_strong_hold = List.of(
                WardenItems.WARDEN_SHIELD.get(),
                WardenItems.WARDEN_KNIFE.get(),
                WardenItems.WARDEN_BOOK.get(),
                WardenItems.DARK_MOON_SHIELD.get(),
                WardenItems.DARK_MOON_KNIFE.get(),
                WardenItems.DARK_MOON_BOOK.get()
        );
        zoneForgeItemListMap.put(MOONTAIN_STRONG_HOLD, new ArrayList<>() {{
            moontain_strong_hold.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> divineIsland = List.of(
                DivineIslandItems.DIVINE_SWORD_0.get(),
                DivineIslandItems.DIVINE_BOW_0.get(),
                DivineIslandItems.DIVINE_SCEPTRE_0.get(),
                DivineIslandItems.DIVINE_HELMET_0.get(),
                DivineIslandItems.DIVINE_CHEST_0.get(),
                DivineIslandItems.DIVINE_LEGGINGS_0.get(),
                DivineIslandItems.DIVINE_BOOTS_0.get(),
                DivineIslandItems.DIVINE_SWORD_1.get(),
                DivineIslandItems.DIVINE_BOW_1.get(),
                DivineIslandItems.DIVINE_SCEPTRE_1.get(),
                DivineIslandItems.DIVINE_HELMET_1.get(),
                DivineIslandItems.DIVINE_CHEST_1.get(),
                DivineIslandItems.DIVINE_LEGGINGS_1.get(),
                DivineIslandItems.DIVINE_BOOTS_1.get()
        );
        zoneForgeItemListMap.put(DIVINE_ISLAND, new ArrayList<>() {{
            divineIsland.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> northVillage = List.of(
                BunkerItems.BUNKER_SWORD.get(),
                BunkerItems.BUNKER_BOW.get(),
                BunkerItems.BUNKER_SCEPTRE.get(),
                BunkerItems.BUNKER_SHIELD.get(),
                BunkerItems.BUNKER_KNIFE.get(),
                BunkerItems.BUNKER_BOOK.get(),
                BunkerItems.BUNKER_CURIO_1.get(),
                BunkerItems.BUNKER_HELMET_2.get(),
                BunkerItems.BUNKER_CHEST_2.get(),
                BunkerItems.BUNKER_LEGGINGS_2.get(),
                BunkerItems.BUNKER_BOOTS_2.get()
        );
        zoneForgeItemListMap.put(NORTH_VILLAGE, new ArrayList<>() {{
            northVillage.forEach(item -> add(item.getDefaultInstance()));
        }});

        zoneForgeItemListMap.forEach((zone, itemList) -> {
            Component zoneName = zoneNameMap.get(zone);
            itemList.forEach(stack -> {
                Item item = stack.getItem();
                if (!itemForgePlaceMap.containsKey(item)) {
                    itemForgePlaceMap.put(item, new ArrayList<>());
                }
                itemForgePlaceMap.get(item).add(zoneName);
            });
        });
    }

    public static List<ItemStack> getPlayerInZoneItemList(Player player) {
        List<ItemStack> itemList = new ArrayList<>();
        zoneForgeItemListMap.forEach((zone, list) -> {
            if (player.getX() < zone.boundaryX1 && player.getX() > zone.boundaryX2
                    && player.getZ() < zone.boundaryZ1 && player.getZ() > zone.boundaryZ2) {
                itemList.addAll(list);
            }
        });
        return itemList;
    }

    public static String itemTag = "forgeQuality";

    public static void setForgeQualityOnEquip(ItemStack equip, int tier) {
        equip.getOrCreateTagElement(Utils.MOD_ID).putInt(itemTag, tier);
    }

    public static int getForgeQualityOnEquip(ItemStack equip) {
        if (equip.getTagElement(Utils.MOD_ID) != null) {
            if (equip.getTagElement(Utils.MOD_ID).contains(itemTag))
                return Math.min(13, equip.getTagElement(Utils.MOD_ID).getInt(itemTag));
        }
        return -1;
    }

    public record TierValueAndDescription(double value, String description, Style style) {
    }

    public static Map<Integer, TierValueAndDescription> tierValueAndDescriptionMap = new HashMap<>() {{
        put(0, new TierValueAndDescription(0.8, "粗糙", Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
        put(1, new TierValueAndDescription(1, "优秀", Style.EMPTY.applyFormat(ChatFormatting.GREEN)));
        put(2, new TierValueAndDescription(1.1, "精良", Style.EMPTY.applyFormat(ChatFormatting.AQUA)));
        put(3, new TierValueAndDescription(1.2, "史诗", Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE)));
        put(4, new TierValueAndDescription(1.3, "传说", Style.EMPTY.applyFormat(ChatFormatting.GOLD)));
        put(5, new TierValueAndDescription(1.4, "神话", Style.EMPTY.applyFormat(ChatFormatting.RED)));
        put(6, new TierValueAndDescription(1.5, "终末", CustomStyle.styleOfEnd));
        put(7, new TierValueAndDescription(1.65, "思旧", CustomStyle.styleOfMoon));
        put(8, new TierValueAndDescription(1.8, "追忆", CustomStyle.styleOfMoon1));
        put(9, new TierValueAndDescription(2, "不可思议", CustomStyle.styleOfPower));
        put(10, new TierValueAndDescription(2.25, "望尘莫及", CustomStyle.styleOfCastleCrystal));
        put(11, new TierValueAndDescription(2.5, "仅存于梦", CustomStyle.styleOfSakura));
        put(12, new TierValueAndDescription(2.75, "绝无仅有", CustomStyle.styleOfYSR));
        put(13, new TierValueAndDescription(3d, "巅峰之作", CustomStyle.styleOfSky));
    }};

    public static List<Component> getTierAndValueDescription() {
        List<Component> description = new ArrayList<>();
        description.add(Te.s("锻造品质与对应属性:", CustomStyle.styleOfGold));
        for (int i = 0; i < ForgeEquipUtils.tierValueAndDescriptionMap.size(); i++) {
            ForgeEquipUtils.TierValueAndDescription obj = ForgeEquipUtils.tierValueAndDescriptionMap.get(i);
            description.add(Te.s(obj.description(), obj.style(), " - ", obj.style(),
                    String.valueOf(obj.value()), obj.style()));
        }
        return description;
    }

    public static double getTierValueEffect(int tier) {
        return tierValueAndDescriptionMap.getOrDefault(tier, new TierValueAndDescription(1, "无名", CustomStyle.styleOfMoontain)).value;
    }

    public static double getTierValueEffect(ItemStack equip) {
        return getTierValueEffect(getForgeQualityOnEquip(equip));
    }

    public static Component getDescription(int tier) {
        TierValueAndDescription tierValueAndDescription = tierValueAndDescriptionMap.getOrDefault(tier,
                new TierValueAndDescription(1, "无名", CustomStyle.styleOfMoontain));
        return Te.m(tierValueAndDescription.description, tierValueAndDescription.style);
    }

    public static Style getStyle(int tier) {
        return tierValueAndDescriptionMap.getOrDefault(tier, new TierValueAndDescription(1, "无名", CustomStyle.styleOfMoontain)).style;
    }

    public static Map<Integer, List<Double>> successForgeRate = new HashMap<>() {{
        put(0, List.of(0.25, 0.25, 0.25, 0.10, 0.10, 0.05));
        put(1, List.of(0.15, 0.20, 0.25, 0.25, 0.10, 0.05));
        put(2, List.of(0.10, 0.15, 0.25, 0.30, 0.15, 0.05));
        put(3, List.of(0.05, 0.15, 0.20, 0.35, 0.20, 0.05));
        put(4, List.of(0.00, 0.00, 0.20, 0.45, 0.25, 0.05, 0.03, 0.02));
        put(5, List.of(0.00, 0.00, 0.00, 0.35, 0.45, 0.10, 0.05, 0.03, 0.02));
        put(6, List.of(0.00, 0.00, 0.00, 0.00, 0.15, 0.45, 0.3, 0.05, 0.03, 0.02));
        put(7, List.of(0.00, 0.00, 0.00, 0.00, 0.00, 0.3, 0.45, 0.1, 0.1, 0.03, 0.02));
        put(8, List.of(0.00, 0.00, 0.00, 0.00, 0.00, 0.0, 0.6, 0.2, 0.1, 0.05, 0.03, 0.02));
    }};

    public static int getOneTimeForgeTier(int hammerTier) {
        double num = new Random().nextDouble();
        List<Double> rateList = successForgeRate.get(hammerTier);
        for (int i = 0; i < rateList.size(); i++) {
            num -= rateList.get(i);
            if (num <= 0) return i;
        }
        return 5;
    }

    public static double getTraditionalEquipBaseValue(ItemStack equip, Map<Item, Double> map,
                                                      @Nullable Player player, boolean computeTier) {
        double exValue = 0;
        if (player != null
                && Utils.levelRequire.getOrDefault(equip.getItem(), 0) > player.experienceLevel) return 0;
        if (equip.getItem() instanceof ExBaseAttributeValueEquip exBaseAttributeValueEquip
                && exBaseAttributeValueEquip.getTagAndRateMap().containsKey(map)) {
            CompoundTag data = ExBaseAttributeValueEquip.getStackExBaseAttributeData(equip);
            exValue += exBaseAttributeValueEquip.getTagAndRateMap().get(map).getValueByData(data);
        }
        return (map.getOrDefault(equip.getItem(), 0d) + exValue)
                * (computeTier ? getTierValueEffect(equip) : 1);
    }

    public static double getTraditionalEquipBaseValue(ItemStack equip, Map<Item, Double> map, @Nullable Player player) {
        return getTraditionalEquipBaseValue(equip, map, player, false);
    }

    public static double getTraditionalEquipBaseValue(ItemStack equip, Map<Item, Double> map) {
        return getTraditionalEquipBaseValue(equip, map, null, true);
    }

    public static double getRandomEquipBaseValue(ItemStack equip, String type) {
        double attribute = equip.getOrCreateTagElement(Utils.MOD_ID).getDouble(type);
        return attribute * getTierValueEffect(equip);
    }

    public static double getRandomEquipBaseValue(Player player, ItemStack equip, String type) {
        if (equip.getItem() instanceof RandomLootEquip randomLootEquip) {
            if (player.experienceLevel < randomLootEquip.levelRequire()) return 0;
        }
        return getRandomEquipBaseValue(equip, type);
    }

    public static Item getEquipPiece(int tier) {
        List<Item> items = getEquipPieceList();
        return items.get(tier);
    }

    public static int getEquipPieceTier(Item item) {
        List<Item> items = getEquipPieceList();
        return items.indexOf(item);
    }

    public static List<Item> getEquipPieceList() {
        return List.of(ModItems.EQUIP_PIECE_0.get(),
                ModItems.EQUIP_PIECE_1.get(), ModItems.EQUIP_PIECE_2.get(), ModItems.EQUIP_PIECE_3.get(), ModItems.EQUIP_PIECE_4.get(),
                ModItems.EQUIP_PIECE_5.get(), ModItems.EQUIP_PIECE_6.get(), ModItems.EQUIP_PIECE_7.get(), ModItems.EQUIP_PIECE_8.get(),
                ModItems.EQUIP_PIECE_9.get(), ModItems.EQUIP_PIECE_10.get(), ModItems.EQUIP_PIECE_11.get(), ModItems.EQUIP_PIECE_12.get(),
                ModItems.EQUIP_PIECE_13.get());
    }

    public static boolean itemContainForgeQuality(ItemStack itemStack) {
        if (itemStack.getTagElement(Utils.MOD_ID) != null)
            return itemStack.getTagElement(Utils.MOD_ID).contains(itemTag);
        return false;
    }

    public static int getForgeLevel(ItemStack stack) {
        if (stack.getTagElement(Utils.MOD_ID) != null) {
            return stack.getOrCreateTagElement(Utils.MOD_ID).getInt("Forging");
        }
        return 0;
    }

    public static void setForgeLevel(ItemStack stack, int forgeLevel) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putInt("Forging", forgeLevel);
    }
}

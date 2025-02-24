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
    }};

    public static void setZoneForgeItemListMap() {
        List<Item> plain = List.of(
                ModItems.PlainArmorHelmet.get(),
                ModItems.PlainArmorChest.get(),
                ModItems.PlainArmorLeggings.get(),
                ModItems.PlainArmorBoots.get(),
                ModItems.KazeSword0.get(),
                ModItems.KazeBoots.get(),
                ModItems.PlainSword0.get(),
                ModItems.PlainBow0.get(),
                ModItems.LIFE_SCEPTRE_0.get()
        );

        zoneForgeItemListMap.put(PLAIN_VILLAGE, new ArrayList<>() {{
            plain.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> forest = List.of(
                ModItems.ForestSword0.get(),
                ModItems.ForestBow0.get(),
                ModItems.ForestArmorHelmet.get(),
                ModItems.ForestArmorChest.get(),
                ModItems.ForestArmorLeggings.get(),
                ModItems.ForestArmorBoots.get(),
                ModItems.LakeSword0.get(),
                ModItems.lakeBow0.get(),
                ModItems.lakeSceptre0.get(),
                ModItems.LakeArmorHelmet.get(),
                ModItems.LakeArmorChest.get(),
                ModItems.LakeArmorLeggings.get(),
                ModItems.LakeArmorBoots.get(),
                ModItems.MineSword0.get(),
                ModItems.MineBow0.get(),
                ModItems.MineArmorHelmet.get(),
                ModItems.MineArmorChest.get(),
                ModItems.MineArmorLeggings.get(),
                ModItems.MineArmorBoots.get(),
                ModItems.VolcanoSword0.get(),
                ModItems.VolcanoBow0.get(),
                ModItems.VolcanoArmorHelmet.get(),
                ModItems.VolcanoArmorChest.get(),
                ModItems.VolcanoArmorLeggings.get(),
                ModItems.VolcanoArmorBoots.get()
        );
        zoneForgeItemListMap.put(FOREST_VILLAGE, new ArrayList<>() {{
            forest.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> lake = List.of(
                ModItems.LakeSword0.get(),
                ModItems.LakeArmorHelmet.get(),
                ModItems.LakeArmorChest.get(),
                ModItems.LakeArmorLeggings.get(),
                ModItems.LakeArmorBoots.get(),
                ModItems.SlimeBoots.get()
        );
        zoneForgeItemListMap.put(LAKE_VILLAGE, new ArrayList<>() {{
            lake.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> volcano = List.of(
                ModItems.VolcanoSword0.get(),
                ModItems.VolcanoBow0.get(),
                ModItems.VolcanoArmorHelmet.get(),
                ModItems.VolcanoArmorChest.get(),
                ModItems.VolcanoArmorLeggings.get(),
                ModItems.VolcanoArmorBoots.get(),
                C7Items.boneImpKnife.get()
        );
        zoneForgeItemListMap.put(VOLCANO_VILLAGE, new ArrayList<>() {{
            volcano.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> snow = List.of(
                ModItems.SnowSword0.get(),
                ModItems.SnowArmorHelmet.get(),
                ModItems.SnowArmorChest.get(),
                ModItems.SnowArmorLeggings.get(),
                ModItems.SnowArmorBoots.get(),
                ModItems.ICE_SWORD.get(),
                ModItems.ICE_SWORD_E.get(),
                ModItems.ICE_BOW.get(),
                ModItems.ICE_BOW_E.get(),
                ModItems.ICE_SCEPTRE.get(),
                ModItems.ICE_SCEPTRE_E.get(),
                ModItems.IceArmorHelmet.get(), ModItems.IceArmorChest.get(),
                ModItems.IceArmorLeggings.get(), ModItems.IceArmorBoots.get(),
                ModItems.CASTLE_SWORD.get(),
                ModItems.CASTLE_SWORD_E.get(),
                ModItems.CASTLE_BOW.get(),
                ModItems.CASTLE_BOW_E.get(),
                ModItems.CASTLE_SCEPTRE.get(),
                ModItems.CASTLE_SCEPTRE_E.get(),
                ModItems.CastleAttackHelmet.get(), ModItems.CastleAttackChest.get(), ModItems.CastleAttackLeggings.get(), ModItems.CastleAttackBoots.get(),
                ModItems.CastleSwiftHelmet.get(), ModItems.CastleSwiftChest.get(), ModItems.CastleSwiftLeggings.get(), ModItems.CastleSwiftBoots.get(),
                ModItems.CastleManaHelmet.get(), ModItems.CastleManaChest.get(), ModItems.CastleManaLeggings.get(), ModItems.CastleManaBoots.get()
        );
        zoneForgeItemListMap.put(SNOW_VILLAGE, new ArrayList<>() {{
            snow.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> birch = List.of(
                ModItems.SeaSword0.get(),
                ModItems.huskSword0.get(),
                ModItems.SeaBow.get(),
                ModItems.ShipSword.get(),
                ModItems.ShipBow.get(),
                ModItems.ShipSceptre.get(),
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
                ModItems.SakuraDemonSword.get(),
                ModItems.SakuraBow.get(),
                ModItems.goldenShield.get(),
                ModItems.goldenKnife.get(),
                ModItems.ManaShield.get(),
                ModItems.manaKnife.get(),
                ModItems.EarthManaArmorHelmet.get(),
                ModItems.EarthManaArmorChest.get(),
                ModItems.EarthManaArmorLeggings.get(),
                ModItems.EarthManaArmorBoots.get(),
                ModItems.BloodManaArmorHelmet.get(),
                ModItems.BloodManaArmorChest.get(),
                ModItems.BloodManaArmorLeggings.get(),
                ModItems.BloodManaArmorBoots.get(),
                ModItems.DevilAttackChest.get(),
                ModItems.DevilSwiftBoots.get(),
                ModItems.DevilManaHelmet.get(),
                ModItems.TabooAttackLeggings.get(),
                ModItems.TabooSwiftHelmet.get(),
                ModItems.TabooManaBoots.get()
        );
        zoneForgeItemListMap.put(SAKURA_VILLAGE, new ArrayList<>() {{
            sakura.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> sky = List.of(
                ModItems.SkyBow.get(),
                ModItems.SKY_SWORD.get(),
                ModItems.SKY_ARMOR_HELMET.get(),
                ModItems.SKY_ARMOR_CHEST.get(),
                ModItems.SKY_ARMOR_LEGGINGS.get(),
                ModItems.SKY_ARMOR_BOOTS.get(),
                ModItems.NetherShield.get(),
                ModItems.WitherSword0.get(),
                ModItems.PiglinHelmet0.get(),
                ModItems.WitherBow0.get(),
                ModItems.MagmaSceptre0.get(),
                ModItems.NETHER_SWORD.get(),
                ModItems.NETHER_SWORD_E.get(),
                ModItems.NETHER_SCEPTRE.get(),
                ModItems.NETHER_SCEPTRE_E.get(),
                ModItems.NETHER_KNIFE.get(),
                ModItems.NETHER_KNIFE_E.get(),
                ModItems.NETHER_ARMOR_HELMET.get(),
                ModItems.NETHER_ARMOR_CHEST.get(),
                ModItems.NETHER_ARMOR_LEGGINGS.get(),
                ModItems.NETHER_ARMOR_BOOTS.get(),
                ModItems.NetherManaArmorHelmet.get(),
                ModItems.NetherManaArmorChest.get(),
                ModItems.NetherManaArmorLeggings.get(),
                ModItems.NetherManaArmorBoots.get(),
                ModItems.StarBottle.get(),
                C7Items.vdSword.get(),
                C7Items.vdBow.get(),
                C7Items.vdSceptre.get()
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
                ModItems.MoonShield.get(),
                ModItems.MoonKnife.get(),
                ModItems.MoonBook.get(),
                ModItems.MoonBelt.get(),
                ModItems.MoonHelmet.get(),
                ModItems.MoonLeggings.get()
        );

        zoneForgeItemListMap.put(XUNNAN_VILLAGE, new ArrayList<>() {{
            xunNan.forEach(item -> add(item.getDefaultInstance()));
        }});

        List<Item> xunXi = List.of(
                ModItems.PurpleIronArmorHelmet.get(),
                ModItems.PurpleIronArmorChest.get(),
                ModItems.PurpleIronArmorLeggings.get(),
                ModItems.PurpleIronArmorBoots.get()
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
                DivineIslandItems.DIVINE_BOOTS_0.get()
        );

        zoneForgeItemListMap.put(DIVINE_ISLAND, new ArrayList<>() {{
            divineIsland.forEach(item -> add(item.getDefaultInstance()));
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

    public static double getTraditionalEquipBaseValue(ItemStack equip, Map<Item, Double> map, @Nullable Player player, boolean computeForge) {
        double exValue = 0;
        if (player != null
                && Utils.levelRequire.getOrDefault(equip.getItem(), 0) > player.experienceLevel) return 0;
        if (equip.getItem() instanceof ExBaseAttributeValueEquip exBaseAttributeValueEquip
                && exBaseAttributeValueEquip.getTagAndRateMap().containsKey(map)) {
            CompoundTag data = ExBaseAttributeValueEquip.getStackExBaseAttributeData(equip);
            exValue += exBaseAttributeValueEquip.getTagAndRateMap().get(map).getValueByData(data);
        }
        return (map.getOrDefault(equip.getItem(), 0d) + exValue) * (computeForge ? getTierValueEffect(equip) : 1);
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
        return List.of(ModItems.equipPiece0.get(),
                ModItems.equipPiece1.get(), ModItems.equipPiece2.get(), ModItems.equipPiece3.get(), ModItems.equipPiece4.get(),
                ModItems.equipPiece5.get(), ModItems.equipPiece6.get(), ModItems.equipPiece7.get(), ModItems.equipPiece8.get(),
                ModItems.equipPiece9.get(), ModItems.equipPiece10.get(), ModItems.equipPiece11.get(), ModItems.equipPiece12.get(),
                ModItems.equipPiece13.get());
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

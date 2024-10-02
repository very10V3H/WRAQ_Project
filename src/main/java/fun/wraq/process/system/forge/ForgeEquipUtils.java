package fun.wraq.process.system.forge;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter7.C7Items;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class ForgeEquipUtils {

    public record Zone(int boundaryX1, int boundaryZ1, int boundaryX2, int boundaryZ2) {
    }

    public static Map<Zone, List<ItemStack>> zoneForgeItemListMap = new HashMap<>();
    public static Map<Item, Component> itemZoneMap = new HashMap<>();

    public static void setZoneForgeItemListMap() {
        List<Item> plain = List.of(
                ModItems.PlainSword0.get(),
                ModItems.PlainBow0.get(),
                ModItems.PlainSceptre0.get(),
                ModItems.PlainArmorHelmet.get(),
                ModItems.PlainArmorChest.get(),
                ModItems.PlainArmorLeggings.get(),
                ModItems.PlainArmorBoots.get(),
                ModItems.KazeSword0.get(),
                ModItems.KazeManaCore.get(),
                ModItems.KazeBoots.get()
        );

        zoneForgeItemListMap.put(new Zone(828, 261, 650, 120), new ArrayList<>() {{
            plain.forEach(item -> add(item.getDefaultInstance()));
        }});
        plain.forEach(item -> {
            itemZoneMap.put(item, Component.literal("平原村").withStyle(CustomStyle.styleOfPlain));
        });

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
                ModItems.MineArmorBoots.get()
        );
        zoneForgeItemListMap.put(new Zone(1159, 103, 1010, -2), new ArrayList<>() {{
            forest.forEach(item -> add(item.getDefaultInstance()));
        }});
        forest.forEach(item -> {
            itemZoneMap.put(item, Component.literal("雨林村").withStyle(CustomStyle.styleOfForest));
        });

        List<Item> lake = List.of(
                ModItems.LakeSword0.get(),
                ModItems.LakeArmorHelmet.get(),
                ModItems.LakeArmorChest.get(),
                ModItems.LakeArmorLeggings.get(),
                ModItems.LakeArmorBoots.get(),
                ModItems.SlimeBoots.get()
        );
        zoneForgeItemListMap.put(new Zone(942, -375, 842, -458), new ArrayList<>() {{
            lake.forEach(item -> add(item.getDefaultInstance()));
        }});
        lake.forEach(item -> {
            itemZoneMap.put(item, Component.literal("海岸村").withStyle(CustomStyle.styleOfLake));
        });

        List<Item> volcano = List.of(
                ModItems.VolcanoSword0.get(),
                ModItems.VolcanoBow0.get(),
                ModItems.VolcanoArmorHelmet.get(),
                ModItems.VolcanoArmorChest.get(),
                ModItems.VolcanoArmorLeggings.get(),
                ModItems.VolcanoArmorBoots.get(),
                C7Items.boneImpKnife.get()
        );
        zoneForgeItemListMap.put(new Zone(2631, -476, 2512, -600), new ArrayList<>() {{
            volcano.forEach(item -> add(item.getDefaultInstance()));
        }});
        volcano.forEach(item -> {
            itemZoneMap.put(item, Component.literal("火山村").withStyle(CustomStyle.styleOfVolcano));
        });

        List<Item> snow = List.of(
                ModItems.SnowSword0.get(),
                ModItems.SnowArmorHelmet.get(),
                ModItems.SnowArmorChest.get(),
                ModItems.SnowArmorLeggings.get(),
                ModItems.SnowArmorBoots.get(),
                ModItems.IceSword.get(),
                ModItems.IceBow.get(),
                ModItems.IceSceptre.get(),
                ModItems.IceArmorHelmet.get(), ModItems.IceArmorChest.get(), ModItems.IceArmorLeggings.get(), ModItems.IceArmorBoots.get(),
                ModItems.CastleSword.get(), ModItems.CastleBow.get(), ModItems.CastleSceptre.get(),
                ModItems.CastleAttackHelmet.get(), ModItems.CastleAttackChest.get(), ModItems.CastleAttackLeggings.get(), ModItems.CastleAttackBoots.get(),
                ModItems.CastleSwiftHelmet.get(), ModItems.CastleSwiftChest.get(), ModItems.CastleSwiftLeggings.get(), ModItems.CastleSwiftBoots.get(),
                ModItems.CastleManaHelmet.get(), ModItems.CastleManaChest.get(), ModItems.CastleManaLeggings.get(), ModItems.CastleManaBoots.get()
        );
        zoneForgeItemListMap.put(new Zone(1395, -1509, 1251, -1702), new ArrayList<>() {{
            snow.forEach(item -> add(item.getDefaultInstance()));
        }});
        snow.forEach(item -> {
            itemZoneMap.put(item, Component.literal("北冰村").withStyle(CustomStyle.styleOfSnow));
        });

        List<Item> birch = List.of(
                ModItems.SeaSword0.get(),
                ModItems.huskSword0.get(),
                ModItems.SeaManaCore.get(),
                ModItems.BlackForestManaCore.get(),
                ModItems.SeaBow.get(),
                ModItems.ShipSword.get(),
                ModItems.ShipBow.get(),
                ModItems.ShipSceptre.get(),
                ModItems.LIGHTNING_HELMET.get(),
                ModItems.LIGHTNING_CHEST.get(),
                ModItems.LIGHTNING_LEGGINGS.get(),
                ModItems.LIGHTNING_BOOTS.get()
        );
        zoneForgeItemListMap.put(new Zone(2021, 1767, 1814, 1592), new ArrayList<>() {{
            birch.forEach(item -> add(item.getDefaultInstance()));
        }});
        birch.forEach(item -> {
            itemZoneMap.put(item, Component.literal("沙岸村").withStyle(CustomStyle.styleOfHusk));
        });

        List<Item> sakura = List.of(
                ModItems.SakuraDemonSword.get(),
                ModItems.SakuraBow.get(),
                ModItems.SakuraCore.get(),
                ModItems.SakuraArmorHelmet.get(),
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
        zoneForgeItemListMap.put(new Zone(2445, 1799, 2353, 1719), new ArrayList<>() {{
            sakura.forEach(item -> add(item.getDefaultInstance()));
        }});
        sakura.forEach(item -> {
            itemZoneMap.put(item, Component.literal("绯樱村").withStyle(CustomStyle.styleOfSakura));
        });

        List<Item> sky = List.of(
                ModItems.SkyBow.get(),
                ModItems.SkyArmorHelmet.get(),
                ModItems.SkyArmorChest.get(),
                ModItems.SkyArmorLeggings.get(),
                ModItems.SkyArmorBoots.get(),
                ModItems.NetherShield.get(),
                ModItems.WitherSword0.get(),
                ModItems.PiglinHelmet0.get(),
                ModItems.WitherBow0.get(),
                ModItems.MagmaSceptre0.get(),
                ModItems.NetherSceptre.get(),
                ModItems.NetherArmorHelmet.get(),
                ModItems.NetherArmorChest.get(),
                ModItems.NetherArmorLeggings.get(),
                ModItems.NetherArmorBoots.get(),
                ModItems.NetherManaArmorHelmet.get(),
                ModItems.NetherManaArmorChest.get(),
                ModItems.NetherManaArmorLeggings.get(),
                ModItems.NetherManaArmorBoots.get(),
                ModItems.EndCurios.get(),
                ModItems.EndCurios1.get(),
                ModItems.StarBottle.get(),
                C7Items.vdSword.get(),
                C7Items.vdBow.get(),
                C7Items.vdSceptre.get()
        );

        zoneForgeItemListMap.put(new Zone(1013, 64, 900, -42), new ArrayList<>() {{
            sky.forEach(item -> add(item.getDefaultInstance()));
        }});
        sky.forEach(item -> {
            itemZoneMap.put(item, Component.literal("天空城").withStyle(CustomStyle.styleOfSky));
        });

        List<Item> xunNan = List.of(
                ModItems.PurpleIronArmorHelmet.get(),
                ModItems.PurpleIronArmorHelmet.get(),
                ModItems.MoonSword.get(),
                ModItems.MoonBow.get(),
                ModItems.MoonSceptre.get(),
                ModItems.MoonShield.get(),
                ModItems.MoonKnife.get(),
                ModItems.MoonBook.get(),
                ModItems.MoonBelt.get(),
                ModItems.MoonHelmet.get(),
                ModItems.MoonLeggings.get()
        );

        zoneForgeItemListMap.put(new Zone(1268, -1024, 1066, -1132), new ArrayList<>() {{
            xunNan.forEach(item -> add(item.getDefaultInstance()));
        }});
        xunNan.forEach(item -> {
            itemZoneMap.put(item, Component.literal("薰楠村").withStyle(CustomStyle.styleOfJacaranda));
        });

        List<Item> xunXi = List.of(
                ModItems.PurpleIronArmorHelmet.get(),
                ModItems.PurpleIronArmorChest.get(),
                ModItems.PurpleIronArmorLeggings.get(),
                ModItems.PurpleIronArmorBoots.get()
        );

        zoneForgeItemListMap.put(new Zone(1093, -1241, 970, -1358), new ArrayList<>() {{
            xunXi.forEach(item -> add(item.getDefaultInstance()));
        }});
        xunXi.forEach(item -> {
            itemZoneMap.put(item, Component.literal("薰曦村").withStyle(CustomStyle.styleOfJacaranda));
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

    public record TierValueAndDescription(double value, String description, Style style) {}

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
        put(4, List.of(0.05, 0.10, 0.15, 0.30, 0.30, 0.05, 0.03, 0.02));
        put(5, List.of(0.05, 0.10, 0.10, 0.25, 0.30, 0.10, 0.05, 0.03, 0.02));
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

    public static double getTraditionalEquipBaseValue(ItemStack equip, Map<Item, Double> map) {
        double exValue = 0;
        if (equip.getItem() instanceof ExBaseAttributeValueEquip exBaseAttributeValueEquip
                && exBaseAttributeValueEquip.getTagAndRateMap().containsKey(map)) {
            CompoundTag data = ExBaseAttributeValueEquip.getStackExBaseAttributeData(equip);
            exValue += exBaseAttributeValueEquip.getTagAndRateMap().get(map).getValueByData(data);
        }
        return (map.getOrDefault(equip.getItem(), 0d) + exValue) * getTierValueEffect(equip);
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
}

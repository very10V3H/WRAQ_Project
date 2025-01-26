package fun.wraq.process.system.element.data;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class ElementData {
    public static final String ELEMENT_DATA_KEY = "ElementData";
    public static CompoundTag getElementData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, ELEMENT_DATA_KEY);
    }

    public static List<String> allPieceCountKey = new ArrayList<>();
    public static final String LIFE_PIECE_COUNT_KEY = "LifePieceCount";
    public static final String WATER_PIECE_COUNT_KEY = "WaterPieceCount";
    public static final String FIRE_PIECE_COUNT_KEY = "FirePieceCount";
    public static final String STONE_PIECE_COUNT_KEY = "StonePieceCount";
    public static final String ICE_PIECE_COUNT_KEY = "IcePieceCount";
    public static final String LIGHTNING_PIECE_COUNT_KEY = "LightningPieceCount";
    public static final String WIND_PIECE_COUNT_KEY = "WindPieceCount";

    public static List<String> getAllPieceCountKey() {
        if (allPieceCountKey.isEmpty()) {
            allPieceCountKey.addAll(List.of(
                    LIFE_PIECE_COUNT_KEY,
                    WATER_PIECE_COUNT_KEY,
                    FIRE_PIECE_COUNT_KEY,
                    STONE_PIECE_COUNT_KEY,
                    ICE_PIECE_COUNT_KEY,
                    LIGHTNING_PIECE_COUNT_KEY,
                    WIND_PIECE_COUNT_KEY
            ));
        }
        return allPieceCountKey;
    }

    public static Map<String, Component> descriptionMap = new HashMap<>();
    public static Component getDescriptionByCountKey(String countKey) {
        if (descriptionMap.isEmpty()) {
            descriptionMap.put(LIFE_PIECE_COUNT_KEY, Element.Description.LifeElement(""));
            descriptionMap.put(WATER_PIECE_COUNT_KEY, Element.Description.WaterElement(""));
            descriptionMap.put(FIRE_PIECE_COUNT_KEY, Element.Description.FireElement(""));
            descriptionMap.put(STONE_PIECE_COUNT_KEY, Element.Description.StoneElement(""));
            descriptionMap.put(ICE_PIECE_COUNT_KEY, Element.Description.IceElement(""));
            descriptionMap.put(LIGHTNING_PIECE_COUNT_KEY, Element.Description.LightningElement(""));
            descriptionMap.put(WIND_PIECE_COUNT_KEY, Element.Description.WindElement(""));
            descriptionMap.put(RAINBOW_POWDER_COUNT_KEY, Te.s("「", ModItems.RainbowPowder, "」"));
            descriptionMap.put(WEAK_PIECE_COUNT_KEY, Te.s("「", ElementItems.weakPiece0, "」"));
        }
        return descriptionMap.get(countKey);
    }

    public static Map<Item, String> piece0CountKeyMap = new HashMap<>();
    public static String getPiece0CountKey(Item item) {
        if (piece0CountKeyMap.isEmpty()) {
            piece0CountKeyMap.put(ModItems.LifeElementPiece0.get(), LIFE_PIECE_COUNT_KEY);
            piece0CountKeyMap.put(ModItems.WaterElementPiece0.get(), WATER_PIECE_COUNT_KEY);
            piece0CountKeyMap.put(ModItems.FireElementPiece0.get(), FIRE_PIECE_COUNT_KEY);
            piece0CountKeyMap.put(ModItems.StoneElementPiece0.get(), STONE_PIECE_COUNT_KEY);
            piece0CountKeyMap.put(ModItems.IceElementPiece0.get(), ICE_PIECE_COUNT_KEY);
            piece0CountKeyMap.put(ModItems.LightningElementPiece0.get(), LIGHTNING_PIECE_COUNT_KEY);
            piece0CountKeyMap.put(ModItems.WindElementPiece0.get(), WIND_PIECE_COUNT_KEY);
        }
        return piece0CountKeyMap.get(item);
    }

    public static Map<String, Item> piece0Map = new HashMap<>();
    public static Item getPiece0ByCountKey(String countKey) {
        if (piece0Map.isEmpty()) {
            piece0Map.put(LIFE_PIECE_COUNT_KEY, ModItems.LifeElementPiece0.get());
            piece0Map.put(WATER_PIECE_COUNT_KEY, ModItems.WaterElementPiece0.get());
            piece0Map.put(FIRE_PIECE_COUNT_KEY, ModItems.FireElementPiece0.get());
            piece0Map.put(STONE_PIECE_COUNT_KEY, ModItems.StoneElementPiece0.get());
            piece0Map.put(ICE_PIECE_COUNT_KEY, ModItems.IceElementPiece0.get());
            piece0Map.put(LIGHTNING_PIECE_COUNT_KEY, ModItems.LightningElementPiece0.get());
            piece0Map.put(WIND_PIECE_COUNT_KEY, ModItems.WindElementPiece0.get());
        }
        return piece0Map.get(countKey);
    }

    public static Map<String, Item> piece1Map = new HashMap<>();
    public static Item getPiece1ByCountKey(String countKey) {
        if (piece1Map.isEmpty()) {
            piece1Map.put(LIFE_PIECE_COUNT_KEY, ModItems.LifeElementPiece1.get());
            piece1Map.put(WATER_PIECE_COUNT_KEY, ModItems.WaterElementPiece1.get());
            piece1Map.put(FIRE_PIECE_COUNT_KEY, ModItems.FireElementPiece1.get());
            piece1Map.put(STONE_PIECE_COUNT_KEY, ModItems.StoneElementPiece1.get());
            piece1Map.put(ICE_PIECE_COUNT_KEY, ModItems.IceElementPiece1.get());
            piece1Map.put(LIGHTNING_PIECE_COUNT_KEY, ModItems.LightningElementPiece1.get());
            piece1Map.put(WIND_PIECE_COUNT_KEY, ModItems.WindElementPiece1.get());
        }
        return piece1Map.get(countKey);
    }

    public static Map<String, Item> piece2Map = new HashMap<>();
    public static Item getPiece2ByCountKey(String countKey) {
        if (piece2Map.isEmpty()) {
            piece2Map.put(LIFE_PIECE_COUNT_KEY, ModItems.LifeElementPiece2.get());
            piece2Map.put(WATER_PIECE_COUNT_KEY, ModItems.WaterElementPiece2.get());
            piece2Map.put(FIRE_PIECE_COUNT_KEY, ModItems.FireElementPiece2.get());
            piece2Map.put(STONE_PIECE_COUNT_KEY, ModItems.StoneElementPiece2.get());
            piece2Map.put(ICE_PIECE_COUNT_KEY, ModItems.IceElementPiece2.get());
            piece2Map.put(LIGHTNING_PIECE_COUNT_KEY, ModItems.LightningElementPiece2.get());
            piece2Map.put(WIND_PIECE_COUNT_KEY, ModItems.WindElementPiece2.get());
        }
        return piece2Map.get(countKey);
    }

    public static final String RAINBOW_POWDER_COUNT_KEY = "RainbowPowderCount";
    public static final String WEAK_PIECE_COUNT_KEY = "WeakPieceCount";

    public static int getPieceCount(Player player, String countKey) {
        return getElementData(player).getInt(countKey);
    }

    public static void setPieceCount(Player player, String countKey, int count) {
        getElementData(player).putInt(countKey, count);
    }

    public static void tryToChangeRainbowPowder(Player player, int count) {
        if (!getAllPieceCountKey().stream().allMatch(countKey -> getPieceCount(player, countKey) >= count * 7)) {

            return;
        }
        sendMSG(player, Te.s("本次转换需要消耗:"));
        player.sendSystemMessage(Te.s(" 1.", CustomStyle.styleOfWorld,
                "所有元素微型碎片", ChatFormatting.LIGHT_PURPLE, " * " + (count * 7), ChatFormatting.AQUA));
    }

    public static void changeRainbowPowder(Player player, int count) {
        getAllPieceCountKey().forEach(countKey -> {
            int originCount = getPieceCount(player, countKey);
            int afterGetCount = originCount - count * 7;
            setPieceCount(player, countKey, afterGetCount);
        });
        incrementPieceCount(player, RAINBOW_POWDER_COUNT_KEY, count);
    }

    public static void tryToGetRainbowCrystal(Player player, int count) {
        int rainbowPowderCount = getPieceCount(player, RAINBOW_POWDER_COUNT_KEY);
        int canBeConvertCount = getAllPieceCountKey()
                .stream().mapToInt(countKey -> getPieceCount(player, countKey))
                .min().orElse(0);
        if (rainbowPowderCount + canBeConvertCount / 7 < count * 49) {
            sendMSG(player, Te.s("所需材料不足，", "量子化七色棱镜碎片", CustomStyle.styleOfWorld,
                    "与可用于转化的", "所有元素微型碎片", ChatFormatting.LIGHT_PURPLE, "之和未达到所需值。当前可以使用以上两种材料最多转换",
                    (rainbowPowderCount + canBeConvertCount / 7) / 49 + "个", ModItems.RainbowCrystal));
            return;
        }
        if (!InventoryOperation.checkPlayerHasItem(player, ModItems.COMPLETE_GEM.get(), count * 7)) {
            sendMSG(player, Te.s("需要背包内有", ModItems.COMPLETE_GEM, " * " + (count * 7), ChatFormatting.AQUA));
            return;
        }
        if (rainbowPowderCount >= count * 49) {
            sendMSG(player, Te.s("本次转换将消耗:"));
            player.sendSystemMessage(Te.s(" 1.", CustomStyle.styleOfWorld,
                    "量子化七色棱镜碎片", CustomStyle.styleOfWorld, " * " + count * 49, ChatFormatting.AQUA));
        } else {
            sendMSG(player, Te.s("本次转换将消耗:"));
            player.sendSystemMessage(Te.s(" 1.", CustomStyle.styleOfWorld,
                    "量子化七色棱镜碎片", CustomStyle.styleOfWorld, " * " + (rainbowPowderCount - (rainbowPowderCount % 49)), ChatFormatting.AQUA));
            player.sendSystemMessage(Te.s(" 2.", CustomStyle.styleOfWorld,
                    "所有元素微型碎片", CustomStyle.styleOfWorld, " * " + (count * 49 - rainbowPowderCount / 49) * 7, ChatFormatting.AQUA));
        }
    }

    public static void getRainbowCrystal(Player player, int count) {
        int rainbowPowderCount = getPieceCount(player, RAINBOW_POWDER_COUNT_KEY);

        if (rainbowPowderCount >= count * 49) {
            int afterGetRainbowPowderCount = rainbowPowderCount - count * 49;
            setPieceCount(player, RAINBOW_POWDER_COUNT_KEY, afterGetRainbowPowderCount);
        } else {
            int needConvertCount = count * 49 - rainbowPowderCount;
            setPieceCount(player, RAINBOW_POWDER_COUNT_KEY, 0);
            getAllPieceCountKey().forEach(countKey -> {
                int originCount = getPieceCount(player, countKey);
                int afterReduceCount = originCount - needConvertCount * 7;
                setPieceCount(player, countKey, afterReduceCount);
            });
        }
        InventoryOperation.removeItem(player, ModItems.COMPLETE_GEM.get(), count * 7);
        InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.RainbowCrystal.get(), count));
    }

    public static void tryToConvertWeakPiece(Player player) {
        if (getPieceCount(player, RAINBOW_POWDER_COUNT_KEY) > 0
                && getAllPieceCountKey()
                .stream().mapToInt(countKey -> getPieceCount(player, countKey))
                .min().orElse(0) >= 64) {
            sendMSG(player, Te.s("想使用何种元素碎片进行转化呢？"));
            getAllPieceCountKey().forEach(countKey -> {
                int originCount = getPieceCount(player, countKey);
                if (originCount >= 64) {
                    Component component = getDescriptionByCountKey(countKey);
                    Te.c(component, "/vmd ", Te.s("使用", component, "来转化"));
                }
            });
        } else {
            sendMSG(player, Te.s("转化所需的材料不足"));
        }
    }

    public static void convertWeakPiece(Player player, String useElementCountKey) {
        incrementPieceCount(player, useElementCountKey, -64);
        incrementPieceCount(player, RAINBOW_POWDER_COUNT_KEY, -1);
        incrementPieceCount(player, WEAK_PIECE_COUNT_KEY, 64);
    }

    public static void incrementPieceCount(Player player, String countKey, int increment) {
        int originPieceCount = getPieceCount(player, countKey);
        int afterIncrementPieceCount = originPieceCount + increment;
        setPieceCount(player, countKey, afterIncrementPieceCount);
    }

    public static void tryToConvertPiece(Player player) {
        if (getPieceCount(player, WEAK_PIECE_COUNT_KEY) > 0) {
            sendMSG(player, Te.s("想要转化何种元素碎片呢？"));
            getAllPieceCountKey()
                    .stream().filter(countKey -> getPieceCount(player, countKey) > 0)
                    .forEach(countKey -> {
                        Component component = getDescriptionByCountKey(countKey);
                        Te.c(component, "/vmd ", Te.s("转化为", component));
            });
        }
    }

    public static void convertPiece(Player player, String countKey, int convertCount) {
        if (getPieceCount(player, WEAK_PIECE_COUNT_KEY) < convertCount) {
            sendMSG(player, Te.s(getDescriptionByCountKey(WEAK_PIECE_COUNT_KEY), "不足"));
            return;
        }
        incrementPieceCount(player, countKey, convertCount - 1);
        incrementPieceCount(player, WEAK_PIECE_COUNT_KEY, -convertCount);
    }

    public static void tryToGetPiece1(Player player, String elementCountKey, int count) {
        if (getPieceCount(player, elementCountKey) < count * 64) {
            sendMSG(player, Te.s(getDescriptionByCountKey(elementCountKey), "不足"));
            return;
        }
        if (getPieceCount(player, RAINBOW_POWDER_COUNT_KEY) < count) {
            sendMSG(player, Te.s(getDescriptionByCountKey(elementCountKey), "不足"));
            return;
        }
        sendMSG(player, Te.s(""));
    }

    public static void getPiece1(Player player, String elementCountKey, int count) {
        incrementPieceCount(player, elementCountKey, count * -64);
        incrementPieceCount(player, RAINBOW_POWDER_COUNT_KEY, count * -1);
        InventoryOperation.giveItemStack(player, new ItemStack(getPiece1ByCountKey(elementCountKey), count));
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("量子化元素", CustomStyle.styleOfWorld), content);
    }

    public static void handleServerPlayerTick(Player player) {
        if (player.tickCount % 20 == 9
                && InventoryOperation.checkPlayerHasItem(player, ModItems.U_Disk.get(), 1)) {
            Set<Item> piece0Set = getAllPieceCountKey().stream()
                    .map(ElementData::getPiece0ByCountKey).collect(Collectors.toSet());
            for (int i = 0 ; i < player.getInventory().getContainerSize() ; i ++) {
                ItemStack stack = player.getInventory().getItem(i);
                if (piece0Set.contains(stack.getItem())) {
                    int count = stack.getCount();
                    incrementPieceCount(player, getPiece0CountKey(stack.getItem()), count);
                    stack.shrink(count);
                }
            }
        }
    }
}

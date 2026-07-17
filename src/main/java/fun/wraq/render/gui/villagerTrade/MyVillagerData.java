package fun.wraq.render.gui.villagerTrade;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;
import com.ibm.icu.impl.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyVillagerData {
    public static void setMyVillagerData(String displayName, String codeName, Style style,
                                         VillagerType villagerType, VillagerProfession profession, List<ItemStack> itemStackList) {
        villagerNameMap.put(codeName.toLowerCase(), Component.literal(displayName).withStyle(style));
        TradeList.tradeContent.put(displayName, itemStackList);
        villagerTypeMap.put(codeName.toLowerCase(), villagerType);
        villagerProfessionMap.put(codeName.toLowerCase(), profession);
    }

    public static void setMyVillagerDataWraq3(String displayName, String codeName, Style style,
        List<Pair<ItemStack, List<ItemStack>>> tradeRecipe) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (Pair<ItemStack, List<ItemStack>> recipePair : tradeRecipe) {
            itemStackList.add(recipePair.first);
            TradeList.tradeRecipeMap.put(recipePair.first, recipePair.second);
        }

        villagerNameMap.put(codeName.toLowerCase(), Component.literal(displayName).withStyle(style));
        TradeList.tradeContent.put(displayName, itemStackList);
    }

    public static Map<String, MutableComponent> villagerNameMap = new HashMap<>();
    public static Map<String, VillagerType> villagerTypeMap = new HashMap<>();
    public static Map<String, VillagerProfession> villagerProfessionMap = new HashMap<>();
}

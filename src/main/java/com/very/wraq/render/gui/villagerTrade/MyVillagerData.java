package com.very.wraq.render.gui.villagerTrade;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;

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

    public static Map<String, MutableComponent> villagerNameMap = new HashMap<>();
    public static Map<String, VillagerType> villagerTypeMap = new HashMap<>();
    public static Map<String, VillagerProfession> villagerProfessionMap = new HashMap<>();
}

package com.very.wraq.process.func.plan;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SimpleFoiledItem;

import java.util.ArrayList;
import java.util.List;

public class SimpleTierPaper extends SimpleFoiledItem {

    private final int tier;

    public SimpleTierPaper(Properties p_43136_, int tier) {
        super(p_43136_);
        this.tier = tier;
    }

    public static List<Component> tier1Description = new ArrayList<>() {{
        add(Component.literal("1. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("每日任务").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("提供的").withStyle(ChatFormatting.WHITE)).
                append(ModItems.worldSoul5.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("翻倍").withStyle(CustomStyle.styleOfMoon)));
        add(Component.literal("2. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("+15%额外产出").withStyle(ChatFormatting.GOLD)));
        add(Component.literal("3. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("接取专属月卡任务(材料需求减半)").withStyle(ChatFormatting.LIGHT_PURPLE)));
        SupplyBox supplyBoxTier1 = (SupplyBox) ModItems.supplyBoxTier1.get();
        add(Component.literal("4. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("每日补给").withStyle(ChatFormatting.GOLD)).
                append(Component.literal("内容更改为").withStyle(ChatFormatting.WHITE)).
                append(supplyBoxTier1.getDefaultInstance().getDisplayName()));
        add(Component.literal(" 内含:").withStyle(CustomStyle.styleOfWorld));
        for (int i = 0; i < supplyBoxTier1.getSupplyItems().size(); i++) {
            ItemStack supplyItem = supplyBoxTier1.getSupplyItems().get(i);
            add(Component.literal("  " + i + " ").withStyle(ChatFormatting.AQUA).
                    append(supplyItem.getDisplayName()).
                    append(Component.literal(" * ").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("" + supplyItem.getCount()).withStyle(ChatFormatting.AQUA)));
        }
        add(Component.literal("5. ").withStyle(CustomStyle.styleOfWorld).
                append(ComponentUtils.AttributeDescription.ExpUp("+100%")));
        add(Component.literal("6. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("自定义称号").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("使用次数 + ").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("2").withStyle(ChatFormatting.AQUA)));
        add(Component.literal("7. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("你将始终保持满饱食度").withStyle(CustomStyle.styleOfLife)));
        add(Component.literal("8. 持续31天").withStyle(CustomStyle.styleOfWorld));
    }};
    public static List<Component> tier2Description = new ArrayList<>() {{
        add(Component.literal("1. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("每日任务").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("提供的").withStyle(ChatFormatting.WHITE)).
                append(ModItems.worldSoul5.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("翻倍").withStyle(CustomStyle.styleOfMoon)));
        add(Component.literal("2. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("+30%额外产出").withStyle(ChatFormatting.GOLD)));
        add(Component.literal("3. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("接取专属月卡任务(材料需求减半)").withStyle(ChatFormatting.LIGHT_PURPLE)));
        SupplyBox supplyBoxTier2 = (SupplyBox) ModItems.supplyBoxTier2.get();
        add(Component.literal("4. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("每日补给").withStyle(ChatFormatting.GOLD)).
                append(Component.literal("内容更改为").withStyle(ChatFormatting.WHITE)).
                append(ModItems.supplyBoxTier2.get().getDefaultInstance().getDisplayName()));
        add(Component.literal(" 内含:").withStyle(CustomStyle.styleOfWorld));
        for (int i = 0; i < supplyBoxTier2.getSupplyItems().size(); i++) {
            ItemStack supplyItem = supplyBoxTier2.getSupplyItems().get(i);
            add(Component.literal("  " + i + " ").withStyle(ChatFormatting.AQUA).
                    append(supplyItem.getDisplayName()).
                    append(Component.literal(" * ").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("" + supplyItem.getCount()).withStyle(ChatFormatting.AQUA)));
        }
        add(Component.literal("5. ").withStyle(CustomStyle.styleOfWorld).
                append(ComponentUtils.AttributeDescription.ExpUp("+200%")));
        add(Component.literal("6. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("自定义称号").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("使用次数 + ").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("6").withStyle(ChatFormatting.AQUA)));
        add(Component.literal("7. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("你将始终保持满饱食度").withStyle(CustomStyle.styleOfLife)));
        add(Component.literal("8. 持续31天").withStyle(CustomStyle.styleOfWorld));
    }};
    public static List<Component> tier3Description = new ArrayList<>() {{
        add(Component.literal("1. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("每日任务").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("提供的").withStyle(ChatFormatting.WHITE)).
                append(ModItems.worldSoul5.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("翻倍").withStyle(CustomStyle.styleOfMoon)));
        add(Component.literal("2. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("+50%额外产出").withStyle(ChatFormatting.GOLD)));
        add(Component.literal("3. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("接取专属月卡任务(材料需求减半)").withStyle(ChatFormatting.LIGHT_PURPLE)));
        SupplyBox supplyBoxTier3 = (SupplyBox) ModItems.supplyBoxTier3.get();
        add(Component.literal("4. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("每日补给").withStyle(ChatFormatting.GOLD)).
                append(Component.literal("内容更改为").withStyle(ChatFormatting.WHITE)).
                append(ModItems.supplyBoxTier3.get().getDefaultInstance().getDisplayName()));
        add(Component.literal(" 内含:").withStyle(CustomStyle.styleOfWorld));
        for (int i = 0; i < supplyBoxTier3.getSupplyItems().size(); i++) {
            ItemStack supplyItem = supplyBoxTier3.getSupplyItems().get(i);
            add(Component.literal("  " + i + " ").withStyle(ChatFormatting.AQUA).
                    append(supplyItem.getDisplayName()).
                    append(Component.literal(" * ").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("" + supplyItem.getCount()).withStyle(ChatFormatting.AQUA)));
        }
        add(Component.literal("5. ").withStyle(CustomStyle.styleOfWorld).
                append(ComponentUtils.AttributeDescription.ExpUp("+300%")));
        add(Component.literal("6. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("在计划持续时间内可无限制地使用").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("自定义称号").withStyle(ChatFormatting.GRAY)));
        add(Component.literal("7. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("你将始终保持满饱食度").withStyle(CustomStyle.styleOfLife)));
        add(Component.literal("8. 持续31天").withStyle(CustomStyle.styleOfWorld));
    }};

    public List<Component> getTierDescription() {
        return getTierDescription(tier);
    }

    public static List<Component> getTierDescription(int tier) {
        switch (tier) {
            case 1 -> {
                return tier1Description;
            }
            case 2 -> {
                return tier2Description;
            }
            case 3 -> {
                return tier3Description;
            }
        }
        return List.of();
    }

    public static Component getTierTitle(int tier) {
        switch (tier) {
            case 1 -> {
                return Component.literal("本源学者").withStyle(ChatFormatting.GREEN);
            }
            case 2 -> {
                return Component.literal("本源杰青").withStyle(ChatFormatting.AQUA);
            }
            case 3 -> {
                return Component.literal("因子计划").withStyle(ChatFormatting.LIGHT_PURPLE);
            }
        }
        return Component.empty();
    }

    public int getTier() {
        return tier;
    }
}

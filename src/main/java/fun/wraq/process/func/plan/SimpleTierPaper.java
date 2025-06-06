package fun.wraq.process.func.plan;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
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

    public static int lastDay = 31;

    public static List<Component> tier1Description = new ArrayList<>() {{
        add(Te.s("1. ", CustomStyle.styleOfWorld, "本源回廊", CustomStyle.styleOfWorld,
                "/", "每日任务", CustomStyle.styleOfWorld,
                "/", "随机事件", CustomStyle.styleOfFlexible,
                "/", "炼魔塔", "的", ModItems.WORLD_SOUL_5.get(), "翻倍", CustomStyle.styleOfGold));
        add(Component.literal("2. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("+15%额外产出").withStyle(ChatFormatting.GOLD)));
        add(Component.literal("3. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("接取专属月卡任务(材料需求减半)").withStyle(ChatFormatting.LIGHT_PURPLE)));
        add(Te.s("4. ", CustomStyle.styleOfWorld, "委托任务", CustomStyle.styleOfWorld,
                "高评级时间延长", "1min", ChatFormatting.LIGHT_PURPLE, "，并保底获得", "B+", ChatFormatting.LIGHT_PURPLE, "评级"));
        SupplyBox supplyBoxTier1 = (fun.wraq.process.func.plan.SupplyBox) ModItems.SUPPLY_BOX_TIER_1.get();
        add(Component.literal("5. ").withStyle(CustomStyle.styleOfWorld).
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
        add(Component.literal("6. ").withStyle(CustomStyle.styleOfWorld).
                append(ComponentUtils.AttributeDescription.expUp("+100%")));
        add(Component.literal("7. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("自定义称号").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("使用次数 + ").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("2").withStyle(ChatFormatting.AQUA)));
        add(Component.literal("8. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("你将始终保持满饱食度").withStyle(CustomStyle.styleOfLife)));
        add(Te.s("9. ", CustomStyle.styleOfWorld, "每小时额外恢复", "5理智", CustomStyle.styleOfFlexible));
        add(Te.s("10. ", CustomStyle.styleOfWorld, "购买资产享", "9折优惠", ChatFormatting.GOLD));
        add(Component.literal("11. 持续" + lastDay + "天").withStyle(CustomStyle.styleOfWorld));
    }};
    public static List<Component> tier2Description = new ArrayList<>() {{
        add(Te.s("1. ", CustomStyle.styleOfWorld, "本源回廊", CustomStyle.styleOfWorld,
                "/", "每日任务", CustomStyle.styleOfWorld,
                "/", "随机事件", CustomStyle.styleOfFlexible,
                "/", "炼魔塔", "的", ModItems.WORLD_SOUL_5.get(), "翻倍", CustomStyle.styleOfGold));
        add(Component.literal("2. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("+30%额外产出").withStyle(ChatFormatting.GOLD)));
        add(Component.literal("3. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("接取专属月卡任务(材料需求减半)").withStyle(ChatFormatting.LIGHT_PURPLE)));
        add(Te.s("4. ", CustomStyle.styleOfWorld, "委托任务", CustomStyle.styleOfWorld,
                "高评级时间延长", "1min", ChatFormatting.LIGHT_PURPLE, "，并保底获得", "B+", ChatFormatting.LIGHT_PURPLE, "评级"));
        SupplyBox supplyBoxTier2 = (fun.wraq.process.func.plan.SupplyBox) ModItems.SUPPLY_BOX_TIER_2.get();
        add(Component.literal("5. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("每日补给").withStyle(ChatFormatting.GOLD)).
                append(Component.literal("内容更改为").withStyle(ChatFormatting.WHITE)).
                append(ModItems.SUPPLY_BOX_TIER_2.get().getDefaultInstance().getDisplayName()));
        add(Component.literal(" 内含:").withStyle(CustomStyle.styleOfWorld));
        for (int i = 0; i < supplyBoxTier2.getSupplyItems().size(); i++) {
            ItemStack supplyItem = supplyBoxTier2.getSupplyItems().get(i);
            add(Component.literal("  " + i + " ").withStyle(ChatFormatting.AQUA).
                    append(supplyItem.getDisplayName()).
                    append(Component.literal(" * ").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("" + supplyItem.getCount()).withStyle(ChatFormatting.AQUA)));
        }
        add(Component.literal("6. ").withStyle(CustomStyle.styleOfWorld).
                append(ComponentUtils.AttributeDescription.expUp("+200%")));
        add(Component.literal("7. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("自定义称号").withStyle(ChatFormatting.GRAY)).
                append(Component.literal("使用次数 + ").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("6").withStyle(ChatFormatting.AQUA)));
        add(Component.literal("8. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("你将始终保持满饱食度").withStyle(CustomStyle.styleOfLife)));
        add(Component.literal("9. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("使用天空城传送中枢将不消耗资源").withStyle(CustomStyle.styleOfGold)));
        add(Te.s("10. ", CustomStyle.styleOfWorld, "每小时额外恢复", "15理智", CustomStyle.styleOfFlexible));
        add(Te.s("11. ", CustomStyle.styleOfWorld, "购买资产享", "85折优惠", ChatFormatting.GOLD));
        add(Component.literal("12. 持续" + lastDay + "天").withStyle(CustomStyle.styleOfWorld));
    }};
    public static List<Component> tier3Description = new ArrayList<>() {{
        add(Te.s("1. ", CustomStyle.styleOfWorld, "本源回廊", CustomStyle.styleOfWorld,
                "/", "每日任务", CustomStyle.styleOfWorld,
                "/", "随机事件", CustomStyle.styleOfFlexible,
                "/", "炼魔塔", "的", ModItems.WORLD_SOUL_5.get(), "翻倍", CustomStyle.styleOfGold));
        add(Component.literal("2. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("+50%额外产出").withStyle(ChatFormatting.GOLD)));
        add(Component.literal("3. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("接取专属月卡任务(材料需求减半)").withStyle(ChatFormatting.LIGHT_PURPLE)));
        add(Te.s("4. ", CustomStyle.styleOfWorld, "委托任务", CustomStyle.styleOfWorld,
                "高评级时间延长", "1min", ChatFormatting.LIGHT_PURPLE, "，并保底获得", "B+", ChatFormatting.LIGHT_PURPLE, "评级"));
        SupplyBox supplyBoxTier3 = (SupplyBox) ModItems.SUPPLY_BOX_TIER_3.get();
        add(Component.literal("5. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("每日补给").withStyle(ChatFormatting.GOLD)).
                append(Component.literal("内容更改为").withStyle(ChatFormatting.WHITE)).
                append(ModItems.SUPPLY_BOX_TIER_3.get().getDefaultInstance().getDisplayName()));
        add(Component.literal(" 内含:").withStyle(CustomStyle.styleOfWorld));
        for (int i = 0; i < supplyBoxTier3.getSupplyItems().size(); i++) {
            ItemStack supplyItem = supplyBoxTier3.getSupplyItems().get(i);
            add(Component.literal("  " + i + " ").withStyle(ChatFormatting.AQUA).
                    append(supplyItem.getDisplayName()).
                    append(Component.literal(" * ").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("" + supplyItem.getCount()).withStyle(ChatFormatting.AQUA)));
        }
        add(Component.literal("6. ").withStyle(CustomStyle.styleOfWorld).
                append(ComponentUtils.AttributeDescription.expUp("+300%")));
        add(Component.literal("7. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("在计划持续时间内可无限制地使用").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("自定义称号").withStyle(ChatFormatting.GRAY)));
        add(Component.literal("8. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("你将始终保持满饱食度").withStyle(CustomStyle.styleOfLife)));
        add(Component.literal("9. ").withStyle(CustomStyle.styleOfWorld).
                append(Component.literal("使用天空城传送中枢将不消耗资源").withStyle(CustomStyle.styleOfGold)));
        add(Te.s("10. ", CustomStyle.styleOfWorld, "每小时额外恢复", "25理智", CustomStyle.styleOfFlexible));
        add(Te.s("11. ", CustomStyle.styleOfWorld, "购买资产享", "8折优惠", ChatFormatting.GOLD));
        add(Component.literal("12. 持续" + lastDay + "天").withStyle(CustomStyle.styleOfWorld));
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

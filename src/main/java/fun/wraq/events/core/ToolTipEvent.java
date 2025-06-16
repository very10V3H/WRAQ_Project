package fun.wraq.events.core;

import fun.wraq.blocks.blocks.brew.BrewingNote;
import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.blocks.blocks.inject.InjectRecipe;
import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.equip.WraqMainHandEquip;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.EnhancedForgedItem;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.InjectingRecipe;
import fun.wraq.items.misc.PocketItem;
import fun.wraq.process.func.plan.SimpleTierPaper;
import fun.wraq.process.system.cooking.CookingValue;
import fun.wraq.process.system.forge.ForgeHammer;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.extraordinary.ExtraordinaryItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ToolTipEvent {
    @SubscribeEvent
    public static void ToolTipChange(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        List<Component> tooltip = event.getToolTip();
        stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        if (stack.is(ModItems.NOTE_PAPER.get())) {
            event.getToolTip().add(Component.literal(" 置于背包以获取").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("副本奖励").withStyle(ChatFormatting.AQUA)));
        }

        if (stack.getItem() instanceof SimpleTierPaper simpleTierPaper) {
            event.getToolTip().addAll(simpleTierPaper.getTierDescription());
        }

        List<Item> items = new ArrayList<>() {{
            add(ModItems.LIFE_ELEMENT_PIECE_2.get());
            add(ModItems.WATER_ELEMENT_PIECE_2.get());
            add(ModItems.FIRE_ELEMENT_PIECE_2.get());
            add(ModItems.STONE_ELEMENT_PIECE_2.get());
            add(ModItems.ICE_ELEMENT_PIECE_2.get());
            add(ModItems.LIGHTNING_ELEMENT_PIECE_2.get());
            add(ModItems.WIND_ELEMENT_PIECE_2.get());
        }};
        if (items.contains(stack.getItem())) {
            event.getToolTip().add(
                    Component.literal(" - 可置于主手武器物品栏上方").
                            withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));

            event.getToolTip().add(
                    Component.literal(" - 基于数量为你的近战攻击/箭矢攻击/法球攻击/法术提供元素附加").
                            withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));

            event.getToolTip().add(
                    Component.literal(" - 你需要有一定的归一化元素强度，方可获得元素附加").
                            withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        }
        if (InjectRecipe.injectingRecipeMap.isEmpty()) InjectRecipe.setInjectingRecipeMap();
        if ((InjectRecipe.injectingRecipeMap.containsKey(stack.getItem())
                || InjectRecipe.productSourceItemMap.containsKey(stack.getItem())
                || InjectRecipe.injectingWaysMap.containsKey(stack.getItem()))
                && !Screen.hasAltDown()
                && !Screen.hasControlDown()) {
            if (!Screen.hasShiftDown()) {
                event.getToolTip().add(Te.s(""));
                event.getToolTip().add(Component.literal("[在灌注配方中，按下Shift查看配方]")
                        .withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfInject));
            }
            else {
                event.getToolTip().add(Te.s(""));
                List<InjectingRecipe> recipeList = new ArrayList<>();
                // 是SourceItem
                if (InjectRecipe.injectingRecipeMap.containsKey(item)) {
                    recipeList.add(InjectRecipe.injectingRecipeMap.get(item));
                }
                // 是Material
                if (InjectRecipe.injectingWaysMap.containsKey(item)) {
                    recipeList.addAll(InjectRecipe.injectingWaysMap.get(item));
                }
                // 是product
                if (InjectRecipe.productSourceItemMap.containsKey(item)) {
                    recipeList.add(InjectRecipe.injectingRecipeMap.get(InjectRecipe.productSourceItemMap.get(item)));
                }

                int stage = 0;
                if (recipeList.size() > 4) {
                    stage = (ClientUtils.serverTick / Tick.s(3))
                            % (recipeList.size() / 4 + (recipeList.size() % 4 != 0 ? 1 : 0));
                    tooltip.add(Te.s("第" + (stage + 1) + "页，"
                            + "共" + (recipeList.size() / 4 + (recipeList.size() % 4 != 0 ? 1 : 0))
                            + "页，共" + recipeList.size() + "个配方"));
                }
                tooltip.add(Te.s("->", ChatFormatting.GOLD, "在", "灌注台", CustomStyle.styleOfPurpleIron,
                        "->", ChatFormatting.GOLD));

                for (int i = stage * 4; i < Math.min(recipeList.size(), stage * 4 + 4); i++) {
                    InjectingRecipe injectingRecipe = recipeList.get(i);
                    Item sourceItem = InjectRecipe.productSourceItemMap.get(injectingRecipe.product);
                    if (sourceItem.equals(item)) {
                        tooltip.add(Te.s((i + 1) + ".", CustomStyle.styleOfInject,
                                "作为", "被灌注物", CustomStyle.styleOfInject));
                    } else if (InjectRecipe.injectingWaysMap.containsKey(item)) {
                        tooltip.add(Te.s((i + 1) + ".", CustomStyle.styleOfInject,
                                "作为", "灌注耗材", ChatFormatting.GREEN));
                    } else if (InjectRecipe.productSourceItemMap.containsKey(item)) {
                        tooltip.add(Te.s((i + 1) + ".", CustomStyle.styleOfInject,
                                "作为", "灌注产物", ChatFormatting.AQUA));
                    }
                    tooltip.add(Te.s(" 使用 ", ChatFormatting.GREEN, injectingRecipe.material,
                            " * " + injectingRecipe.getMaterialCount(), ChatFormatting.AQUA));
                    tooltip.add(Te.s(" 灌注 ", CustomStyle.styleOfInject, sourceItem, " * "
                            + injectingRecipe.getSourceItemCount(), ChatFormatting.AQUA));
                    if (sourceItem instanceof WraqArmor || sourceItem instanceof WraqMainHandEquip) {
                        tooltip.add(Te.s("▲将保留强化等级/品质/宝石等信息", CustomStyle.styleOfGold));
                    }
                    tooltip.add(Te.s(" 得到 ", ChatFormatting.AQUA, injectingRecipe.product));
                    if (i != recipeList.size() - 1 && recipeList.size() <= 4) {
                        tooltip.add(Te.s(""));
                    }
                }
            }
        }
        if (item instanceof EnhancedForgedItem enhancedForgedItem) {
            if (enhancedForgedItem.getEnhanceTier() == 0) {
                tooltip.add(Te.s(""));
                tooltip.add(Te.s("「", ChatFormatting.AQUA, "可在锻造台中", "锐化", CustomStyle.styleOfWorld,
                        "」", ChatFormatting.AQUA));
            }
        }
        if (stack.is(ModItems.WORLD_FORGE_STONE.get())) {
            event.getToolTip().add(Component.literal(" 用于装备的24+增幅").withStyle(CustomStyle.styleOfWorld));
        }
        if (stack.getTagElement(Utils.MOD_ID) != null) {
            if (item instanceof SwordItem || item instanceof BowItem || item instanceof ArmorItem || item instanceof PickaxeItem) {
                stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
            }
            CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
            if (stack.is(ModItems.BREWING_NOTE.get())) {
                Component[] Name = {
                        Component.literal("平原根源").withStyle(ChatFormatting.GREEN),
                        Component.literal("森林根源").withStyle(ChatFormatting.DARK_GREEN),
                        Component.literal("湖泊根源").withStyle(ChatFormatting.BLUE),
                        Component.literal("火山根源").withStyle(ChatFormatting.YELLOW),
                        Component.literal("冰川根源").withStyle(ChatFormatting.AQUA),
                        Component.literal("天空碎片").withStyle(CustomStyle.styleOfSky),
                        Component.literal("唤魔之源").withStyle(CustomStyle.styleOfMana),
                        Component.literal("下界能量").withStyle(CustomStyle.styleOfNether),
                        Component.literal("大块黄金").withStyle(CustomStyle.styleOfGold),
                        Component.literal("本源珍珠 - I").withStyle(CustomStyle.styleOfPlain),
                        Component.literal("本源珍珠 - II").withStyle(CustomStyle.styleOfSky),
                        Component.literal("本源珍珠 - III").withStyle(CustomStyle.styleOfNether),
                        Component.literal("本源珍珠 - IV").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("本源珍珠 - V").withStyle(CustomStyle.styleOfSakura),
                        Component.literal("本源珍珠 - VI").withStyle(CustomStyle.styleOfMoon),
                };
                String[] DataName = {
                        "PlainBrewingExp",
                        "ForestBrewingExp",
                        "LakeBrewingExp",
                        "VolcanoBrewingExp",
                        "SnowBrewingExp",
                        "SkyBrewingExp",
                        "EvokerBrewingExp",
                        "NetherBrewingExp",
                        BrewingNote.ExpName.boss2Piece,
                        BrewingNote.ExpName.pear1,
                        BrewingNote.ExpName.pear2,
                        BrewingNote.ExpName.pear3,
                        BrewingNote.ExpName.pear4,
                        BrewingNote.ExpName.pear5,
                        BrewingNote.ExpName.pear6,
                };
                CompoundTag BrewingData = stack.getOrCreateTagElement(Utils.MOD_ID);
                event.getToolTip().add(Component.literal(" "));
                event.getToolTip().add(Component.literal("~当前酿造等阶: ").withStyle(ChatFormatting.WHITE).
                        append(Utils.BrewingLevelName[Compute.BrewingLevel(stack)]));
                ComponentUtils.descriptionDash(event.getToolTip(), ChatFormatting.WHITE, CustomStyle.styleOfBrew, ChatFormatting.WHITE);
                event.getToolTip().add(Component.literal(" 酿造经验明细:").withStyle(CustomStyle.styleOfBrew));
                for (int i = 0; i < Name.length; i++) {
                    if (i == 8)
                        event.getToolTip().add(Component.literal(" 进阶酿造经验:").withStyle(CustomStyle.styleOfSakura));
                    if (BrewingData.contains(DataName[i]))
                        event.getToolTip().add(Component.literal("·").withStyle(ChatFormatting.GRAY).
                                append(Name[i]).
                                append(Component.literal("：" + String.valueOf(BrewingData.getInt(DataName[i]))).withStyle(ChatFormatting.WHITE)));
                    else
                        event.getToolTip().add(Component.literal("·").withStyle(ChatFormatting.GRAY).
                                append(Name[i]).
                                append(Component.literal("：" + String.valueOf(0)).withStyle(ChatFormatting.WHITE)));
                }
                ComponentUtils.descriptionDash(event.getToolTip(), ChatFormatting.WHITE, CustomStyle.styleOfBrew, ChatFormatting.WHITE);
            }

            if (stack.is(ModItems.FOREST_BOSS_SWORD.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("森林次元熵").withStyle(CustomStyle.styleOfHealth)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Forest).withStyle(CustomStyle.styleOfHealth)));
            }
            if (stack.is(ModItems.VOLCANO_BOSS_SWORD.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("熔岩次元熵").withStyle(CustomStyle.styleOfVolcano)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Volcano).withStyle(CustomStyle.styleOfVolcano)));
            }
            if (stack.is(ModItems.LAKE_BOSS_SWORD.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("湖泊次元熵").withStyle(ChatFormatting.BLUE)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Lake).withStyle(ChatFormatting.BLUE)));
            }
            if (stack.is(ModItems.SKY_BOSS_BOW.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("天空次元熵").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Sky).withStyle(CustomStyle.styleOfSky)));
            }
            if (stack.is(ModItems.SNOW_BOSS_CHEST.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("冰川次元熵").withStyle(CustomStyle.styleOfSnow)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Snow).withStyle(CustomStyle.styleOfSnow)));
            }
            if (data.contains(InventoryCheck.owner)) {
                event.getToolTip().add(Component.literal("「").withStyle(ChatFormatting.AQUA).
                        append(Component.literal("绑定于 " + data.getString(InventoryCheck.owner)).withStyle(CustomStyle.styleOfMoon)).
                        append(Component.literal("」").withStyle(ChatFormatting.AQUA)));
            }
        }
        ForgeRecipe.toolTip(event);
        boolean hasUsage = false;
        List<Component> usage = new ArrayList<>();
        if (item instanceof ForgeHammer) {
            hasUsage = true;
            usage.add(Component.literal("手持任意 ").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("锻造锤 ").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("右键 ").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("维瑞阿契锻造台").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal(" 打开锻造界面")));
            usage.add(Component.literal("在").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(" 不同地区 ").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("打开锻造界面会有不同的装备供打造").withStyle(ChatFormatting.WHITE)));
            usage.add(Component.literal("提示：前期装备可以不用过多考虑锻造品质").withStyle(ChatFormatting.GRAY));
        }
        if (item.equals(ModItems.PLAIN_SOUL.get())) {
            hasUsage = true;
            usage.add(Component.literal("在").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("，根源通常作为基础材料，用于合成意志").withStyle(ChatFormatting.WHITE)));
            usage.add(Component.literal("而意志通常作为合成各类装备的材料").withStyle(ChatFormatting.WHITE));
        }
        if (item.equals(ModItems.BACKPACK_TICKETS.get())) {
            hasUsage = true;
            usage.add(Component.literal("可以在天空城找到").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("背包兑换商人").withStyle(ChatFormatting.GOLD)).
                    append(Component.literal("兑换背包").withStyle(ChatFormatting.WHITE)));
        }
        if (item.equals(ModItems.LIFE_ELEMENT_PIECE_0.get())) {
            hasUsage = true;
            usage.add(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("拥有7种元素").withStyle(CustomStyle.styleOfMoon)));
            usage.add(Component.literal("这些").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE)).
                    append(Component.literal("构成了整个世界").withStyle(ChatFormatting.WHITE)));
        }
        if (item.equals(ModItems.SUN_POWER.get())) {
            hasUsage = true;
            usage.add(Component.literal("击杀带有").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("生机元素").withStyle(CustomStyle.styleOfLife)).
                    append(Component.literal("的怪物概率掉落").withStyle(ChatFormatting.WHITE)));
            usage.add(Component.literal("每名玩家每日最多可获取36个").withStyle(ChatFormatting.WHITE));
        }
        if (item.equals(ModItems.LAKE_CORE.get())) {
            hasUsage = true;
            usage.add(Component.literal("击杀带有").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("碧水元素").withStyle(CustomStyle.styleOfWater)).
                    append(Component.literal("的怪物概率掉落").withStyle(ChatFormatting.WHITE)));
            usage.add(Component.literal("每名玩家每日最多可获取36个").withStyle(ChatFormatting.WHITE));
        }
        if (item.equals(ModItems.VOLCANO_CORE.get())) {
            hasUsage = true;
            usage.add(Component.literal("击杀带有").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("炽焰元素").withStyle(CustomStyle.styleOfVolcano)).
                    append(Component.literal("的怪物概率掉落").withStyle(ChatFormatting.WHITE)));
            usage.add(Component.literal("每名玩家每日最多可获取36个").withStyle(ChatFormatting.WHITE));
        }
        if (item instanceof UsageOrGetWayDescriptionItem usageOrGetWayDescriptionItem) {
            if (!usageOrGetWayDescriptionItem.getWayDescription().isEmpty()) {
                hasUsage = true;
                usage.addAll(usageOrGetWayDescriptionItem.getWayDescription());
            }
        }
        if (hasUsage) {
            if (Screen.hasShiftDown()) {
                tooltip.add(Te.s(""));
                tooltip.add(Te.s("->", ChatFormatting.GOLD, "来源/用途", CustomStyle.styleOfWorld, "->", ChatFormatting.GOLD));
                event.getToolTip().addAll(usage);
            } else {
                tooltip.add(Te.s(""));
                event.getToolTip().add(Component.literal("[按住shift查看来源/用途]").withStyle(ChatFormatting.GRAY));
            }
        }
        if (item instanceof RandomCurios) {
            tooltip.add(Te.s(""));
            event.getToolTip().add(Component.literal("需要注意的是，除了纹章以外的大多数饰品").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.literal("同一类物品仅会生效最后装备的一个的效果").withStyle(ChatFormatting.GRAY));
        }
        if (item instanceof Decomposable decomposable) {
            if (decomposable.getProduct() != null && !decomposable.getProduct().is(Items.AIR)) {
                tooltip.add(Te.s(""));
                tooltip.add(Te.s(" 可在锻造台分解为", CustomStyle.styleOfStone, decomposable.getProduct().getDisplayName(),
                        " * " + decomposable.getProduct().getCount(), CustomStyle.styleOfWorld));
            }
        }
        CookingValue.handleToolTip(event.getItemStack(), tooltip);
        PocketItem.addTooltip(stack, tooltip);

        List<Item> betaItems = List.of(ExtraordinaryItems.KANUPUS_WING_F.get(),
                ExtraordinaryItems.KANUPUS_SWORD.get(),
                ExtraordinaryItems.SHIRO_BOW.get(),
                ExtraordinaryItems.NETHER_SCEPTRE_EX.get());
        if (betaItems.contains(item)) {
            tooltip.add(Te.s("该物品处于测试阶段，未来有较大改动可能性.", CustomStyle.styleOfWorld));
        }
    }
}

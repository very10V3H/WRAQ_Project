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
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.InjectingRecipe;
import fun.wraq.process.func.plan.SimpleTierPaper;
import fun.wraq.process.system.forge.ForgeHammer;
import fun.wraq.render.toolTip.CustomStyle;
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
        if (stack.is(ModItems.notePaper.get())) {
            event.getToolTip().add(Component.literal(" 置于背包以获取").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("副本奖励").withStyle(ChatFormatting.AQUA)));
        }

        if (stack.getItem() instanceof SimpleTierPaper simpleTierPaper) {
            event.getToolTip().addAll(simpleTierPaper.getTierDescription());
        }

        List<Item> items = new ArrayList<>() {{
            add(ModItems.LifeElementPiece2.get());
            add(ModItems.WaterElementPiece2.get());
            add(ModItems.FireElementPiece2.get());
            add(ModItems.StoneElementPiece2.get());
            add(ModItems.IceElementPiece2.get());
            add(ModItems.LightningElementPiece2.get());
            add(ModItems.WindElementPiece2.get());
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
                || InjectRecipe.injectedGetItemSourceItemMap.containsKey(stack.getItem())) && !Screen.hasAltDown()
                && !Screen.hasControlDown()) {
            if (!Screen.hasShiftDown()) {
                event.getToolTip().add(Te.s(""));
                event.getToolTip().add(Component.literal("[在灌注配方中，按下Shift查看配方]")
                        .withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfInject));
            }
            else {
                event.getToolTip().add(Te.s(""));
                InjectingRecipe injectingRecipe;
                Item sourceItem = stack.getItem();
                if (InjectRecipe.injectedGetItemSourceItemMap.containsKey(stack.getItem())
                        && InjectRecipe.injectingRecipeMap.containsKey(stack.getItem())) {
                    if (ClientUtils.clientPlayerTick % 40 < 20) {
                        sourceItem = InjectRecipe.injectedGetItemSourceItemMap.get(stack.getItem());
                        injectingRecipe = InjectRecipe.injectingRecipeMap.get(sourceItem);
                    } else {
                        injectingRecipe = InjectRecipe.injectingRecipeMap.get(stack.getItem());
                    }
                } else {
                    if (InjectRecipe.injectedGetItemSourceItemMap.containsKey(stack.getItem())) {
                        sourceItem = InjectRecipe.injectedGetItemSourceItemMap.get(stack.getItem());
                        injectingRecipe = InjectRecipe.injectingRecipeMap.get(sourceItem);
                    } else {
                        injectingRecipe = InjectRecipe.injectingRecipeMap.get(stack.getItem());
                    }
                }
                tooltip.add(Te.s("->", ChatFormatting.GOLD, "在", "灌注台", CustomStyle.styleOfPurpleIron,
                        "->", ChatFormatting.GOLD));
                event.getToolTip().add(Component.literal("使用 ").withStyle(ChatFormatting.GREEN).
                        append(injectingRecipe.getForgingNeededMaterial().getDefaultInstance().getDisplayName()).
                        append(Component.literal(" * " + injectingRecipe.getMaterialCount()).withStyle(ChatFormatting.WHITE)));
                event.getToolTip().add(Component.literal("灌注 ").withStyle(CustomStyle.styleOfInject).
                        append(sourceItem.getDefaultInstance().getDisplayName()).
                        append(Component.literal(" * " + injectingRecipe.getOriginalMaterialNeedCount()).withStyle(ChatFormatting.WHITE)));
                if (sourceItem instanceof WraqArmor || sourceItem instanceof WraqMainHandEquip) {
                    tooltip.add(Te.s("▲将保留强化等级/品质/宝石等信息", CustomStyle.styleOfGold));
                }
                event.getToolTip().add(Component.literal("得到 ").withStyle(ChatFormatting.AQUA).
                        append(injectingRecipe.getForgingGetItem().getDefaultInstance().getDisplayName()));
            }
        }
        if (stack.is(ModItems.WORLD_FORGE_STONE.get())) {
            event.getToolTip().add(Component.literal(" 用于装备的24+增幅").withStyle(CustomStyle.styleOfWorld));
        }
        if (stack.getTagElement(Utils.MOD_ID) != null) {
            if (item instanceof SwordItem || item instanceof BowItem || item instanceof ArmorItem || item instanceof PickaxeItem)
                stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
            CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
            if (stack.is(ModItems.BrewingNote.get())) {
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

            if (stack.is(ModItems.ForestBossSword.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("森林次元熵").withStyle(CustomStyle.styleOfHealth)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Forest).withStyle(CustomStyle.styleOfHealth)));
            }
            if (stack.is(ModItems.VolcanoBossSword.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("熔岩次元熵").withStyle(CustomStyle.styleOfVolcano)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Volcano).withStyle(CustomStyle.styleOfVolcano)));
            }
            if (stack.is(ModItems.LakeBossSword.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("湖泊次元熵").withStyle(ChatFormatting.BLUE)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Lake).withStyle(ChatFormatting.BLUE)));
            }
            if (stack.is(ModItems.SkyBossBow.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("天空次元熵").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Sky).withStyle(CustomStyle.styleOfSky)));
            }
            if (stack.is(ModItems.SnowBossArmorChest.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("冰川次元熵").withStyle(CustomStyle.styleOfSnow)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Snow).withStyle(CustomStyle.styleOfSnow)));
            }
            if (stack.is(ModItems.SBoots.get()) || stack.is(ModItems.SLeggings.get())
                    || stack.is(ModItems.SChest.get()) || stack.is(ModItems.SHelmet.get())
                    || stack.is(ModItems.ISArmorBoots.get()) || stack.is(ModItems.ISArmorLeggings.get())
                    || stack.is(ModItems.ISArmorChest.get()) || stack.is(ModItems.ISArmorHelmet.get())) {
                List<Component> components = event.getToolTip();
                if (stack.is(ModItems.SBoots.get()) || stack.is(ModItems.ISArmorBoots.get())) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(CustomStyle.styleOfSpider)));
                } else if (stack.is(ModItems.SLeggings.get()) || stack.is(ModItems.ISArmorLeggings.get())) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("护腿").withStyle(CustomStyle.styleOfSpider)));
                } else if (stack.is(ModItems.SChest.get()) || stack.is(ModItems.ISArmorChest.get())) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("胸甲").withStyle(CustomStyle.styleOfSpider)));
                } else if (stack.is(ModItems.SHelmet.get()) || stack.is(ModItems.ISArmorHelmet.get())) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(CustomStyle.styleOfSpider)));
                }
                ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfSpider, ChatFormatting.WHITE);
                ComponentUtils.descriptionOfBasic(components);
                int SIndex = data.getInt("SIndex");
                int Rate = (stack.is(ModItems.ISArmorBoots.get()) || stack.is(ModItems.ISArmorLeggings.get())
                        || stack.is(ModItems.ISArmorChest.get()) || stack.is(ModItems.ISArmorHelmet.get())) ? 2 : 1;
                for (int i = 1; i <= 5; i++) {
                    String IsSunPower = "#Slot#" + i + "#SunPower";
                    String IsLakePower = "#Slot#" + i + "#LakePower";
                    String IsVolcanoPower = "#Slot#" + i + "#VolcanoPower";
                    String IsSnowPower = "#Slot#" + i + "#SnowPower";
                    String IsSkyPower = "#Slot#" + i + "#SkyPower";
                    String IsManaPower = "#Slot#" + i + "#ManaPower";
                    String IsNetherPower = "#Slot#" + i + "#NetherPower";
                    String Index = "";
                    if (SIndex == i) Index = "✧";
                    String Star = "";
                    ChatFormatting chatFormatting0 = ChatFormatting.WHITE;
                    ChatFormatting chatFormatting1 = ChatFormatting.GREEN;
                    ChatFormatting chatFormatting2 = ChatFormatting.AQUA;
                    ChatFormatting chatFormatting3 = ChatFormatting.LIGHT_PURPLE;
                    if (data.contains(IsSunPower)) {
                        if (data.getInt(IsSunPower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        } else if (data.getInt(IsSunPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsSunPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.Health + " 最大生命值").withStyle(ChatFormatting.GREEN).
                                append(Component.literal(String.format(" +%.1f", data.getDouble(IsSunPower) * Rate * 2) + Star).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.GRAY)));
                    } else if (data.contains(IsLakePower)) {
                        if (data.getInt(IsLakePower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        } else if (data.getInt(IsLakePower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsLakePower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.CoolDown + " 冷却缩减").withStyle(ChatFormatting.AQUA).
                                append(Component.literal(String.format(" +%.1f%%", data.getDouble(IsLakePower) * Rate * 1.5 / 2) + Star).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.GRAY)));
                    } else if (data.contains(IsVolcanoPower)) {
                        if (data.getInt(IsVolcanoPower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        } else if (data.getInt(IsVolcanoPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsVolcanoPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.Sword + " 额外攻击").withStyle(ChatFormatting.YELLOW).
                                append(Component.literal(String.format(" +%.1f", data.getDouble(IsVolcanoPower) * Rate * 1.5) + Star).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.GRAY)));
                    } else if (data.contains(IsSnowPower)) {
                        if (data.getInt(IsSnowPower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        } else if (data.getInt(IsSnowPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsSnowPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.CritDamage + " 暴击伤害").withStyle(ChatFormatting.BLUE).
                                append(Component.literal(String.format(" +%.1f%%", data.getDouble(IsSnowPower) * Rate * 1.5) + Star).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.GRAY)));
                    } else if (data.contains(IsSkyPower)) {
                        if (data.getInt(IsSkyPower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        } else if (data.getInt(IsSkyPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsSkyPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.CritRate + " 暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).
                                append(Component.literal(String.format(" +%.1f%%", data.getDouble(IsSkyPower) * Rate * 1.5 / 2) + Star).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.GRAY)));
                    } else if (data.contains(IsManaPower)) {
                        if (data.getInt(IsManaPower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        } else if (data.getInt(IsManaPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsManaPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.Mana + " 魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE).
                                append(Component.literal(String.format(" +%.1f", data.getDouble(IsManaPower) * Rate * 1.5) + Star).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.GRAY)));
                    } else if (data.contains(IsNetherPower)) {
                        if (data.getInt(IsNetherPower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        } else if (data.getInt(IsNetherPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsNetherPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.HealSteal + " 生命偷取").withStyle(ChatFormatting.RED).
                                append(Component.literal(String.format(" +%.1f%%", data.getDouble(IsNetherPower) * Rate * 1.5 / 4) + Star).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.GRAY)));
                    } else {
                        components.add(Component.literal(i + ".[待涂附]").withStyle(ChatFormatting.GRAY));
                    }
                }
                ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfSpider, ChatFormatting.WHITE);
                components.add(Component.literal(" "));
                if (Rate == 1) {
                    components.add(Component.literal("SArmor-I").withStyle(CustomStyle.styleOfSpider).withStyle(ChatFormatting.ITALIC));
                    components.add(Component.literal("MainStoryII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
                } else {
                    components.add(Component.literal("SArmor-X").withStyle(CustomStyle.styleOfSpider).withStyle(ChatFormatting.ITALIC));
                    components.add(Component.literal("Intensified-Spider").withStyle(CustomStyle.styleOfVolcano).withStyle(ChatFormatting.ITALIC));
                }
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
        if (item.equals(ModItems.PlainSoul.get())) {
            hasUsage = true;
            usage.add(Component.literal("在").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("，根源通常作为基础材料，用于合成意志").withStyle(ChatFormatting.WHITE)));
            usage.add(Component.literal("而意志通常作为合成各类装备的材料").withStyle(ChatFormatting.WHITE));
        }
        if (item.equals(ModItems.BackSpawn.get())) {
            hasUsage = true;
            usage.add(Component.literal("右键这个物品，会进行").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("引导").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("，引导的").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("时间").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("取决于距离最近重生点的").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("距离").withStyle(ChatFormatting.GREEN)));
            usage.add(Component.literal("这个距离仅会计算水平距离（想要回到天空城，于天空城下方使用即可）").withStyle(ChatFormatting.AQUA));
            usage.add(Component.literal("若位于非").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("主世界").withStyle(ChatFormatting.GREEN)).
                    append(Component.literal("的维度").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("，则会引导至上次处于的主世界重生点").withStyle(ChatFormatting.WHITE)));

        }
        if (item.equals(ModItems.BackPackTickets.get())) {
            hasUsage = true;
            usage.add(Component.literal("可以在天空城找到").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("背包兑换商人").withStyle(ChatFormatting.GOLD)).
                    append(Component.literal("兑换背包").withStyle(ChatFormatting.WHITE)));
        }
        if (item.equals(ModItems.LifeElementPiece0.get())) {
            hasUsage = true;
            usage.add(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("拥有7种元素").withStyle(CustomStyle.styleOfMoon)));
            usage.add(Component.literal("这些").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE)).
                    append(Component.literal("构成了整个世界").withStyle(ChatFormatting.WHITE)));
        }
        if (item.equals(ModItems.SunPower.get())) {
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
            if (!decomposable.getProduct().is(Items.AIR)) {
                tooltip.add(Te.s(""));
                tooltip.add(Te.s(" 可在锻造台分解为", CustomStyle.styleOfStone, decomposable.getProduct().getDisplayName(),
                        " * " + decomposable.getProduct().getCount(), CustomStyle.styleOfWorld));
            }
        }
    }
}

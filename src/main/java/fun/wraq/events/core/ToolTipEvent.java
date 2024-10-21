package fun.wraq.events.core;

import fun.wraq.blocks.blocks.brew.BrewingNote;
import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.blocks.blocks.inject.InjectRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.InjectingRecipe;
import fun.wraq.process.func.plan.SimpleTierPaper;
import fun.wraq.process.system.forge.ForgeHammer;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.CastleCurios;
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
        event.getItemStack().hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
        if (event.getItemStack().is(ModItems.notePaper.get())) {
            event.getToolTip().add(Component.literal(" 置于背包以获取").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("副本奖励").withStyle(ChatFormatting.AQUA)));
        }

        if (event.getItemStack().getItem() instanceof SimpleTierPaper simpleTierPaper) {
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
        if (items.contains(event.getItemStack().getItem())) {
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
        if ((InjectRecipe.injectingRecipeMap.containsKey(event.getItemStack().getItem())
                || InjectRecipe.injectedGetItemSourceItemMap.containsKey(event.getItemStack().getItem()))
                && !(event.getItemStack().getItem() instanceof CastleCurios)) {
            if (!Screen.hasShiftDown())
                event.getToolTip().add(Component.literal("[在灌注配方中，shift查看配方]").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfInject));
            else {
                InjectingRecipe injectingRecipe;
                Item sourceItem = event.getItemStack().getItem();
                if (InjectRecipe.injectingRecipeMap.containsKey(event.getItemStack().getItem())) {
                    injectingRecipe = InjectRecipe.injectingRecipeMap.get(event.getItemStack().getItem());
                } else {
                    sourceItem = InjectRecipe.injectedGetItemSourceItemMap.get(event.getItemStack().getItem());
                    injectingRecipe = InjectRecipe.injectingRecipeMap.get(sourceItem);
                }
                event.getToolTip().add(Component.literal("使用 ").withStyle(ChatFormatting.GREEN).
                        append(injectingRecipe.getForgingNeededMaterial().getDefaultInstance().getDisplayName()).
                        append(Component.literal(" * " + injectingRecipe.getMaterialCount()).withStyle(ChatFormatting.WHITE)));
                event.getToolTip().add(Component.literal("灌注 ").withStyle(CustomStyle.styleOfInject).
                        append(sourceItem.getDefaultInstance().getDisplayName()).
                        append(Component.literal(" * " + injectingRecipe.getOriginalMaterialNeedCount()).withStyle(ChatFormatting.WHITE)));
                event.getToolTip().add(Component.literal("得到 ").withStyle(ChatFormatting.AQUA).
                        append(injectingRecipe.getForgingGetItem().getDefaultInstance().getDisplayName()));
            }
        }
        if (event.getItemStack().is(ModItems.worldForgeStone.get())) {
            event.getToolTip().add(Component.literal(" 用于装备的24+增幅").withStyle(CustomStyle.styleOfWorld));
        }
        if (event.getItemStack().getTagElement(Utils.MOD_ID) != null) {
            ItemStack equip = event.getItemStack();
            Item item = equip.getItem();
            if (item instanceof SwordItem || item instanceof BowItem || item instanceof ArmorItem || item instanceof PickaxeItem)
                equip.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
            CompoundTag data = event.getItemStack().getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains("attackdamage"))
                event.getToolTip().add(Component.literal("·基础攻击 ").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("attackdamage"))).withStyle(ChatFormatting.WHITE)));
            if (data.contains("breakDefence"))
                event.getToolTip().add(Component.literal("·护甲穿透+").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("breakDefence") * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if (data.contains("criticalrate"))
                event.getToolTip().add(Component.literal("·暴击几率+").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("criticalrate") * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if (data.contains("criticaldamage"))
                event.getToolTip().add(Component.literal("·暴击伤害+").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("criticaldamage") * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if (data.contains("healsteal"))
                event.getToolTip().add(Component.literal("·生命偷取+").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("healsteal") * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if (data.contains("speedup"))
                event.getToolTip().add(Component.literal("·移动速度+").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f", event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("speedup") * 100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if (data.contains("randomsword")) {
                event.getToolTip().add(Component.literal("··········································").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.OBFUSCATED).withStyle(ChatFormatting.BOLD));
                event.getToolTip().add(Component.literal(" "));
                event.getToolTip().add(Component.literal("Forging-Sword-I").withStyle(ChatFormatting.GRAY));
                event.getToolTip().add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
            }
            if (data.contains("Quest")) {
                if (data.getInt("Quest") == 0) {
                    event.getToolTip().add(Component.literal("当前任务:64*平原根源"));
                } else {
                    if (data.getInt("Quest") == 1) {
                        event.getToolTip().add(Component.literal("当前任务:64*森林根源"));
                    } else {
                        if (data.getInt("Quest") == 2) {
                            event.getToolTip().add(Component.literal("当前任务:64*湖泊根源"));
                        } else {
                            if (data.getInt("Quest") == 3) {
                                event.getToolTip().add(Component.literal("当前任务:64*火山根源"));
                            } else {
                                event.getToolTip().remove(Component.literal("当前任务:64*平原根源"));
                                event.getToolTip().remove(Component.literal("当前任务:64*森林根源"));
                                event.getToolTip().remove(Component.literal("当前任务:64*湖泊根源"));
                                event.getToolTip().remove(Component.literal("当前任务:64*火山根源"));
                                event.getToolTip().add(Component.literal("目前没有任务，右键以接取一个任务！"));
                            }
                        }
                    }
                }
            }
            if (event.getItemStack().is(ModItems.DailyMission.get())) {
                int index = 1;
                Component[] Name = {Component.literal(""), Component.literal("平原僵尸").withStyle(ChatFormatting.GREEN),
                        Component.literal("森林骷髅").withStyle(ChatFormatting.DARK_GREEN),
                        Component.literal("森林僵尸").withStyle(ChatFormatting.DARK_GREEN),
                        Component.literal("湖泊守卫者").withStyle(ChatFormatting.BLUE),
                        Component.literal("火山烈焰").withStyle(ChatFormatting.YELLOW),
                        Component.literal("矿洞僵尸").withStyle(ChatFormatting.GRAY),
                        Component.literal("矿洞骷髅").withStyle(ChatFormatting.GRAY),
                        Component.literal("冰川流浪者").withStyle(ChatFormatting.AQUA),
                        Component.literal("天空城的不速之客").withStyle(ChatFormatting.AQUA),
                        Component.literal("森林唤魔者").withStyle(CustomStyle.styleOfMana),
                        Component.literal("脆弱的灵魂").withStyle(CustomStyle.styleOfHusk),
                        Component.literal("神殿守卫").withStyle(CustomStyle.styleOfSea),
                        Component.literal("唤雷守卫").withStyle(CustomStyle.styleOfLightingIsland),
                        Component.literal("下界凋零骷髅").withStyle(CustomStyle.styleOfNether),
                        Component.literal("下界猪灵").withStyle(CustomStyle.styleOfNether),
                        Component.literal("下界遗骸").withStyle(CustomStyle.styleOfNether),
                        Component.literal("下界熔岩能量聚合物").withStyle(CustomStyle.styleOfNether),
                };
                Component[] NameStrike = {Component.literal(""), Component.literal("平原僵尸").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("森林骷髅").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("森林僵尸").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("湖泊守卫者").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("火山烈焰").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("矿洞僵尸").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("矿洞骷髅").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("冰川流浪者").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("天空城的不速之客").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("森林唤魔者").withStyle(CustomStyle.styleOfMana).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("脆弱的灵魂").withStyle(CustomStyle.styleOfHusk).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("神殿守卫").withStyle(CustomStyle.styleOfSea).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("唤雷守卫").withStyle(CustomStyle.styleOfLightingIsland).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("下界凋零骷髅").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("下界猪灵").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("下界遗骸").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("下界熔岩能量聚合物").withStyle(CustomStyle.styleOfNether).withStyle(ChatFormatting.STRIKETHROUGH),

                };
                for (int i = 1; i <= 17; i++) {
                    String string = "DailyMission" + i;
                    if (data.contains(string) && data.getInt(string) > 0) {
                        if (data.getInt(string) <= ClientUtils.DailyMission[i]) {
                            event.getToolTip().add(Component.literal(" " + index + ".").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("击杀").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.STRIKETHROUGH)).
                                    append(NameStrike[i]).
                                    append(Component.literal(" (").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(data.getInt(string) + "/" + data.getInt(string) + ")")).
                                    append(Component.literal("√").withStyle(ChatFormatting.GREEN)));
                        } else {
                            event.getToolTip().add(Component.literal(" " + index + ".").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("击杀").withStyle(ChatFormatting.WHITE)).
                                    append(Name[i]).
                                    append(Component.literal(" (").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(ClientUtils.DailyMission[i] + "/" + data.getInt(string) + ")")).
                                    append(Component.literal("?").withStyle(ChatFormatting.GOLD)));
                        }
                        index++;
                    }
                }
            }
            if (event.getItemStack().is(ModItems.BrewingNote.get())) {
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
                CompoundTag BrewingData = event.getItemStack().getOrCreateTagElement(Utils.MOD_ID);
                event.getToolTip().add(Component.literal(" "));
                event.getToolTip().add(Component.literal("~当前酿造等阶: ").withStyle(ChatFormatting.WHITE).
                        append(Utils.BrewingLevelName[Compute.BrewingLevel(event.getItemStack())]));
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

            if (data.contains("Number")) {
                int Number = data.getInt("Number");
                if (Number < 10) {
                    event.getToolTip().add(Component.literal("∫维瑞阿契第").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                            append(Component.literal("" + Number).withStyle(ChatFormatting.RED)).
                            append(Component.literal("件").withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.BOLD).
                            append(equip.getDisplayName()));
                } else if (Number < 100) {
                    event.getToolTip().add(Component.literal("∫维瑞阿契第").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                            append(Component.literal("" + Number).withStyle(ChatFormatting.GOLD)).
                            append(Component.literal("件").withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.BOLD).
                            append(equip.getDisplayName()));
                } else if (Number < 1000) {
                    event.getToolTip().add(Component.literal("∫维瑞阿契第").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                            append(Component.literal("" + Number).withStyle(ChatFormatting.AQUA)).
                            append(Component.literal("件").withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.BOLD).
                            append(equip.getDisplayName()));
                } else {
                    event.getToolTip().add(Component.literal("∫维瑞阿契第").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                            append(Component.literal("" + Number).withStyle(ChatFormatting.GREEN)).
                            append(Component.literal("件").withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.BOLD).
                            append(equip.getDisplayName()));
                }
            }
            if (equip.is(ModItems.ForestBossSword.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("森林次元熵").withStyle(CustomStyle.styleOfHealth)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Forest).withStyle(CustomStyle.styleOfHealth)));
            }
            if (equip.is(ModItems.VolcanoBossSword.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("熔岩次元熵").withStyle(CustomStyle.styleOfVolcano)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Volcano).withStyle(CustomStyle.styleOfVolcano)));
            }
            if (equip.is(ModItems.LakeBossSword.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("湖泊次元熵").withStyle(ChatFormatting.BLUE)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Lake).withStyle(ChatFormatting.BLUE)));
            }
            if (equip.is(ModItems.SkyBossBow.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("天空次元熵").withStyle(CustomStyle.styleOfSky)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Sky).withStyle(CustomStyle.styleOfSky)));
            }
            if (equip.is(ModItems.SnowBossArmorChest.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("冰川次元熵").withStyle(CustomStyle.styleOfSnow)).
                        append(Component.literal("为:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Snow).withStyle(CustomStyle.styleOfSnow)));
            }
            if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.SLeggings.get())
                    || equip.is(ModItems.SChest.get()) || equip.is(ModItems.SHelmet.get())
                    || equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                    || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
                List<Component> components = event.getToolTip();
                if (equip.is(ModItems.SBoots.get()) || equip.is(ModItems.ISArmorBoots.get())) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(CustomStyle.styleOfSpider)));
                } else if (equip.is(ModItems.SLeggings.get()) || equip.is(ModItems.ISArmorLeggings.get())) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("护腿").withStyle(CustomStyle.styleOfSpider)));
                } else if (equip.is(ModItems.SChest.get()) || equip.is(ModItems.ISArmorChest.get())) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("胸甲").withStyle(CustomStyle.styleOfSpider)));
                } else if (equip.is(ModItems.SHelmet.get()) || equip.is(ModItems.ISArmorHelmet.get())) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(CustomStyle.styleOfSpider)));
                }
                ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfSpider, ChatFormatting.WHITE);
                ComponentUtils.descriptionOfBasic(components);
                int SIndex = data.getInt("SIndex");
                int Rate = (equip.is(ModItems.ISArmorBoots.get()) || equip.is(ModItems.ISArmorLeggings.get())
                        || equip.is(ModItems.ISArmorChest.get()) || equip.is(ModItems.ISArmorHelmet.get())) ? 2 : 1;
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
        Item item = event.getItemStack().getItem();
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
        if (item.equals(ModItems.LakeCore.get())) {
            hasUsage = true;
            usage.add(Component.literal("击杀带有").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("碧水元素").withStyle(CustomStyle.styleOfWater)).
                    append(Component.literal("的怪物概率掉落").withStyle(ChatFormatting.WHITE)));
            usage.add(Component.literal("每名玩家每日最多可获取36个").withStyle(ChatFormatting.WHITE));
        }
        if (item.equals(ModItems.VolcanoCore.get())) {
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
                event.getToolTip().addAll(usage);
            } else {
                event.getToolTip().add(Component.literal("[按住shift查看来源/用途]").withStyle(ChatFormatting.GRAY));
            }
        }
        if (item instanceof RandomCurios) {
            event.getToolTip().add(Component.literal("需要注意的是，除了纹章以外的大多数饰品").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.literal("同一类物品仅会生效最后装备的一个的效果").withStyle(ChatFormatting.GRAY));
        }
    }
}

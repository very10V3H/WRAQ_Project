package com.Very.very.Events.MainEvents;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class ToolTipEvent {

    @SubscribeEvent
    public static void ToolTipChange(ItemTooltipEvent event)
    {
        if(event.getItemStack().getTagElement(Utils.MOD_ID) != null)
        {
            ItemStack equip = event.getItemStack();
            equip.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
            CompoundTag data = event.getItemStack().getOrCreateTagElement(Utils.MOD_ID);
            if(data.contains("attackdamage")) event.getToolTip().add(Component.literal("·基础攻击 ").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("attackdamage"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            if(data.contains("breakdefence")) event.getToolTip().add(Component.literal("·护甲穿透+").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("breakdefence")*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("criticalrate")) event.getToolTip().add(Component.literal("·暴击几率+").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("criticalrate")*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("criticaldamage")) event.getToolTip().add(Component.literal("·暴击伤害+").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("criticaldamage")*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("healsteal")) event.getToolTip().add(Component.literal("·生命偷取+").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("healsteal")*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("speedup")) event.getToolTip().add(Component.literal("·移动速度+").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("speedup")*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("randomsword"))
            {
                event.getToolTip().add(Component.literal("··········································").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.OBFUSCATED).withStyle(ChatFormatting.BOLD));
                event.getToolTip().add(Component.literal(" "));
                event.getToolTip().add(Component.literal("Forging-Sword-I").withStyle(ChatFormatting.GRAY));
                event.getToolTip().add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
            }
            if(data.contains("Quest"))
            {
                if(data.getInt("Quest") == 0)
                {
                    event.getToolTip().add(Component.literal("当前任务:64*平原根源"));
                }
                else
                {
                    if(data.getInt("Quest") == 1)
                    {
                        event.getToolTip().add(Component.literal("当前任务:64*森林根源"));
                    }
                    else
                    {
                        if(data.getInt("Quest") == 2)
                        {
                            event.getToolTip().add(Component.literal("当前任务:64*湖泊根源"));
                        }
                        else
                        {
                            if(data.getInt("Quest") == 3)
                            {
                                event.getToolTip().add(Component.literal("当前任务:64*火山根源"));
                            }
                            else
                            {
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
            if(event.getItemStack().is(Moditems.DailyMission.get()))
            {
                int index = 1;
                Component[] Name = {Component.literal(""),Component.literal("平原僵尸").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                        Component.literal("森林骷髅").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN),
                        Component.literal("森林僵尸").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN),
                        Component.literal("湖泊守卫者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE),
                        Component.literal("火山烈焰").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW),
                        Component.literal("矿洞僵尸").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                        Component.literal("矿洞骷髅").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                        Component.literal("冰川流浪者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                        Component.literal("天空城的不速之客").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                        Component.literal("森林唤魔者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana),
                        Component.literal("脆弱的灵魂").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBlackForest),
                        Component.literal("神殿守卫").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea),
                        Component.literal("唤雷守卫").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland),
                        Component.literal("下界凋零骷髅").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                        Component.literal("下界猪灵").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                        Component.literal("下界遗骸").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                        Component.literal("下界熔岩能量聚合物").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                };
                Component[] NameStrike = {Component.literal(""),Component.literal("平原僵尸").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("森林骷髅").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("森林僵尸").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("湖泊守卫者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("火山烈焰").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("矿洞僵尸").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("矿洞骷髅").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("冰川流浪者").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("天空城的不速之客").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("森林唤魔者").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("脆弱的灵魂").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfBlackForest).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("神殿守卫").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSea).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("唤雷守卫").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("下界凋零骷髅").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("下界猪灵").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("下界遗骸").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether).withStyle(ChatFormatting.STRIKETHROUGH),
                        Component.literal("下界熔岩能量聚合物").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether).withStyle(ChatFormatting.STRIKETHROUGH),

                };
                for(int i = 1; i <= 17; i++)
                {
                    String string = "DailyMission"+i;
                    if(data.contains(string) && data.getInt(string) > 0)
                    {
                        if(data.getInt(string) <= ClientUtils.DailyMission[i])
                        {
                            event.getToolTip().add(Component.literal(" "+index+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("击杀").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.STRIKETHROUGH)).
                                    append(NameStrike[i]).
                                    append(Component.literal(" (").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(data.getInt(string)+"/"+data.getInt(string)+")").withStyle(ChatFormatting.RESET)).
                                    append(Component.literal("√").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
                        }
                        else
                        {
                            event.getToolTip().add(Component.literal(" "+index+".").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("击杀").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                    append(Name[i]).
                                    append(Component.literal(" (").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal(ClientUtils.DailyMission[i]+"/"+data.getInt(string)+")").withStyle(ChatFormatting.RESET)).
                                    append(Component.literal("?").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)));
                        }
                        index++;
                    }
                }
            }
            if(event.getItemStack().is(Moditems.BrewingNote.get()))
            {
                Component[] Name = {
                        Component.literal("平原根源").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN),
                        Component.literal("森林根源").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN),
                        Component.literal("湖泊根源").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE),
                        Component.literal("火山根源").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW),
                        Component.literal("冰川根源").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                        Component.literal("天空碎片").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSky),
                        Component.literal("唤魔之源").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana),
                        Component.literal("下界能量").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                };
                String[] DataName = {
                        "PlainBrewingExp",
                        "ForestBrewingExp",
                        "LakeBrewingExp",
                        "VolcanoBrewingExp",
                        "SnowBrewingExp",
                        "SkyBrewingExp",
                        "EvokerBrewingExp",
                        "NetherBrewingExp"
                };
                CompoundTag BrewingData = event.getItemStack().getOrCreateTagElement(Utils.MOD_ID);
                event.getToolTip().add(Component.literal(" "));
                event.getToolTip().add(Component.literal("~当前酿造等阶: ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                        append(Utils.BrewingLevelName[Compute.BrewingLevel(event.getItemStack())]));
                Compute.DescriptionDash(event.getToolTip(),ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
                event.getToolTip().add(Component.literal("·酿造经验明细:").withStyle(ChatFormatting.UNDERLINE));
                for(int i = 0; i < 8; i++)
                {
                    if(BrewingData.contains(DataName[i]))
                        event.getToolTip().add(Component.literal("·").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                                append(Name[i]).
                                append(Component.literal("酿造经验: ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).
                                append(Component.literal(String.valueOf(BrewingData.getInt(DataName[i]))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    else
                        event.getToolTip().add(Component.literal("·").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                                append(Name[i]).
                                append(Component.literal("酿造经验: ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).
                                append(Component.literal(String.valueOf(0)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
                Compute.DescriptionDash(event.getToolTip(),ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
            }
            if(event.getItemStack().is(Moditems.Main1necklaceGem.get()) || event.getItemStack().is(Moditems.Main1BeltGem.get()) ||
                event.getItemStack().is(Moditems.Main1BraceletGem.get()) || event.getItemStack().is(Moditems.Main1HandGem.get())
                    || event.getItemStack().is(Moditems.NetherGem.get())){
                event.getToolTip().add(Component.literal(" "));
                for(int i = 0; i < Utils.AttributeName.length; i++){
                    if(data.contains(Utils.AttributeName[i])){
                        List<Component> components = event.getToolTip();
                        if(Utils.AttributeName[i].equals("GemSAttack")) Compute.EmojiDescriptionExAttackDamage(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSBreakDefence")) Compute.EmojiDescriptionBreakDefence(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSCritRate")) Compute.EmojiDescriptionCritRate(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSCritDamage")) Compute.EmojiDescriptionCritDamage(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSManaDamage")) Compute.EmojiDescriptionManaAttackDamage(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSManaBreakDefence")) Compute.EmojiDescriptionManaBreakDefence(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSManaReply")) Compute.EmojiDescriptionManaRecover(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSCoolDown")) Compute.EmojiDescriptionCoolDown(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSHealSteal")) Compute.EmojiDescriptionHealSteal(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSDefence")) Compute.EmojiDescriptionDefence(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSManaDefence"))  Compute.EmojiDescriptionManaDefence(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSSpeed")) Compute.EmojiDescriptionMovementSpeed(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSMaxMana")) Compute.EmojiDescriptionMaxMana(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSMaxHeal"))  Compute.EmojiDescriptionMaxHealth(components,data.getDouble(Utils.AttributeName[i]));
                        if(Utils.AttributeName[i].equals("GemSExpImprove"))  Compute.EmojiDescriptionExpUp(components,data.getDouble(Utils.AttributeName[i]));
                    }
                }
                if (event.getItemStack().is(Moditems.NetherGem.get())) {
                    event.getToolTip().add(Component.literal(" "));
                    event.getToolTip().add(Component.literal("等级需求:50").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE));
                    event.getToolTip().add(Component.literal("Curios-MainStoryIII").withStyle(Utils.styleOfNether).withStyle(ChatFormatting.BOLD));

                }
                else {
                    event.getToolTip().add(Component.literal(" "));
                    event.getToolTip().add(Component.literal("等级需求:30").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.UNDERLINE));
                    event.getToolTip().add(Component.literal("Curios-MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));
                }
            }
            if (data.contains("Number")) {
                int Number = data.getInt("Number");
                if (Number < 10) {
                    event.getToolTip().add(Component.literal("∫维瑞阿契第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                            append(Component.literal(""+Number).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)).
                            append(Component.literal("件").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.BOLD).
                            append(equip.getDisplayName()));
                }
                else if (Number < 100) {
                    event.getToolTip().add(Component.literal("∫维瑞阿契第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                            append(Component.literal(""+Number).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                            append(Component.literal("件").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.BOLD).
                            append(equip.getDisplayName()));
                }
                else if (Number < 1000) {
                    event.getToolTip().add(Component.literal("∫维瑞阿契第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                            append(Component.literal(""+Number).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                            append(Component.literal("件").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.BOLD).
                            append(equip.getDisplayName()));
                }
                else {
                    event.getToolTip().add(Component.literal("∫维瑞阿契第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                            append(Component.literal(""+Number).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).
                            append(Component.literal("件").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.BOLD).
                            append(equip.getDisplayName()));
                }
            }
            if (equip.is(Moditems.ForestBossSword.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                        append(Component.literal("森林次元熵").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfHealth)).
                        append(Component.literal("为:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Forest).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfHealth)));
            }
            if (equip.is(Moditems.VolcanoBossSword.get())) {
                List<Component> components = event.getToolTip();
                components.add(Component.literal("∰当前").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                        append(Component.literal("熔岩次元熵").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfVolcano)).
                        append(Component.literal("为:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(" " + ClientUtils.Entropy.Volcano).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfVolcano)));
            }
            if (equip.is(Moditems.SBoots.get()) || equip.is(Moditems.SLeggings.get())
                    || equip.is(Moditems.SChest.get()) || equip.is(Moditems.SHelmet.get())
                    || equip.is(Moditems.ISArmorBoots.get()) || equip.is(Moditems.ISArmorLeggings.get())
                    || equip.is(Moditems.ISArmorChest.get()) || equip.is(Moditems.ISArmorHelmet.get())) {
                List<Component> components = event.getToolTip();
                if (equip.is(Moditems.SBoots.get()) || equip.is(Moditems.ISArmorBoots.get()) ) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider)));
                }
                else if (equip.is(Moditems.SLeggings.get()) || equip.is(Moditems.ISArmorLeggings.get())) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("护腿").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider)));
                }
                else if (equip.is(Moditems.SChest.get()) || equip.is(Moditems.ISArmorChest.get())) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("胸甲").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider)));
                }
                else if (equip.is(Moditems.SHelmet.get()) || equip.is(Moditems.ISArmorHelmet.get())) {
                    components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider)));
                }
                Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfSpider,ChatFormatting.WHITE);
                Compute.DescriptionOfBasic(components);
                int SIndex = data.getInt("SIndex");
                int Rate = (equip.is(Moditems.ISArmorBoots.get()) || equip.is(Moditems.ISArmorLeggings.get())
                        || equip.is(Moditems.ISArmorChest.get()) || equip.is(Moditems.ISArmorHelmet.get())) ? 2 : 1;
                for (int i = 1; i <= 5; i++) {
                    String IsSunPower = "#Slot#"+i+"#SunPower";
                    String IsLakePower = "#Slot#"+i+"#LakePower";
                    String IsVolcanoPower = "#Slot#"+i+"#VolcanoPower";
                    String IsSnowPower = "#Slot#"+i+"#SnowPower";
                    String IsSkyPower = "#Slot#"+i+"#SkyPower";
                    String IsManaPower = "#Slot#"+i+"#ManaPower";
                    String IsNetherPower = "#Slot#"+i+"#NetherPower";
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
                        }
                        else if (data.getInt(IsSunPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsSunPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.Health + " 最大生命值").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN).
                                append(Component.literal(String.format(" +%.1f",data.getFloat(IsSunPower) * Rate * 2)+Star).withStyle(ChatFormatting.RESET).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    }
                    else
                    if (data.contains(IsLakePower)) {
                        if (data.getInt(IsLakePower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        }
                        else if (data.getInt(IsLakePower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsLakePower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.CoolDown + " 冷却缩减").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA).
                                append(Component.literal(String.format(" +%.1f%%",data.getFloat(IsLakePower) * Rate * 1.5/2)+Star).withStyle(ChatFormatting.RESET).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    }
                    else
                    if (data.contains(IsVolcanoPower)) {
                        if (data.getInt(IsVolcanoPower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        }
                        else if (data.getInt(IsVolcanoPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsVolcanoPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.Sword + " 额外攻击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW).
                                append(Component.literal(String.format(" +%.1f",data.getFloat(IsVolcanoPower) * Rate * 1.5)+Star).withStyle(ChatFormatting.RESET).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    }
                    else
                    if (data.contains(IsSnowPower)) {
                        if (data.getInt(IsSnowPower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        }
                        else if (data.getInt(IsSnowPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsSnowPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.CritDamage + " 暴击伤害").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE).
                                append(Component.literal(String.format(" +%.1f%%",data.getFloat(IsSnowPower) * Rate * 1.5)+Star).withStyle(ChatFormatting.RESET).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    }
                    else
                    if (data.contains(IsSkyPower)) {
                        if (data.getInt(IsSkyPower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        }
                        else if (data.getInt(IsSkyPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsSkyPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.CritRate + " 暴击几率").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).
                                append(Component.literal(String.format(" +%.1f%%",data.getFloat(IsSkyPower) * Rate * 1.5/2)+Star).withStyle(ChatFormatting.RESET).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    }
                    else
                    if (data.contains(IsManaPower)) {
                        if (data.getInt(IsManaPower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        }
                        else if (data.getInt(IsManaPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsManaPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.Mana + " 魔法攻击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).
                                append(Component.literal(String.format(" +%.1f",data.getFloat(IsManaPower) * Rate * 1.5)+Star).withStyle(ChatFormatting.RESET).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    }
                    else
                    if (data.contains(IsNetherPower)) {
                        if (data.getInt(IsNetherPower) >= 9) {
                            chatFormatting0 = chatFormatting3;
                            Star = "✦";
                        }
                        else if (data.getInt(IsNetherPower) >= 8) chatFormatting0 = chatFormatting2;
                        else if (data.getInt(IsNetherPower) >= 7) chatFormatting0 = chatFormatting1;
                        components.add(Component.literal(i + "." + Utils.Emoji.HealSteal + " 生命偷取").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED).
                                append(Component.literal(String.format(" +%.1f%%",data.getFloat(IsNetherPower) * Rate * 1.5/4)+Star).withStyle(ChatFormatting.RESET).withStyle(chatFormatting0)).
                                append(Component.literal(Index).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    }
                    else {
                        components.add(Component.literal(i+".[待涂附]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
                    }
                }
                Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfSpider,ChatFormatting.WHITE);
                components.add(Component.literal(" "));
                if (Rate == 1) {
                    components.add(Component.literal("SArmor-I").withStyle(Utils.styleOfSpider).withStyle(ChatFormatting.ITALIC));
                    components.add(Component.literal("MainStoryII").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
                }
                else {
                    components.add(Component.literal("SArmor-X").withStyle(Utils.styleOfSpider).withStyle(ChatFormatting.ITALIC));
                    components.add(Component.literal("Intensified-Spider").withStyle(Utils.styleOfVolcano).withStyle(ChatFormatting.ITALIC));
                }
            }
            if (equip.is(Moditems.GoldSword0.get())) {
                List<Component> components = event.getToolTip();
                Compute.ForgingHoverName(equip,Component.literal("黄金匕首").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
                components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("匕首").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
                Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
                Compute.DescriptionOfBasic(components);

                Compute.BasicSwordDescription(components,0,0,0,0,0,0);
                Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
                Compute.DescriptionOfAddtion(components);
                components.add(Component.literal("基于银行内的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                        append(Component.literal("VB余额").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                        append(Component.literal("提供至多").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("300额外攻击力").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)).
                        append(Component.literal("与").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("300%移动速度").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
                components.add(Component.literal("·当前攻击力加成:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW).
                        append(Component.literal(String.format("%.2f",ClientUtils.VBNUM / 10000)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                components.add(Component.literal("·当前移动速度加成:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN).
                        append(Component.literal(String.format("%.2f%%",ClientUtils.VBNUM / 10000)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
                components.add(Component.literal("Gold-Knife-0").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
                components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
            }
            if(Utils.MainHandTag.containsKey(event.getItemStack().getItem()) && !event.getItemStack().is(Moditems.BrewingNote.get())) {
                int killCount = event.getItemStack().getTagElement(Utils.MOD_ID).getInt("KillCount");
                int Rate = (int) (killCount*1.0/100000 * 58);
                StringBuilder Bar1 = new StringBuilder();
                StringBuilder Bar2 = new StringBuilder();
                Rate = Math.min(Rate,58);
                for (int i = 0; i < Rate; i++) Bar1.append("|");
                for (int i = 0; i < 58 - Rate; i++) Bar2.append("|");
                event.getToolTip().add(Component.literal("—————武器熟练度—————").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
                if (Rate < 8) {
                    event.getToolTip().add(Component.literal("▶"+Bar1).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD).
                            append(Component.literal(Bar2.toString()).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    event.getToolTip().add(Component.literal("·熟练度值:"+killCount+"/100000").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));

                }
                else if (Rate < 16) {
                    event.getToolTip().add(Component.literal("▶"+Bar1).withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                            append(Component.literal(Bar2.toString()).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    event.getToolTip().add(Component.literal("·熟练度值:"+killCount+"/100000").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
                }
                else if (Rate < 30) {
                    event.getToolTip().add(Component.literal("▶"+Bar1).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD).
                            append(Component.literal(Bar2.toString()).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    event.getToolTip().add(Component.literal("·熟练度值:"+killCount+"/100000").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
                }
                else if (Rate < 45) {
                    event.getToolTip().add(Component.literal("▶"+Bar1).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).
                            append(Component.literal(Bar2.toString()).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    event.getToolTip().add(Component.literal("·熟练度值:"+killCount+"/100000").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
                }
                else {
                    event.getToolTip().add(Component.literal("▶"+Bar1).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD).
                            append(Component.literal(Bar2.toString()).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
                    event.getToolTip().add(Component.literal("-熟练度值:"+killCount+"/100000").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC));
                }
                event.getToolTip().add(Component.literal("当前熟练度提升:").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
                if (killCount >= 100000) killCount = 100000;
                if (Utils.MainHandTag.containsKey(equip.getItem()) && Utils.BaseDamage.containsKey(equip.getItem())){
                    event.getToolTip().add(Component.literal("额外攻击:+").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD).
                            append(Component.literal(String.format("%.0f",Utils.BaseDamage.get(equip.getItem())*0.5*(killCount/100000.0))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
                if (Utils.MainHandTag.containsKey(equip.getItem()) && Utils.ManaDamage.containsKey(equip.getItem())){
                    event.getToolTip().add(Component.literal("魔法攻击:+").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).
                            append(Component.literal(String.format("%.0f",Utils.ManaDamage.get(equip.getItem())*0.5*(killCount/100000.0))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
            }
            if(data.contains("Slot") || data.contains("Gems1")) event.getToolTip().add(Component.literal(" "));
            if(data.contains("Slot") || data.contains("Gems1")) event.getToolTip().add(Component.literal("—————宝石孔位信息—————").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
            if(data.contains("Gems1") && data.getString("Gems1").equals("skyGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈天空宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems1") && data.getString("Gems1").equals("EvokerGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈唤魔宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems1") && data.getString("Gems1").equals("plainGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈平原宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems1") && data.getString("Gems1").equals("forestGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈森林宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems1") && data.getString("Gems1").equals("lakeGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈湖泊宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_BLUE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems1") && data.getString("Gems1").equals("volcanoGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈火山宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems1") && data.getString("Gems1").equals("snowGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈冰川宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));

            if(data.contains("Gems2") && data.getString("Gems2").equals("skyGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈天空宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems2") && data.getString("Gems2").equals("EvokerGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈唤魔宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems2") && data.getString("Gems2").equals("plainGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈平原宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems2") && data.getString("Gems2").equals("forestGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈森林宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems2") && data.getString("Gems2").equals("lakeGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈湖泊宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_BLUE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems2") && data.getString("Gems2").equals("volcanoGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈火山宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems2") && data.getString("Gems2").equals("snowGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈冰川宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));

            if(data.contains("Gems3") && data.getString("Gems3").equals("skyGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈天空宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems3") && data.getString("Gems3").equals("EvokerGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈唤魔宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems3") && data.getString("Gems3").equals("plainGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈平原宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems3") && data.getString("Gems3").equals("forestGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈森林宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems3") && data.getString("Gems3").equals("lakeGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈湖泊宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_BLUE)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems3") && data.getString("Gems3").equals("volcanoGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈火山宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems3") && data.getString("Gems3").equals("snowGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈冰川宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));

            if(data.contains("MaxSlot") && data.contains("Slot")) {
                for(int i = 0; i < data.getInt("Slot") ; i++)
                {
                    event.getToolTip().add(Component.literal("[未镶嵌]").withStyle(ChatFormatting.GRAY));
                }
            }
            if(data.contains("Slot") || data.contains("Gems1")) event.getToolTip().add(Component.literal("—————宝石属性总和—————").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
            if(data.contains("Slot") || data.contains("Gems1")) {
                List<Component> components = event.getToolTip();
                if(Compute.ItemBaseDamageGems(data) > 0) Compute.EmojiDescriptionExAttackDamage(components,Compute.ItemBaseDamageGems(data));
                if(Compute.ItemSpeedUpGems(data) > 0) Compute.EmojiDescriptionMovementSpeed(components,Compute.ItemSpeedUpGems(data));
                if(Compute.ItemManaDamageGems(data) > 0) Compute.EmojiDescriptionManaAttackDamage(components,Compute.ItemManaDamageGems(data));
                if(Compute.ItemManaReplyGems(data) > 0) Compute.EmojiDescriptionManaRecover(components,Compute.ItemManaReplyGems(data));
                if(Compute.ItemCritRateGems(data) > 0) Compute.EmojiDescriptionCritRate(components,Compute.ItemCritRateGems(data));
                if(Compute.ItemCritDamageGems(data) > 0) Compute.EmojiDescriptionCritDamage(components,Compute.ItemCritDamageGems(data));
                if(Compute.ItemCoolDownGems(data) > 0) Compute.EmojiDescriptionCoolDown(components,Compute.ItemCoolDownGems(data));
                if(Compute.ItemHealUpGems(data) > 0)  Compute.EmojiDescriptionMaxHealth(components,Compute.ItemHealUpGems(data));
                if(Compute.ItemHealReplyGems(data) > 0) Compute.EmojiDescriptionHealthRecover(components,Compute.ItemHealReplyGems(data));
                if(Compute.ItemDefenceGems(data) > 0) Compute.EmojiDescriptionDefence(components,Compute.ItemDefenceGems(data));
            }

            if(data.contains("Forging")) {
                event.getToolTip().add(Component.literal("—————强化属性信息—————").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));
                event.getToolTip().add(Component.literal("强化属性:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.WHITE));
                if(Utils.BaseDamage.containsKey(equip.getItem())) {
                    float DamageUp = Utils.BaseDamage.get(equip.getItem()) * (data.getInt("Forging") / 20.0F);
                    Compute.EmojiDescriptionBaseAttackDamage(event.getToolTip(),DamageUp);
                }
                if(Utils.ManaDamage.containsKey(equip.getItem())){
                    float DamageUp = Utils.ManaDamage.get(equip.getItem()) * (data.getInt("Forging") / 20.0F);
                    Compute.EmojiDescriptionManaAttackDamage(event.getToolTip(),DamageUp);
                }
                if(Utils.Defence.containsKey(equip.getItem())) {
                    float DefenceUp = Utils.Defence.get(equip.getItem()) * (data.getInt("Forging") / 20.0F);
                    Compute.EmojiDescriptionDefence(event.getToolTip(),DefenceUp);
                }
            }

            if (data.contains("Owner")) {
                event.getToolTip().add(Component.literal("Owner:"+data.getString("Owner")).withStyle(ChatFormatting.AQUA));
            }


/*            if (event.getEntity().isShiftKeyDown()) {
                if (equip.is(Moditems.ForestBossPocket.get())) {
                    List<Component> components = event.getToolTip();
                }
            }*/
        }
    }
}

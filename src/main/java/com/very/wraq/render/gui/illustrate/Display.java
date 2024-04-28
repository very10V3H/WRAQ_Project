package com.very.wraq.render.gui.illustrate;

import com.very.wraq.blocks.blocks.ForgeRecipe;
import com.very.wraq.blocks.blocks.InjectRecipe;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class Display {
    public static String DisplayString = "Display";

    @SubscribeEvent
    public static void DisplayToolTipEvent(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        CompoundTag data = itemStack.getTagElement(Utils.MOD_ID);
        List<Component> components = event.getToolTip();
        if (data != null && data.contains(DisplayString)) {
            components.add(Component.literal(" 获取方式:").withStyle(ChatFormatting.AQUA));

            if (InjectRecipe.injectingRecipeMap.isEmpty()) InjectRecipe.setInjectingRecipeMap();
            if (InjectRecipe.containItem(item)) components.add(Component.literal(" - 使用灌注升级"));

            if (ForgeRecipe.ForgedItemList.isEmpty()) ForgeRecipe.setForgedItemList();
            if (ForgeRecipe.ForgedItemList.contains(item)) components.add(Component.literal(" - 使用锻造图锻造"));

            if ((new ArrayList<>(){{
                add(ModItems.Sword1.get());
                add(ModItems.Sword2.get());
                add(ModItems.randomsword.get());
                add(ModItems.Extraction.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 测试物品"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.Knife.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 从来自very_H的一封信获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.PlainSword0.get());
                add(ModItems.PlainBow0.get());
                add(ModItems.PlainSceptre0.get());
                add(ModItems.PlainBracelet.get());
                add(ModItems.PlainManaBook.get());
                add(ModItems.PlainPower.get());
                add(ModItems.PlainArmorHelmet.get());
                add(ModItems.PlainArmorChest.get());
                add(ModItems.PlainArmorLeggings.get());
                add(ModItems.PlainArmorBoots.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于平原铁匠铺兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.ForestSword0.get());
                add(ModItems.ForestBow0.get());
                add(ModItems.ForestBracelet.get());
                add(ModItems.ForestManaBook.get());
                add(ModItems.ForestPower.get());
                add(ModItems.ForestArmorHelmet.get());
                add(ModItems.ForestArmorChest.get());
                add(ModItems.ForestArmorLeggings.get());
                add(ModItems.ForestArmorBoots.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于护林人小屋兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.LakeSword0.get());
                add(ModItems.LakeBracelet.get());
                add(ModItems.LakeManaBook.get());
                add(ModItems.LakePower.get());
                add(ModItems.LakeArmorHelmet.get());
                add(ModItems.LakeArmorChest.get());
                add(ModItems.LakeArmorLeggings.get());
                add(ModItems.LakeArmorBoots.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城高塔0层兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.VolcanoSword0.get());
                add(ModItems.VolcanoBow0.get());
                add(ModItems.VolcanoBracelet.get());
                add(ModItems.VolcanoManaBook.get());
                add(ModItems.VolcanoPower.get());
                add(ModItems.VolcanoArmorHelmet.get());
                add(ModItems.VolcanoArmorChest.get());
                add(ModItems.VolcanoArmorLeggings.get());
                add(ModItems.VolcanoArmorBoots.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城高塔0层兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.MineSword0.get());
                add(ModItems.MineBracelet.get());
                add(ModItems.MineBow0.get());
                add(ModItems.MineShield.get());
                add(ModItems.MineArmorHelmet.get());
                add(ModItems.MineArmorChest.get());
                add(ModItems.MineArmorLeggings.get());
                add(ModItems.MineArmorBoots.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城高塔0层兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.MineHat.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于铁铁铁铁头商人处使用金币购买"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.FieldSword0.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城高塔0层兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SnowSword0.get());
                add(ModItems.SnowBracelet.get());
                add(ModItems.SnowManaBook.get());
                add(ModItems.SnowPower.get());
                add(ModItems.SnowShield.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城高塔0层兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.PlainRing.get());
                add(ModItems.ForestRing.get());
                add(ModItems.LakeRing.get());
                add(ModItems.VolcanoRing.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城饰品商人处兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.PlainCord.get());
                add(ModItems.ForestCord.get());
                add(ModItems.LakeCord.get());
                add(ModItems.VolcanoCord.get());
                add(ModItems.PlainForestCord.get());
                add(ModItems.LakeVolcanoCord.get());
                add(ModItems.FinalCord.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于主城兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SkyBow.get());
                add(ModItems.SkyBracelet.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城高塔顶层兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.EvokerSword.get());
                add(ModItems.EvokerBook0.get());
                add(ModItems.EvokerBook1.get());
                add(ModItems.EvokerBook2.get());
                add(ModItems.EvokerBook3.get());
                add(ModItems.LifeManaArmorHelmet.get());
                add(ModItems.LifeManaArmorChest.get());
                add(ModItems.LifeManaArmorLeggings.get());
                add(ModItems.LifeManaArmorBoots.get());
                add(ModItems.ObsiManaArmorHelmet.get());
                add(ModItems.ObsiManaArmorChest.get());
                add(ModItems.ObsiManaArmorLeggings.get());
                add(ModItems.ObsiManaArmorBoots.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城炼金术士处兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.IslandHelmetForgeDraw.get());
                add(ModItems.IslandChestForgeDraw.get());
                add(ModItems.IslandLeggingsForgeDraw.get());
                add(ModItems.IslandBootsForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀唤雷守卫概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SkyHelmetForgeDraw.get());
                add(ModItems.SkyChestForgeDraw.get());
                add(ModItems.SkyLeggingsForgeDraw.get());
                add(ModItems.SkyBootsForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀天空城的不速之客概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.PlainSceptre4.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 在灌注台使用阳光聚合物灌注平原权杖III"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.ManaSword.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城下界征讨协会兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.ManaSword.get());
                add(ModItems.WitherBonePower.get());
                add(ModItems.PigLinPower.get());
                add(ModItems.WitherBoneMealPower.get());
                add(ModItems.NetherPower.get());
                add(ModItems.MagmaPower.get());
                add(ModItems.QuartzSword.get());
                add(ModItems.QuartzSabre.get());
                add(ModItems.WitherSword0.get());
                add(ModItems.WitherBow0.get());
                add(ModItems.MagmaSceptre0.get());
                add(ModItems.NetherShield.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城下界征讨协会兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.NetherHelmetForgeDraw.get());
                add(ModItems.NetherManaHelmetForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀下界凋零骷髅概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.NetherChestForgeDraw.get());
                add(ModItems.NetherManaChestForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀下界骷髅概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.NetherLeggingsForgeDraw.get());
                add(ModItems.NetherManaLeggingsForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀下界熔岩能量概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.NetherBootsForgeDraw.get());
                add(ModItems.NetherManaBootsForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀下界猪灵概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.KazeBootsForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀狂风概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SeaSwordForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀神殿守卫几率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.BlackForestSwordForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀脆弱的灵魂几率获取"));
            }


            if ((new ArrayList<>(){{
                add(ModItems.SeaBowForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀脆弱的灵魂或神殿守卫概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.KazeSwordForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀狂风几率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SHelmet.get());
                add(ModItems.SChest.get());
                add(ModItems.SLeggings.get());
                add(ModItems.SBoots.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城微光研究所兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.GoldSword0.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 暂未开放"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.CodeSceptre.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 已过时，不建议制作"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.BlackForestSword4.get());
                add(ModItems.SeaSword4.get());
                add(ModItems.ManaSword1.get());
                add(ModItems.SnowSword4.get());
                add(ModItems.ForestSword4.get());
                add(ModItems.VolcanoSword4.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 完成对应的回忆，获取激化材料后灌注而成"));
            }


            if ((new ArrayList<>(){{
                add(ModItems.ForestBossSwordForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 使用森罗次元口袋概率获取"));
            }


            if ((new ArrayList<>(){{
                add(ModItems.VolcanoBossSwordForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 使用熔岩次元口袋概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SakuraSwordForgeDraw.get());
                add(ModItems.SakuraBowForgingDraw.get());
                add(ModItems.SakuraCoreForgingDraw.get());
                add(ModItems.SakuraArmorHelmet.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 使用樱花礼盒概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.WheatArmorChest.get());
                add(ModItems.MinePantsForgingDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 使用稻草礼盒概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.LakeBossSwordForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 使用湖泊次元口袋概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SkyBossBowForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 使用天空次元口袋概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SoulSword.get());
                add(ModItems.SoulBow.get());
                add(ModItems.SoulSceptre.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于本源塔兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.PurpleIronPickaxe0.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于紫晶矿洞达到200击杀数获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.PurpleIronPickaxe1.get());
                add(ModItems.PurpleIronPickaxe2.get());
                add(ModItems.PurpleIronPickaxe3.get());
                add(ModItems.PurpleIronArmorHelmet.get());
                add(ModItems.PurpleIronArmorChest.get());
                add(ModItems.PurpleIronArmorLeggings.get());
                add(ModItems.PurpleIronArmorBoots.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于樱岛紫晶锻造所锻造"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.LeatherArmorHelmet.get());
                add(ModItems.LeatherArmorChest.get());
                add(ModItems.LeatherArmorLeggings.get());
                add(ModItems.LeatherArmorBoots.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于冰原村兑换（推荐使用下界熔岩能量来应对寒冷值问题）"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.IceBook.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于主城兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.IceBracelet.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 使用传说之证 + 冰霜骑士之心灌注"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.IceSwordForgeDraw.get());
                add(ModItems.IceBowForgeDraw.get());
                add(ModItems.IceSceptreForgeDraw.get());
                add(ModItems.IceHelmetForgeDraw.get());
                add(ModItems.IceChestForgeDraw.get());
                add(ModItems.IceLeggingsForgeDraw.get());
                add(ModItems.IceBootsForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 通关冰霜骑士副本概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.ShipSwordForgeDraw.get());
                add(ModItems.ShipBowForgeDraw.get());
                add(ModItems.ShipSceptreForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于废弃船厂钓鱼概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.NetherSceptreForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀下界怪物概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.GoldenShieldForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 通关突见忍副本概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.ManaShield.get());
                add(ModItems.ManaKnife.get());
                add(ModItems.EarthPower.get());
                add(ModItems.EarthManaArmorHelmet.get());
                add(ModItems.EarthManaArmorChest.get());
                add(ModItems.EarthManaArmorLeggings.get());
                add(ModItems.EarthManaArmorBoots.get());
                add(ModItems.BloodManaArmorHelmet.get());
                add(ModItems.BloodManaArmorChest.get());
                add(ModItems.BloodManaArmorLeggings.get());
                add(ModItems.BloodManaArmorBoots.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于樱岛旧世魔力研究所兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.TabooAttackLeggingsForgeDraw.get());
                add(ModItems.TabooManaBootsForgeDraw.get());
                add(ModItems.TabooSwiftHelmetForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于旧世复生魔王副本挖掘魔能方块概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.WitherBook.get());
                add(ModItems.EarthBook.get());
                add(ModItems.GoldenBook.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于主城兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.MoonShieldForgeDraw.get());
                add(ModItems.MoonKnifeForgeDraw.get());
                add(ModItems.MoonBookForgeDraw.get());
                add(ModItems.MoonSwordForgeDraw.get());
                add(ModItems.MoonBowForgeDraw.get());
                add(ModItems.MoonSceptreForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 使用尘月赠礼概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.DevilSword.get());
                add(ModItems.DevilBow.get());
                add(ModItems.DevilSceptre.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 使用魔王之血灌注"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.CastleSwordForgeDraw.get());
                add(ModItems.CastleBowForgeDraw.get());
                add(ModItems.CastleSceptreForgeDraw.get());
                add(ModItems.BeaconBow.get());
                add(ModItems.BlazeSword.get());
                add(ModItems.TreeSceptre.get());
                add(ModItems.CastleAttackHelmetForgeDraw.get());
                add(ModItems.CastleAttackChestForgeDraw.get());
                add(ModItems.CastleAttackLeggingsForgeDraw.get());
                add(ModItems.CastleAttackBootsForgeDraw.get());
                add(ModItems.CastleSwiftHelmetForgeDraw.get());
                add(ModItems.CastleSwiftChestForgeDraw.get());
                add(ModItems.CastleSwiftLeggingsForgeDraw.get());
                add(ModItems.CastleSwiftBootsForgeDraw.get());
                add(ModItems.CastleManaHelmetForgeDraw.get());
                add(ModItems.CastleManaChestForgeDraw.get());
                add(ModItems.CastleManaLeggingsForgeDraw.get());
                add(ModItems.CastleManaBootsForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于暗黑城堡大门兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.EndPower.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于终界研究者处兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.PurpleIronBow.get());
                add(ModItems.PurpleIronSword.get());
                add(ModItems.PurpleIronSceptre.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 通关紫晶骑士副本概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.LifeCrystal0.get());
                add(ModItems.WaterCrystal0.get());
                add(ModItems.FireCrystal0.get());
                add(ModItems.StoneCrystal0.get());
                add(ModItems.IceCrystal0.get());
                add(ModItems.WindCrystal0.get());
                add(ModItems.LightningCrystal0.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于主城兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.LifeElementBowForgeDraw.get());
                add(ModItems.LifeElementSwordForgeDraw.get());
                add(ModItems.LifeElementSceptreForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击败原初生机元素概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.WaterElementBowForgeDraw.get());
                add(ModItems.WaterElementSwordForgeDraw.get());
                add(ModItems.WaterElementSceptreForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击败原初碧水元素概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.FireElementBowForgeDraw.get());
                add(ModItems.FireElementSwordForgeDraw.get());
                add(ModItems.FireElementSceptreForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击败原初炽焰元素概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SpringAttackArmorHelmet.get());
                add(ModItems.SpringAttackArmorChest.get());
                add(ModItems.SpringAttackArmorLeggings.get());
                add(ModItems.SpringAttackArmorBoots.get());
                add(ModItems.SpringSwiftArmorHelmet.get());
                add(ModItems.SpringSwiftArmorChest.get());
                add(ModItems.SpringSwiftArmorLeggings.get());
                add(ModItems.SpringSwiftArmorBoots.get());
                add(ModItems.SpringManaArmorHelmet.get());
                add(ModItems.SpringManaArmorChest.get());
                add(ModItems.SpringManaArmorLeggings.get());
                add(ModItems.SpringManaArmorBoots.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 新春活动：击杀年兽概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.PlainCrest0.get());
                add(ModItems.PlainCrest1.get());
                add(ModItems.PlainCrest2.get());
                add(ModItems.PlainCrest3.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀平原僵尸概率获取 + 于纹章制作师兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.ForestCrest0.get());
                add(ModItems.ForestCrest1.get());
                add(ModItems.ForestCrest2.get());
                add(ModItems.ForestCrest3.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀森林僵尸/骷髅概率获取 + 于纹章制作师兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.LakeCrest0.get());
                add(ModItems.LakeCrest1.get());
                add(ModItems.LakeCrest2.get());
                add(ModItems.LakeCrest3.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀湖泊守卫者概率获取 + 于纹章制作师兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.VolcanoCrest0.get());
                add(ModItems.VolcanoCrest1.get());
                add(ModItems.VolcanoCrest2.get());
                add(ModItems.VolcanoCrest3.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀熔岩概率获取 + 于纹章制作师兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.MineCrest0.get());
                add(ModItems.MineCrest1.get());
                add(ModItems.MineCrest2.get());
                add(ModItems.MineCrest3.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀矿洞僵尸/骷髅概率获取 + 于纹章制作师兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SnowCrest0.get());
                add(ModItems.SnowCrest1.get());
                add(ModItems.SnowCrest2.get());
                add(ModItems.SnowCrest3.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀冰川探索者概率获取 + 于纹章制作师兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SkyCrest0.get());
                add(ModItems.SkyCrest1.get());
                add(ModItems.SkyCrest2.get());
                add(ModItems.SkyCrest3.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀天空城的不速之客概率获取 + 于纹章制作师兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.ManaCrest0.get());
                add(ModItems.ManaCrest1.get());
                add(ModItems.ManaCrest2.get());
                add(ModItems.ManaCrest3.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀森林唤魔者概率获取 + 于纹章制作师兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.PlainCrest4.get());
                add(ModItems.ForestCrest4.get());
                add(ModItems.LakeCrest4.get());
                add(ModItems.MineCrest4.get());
                add(ModItems.VolcanoCrest4.get());
                add(ModItems.SkyCrest4.get());
                add(ModItems.SnowCrest4.get());
                add(ModItems.ManaCrest4.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于纹章制作师兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.PlainAttackRing0.get());
                add(ModItems.PlainManaAttackRing0.get());
                add(ModItems.PlainHealthRing0.get());
                add(ModItems.PlainDefenceRing0.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 通关普莱尼副本概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.FantasyMedal.get());
                add(ModItems.FantasyBracelet.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 通过彩券获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.SpringRing0.get());
                add(ModItems.SpringHand0.get());
                add(ModItems.SpringBelt0.get());
                add(ModItems.SpringNecklace0.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 2024春节活动获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.Boss2AttackRing0.get());
                add(ModItems.Boss2AttackRing1.get());
                add(ModItems.Boss2AttackRing2.get());
                add(ModItems.Boss2AttackRing3.get());
                add(ModItems.Boss2ManaAttackRing0.get());
                add(ModItems.Boss2ManaAttackRing1.get());
                add(ModItems.Boss2ManaAttackRing2.get());
                add(ModItems.Boss2ManaAttackRing3.get());
                add(ModItems.Boss2DefenceRing0.get());
                add(ModItems.Boss2DefenceRing1.get());
                add(ModItems.Boss2DefenceRing2.get());
                add(ModItems.Boss2DefenceRing3.get());
                add(ModItems.Boss2HealthRing0.get());
                add(ModItems.Boss2HealthRing1.get());
                add(ModItems.Boss2HealthRing2.get());
                add(ModItems.Boss2HealthRing3.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于樱岛黄金商人处兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.EarthManaCurios.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀地灵概率获取（每名玩家仅会掉落一次）"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.BloodManaCurios.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀血灵概率获取（每名玩家仅会掉落一次）"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.MoonCurios.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 维瑞阿契开服满月赠礼"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.MoonBeltForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 使用尘月赠礼概率获得"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.ParkourGloves.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于主城跑酷商人处兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.BeaconBracelet.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀烽火台哨卫概率获取（每名玩家仅会获取一次）"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.BlazeBracelet.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀熔岩湖溢出物概率获取（每名玩家仅会获取一次）"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.TreeBracelet.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀古树魔能研究者概率获取（每名玩家仅会获取一次）"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.CastleNecklace.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 通过暗黑城堡一层副本概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.CastleBrooch.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 通过暗黑城堡二层副本概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.RubyNecklace.get());
                add(ModItems.SapphireNecklace.get());
                add(ModItems.FancySapphireNecklace.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 于天空城饰品商人处兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.StarBottleForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀星使概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.QingMingAttackRing.get());
                add(ModItems.QingMingManaRing.get());
                add(ModItems.QingMingBowRing.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 2024清明活动兑换"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.LifeHolyStone0.get());
                add(ModItems.LifeHolyStone1.get());
                add(ModItems.LifeHolyStone2.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀原初生机元素概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.WaterHolyStone0.get());
                add(ModItems.WaterHolyStone1.get());
                add(ModItems.WaterHolyStone2.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀原初碧水元素概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.FireHolyStone0.get());
                add(ModItems.FireHolyStone1.get());
                add(ModItems.FireHolyStone2.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀原初炽焰元素概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.StoneHolyStone0.get());
                add(ModItems.StoneHolyStone1.get());
                add(ModItems.StoneHolyStone2.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀原初层岩元素概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.IceHolyStone0.get());
                add(ModItems.IceHolyStone1.get());
                add(ModItems.IceHolyStone2.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀原初凛冰元素概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.WindHolyStone0.get());
                add(ModItems.WindHolyStone1.get());
                add(ModItems.WindHolyStone2.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀原初澄风元素概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.LightningHolyStone0.get());
                add(ModItems.LightningHolyStone1.get());
                add(ModItems.LightningHolyStone2.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀原初怒雷元素概率获取"));
            }

            if ((new ArrayList<>(){{
                add(ModItems.EndCuriosForgeDraw.get());
                add(ModItems.EndCurios1ForgeDraw.get());
            }}).contains(item)) {
                components.add(Component.literal(" - 击杀终界征讨者遗骸概率获取"));
            }

        }
    }

}

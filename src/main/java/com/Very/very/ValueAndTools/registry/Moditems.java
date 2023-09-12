package com.Very.very.ValueAndTools.registry;

import com.Very.very.Blocks.Brewing.*;
import com.Very.very.Blocks.Brewing.SolidifiedSouls.*;
import com.Very.very.Series.EndSeries.EventControl.BlackForestRecall.BlackForestRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.BlackForestRecall.BlackForestSword4;
import com.Very.very.Series.EndSeries.EventControl.BlackForestRecall.IntensifiedBlackForestSoul;
import com.Very.very.Series.EndSeries.EventControl.ForestRecall.ForestRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.ForestRecall.ForestSword4;
import com.Very.very.Series.EndSeries.EventControl.ForestRecall.IntensifiedForestSoul;
import com.Very.very.Series.EndSeries.EventControl.KazeRecall.IntensifiedKazeSoul;
import com.Very.very.Series.EndSeries.EventControl.KazeRecall.KazeRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.KazeRecall.KazeSword4;
import com.Very.very.Series.EndSeries.EventControl.LightningIslandRecall.*;
import com.Very.very.Series.EndSeries.EventControl.NetherRecall1.IntensifiedRuby;
import com.Very.very.Series.EndSeries.EventControl.NetherRecall1.ManaSword1;
import com.Very.very.Series.EndSeries.EventControl.NetherRecall1.RecallRuby;
import com.Very.very.Series.EndSeries.EventControl.SeaRecall.IntensifiedSeaSoul;
import com.Very.very.Series.EndSeries.EventControl.SeaRecall.SeaRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.SeaRecall.SeaSword4;
import com.Very.very.Series.EndSeries.EventControl.SnowRecall.IntensifiedSnowSoul;
import com.Very.very.Series.EndSeries.EventControl.SnowRecall.SnowRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.SnowRecall.SnowSword4;
import com.Very.very.Series.EndSeries.EventControl.SpiderRecall.*;
import com.Very.very.Series.EndSeries.EventControl.VolcanoRecall.VolcanoRecallSoul;
import com.Very.very.Series.EndSeries.EventControl.VolcanoRecall.VolcanoSword4;
import com.Very.very.Series.EndSeries.RecallBooks.*;
import com.Very.very.Series.EndSeries.RecallPiece;
import com.Very.very.Items.Animation.AnimatedItem;
import com.Very.very.Items.Drugs.drug0;
import com.Very.very.Items.DevelopmentTools.*;
import com.Very.very.Items.Explore.*;
import com.Very.very.Items.LevelReward.LevelReward.*;
import com.Very.very.Items.LevelReward.PotionPackets.*;
import com.Very.very.Items.LevelReward.VariousBag.BackPackTicket;
import com.Very.very.Items.LevelReward.VariousBag.GoldCoinBag;
import com.Very.very.Items.MainStory_1.*;
import com.Very.very.Items.MainStory_1.Mission.*;
import com.Very.very.Items.MobItem.*;
import com.Very.very.Items.Money.*;
import com.Very.very.Items.RandomGems.*;
import com.Very.very.Items.SkillItems.ID_Card;
import com.Very.very.Items.gems.*;
import com.Very.very.Items.ProfessionAndQuest.Profession_Barker;
import com.Very.very.Items.ProfessionAndQuest.Profession_Bow;
import com.Very.very.Items.ProfessionAndQuest.Profession_Sword;
import com.Very.very.Items.ProfessionAndQuest.Quest;
import com.Very.very.Items.Mission.Daily;
import com.Very.very.Items.Mission.IronLove;
import com.Very.very.Series.NetherSeries.Material.*;
import com.Very.very.Series.NetherSeries.Runes.NetherRune0;
import com.Very.very.Series.NetherSeries.Runes.NetherRune1;
import com.Very.very.Series.NetherSeries.Runes.NetherRune2;
import com.Very.very.Series.NetherSeries.Runes.NetherRune3;
import com.Very.very.Series.NetherSeries.Equip.*;
import com.Very.very.Series.NetherSeries.Equip.ForgingDraw.NetherBForgingDrawing;
import com.Very.very.Series.NetherSeries.Equip.ForgingDraw.NetherCForgingDrawing;
import com.Very.very.Series.NetherSeries.Equip.ForgingDraw.NetherHForgingDrawing;
import com.Very.very.Series.NetherSeries.Equip.ForgingDraw.NetherLForgingDrawing;
import com.Very.very.Series.NetherSeries.Power.MagmaPower;
import com.Very.very.Series.NetherSeries.Power.PiglinPower;
import com.Very.very.Series.NetherSeries.Power.WitherBoneMealPower;
import com.Very.very.Series.NetherSeries.Power.WitherBonePower;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Gems.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Runes.SnowRune0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Runes.SnowRune1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Runes.SnowRune2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Runes.SnowRune3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.Sword.SnowSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor.LakeArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword.LakeSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Field.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor.ForestArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.BossItems.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Rune.ForestRune0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Rune.ForestRune1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Rune.ForestRune2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Rune.ForestRune3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Sword.ForestSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Sword.ForestSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Sword.ForestSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Sword.ForestSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Mine.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor.PlainArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Runes_Plain.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sceptre.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sword.PlainSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sword.PlainSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sword.PlainSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sword.PlainSwrod1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Snow.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor.VolcanoArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.BossItems.*;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Rune.VolcanoRune0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Rune.VolcanoRune1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Rune.VolcanoRune2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Rune.VolcanoRune3;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Sword.VolcanoSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Sword.VolcanoSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Sword.VolcanoSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Sword.VolcanoSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_II.BlackForest.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.CodeMana.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Dimension.SilverFishSoul;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Books.EvokerBook0;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Books.EvokerBook1;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Books.EvokerBook2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Books.EvokerBook3;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Runes.ManaRune0;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Runes.ManaRune1;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Runes.ManaRune2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Runes.ManaRune3;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Kaze.Sword.KazeSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.Armor.LightningArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.LIForgingDraw.LABForgingDrawing;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.LIForgingDraw.LACForgingDrawing;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.LIForgingDraw.LAHForgingDrawing;
import com.Very.very.Series.OverWorldSeries.MainStory_II.LightningIsland.LIForgingDraw.LALForgingDrawing;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.LifeMana.LifeManaArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.ManaArmor.ObsiMana.ObsiManaArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword0;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword1;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sea.Sword.SeaSword3;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorBoots;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorChest;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorHelmet;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.Armor.SkyArmorLeggings;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.SkyForgingDraw.SkyBForgingDrawing;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.SkyForgingDraw.SkyCForgingDrawing;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.SkyForgingDraw.SkyHForgingDrawing;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Sky.SkyForgingDraw.SkyLForgingDrawing;
import com.Very.very.Projectile.Mana.Shoot;
import com.Very.very.Items.RewardPack.Boss1;
import com.Very.very.Items.RewardPack.Boss2;
import com.Very.very.Items.RewardPack.Main1Reward;
import com.Very.very.Items.RewardPack.SignInReward;
import com.Very.very.Series.OverWorldSeries.Forging.forgingstone0;
import com.Very.very.Series.OverWorldSeries.Forging.forgingstone1;
import com.Very.very.Series.OverWorldSeries.Forging.forgingstone2;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Spider.*;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Spider.Ointment.*;
import com.Very.very.Series.OverWorldSeries.VariousItem.FeiLeiShen;
import com.Very.very.Items.SkyCity.TicketToSkyCity;
import com.Very.very.Series.SakuraSeries.SakurMob.*;
import com.Very.very.Series.SakuraSeries.Scarecrow.*;
import com.Very.very.ValueAndTools.*;
import com.Very.very.Projectile.BowTest.BowSnow;
import com.Very.very.Projectile.BowTest.Mybow;
import com.Very.very.Items.Forging.SpeIronIngot;
import com.Very.very.Items.Forging.randomsword;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Render.Gui.TestAndHelper.SmartPhoneOpen;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Moditems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> Obsidian_INGOT = ITEMS.register("obsidian_ingot",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> PP = ITEMS.register("pp",
            ()->new Item(new Item.Properties()));
    public static final RegistryObject<Item> Sliver_BLOCK = ITEMS.register("sliver_block",
            ()->new BlockItem(ModBlocks.Sliver_Block.get(),new Item.Properties()));
    public static final RegistryObject<Item> HBOSSITEM = ITEMS.register("hboss",
            ()->new ForgeSpawnEggItem(EntityInit.HETITY,9577503,13423070,new Item.Properties()));
    public static final RegistryObject<Item> Sword1 = ITEMS.register("sword1",
            ()->new Sword1(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> Sword2 = ITEMS.register("sword2",
            ()->new Sword2(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> Main0 = ITEMS.register("main0",
            ()->new Main0(new Item.Properties()));
    public static final RegistryObject<Item> Main0_1 = ITEMS.register("main0_1",
            ()->new Main0_1(new Item.Properties()));
    public static final RegistryObject<Item> Main0_2 = ITEMS.register("main0_2",
            ()->new Main0_2(new Item.Properties()));
    public static final RegistryObject<Item> Main0_3 = ITEMS.register("main0_3",
            ()->new Main0_3(new Item.Properties()));
    public static final RegistryObject<Item> Main1_1 = ITEMS.register("main1_1",
            ()->new Main1_1(new Item.Properties()));
    public static final RegistryObject<Item> Main1_2 = ITEMS.register("main1_2",
            ()->new Main1_2(new Item.Properties()));
    public static final RegistryObject<Item> Main1_0 = ITEMS.register("main1_0",
            ()->new Main1_0(new Item.Properties()));
    public static final RegistryObject<Item> Main1_3 = ITEMS.register("main1_3",
            ()->new Main1_3(new Item.Properties()));
    public static final RegistryObject<Item> Main1_4 = ITEMS.register("main1_4",
            ()->new Main1_4(new Item.Properties()));
    public static final RegistryObject<Item> GoldCoin = ITEMS.register("gold_coin",
            ()->new GoldCoin(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SilverCoin = ITEMS.register("silver_coin",
            ()->new SilverCoin(new Item.Properties()));
    public static final RegistryObject<Item> SignInReset = ITEMS.register("signinreset",
            ()->new SignInReset(new Item.Properties()));
    public static final RegistryObject<Item> SignInGet = ITEMS.register("signinget",
            ()->new SignInGet(new Item.Properties()));
    public static final RegistryObject<Item> GetTime = ITEMS.register("gettime",
            ()->new GetTime(new Item.Properties()));
    public static final RegistryObject<Item> ItemIDCheck = ITEMS.register("idcheck",
            ()->new ItemIDCheck(new Item.Properties()));
    public static final RegistryObject<Item> SignInReward = ITEMS.register("signinreward",
            ()->new SignInReward(new Item.Properties()));
    public static final RegistryObject<Item> Note_0 = ITEMS.register("note_0",
            ()->new Note_0(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ExploreNote = ITEMS.register("explorenote",
            ()->new ExploreNote(new Item.Properties()));
    public static final RegistryObject<Item> Knife = ITEMS.register("knife",
            ()->new Knife(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> ForNew = ITEMS.register("fornew",
            ()->new ForNew(new Item.Properties()));
    public static final RegistryObject<Item> Piece = ITEMS.register("rune_piece",
            ()->new Piece(new Item.Properties()));
    public static final RegistryObject<Item> GreenRunes_0 = ITEMS.register("green_runes_0",
            ()->new GreenRunes_0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GreenRunes_1 = ITEMS.register("green_runes_1",
            ()->new GreenRunes_1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorPlain = ITEMS.register("armor1",
            ()->new MobArmor(25,25,2));
    public static final RegistryObject<Item> GreenRunes_2 = ITEMS.register("green_runes_2",
            ()->new GreenRunes_2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PlainSoul = ITEMS.register("plain_souls",
            ()->new PlainSoul(new Item.Properties()));
    public static final RegistryObject<Item> PlainRune = ITEMS.register("plain_runes",
            ()->new PlainRune(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> runes = ITEMS.register("runes",
            ()->new Profile(new Item.Properties()));
    public static final RegistryObject<Item> GreenRunes_3 = ITEMS.register("green_runes_3",
            ()->new GreenRunes_3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorForestSkeleton = ITEMS.register("armor2",
            ()->new MobArmor(50,25,5));
    public static final RegistryObject<Item> plainarmorboots = ITEMS.register("plainarmorboots",
            ()->new PlainArmorBoots(ItemMaterial.PlainMaterialBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> plainarmorleggings = ITEMS.register("plainarmorleggings",
            ()->new PlainArmorLeggings(ItemMaterial.PlainMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> plainarmorchest = ITEMS.register("plainarmorchest",
            ()->new PlainArmorChest(ItemMaterial.PlainMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> plainarmorhelmet = ITEMS.register("plainarmorhelmet",
            ()->new PlainArmorHelmet(ItemMaterial.PlainMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> PlainSword0 = ITEMS.register("plainsword0",
            ()->new PlainSword0(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> PlainSword1 = ITEMS.register("plainsword1",
            ()->new PlainSwrod1(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> PlainSword2 = ITEMS.register("plainsword2",
            ()->new PlainSword2(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> PlainSword3 = ITEMS.register("plainsword3",
            ()->new PlainSword3(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> ArmorForestZombie = ITEMS.register("armorforest2",
            ()->new MobArmor(100,25,8));
    public static final RegistryObject<Item> ArmorBlaze = ITEMS.register("armorblaze",
            ()->new MobArmor(200,25,18));
    public static final RegistryObject<Item> plaingems0 = ITEMS.register("plaingems0",
            ()->new plaingems0(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ArmorDrown = ITEMS.register("armordrown",
            ()->new MobArmor(200,25,12));
    public static final RegistryObject<Item> forestsword0 = ITEMS.register("forestsword0",
            ()->new ForestSword0(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> forestsword1 = ITEMS.register("forestsword1",
            ()->new ForestSword1(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> forestsword2 = ITEMS.register("forestsword2",
            ()->new ForestSword2(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> forestsword3 = ITEMS.register("forestsword3",
            ()->new ForestSword3(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> ForestSoul = ITEMS.register("forestsoul",
            ()->new ForestSoul(new Item.Properties()));
    public static final RegistryObject<Item> ForestRune = ITEMS.register("forestrune",
            ()->new ForestRune(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> forestarmorboots = ITEMS.register("forestarmorboots",
            ()->new ForestArmorBoots(ItemMaterial.ForestMaterialBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> forestarmorleggings = ITEMS.register("forestarmorleggings",
            ()->new ForestArmorLeggings(ItemMaterial.ForestMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> forestarmorchest = ITEMS.register("forestarmorchest",
            ()->new ForestArmorChest(ItemMaterial.ForestMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> forestarmorhelmet = ITEMS.register("forestarmorhelmet",
            ()->new ForestArmorHelmet(ItemMaterial.ForestMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> gemspiece = ITEMS.register("gemspiece",
            ()->new gemspiece(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> bossaward1 = ITEMS.register("bossaward1",
            ()->new Boss1(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> bossaward2 = ITEMS.register("bossaward2",
            ()->new Boss2(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> WaterSoul = ITEMS.register("watersoul",
            ()->new LakeSoul(new Item.Properties()));
    public static final RegistryObject<Item> WaterRune = ITEMS.register("waterrune",
            ()->new LakeRune(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VolcanoSoul = ITEMS.register("volcanosoul",
            ()->new VolcanoSoul(new Item.Properties()));
    public static final RegistryObject<Item> VolcanoRune = ITEMS.register("volcanorune",
            ()->new VolcanoRune(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> Main0_4 = ITEMS.register("main0_4",
            ()->new Main0_4(new Item.Properties()));
    public static final RegistryObject<Item> Main0_5 = ITEMS.register("main0_5",
            ()->new Main0_5(new Item.Properties()));
    public static final RegistryObject<Item> Main1_5 = ITEMS.register("main1_5",
            ()->new Main1_5(new Item.Properties()));
    public static final RegistryObject<Item> backspawn = ITEMS.register("backspawn",
            ()->new BackSpawn(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> main1reward = ITEMS.register("main1reward",
            ()->new Main1Reward(new Item.Properties()));
    public static final RegistryObject<Item> forestrune0 = ITEMS.register("forestrune0",
            ()->new ForestRune0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> forestrune1 = ITEMS.register("forestrune1",
            ()->new ForestRune1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> forestrune2 = ITEMS.register("forestrune2",
            ()->new ForestRune2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> forestrune3 = ITEMS.register("forestrune3",
            ()->new ForestRune3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> lakearmorboots = ITEMS.register("lakearmorboots",
            ()->new LakeArmorBoots(ItemMaterial.LakeMaterialBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> lakearmorleggings = ITEMS.register("lakearmorleggings",
            ()->new LakeArmorLeggings(ItemMaterial.LakeMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> lakearmorchest = ITEMS.register("lakearmorchest",
            ()->new LakeArmorChest(ItemMaterial.LakeMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> lakearmorhelmet = ITEMS.register("lakearmorhelmet",
            ()->new LakeArmorHelmet(ItemMaterial.LakeMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> volcanoarmorboots = ITEMS.register("volcanoarmorboots",
            ()->new VolcanoArmorBoots(ItemMaterial.VolcanoMaterialBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> volcanoarmorleggings = ITEMS.register("volcanoarmorleggings",
            ()->new VolcanoArmorLeggings(ItemMaterial.VolcanoMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> volcanoarmorchest = ITEMS.register("volcanoarmorchest",
            ()->new VolcanoArmorChest(ItemMaterial.VolcanoMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> volcanoarmorhelmet = ITEMS.register("volcanoarmorhelmet",
            ()->new VolcanoArmorHelmet(ItemMaterial.VolcanoMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> lakesword0 = ITEMS.register("lakesword0",
            ()->new LakeSword0(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> lakesword1 = ITEMS.register("lakesword1",
            ()->new LakeSword1(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> lakesword2 = ITEMS.register("lakesword2",
            ()->new LakeSword2(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> lakesword3 = ITEMS.register("lakesword3",
            ()->new LakeSword3(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> attributecheck = ITEMS.register("attributecheck",
            ()->new AttributeCheck(new Item.Properties()));
    public static final RegistryObject<Item> volcanosword0 = ITEMS.register("volcanosword0",
            ()->new VolcanoSword0(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> volcanosword1 = ITEMS.register("volcanosword1",
            ()->new VolcanoSword1(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> volcanosword2 = ITEMS.register("volcanosword2",
            ()->new VolcanoSword2(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> volcanosword3 = ITEMS.register("volcanosword3",
            ()->new VolcanoSword3(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> plaingems = ITEMS.register("plaingems",
            ()->new PlainRing(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> forestgems = ITEMS.register("forestgems",
            ()->new ForestRing(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> lakegems = ITEMS.register("lakegems",
            ()->new LakeRing(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> volcanogems = ITEMS.register("volcanogems",
            ()->new VolcanoGems(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> randomsword = ITEMS.register("randomsword",
            ()->new randomsword(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> tickettosky = ITEMS.register("tickettosky",
            ()->new TicketToSkyCity(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> quest = ITEMS.register("quest",
            ()->new Quest(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> hovertest = ITEMS.register("hovertest",
            ()->new HoverTest(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Mybow = ITEMS.register("mybow",
            ()->new Mybow(new Item.Properties().durability(500)));
    public static final RegistryObject<Item> ArrowItem = ITEMS.register("arrowitem",
            ()->new ArrowItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> forestbow = ITEMS.register("forestbow",
            ()->new ForestBow(new Item.Properties()));
    public static final RegistryObject<Item> minesword0 = ITEMS.register("minesword0",
            ()->new MineSword0(ItemTier.MaterialForPickaxe0,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> minesword1 = ITEMS.register("minesword1",
            ()->new MineSword1(ItemTier.MaterialForPickaxe1,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> minesword2 = ITEMS.register("minesword2",
            ()->new MineSword2(ItemTier.MaterialForPickaxe2,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> minesword3 = ITEMS.register("minesword3",
            ()->new MineSword3(ItemTier.MaterialForPickaxe3,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> ArmorMine = ITEMS.register("armormine",
            ()->new MobArmor(400,25,25));
    public static final RegistryObject<Item> spawn1 = ITEMS.register("spawn1",
            ()->new spawn1(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ArmorSnow = ITEMS.register("armorsnow",
            ()->new MobArmor(500,25,35));
    public static final RegistryObject<Item> ArmorField = ITEMS.register("armorfield",
            ()->new MobArmor(500,25,32));
    public static final RegistryObject<Item> profession_bow = ITEMS.register("profession_bow",
            ()->new Profession_Bow(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> profession_sword = ITEMS.register("profession_sword",
            ()->new Profession_Sword(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> profession_barker = ITEMS.register("profession_barker",
            ()->new Profession_Barker(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MineSoul = ITEMS.register("minesoul",
            ()->new MineSoul(new Item.Properties()));
    public static final RegistryObject<Item> MineSoul1 = ITEMS.register("minesoul1",
            ()->new MineSoul1(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MineRune = ITEMS.register("minerune",
            ()->new MineRune(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> FieldSoul = ITEMS.register("fieldsoul",
            ()->new FieldSoul(new Item.Properties()));
    public static final RegistryObject<Item> FieldRune = ITEMS.register("fieldrune",
            ()->new FieldRune(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> fieldsword0 = ITEMS.register("fieldsword0",
            ()->new FieldSword0(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> fieldsword1 = ITEMS.register("fieldsword1",
            ()->new FieldSword1(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> fieldsword2 = ITEMS.register("fieldsword2",
            ()->new FieldSword2(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> fieldsword3 = ITEMS.register("fieldsword3",
            ()->new FieldSword3(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> SnowSoul = ITEMS.register("snowsoul",
            ()->new SnowSoul(new Item.Properties()));
    public static final RegistryObject<Item> SnowRune = ITEMS.register("snowrune",
            ()->new SnowRune(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> snowsword0 = ITEMS.register("snowsword0",
            ()->new SnowSword0(ItemTier.MaterialForPickaxe3,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> snowsword1 = ITEMS.register("snowsword1",
            ()->new SnowSword1(ItemTier.MaterialForPickaxe3,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> snowsword2 = ITEMS.register("snowsword2",
            ()->new SnowSword2(ItemTier.MaterialForPickaxe3,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> snowsword3 = ITEMS.register("snowsword3",
            ()->new SnowSword3(ItemTier.MaterialForPickaxe3,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> Note_1 = ITEMS.register("note_1",
            ()->new Note_1(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Note_2 = ITEMS.register("note_2",
            ()->new Note_2(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Note_3 = ITEMS.register("note_3",
            ()->new Note_3(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Breathe = ITEMS.register("breathe",
            ()->new Breath(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FireResistent = ITEMS.register("fireresistent",
            ()->new FireResistent(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Climb = ITEMS.register("climb",
            ()->new Climb(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Extraction = ITEMS.register("extraction",
            ()->new Extraction(ItemTier.Extraction,2,1.0F));
    public static final RegistryObject<Item> SmartPhone = ITEMS.register("smartphone",
            ()->new SmartPhoneitem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> plainbow = ITEMS.register("plainbow",
            ()->new PlainBow(new Item.Properties()));
    public static final RegistryObject<Item> volcanobow = ITEMS.register("volcanobow",
            ()->new VolcanoBow(new Item.Properties()));
    public static final RegistryObject<Item> Security = ITEMS.register("security",
            ()->new openSecurity(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> resetSecurity = ITEMS.register("resetsecurity",
            ()->new ResetSecurity(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> main1crystal = ITEMS.register("main1crystal",
            ()->new main1crystal(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> main1Cord1 = ITEMS.register("main1cord1",
            ()->new main1Cord1(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> main1Cord2 = ITEMS.register("main1cord2",
            ()->new main1Cord2(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> main1Cord3 = ITEMS.register("main1cord3",
            ()->new main1Cord3(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> main1Cord4 = ITEMS.register("main1cord4",
            ()->new main1Cord4(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> drug0= ITEMS.register("drug0",
            ()->new drug0(new Item.Properties()));
    public static final RegistryObject<Item> main1Cord12 = ITEMS.register("main1cord12",
            ()->new main1Cord12(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> main1Cord34 = ITEMS.register("main1cord34",
            ()->new main1Cord34(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> main1cord5 = ITEMS.register("main1cord5",
            ()->new main1Cord5(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> NewCurios = ITEMS.register("newcurios",
            ()->new NewCurios(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SpeIron = ITEMS.register("speiron",
            ()->new SpeIronIngot(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> bowsnow = ITEMS.register("bowsnow",
            ()->new BowSnow(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> tick = ITEMS.register("tick",
            ()->new Tick(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> skybow = ITEMS.register("skybow",
            ()->new skybow(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ArmorSky = ITEMS.register("armorsky",
            ()->new MobArmor(100,25,40));
    public static final RegistryObject<Item> SkySoul = ITEMS.register("skysoul",
            ()->new skysoul(new Item.Properties()));
    public static final RegistryObject<Item> SkyRune = ITEMS.register("skyrune",
            ()->new skyrune(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> openslot = ITEMS.register("openslot",
            ()->new SlotOpen(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> skygem = ITEMS.register("skygem",
            ()->new SkyGem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EntityCopy = ITEMS.register("entitycopy",
            ()->new EntityTP(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BlockReset = ITEMS.register("blockreset",
            ()->new BlockPosReset(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Shoot = ITEMS.register("shoot",
            ()->new Shoot(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ArmorEvoker = ITEMS.register("armorevoker",
            ()->new MobArmor(500,25,45));
    public static final RegistryObject<Item> EvokerSoul = ITEMS.register("evokersoul",
            ()->new EvokerSoul(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> manabucket = ITEMS.register("manabucket",
            ()->new ManaBucket(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> EvokerRune = ITEMS.register("evokerrune",
            ()->new EvokerRune(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> manabalance_empty = ITEMS.register("manabalance_empty",
            ()->new ManaBalance_Empty(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> manabalance_filled = ITEMS.register("manabalance_filled",
            ()->new ManaBalance_Filled(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> evokersword = ITEMS.register("evokersword",
            ()->new EvokerSword(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> plainsceptre0 = ITEMS.register("plainsceptre0",
            ()->new PlainSceptre0(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> plainsceptre1 = ITEMS.register("plainsceptre1",
            ()->new PlainSceptre1(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> plainsceptre2 = ITEMS.register("plainsceptre2",
            ()->new PlainSceptre2(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> plainsceptre3 = ITEMS.register("plainsceptre3",
            ()->new PlainSceptre3(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> plainsceptre4 = ITEMS.register("plainsceptre4",
            ()->new PlainSceptre4(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> lifemanaarmorboots = ITEMS.register("lifemanaarmorboots",
            ()->new LifeManaArmorBoots(ItemMaterial.LifeManaBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> lifemanaarmorleggings = ITEMS.register("lifemanaarmorleggings",
            ()->new LifeManaArmorLeggings(ItemMaterial.LifeManaLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> lifemanaarmorchest = ITEMS.register("lifemanaarmorchest",
            ()->new LifeManaArmorChest(ItemMaterial.LifeManaChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> lifemanaarmorhelmet = ITEMS.register("lifemanaarmorhelmet",
            ()->new LifeManaArmorHelmet(ItemMaterial.LifeManaHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> obsimanaarmorboots = ITEMS.register("obsimanaarmorboots",
            ()->new ObsiManaArmorBoots(ItemMaterial.ObsiManaBoots, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> obsimanaarmorleggings = ITEMS.register("obsimanaarmorleggings",
            ()->new ObsiManaArmorLeggings(ItemMaterial.ObsiManaLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> obsimanaarmorchest = ITEMS.register("obsimanaarmorchest",
            ()->new ObsiManaArmorChest(ItemMaterial.ObsiManaChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> obsimanaarmorhelmet = ITEMS.register("obsimanaarmorhelmet",
            ()->new ObsiManaArmorHelmet(ItemMaterial.ObsiManaHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> plainmana = ITEMS.register("plainmana",
            ()->new PlainMana(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> forestmana = ITEMS.register("forestmana",
            ()->new ForestMana(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> lakemana = ITEMS.register("lakemana",
            ()->new LakeMana(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> volcanomana = ITEMS.register("volcanomana",
            ()->new VolcanoMana(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ForgingStone0 = ITEMS.register("forgingstone0",
            ()->new forgingstone0(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ForgingStone1 = ITEMS.register("forgingstone1",
            ()->new forgingstone1(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ForgingStone2 = ITEMS.register("forgingstone2",
            ()->new forgingstone2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> FeiLeiShen = ITEMS.register("feileishen",
            ()->new FeiLeiShen(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IndexCheck = ITEMS.register("indexcheck",
            ()->new InventoryCheck(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ruby = ITEMS.register("ruby",
            ()->new Ruby(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ArmorWitherSkeleton = ITEMS.register("armorwitherskeleton",
            ()->new MobArmor(200,250,50));
    public static final RegistryObject<Item> ManaSword = ITEMS.register("manasword",
            ()->new ManaSword(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> ArmorEvokerMaster = ITEMS.register("armorevokermaster",
            ()->new MobArmor(2000,100,60));
    public static final RegistryObject<Item> NetherSoul = ITEMS.register("nethersoul",
            ()->new NetherSoul(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> NetherRune = ITEMS.register("netherrune",
            ()->new NetherRune(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherSwordModel = ITEMS.register("netherswordmodel",
            ()->new NetherSwordModel(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> witherBone = ITEMS.register("witherbone",
            ()->new WitherBone(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> manaRune0 = ITEMS.register("manarune0",
            ()->new ManaRune0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> manaRune1 = ITEMS.register("manarune1",
            ()->new ManaRune1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> manaRune2 = ITEMS.register("manarune2",
            ()->new ManaRune2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> manaRune3 = ITEMS.register("manarune3",
            ()->new ManaRune3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorZombiePiglin = ITEMS.register("armorzombiepiglin",
            ()->new MobArmor(400,500,55));
    public static final RegistryObject<Item> PigLinSoul = ITEMS.register("piglinsoul",
            ()->new PiglinSoul(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ArmorNetherSkeletonHelmet = ITEMS.register("armornetherskeletonhelmet",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.HELMET,200,250,60));
    public static final RegistryObject<Item> ArmorNetherSkeletonChest = ITEMS.register("armornetherskeletonchest",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.CHESTPLATE,200,250,60));
    public static final RegistryObject<Item> ArmorNetherSkeletonLeggings = ITEMS.register("armornetherskeletonleggings",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.LEGGINGS,200,250,60));
    public static final RegistryObject<Item> ArmorNetherSkeletonBoots = ITEMS.register("armornetherskeletonboots",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.BOOTS,200,250,60));
    public static final RegistryObject<Item> netherbonemeal = ITEMS.register("netherbonemeal",
            ()->new NetherBoneMeal(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> NetherQuartz = ITEMS.register("netherquartz",
            ()->new NetherQuartz(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> WitherBonePower = ITEMS.register("witherbonepower",
            ()->new WitherBonePower(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PigLinPower = ITEMS.register("piglinpower",
            ()->new PiglinPower(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> WitherBoneMealPower = ITEMS.register("witherbonemealpower",
            ()->new WitherBoneMealPower(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EvokerGem = ITEMS.register("evokergem",
            ()->new EvokerGem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherPower = ITEMS.register("netherpower",
            ()->new NetherPower(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherBow = ITEMS.register("netherbow",
            ()->new NetherBow(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> QuartzSword = ITEMS.register("quartzsword",
            ()->new QuartzSword(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> QuartzSoul = ITEMS.register("quartzsoul",
            ()->new QuartzSoul(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> QuartzRune = ITEMS.register("quartzrune",
            ()->new QuartzRune(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PowerModel = ITEMS.register("powermodel",
            ()->new PowerModel(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> QuartzSabre = ITEMS.register("quartzsabre",
            ()->new QuartzSabre(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> quartzcheck = ITEMS.register("quartzcheck",
            ()->new quartzsabrecheck(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EvokerBook0 = ITEMS.register("evokerbook0",
            ()->new EvokerBook0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EvokerBook1 = ITEMS.register("evokerbook1",
            ()->new EvokerBook1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EvokerBook2 = ITEMS.register("evokerbook2",
            ()->new EvokerBook2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EvokerBook3 = ITEMS.register("evokerbook3",
            ()->new EvokerBook3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherSabreModel = ITEMS.register("nethersabremodel",
            ()->new NetherSabreModel(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ArmorGuardian = ITEMS.register("armorguardian",
            ()->new MobArmor(250,100,48));
    public static final RegistryObject<Item> SeaSoul = ITEMS.register("seasoul",
            ()->new SeaSoul(new Item.Properties()));
    public static final RegistryObject<Item> SeaRune = ITEMS.register("searune",
            ()->new SeaRune(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ArmorLZHelmet = ITEMS.register("armorlzhelmet",
            ()->new MobArmor(ItemMaterial.MaterialForArmor2, ArmorItem.Type.HELMET,100,250,50));
    public static final RegistryObject<Item> ArmorLZChest = ITEMS.register("armorlzchest",
            ()->new MobArmor(ItemMaterial.MaterialForArmor2, ArmorItem.Type.CHESTPLATE,100,250,50));
    public static final RegistryObject<Item> ArmorLZLeggings = ITEMS.register("armorlzleggings",
            ()->new MobArmor(ItemMaterial.MaterialForArmor2, ArmorItem.Type.LEGGINGS,100,250,50));
    public static final RegistryObject<Item> ArmorLZBoots = ITEMS.register("armorlzboots",
            ()->new MobArmor(ItemMaterial.MaterialForArmor2, ArmorItem.Type.BOOTS,100,250,50));
    public static final RegistryObject<Item> LightningSoul = ITEMS.register("lightningsoul",
            ()->new LightningSoul(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LightningRune = ITEMS.register("lightningrune",
            ()->new LightningRune(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PlainGem = ITEMS.register("plaingem",
            ()->new PlainGem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ForestGem = ITEMS.register("forestgem",
            ()->new ForestGem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LakeGem = ITEMS.register("lakegem",
            ()->new LakeGem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> VolcanoGem = ITEMS.register("volcanogem",
            ()->new VolcanoGem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SnowGem = ITEMS.register("snowgem",
            ()->new SnowGem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> IslandArmorHelmet = ITEMS.register("islandarmorhelmet",
            ()->new LightningArmorHelmet(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> IslandArmorChest = ITEMS.register("islandarmorchest",
            ()->new LightningArmorChest(ItemMaterial.IslandMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> IslandArmorLeggings = ITEMS.register("islandarmorleggings",
            ()->new LightningArmorLeggings(ItemMaterial.IslandMaterial, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> IslandArmorBoots = ITEMS.register("islandarmorboots",
            ()->new LightningArmorBoots(ItemMaterial.IslandMaterial, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> SeaSword0 = ITEMS.register("seasword0",
            ()->new SeaSword0(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> SeaSword1 = ITEMS.register("seasword1",
            ()->new SeaSword1(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> SeaSword2 = ITEMS.register("seasword2",
            ()->new SeaSword2(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> SeaSword3 = ITEMS.register("seasword3",
            ()->new SeaSword3(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> BlackForestSword0 = ITEMS.register("blackforestsword0",
            ()->new BlackForestSword0(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> BlackForestSword1 = ITEMS.register("blackforestsword1",
            ()->new BlackForestSword1(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> BlackForestSword2 = ITEMS.register("blackforestsword2",
            ()->new BlackForestSword2(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> BlackForestSword3 = ITEMS.register("blackforestsword3",
            ()->new BlackForestSword3(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> volcanoRune0 = ITEMS.register("volcanorune0",
            ()->new VolcanoRune0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> volcanoRune1 = ITEMS.register("volcanorune1",
            ()->new VolcanoRune1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> volcanoRune2 = ITEMS.register("volcanorune2",
            ()->new VolcanoRune2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> volcanoRune3 = ITEMS.register("volcanorune3",
            ()->new VolcanoRune3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LAHForgingDrawing = ITEMS.register("lahelmet_fg",
            ()->new LAHForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> LACForgingDrawing = ITEMS.register("lachest_fg",
            ()->new LACForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> LALForgingDrawing = ITEMS.register("laleggings_fg",
            ()->new LALForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> LABForgingDrawing = ITEMS.register("laboots_fg",
            ()->new LABForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> DailyMission = ITEMS.register("dailymission",
            ()->new Daily(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward5 = ITEMS.register("levelreward5",
            ()->new LevelReward5(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward10 = ITEMS.register("levelreward10",
            ()->new LevelReward10(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward15 = ITEMS.register("levelreward15",
            ()->new LevelReward15(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward20 = ITEMS.register("levelreward20",
            ()->new LevelReward20(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward25 = ITEMS.register("levelreward25",
            ()->new LevelReward25(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward30 = ITEMS.register("levelreward30",
            ()->new LevelReward30(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward35 = ITEMS.register("levelreward35",
            ()->new LevelReward35(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward40 = ITEMS.register("levelreward40",
            ()->new LevelReward40(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward45 = ITEMS.register("levelreward45",
            ()->new LevelReward45(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward50 = ITEMS.register("levelreward50",
            ()->new LevelReward50(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward55 = ITEMS.register("levelreward55",
            ()->new LevelReward55(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> LevelReward60 = ITEMS.register("levelreward60",
            ()->new LevelReward60(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));
    public static final RegistryObject<Item> SkyArmorHelmet = ITEMS.register("skyarmorhelmet",
            ()->new SkyArmorHelmet(ItemMaterial.SkyMaterrial, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> SkyArmorChest = ITEMS.register("skyarmorchest",
            ()->new SkyArmorChest(ItemMaterial.SkyMaterrial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SkyArmorLeggings = ITEMS.register("skyarmorleggings",
            ()->new SkyArmorLeggings(ItemMaterial.SkyMaterrial, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> SkyArmorBoots = ITEMS.register("skyarmorboots",
            ()->new SkyArmorBoots(ItemMaterial.SkyMaterrial, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> NetherArmorHelmet = ITEMS.register("netherarmorhelmet",
            ()->new NetherArmorHelmet(ItemMaterial.NetherAll, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> NetherArmorChest = ITEMS.register("netherarmorchest",
            ()->new NetherArmorChest(ItemMaterial.NetherAll, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> NetherArmorLeggings = ITEMS.register("netherarmorleggings",
            ()->new NetherArmorLeggings(ItemMaterial.NetherAll, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> NetherArmorBoots = ITEMS.register("netherarmorboots",
            ()->new NetherArmorBoots(ItemMaterial.NetherAll, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> AttackUp_PotionBag = ITEMS.register("attackup_potionbag",
            ()->new AttackUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BreakDefenceUp_PotionBag = ITEMS.register("breakdefenceup_potionbag",
            ()->new BreakDefenceUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CritRateUp_PotionBag = ITEMS.register("critrateup_potionbag",
            ()->new CritRateUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CritDamageUp_PotionBag = ITEMS.register("critdamageup_potionbag",
            ()->new CritDamageUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ManaDamageUp_PotionBag = ITEMS.register("manadamageup_potionbag",
            ()->new ManaDamageUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ManaBreakDefenceUp_PotionBag = ITEMS.register("manabreakdefenceup_potionbag",
            ()->new ManaBreakDefenceUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ManaReplyUp_PotionBag = ITEMS.register("manareplyup_potionbag",
            ()->new ManaReplyUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> CoolDownDecreaseUp_PotionBag = ITEMS.register("cooldowndecreaseup_potionbag",
            ()->new CoolDownDecreaseUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> HealStealUp_PotionBag = ITEMS.register("healstealup_potionbag",
            ()->new HealStealUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DefenceUp_PotionBag = ITEMS.register("defenceup_potionbag",
            ()->new DefenceUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ManaDefenceUp_PotionBag = ITEMS.register("manadefenceup_potionbag",
            ()->new ManaDefenceUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SpeedUp_PotionBag = ITEMS.register("speedup_potionbag",
            ()->new SpeedUpPotionBag(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GoldCoinBag = ITEMS.register("goldcoinbag",
            ()->new GoldCoinBag(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Purifier = ITEMS.register("purifier",
            ()->new Purifier(new Item.Properties()));
    public static final RegistryObject<Item> PurifiedWater = ITEMS.register("purified_water",
            ()->new PurifiedWater(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BrewingNote = ITEMS.register("brewingnote",
            ()->new BrewingNote(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> PlainSolidifiedSoul = ITEMS.register("plain_solidified_soul",
            ()->new PlainSolidifiedSoul(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ForestSolidifiedSoul = ITEMS.register("forest_solidified_soul",
            ()->new ForestSolidifiedSoul(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LakeSolidifiedSoul = ITEMS.register("lake_solidified_soul",
            ()->new LakeSolidifiedSoul(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> VolcanoSolidifiedSoul = ITEMS.register("volcano_solidified_soul",
            ()->new VolcanoSolidifiedSoul(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SnowSolidifiedSoul = ITEMS.register("snow_solidified_soul",
            ()->new SnowSolidifiedSoul(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SkySolidifiedSoul = ITEMS.register("sky_solidified_soul",
            ()->new SkySolidifiedSoul(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BlackForestSoul = ITEMS.register("blackforestsoul",
            ()->new BlackForestSoul(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BlackForestRune = ITEMS.register("blackforestrune",
            ()->new BlackForestRune(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EvokerSolidifiedSoul = ITEMS.register("evoker_solidified_soul",
            ()->new EvokerSolidifiedSoul(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> NetherSolidifiedSoul = ITEMS.register("nether_solidified_soul",
            ()->new NetherSolidifiedSoul(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Solidifier = ITEMS.register("solidifier",
            ()->new Solidifier(new Item.Properties()));
    public static final RegistryObject<Item> ArmorHusk = ITEMS.register("armorhusk",
            ()->new MobArmor(400,150,55));
    public static final RegistryObject<Item> ArmorSpider = ITEMS.register("armorspider",
            ()->new MobArmor(400,150,58));
    public static final RegistryObject<Item> Stabilizer = ITEMS.register("stabilizer",
            ()->new Stabilizer(new Item.Properties()));
    public static final RegistryObject<Item> SkyHForgingDrawing = ITEMS.register("skyhelmet_fg",
            ()->new SkyHForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> SkyCForgingDrawing = ITEMS.register("skychest_fg",
            ()->new SkyCForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> SkyLForgingDrawing = ITEMS.register("skyleggings_fg",
            ()->new SkyLForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> SkyBForgingDrawing = ITEMS.register("skyboots_fg",
            ()->new SkyBForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> Concentrater = ITEMS.register("concentrater",
            ()->new Concentrater(new Item.Properties()));
    public static final RegistryObject<Item> SeaSwordForgingDrawing = ITEMS.register("seasword_fg",
            ()->new SeaSwordForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> BlackForestForgingDrawing = ITEMS.register("blackforestsword_fg",
            ()->new BlackForestSwordForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> BackPackTickets = ITEMS.register("backpackticket",
            ()->new BackPackTicket(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> ArmorPFH = ITEMS.register("armorpfhelmet",
            ()->new MobArmor(ItemMaterial.ArmorPF, ArmorItem.Type.HELMET, 45,25,10));
    public static final RegistryObject<Item> ArmorPFC = ITEMS.register("armorpfchest",
            ()->new MobArmor(ItemMaterial.ArmorPF, ArmorItem.Type.CHESTPLATE, 45,25,10));
    public static final RegistryObject<Item> ArmorPFL = ITEMS.register("armorpfleggings",
            ()->new MobArmor(ItemMaterial.ArmorPF, ArmorItem.Type.LEGGINGS, 45,25,10));
    public static final RegistryObject<Item> ArmorPFB = ITEMS.register("armorpfboots",
            ()->new MobArmor(ItemMaterial.ArmorPF, ArmorItem.Type.BOOTS, 45,25,10));
    public static final RegistryObject<Item> SunPower = ITEMS.register("sunpower",
            ()->new SunPower(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorSVH = ITEMS.register("armorsvhelmet",
            ()->new MobArmor(ItemMaterial.ArmorSV, ArmorItem.Type.HELMET,80,45,20));
    public static final RegistryObject<Item> ArmorSVC = ITEMS.register("armorsvchest",
            ()->new MobArmor(ItemMaterial.ArmorSV, ArmorItem.Type.CHESTPLATE,80,45,20));
    public static final RegistryObject<Item> ArmorSVL = ITEMS.register("armorsvleggings",
            ()->new MobArmor(ItemMaterial.ArmorSV, ArmorItem.Type.LEGGINGS,80,45,20));
    public static final RegistryObject<Item> ArmorSVB = ITEMS.register("armorsvboots",
            ()->new MobArmor(ItemMaterial.ArmorSV, ArmorItem.Type.BOOTS,80,45,20));
    public static final RegistryObject<Item> ArmorSLH = ITEMS.register("armorslhelmet",
            ()->new MobArmor(ItemMaterial.ArmorSL, ArmorItem.Type.HELMET,80,45,20));
    public static final RegistryObject<Item> ArmorSLC = ITEMS.register("armorslchest",
            ()->new MobArmor(ItemMaterial.ArmorSL, ArmorItem.Type.CHESTPLATE,80,45,20));
    public static final RegistryObject<Item> ArmorSLL = ITEMS.register("armorslleggings",
            ()->new MobArmor(ItemMaterial.ArmorSL, ArmorItem.Type.LEGGINGS,80,45,20));
    public static final RegistryObject<Item> ArmorSLB = ITEMS.register("armorslboots",
            ()->new MobArmor(ItemMaterial.ArmorSL, ArmorItem.Type.BOOTS,80,45,20));
    public static final RegistryObject<Item> Main1HandGem = ITEMS.register("mainhand",
            ()->new RandomGemPlain(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> Main1BeltGem = ITEMS.register("mainbelt",
            ()->new RandomGemForest(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> Main1necklaceGem = ITEMS.register("mainnecklace",
            ()->new RandomGemLake(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> Main1BraceletGem = ITEMS.register("mainbracelet",
            ()->new RandomGemVolcano(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LightningChange = ITEMS.register("lightningchange",
            ()->new LightningChange(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GuiOpen = ITEMS.register("guiopen",
            ()->new SmartPhoneOpen(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GemBag = ITEMS.register("gembag",
            ()->new RandomGemGive(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RandomGemPiece = ITEMS.register("randomgempiece",
            ()->new RandomGemPiece(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ArmorMagma = ITEMS.register("armormagma",
            ()->new MobArmor(500,40,55));
    public static final RegistryObject<Item> NetherMagmaPower = ITEMS.register("nethermagmapower",
            ()->new NetherMagmaPower(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherHForgingDrawing = ITEMS.register("netherhelmet_fg",
            ()->new NetherHForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> NetherCForgingDrawing = ITEMS.register("netherchest_fg",
            ()->new NetherCForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> NetherLForgingDrawing = ITEMS.register("netherleggings_fg",
            ()->new NetherLForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> NetherBForgingDrawing = ITEMS.register("netherboots_fg",
            ()->new NetherBForgingDrawing(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> MagmaPower = ITEMS.register("magmapower",
            ()->new MagmaPower(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRune0 = ITEMS.register("netherrune0",
            ()->new NetherRune0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRune1 = ITEMS.register("netherrune1",
            ()->new NetherRune1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRune2 = ITEMS.register("netherrune2",
            ()->new NetherRune2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRune3 = ITEMS.register("netherrune3",
            ()->new NetherRune3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorNSHelmet = ITEMS.register("armornshelmet",
            ()->new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.HELMET,200,250,75));
    public static final RegistryObject<Item> SnowRune0 = ITEMS.register("snowrune0",
            ()->new SnowRune0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SnowRune1 = ITEMS.register("snowrune1",
            ()->new SnowRune1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SnowRune2 = ITEMS.register("snowrune2",
            ()->new SnowRune2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SnowRune3 = ITEMS.register("snowrune3",
            ()->new SnowRune3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> KazeSword0 = ITEMS.register("kazesword0",
            ()->new KazeSword0(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> KazeSword1 = ITEMS.register("kazesword1",
            ()->new KazeSword1(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> KazeSword2 = ITEMS.register("kazesword2",
            ()->new KazeSword2(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> KazeSword3 = ITEMS.register("kazesword3",
            ()->new KazeSword3(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> ArmorKazeHelmet = ITEMS.register("armorkazehelmet",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.HELMET,100,50,70));
    public static final RegistryObject<Item> ArmorKazeChest = ITEMS.register("armorkazechest",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.CHESTPLATE,100,50,70));
    public static final RegistryObject<Item> ArmorKazeLeggings = ITEMS.register("armorkazeleggings",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.LEGGINGS,100,50,70));
    public static final RegistryObject<Item> ArmorKazeBoots = ITEMS.register("armorkazeboots",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS,100,50,70));
    public static final RegistryObject<Item> ArmorKazeRecall = ITEMS.register("armorkazerecall",
            ()->new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.HELMET,500,50,90));
    public static final RegistryObject<Item> KazeSoul = ITEMS.register("kazesoul",
            ()->new KazeSoul(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> KazeRune = ITEMS.register("kazerune",
            ()->new KazeRune(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> KazeSwordFG = ITEMS.register("kazesword_fg",
            ()->new KazeSwordForgingDrawing(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LakeCore = ITEMS.register("lakecore",
            ()->new LakeCore(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> KazeBoots = ITEMS.register("kazeboots",
            ()->new KazeArmorBoots(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> KazeBootsFG = ITEMS.register("kazeboots_fg",
            ()->new KazeBootsForgingDrawing(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherGem = ITEMS.register("nethergem",
            ()->new RandomGemNether(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SpiderSoul = ITEMS.register("spidersoul",
            ()->new SpiderSoul(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SpiderRune = ITEMS.register("spiderrune",
            ()->new SpiderRune(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SBoots = ITEMS.register("sboots",
            ()->new SpiderArmorBoots(ItemMaterial.ArmorS, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> SLeggings = ITEMS.register("sleggings",
            ()->new SpiderArmorLeggings(ItemMaterial.ArmorS, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> SChest = ITEMS.register("schest",
            ()->new SpiderArmorChest(ItemMaterial.ArmorS, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SHelmet= ITEMS.register("shelmet",
            ()->new SpiderArmorHelmet(ItemMaterial.ArmorS, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> SunOintment0 = ITEMS.register("sunointment0",
            ()->new SunOintment0(new Item.Properties().rarity(Rarity.create("test", ChatFormatting.GREEN))));
    public static final RegistryObject<Item> SunOintment1 = ITEMS.register("sunointment1",
            ()->new SunOintment1(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SunOintment2 = ITEMS.register("sunointment2",
            ()->new SunOintment2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LakeOintment0 = ITEMS.register("lakeointment0",
            ()->new LakeOintment0(new Item.Properties().rarity(Rarity.create("test", ChatFormatting.GREEN))));
    public static final RegistryObject<Item> LakeOintment1 = ITEMS.register("lakeointment1",
            ()->new LakeOintment1(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LakeOintment2 = ITEMS.register("lakeointment2",
            ()->new LakeOintment2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> VolcanoOintment0 = ITEMS.register("volcanoointment0",
            ()->new VolcanoOintment0(new Item.Properties().rarity(Rarity.create("test", ChatFormatting.GREEN))));
    public static final RegistryObject<Item> VolcanoOintment1 = ITEMS.register("volcanoointment1",
            ()->new VolcanoOintment1(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VolcanoOintment2 = ITEMS.register("volcanoointment2",
            ()->new VolcanoOintment2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SnowOintment0 = ITEMS.register("snowointment0",
            ()->new SnowOintment0(new Item.Properties().rarity(Rarity.create("test", ChatFormatting.GREEN))));
    public static final RegistryObject<Item> SnowOintment1 = ITEMS.register("snowointment1",
            ()->new SnowOintment1(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SnowOintment2 = ITEMS.register("snowointment2",
            ()->new SnowOintment2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SkyOintment0 = ITEMS.register("skyointment0",
            ()->new SkyOintment0(new Item.Properties().rarity(Rarity.create("test", ChatFormatting.GREEN))));
    public static final RegistryObject<Item> SkyOintment1 = ITEMS.register("skyointment1",
            ()->new SkyOintment1(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SkyOintment2 = ITEMS.register("skyointment2",
            ()->new SkyOintment2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ManaOintment0 = ITEMS.register("manaointment0",
            ()->new ManaOintment0(new Item.Properties().rarity(Rarity.create("test", ChatFormatting.GREEN))));
    public static final RegistryObject<Item> ManaOintment1 = ITEMS.register("manaointment1",
            ()->new ManaOintment1(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ManaOintment2 = ITEMS.register("manaointment2",
            ()->new ManaOintment2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherOintment0 = ITEMS.register("netherointment0",
            ()->new NetherOintment0(new Item.Properties().rarity(Rarity.create("test", ChatFormatting.GREEN))));
    public static final RegistryObject<Item> NetherOintment1 = ITEMS.register("netherointment1",
            ()->new NetherOintment1(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> NetherOintment2 = ITEMS.register("netherointment2",
            ()->new NetherOintment2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> VolcanoCore = ITEMS.register("volcanocore",
            ()->new VolcanoCore(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GoldSword0 = ITEMS.register("goldsword0",
            ()->new GoldSword0(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> CodeSceptre = ITEMS.register("codesceptre",
            ()->new CodeSceptre(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> BreakMana = ITEMS.register("breakmana",
            ()->new BreakMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DamageMana = ITEMS.register("damagemana",
            ()->new DamageMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> EffectMana = ITEMS.register("effectmana",
            ()->new EffectMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GatherMana = ITEMS.register("gathermana",
            ()->new GatherMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> KazeMana = ITEMS.register("kazemana",
            ()->new KazeMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LightningMana = ITEMS.register("lightningmana",
            ()->new LightningMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> RangeMana = ITEMS.register("rangemana",
            ()->new RangeMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SnowMana = ITEMS.register("snowmana",
            ()->new SnowMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LightningPower = ITEMS.register("lightningpower",
            ()->new LightningPower(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ManaModel = ITEMS.register("manamodel",
            ()->new ManaModel(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> IronLove = ITEMS.register("ironlove",
            ()->new IronLove(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SilverFishSoul = ITEMS.register("silverfishsoul",
            ()->new SilverFishSoul(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorSilverFish = ITEMS.register("armorsilverfish",
            ()->new MobArmor(400,25,70));
    public static final RegistryObject<Item> BlackForestRecall = ITEMS.register("blackforestrecall",
            ()->new BlackForestSwordRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EvokerRecallBook = ITEMS.register("evokerrecallbook",
            ()->new EvokerRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ForestRecallBook = ITEMS.register("forestrecallbook",
            ()->new ForestRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> KazeRecallBook = ITEMS.register("kazerecallbook",
            ()->new KazeRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LakeRecallBook = ITEMS.register("lakerecallbook",
            ()->new LakeRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LightningRecallBook = ITEMS.register("lightningrecallbook",
            ()->new LightningRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRecallBook1 = ITEMS.register("netherrecallbook1",
            ()->new NetherRecallBook1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRecallBook2 = ITEMS.register("netherrecallbook2",
            ()->new NetherRecallBook2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PlainRecallBook = ITEMS.register("plainrecallbook",
            ()->new PlainRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SeaRecallBook = ITEMS.register("searecallbook",
            ()->new SeaRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SkyRecallBook = ITEMS.register("skyrecallbook",
            ()->new SkyRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SnowRecallBook = ITEMS.register("snowrecallbook",
            ()->new SnowRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SpiderRecallBook = ITEMS.register("spiderrecallbook",
            ()->new SpiderRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> VolcanoRecallBook = ITEMS.register("volcanorecallbook",
            ()->new VolcanoRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> RecallPiece = ITEMS.register("recallpiece",
            ()->new RecallPiece(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ArmorEnderMan = ITEMS.register("armorenderman",
            ()->new MobArmor(500,400,80));
    public static final RegistryObject<Item> BarrierSet = ITEMS.register("barrierset",
            ()->new BarrierSet(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> KazeRecallSoul = ITEMS.register("kazerecallsoul",
            ()->new KazeRecallSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> IntensifiedKazeSoul = ITEMS.register("intensifiedkazesoul",
            ()->new IntensifiedKazeSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> KazeSword4 = ITEMS.register("kazesword4",
            ()->new KazeSword4(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> ArmorSpiderRecall = ITEMS.register("armorspiderecall",
            ()->new MobArmor(500,50,90));
    public static final RegistryObject<Item> SpiderRecallSoul = ITEMS.register("spiderrecallsoul",
            ()->new SpiderRecallSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> ManageSword = ITEMS.register("managesword",
            ()->new ManageSword(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> ISArmorHelmet = ITEMS.register("isarmorhelmet",
            ()->new SpiderRecallArmorHelmet(ItemMaterial.ArmorKaze, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> ISArmorChest = ITEMS.register("isarmorchest",
            ()->new SpiderRecallArmorChest(ItemMaterial.ArmorKaze, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> ISArmorLeggings = ITEMS.register("isarmorleggings",
            ()->new SpiderRecallArmorLeggings(ItemMaterial.ArmorKaze, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> ISArmorBoots = ITEMS.register("isarmorboots",
            ()->new SpiderRecallArmorBoots(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> IntensifiedSpiderSoul = ITEMS.register("intensifiedspidersoul",
            ()->new IntensifiedSpiderSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> BlackForestRecallSoul = ITEMS.register("blackforestrecallsoul",
            ()->new BlackForestRecallSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> IntensifiedBlackForestSoul = ITEMS.register("intensifiedblackforestsoul",
            ()->new IntensifiedBlackForestSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> ArmorHuskRecall = ITEMS.register("armorhuskrecall",
            ()->new MobArmor(800,50,90));
    public static final RegistryObject<Item> BlackForestSword4 = ITEMS.register("blackforestsword4",
            ()->new BlackForestSword4(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> WaterSet = ITEMS.register("waterset",
            ()->new WaterSet(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ArmorSeaRecall = ITEMS.register("armorsearecall",
            ()->new MobArmor(500,50,90));
    public static final RegistryObject<Item> SeaRecallSoul = ITEMS.register("searecallsoul",
            ()->new SeaRecallSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> IntensifiedSeaSoul = ITEMS.register("intensifiedseasoul",
            ()->new IntensifiedSeaSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> SeaSword4 = ITEMS.register("seasword4",
            ()->new SeaSword4(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> ILArmorHelmet = ITEMS.register("ilarmorhelmet",
            ()->new iLightningArmorHelmet(ItemMaterial.ArmorIL, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> ILArmorChest = ITEMS.register("ilarmorchest",
            ()->new iLightningArmorChest(ItemMaterial.ArmorIL, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> ILArmorLeggings = ITEMS.register("ilarmorleggings",
            ()->new iLightningArmorLeggings(ItemMaterial.ArmorIL, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> ILArmorBoots = ITEMS.register("ilarmorboots",
            ()->new iLightningArmorBoots(ItemMaterial.ArmorIL, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> LightningRecallSoul = ITEMS.register("lightningrecallsoul",
            ()->new LightningRecallSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> IntensifiedLightningSoul = ITEMS.register("intensifiedlightningsoul",
            ()->new IntensifiedLightningSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> ArmorLightningRecall = ITEMS.register("armorlightningrecall",
            ()->new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> NetherGemPiece = ITEMS.register("nethergempiece",
            ()->new NetherGemPiece(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherGemPieceBag1 = ITEMS.register("nethergempiecebag1",
            ()->new NetherGemPieceBag1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherGemPieceBag2 = ITEMS.register("nethergempiecebag2",
            ()->new NetherGemPieceBag2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherGemPieceBag3 = ITEMS.register("nethergempiecebag3",
            ()->new NetherGemPieceBag3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherGemPieceBag4 = ITEMS.register("nethergempiecebag4",
            ()->new NetherGemPieceBag4(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRecallSoul = ITEMS.register("netherrecallsoul",
            ()->new RecallRuby(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> IntensifiedNetherRecallSoul = ITEMS.register("intensifiednetherrecallsoul",
            ()->new IntensifiedRuby(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> ArmorNetherRecall = ITEMS.register("armornetherrecall",
            ()->new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> ManaSword1 = ITEMS.register("manasword1",
            ()->new ManaSword1(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> SnowRecallSoul = ITEMS.register("snowrecallsoul",
            ()->new SnowRecallSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> IntensifiedSnowRecallSoul = ITEMS.register("intensifiedsnowrecallsoul",
            ()->new IntensifiedSnowSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> ArmorSnowRecall = ITEMS.register("armorsnowrecall",
            ()->new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> snowsword4 = ITEMS.register("snowsword4",
            ()->new SnowSword4(ItemTier.MaterialForPickaxe3,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> ForestRecallSoul = ITEMS.register("forestrecallsoul",
            ()->new ForestRecallSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> IntensifiedForestRecallSoul = ITEMS.register("intensifiedforestsoul",
            ()->new IntensifiedForestSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> ArmorForestRecall = ITEMS.register("armorforestrecall",
            ()->new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> forestsword4 = ITEMS.register("forestsword4",
            ()->new ForestSword4(ItemTier.VMaterial,2,1.0F,new Item.Properties()));
    public static final RegistryObject<Item> VolcanoSword4 = ITEMS.register("volcanosword4",
            ()->new VolcanoSword4(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> IntensifiedVolcanoRecallSoul = ITEMS.register("intensifiedvolcanosoul",
            ()->new IntensifiedForestSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> ArmorVolcanoRecall = ITEMS.register("armorvolcanorecall",
            ()->new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> VolcanoRecallSoul = ITEMS.register("volcanorecallsoul",
            ()->new VolcanoRecallSoul(new Item.Properties().rarity(Rarity.create("gold", ChatFormatting.GOLD))));
    public static final RegistryObject<Item> ANIMATED_ITEM = ITEMS.register("animated_item",
            () -> new AnimatedItem(new Item.Properties()));
    public static final RegistryObject<Item> ID_Card = ITEMS.register("id_card",
            ()->new ID_Card(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ForestBossPocket = ITEMS.register("forest_boss_pocket",
            ()->new ForestBossPocket(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ForestBossCore = ITEMS.register("forest_boss_core",
            ()->new ForestBossCore(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ForestBossGem = ITEMS.register("forest_boss_gem",
            ()->new ForestBossGem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ForestBossCentral = ITEMS.register("forest_boss_central",
            ()->new ForestBossCentral(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ForestBossSword = ITEMS.register("forest_boss_sword",
            ()->new ForestBossSword(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> VolcanoBossPocket = ITEMS.register("volcano_boss_pocket",
            ()->new VolcanoBossPocket(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VolcanoBossCore = ITEMS.register("volcano_boss_core",
            ()->new VolcanoBossCore(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VolcanoBossGem = ITEMS.register("volcano_boss_gem",
            ()->new VolcanoBossGem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VolcanoBossCentral = ITEMS.register("volcano_boss_central",
            ()->new VolcanoBossCentral(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> VolcanoBossSword = ITEMS.register("volcano_boss_sword",
            ()->new VolcanoBossSword(ItemTier.VMaterial,2,1.0F));
    public static final RegistryObject<Item> ArmorForestBoss = ITEMS.register("armor_forest_boss",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 300, 50, 15, 0.3f, 20, 0.25f, 1.25f, 0.05f));
    public static final RegistryObject<Item> ArmorVolcanoBoss = ITEMS.register("armor_volcano_boss",
            ()->new MobArmor(ItemMaterial.MaterialForArmor2, ArmorItem.Type.HELMET, 500, 50, 30, 0.4f, 30, 0.3f, 1.25f, 0.08f));
    public static final RegistryObject<Item> SakuraChange = ITEMS.register("sakura_change",
            ()->new SakuraChange(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BlockFill = ITEMS.register("block_fill",
            ()->new BlockFill(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BlockFix = ITEMS.register("block_fix",
            ()->new BlockFix(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> SakuraPetal = ITEMS.register("sakura_petal",
            ()->new SakuraPetal(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SakuraPocket = ITEMS.register("sakura_pocket",
            ()->new SakuraPocket(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SakuraReForge = ITEMS.register("sakura_reforge",
            ()->new SakuraReForge(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SakuraSwordForgingDrawing = ITEMS.register("sakura_sword_fd",
            ()->new SakuraSwordForgingDrawing(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> SakuraArmorHelmet = ITEMS.register("sakura_armor_helmet",
            ()->new SakuraArmorHelmet(ItemMaterial.LakeMaterialHelmet, ArmorItem.Type.HELMET));

    public static final RegistryObject<Item> Wheat = ITEMS.register("vmd_wheat",
            ()->new Wheat(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> WheatPocket = ITEMS.register("wheat_pocket",
            ()->new WheatPocket(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> WheatReForge = ITEMS.register("wheat_reforge",
            ()->new WheatReForge(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> MineLeggingsForgingDrawing = ITEMS.register("mine_leggings_fd",
            ()->new MineLeggingsForgingDrawing(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> WheatArmorChest = ITEMS.register("wheat_armor_chest",
            ()->new WheatArmorChest(ItemMaterial.LakeMaterialChest, ArmorItem.Type.CHESTPLATE));

    public static final RegistryObject<Item> ArmorSakuraMob = ITEMS.register("armor_sakura_mob",
            ()->new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 300, 50, 90, 0.3f, 20, 0.25f, 1.25f, 0.05f));

}
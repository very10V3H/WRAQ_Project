package fun.wraq.common.registry;

import fun.wraq.Items.DevelopmentTools.*;
import fun.wraq.Items.Explore.*;
import fun.wraq.Items.Forging.*;
import fun.wraq.Items.KillPaper.KillPaper;
import fun.wraq.Items.LevelReward.VariousBag.GoldCoinBag;
import fun.wraq.Items.LevelReward.VariousBag.LogBag;
import fun.wraq.Items.Lotteries.*;
import fun.wraq.Items.MainStory_1.BackSpawn;
import fun.wraq.Items.MainStory_1.ForNew;
import fun.wraq.Items.MainStory_1.Mission.*;
import fun.wraq.Items.MainStory_1.NewCurios;
import fun.wraq.Items.Mission.Daily;
import fun.wraq.Items.Mission.IronLove;
import fun.wraq.Items.MobItem.HolyArmor;
import fun.wraq.Items.MobItem.MobArmor;
import fun.wraq.Items.Money.*;
import fun.wraq.Items.ProfessionAndQuest.Profession_Barker;
import fun.wraq.Items.ProfessionAndQuest.Profession_Bow;
import fun.wraq.Items.ProfessionAndQuest.Profession_Sword;
import fun.wraq.Items.ProfessionAndQuest.Quest;
import fun.wraq.Items.Ps.PsBottle;
import fun.wraq.Items.RewardPack.Boss1;
import fun.wraq.Items.RewardPack.Boss2;
import fun.wraq.Items.RewardPack.Main1Reward;
import fun.wraq.Items.RewardPack.SignInReward;
import fun.wraq.Items.SkillItems.ID_Card;
import fun.wraq.Items.SkyCity.TicketToSkyCity;
import fun.wraq.blocks.blocks.brew.*;
import fun.wraq.blocks.blocks.brew.solidifiedSouls.*;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.UniformItems;
import fun.wraq.entities.animatedItem.AnimatedItem;
import fun.wraq.events.mob.instance.item.NetherHand;
import fun.wraq.events.mob.instance.item.PlainNecklace;
import fun.wraq.events.sec.SoulBag;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.plan.SimpleTierPaper;
import fun.wraq.process.func.plan.SupplyBox;
import fun.wraq.process.system.element.RainbowCrystal;
import fun.wraq.process.system.element.RainbowPowder;
import fun.wraq.process.system.element.crystal.*;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementBow;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSceptre;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementBow;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSceptre;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword;
import fun.wraq.process.system.element.equipAndCurios.waterElement.WaterElementBow;
import fun.wraq.process.system.element.equipAndCurios.waterElement.WaterElementSceptre;
import fun.wraq.process.system.element.equipAndCurios.waterElement.WaterElementSword;
import fun.wraq.process.system.element.holyStone.*;
import fun.wraq.process.system.enhanceForge.Pearl;
import fun.wraq.process.system.forge.ForgeHammer;
import fun.wraq.process.system.instance.MopUpPaper;
import fun.wraq.process.system.instance.MopUpPaperLoot;
import fun.wraq.process.system.lottery.NewLotteries;
import fun.wraq.process.system.lottery.items.LotteryPrefix;
import fun.wraq.process.system.parkour.KillPaperLoot;
import fun.wraq.process.system.parkour.ParkourGloves;
import fun.wraq.process.system.potion.NewPotion;
import fun.wraq.process.system.potion.NewThrowablePotion;
import fun.wraq.process.system.potion.PotionBag;
import fun.wraq.process.system.teamInstance.instances.blackCastle.CastleNecklace;
import fun.wraq.render.gui.testAndHelper.SmartPhoneOpen;
import fun.wraq.render.mobEffects.ModPotions;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.end.EndPower;
import fun.wraq.series.end.curios.EndCrystal;
import fun.wraq.series.end.curios.EndCuriosBow;
import fun.wraq.series.end.curios.EndCuriosMana;
import fun.wraq.series.end.eventController.ForestRecall.ForestSword4;
import fun.wraq.series.end.eventController.LightningIslandRecall.IntensifiedLightningArmor;
import fun.wraq.series.end.eventController.LightningIslandRecall.IntensifiedLightningSoul;
import fun.wraq.series.end.eventController.LightningIslandRecall.LightningRecallSoul;
import fun.wraq.series.end.eventController.NetherRecall1.ManaSword1;
import fun.wraq.series.end.eventController.SpiderRecall.SpiderRecallArmorBoots;
import fun.wraq.series.end.eventController.SpiderRecall.SpiderRecallArmorChest;
import fun.wraq.series.end.eventController.SpiderRecall.SpiderRecallArmorHelmet;
import fun.wraq.series.end.eventController.SpiderRecall.SpiderRecallArmorLeggings;
import fun.wraq.series.end.recallBooks.*;
import fun.wraq.series.gems.Curios.FancySapphireNecklace;
import fun.wraq.series.gems.Curios.RubyNecklace;
import fun.wraq.series.gems.Curios.SapphireNecklace;
import fun.wraq.series.instance.series.castle.*;
import fun.wraq.series.instance.series.devil.*;
import fun.wraq.series.instance.series.ice.IceArmor;
import fun.wraq.series.instance.series.ice.IceBelt;
import fun.wraq.series.instance.series.ice.IceBook;
import fun.wraq.series.instance.series.ice.IceLoot;
import fun.wraq.series.instance.series.ice.weapon.IceBow;
import fun.wraq.series.instance.series.ice.weapon.IceSceptre;
import fun.wraq.series.instance.series.ice.weapon.IceSword;
import fun.wraq.series.instance.series.moon.Equip.*;
import fun.wraq.series.instance.series.moon.MoonCurios;
import fun.wraq.series.instance.series.moon.MoonLoot;
import fun.wraq.series.instance.series.plain.PlainAttackRing;
import fun.wraq.series.instance.series.plain.PlainDefenceRing;
import fun.wraq.series.instance.series.plain.PlainHealthRing;
import fun.wraq.series.instance.series.plain.PlainManaAttackRing;
import fun.wraq.series.instance.series.purple.PurpleIronBow;
import fun.wraq.series.instance.series.purple.PurpleIronSceptre;
import fun.wraq.series.instance.series.purple.PurpleIronSword;
import fun.wraq.series.instance.series.sakura.Boss2AttackRing;
import fun.wraq.series.instance.series.sakura.Boss2DefenceRing;
import fun.wraq.series.instance.series.sakura.Boss2HealthRing;
import fun.wraq.series.instance.series.sakura.Boss2ManaAttackRing;
import fun.wraq.series.instance.series.taboo.TabooAttackArmor;
import fun.wraq.series.instance.series.taboo.TabooManaArmor;
import fun.wraq.series.instance.series.taboo.TabooSwiftArmor;
import fun.wraq.series.nether.equip.attack.NetherArmor;
import fun.wraq.series.nether.equip.attack.NetherPower;
import fun.wraq.series.nether.equip.attack.bow.NetherBow;
import fun.wraq.series.nether.equip.attack.bow.NetherKnife;
import fun.wraq.series.nether.equip.attack.bow.WitherBow;
import fun.wraq.series.nether.equip.attack.sword.*;
import fun.wraq.series.nether.equip.common.PiglinHelmet;
import fun.wraq.series.nether.equip.mana.MagmaSceptre;
import fun.wraq.series.nether.equip.mana.NetherManaArmor;
import fun.wraq.series.nether.equip.mana.NetherSceptre;
import fun.wraq.series.nether.equip.mana.WitherBook;
import fun.wraq.series.nether.material.NetherMagmaPower;
import fun.wraq.series.nether.material.ToNether;
import fun.wraq.series.nether.power.MagmaPower;
import fun.wraq.series.nether.power.PiglinPower;
import fun.wraq.series.nether.power.WitherBoneMealPower;
import fun.wraq.series.nether.power.WitherBonePower;
import fun.wraq.series.nether.runes.NetherRune0;
import fun.wraq.series.nether.runes.NetherRune1;
import fun.wraq.series.nether.runes.NetherRune2;
import fun.wraq.series.nether.runes.NetherRune3;
import fun.wraq.series.overworld.IceSeries.LeatherArmor;
import fun.wraq.series.overworld.WorldBoss.CropPackets;
import fun.wraq.series.overworld.WorldBoss.GiantTicket;
import fun.wraq.series.overworld.castle.*;
import fun.wraq.series.overworld.chapter1.Field.FieldSword;
import fun.wraq.series.overworld.chapter1.Main1Boss.main1crystal;
import fun.wraq.series.overworld.chapter1.ManaBook.ManaNote;
import fun.wraq.series.overworld.chapter1.Mine.Armor.MineArmorBoots;
import fun.wraq.series.overworld.chapter1.Mine.Armor.MineArmorChest;
import fun.wraq.series.overworld.chapter1.Mine.Armor.MineArmorHelmet;
import fun.wraq.series.overworld.chapter1.Mine.Armor.MineArmorLeggings;
import fun.wraq.series.overworld.chapter1.Mine.MineBow;
import fun.wraq.series.overworld.chapter1.Mine.Crest.MineCrest;
import fun.wraq.series.overworld.chapter1.Mine.MineHat;
import fun.wraq.series.overworld.chapter1.Mine.MineShield;
import fun.wraq.series.overworld.chapter1.Mine.Sword.MineSword;
import fun.wraq.series.overworld.chapter1.snow.SnowArmor;
import fun.wraq.series.overworld.chapter1.snow.SnowCrest;
import fun.wraq.series.overworld.chapter1.snow.Runes.SnowRune0;
import fun.wraq.series.overworld.chapter1.snow.Runes.SnowRune1;
import fun.wraq.series.overworld.chapter1.snow.Runes.SnowRune2;
import fun.wraq.series.overworld.chapter1.snow.Runes.SnowRune3;
import fun.wraq.series.overworld.chapter1.snow.SnowBoss;
import fun.wraq.series.overworld.chapter1.snow.SnowPower;
import fun.wraq.series.overworld.chapter1.snow.SnowShield;
import fun.wraq.series.overworld.chapter1.snow.SnowSword;
import fun.wraq.series.overworld.chapter1.forest.ForestPower;
import fun.wraq.series.overworld.chapter1.forest.ForestRing;
import fun.wraq.series.overworld.chapter1.forest.armor.ForestArmorBoots;
import fun.wraq.series.overworld.chapter1.forest.armor.ForestArmorChest;
import fun.wraq.series.overworld.chapter1.forest.armor.ForestArmorHelmet;
import fun.wraq.series.overworld.chapter1.forest.armor.ForestArmorLeggings;
import fun.wraq.series.overworld.chapter1.forest.bossItems.*;
import fun.wraq.series.overworld.chapter1.forest.bow.ForestBow;
import fun.wraq.series.overworld.chapter1.forest.crest.ForestCrest;
import fun.wraq.series.overworld.chapter1.forest.rune.ForestRune0;
import fun.wraq.series.overworld.chapter1.forest.rune.ForestRune1;
import fun.wraq.series.overworld.chapter1.forest.rune.ForestRune2;
import fun.wraq.series.overworld.chapter1.forest.rune.ForestRune3;
import fun.wraq.series.overworld.chapter1.forest.sword.ForestSword;
import fun.wraq.series.overworld.chapter1.plain.PlainPower;
import fun.wraq.series.overworld.chapter1.plain.PlainRing;
import fun.wraq.series.overworld.chapter1.plain.armor.PlainArmorBoots;
import fun.wraq.series.overworld.chapter1.plain.armor.PlainArmorChest;
import fun.wraq.series.overworld.chapter1.plain.armor.PlainArmorHelmet;
import fun.wraq.series.overworld.chapter1.plain.armor.PlainArmorLeggings;
import fun.wraq.series.overworld.chapter1.plain.PlainBow;
import fun.wraq.series.overworld.chapter1.plain.crest.PlainCrest;
import fun.wraq.series.overworld.chapter1.plain.runes.*;
import fun.wraq.series.overworld.chapter1.plain.sceptre.PlainSceptre;
import fun.wraq.series.overworld.chapter1.plain.sword.PlainSword;
import fun.wraq.series.overworld.chapter1.volcano.*;
import fun.wraq.series.overworld.chapter1.volcano.armor.VolcanoArmorBoots;
import fun.wraq.series.overworld.chapter1.volcano.armor.VolcanoArmorChest;
import fun.wraq.series.overworld.chapter1.volcano.armor.VolcanoArmorHelmet;
import fun.wraq.series.overworld.chapter1.volcano.armor.VolcanoArmorLeggings;
import fun.wraq.series.overworld.chapter1.volcano.bossItems.*;
import fun.wraq.series.overworld.chapter1.volcano.VolcanoBow;
import fun.wraq.series.overworld.chapter1.volcano.rune.VolcanoRune0;
import fun.wraq.series.overworld.chapter1.volcano.rune.VolcanoRune1;
import fun.wraq.series.overworld.chapter1.volcano.rune.VolcanoRune2;
import fun.wraq.series.overworld.chapter1.volcano.rune.VolcanoRune3;
import fun.wraq.series.overworld.chapter1.waterSystem.LakePower;
import fun.wraq.series.overworld.chapter1.waterSystem.LakeRing;
import fun.wraq.series.overworld.chapter1.waterSystem.bossItems.LakeBoss;
import fun.wraq.series.overworld.chapter1.waterSystem.crest.LakeCrest;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.LakeBow;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.LakeSceptre;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.armor.LakeArmorBoots;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.armor.LakeArmorChest;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.armor.LakeArmorHelmet;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.armor.LakeArmorLeggings;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.sword.LakeSword;
import fun.wraq.series.overworld.chapter1.waterSystem.runes.LakeRune0;
import fun.wraq.series.overworld.chapter1.waterSystem.runes.LakeRune1;
import fun.wraq.series.overworld.chapter1.waterSystem.runes.LakeRune2;
import fun.wraq.series.overworld.chapter1.waterSystem.runes.LakeRune3;
import fun.wraq.series.overworld.chapter2.blackForest.BlackForestCore;
import fun.wraq.series.overworld.chapter2.blackForest.HuskSword;
import fun.wraq.series.overworld.chapter2.codeMana.*;
import fun.wraq.series.overworld.chapter2.dimension.ToEnd;
import fun.wraq.series.overworld.chapter2.evoker.Crest.ManaCrest;
import fun.wraq.series.overworld.chapter2.evoker.EvokerSceptre;
import fun.wraq.series.overworld.chapter2.evoker.ManaBalance_Empty;
import fun.wraq.series.overworld.chapter2.evoker.ManaBalance_Filled;
import fun.wraq.series.overworld.chapter2.evoker.Runes.ManaRune0;
import fun.wraq.series.overworld.chapter2.evoker.Runes.ManaRune1;
import fun.wraq.series.overworld.chapter2.evoker.Runes.ManaRune2;
import fun.wraq.series.overworld.chapter2.evoker.Runes.ManaRune3;
import fun.wraq.series.overworld.chapter2.kaze.KazeArmorBoots;
import fun.wraq.series.overworld.chapter2.kaze.KazeCore;
import fun.wraq.series.overworld.chapter2.kaze.Sword.KazeSword;
import fun.wraq.series.overworld.chapter2.lavender.LavenderBracelet;
import fun.wraq.series.overworld.chapter2.lightningIsland.Armor.LightningArmor;
import fun.wraq.series.overworld.chapter2.lightningIsland.LightningChange;
import fun.wraq.series.overworld.chapter2.manaArmor.LifeMana.LifeManaArmor;
import fun.wraq.series.overworld.chapter2.manaArmor.LifeMana1;
import fun.wraq.series.overworld.chapter2.manaArmor.ObsiMana.ObsiManaArmor;
import fun.wraq.series.overworld.chapter2.manaArmor.ObsiMana1;
import fun.wraq.series.overworld.chapter2.sea.SeaBow;
import fun.wraq.series.overworld.chapter2.sea.SeaCore;
import fun.wraq.series.overworld.chapter2.sea.Sword.SeaSword;
import fun.wraq.series.overworld.chapter2.sky.Armor.SkyArmor;
import fun.wraq.series.overworld.chapter2.sky.BossItems.SkyBoss;
import fun.wraq.series.overworld.chapter2.sky.Crest.SkyCrest;
import fun.wraq.series.overworld.chapter2.sky.SkyBow;
import fun.wraq.series.overworld.chapter2.sky.WindBottle;
import fun.wraq.series.overworld.chapter2.spider.Ointment.*;
import fun.wraq.series.overworld.chapter2.spider.SpiderArmorBoots;
import fun.wraq.series.overworld.chapter2.spider.SpiderArmorChest;
import fun.wraq.series.overworld.chapter2.spider.SpiderArmorHelmet;
import fun.wraq.series.overworld.chapter2.spider.SpiderArmorLeggings;
import fun.wraq.series.overworld.chapter7.star.StarArmor;
import fun.wraq.series.overworld.chapter7.star.StarBottle;
import fun.wraq.series.overworld.forging.ForgingStone0;
import fun.wraq.series.overworld.forging.ForgingStone1;
import fun.wraq.series.overworld.forging.ForgingStone2;
import fun.wraq.series.overworld.kanata.WraqKanata;
import fun.wraq.series.overworld.knife.OriginKnife;
import fun.wraq.series.overworld.sakuraSeries.BloodMana.BloodManaArmor;
import fun.wraq.series.overworld.sakuraSeries.BloodMana.BloodManaCurios;
import fun.wraq.series.overworld.sakuraSeries.BloodMana.ManaKnife;
import fun.wraq.series.overworld.sakuraSeries.BloodMana.ManaShield;
import fun.wraq.series.overworld.sakuraSeries.Boss2.GoldenAttackOffhand;
import fun.wraq.series.overworld.sakuraSeries.Boss2.GoldenBook;
import fun.wraq.series.overworld.sakuraSeries.EarthMana.EarthBook;
import fun.wraq.series.overworld.sakuraSeries.EarthMana.EarthManaArmor;
import fun.wraq.series.overworld.sakuraSeries.EarthMana.EarthManaCurios;
import fun.wraq.series.overworld.sakuraSeries.EarthMana.EarthPower;
import fun.wraq.series.overworld.sakuraSeries.MineWorker.MinePants;
import fun.wraq.series.overworld.sakuraSeries.MineWorker.PurpleIronArmor;
import fun.wraq.series.overworld.sakuraSeries.MineWorker.PurplePickaxe;
import fun.wraq.series.overworld.sakuraSeries.SakuraMob.*;
import fun.wraq.series.overworld.sakuraSeries.Scarecrow.Wheat;
import fun.wraq.series.overworld.sakuraSeries.Scarecrow.WheatArmorChest;
import fun.wraq.series.overworld.sakuraSeries.Scarecrow.WheatPocket;
import fun.wraq.series.overworld.sakuraSeries.Scarecrow.WheatReForge;
import fun.wraq.series.overworld.sakuraSeries.Ship.ShipBow;
import fun.wraq.series.overworld.sakuraSeries.Ship.ShipSceptre;
import fun.wraq.series.overworld.sakuraSeries.Ship.ShipSword;
import fun.wraq.series.overworld.sakuraSeries.Slime.SlimeBoots;
import fun.wraq.series.specialevents.labourDay.*;
import fun.wraq.series.specialevents.qingMing.*;
import fun.wraq.series.specialevents.springFes.*;
import fun.wraq.series.worldsoul.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> PP = ITEMS.register("pp",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> Main0 = ITEMS.register("main0",
            () -> new Main0(new Item.Properties()));
    public static final RegistryObject<Item> Main0_1 = ITEMS.register("main0_1",
            () -> new Main0_1(new Item.Properties()));
    public static final RegistryObject<Item> Main0_2 = ITEMS.register("main0_2",
            () -> new Main0_2(new Item.Properties()));
    public static final RegistryObject<Item> Main0_3 = ITEMS.register("main0_3",
            () -> new Main0_3(new Item.Properties()));
    public static final RegistryObject<Item> Main1_1 = ITEMS.register("main1_1",
            () -> new Main1_1(new Item.Properties()));
    public static final RegistryObject<Item> Main1_2 = ITEMS.register("main1_2",
            () -> new Main1_2(new Item.Properties()));
    public static final RegistryObject<Item> Main1_0 = ITEMS.register("main1_0",
            () -> new Main1_0(new Item.Properties()));
    public static final RegistryObject<Item> Main1_3 = ITEMS.register("main1_3",
            () -> new Main1_3(new Item.Properties()));
    public static final RegistryObject<Item> Main1_4 = ITEMS.register("main1_4",
            () -> new Main1_4(new Item.Properties()));
    public static final RegistryObject<Item> goldCoin = ITEMS.register("gold_coin",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
                    components.add(Te.s("等价于: ", CustomStyle.styleOfWorld, stack.getCount() * 144 + "vb", CustomStyle.styleOfGold));
                    super.appendHoverText(stack, level, components, flag);
                }
            });
    public static final RegistryObject<Item> silverCoin = ITEMS.register("silver_coin",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
                    components.add(Te.s("等价于: ", CustomStyle.styleOfWorld, stack.getCount() * 12 + "vb", CustomStyle.styleOfGold));
                    super.appendHoverText(stack, level, components, flag);
                }
            });
    public static final RegistryObject<Item> copperCoin = ITEMS.register("copper_coin",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Copper)) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
                    components.add(Te.s("等价于: ", CustomStyle.styleOfWorld, stack.getCount() + "vb", CustomStyle.styleOfGold));
                    super.appendHoverText(stack, level, components, flag);
                }
            });
    public static final RegistryObject<Item> SignInReset = ITEMS.register("signinreset",
            () -> new SignInReset(new Item.Properties()));
    public static final RegistryObject<Item> SignInGet = ITEMS.register("signinget",
            () -> new SignInGet(new Item.Properties()));
    public static final RegistryObject<Item> GetTime = ITEMS.register("gettime",
            () -> new GetTime(new Item.Properties()));
    public static final RegistryObject<Item> ItemIDCheck = ITEMS.register("idcheck",
            () -> new ItemIDCheck(new Item.Properties()));
    public static final RegistryObject<Item> SignInReward = ITEMS.register("signinreward",
            () -> new SignInReward(new Item.Properties()));
    public static final RegistryObject<Item> Note_0 = ITEMS.register("note_0",
            () -> new Note_0(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ExploreNote = ITEMS.register("explorenote",
            () -> new ExploreNote(new Item.Properties()));
    public static final RegistryObject<Item> ForNew = ITEMS.register("fornew",
            () -> new ForNew(new Item.Properties()));
    public static final RegistryObject<Item> ORIGIN_KNIFE_PLAIN = ITEMS.register("origin_knife_plain",
            () -> new OriginKnife(new Item.Properties().rarity(CustomStyle.Plain),
                    CustomStyle.styleOfPlain, ComponentUtils.getSuffixOfChapterI(), 0));

    public static final RegistryObject<Item> ORIGIN_KNIFE_FOREST = ITEMS.register("origin_knife_forest",
            () -> new OriginKnife(new Item.Properties().rarity(CustomStyle.Forest),
                    CustomStyle.styleOfForest, ComponentUtils.getSuffixOfChapterI(), 1));

    public static final RegistryObject<Item> ORIGIN_KNIFE_LAKE = ITEMS.register("origin_knife_lake",
            () -> new OriginKnife(new Item.Properties().rarity(CustomStyle.Lake),
                    CustomStyle.styleOfLake, ComponentUtils.getSuffixOfChapterI(), 2));

    public static final RegistryObject<Item> ORIGIN_KNIFE_MINE = ITEMS.register("origin_knife_mine",
            () -> new OriginKnife(new Item.Properties().rarity(CustomStyle.Mine),
                    CustomStyle.styleOfMine, ComponentUtils.getSuffixOfChapterI(), 3));

    public static final RegistryObject<Item> ORIGIN_KNIFE_VOLCANO = ITEMS.register("origin_knife_volcano",
            () -> new OriginKnife(new Item.Properties().rarity(CustomStyle.Volcano),
                    CustomStyle.styleOfVolcano, ComponentUtils.getSuffixOfChapterII(), 4));

    public static final RegistryObject<Item> ORIGIN_KNIFE_SKY = ITEMS.register("origin_knife_sky",
            () -> new OriginKnife(new Item.Properties().rarity(CustomStyle.Sky),
                    CustomStyle.styleOfSky, ComponentUtils.getSuffixOfChapterII(), 5));

    public static final RegistryObject<Item> BAMBOO_KANATA = ITEMS.register("bamboo_kanata",
            () -> new WraqKanata(new Item.Properties().rarity(CustomStyle.WindBold), 0.15, CustomStyle.styleOfKaze,
                    ComponentUtils.getSuffixOfChapterI()));

    public static final RegistryObject<Item> SKY_KANATA = ITEMS.register("sky_kanata",
            () -> new WraqKanata(new Item.Properties().rarity(CustomStyle.SkyBold), 0.3, CustomStyle.styleOfSky,
                    ComponentUtils.getSuffixOfChapterII()));

    public static final RegistryObject<Item> NETHER_KANATA = ITEMS.register("nether_kanata",
            () -> new WraqKanata(new Item.Properties().rarity(CustomStyle.NetherBold), 0.45, CustomStyle.styleOfNether,
                    ComponentUtils.getSuffixOfNether()));

    public static final RegistryObject<Item> SAKURA_KANATA = ITEMS.register("sakura_kanata",
            () -> new WraqKanata(new Item.Properties().rarity(CustomStyle.SakuraBold), 0.6, CustomStyle.styleOfSakura,
                    ComponentUtils.getSuffixOfSakura()));

    public static final RegistryObject<Item> RunePiece = ITEMS.register("rune_piece",
            () -> new Piece(new Item.Properties()));
    public static final RegistryObject<Item> PlainRune0 = ITEMS.register("green_runes_0",
            () -> new GreenRunes_0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PlainRune1 = ITEMS.register("green_runes_1",
            () -> new GreenRunes_1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorPlain = ITEMS.register("armor1",
            () -> new MobArmor(StringUtils.MobName.PlainZombie));
    public static final RegistryObject<Item> PlainRune2 = ITEMS.register("green_runes_2",
            () -> new GreenRunes_2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PlainSoul = ITEMS.register("plain_souls",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Plain)));
    public static final RegistryObject<Item> PlainRune = ITEMS.register("plain_runes",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PlainBold)));
    public static final RegistryObject<Item> runes = ITEMS.register("runes",
            () -> new Profile(new Item.Properties()));
    public static final RegistryObject<Item> PlainRune3 = ITEMS.register("green_runes_3",
            () -> new GreenRunes_3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorForestSkeleton = ITEMS.register("armor2",
            () -> new MobArmor(StringUtils.MobName.ForestSkeleton));

    public static final RegistryObject<Item> PlainSword0 = ITEMS.register("plainsword0",
            () -> new PlainSword(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> PlainSword1 = ITEMS.register("plainsword1",
            () -> new PlainSword(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> PlainSword2 = ITEMS.register("plainsword2",
            () -> new PlainSword(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> PlainSword3 = ITEMS.register("plainsword3",
            () -> new PlainSword(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PlainBow0 = ITEMS.register("plainbow0",
            () -> new PlainBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> PlainBow1 = ITEMS.register("plainbow1",
            () -> new PlainBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> PlainBow2 = ITEMS.register("plainbow2",
            () -> new PlainBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> PlainBow3 = ITEMS.register("plainbow3",
            () -> new PlainBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PlainSceptre0 = ITEMS.register("plainsceptre0",
            () -> new PlainSceptre(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> PlainSceptre1 = ITEMS.register("plainsceptre1",
            () -> new PlainSceptre(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> PlainSceptre2 = ITEMS.register("plainsceptre2",
            () -> new PlainSceptre(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> PlainSceptre3 = ITEMS.register("plainsceptre3",
            () -> new PlainSceptre(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));
    public static final RegistryObject<Item> PlainSceptre4 = ITEMS.register("plainsceptre4",
            () -> new PlainSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic), 4));

    public static final RegistryObject<Item> PlainPower = ITEMS.register("plain_power",
            () -> new PlainPower(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> PlainPower1 = ITEMS.register("plain_power1",
            () -> new PlainPower(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> PlainPower2 = ITEMS.register("plain_power2",
            () -> new PlainPower(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> PlainPower3 = ITEMS.register("plain_power3",
            () -> new PlainPower(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PlainManaBook = ITEMS.register("mananote_plain",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.PlainItalic), 0,
                    20, 0.1, 1, 100, 8, 0.05, 0.1));

    public static final RegistryObject<Item> PlainArmorHelmet = ITEMS.register("plainarmorhelmet",
            () -> new PlainArmorHelmet(ItemMaterial.PlainMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> PlainArmorChest = ITEMS.register("plainarmorchest",
            () -> new PlainArmorChest(ItemMaterial.PlainMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> PlainArmorLeggings = ITEMS.register("plainarmorleggings",
            () -> new PlainArmorLeggings(ItemMaterial.PlainMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> PlainArmorBoots = ITEMS.register("plainarmorboots",
            () -> new PlainArmorBoots(ItemMaterial.PlainMaterialBoots, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> ArmorForestZombie = ITEMS.register("armorforest2",
            () -> new MobArmor(StringUtils.MobName.ForestZombie));
    public static final RegistryObject<Item> ArmorBlaze = ITEMS.register("armorblaze",
            () -> new MobArmor(StringUtils.MobName.VolcanoBlaze));
    public static final RegistryObject<Item> ForestSoul = ITEMS.register("forestsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Forest)));
    public static final RegistryObject<Item> ForestRune = ITEMS.register("forestrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.ForestBold)));

    public static final RegistryObject<Item> ForestSword0 = ITEMS.register("forestsword0",
            () -> new ForestSword(new Item.Properties().rarity(CustomStyle.ForestItalic), 0));
    public static final RegistryObject<Item> ForestSword1 = ITEMS.register("forestsword1",
            () -> new ForestSword(new Item.Properties().rarity(CustomStyle.ForestItalic), 1));
    public static final RegistryObject<Item> ForestSword2 = ITEMS.register("forestsword2",
            () -> new ForestSword(new Item.Properties().rarity(CustomStyle.ForestItalic), 2));
    public static final RegistryObject<Item> ForestSword3 = ITEMS.register("forestsword3",
            () -> new ForestSword(new Item.Properties().rarity(CustomStyle.ForestItalic), 3));

    public static final RegistryObject<Item> ForestBow0 = ITEMS.register("forestbow0",
            () -> new ForestBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.ForestItalic), 0));
    public static final RegistryObject<Item> ForestBow1 = ITEMS.register("forestbow1",
            () -> new ForestBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.ForestItalic), 1));
    public static final RegistryObject<Item> ForestBow2 = ITEMS.register("forestbow2",
            () -> new ForestBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.ForestItalic), 2));
    public static final RegistryObject<Item> ForestBow3 = ITEMS.register("forestbow3",
            () -> new ForestBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.ForestItalic), 3));

    public static final RegistryObject<Item> ForestPower = ITEMS.register("forest_power",
            () -> new ForestPower(new Item.Properties().rarity(CustomStyle.ForestItalic), 0));
    public static final RegistryObject<Item> ForestPower1 = ITEMS.register("forest_power1",
            () -> new ForestPower(new Item.Properties().rarity(CustomStyle.ForestItalic), 1));
    public static final RegistryObject<Item> ForestPower2 = ITEMS.register("forest_power2",
            () -> new ForestPower(new Item.Properties().rarity(CustomStyle.ForestItalic), 2));
    public static final RegistryObject<Item> ForestPower3 = ITEMS.register("forest_power3",
            () -> new ForestPower(new Item.Properties().rarity(CustomStyle.ForestItalic), 3));

    public static final RegistryObject<Item> ForestManaBook = ITEMS.register("mananote_forest",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.ForestItalic), 1,
                    40, 0.12, 1, 125, 10, 0.1, 0.15));

    public static final RegistryObject<Item> ForestArmorHelmet = ITEMS.register("forestarmorhelmet",
            () -> new ForestArmorHelmet(ItemMaterial.Forest, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> ForestArmorChest = ITEMS.register("forestarmorchest",
            () -> new ForestArmorChest(ItemMaterial.Forest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> ForestArmorLeggings = ITEMS.register("forestarmorleggings",
            () -> new ForestArmorLeggings(ItemMaterial.Forest, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> ForestArmorBoots = ITEMS.register("forestarmorboots",
            () -> new ForestArmorBoots(ItemMaterial.Forest, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> gemPiece = ITEMS.register("gemspiece",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE), false, false));
    public static final RegistryObject<Item> completeGem = ITEMS.register("complete_gem",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ReputationMedal = ITEMS.register("reputation_medal",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CrudeCoal = ITEMS.register("crude_coal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Plain)));

    public static final RegistryObject<Item> HotCoal = ITEMS.register("hot_coal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Volcano)));

    public static final RegistryObject<Item> RefiningCoal = ITEMS.register("refining_coal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Lake)));

    public static final RegistryObject<Item> BlazeCoal = ITEMS.register("blaze_coal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Quartz)));

    public static final RegistryObject<Item> CrudeIron = ITEMS.register("crude_iron",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Plain)));

    public static final RegistryObject<Item> HotIron = ITEMS.register("hot_iron",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Volcano)));

    public static final RegistryObject<Item> RefiningIron = ITEMS.register("refining_iron",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Lake)));

    public static final RegistryObject<Item> CrudeCopper = ITEMS.register("crude_copper",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Plain)));

    public static final RegistryObject<Item> HotCopper = ITEMS.register("hot_copper",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Volcano)));

    public static final RegistryObject<Item> RefiningCopper = ITEMS.register("refining_copper",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Lake)));

    public static final RegistryObject<Item> CrudeGold = ITEMS.register("crude_gold",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Plain)));

    public static final RegistryObject<Item> BlazeGold = ITEMS.register("blaze_gold",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Quartz)));

    public static final RegistryObject<Item> RefiningGold = ITEMS.register("refining_gold",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Lake)));

    public static final RegistryObject<Item> NaturalCore = ITEMS.register("natural_core",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Life), false, List.of(
                    Te.s("与", "农耕大师", CustomStyle.styleOfField, "交易获取")
            )));

    public static final RegistryObject<Item> OreRune = ITEMS.register("ore_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MineBold), false, List.of(
                    Te.s("与", "采矿大师", CustomStyle.styleOfMine, "交易获取")
            )));

    public static final RegistryObject<Item> bossaward1 = ITEMS.register("bossaward1",
            () -> new Boss1(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> bossaward2 = ITEMS.register("bossaward2",
            () -> new Boss2(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LakeSoul = ITEMS.register("watersoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Lake)));
    public static final RegistryObject<Item> LakeRune = ITEMS.register("waterrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LakeBold)));
    public static final RegistryObject<Item> VolcanoSoul = ITEMS.register("volcanosoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> VolcanoRune = ITEMS.register("volcanorune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.VolcanoBold)));
    public static final RegistryObject<Item> Main0_4 = ITEMS.register("main0_4",
            () -> new Main0_4(new Item.Properties()));
    public static final RegistryObject<Item> Main0_5 = ITEMS.register("main0_5",
            () -> new Main0_5(new Item.Properties()));
    public static final RegistryObject<Item> Main1_5 = ITEMS.register("main1_5",
            () -> new Main1_5(new Item.Properties()));
    public static final RegistryObject<Item> BackSpawn = ITEMS.register("backspawn",
            () -> new BackSpawn(new Item.Properties().rarity(Rarity.create("souvenirs", ChatFormatting.BLUE)).stacksTo(1)));
    public static final RegistryObject<Item> main1reward = ITEMS.register("main1reward",
            () -> new Main1Reward(new Item.Properties()));
    public static final RegistryObject<Item> ForestRune0 = ITEMS.register("forestrune0",
            () -> new ForestRune0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ForestRune1 = ITEMS.register("forestrune1",
            () -> new ForestRune1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ForestRune2 = ITEMS.register("forestrune2",
            () -> new ForestRune2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ForestRune3 = ITEMS.register("forestrune3",
            () -> new ForestRune3(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> LakeSword0 = ITEMS.register("lakesword0",
            () -> new LakeSword(new Item.Properties().rarity(CustomStyle.LakeItalic), 0));
    public static final RegistryObject<Item> LakeSword1 = ITEMS.register("lakesword1",
            () -> new LakeSword(new Item.Properties().rarity(CustomStyle.LakeItalic), 1));
    public static final RegistryObject<Item> LakeSword2 = ITEMS.register("lakesword2",
            () -> new LakeSword(new Item.Properties().rarity(CustomStyle.LakeItalic), 2));
    public static final RegistryObject<Item> LakeSword3 = ITEMS.register("lakesword3",
            () -> new LakeSword(new Item.Properties().rarity(CustomStyle.LakeItalic), 3));

    public static final RegistryObject<Item> lakeBow0 = ITEMS.register("lake_bow0",
            () -> new LakeBow(new Item.Properties().rarity(CustomStyle.WaterItalic), 0));
    public static final RegistryObject<Item> lakeBow1 = ITEMS.register("lake_bow1",
            () -> new LakeBow(new Item.Properties().rarity(CustomStyle.WaterItalic), 1));
    public static final RegistryObject<Item> lakeBow2 = ITEMS.register("lake_bow2",
            () -> new LakeBow(new Item.Properties().rarity(CustomStyle.WaterItalic), 2));
    public static final RegistryObject<Item> lakeBow3 = ITEMS.register("lake_bow3",
            () -> new LakeBow(new Item.Properties().rarity(CustomStyle.WaterItalic), 3));

    public static final RegistryObject<Item> lakeSceptre0 = ITEMS.register("lake_sceptre0",
            () -> new LakeSceptre(new Item.Properties().rarity(CustomStyle.WaterItalic), 0));
    public static final RegistryObject<Item> lakeSceptre1 = ITEMS.register("lake_sceptre1",
            () -> new LakeSceptre(new Item.Properties().rarity(CustomStyle.WaterItalic), 1));
    public static final RegistryObject<Item> lakeSceptre2 = ITEMS.register("lake_sceptre2",
            () -> new LakeSceptre(new Item.Properties().rarity(CustomStyle.WaterItalic), 2));
    public static final RegistryObject<Item> lakeSceptre3 = ITEMS.register("lake_sceptre3",
            () -> new LakeSceptre(new Item.Properties().rarity(CustomStyle.WaterItalic), 3));

    public static final RegistryObject<Item> LakePower = ITEMS.register("lake_power",
            () -> new LakePower(new Item.Properties().rarity(CustomStyle.WaterItalic), 0));
    public static final RegistryObject<Item> LakePower1 = ITEMS.register("lake_power1",
            () -> new LakePower(new Item.Properties().rarity(CustomStyle.WaterItalic), 1));
    public static final RegistryObject<Item> LakePower2 = ITEMS.register("lake_power2",
            () -> new LakePower(new Item.Properties().rarity(CustomStyle.WaterItalic), 2));
    public static final RegistryObject<Item> LakePower3 = ITEMS.register("lake_power3",
            () -> new LakePower(new Item.Properties().rarity(CustomStyle.WaterItalic), 3));

    public static final RegistryObject<Item> LakeManaBook = ITEMS.register("mananote_lake",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.LakeItalic), 2,
                    60, 0.14, 1, 150, 12, 0.15, 0.2));

    public static final RegistryObject<Item> LakeArmorHelmet = ITEMS.register("lakearmorhelmet",
            () -> new LakeArmorHelmet(ItemMaterial.Lake, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> LakeArmorChest = ITEMS.register("lakearmorchest",
            () -> new LakeArmorChest(ItemMaterial.LakeMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> LakeArmorLeggings = ITEMS.register("lakearmorleggings",
            () -> new LakeArmorLeggings(ItemMaterial.LakeMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> LakeArmorBoots = ITEMS.register("lakearmorboots",
            () -> new LakeArmorBoots(ItemMaterial.LakeMaterialBoots, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> MineSword0 = ITEMS.register("minesword0",
            () -> new MineSword(new Item.Properties().rarity(CustomStyle.MineItalic), 0));
    public static final RegistryObject<Item> MineSword1 = ITEMS.register("minesword1",
            () -> new MineSword(new Item.Properties().rarity(CustomStyle.MineItalic), 1));
    public static final RegistryObject<Item> MineSword2 = ITEMS.register("minesword2",
            () -> new MineSword(new Item.Properties().rarity(CustomStyle.MineItalic), 2));
    public static final RegistryObject<Item> MineSword3 = ITEMS.register("minesword3",
            () -> new MineSword(new Item.Properties().rarity(CustomStyle.MineItalic), 3));

    public static final RegistryObject<Item> MineBow0 = ITEMS.register("minebow0",
            () -> new MineBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.MineItalic), 0));
    public static final RegistryObject<Item> MineBow1 = ITEMS.register("minebow1",
            () -> new MineBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.MineItalic), 1));
    public static final RegistryObject<Item> MineBow2 = ITEMS.register("minebow2",
            () -> new MineBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.MineItalic), 2));
    public static final RegistryObject<Item> MineBow3 = ITEMS.register("minebow3",
            () -> new MineBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.MineItalic), 3));

    public static final RegistryObject<Item> MineShield = ITEMS.register("mine_shield",
            () -> new MineShield());

    public static final RegistryObject<Item> MineArmorHelmet = ITEMS.register("minearmorhelmet",
            () -> new MineArmorHelmet(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> MineArmorChest = ITEMS.register("minearmorchest",
            () -> new MineArmorChest(ItemMaterial.IslandMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> MineArmorLeggings = ITEMS.register("minearmorleggings",
            () -> new MineArmorLeggings(ItemMaterial.IslandMaterial, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> MineArmorBoots = ITEMS.register("minearmorboots",
            () -> new MineArmorBoots(ItemMaterial.IslandMaterial, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> VolcanoSword0 = ITEMS.register("volcanosword0",
            () -> new VolcanoSword(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 0));
    public static final RegistryObject<Item> VolcanoSword1 = ITEMS.register("volcanosword1",
            () -> new VolcanoSword(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 1));
    public static final RegistryObject<Item> VolcanoSword2 = ITEMS.register("volcanosword2",
            () -> new VolcanoSword(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 2));
    public static final RegistryObject<Item> VolcanoSword3 = ITEMS.register("volcanosword3",
            () -> new VolcanoSword(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 3));

    public static final RegistryObject<Item> VolcanoBow0 = ITEMS.register("volcanobow0",
            () -> new VolcanoBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.VolcanoItalic), 0));
    public static final RegistryObject<Item> VolcanoBow1 = ITEMS.register("volcanobow1",
            () -> new VolcanoBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.VolcanoItalic), 1));
    public static final RegistryObject<Item> VolcanoBow2 = ITEMS.register("volcanobow2",
            () -> new VolcanoBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.VolcanoItalic), 2));
    public static final RegistryObject<Item> VolcanoBow3 = ITEMS.register("volcanobow3",
            () -> new VolcanoBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.VolcanoItalic), 3));

    public static final RegistryObject<Item> VolcanoPower = ITEMS.register("volcano_power",
            () -> new VolcanoPower(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 0));
    public static final RegistryObject<Item> VolcanoPower1 = ITEMS.register("volcano_power1",
            () -> new VolcanoPower(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 1));
    public static final RegistryObject<Item> VolcanoPower2 = ITEMS.register("volcano_power2",
            () -> new VolcanoPower(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 2));
    public static final RegistryObject<Item> VolcanoPower3 = ITEMS.register("volcano_power3",
            () -> new VolcanoPower(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 3));

    public static final RegistryObject<Item> VolcanoManaBook = ITEMS.register("mananote_volcano",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 3,
                    80, 0.17, 2, 175, 14, 0.2, 0.25));

    public static final RegistryObject<Item> VolcanoArmorHelmet = ITEMS.register("volcanoarmorhelmet",
            () -> new VolcanoArmorHelmet(ItemMaterial.VolcanoMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> VolcanoArmorChest = ITEMS.register("volcanoarmorchest",
            () -> new VolcanoArmorChest(ItemMaterial.VolcanoMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> VolcanoArmorLeggings = ITEMS.register("volcanoarmorleggings",
            () -> new VolcanoArmorLeggings(ItemMaterial.VolcanoMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> VolcanoArmorBoots = ITEMS.register("volcanoarmorboots",
            () -> new VolcanoArmorBoots(ItemMaterial.VolcanoMaterialBoots, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> attributecheck = ITEMS.register("attributecheck",
            () -> new AttributeCheck(new Item.Properties()));

    public static final RegistryObject<Item> ClearTree = ITEMS.register("clear_tree",
            () -> new ClearTree(new Item.Properties()));

    public static final RegistryObject<Item> PlainRing = ITEMS.register("plaingems",
            () -> new PlainRing(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic)));
    public static final RegistryObject<Item> ForestRing = ITEMS.register("forestgems",
            () -> new ForestRing(new Item.Properties().stacksTo(1).rarity(CustomStyle.ForestItalic)));
    public static final RegistryObject<Item> LakeRing = ITEMS.register("lakegems",
            () -> new LakeRing(new Item.Properties().stacksTo(1).rarity(CustomStyle.LakeItalic)));
    public static final RegistryObject<Item> VolcanoRing = ITEMS.register("volcanogems",
            () -> new VolcanoRing(new Item.Properties().stacksTo(1).rarity(CustomStyle.VolcanoItalic)));
    public static final RegistryObject<Item> tickettosky = ITEMS.register("tickettosky",
            () -> new TicketToSkyCity(new Item.Properties().rarity(Rarity.create("souvenirs", ChatFormatting.BLUE))));
    public static final RegistryObject<Item> quest = ITEMS.register("quest",
            () -> new Quest(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> hovertest = ITEMS.register("hovertest",
            () -> new HoverTest(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ArrowItem = ITEMS.register("arrowitem",
            () -> new ArrowItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> ArmorMine = ITEMS.register("armormine",
            () -> new MobArmor(StringUtils.MobName.MineZombie));
    public static final RegistryObject<Item> profession_bow = ITEMS.register("profession_bow",
            () -> new Profession_Bow(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> profession_sword = ITEMS.register("profession_sword",
            () -> new Profession_Sword(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> profession_barker = ITEMS.register("profession_barker",
            () -> new Profession_Barker(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MineSoul = ITEMS.register("minesoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Mine)));
    public static final RegistryObject<Item> MineSoul1 = ITEMS.register("minesoul1",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MineRune = ITEMS.register("minerune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MineBold)));
    public static final RegistryObject<Item> FieldSoul = ITEMS.register("fieldsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Field)));
    public static final RegistryObject<Item> FieldRune = ITEMS.register("fieldrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.FieldBold)));
    public static final RegistryObject<Item> FieldSword0 = ITEMS.register("fieldsword0",
            () -> new FieldSword(new Item.Properties().rarity(CustomStyle.FieldItalic), 0));
    public static final RegistryObject<Item> FieldSword1 = ITEMS.register("fieldsword1",
            () -> new FieldSword(new Item.Properties().rarity(CustomStyle.FieldItalic), 1));
    public static final RegistryObject<Item> FieldSword2 = ITEMS.register("fieldsword2",
            () -> new FieldSword(new Item.Properties().rarity(CustomStyle.FieldItalic), 2));
    public static final RegistryObject<Item> FieldSword3 = ITEMS.register("fieldsword3",
            () -> new FieldSword(new Item.Properties().rarity(CustomStyle.FieldItalic), 3));
    public static final RegistryObject<Item> SnowSoul = ITEMS.register("snowsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Snow)));
    public static final RegistryObject<Item> SnowRune = ITEMS.register("snowrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SnowBold)));

    public static final RegistryObject<Item> SnowSword0 = ITEMS.register("snowsword0",
            () -> new SnowSword(new Item.Properties().rarity(CustomStyle.SnowItalic), 0));
    public static final RegistryObject<Item> SnowSword1 = ITEMS.register("snowsword1",
            () -> new SnowSword(new Item.Properties().rarity(CustomStyle.SnowItalic), 1));
    public static final RegistryObject<Item> SnowSword2 = ITEMS.register("snowsword2",
            () -> new SnowSword(new Item.Properties().rarity(CustomStyle.SnowItalic), 2));
    public static final RegistryObject<Item> SnowSword3 = ITEMS.register("snowsword3",
            () -> new SnowSword(new Item.Properties().rarity(CustomStyle.SnowItalic), 3));

    public static final RegistryObject<Item> SnowPower = ITEMS.register("snow_power",
            () -> new SnowPower(new Item.Properties().rarity(CustomStyle.SnowItalic), 0));
    public static final RegistryObject<Item> SnowPower1 = ITEMS.register("snow_power1",
            () -> new SnowPower(new Item.Properties().rarity(CustomStyle.SnowItalic), 1));
    public static final RegistryObject<Item> SnowPower2 = ITEMS.register("snow_power2",
            () -> new SnowPower(new Item.Properties().rarity(CustomStyle.SnowItalic), 2));
    public static final RegistryObject<Item> SnowPower3 = ITEMS.register("snow_power3",
            () -> new SnowPower(new Item.Properties().rarity(CustomStyle.SnowItalic), 3));

    public static final RegistryObject<Item> SnowManaBook = ITEMS.register("mananote_snow",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.SnowItalic), 4,
                    100, 0.2, 2, 200, 16, 0.25, 0.3));

    public static final RegistryObject<Item> SnowShield = ITEMS.register("snow_shield",
            () -> new SnowShield());

    public static final RegistryObject<Item> Note_1 = ITEMS.register("note_1",
            () -> new Note_1(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Note_2 = ITEMS.register("note_2",
            () -> new Note_2(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Note_3 = ITEMS.register("note_3",
            () -> new Note_3(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SmartPhone = ITEMS.register("smartphone",
            () -> new SmartPhoneitem(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> Security = ITEMS.register("security",
            () -> new openSecurity(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ResetSecurity = ITEMS.register("resetsecurity",
            () -> new ResetSecurity(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Main1Crystal = ITEMS.register("main1crystal",
            () -> new main1crystal(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NewCurios = ITEMS.register("newcurios",
            () -> new NewCurios(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SpeIron = ITEMS.register("speiron",
            () -> new SpeIronIngot(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> tick = ITEMS.register("tick",
            () -> new Tick(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SkyBow = ITEMS.register("skybow",
            () -> new SkyBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.SkyItalic)));
    public static final RegistryObject<Item> SkySoul = ITEMS.register("skysoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Sky)));
    public static final RegistryObject<Item> SkyRune = ITEMS.register("skyrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> EntityCopy = ITEMS.register("entitycopy",
            () -> new EntityTP(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BlockReset = ITEMS.register("blockreset",
            () -> new BlockPosReset(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EvokerSoul = ITEMS.register("evokersoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Evoker)));
    public static final RegistryObject<Item> ManaBucket = ITEMS.register("manabucket",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Evoker)));
    public static final RegistryObject<Item> EvokerRune = ITEMS.register("evokerrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EvokerBold)));
    public static final RegistryObject<Item> ManaBalance_Empty = ITEMS.register("manabalance_empty",
            () -> new ManaBalance_Empty(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ManaBalance_filled = ITEMS.register("manabalance_filled",
            () -> new ManaBalance_Filled(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> EvokerSword = ITEMS.register("evokersword",
            () -> new EvokerSceptre(new Item.Properties().rarity(CustomStyle.EvokerItalic), 0));
    public static final RegistryObject<Item> EvokerSword1 = ITEMS.register("evokersword1",
            () -> new EvokerSceptre(new Item.Properties().rarity(CustomStyle.EvokerItalic), 1));
    public static final RegistryObject<Item> EvokerSword2 = ITEMS.register("evokersword2",
            () -> new EvokerSceptre(new Item.Properties().rarity(CustomStyle.EvokerItalic), 2));
    public static final RegistryObject<Item> EvokerSword3 = ITEMS.register("evokersword3",
            () -> new EvokerSceptre(new Item.Properties().rarity(CustomStyle.EvokerItalic), 3));

    public static final RegistryObject<Item> EvokerBook0 = ITEMS.register("evokerbook0",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.EvokerItalic), 5,
                    120, 0.22, 2, 225, 17, 0.3, 0.35));

    public static final RegistryObject<Item> EvokerBook1 = ITEMS.register("evokerbook1",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.EvokerItalic), 6,
                    140, 0.24, 3, 250, 18, 0.35, 0.4));

    public static final RegistryObject<Item> EvokerBook2 = ITEMS.register("evokerbook2",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.EvokerItalic), 7,
                    160, 0.26, 4, 275, 19, 0.4, 0.45));

    public static final RegistryObject<Item> EvokerBook3 = ITEMS.register("evokerbook3",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.EvokerItalic), 8,
                    180, 0.28, 5, 300, 20, 0.45, 0.5));

    public static final RegistryObject<Item> LifeManaArmorHelmet = ITEMS.register("lifemanaarmorhelmet",
            () -> new LifeManaArmor(ItemMaterial.LifeMana, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LifeManaArmorChest = ITEMS.register("lifemanaarmorchest",
            () -> new LifeManaArmor(ItemMaterial.LifeMana, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LifeManaArmorLeggings = ITEMS.register("lifemanaarmorleggings",
            () -> new LifeManaArmor(ItemMaterial.LifeMana, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LifeManaArmorBoots = ITEMS.register("lifemanaarmorboots",
            () -> new LifeManaArmor(ItemMaterial.LifeMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.LifeItalic)));

    public static final RegistryObject<Item> LifeManaArmorHelmetE = ITEMS.register("life_armor_helmet_e",
            () -> new LifeMana1(ItemMaterial.ArmorLifeE, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LifeManaArmorChestE = ITEMS.register("life_armor_chest_e",
            () -> new LifeMana1(ItemMaterial.ArmorLifeE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LifeManaArmorLeggingsE = ITEMS.register("life_armor_leggings_e",
            () -> new LifeMana1(ItemMaterial.ArmorLifeE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LifeManaArmorBootsE = ITEMS.register("life_armor_boots_e",
            () -> new LifeMana1(ItemMaterial.ArmorLifeE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.LifeItalic)));

    public static final RegistryObject<Item> ObsiManaArmorHelmet = ITEMS.register("obsimanaarmorhelmet",
            () -> new ObsiManaArmor(ItemMaterial.ObsiMana, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> ObsiManaArmorChest = ITEMS.register("obsimanaarmorchest",
            () -> new ObsiManaArmor(ItemMaterial.ObsiMana, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> ObsiManaArmorLeggings = ITEMS.register("obsimanaarmorleggings",
            () -> new ObsiManaArmor(ItemMaterial.ObsiMana, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> ObsiManaArmorBoots = ITEMS.register("obsimanaarmorboots",
            () -> new ObsiManaArmor(ItemMaterial.ObsiMana, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> ObsiManaArmorHelmetE = ITEMS.register("obsi_armor_helmet_e",
            () -> new ObsiMana1(ItemMaterial.ArmorObsiE, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> ObsiManaArmorChestE = ITEMS.register("obsi_armor_chest_e",
            () -> new ObsiMana1(ItemMaterial.ArmorObsiE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> ObsiManaArmorLeggingsE = ITEMS.register("obsi_armor_leggings_e",
            () -> new ObsiMana1(ItemMaterial.ArmorObsiE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> ObsiManaArmorBootsE = ITEMS.register("obsi_armor_boots_e",
            () -> new ObsiMana1(ItemMaterial.ArmorObsiE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.EvokerItalic)));

    public static final RegistryObject<Item> plainmana = ITEMS.register("plainmana",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> forestmana = ITEMS.register("forestmana",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> lakemana = ITEMS.register("lakemana",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> volcanomana = ITEMS.register("volcanomana",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ForgingStone0 = ITEMS.register("forgingstone0",
            () -> new ForgingStone0(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ForgingStone1 = ITEMS.register("forgingstone1",
            () -> new ForgingStone1(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ForgingStone2 = ITEMS.register("forgingstone2",
            () -> new ForgingStone2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> Ruby = ITEMS.register("ruby",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Nether)));
    public static final RegistryObject<Item> NetherQuartz = ITEMS.register("netherquartz",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Quartz)));
    public static final RegistryObject<Item> NetherSoul = ITEMS.register("nethersoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Nether)));
    public static final RegistryObject<Item> NetherRune = ITEMS.register("netherrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold)));
    public static final RegistryObject<Item> NetherSwordModel = ITEMS.register("netherswordmodel",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> witherSkeletonSoul = ITEMS.register("witherbone",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Wither)));

    public static final RegistryObject<Item> ManaSword = ITEMS.register("manasword",
            () -> new ManaSword(new Item.Properties().rarity(CustomStyle.EvokerItalic)));

    public static final RegistryObject<Item> WitherRune = ITEMS.register("wither_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WitherBold), true, true, List.of(
                    Te.s("经下界能量浇筑的凋零残骨。", CustomStyle.styleOfNether),
                    Te.s("凋零与下界能量在这个物体中互相交融，散发出阵阵能量光。", CustomStyle.styleOfNether)
            )));
    public static final RegistryObject<Item> PiglinRune = ITEMS.register("piglin_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PiglinBold), true, true, List.of(
                    Te.s("猪灵最喜爱的物件。", CustomStyle.styleOfGold)
            )));
    public static final RegistryObject<Item> NetherSkeletonRune = ITEMS.register("nether_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WitherBold), true, true, List.of(
                    Te.s("下界遗骸的精细粉末。", CustomStyle.styleOfNether),
                    Te.s("将下界遗骸粉末融入下界能量进一步研磨而成。", CustomStyle.styleOfNether)
            )));
    public static final RegistryObject<Item> MagmaRune = ITEMS.register("magma_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MagmaBold), true, true, List.of(
                    Te.s("封装的下界熔岩能量。", CustomStyle.styleOfPower),
                    Te.s("能够放置在背包里已经是个奇迹了。", CustomStyle.styleOfPower)
            )));

    public static final RegistryObject<Item> WitherSword0 = ITEMS.register("wither_sword_0",
            () -> new WitherSword(new Item.Properties().rarity(CustomStyle.WitherItalic), 0));
    public static final RegistryObject<Item> WitherSword1 = ITEMS.register("wither_sword_1",
            () -> new WitherSword(new Item.Properties().rarity(CustomStyle.WitherItalic), 1));
    public static final RegistryObject<Item> WitherSword2 = ITEMS.register("wither_sword_2",
            () -> new WitherSword(new Item.Properties().rarity(CustomStyle.WitherItalic), 2));
    public static final RegistryObject<Item> WitherSword3 = ITEMS.register("wither_sword_3",
            () -> new WitherSword(new Item.Properties().rarity(CustomStyle.WitherItalic), 3));

    public static final RegistryObject<Item> PiglinHelmet0 = ITEMS.register("piglin_helmet_0",
            () -> new PiglinHelmet(ItemMaterial.Golden, ArmorItem.Type.HELMET, 0));
    public static final RegistryObject<Item> PiglinHelmet1 = ITEMS.register("piglin_helmet_1",
            () -> new PiglinHelmet(ItemMaterial.Golden, ArmorItem.Type.HELMET, 1));
    public static final RegistryObject<Item> PiglinHelmet2 = ITEMS.register("piglin_helmet_2",
            () -> new PiglinHelmet(ItemMaterial.Golden, ArmorItem.Type.HELMET, 2));
    public static final RegistryObject<Item> PiglinHelmet3 = ITEMS.register("piglin_helmet_3",
            () -> new PiglinHelmet(ItemMaterial.Golden, ArmorItem.Type.HELMET, 3));

    public static final RegistryObject<Item> WitherBow0 = ITEMS.register("wither_bow_0",
            () -> new WitherBow(new Item.Properties().rarity(CustomStyle.WitherItalic).stacksTo(1), 0));
    public static final RegistryObject<Item> WitherBow1 = ITEMS.register("wither_bow_1",
            () -> new WitherBow(new Item.Properties().rarity(CustomStyle.WitherItalic).stacksTo(1), 1));
    public static final RegistryObject<Item> WitherBow2 = ITEMS.register("wither_bow_2",
            () -> new WitherBow(new Item.Properties().rarity(CustomStyle.WitherItalic).stacksTo(1), 2));
    public static final RegistryObject<Item> WitherBow3 = ITEMS.register("wither_bow_3",
            () -> new WitherBow(new Item.Properties().rarity(CustomStyle.WitherItalic).stacksTo(1), 3));

    public static final RegistryObject<Item> WitherBook = ITEMS.register("wither_book",
            () -> new WitherBook(new Item.Properties().rarity(CustomStyle.WitherItalic)));

    public static final RegistryObject<Item> MagmaSceptre0 = ITEMS.register("magma_sceptre_0",
            () -> new MagmaSceptre(new Item.Properties().rarity(Rarity.EPIC), 0));
    public static final RegistryObject<Item> MagmaSceptre1 = ITEMS.register("magma_sceptre_1",
            () -> new MagmaSceptre(new Item.Properties().rarity(Rarity.EPIC), 1));
    public static final RegistryObject<Item> MagmaSceptre2 = ITEMS.register("magma_sceptre_2",
            () -> new MagmaSceptre(new Item.Properties().rarity(Rarity.EPIC), 2));
    public static final RegistryObject<Item> MagmaSceptre3 = ITEMS.register("magma_sceptre_3",
            () -> new MagmaSceptre(new Item.Properties().rarity(Rarity.EPIC), 3));

    public static final RegistryObject<Item> manaRune0 = ITEMS.register("manarune0",
            () -> new ManaRune0(new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> manaRune1 = ITEMS.register("manarune1",
            () -> new ManaRune1(new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> manaRune2 = ITEMS.register("manarune2",
            () -> new ManaRune2(new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> manaRune3 = ITEMS.register("manarune3",
            () -> new ManaRune3(new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> PigLinSoul = ITEMS.register("piglinsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Magma)));

    public static final RegistryObject<Item> ArmorNetherInstanceChest = ITEMS.register("nether_instance_chest",
            () -> new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> ArmorNetherInstanceLeggings = ITEMS.register("nether_instance_leggings",
            () -> new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> ArmorNetherInstanceBoots = ITEMS.register("nether_instance_boots",
            () -> new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> netherSkeletonSoul = ITEMS.register("netherbonemeal",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Wither)));
    public static final RegistryObject<Item> WitherBonePower = ITEMS.register("witherbonepower",
            () -> new WitherBonePower(new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> PigLinPower = ITEMS.register("piglinpower",
            () -> new PiglinPower(new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> WitherBoneMealPower = ITEMS.register("witherbonemealpower",
            () -> new WitherBoneMealPower(new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> MagmaPower = ITEMS.register("magmapower",
            () -> new MagmaPower(new Item.Properties().rarity(CustomStyle.MagmaItalic)));
    public static final RegistryObject<Item> NetherPower = ITEMS.register("netherpower",
            () -> new NetherPower(new Item.Properties().rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> NetherBow = ITEMS.register("netherbow",
            () -> new NetherBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> QuartzSword = ITEMS.register("quartzsword",
            () -> new QuartzSword(new Item.Properties().rarity(CustomStyle.QuartzItalic)));
    public static final RegistryObject<Item> QuartzSoul = ITEMS.register("quartzsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Quartz)));
    public static final RegistryObject<Item> QuartzRune = ITEMS.register("quartzrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.QuartzBold)));
    public static final RegistryObject<Item> PowerModel = ITEMS.register("powermodel",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Evoker)));
    public static final RegistryObject<Item> QuartzSabre = ITEMS.register("quartzsabre",
            () -> new QuartzSabre(new Item.Properties().rarity(CustomStyle.QuartzItalic)));

    public static final RegistryObject<Item> NetherSceptre = ITEMS.register("nether_sceptre",
            () -> new NetherSceptre(new Item.Properties().rarity(CustomStyle.ShipItalic)));

    public static final RegistryObject<Item> NETHER_KNIFE = ITEMS.register("nether_knife",
            () -> new NetherKnife(new Item.Properties().rarity(CustomStyle.NetherBold), Te.m("朱雀之翎", CustomStyle.styleOfRed)));

    public static final RegistryObject<Item> PHOENIX_LEATHER = ITEMS.register("phoenix_leather",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold), false, (player) -> {
                Compute.sendEffectLastTimeByItemId(player, "phoenix_leather", 0, true);
                EnhanceNormalAttackModifier.addModifier(player,
                        new EnhanceNormalAttackModifier("PHOENIX_LEATHER", true, 1, 1, (p, mob) -> {
                            Compute.removeEffectLastTimeByItemId(p, "phoenix_leather" );
                        }));
            }));

    public static final RegistryObject<Item> NETHER_SWORD = ITEMS.register("nether_sword",
            () -> new NetherSword(new Item.Properties().rarity(CustomStyle.NetherBold)));

    public static final RegistryObject<Item> BASALT_ROCK = ITEMS.register("basalt_rock",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold), false, (player) -> {
                player.getCooldowns().removeCooldown(NETHER_SWORD.get());
            }));

    public static final RegistryObject<Item> NetherShield = ITEMS.register("nether_shield",
            () -> new NetherShield(new Item.Properties().rarity(CustomStyle.NetherItalic).stacksTo(1)));

    public static final RegistryObject<Item> quartzcheck = ITEMS.register("quartzcheck",
            () -> new quartzsabrecheck(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> NetherSabreModel = ITEMS.register("nethersabremodel",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Quartz)));
    public static final RegistryObject<Item> SeaSoul = ITEMS.register("seasoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Sea)));
    public static final RegistryObject<Item> SeaRune = ITEMS.register("searune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SeaBold)));
    public static final RegistryObject<Item> ArmorLZHelmet = ITEMS.register("armorlzhelmet",
            () -> new MobArmor(ItemMaterial.BasicArmor2, ArmorItem.Type.HELMET, StringUtils.MobName.LightingZombie));
    public static final RegistryObject<Item> ArmorLZChest = ITEMS.register("armorlzchest",
            () -> new MobArmor(ItemMaterial.BasicArmor2, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.LightingZombie));
    public static final RegistryObject<Item> ArmorLZLeggings = ITEMS.register("armorlzleggings",
            () -> new MobArmor(ItemMaterial.BasicArmor2, ArmorItem.Type.LEGGINGS, StringUtils.MobName.LightingZombie));
    public static final RegistryObject<Item> ArmorLZBoots = ITEMS.register("armorlzboots",
            () -> new MobArmor(ItemMaterial.BasicArmor2, ArmorItem.Type.BOOTS, StringUtils.MobName.LightingZombie));
    public static final RegistryObject<Item> LightningSoul = ITEMS.register("lightningsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Lightning)));
    public static final RegistryObject<Item> LightningRune = ITEMS.register("lightningrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LightningBold)));

    public static final RegistryObject<Item> LIGHTNING_HELMET = ITEMS.register("islandarmorhelmet",
            () -> new LightningArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.LightningItalic), 0));
    public static final RegistryObject<Item> LIGHTNING_CHEST = ITEMS.register("islandarmorchest",
            () -> new LightningArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.LightningItalic), 0));
    public static final RegistryObject<Item> LIGHTNING_LEGGINGS = ITEMS.register("islandarmorleggings",
            () -> new LightningArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.LightningItalic), 0));
    public static final RegistryObject<Item> LIGHTNING_BOOTS = ITEMS.register("islandarmorboots",
            () -> new LightningArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.LightningItalic), 0));

    public static final RegistryObject<Item> SeaSword0 = ITEMS.register("seasword0",
            () -> new SeaSword(new Item.Properties().rarity(CustomStyle.SeaItalic), 0));
    public static final RegistryObject<Item> SeaSword1 = ITEMS.register("seasword1",
            () -> new SeaSword(new Item.Properties().rarity(CustomStyle.SeaItalic), 1));
    public static final RegistryObject<Item> SeaSword2 = ITEMS.register("seasword2",
            () -> new SeaSword(new Item.Properties().rarity(CustomStyle.SeaItalic), 2));
    public static final RegistryObject<Item> SeaSword3 = ITEMS.register("seasword3",
            () -> new SeaSword(new Item.Properties().rarity(CustomStyle.SeaItalic), 3));

    public static final RegistryObject<Item> SeaBow = ITEMS.register("sea_bow",
            () -> new SeaBow(new Item.Properties().rarity(CustomStyle.SeaBold)));

    public static final RegistryObject<Item> SeaManaCore = ITEMS.register("sea_mana_core",
            () -> new SeaCore(new Item.Properties().rarity(CustomStyle.SeaBold)));

    public static final RegistryObject<Item> huskSword0 = ITEMS.register("blackforestsword0",
            () -> new HuskSword(new Item.Properties().rarity(CustomStyle.HuskItalic), 0));
    public static final RegistryObject<Item> huskSword1 = ITEMS.register("blackforestsword1",
            () -> new HuskSword(new Item.Properties().rarity(CustomStyle.HuskItalic), 1));
    public static final RegistryObject<Item> huskSword2 = ITEMS.register("blackforestsword2",
            () -> new HuskSword(new Item.Properties().rarity(CustomStyle.HuskItalic), 2));
    public static final RegistryObject<Item> huskSword3 = ITEMS.register("blackforestsword3",
            () -> new HuskSword(new Item.Properties().rarity(CustomStyle.HuskItalic), 3));

    public static final RegistryObject<Item> BlackForestManaCore = ITEMS.register("blackforest_mana_core",
            () -> new BlackForestCore(new Item.Properties().rarity(CustomStyle.HuskBold)));

    public static final RegistryObject<Item> volcanoRune0 = ITEMS.register("volcanorune0",
            () -> new VolcanoRune0(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> volcanoRune1 = ITEMS.register("volcanorune1",
            () -> new VolcanoRune1(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> volcanoRune2 = ITEMS.register("volcanorune2",
            () -> new VolcanoRune2(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> volcanoRune3 = ITEMS.register("volcanorune3",
            () -> new VolcanoRune3(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> DailyMission = ITEMS.register("dailymission",
            () -> new Daily(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));

    public static final RegistryObject<Item> SKY_ARMOR_HELMET = ITEMS.register("skyarmorhelmet",
            () -> new SkyArmor(ItemMaterial.SkyMaterial, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> SKY_ARMOR_CHEST = ITEMS.register("skyarmorchest",
            () -> new SkyArmor(ItemMaterial.SkyMaterial, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> SKY_ARMOR_LEGGINGS = ITEMS.register("skyarmorleggings",
            () -> new SkyArmor(ItemMaterial.SkyMaterial, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> SKY_ARMOR_BOOTS = ITEMS.register("skyarmorboots",
            () -> new SkyArmor(ItemMaterial.SkyMaterial, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.SkyBold)));

    public static final RegistryObject<Item> GoldCoinBag = ITEMS.register("goldcoinbag",
            () -> new GoldCoinBag(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> Purifier = ITEMS.register("purifier",
            () -> new Purifier(new Item.Properties()));
    public static final RegistryObject<Item> PurifiedWater = ITEMS.register("purified_water",
            () -> new PurifiedWater(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BrewingNote = ITEMS.register("brewingnote",
            () -> new BrewingNote(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> PlainSolidifiedSoul = ITEMS.register("plain_solidified_soul",
            () -> new PlainSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Plain)));
    public static final RegistryObject<Item> ForestSolidifiedSoul = ITEMS.register("forest_solidified_soul",
            () -> new ForestSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Forest)));
    public static final RegistryObject<Item> LakeSolidifiedSoul = ITEMS.register("lake_solidified_soul",
            () -> new LakeSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Lake)));
    public static final RegistryObject<Item> VolcanoSolidifiedSoul = ITEMS.register("volcano_solidified_soul",
            () -> new VolcanoSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> SnowSolidifiedSoul = ITEMS.register("snow_solidified_soul",
            () -> new SnowSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Snow)));
    public static final RegistryObject<Item> SkySolidifiedSoul = ITEMS.register("sky_solidified_soul",
            () -> new SkySolidifiedSoul(new Item.Properties().rarity(CustomStyle.Sky)));
    public static final RegistryObject<Item> huskSoul = ITEMS.register("blackforestsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Husk)));
    public static final RegistryObject<Item> huskRune = ITEMS.register("blackforestrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.HuskBold)));
    public static final RegistryObject<Item> EvokerSolidifiedSoul = ITEMS.register("evoker_solidified_soul",
            () -> new EvokerSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Evoker)));
    public static final RegistryObject<Item> NetherSolidifiedSoul = ITEMS.register("nether_solidified_soul",
            () -> new NetherSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Nether)));
    public static final RegistryObject<Item> Solidifier = ITEMS.register("solidifier",
            () -> new Solidifier(new Item.Properties()));
    public static final RegistryObject<Item> Stabilizer = ITEMS.register("stabilizer",
            () -> new Stabilizer(new Item.Properties()));
    public static final RegistryObject<Item> Concentrater = ITEMS.register("concentrater",
            () -> new Concentrater(new Item.Properties()));
    public static final RegistryObject<Item> BackPackTickets = ITEMS.register("backpackticket",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> SunPower = ITEMS.register("sunpower",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> LightningChange = ITEMS.register("lightningchange",
            () -> new LightningChange(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GuiOpen = ITEMS.register("guiopen",
            () -> new SmartPhoneOpen(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> magmaSoul = ITEMS.register("nethermagmapower",
            () -> new NetherMagmaPower(new Item.Properties().rarity(CustomStyle.Magma)));
    public static final RegistryObject<Item> NetherRune0 = ITEMS.register("netherrune0",
            () -> new NetherRune0(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRune1 = ITEMS.register("netherrune1",
            () -> new NetherRune1(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRune2 = ITEMS.register("netherrune2",
            () -> new NetherRune2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> NetherRune3 = ITEMS.register("netherrune3",
            () -> new NetherRune3(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ArmorNSHelmet = ITEMS.register("armornshelmet",
            () -> new MobArmor(ItemMaterial.NetherAll, ArmorItem.Type.HELMET, 400, 50, 75));
    public static final RegistryObject<Item> SnowRune0 = ITEMS.register("snowrune0",
            () -> new SnowRune0(new Item.Properties().rarity(CustomStyle.SnowItalic)));
    public static final RegistryObject<Item> SnowRune1 = ITEMS.register("snowrune1",
            () -> new SnowRune1(new Item.Properties().rarity(CustomStyle.SnowItalic)));
    public static final RegistryObject<Item> SnowRune2 = ITEMS.register("snowrune2",
            () -> new SnowRune2(new Item.Properties().rarity(CustomStyle.SnowItalic)));
    public static final RegistryObject<Item> SnowRune3 = ITEMS.register("snowrune3",
            () -> new SnowRune3(new Item.Properties().rarity(CustomStyle.SnowItalic)));
    public static final RegistryObject<Item> KazeSword0 = ITEMS.register("kazesword0",
            () -> new KazeSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 0));
    public static final RegistryObject<Item> KazeSword1 = ITEMS.register("kazesword1",
            () -> new KazeSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 1));
    public static final RegistryObject<Item> KazeSword2 = ITEMS.register("kazesword2",
            () -> new KazeSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 2));
    public static final RegistryObject<Item> KazeSword3 = ITEMS.register("kazesword3",
            () -> new KazeSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 3));

    public static final RegistryObject<Item> KazeManaCore = ITEMS.register("kaze_mana_core",
            () -> new KazeCore(new Item.Properties().rarity(CustomStyle.KazeBold)));

    public static final RegistryObject<Item> ArmorKazeChest = ITEMS.register("armorkazechest",
            () -> new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.CHESTPLATE, 100, 50, 70));
    public static final RegistryObject<Item> ArmorKazeLeggings = ITEMS.register("armorkazeleggings",
            () -> new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.LEGGINGS, 100, 50, 70));
    public static final RegistryObject<Item> ArmorKazeBoots = ITEMS.register("armorkazeboots",
            () -> new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS, 100, 50, 70));
    public static final RegistryObject<Item> ArmorKazeRecall = ITEMS.register("armorkazerecall",
            () -> new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> KazeSoul = ITEMS.register("kazesoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Kaze)));
    public static final RegistryObject<Item> KazeRune = ITEMS.register("kazerune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.KazeBold)));
    public static final RegistryObject<Item> LakeCore = ITEMS.register("lakecore",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WaterItalic)));
    public static final RegistryObject<Item> KazeBoots = ITEMS.register("kazeboots",
            () -> new KazeArmorBoots(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> SpiderSoul = ITEMS.register("spidersoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SpiderRarity)));
    public static final RegistryObject<Item> SpiderRune = ITEMS.register("spiderrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SpiderBold)));
    public static final RegistryObject<Item> SBoots = ITEMS.register("sboots",
            () -> new SpiderArmorBoots(ItemMaterial.ArmorS, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> SLeggings = ITEMS.register("sleggings",
            () -> new SpiderArmorLeggings(ItemMaterial.ArmorS, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> SChest = ITEMS.register("schest",
            () -> new SpiderArmorChest(ItemMaterial.ArmorS, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SHelmet = ITEMS.register("shelmet",
            () -> new SpiderArmorHelmet(ItemMaterial.ArmorS, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> SunOintment0 = ITEMS.register("sunointment0",
            () -> new SunOintment0(new Item.Properties().rarity(CustomStyle.Life)));
    public static final RegistryObject<Item> SunOintment1 = ITEMS.register("sunointment1",
            () -> new SunOintment0(new Item.Properties().rarity(CustomStyle.Life), 1));
    public static final RegistryObject<Item> SunOintment2 = ITEMS.register("sunointment2",
            () -> new SunOintment0(new Item.Properties().rarity(CustomStyle.LifeItalic), 2));
    public static final RegistryObject<Item> LakeOintment0 = ITEMS.register("lakeointment0",
            () -> new LakeOintment0(new Item.Properties().rarity(CustomStyle.Lake)));
    public static final RegistryObject<Item> LakeOintment1 = ITEMS.register("lakeointment1",
            () -> new LakeOintment0(new Item.Properties().rarity(CustomStyle.Lake), 1));
    public static final RegistryObject<Item> LakeOintment2 = ITEMS.register("lakeointment2",
            () -> new LakeOintment0(new Item.Properties().rarity(CustomStyle.LakeItalic), 2));
    public static final RegistryObject<Item> VolcanoOintment0 = ITEMS.register("volcanoointment0",
            () -> new VolcanoOintment0(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> VolcanoOintment1 = ITEMS.register("volcanoointment1",
            () -> new VolcanoOintment0(new Item.Properties().rarity(CustomStyle.Volcano), 1));
    public static final RegistryObject<Item> VolcanoOintment2 = ITEMS.register("volcanoointment2",
            () -> new VolcanoOintment0(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 2));
    public static final RegistryObject<Item> SnowOintment0 = ITEMS.register("snowointment0",
            () -> new SnowOintment0(new Item.Properties().rarity(CustomStyle.Snow)));
    public static final RegistryObject<Item> SnowOintment1 = ITEMS.register("snowointment1",
            () -> new SnowOintment0(new Item.Properties().rarity(CustomStyle.Snow), 1));
    public static final RegistryObject<Item> SnowOintment2 = ITEMS.register("snowointment2",
            () -> new SnowOintment0(new Item.Properties().rarity(CustomStyle.SnowItalic), 2));
    public static final RegistryObject<Item> SkyOintment0 = ITEMS.register("skyointment0",
            () -> new SkyOintment0(new Item.Properties().rarity(CustomStyle.Sky)));
    public static final RegistryObject<Item> SkyOintment1 = ITEMS.register("skyointment1",
            () -> new SkyOintment0(new Item.Properties().rarity(CustomStyle.Sky), 1));
    public static final RegistryObject<Item> SkyOintment2 = ITEMS.register("skyointment2",
            () -> new SkyOintment0(new Item.Properties().rarity(CustomStyle.SkyItalic), 2));
    public static final RegistryObject<Item> ManaOintment0 = ITEMS.register("manaointment0",
            () -> new ManaOintment0(new Item.Properties().rarity(CustomStyle.Evoker)));
    public static final RegistryObject<Item> ManaOintment1 = ITEMS.register("manaointment1",
            () -> new ManaOintment0(new Item.Properties().rarity(CustomStyle.Evoker), 1));
    public static final RegistryObject<Item> ManaOintment2 = ITEMS.register("manaointment2",
            () -> new ManaOintment0(new Item.Properties().rarity(CustomStyle.EvokerItalic), 2));
    public static final RegistryObject<Item> NetherOintment0 = ITEMS.register("netherointment0",
            () -> new NetherOintment0(new Item.Properties().rarity(CustomStyle.Nether)));
    public static final RegistryObject<Item> NetherOintment1 = ITEMS.register("netherointment1",
            () -> new NetherOintment0(new Item.Properties().rarity(CustomStyle.Nether), 1));
    public static final RegistryObject<Item> NetherOintment2 = ITEMS.register("netherointment2",
            () -> new NetherOintment0(new Item.Properties().rarity(CustomStyle.NetherItalic), 2));

    public static final RegistryObject<Item> VolcanoCore = ITEMS.register("volcanocore",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.VolcanoBold)));
    public static final RegistryObject<Item> CodeSceptre = ITEMS.register("codesceptre",
            () -> new CodeSceptre(fun.wraq.common.registry.ItemTier.VMaterial, 2, 0, new Item.Properties().rarity(CustomStyle.MagmaItalic)));
    public static final RegistryObject<Item> BreakMana = ITEMS.register("breakmana",
            () -> new BreakMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DamageMana = ITEMS.register("damagemana",
            () -> new DamageMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> EffectMana = ITEMS.register("effectmana",
            () -> new EffectMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> GatherMana = ITEMS.register("gathermana",
            () -> new GatherMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> KazeMana = ITEMS.register("kazemana",
            () -> new KazeMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LightningMana = ITEMS.register("lightningmana",
            () -> new LightningMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> RangeMana = ITEMS.register("rangemana",
            () -> new RangeMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> SnowMana = ITEMS.register("snowmana",
            () -> new SnowMana(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> LightningPower = ITEMS.register("lightningpower",
            () -> new LightningPower(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> ManaModel = ITEMS.register("manamodel",
            () -> new ManaModel(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> IronLove = ITEMS.register("ironlove",
            () -> new IronLove(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> toEnd = ITEMS.register("silverfishsoul",
            () -> new ToEnd(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> BlackForestRecallBook = ITEMS.register("blackforestrecall",
            () -> new BlackForestRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EvokerRecallBook = ITEMS.register("evokerrecallbook",
            () -> new EvokerRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> ForestRecallBook = ITEMS.register("forestrecallbook",
            () -> new ForestRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> KazeRecallBook = ITEMS.register("kazerecallbook",
            () -> new KazeRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> LakeRecallBook = ITEMS.register("lakerecallbook",
            () -> new LakeRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> LightningRecallBook = ITEMS.register("lightningrecallbook",
            () -> new LightningRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> NetherRecallBook1 = ITEMS.register("netherrecallbook1",
            () -> new NetherRecallBook1(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> NetherRecallBook2 = ITEMS.register("netherrecallbook2",
            () -> new NetherRecallBook2(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> PlainRecallBook = ITEMS.register("plainrecallbook",
            () -> new PlainRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> SeaRecallBook = ITEMS.register("searecallbook",
            () -> new SeaRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> SkyRecallBook = ITEMS.register("skyrecallbook",
            () -> new SkyRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> SnowRecallBook = ITEMS.register("snowrecallbook",
            () -> new SnowRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> SpiderRecallBook = ITEMS.register("spiderrecallbook",
            () -> new SpiderRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> VolcanoRecallBook = ITEMS.register("volcanorecallbook",
            () -> new VolcanoRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> RecallPiece = ITEMS.register("recallpiece",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BarrierSet = ITEMS.register("barrierset",
            () -> new BarrierSet(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> KazeRecallSoul = ITEMS.register("kazerecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Kaze)));
    public static final RegistryObject<Item> IntensifiedKazeSoul = ITEMS.register("intensifiedkazesoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.KazeBold)));
    public static final RegistryObject<Item> KazeSword4 = ITEMS.register("kazesword4",
            () -> new KazeSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 4));
    public static final RegistryObject<Item> ArmorSpiderRecall = ITEMS.register("armorspiderecall",
            () -> new MobArmor(500, 50, 90));
    public static final RegistryObject<Item> SpiderRecallSoul = ITEMS.register("spiderrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SpiderRarity)));
    public static final RegistryObject<Item> ManageSword = ITEMS.register("managesword",
            () -> new ManageSword(fun.wraq.common.registry.ItemTier.VMaterial, 2, 0));
    public static final RegistryObject<Item> ISArmorHelmet = ITEMS.register("isarmorhelmet",
            () -> new SpiderRecallArmorHelmet(ItemMaterial.ArmorKaze, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> ISArmorChest = ITEMS.register("isarmorchest",
            () -> new SpiderRecallArmorChest(ItemMaterial.ArmorKaze, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> ISArmorLeggings = ITEMS.register("isarmorleggings",
            () -> new SpiderRecallArmorLeggings(ItemMaterial.ArmorKaze, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> ISArmorBoots = ITEMS.register("isarmorboots",
            () -> new SpiderRecallArmorBoots(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> IntensifiedSpiderSoul = ITEMS.register("intensifiedspidersoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SpiderBold)));
    public static final RegistryObject<Item> BlackForestRecallSoul = ITEMS.register("blackforestrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Husk)));
    public static final RegistryObject<Item> IntensifiedBlackForestSoul = ITEMS.register("intensifiedblackforestsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.HuskBold)));
    public static final RegistryObject<Item> ArmorHuskRecall = ITEMS.register("armorhuskrecall",
            () -> new MobArmor(800, 50, 90));
    public static final RegistryObject<Item> BlackForestSword4 = ITEMS.register("blackforestsword4",
            () -> new HuskSword(new Item.Properties().rarity(CustomStyle.HuskItalic), 4));
    public static final RegistryObject<Item> WaterSet = ITEMS.register("waterset",
            () -> new WaterSet(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ArmorSeaRecall = ITEMS.register("armorsearecall",
            () -> new MobArmor(500, 50, 90));
    public static final RegistryObject<Item> SeaRecallSoul = ITEMS.register("searecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Sea)));
    public static final RegistryObject<Item> IntensifiedSeaSoul = ITEMS.register("intensifiedseasoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SeaBold)));
    public static final RegistryObject<Item> SeaSword4 = ITEMS.register("seasword4",
            () -> new SeaSword(new Item.Properties().rarity(CustomStyle.SeaItalic), 4));

    public static final RegistryObject<Item> ILArmorHelmet = ITEMS.register("ilarmorhelmet",
            () -> new IntensifiedLightningArmor(ItemMaterial.ArmorIL, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.LightningItalic)));
    public static final RegistryObject<Item> ILArmorChest = ITEMS.register("ilarmorchest",
            () -> new IntensifiedLightningArmor(ItemMaterial.ArmorIL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.LightningItalic)));
    public static final RegistryObject<Item> ILArmorLeggings = ITEMS.register("ilarmorleggings",
            () -> new IntensifiedLightningArmor(ItemMaterial.ArmorIL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.LightningItalic)));
    public static final RegistryObject<Item> ILArmorBoots = ITEMS.register("ilarmorboots",
            () -> new IntensifiedLightningArmor(ItemMaterial.ArmorIL, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.LightningItalic)));

    public static final RegistryObject<Item> LightningRecallSoul = ITEMS.register("lightningrecallsoul",
            () -> new LightningRecallSoul(new Item.Properties().rarity(CustomStyle.Lightning)));
    public static final RegistryObject<Item> IntensifiedLightningSoul = ITEMS.register("intensifiedlightningsoul",
            () -> new IntensifiedLightningSoul(new Item.Properties().rarity(CustomStyle.LightningBold)));
    public static final RegistryObject<Item> ArmorLightningRecall = ITEMS.register("armorlightningrecall",
            () -> new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> NetherRecallSoul = ITEMS.register("netherrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Nether)));
    public static final RegistryObject<Item> IntensifiedNetherSoul = ITEMS.register("intensifiednetherrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold)));
    public static final RegistryObject<Item> ArmorNetherRecall = ITEMS.register("armornetherrecall",
            () -> new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> ManaSword1 = ITEMS.register("manasword1",
            () -> new ManaSword1(new Item.Properties().rarity(CustomStyle.MagmaItalic)));
    public static final RegistryObject<Item> SnowRecallSoul = ITEMS.register("snowrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Snow)));
    public static final RegistryObject<Item> IntensifiedSnowSoul = ITEMS.register("intensifiedsnowrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SnowBold)));
    public static final RegistryObject<Item> ArmorSnowRecall = ITEMS.register("armorsnowrecall",
            () -> new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> SnowSword4 = ITEMS.register("snowsword4",
            () -> new SnowSword(new Item.Properties().rarity(CustomStyle.SnowItalic), 4));
    public static final RegistryObject<Item> IntensifiedForestSoul = ITEMS.register("forestrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Forest)));
    public static final RegistryObject<Item> IntensifiedForestRecallSoul = ITEMS.register("intensifiedforestsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.ForestBold)));
    public static final RegistryObject<Item> ArmorForestRecall = ITEMS.register("armorforestrecall",
            () -> new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> ForestSword4 = ITEMS.register("forestsword4",
            () -> new ForestSword4(new Item.Properties().rarity(CustomStyle.MagmaItalic)));
    public static final RegistryObject<Item> VolcanoSword4 = ITEMS.register("volcanosword4",
            () -> new VolcanoSword(new Item.Properties().rarity(CustomStyle.MagmaItalic), 4));
    public static final RegistryObject<Item> IntensifiedVolcanoSoul = ITEMS.register("intensifiedvolcanosoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.VolcanoBold)));
    public static final RegistryObject<Item> ArmorVolcanoRecall = ITEMS.register("armorvolcanorecall",
            () -> new MobArmor(ItemMaterial.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> VolcanoRecallSoul = ITEMS.register("volcanorecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> ID_Card = ITEMS.register("id_card",
            () -> new ID_Card(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> ForestBossPocket = ITEMS.register("forest_boss_pocket",
            () -> new ForestBossPocket(new Item.Properties().rarity(CustomStyle.ForestBold)));
    public static final RegistryObject<Item> ForestBossCore = ITEMS.register("forest_boss_core",
            () -> new ForestBossCore(new Item.Properties().rarity(CustomStyle.ForestBold)));
    public static final RegistryObject<Item> ForestBossGem = ITEMS.register("forest_boss_gem",
            () -> new ForestBossGem(new Item.Properties().rarity(CustomStyle.ForestBold)));
    public static final RegistryObject<Item> ForestBossCentral = ITEMS.register("forest_boss_central",
            () -> new ForestBossCentral(new Item.Properties().rarity(CustomStyle.ForestBold)));
    public static final RegistryObject<Item> ForestBossSword = ITEMS.register("forest_boss_sword",
            () -> new ForestBossSword(fun.wraq.common.registry.ItemTier.VMaterial, 2, 0));
    public static final RegistryObject<Item> ForestBossSwordForgeDraw = ITEMS.register("forest_sword4_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.ForestBold), ModItems.ForestBossSword.get()));

    public static final RegistryObject<Item> VolcanoBossPocket = ITEMS.register("volcano_boss_pocket",
            () -> new VolcanoBossPocket(new Item.Properties().rarity(CustomStyle.VolcanoBold)));
    public static final RegistryObject<Item> VolcanoBossCore = ITEMS.register("volcano_boss_core",
            () -> new VolcanoBossCore(new Item.Properties().rarity(CustomStyle.VolcanoBold)));
    public static final RegistryObject<Item> VolcanoBossGem = ITEMS.register("volcano_boss_gem",
            () -> new VolcanoBossGem(new Item.Properties().rarity(CustomStyle.VolcanoBold)));
    public static final RegistryObject<Item> VolcanoBossCentral = ITEMS.register("volcano_boss_central",
            () -> new VolcanoBossCentral(new Item.Properties().rarity(CustomStyle.VolcanoBold)));
    public static final RegistryObject<Item> VolcanoBossSword = ITEMS.register("volcano_boss_sword",
            () -> new VolcanoBossSword(fun.wraq.common.registry.ItemTier.VMaterial, 2, 0));
    public static final RegistryObject<Item> VolcanoBossSwordForgeDraw = ITEMS.register("volcano_sword4_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.VolcanoBold), ModItems.VolcanoBossSword.get()));

    public static final RegistryObject<Item> ArmorForestBoss = ITEMS.register("armor_forest_boss",
            () -> new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, StringUtils.MobName.ForestBoss));
    public static final RegistryObject<Item> ArmorVolcanoBoss = ITEMS.register("armor_volcano_boss",
            () -> new MobArmor(ItemMaterial.BasicArmor2, ArmorItem.Type.HELMET, StringUtils.MobName.VolcanoBoss));
    public static final RegistryObject<Item> SakuraChange = ITEMS.register("sakura_change",
            () -> new SakuraChange(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BlockFill = ITEMS.register("block_fill",
            () -> new BlockFill(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BlockFix = ITEMS.register("block_fix",
            () -> new BlockFix(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> SakuraPetal = ITEMS.register("sakura_petal",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SakuraBold)));
    public static final RegistryObject<Item> SakuraPocket = ITEMS.register("sakura_pocket",
            () -> new SakuraPocket(new Item.Properties().rarity(CustomStyle.SakuraBold)));
    public static final RegistryObject<Item> SakuraReForge = ITEMS.register("sakura_reforge",
            () -> new SakuraReForge(new Item.Properties().rarity(CustomStyle.SakuraBold)));
    public static final RegistryObject<Item> SakuraDemonSword = ITEMS.register("sakura_demon_sword",
            () -> new SakuraSword(new Item.Properties().rarity(CustomStyle.SakuraItalic)));

    public static final RegistryObject<Item> SakuraArmorHelmet = ITEMS.register("sakura_armor_helmet",
            () -> new SakuraArmorHelmet(ItemMaterial.Lake, ArmorItem.Type.HELMET));

    public static final RegistryObject<Item> Wheat = ITEMS.register("vmd_wheat",
            () -> new Wheat(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> WheatPocket = ITEMS.register("wheat_pocket",
            () -> new WheatPocket(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> WheatReForge = ITEMS.register("wheat_reforge",
            () -> new WheatReForge(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> WheatArmorChest = ITEMS.register("wheat_armor_chest",
            () -> new WheatArmorChest(ItemMaterial.LakeMaterialChest, ArmorItem.Type.CHESTPLATE));

    public static final RegistryObject<Item> MinePants = ITEMS.register("mine_pants",
            () -> new MinePants(ItemMaterial.LakeMaterialChest, ArmorItem.Type.LEGGINGS));

    public static final RegistryObject<Item> MinePantsForgingDraw = ITEMS.register("mine_leggings_fd",
            () -> new WraqForge(new Item.Properties().rarity(Rarity.EPIC), ModItems.MinePants.get()));

    public static final RegistryObject<Item> SnowArmorHelmet = ITEMS.register("snowarmorhelmet",
            () -> new SnowArmor(ItemMaterial.SkyMaterial, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> SnowArmorChest = ITEMS.register("snowarmorchest",
            () -> new SnowArmor(ItemMaterial.SkyMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SnowArmorLeggings = ITEMS.register("snowarmorleggings",
            () -> new SnowArmor(ItemMaterial.SkyMaterial, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> SnowArmorBoots = ITEMS.register("snowarmorboots",
            () -> new SnowArmor(ItemMaterial.SkyMaterial, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> LakeBossSword = ITEMS.register("lake_boss_sword",
            () -> new LakeBoss.LakeBossSword(ItemTier.VMaterial, 2, 0));
    public static final RegistryObject<Item> LakeBossSwordForgeDraw = ITEMS.register("lake_sword4_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.LakeBold), ModItems.LakeBossSword.get()));

    public static final RegistryObject<Item> LakeBossCore = ITEMS.register("lake_boss_core",
            () -> new LakeBoss.LakeBossCore(new Item.Properties().rarity(CustomStyle.LakeBold)));
    public static final RegistryObject<Item> LakeBossGem = ITEMS.register("lake_boss_gem",
            () -> new LakeBoss.LakeBossGem(new Item.Properties().rarity(CustomStyle.LakeBold)));
    public static final RegistryObject<Item> LakeBossCentral = ITEMS.register("lake_boss_central",
            () -> new LakeBoss.LakeBossCentral(new Item.Properties().rarity(CustomStyle.LakeBold)));
    public static final RegistryObject<Item> LakeBossPocket = ITEMS.register("lake_boss_pocket",
            () -> new LakeBoss.LakeBossPocket(new Item.Properties().rarity(CustomStyle.LakeBold)));
    public static final RegistryObject<Item> ArmorLakeBoss = ITEMS.register("armor_lake_boss",
            () -> new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, StringUtils.MobName.LakeBoss));

    public static final RegistryObject<Item> SkyBossBow = ITEMS.register("sky_boss_bow",
            () -> new SkyBoss.SkyBossBow(new Item.Properties().rarity(CustomStyle.EntropyItalic).stacksTo(1)));
    public static final RegistryObject<Item> SkyBossBowForgeDraw = ITEMS.register("sky_boss_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.SkyBold), ModItems.SkyBossBow.get()));
    public static final RegistryObject<Item> SkyBossCore = ITEMS.register("sky_boss_core",
            () -> new SkyBoss.SkyBossCore(new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> SkyBossGem = ITEMS.register("sky_boss_gem",
            () -> new SkyBoss.SkyBossGem(new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> SkyBossCentral = ITEMS.register("sky_boss_central",
            () -> new SkyBoss.SkyBossCentral(new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> SkyBossPocket = ITEMS.register("sky_boss_pocket",
            () -> new SkyBoss.SkyBossPocket(new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> ArmorSkyBoss = ITEMS.register("armor_sky_boss",
            () -> new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, StringUtils.MobName.SkyBoss));

    public static final RegistryObject<Item> NETHER_ARMOR_HELMET = ITEMS.register("netherarmorhelmet",
            () -> new NetherArmor(ItemMaterial.NetherAll, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> NETHER_ARMOR_CHEST = ITEMS.register("netherarmorchest",
            () -> new NetherArmor(ItemMaterial.NetherAll, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> NETHER_ARMOR_LEGGINGS = ITEMS.register("netherarmorleggings",
            () -> new NetherArmor(ItemMaterial.NetherAll, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> NETHER_ARMOR_BOOTS = ITEMS.register("netherarmorboots",
            () -> new NetherArmor(ItemMaterial.NetherAll, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.NetherItalic)));

    public static final RegistryObject<Item> PlainCrest0 = ITEMS.register("plain_crest_0",
            () -> new PlainCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> PlainCrest1 = ITEMS.register("plain_crest_1",
            () -> new PlainCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> PlainCrest2 = ITEMS.register("plain_crest_2",
            () -> new PlainCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> PlainCrest3 = ITEMS.register("plain_crest_3",
            () -> new PlainCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> PlainCrest4 = ITEMS.register("plain_crest_4",
            () -> new PlainCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> ForestCrest0 = ITEMS.register("forest_crest_0",
            () -> new ForestCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> ForestCrest1 = ITEMS.register("forest_crest_1",
            () -> new ForestCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> ForestCrest2 = ITEMS.register("forest_crest_2",
            () -> new ForestCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> ForestCrest3 = ITEMS.register("forest_crest_3",
            () -> new ForestCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> ForestCrest4 = ITEMS.register("forest_crest_4",
            () -> new ForestCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> LakeCrest0 = ITEMS.register("lake_crest_0",
            () -> new LakeCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> LakeCrest1 = ITEMS.register("lake_crest_1",
            () -> new LakeCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> LakeCrest2 = ITEMS.register("lake_crest_2",
            () -> new LakeCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> LakeCrest3 = ITEMS.register("lake_crest_3",
            () -> new LakeCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> LakeCrest4 = ITEMS.register("lake_crest_4",
            () -> new LakeCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> VolcanoCrest0 = ITEMS.register("volcano_crest_0",
            () -> new VolcanoCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> VolcanoCrest1 = ITEMS.register("volcano_crest_1",
            () -> new VolcanoCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> VolcanoCrest2 = ITEMS.register("volcano_crest_2",
            () -> new VolcanoCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> VolcanoCrest3 = ITEMS.register("volcano_crest_3",
            () -> new VolcanoCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> VolcanoCrest4 = ITEMS.register("volcano_crest_4",
            () -> new VolcanoCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> MineCrest0 = ITEMS.register("mine_crest_0",
            () -> new MineCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> MineCrest1 = ITEMS.register("mine_crest_1",
            () -> new MineCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> MineCrest2 = ITEMS.register("mine_crest_2",
            () -> new MineCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> MineCrest3 = ITEMS.register("mine_crest_3",
            () -> new MineCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> MineCrest4 = ITEMS.register("mine_crest_4",
            () -> new MineCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> SnowCrest0 = ITEMS.register("snow_crest_0",
            () -> new SnowCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> SnowCrest1 = ITEMS.register("snow_crest_1",
            () -> new SnowCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> SnowCrest2 = ITEMS.register("snow_crest_2",
            () -> new SnowCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> SnowCrest3 = ITEMS.register("snow_crest_3",
            () -> new SnowCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> SnowCrest4 = ITEMS.register("snow_crest_4",
            () -> new SnowCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> SkyCrest0 = ITEMS.register("sky_crest_0",
            () -> new SkyCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> SkyCrest1 = ITEMS.register("sky_crest_1",
            () -> new SkyCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> SkyCrest2 = ITEMS.register("sky_crest_2",
            () -> new SkyCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> SkyCrest3 = ITEMS.register("sky_crest_3",
            () -> new SkyCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> SkyCrest4 = ITEMS.register("sky_crest_4",
            () -> new SkyCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> ManaCrest0 = ITEMS.register("mana_crest_0",
            () -> new ManaCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> ManaCrest1 = ITEMS.register("mana_crest_1",
            () -> new ManaCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> ManaCrest2 = ITEMS.register("mana_crest_2",
            () -> new ManaCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> ManaCrest3 = ITEMS.register("mana_crest_3",
            () -> new ManaCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> ManaCrest4 = ITEMS.register("mana_crest_4",
            () -> new ManaCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> SnowBossArmorChest = ITEMS.register("snow_boss_armor_chest",
            () -> new SnowBoss.SnowBossArmorChest(ItemMaterial.SkyMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SnowBossCore = ITEMS.register("snow_boss_core",
            () -> new SnowBoss.SnowBossCore(new Item.Properties().rarity(CustomStyle.SnowBold)));
    public static final RegistryObject<Item> SnowBossGem = ITEMS.register("snow_boss_gem",
            () -> new SnowBoss.SnowBossGem(new Item.Properties().rarity(CustomStyle.SnowBold)));
    public static final RegistryObject<Item> SnowBossCentral = ITEMS.register("snow_boss_central",
            () -> new SnowBoss.SnowBossCentral(new Item.Properties().rarity(CustomStyle.SnowBold)));
    public static final RegistryObject<Item> SnowBossPocket = ITEMS.register("snow_boss_pocket",
            () -> new SnowBoss.SnowBossPocket(new Item.Properties().rarity(CustomStyle.SnowBold)));
    public static final RegistryObject<Item> ArmorSnowBoss = ITEMS.register("armor_snow_boss",
            () -> new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, StringUtils.MobName.SnowBoss));

    public static final RegistryObject<Item> SpongeClear = ITEMS.register("sponge_clear",
            () -> new SpongeClear(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> PillarsSet = ITEMS.register("pillars_set",
            () -> new PillarsSet(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> ArmorBoss2 = ITEMS.register("armor_boss2",
            () -> new MobArmor(StringUtils.MobName.Boss2));

    public static final RegistryObject<Item> Boss2Piece = ITEMS.register("boss_2_piece",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> WorldSoul1 = ITEMS.register("worldsoul1",
            () -> new Item(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> WorldSoul2 = ITEMS.register("worldsoul2",
            () -> new Item(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> WorldSoul3 = ITEMS.register("worldsoul3",
            () -> new Item(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> WorldSoul4 = ITEMS.register("worldsoul4",
            () -> new Item(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> worldSoul5 = ITEMS.register("worldsoul5",
            () -> new Item(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> SoulBow = ITEMS.register("soulbow",
            () -> new SoulBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> SoulSword = ITEMS.register("soulsword",
            () -> new SoulSword(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> SoulSceptre = ITEMS.register("soulsceptre",
            () -> new SoulSceptre(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> WorldSoulNote = ITEMS.register("world_soul_note",
            () -> new WorldSoulNote(new Item.Properties().rarity(CustomStyle.WorldItalic)));

    public static final RegistryObject<Item> SkillReset = ITEMS.register("skill_reset",
            () -> new SkillReset(new Item.Properties().rarity(CustomStyle.WorldItalic)));

    public static final RegistryObject<Item> SnowBossChestForgeDraw = ITEMS.register("snow_boss_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.SnowBold), ModItems.SnowBossArmorChest.get()));

    public static final RegistryObject<Item> PlainSoulBag = ITEMS.register("plain_soul_bag",
            () -> new SoulBag(new Item.Properties().rarity(CustomStyle.PlainBold), ModItems.PlainSoul.get()));

    public static final RegistryObject<Item> ForestSoulBag = ITEMS.register("forest_soul_bag",
            () -> new SoulBag(new Item.Properties().rarity(CustomStyle.ForestBold), ModItems.ForestSoul.get()));

    public static final RegistryObject<Item> VolcanoSoulBag = ITEMS.register("volcano_soul_bag",
            () -> new SoulBag(new Item.Properties().rarity(CustomStyle.VolcanoBold), ModItems.VolcanoSoul.get()));

    public static final RegistryObject<Item> LakeSoulBag = ITEMS.register("lake_soul_bag",
            () -> new SoulBag(new Item.Properties().rarity(CustomStyle.LakeBold), ModItems.LakeSoul.get()));

    public static final RegistryObject<Item> ArmorPlainBossHelmet = ITEMS.register("armor_plain_boss_helmet",
            () -> new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.HELMET, StringUtils.MobName.PlainBoss));

    public static final RegistryObject<Item> ArmorPlainBossChest = ITEMS.register("armor_plain_boss_chest",
            () -> new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.PlainBoss));

    public static final RegistryObject<Item> ArmorPlainBossLeggings = ITEMS.register("armor_plain_boss_leggings",
            () -> new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.LEGGINGS, StringUtils.MobName.PlainBoss));

    public static final RegistryObject<Item> ArmorPlainBossBoots = ITEMS.register("armor_plain_boss_boots",
            () -> new MobArmor(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS, StringUtils.MobName.PlainBoss));

    public static final RegistryObject<Item> ArmorMain1Boss = ITEMS.register("armor_main1boss",
            () -> new MobArmor(StringUtils.MobName.Main1Boss));

    public static final RegistryObject<Item> PlainAttackRing0 = ITEMS.register("plain_attack_ring0",
            () -> new PlainAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));

    public static final RegistryObject<Item> PlainAttackRing1 = ITEMS.register("plain_attack_ring1",
            () -> new PlainAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));

    public static final RegistryObject<Item> PlainAttackRing2 = ITEMS.register("plain_attack_ring2",
            () -> new PlainAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));

    public static final RegistryObject<Item> PlainAttackRing3 = ITEMS.register("plain_attack_ring3",
            () -> new PlainAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PlainManaAttackRing0 = ITEMS.register("plain_manaattack_ring0",
            () -> new PlainManaAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));

    public static final RegistryObject<Item> PlainManaAttackRing1 = ITEMS.register("plain_manaattack_ring1",
            () -> new PlainManaAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));

    public static final RegistryObject<Item> PlainManaAttackRing2 = ITEMS.register("plain_manaattack_ring2",
            () -> new PlainManaAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));

    public static final RegistryObject<Item> PlainManaAttackRing3 = ITEMS.register("plain_manaattack_ring3",
            () -> new PlainManaAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PlainHealthRing0 = ITEMS.register("plain_health_ring0",
            () -> new PlainHealthRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));

    public static final RegistryObject<Item> PlainHealthRing1 = ITEMS.register("plain_health_ring1",
            () -> new PlainHealthRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));

    public static final RegistryObject<Item> PlainHealthRing2 = ITEMS.register("plain_health_ring2",
            () -> new PlainHealthRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));

    public static final RegistryObject<Item> PlainHealthRing3 = ITEMS.register("plain_health_ring3",
            () -> new PlainHealthRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PlainDefenceRing0 = ITEMS.register("plain_defence_ring0",
            () -> new PlainDefenceRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));

    public static final RegistryObject<Item> PlainDefenceRing1 = ITEMS.register("plain_defence_ring1",
            () -> new PlainDefenceRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));

    public static final RegistryObject<Item> PlainDefenceRing2 = ITEMS.register("plain_defence_ring2",
            () -> new PlainDefenceRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));

    public static final RegistryObject<Item> PlainDefenceRing3 = ITEMS.register("plain_defence_ring3",
            () -> new PlainDefenceRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PlainBossSoul = ITEMS.register("plain_boss_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PlainBold)));

    public static final RegistryObject<Item> Ps_Bottle0 = ITEMS.register("ps_bottle0",
            () -> new PsBottle(new Item.Properties().rarity(Rarity.UNCOMMON), 2));

    public static final RegistryObject<Item> Ps_Bottle1 = ITEMS.register("ps_bottle1",
            () -> new PsBottle(new Item.Properties().rarity(Rarity.UNCOMMON), 5));

    public static final RegistryObject<Item> Ps_Bottle2 = ITEMS.register("ps_bottle2",
            () -> new PsBottle(new Item.Properties().rarity(Rarity.UNCOMMON), 10));

    public static final RegistryObject<Item> QuiverBag = ITEMS.register("quiver_bag",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> SakuraBow = ITEMS.register("sakura_bow",
            () -> new SakuraBow(new Item.Properties().rarity(CustomStyle.SakuraItalic)));

    public static final RegistryObject<Item> SakuraCore = ITEMS.register("sakura_mana_core",
            () -> new SakuraCore(new Item.Properties().rarity(CustomStyle.SakuraBold)));

    public static final RegistryObject<Item> SakuraBowForgingDraw = ITEMS.register("sakura_bow_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.SakuraBold), ModItems.SakuraBow.get()));

    public static final RegistryObject<Item> SakuraCoreForgingDraw = ITEMS.register("sakura_mana_core_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.SakuraBold), ModItems.SakuraCore.get()));

    public static final RegistryObject<Item> PurpleIron = ITEMS.register("purpleiron",
            () -> new Item(new Item.Properties().rarity(CustomStyle.PurpleIron)));

    public static final RegistryObject<Item> PurpleIronPiece = ITEMS.register("purpleiron_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PurpleIron)));

    public static final RegistryObject<Item> PurpleIronPickaxe0 = ITEMS.register("purpleiron_pickaxe0",
            () -> new PurplePickaxe(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 0));

    public static final RegistryObject<Item> PurpleIronPickaxe1 = ITEMS.register("purpleiron_pickaxe1",
            () -> new PurplePickaxe(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 1));

    public static final RegistryObject<Item> PurpleIronPickaxe2 = ITEMS.register("purpleiron_pickaxe2",
            () -> new PurplePickaxe(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 2));

    public static final RegistryObject<Item> PurpleIronPickaxe3 = ITEMS.register("purpleiron_pickaxe3",
            () -> new PurplePickaxe(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 3));

    public static final RegistryObject<Item> PurpleIronArmorHelmet = ITEMS.register("purpleiron_armor_helmet",
            () -> new PurpleIronArmor(ItemMaterial.PurpleIron, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.PurpleIronItalic)));

    public static final RegistryObject<Item> PurpleIronArmorChest = ITEMS.register("purpleiron_armor_chest",
            () -> new PurpleIronArmor(ItemMaterial.PurpleIron, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.PurpleIronItalic)));

    public static final RegistryObject<Item> PurpleIronArmorLeggings = ITEMS.register("purpleiron_armor_leggings",
            () -> new PurpleIronArmor(ItemMaterial.PurpleIron, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.PurpleIronItalic)));

    public static final RegistryObject<Item> PurpleIronArmorBoots = ITEMS.register("purpleiron_armor_boots",
            () -> new PurpleIronArmor(ItemMaterial.PurpleIron, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.PurpleIronItalic)));

    public static final RegistryObject<Item> MobArmorIceHelmet = ITEMS.register("mob_armor_ice_helmet",
            () -> new MobArmor(ItemMaterial.ArmorIce, ArmorItem.Type.HELMET, StringUtils.MobName.IceKnight));

    public static final RegistryObject<Item> MobArmorIceChest = ITEMS.register("mob_armor_ice_chest",
            () -> new MobArmor(ItemMaterial.ArmorIce, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorIceLeggings = ITEMS.register("mob_armor_ice_leggings",
            () -> new MobArmor(ItemMaterial.ArmorIce, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorIceBoots = ITEMS.register("mob_armor_ice_boots",
            () -> new MobArmor(ItemMaterial.ArmorIce, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> IceSoul = ITEMS.register("ice_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> IceBarrierSet = ITEMS.register("ice_barrier_set",
            () -> new IceBarrierSet(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> PlainCompleteGem = ITEMS.register("plain_complete_gem",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PlainBold)));

    public static final RegistryObject<Item> IceCompleteGem = ITEMS.register("ice_complete_gem",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> IceArmorHelmet = ITEMS.register("ice_armor_helmet",
            () -> new IceArmor(ItemMaterial.ArmorIce, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> IceArmorChest = ITEMS.register("ice_armor_chest",
            () -> new IceArmor(ItemMaterial.ArmorIce, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> IceArmorLeggings = ITEMS.register("ice_armor_leggings",
            () -> new IceArmor(ItemMaterial.ArmorIce, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> IceArmorBoots = ITEMS.register("ice_armor_boots",
            () -> new IceArmor(ItemMaterial.ArmorIce, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> FantasyMedal = ITEMS.register("fantasy_medal",
            () -> new FantasyMedal(new Item.Properties().rarity(CustomStyle.FantasyBold)));

    public static final RegistryObject<Item> FantasyBracelet = ITEMS.register("fantasy_bracelet",
            () -> new FantasyBracelet(new Item.Properties().rarity(CustomStyle.FantasyBold)));

    public static final RegistryObject<Item> commonLotteries = ITEMS.register("common_lotteries",
            () -> new CommonLotteries(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> UnCommonLotteries = ITEMS.register("uncommon_lotteries",
            () -> new UnCommonLotteries(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> RevelationBook = ITEMS.register("revelation_book",
            () -> new RevelationBook(new Item.Properties().rarity(CustomStyle.Fantasy)));

    public static final RegistryObject<Item> LeatherArmorHelmet = ITEMS.register("leather_armor_helmet",
            () -> new LeatherArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.HELMET, 0));

    public static final RegistryObject<Item> LeatherArmorChest = ITEMS.register("leather_armor_chest",
            () -> new LeatherArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.CHESTPLATE, 1));

    public static final RegistryObject<Item> LeatherArmorLeggings = ITEMS.register("leather_armor_leggings",
            () -> new LeatherArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.LEGGINGS, 2));

    public static final RegistryObject<Item> LeatherArmorBoots = ITEMS.register("leather_armor_boots",
            () -> new LeatherArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.BOOTS, 3));

    public static final RegistryObject<Item> LeatherSoul = ITEMS.register("leather_soul",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Ice)));

    public static final RegistryObject<Item> LeatherRune = ITEMS.register("leather_rune",
            () -> new Item(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> MobArmorIceHunterHelmet = ITEMS.register("mob_armor_ice_hunter_helmet",
            () -> new MobArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.HELMET, StringUtils.MobName.IceHunter));

    public static final RegistryObject<Item> MobArmorIceHunterChest = ITEMS.register("mob_armor_ice_hunter_chest",
            () -> new MobArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.IceHunter));

    public static final RegistryObject<Item> MobArmorIceHunterLeggings = ITEMS.register("mob_armor_ice_hunter_leggings",
            () -> new MobArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.LEGGINGS, StringUtils.MobName.IceHunter));

    public static final RegistryObject<Item> MobArmorIceHunterBoots = ITEMS.register("mob_armor_ice_hunter_boots",
            () -> new MobArmor(ItemMaterial.ArmorLeather, ArmorItem.Type.BOOTS, StringUtils.MobName.IceHunter));

    public static final RegistryObject<Item> Value = ITEMS.register("value",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> IceSword = ITEMS.register("ice_sword",
            () -> new IceSword(new Item.Properties().rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> IceBow = ITEMS.register("ice_bow",
            () -> new IceBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> IceSceptre = ITEMS.register("ice_sceptre",
            () -> new IceSceptre(new Item.Properties().rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> IceBook = ITEMS.register("ice_book",
            () -> new IceBook(new Item.Properties().rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> U_Disk = ITEMS.register("u_disk",
            () -> new U_Disk(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> ShipSword = ITEMS.register("ship_sword",
            () -> new ShipSword(new Item.Properties().rarity(CustomStyle.ShipItalic)));

    public static final RegistryObject<Item> ShipBow = ITEMS.register("ship_bow",
            () -> new ShipBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.ShipItalic)));

    public static final RegistryObject<Item> ShipSceptre = ITEMS.register("ship_sceptre",
            () -> new ShipSceptre(new Item.Properties().rarity(CustomStyle.ShipItalic)));

    public static final RegistryObject<Item> ShipPiece = ITEMS.register("ship_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.ShipBold)));

    public static final RegistryObject<Item> IceLoot = ITEMS.register("ice_loot",
            () -> new IceLoot(new Item.Properties().rarity(CustomStyle.Ice)));

    public static final RegistryObject<Item> MineHat = ITEMS.register("mine_hat",
            () -> new MineHat(ItemMaterial.SkyMaterial, ArmorItem.Type.HELMET));

    public static final RegistryObject<Item> ForgeEnhance0 = ITEMS.register("forge_enhance_0",
            () -> new ForgeEnhancePaper(new Item.Properties().rarity(CustomStyle.Plain), 0));

    public static final RegistryObject<Item> ForgeEnhance1 = ITEMS.register("forge_enhance_1",
            () -> new ForgeEnhancePaper(new Item.Properties().rarity(CustomStyle.Sky), 1));

    public static final RegistryObject<Item> ForgeEnhance2 = ITEMS.register("forge_enhance_2",
            () -> new ForgeEnhancePaper(new Item.Properties().rarity(CustomStyle.EndBold), 2));

    public static final RegistryObject<Item> ForgeEnhance3 = ITEMS.register("forge_enhance_3",
            () -> new ForgeEnhancePaper(new Item.Properties().rarity(CustomStyle.RedBold), 3));

    public static final RegistryObject<Item> ForgeProtect = ITEMS.register("forge_protect",
            () -> new ForgeProtect(new Item.Properties().rarity(CustomStyle.EndBold)));

    public static final RegistryObject<Item> NetherManaArmorHelmet = ITEMS.register("nether_mana_helmet",
            () -> new NetherManaArmor(ItemMaterial.NetherMana, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.NetherItalic)));

    public static final RegistryObject<Item> NetherManaArmorChest = ITEMS.register("nether_mana_chest",
            () -> new NetherManaArmor(ItemMaterial.NetherMana, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.NetherItalic)));

    public static final RegistryObject<Item> NetherManaArmorLeggings = ITEMS.register("nether_mana_leggings",
            () -> new NetherManaArmor(ItemMaterial.NetherMana, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.NetherItalic)));

    public static final RegistryObject<Item> NetherManaArmorBoots = ITEMS.register("nether_mana_boots",
            () -> new NetherManaArmor(ItemMaterial.NetherMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.NetherItalic)));

    public static final RegistryObject<Item> FireWorkGun = ITEMS.register("fire_work_gun",
            () -> new FireWorkGun(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> SpringRing0 = ITEMS.register("spring_ring0",
            () -> new SpringRing(new Item.Properties().rarity(CustomStyle.SpringBold), 0));

    public static final RegistryObject<Item> SpringRing1 = ITEMS.register("spring_ring1",
            () -> new SpringRing(new Item.Properties().rarity(CustomStyle.SpringBold), 1));

    public static final RegistryObject<Item> SpringRing2 = ITEMS.register("spring_ring2",
            () -> new SpringRing(new Item.Properties().rarity(CustomStyle.SpringBold), 2));

    public static final RegistryObject<Item> SpringRing3 = ITEMS.register("spring_ring3",
            () -> new SpringRing(new Item.Properties().rarity(CustomStyle.SpringBold), 3));

    public static final RegistryObject<Item> SpringMoney = ITEMS.register("spring_money",
            () -> new Item(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> RedEnvelope = ITEMS.register("red_envelope",
            () -> new RedEnvelope(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> IceHeart = ITEMS.register("ice_heart",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> WoodenStake0 = ITEMS.register("wooden_stake0",
            () -> new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 100, 100, 20));

    public static final RegistryObject<Item> WoodenStake1 = ITEMS.register("wooden_stake1",
            () -> new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 400, 400, 40));

    public static final RegistryObject<Item> WoodenStake2 = ITEMS.register("wooden_stake2",
            () -> new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 600, 600, 60));

    public static final RegistryObject<Item> WoodenStake3 = ITEMS.register("wooden_stake3",
            () -> new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 900, 900, 80));

    public static final RegistryObject<Item> WoodenStake4 = ITEMS.register("wooden_stake4",
            () -> new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 1200, 1200, 100));

    public static final RegistryObject<Item> WoodenStake5 = ITEMS.register("wooden_stake5",
            () -> new MobArmor(ItemMaterial.MaterialForBoss, ArmorItem.Type.HELMET, 1600, 1600, 120));

    public static final RegistryObject<Item> SpringHand0 = ITEMS.register("spring_hand0",
            () -> new SpringHand(new Item.Properties().rarity(CustomStyle.SpringBold), 0));

    public static final RegistryObject<Item> SpringHand1 = ITEMS.register("spring_hand1",
            () -> new SpringHand(new Item.Properties().rarity(CustomStyle.SpringBold), 1));

    public static final RegistryObject<Item> SpringHand2 = ITEMS.register("spring_hand2",
            () -> new SpringHand(new Item.Properties().rarity(CustomStyle.SpringBold), 2));

    public static final RegistryObject<Item> SpringHand3 = ITEMS.register("spring_hand3",
            () -> new SpringHand(new Item.Properties().rarity(CustomStyle.SpringBold), 3));

    public static final RegistryObject<Item> SpringBelt0 = ITEMS.register("spring_belt0",
            () -> new SpringBelt(new Item.Properties().rarity(CustomStyle.SpringBold), 0));

    public static final RegistryObject<Item> SpringBelt1 = ITEMS.register("spring_belt1",
            () -> new SpringBelt(new Item.Properties().rarity(CustomStyle.SpringBold), 1));

    public static final RegistryObject<Item> SpringBelt2 = ITEMS.register("spring_belt2",
            () -> new SpringBelt(new Item.Properties().rarity(CustomStyle.SpringBold), 2));

    public static final RegistryObject<Item> SpringBelt3 = ITEMS.register("spring_belt3",
            () -> new SpringBelt(new Item.Properties().rarity(CustomStyle.SpringBold), 3));

    public static final RegistryObject<Item> SpringNecklace0 = ITEMS.register("spring_necklace0",
            () -> new SpringNecklace(new Item.Properties().rarity(CustomStyle.SpringBold), 0));

    public static final RegistryObject<Item> SpringNecklace1 = ITEMS.register("spring_necklace1",
            () -> new SpringNecklace(new Item.Properties().rarity(CustomStyle.SpringBold), 1));

    public static final RegistryObject<Item> SpringNecklace2 = ITEMS.register("spring_necklace2",
            () -> new SpringNecklace(new Item.Properties().rarity(CustomStyle.SpringBold), 2));

    public static final RegistryObject<Item> SpringNecklace3 = ITEMS.register("spring_necklace3",
            () -> new SpringNecklace(new Item.Properties().rarity(CustomStyle.SpringBold), 3));

    public static final RegistryObject<Item> SpringGoldCoin = ITEMS.register("spring_gold_coin",
            () -> new Item(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> MobArmorSpringHelmet = ITEMS.register("mob_armor_spring_helmet",
            () -> new MobArmor(ItemMaterial.Spring, ArmorItem.Type.HELMET, StringUtils.MobName.SpringMob));

    public static final RegistryObject<Item> MobArmorSpringChest = ITEMS.register("mob_armor_spring_chest",
            () -> new MobArmor(ItemMaterial.Spring, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorSpringLeggings = ITEMS.register("mob_armor_spring_leggings",
            () -> new MobArmor(ItemMaterial.Spring, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorSpringBoots = ITEMS.register("mob_armor_spring_boots",
            () -> new MobArmor(ItemMaterial.Spring, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> SpringAttackArmorHelmet = ITEMS.register("spring_attack_helmet",
            () -> new SpringAttackArmor(ItemMaterial.Spring, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.SpringItalic), 0));

    public static final RegistryObject<Item> SpringAttackArmorChest = ITEMS.register("spring_attack_chest",
            () -> new SpringAttackArmor(ItemMaterial.Spring, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.SpringItalic), 1));

    public static final RegistryObject<Item> SpringAttackArmorLeggings = ITEMS.register("spring_attack_leggings",
            () -> new SpringAttackArmor(ItemMaterial.Spring, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.SpringItalic), 2));

    public static final RegistryObject<Item> SpringAttackArmorBoots = ITEMS.register("spring_attack_boots",
            () -> new SpringAttackArmor(ItemMaterial.Spring, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.SpringItalic), 3));

    public static final RegistryObject<Item> SpringSwiftArmorHelmet = ITEMS.register("spring_swift_helmet",
            () -> new SpringSwiftArmor(ItemMaterial.Spring, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.SpringItalic), 0));

    public static final RegistryObject<Item> SpringSwiftArmorChest = ITEMS.register("spring_swift_chest",
            () -> new SpringSwiftArmor(ItemMaterial.Spring, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.SpringItalic), 1));

    public static final RegistryObject<Item> SpringSwiftArmorLeggings = ITEMS.register("spring_swift_leggings",
            () -> new SpringSwiftArmor(ItemMaterial.Spring, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.SpringItalic), 2));

    public static final RegistryObject<Item> SpringSwiftArmorBoots = ITEMS.register("spring_swift_boots",
            () -> new SpringSwiftArmor(ItemMaterial.Spring, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.SpringItalic), 3));

    public static final RegistryObject<Item> SpringManaArmorHelmet = ITEMS.register("spring_mana_helmet",
            () -> new SpringManaArmor(ItemMaterial.Spring, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.SpringItalic), 0));

    public static final RegistryObject<Item> SpringManaArmorChest = ITEMS.register("spring_mana_chest",
            () -> new SpringManaArmor(ItemMaterial.Spring, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.SpringItalic), 1));

    public static final RegistryObject<Item> SpringManaArmorLeggings = ITEMS.register("spring_mana_leggings",
            () -> new SpringManaArmor(ItemMaterial.Spring, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.SpringItalic), 2));

    public static final RegistryObject<Item> SpringManaArmorBoots = ITEMS.register("spring_mana_boots",
            () -> new SpringManaArmor(ItemMaterial.Spring, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.SpringItalic), 3));

    public static final RegistryObject<Item> SpringHeart = ITEMS.register("spring_heart",
            () -> new Item(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> SpringSoul = ITEMS.register("spring_soul",
            () -> new Item(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> SpringLoot = ITEMS.register("spring_loot",
            () -> new SpringLoot(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> FireCracker = ITEMS.register("fire_cracker",
            () -> new FireCracker(new Item.Properties().rarity(CustomStyle.Spring)));

    public static final RegistryObject<Item> SpringPiece = ITEMS.register("spring_piece",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Spring)));

    public static final RegistryObject<Item> SpringScale0 = ITEMS.register("spring_scale0",
            () -> new SpringScale(new Item.Properties().rarity(CustomStyle.SpringBold), 0));

    public static final RegistryObject<Item> SpringScale1 = ITEMS.register("spring_scale1",
            () -> new SpringScale(new Item.Properties().rarity(CustomStyle.SpringBold), 1));

    public static final RegistryObject<Item> SpringScale2 = ITEMS.register("spring_scale2",
            () -> new SpringScale(new Item.Properties().rarity(CustomStyle.SpringBold), 2));

    public static final RegistryObject<Item> SpringScale3 = ITEMS.register("spring_scale3",
            () -> new SpringScale(new Item.Properties().rarity(CustomStyle.SpringBold), 3));

    public static final RegistryObject<Item> Boss2AttackRing0 = ITEMS.register("boss2_attack_ring0",
            () -> new Boss2AttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 0));

    public static final RegistryObject<Item> Boss2AttackRing1 = ITEMS.register("boss2_attack_ring1",
            () -> new Boss2AttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 1));

    public static final RegistryObject<Item> Boss2AttackRing2 = ITEMS.register("boss2_attack_ring2",
            () -> new Boss2AttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 2));

    public static final RegistryObject<Item> Boss2AttackRing3 = ITEMS.register("boss2_attack_ring3",
            () -> new Boss2AttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 3));

    public static final RegistryObject<Item> Boss2ManaAttackRing0 = ITEMS.register("boss2_mana_attack_ring0",
            () -> new Boss2ManaAttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 0));

    public static final RegistryObject<Item> Boss2ManaAttackRing1 = ITEMS.register("boss2_mana_attack_ring1",
            () -> new Boss2ManaAttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 1));

    public static final RegistryObject<Item> Boss2ManaAttackRing2 = ITEMS.register("boss2_mana_attack_ring2",
            () -> new Boss2ManaAttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 2));

    public static final RegistryObject<Item> Boss2ManaAttackRing3 = ITEMS.register("boss2_mana_attack_ring3",
            () -> new Boss2ManaAttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 3));

    public static final RegistryObject<Item> Boss2DefenceRing0 = ITEMS.register("boss2_defence_ring0",
            () -> new Boss2DefenceRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 0));

    public static final RegistryObject<Item> Boss2DefenceRing1 = ITEMS.register("boss2_defence_ring1",
            () -> new Boss2DefenceRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 1));

    public static final RegistryObject<Item> Boss2DefenceRing2 = ITEMS.register("boss2_defence_ring2",
            () -> new Boss2DefenceRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 2));

    public static final RegistryObject<Item> Boss2DefenceRing3 = ITEMS.register("boss2_defence_ring3",
            () -> new Boss2DefenceRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 3));

    public static final RegistryObject<Item> Boss2HealthRing0 = ITEMS.register("boss2_health_ring0",
            () -> new Boss2HealthRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 0));

    public static final RegistryObject<Item> Boss2HealthRing1 = ITEMS.register("boss2_health_ring1",
            () -> new Boss2HealthRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 1));

    public static final RegistryObject<Item> Boss2HealthRing2 = ITEMS.register("boss2_health_ring2",
            () -> new Boss2HealthRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 2));

    public static final RegistryObject<Item> Boss2HealthRing3 = ITEMS.register("boss2_health_ring3",
            () -> new Boss2HealthRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 3));

    public static final RegistryObject<Item> MobArmorGiant = ITEMS.register("mob_armor_giant",
            () -> new MobArmor(ItemMaterial.ArmorIce, ArmorItem.Type.HELMET, StringUtils.MobName.Giant));

    public static final RegistryObject<Item> GiantTicket = ITEMS.register("giant_ticket",
            () -> new GiantTicket(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> GiantMedal = ITEMS.register("giant_medal",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> CropBag = ITEMS.register("crop_bag",
            () -> new CropPackets(new Item.Properties().rarity(CustomStyle.FieldBold)));

    public static final RegistryObject<Item> LogBag = ITEMS.register("log_bag",
            () -> new LogBag(new Item.Properties().rarity(CustomStyle.FieldBold)));

    public static final RegistryObject<Item> EarthManaSoul = ITEMS.register("earth_mana_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> BloodManaSoul = ITEMS.register("blood_mana_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> EarthManaRune = ITEMS.register("earth_mana_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> BloodManaRune = ITEMS.register("blood_mana_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> MobArmorBloodManaHelmet = ITEMS.register("mob_armor_blood_mana",
            () -> new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET, StringUtils.MobName.BloodMana));

    public static final RegistryObject<Item> MobArmorEarthManaHelmet = ITEMS.register("mob_armor_earth_mana_helmet",
            () -> new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET, StringUtils.MobName.EarthMana));

    public static final RegistryObject<Item> MobArmorEarthManaChest = ITEMS.register("mob_armor_earth_mana_chest",
            () -> new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorEarthManaLeggings = ITEMS.register("mob_armor_earth_mana_leggings",
            () -> new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorEarthManaBoots = ITEMS.register("mob_armor_earth_mana_boots",
            () -> new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> wolfLeather = ITEMS.register("wolf_leather",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.ForestBold)));

    public static final RegistryObject<Item> BloodManaArmorHelmet = ITEMS.register("blood_mana_helmet",
            () -> new BloodManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> BloodManaArmorChest = ITEMS.register("blood_mana_chest",
            () -> new BloodManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> BloodManaArmorLeggings = ITEMS.register("blood_mana_leggings",
            () -> new BloodManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> BloodManaArmorBoots = ITEMS.register("blood_mana_boots",
            () -> new BloodManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> EarthManaArmorHelmet = ITEMS.register("earth_mana_helmet",
            () -> new EarthManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> EarthManaArmorChest = ITEMS.register("earth_mana_chest",
            () -> new EarthManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> EarthManaArmorLeggings = ITEMS.register("earth_mana_leggings",
            () -> new EarthManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> EarthManaArmorBoots = ITEMS.register("earth_mana_boots",
            () -> new EarthManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> EarthManaCurios = ITEMS.register("earth_mana_curios",
            () -> new EarthManaCurios(new Item.Properties().rarity(CustomStyle.BloodManaBold).stacksTo(1), 0));

    public static final RegistryObject<Item> BloodManaCurios = ITEMS.register("blood_mana_curios",
            () -> new BloodManaCurios(new Item.Properties().rarity(CustomStyle.BloodManaBold).stacksTo(1), 0));

    public static final RegistryObject<Item> ManaShield = ITEMS.register("mana_shield",
            () -> new ManaShield(new Item.Properties().rarity(CustomStyle.BloodManaItalic).stacksTo(1)));

    public static final RegistryObject<Item> manaKnife = ITEMS.register("mana_knife",
            () -> new ManaKnife(new Item.Properties().rarity(CustomStyle.BloodManaItalic).stacksTo(1)));

    public static final RegistryObject<Item> EarthPower = ITEMS.register("earth_power",
            () -> new EarthPower(new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> EarthBook = ITEMS.register("earth_book",
            () -> new EarthBook(new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> goldenShield = ITEMS.register("golden_shield",
            () -> new GoldenAttackOffhand(new Item.Properties().rarity(CustomStyle.GoldItalic).stacksTo(1),
                    Component.literal("盾牌").withStyle(CustomStyle.styleOfMine), 0));

    public static final RegistryObject<Item> goldenKnife = ITEMS.register("golden_knife",
            () -> new GoldenAttackOffhand(new Item.Properties().rarity(CustomStyle.GoldItalic).stacksTo(1),
                    Component.literal("匕首").withStyle(ChatFormatting.AQUA), 1));

    public static final RegistryObject<Item> GoldenBook = ITEMS.register("golden_book",
            () -> new GoldenBook(new Item.Properties().rarity(CustomStyle.GoldItalic)));

    public static final RegistryObject<Item> MobArmorDevilHelmet = ITEMS.register("mob_armor_devil_helmet",
            () -> new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET, StringUtils.MobName.Devil));

    public static final RegistryObject<Item> DevilBlood = ITEMS.register("devil_blood",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> DevilAttackSoul = ITEMS.register("devil_attack_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> DevilSwiftSoul = ITEMS.register("devil_swift_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> DevilManaSoul = ITEMS.register("devil_mana_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> DevilAttackChest = ITEMS.register("devil_attack_chest",
            () -> new DevilAttackArmor(ItemMaterial.Devil, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> DevilSwiftBoots = ITEMS.register("devil_swift_boots",
            () -> new DevilSwiftArmor(ItemMaterial.Devil, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> DevilManaHelmet = ITEMS.register("devil_mana_helmet",
            () -> new DevilManaArmor(ItemMaterial.Devil, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> DevilBloodManaCurios = ITEMS.register("devil_blood_mana_curios",
            () -> new BloodManaCurios(new Item.Properties().rarity(CustomStyle.BloodManaBold), 1));

    public static final RegistryObject<Item> DevilEarthManaCurios = ITEMS.register("devil_earth_mana_curios",
            () -> new EarthManaCurios(new Item.Properties().rarity(CustomStyle.BloodManaBold), 1));

    public static final RegistryObject<Item> DevilLoot = ITEMS.register("devil_loot",
            () -> new DevilLoot(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> DragonPrefix = ITEMS.register("dragon_prefix",
            () -> new DragonPrefix(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> DING_ZHEN_MUSIC_DISC = ITEMS.register("ding_zhen_music_disc",
            () -> new RecordItem(6, ModSounds.IGotSmoke, new Item.Properties().stacksTo(1), 5200));

    public static final RegistryObject<Item> MobArmorMoonAttack = ITEMS.register("mob_armor_moon_attack",
            () -> new MobArmor(ItemMaterial.Moon, ArmorItem.Type.HELMET, StringUtils.MobName.MoonAttack));

    public static final RegistryObject<Item> MobArmorMoonMana = ITEMS.register("mob_armor_moon_mana",
            () -> new MobArmor(ItemMaterial.Moon, ArmorItem.Type.HELMET, StringUtils.MobName.MoonMana));

    public static final RegistryObject<Item> MobArmorMoonChest = ITEMS.register("mob_armor_moon_chest",
            () -> new MobArmor(ItemMaterial.Moon, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorMoonLeggings = ITEMS.register("mob_armor_moon_leggings",
            () -> new MobArmor(ItemMaterial.Moon, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorMoonBoots = ITEMS.register("mob_armor_moon_boots",
            () -> new MobArmor(ItemMaterial.Moon, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MoonSoul = ITEMS.register("moon_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MoonBold)));

    public static final RegistryObject<Item> MoonCompleteGem = ITEMS.register("moon_complete_gem",
            () -> new Item(new Item.Properties().rarity(CustomStyle.MoonBold)));

    public static final RegistryObject<Item> MoonShield = ITEMS.register("moon_shield",
            () -> new MoonShield());

    public static final RegistryObject<Item> MoonKnife = ITEMS.register("moon_knife",
            () -> new MoonKnife());

    public static final RegistryObject<Item> MoonBook = ITEMS.register("moon_book",
            () -> new MoonBook());

    public static final RegistryObject<Item> MoonLoot = ITEMS.register("moon_loot",
            () -> new MoonLoot(new Item.Properties().rarity(CustomStyle.Moon)));

    public static final RegistryObject<Item> DevilSword = ITEMS.register("devil_sword",
            () -> new DevilSword(new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> DevilBow = ITEMS.register("devil_bow",
            () -> new DevilBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> DevilSceptre = ITEMS.register("devil_sceptre",
            () -> new DevilSceptre(new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> SlimeBall = ITEMS.register("slime_ball",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Life)));

    public static final RegistryObject<Item> BigSlimeBall = ITEMS.register("big_slime_ball",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> SlimeBoots = ITEMS.register("slime_boots",
            () -> new SlimeBoots(ItemMaterial.ArmorKaze, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> MoonLeggings = ITEMS.register("moon_leggings",
            () -> new MoonArmor(ItemMaterial.Moon, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.Moon1Italic)));

    public static final RegistryObject<Item> MoonHelmet = ITEMS.register("moon_helmet",
            () -> new MoonArmor(ItemMaterial.Moon, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.Moon1Italic)));

    public static final RegistryObject<Item> MoonSword = ITEMS.register("moon_sword",
            () -> new MoonSword(new Item.Properties().rarity(CustomStyle.Moon1Italic), 0.4));

    public static final RegistryObject<Item> MoonBow = ITEMS.register("moon_bow",
            () -> new MoonBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.Moon1Italic), 2));

    public static final RegistryObject<Item> MoonSceptre = ITEMS.register("moon_sceptre",
            () -> new MoonSceptre(new Item.Properties().rarity(CustomStyle.Moon1Italic), 0.8));

    public static final RegistryObject<Item> ParkourMedal = ITEMS.register("parkour_medal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> MoonCurios = ITEMS.register("moon_curios",
            () -> new MoonCurios(new Item.Properties().rarity(CustomStyle.Moon1Bold)));

    public static final RegistryObject<Item> MoonBelt = ITEMS.register("moon_belt",
            () -> new MoonBelt(new Item.Properties().rarity(CustomStyle.Moon1Bold)));

    public static final RegistryObject<Item> IntensifiedDevilBlood = ITEMS.register("intensified_devil_blood",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> TabooPiece = ITEMS.register("taboo_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> PurpleIronConstraintStone = ITEMS.register("purple_iron_constraint_stone",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> ConstrainTaboo = ITEMS.register("constraint_taboo",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> MobArmorTabooDevil = ITEMS.register("mob_armor_taboo_devil",
            () -> new MobArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET, StringUtils.MobName.TabooDevil));

    public static final RegistryObject<Item> TabooAttackLeggings = ITEMS.register("taboo_attack_leggings",
            () -> new TabooAttackArmor(ItemMaterial.BloodMana, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> TabooSwiftHelmet = ITEMS.register("taboo_swift_helmet",
            () -> new TabooSwiftArmor(ItemMaterial.BloodMana, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> TabooManaBoots = ITEMS.register("taboo_mana_boots",
            () -> new TabooManaArmor(ItemMaterial.BloodMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> KillPaperLoot = ITEMS.register("kill_paper_loot",
            () -> new KillPaperLoot(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> ParkourGloves = ITEMS.register("parkour_gloves",
            () -> new ParkourGloves(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> EndPower = ITEMS.register("end_power",
            () -> new EndPower(new Item.Properties().rarity(CustomStyle.EndBold), 0));

    public static final RegistryObject<Item> EndPower1 = ITEMS.register("end_power1",
            () -> new EndPower(new Item.Properties().rarity(CustomStyle.EndBold), 1));

    public static final RegistryObject<Item> EndPower2 = ITEMS.register("end_power2",
            () -> new EndPower(new Item.Properties().rarity(CustomStyle.EndBold), 2));

    public static final RegistryObject<Item> EndPower3 = ITEMS.register("end_power3",
            () -> new EndPower(new Item.Properties().rarity(CustomStyle.EndBold), 3));

    public static final RegistryObject<Item> CastleNecklace = ITEMS.register("castle_necklace",
            () -> new CastleNecklace(new Item.Properties().rarity(CustomStyle.CastleBold).stacksTo(1)));

    public static final RegistryObject<Item> CastleTabooPiece = ITEMS.register("castle_taboo_piece",
            () -> new Item(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> MobArmorCastleKnightHelmet = ITEMS.register("mob_armor_castle_knight_helmet",
            () -> new MobArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.CastleKnight));

    public static final RegistryObject<Item> CastleKnightStone = ITEMS.register("castle_knight_stone",
            () -> new Item(new Item.Properties().rarity(CustomStyle.CastleCrystalBold)));

    public static final RegistryObject<Item> CastleCrystal = ITEMS.register("castle_crystal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.CastleCrystalBold)));

    public static final RegistryObject<Item> CastleCrystalNorth = ITEMS.register("castle_crystal_north",
            () -> new Item(new Item.Properties().rarity(CustomStyle.CastleCrystalBold)));

    public static final RegistryObject<Item> CastleCrystalSouth = ITEMS.register("castle_crystal_south",
            () -> new Item(new Item.Properties().rarity(CustomStyle.CastleCrystalBold)));

    public static final RegistryObject<Item> RubyNecklace = ITEMS.register("ruby_necklace",
            () -> new RubyNecklace(new Item.Properties().rarity(CustomStyle.RedBold), 0));

    public static final RegistryObject<Item> RubyNecklace1 = ITEMS.register("ruby_necklace1",
            () -> new RubyNecklace(new Item.Properties().rarity(CustomStyle.RedBold), 1));

    public static final RegistryObject<Item> RubyNecklace2 = ITEMS.register("ruby_necklace2",
            () -> new RubyNecklace(new Item.Properties().rarity(CustomStyle.RedBold), 2));

    public static final RegistryObject<Item> RubyNecklace3 = ITEMS.register("ruby_necklace3",
            () -> new RubyNecklace(new Item.Properties().rarity(CustomStyle.RedBold), 3));

    public static final RegistryObject<Item> SapphireNecklace = ITEMS.register("sapphire_necklace",
            () -> new SapphireNecklace(new Item.Properties().rarity(CustomStyle.LakeBold), 0));

    public static final RegistryObject<Item> SapphireNecklace1 = ITEMS.register("sapphire_necklace1",
            () -> new SapphireNecklace(new Item.Properties().rarity(CustomStyle.LakeBold), 1));

    public static final RegistryObject<Item> SapphireNecklace2 = ITEMS.register("sapphire_necklace2",
            () -> new SapphireNecklace(new Item.Properties().rarity(CustomStyle.LakeBold), 2));

    public static final RegistryObject<Item> SapphireNecklace3 = ITEMS.register("sapphire_necklace3",
            () -> new SapphireNecklace(new Item.Properties().rarity(CustomStyle.LakeBold), 3));

    public static final RegistryObject<Item> FancySapphireNecklace = ITEMS.register("fancy_sapphire_necklace",
            () -> new FancySapphireNecklace(new Item.Properties().rarity(CustomStyle.SakuraBold), 0));

    public static final RegistryObject<Item> FancySapphireNecklace1 = ITEMS.register("fancy_sapphire_necklace1",
            () -> new FancySapphireNecklace(new Item.Properties().rarity(CustomStyle.SakuraBold), 1));

    public static final RegistryObject<Item> FancySapphireNecklace2 = ITEMS.register("fancy_sapphire_necklace2",
            () -> new FancySapphireNecklace(new Item.Properties().rarity(CustomStyle.SakuraBold), 2));

    public static final RegistryObject<Item> FancySapphireNecklace3 = ITEMS.register("fancy_sapphire_necklace3",
            () -> new FancySapphireNecklace(new Item.Properties().rarity(CustomStyle.SakuraBold), 3));

    public static final RegistryObject<Item> CastleCuriosPowder = ITEMS.register("castle_curios_powder",
            () -> new Item(new Item.Properties().rarity(CustomStyle.CastleCrystalBold)));

    public static final RegistryObject<Item> MobArmorPurpleIronKnightHelmet = ITEMS.register("mob_armor_purple_knight_helmet",
            () -> new HolyArmor(ItemMaterial.Lake, ArmorItem.Type.HELMET, StringUtils.MobName.PurpleIronKnight));

    public static final RegistryObject<Item> MobArmorPurpleIronKnightChest = ITEMS.register("mob_armor_purple_knight_chest",
            () -> new HolyArmor(ItemMaterial.LakeMaterialChest, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorPurpleIronKnightLeggings = ITEMS.register("mob_armor_purple_knight_leggings",
            () -> new HolyArmor(ItemMaterial.LakeMaterialLeggings, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorPurpleIronKnightBoots = ITEMS.register("mob_armor_purple_knight_boots",
            () -> new HolyArmor(ItemMaterial.LakeMaterialBoots, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> PurpleIronBow = ITEMS.register("purple_iron_bow",
            () -> new PurpleIronBow(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 0));

    public static final RegistryObject<Item> PurpleIronBow1 = ITEMS.register("purple_iron_bow1",
            () -> new PurpleIronBow(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 1));

    public static final RegistryObject<Item> PurpleIronBow2 = ITEMS.register("purple_iron_bow2",
            () -> new PurpleIronBow(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 2));

    public static final RegistryObject<Item> PurpleIronBow3 = ITEMS.register("purple_iron_bow3",
            () -> new PurpleIronBow(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 3));

    public static final RegistryObject<Item> PurpleIronSword = ITEMS.register("purple_iron_sword",
            () -> new PurpleIronSword(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 0));

    public static final RegistryObject<Item> PurpleIronSword1 = ITEMS.register("purple_iron_sword1",
            () -> new PurpleIronSword(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 1));

    public static final RegistryObject<Item> PurpleIronSword2 = ITEMS.register("purple_iron_sword2",
            () -> new PurpleIronSword(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 2));

    public static final RegistryObject<Item> PurpleIronSword3 = ITEMS.register("purple_iron_sword3",
            () -> new PurpleIronSword(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 3));

    public static final RegistryObject<Item> PurpleIronSceptre = ITEMS.register("purple_iron_sceptre",
            () -> new PurpleIronSceptre(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 0));

    public static final RegistryObject<Item> PurpleIronSceptre1 = ITEMS.register("purple_iron_sceptre1",
            () -> new PurpleIronSceptre(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 1));

    public static final RegistryObject<Item> PurpleIronSceptre2 = ITEMS.register("purple_iron_sceptre2",
            () -> new PurpleIronSceptre(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 2));

    public static final RegistryObject<Item> PurpleIronSceptre3 = ITEMS.register("purple_iron_sceptre3",
            () -> new PurpleIronSceptre(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 3));

    public static final RegistryObject<Item> PurpleIronBud1 = ITEMS.register("purple_iron_bud1",
            () -> new Item(new Item.Properties().rarity(CustomStyle.PurpleIronBold)));

    public static final RegistryObject<Item> PurpleIronBud2 = ITEMS.register("purple_iron_bud2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PurpleIronBold), true, true));

    public static final RegistryObject<Item> PurpleIronBud3 = ITEMS.register("purple_iron_bud3",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PurpleIronBold), true, true));

    public static final RegistryObject<Item> LotteryStar = ITEMS.register("lottery_star",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC), true, true));

    public static final RegistryObject<Item> LotteryPrefix = ITEMS.register("lottery_prefix",
            () -> new LotteryPrefix(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> LakeRune0 = ITEMS.register("lake_rune0",
            () -> new LakeRune0(new Item.Properties().rarity(CustomStyle.LakeBold)));

    public static final RegistryObject<Item> LakeRune1 = ITEMS.register("lake_rune1",
            () -> new LakeRune1(new Item.Properties().rarity(CustomStyle.LakeBold)));

    public static final RegistryObject<Item> LakeRune2 = ITEMS.register("lake_rune2",
            () -> new LakeRune2(new Item.Properties().rarity(CustomStyle.LakeBold)));

    public static final RegistryObject<Item> LakeRune3 = ITEMS.register("lake_rune3",
            () -> new LakeRune3(new Item.Properties().rarity(CustomStyle.LakeBold)));

    public static final RegistryObject<Item> AttackSword0 = ITEMS.register("attack_sword_0",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> QiFuCurios1Passive1 = ITEMS.register("qifu_curios1_passive1",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> QiFuCurios1Passive2 = ITEMS.register("qifu_curios1_passive2",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> QiFuCurios1Passive3 = ITEMS.register("qifu_curios1_passive3",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> QingTuan = ITEMS.register("qing_tuan",
            () -> new QingTuan(new Item.Properties().rarity(CustomStyle.ForestBold)));

    public static final RegistryObject<Item> AnimatedItem = ITEMS.register("animated_item",
            () -> new AnimatedItem(new Item.Properties()));

    public static final RegistryObject<Item> StarBottle = ITEMS.register("star_bottle",
            () -> new StarBottle(new Item.Properties().rarity(CustomStyle.Moon1Bold)));

    public static final RegistryObject<Item> StarSoul = ITEMS.register("star_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Moon1), true, true));

    public static final RegistryObject<Item> StarRune = ITEMS.register("star_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Moon1Bold), true, true));

    public static final RegistryObject<Item> StarStar = ITEMS.register("star_star",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Moon1Italic), true, true));

    public static final RegistryObject<Item> MobArmorStar = ITEMS.register("mob_armor_star",
            () -> new MobArmor(StringUtils.MobName.Star));

    public static final RegistryObject<Item> MobArmorStar1 = ITEMS.register("mob_armor_star1",
            () -> new MobArmor(StringUtils.MobName.Star1));

    public static final RegistryObject<Item> StarLeggings = ITEMS.register("star_leggings",
            () -> new StarArmor(ItemMaterial.Moon, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.Moon1Italic)));

    public static final RegistryObject<Item> StarHelmet = ITEMS.register("star_helmet",
            () -> new StarArmor(ItemMaterial.Moon, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.Moon1Italic)));

    public static final RegistryObject<Item> LifeFruit = ITEMS.register("life_fruit",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Life)));

    public static final RegistryObject<Item> QingMingPrefix = ITEMS.register("qingming_prefix",
            () -> new QingMingPrefix(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> QingMingGem = ITEMS.register("qingming_gem",
            () -> new QingMingGem(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> QingMingForgePaper = ITEMS.register("qingming_forge_paper",
            () -> new QingMingForgePaper(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> QingMingAttackRing = ITEMS.register("qingming_attack_ring",
            () -> new QingMingAttackRing(new Item.Properties().rarity(CustomStyle.LifeBold).stacksTo(1)));

    public static final RegistryObject<Item> QingMingBowRing = ITEMS.register("qingming_bow_ring",
            () -> new QingMingBowRing(new Item.Properties().rarity(CustomStyle.LifeBold).stacksTo(1)));

    public static final RegistryObject<Item> QingMingManaRing = ITEMS.register("qingming_mana_ring",
            () -> new QingMingManaRing(new Item.Properties().rarity(CustomStyle.LifeBold).stacksTo(1)));

    public static final RegistryObject<Item> LifeElementPiece0 = ITEMS.register("life_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Life)));

    public static final RegistryObject<Item> LifeElementPiece1 = ITEMS.register("life_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> LifeElementPiece2 = ITEMS.register("life_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LifeItalic)));

    public static final RegistryObject<Item> WaterElementPiece0 = ITEMS.register("water_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Water)));

    public static final RegistryObject<Item> WaterElementPiece1 = ITEMS.register("water_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WaterBold)));

    public static final RegistryObject<Item> WaterElementPiece2 = ITEMS.register("water_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WaterItalic)));

    public static final RegistryObject<Item> FireElementPiece0 = ITEMS.register("fire_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Magma)));

    public static final RegistryObject<Item> FireElementPiece1 = ITEMS.register("fire_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.FireBold)));

    public static final RegistryObject<Item> FireElementPiece2 = ITEMS.register("fire_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.FireItalic)));

    public static final RegistryObject<Item> StoneElementPiece0 = ITEMS.register("stone_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Stone)));

    public static final RegistryObject<Item> StoneElementPiece1 = ITEMS.register("stone_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.StoneBold)));

    public static final RegistryObject<Item> StoneElementPiece2 = ITEMS.register("stone_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.StoneItalic)));

    public static final RegistryObject<Item> IceElementPiece0 = ITEMS.register("ice_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Ice)));

    public static final RegistryObject<Item> IceElementPiece1 = ITEMS.register("ice_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> IceElementPiece2 = ITEMS.register("ice_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> LightningElementPiece0 = ITEMS.register("lightning_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Lightning)));

    public static final RegistryObject<Item> LightningElementPiece1 = ITEMS.register("lightning_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LightningBold)));

    public static final RegistryObject<Item> LightningElementPiece2 = ITEMS.register("lightning_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LightningItalic)));

    public static final RegistryObject<Item> WindElementPiece0 = ITEMS.register("wind_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Wind)));

    public static final RegistryObject<Item> WindElementPiece1 = ITEMS.register("wind_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WindBold)));

    public static final RegistryObject<Item> WindElementPiece2 = ITEMS.register("wind_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WindItalic)));

    public static final RegistryObject<Item> LifeCrystal0 = ITEMS.register("life_crystal0",
            () -> new LifeCrystal(new Item.Properties().rarity(CustomStyle.LifeBold), 0));

    public static final RegistryObject<Item> LifeCrystal1 = ITEMS.register("life_crystal1",
            () -> new LifeCrystal(new Item.Properties().rarity(CustomStyle.LifeBold), 1));

    public static final RegistryObject<Item> LifeCrystal2 = ITEMS.register("life_crystal2",
            () -> new LifeCrystal(new Item.Properties().rarity(CustomStyle.LifeBold), 2));

    public static final RegistryObject<Item> LifeCrystal3 = ITEMS.register("life_crystal3",
            () -> new LifeCrystal(new Item.Properties().rarity(CustomStyle.LifeBold), 3));


    public static final RegistryObject<Item> WaterCrystal0 = ITEMS.register("water_crystal0",
            () -> new WaterCrystal(new Item.Properties().rarity(CustomStyle.WaterBold), 0));

    public static final RegistryObject<Item> WaterCrystal1 = ITEMS.register("water_crystal1",
            () -> new WaterCrystal(new Item.Properties().rarity(CustomStyle.WaterBold), 1));

    public static final RegistryObject<Item> WaterCrystal2 = ITEMS.register("water_crystal2",
            () -> new WaterCrystal(new Item.Properties().rarity(CustomStyle.WaterBold), 2));

    public static final RegistryObject<Item> WaterCrystal3 = ITEMS.register("water_crystal3",
            () -> new WaterCrystal(new Item.Properties().rarity(CustomStyle.WaterBold), 3));

    public static final RegistryObject<Item> FireCrystal0 = ITEMS.register("fire_crystal0",
            () -> new FireCrystal(new Item.Properties().rarity(CustomStyle.FireBold), 0));

    public static final RegistryObject<Item> FireCrystal1 = ITEMS.register("fire_crystal1",
            () -> new FireCrystal(new Item.Properties().rarity(CustomStyle.FireBold), 1));

    public static final RegistryObject<Item> FireCrystal2 = ITEMS.register("fire_crystal2",
            () -> new FireCrystal(new Item.Properties().rarity(CustomStyle.FireBold), 2));

    public static final RegistryObject<Item> FireCrystal3 = ITEMS.register("fire_crystal3",
            () -> new FireCrystal(new Item.Properties().rarity(CustomStyle.FireBold), 3));

    public static final RegistryObject<Item> StoneCrystal0 = ITEMS.register("stone_crystal0",
            () -> new StoneCrystal(new Item.Properties().rarity(CustomStyle.StoneBold), 0));

    public static final RegistryObject<Item> StoneCrystal1 = ITEMS.register("stone_crystal1",
            () -> new StoneCrystal(new Item.Properties().rarity(CustomStyle.StoneBold), 1));

    public static final RegistryObject<Item> StoneCrystal2 = ITEMS.register("stone_crystal2",
            () -> new StoneCrystal(new Item.Properties().rarity(CustomStyle.StoneBold), 2));

    public static final RegistryObject<Item> StoneCrystal3 = ITEMS.register("stone_crystal3",
            () -> new StoneCrystal(new Item.Properties().rarity(CustomStyle.StoneBold), 3));

    public static final RegistryObject<Item> IceCrystal0 = ITEMS.register("ice_crystal0",
            () -> new IceCrystal(new Item.Properties().rarity(CustomStyle.IceBold), 0));

    public static final RegistryObject<Item> IceCrystal1 = ITEMS.register("ice_crystal1",
            () -> new IceCrystal(new Item.Properties().rarity(CustomStyle.IceBold), 1));

    public static final RegistryObject<Item> IceCrystal2 = ITEMS.register("ice_crystal2",
            () -> new IceCrystal(new Item.Properties().rarity(CustomStyle.IceBold), 2));

    public static final RegistryObject<Item> IceCrystal3 = ITEMS.register("ice_crystal3",
            () -> new IceCrystal(new Item.Properties().rarity(CustomStyle.IceBold), 3));

    public static final RegistryObject<Item> WindCrystal0 = ITEMS.register("wind_crystal0",
            () -> new WindCrystal(new Item.Properties().rarity(CustomStyle.WindBold), 0));

    public static final RegistryObject<Item> WindCrystal1 = ITEMS.register("wind_crystal1",
            () -> new WindCrystal(new Item.Properties().rarity(CustomStyle.WindBold), 1));

    public static final RegistryObject<Item> WindCrystal2 = ITEMS.register("wind_crystal2",
            () -> new WindCrystal(new Item.Properties().rarity(CustomStyle.WindBold), 2));

    public static final RegistryObject<Item> WindCrystal3 = ITEMS.register("wind_crystal3",
            () -> new WindCrystal(new Item.Properties().rarity(CustomStyle.WindBold), 3));

    public static final RegistryObject<Item> LightningCrystal0 = ITEMS.register("lightning_crystal0",
            () -> new LightningCrystal(new Item.Properties().rarity(CustomStyle.LightningBold), 0));

    public static final RegistryObject<Item> LightningCrystal1 = ITEMS.register("lightning_crystal1",
            () -> new LightningCrystal(new Item.Properties().rarity(CustomStyle.LightningBold), 1));

    public static final RegistryObject<Item> LightningCrystal2 = ITEMS.register("lightning_crystal2",
            () -> new LightningCrystal(new Item.Properties().rarity(CustomStyle.LightningBold), 2));

    public static final RegistryObject<Item> LightningCrystal3 = ITEMS.register("lightning_crystal3",
            () -> new LightningCrystal(new Item.Properties().rarity(CustomStyle.LightningBold), 3));

    public static final RegistryObject<Item> LifeElement = ITEMS.register("life_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> WaterElement = ITEMS.register("water_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.WaterBold)));

    public static final RegistryObject<Item> FireElement = ITEMS.register("fire_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.FireBold)));

    public static final RegistryObject<Item> StoneElement = ITEMS.register("stone_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.StoneBold)));

    public static final RegistryObject<Item> IceElement = ITEMS.register("ice_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> LightningElement = ITEMS.register("lightning_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.LightningBold)));

    public static final RegistryObject<Item> WindElement = ITEMS.register("wind_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.WindBold)));

    public static final RegistryObject<Item> MobArmorLifeElementHelmet = ITEMS.register("mob_armor_life_element_helmet",
            () -> new MobArmor(ItemMaterial.LifeElement, ArmorItem.Type.HELMET, StringUtils.MobName.LifeElement));

    public static final RegistryObject<Item> MobArmorLifeElementChest = ITEMS.register("mob_armor_life_element_chest",
            () -> new MobArmor(ItemMaterial.LifeElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorLifeElementLeggings = ITEMS.register("mob_armor_life_element_leggings",
            () -> new MobArmor(ItemMaterial.LifeElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorLifeElementBoots = ITEMS.register("mob_armor_life_element_boots",
            () -> new MobArmor(ItemMaterial.LifeElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorWaterElementHelmet = ITEMS.register("mob_armor_water_element_helmet",
            () -> new MobArmor(ItemMaterial.WaterElement, ArmorItem.Type.HELMET, StringUtils.MobName.WaterElement));

    public static final RegistryObject<Item> MobArmorWaterElementChest = ITEMS.register("mob_armor_water_element_chest",
            () -> new MobArmor(ItemMaterial.WaterElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorWaterElementLeggings = ITEMS.register("mob_armor_water_element_leggings",
            () -> new MobArmor(ItemMaterial.WaterElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorWaterElementBoots = ITEMS.register("mob_armor_water_element_boots",
            () -> new MobArmor(ItemMaterial.WaterElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorFireElementHelmet = ITEMS.register("mob_armor_fire_element_helmet",
            () -> new MobArmor(ItemMaterial.FireElement, ArmorItem.Type.HELMET, StringUtils.MobName.FireElement));

    public static final RegistryObject<Item> MobArmorFireElementChest = ITEMS.register("mob_armor_fire_element_chest",
            () -> new MobArmor(ItemMaterial.FireElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorFireElementLeggings = ITEMS.register("mob_armor_fire_element_leggings",
            () -> new MobArmor(ItemMaterial.FireElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorFireElementBoots = ITEMS.register("mob_armor_fire_element_boots",
            () -> new MobArmor(ItemMaterial.FireElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorStoneElementHelmet = ITEMS.register("mob_armor_stone_element_helmet",
            () -> new MobArmor(ItemMaterial.StoneElement, ArmorItem.Type.HELMET, StringUtils.MobName.StoneElement));

    public static final RegistryObject<Item> MobArmorStoneElementChest = ITEMS.register("mob_armor_stone_element_chest",
            () -> new MobArmor(ItemMaterial.StoneElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorStoneElementLeggings = ITEMS.register("mob_armor_stone_element_leggings",
            () -> new MobArmor(ItemMaterial.StoneElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorStoneElementBoots = ITEMS.register("mob_armor_stone_element_boots",
            () -> new MobArmor(ItemMaterial.StoneElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorIceElementHelmet = ITEMS.register("mob_armor_ice_element_helmet",
            () -> new MobArmor(ItemMaterial.IceElement, ArmorItem.Type.HELMET, StringUtils.MobName.IceElement));

    public static final RegistryObject<Item> MobArmorIceElementChest = ITEMS.register("mob_armor_ice_element_chest",
            () -> new MobArmor(ItemMaterial.IceElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorIceElementLeggings = ITEMS.register("mob_armor_ice_element_leggings",
            () -> new MobArmor(ItemMaterial.IceElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorIceElementBoots = ITEMS.register("mob_armor_ice_element_boots",
            () -> new MobArmor(ItemMaterial.IceElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorLightningElementHelmet = ITEMS.register("mob_armor_lightning_element_helmet",
            () -> new MobArmor(ItemMaterial.LightningElement, ArmorItem.Type.HELMET, StringUtils.MobName.LightningElement));

    public static final RegistryObject<Item> MobArmorLightningElementChest = ITEMS.register("mob_armor_lightning_element_chest",
            () -> new MobArmor(ItemMaterial.LightningElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorLightningElementLeggings = ITEMS.register("mob_armor_lightning_element_leggings",
            () -> new MobArmor(ItemMaterial.LightningElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorLightningElementBoots = ITEMS.register("mob_armor_lightning_element_boots",
            () -> new MobArmor(ItemMaterial.LightningElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorWindElementHelmet = ITEMS.register("mob_armor_wind_element_helmet",
            () -> new MobArmor(ItemMaterial.WindElement, ArmorItem.Type.HELMET, StringUtils.MobName.WindElement));

    public static final RegistryObject<Item> MobArmorWindElementChest = ITEMS.register("mob_armor_wind_element_chest",
            () -> new MobArmor(ItemMaterial.WindElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorWindElementLeggings = ITEMS.register("mob_armor_wind_element_leggings",
            () -> new MobArmor(ItemMaterial.WindElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorWindElementBoots = ITEMS.register("mob_armor_wind_element_boots",
            () -> new MobArmor(ItemMaterial.WindElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> RainbowPowder = ITEMS.register("rainbow_powder",
            () -> new RainbowPowder(new Item.Properties()));

    public static final RegistryObject<Item> RainbowCrystal = ITEMS.register("rainbow_crystal",
            () -> new RainbowCrystal(new Item.Properties()));

    public static final RegistryObject<Item> LifeHolyStone0 = ITEMS.register("life_holy_stone_0",
            () -> new LifeHolyStone(new Item.Properties().rarity(CustomStyle.LifeBold), 0));

    public static final RegistryObject<Item> LifeHolyStone1 = ITEMS.register("life_holy_stone_1",
            () -> new LifeHolyStone(new Item.Properties().rarity(CustomStyle.LifeBold), 1));

    public static final RegistryObject<Item> LifeHolyStone2 = ITEMS.register("life_holy_stone_2",
            () -> new LifeHolyStone(new Item.Properties().rarity(CustomStyle.LifeBold), 2));

    public static final RegistryObject<Item> WaterHolyStone0 = ITEMS.register("water_holy_stone_0",
            () -> new WaterHolyStone(new Item.Properties().rarity(CustomStyle.WaterBold), 0));

    public static final RegistryObject<Item> WaterHolyStone1 = ITEMS.register("water_holy_stone_1",
            () -> new WaterHolyStone(new Item.Properties().rarity(CustomStyle.WaterBold), 1));

    public static final RegistryObject<Item> WaterHolyStone2 = ITEMS.register("water_holy_stone_2",
            () -> new WaterHolyStone(new Item.Properties().rarity(CustomStyle.WaterBold), 2));

    public static final RegistryObject<Item> FireHolyStone0 = ITEMS.register("fire_holy_stone_0",
            () -> new FireHolyStone(new Item.Properties().rarity(CustomStyle.FireBold), 0));

    public static final RegistryObject<Item> FireHolyStone1 = ITEMS.register("fire_holy_stone_1",
            () -> new FireHolyStone(new Item.Properties().rarity(CustomStyle.FireBold), 1));

    public static final RegistryObject<Item> FireHolyStone2 = ITEMS.register("fire_holy_stone_2",
            () -> new FireHolyStone(new Item.Properties().rarity(CustomStyle.FireBold), 2));

    public static final RegistryObject<Item> StoneHolyStone0 = ITEMS.register("stone_holy_stone_0",
            () -> new StoneHolyStone(new Item.Properties().rarity(CustomStyle.StoneBold), 0));

    public static final RegistryObject<Item> StoneHolyStone1 = ITEMS.register("stone_holy_stone_1",
            () -> new StoneHolyStone(new Item.Properties().rarity(CustomStyle.StoneBold), 1));

    public static final RegistryObject<Item> StoneHolyStone2 = ITEMS.register("stone_holy_stone_2",
            () -> new StoneHolyStone(new Item.Properties().rarity(CustomStyle.StoneBold), 2));

    public static final RegistryObject<Item> IceHolyStone0 = ITEMS.register("ice_holy_stone_0",
            () -> new IceHolyStone(new Item.Properties().rarity(CustomStyle.IceBold), 0));

    public static final RegistryObject<Item> IceHolyStone1 = ITEMS.register("ice_holy_stone_1",
            () -> new IceHolyStone(new Item.Properties().rarity(CustomStyle.IceBold), 1));

    public static final RegistryObject<Item> IceHolyStone2 = ITEMS.register("ice_holy_stone_2",
            () -> new IceHolyStone(new Item.Properties().rarity(CustomStyle.IceBold), 2));

    public static final RegistryObject<Item> LightningHolyStone0 = ITEMS.register("lightning_holy_stone_0",
            () -> new LightningHolyStone(new Item.Properties().rarity(CustomStyle.LightningBold), 0));

    public static final RegistryObject<Item> LightningHolyStone1 = ITEMS.register("lightning_holy_stone_1",
            () -> new LightningHolyStone(new Item.Properties().rarity(CustomStyle.LightningBold), 1));

    public static final RegistryObject<Item> LightningHolyStone2 = ITEMS.register("lightning_holy_stone_2",
            () -> new LightningHolyStone(new Item.Properties().rarity(CustomStyle.LightningBold), 2));

    public static final RegistryObject<Item> WindHolyStone0 = ITEMS.register("wind_holy_stone_0",
            () -> new WindHolyStone(new Item.Properties().rarity(CustomStyle.WindBold), 0));

    public static final RegistryObject<Item> WindHolyStone1 = ITEMS.register("wind_holy_stone_1",
            () -> new WindHolyStone(new Item.Properties().rarity(CustomStyle.WindBold), 1));

    public static final RegistryObject<Item> WindHolyStone2 = ITEMS.register("wind_holy_stone_2",
            () -> new WindHolyStone(new Item.Properties().rarity(CustomStyle.WindBold), 2));

    public static final RegistryObject<Item> LifeElementSword = ITEMS.register("life_element_sword",
            () -> new LifeElementSword(new Item.Properties().stacksTo(1).rarity(CustomStyle.LifeItalic)));

    public static final RegistryObject<Item> LifeElementBow = ITEMS.register("life_element_bow",
            () -> new LifeElementBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.LifeItalic)));

    public static final RegistryObject<Item> LifeElementSceptre = ITEMS.register("life_element_sceptre",
            () -> new LifeElementSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic)));

    public static final RegistryObject<Item> LifeElementSwordForgeDraw = ITEMS.register("life_element_sword_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.LifeBold), ModItems.LifeElementSword.get()));

    public static final RegistryObject<Item> LifeElementBowForgeDraw = ITEMS.register("life_element_bow_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.LifeBold), ModItems.LifeElementBow.get()));

    public static final RegistryObject<Item> LifeElementSceptreForgeDraw = ITEMS.register("life_element_sceptre_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.LifeBold), ModItems.LifeElementSceptre.get()));

    public static final RegistryObject<Item> WaterElementSword = ITEMS.register("water_element_sword",
            () -> new WaterElementSword(new Item.Properties().stacksTo(1).rarity(CustomStyle.WaterItalic)));

    public static final RegistryObject<Item> WaterElementBow = ITEMS.register("water_element_bow",
            () -> new WaterElementBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.WaterItalic)));

    public static final RegistryObject<Item> WaterElementSceptre = ITEMS.register("water_element_sceptre",
            () -> new WaterElementSceptre(new Item.Properties().rarity(CustomStyle.WaterItalic)));

    public static final RegistryObject<Item> WaterElementSwordForgeDraw = ITEMS.register("water_element_sword_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.WaterBold), ModItems.WaterElementSword.get()));

    public static final RegistryObject<Item> WaterElementBowForgeDraw = ITEMS.register("water_element_bow_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.WaterBold), ModItems.WaterElementBow.get()));

    public static final RegistryObject<Item> WaterElementSceptreForgeDraw = ITEMS.register("water_element_sceptre_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.WaterBold), ModItems.WaterElementSceptre.get()));

    public static final RegistryObject<Item> ChangeLog = ITEMS.register("change_log",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> FireElementSword = ITEMS.register("fire_element_sword",
            () -> new FireElementSword(new Item.Properties().stacksTo(1).rarity(CustomStyle.FireItalic)));

    public static final RegistryObject<Item> FireElementBow = ITEMS.register("fire_element_bow",
            () -> new FireElementBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.FireItalic)));

    public static final RegistryObject<Item> FireElementSceptre = ITEMS.register("fire_element_sceptre",
            () -> new FireElementSceptre(new Item.Properties().rarity(CustomStyle.FireItalic)));

    public static final RegistryObject<Item> FireElementSwordForgeDraw = ITEMS.register("fire_element_sword_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.FireBold), ModItems.FireElementSword.get()));

    public static final RegistryObject<Item> FireElementBowForgeDraw = ITEMS.register("fire_element_bow_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.FireBold), ModItems.FireElementBow.get()));

    public static final RegistryObject<Item> FireElementSceptreForgeDraw = ITEMS.register("fire_element_sceptre_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.FireBold), ModItems.FireElementSceptre.get()));

    public static final RegistryObject<Item> ShulkerSoul = ITEMS.register("shulker_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EndBold), true, true));

    public static final RegistryObject<Item> EnderMiteSoul = ITEMS.register("ender_mite_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EndBold), true, true));

    public static final RegistryObject<Item> EndCrystal = ITEMS.register("end_crystal",
            () -> new EndCrystal(new Item.Properties().rarity(CustomStyle.EndBold)));

    public static final RegistryObject<Item> END_CURIOS_BOW = ITEMS.register("end_curios",
            () -> new EndCuriosBow(new Item.Properties().rarity(CustomStyle.EndBold)));

    public static final RegistryObject<Item> END_CURIOS_MANA = ITEMS.register("end_curios1",
            () -> new EndCuriosMana(new Item.Properties().rarity(CustomStyle.EndBold)));

    public static final RegistryObject<Item> EndCuriosForgeDraw = ITEMS.register("end_curios_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.EndBold), ModItems.END_CURIOS_BOW.get()));

    public static final RegistryObject<Item> EndCurios1ForgeDraw = ITEMS.register("end_curios1_forge_draw",
            () -> new WraqForge(new Item.Properties().rarity(CustomStyle.EndBold), ModItems.END_CURIOS_MANA.get()));

    public static final RegistryObject<Item> MobArmorEndStrayHelmet = ITEMS.register("mob_armor_end_stray_helmet",
            () -> new MobArmor(ItemMaterial.PurpleIron, ArmorItem.Type.HELMET, StringUtils.MobName.EndStray));

    public static final RegistryObject<Item> RoseGoldCoin = ITEMS.register("rose_gold_coin",
            () -> new Item(new Item.Properties().rarity(CustomStyle.PurpleIronBold)));

    public static final RegistryObject<Item> MobArmorLabourDay1 = ITEMS.register("mob_armor_labour_day1",
            () -> new MobArmor(ItemMaterial.BasicArmor1, ArmorItem.Type.HELMET, StringUtils.MobName.LabourDay1));

    public static final RegistryObject<Item> MobArmorLabourDay2 = ITEMS.register("mob_armor_labour_day2",
            () -> new MobArmor(ItemMaterial.BasicArmor2, ArmorItem.Type.HELMET, StringUtils.MobName.LabourDay2));

    public static final RegistryObject<Item> OldSilverCoin = ITEMS.register("old_silver_coin",
            () -> new OldCoin(new Item.Properties().rarity(CustomStyle.Mine)));

    public static final RegistryObject<Item> OldGoldCoin = ITEMS.register("old_gold_coin",
            () -> new OldCoin(new Item.Properties().rarity(CustomStyle.Gold)));

    public static final RegistryObject<Item> LabourDayForgePaper = ITEMS.register("labour_day_forge_paper",
            () -> new LabourDayForgePaper(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> LabourDayIronHoe = ITEMS.register("labour_day_iron_hoe",
            () -> new LabourDayIronHoe(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> LabourDayIronPickaxe = ITEMS.register("labour_day_iron_pickaxe",
            () -> new LabourDayIronPickaxe(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> LabourDayPrefix = ITEMS.register("labour_day_prefix",
            () -> new LabourDayPrefix(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> LabourDayGem = ITEMS.register("labour_day_gem",
            () -> new LabourDayGem(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> LabourDayLottery = ITEMS.register("labour_day_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.GoldBold), new ArrayList<>() {{
                add(new NewLotteries.Loot(new ItemStack(ModItems.LabourDayIronPickaxe.get()), 0.01));
                add(new NewLotteries.Loot(new ItemStack(ModItems.LabourDayIronHoe.get()), 0.01));
                add(new NewLotteries.Loot(new ItemStack(ModItems.LabourDayForgePaper.get()), 0.02));
                add(new NewLotteries.Loot(new ItemStack(ModItems.LabourDayGem.get()), 0.02));
                add(new NewLotteries.Loot(new ItemStack(ModItems.LabourDayPrefix.get()), 0.04));
                add(new NewLotteries.Loot(new ItemStack(ModItems.OldGoldCoin.get()), 0.45));
                add(new NewLotteries.Loot(new ItemStack(ModItems.OldSilverCoin.get()), 0.45));
            }}));

    public static final RegistryObject<Item> CastleMopUpPaper = ITEMS.register("castle_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.Castle));

    public static final RegistryObject<Item> CastleSecondFloorMopUpPaper = ITEMS.register("castle_second_floor_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.CastleSecondFloor));

    public static final RegistryObject<Item> DevilMopUpPaper = ITEMS.register("devil_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.Devil));

    public static final RegistryObject<Item> IceKnightMopUpPaper = ITEMS.register("ice_knight_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.IceKnight));

    public static final RegistryObject<Item> LightningMopUpPaper = ITEMS.register("lightning_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.Lightning));

    public static final RegistryObject<Item> Main1BossMopUpPaper = ITEMS.register("main1_boss_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.Main1Boss));

    public static final RegistryObject<Item> MoonMopUpPaper = ITEMS.register("moon_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.Moon));

    public static final RegistryObject<Item> NetherMopUpPaper = ITEMS.register("nether_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.Nether));

    public static final RegistryObject<Item> PlainMopUpPaper = ITEMS.register("plain_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.Plain));

    public static final RegistryObject<Item> PurpleIronKnightMopUpPaper = ITEMS.register("purple_iron_knight_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.PurpleIronKnight));

    public static final RegistryObject<Item> SakuraBossMopUpPaper = ITEMS.register("sakura_boss_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.SakuraBoss));

    public static final RegistryObject<Item> TabooDevilMopUpPaper = ITEMS.register("taboo_devil_mop_up_paper",
            () -> new MopUpPaper(new Item.Properties().rarity(CustomStyle.RedBold), MopUpPaper.InstanceName.TabooDevil));

    public static final RegistryObject<Item> WorldSoulHollow = ITEMS.register("worldsoul_hollow",
            () -> new Item(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> MobArmorTower1FloorHelmet = ITEMS.register("mob_armor_tower_1floor_helmet",
            () -> new MobArmor(ItemMaterial.LifeElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower1Floor));

    public static final RegistryObject<Item> MobArmorTower2FloorHelmet = ITEMS.register("mob_armor_tower_2floor_helmet",
            () -> new MobArmor(ItemMaterial.WaterElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower2Floor));

    public static final RegistryObject<Item> MobArmorTower3FloorHelmet = ITEMS.register("mob_armor_tower_3floor_helmet",
            () -> new MobArmor(ItemMaterial.FireElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower3Floor));

    public static final RegistryObject<Item> MobArmorTower4FloorHelmet = ITEMS.register("mob_armor_tower_4floor_helmet",
            () -> new MobArmor(ItemMaterial.StoneElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower4Floor));

    public static final RegistryObject<Item> MobArmorTower5FloorHelmet = ITEMS.register("mob_armor_tower_5floor_helmet",
            () -> new MobArmor(ItemMaterial.IceElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower5Floor));

    public static final RegistryObject<Item> MobArmorTower6FloorHelmet = ITEMS.register("mob_armor_tower_6floor_helmet",
            () -> new MobArmor(ItemMaterial.LightningElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower6Floor));

    public static final RegistryObject<Item> MopUpPaperLoot = ITEMS.register("mop_up_paper_loot",
            () -> new MopUpPaperLoot(new Item.Properties().rarity(CustomStyle.RedBold)));

    public static final RegistryObject<Item> SwordLottery = ITEMS.register("sword_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.MagmaBold), new ArrayList<>() {{
                List<NewLotteries.Loot> loots = List.of(
/*                        new NewLotteries.Loot(new ItemStack(ModItems.AttackCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.AttackCurios1.get()), 0.005),*/
                        /*new NewLotteries.Loot(new ItemStack(ModItems.AttackCurios0.get()), 0.005),*/
                        new NewLotteries.Loot(new ItemStack(UniformItems.AttackCurios2.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(UniformItems.FireCurios0.get()), 0.005),
                        /*new NewLotteries.Loot(new ItemStack(ModItems.LifeCrystal0.get()), 0.005),*/
/*                        new NewLotteries.Loot(new ItemStack(ModItems.WaterCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.FireCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.StoneCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.IceCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.LightningCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.WindCurios0.get()), 0.005),*/
                        new NewLotteries.Loot(new ItemStack(ModItems.KillPaperLoot.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.MopUpPaperLoot.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.completeGem.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.ReputationMedal.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.goldCoin.get(), 4), 0.1)
                );
                addAll(loots);
            }}));

    public static final RegistryObject<Item> BowLottery = ITEMS.register("bow_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.KazeBold), new ArrayList<>() {{
                List<NewLotteries.Loot> loots = List.of(
/*                        new NewLotteries.Loot(new ItemStack(ModItems.BowCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.BowCurios1.get()), 0.005),*/
                        /*new NewLotteries.Loot(new ItemStack(ModItems.BowCurios0.get()), 0.005),*/
                        new NewLotteries.Loot(new ItemStack(UniformItems.BowCurios2.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(UniformItems.FireCurios0.get()), 0.005),
                        /*new NewLotteries.Loot(new ItemStack(ModItems.LifeCrystal0.get()), 0.005),*/
/*                        new NewLotteries.Loot(new ItemStack(ModItems.WaterCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.FireCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.StoneCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.IceCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.LightningCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.WindCurios0.get()), 0.005),*/
                        new NewLotteries.Loot(new ItemStack(ModItems.KillPaperLoot.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.MopUpPaperLoot.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.completeGem.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.ReputationMedal.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.goldCoin.get(), 4), 0.1)
                );
                addAll(loots);
            }}));

    public static final RegistryObject<Item> SceptreLottery = ITEMS.register("sceptre_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.EvokerBold), new ArrayList<>() {{
                List<NewLotteries.Loot> loots = List.of(
/*                        new NewLotteries.Loot(new ItemStack(ModItems.ManaCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.ManaCurios1.get()), 0.005),*/
                        /*new NewLotteries.Loot(new ItemStack(ModItems.ManaCurios0.get()), 0.005),*/
                        new NewLotteries.Loot(new ItemStack(UniformItems.ManaCurios2.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(UniformItems.FireCurios0.get()), 0.005),
                        /*new NewLotteries.Loot(new ItemStack(ModItems.LifeCrystal0.get()), 0.005),*/
/*                        new NewLotteries.Loot(new ItemStack(ModItems.WaterCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.FireCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.StoneCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.IceCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.LightningCurios0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.WindCurios0.get()), 0.005),*/
                        new NewLotteries.Loot(new ItemStack(ModItems.KillPaperLoot.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.MopUpPaperLoot.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.completeGem.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.ReputationMedal.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.goldCoin.get(), 4), 0.1)
                );
                addAll(loots);
            }}));

    public static final RegistryObject<Item> WaterBottle = ITEMS.register("water_bottle",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Water)));

    public static final RegistryObject<Item> AttackUpPotion = ITEMS.register("attackup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Volcano), NewPotion.PotionName.AttackUp, 0));
    public static final RegistryObject<Item> AttackUpConPotion = ITEMS.register("attackup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Volcano), NewPotion.PotionName.AttackUp, 1));
    public static final RegistryObject<Item> AttackUpLongPotion = ITEMS.register("attackup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Volcano), NewPotion.PotionName.AttackUp, 2));

    public static final RegistryObject<Item> SplashAttackUpPotion = ITEMS.register("splash_attackup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Volcano), ModPotions.Type.AttackUp));
    public static final RegistryObject<Item> SplashAttackUpConPotion = ITEMS.register("splash_attackup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Volcano), ModPotions.Type.ConAttackUp));
    public static final RegistryObject<Item> SplashAttackUpLongPotion = ITEMS.register("splash_attackup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Volcano), ModPotions.Type.LongAttackUp));

    public static final RegistryObject<Item> DefencePenetrationUpPotion = ITEMS.register("breakdefenceup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefencePenetrationUp, 0));
    public static final RegistryObject<Item> DefencePenetrationUpConPotion = ITEMS.register("breakdefenceup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefencePenetrationUp, 1));
    public static final RegistryObject<Item> DefencePenetrationUpLongPotion = ITEMS.register("breakdefenceup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefencePenetrationUp, 2));

    public static final RegistryObject<Item> SplashDefencePenetrationUpPotion = ITEMS.register("splash_breakdefenceup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.DefencePenetrationUp));
    public static final RegistryObject<Item> SplashDefencePenetrationUpConPotion = ITEMS.register("splash_breakdefenceup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.ConDefencePenetrationUp));
    public static final RegistryObject<Item> SplashDefencePenetrationUpLongPotion = ITEMS.register("splash_breakdefenceup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.LongDefencePenetrationUp));

    public static final RegistryObject<Item> ManaPenetrationUpPotion = ITEMS.register("breakmanadefenceup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaPenetrationUp, 0));
    public static final RegistryObject<Item> ManaPenetrationUpConPotion = ITEMS.register("breakmanadefenceup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaPenetrationUp, 1));
    public static final RegistryObject<Item> ManaPenetrationUpLongPotion = ITEMS.register("breakmanadefenceup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaPenetrationUp, 2));

    public static final RegistryObject<Item> SplashManaPenetrationUpPotion = ITEMS.register("splash_breakmanadefenceup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ManaPenetrationUp));
    public static final RegistryObject<Item> SplashManaPenetrationUpConPotion = ITEMS.register("splash_breakmanadefenceup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ConManaPenetrationUp));
    public static final RegistryObject<Item> SplashManaPenetrationUpLongPotion = ITEMS.register("splash_breakmanadefenceup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.LongManaPenetrationUp));

    public static final RegistryObject<Item> CooldownUpPotion = ITEMS.register("cooldownup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Water), NewPotion.PotionName.CooldownUp, 0));
    public static final RegistryObject<Item> CooldownUpConPotion = ITEMS.register("cooldownup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Water), NewPotion.PotionName.CooldownUp, 1));
    public static final RegistryObject<Item> CooldownUpLongPotion = ITEMS.register("cooldownup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Water), NewPotion.PotionName.CooldownUp, 2));

    public static final RegistryObject<Item> SplashCooldownUpPotion = ITEMS.register("splash_cooldownup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Water), ModPotions.Type.CoolDownUp));
    public static final RegistryObject<Item> SplashCooldownUpConPotion = ITEMS.register("splash_cooldownup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Water), ModPotions.Type.ConCoolDownUp));
    public static final RegistryObject<Item> SplashCooldownUpLongPotion = ITEMS.register("splash_cooldownup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Water), ModPotions.Type.LongCoolDownUp));

    public static final RegistryObject<Item> CritDamageUpPotion = ITEMS.register("critdamageup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.CritDamageUp, 0));
    public static final RegistryObject<Item> CritDamageUpConPotion = ITEMS.register("critdamageup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.CritDamageUp, 1));
    public static final RegistryObject<Item> CritDamageUpLongPotion = ITEMS.register("critdamageup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.CritDamageUp, 2));

    public static final RegistryObject<Item> SplashCritDamageUpPotion = ITEMS.register("splash_critdamageup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.CritDamageUp));
    public static final RegistryObject<Item> SplashCritDamageUpConPotion = ITEMS.register("splash_critdamageup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.ConCritDamageUp));
    public static final RegistryObject<Item> SplashCritDamageUpLongPotion = ITEMS.register("splash_critdamageup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.LongCritDamageUp));

    public static final RegistryObject<Item> CritRateUpPotion = ITEMS.register("critrateup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Sakura), NewPotion.PotionName.CritRateUp, 0));
    public static final RegistryObject<Item> CritRateUpConPotion = ITEMS.register("critrateup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Sakura), NewPotion.PotionName.CritRateUp, 1));
    public static final RegistryObject<Item> CritRateUpLongPotion = ITEMS.register("critrateup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Sakura), NewPotion.PotionName.CritRateUp, 2));

    public static final RegistryObject<Item> SplashCritRateUpPotion = ITEMS.register("splash_critrateup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Sakura), ModPotions.Type.CritRateUp));
    public static final RegistryObject<Item> SplashCritRateUpConPotion = ITEMS.register("splash_critrateup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Sakura), ModPotions.Type.ConCritRateUp));
    public static final RegistryObject<Item> SplashCritRateUpLongPotion = ITEMS.register("splash_critrateup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Sakura), ModPotions.Type.LongCritRateUp));

    public static final RegistryObject<Item> DefenceUpPotion = ITEMS.register("defenceup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefenceUp, 0));
    public static final RegistryObject<Item> DefenceUpConPotion = ITEMS.register("defenceup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefenceUp, 1));
    public static final RegistryObject<Item> DefenceUpLongPotion = ITEMS.register("defenceup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefenceUp, 2));

    public static final RegistryObject<Item> SplashDefenceUpPotion = ITEMS.register("splash_defenceup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.DefenceUp));
    public static final RegistryObject<Item> SplashDefenceUpConPotion = ITEMS.register("splash_defenceup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.ConDefenceUp));
    public static final RegistryObject<Item> SplashDefenceUpLongPotion = ITEMS.register("splash_defenceup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.LongDefenceUp));

    public static final RegistryObject<Item> HealthStealUpPotion = ITEMS.register("healstealup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Nether), NewPotion.PotionName.HealthStealUp, 0));
    public static final RegistryObject<Item> HealthStealUpConPotion = ITEMS.register("healstealup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Nether), NewPotion.PotionName.HealthStealUp, 1));
    public static final RegistryObject<Item> HealthStealUpLongPotion = ITEMS.register("healstealup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Nether), NewPotion.PotionName.HealthStealUp, 2));

    public static final RegistryObject<Item> SplashHealthStealUpPotion = ITEMS.register("splash_healstealup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Nether), ModPotions.Type.HealthStealUp));
    public static final RegistryObject<Item> SplashHealthStealUpConPotion = ITEMS.register("splash_healstealup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Nether), ModPotions.Type.ConHealthStealUp));
    public static final RegistryObject<Item> SplashHealthStealUpLongPotion = ITEMS.register("splash_healstealup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Nether), ModPotions.Type.LongHealthStealUp));

    public static final RegistryObject<Item> ManaDamageUpPotion = ITEMS.register("manadamageup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaDamageUp, 0));
    public static final RegistryObject<Item> ManaDamageUpConPotion = ITEMS.register("manadamageup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaDamageUp, 1));
    public static final RegistryObject<Item> ManaDamageUpLongPotion = ITEMS.register("manadamageup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaDamageUp, 2));

    public static final RegistryObject<Item> SplashManaDamageUpPotion = ITEMS.register("splash_manadamageup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ManaDamageUp));
    public static final RegistryObject<Item> SplashManaDamageUpConPotion = ITEMS.register("splash_manadamageup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ConManaDamageUp));
    public static final RegistryObject<Item> SplashManaDamageUpLongPotion = ITEMS.register("splash_manadamageup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.LongManaDamageUp));

    public static final RegistryObject<Item> ManaDefenceUpPotion = ITEMS.register("manadefenceup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.ManaDefenceUp, 0));
    public static final RegistryObject<Item> ManaDefenceUpConPotion = ITEMS.register("manadefenceup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.ManaDefenceUp, 1));
    public static final RegistryObject<Item> ManaDefenceUpLongPotion = ITEMS.register("manadefenceup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.ManaDefenceUp, 2));

    public static final RegistryObject<Item> SplashManaDefenceUpPotion = ITEMS.register("splash_manadefenceup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.ManaDefenceUp));
    public static final RegistryObject<Item> SplashManaDefenceUpConPotion = ITEMS.register("splash_manadefenceup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.ConManaDefenceUp));
    public static final RegistryObject<Item> SplashManaDefenceUpLongPotion = ITEMS.register("splash_manadefenceup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.LongManaDefenceUp));

    public static final RegistryObject<Item> ManaRecoverUpPotion = ITEMS.register("manareplyup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaRecoverUp, 0));
    public static final RegistryObject<Item> ManaRecoverUpConPotion = ITEMS.register("manareplyup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaRecoverUp, 1));
    public static final RegistryObject<Item> ManaRecoverUpLongPotion = ITEMS.register("manareplyup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaRecoverUp, 2));

    public static final RegistryObject<Item> SplashManaRecoverUpPotion = ITEMS.register("splash_manareplyup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ManaRecoverUp));
    public static final RegistryObject<Item> SplashManaRecoverUpConPotion = ITEMS.register("splash_manareplyup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ConManaRecoverUp));
    public static final RegistryObject<Item> SplashManaRecoverUpLongPotion = ITEMS.register("splash_manareplyup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.LongManaRecoverUp));

    public static final RegistryObject<Item> MovementSpeedUpPotion = ITEMS.register("speedup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.MovementSpeedUp, 0));
    public static final RegistryObject<Item> MovementSpeedUpConPotion = ITEMS.register("speedup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.MovementSpeedUp, 1));
    public static final RegistryObject<Item> MovementSpeedUpLongPotion = ITEMS.register("speedup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.MovementSpeedUp, 2));

    public static final RegistryObject<Item> SplashMovementSpeedUpPotion = ITEMS.register("splash_speedup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.MovementSpeedUp));
    public static final RegistryObject<Item> SplashMovementSpeedUpConPotion = ITEMS.register("splash_speedup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.ConMovementSpeedUp));
    public static final RegistryObject<Item> SplashMovementSpeedUpLongPotion = ITEMS.register("splash_speedup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.LongMovementSpeedUp));

    public static final RegistryObject<Item> HealthRecoverUpPotion = ITEMS.register("healreply_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.HealthRecoverUp, 0));
    public static final RegistryObject<Item> HealthRecoverUpConPotion = ITEMS.register("healreply_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.HealthRecoverUp, 1));
    public static final RegistryObject<Item> HealthRecoverUpLongPotion = ITEMS.register("healreply_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.HealthRecoverUp, 2));

    public static final RegistryObject<Item> SplashHealthRecoverUpPotion = ITEMS.register("splash_healreply_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.HealthRecoverUp));
    public static final RegistryObject<Item> SplashHealthRecoverUpConPotion = ITEMS.register("splash_healreply_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.ConHealthRecoverUp));
    public static final RegistryObject<Item> SplashHealthRecoverUpLongPotion = ITEMS.register("splash_healreply_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.LongHealthRecoverUp));

    public static final RegistryObject<Item> ATTACK_UP_POTION_BAG = ITEMS.register("attackup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), AttackUpPotion.get()));

    public static final RegistryObject<Item> DEFENCE_PENETRATION_POTION_BAG = ITEMS.register("breakdefenceup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), DefencePenetrationUpPotion.get()));

    public static final RegistryObject<Item> CRIT_RATE_UP_POTIONBAG = ITEMS.register("critrateup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), CritRateUpPotion.get()));

    public static final RegistryObject<Item> CRIT_DAMAGE_UP_POTION_BAG = ITEMS.register("critdamageup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), CritDamageUpPotion.get()));

    public static final RegistryObject<Item> MANA_DAMAGE_UP_POTION_BAG = ITEMS.register("manadamageup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), ManaDamageUpPotion.get()));

    public static final RegistryObject<Item> MANA_PENETRATION_POTION_BAG = ITEMS.register("manabreakdefenceup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), ManaPenetrationUpPotion.get()));

    public static final RegistryObject<Item> MANA_RECOVER_POTION_BAG = ITEMS.register("manareplyup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), ManaRecoverUpPotion.get()));

    public static final RegistryObject<Item> POWER_RELEASE_SPEED_POTION_BAG = ITEMS.register("cooldowndecreaseup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), CooldownUpPotion.get()));

    public static final RegistryObject<Item> HEALTH_STEAL_UP_POTION_BAG = ITEMS.register("healstealup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), HealthStealUpPotion.get()));

    public static final RegistryObject<Item> DEFENCE_UP_POTION_BAG = ITEMS.register("defenceup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), DefenceUpPotion.get()));

    public static final RegistryObject<Item> MANA_DEFENCE_UP_POTION_BAG = ITEMS.register("manadefenceup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), ManaDefenceUpPotion.get()));

    public static final RegistryObject<Item> MOVEMENT_SPEED_UP_POTION_BAG = ITEMS.register("speedup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), MovementSpeedUpPotion.get()));

    public static final RegistryObject<Item> HEALTH_RECOVER_POTION_BAG = ITEMS.register("healthrecover_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), HealthRecoverUpPotion.get()));

    public static final RegistryObject<Item> Pearl1 = ITEMS.register("pearl1",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> Pearl2 = ITEMS.register("pearl2",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.SkyBold)));

    public static final RegistryObject<Item> Pearl3 = ITEMS.register("pearl3",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.NetherBold)));

    public static final RegistryObject<Item> Pearl4 = ITEMS.register("pearl4",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.EndBold)));

    public static final RegistryObject<Item> Pearl5 = ITEMS.register("pearl5",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.SakuraBold)));

    public static final RegistryObject<Item> Pearl6 = ITEMS.register("pearl6",
            () -> new Pearl(new Item.Properties().rarity(CustomStyle.MoonBold)));

    public static final RegistryObject<Item> WORLD_FORGE_STONE = ITEMS.register("world_forge_stone",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> Splasher = ITEMS.register("splasher",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Magma)));

    public static final RegistryObject<Item> DamageEnhancePotion = ITEMS.register("damage_enhance_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.damageEnhance, 0));

    public static final RegistryObject<Item> DamageEnhanceConPotion = ITEMS.register("damage_enhance_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.damageEnhance, 1));

    public static final RegistryObject<Item> DamageEnhanceLongPotion = ITEMS.register("damage_enhance_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.damageEnhance, 2));

    public static final RegistryObject<Item> SplashDamageEnhancePotion = ITEMS.register("splash_damage_enhance_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.damageEnhance + "_potion"));

    public static final RegistryObject<Item> SplashDamageEnhanceConPotion = ITEMS.register("splash_damage_enhance_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), "con_" + NewPotion.PotionName.damageEnhance + "_potion"));

    public static final RegistryObject<Item> SplashDamageEnhanceLongPotion = ITEMS.register("splash_damage_enhance_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), "long_" + NewPotion.PotionName.damageEnhance + "_potion"));

    public static final RegistryObject<Item> AttackDamageEnhancePotion = ITEMS.register("attack_damage_enhance_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.attackDamageEnhance, 0));

    public static final RegistryObject<Item> AttackDamageEnhanceConPotion = ITEMS.register("attack_damage_enhance_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.attackDamageEnhance, 1));

    public static final RegistryObject<Item> AttackDamageEnhanceLongPotion = ITEMS.register("attack_damage_enhance_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.attackDamageEnhance, 2));

    public static final RegistryObject<Item> SplashAttackDamageEnhancePotion = ITEMS.register("splash_attack_damage_enhance_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.attackDamageEnhance + "_potion"));

    public static final RegistryObject<Item> SplashAttackDamageEnhanceConPotion = ITEMS.register("splash_attack_damage_enhance_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), "con_" + NewPotion.PotionName.attackDamageEnhance + "_potion"));

    public static final RegistryObject<Item> SplashAttackDamageEnhanceLongPotion = ITEMS.register("splash_attack_damage_enhance_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), "long_" + NewPotion.PotionName.attackDamageEnhance + "_potion"));

    public static final RegistryObject<Item> ManaDamageEnhancePotion = ITEMS.register("mana_damage_enhance_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.manaDamageEnhance, 0));

    public static final RegistryObject<Item> ManaDamageEnhanceConPotion = ITEMS.register("mana_damage_enhance_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.manaDamageEnhance, 1));

    public static final RegistryObject<Item> ManaDamageEnhanceLongPotion = ITEMS.register("mana_damage_enhance_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.manaDamageEnhance, 2));

    public static final RegistryObject<Item> SplashManaDamageEnhancePotion = ITEMS.register("splash_mana_damage_enhance_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.manaDamageEnhance + "_potion"));

    public static final RegistryObject<Item> SplashManaDamageEnhanceConPotion = ITEMS.register("splash_mana_damage_enhance_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), "con_" + NewPotion.PotionName.manaDamageEnhance + "_potion"));

    public static final RegistryObject<Item> SplashManaDamageEnhanceLongPotion = ITEMS.register("splash_mana_damage_enhance_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), "long_" + NewPotion.PotionName.manaDamageEnhance + "_potion"));

    public static final RegistryObject<Item> GiantPotion = ITEMS.register("giant_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Life), NewPotion.PotionName.giant, 0));

    public static final RegistryObject<Item> GiantConPotion = ITEMS.register("giant_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Life), NewPotion.PotionName.giant, 1));

    public static final RegistryObject<Item> GiantLongPotion = ITEMS.register("giant_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Life), NewPotion.PotionName.giant, 2));

    public static final RegistryObject<Item> SplashGiantPotion = ITEMS.register("splash_giant_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Life), NewPotion.PotionName.giant + "_potion"));

    public static final RegistryObject<Item> SplashGiantConPotion = ITEMS.register("splash_giant_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Life), "con_" + NewPotion.PotionName.giant + "_potion"));

    public static final RegistryObject<Item> SplashGiantLongPotion = ITEMS.register("splash_giant_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Life), "long_" + NewPotion.PotionName.giant + "_potion"));

    public static final RegistryObject<Item> StonePotion = ITEMS.register("stone_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.stone, 0));

    public static final RegistryObject<Item> StoneConPotion = ITEMS.register("stone_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.stone, 1));

    public static final RegistryObject<Item> StoneLongPotion = ITEMS.register("stone_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.stone, 2));

    public static final RegistryObject<Item> SplashStonePotion = ITEMS.register("splash_stone_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.stone + "_potion"));

    public static final RegistryObject<Item> SplashStoneConPotion = ITEMS.register("splash_stone_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), "con_" + NewPotion.PotionName.stone + "_potion"));

    public static final RegistryObject<Item> SplashStoneLongPotion = ITEMS.register("splash_stone_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), "long_" + NewPotion.PotionName.stone + "_potion"));

    public static final RegistryObject<Item> ExHarvestPotion = ITEMS.register("ex_harvest_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Gold), NewPotion.PotionName.exHarvest, 0));

    public static final RegistryObject<Item> ExHarvestConPotion = ITEMS.register("ex_harvest_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Gold), NewPotion.PotionName.exHarvest, 1));

    public static final RegistryObject<Item> ExHarvestLongPotion = ITEMS.register("ex_harvest_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Gold), NewPotion.PotionName.exHarvest, 2));

    public static final RegistryObject<Item> SplashExHarvestPotion = ITEMS.register("splash_ex_harvest_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Gold), NewPotion.PotionName.exHarvest + "_potion"));

    public static final RegistryObject<Item> SplashExHarvestConPotion = ITEMS.register("splash_ex_harvest_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Gold), "con_" + NewPotion.PotionName.exHarvest + "_potion"));

    public static final RegistryObject<Item> SplashExHarvestLongPotion = ITEMS.register("splash_ex_harvest_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Gold), "long_" + NewPotion.PotionName.exHarvest + "_potion"));

    public static final RegistryObject<Item> WoodHammer = ITEMS.register("wood_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.HuskBold), 0));

    public static final RegistryObject<Item> StoneHammer = ITEMS.register("stone_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.MineBold), 1));

    public static final RegistryObject<Item> IronHammer = ITEMS.register("iron_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.SnowBold), 2));

    public static final RegistryObject<Item> CopperHammer = ITEMS.register("copper_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.CopperBold), 3));

    public static final RegistryObject<Item> GoldHammer = ITEMS.register("gold_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.GoldBold), 4));

    public static final RegistryObject<Item> DiamondHammer = ITEMS.register("diamond_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.IceBold), 5));

    public static final RegistryObject<Item> toNether = ITEMS.register("to_nether",
            () -> new ToNether(new Item.Properties().rarity(CustomStyle.NetherBold)));

    public static final RegistryObject<Item> equipPiece0 = ITEMS.register("equip_piece0",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Gray)));

    public static final RegistryObject<Item> equipPiece1 = ITEMS.register("equip_piece1",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Green)));

    public static final RegistryObject<Item> equipPiece2 = ITEMS.register("equip_piece2",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Aqua)));

    public static final RegistryObject<Item> equipPiece3 = ITEMS.register("equip_piece3",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.LightPurple)));

    public static final RegistryObject<Item> equipPiece4 = ITEMS.register("equip_piece4",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Gold)));

    public static final RegistryObject<Item> equipPiece5 = ITEMS.register("equip_piece5",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Red)));

    public static final RegistryObject<Item> equipPiece6 = ITEMS.register("equip_piece6",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.End)));

    public static final RegistryObject<Item> equipPiece7 = ITEMS.register("equip_piece7",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Moon)));

    public static final RegistryObject<Item> equipPiece8 = ITEMS.register("equip_piece8",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Moon1)));

    public static final RegistryObject<Item> equipPiece9 = ITEMS.register("equip_piece9",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Magma)));

    public static final RegistryObject<Item> equipPiece10 = ITEMS.register("equip_piece10",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.CastleCrystal)));

    public static final RegistryObject<Item> equipPiece11 = ITEMS.register("equip_piece11",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Sakura)));

    public static final RegistryObject<Item> equipPiece12 = ITEMS.register("equip_piece12",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.YSR)));

    public static final RegistryObject<Item> equipPiece13 = ITEMS.register("equip_piece13",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Sky)));

    public static final RegistryObject<Item> notePaper = ITEMS.register("note_paper",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> killPaper = ITEMS.register("kill_paper",
            () -> new KillPaper(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> supplyBoxTier1 = ITEMS.register("supply_box_tier_1",
            () -> new SupplyBox(new Item.Properties().rarity(CustomStyle.Green), new ArrayList<>() {{
                add(new ItemStack(MopUpPaperLoot.get(), 4));
                add(new ItemStack(KillPaperLoot.get(), 8));
                add(new ItemStack(notePaper.get(), 96));
                add(new ItemStack(RevelationBook.get(), 16));
            }}));

    public static final RegistryObject<Item> supplyBoxTier2 = ITEMS.register("supply_box_tier_2",
            () -> new SupplyBox(new Item.Properties().rarity(Rarity.RARE), new ArrayList<>() {{
                add(new ItemStack(MopUpPaperLoot.get(), 8));
                add(new ItemStack(KillPaperLoot.get(), 16));
                add(new ItemStack(notePaper.get(), 128));
                add(new ItemStack(RevelationBook.get(), 32));
                add(new ItemStack(worldSoul5.get(), 80));
            }}));

    public static final RegistryObject<Item> supplyBoxTier3 = ITEMS.register("supply_box_tier_3",
            () -> new SupplyBox(new Item.Properties().rarity(Rarity.EPIC), new ArrayList<>() {{
                add(new ItemStack(MopUpPaperLoot.get(), 16));
                add(new ItemStack(KillPaperLoot.get(), 32));
                add(new ItemStack(notePaper.get(), 160));
                add(new ItemStack(RevelationBook.get(), 64));
                add(new ItemStack(worldSoul5.get(), 200));
            }}));

    public static final RegistryObject<Item> lavenderBracelet = ITEMS.register("lavender_bracelet",
            () -> new LavenderBracelet(new Item.Properties().rarity(CustomStyle.JacarandaBold).stacksTo(1)));

    public static final RegistryObject<Item> windBottle = ITEMS.register("wind_bottle",
            () -> new WindBottle(new Item.Properties().rarity(CustomStyle.WindBold)));

    public static final RegistryObject<Item> plainNecklace = ITEMS.register("plain_necklace",
            () -> new PlainNecklace(new Item.Properties().rarity(CustomStyle.PlainBold)));

    public static final RegistryObject<Item> netherHand = ITEMS.register("nether_hand",
            () -> new NetherHand(new Item.Properties().rarity(CustomStyle.NetherBold)));

    public static final RegistryObject<Item> iceBelt = ITEMS.register("ice_belt",
            () -> new IceBelt(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> simpleTier1Paper = ITEMS.register("simple_tier_1_paper",
            () -> new SimpleTierPaper(new Item.Properties().rarity(CustomStyle.Green), 1));

    public static final RegistryObject<Item> simpleTier2Paper = ITEMS.register("simple_tier_2_paper",
            () -> new SimpleTierPaper(new Item.Properties().rarity(Rarity.RARE), 2));

    public static final RegistryObject<Item> simpleTier3Paper = ITEMS.register("simple_tier_3_paper",
            () -> new SimpleTierPaper(new Item.Properties().rarity(Rarity.EPIC), 3));

    public static final RegistryObject<Item> CastleSoul = ITEMS.register("castle_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Castle)));

    public static final RegistryObject<Item> BeaconBow = ITEMS.register("beacon_bow",
            () -> new BeaconBow(new Item.Properties().rarity(CustomStyle.MagmaItalic), 0));

    public static final RegistryObject<Item> BeaconBow1 = ITEMS.register("beacon_bow1",
            () -> new BeaconBow(new Item.Properties().rarity(CustomStyle.MagmaItalic), 1));

    public static final RegistryObject<Item> BeaconBow2 = ITEMS.register("beacon_bow2",
            () -> new BeaconBow(new Item.Properties().rarity(CustomStyle.MagmaItalic), 2));

    public static final RegistryObject<Item> BeaconBow3 = ITEMS.register("beacon_bow3",
            () -> new BeaconBow(new Item.Properties().rarity(CustomStyle.MagmaItalic), 3));

    public static final RegistryObject<Item> BeaconBracelet = ITEMS.register("beacon_bracelet",
            () -> new BeaconBracelet(new Item.Properties().rarity(CustomStyle.MagmaBold)));

    public static final RegistryObject<Item> BlazeSword = ITEMS.register("blaze_sword",
            () -> new BlazeSword(new Item.Properties().rarity(CustomStyle.MagmaItalic), 0));

    public static final RegistryObject<Item> BlazeSword1 = ITEMS.register("blaze_sword1",
            () -> new BlazeSword(new Item.Properties().rarity(CustomStyle.MagmaItalic), 1));

    public static final RegistryObject<Item> BlazeSword2 = ITEMS.register("blaze_sword2",
            () -> new BlazeSword(new Item.Properties().rarity(CustomStyle.MagmaItalic), 2));

    public static final RegistryObject<Item> BlazeSword3 = ITEMS.register("blaze_sword3",
            () -> new BlazeSword(new Item.Properties().rarity(CustomStyle.MagmaItalic), 3));

    public static final RegistryObject<Item> BlazeBracelet = ITEMS.register("blaze_bracelet",
            () -> new BlazeBracelet(new Item.Properties().rarity(CustomStyle.MagmaBold)));

    public static final RegistryObject<Item> TreeBracelet = ITEMS.register("tree_bracelet",
            () -> new TreeBracelet(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> TreeSceptre = ITEMS.register("tree_sceptre",
            () -> new TreeSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic), 0));

    public static final RegistryObject<Item> TreeSceptre1 = ITEMS.register("tree_sceptre1",
            () -> new TreeSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic), 1));

    public static final RegistryObject<Item> TreeSceptre2 = ITEMS.register("tree_sceptre2",
            () -> new TreeSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic), 2));

    public static final RegistryObject<Item> TreeSceptre3 = ITEMS.register("tree_sceptre3",
            () -> new TreeSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic), 3));

    public static final RegistryObject<Item> BeaconSoul = ITEMS.register("beacon_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Magma)));

    public static final RegistryObject<Item> BeaconRune = ITEMS.register("beacon_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MagmaBold)));

    public static final RegistryObject<Item> BlazeSoul = ITEMS.register("blaze_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Magma)));

    public static final RegistryObject<Item> BlazeRune = ITEMS.register("blaze_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MagmaBold)));

    public static final RegistryObject<Item> TreeSoul = ITEMS.register("tree_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Life)));

    public static final RegistryObject<Item> TreeRune = ITEMS.register("tree_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> MobArmorBlackCastleOneFloorManaHelmet = ITEMS.register("mob_armor_black_castle_one_floor_mana_helmet",
            () -> new MobArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.BlackCastleOneFloorMana));

    public static final RegistryObject<Item> MobArmorBlackCastleOneFloorAttackHelmet = ITEMS.register("mob_armor_black_castle_one_floor_attack_helmet",
            () -> new MobArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.BlackCastleOneFloorAttack));

    public static final RegistryObject<Item> MobArmorBlackCastleOneFloorChest = ITEMS.register("mob_armor_black_castle_one_floor_chest",
            () -> new MobArmor(ItemMaterial.Castle, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorBlackCastleOneFloorLeggings = ITEMS.register("mob_armor_black_castle_one_floor_leggings",
            () -> new MobArmor(ItemMaterial.Castle, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MobArmorBlackCastleOneFloorBoots = ITEMS.register("mob_armor_black_castle_one_floor_boots",
            () -> new MobArmor(ItemMaterial.Castle, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> CastlePiece = ITEMS.register("castle_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Castle)));

    public static final RegistryObject<Item> CastleIngot = ITEMS.register("castle_ingot",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> CastleSwordPiece = ITEMS.register("castle_sword_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> CastleBowPiece = ITEMS.register("castle_bow_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> CastleSceptrePiece = ITEMS.register("castle_sceptre_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> CastleArmorPiece = ITEMS.register("castle_armor_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> CastleLoot = ITEMS.register("castle_loot",
            () -> new CastleLoot(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> CastleSword = ITEMS.register("castle_sword",
            () -> new CastleSword(new Item.Properties().stacksTo(1).rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleBow = ITEMS.register("castle_bow",
            () -> new CastleBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleSceptre = ITEMS.register("castle_sceptre",
            () -> new CastleSceptre(new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleAttackHelmet = ITEMS.register("castle_attack_helmet",
            () -> new CastleAttackArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleAttackChest = ITEMS.register("castle_attack_chest",
            () -> new CastleAttackArmor(ItemMaterial.Castle, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleAttackLeggings = ITEMS.register("castle_attack_leggings",
            () -> new CastleAttackArmor(ItemMaterial.Castle, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleAttackBoots = ITEMS.register("castle_attack_boots",
            () -> new CastleAttackArmor(ItemMaterial.Castle, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleSwiftHelmet = ITEMS.register("castle_swift_helmet",
            () -> new CastleSwiftArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleSwiftChest = ITEMS.register("castle_swift_chest",
            () -> new CastleSwiftArmor(ItemMaterial.Castle, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleSwiftLeggings = ITEMS.register("castle_swift_leggings",
            () -> new CastleSwiftArmor(ItemMaterial.Castle, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleSwiftBoots = ITEMS.register("castle_swift_boots",
            () -> new CastleSwiftArmor(ItemMaterial.Castle, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleManaHelmet = ITEMS.register("castle_mana_helmet",
            () -> new CastleManaArmor(ItemMaterial.Castle, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleManaChest = ITEMS.register("castle_mana_chest",
            () -> new CastleManaArmor(ItemMaterial.Castle, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleManaLeggings = ITEMS.register("castle_mana_leggings",
            () -> new CastleManaArmor(ItemMaterial.Castle, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CastleManaBoots = ITEMS.register("castle_mana_boots",
            () -> new CastleManaArmor(ItemMaterial.Castle, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> skinTemplatePaper = ITEMS.register("skin_template_paper",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> stackUpgradePaper = ITEMS.register("stack_upgrade_paper",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> pickUpgradePaper = ITEMS.register("pick_upgrade_paper",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> INCEPTION_UPGRADE_PAPER = ITEMS.register("inception_upgrade_paper",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> goldCoinLottery = ITEMS.register("gold_coin_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.GoldBold), new ArrayList<>() {{
                add(new NewLotteries.Loot(ModItems.INCEPTION_UPGRADE_PAPER.get().getDefaultInstance(), 0.01));
                add(new NewLotteries.Loot(ModItems.skinTemplatePaper.get().getDefaultInstance(), 0.02));
                add(new NewLotteries.Loot(ModItems.pickUpgradePaper.get().getDefaultInstance(), 0.02));
                add(new NewLotteries.Loot(ModItems.RefiningGold.get().getDefaultInstance(), 0.02));
                add(new NewLotteries.Loot(ModItems.RefiningCopper.get().getDefaultInstance(), 0.03));
                add(new NewLotteries.Loot(ModItems.RefiningIron.get().getDefaultInstance(), 0.03));
                add(new NewLotteries.Loot(ModItems.stackUpgradePaper.get().getDefaultInstance(), 0.1));
                add(new NewLotteries.Loot(ModItems.commonLotteries.get().getDefaultInstance(), 0.1));
                add(new NewLotteries.Loot(ModItems.goldCoin.get().getDefaultInstance(), 0.1));
            }}));

    public static final RegistryObject<Item> GOLDEN_APPLE = ITEMS.register("golden_apple",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> NETHER_IMPRINT = ITEMS.register("nether_imprint",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold)));

    public static final RegistryObject<Item> FOILED_NETHER_IMPRINT = ITEMS.register("foiled_nether_imprint",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold), false, true));

    public static final RegistryObject<Item> GOLDEN_SHEET = ITEMS.register("golden_sheet",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> REFINED_PIECE = ITEMS.register("refined_piece",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(16)));
}
package fun.wraq.common.registry;

import fun.wraq.blocks.blocks.brew.*;
import fun.wraq.blocks.blocks.brew.solidifiedSouls.*;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.UniformItems;
import fun.wraq.events.mob.instance.item.NetherHand;
import fun.wraq.events.mob.instance.item.PlainNecklace;
import fun.wraq.events.mob.instance.item.RevenantGoldenHelmet;
import fun.wraq.items.dev.BarrierSet;
import fun.wraq.items.dev.ClearTree;
import fun.wraq.items.dev.WaterSet;
import fun.wraq.items.dev.equip.ManageBow;
import fun.wraq.items.dev.equip.ManageSceptre;
import fun.wraq.items.dev.equip.ManageSword;
import fun.wraq.items.dev.rail.RailwayPillarSetTool;
import fun.wraq.items.forge.ForgeEnhancePaper;
import fun.wraq.items.forge.ForgeProtect;
import fun.wraq.items.kill.KillPaper;
import fun.wraq.items.lotteries.CommonLotteries;
import fun.wraq.items.lotteries.ExpItem;
import fun.wraq.items.lotteries.FantasyCurio;
import fun.wraq.items.lotteries.UnCommonLotteries;
import fun.wraq.items.m.BackSpawn;
import fun.wraq.items.m.ForNew;
import fun.wraq.items.m.Main0;
import fun.wraq.items.misc.PocketItem;
import fun.wraq.items.mission.Daily;
import fun.wraq.items.mission.IronLove;
import fun.wraq.items.mob.MobArmor;
import fun.wraq.items.money.GoldCoinBag;
import fun.wraq.items.money.UDisk;
import fun.wraq.items.ps.PsBottle;
import fun.wraq.items.skill.ID_Card;
import fun.wraq.items.ticket.TicketToSkyCity;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.plan.SimpleTierPaper;
import fun.wraq.process.func.plan.SupplyBox;
import fun.wraq.process.func.power.impl.*;
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
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.process.system.enhanceForge.Pearl;
import fun.wraq.process.system.estate.EstateKey;
import fun.wraq.process.system.forge.EquipPiece;
import fun.wraq.process.system.forge.ForgeHammer;
import fun.wraq.process.system.forge.ForgeTemplate;
import fun.wraq.process.system.lottery.NewLotteries;
import fun.wraq.process.system.lottery.items.LotteryPrefix;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.process.system.parkour.KillPaperLoot;
import fun.wraq.process.system.parkour.ParkourGloves;
import fun.wraq.process.system.potion.NewPotion;
import fun.wraq.process.system.potion.NewThrowablePotion;
import fun.wraq.process.system.potion.PotionBag;
import fun.wraq.process.system.teamInstance.instances.blackCastle.CastleNecklace;
import fun.wraq.process.system.tp.TpPass;
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
import fun.wraq.series.end.recallBooks.*;
import fun.wraq.series.events.spring2024.DragonPrefix;
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
import fun.wraq.series.instance.series.moon.MoonSoul;
import fun.wraq.series.instance.series.plain.*;
import fun.wraq.series.instance.series.purple.EnhancePurpleIronArmor;
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
import fun.wraq.series.overworld.castle.BeaconBow;
import fun.wraq.series.overworld.castle.BlazeSword;
import fun.wraq.series.overworld.castle.TreeSceptre;
import fun.wraq.series.overworld.chapter1.field.FieldSword;
import fun.wraq.series.overworld.chapter1.forest.*;
import fun.wraq.series.overworld.chapter1.mana.ManaNote;
import fun.wraq.series.overworld.chapter1.mine.*;
import fun.wraq.series.overworld.chapter1.plain.*;
import fun.wraq.series.overworld.chapter1.snow.*;
import fun.wraq.series.overworld.chapter1.volcano.*;
import fun.wraq.series.overworld.chapter1.waterSystem.LakePower;
import fun.wraq.series.overworld.chapter1.waterSystem.LakeRing;
import fun.wraq.series.overworld.chapter1.waterSystem.bossItems.LakeBoss;
import fun.wraq.series.overworld.chapter1.waterSystem.crest.LakeCrest;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.LakeArmor;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.LakeBow;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.LakeSceptre;
import fun.wraq.series.overworld.chapter1.waterSystem.equip.LakeSword;
import fun.wraq.series.overworld.chapter2.blackForest.BlackForestCore;
import fun.wraq.series.overworld.chapter2.blackForest.HuskSword;
import fun.wraq.series.overworld.chapter2.dimension.ToEnd;
import fun.wraq.series.overworld.chapter2.evoker.EvokerSceptre;
import fun.wraq.series.overworld.chapter2.evoker.ManaBalance_Empty;
import fun.wraq.series.overworld.chapter2.evoker.ManaBalance_Filled;
import fun.wraq.series.overworld.chapter2.evoker.ManaCrest;
import fun.wraq.series.overworld.chapter2.kaze.KazeArmorBoots;
import fun.wraq.series.overworld.chapter2.kaze.KazeCore;
import fun.wraq.series.overworld.chapter2.kaze.Sword.KazeSword;
import fun.wraq.series.overworld.chapter2.lavender.LavenderBracelet;
import fun.wraq.series.overworld.chapter2.lightningIsland.LightningArmor;
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
import fun.wraq.series.overworld.chapter2.sky.SkySword;
import fun.wraq.series.overworld.chapter2.spider.SpiderArmorBoots;
import fun.wraq.series.overworld.chapter2.spider.SpiderArmorChest;
import fun.wraq.series.overworld.chapter2.spider.SpiderArmorHelmet;
import fun.wraq.series.overworld.chapter2.spider.SpiderArmorLeggings;
import fun.wraq.series.overworld.chapter7.star.StarArmor;
import fun.wraq.series.overworld.chapter7.star.StarBottle;
import fun.wraq.series.overworld.curios.FancySapphireNecklace;
import fun.wraq.series.overworld.curios.RubyNecklace;
import fun.wraq.series.overworld.curios.SapphireNecklace;
import fun.wraq.series.overworld.forging.ForgingStone0;
import fun.wraq.series.overworld.forging.ForgingStone1;
import fun.wraq.series.overworld.forging.ForgingStone2;
import fun.wraq.series.overworld.kanata.WraqKanata;
import fun.wraq.series.overworld.knife.OriginKnife;
import fun.wraq.series.overworld.sakura.BloodMana.BloodManaArmor;
import fun.wraq.series.overworld.sakura.BloodMana.BloodManaCurios;
import fun.wraq.series.overworld.sakura.BloodMana.ManaKnife;
import fun.wraq.series.overworld.sakura.BloodMana.ManaShield;
import fun.wraq.series.overworld.sakura.Boss2.GoldenAttackOffhand;
import fun.wraq.series.overworld.sakura.Boss2.GoldenBook;
import fun.wraq.series.overworld.sakura.EarthMana.EarthBook;
import fun.wraq.series.overworld.sakura.EarthMana.EarthManaArmor;
import fun.wraq.series.overworld.sakura.EarthMana.EarthManaCurios;
import fun.wraq.series.overworld.sakura.EarthMana.EarthPower;
import fun.wraq.series.overworld.sakura.MineWorker.MinePants;
import fun.wraq.series.overworld.sakura.MineWorker.PurpleIronArmor;
import fun.wraq.series.overworld.sakura.SakuraMob.SakuraArmorHelmet;
import fun.wraq.series.overworld.sakura.SakuraMob.SakuraBow;
import fun.wraq.series.overworld.sakura.SakuraMob.SakuraCore;
import fun.wraq.series.overworld.sakura.SakuraMob.SakuraSword;
import fun.wraq.series.overworld.sakura.Scarecrow.Wheat;
import fun.wraq.series.overworld.sakura.Scarecrow.WheatChest;
import fun.wraq.series.overworld.sakura.Ship.ShipBow;
import fun.wraq.series.overworld.sakura.Ship.ShipSceptre;
import fun.wraq.series.overworld.sakura.Ship.ShipSword;
import fun.wraq.series.overworld.sakura.Slime.SlimeBoots;
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
    public static final RegistryObject<Item> MAIN_0 = ITEMS.register("main0",
            () -> new Main0(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_COIN = ITEMS.register("gold_coin",
            () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
                    components.add(Te.s("等价于: ", CustomStyle.styleOfWorld, stack.getCount() * 144 + "vb", CustomStyle.styleOfGold));
                    super.appendHoverText(stack, level, components, flag);
                }
            });
    public static final RegistryObject<Item> DIAMOND_COIN = ITEMS.register("diamond_coin",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
                    components.add(Te.s("等价于: ", CustomStyle.styleOfWorld,
                            stack.getCount() * 144 * 69 + "vb", CustomStyle.styleOfGold));
                    super.appendHoverText(stack, level, components, flag);
                }
            });
    public static final RegistryObject<Item> SILVER_COIN = ITEMS.register("silver_coin",
            () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
                    components.add(Te.s("等价于: ", CustomStyle.styleOfWorld, stack.getCount() * 12 + "vb", CustomStyle.styleOfGold));
                    super.appendHoverText(stack, level, components, flag);
                }
            });
    public static final RegistryObject<Item> COPPER_COIN = ITEMS.register("copper_coin",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Copper)) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
                    components.add(Te.s("等价于: ", CustomStyle.styleOfWorld, stack.getCount() + "vb", CustomStyle.styleOfGold));
                    super.appendHoverText(stack, level, components, flag);
                }
            });
    public static final RegistryObject<Item> FOR_NEW = ITEMS.register("fornew",
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

    public static final RegistryObject<Item> PLAIN_SOUL = ITEMS.register("plain_souls",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Plain)));
    public static final RegistryObject<Item> PLAIN_RUNE = ITEMS.register("plain_runes",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PlainBold)));
    public static final RegistryObject<Item> PLAIN_SWORD_0 = ITEMS.register("plainsword0",
            () -> new PlainSword(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> PLAIN_SWORD_1 = ITEMS.register("plainsword1",
            () -> new PlainSword(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> PLAIN_SWORD_2 = ITEMS.register("plainsword2",
            () -> new PlainSword(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> PLAIN_SWORD_3 = ITEMS.register("plainsword3",
            () -> new PlainSword(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PLAIN_BOW_0 = ITEMS.register("plainbow0",
            () -> new PlainBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> PLAIN_BOW_1 = ITEMS.register("plainbow1",
            () -> new PlainBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> PLAIN_BOW_2 = ITEMS.register("plainbow2",
            () -> new PlainBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> PLAIN_BOW_3 = ITEMS.register("plainbow3",
            () -> new PlainBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> LIFE_SCEPTRE_0 = ITEMS.register("plainsceptre0",
            () -> new LifeSceptre(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> LIFE_SCEPTRE_1 = ITEMS.register("plainsceptre1",
            () -> new LifeSceptre(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> LIFE_SCEPTRE_2 = ITEMS.register("plainsceptre2",
            () -> new LifeSceptre(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> LIFE_SCEPTRE_3 = ITEMS.register("plainsceptre3",
            () -> new LifeSceptre(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));
    public static final RegistryObject<Item> LIFE_SCEPTRE_X = ITEMS.register("plainsceptre4",
            () -> new LifeSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic), 4));

    public static final RegistryObject<Item> PlainPower = ITEMS.register("plain_power",
            () -> new PlainPower(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> PlainPower1 = ITEMS.register("plain_power1",
            () -> new PlainPower(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> PlainPower2 = ITEMS.register("plain_power2",
            () -> new PlainPower(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> PlainPower3 = ITEMS.register("plain_power3",
            () -> new PlainPower(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PLAIN_MANA_BOOK = ITEMS.register("mananote_plain",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.PlainItalic),
                    0, 20, 0.1, 1, 100, 8, 0.05, 0.1));

    public static final RegistryObject<Item> PLAIN_HELMET = ITEMS.register("plainarmorhelmet",
            () -> new PlainArmor(ModArmorMaterials.PlainMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> PLAIN_CHEST = ITEMS.register("plainarmorchest",
            () -> new PlainArmor(ModArmorMaterials.PlainMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> PLAIN_LEGGINGS = ITEMS.register("plainarmorleggings",
            () -> new PlainArmor(ModArmorMaterials.PlainMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> PLAIN_BOOTS = ITEMS.register("plainarmorboots",
            () -> new PlainArmor(ModArmorMaterials.PlainMaterialBoots, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> FOREST_SOUL = ITEMS.register("forestsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Forest)));
    public static final RegistryObject<Item> FOREST_RUNE = ITEMS.register("forestrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.ForestBold)));

    public static final RegistryObject<Item> FOREST_SWORD_0 = ITEMS.register("forestsword0",
            () -> new ForestSword(new Item.Properties().rarity(CustomStyle.ForestItalic), 0));
    public static final RegistryObject<Item> FOREST_SWORD_1 = ITEMS.register("forestsword1",
            () -> new ForestSword(new Item.Properties().rarity(CustomStyle.ForestItalic), 1));
    public static final RegistryObject<Item> FOREST_SWORD_2 = ITEMS.register("forestsword2",
            () -> new ForestSword(new Item.Properties().rarity(CustomStyle.ForestItalic), 2));
    public static final RegistryObject<Item> FOREST_SWORD_3 = ITEMS.register("forestsword3",
            () -> new ForestSword(new Item.Properties().rarity(CustomStyle.ForestItalic), 3));

    public static final RegistryObject<Item> FOREST_BOW_0 = ITEMS.register("forestbow0",
            () -> new ForestBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.ForestItalic), 0));
    public static final RegistryObject<Item> FOREST_BOW_1 = ITEMS.register("forestbow1",
            () -> new ForestBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.ForestItalic), 1));
    public static final RegistryObject<Item> FOREST_BOW_2 = ITEMS.register("forestbow2",
            () -> new ForestBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.ForestItalic), 2));
    public static final RegistryObject<Item> FOREST_BOW_3 = ITEMS.register("forestbow3",
            () -> new ForestBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.ForestItalic), 3));

    public static final RegistryObject<Item> ForestPower = ITEMS.register("forest_power",
            () -> new ForestPower(new Item.Properties().rarity(CustomStyle.ForestItalic), 0));
    public static final RegistryObject<Item> ForestPower1 = ITEMS.register("forest_power1",
            () -> new ForestPower(new Item.Properties().rarity(CustomStyle.ForestItalic), 1));
    public static final RegistryObject<Item> ForestPower2 = ITEMS.register("forest_power2",
            () -> new ForestPower(new Item.Properties().rarity(CustomStyle.ForestItalic), 2));
    public static final RegistryObject<Item> ForestPower3 = ITEMS.register("forest_power3",
            () -> new ForestPower(new Item.Properties().rarity(CustomStyle.ForestItalic), 3));

    public static final RegistryObject<Item> FOREST_MANA_BOOK = ITEMS.register("mananote_forest",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.ForestItalic), 1,
                    40, 0.12, 1, 125, 10, 0.1, 0.15));

    public static final RegistryObject<Item> FOREST_HELMET = ITEMS.register("forestarmorhelmet",
            () -> new ForestArmor(ModArmorMaterials.Forest, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> FOREST_CHEST = ITEMS.register("forestarmorchest",
            () -> new ForestArmor(ModArmorMaterials.Forest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> FOREST_LEGGINGS = ITEMS.register("forestarmorleggings",
            () -> new ForestArmor(ModArmorMaterials.Forest, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> FOREST_BOOTS = ITEMS.register("forestarmorboots",
            () -> new ForestArmor(ModArmorMaterials.Forest, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> COMPLETE_GEM = ITEMS.register("complete_gem",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC), false, false, List.of(
                    Te.s("主要用于", "装备锻造", CustomStyle.styleOfGold, "与", "宝石制作")
            )));
    public static final RegistryObject<Item> GEM_PIECE = ITEMS.register("gemspiece",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE), false, false, List.of(
                    Te.s("完整水晶", ChatFormatting.LIGHT_PURPLE, "的一块碎片"),
                    Te.s("可以在", "任意村民", CustomStyle.styleOfSunIsland, "处兑换", COMPLETE_GEM.get())
            )));
    public static final RegistryObject<Item> REPUTATION_MEDAL = ITEMS.register("reputation_medal",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.UNCOMMON), false, false, List.of(
                    Te.s("主要用于", "装备锻造", CustomStyle.styleOfGold)
            )));
    public static final RegistryObject<Item> CRUDE_COAL = ITEMS.register("crude_coal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Plain)));
    public static final RegistryObject<Item> HOT_COAL = ITEMS.register("hot_coal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> REFINING_COAL = ITEMS.register("refining_coal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Lake)));
    public static final RegistryObject<Item> BLAZE_COAL = ITEMS.register("blaze_coal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Quartz)));

    public static final RegistryObject<Item> CRUDE_IRON = ITEMS.register("crude_iron",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Plain)));
    public static final RegistryObject<Item> HOT_IRON = ITEMS.register("hot_iron",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> REFINING_IRON = ITEMS.register("refining_iron",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Lake)));

    public static final RegistryObject<Item> CRUDE_COPPER = ITEMS.register("crude_copper",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Plain)));
    public static final RegistryObject<Item> HOT_COPPER = ITEMS.register("hot_copper",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> REFINING_COPPER = ITEMS.register("refining_copper",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Lake)));

    public static final RegistryObject<Item> CRUDE_GOLD = ITEMS.register("crude_gold",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Plain)));
    public static final RegistryObject<Item> BLAZE_GOLD = ITEMS.register("blaze_gold",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Quartz)));
    public static final RegistryObject<Item> REFINING_GOLD = ITEMS.register("refining_gold",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Lake)));

    public static final RegistryObject<Item> NATURAL_CORE = ITEMS.register("natural_core",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Life), false, List.of(
                    Te.s("与", "农耕大师", CustomStyle.styleOfField, "交易获取")
            )));
    public static final RegistryObject<Item> ORE_RUNE = ITEMS.register("ore_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MineBold), false, List.of(
                    Te.s("与", "采矿大师", CustomStyle.styleOfMine, "交易获取")
            )));

    public static final RegistryObject<Item> LAKE_SOUL = ITEMS.register("watersoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Lake)));
    public static final RegistryObject<Item> LAKE_RUNE = ITEMS.register("waterrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LakeBold)));
    public static final RegistryObject<Item> VOLCANO_SOUL = ITEMS.register("volcanosoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> VOLCANO_RUNE = ITEMS.register("volcanorune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.VolcanoBold)));
    public static final RegistryObject<Item> BACK_SPAWN_TICKET = ITEMS.register("backspawn",
            () -> new BackSpawn(new Item.Properties().rarity(Rarity.create("souvenirs", ChatFormatting.BLUE)).stacksTo(1)));

    public static final RegistryObject<Item> LAKE_SWORD_0 = ITEMS.register("lakesword0",
            () -> new LakeSword(new Item.Properties().rarity(CustomStyle.LakeItalic), 0));
    public static final RegistryObject<Item> LAKE_SWORD_1 = ITEMS.register("lakesword1",
            () -> new LakeSword(new Item.Properties().rarity(CustomStyle.LakeItalic), 1));
    public static final RegistryObject<Item> LAKE_SWORD_2 = ITEMS.register("lakesword2",
            () -> new LakeSword(new Item.Properties().rarity(CustomStyle.LakeItalic), 2));
    public static final RegistryObject<Item> LAKE_SWORD_3 = ITEMS.register("lakesword3",
            () -> new LakeSword(new Item.Properties().rarity(CustomStyle.LakeItalic), 3));

    public static final RegistryObject<Item> LAKE_BOW_0 = ITEMS.register("lake_bow0",
            () -> new LakeBow(new Item.Properties().rarity(CustomStyle.WaterItalic), 0));
    public static final RegistryObject<Item> LAKE_BOW_1 = ITEMS.register("lake_bow1",
            () -> new LakeBow(new Item.Properties().rarity(CustomStyle.WaterItalic), 1));
    public static final RegistryObject<Item> LAKE_BOW_2 = ITEMS.register("lake_bow2",
            () -> new LakeBow(new Item.Properties().rarity(CustomStyle.WaterItalic), 2));
    public static final RegistryObject<Item> LAKE_BOW_3 = ITEMS.register("lake_bow3",
            () -> new LakeBow(new Item.Properties().rarity(CustomStyle.WaterItalic), 3));

    public static final RegistryObject<Item> LAKE_SCEPTRE_0 = ITEMS.register("lake_sceptre0",
            () -> new LakeSceptre(new Item.Properties().rarity(CustomStyle.WaterItalic), 0));
    public static final RegistryObject<Item> LAKE_SCEPTRE_1 = ITEMS.register("lake_sceptre1",
            () -> new LakeSceptre(new Item.Properties().rarity(CustomStyle.WaterItalic), 1));
    public static final RegistryObject<Item> LAKE_SCEPTRE_2 = ITEMS.register("lake_sceptre2",
            () -> new LakeSceptre(new Item.Properties().rarity(CustomStyle.WaterItalic), 2));
    public static final RegistryObject<Item> LAKE_SCEPTRE_3 = ITEMS.register("lake_sceptre3",
            () -> new LakeSceptre(new Item.Properties().rarity(CustomStyle.WaterItalic), 3));

    public static final RegistryObject<Item> LAKE_POWER = ITEMS.register("lake_power",
            () -> new LakePower(new Item.Properties().rarity(CustomStyle.WaterItalic), 0));
    public static final RegistryObject<Item> LAKE_POWER_1 = ITEMS.register("lake_power1",
            () -> new LakePower(new Item.Properties().rarity(CustomStyle.WaterItalic), 1));
    public static final RegistryObject<Item> LAKE_POWER_2 = ITEMS.register("lake_power2",
            () -> new LakePower(new Item.Properties().rarity(CustomStyle.WaterItalic), 2));
    public static final RegistryObject<Item> LAKE_POWER_3 = ITEMS.register("lake_power3",
            () -> new LakePower(new Item.Properties().rarity(CustomStyle.WaterItalic), 3));

    public static final RegistryObject<Item> LAKE_MANA_BOOK = ITEMS.register("mananote_lake",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.LakeItalic), 2,
                    60, 0.14, 1, 150, 12, 0.15, 0.2));

    public static final RegistryObject<Item> LAKE_HELMET = ITEMS.register("lakearmorhelmet",
            () -> new LakeArmor(ModArmorMaterials.Lake, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> LAKE_CHEST = ITEMS.register("lakearmorchest",
            () -> new LakeArmor(ModArmorMaterials.LakeMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> LAKE_LEGGINGS = ITEMS.register("lakearmorleggings",
            () -> new LakeArmor(ModArmorMaterials.LakeMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> LAKE_BOOTS = ITEMS.register("lakearmorboots",
            () -> new LakeArmor(ModArmorMaterials.LakeMaterialBoots, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> MINE_SWORD_0 = ITEMS.register("minesword0",
            () -> new MineSword(new Item.Properties().rarity(CustomStyle.MineItalic), 0));
    public static final RegistryObject<Item> MINE_SWORD_1 = ITEMS.register("minesword1",
            () -> new MineSword(new Item.Properties().rarity(CustomStyle.MineItalic), 1));
    public static final RegistryObject<Item> MINE_SWORD_2 = ITEMS.register("minesword2",
            () -> new MineSword(new Item.Properties().rarity(CustomStyle.MineItalic), 2));
    public static final RegistryObject<Item> MINE_SWORD_3 = ITEMS.register("minesword3",
            () -> new MineSword(new Item.Properties().rarity(CustomStyle.MineItalic), 3));

    public static final RegistryObject<Item> MINE_BOW_0 = ITEMS.register("minebow0",
            () -> new MineBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.MineItalic), 0));
    public static final RegistryObject<Item> MINE_BOW_1 = ITEMS.register("minebow1",
            () -> new MineBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.MineItalic), 1));
    public static final RegistryObject<Item> MINE_BOW_2 = ITEMS.register("minebow2",
            () -> new MineBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.MineItalic), 2));
    public static final RegistryObject<Item> MINE_BOW_3 = ITEMS.register("minebow3",
            () -> new MineBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.MineItalic), 3));

    public static final RegistryObject<Item> MINE_POWER = ITEMS.register("mine_power",
            () -> new MinePower(new Item.Properties().rarity(CustomStyle.MineItalic), 0));
    public static final RegistryObject<Item> MINE_POWER_1 = ITEMS.register("mine_power_1",
            () -> new MinePower(new Item.Properties().rarity(CustomStyle.MineItalic), 1));
    public static final RegistryObject<Item> MINE_POWER_2 = ITEMS.register("mine_power_2",
            () -> new MinePower(new Item.Properties().rarity(CustomStyle.MineItalic), 2));
    public static final RegistryObject<Item> MINE_POWER_3 = ITEMS.register("mine_power_3",
            () -> new MinePower(new Item.Properties().rarity(CustomStyle.MineItalic), 3));

    public static final RegistryObject<Item> MINE_SHIELD = ITEMS.register("mine_shield", MineShield::new);

    public static final RegistryObject<Item> MINE_MANA_NOTE = ITEMS.register("mine_mana_note",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.MineItalic), 3,
                    80, 0.17, 2, 175, 14, 0.2, 0.25));

    public static final RegistryObject<Item> MINE_HELMET = ITEMS.register("minearmorhelmet",
            () -> new MineArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> MINE_CHEST = ITEMS.register("minearmorchest",
            () -> new MineArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> MINE_LEGGINGS = ITEMS.register("minearmorleggings",
            () -> new MineArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> MINE_BOOTS = ITEMS.register("minearmorboots",
            () -> new MineArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> VOLCANO_SWORD_0 = ITEMS.register("volcanosword0",
            () -> new VolcanoSword(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 0));
    public static final RegistryObject<Item> VOLCANO_SWORD_1 = ITEMS.register("volcanosword1",
            () -> new VolcanoSword(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 1));
    public static final RegistryObject<Item> VOLCANO_SWORD_2 = ITEMS.register("volcanosword2",
            () -> new VolcanoSword(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 2));
    public static final RegistryObject<Item> VOLCANO_SWORD_3 = ITEMS.register("volcanosword3",
            () -> new VolcanoSword(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 3));

    public static final RegistryObject<Item> VOLCANO_BOW_0 = ITEMS.register("volcanobow0",
            () -> new VolcanoBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.VolcanoItalic), 0));
    public static final RegistryObject<Item> VOLCANO_BOW_1 = ITEMS.register("volcanobow1",
            () -> new VolcanoBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.VolcanoItalic), 1));
    public static final RegistryObject<Item> VOLCANO_BOW_2 = ITEMS.register("volcanobow2",
            () -> new VolcanoBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.VolcanoItalic), 2));
    public static final RegistryObject<Item> VOLCANO_BOW_3 = ITEMS.register("volcanobow3",
            () -> new VolcanoBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.VolcanoItalic), 3));

    public static final RegistryObject<Item> VOLCANO_POWER = ITEMS.register("volcano_power",
            () -> new VolcanoPower(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 0));
    public static final RegistryObject<Item> VOLCANO_POWER_1 = ITEMS.register("volcano_power1",
            () -> new VolcanoPower(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 1));
    public static final RegistryObject<Item> VOLCANO_POWER_2 = ITEMS.register("volcano_power2",
            () -> new VolcanoPower(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 2));
    public static final RegistryObject<Item> VOLCANO_POWER_3 = ITEMS.register("volcano_power3",
            () -> new VolcanoPower(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 3));

    public static final RegistryObject<Item> VOLCANO_MANA_BOOK = ITEMS.register("mananote_volcano",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.VolcanoItalic), 4,
                    100, 0.2, 2, 200, 16, 0.25, 0.3));

    public static final RegistryObject<Item> VOLCANO_HELMET = ITEMS.register("volcanoarmorhelmet",
            () -> new VolcanoArmor(ModArmorMaterials.VolcanoMaterialHelmet, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> VOLCANO_CHEST = ITEMS.register("volcanoarmorchest",
            () -> new VolcanoArmor(ModArmorMaterials.VolcanoMaterialChest, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> VOLCANO_LEGGINGS = ITEMS.register("volcanoarmorleggings",
            () -> new VolcanoArmor(ModArmorMaterials.VolcanoMaterialLeggings, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> VOLCANO_BOOTS = ITEMS.register("volcanoarmorboots",
            () -> new VolcanoArmor(ModArmorMaterials.VolcanoMaterialBoots, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> ClearTree = ITEMS.register("clear_tree",
            () -> new ClearTree(new Item.Properties()));

    public static final RegistryObject<Item> PLAIN_RING = ITEMS.register("plaingems",
            () -> new PlainRing(new Item.Properties().stacksTo(1).rarity(CustomStyle.PlainItalic)));
    public static final RegistryObject<Item> FOREST_RING = ITEMS.register("forestgems",
            () -> new ForestRing(new Item.Properties().stacksTo(1).rarity(CustomStyle.ForestItalic)));
    public static final RegistryObject<Item> LAKE_RING = ITEMS.register("lakegems",
            () -> new LakeRing(new Item.Properties().stacksTo(1).rarity(CustomStyle.LakeItalic)));
    public static final RegistryObject<Item> VOLCANO_RING = ITEMS.register("volcanogems",
            () -> new VolcanoRing(new Item.Properties().stacksTo(1).rarity(CustomStyle.VolcanoItalic)));
    public static final RegistryObject<Item> SKY_CITY_TICKET = ITEMS.register("tickettosky",
            () -> new TicketToSkyCity(new Item.Properties().rarity(Rarity.create("souvenirs", ChatFormatting.BLUE))));

    public static final RegistryObject<Item> MINE_SOUL = ITEMS.register("minesoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Mine)));
    public static final RegistryObject<Item> MINE_SOUL_1 = ITEMS.register("minesoul1",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> MINE_RUNE = ITEMS.register("minerune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MineBold)));
    public static final RegistryObject<Item> FIELD_SOUL = ITEMS.register("fieldsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Field)));
    public static final RegistryObject<Item> FIELD_RUNE = ITEMS.register("fieldrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.FieldBold)));
    public static final RegistryObject<Item> FIELD_SWORD_0 = ITEMS.register("fieldsword0",
            () -> new FieldSword(new Item.Properties().rarity(CustomStyle.FieldItalic), 0));
    public static final RegistryObject<Item> FIELD_SWORD_1 = ITEMS.register("fieldsword1",
            () -> new FieldSword(new Item.Properties().rarity(CustomStyle.FieldItalic), 1));
    public static final RegistryObject<Item> FIELD_SWORD_2 = ITEMS.register("fieldsword2",
            () -> new FieldSword(new Item.Properties().rarity(CustomStyle.FieldItalic), 2));
    public static final RegistryObject<Item> FIELD_SWORD_3 = ITEMS.register("fieldsword3",
            () -> new FieldSword(new Item.Properties().rarity(CustomStyle.FieldItalic), 3));
    public static final RegistryObject<Item> SNOW_SOUL = ITEMS.register("snowsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Snow)));
    public static final RegistryObject<Item> SNOW_RUNE = ITEMS.register("snowrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SnowBold)));

    public static final RegistryObject<Item> SNOW_SWORD_0 = ITEMS.register("snowsword0",
            () -> new SnowSword(new Item.Properties().rarity(CustomStyle.SnowItalic), 0));
    public static final RegistryObject<Item> SNOW_SWORD_1 = ITEMS.register("snowsword1",
            () -> new SnowSword(new Item.Properties().rarity(CustomStyle.SnowItalic), 1));
    public static final RegistryObject<Item> SNOW_SWORD_2 = ITEMS.register("snowsword2",
            () -> new SnowSword(new Item.Properties().rarity(CustomStyle.SnowItalic), 2));
    public static final RegistryObject<Item> SNOW_SWORD_3 = ITEMS.register("snowsword3",
            () -> new SnowSword(new Item.Properties().rarity(CustomStyle.SnowItalic), 3));

    public static final RegistryObject<Item> SNOW_POWER = ITEMS.register("snow_power",
            () -> new SnowPower(new Item.Properties().rarity(CustomStyle.SnowItalic), 0));
    public static final RegistryObject<Item> SNOW_POWER_1 = ITEMS.register("snow_power1",
            () -> new SnowPower(new Item.Properties().rarity(CustomStyle.SnowItalic), 1));
    public static final RegistryObject<Item> SNOW_POWER_2 = ITEMS.register("snow_power2",
            () -> new SnowPower(new Item.Properties().rarity(CustomStyle.SnowItalic), 2));
    public static final RegistryObject<Item> SNOW_POWER_3 = ITEMS.register("snow_power3",
            () -> new SnowPower(new Item.Properties().rarity(CustomStyle.SnowItalic), 3));

    public static final RegistryObject<Item> SNOW_SHIELD = ITEMS.register("snow_shield", SnowShield::new);

    public static final RegistryObject<Item> SKY_BOW = ITEMS.register("skybow",
            () -> new SkyBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.SkyItalic)));
    public static final RegistryObject<Item> SKY_SWORD = ITEMS.register("sky_sword",
            () -> new SkySword(new Item.Properties().rarity(CustomStyle.SkyItalic)));
    public static final RegistryObject<Item> SKY_SOUL = ITEMS.register("skysoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Sky)));
    public static final RegistryObject<Item> SKY_RUNE = ITEMS.register("skyrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> EVOKER_SOUL = ITEMS.register("evokersoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Evoker)));
    public static final RegistryObject<Item> MANA_BUCKET = ITEMS.register("manabucket",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Evoker)));
    public static final RegistryObject<Item> EVOKER_RUNE = ITEMS.register("evokerrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EvokerBold)));
    public static final RegistryObject<Item> MANA_BALANCE_EMPTY = ITEMS.register("manabalance_empty",
            () -> new ManaBalance_Empty(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> MANA_BALANCE_FILLED = ITEMS.register("manabalance_filled",
            () -> new ManaBalance_Filled(new Item.Properties().rarity(CustomStyle.EvokerBold)));

    public static final RegistryObject<Item> EVOKER_SWORD = ITEMS.register("evokersword",
            () -> new EvokerSceptre(new Item.Properties().rarity(CustomStyle.EvokerItalic), 0));
    public static final RegistryObject<Item> EVOKER_SWORD_1 = ITEMS.register("evokersword1",
            () -> new EvokerSceptre(new Item.Properties().rarity(CustomStyle.EvokerItalic), 1));
    public static final RegistryObject<Item> EVOKER_SWORD_2 = ITEMS.register("evokersword2",
            () -> new EvokerSceptre(new Item.Properties().rarity(CustomStyle.EvokerItalic), 2));
    public static final RegistryObject<Item> EVOKER_SWORD_3 = ITEMS.register("evokersword3",
            () -> new EvokerSceptre(new Item.Properties().rarity(CustomStyle.EvokerItalic), 3));

    public static final RegistryObject<Item> LIFE_MANA_POWER_0 = ITEMS.register("life_mana_power_0",
            () -> new LifeManaPower(new Item.Properties().rarity(CustomStyle.LifeBold), 0));
    public static final RegistryObject<Item> LIFE_MANA_POWER_1 = ITEMS.register("life_mana_power_1",
            () -> new LifeManaPower(new Item.Properties().rarity(CustomStyle.LifeBold), 1));
    public static final RegistryObject<Item> LIFE_MANA_POWER_2 = ITEMS.register("life_mana_power_2",
            () -> new LifeManaPower(new Item.Properties().rarity(CustomStyle.LifeBold), 2));
    public static final RegistryObject<Item> LIFE_MANA_POWER_3 = ITEMS.register("life_mana_power_3",
            () -> new LifeManaPower(new Item.Properties().rarity(CustomStyle.LifeBold), 3));

    public static final RegistryObject<Item> OBSI_MANA_POWER_0 = ITEMS.register("obsi_mana_power_0",
            () -> new ObsiManaPower(new Item.Properties().rarity(CustomStyle.EvokerBold), 0));
    public static final RegistryObject<Item> OBSI_MANA_POWER_1 = ITEMS.register("obsi_mana_power_1",
            () -> new ObsiManaPower(new Item.Properties().rarity(CustomStyle.EvokerBold), 1));
    public static final RegistryObject<Item> OBSI_MANA_POWER_2 = ITEMS.register("obsi_mana_power_2",
            () -> new ObsiManaPower(new Item.Properties().rarity(CustomStyle.EvokerBold), 2));
    public static final RegistryObject<Item> OBSI_MANA_POWER_3 = ITEMS.register("obsi_mana_power_3",
            () -> new ObsiManaPower(new Item.Properties().rarity(CustomStyle.EvokerBold), 3));

    public static final RegistryObject<Item> EVOKER_BOOK_0 = ITEMS.register("evokerbook0",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.EvokerItalic), 5,
                    120, 0.22, 2, 225, 17, 0.3, 0.35));
    public static final RegistryObject<Item> EVOKER_BOOK_1 = ITEMS.register("evokerbook1",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.EvokerItalic), 6,
                    140, 0.24, 3, 250, 18, 0.35, 0.4));
    public static final RegistryObject<Item> EVOKER_BOOK_2 = ITEMS.register("evokerbook2",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.EvokerItalic), 7,
                    160, 0.26, 4, 275, 19, 0.4, 0.45));
    public static final RegistryObject<Item> EVOKER_BOOK_3 = ITEMS.register("evokerbook3",
            () -> new ManaNote(new Item.Properties().rarity(CustomStyle.EvokerItalic), 8,
                    180, 0.28, 5, 300, 20, 0.45, 0.5));

    public static final RegistryObject<Item> LIFE_MANA_HELMET = ITEMS.register("lifemanaarmorhelmet",
            () -> new LifeManaArmor(ModArmorMaterials.LifeMana, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LIFE_MANA_CHEST = ITEMS.register("lifemanaarmorchest",
            () -> new LifeManaArmor(ModArmorMaterials.LifeMana, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LIFE_MANA_LEGGINGS = ITEMS.register("lifemanaarmorleggings",
            () -> new LifeManaArmor(ModArmorMaterials.LifeMana, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LIFE_MANA_BOOTS = ITEMS.register("lifemanaarmorboots",
            () -> new LifeManaArmor(ModArmorMaterials.LifeMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.LifeItalic)));

    public static final RegistryObject<Item> LIFE_MANA_HELMET_E = ITEMS.register("life_armor_helmet_e",
            () -> new LifeMana1(ModArmorMaterials.ArmorLifeE, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LIFE_MANA_CHEST_E = ITEMS.register("life_armor_chest_e",
            () -> new LifeMana1(ModArmorMaterials.ArmorLifeE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LIFE_MANA_LEGGINGS_E = ITEMS.register("life_armor_leggings_e",
            () -> new LifeMana1(ModArmorMaterials.ArmorLifeE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LIFE_MANA_BOOTS_E = ITEMS.register("life_armor_boots_e",
            () -> new LifeMana1(ModArmorMaterials.ArmorLifeE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.LifeItalic)));

    public static final RegistryObject<Item> OBSI_MANA_HELMET = ITEMS.register("obsimanaarmorhelmet",
            () -> new ObsiManaArmor(ModArmorMaterials.ObsiMana, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> OBSI_MANA_CHEST = ITEMS.register("obsimanaarmorchest",
            () -> new ObsiManaArmor(ModArmorMaterials.ObsiMana, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> OBSI_MANA_LEGGINGS = ITEMS.register("obsimanaarmorleggings",
            () -> new ObsiManaArmor(ModArmorMaterials.ObsiMana, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> OBIS_MANA_BOOTS = ITEMS.register("obsimanaarmorboots",
            () -> new ObsiManaArmor(ModArmorMaterials.ObsiMana, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> OBSI_MANA_HELMET_E = ITEMS.register("obsi_armor_helmet_e",
            () -> new ObsiMana1(ModArmorMaterials.ArmorObsiE, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> OBSI_MANA_CHEST_E = ITEMS.register("obsi_armor_chest_e",
            () -> new ObsiMana1(ModArmorMaterials.ArmorObsiE, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> OBSI_MANA_LEGGINGS_E = ITEMS.register("obsi_armor_leggings_e",
            () -> new ObsiMana1(ModArmorMaterials.ArmorObsiE, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> OBSI_MANA_BOOTS_E = ITEMS.register("obsi_armor_boots_e",
            () -> new ObsiMana1(ModArmorMaterials.ArmorObsiE, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.EvokerItalic)));

    public static final RegistryObject<Item> PLAIN_MANA = ITEMS.register("plainmana",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> FOREST_MANA = ITEMS.register("forestmana",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> LAKE_MANA = ITEMS.register("lakemana",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> VOLCANO_MANA = ITEMS.register("volcanomana",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> FORGING_STONE_0 = ITEMS.register("forgingstone0",
            () -> new ForgingStone0(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> FORGING_STONE_1 = ITEMS.register("forgingstone1",
            () -> new ForgingStone1(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> FORGING_STONE_2 = ITEMS.register("forgingstone2",
            () -> new ForgingStone2(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> FORGE_TEMPLATE = ITEMS.register("forge_template",
            () -> new ForgeTemplate(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));

    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Nether)));
    public static final RegistryObject<Item> NETHER_QUARTZ = ITEMS.register("netherquartz",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Quartz)));
    public static final RegistryObject<Item> NETHER_SOUL = ITEMS.register("nethersoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Nether)));
    public static final RegistryObject<Item> NETHER_RUNE = ITEMS.register("netherrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold)));
    public static final RegistryObject<Item> NETHER_SWORD_MODEL = ITEMS.register("netherswordmodel",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> WITHER_SKELETON_SOUL = ITEMS.register("witherbone",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Wither)));

    public static final RegistryObject<Item> MANA_SWORD = ITEMS.register("manasword",
            () -> new ManaSword(new Item.Properties().rarity(CustomStyle.EvokerItalic)));

    public static final RegistryObject<Item> WITHER_RUNE = ITEMS.register("wither_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WitherBold), true, true, List.of(
                    Te.s("经下界能量浇筑的凋零残骨。", CustomStyle.styleOfNether),
                    Te.s("凋零与下界能量在这个物体中互相交融，散发出阵阵能量光。", CustomStyle.styleOfNether)
            )));
    public static final RegistryObject<Item> PIGLIN_RUNE = ITEMS.register("piglin_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PiglinBold), true, true, List.of(
                    Te.s("猪灵最喜爱的物件。", CustomStyle.styleOfGold)
            )));
    public static final RegistryObject<Item> NETHER_SKELETON_RUNE = ITEMS.register("nether_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WitherBold), true, true, List.of(
                    Te.s("下界遗骸的精细粉末。", CustomStyle.styleOfNether),
                    Te.s("将下界遗骸粉末融入下界能量进一步研磨而成。", CustomStyle.styleOfNether)
            )));
    public static final RegistryObject<Item> MAGMA_RUNE = ITEMS.register("magma_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MagmaBold), true, true, List.of(
                    Te.s("封装的下界熔岩能量。", CustomStyle.styleOfPower),
                    Te.s("能够放置在背包里已经是个奇迹了。", CustomStyle.styleOfPower)
            )));

    public static final RegistryObject<Item> WITHER_SWORD_0 = ITEMS.register("wither_sword_0",
            () -> new WitherSword(new Item.Properties().rarity(CustomStyle.WitherItalic), 0));
    public static final RegistryObject<Item> WITHER_SWORD_1 = ITEMS.register("wither_sword_1",
            () -> new WitherSword(new Item.Properties().rarity(CustomStyle.WitherItalic), 1));
    public static final RegistryObject<Item> WITHER_SWORD_2 = ITEMS.register("wither_sword_2",
            () -> new WitherSword(new Item.Properties().rarity(CustomStyle.WitherItalic), 2));
    public static final RegistryObject<Item> WITHER_SWORD_3 = ITEMS.register("wither_sword_3",
            () -> new WitherSword(new Item.Properties().rarity(CustomStyle.WitherItalic), 3));

    public static final RegistryObject<Item> PIGLIN_HELMET_0 = ITEMS.register("piglin_helmet_0",
            () -> new PiglinHelmet(ModArmorMaterials.Golden, ArmorItem.Type.HELMET, 0));
    public static final RegistryObject<Item> PIGLIN_HELMET_1 = ITEMS.register("piglin_helmet_1",
            () -> new PiglinHelmet(ModArmorMaterials.Golden, ArmorItem.Type.HELMET, 1));
    public static final RegistryObject<Item> PIGLIN_HELMET_2 = ITEMS.register("piglin_helmet_2",
            () -> new PiglinHelmet(ModArmorMaterials.Golden, ArmorItem.Type.HELMET, 2));
    public static final RegistryObject<Item> PIGLIN_HELMET_3 = ITEMS.register("piglin_helmet_3",
            () -> new PiglinHelmet(ModArmorMaterials.Golden, ArmorItem.Type.HELMET, 3));

    public static final RegistryObject<Item> REVENANT_GOLDEN_HELMET = ITEMS.register("revenant_golden_helmet",
            () -> new RevenantGoldenHelmet(ModArmorMaterials.FANVER_GOLDEN, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.NetherBold)));

    public static final RegistryObject<Item> WITHER_BOW_0 = ITEMS.register("wither_bow_0",
            () -> new WitherBow(new Item.Properties().rarity(CustomStyle.WitherItalic).stacksTo(1), 0));
    public static final RegistryObject<Item> WITHER_BOW_1 = ITEMS.register("wither_bow_1",
            () -> new WitherBow(new Item.Properties().rarity(CustomStyle.WitherItalic).stacksTo(1), 1));
    public static final RegistryObject<Item> WITHER_BOW_2 = ITEMS.register("wither_bow_2",
            () -> new WitherBow(new Item.Properties().rarity(CustomStyle.WitherItalic).stacksTo(1), 2));
    public static final RegistryObject<Item> WITHER_BOW_3 = ITEMS.register("wither_bow_3",
            () -> new WitherBow(new Item.Properties().rarity(CustomStyle.WitherItalic).stacksTo(1), 3));

    public static final RegistryObject<Item> WITHER_BOOK = ITEMS.register("wither_book",
            () -> new WitherBook(new Item.Properties().rarity(CustomStyle.WitherItalic)));

    public static final RegistryObject<Item> MAGMA_SCEPTRE_0 = ITEMS.register("magma_sceptre_0",
            () -> new MagmaSceptre(new Item.Properties().rarity(Rarity.EPIC), 0));
    public static final RegistryObject<Item> MAGMA_SCEPTRE_1 = ITEMS.register("magma_sceptre_1",
            () -> new MagmaSceptre(new Item.Properties().rarity(Rarity.EPIC), 1));
    public static final RegistryObject<Item> MAGMA_SCEPTRE_2 = ITEMS.register("magma_sceptre_2",
            () -> new MagmaSceptre(new Item.Properties().rarity(Rarity.EPIC), 2));
    public static final RegistryObject<Item> MAGMA_SCEPTRE_3 = ITEMS.register("magma_sceptre_3",
            () -> new MagmaSceptre(new Item.Properties().rarity(Rarity.EPIC), 3));

    public static final RegistryObject<Item> PIGLIN_SOUL = ITEMS.register("piglinsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Magma)));
    public static final RegistryObject<Item> NETHER_SKELETON_SOUL = ITEMS.register("netherbonemeal",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Wither)));

    public static final RegistryObject<Item> WITHER_POWER = ITEMS.register("witherbonepower",
            () -> new WitherBonePower(new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> WITHER_ENHANCE_POWER = ITEMS.register("wither_enhance_power",
            () -> new WitherEnhancePower(new Item.Properties().rarity(CustomStyle.WitherBold)));

    public static final RegistryObject<Item> PIGLIN_POWER = ITEMS.register("piglinpower",
            () -> new PiglinPower(new Item.Properties().rarity(CustomStyle.EvokerItalic)));

    public static final RegistryObject<Item> WITHER_BONE_MEAL_POWER = ITEMS.register("witherbonemealpower",
            () -> new WitherBoneMealPower(new Item.Properties().rarity(CustomStyle.EvokerItalic)));
    public static final RegistryObject<Item> WITHER_SKELETON_ENHANCE_POWER = ITEMS.register("wither_skeleton_enhance_power",
            () -> new WitherSkeletonEnhancePower(new Item.Properties().rarity(CustomStyle.WitherBold)));

    public static final RegistryObject<Item> MAGMA_POWER = ITEMS.register("magmapower",
            () -> new MagmaPower(new Item.Properties().rarity(CustomStyle.MagmaItalic), 0));
    public static final RegistryObject<Item> MAGMA_POWER_1 = ITEMS.register("magmapower1",
            () -> new MagmaPower(new Item.Properties().rarity(CustomStyle.MagmaItalic), 1));
    public static final RegistryObject<Item> MAGMA_POWER_2 = ITEMS.register("magmapower2",
            () -> new MagmaPower(new Item.Properties().rarity(CustomStyle.MagmaItalic), 2));
    public static final RegistryObject<Item> MAGMA_POWER_3 = ITEMS.register("magmapower3",
            () -> new MagmaPower(new Item.Properties().rarity(CustomStyle.MagmaItalic), 3));

    public static final RegistryObject<Item> NETHER_POWER = ITEMS.register("netherpower",
            () -> new NetherPower(new Item.Properties().rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> NETHER_BOW = ITEMS.register("netherbow",
            () -> new NetherBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> QUARTZ_SWORD = ITEMS.register("quartzsword",
            () -> new QuartzSword(new Item.Properties().rarity(CustomStyle.QuartzItalic)));
    public static final RegistryObject<Item> QUARTZ_SOUL = ITEMS.register("quartzsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Quartz)));
    public static final RegistryObject<Item> QUARTZ_RUNE = ITEMS.register("quartzrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.QuartzBold)));

    public static final RegistryObject<Item> NETHER_IMPRINT = ITEMS.register("nether_imprint",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold)));
    public static final RegistryObject<Item> FOILED_NETHER_IMPRINT = ITEMS.register("foiled_nether_imprint",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold), true, true, List.of(
                    Te.s("于", "炼造台", CustomStyle.styleOfPower, "制作")
            )));

    public static final RegistryObject<Item> NETHER_POWER_MODEL = ITEMS.register("powermodel",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Evoker)));
    public static final RegistryObject<Item> QUARTZ_SABRE = ITEMS.register("quartzsabre",
            () -> new QuartzSabre(new Item.Properties().rarity(CustomStyle.QuartzItalic)));

    public static final RegistryObject<Item> NETHER_SCEPTRE = ITEMS.register("nether_sceptre",
            () -> new NetherSceptre(new Item.Properties().rarity(CustomStyle.ShipItalic), 0));

    public static final RegistryObject<Item> NETHER_SCEPTRE_E = ITEMS.register("nether_sceptre_e",
            () -> new NetherSceptre(new Item.Properties().rarity(CustomStyle.ShipItalic), 1));

    public static final RegistryObject<Item> NETHER_KNIFE = ITEMS.register("nether_knife",
            () -> new NetherKnife(new Item.Properties().rarity(CustomStyle.NetherBold),
                    Te.m("朱雀之翎", CustomStyle.styleOfRed), 0));

    public static final RegistryObject<Item> NETHER_KNIFE_E = ITEMS.register("nether_knife_e",
            () -> new NetherKnife(new Item.Properties().rarity(CustomStyle.NetherBold),
                    Te.m("朱雀之翎", CustomStyle.styleOfRed), 1));

    public static final RegistryObject<Item> PHOENIX_LEATHER = ITEMS.register("phoenix_leather",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold), false, (player) -> {
                Compute.sendEffectLastTimeByItemId(player, "phoenix_leather", 0, true);
                EnhanceNormalAttackModifier.addModifier(player,
                        new EnhanceNormalAttackModifier("PHOENIX_LEATHER", true, 1, 1, (p, mob) -> {
                            Compute.removeEffectLastTimeByItemId(p, "phoenix_leather");
                        }));
            }));

    public static final RegistryObject<Item> NETHER_SWORD = ITEMS.register("nether_sword",
            () -> new NetherSword(new Item.Properties().rarity(CustomStyle.NetherBold), 0));

    public static final RegistryObject<Item> NETHER_SWORD_E = ITEMS.register("nether_sword_e",
            () -> new NetherSword(new Item.Properties().rarity(CustomStyle.NetherBold), 1));

    public static final RegistryObject<Item> BASALT_ROCK = ITEMS.register("basalt_rock",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold), false, (player) -> {
                player.getCooldowns().removeCooldown(NETHER_SWORD.get());
            }));

    public static final RegistryObject<Item> NETHER_SHIELD = ITEMS.register("nether_shield",
            () -> new NetherShield(new Item.Properties().rarity(CustomStyle.NetherItalic).stacksTo(1)));
    public static final RegistryObject<Item> NETHER_SABRE_MODEL = ITEMS.register("nethersabremodel",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Quartz)));

    public static final RegistryObject<Item> SEA_SOUL = ITEMS.register("seasoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Sea)));
    public static final RegistryObject<Item> SEA_RUNE = ITEMS.register("searune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SeaBold)));

    public static final RegistryObject<Item> LIGHTNING_SOUL = ITEMS.register("lightningsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Lightning)));
    public static final RegistryObject<Item> LIGHTNING_RUNE = ITEMS.register("lightningrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LightningBold)));

    public static final RegistryObject<Item> LIGHTNING_HELMET = ITEMS.register("islandarmorhelmet",
            () -> new LightningArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.LightningItalic), 0));
    public static final RegistryObject<Item> LIGHTNING_CHEST = ITEMS.register("islandarmorchest",
            () -> new LightningArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.LightningItalic), 0));
    public static final RegistryObject<Item> LIGHTNING_LEGGINGS = ITEMS.register("islandarmorleggings",
            () -> new LightningArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.LightningItalic), 0));
    public static final RegistryObject<Item> LIGHTNING_BOOTS = ITEMS.register("islandarmorboots",
            () -> new LightningArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.LightningItalic), 0));

    public static final RegistryObject<Item> SEA_SWORD_0 = ITEMS.register("seasword0",
            () -> new SeaSword(new Item.Properties().rarity(CustomStyle.SeaItalic), 0));
    public static final RegistryObject<Item> SEA_SWORD_1 = ITEMS.register("seasword1",
            () -> new SeaSword(new Item.Properties().rarity(CustomStyle.SeaItalic), 1));
    public static final RegistryObject<Item> SEA_SWORD_2 = ITEMS.register("seasword2",
            () -> new SeaSword(new Item.Properties().rarity(CustomStyle.SeaItalic), 2));
    public static final RegistryObject<Item> SEA_SWORD_3 = ITEMS.register("seasword3",
            () -> new SeaSword(new Item.Properties().rarity(CustomStyle.SeaItalic), 3));

    public static final RegistryObject<Item> SEA_BOW = ITEMS.register("sea_bow",
            () -> new SeaBow(new Item.Properties().rarity(CustomStyle.SeaBold)));

    public static final RegistryObject<Item> SEA_MANA_CORE = ITEMS.register("sea_mana_core",
            () -> new SeaCore(new Item.Properties().rarity(CustomStyle.SeaBold)));

    public static final RegistryObject<Item> HUSK_SWORD_0 = ITEMS.register("blackforestsword0",
            () -> new HuskSword(new Item.Properties().rarity(CustomStyle.HuskItalic), 0));
    public static final RegistryObject<Item> HUSK_SWORD_1 = ITEMS.register("blackforestsword1",
            () -> new HuskSword(new Item.Properties().rarity(CustomStyle.HuskItalic), 1));
    public static final RegistryObject<Item> HUSK_SWORD_2 = ITEMS.register("blackforestsword2",
            () -> new HuskSword(new Item.Properties().rarity(CustomStyle.HuskItalic), 2));
    public static final RegistryObject<Item> HUSK_SWORD_3 = ITEMS.register("blackforestsword3",
            () -> new HuskSword(new Item.Properties().rarity(CustomStyle.HuskItalic), 3));

    public static final RegistryObject<Item> BLACK_FOREST_MANA_CORE = ITEMS.register("blackforest_mana_core",
            () -> new BlackForestCore(new Item.Properties().rarity(CustomStyle.HuskBold)));

    public static final RegistryObject<Item> DAILY_MISSION = ITEMS.register("dailymission",
            () -> new Daily(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1)));

    public static final RegistryObject<Item> SKY_HELMET = ITEMS.register("skyarmorhelmet",
            () -> new SkyArmor(ModArmorMaterials.SkyMaterial, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> SKY_CHEST = ITEMS.register("skyarmorchest",
            () -> new SkyArmor(ModArmorMaterials.SkyMaterial, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> SKY_LEGGINGS = ITEMS.register("skyarmorleggings",
            () -> new SkyArmor(ModArmorMaterials.SkyMaterial, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> SKY_BOOTS = ITEMS.register("skyarmorboots",
            () -> new SkyArmor(ModArmorMaterials.SkyMaterial, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.SkyBold)));

    public static final RegistryObject<Item> GOLD_COIN_BAG = ITEMS.register("goldcoinbag",
            () -> new GoldCoinBag(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PURIFIER = ITEMS.register("purifier",
            () -> new Purifier(new Item.Properties()));
    public static final RegistryObject<Item> PURIFIED_WATER = ITEMS.register("purified_water",
            () -> new PurifiedWater(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> BREWING_NOTE = ITEMS.register("brewingnote",
            () -> new BrewingNote(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> PLAIN_SOLIDIFIED_SOUL = ITEMS.register("plain_solidified_soul",
            () -> new PlainSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Plain)));
    public static final RegistryObject<Item> FOREST_SOLIDIFIED_SOUL = ITEMS.register("forest_solidified_soul",
            () -> new ForestSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Forest)));
    public static final RegistryObject<Item> LAKE_SOLIDIFIED_SOUL = ITEMS.register("lake_solidified_soul",
            () -> new LakeSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Lake)));
    public static final RegistryObject<Item> VOLCANO_SOLIDIFIED_SOUL = ITEMS.register("volcano_solidified_soul",
            () -> new VolcanoSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> SNOW_SOLIDIFIED_SOUL = ITEMS.register("snow_solidified_soul",
            () -> new SnowSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Snow)));
    public static final RegistryObject<Item> SKY_SOLIDIFIED_SOUL = ITEMS.register("sky_solidified_soul",
            () -> new SkySolidifiedSoul(new Item.Properties().rarity(CustomStyle.Sky)));

    public static final RegistryObject<Item> HUSK_SOUL = ITEMS.register("blackforestsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Husk)));
    public static final RegistryObject<Item> HUSK_RUNE = ITEMS.register("blackforestrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.HuskBold)));

    public static final RegistryObject<Item> EVOKER_SOLIDIFIED_SOUL = ITEMS.register("evoker_solidified_soul",
            () -> new EvokerSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Evoker)));
    public static final RegistryObject<Item> NETHER_SOLIDIFIED_SOUL = ITEMS.register("nether_solidified_soul",
            () -> new NetherSolidifiedSoul(new Item.Properties().rarity(CustomStyle.Nether)));
    public static final RegistryObject<Item> SOLIDIFIER = ITEMS.register("solidifier",
            () -> new Solidifier(new Item.Properties()));
    public static final RegistryObject<Item> STABILIZER = ITEMS.register("stabilizer",
            () -> new Stabilizer(new Item.Properties()));
    public static final RegistryObject<Item> CONCENTRATER = ITEMS.register("concentrater",
            () -> new Concentrater(new Item.Properties()));
    public static final RegistryObject<Item> BACKPACK_TICKETS = ITEMS.register("backpackticket",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> SUN_POWER = ITEMS.register("sunpower",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> MAGMA_SOUL = ITEMS.register("nethermagmapower",
            () -> new NetherMagmaPower(new Item.Properties().rarity(CustomStyle.Magma)));

    public static final RegistryObject<Item> KAZE_SWORD_0 = ITEMS.register("kazesword0",
            () -> new KazeSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 0));
    public static final RegistryObject<Item> KAZE_SWORD_1 = ITEMS.register("kazesword1",
            () -> new KazeSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 1));
    public static final RegistryObject<Item> KAZE_SWORD_2 = ITEMS.register("kazesword2",
            () -> new KazeSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 2));
    public static final RegistryObject<Item> KAZE_SWORD_3 = ITEMS.register("kazesword3",
            () -> new KazeSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 3));

    public static final RegistryObject<Item> KAZE_MANA_CORE = ITEMS.register("kaze_mana_core",
            () -> new KazeCore(new Item.Properties().rarity(CustomStyle.KazeBold)));
    public static final RegistryObject<Item> ARMOR_KAZE_CHEST = ITEMS.register("armorkazechest",
            () -> new MobArmor(ModArmorMaterials.ArmorKaze, ArmorItem.Type.CHESTPLATE, 100, 50, 70));
    public static final RegistryObject<Item> ARMOR_KAZE_LEGGINGS = ITEMS.register("armorkazeleggings",
            () -> new MobArmor(ModArmorMaterials.ArmorKaze, ArmorItem.Type.LEGGINGS, 100, 50, 70));
    public static final RegistryObject<Item> ARMOR_KAZE_BOOTS = ITEMS.register("armorkazeboots",
            () -> new MobArmor(ModArmorMaterials.ArmorKaze, ArmorItem.Type.BOOTS, 100, 50, 70));
    public static final RegistryObject<Item> ARMOR_KAZE_RECALL = ITEMS.register("armorkazerecall",
            () -> new MobArmor(ModArmorMaterials.ArmorKaze, ArmorItem.Type.HELMET, 500, 50, 90));

    public static final RegistryObject<Item> KAZE_SOUL = ITEMS.register("kazesoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Kaze)));
    public static final RegistryObject<Item> KAZE_RUNE = ITEMS.register("kazerune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.KazeBold)));

    public static final RegistryObject<Item> LAKE_CORE = ITEMS.register("lakecore",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WaterItalic)));
    public static final RegistryObject<Item> KAZE_BOOTS = ITEMS.register("kazeboots",
            () -> new KazeArmorBoots(ModArmorMaterials.ArmorKaze, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> SLIME_BALL = ITEMS.register("slime_ball",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Life)));
    public static final RegistryObject<Item> BIG_SLIME_BALL = ITEMS.register("big_slime_ball",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> SLIME_BOOTS = ITEMS.register("slime_boots",
            () -> new SlimeBoots(ModArmorMaterials.ArmorKaze, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> SPIDER_SOUL = ITEMS.register("spidersoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SpiderRarity)));
    public static final RegistryObject<Item> SPIDER_RUNE = ITEMS.register("spiderrune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SpiderBold)));

    public static final RegistryObject<Item> SBoots = ITEMS.register("sboots",
            () -> new SpiderArmorBoots(ModArmorMaterials.ArmorS, ArmorItem.Type.BOOTS));
    public static final RegistryObject<Item> SLeggings = ITEMS.register("sleggings",
            () -> new SpiderArmorLeggings(ModArmorMaterials.ArmorS, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> SChest = ITEMS.register("schest",
            () -> new SpiderArmorChest(ModArmorMaterials.ArmorS, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SHelmet = ITEMS.register("shelmet",
            () -> new SpiderArmorHelmet(ModArmorMaterials.ArmorS, ArmorItem.Type.HELMET));

    public static final RegistryObject<Item> VOLCANO_CORE = ITEMS.register("volcanocore",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.VolcanoBold)));

    public static final RegistryObject<Item> IronLove = ITEMS.register("ironlove",
            () -> new IronLove(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> END_PEARL = ITEMS.register("silverfishsoul",
            () -> new ToEnd(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> BLACK_FOREST_RECALL_BOOK = ITEMS.register("blackforestrecall",
            () -> new BlackForestRecallBook(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> EVOKER_RECALL_BOOK = ITEMS.register("evokerrecallbook",
            () -> new EvokerRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> FOREST_RECALL_BOOK = ITEMS.register("forestrecallbook",
            () -> new ForestRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> KAZE_RECALL_BOOK = ITEMS.register("kazerecallbook",
            () -> new KazeRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> LAKE_RECALL_BOOK = ITEMS.register("lakerecallbook",
            () -> new LakeRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> LIGHTNING_RECALL_BOOK = ITEMS.register("lightningrecallbook",
            () -> new LightningRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> NETHER_RECALL_BOOK_1 = ITEMS.register("netherrecallbook1",
            () -> new NetherRecallBook1(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> NETHER_RECALL_BOOK_2 = ITEMS.register("netherrecallbook2",
            () -> new NetherRecallBook2(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> PLAIN_RECALL_BOOK = ITEMS.register("plainrecallbook",
            () -> new PlainRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> SEA_RECALL_BOOK = ITEMS.register("searecallbook",
            () -> new SeaRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> SKY_RECALL_BOOK = ITEMS.register("skyrecallbook",
            () -> new SkyRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> SNOW_RECALL_BOOK = ITEMS.register("snowrecallbook",
            () -> new SnowRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> SPIDER_RECALL_BOOK = ITEMS.register("spiderrecallbook",
            () -> new SpiderRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> VOLCANO_RECALL_BOOK = ITEMS.register("volcanorecallbook",
            () -> new VolcanoRecallBook(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> RECALL_PIECE = ITEMS.register("recallpiece",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> BARRIER_SET = ITEMS.register("barrierset",
            () -> new BarrierSet(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> KAZE_RECALL_SOUL = ITEMS.register("kazerecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Kaze)));
    public static final RegistryObject<Item> INTENSIFIED_KAZE_SOUL = ITEMS.register("intensifiedkazesoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.KazeBold)));
    public static final RegistryObject<Item> KAZE_SWORD_4 = ITEMS.register("kazesword4",
            () -> new KazeSword(new Item.Properties().rarity(CustomStyle.KazeItalic), 4));
    public static final RegistryObject<Item> ARMOR_SPIDER_RECALL = ITEMS.register("armorspiderecall",
            () -> new MobArmor(500, 50, 90));
    public static final RegistryObject<Item> SPIDER_RECALL_SOUL = ITEMS.register("spiderrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SpiderRarity)));
    public static final RegistryObject<Item> MANAGE_SWORD = ITEMS.register("manage_sword",
            () -> new ManageSword(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> MANAGE_BOW = ITEMS.register("manage_bow",
            () -> new ManageBow(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> MANAGE_SCEPTRE = ITEMS.register("manage_sceptre",
            () -> new ManageSceptre(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> INTENSIFIED_SPIDER_SOUL = ITEMS.register("intensifiedspidersoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SpiderBold)));
    public static final RegistryObject<Item> BLACK_FOREST_RECALL_SOUL = ITEMS.register("blackforestrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Husk)));
    public static final RegistryObject<Item> INTENSIFIED_BLACK_FOREST_SOUL = ITEMS.register("intensifiedblackforestsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.HuskBold)));
    public static final RegistryObject<Item> ARMOR_HUSK_RECALL = ITEMS.register("armorhuskrecall",
            () -> new MobArmor(800, 50, 90));
    public static final RegistryObject<Item> HUSK_SWORD_4 = ITEMS.register("blackforestsword4",
            () -> new HuskSword(new Item.Properties().rarity(CustomStyle.HuskItalic), 4));
    public static final RegistryObject<Item> WATER_SET = ITEMS.register("waterset",
            () -> new WaterSet(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> ARMOR_SEA_RECALL = ITEMS.register("armorsearecall",
            () -> new MobArmor(500, 50, 90));
    public static final RegistryObject<Item> SEA_RECALL_SOUL = ITEMS.register("searecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Sea)));
    public static final RegistryObject<Item> INTENSIFIED_SEA_SOUL = ITEMS.register("intensifiedseasoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SeaBold)));
    public static final RegistryObject<Item> SEA_SWORD_4 = ITEMS.register("seasword4",
            () -> new SeaSword(new Item.Properties().rarity(CustomStyle.SeaItalic), 4));

    public static final RegistryObject<Item> IL_HELMET = ITEMS.register("ilarmorhelmet",
            () -> new IntensifiedLightningArmor(ModArmorMaterials.ArmorIL, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.LightningItalic)));
    public static final RegistryObject<Item> IL_CHEST = ITEMS.register("ilarmorchest",
            () -> new IntensifiedLightningArmor(ModArmorMaterials.ArmorIL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.LightningItalic)));
    public static final RegistryObject<Item> IL_LEGGINGS = ITEMS.register("ilarmorleggings",
            () -> new IntensifiedLightningArmor(ModArmorMaterials.ArmorIL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.LightningItalic)));
    public static final RegistryObject<Item> IL_BOOTS = ITEMS.register("ilarmorboots",
            () -> new IntensifiedLightningArmor(ModArmorMaterials.ArmorIL, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.LightningItalic)));

    public static final RegistryObject<Item> LIGHTNING_RECALL_SOUL = ITEMS.register("lightningrecallsoul",
            () -> new LightningRecallSoul(new Item.Properties().rarity(CustomStyle.Lightning)));
    public static final RegistryObject<Item> INTENSIFIED_LIGHTNING_SOUL = ITEMS.register("intensifiedlightningsoul",
            () -> new IntensifiedLightningSoul(new Item.Properties().rarity(CustomStyle.LightningBold)));
    public static final RegistryObject<Item> ARMOR_LIGHTNING_RECALL = ITEMS.register("armorlightningrecall",
            () -> new MobArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> NETHER_RECALL_SOUL = ITEMS.register("netherrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Nether)));
    public static final RegistryObject<Item> INTENSIFIED_NETHER_SOUL = ITEMS.register("intensifiednetherrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.NetherBold)));
    public static final RegistryObject<Item> ARMOR_NETHER_RECALL = ITEMS.register("armornetherrecall",
            () -> new MobArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> MANA_SWORD_1 = ITEMS.register("manasword1",
            () -> new ManaSword1(new Item.Properties().rarity(CustomStyle.MagmaItalic)));
    public static final RegistryObject<Item> SNOW_RECALL_SOUL = ITEMS.register("snowrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Snow)));
    public static final RegistryObject<Item> INTENSIFIED_SNOW_SOUL = ITEMS.register("intensifiedsnowrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SnowBold)));
    public static final RegistryObject<Item> ARMOR_SNOW_RECALL = ITEMS.register("armorsnowrecall",
            () -> new MobArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> SNOW_SWORD_4 = ITEMS.register("snowsword4",
            () -> new SnowSword(new Item.Properties().rarity(CustomStyle.SnowItalic), 4));
    public static final RegistryObject<Item> INTENSIFIED_FOREST_SOUL = ITEMS.register("forestrecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Forest)));
    public static final RegistryObject<Item> INTENSIFIED_FOREST_RECALL_SOUL = ITEMS.register("intensifiedforestsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.ForestBold)));
    public static final RegistryObject<Item> ARMOR_FOREST_RECALL = ITEMS.register("armorforestrecall",
            () -> new MobArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> FOREST_SWORD_4 = ITEMS.register("forestsword4",
            () -> new ForestSword4(new Item.Properties().rarity(CustomStyle.MagmaItalic)));
    public static final RegistryObject<Item> VOLCANO_SWORD_4 = ITEMS.register("volcanosword4",
            () -> new VolcanoSword(new Item.Properties().rarity(CustomStyle.MagmaItalic), 4));
    public static final RegistryObject<Item> INTENSIFIED_VOLCANO_SOUL = ITEMS.register("intensifiedvolcanosoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.VolcanoBold)));
    public static final RegistryObject<Item> ARMOR_VOLCANO_RECALL = ITEMS.register("armorvolcanorecall",
            () -> new MobArmor(ModArmorMaterials.IslandMaterial, ArmorItem.Type.HELMET, 500, 50, 90));
    public static final RegistryObject<Item> VOLCANO_RECALL_SOUL = ITEMS.register("volcanorecallsoul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Volcano)));
    public static final RegistryObject<Item> ID_CARD = ITEMS.register("id_card",
            () -> new ID_Card(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> FOREST_BOSS_SWORD = ITEMS.register("forest_boss_sword",
            () -> new ForestBossSword(fun.wraq.common.registry.ItemTier.VMaterial, 2, 0));
    public static final RegistryObject<Item> VOLCANO_BOSS_SWORD = ITEMS.register("volcano_boss_sword",
            () -> new VolcanoBossSword(fun.wraq.common.registry.ItemTier.VMaterial, 2, 0));

    public static final RegistryObject<Item> SAKURA_PETAL = ITEMS.register("sakura_petal",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.SakuraBold)));
    public static final RegistryObject<Item> SAKURA_PETAL_POCKET = ITEMS.register("sakura_petal_pocket",
            () -> new PocketItem(new Item.Properties().rarity(CustomStyle.SakuraBold), SAKURA_PETAL.get()));
    public static final RegistryObject<Item> SAKURA_SWORD = ITEMS.register("sakura_demon_sword",
            () -> new SakuraSword(new Item.Properties().rarity(CustomStyle.SakuraItalic)));
    public static final RegistryObject<Item> SAKURA_POWER = ITEMS.register("sakura_power",
            () -> new SakuraPower(new Item.Properties().rarity(CustomStyle.SakuraBold)));
    public static final RegistryObject<Item> SAKURA_HELMET = ITEMS.register("sakura_armor_helmet",
            () -> new SakuraArmorHelmet(ModArmorMaterials.Lake, ArmorItem.Type.HELMET));

    public static final RegistryObject<Item> WHEAT = ITEMS.register("wheat",
            () -> new Wheat(new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> WHEAT_CHEST = ITEMS.register("wheat_armor_chest",
            () -> new WheatChest(ModArmorMaterials.LakeMaterialChest, ArmorItem.Type.CHESTPLATE));

    public static final RegistryObject<Item> MINE_PANTS = ITEMS.register("mine_pants",
            () -> new MinePants(ModArmorMaterials.LakeMaterialChest, ArmorItem.Type.LEGGINGS));

    public static final RegistryObject<Item> SNOW_HELMET = ITEMS.register("snowarmorhelmet",
            () -> new SnowArmor(ModArmorMaterials.SkyMaterial, ArmorItem.Type.HELMET));
    public static final RegistryObject<Item> SNOW_CHEST = ITEMS.register("snowarmorchest",
            () -> new SnowArmor(ModArmorMaterials.SkyMaterial, ArmorItem.Type.CHESTPLATE));
    public static final RegistryObject<Item> SNOW_LEGGINGS = ITEMS.register("snowarmorleggings",
            () -> new SnowArmor(ModArmorMaterials.SkyMaterial, ArmorItem.Type.LEGGINGS));
    public static final RegistryObject<Item> SNOW_BOOTS = ITEMS.register("snowarmorboots",
            () -> new SnowArmor(ModArmorMaterials.SkyMaterial, ArmorItem.Type.BOOTS));

    public static final RegistryObject<Item> LAKE_BOSS_SWORD = ITEMS.register("lake_boss_sword",
            () -> new LakeBoss.LakeBossSword(ItemTier.VMaterial, 2, 0));
    public static final RegistryObject<Item> SKY_BOSS_BOW = ITEMS.register("sky_boss_bow",
            () -> new SkyBoss.SkyBossBow(new Item.Properties().rarity(CustomStyle.EntropyItalic).stacksTo(1)));

    public static final RegistryObject<Item> NETHER_HELMET = ITEMS.register("netherarmorhelmet",
            () -> new NetherArmor(ModArmorMaterials.NetherAll, ArmorItem.Type.HELMET,
                    new Item.Properties().rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> NETHER_CHEST = ITEMS.register("netherarmorchest",
            () -> new NetherArmor(ModArmorMaterials.NetherAll, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> NETHER_LEGGINGS = ITEMS.register("netherarmorleggings",
            () -> new NetherArmor(ModArmorMaterials.NetherAll, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> NETHER_BOOTS = ITEMS.register("netherarmorboots",
            () -> new NetherArmor(ModArmorMaterials.NetherAll, ArmorItem.Type.BOOTS,
                    new Item.Properties().rarity(CustomStyle.NetherItalic)));

    public static final RegistryObject<Item> NETHER_MANA_HELMET = ITEMS.register("nether_mana_helmet",
            () -> new NetherManaArmor(ModArmorMaterials.NetherMana, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> NETHER_MANA_CHEST = ITEMS.register("nether_mana_chest",
            () -> new NetherManaArmor(ModArmorMaterials.NetherMana, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> NETHER_MANA_LEGGINGS = ITEMS.register("nether_mana_leggings",
            () -> new NetherManaArmor(ModArmorMaterials.NetherMana, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.NetherItalic)));
    public static final RegistryObject<Item> NETHER_MANA_BOOTS = ITEMS.register("nether_mana_boots",
            () -> new NetherManaArmor(ModArmorMaterials.NetherMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.NetherItalic)));

    public static final RegistryObject<Item> PLAIN_CREST_0 = ITEMS.register("plain_crest_0",
            () -> new PlainCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> PLAIN_CREST_1 = ITEMS.register("plain_crest_1",
            () -> new PlainCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> PLAIN_CREST_2 = ITEMS.register("plain_crest_2",
            () -> new PlainCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> PLAIN_CREST_3 = ITEMS.register("plain_crest_3",
            () -> new PlainCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> PLAIN_CREST_4 = ITEMS.register("plain_crest_4",
            () -> new PlainCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> FOREST_CREST_0 = ITEMS.register("forest_crest_0",
            () -> new ForestCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> FOREST_CREST_1 = ITEMS.register("forest_crest_1",
            () -> new ForestCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> FOREST_CREST_2 = ITEMS.register("forest_crest_2",
            () -> new ForestCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> FOREST_CREST_3 = ITEMS.register("forest_crest_3",
            () -> new ForestCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> FOREST_CREST_4 = ITEMS.register("forest_crest_4",
            () -> new ForestCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> LAKE_CREST_0 = ITEMS.register("lake_crest_0",
            () -> new LakeCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> LAKE_CREST_1 = ITEMS.register("lake_crest_1",
            () -> new LakeCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> LAKE_CREST_2 = ITEMS.register("lake_crest_2",
            () -> new LakeCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> LAKE_CREST_3 = ITEMS.register("lake_crest_3",
            () -> new LakeCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> LAKE_CREST_4 = ITEMS.register("lake_crest_4",
            () -> new LakeCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> VOLCANO_CREST_0 = ITEMS.register("volcano_crest_0",
            () -> new VolcanoCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> VOLCANO_CREST_1 = ITEMS.register("volcano_crest_1",
            () -> new VolcanoCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> VOLCANO_CREST_2 = ITEMS.register("volcano_crest_2",
            () -> new VolcanoCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> VOLCANO_CREST_3 = ITEMS.register("volcano_crest_3",
            () -> new VolcanoCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> VOLCANO_CREST_4 = ITEMS.register("volcano_crest_4",
            () -> new VolcanoCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> MINE_CREST_0 = ITEMS.register("mine_crest_0",
            () -> new MineCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> MINE_CREST_1 = ITEMS.register("mine_crest_1",
            () -> new MineCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> MINE_CREST_2 = ITEMS.register("mine_crest_2",
            () -> new MineCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> MINE_CREST_3 = ITEMS.register("mine_crest_3",
            () -> new MineCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> MINE_CREST_4 = ITEMS.register("mine_crest_4",
            () -> new MineCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> SNOW_CREST_0 = ITEMS.register("snow_crest_0",
            () -> new SnowCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> SNOW_CREST_1 = ITEMS.register("snow_crest_1",
            () -> new SnowCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> SNOW_CREST_2 = ITEMS.register("snow_crest_2",
            () -> new SnowCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> SNOW_CREST_3 = ITEMS.register("snow_crest_3",
            () -> new SnowCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> SNOW_CREST_4 = ITEMS.register("snow_crest_4",
            () -> new SnowCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> SKY_CREST_0 = ITEMS.register("sky_crest_0",
            () -> new SkyCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> SKY_CREST_1 = ITEMS.register("sky_crest_1",
            () -> new SkyCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> SKY_CREST_2 = ITEMS.register("sky_crest_2",
            () -> new SkyCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> SKY_CREST_3 = ITEMS.register("sky_crest_3",
            () -> new SkyCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> SKY_CREST_4 = ITEMS.register("sky_crest_4",
            () -> new SkyCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> MANA_CREST_0 = ITEMS.register("mana_crest_0",
            () -> new ManaCrest(new Item.Properties().rarity(Rarity.COMMON), 0));
    public static final RegistryObject<Item> MANA_CREST_1 = ITEMS.register("mana_crest_1",
            () -> new ManaCrest(new Item.Properties().rarity(Rarity.UNCOMMON), 1));
    public static final RegistryObject<Item> MANA_CREST_2 = ITEMS.register("mana_crest_2",
            () -> new ManaCrest(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> MANA_CREST_3 = ITEMS.register("mana_crest_3",
            () -> new ManaCrest(new Item.Properties().rarity(Rarity.EPIC), 3));
    public static final RegistryObject<Item> MANA_CREST_4 = ITEMS.register("mana_crest_4",
            () -> new ManaCrest(new Item.Properties().rarity(CustomStyle.Red), 4));

    public static final RegistryObject<Item> SNOW_BOSS_CHEST = ITEMS.register("snow_boss_armor_chest",
            () -> new SnowBoss.SnowBossArmorChest(ModArmorMaterials.SkyMaterial, ArmorItem.Type.CHESTPLATE));

    public static final RegistryObject<Item> RAILWAY_PILLAR_SET_TOOL = ITEMS.register("railway_pillar_set_tool",
            () -> new RailwayPillarSetTool(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> Boss2Piece = ITEMS.register("boss_2_piece",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> GOLDEN_SHEET = ITEMS.register("golden_sheet",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GoldBold)));

    public static final RegistryObject<Item> WORLD_SOUL_5 = ITEMS.register("worldsoul5",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WorldBold), false, false, List.of(
                    Te.s("世界本源", CustomStyle.styleOfWorld, "具象的凝聚物"),
                    Te.s("可以在", "本源商人或vp商店", CustomStyle.styleOfWorld),
                    Te.s("兑换一些", "特殊补给包", ChatFormatting.LIGHT_PURPLE)
            )));
    public static final RegistryObject<Item> WORLD_SOUL_4 = ITEMS.register("worldsoul4",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WorldBold)));
    public static final RegistryObject<Item> WORLD_SOUL_3 = ITEMS.register("worldsoul3",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WorldBold), false, false, List.of(
                    Te.s("世界本源", CustomStyle.styleOfWorld, "具象的一整块物质"),
                    Te.s("可用在", "大多数装备", CustomStyle.styleOfWorld, "的锻造中")
            )));
    public static final RegistryObject<Item> WORLD_SOUL_2 = ITEMS.register("worldsoul2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WorldBold), false, false, List.of(
                    Te.s("世界本源", CustomStyle.styleOfWorld, "具象的一团物质"),
                    Te.s("可以在", "本源收集装置", CustomStyle.styleOfWorld, "用64个合成", WORLD_SOUL_3)
            )));
    public static final RegistryObject<Item> WORLD_SOUL_1 = ITEMS.register("worldsoul1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WorldBold), false, false, List.of(
                    Te.s("世界本源", CustomStyle.styleOfWorld, "具象的一小搓物质"),
                    Te.s("可以在", "本源收集装置", CustomStyle.styleOfWorld, "用64个合成", WORLD_SOUL_2)
            )));

    public static final RegistryObject<Item> SOUL_BOW = ITEMS.register("soulbow",
            () -> new SoulBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.WorldBold)));
    public static final RegistryObject<Item> SOUL_SWORD = ITEMS.register("soulsword",
            () -> new SoulSword(new Item.Properties().rarity(CustomStyle.WorldBold)));
    public static final RegistryObject<Item> SOUL_SCEPTRE = ITEMS.register("soulsceptre",
            () -> new SoulSceptre(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> WORLD_SOUL_NOTE = ITEMS.register("world_soul_note",
            () -> new WorldSoulNote(new Item.Properties().rarity(CustomStyle.WorldItalic)));

    public static final RegistryObject<Item> SKILL_RESET = ITEMS.register("skill_reset",
            () -> new SkillReset(new Item.Properties().rarity(CustomStyle.WorldItalic)));

    public static final RegistryObject<Item> PLAIN_BOSS_SCEPTRE = ITEMS.register("plain_boss_sceptre",
            () -> new PlainBossSceptre(new Item.Properties().rarity(CustomStyle.PlainItalic)));

    public static final RegistryObject<Item> PLAIN_ATTACK_RING_0 = ITEMS.register("plain_attack_ring0",
            () -> new PlainAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> PLAIN_ATTACK_RING_1 = ITEMS.register("plain_attack_ring1",
            () -> new PlainAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> PLAIN_ATTACK_RING_2 = ITEMS.register("plain_attack_ring2",
            () -> new PlainAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> PLAIN_ATTACK_RING_3 = ITEMS.register("plain_attack_ring3",
            () -> new PlainAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PLAIN_MANA_RING_0 = ITEMS.register("plain_manaattack_ring0",
            () -> new PlainManaAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> PLAIN_MANA_RING_1 = ITEMS.register("plain_manaattack_ring1",
            () -> new PlainManaAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> PLAIN_MANA_RING_2 = ITEMS.register("plain_manaattack_ring2",
            () -> new PlainManaAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> PLAIN_MANA_RING_3 = ITEMS.register("plain_manaattack_ring3",
            () -> new PlainManaAttackRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PLAIN_HEALTH_RING_0 = ITEMS.register("plain_health_ring0",
            () -> new PlainHealthRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> PLAIN_HEALTH_RING_1 = ITEMS.register("plain_health_ring1",
            () -> new PlainHealthRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> PLAIN_HEALTH_RING_2 = ITEMS.register("plain_health_ring2",
            () -> new PlainHealthRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> PLAIN_HEALTH_RING_3 = ITEMS.register("plain_health_ring3",
            () -> new PlainHealthRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PLAIN_DEFENCE_RING_0 = ITEMS.register("plain_defence_ring0",
            () -> new PlainDefenceRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 0));
    public static final RegistryObject<Item> PLAIN_DEFENCE_RING_1 = ITEMS.register("plain_defence_ring1",
            () -> new PlainDefenceRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 1));
    public static final RegistryObject<Item> PLAIN_DEFENCE_RING_2 = ITEMS.register("plain_defence_ring2",
            () -> new PlainDefenceRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 2));
    public static final RegistryObject<Item> PLAIN_DEFENCE_RING_3 = ITEMS.register("plain_defence_ring3",
            () -> new PlainDefenceRing(new Item.Properties().rarity(CustomStyle.PlainItalic), 3));

    public static final RegistryObject<Item> PLAIN_BOSS_SOUL = ITEMS.register("plain_boss_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PlainBold)));

    public static final RegistryObject<Item> PS_BOTTLE_0 = ITEMS.register("ps_bottle0",
            () -> new PsBottle(new Item.Properties().rarity(Rarity.UNCOMMON), 2));
    public static final RegistryObject<Item> PS_BOTTLE_1 = ITEMS.register("ps_bottle1",
            () -> new PsBottle(new Item.Properties().rarity(Rarity.UNCOMMON), 5));
    public static final RegistryObject<Item> PS_BOTTLE_2 = ITEMS.register("ps_bottle2",
            () -> new PsBottle(new Item.Properties().rarity(Rarity.UNCOMMON), 10));

    public static final RegistryObject<Item> SAKURA_BOW = ITEMS.register("sakura_bow",
            () -> new SakuraBow(new Item.Properties().rarity(CustomStyle.SakuraItalic)));
    public static final RegistryObject<Item> SAKURA_MANA_CORE = ITEMS.register("sakura_mana_core",
            () -> new SakuraCore(new Item.Properties().rarity(CustomStyle.SakuraBold)));

    public static final RegistryObject<Item> PURPLE_IRON_INGOT = ITEMS.register("purpleiron",
            () -> new Item(new Item.Properties().rarity(CustomStyle.PurpleIron)));
    public static final RegistryObject<Item> PURPLE_IRON_PIECE = ITEMS.register("purpleiron_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PurpleIron)));

    public static final RegistryObject<Item> PURPLE_IRON_HELMET = ITEMS.register("purpleiron_armor_helmet",
            () -> new PurpleIronArmor(ModArmorMaterials.PurpleIron,
                    ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.PurpleIronItalic)));
    public static final RegistryObject<Item> PURPLE_IRON_CHEST = ITEMS.register("purpleiron_armor_chest",
            () -> new PurpleIronArmor(ModArmorMaterials.PurpleIron,
                    ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.PurpleIronItalic)));
    public static final RegistryObject<Item> PURPLE_IRON_LEGGINGS = ITEMS.register("purpleiron_armor_leggings",
            () -> new PurpleIronArmor(ModArmorMaterials.PurpleIron,
                    ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.PurpleIronItalic)));
    public static final RegistryObject<Item> PURPLE_IRON_BOOTS = ITEMS.register("purpleiron_armor_boots",
            () -> new PurpleIronArmor(ModArmorMaterials.PurpleIron,
                    ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.PurpleIronItalic)));

    public static final RegistryObject<Item> ENHANCE_PURPLE_IRON_CHEST =
            ITEMS.register("enhance_purple_iron_chest",
                    () -> new EnhancePurpleIronArmor(ModArmorMaterials.FANVER_IRON, ArmorItem.Type.CHESTPLATE,
                            new Item.Properties().rarity(CustomStyle.PurpleIronItalic)));

    public static final RegistryObject<Item> ICE_BOSS_SOUL = ITEMS.register("ice_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> PLAIN_COMPLETE_GEM = ITEMS.register("plain_complete_gem",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PlainBold)));

    public static final RegistryObject<Item> ICE_COMPLETE_GEM = ITEMS.register("ice_complete_gem",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> FANTASY_MEDAL = ITEMS.register("fantasy_medal",
            () -> new FantasyCurio(new Item.Properties().rarity(CustomStyle.FantasyBold), 0));
    public static final RegistryObject<Item> FANTASY_BRACELET = ITEMS.register("fantasy_bracelet",
            () -> new FantasyCurio(new Item.Properties().rarity(CustomStyle.FantasyBold), 0));
    public static final RegistryObject<Item> FANTASY_MEDAL_1 = ITEMS.register("fantasy_medal_1",
            () -> new FantasyCurio(new Item.Properties().rarity(CustomStyle.FantasyBold), 1));
    public static final RegistryObject<Item> FANTASY_BRACELET_1 = ITEMS.register("fantasy_bracelet_1",
            () -> new FantasyCurio(new Item.Properties().rarity(CustomStyle.FantasyBold), 1));
    public static final RegistryObject<Item> FANTASY_MEDAL_2 = ITEMS.register("fantasy_medal_2",
            () -> new FantasyCurio(new Item.Properties().rarity(CustomStyle.FantasyBold), 2));
    public static final RegistryObject<Item> FANTASY_BRACELET_2 = ITEMS.register("fantasy_bracelet_2",
            () -> new FantasyCurio(new Item.Properties().rarity(CustomStyle.FantasyBold), 2));

    public static final RegistryObject<Item> COMMON_LOTTERIES = ITEMS.register("common_lotteries",
            () -> new CommonLotteries(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> UNCOMMON_LOTTERIES = ITEMS.register("uncommon_lotteries",
            () -> new UnCommonLotteries(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> REVELATION_BOOK = ITEMS.register("revelation_book",
            () -> new ExpItem(new Item.Properties().rarity(CustomStyle.Fantasy), 0.05, 0.1));
    public static final RegistryObject<Item> REVELATION_HEART = ITEMS.register("revelation_heart",
            () -> new ExpItem(new Item.Properties().rarity(CustomStyle.Fantasy), 0.9, 1.1));

    public static final RegistryObject<Item> MOB_ARMOR_ICE_HUNTER_HELMET = ITEMS.register("mob_armor_ice_hunter_helmet",
            () -> new MobArmor(ModArmorMaterials.ArmorLeather, ArmorItem.Type.HELMET, StringUtils.MobName.IceHunter));
    public static final RegistryObject<Item> MOB_ARMOR_ICE_HUNTER_CHEST = ITEMS.register("mob_armor_ice_hunter_chest",
            () -> new MobArmor(ModArmorMaterials.ArmorLeather, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.IceHunter));
    public static final RegistryObject<Item> MOB_ARMOR_ICE_HUNTER_LEGGINGS = ITEMS.register("mob_armor_ice_hunter_leggings",
            () -> new MobArmor(ModArmorMaterials.ArmorLeather, ArmorItem.Type.LEGGINGS, StringUtils.MobName.IceHunter));
    public static final RegistryObject<Item> MOB_ARMOR_ICE_HUNTER_BOOTS = ITEMS.register("mob_armor_ice_hunter_boots",
            () -> new MobArmor(ModArmorMaterials.ArmorLeather, ArmorItem.Type.BOOTS, StringUtils.MobName.IceHunter));

    public static final RegistryObject<Item> VALUE = ITEMS.register("value",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ICE_SWORD = ITEMS.register("ice_sword",
            () -> new IceSword(new Item.Properties().rarity(CustomStyle.IceItalic), 0));
    public static final RegistryObject<Item> ICE_SWORD_E = ITEMS.register("ice_sword_e",
            () -> new IceSword(new Item.Properties().rarity(CustomStyle.IceItalic), 1));
    public static final RegistryObject<Item> ICE_BOW = ITEMS.register("ice_bow",
            () -> new IceBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.IceItalic), 0));
    public static final RegistryObject<Item> ICE_BOW_E = ITEMS.register("ice_bow_e",
            () -> new IceBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.IceItalic), 1));
    public static final RegistryObject<Item> ICE_SCEPTRE = ITEMS.register("ice_sceptre",
            () -> new IceSceptre(new Item.Properties().rarity(CustomStyle.IceItalic), 0));
    public static final RegistryObject<Item> ICE_SCEPTRE_E = ITEMS.register("ice_sceptre_e",
            () -> new IceSceptre(new Item.Properties().rarity(CustomStyle.IceItalic), 1));
    public static final RegistryObject<Item> ICE_POWER = ITEMS.register("ice_power",
            () -> new IcePower(new Item.Properties().rarity(CustomStyle.IceBold)));
    public static final RegistryObject<Item> ICE_BOOK = ITEMS.register("ice_book",
            () -> new IceBook(new Item.Properties().rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> U_DISK = ITEMS.register("u_disk",
            () -> new UDisk(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> SHIP_SWORD = ITEMS.register("ship_sword",
            () -> new ShipSword(new Item.Properties().rarity(CustomStyle.ShipItalic)));
    public static final RegistryObject<Item> SHIP_BOW = ITEMS.register("ship_bow",
            () -> new ShipBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.ShipItalic)));
    public static final RegistryObject<Item> SHIP_SCEPTRE = ITEMS.register("ship_sceptre",
            () -> new ShipSceptre(new Item.Properties().rarity(CustomStyle.ShipItalic)));
    public static final RegistryObject<Item> SHIP_PIECE = ITEMS.register("ship_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.ShipBold)));
    public static final RegistryObject<Item> SHIP_PIECE_POCKET = ITEMS.register("ship_piece_pocket",
            () -> new PocketItem(new Item.Properties().rarity(CustomStyle.ShipBold), SHIP_PIECE.get()));

    public static final RegistryObject<Item> ICE_LOOT = ITEMS.register("ice_loot",
            () -> new IceLoot(new Item.Properties().rarity(CustomStyle.Ice)));

    public static final RegistryObject<Item> MINE_HAT = ITEMS.register("mine_hat",
            () -> new MineHat(ModArmorMaterials.SkyMaterial, ArmorItem.Type.HELMET));

    public static final RegistryObject<Item> FORGE_ENHANCE_0 = ITEMS.register("forge_enhance_0",
            () -> new ForgeEnhancePaper(new Item.Properties().rarity(CustomStyle.Plain), 0));
    public static final RegistryObject<Item> FORGE_ENHANCE_1 = ITEMS.register("forge_enhance_1",
            () -> new ForgeEnhancePaper(new Item.Properties().rarity(CustomStyle.Sky), 1));
    public static final RegistryObject<Item> FORGE_ENHANCE_2 = ITEMS.register("forge_enhance_2",
            () -> new ForgeEnhancePaper(new Item.Properties().rarity(CustomStyle.EndBold), 2));
    public static final RegistryObject<Item> FORGE_ENHANCE_3 = ITEMS.register("forge_enhance_3",
            () -> new ForgeEnhancePaper(new Item.Properties().rarity(CustomStyle.RedBold), 3));

    public static final RegistryObject<Item> FORGE_PROTECT = ITEMS.register("forge_protect",
            () -> new ForgeProtect(new Item.Properties().rarity(CustomStyle.EndBold)));

    public static final RegistryObject<Item> ICE_HEART = ITEMS.register("ice_heart",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> BOSS_2_ATTACK_RING_0 = ITEMS.register("boss2_attack_ring0",
            () -> new Boss2AttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 0));
    public static final RegistryObject<Item> BOSS_2_ATTACK_RING_1 = ITEMS.register("boss2_attack_ring1",
            () -> new Boss2AttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 1));
    public static final RegistryObject<Item> BOSS_2_ATTACK_RING_2 = ITEMS.register("boss2_attack_ring2",
            () -> new Boss2AttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 2));
    public static final RegistryObject<Item> BOSS_2_ATTACK_RING_3 = ITEMS.register("boss2_attack_ring3",
            () -> new Boss2AttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 3));

    public static final RegistryObject<Item> BOSS_2_MANA_ATTACK_RING_0 = ITEMS.register("boss2_mana_attack_ring0",
            () -> new Boss2ManaAttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 0));
    public static final RegistryObject<Item> BOSS_2_MANA_ATTACK_RING_1 = ITEMS.register("boss2_mana_attack_ring1",
            () -> new Boss2ManaAttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 1));
    public static final RegistryObject<Item> BOSS_2_MANA_ATTACK_RING_2 = ITEMS.register("boss2_mana_attack_ring2",
            () -> new Boss2ManaAttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 2));
    public static final RegistryObject<Item> BOSS_2_MANA_ATTACK_RING_3 = ITEMS.register("boss2_mana_attack_ring3",
            () -> new Boss2ManaAttackRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 3));

    public static final RegistryObject<Item> BOSS_2_DEFENCE_RING_0 = ITEMS.register("boss2_defence_ring0",
            () -> new Boss2DefenceRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 0));
    public static final RegistryObject<Item> BOSS_2_DEFENCE_RING_1 = ITEMS.register("boss2_defence_ring1",
            () -> new Boss2DefenceRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 1));
    public static final RegistryObject<Item> BOSS_2_DEFENCE_RING_2 = ITEMS.register("boss2_defence_ring2",
            () -> new Boss2DefenceRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 2));
    public static final RegistryObject<Item> BOSS_2_DEFENCE_RING_3 = ITEMS.register("boss2_defence_ring3",
            () -> new Boss2DefenceRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 3));

    public static final RegistryObject<Item> BOSS_2_HEALTH_RING_0 = ITEMS.register("boss2_health_ring0",
            () -> new Boss2HealthRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 0));
    public static final RegistryObject<Item> BOSS_2_HEALTH_RING_1 = ITEMS.register("boss2_health_ring1",
            () -> new Boss2HealthRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 1));
    public static final RegistryObject<Item> BOSS_2_HEALTH_RING_2 = ITEMS.register("boss2_health_ring2",
            () -> new Boss2HealthRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 2));
    public static final RegistryObject<Item> BOSS_2_HEALTH_RING_3 = ITEMS.register("boss2_health_ring3",
            () -> new Boss2HealthRing(new Item.Properties().rarity(CustomStyle.GoldBold).stacksTo(1), 3));

    public static final RegistryObject<Item> RANDOM_EVENT_MEDAL = ITEMS.register("random_event_medal",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> EARTH_MANA_SOUL = ITEMS.register("earth_mana_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EvokerBold)));
    public static final RegistryObject<Item> BLOOD_MANA_SOUL = ITEMS.register("blood_mana_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));
    public static final RegistryObject<Item> EARTH_MANA_RUNE = ITEMS.register("earth_mana_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EvokerBold)));
    public static final RegistryObject<Item> BLOOD_MANA_RUNE = ITEMS.register("blood_mana_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> MOB_ARMOR_EARTH_MANA_CHEST = ITEMS.register("mob_armor_earth_mana_chest",
            () -> new MobArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_EARTH_MANA_LEGGINGS = ITEMS.register("mob_armor_earth_mana_leggings",
            () -> new MobArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_EARTH_MANA_BOOTS = ITEMS.register("mob_armor_earth_mana_boots",
            () -> new MobArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> WOLF_LEATHER = ITEMS.register("wolf_leather",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.ForestBold)));
    public static final RegistryObject<Item> WOLF_LEATHER_POCKET = ITEMS.register("wolf_leather_material",
            () -> new PocketItem(new Item.Properties().rarity(CustomStyle.ForestBold), WOLF_LEATHER.get()));

    public static final RegistryObject<Item> BLOOD_MANA_HELMET = ITEMS.register("blood_mana_helmet",
            () -> new BloodManaArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> BLOOD_MANA_CHEST = ITEMS.register("blood_mana_chest",
            () -> new BloodManaArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> BLOOD_MANA_LEGGINGS = ITEMS.register("blood_mana_leggings",
            () -> new BloodManaArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> BLOOD_MANA_BOOTS = ITEMS.register("blood_mana_boots",
            () -> new BloodManaArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> BLOOD_MANA_POWER = ITEMS.register("blood_mana_power",
            () -> new BloodManaPower(new Item.Properties().rarity(CustomStyle.BloodManaBold)));
    public static final RegistryObject<Item> BLOOD_MANA_CURIOS = ITEMS.register("blood_mana_curios",
            () -> new BloodManaCurios(new Item.Properties().rarity(CustomStyle.BloodManaBold).stacksTo(1), 0));

    public static final RegistryObject<Item> EARTH_MANA_HELMET = ITEMS.register("earth_mana_helmet",
            () -> new EarthManaArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> EARTH_MANA_CHEST = ITEMS.register("earth_mana_chest",
            () -> new EarthManaArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> EARTH_MANA_LEGGINGS = ITEMS.register("earth_mana_leggings",
            () -> new EarthManaArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> EARTH_MANA_BOOTS = ITEMS.register("earth_mana_boots",
            () -> new EarthManaArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> EARTH_MANA_CURIOS = ITEMS.register("earth_mana_curios",
            () -> new EarthManaCurios(new Item.Properties().rarity(CustomStyle.BloodManaBold).stacksTo(1), 0));

    public static final RegistryObject<Item> MANA_SHIELD = ITEMS.register("mana_shield",
            () -> new ManaShield(new Item.Properties().rarity(CustomStyle.BloodManaItalic).stacksTo(1)));
    public static final RegistryObject<Item> MANA_KNIFE = ITEMS.register("mana_knife",
            () -> new ManaKnife(new Item.Properties().rarity(CustomStyle.BloodManaItalic).stacksTo(1)));

    public static final RegistryObject<Item> EARTH_POWER = ITEMS.register("earth_power",
            () -> new EarthPower(new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> EARTH_BOOK = ITEMS.register("earth_book",
            () -> new EarthBook(new Item.Properties().rarity(CustomStyle.JacarandaItalic)));

    public static final RegistryObject<Item> ICE_HELMET = ITEMS.register("ice_armor_helmet",
            () -> new IceArmor(ModArmorMaterials.ArmorIce, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.IceItalic)));
    public static final RegistryObject<Item> ICE_CHEST = ITEMS.register("ice_armor_chest",
            () -> new IceArmor(ModArmorMaterials.ArmorIce, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.IceItalic)));
    public static final RegistryObject<Item> ICE_LEGGINGS = ITEMS.register("ice_armor_leggings",
            () -> new IceArmor(ModArmorMaterials.ArmorIce, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.IceItalic)));
    public static final RegistryObject<Item> ICE_BOOTS = ITEMS.register("ice_armor_boots",
            () -> new IceArmor(ModArmorMaterials.ArmorIce, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> GOLDEN_SHIELD = ITEMS.register("golden_shield",
            () -> new GoldenAttackOffhand(new Item.Properties().rarity(CustomStyle.GoldItalic).stacksTo(1),
                    Component.literal("盾牌").withStyle(CustomStyle.styleOfMine), 0));
    public static final RegistryObject<Item> GOLDEN_KNIFE = ITEMS.register("golden_knife",
            () -> new GoldenAttackOffhand(new Item.Properties().rarity(CustomStyle.GoldItalic).stacksTo(1),
                    Component.literal("匕首").withStyle(ChatFormatting.AQUA), 1));
    public static final RegistryObject<Item> GOLDEN_BOOK = ITEMS.register("golden_book",
            () -> new GoldenBook(new Item.Properties().rarity(CustomStyle.GoldItalic)));

    public static final RegistryObject<Item> MOB_ARMOR_DEVIL_HELMET = ITEMS.register("mob_armor_devil_helmet",
            () -> new MobArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.HELMET, StringUtils.MobName.Devil));

    public static final RegistryObject<Item> DEVIL_BLOOD = ITEMS.register("devil_blood",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> DEVIL_ATTACK_SOUL = ITEMS.register("devil_attack_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));
    public static final RegistryObject<Item> DEVIL_SWIFT_SOUL = ITEMS.register("devil_swift_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));
    public static final RegistryObject<Item> DEVIL_MANA_SOUL = ITEMS.register("devil_mana_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> DEVIL_ATTACK_CHEST = ITEMS.register("devil_attack_chest",
            () -> new DevilAttackArmor(ModArmorMaterials.Devil, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> DEVIL_SWIFT_BOOTS = ITEMS.register("devil_swift_boots",
            () -> new DevilSwiftArmor(ModArmorMaterials.Devil, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> DEVIL_MANA_HELMET = ITEMS.register("devil_mana_helmet",
            () -> new DevilManaArmor(ModArmorMaterials.Devil, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> DEVIL_BLOOD_MANA_CURIOS = ITEMS.register("devil_blood_mana_curios",
            () -> new BloodManaCurios(new Item.Properties().rarity(CustomStyle.BloodManaBold), 1));
    public static final RegistryObject<Item> DEVIL_EARTH_MANA_CURIOS = ITEMS.register("devil_earth_mana_curios",
            () -> new EarthManaCurios(new Item.Properties().rarity(CustomStyle.BloodManaBold), 1));

    public static final RegistryObject<Item> DEVIL_LOOT = ITEMS.register("devil_loot",
            () -> new DevilLoot(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> DRAGON_PREFIX = ITEMS.register("dragon_prefix",
            () -> new DragonPrefix(new Item.Properties().rarity(CustomStyle.SpringBold)));

    public static final RegistryObject<Item> DING_ZHEN_MUSIC_DISC = ITEMS.register("ding_zhen_music_disc",
            () -> new RecordItem(6, ModSounds.IGotSmoke, new Item.Properties().stacksTo(1), 5200));

    public static final RegistryObject<Item> MOB_ARMOR_MOON_ATTACK = ITEMS.register("mob_armor_moon_attack",
            () -> new MobArmor(ModArmorMaterials.Moon, ArmorItem.Type.HELMET, StringUtils.MobName.MoonAttack));
    public static final RegistryObject<Item> MOB_ARMOR_MOON_MANA = ITEMS.register("mob_armor_moon_mana",
            () -> new MobArmor(ModArmorMaterials.Moon, ArmorItem.Type.HELMET, StringUtils.MobName.MoonMana));
    public static final RegistryObject<Item> MOB_ARMOR_MOON_CHEST = ITEMS.register("mob_armor_moon_chest",
            () -> new MobArmor(ModArmorMaterials.Moon, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_MOON_LEGGINGS = ITEMS.register("mob_armor_moon_leggings",
            () -> new MobArmor(ModArmorMaterials.Moon, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_MOON_BOOTS = ITEMS.register("mob_armor_moon_boots",
            () -> new MobArmor(ModArmorMaterials.Moon, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MOON_SOUL = ITEMS.register("moon_soul",
            () -> new MoonSoul(new Item.Properties().rarity(CustomStyle.MoonBold)));
    public static final RegistryObject<Item> MOON_COMPLETE_GEM = ITEMS.register("moon_complete_gem",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MoonBold)));

    public static final RegistryObject<Item> MOON_SHIELD = ITEMS.register("moon_shield", MoonShield::new);
    public static final RegistryObject<Item> MOON_KNIFE = ITEMS.register("moon_knife", MoonKnife::new);
    public static final RegistryObject<Item> MOON_BOOK = ITEMS.register("moon_book", MoonBook::new);

    public static final RegistryObject<Item> MOON_LOOT = ITEMS.register("moon_loot",
            () -> new MoonLoot(new Item.Properties().rarity(CustomStyle.Moon)));

    public static final RegistryObject<Item> DEVIL_SWORD = ITEMS.register("devil_sword",
            () -> new DevilSword(new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> DEVIL_BOW = ITEMS.register("devil_bow",
            () -> new DevilBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> DEVIL_SCEPTRE = ITEMS.register("devil_sceptre",
            () -> new DevilSceptre(new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> INTENSIFIED_DEVIL_BLOOD = ITEMS.register("intensified_devil_blood",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> TABOO_PIECE = ITEMS.register("taboo_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> PURPLE_IRON_CONSTRAINT_STONE = ITEMS.register("purple_iron_constraint_stone",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));

    public static final RegistryObject<Item> CONSTRAINT_TABOO = ITEMS.register("constraint_taboo",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.BloodManaBold)));
    public static final RegistryObject<Item> TABOO_ATTACK_LEGGINGS = ITEMS.register("taboo_attack_leggings",
            () -> new TabooAttackArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> TABOO_SWIFT_HELMET = ITEMS.register("taboo_swift_helmet",
            () -> new TabooSwiftArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));
    public static final RegistryObject<Item> TABOO_MANA_BOOTS = ITEMS.register("taboo_mana_boots",
            () -> new TabooManaArmor(ModArmorMaterials.BloodMana, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.BloodManaItalic)));

    public static final RegistryObject<Item> MOON_LEGGINGS = ITEMS.register("moon_leggings",
            () -> new MoonArmor(ModArmorMaterials.Moon, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.Moon1Italic)));
    public static final RegistryObject<Item> MOON_HELMET = ITEMS.register("moon_helmet",
            () -> new MoonArmor(ModArmorMaterials.Moon, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.Moon1Italic)));
    public static final RegistryObject<Item> MOON_SWORD = ITEMS.register("moon_sword",
            () -> new MoonSword(new Item.Properties().rarity(CustomStyle.Moon1Italic), 0.1, 0));
    public static final RegistryObject<Item> MOON_SWORD_E = ITEMS.register("moon_sword_e",
            () -> new MoonSword(new Item.Properties().rarity(CustomStyle.Moon1Italic), 0.2, 1));
    public static final RegistryObject<Item> MOON_BOW = ITEMS.register("moon_bow",
            () -> new MoonBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.Moon1Italic), 0.1, 0));
    public static final RegistryObject<Item> MOON_BOW_E = ITEMS.register("moon_bow_e",
            () -> new MoonBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.Moon1Italic), 0.2, 1));
    public static final RegistryObject<Item> MOON_SCEPTRE = ITEMS.register("moon_sceptre",
            () -> new MoonSceptre(new Item.Properties().rarity(CustomStyle.Moon1Italic), 0.2, 0));
    public static final RegistryObject<Item> MOON_SCEPTRE_E = ITEMS.register("moon_sceptre_e",
            () -> new MoonSceptre(new Item.Properties().rarity(CustomStyle.Moon1Italic), 0.4, 1));
    public static final RegistryObject<Item> MOON_CURIOS = ITEMS.register("moon_curios",
            () -> new MoonCurios(new Item.Properties().rarity(CustomStyle.Moon1Bold)));
    public static final RegistryObject<Item> MOON_BELT = ITEMS.register("moon_belt",
            () -> new MoonBelt(new Item.Properties().rarity(CustomStyle.Moon1Bold)));

    public static final RegistryObject<Item> PARKOUR_MEDAL = ITEMS.register("parkour_medal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> KILL_PAPER_LOOT = ITEMS.register("kill_paper_loot",
            () -> new KillPaperLoot(new Item.Properties().rarity(CustomStyle.LifeBold), false));
    public static final RegistryObject<Item> KILL_PAPER_LOOT_L = ITEMS.register("kill_paper_loot_l",
            () -> new KillPaperLoot(new Item.Properties().rarity(CustomStyle.LifeBold), true));

    public static final RegistryObject<Item> PARKOUR_GLOVES = ITEMS.register("parkour_gloves",
            () -> new ParkourGloves(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> END_POWER = ITEMS.register("end_power",
            () -> new EndPower(new Item.Properties().rarity(CustomStyle.EndBold), 0));
    public static final RegistryObject<Item> END_POWER_1 = ITEMS.register("end_power1",
            () -> new EndPower(new Item.Properties().rarity(CustomStyle.EndBold), 1));
    public static final RegistryObject<Item> END_POWER_2 = ITEMS.register("end_power2",
            () -> new EndPower(new Item.Properties().rarity(CustomStyle.EndBold), 2));
    public static final RegistryObject<Item> END_POWER_3 = ITEMS.register("end_power3",
            () -> new EndPower(new Item.Properties().rarity(CustomStyle.EndBold), 3));

    public static final RegistryObject<Item> CASTLE_NECKLACE = ITEMS.register("castle_necklace",
            () -> new CastleNecklace(new Item.Properties().rarity(CustomStyle.CastleBold).stacksTo(1)));

    public static final RegistryObject<Item> MOB_ARMOR_CASTLE_KNIGHT_HELMET = ITEMS.register("mob_armor_castle_knight_helmet",
            () -> new MobArmor(ModArmorMaterials.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.CastleKnight));

    public static final RegistryObject<Item> CASTLE_CRYSTAL = ITEMS.register("castle_crystal",
            () -> new Item(new Item.Properties().rarity(CustomStyle.CastleCrystalBold)));
    public static final RegistryObject<Item> CASTLE_CRYSTAL_NORTH = ITEMS.register("castle_crystal_north",
            () -> new Item(new Item.Properties().rarity(CustomStyle.CastleCrystalBold)));
    public static final RegistryObject<Item> CASTLE_CRYSTAL_SOUTH = ITEMS.register("castle_crystal_south",
            () -> new Item(new Item.Properties().rarity(CustomStyle.CastleCrystalBold)));

    public static final RegistryObject<Item> RUBY_NECKLACE = ITEMS.register("ruby_necklace",
            () -> new RubyNecklace(new Item.Properties().rarity(CustomStyle.RedBold), 0));
    public static final RegistryObject<Item> RUBY_NECKLACE_1 = ITEMS.register("ruby_necklace1",
            () -> new RubyNecklace(new Item.Properties().rarity(CustomStyle.RedBold), 1));
    public static final RegistryObject<Item> RUBY_NECKLACE_2 = ITEMS.register("ruby_necklace2",
            () -> new RubyNecklace(new Item.Properties().rarity(CustomStyle.RedBold), 2));
    public static final RegistryObject<Item> RUBY_NECKLACE_3 = ITEMS.register("ruby_necklace3",
            () -> new RubyNecklace(new Item.Properties().rarity(CustomStyle.RedBold), 3));

    public static final RegistryObject<Item> SAPPHIRE_NECKLACE = ITEMS.register("sapphire_necklace",
            () -> new SapphireNecklace(new Item.Properties().rarity(CustomStyle.LakeBold), 0));
    public static final RegistryObject<Item> SAPPHIRE_NECKLACE_1 = ITEMS.register("sapphire_necklace1",
            () -> new SapphireNecklace(new Item.Properties().rarity(CustomStyle.LakeBold), 1));
    public static final RegistryObject<Item> SAPPHIRE_NECKLACE_2 = ITEMS.register("sapphire_necklace2",
            () -> new SapphireNecklace(new Item.Properties().rarity(CustomStyle.LakeBold), 2));
    public static final RegistryObject<Item> SAPPHIRE_NECKLACE_3 = ITEMS.register("sapphire_necklace3",
            () -> new SapphireNecklace(new Item.Properties().rarity(CustomStyle.LakeBold), 3));

    public static final RegistryObject<Item> FANCY_SAPPHIRE_NECKLACE = ITEMS.register("fancy_sapphire_necklace",
            () -> new FancySapphireNecklace(new Item.Properties().rarity(CustomStyle.SakuraBold), 0));
    public static final RegistryObject<Item> FANCY_SAPPHIRE_NECKLACE_1 = ITEMS.register("fancy_sapphire_necklace1",
            () -> new FancySapphireNecklace(new Item.Properties().rarity(CustomStyle.SakuraBold), 1));
    public static final RegistryObject<Item> FANCY_SAPPHIRE_NECKLACE_2 = ITEMS.register("fancy_sapphire_necklace2",
            () -> new FancySapphireNecklace(new Item.Properties().rarity(CustomStyle.SakuraBold), 2));
    public static final RegistryObject<Item> FANCY_SAPPHIRE_NECKLACE_3 = ITEMS.register("fancy_sapphire_necklace3",
            () -> new FancySapphireNecklace(new Item.Properties().rarity(CustomStyle.SakuraBold), 3));

    public static final RegistryObject<Item> CASTLE_CURIOS_POWDER = ITEMS.register("castle_curios_powder",
            () -> new Item(new Item.Properties().rarity(CustomStyle.CastleCrystalBold)));

    public static final RegistryObject<Item> PURPLE_IRON_WEAPON_PIECE = ITEMS.register("purple_iron_weapon_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PurpleIronItalic)));

    public static final RegistryObject<Item> PURPLE_IRON_BOW = ITEMS.register("purple_iron_bow",
            () -> new PurpleIronBow(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 0));
    public static final RegistryObject<Item> PURPLE_IRON_BOW_1 = ITEMS.register("purple_iron_bow1",
            () -> new PurpleIronBow(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 1));
    public static final RegistryObject<Item> PURPLE_IRON_BOW_2 = ITEMS.register("purple_iron_bow2",
            () -> new PurpleIronBow(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 2));
    public static final RegistryObject<Item> PURPLE_IRON_BOW_3 = ITEMS.register("purple_iron_bow3",
            () -> new PurpleIronBow(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 3));

    public static final RegistryObject<Item> PURPLE_IRON_SWORD = ITEMS.register("purple_iron_sword",
            () -> new PurpleIronSword(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 0));
    public static final RegistryObject<Item> PURPLE_IRON_SWORD_1 = ITEMS.register("purple_iron_sword1",
            () -> new PurpleIronSword(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 1));
    public static final RegistryObject<Item> PURPLE_IRON_SWORD_2 = ITEMS.register("purple_iron_sword2",
            () -> new PurpleIronSword(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 2));
    public static final RegistryObject<Item> PURPLE_IRON_SWORD_3 = ITEMS.register("purple_iron_sword3",
            () -> new PurpleIronSword(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 3));

    public static final RegistryObject<Item> PURPLE_IRON_SCEPTRE = ITEMS.register("purple_iron_sceptre",
            () -> new PurpleIronSceptre(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 0));
    public static final RegistryObject<Item> PURPLE_IRON_SCEPTRE_1 = ITEMS.register("purple_iron_sceptre1",
            () -> new PurpleIronSceptre(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 1));
    public static final RegistryObject<Item> PURPLE_IRON_SCEPTRE_2 = ITEMS.register("purple_iron_sceptre2",
            () -> new PurpleIronSceptre(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 2));
    public static final RegistryObject<Item> PURPLE_IRON_SCEPTRE_3 = ITEMS.register("purple_iron_sceptre3",
            () -> new PurpleIronSceptre(new Item.Properties().rarity(CustomStyle.PurpleIronItalic), 3));

    public static final RegistryObject<Item> PURPLE_IRON_BUD_1 = ITEMS.register("purple_iron_bud1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PurpleIronBold), true, true));
    public static final RegistryObject<Item> PURPLE_IRON_BUD_2 = ITEMS.register("purple_iron_bud2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PurpleIronBold), true, true));
    public static final RegistryObject<Item> PURPLE_IRON_BUD_3 = ITEMS.register("purple_iron_bud3",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PurpleIronBold), true, true));

    public static final RegistryObject<Item> LOTTERY_STAR = ITEMS.register("lottery_star",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC), true, true));
    public static final RegistryObject<Item> LOTTERY_PREFIX = ITEMS.register("lottery_prefix",
            () -> new LotteryPrefix(new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> STAR_BOTTLE = ITEMS.register("star_bottle",
            () -> new StarBottle(new Item.Properties().rarity(CustomStyle.Moon1Bold)));
    public static final RegistryObject<Item> STAR_SOUL = ITEMS.register("star_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Moon1), true, true));
    public static final RegistryObject<Item> STAR_RUNE = ITEMS.register("star_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Moon1Bold), true, true));
    public static final RegistryObject<Item> STAR_STAR = ITEMS.register("star_star",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Moon1Italic), true, true));

    public static final RegistryObject<Item> STAR_LEGGINGS = ITEMS.register("star_leggings",
            () -> new StarArmor(ModArmorMaterials.Moon, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.Moon1Italic)));
    public static final RegistryObject<Item> STAR_HELMET = ITEMS.register("star_helmet",
            () -> new StarArmor(ModArmorMaterials.Moon, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.Moon1Italic)));

    public static final RegistryObject<Item> LIFE_FRUIT = ITEMS.register("life_fruit",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Life)));

    public static final RegistryObject<Item> LIFE_ELEMENT_PIECE_0 = ITEMS.register("life_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Life)));
    public static final RegistryObject<Item> LIFE_ELEMENT_PIECE_1 = ITEMS.register("life_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> LIFE_ELEMENT_PIECE_2 = ITEMS.register("life_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LifeItalic)));

    public static final RegistryObject<Item> WATER_ELEMENT_PIECE_0 = ITEMS.register("water_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Water)));
    public static final RegistryObject<Item> WATER_ELEMENT_PIECE_1 = ITEMS.register("water_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WaterBold)));
    public static final RegistryObject<Item> WATER_ELEMENT_PIECE_2 = ITEMS.register("water_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WaterItalic)));

    public static final RegistryObject<Item> FIRE_ELEMENT_PIECE_0 = ITEMS.register("fire_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Magma)));
    public static final RegistryObject<Item> FIRE_ELEMENT_PIECE_1 = ITEMS.register("fire_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.FireBold)));
    public static final RegistryObject<Item> FIRE_ELEMENT_PIECE_2 = ITEMS.register("fire_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.FireItalic)));

    public static final RegistryObject<Item> STONE_ELEMENT_PIECE_0 = ITEMS.register("stone_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Stone)));
    public static final RegistryObject<Item> STONE_ELEMENT_PIECE_1 = ITEMS.register("stone_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.StoneBold)));
    public static final RegistryObject<Item> STONE_ELEMENT_PIECE_2 = ITEMS.register("stone_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.StoneItalic)));

    public static final RegistryObject<Item> ICE_ELEMENT_PIECE_0 = ITEMS.register("ice_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Ice)));
    public static final RegistryObject<Item> ICE_ELEMENT_PIECE_1 = ITEMS.register("ice_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceBold)));
    public static final RegistryObject<Item> ICE_ELEMENT_PIECE_2 = ITEMS.register("ice_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.IceItalic)));

    public static final RegistryObject<Item> LIGHTNING_ELEMENT_PIECE_0 = ITEMS.register("lightning_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Lightning)));
    public static final RegistryObject<Item> LIGHTNING_ELEMENT_PIECE_1 = ITEMS.register("lightning_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LightningBold)));
    public static final RegistryObject<Item> LIGHTNING_ELEMENT_PIECE_2 = ITEMS.register("lightning_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LightningItalic)));

    public static final RegistryObject<Item> WIND_ELEMENT_PIECE_0 = ITEMS.register("wind_element_piece0",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Wind)));
    public static final RegistryObject<Item> WIND_ELEMENT_PIECE_1 = ITEMS.register("wind_element_piece1",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WindBold)));
    public static final RegistryObject<Item> WIND_ELEMENT_PIECE_2 = ITEMS.register("wind_element_piece2",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.WindItalic)));

    public static final RegistryObject<Item> LIFE_CRYSTAL_0 = ITEMS.register("life_crystal0",
            () -> new LifeCrystal(new Item.Properties().rarity(CustomStyle.LifeBold), 0));
    public static final RegistryObject<Item> LIFE_CRYSTAL_1 = ITEMS.register("life_crystal1",
            () -> new LifeCrystal(new Item.Properties().rarity(CustomStyle.LifeBold), 1));
    public static final RegistryObject<Item> LIFE_CRYSTAL_2 = ITEMS.register("life_crystal2",
            () -> new LifeCrystal(new Item.Properties().rarity(CustomStyle.LifeBold), 2));
    public static final RegistryObject<Item> LIFE_CRYSTAL_3 = ITEMS.register("life_crystal3",
            () -> new LifeCrystal(new Item.Properties().rarity(CustomStyle.LifeBold), 3));

    public static final RegistryObject<Item> WATER_CRYSTAL_0 = ITEMS.register("water_crystal0",
            () -> new WaterCrystal(new Item.Properties().rarity(CustomStyle.WaterBold), 0));
    public static final RegistryObject<Item> WATER_CRYSTAL_1 = ITEMS.register("water_crystal1",
            () -> new WaterCrystal(new Item.Properties().rarity(CustomStyle.WaterBold), 1));
    public static final RegistryObject<Item> WATER_CRYSTAL_2 = ITEMS.register("water_crystal2",
            () -> new WaterCrystal(new Item.Properties().rarity(CustomStyle.WaterBold), 2));
    public static final RegistryObject<Item> WATER_CRYSTAL_3 = ITEMS.register("water_crystal3",
            () -> new WaterCrystal(new Item.Properties().rarity(CustomStyle.WaterBold), 3));

    public static final RegistryObject<Item> FIRE_CRYSTAL_0 = ITEMS.register("fire_crystal0",
            () -> new FireCrystal(new Item.Properties().rarity(CustomStyle.FireBold), 0));
    public static final RegistryObject<Item> FIRE_CRYSTAL_1 = ITEMS.register("fire_crystal1",
            () -> new FireCrystal(new Item.Properties().rarity(CustomStyle.FireBold), 1));
    public static final RegistryObject<Item> FIRE_CRYSTAL_2 = ITEMS.register("fire_crystal2",
            () -> new FireCrystal(new Item.Properties().rarity(CustomStyle.FireBold), 2));
    public static final RegistryObject<Item> FIRE_CRYSTAL_3 = ITEMS.register("fire_crystal3",
            () -> new FireCrystal(new Item.Properties().rarity(CustomStyle.FireBold), 3));

    public static final RegistryObject<Item> STONE_CRYSTAL_0 = ITEMS.register("stone_crystal0",
            () -> new StoneCrystal(new Item.Properties().rarity(CustomStyle.StoneBold), 0));
    public static final RegistryObject<Item> STONE_CRYSTAL_1 = ITEMS.register("stone_crystal1",
            () -> new StoneCrystal(new Item.Properties().rarity(CustomStyle.StoneBold), 1));
    public static final RegistryObject<Item> STONE_CRYSTAL_2 = ITEMS.register("stone_crystal2",
            () -> new StoneCrystal(new Item.Properties().rarity(CustomStyle.StoneBold), 2));
    public static final RegistryObject<Item> STONE_CRYSTAL_3 = ITEMS.register("stone_crystal3",
            () -> new StoneCrystal(new Item.Properties().rarity(CustomStyle.StoneBold), 3));

    public static final RegistryObject<Item> ICE_CRYSTAL_0 = ITEMS.register("ice_crystal0",
            () -> new IceCrystal(new Item.Properties().rarity(CustomStyle.IceBold), 0));
    public static final RegistryObject<Item> ICE_CRYSTAL_1 = ITEMS.register("ice_crystal1",
            () -> new IceCrystal(new Item.Properties().rarity(CustomStyle.IceBold), 1));
    public static final RegistryObject<Item> ICE_CRYSTAL_2 = ITEMS.register("ice_crystal2",
            () -> new IceCrystal(new Item.Properties().rarity(CustomStyle.IceBold), 2));
    public static final RegistryObject<Item> ICE_CRYSTAL_3 = ITEMS.register("ice_crystal3",
            () -> new IceCrystal(new Item.Properties().rarity(CustomStyle.IceBold), 3));

    public static final RegistryObject<Item> WIND_CRYSTAL_0 = ITEMS.register("wind_crystal0",
            () -> new WindCrystal(new Item.Properties().rarity(CustomStyle.WindBold), 0));
    public static final RegistryObject<Item> WIND_CRYSTAL_1 = ITEMS.register("wind_crystal1",
            () -> new WindCrystal(new Item.Properties().rarity(CustomStyle.WindBold), 1));
    public static final RegistryObject<Item> WIND_CRYSTAL_2 = ITEMS.register("wind_crystal2",
            () -> new WindCrystal(new Item.Properties().rarity(CustomStyle.WindBold), 2));
    public static final RegistryObject<Item> WIND_CRYSTAL_3 = ITEMS.register("wind_crystal3",
            () -> new WindCrystal(new Item.Properties().rarity(CustomStyle.WindBold), 3));

    public static final RegistryObject<Item> LIGHTNING_CRYSTAL_0 = ITEMS.register("lightning_crystal0",
            () -> new LightningCrystal(new Item.Properties().rarity(CustomStyle.LightningBold), 0));
    public static final RegistryObject<Item> LIGHTNING_CRYSTAL_1 = ITEMS.register("lightning_crystal1",
            () -> new LightningCrystal(new Item.Properties().rarity(CustomStyle.LightningBold), 1));
    public static final RegistryObject<Item> LIGHTNING_CRYSTAL_2 = ITEMS.register("lightning_crystal2",
            () -> new LightningCrystal(new Item.Properties().rarity(CustomStyle.LightningBold), 2));
    public static final RegistryObject<Item> LIGHTNING_CRYSTAL_3 = ITEMS.register("lightning_crystal3",
            () -> new LightningCrystal(new Item.Properties().rarity(CustomStyle.LightningBold), 3));

    public static final RegistryObject<Item> LIFE_ELEMENT = ITEMS.register("life_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> WATER_ELEMENT = ITEMS.register("water_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.WaterBold)));
    public static final RegistryObject<Item> FIRE_ELEMENT = ITEMS.register("fire_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.FireBold)));
    public static final RegistryObject<Item> STONE_ELEMENT = ITEMS.register("stone_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.StoneBold)));
    public static final RegistryObject<Item> ICE_ELEMENT = ITEMS.register("ice_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.IceBold)));
    public static final RegistryObject<Item> LIGHTNING_ELEMENT = ITEMS.register("lightning_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.LightningBold)));
    public static final RegistryObject<Item> WIND_ELEMENT = ITEMS.register("wind_element",
            () -> new Item(new Item.Properties().rarity(CustomStyle.WindBold)));

    public static final RegistryObject<Item> MOB_ARMOR_LIFE_ELEMENT_HELMET
            = ITEMS.register("mob_armor_life_element_helmet",
            () -> new MobArmor(ModArmorMaterials.LifeElement, ArmorItem.Type.HELMET, StringUtils.MobName.LifeElement));
    public static final RegistryObject<Item> MOB_ARMOR_LIFE_ELEMENT_CHEST
            = ITEMS.register("mob_armor_life_element_chest",
            () -> new MobArmor(ModArmorMaterials.LifeElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_LIFE_ELEMENT_LEGGINGS
            = ITEMS.register("mob_armor_life_element_leggings",
            () -> new MobArmor(ModArmorMaterials.LifeElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_LIFE_ELEMENT_BOOTS
            = ITEMS.register("mob_armor_life_element_boots",
            () -> new MobArmor(ModArmorMaterials.LifeElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MOB_ARMOR_WATER_ELEMENT_HELMET
            = ITEMS.register("mob_armor_water_element_helmet",
            () -> new MobArmor(ModArmorMaterials.WaterElement, ArmorItem.Type.HELMET, StringUtils.MobName.WaterElement));
    public static final RegistryObject<Item> MOB_ARMOR_WATER_ELEMENT_CHEST
            = ITEMS.register("mob_armor_water_element_chest",
            () -> new MobArmor(ModArmorMaterials.WaterElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_WATER_ELEMENT_LEGGINGS
            = ITEMS.register("mob_armor_water_element_leggings",
            () -> new MobArmor(ModArmorMaterials.WaterElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_WATER_ELEMENT_BOOTS
            = ITEMS.register("mob_armor_water_element_boots",
            () -> new MobArmor(ModArmorMaterials.WaterElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MOB_ARMOR_FIRE_ELEMENT_HELMET
            = ITEMS.register("mob_armor_fire_element_helmet",
            () -> new MobArmor(ModArmorMaterials.FireElement, ArmorItem.Type.HELMET, StringUtils.MobName.FireElement));
    public static final RegistryObject<Item> MOB_ARMOR_FIRE_ELEMENT_CHEST
            = ITEMS.register("mob_armor_fire_element_chest",
            () -> new MobArmor(ModArmorMaterials.FireElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_FIRE_ELEMENT_LEGGINGS
            = ITEMS.register("mob_armor_fire_element_leggings",
            () -> new MobArmor(ModArmorMaterials.FireElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_FIRE_ELEMENT_BOOTS
            = ITEMS.register("mob_armor_fire_element_boots",
            () -> new MobArmor(ModArmorMaterials.FireElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MOB_ARMOR_STONE_ELEMENT_HELMET = ITEMS.register("mob_armor_stone_element_helmet",
            () -> new MobArmor(ModArmorMaterials.StoneElement, ArmorItem.Type.HELMET, StringUtils.MobName.StoneElement));
    public static final RegistryObject<Item> MOB_ARMOR_STONE_ELEMENT_CHEST = ITEMS.register("mob_armor_stone_element_chest",
            () -> new MobArmor(ModArmorMaterials.StoneElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_STONE_ELEMENT_LEGGINGS = ITEMS.register("mob_armor_stone_element_leggings",
            () -> new MobArmor(ModArmorMaterials.StoneElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_STONE_ELEMENT_BOOTS = ITEMS.register("mob_armor_stone_element_boots",
            () -> new MobArmor(ModArmorMaterials.StoneElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MOB_ARMOR_ICE_ELEMENT_HELMET = ITEMS.register("mob_armor_ice_element_helmet",
            () -> new MobArmor(ModArmorMaterials.IceElement, ArmorItem.Type.HELMET, StringUtils.MobName.IceElement));
    public static final RegistryObject<Item> MOB_ARMOR_ICE_ELEMENT_CHEST = ITEMS.register("mob_armor_ice_element_chest",
            () -> new MobArmor(ModArmorMaterials.IceElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_ICE_ELEMENT_LEGGINGS = ITEMS.register("mob_armor_ice_element_leggings",
            () -> new MobArmor(ModArmorMaterials.IceElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_ICE_ELEMENT_BOOTS = ITEMS.register("mob_armor_ice_element_boots",
            () -> new MobArmor(ModArmorMaterials.IceElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MOB_ARMOR_LIGHTNING_ELEMENT_HELMET = ITEMS.register("mob_armor_lightning_element_helmet",
            () -> new MobArmor(ModArmorMaterials.LightningElement, ArmorItem.Type.HELMET, StringUtils.MobName.LightningElement));
    public static final RegistryObject<Item> MOB_ARMOR_LIGHTNING_ELEMENT_CHEST = ITEMS.register("mob_armor_lightning_element_chest",
            () -> new MobArmor(ModArmorMaterials.LightningElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_LIGHTNING_ELEMENT_LEGGINGS = ITEMS.register("mob_armor_lightning_element_leggings",
            () -> new MobArmor(ModArmorMaterials.LightningElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_LIGHTNING_ELEMENT_BOOTS = ITEMS.register("mob_armor_lightning_element_boots",
            () -> new MobArmor(ModArmorMaterials.LightningElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> MOB_ARMOR_WIND_ELEMENT_HELMET = ITEMS.register("mob_armor_wind_element_helmet",
            () -> new MobArmor(ModArmorMaterials.WindElement, ArmorItem.Type.HELMET, StringUtils.MobName.WindElement));
    public static final RegistryObject<Item> MOB_ARMOR_WIND_ELEMENT_CHEST = ITEMS.register("mob_armor_wind_element_chest",
            () -> new MobArmor(ModArmorMaterials.WindElement, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_WIND_ELEMENT_LEGGINGS = ITEMS.register("mob_armor_wind_element_leggings",
            () -> new MobArmor(ModArmorMaterials.WindElement, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_WIND_ELEMENT_BOOTS = ITEMS.register("mob_armor_wind_element_boots",
            () -> new MobArmor(ModArmorMaterials.WindElement, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> RAINBOW_POWDER = ITEMS.register("rainbow_powder",
            () -> new RainbowPowder(new Item.Properties()));
    public static final RegistryObject<Item> RAINBOW_CRYSTAL = ITEMS.register("rainbow_crystal",
            () -> new RainbowCrystal(new Item.Properties()));

    public static final RegistryObject<Item> LIFE_HOLY_STONE_0 = ITEMS.register("life_holy_stone_0",
            () -> new LifeElementHolyStone(new Item.Properties().rarity(CustomStyle.LifeBold), 0));
    public static final RegistryObject<Item> LIFE_HOLY_STONE_1 = ITEMS.register("life_holy_stone_1",
            () -> new LifeElementHolyStone(new Item.Properties().rarity(CustomStyle.LifeBold), 1));
    public static final RegistryObject<Item> LIFE_HOLY_STONE_2 = ITEMS.register("life_holy_stone_2",
            () -> new LifeElementHolyStone(new Item.Properties().rarity(CustomStyle.LifeBold), 2));

    public static final RegistryObject<Item> WATER_HOLY_STONE_0 = ITEMS.register("water_holy_stone_0",
            () -> new WaterElementHolyStone(new Item.Properties().rarity(CustomStyle.WaterBold), 0));
    public static final RegistryObject<Item> WATER_HOLY_STONE_1 = ITEMS.register("water_holy_stone_1",
            () -> new WaterElementHolyStone(new Item.Properties().rarity(CustomStyle.WaterBold), 1));
    public static final RegistryObject<Item> WATER_HOLY_STONE_2 = ITEMS.register("water_holy_stone_2",
            () -> new WaterElementHolyStone(new Item.Properties().rarity(CustomStyle.WaterBold), 2));

    public static final RegistryObject<Item> FIRE_HOLY_STONE_0 = ITEMS.register("fire_holy_stone_0",
            () -> new FireElementHolyStone(new Item.Properties().rarity(CustomStyle.FireBold), 0));
    public static final RegistryObject<Item> FIRE_HOLY_STONE_1 = ITEMS.register("fire_holy_stone_1",
            () -> new FireElementHolyStone(new Item.Properties().rarity(CustomStyle.FireBold), 1));
    public static final RegistryObject<Item> FIRE_HOLY_STONE_2 = ITEMS.register("fire_holy_stone_2",
            () -> new FireElementHolyStone(new Item.Properties().rarity(CustomStyle.FireBold), 2));

    public static final RegistryObject<Item> STONE_HOLY_STONE_0 = ITEMS.register("stone_holy_stone_0",
            () -> new StoneElementHolyStone(new Item.Properties().rarity(CustomStyle.StoneBold), 0));
    public static final RegistryObject<Item> STONE_HOLY_STONE_1 = ITEMS.register("stone_holy_stone_1",
            () -> new StoneElementHolyStone(new Item.Properties().rarity(CustomStyle.StoneBold), 1));
    public static final RegistryObject<Item> STONE_HOLY_STONE_2 = ITEMS.register("stone_holy_stone_2",
            () -> new StoneElementHolyStone(new Item.Properties().rarity(CustomStyle.StoneBold), 2));

    public static final RegistryObject<Item> ICE_HOLY_STONE_0 = ITEMS.register("ice_holy_stone_0",
            () -> new IceElementHolyStone(new Item.Properties().rarity(CustomStyle.IceBold), 0));
    public static final RegistryObject<Item> ICE_HOLY_STONE_1 = ITEMS.register("ice_holy_stone_1",
            () -> new IceElementHolyStone(new Item.Properties().rarity(CustomStyle.IceBold), 1));
    public static final RegistryObject<Item> ICE_HOLY_STONE_2 = ITEMS.register("ice_holy_stone_2",
            () -> new IceElementHolyStone(new Item.Properties().rarity(CustomStyle.IceBold), 2));

    public static final RegistryObject<Item> LIGHTNING_HOLY_STONE_0 = ITEMS.register("lightning_holy_stone_0",
            () -> new LightningElementHolyStone(new Item.Properties().rarity(CustomStyle.LightningBold), 0));
    public static final RegistryObject<Item> LIGHTNING_HOLY_STONE_1 = ITEMS.register("lightning_holy_stone_1",
            () -> new LightningElementHolyStone(new Item.Properties().rarity(CustomStyle.LightningBold), 1));
    public static final RegistryObject<Item> LIGHTNING_HOLY_STONE_2 = ITEMS.register("lightning_holy_stone_2",
            () -> new LightningElementHolyStone(new Item.Properties().rarity(CustomStyle.LightningBold), 2));

    public static final RegistryObject<Item> WIND_HOLY_STONE_0 = ITEMS.register("wind_holy_stone_0",
            () -> new WindElementHolyStone(new Item.Properties().rarity(CustomStyle.WindBold), 0));
    public static final RegistryObject<Item> WIND_HOLY_STONE_1 = ITEMS.register("wind_holy_stone_1",
            () -> new WindElementHolyStone(new Item.Properties().rarity(CustomStyle.WindBold), 1));
    public static final RegistryObject<Item> WIND_HOLY_STONE_2 = ITEMS.register("wind_holy_stone_2",
            () -> new WindElementHolyStone(new Item.Properties().rarity(CustomStyle.WindBold), 2));

    public static final RegistryObject<Item> LIFE_ELEMENT_SWORD = ITEMS.register("life_element_sword",
            () -> new LifeElementSword(new Item.Properties().stacksTo(1).rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LIFE_ELEMENT_BOW = ITEMS.register("life_element_bow",
            () -> new LifeElementBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.LifeItalic)));
    public static final RegistryObject<Item> LIFE_ELEMENT_SCEPTRE = ITEMS.register("life_element_sceptre",
            () -> new LifeElementSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic)));

    public static final RegistryObject<Item> WATER_ELEMENT_SWORD = ITEMS.register("water_element_sword",
            () -> new WaterElementSword(new Item.Properties().stacksTo(1).rarity(CustomStyle.WaterItalic)));
    public static final RegistryObject<Item> WATER_ELEMENT_BOW = ITEMS.register("water_element_bow",
            () -> new WaterElementBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.WaterItalic)));
    public static final RegistryObject<Item> WATER_ELEMENT_SCEPTRE = ITEMS.register("water_element_sceptre",
            () -> new WaterElementSceptre(new Item.Properties().rarity(CustomStyle.WaterItalic)));

    public static final RegistryObject<Item> FIRE_ELEMENT_SWORD = ITEMS.register("fire_element_sword",
            () -> new FireElementSword(new Item.Properties().stacksTo(1).rarity(CustomStyle.FireItalic)));
    public static final RegistryObject<Item> FIRE_ELEMENT_BOW = ITEMS.register("fire_element_bow",
            () -> new FireElementBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.FireItalic)));
    public static final RegistryObject<Item> FIRE_ELEMENT_SCEPTRE = ITEMS.register("fire_element_sceptre",
            () -> new FireElementSceptre(new Item.Properties().rarity(CustomStyle.FireItalic)));

    public static final RegistryObject<Item> SHULKER_SOUL = ITEMS.register("shulker_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EndBold), true, true));
    public static final RegistryObject<Item> ENDER_MITE_SOUL = ITEMS.register("ender_mite_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EndBold), true, true));

    public static final RegistryObject<Item> END_CRYSTAL = ITEMS.register("end_crystal",
            () -> new EndCrystal(new Item.Properties().rarity(CustomStyle.EndBold)));

    public static final RegistryObject<Item> END_CURIOS_BOW = ITEMS.register("end_curios",
            () -> new EndCuriosBow(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> END_CURIOS_MANA = ITEMS.register("end_curios1",
            () -> new EndCuriosMana(new Item.Properties().rarity(CustomStyle.EndBold)));

    public static final RegistryObject<Item> ROSE_GOLD_COIN = ITEMS.register("rose_gold_coin",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.PurpleIronBold), false, false, List.of(
                    Te.s("于", "旭升岛秘藏商人", CustomStyle.styleOfSunIsland, "处兑换"),
                    Te.s("有一些", "特殊的用途", CustomStyle.styleOfSakura)
            )));

    public static final RegistryObject<Item> MILLION_MONEY = ITEMS.register("million_money",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC), false, false, List.of(
                    Te.s("在", "联合银行职员", CustomStyle.styleOfGold, "处兑换"),
                    Te.s("价值1,000,000VB", CustomStyle.styleOfGold)
            )));

    public static final RegistryObject<Item> WORLDSOUL_HOLLOW = ITEMS.register("worldsoul_hollow",
            () -> new Item(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> MOB_ARMOR_TOWER_1_FLOOR_HELMET = ITEMS.register("mob_armor_tower_1floor_helmet",
            () -> new MobArmor(ModArmorMaterials.LifeElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower1Floor));
    public static final RegistryObject<Item> MOB_ARMOR_TOWER_2_FLOOR_HELMET = ITEMS.register("mob_armor_tower_2floor_helmet",
            () -> new MobArmor(ModArmorMaterials.WaterElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower2Floor));
    public static final RegistryObject<Item> MOB_ARMOR_TOWER_3_FLOOR_HELMET = ITEMS.register("mob_armor_tower_3floor_helmet",
            () -> new MobArmor(ModArmorMaterials.FireElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower3Floor));
    public static final RegistryObject<Item> MOB_ARMOR_TOWER_4_FLOOR_HELMET = ITEMS.register("mob_armor_tower_4floor_helmet",
            () -> new MobArmor(ModArmorMaterials.StoneElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower4Floor));
    public static final RegistryObject<Item> MOB_ARMOR_TOWER_5_FLOOR_HELMET = ITEMS.register("mob_armor_tower_5floor_helmet",
            () -> new MobArmor(ModArmorMaterials.IceElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower5Floor));
    public static final RegistryObject<Item> MOB_ARMOR_TOWER_6_FLOOR_HELMET = ITEMS.register("mob_armor_tower_6floor_helmet",
            () -> new MobArmor(ModArmorMaterials.LightningElement, ArmorItem.Type.HELMET, StringUtils.MobName.Tower6Floor));

    public static final RegistryObject<Item> WATER_BOTTLE = ITEMS.register("water_bottle",
            () -> new Item(new Item.Properties().rarity(CustomStyle.Water)));

    public static final RegistryObject<Item> ATTACKUP_POTION = ITEMS.register("attackup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Volcano), NewPotion.PotionName.AttackUp, 0));
    public static final RegistryObject<Item> ATTACKUP_CON_POTION = ITEMS.register("attackup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Volcano), NewPotion.PotionName.AttackUp, 1));
    public static final RegistryObject<Item> ATTACKUP_LONG_POTION = ITEMS.register("attackup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Volcano), NewPotion.PotionName.AttackUp, 2));

    public static final RegistryObject<Item> SPLASH_ATTACKUP_POTION = ITEMS.register("splash_attackup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Volcano), ModPotions.Type.AttackUp));
    public static final RegistryObject<Item> SPLASH_ATTACKUP_CON_POTION = ITEMS.register("splash_attackup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Volcano), ModPotions.Type.ConAttackUp));
    public static final RegistryObject<Item> SPLASH_ATTACKUP_LONG_POTION = ITEMS.register("splash_attackup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Volcano), ModPotions.Type.LongAttackUp));

    public static final RegistryObject<Item> DEFENCE_PENETRATION_UP_POTION = ITEMS.register("breakdefenceup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefencePenetrationUp, 0));
    public static final RegistryObject<Item> DEFENCE_PENETRATION_UP_CON_POTION = ITEMS.register("breakdefenceup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefencePenetrationUp, 1));
    public static final RegistryObject<Item> DEFENCE_PENETRATION_UP_LONG_POTION = ITEMS.register("breakdefenceup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefencePenetrationUp, 2));

    public static final RegistryObject<Item> SPLASH_DEFENCE_PENETRATION_UP_POTION = ITEMS.register("splash_breakdefenceup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.DefencePenetrationUp));
    public static final RegistryObject<Item> SPLASH_DEFENCE_PENETRATION_UP_CON_POTION = ITEMS.register("splash_breakdefenceup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.ConDefencePenetrationUp));
    public static final RegistryObject<Item> SPLASH_DEFENCE_PENETRATION_UP_LONG_POTION = ITEMS.register("splash_breakdefenceup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.LongDefencePenetrationUp));

    public static final RegistryObject<Item> MANA_PENETRATION_UP_POTION = ITEMS.register("breakmanadefenceup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaPenetrationUp, 0));
    public static final RegistryObject<Item> MANA_PENETRATION_UP_CON_POTION = ITEMS.register("breakmanadefenceup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaPenetrationUp, 1));
    public static final RegistryObject<Item> MANA_PENETRATION_UP_LONG_POTION = ITEMS.register("breakmanadefenceup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaPenetrationUp, 2));

    public static final RegistryObject<Item> SPLASH_MANA_PENETRATION_UP_POTION = ITEMS.register("splash_breakmanadefenceup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ManaPenetrationUp));
    public static final RegistryObject<Item> SPLASH_MANA_PENETRATION_UP_CON_POTION = ITEMS.register("splash_breakmanadefenceup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ConManaPenetrationUp));
    public static final RegistryObject<Item> SPLASH_MANA_PENETRATION_UP_LONG_POTION = ITEMS.register("splash_breakmanadefenceup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.LongManaPenetrationUp));

    public static final RegistryObject<Item> COOLDOWN_UP_POTION = ITEMS.register("cooldownup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Water), NewPotion.PotionName.CooldownUp, 0));
    public static final RegistryObject<Item> COOLDOWN_UP_CON_POTION = ITEMS.register("cooldownup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Water), NewPotion.PotionName.CooldownUp, 1));
    public static final RegistryObject<Item> COOLDOWN_UP_LONG_POTION = ITEMS.register("cooldownup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Water), NewPotion.PotionName.CooldownUp, 2));

    public static final RegistryObject<Item> SPLASH_COOLDOWN_UP_POTION = ITEMS.register("splash_cooldownup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Water), ModPotions.Type.CoolDownUp));
    public static final RegistryObject<Item> SPLASH_COOLDOWN_UP_CON_POTION = ITEMS.register("splash_cooldownup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Water), ModPotions.Type.ConCoolDownUp));
    public static final RegistryObject<Item> SPLASH_COOLDOWN_UP_LONG_POTION = ITEMS.register("splash_cooldownup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Water), ModPotions.Type.LongCoolDownUp));

    public static final RegistryObject<Item> CRIT_DAMAGE_UP_POTION = ITEMS.register("critdamageup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.CritDamageUp, 0));
    public static final RegistryObject<Item> CRIT_DAMAGE_UP_CON_POTION = ITEMS.register("critdamageup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.CritDamageUp, 1));
    public static final RegistryObject<Item> CRIT_DAMAGE_UP_LONG_POTION = ITEMS.register("critdamageup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.CritDamageUp, 2));

    public static final RegistryObject<Item> SPLASH_CRIT_DAMAGE_UP_POTION = ITEMS.register("splash_critdamageup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.CritDamageUp));
    public static final RegistryObject<Item> SPLASH_CRIT_DAMAGE_UP_CON_POTION = ITEMS.register("splash_critdamageup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.ConCritDamageUp));
    public static final RegistryObject<Item> SPLASH_CRIT_DAMAGE_UP_LONG_POTION = ITEMS.register("splash_critdamageup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.LongCritDamageUp));

    public static final RegistryObject<Item> CRIT_RATE_UP_POTION = ITEMS.register("critrateup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Sakura), NewPotion.PotionName.CritRateUp, 0));
    public static final RegistryObject<Item> CRIT_RATE_UP_CON_POTION = ITEMS.register("critrateup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Sakura), NewPotion.PotionName.CritRateUp, 1));
    public static final RegistryObject<Item> CRIT_RATE_UP_LONG_POTION = ITEMS.register("critrateup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Sakura), NewPotion.PotionName.CritRateUp, 2));

    public static final RegistryObject<Item> SPLASH_CRIT_RATE_UP_POTION = ITEMS.register("splash_critrateup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Sakura), ModPotions.Type.CritRateUp));
    public static final RegistryObject<Item> SPLASH_CRIT_RATE_UP_CON_POTION = ITEMS.register("splash_critrateup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Sakura), ModPotions.Type.ConCritRateUp));
    public static final RegistryObject<Item> SPLASH_CRIT_RATE_UP_LONG_POTION = ITEMS.register("splash_critrateup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Sakura), ModPotions.Type.LongCritRateUp));

    public static final RegistryObject<Item> DEFENCE_UP_POTION = ITEMS.register("defenceup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefenceUp, 0));
    public static final RegistryObject<Item> DEFENCE_UP_CON_POTION = ITEMS.register("defenceup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefenceUp, 1));
    public static final RegistryObject<Item> DEFENCE_UP_LONG_POTION = ITEMS.register("defenceup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.DefenceUp, 2));

    public static final RegistryObject<Item> SPLASH_DEFENCE_UP_POTION = ITEMS.register("splash_defenceup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.DefenceUp));
    public static final RegistryObject<Item> SPLASH_DEFENCE_UP_CON_POTION = ITEMS.register("splash_defenceup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.ConDefenceUp));
    public static final RegistryObject<Item> SPLASH_DEFENCE_UP_LONG_POTION = ITEMS.register("splash_defenceup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), ModPotions.Type.LongDefenceUp));

    public static final RegistryObject<Item> HEALTH_STEAL_UP_POTION = ITEMS.register("healstealup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Nether), NewPotion.PotionName.HealthStealUp, 0));
    public static final RegistryObject<Item> HEALTH_STEAL_UP_CON_POTION = ITEMS.register("healstealup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Nether), NewPotion.PotionName.HealthStealUp, 1));
    public static final RegistryObject<Item> HEALTH_STEAL_UP_LONG_POTION = ITEMS.register("healstealup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Nether), NewPotion.PotionName.HealthStealUp, 2));

    public static final RegistryObject<Item> SPLASH_HEALTH_STEAL_UP_POTION = ITEMS.register("splash_healstealup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Nether), ModPotions.Type.HealthStealUp));
    public static final RegistryObject<Item> SPLASH_HEALTH_STEAL_UP_CON_POTION = ITEMS.register("splash_healstealup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Nether), ModPotions.Type.ConHealthStealUp));
    public static final RegistryObject<Item> SPLASH_HEALTH_STEAL_UP_LONG_POTION = ITEMS.register("splash_healstealup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Nether), ModPotions.Type.LongHealthStealUp));

    public static final RegistryObject<Item> MANA_DAMAGE_UP_POTION = ITEMS.register("manadamageup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaDamageUp, 0));
    public static final RegistryObject<Item> MANA_DAMAGE_UP_CON_POTION = ITEMS.register("manadamageup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaDamageUp, 1));
    public static final RegistryObject<Item> MANA_DAMAGE_UP_LONG_POTION = ITEMS.register("manadamageup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaDamageUp, 2));

    public static final RegistryObject<Item> SPLASH_MANA_DAMAGE_UP_POTION = ITEMS.register("splash_manadamageup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ManaDamageUp));
    public static final RegistryObject<Item> SPLASH_MANA_DAMAGE_UP_CON_POTION = ITEMS.register("splash_manadamageup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ConManaDamageUp));
    public static final RegistryObject<Item> SPLASH_MANA_DAMAGE_UP_LONG_POTION = ITEMS.register("splash_manadamageup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.LongManaDamageUp));

    public static final RegistryObject<Item> MANA_DEFENCE_UP_POTION = ITEMS.register("manadefenceup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.ManaDefenceUp, 0));
    public static final RegistryObject<Item> MANA_DEFENCE_UP_CON_POTION = ITEMS.register("manadefenceup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.ManaDefenceUp, 1));
    public static final RegistryObject<Item> MANA_DEFENCE_UP_LONG_POTION = ITEMS.register("manadefenceup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Lightning), NewPotion.PotionName.ManaDefenceUp, 2));

    public static final RegistryObject<Item> SPLASH_MANA_DEFENCE_UP_POTION = ITEMS.register("splash_manadefenceup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.ManaDefenceUp));
    public static final RegistryObject<Item> SPLASH_MANA_DEFENCE_UP_CON_POTION = ITEMS.register("splash_manadefenceup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.ConManaDefenceUp));
    public static final RegistryObject<Item> SPLASH_MANA_DEFENCE_UP_LONG_POTION = ITEMS.register("splash_manadefenceup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Lightning), ModPotions.Type.LongManaDefenceUp));

    public static final RegistryObject<Item> MANA_RECOVER_UP_POTION = ITEMS.register("manareplyup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaRecoverUp, 0));
    public static final RegistryObject<Item> MANA_RECOVER_UP_CON_POTION = ITEMS.register("manareplyup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaRecoverUp, 1));
    public static final RegistryObject<Item> MANA_RECOVER_UP_LONG_POTION = ITEMS.register("manareplyup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.ManaRecoverUp, 2));

    public static final RegistryObject<Item> SPLASH_MANA_RECOVER_UP_POTION = ITEMS.register("splash_manareplyup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ManaRecoverUp));
    public static final RegistryObject<Item> SPLASH_MANA_RECOVER_UP_CON_POTION = ITEMS.register("splash_manareplyup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.ConManaRecoverUp));
    public static final RegistryObject<Item> SPLASH_MANA_RECOVER_UP_LONG_POTION = ITEMS.register("splash_manareplyup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), ModPotions.Type.LongManaRecoverUp));

    public static final RegistryObject<Item> MOVEMENT_SPEED_UP_POTION = ITEMS.register("speedup_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.MovementSpeedUp, 0));
    public static final RegistryObject<Item> MOVEMENT_SPEED_UP_CON_POTION = ITEMS.register("speedup_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.MovementSpeedUp, 1));
    public static final RegistryObject<Item> MOVEMENT_SPEED_UP_LONG_POTION = ITEMS.register("speedup_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.MovementSpeedUp, 2));

    public static final RegistryObject<Item> SPLASH_MOVEMENT_SPEED_UP_POTION = ITEMS.register("splash_speedup_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.MovementSpeedUp));
    public static final RegistryObject<Item> SPLASH_MOVEMENT_SPEED_UP_CON_POTION = ITEMS.register("splash_speedup_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.ConMovementSpeedUp));
    public static final RegistryObject<Item> SPLASH_MOVEMENT_SPEED_UP_LONG_POTION = ITEMS.register("splash_speedup_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.LongMovementSpeedUp));

    public static final RegistryObject<Item> HEALTH_RECOVER_UP_POTION = ITEMS.register("healreply_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.HealthRecoverUp, 0));
    public static final RegistryObject<Item> HEALTH_RECOVER_UP_CON_POTION = ITEMS.register("healreply_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.HealthRecoverUp, 1));
    public static final RegistryObject<Item> HEALTH_RECOVER_UP_LONG_POTION = ITEMS.register("healreply_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Plain), NewPotion.PotionName.HealthRecoverUp, 2));

    public static final RegistryObject<Item> SPLASH_HEALTH_RECOVER_UP_POTION = ITEMS.register("splash_healreply_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.HealthRecoverUp));
    public static final RegistryObject<Item> SPLASH_HEALTH_RECOVER_UP_CON_POTION = ITEMS.register("splash_healreply_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.ConHealthRecoverUp));
    public static final RegistryObject<Item> SPLASH_HEALTH_RECOVER_UP_LONG_POTION = ITEMS.register("splash_healreply_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Plain), ModPotions.Type.LongHealthRecoverUp));

    public static final RegistryObject<Item> ATTACK_UP_POTION_BAG = ITEMS.register("attackup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), ATTACKUP_POTION.get()));
    public static final RegistryObject<Item> DEFENCE_PENETRATION_POTION_BAG = ITEMS.register("breakdefenceup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), DEFENCE_PENETRATION_UP_POTION.get()));
    public static final RegistryObject<Item> CRIT_RATE_UP_POTION_BAG = ITEMS.register("critrateup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), CRIT_RATE_UP_POTION.get()));
    public static final RegistryObject<Item> CRIT_DAMAGE_UP_POTION_BAG = ITEMS.register("critdamageup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), CRIT_DAMAGE_UP_POTION.get()));
    public static final RegistryObject<Item> MANA_DAMAGE_UP_POTION_BAG = ITEMS.register("manadamageup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), MANA_DAMAGE_UP_POTION.get()));
    public static final RegistryObject<Item> MANA_PENETRATION_POTION_BAG = ITEMS.register("manabreakdefenceup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), MANA_PENETRATION_UP_POTION.get()));
    public static final RegistryObject<Item> MANA_RECOVER_POTION_BAG = ITEMS.register("manareplyup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), MANA_RECOVER_UP_POTION.get()));
    public static final RegistryObject<Item> POWER_RELEASE_SPEED_POTION_BAG = ITEMS.register("cooldowndecreaseup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), COOLDOWN_UP_POTION.get()));
    public static final RegistryObject<Item> HEALTH_STEAL_UP_POTION_BAG = ITEMS.register("healstealup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), HEALTH_STEAL_UP_POTION.get()));
    public static final RegistryObject<Item> DEFENCE_UP_POTION_BAG = ITEMS.register("defenceup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), DEFENCE_UP_POTION.get()));
    public static final RegistryObject<Item> MANA_DEFENCE_UP_POTION_BAG = ITEMS.register("manadefenceup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), MANA_DEFENCE_UP_POTION.get()));
    public static final RegistryObject<Item> MOVEMENT_SPEED_UP_POTION_BAG = ITEMS.register("speedup_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), MOVEMENT_SPEED_UP_POTION.get()));
    public static final RegistryObject<Item> HEALTH_RECOVER_POTION_BAG = ITEMS.register("healthrecover_potionbag",
            () -> new PotionBag(new Item.Properties().rarity(Rarity.RARE), HEALTH_RECOVER_UP_POTION.get()));

    public static final RegistryObject<Item> PEARL_1 = ITEMS.register("pearl1",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.LifeBold)));
    public static final RegistryObject<Item> PEARL_2 = ITEMS.register("pearl2",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.SkyBold)));
    public static final RegistryObject<Item> PEARL_3 = ITEMS.register("pearl3",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.NetherBold)));
    public static final RegistryObject<Item> PEARL_4 = ITEMS.register("pearl4",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.EndBold)));
    public static final RegistryObject<Item> PEARL_5 = ITEMS.register("pearl5",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.SakuraBold)));
    public static final RegistryObject<Item> PEARL_6 = ITEMS.register("pearl6",
            () -> new Pearl(new Item.Properties().rarity(CustomStyle.MoonBold)));

    public static final RegistryObject<Item> WORLD_FORGE_STONE = ITEMS.register("world_forge_stone",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> SPLASHER = ITEMS.register("splasher",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(CustomStyle.Magma)));

    public static final RegistryObject<Item> DAMAGE_ENHANCE_POTION = ITEMS.register("damage_enhance_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.damageEnhance, 0));
    public static final RegistryObject<Item> DAMAGE_ENHANCE_CON_POTION = ITEMS.register("damage_enhance_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.damageEnhance, 1));
    public static final RegistryObject<Item> DAMAGE_ENHANCE_LONG_POTION = ITEMS.register("damage_enhance_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.damageEnhance, 2));

    public static final RegistryObject<Item> SPLASH_DAMAGE_ENHANCE_POTION = ITEMS.register("splash_damage_enhance_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.damageEnhance + "_potion"));
    public static final RegistryObject<Item> SPLASH_DAMAGE_ENHANCE_CON_POTION = ITEMS.register("splash_damage_enhance_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), "con_" + NewPotion.PotionName.damageEnhance + "_potion"));
    public static final RegistryObject<Item> SPLASH_DAMAGE_ENHANCE_LONG_POTION = ITEMS.register("splash_damage_enhance_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), "long_" + NewPotion.PotionName.damageEnhance + "_potion"));

    public static final RegistryObject<Item> ATTACK_DAMAGE_ENHANCE_POTION = ITEMS.register("attack_damage_enhance_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.attackDamageEnhance, 0));
    public static final RegistryObject<Item> ATTACK_DAMAGE_ENHANCE_CON_POTION = ITEMS.register("attack_damage_enhance_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.attackDamageEnhance, 1));
    public static final RegistryObject<Item> ATTACK_DAMAGE_ENHANCE_LONG_POTION = ITEMS.register("attack_damage_enhance_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.attackDamageEnhance, 2));

    public static final RegistryObject<Item> SPLASH_ATTACK_DAMAGE_ENHANCE_POTION = ITEMS.register("splash_attack_damage_enhance_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), NewPotion.PotionName.attackDamageEnhance + "_potion"));
    public static final RegistryObject<Item> SPLASH_ATTACK_DAMAGE_ENHANCE_CON_POTION = ITEMS.register("splash_attack_damage_enhance_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), "con_" + NewPotion.PotionName.attackDamageEnhance + "_potion"));
    public static final RegistryObject<Item> SPLASH_ATTACK_DAMAGE_ENHANCE_LONG_POTION = ITEMS.register("splash_attack_damage_enhance_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Magma), "long_" + NewPotion.PotionName.attackDamageEnhance + "_potion"));

    public static final RegistryObject<Item> MANA_DAMAGE_ENHANCE_POTION = ITEMS.register("mana_damage_enhance_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.manaDamageEnhance, 0));
    public static final RegistryObject<Item> MANA_DAMAGE_ENHANCE_CON_POTION = ITEMS.register("mana_damage_enhance_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.manaDamageEnhance, 1));
    public static final RegistryObject<Item> MANA_DAMAGE_ENHANCE_LONG_POTION = ITEMS.register("mana_damage_enhance_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.manaDamageEnhance, 2));

    public static final RegistryObject<Item> SPLASH_MANA_DAMAGE_ENHANCE_POTION = ITEMS.register("splash_mana_damage_enhance_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), NewPotion.PotionName.manaDamageEnhance + "_potion"));
    public static final RegistryObject<Item> SPLASH_MANA_DAMAGE_ENHANCE_CON_POTION = ITEMS.register("splash_mana_damage_enhance_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), "con_" + NewPotion.PotionName.manaDamageEnhance + "_potion"));
    public static final RegistryObject<Item> SPLASH_MANA_DAMAGE_ENHANCE_LONG_POTION = ITEMS.register("splash_mana_damage_enhance_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Evoker), "long_" + NewPotion.PotionName.manaDamageEnhance + "_potion"));

    public static final RegistryObject<Item> GIANT_POTION = ITEMS.register("giant_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Life), NewPotion.PotionName.giant, 0));
    public static final RegistryObject<Item> GIANT_CON_POTION = ITEMS.register("giant_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Life), NewPotion.PotionName.giant, 1));
    public static final RegistryObject<Item> GIANT_LONG_POTION = ITEMS.register("giant_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Life), NewPotion.PotionName.giant, 2));

    public static final RegistryObject<Item> SPLASH_GIANT_POTION = ITEMS.register("splash_giant_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Life), NewPotion.PotionName.giant + "_potion"));
    public static final RegistryObject<Item> SPLASH_GIANT_CON_POTION = ITEMS.register("splash_giant_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Life), "con_" + NewPotion.PotionName.giant + "_potion"));
    public static final RegistryObject<Item> SPLASH_GIANT_LONG_POTION = ITEMS.register("splash_giant_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Life), "long_" + NewPotion.PotionName.giant + "_potion"));

    public static final RegistryObject<Item> STONE_POTION = ITEMS.register("stone_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.stone, 0));
    public static final RegistryObject<Item> STONE_CON_POTION = ITEMS.register("stone_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.stone, 1));
    public static final RegistryObject<Item> STONE_LONG_POTION = ITEMS.register("stone_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.stone, 2));

    public static final RegistryObject<Item> SPLASH_STONE_POTION = ITEMS.register("splash_stone_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), NewPotion.PotionName.stone + "_potion"));
    public static final RegistryObject<Item> SPLASH_STONE_CON_POTION = ITEMS.register("splash_stone_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), "con_" + NewPotion.PotionName.stone + "_potion"));
    public static final RegistryObject<Item> SPLASH_STONE_LONG_POTION = ITEMS.register("splash_stone_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Mine), "long_" + NewPotion.PotionName.stone + "_potion"));

    public static final RegistryObject<Item> EX_HARVEST_POTION = ITEMS.register("ex_harvest_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Gold), NewPotion.PotionName.exHarvest, 0));
    public static final RegistryObject<Item> EX_HARVEST_CON_POTION = ITEMS.register("ex_harvest_con_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Gold), NewPotion.PotionName.exHarvest, 1));
    public static final RegistryObject<Item> EX_HARVEST_LONG_POTION = ITEMS.register("ex_harvest_long_potion",
            () -> new NewPotion(new Item.Properties().rarity(CustomStyle.Gold), NewPotion.PotionName.exHarvest, 2));

    public static final RegistryObject<Item> SPLASH_EX_HARVEST_POTION = ITEMS.register("splash_ex_harvest_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Gold), NewPotion.PotionName.exHarvest + "_potion"));
    public static final RegistryObject<Item> SPLASH_EX_HARVEST_CON_POTION = ITEMS.register("splash_ex_harvest_con_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Gold), "con_" + NewPotion.PotionName.exHarvest + "_potion"));
    public static final RegistryObject<Item> SPLASH_EX_HARVEST_LONG_POTION = ITEMS.register("splash_ex_harvest_long_potion",
            () -> new NewThrowablePotion(new Item.Properties().rarity(CustomStyle.Gold), "long_" + NewPotion.PotionName.exHarvest + "_potion"));

    public static final RegistryObject<Item> WOOD_HAMMER = ITEMS.register("wood_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.HuskBold), 0));
    public static final RegistryObject<Item> STONE_HAMMER = ITEMS.register("stone_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.MineBold), 1));
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.SnowBold), 2));
    public static final RegistryObject<Item> COPPER_HAMMER = ITEMS.register("copper_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.CopperBold), 3));
    public static final RegistryObject<Item> GOLD_HAMMER = ITEMS.register("gold_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.GoldBold), 4));
    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.IceBold), 5));
    public static final RegistryObject<Item> EMERALD_HAMMER = ITEMS.register("emerald_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.MOONTAIN_BOLD), 6));
    public static final RegistryObject<Item> NETHER_HAMMER = ITEMS.register("nether_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.NetherBold), 7));
    public static final RegistryObject<Item> END_HAMMER = ITEMS.register("end_hammer",
            () -> new ForgeHammer(new Item.Properties().rarity(CustomStyle.EndBold), 8));

    public static final RegistryObject<Item> NETHER_PEARL = ITEMS.register("to_nether",
            () -> new ToNether(new Item.Properties().rarity(CustomStyle.NetherBold)));

    public static final RegistryObject<Item> EQUIP_PIECE_0 = ITEMS.register("equip_piece0",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.Gray), 0));
    public static final RegistryObject<Item> EQUIP_PIECE_1 = ITEMS.register("equip_piece1",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.Green), 1));
    public static final RegistryObject<Item> EQUIP_PIECE_2 = ITEMS.register("equip_piece2",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.Aqua), 2));
    public static final RegistryObject<Item> EQUIP_PIECE_3 = ITEMS.register("equip_piece3",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.LightPurple), 3));
    public static final RegistryObject<Item> EQUIP_PIECE_4 = ITEMS.register("equip_piece4",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.Gold), 4));
    public static final RegistryObject<Item> EQUIP_PIECE_5 = ITEMS.register("equip_piece5",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.Red), 5));
    public static final RegistryObject<Item> EQUIP_PIECE_6 = ITEMS.register("equip_piece6",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.End), 6));
    public static final RegistryObject<Item> EQUIP_PIECE_7 = ITEMS.register("equip_piece7",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.Moon), 7));
    public static final RegistryObject<Item> EQUIP_PIECE_8 = ITEMS.register("equip_piece8",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.Moon1), 8));
    public static final RegistryObject<Item> EQUIP_PIECE_9 = ITEMS.register("equip_piece9",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.Magma), 9));
    public static final RegistryObject<Item> EQUIP_PIECE_10 = ITEMS.register("equip_piece10",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.CastleCrystal), 10));
    public static final RegistryObject<Item> EQUIP_PIECE_11 = ITEMS.register("equip_piece11",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.Sakura), 11));
    public static final RegistryObject<Item> EQUIP_PIECE_12 = ITEMS.register("equip_piece12",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.YSR), 12));
    public static final RegistryObject<Item> EQUIP_PIECE_13 = ITEMS.register("equip_piece13",
            () -> new EquipPiece(new Item.Properties().rarity(CustomStyle.Sky), 13));

    public static final RegistryObject<Item> NOTE_PAPER = ITEMS.register("note_paper",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> KILL_PAPER = ITEMS.register("kill_paper",
            () -> new KillPaper(new Item.Properties().rarity(Rarity.UNCOMMON), 32));
    public static final RegistryObject<Item> KILL_PAPER_L = ITEMS.register("kill_paper_l",
            () -> new KillPaper(new Item.Properties().rarity(Rarity.EPIC), 128));

    public static final RegistryObject<Item> LAVENDER_BRACELET = ITEMS.register("lavender_bracelet",
            () -> new LavenderBracelet(new Item.Properties().rarity(CustomStyle.JacarandaBold).stacksTo(1)));

    public static final RegistryObject<Item> PLAIN_NECKLACE = ITEMS.register("plain_necklace",
            () -> new PlainNecklace(new Item.Properties().rarity(CustomStyle.PlainBold)));

    public static final RegistryObject<Item> NETHER_HAND = ITEMS.register("nether_hand",
            () -> new NetherHand(new Item.Properties().rarity(CustomStyle.NetherBold)));

    public static final RegistryObject<Item> ICE_BELT = ITEMS.register("ice_belt",
            () -> new IceBelt(new Item.Properties().rarity(CustomStyle.IceBold)));

    public static final RegistryObject<Item> CASTLE_SOUL = ITEMS.register("castle_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Castle)));

    public static final RegistryObject<Item> BEACON_BOW = ITEMS.register("beacon_bow",
            () -> new BeaconBow(new Item.Properties().rarity(CustomStyle.MagmaItalic), 0));
    public static final RegistryObject<Item> BEACON_BOW_1 = ITEMS.register("beacon_bow1",
            () -> new BeaconBow(new Item.Properties().rarity(CustomStyle.MagmaItalic), 1));
    public static final RegistryObject<Item> BEACON_BOW_2 = ITEMS.register("beacon_bow2",
            () -> new BeaconBow(new Item.Properties().rarity(CustomStyle.MagmaItalic), 2));
    public static final RegistryObject<Item> BEACON_BOW_3 = ITEMS.register("beacon_bow3",
            () -> new BeaconBow(new Item.Properties().rarity(CustomStyle.MagmaItalic), 3));

    public static final RegistryObject<Item> BLAZE_SWORD = ITEMS.register("blaze_sword",
            () -> new BlazeSword(new Item.Properties().rarity(CustomStyle.MagmaItalic), 0));
    public static final RegistryObject<Item> BLAZE_SWORD_1 = ITEMS.register("blaze_sword1",
            () -> new BlazeSword(new Item.Properties().rarity(CustomStyle.MagmaItalic), 1));
    public static final RegistryObject<Item> BLAZE_SWORD_2 = ITEMS.register("blaze_sword2",
            () -> new BlazeSword(new Item.Properties().rarity(CustomStyle.MagmaItalic), 2));
    public static final RegistryObject<Item> BLAZE_SWORD_3 = ITEMS.register("blaze_sword3",
            () -> new BlazeSword(new Item.Properties().rarity(CustomStyle.MagmaItalic), 3));

    public static final RegistryObject<Item> TREE_SCEPTRE = ITEMS.register("tree_sceptre",
            () -> new TreeSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic), 0));
    public static final RegistryObject<Item> TREE_SCEPTRE_1 = ITEMS.register("tree_sceptre1",
            () -> new TreeSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic), 1));
    public static final RegistryObject<Item> TREE_SCEPTRE_2 = ITEMS.register("tree_sceptre2",
            () -> new TreeSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic), 2));
    public static final RegistryObject<Item> TREE_SCEPTRE_3 = ITEMS.register("tree_sceptre3",
            () -> new TreeSceptre(new Item.Properties().rarity(CustomStyle.LifeItalic), 3));

    public static final RegistryObject<Item> BEACON_SOUL = ITEMS.register("beacon_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Magma)));
    public static final RegistryObject<Item> BEACON_RUNE = ITEMS.register("beacon_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MagmaBold)));

    public static final RegistryObject<Item> BLAZE_SOUL = ITEMS.register("blaze_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Magma)));
    public static final RegistryObject<Item> BLAZE_RUNE = ITEMS.register("blaze_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.MagmaBold)));

    public static final RegistryObject<Item> TREE_SOUL = ITEMS.register("tree_soul",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Life)));
    public static final RegistryObject<Item> TREE_RUNE = ITEMS.register("tree_rune",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.LifeBold)));

    public static final RegistryObject<Item> MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_MANA_HELMET = ITEMS.register("mob_armor_black_castle_one_floor_mana_helmet",
            () -> new MobArmor(ModArmorMaterials.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.BlackCastleOneFloorMana));
    public static final RegistryObject<Item> MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_ATTACK_HELMET = ITEMS.register("mob_armor_black_castle_one_floor_attack_helmet",
            () -> new MobArmor(ModArmorMaterials.Castle, ArmorItem.Type.HELMET, StringUtils.MobName.BlackCastleOneFloorAttack));
    public static final RegistryObject<Item> MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_CHEST = ITEMS.register("mob_armor_black_castle_one_floor_chest",
            () -> new MobArmor(ModArmorMaterials.Castle, ArmorItem.Type.CHESTPLATE, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_LEGGINGS = ITEMS.register("mob_armor_black_castle_one_floor_leggings",
            () -> new MobArmor(ModArmorMaterials.Castle, ArmorItem.Type.LEGGINGS, StringUtils.MobName.NoAttribute));
    public static final RegistryObject<Item> MOB_ARMOR_BLACK_CASTLE_ONE_FLOOR_BOOTS = ITEMS.register("mob_armor_black_castle_one_floor_boots",
            () -> new MobArmor(ModArmorMaterials.Castle, ArmorItem.Type.BOOTS, StringUtils.MobName.NoAttribute));

    public static final RegistryObject<Item> CASTLE_PIECE = ITEMS.register("castle_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.Castle)));

    public static final RegistryObject<Item> CASTLE_INGOT = ITEMS.register("castle_ingot",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> CASTLE_SWORD_PIECE = ITEMS.register("castle_sword_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.CastleBold)));
    public static final RegistryObject<Item> CASTLE_BOW_PIECE = ITEMS.register("castle_bow_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.CastleBold)));
    public static final RegistryObject<Item> CASTLE_SCEPTRE_PIECE = ITEMS.register("castle_sceptre_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> CASTLE_ARMOR_PIECE = ITEMS.register("castle_armor_piece",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> CASTLE_LOOT = ITEMS.register("castle_loot",
            () -> new CastleLoot(new Item.Properties().rarity(CustomStyle.CastleBold)));

    public static final RegistryObject<Item> CASTLE_SWORD = ITEMS.register("castle_sword",
            () -> new CastleSword(new Item.Properties().stacksTo(1).rarity(CustomStyle.CastleItalic), 0));
    public static final RegistryObject<Item> CASTLE_SWORD_E = ITEMS.register("castle_sword_e",
            () -> new CastleSword(new Item.Properties().stacksTo(1).rarity(CustomStyle.CastleItalic), 1));
    public static final RegistryObject<Item> CASTLE_BOW = ITEMS.register("castle_bow",
            () -> new CastleBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.CastleItalic), 0));
    public static final RegistryObject<Item> CASTLE_BOW_E = ITEMS.register("castle_bow_e",
            () -> new CastleBow(new Item.Properties().stacksTo(1).rarity(CustomStyle.CastleItalic), 1));
    public static final RegistryObject<Item> CASTLE_SCEPTRE = ITEMS.register("castle_sceptre",
            () -> new CastleSceptre(new Item.Properties().rarity(CustomStyle.CastleItalic), 0));
    public static final RegistryObject<Item> CASTLE_SCEPTRE_E = ITEMS.register("castle_sceptre_e",
            () -> new CastleSceptre(new Item.Properties().rarity(CustomStyle.CastleItalic), 1));

    public static final RegistryObject<Item> CASTLE_ATTACK_HELMET = ITEMS.register("castle_attack_helmet",
            () -> new CastleAttackArmor(ModArmorMaterials.Castle, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.CastleItalic)));
    public static final RegistryObject<Item> CASTLE_ATTACK_CHEST = ITEMS.register("castle_attack_chest",
            () -> new CastleAttackArmor(ModArmorMaterials.Castle, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.CastleItalic)));
    public static final RegistryObject<Item> CASTLE_ATTACK_LEGGINGS = ITEMS.register("castle_attack_leggings",
            () -> new CastleAttackArmor(ModArmorMaterials.Castle, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.CastleItalic)));
    public static final RegistryObject<Item> CASTLE_ATTACK_BOOTS = ITEMS.register("castle_attack_boots",
            () -> new CastleAttackArmor(ModArmorMaterials.Castle, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CASTLE_SWIFT_HELMET = ITEMS.register("castle_swift_helmet",
            () -> new CastleSwiftArmor(ModArmorMaterials.Castle, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.CastleItalic)));
    public static final RegistryObject<Item> CASTLE_SWIFT_CHEST = ITEMS.register("castle_swift_chest",
            () -> new CastleSwiftArmor(ModArmorMaterials.Castle, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.CastleItalic)));
    public static final RegistryObject<Item> CASTLE_SWIFT_LEGGINGS = ITEMS.register("castle_swift_leggings",
            () -> new CastleSwiftArmor(ModArmorMaterials.Castle, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.CastleItalic)));
    public static final RegistryObject<Item> CASTLE_SWIFT_BOOTS = ITEMS.register("castle_swift_boots",
            () -> new CastleSwiftArmor(ModArmorMaterials.Castle, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> CASTLE_MANA_HELMET = ITEMS.register("castle_mana_helmet",
            () -> new CastleManaArmor(ModArmorMaterials.Castle, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.CastleItalic)));
    public static final RegistryObject<Item> CASTLE_MANA_CHEST = ITEMS.register("castle_mana_chest",
            () -> new CastleManaArmor(ModArmorMaterials.Castle, ArmorItem.Type.CHESTPLATE, new Item.Properties().rarity(CustomStyle.CastleItalic)));
    public static final RegistryObject<Item> CASTLE_MANA_LEGGINGS = ITEMS.register("castle_mana_leggings",
            () -> new CastleManaArmor(ModArmorMaterials.Castle, ArmorItem.Type.LEGGINGS, new Item.Properties().rarity(CustomStyle.CastleItalic)));
    public static final RegistryObject<Item> CASTLE_MANA_BOOTS = ITEMS.register("castle_mana_boots",
            () -> new CastleManaArmor(ModArmorMaterials.Castle, ArmorItem.Type.BOOTS, new Item.Properties().rarity(CustomStyle.CastleItalic)));

    public static final RegistryObject<Item> SKIN_TEMPLATE_PAPER = ITEMS.register("skin_template_paper",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> STACK_UPGRADE_PAPER = ITEMS.register("stack_upgrade_paper",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> PICK_UPGRADE_PAPER = ITEMS.register("pick_upgrade_paper",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> INCEPTION_UPGRADE_PAPER = ITEMS.register("inception_upgrade_paper",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> GOLD_COIN_LOTTERY = ITEMS.register("gold_coin_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.GoldBold), new ArrayList<>() {{
                add(new NewLotteries.Loot(ModItems.INCEPTION_UPGRADE_PAPER.get().getDefaultInstance(), 0.01));
                add(new NewLotteries.Loot(ModItems.SKIN_TEMPLATE_PAPER.get().getDefaultInstance(), 0.02));
                add(new NewLotteries.Loot(ModItems.PICK_UPGRADE_PAPER.get().getDefaultInstance(), 0.02));
                add(new NewLotteries.Loot(PickaxeItems.TINKER_NETHERITE.get().getDefaultInstance(), 0.02));
                add(new NewLotteries.Loot(PickaxeItems.TINKER_DIAMOND.get().getDefaultInstance(), 0.03));
                add(new NewLotteries.Loot(PickaxeItems.TINKER_GOLD.get().getDefaultInstance(), 0.03));
                add(new NewLotteries.Loot(ModItems.STACK_UPGRADE_PAPER.get().getDefaultInstance(), 0.1));
                add(new NewLotteries.Loot(ModItems.COMMON_LOTTERIES.get().getDefaultInstance(), 0.1));
                add(new NewLotteries.Loot(ModItems.GOLD_COIN.get().getDefaultInstance(), 0.1));
            }}));

    public static final RegistryObject<Item> GOLDEN_APPLE = ITEMS.register("golden_apple",
            () -> new SimpleFoiledItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> REFINED_PIECE = ITEMS.register("refined_piece",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE).stacksTo(16)));

    public static final RegistryObject<Item> TP_TICKET = ITEMS.register("tp_ticket",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.EndBold), false, true, List.of(
                    Te.s(" 使用传送中枢的消耗品", CustomStyle.styleOfEnd)
            )));

    public static final RegistryObject<Item> TP_PASS_1DAY = ITEMS.register("tp_pass_1day",
            () -> new TpPass(new Item.Properties().rarity(CustomStyle.EndBold), 1));
    public static final RegistryObject<Item> TP_PASS_2DAY = ITEMS.register("tp_pass_2day",
            () -> new TpPass(new Item.Properties().rarity(CustomStyle.EndBold), 2));
    public static final RegistryObject<Item> TP_PASS_3DAY = ITEMS.register("tp_pass_3day",
            () -> new TpPass(new Item.Properties().rarity(CustomStyle.EndBold), 3));

    public static final RegistryObject<Item> SUPPLY_BOX_TIER_0 = ITEMS.register("supply_box_tier_0",
            () -> new SupplyBox(new Item.Properties().rarity(CustomStyle.Green), new ArrayList<>() {{
                add(new ItemStack(REVELATION_HEART.get(), 1));
                add(new ItemStack(NOTE_PAPER.get(), 64));
                add(new ItemStack(EndlessInstanceItems.EASTERN_TOWER_PAPER.get(), 1));
            }}));

    public static final RegistryObject<Item> SUPPLY_BOX_TIER_1 = ITEMS.register("supply_box_tier_1",
            () -> new SupplyBox(new Item.Properties().rarity(Rarity.UNCOMMON), new ArrayList<>() {{
                add(new ItemStack(REVELATION_HEART.get(), 2));
                add(new ItemStack(KILL_PAPER_LOOT.get(), 12));
                add(new ItemStack(NOTE_PAPER.get(), 80));
                add(new ItemStack(EndlessInstanceItems.EASTERN_TOWER_PAPER.get(), 2));
                add(new ItemStack(REVELATION_BOOK.get(), 8));
            }}));

    public static final RegistryObject<Item> SUPPLY_BOX_TIER_2 = ITEMS.register("supply_box_tier_2",
            () -> new SupplyBox(new Item.Properties().rarity(Rarity.RARE), new ArrayList<>() {{
                add(new ItemStack(REVELATION_HEART.get(), 4));
                add(new ItemStack(KILL_PAPER_LOOT.get(), 24));
                add(new ItemStack(NOTE_PAPER.get(), 96));
                add(new ItemStack(EndlessInstanceItems.EASTERN_TOWER_PAPER.get(), 3));
                add(new ItemStack(REVELATION_BOOK.get(), 16));
                add(new ItemStack(WORLD_SOUL_5.get(), 80));
            }}));

    public static final RegistryObject<Item> SUPPLY_BOX_TIER_3 = ITEMS.register("supply_box_tier_3",
            () -> new SupplyBox(new Item.Properties().rarity(Rarity.EPIC), new ArrayList<>() {{
                add(new ItemStack(REVELATION_HEART.get(), 6));
                add(new ItemStack(KILL_PAPER_LOOT.get(), 48));
                add(new ItemStack(NOTE_PAPER.get(), 128));
                add(new ItemStack(EndlessInstanceItems.EASTERN_TOWER_PAPER.get(), 4));
                add(new ItemStack(REVELATION_BOOK.get(), 32));
                add(new ItemStack(WORLD_SOUL_5.get(), 200));
            }}));

    public static final RegistryObject<Item> SENIOR_POTION_SUPPLY = ITEMS.register("senior_potion_supply",
            () -> new SupplyBox(new Item.Properties().rarity(Rarity.UNCOMMON), new ArrayList<>() {{
                add(new ItemStack(EX_HARVEST_CON_POTION.get(), 8));
                add(new ItemStack(DAMAGE_ENHANCE_CON_POTION.get(), 8));
                add(new ItemStack(ATTACK_DAMAGE_ENHANCE_CON_POTION.get(), 8));
                add(new ItemStack(MANA_DAMAGE_ENHANCE_CON_POTION.get(), 8));
                add(new ItemStack(GIANT_CON_POTION.get(), 8));
                add(new ItemStack(STONE_CON_POTION.get(), 8));
            }}));

    public static final RegistryObject<Item> ORE_SUPPLY = ITEMS.register("ore_supply",
            () -> new SupplyBox(new Item.Properties().rarity(Rarity.UNCOMMON), new ArrayList<>() {{
                add(new ItemStack(Items.IRON_INGOT, 64));
                add(new ItemStack(Items.COPPER_INGOT, 64));
                add(new ItemStack(Items.DIAMOND, 16));
                add(new ItemStack(OreItems.WRAQ_ORE_1_ITEM.get(), 16));
                add(new ItemStack(OreItems.WRAQ_ORE_2_ITEM.get(), 16));
                add(new ItemStack(OreItems.WRAQ_ORE_3_ITEM.get(), 16));
                add(new ItemStack(OreItems.WRAQ_ORE_4_ITEM.get(), 16));
            }}));

    public static final RegistryObject<Item> JUNIOR_SUPPLY = ITEMS.register("junior_supply",
            () -> new SupplyBox(new Item.Properties().rarity(Rarity.UNCOMMON), new ArrayList<>() {{
                add(new ItemStack(REPUTATION_MEDAL.get(), 1));
                add(new ItemStack(GEM_PIECE.get(), 16));
                add(new ItemStack(GOLD_COIN.get(), 8));
                add(new ItemStack(REVELATION_BOOK.get(), 32));
                add(new ItemStack(TP_TICKET.get(), 6));
            }}));

    public static final RegistryObject<Item> SENIOR_SUPPLY = ITEMS.register("senior_supply",
            () -> new SupplyBox(new Item.Properties().rarity(Rarity.RARE), new ArrayList<>() {{
                add(new ItemStack(REPUTATION_MEDAL.get(), 4));
                add(new ItemStack(COMPLETE_GEM.get(), 1));
                add(new ItemStack(GOLD_COIN.get(), 32));
                add(new ItemStack(REVELATION_HEART.get(), 6));
                add(new ItemStack(TP_TICKET.get(), 24));
            }}));

    public static final RegistryObject<Item> SIMPLE_TIER_1_PAPER = ITEMS.register("simple_tier_1_paper",
            () -> new SimpleTierPaper(new Item.Properties().rarity(CustomStyle.Green), 1));
    public static final RegistryObject<Item> SIMPLE_TIER_2_PAPER = ITEMS.register("simple_tier_2_paper",
            () -> new SimpleTierPaper(new Item.Properties().rarity(Rarity.RARE), 2));
    public static final RegistryObject<Item> SIMPLE_TIER_3_PAPER = ITEMS.register("simple_tier_3_paper",
            () -> new SimpleTierPaper(new Item.Properties().rarity(Rarity.EPIC), 3));

    public static final RegistryObject<Item> BOND = ITEMS.register("bond",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.RARE), false, true, List.of(
                    Te.s("联合研院", CustomStyle.styleOfWorld, "发行的一种信用债券"),
                    Te.s("可以用于和贝尔", "交换物品", CustomStyle.styleOfGold),
                    Te.s("也可以在联合银行获取", "每日分红", CustomStyle.styleOfGold)
            )));
    public static final RegistryObject<Item> SPECIAL_BOND = ITEMS.register("special_bond",
            () -> new WraqItem(new Item.Properties().rarity(Rarity.EPIC), false, true, List.of(
                    Te.s("联合研院", CustomStyle.styleOfWorld, "特别发行的债券"),
                    Te.s("可以用于", "提升Rank", ChatFormatting.RED),
                    Te.s("也可以在联合银行获取", "每日分红", CustomStyle.styleOfGold)
            )));

    public static final RegistryObject<Item> GOLDEN_BEANS = ITEMS.register("golden_beans",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GoldBold), false, true, List.of(
                    Te.s("联合研院", CustomStyle.styleOfWorld, "发行的一种等价物"),
                    Te.s("主要通过完成", "委托任务", CustomStyle.styleOfWorld, "获得")
            )));

    public static final RegistryObject<Item> REASON = ITEMS.register("reason",
            () -> new WraqItem(new Item.Properties().rarity(CustomStyle.GoldBold), false, true));

    public static final RegistryObject<Item> ESTATE_KEY = ITEMS.register("estate_key",
            () -> new EstateKey(new Item.Properties().rarity(Rarity.RARE), 0));

    public static final RegistryObject<Item> REAL_ESTATE_KEY = ITEMS.register("real_estate_key",
            () -> new EstateKey(new Item.Properties().rarity(CustomStyle.Gold), 1));

    public static final RegistryObject<Item> SWORD_LOTTERY = ITEMS.register("sword_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.MagmaBold), new ArrayList<>() {{
                List<NewLotteries.Loot> loots = List.of(
                        new NewLotteries.Loot(new ItemStack(UniformItems.ATTACK_CURIOS_4.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(UniformItems.STONE_CURIOS_0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.WORLD_FORGE_STONE.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.FORGE_ENHANCE_3.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.EQUIP_PIECE_5.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.KILL_PAPER_LOOT.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.COMPLETE_GEM.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.REPUTATION_MEDAL.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.GOLD_COIN_BAG.get(), 4), 0.1)
                );
                addAll(loots);
            }}));

    public static final RegistryObject<Item> SWORD_LOTTERY_1 = ITEMS.register("sword_lottery_1",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.MagmaBold), new ArrayList<>() {{
                List<NewLotteries.Loot> loots = List.of(
                        new NewLotteries.Loot(new ItemStack(UniformItems.ATTACK_CURIOS_5.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(UniformItems.STONE_CURIOS_0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.WORLD_FORGE_STONE.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.FORGE_ENHANCE_3.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.EQUIP_PIECE_5.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.KILL_PAPER_LOOT.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.COMPLETE_GEM.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.REPUTATION_MEDAL.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.GOLD_COIN_BAG.get(), 4), 0.1)
                );
                addAll(loots);
            }}, null, SWORD_LOTTERY.get(), false));

    public static final RegistryObject<Item> BOW_LOTTERY = ITEMS.register("bow_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.KazeBold), new ArrayList<>() {{
                List<NewLotteries.Loot> loots = List.of(
                        new NewLotteries.Loot(new ItemStack(UniformItems.BOW_CURIOS_4.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(UniformItems.STONE_CURIOS_0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.WORLD_FORGE_STONE.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.FORGE_ENHANCE_3.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.EQUIP_PIECE_5.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.KILL_PAPER_LOOT.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.COMPLETE_GEM.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.REPUTATION_MEDAL.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.GOLD_COIN_BAG.get(), 4), 0.1)
                );
                addAll(loots);
            }}));

    public static final RegistryObject<Item> BOW_LOTTERY_1 = ITEMS.register("bow_lottery_1",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.KazeBold), new ArrayList<>() {{
                List<NewLotteries.Loot> loots = List.of(
                        new NewLotteries.Loot(new ItemStack(UniformItems.BOW_CURIOS_5.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(UniformItems.STONE_CURIOS_0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.WORLD_FORGE_STONE.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.FORGE_ENHANCE_3.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.EQUIP_PIECE_5.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.KILL_PAPER_LOOT.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.COMPLETE_GEM.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.REPUTATION_MEDAL.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.GOLD_COIN_BAG.get(), 4), 0.1)
                );
                addAll(loots);
            }}, null, BOW_LOTTERY.get(), false));

    public static final RegistryObject<Item> SCEPTRE_LOTTERY = ITEMS.register("sceptre_lottery",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.EvokerBold), new ArrayList<>() {{
                List<NewLotteries.Loot> loots = List.of(
                        new NewLotteries.Loot(new ItemStack(UniformItems.MANA_CURIOS_4.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(UniformItems.STONE_CURIOS_0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.WORLD_FORGE_STONE.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.FORGE_ENHANCE_3.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.EQUIP_PIECE_5.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.KILL_PAPER_LOOT.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.COMPLETE_GEM.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.REPUTATION_MEDAL.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.GOLD_COIN_BAG.get(), 4), 0.1)
                );
                addAll(loots);
            }}));

    public static final RegistryObject<Item> SCEPTRE_LOTTERY_1 = ITEMS.register("sceptre_lottery_1",
            () -> new NewLotteries(new Item.Properties().rarity(CustomStyle.EvokerBold), new ArrayList<>() {{
                List<NewLotteries.Loot> loots = List.of(
                        new NewLotteries.Loot(new ItemStack(UniformItems.MANA_CURIOS_5.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(UniformItems.STONE_CURIOS_0.get()), 0.005),
                        new NewLotteries.Loot(new ItemStack(ModItems.WORLD_FORGE_STONE.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.FORGE_ENHANCE_3.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.EQUIP_PIECE_5.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.KILL_PAPER_LOOT.get(), 4), 0.2),
                        new NewLotteries.Loot(new ItemStack(ModItems.COMPLETE_GEM.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.REPUTATION_MEDAL.get()), 0.1),
                        new NewLotteries.Loot(new ItemStack(ModItems.GOLD_COIN_BAG.get(), 4), 0.1)
                );
                addAll(loots);
            }}, null, SCEPTRE_LOTTERY.get(), false));

    public static final RegistryObject<Item> SELL_ITEM = ITEMS.register("sell_item",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> BUY_ITEM = ITEMS.register("buy_item",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC)));
}
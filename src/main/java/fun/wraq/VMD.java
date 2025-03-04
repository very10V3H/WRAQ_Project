package fun.wraq;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import fun.wraq.blocks.blocks.forge.ForgeRecipe;
import fun.wraq.blocks.entity.ModBlockEntities;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.equip.WraqPickaxe;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.*;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.UniformItems;
import fun.wraq.entities.entities.Boss2.Boss2;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.entities.entities.MainBoss.MainBoss;
import fun.wraq.entities.entities.SakuraMob.SakuraMob;
import fun.wraq.entities.entities.Scarecrow.Scarecrow;
import fun.wraq.events.core.BlockEvent;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.mob.jungle.JungleMobSpawn;
import fun.wraq.events.mob.loot.*;
import fun.wraq.events.server.ThreadPools;
import fun.wraq.files.dataBases.DBConnection;
import fun.wraq.files.dataBases.DataBase;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.system.WorldRecordInfo;
import fun.wraq.process.system.element.ElementItems;
import fun.wraq.process.system.endlessinstance.DailyEndlessInstanceEvent;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.market.MarketInfo;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.process.system.point.PointItems;
import fun.wraq.process.system.profession.pet.allay.AllayPet;
import fun.wraq.process.system.profession.pet.allay.item.AllayItems;
import fun.wraq.process.system.profession.pet.dev.PetScreen;
import fun.wraq.process.system.profession.smith.SmithItems;
import fun.wraq.process.system.randomevent.RandomEvent;
import fun.wraq.process.system.randomevent.RandomEventsHandler;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.process.system.teamInstance.NewTeamInstance;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHandler;
import fun.wraq.process.system.tower.TowerTimeRecord;
import fun.wraq.process.system.vp.VpDataHandler;
import fun.wraq.render.gui.blocks.BrewingScreen;
import fun.wraq.render.gui.blocks.ForgingBlockScreen;
import fun.wraq.render.gui.blocks.FurnaceScreen;
import fun.wraq.render.gui.blocks.InjectBlockScreen;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.gui.testAndHelper.ModMenuTypes;
import fun.wraq.render.gui.villagerTrade.TradeList;
import fun.wraq.render.mobEffects.ModEffects;
import fun.wraq.render.mobEffects.ModPotions;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.series.end.citadel.CitadelItems;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.gems.GemItems;
import fun.wraq.series.instance.blade.BladeItems;
import fun.wraq.series.instance.mixture.MixtureItems;
import fun.wraq.series.instance.quiver.QuiverItems;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import fun.wraq.series.instance.series.purple.PurpleIronCommon;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.overworld.chapter7.C7Items;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
import fun.wraq.series.overworld.sun.SunIslandItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.CuriosApi;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

@Mod(Utils.MOD_ID)
@Mod.EventBusSubscriber
public class VMD {
    public VMD() {
        IEventBus modEvenBus = FMLJavaModLoadingContext.get().getModEventBus();
        UniformItems.ITEMS.register(modEvenBus);
        PickaxeItems.ITEMS.register(modEvenBus);
        OreItems.ITEMS.register(modEvenBus);
        EndlessInstanceItems.ITEMS.register(modEvenBus);
        ModItems.ITEMS.register(modEvenBus);
        C1LootItems.ITEMS.register(modEvenBus);
        C2LootItems.ITEMS.register(modEvenBus);
        C3LootItems.ITEMS.register(modEvenBus);
        C4LootItems.ITEMS.register(modEvenBus);
        C5LootItems.ITEMS.register(modEvenBus);
        C6LootItems.ITEMS.register(modEvenBus);
        C7LootItems.ITEMS.register(modEvenBus);
        SpurItems.ITEMS.register(modEvenBus);
        C7Items.ITEMS.register(modEvenBus);
        ElementItems.ITEMS.register(modEvenBus);
        NewRuneItems.ITEMS.register(modEvenBus);
        GemItems.ITEMS.register(modEvenBus);
        SpecialEventItems.ITEMS.register(modEvenBus);
        QuiverItems.ITEMS.register(modEvenBus);
        BladeItems.ITEMS.register(modEvenBus);
        MixtureItems.ITEMS.register(modEvenBus);
        MoontainItems.ITEMS.register(modEvenBus);
        PointItems.ITEMS.register(modEvenBus);
        CitadelItems.ITEMS.register(modEvenBus);
        SunIslandItems.ITEMS.register(modEvenBus);
        WardenItems.ITEMS.register(modEvenBus);
        HarbingerItems.ITEMS.register(modEvenBus);
        AllayItems.ITEMS.register(modEvenBus);
        MushroomItems.ITEMS.register(modEvenBus);
        SmithItems.ITEMS.register(modEvenBus);
        BunkerItems.ITEMS.register(modEvenBus);
        DivineIslandItems.ITEMS.register(modEvenBus);

        ModBlocks.BLOCKS.register(modEvenBus);
        ModEntityType.ENTITY_TYPES.register(modEvenBus);
        modEvenBus.addListener(this::enqueueIMC);
        modEvenBus.addListener(this::clientStart);
        HAttribute.ATTRIBUTES.register(modEvenBus);
        ModSounds.register(modEvenBus);
        ModEffects.register(modEvenBus);
        ModMenuTypes.register(modEvenBus);
        ModBlockEntities.Register(modEvenBus);
        modEvenBus.addListener(this::Attribute);
        ModPotions.register(modEvenBus);
        ModParticles.register(modEvenBus);
        ModCreativeModeTab.register(modEvenBus);
        modEvenBus.addListener(this::AddItemToTab);
        modEvenBus.addListener(this::commonStart);
    }

    @SubscribeEvent
    public static void serverStartEvent(ServerStartingEvent event) throws SQLException, CommandSyntaxException, ParseException {
        Tick.server = event.getServer();
        RandomEventsHandler.server = event.getServer();
        PlanPlayer.read();

        MarketInfo.marketItemInfoRead(event.getServer().overworld());
        MarketInfo.marketProfitInfoRead(event.getServer().overworld());

        MobSpawn.readKillCount();
        VpDataHandler.firstRead();
        WorldRecordInfo.recordInfoMap = DataBase.readWorldInfo();
        TowerTimeRecord.readFromWorldRecordInfo();
    }

    @SubscribeEvent
    public static void serverStopEvent(ServerStoppingEvent event) throws SQLException {
        BlockEvent.mineAndWoodReset(event.getServer().getLevel(Level.OVERWORLD));
        BlockEvent.netherMineReset(event.getServer().getLevel(Level.NETHER));
        MobSpawn.removeAllMob();
        JungleMobSpawn.removeAllMobs();
        RandomEventsHandler.getRandomEvents().forEach(RandomEvent::reset);
        DailyEndlessInstanceEvent.onServerStop();
        AllayPet.onServerStop();
        MarketInfo.marketItemInfoWrite(event.getServer().overworld());
        MarketInfo.marketProfitInfoWrite(event.getServer().overworld());
        PurpleIronCommon.destroyOnServerStop();

        Connection connection = DataBase.createNewDatabaseConnection();
        Statement statement = connection.createStatement();
        TowerTimeRecord.writeToWorldRecordInfo();
        DataBase.writeWorldInfo(statement);
        statement.close();
        connection.close();

        NoTeamInstanceModule.reset();
        NewTeamInstanceHandler.getInstances().forEach(NewTeamInstance::clear);
        VpDataHandler.write();

        DBConnection.connection.close();
        DBConnection.connection = null;
        LogUtils.getLogger().info("Database connection closed");

        ThreadPools.dataExecutor.shutdown();
    }

    private void commonStart(FMLCommonSetupEvent event) {
        ModNetworking.register();
        replaceAttributeValue((RangedAttribute) Attributes.MAX_HEALTH, Double.MAX_VALUE);
        replaceAttributeValue((RangedAttribute) Attributes.ARMOR, Double.MAX_VALUE);
        replaceAttributeValue((RangedAttribute) Attributes.ARMOR_TOUGHNESS, Double.MAX_VALUE);
        Utils.Init();
        TradeList.setTradeContent();
        ForgeRecipe.forgeDrawRecipeInit();
        ForgeEquipUtils.setZoneForgeItemListMap();
    }

    private void clientStart(FMLClientSetupEvent event) {
        ModItemProperties.addCustomBowProperties();
        MenuScreens.register(ModMenuTypes.FIRST_MENU.get(), ForgingBlockScreen::new);
        MenuScreens.register(ModMenuTypes.BREWING_MENU.get(), BrewingScreen::new);
        MenuScreens.register(ModMenuTypes.INJECT_BLOCK_MENU.get(), InjectBlockScreen::new);
        MenuScreens.register(ModMenuTypes.Furnace_Menu.get(), FurnaceScreen::new);
        MenuScreens.register(ModMenuTypes.PET_MENU.get(), PetScreen::new);

        PlayerAnimationFactory.ANIMATION_DATA_FACTORY.registerFactory(
                new ResourceLocation(Utils.MOD_ID, "animation"),
                42,
                VMD::registerPlayerAnimation);
    }

    private static IAnimation registerPlayerAnimation(AbstractClientPlayer player) {
        //This will be invoked for every new player
        return new ModifierLayer<>();
    }

    protected static void replaceAttributeValue(RangedAttribute attribute, double maxValue) {
        attribute.maxValue = maxValue;
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        ModList.get().isLoaded(CuriosApi.MODID);
    }

    private void Attribute(EntityAttributeCreationEvent event) {
        event.put(ModEntityType.HETITY.get(), MainBoss.HBossAttributes().build());
        event.put(ModEntityType.SakuraMob.get(), SakuraMob.setAttributes());
        event.put(ModEntityType.Scarecrow.get(), Scarecrow.setAttributes());
        event.put(ModEntityType.Boss2.get(), Boss2.setAttributes());
        event.put(ModEntityType.CIVIL.get(), Civil.setAttributes());
    }

    @SubscribeEvent
    public static void onEntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
        event.add(ModEntityType.HETITY.get(), HAttribute.MAXHEALTH.get());
    }

    @SubscribeEvent
    public static void comps(RenderTooltipEvent.GatherComponents event) throws CommandSyntaxException {
        BasicAttributeDescription.NewAttributeDescription(event);
    }

    private void AddItemToTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(ModCreativeModeTab.ELEMENT.getKey())) {
            Item[] items = {
                    ModItems.LifeElementPiece0.get(), ModItems.LifeElementPiece1.get(), ModItems.LifeElementPiece2.get(),
                    ModItems.WaterElementPiece0.get(), ModItems.WaterElementPiece1.get(), ModItems.WaterElementPiece2.get(),
                    ModItems.FireElementPiece0.get(), ModItems.FireElementPiece1.get(), ModItems.FireElementPiece2.get(),
                    ModItems.StoneElementPiece0.get(), ModItems.StoneElementPiece1.get(), ModItems.StoneElementPiece2.get(),
                    ModItems.IceElementPiece0.get(), ModItems.IceElementPiece1.get(), ModItems.IceElementPiece2.get(),
                    ModItems.LightningElementPiece0.get(), ModItems.LightningElementPiece1.get(), ModItems.LightningElementPiece2.get(),
                    ModItems.WindElementPiece0.get(), ModItems.WindElementPiece1.get(), ModItems.WindElementPiece2.get(),
                    ModItems.LifeCrystal0.get(), ModItems.LifeCrystal1.get(), ModItems.LifeCrystal2.get(), ModItems.LifeCrystal3.get(),
                    ModItems.WaterCrystal0.get(), ModItems.WaterCrystal1.get(), ModItems.WaterCrystal2.get(), ModItems.WaterCrystal3.get(),
                    ModItems.FireCrystal0.get(), ModItems.FireCrystal1.get(), ModItems.FireCrystal2.get(), ModItems.FireCrystal3.get(),
                    ModItems.StoneCrystal0.get(), ModItems.StoneCrystal1.get(), ModItems.StoneCrystal2.get(), ModItems.StoneCrystal3.get(),
                    ModItems.IceCrystal0.get(), ModItems.IceCrystal1.get(), ModItems.IceCrystal2.get(), ModItems.IceCrystal3.get(),
                    ModItems.WindCrystal0.get(), ModItems.WindCrystal1.get(), ModItems.WindCrystal2.get(), ModItems.WindCrystal3.get(),
                    ModItems.LightningCrystal0.get(), ModItems.LightningCrystal1.get(), ModItems.LightningCrystal2.get(), ModItems.LightningCrystal3.get(),
                    ModItems.RainbowPowder.get(), ModItems.RainbowCrystal.get(),
                    ModItems.LifeHolyStone0.get(), ModItems.LifeHolyStone1.get(), ModItems.LifeHolyStone2.get(),
                    ModItems.WaterHolyStone0.get(), ModItems.WaterHolyStone1.get(), ModItems.WaterHolyStone2.get(),
                    ModItems.FireHolyStone0.get(), ModItems.FireHolyStone1.get(), ModItems.FireHolyStone2.get(),
                    ModItems.StoneHolyStone0.get(), ModItems.StoneHolyStone1.get(), ModItems.StoneHolyStone2.get(),
                    ModItems.IceHolyStone0.get(), ModItems.IceHolyStone1.get(), ModItems.IceHolyStone2.get(),
                    ModItems.LightningHolyStone0.get(), ModItems.LightningHolyStone1.get(), ModItems.LightningHolyStone2.get(),
                    ModItems.WindHolyStone0.get(), ModItems.WindHolyStone1.get(), ModItems.WindHolyStone2.get(),
            };
            for (Item item : items) event.accept(item);

            for (Object o : ElementItems.ITEMS.getEntries().toArray()) {
                RegistryObject<Item> item = (RegistryObject<Item>) o;
                event.accept(item.get().getDefaultInstance());
            }
        }
        if (event.getTabKey().equals(ModCreativeModeTab.SPECIAL_FESTIVAL.getKey())) {
            SpecialEventItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(event::accept);

            Item[] items = {
                    ModItems.QingTuan.get(), ModItems.QingMingPrefix.get(), ModItems.QingMingGem.get(),
                    ModItems.QingMingForgePaper.get(),
                    ModItems.QingMingAttackRing.get(), ModItems.QingMingBowRing.get(), ModItems.QingMingManaRing.get(),
                    ModItems.OldSilverCoin.get(), ModItems.OldGoldCoin.get(),
                    ModItems.LabourDayForgePaper.get(), ModItems.LabourDayIronPickaxe.get(), ModItems.LabourDayIronHoe.get(),
                    ModItems.LabourDayLottery.get(), ModItems.LabourDayPrefix.get(), ModItems.LabourDayGem.get()
            };
            for (Item item : items) {
                event.accept(item);
            }
        }
        if (event.getTabKey().equals(ModCreativeModeTab.WEAPON_TAB.getKey())) {
            for (Item item : Utils.weaponList) event.accept(item.getDefaultInstance());
        }

        if (event.getTabKey().equals(ModCreativeModeTab.ARMOR_TAB.getKey())) {
            for (Item item : Utils.armorList) event.accept(item.getDefaultInstance());
        }

        if (event.getTabKey().equals(ModCreativeModeTab.BREWING_TAB.getKey())) {
            event.accept(ModItems.Purifier.get().getDefaultInstance());
            event.accept(ModItems.PurifiedWater.get().getDefaultInstance());
            event.accept(ModItems.BrewingNote.get().getDefaultInstance());
            event.accept(ModItems.PlainSolidifiedSoul.get().getDefaultInstance());
            event.accept(ModItems.ForestSolidifiedSoul.get().getDefaultInstance());
            event.accept(ModItems.LakeSolidifiedSoul.get().getDefaultInstance());
            event.accept(ModItems.VolcanoSolidifiedSoul.get().getDefaultInstance());
            event.accept(ModItems.SnowSolidifiedSoul.get().getDefaultInstance());
            event.accept(ModItems.SkySolidifiedSoul.get().getDefaultInstance());
            event.accept(ModItems.EvokerSolidifiedSoul.get().getDefaultInstance());
            event.accept(ModItems.NetherSolidifiedSoul.get().getDefaultInstance());
            event.accept(ModItems.Solidifier.get().getDefaultInstance());
            event.accept(ModItems.Stabilizer.get().getDefaultInstance());
            event.accept(ModItems.Concentrater.get().getDefaultInstance());
            event.accept(ModItems.Splasher.get().getDefaultInstance());

            Item[] item = {

                    ModItems.WaterBottle.get(),
                    ModItems.AttackUpPotion.get(), ModItems.AttackUpConPotion.get(), ModItems.AttackUpLongPotion.get(),
                    ModItems.SplashAttackUpPotion.get(), ModItems.SplashAttackUpConPotion.get(), ModItems.SplashAttackUpLongPotion.get(),
                    ModItems.DefencePenetrationUpPotion.get(), ModItems.DefencePenetrationUpConPotion.get(), ModItems.DefencePenetrationUpLongPotion.get(),
                    ModItems.SplashDefencePenetrationUpPotion.get(), ModItems.SplashDefencePenetrationUpConPotion.get(), ModItems.SplashDefencePenetrationUpLongPotion.get(),
                    ModItems.ManaPenetrationUpPotion.get(), ModItems.ManaPenetrationUpConPotion.get(), ModItems.ManaPenetrationUpLongPotion.get(),
                    ModItems.SplashManaPenetrationUpPotion.get(), ModItems.SplashManaPenetrationUpConPotion.get(), ModItems.SplashManaPenetrationUpLongPotion.get(),
                    ModItems.CooldownUpPotion.get(), ModItems.CooldownUpConPotion.get(), ModItems.CooldownUpLongPotion.get(),
                    ModItems.SplashCooldownUpPotion.get(), ModItems.SplashCooldownUpConPotion.get(), ModItems.SplashCooldownUpLongPotion.get(),
                    ModItems.CritDamageUpPotion.get(), ModItems.CritDamageUpConPotion.get(), ModItems.CritDamageUpLongPotion.get(),
                    ModItems.SplashCritDamageUpPotion.get(), ModItems.SplashCritDamageUpConPotion.get(), ModItems.SplashCritDamageUpLongPotion.get(),

                    ModItems.CritRateUpPotion.get(), ModItems.CritRateUpConPotion.get(), ModItems.CritRateUpLongPotion.get(),
                    ModItems.SplashCritRateUpPotion.get(), ModItems.SplashCritRateUpConPotion.get(), ModItems.SplashCritRateUpLongPotion.get(),
                    ModItems.DefenceUpPotion.get(), ModItems.DefenceUpConPotion.get(), ModItems.DefenceUpLongPotion.get(),
                    ModItems.SplashDefenceUpPotion.get(), ModItems.SplashDefenceUpConPotion.get(), ModItems.SplashDefenceUpLongPotion.get(),
                    ModItems.HealthStealUpPotion.get(), ModItems.HealthStealUpConPotion.get(), ModItems.HealthStealUpLongPotion.get(),
                    ModItems.SplashHealthStealUpPotion.get(), ModItems.SplashHealthStealUpConPotion.get(), ModItems.SplashHealthStealUpLongPotion.get(),
                    ModItems.ManaDamageUpPotion.get(), ModItems.ManaDamageUpConPotion.get(), ModItems.ManaDamageUpLongPotion.get(),
                    ModItems.SplashManaDamageUpPotion.get(), ModItems.SplashManaDamageUpConPotion.get(), ModItems.SplashManaDamageUpLongPotion.get(),
                    ModItems.ManaDefenceUpPotion.get(), ModItems.ManaDefenceUpConPotion.get(), ModItems.ManaDefenceUpLongPotion.get(),
                    ModItems.SplashManaDefenceUpPotion.get(), ModItems.SplashManaDefenceUpConPotion.get(), ModItems.SplashManaDefenceUpLongPotion.get(),

                    ModItems.ManaRecoverUpPotion.get(), ModItems.ManaRecoverUpConPotion.get(), ModItems.ManaRecoverUpLongPotion.get(),
                    ModItems.SplashManaRecoverUpPotion.get(), ModItems.SplashManaRecoverUpConPotion.get(), ModItems.SplashManaRecoverUpLongPotion.get(),
                    ModItems.MovementSpeedUpPotion.get(), ModItems.MovementSpeedUpConPotion.get(), ModItems.MovementSpeedUpLongPotion.get(),
                    ModItems.SplashMovementSpeedUpPotion.get(), ModItems.SplashMovementSpeedUpConPotion.get(), ModItems.SplashMovementSpeedUpLongPotion.get(),
                    ModItems.HealthRecoverUpPotion.get(), ModItems.HealthRecoverUpConPotion.get(), ModItems.HealthRecoverUpLongPotion.get(),
                    ModItems.SplashHealthRecoverUpPotion.get(), ModItems.SplashHealthRecoverUpConPotion.get(), ModItems.SplashHealthRecoverUpLongPotion.get(),

                    ModItems.DamageEnhancePotion.get(), ModItems.DamageEnhanceConPotion.get(), ModItems.DamageEnhanceLongPotion.get(),
                    ModItems.SplashDamageEnhancePotion.get(), ModItems.SplashDamageEnhanceConPotion.get(), ModItems.SplashDamageEnhanceLongPotion.get(),
                    ModItems.AttackDamageEnhancePotion.get(), ModItems.AttackDamageEnhanceConPotion.get(), ModItems.AttackDamageEnhanceLongPotion.get(),
                    ModItems.SplashAttackDamageEnhancePotion.get(), ModItems.SplashAttackDamageEnhanceConPotion.get(), ModItems.SplashAttackDamageEnhanceLongPotion.get(),
                    ModItems.ManaDamageEnhancePotion.get(), ModItems.ManaDamageEnhanceConPotion.get(), ModItems.ManaDamageEnhanceLongPotion.get(),
                    ModItems.SplashManaDamageEnhancePotion.get(), ModItems.SplashManaDamageEnhanceConPotion.get(), ModItems.SplashManaDamageEnhanceLongPotion.get(),
                    ModItems.GiantPotion.get(), ModItems.GiantConPotion.get(), ModItems.GiantLongPotion.get(),
                    ModItems.SplashGiantPotion.get(), ModItems.SplashGiantConPotion.get(), ModItems.SplashGiantLongPotion.get(),
                    ModItems.StonePotion.get(), ModItems.StoneConPotion.get(), ModItems.StoneLongPotion.get(),
                    ModItems.SplashStonePotion.get(), ModItems.SplashStoneConPotion.get(), ModItems.SplashStoneLongPotion.get(),
                    ModItems.ExHarvestPotion.get(), ModItems.ExHarvestConPotion.get(), ModItems.ExHarvestLongPotion.get(),
                    ModItems.SplashExHarvestPotion.get(), ModItems.SplashExHarvestConPotion.get(), ModItems.SplashExHarvestLongPotion.get()
            };

            for (Item item1 : item) event.accept(item1.getDefaultInstance());
        }

        if (event.getTabKey().equals(ModCreativeModeTab.CODEMANA_TAB.getKey())) {
            event.accept(ModItems.CodeSceptre.get().getDefaultInstance());
            event.accept(ModItems.BreakMana.get().getDefaultInstance());
            event.accept(ModItems.DamageMana.get().getDefaultInstance());
            event.accept(ModItems.EffectMana.get().getDefaultInstance());
            event.accept(ModItems.GatherMana.get().getDefaultInstance());
            event.accept(ModItems.KazeMana.get().getDefaultInstance());
            event.accept(ModItems.LightningMana.get().getDefaultInstance());
            event.accept(ModItems.RangeMana.get().getDefaultInstance());
            event.accept(ModItems.SnowMana.get().getDefaultInstance());
            event.accept(ModItems.LightningPower.get().getDefaultInstance());
            event.accept(ModItems.ManaModel.get().getDefaultInstance());
        }

        if (event.getTabKey().equals(ModCreativeModeTab.SARMOR_TAB.getKey())) {
            event.accept(ModItems.SHelmet.get().getDefaultInstance());
            event.accept(ModItems.SChest.get().getDefaultInstance());
            event.accept(ModItems.SLeggings.get().getDefaultInstance());
            event.accept(ModItems.SBoots.get().getDefaultInstance());
            event.accept(ModItems.SunOintment0.get().getDefaultInstance());
            event.accept(ModItems.SunOintment1.get().getDefaultInstance());
            event.accept(ModItems.SunOintment2.get().getDefaultInstance());
            event.accept(ModItems.LakeOintment0.get().getDefaultInstance());
            event.accept(ModItems.LakeOintment1.get().getDefaultInstance());
            event.accept(ModItems.LakeOintment2.get().getDefaultInstance());
            event.accept(ModItems.VolcanoOintment0.get().getDefaultInstance());
            event.accept(ModItems.VolcanoOintment1.get().getDefaultInstance());
            event.accept(ModItems.VolcanoOintment2.get().getDefaultInstance());
            event.accept(ModItems.SnowOintment0.get().getDefaultInstance());
            event.accept(ModItems.SnowOintment1.get().getDefaultInstance());
            event.accept(ModItems.SnowOintment2.get().getDefaultInstance());
            event.accept(ModItems.SkyOintment0.get().getDefaultInstance());
            event.accept(ModItems.SkyOintment1.get().getDefaultInstance());
            event.accept(ModItems.SkyOintment2.get().getDefaultInstance());
            event.accept(ModItems.ManaOintment0.get().getDefaultInstance());
            event.accept(ModItems.ManaOintment1.get().getDefaultInstance());
            event.accept(ModItems.ManaOintment2.get().getDefaultInstance());
            event.accept(ModItems.NetherOintment0.get().getDefaultInstance());
            event.accept(ModItems.NetherOintment1.get().getDefaultInstance());
            event.accept(ModItems.NetherOintment2.get().getDefaultInstance());
        }

        if (event.getTabKey().equals(ModCreativeModeTab.DROPSANDMATERIAL_TAB.getKey())) {
            for (Item item : Display.materialList) event.accept(item.getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.LOOT_EQUIP.getKey())) {
            C1LootItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(event::accept);
            C2LootItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(event::accept);
            C3LootItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(event::accept);
            C4LootItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(event::accept);
            C5LootItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(event::accept);
            C6LootItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(event::accept);
            C7LootItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.SPUR_ITEMS.getKey())) {
            SpurItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.FORGING_TAB.getKey())) {
            Item[] items = {
                    ModItems.Pearl1.get(), ModItems.Pearl2.get(),
                    ModItems.Pearl3.get(), ModItems.Pearl4.get(),
                    ModItems.Pearl5.get(), ModItems.Pearl6.get(),
                    ModItems.WORLD_FORGE_STONE.get(), ModItems.Splasher.get(),
                    ModItems.WoodHammer.get(), ModItems.StoneHammer.get(),
                    ModItems.CopperHammer.get(), ModItems.IronHammer.get(),
                    ModItems.GoldHammer.get(), ModItems.DiamondHammer.get(),
                    ModItems.EMERALD_HAMMER.get(), ModItems.NETHER_HAMMER.get(),
                    ModItems.END_HAMMER.get(),
                    ModItems.equipPiece0.get(), ModItems.equipPiece1.get(),
                    ModItems.equipPiece2.get(), ModItems.equipPiece3.get(),
                    ModItems.equipPiece4.get(), ModItems.equipPiece5.get(),
                    ModItems.equipPiece6.get(), ModItems.equipPiece7.get(),
                    ModItems.equipPiece8.get(), ModItems.equipPiece9.get(),
                    ModItems.equipPiece10.get(), ModItems.equipPiece11.get(),
                    ModItems.equipPiece12.get(), ModItems.equipPiece13.get()
            };
            for (Item item : items) event.accept(item.getDefaultInstance());
            event.accept(ModItems.SpeIron.get().getDefaultInstance());
            event.accept(ModItems.ForgingStone0.get().getDefaultInstance());
            event.accept(ModItems.ForgingStone1.get().getDefaultInstance());
            event.accept(ModItems.ForgingStone2.get().getDefaultInstance());
            event.accept(ModItems.ForgeEnhance0.get().getDefaultInstance());
            event.accept(ModItems.ForgeEnhance1.get().getDefaultInstance());
            event.accept(ModItems.ForgeEnhance2.get().getDefaultInstance());
            event.accept(ModItems.ForgeEnhance3.get().getDefaultInstance());
            event.accept(ModItems.ForgeProtect.get().getDefaultInstance());
            event.accept(GemItems.DISMANTLE.get().getDefaultInstance());
            event.accept(ModItems.FORGE_TEMPLATE.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.MONEYANDMISSION_TAB.getKey())) {
            event.accept(ModItems.pickUpgradePaper.get().getDefaultInstance());
            event.accept(ModItems.skinTemplatePaper.get().getDefaultInstance());
            event.accept(ModItems.stackUpgradePaper.get().getDefaultInstance());

            event.accept(ModItems.GEM_PIECE.get().getDefaultInstance());
            event.accept(ModItems.ROSE_GOLD_COIN.get().getDefaultInstance());
            event.accept(ModItems.GOLD_COIN.get().getDefaultInstance());
            event.accept(ModItems.silverCoin.get().getDefaultInstance());
            event.accept(ModItems.copperCoin.get().getDefaultInstance());
            event.accept(ModItems.SignInReward.get().getDefaultInstance());
            event.accept(ModItems.SmartPhone.get().getDefaultInstance());
            event.accept(ModItems.DailyMission.get().getDefaultInstance());
            event.accept(ModItems.ATTACK_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.DEFENCE_PENETRATION_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.CRIT_RATE_UP_POTIONBAG.get().getDefaultInstance());
            event.accept(ModItems.CRIT_DAMAGE_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.MANA_DAMAGE_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.MANA_PENETRATION_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.MANA_RECOVER_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.POWER_RELEASE_SPEED_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.HEALTH_STEAL_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.DEFENCE_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.MANA_DEFENCE_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.MOVEMENT_SPEED_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.GoldCoinBag.get().getDefaultInstance());
            event.accept(ModItems.BackPackTickets.get().getDefaultInstance());
            event.accept(ModItems.COMPLETE_GEM.get().getDefaultInstance());
            event.accept(ModItems.ReputationMedal.get().getDefaultInstance());
            event.accept(ModItems.commonLotteries.get().getDefaultInstance());
            event.accept(ModItems.UnCommonLotteries.get().getDefaultInstance());
            event.accept(ModItems.RevelationBook.get().getDefaultInstance());
            event.accept(ModItems.REVELATION_HEART.get().getDefaultInstance());
            event.accept(ModItems.U_Disk.get().getDefaultInstance());
            event.accept(ModItems.IceLoot.get().getDefaultInstance());
            event.accept(SpecialEventItems.FIRE_WORK_GUN.get().getDefaultInstance());
            event.accept(SpecialEventItems.MONEY.get().getDefaultInstance());
            event.accept(SpecialEventItems.RED_ENVELOPE.get().getDefaultInstance());
            event.accept(SpecialEventItems.SPRING_GOLD_COIN.get().getDefaultInstance());
            event.accept(ModItems.DevilLoot.get().getDefaultInstance());
            event.accept(ModItems.DragonPrefix.get().getDefaultInstance());
            event.accept(ModItems.MoonLoot.get().getDefaultInstance());
            event.accept(ModItems.CastleLoot.get().getDefaultInstance());
            event.accept(ModItems.LotteryStar.get().getDefaultInstance());
            event.accept(ModItems.LotteryPrefix.get().getDefaultInstance());

            event.accept(ModItems.SwordLottery.get().getDefaultInstance());
            event.accept(ModItems.BowLottery.get().getDefaultInstance());
            event.accept(ModItems.SceptreLottery.get().getDefaultInstance());

            event.accept(ModItems.notePaper.get().getDefaultInstance());
            Item[] items = {
                    ModItems.supplyBoxTier0.get(),
                    ModItems.supplyBoxTier1.get(), ModItems.supplyBoxTier2.get(), ModItems.supplyBoxTier3.get(),
                    ModItems.ORE_SUPPLY.get(), ModItems.SENIOR_POTION_SUPPLY.get(),
                    ModItems.JUNIOR_SUPPLY.get(), ModItems.SENIOR_SUPPLY.get(),
                    ModItems.simpleTier1Paper.get(), ModItems.simpleTier2Paper.get(), ModItems.simpleTier3Paper.get(),
                    ModItems.goldCoinLottery.get(), ModItems.GOLDEN_BEANS.get(),
                    ModItems.BOND.get(), ModItems.SPECIAL_BOND.get()
            };
            for (Item item : items) event.accept(item.getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.CURIOS_AND_GEMS.getKey())) {

            for (Item item : Utils.curiosList) event.accept(item.getDefaultInstance());

            for (Object o : GemItems.ITEMS.getEntries().toArray()) {
                RegistryObject<Item> item = (RegistryObject<Item>) o;
                event.accept(item.get().getDefaultInstance());
            }
        }

        if (event.getTabKey().equals(ModCreativeModeTab.MISC_TAB.getKey())) {
            event.accept(ModItems.Main0.get().getDefaultInstance());
            event.accept(ModItems.Note_0.get().getDefaultInstance());
            event.accept(ModItems.ExploreNote.get().getDefaultInstance());
            event.accept(ModItems.ForNew.get().getDefaultInstance());
            event.accept(ModItems.BackSpawn.get().getDefaultInstance());
            event.accept(ModItems.tickettosky.get().getDefaultInstance());
            event.accept(ModItems.Note_1.get().getDefaultInstance());
            event.accept(ModItems.Note_2.get().getDefaultInstance());
            event.accept(ModItems.Note_3.get().getDefaultInstance());
            event.accept(ModItems.LightningChange.get().getDefaultInstance());
            event.accept(ModItems.ID_Card.get().getDefaultInstance());
            event.accept(ModItems.Ps_Bottle0.get().getDefaultInstance());
            event.accept(ModItems.Ps_Bottle1.get().getDefaultInstance());
            event.accept(ModItems.Ps_Bottle2.get().getDefaultInstance());
            event.accept(ModItems.ParkourMedal.get().getDefaultInstance());
            event.accept(ModItems.KillPaperLoot.get().getDefaultInstance());
            event.accept(ModItems.MopUpPaperLoot.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.DEVELOPMENT_TAB.getKey())) {
            event.accept(ModItems.RAILWAY_PILLAR_SET_TOOL.get().getDefaultInstance());
            event.accept(ModItems.GetTime.get().getDefaultInstance());
            event.accept(ModItems.ItemIDCheck.get().getDefaultInstance());
            event.accept(ModItems.attributecheck.get().getDefaultInstance());
            event.accept(ModItems.hovertest.get().getDefaultInstance());
            event.accept(ModItems.ArrowItem.get().getDefaultInstance());
            event.accept(ModItems.Security.get().getDefaultInstance());
            event.accept(ModItems.ResetSecurity.get().getDefaultInstance());
            event.accept(ModItems.tick.get().getDefaultInstance());
            event.accept(ModItems.EntityCopy.get().getDefaultInstance());
            event.accept(ModItems.BlockReset.get().getDefaultInstance());
            event.accept(ModItems.quartzcheck.get().getDefaultInstance());
            event.accept(ModItems.GuiOpen.get().getDefaultInstance());
            event.accept(ModItems.BarrierSet.get().getDefaultInstance());
            event.accept(ModItems.WaterSet.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.WORLD_SOUL.getKey())) {
            event.accept(ModItems.WORLD_SOUL_1.get().getDefaultInstance());
            event.accept(ModItems.WORLD_SOUL_2.get().getDefaultInstance());
            event.accept(ModItems.WORLD_SOUL_3.get().getDefaultInstance());
            event.accept(ModItems.WORLD_SOUL_4.get().getDefaultInstance());
            event.accept(ModItems.WORLD_SOUL_5.get().getDefaultInstance());
            event.accept(ModItems.SoulSword.get().getDefaultInstance());
            event.accept(ModItems.SoulBow.get().getDefaultInstance());
            event.accept(ModItems.SoulSceptre.get().getDefaultInstance());
            event.accept(ModItems.SkillReset.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.KILL_PAPER.getKey())) {
            Item[] items = {
                    ModItems.killPaper.get(), ModItems.killPaperL.get()
            };
            for (Item item : items) event.accept(item.getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.FURNACE.getKey())) {


            OreItems.ITEMS.getEntries()
                    .stream().map(RegistryObject::get)
                    .forEach(event::accept);

            WraqPickaxe.PICKAXE_ITEM_LIST.forEach(event::accept);

            event.accept(ModItems.CrudeCoal.get().getDefaultInstance());
            event.accept(ModItems.HotCoal.get().getDefaultInstance());
            event.accept(ModItems.RefiningCoal.get().getDefaultInstance());
            event.accept(ModItems.BlazeCoal.get().getDefaultInstance());
            event.accept(ModItems.CrudeIron.get().getDefaultInstance());
            event.accept(ModItems.HotIron.get().getDefaultInstance());
            event.accept(ModItems.RefiningIron.get().getDefaultInstance());
            event.accept(ModItems.CrudeCopper.get().getDefaultInstance());
            event.accept(ModItems.HotCopper.get().getDefaultInstance());
            event.accept(ModItems.RefiningCopper.get().getDefaultInstance());
            event.accept(ModItems.CrudeGold.get().getDefaultInstance());
            event.accept(ModItems.BlazeGold.get().getDefaultInstance());
            event.accept(ModItems.RefiningGold.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.CUSTOMIZED.getKey())) {
            UniformItems.ITEMS.getEntries()
                    .stream().map(RegistryObject::get)
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.BLOCK.getKey())) {
            ModBlocks.BLOCKS.getEntries()
                    .stream().map(block -> block.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.POINT.getKey())) {
            PointItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.MOONTAIN.getKey())) {
            MoontainItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.SUN_ISLAND.getKey())) {
            SunIslandItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.HARBINGER.getKey())) {
            HarbingerItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
            BunkerItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.ENDLESS_INSTANCE.getKey())) {
            EndlessInstanceItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.PROFESSION.getKey())) {
            AllayItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
            SmithItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
    }
}



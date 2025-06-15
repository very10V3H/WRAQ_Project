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
import fun.wraq.customized.composites.CompositesItems;
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
import fun.wraq.process.system.cooking.CookingItems;
import fun.wraq.process.system.element.ElementItems;
import fun.wraq.process.system.endlessinstance.DailyEndlessInstanceEvent;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.instance.MopUpPaperItems;
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
import fun.wraq.render.mobEffects.ModEffects;
import fun.wraq.render.mobEffects.ModPotions;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.series.crystal.CrystalItems;
import fun.wraq.series.end.citadel.CitadelItems;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.gems.GemItems;
import fun.wraq.series.holy.HolyItems;
import fun.wraq.series.holy.ice.IceHolyItems;
import fun.wraq.series.instance.blade.BladeItems;
import fun.wraq.series.instance.mixture.MixtureItems;
import fun.wraq.series.instance.quiver.QuiverItems;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import fun.wraq.series.instance.series.mushroom.gem.MushroomParasitismGem;
import fun.wraq.series.instance.series.purple.PurpleIronCommon;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.overworld.chapter7.C7Items;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.extraordinary.ExtraordinaryItems;
import fun.wraq.series.overworld.newarea.NewAreaItems;
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
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.CuriosApi;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Map;

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
        CompositesItems.ITEMS.register(modEvenBus);
        HolyItems.ITEMS.register(modEvenBus);
        IceHolyItems.ITEMS.register(modEvenBus);
        NewAreaItems.ITEMS.register(modEvenBus);
        ExtraordinaryItems.ITEMS.register(modEvenBus);
        MopUpPaperItems.ITEMS.register(modEvenBus);
        CookingItems.ITEMS.register(modEvenBus);
        CrystalItems.ITEMS.register(modEvenBus);

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
        MushroomParasitismGem.clearItemEntity();

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
                    ModItems.LIFE_ELEMENT_PIECE_0.get(), ModItems.LIFE_ELEMENT_PIECE_1.get(), ModItems.LIFE_ELEMENT_PIECE_2.get(),
                    ModItems.WATER_ELEMENT_PIECE_0.get(), ModItems.WATER_ELEMENT_PIECE_1.get(), ModItems.WATER_ELEMENT_PIECE_2.get(),
                    ModItems.FIRE_ELEMENT_PIECE_0.get(), ModItems.FIRE_ELEMENT_PIECE_1.get(), ModItems.FIRE_ELEMENT_PIECE_2.get(),
                    ModItems.STONE_ELEMENT_PIECE_0.get(), ModItems.STONE_ELEMENT_PIECE_1.get(), ModItems.STONE_ELEMENT_PIECE_2.get(),
                    ModItems.ICE_ELEMENT_PIECE_0.get(), ModItems.ICE_ELEMENT_PIECE_1.get(), ModItems.ICE_ELEMENT_PIECE_2.get(),
                    ModItems.LIGHTNING_ELEMENT_PIECE_0.get(), ModItems.LIGHTNING_ELEMENT_PIECE_1.get(), ModItems.LIGHTNING_ELEMENT_PIECE_2.get(),
                    ModItems.WIND_ELEMENT_PIECE_0.get(), ModItems.WIND_ELEMENT_PIECE_1.get(), ModItems.WIND_ELEMENT_PIECE_2.get(),
                    ModItems.LIFE_CRYSTAL_0.get(), ModItems.LIFE_CRYSTAL_1.get(), ModItems.LIFE_CRYSTAL_2.get(), ModItems.LIFE_CRYSTAL_3.get(),
                    ModItems.WATER_CRYSTAL_0.get(), ModItems.WATER_CRYSTAL_1.get(), ModItems.WATER_CRYSTAL_2.get(), ModItems.WATER_CRYSTAL_3.get(),
                    ModItems.FIRE_CRYSTAL_0.get(), ModItems.FIRE_CRYSTAL_1.get(), ModItems.FIRE_CRYSTAL_2.get(), ModItems.FIRE_CRYSTAL_3.get(),
                    ModItems.STONE_CRYSTAL_0.get(), ModItems.STONE_CRYSTAL_1.get(), ModItems.STONE_CRYSTAL_2.get(), ModItems.STONE_CRYSTAL_3.get(),
                    ModItems.ICE_CRYSTAL_0.get(), ModItems.ICE_CRYSTAL_1.get(), ModItems.ICE_CRYSTAL_2.get(), ModItems.ICE_CRYSTAL_3.get(),
                    ModItems.WIND_CRYSTAL_0.get(), ModItems.WIND_CRYSTAL_1.get(), ModItems.WIND_CRYSTAL_2.get(), ModItems.WIND_CRYSTAL_3.get(),
                    ModItems.LIGHTNING_CRYSTAL_0.get(), ModItems.LIGHTNING_CRYSTAL_1.get(), ModItems.LIGHTNING_CRYSTAL_2.get(), ModItems.LIGHTNING_CRYSTAL_3.get(),
                    ModItems.RAINBOW_POWDER.get(), ModItems.RAINBOW_CRYSTAL.get(),
                    ModItems.LIFE_HOLY_STONE_0.get(), ModItems.LIFE_HOLY_STONE_1.get(), ModItems.LIFE_HOLY_STONE_2.get(),
                    ModItems.WATER_HOLY_STONE_0.get(), ModItems.WATER_HOLY_STONE_1.get(), ModItems.WATER_HOLY_STONE_2.get(),
                    ModItems.FIRE_HOLY_STONE_0.get(), ModItems.FIRE_HOLY_STONE_1.get(), ModItems.FIRE_HOLY_STONE_2.get(),
                    ModItems.STONE_HOLY_STONE_0.get(), ModItems.STONE_HOLY_STONE_1.get(), ModItems.STONE_HOLY_STONE_2.get(),
                    ModItems.ICE_HOLY_STONE_0.get(), ModItems.ICE_HOLY_STONE_1.get(), ModItems.ICE_HOLY_STONE_2.get(),
                    ModItems.LIGHTNING_HOLY_STONE_0.get(), ModItems.LIGHTNING_HOLY_STONE_1.get(), ModItems.LIGHTNING_HOLY_STONE_2.get(),
                    ModItems.WIND_HOLY_STONE_0.get(), ModItems.WIND_HOLY_STONE_1.get(), ModItems.WIND_HOLY_STONE_2.get(),
            };
            for (Item item : items) event.accept(item);

            for (Object o : ElementItems.ITEMS.getEntries().toArray()) {
                RegistryObject<Item> item = (RegistryObject<Item>) o;
                event.accept(item.get().getDefaultInstance());
            }
        }
        if (event.getTabKey().equals(ModCreativeModeTab.SPECIAL_FESTIVAL.getKey())) {
            SpecialEventItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.WEAPON_TAB.getKey())) {
            for (Item item : Utils.weaponList) event.accept(item.getDefaultInstance());
        }

        if (event.getTabKey().equals(ModCreativeModeTab.ARMOR_TAB.getKey())) {
            for (Item item : Utils.armorList) event.accept(item.getDefaultInstance());
        }

        if (event.getTabKey().equals(ModCreativeModeTab.BREWING_TAB.getKey())) {
            event.accept(ModItems.PURIFIER.get().getDefaultInstance());
            event.accept(ModItems.PURIFIED_WATER.get().getDefaultInstance());
            event.accept(ModItems.BREWING_NOTE.get().getDefaultInstance());
            event.accept(ModItems.PLAIN_SOLIDIFIED_SOUL.get().getDefaultInstance());
            event.accept(ModItems.FOREST_SOLIDIFIED_SOUL.get().getDefaultInstance());
            event.accept(ModItems.LAKE_SOLIDIFIED_SOUL.get().getDefaultInstance());
            event.accept(ModItems.VOLCANO_SOLIDIFIED_SOUL.get().getDefaultInstance());
            event.accept(ModItems.SNOW_SOLIDIFIED_SOUL.get().getDefaultInstance());
            event.accept(ModItems.SKY_SOLIDIFIED_SOUL.get().getDefaultInstance());
            event.accept(ModItems.EVOKER_SOLIDIFIED_SOUL.get().getDefaultInstance());
            event.accept(ModItems.NETHER_SOLIDIFIED_SOUL.get().getDefaultInstance());
            event.accept(ModItems.SOLIDIFIER.get().getDefaultInstance());
            event.accept(ModItems.STABILIZER.get().getDefaultInstance());
            event.accept(ModItems.CONCENTRATER.get().getDefaultInstance());
            event.accept(ModItems.SPLASHER.get().getDefaultInstance());

            Item[] item = {

                    ModItems.WATER_BOTTLE.get(),
                    ModItems.ATTACKUP_POTION.get(), ModItems.ATTACKUP_CON_POTION.get(), ModItems.ATTACKUP_LONG_POTION.get(),
                    ModItems.SPLASH_ATTACKUP_POTION.get(), ModItems.SPLASH_ATTACKUP_CON_POTION.get(), ModItems.SPLASH_ATTACKUP_LONG_POTION.get(),
                    ModItems.DEFENCE_PENETRATION_UP_POTION.get(), ModItems.DEFENCE_PENETRATION_UP_CON_POTION.get(), ModItems.DEFENCE_PENETRATION_UP_LONG_POTION.get(),
                    ModItems.SPLASH_DEFENCE_PENETRATION_UP_POTION.get(), ModItems.SPLASH_DEFENCE_PENETRATION_UP_CON_POTION.get(), ModItems.SPLASH_DEFENCE_PENETRATION_UP_LONG_POTION.get(),
                    ModItems.MANA_PENETRATION_UP_POTION.get(), ModItems.MANA_PENETRATION_UP_CON_POTION.get(), ModItems.MANA_PENETRATION_UP_LONG_POTION.get(),
                    ModItems.SPLASH_MANA_PENETRATION_UP_POTION.get(), ModItems.SPLASH_MANA_PENETRATION_UP_CON_POTION.get(), ModItems.SPLASH_MANA_PENETRATION_UP_LONG_POTION.get(),
                    ModItems.COOLDOWN_UP_POTION.get(), ModItems.COOLDOWN_UP_CON_POTION.get(), ModItems.COOLDOWN_UP_LONG_POTION.get(),
                    ModItems.SPLASH_COOLDOWN_UP_POTION.get(), ModItems.SPLASH_COOLDOWN_UP_CON_POTION.get(), ModItems.SPLASH_COOLDOWN_UP_LONG_POTION.get(),
                    ModItems.CRIT_DAMAGE_UP_POTION.get(), ModItems.CRIT_DAMAGE_UP_CON_POTION.get(), ModItems.CRIT_DAMAGE_UP_LONG_POTION.get(),
                    ModItems.SPLASH_CRIT_DAMAGE_UP_POTION.get(), ModItems.SPLASH_CRIT_DAMAGE_UP_CON_POTION.get(), ModItems.SPLASH_CRIT_DAMAGE_UP_LONG_POTION.get(),

                    ModItems.CRIT_RATE_UP_POTION.get(), ModItems.CRIT_RATE_UP_CON_POTION.get(), ModItems.CRIT_RATE_UP_LONG_POTION.get(),
                    ModItems.SPLASH_CRIT_RATE_UP_POTION.get(), ModItems.SPLASH_CRIT_RATE_UP_CON_POTION.get(), ModItems.SPLASH_CRIT_RATE_UP_LONG_POTION.get(),
                    ModItems.DEFENCE_UP_POTION.get(), ModItems.DEFENCE_UP_CON_POTION.get(), ModItems.DEFENCE_UP_LONG_POTION.get(),
                    ModItems.SPLASH_DEFENCE_UP_POTION.get(), ModItems.SPLASH_DEFENCE_UP_CON_POTION.get(), ModItems.SPLASH_DEFENCE_UP_LONG_POTION.get(),
                    ModItems.HEALTH_STEAL_UP_POTION.get(), ModItems.HEALTH_STEAL_UP_CON_POTION.get(), ModItems.HEALTH_STEAL_UP_LONG_POTION.get(),
                    ModItems.SPLASH_HEALTH_STEAL_UP_POTION.get(), ModItems.SPLASH_HEALTH_STEAL_UP_CON_POTION.get(), ModItems.SPLASH_HEALTH_STEAL_UP_LONG_POTION.get(),
                    ModItems.MANA_DAMAGE_UP_POTION.get(), ModItems.MANA_DAMAGE_UP_CON_POTION.get(), ModItems.MANA_DAMAGE_UP_LONG_POTION.get(),
                    ModItems.SPLASH_MANA_DAMAGE_UP_POTION.get(), ModItems.SPLASH_MANA_DAMAGE_UP_CON_POTION.get(), ModItems.SPLASH_MANA_DAMAGE_UP_LONG_POTION.get(),
                    ModItems.MANA_DEFENCE_UP_POTION.get(), ModItems.MANA_DEFENCE_UP_CON_POTION.get(), ModItems.MANA_DEFENCE_UP_LONG_POTION.get(),
                    ModItems.SPLASH_MANA_DEFENCE_UP_POTION.get(), ModItems.SPLASH_MANA_DEFENCE_UP_CON_POTION.get(), ModItems.SPLASH_MANA_DEFENCE_UP_LONG_POTION.get(),

                    ModItems.MANA_RECOVER_UP_POTION.get(), ModItems.MANA_RECOVER_UP_CON_POTION.get(), ModItems.MANA_RECOVER_UP_LONG_POTION.get(),
                    ModItems.SPLASH_MANA_RECOVER_UP_POTION.get(), ModItems.SPLASH_MANA_RECOVER_UP_CON_POTION.get(), ModItems.SPLASH_MANA_RECOVER_UP_LONG_POTION.get(),
                    ModItems.MOVEMENT_SPEED_UP_POTION.get(), ModItems.MOVEMENT_SPEED_UP_CON_POTION.get(), ModItems.MOVEMENT_SPEED_UP_LONG_POTION.get(),
                    ModItems.SPLASH_MOVEMENT_SPEED_UP_POTION.get(), ModItems.SPLASH_MOVEMENT_SPEED_UP_CON_POTION.get(), ModItems.SPLASH_MOVEMENT_SPEED_UP_LONG_POTION.get(),
                    ModItems.HEALTH_RECOVER_UP_POTION.get(), ModItems.HEALTH_RECOVER_UP_CON_POTION.get(), ModItems.HEALTH_RECOVER_UP_LONG_POTION.get(),
                    ModItems.SPLASH_HEALTH_RECOVER_UP_POTION.get(), ModItems.SPLASH_HEALTH_RECOVER_UP_CON_POTION.get(), ModItems.SPLASH_HEALTH_RECOVER_UP_LONG_POTION.get(),

                    ModItems.DAMAGE_ENHANCE_POTION.get(), ModItems.DAMAGE_ENHANCE_CON_POTION.get(), ModItems.DAMAGE_ENHANCE_LONG_POTION.get(),
                    ModItems.SPLASH_DAMAGE_ENHANCE_POTION.get(), ModItems.SPLASH_DAMAGE_ENHANCE_CON_POTION.get(), ModItems.SPLASH_DAMAGE_ENHANCE_LONG_POTION.get(),
                    ModItems.ATTACK_DAMAGE_ENHANCE_POTION.get(), ModItems.ATTACK_DAMAGE_ENHANCE_CON_POTION.get(), ModItems.ATTACK_DAMAGE_ENHANCE_LONG_POTION.get(),
                    ModItems.SPLASH_ATTACK_DAMAGE_ENHANCE_POTION.get(), ModItems.SPLASH_ATTACK_DAMAGE_ENHANCE_CON_POTION.get(), ModItems.SPLASH_ATTACK_DAMAGE_ENHANCE_LONG_POTION.get(),
                    ModItems.MANA_DAMAGE_ENHANCE_POTION.get(), ModItems.MANA_DAMAGE_ENHANCE_CON_POTION.get(), ModItems.MANA_DAMAGE_ENHANCE_LONG_POTION.get(),
                    ModItems.SPLASH_MANA_DAMAGE_ENHANCE_POTION.get(), ModItems.SPLASH_MANA_DAMAGE_ENHANCE_CON_POTION.get(), ModItems.SPLASH_MANA_DAMAGE_ENHANCE_LONG_POTION.get(),
                    ModItems.GIANT_POTION.get(), ModItems.GIANT_CON_POTION.get(), ModItems.GIANT_LONG_POTION.get(),
                    ModItems.SPLASH_GIANT_POTION.get(), ModItems.SPLASH_GIANT_CON_POTION.get(), ModItems.SPLASH_GIANT_LONG_POTION.get(),
                    ModItems.STONE_POTION.get(), ModItems.STONE_CON_POTION.get(), ModItems.STONE_LONG_POTION.get(),
                    ModItems.SPLASH_STONE_POTION.get(), ModItems.SPLASH_STONE_CON_POTION.get(), ModItems.SPLASH_STONE_LONG_POTION.get(),
                    ModItems.EX_HARVEST_POTION.get(), ModItems.EX_HARVEST_CON_POTION.get(), ModItems.EX_HARVEST_LONG_POTION.get(),
                    ModItems.SPLASH_EX_HARVEST_POTION.get(), ModItems.SPLASH_EX_HARVEST_CON_POTION.get(), ModItems.SPLASH_EX_HARVEST_LONG_POTION.get()
            };

            for (Item item1 : item) event.accept(item1.getDefaultInstance());
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
                    ModItems.PEARL_1.get(), ModItems.PEARL_2.get(),
                    ModItems.PEARL_3.get(), ModItems.PEARL_4.get(),
                    ModItems.PEARL_5.get(), ModItems.PEARL_6.get(),
                    ModItems.WORLD_FORGE_STONE.get(), ModItems.SPLASHER.get(),
                    ModItems.WOOD_HAMMER.get(), ModItems.STONE_HAMMER.get(),
                    ModItems.COPPER_HAMMER.get(), ModItems.IRON_HAMMER.get(),
                    ModItems.GOLD_HAMMER.get(), ModItems.DIAMOND_HAMMER.get(),
                    ModItems.EMERALD_HAMMER.get(), ModItems.NETHER_HAMMER.get(),
                    ModItems.END_HAMMER.get(),
                    ModItems.EQUIP_PIECE_0.get(), ModItems.EQUIP_PIECE_1.get(),
                    ModItems.EQUIP_PIECE_2.get(), ModItems.EQUIP_PIECE_3.get(),
                    ModItems.EQUIP_PIECE_4.get(), ModItems.EQUIP_PIECE_5.get(),
                    ModItems.EQUIP_PIECE_6.get(), ModItems.EQUIP_PIECE_7.get(),
                    ModItems.EQUIP_PIECE_8.get(), ModItems.EQUIP_PIECE_9.get(),
                    ModItems.EQUIP_PIECE_10.get(), ModItems.EQUIP_PIECE_11.get(),
                    ModItems.EQUIP_PIECE_12.get(), ModItems.EQUIP_PIECE_13.get()
            };
            for (Item item : items) event.accept(item.getDefaultInstance());
            event.accept(ModItems.FORGING_STONE_0.get().getDefaultInstance());
            event.accept(ModItems.FORGING_STONE_1.get().getDefaultInstance());
            event.accept(ModItems.FORGING_STONE_2.get().getDefaultInstance());
            event.accept(ModItems.FORGE_ENHANCE_0.get().getDefaultInstance());
            event.accept(ModItems.FORGE_ENHANCE_1.get().getDefaultInstance());
            event.accept(ModItems.FORGE_ENHANCE_2.get().getDefaultInstance());
            event.accept(ModItems.FORGE_ENHANCE_3.get().getDefaultInstance());
            event.accept(ModItems.FORGE_PROTECT.get().getDefaultInstance());
            event.accept(GemItems.DISMANTLE.get().getDefaultInstance());
            event.accept(ModItems.FORGE_TEMPLATE.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.MONEYANDMISSION_TAB.getKey())) {
            event.accept(ModItems.PICK_UPGRADE_PAPER.get().getDefaultInstance());
            event.accept(ModItems.SKIN_TEMPLATE_PAPER.get().getDefaultInstance());
            event.accept(ModItems.STACK_UPGRADE_PAPER.get().getDefaultInstance());
            event.accept(ModItems.GEM_PIECE.get().getDefaultInstance());
            event.accept(ModItems.ROSE_GOLD_COIN.get().getDefaultInstance());
            event.accept(ModItems.GOLD_COIN.get().getDefaultInstance());
            event.accept(ModItems.SILVER_COIN.get().getDefaultInstance());
            event.accept(ModItems.COPPER_COIN.get().getDefaultInstance());
            event.accept(ModItems.ATTACK_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.DEFENCE_PENETRATION_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.CRIT_RATE_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.CRIT_DAMAGE_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.MANA_DAMAGE_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.MANA_PENETRATION_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.MANA_RECOVER_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.POWER_RELEASE_SPEED_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.HEALTH_STEAL_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.DEFENCE_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.MANA_DEFENCE_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.MOVEMENT_SPEED_UP_POTION_BAG.get().getDefaultInstance());
            event.accept(ModItems.GOLD_COIN_BAG.get().getDefaultInstance());
            event.accept(ModItems.BACKPACK_TICKETS.get().getDefaultInstance());
            event.accept(ModItems.COMPLETE_GEM.get().getDefaultInstance());
            event.accept(ModItems.REPUTATION_MEDAL.get().getDefaultInstance());
            event.accept(ModItems.COMMON_LOTTERIES.get().getDefaultInstance());
            event.accept(ModItems.UNCOMMON_LOTTERIES.get().getDefaultInstance());
            event.accept(ModItems.REVELATION_BOOK.get().getDefaultInstance());
            event.accept(ModItems.REVELATION_HEART.get().getDefaultInstance());
            event.accept(ModItems.U_DISK.get().getDefaultInstance());
            event.accept(ModItems.ICE_LOOT.get().getDefaultInstance());
            event.accept(SpecialEventItems.FIRE_WORK_GUN.get().getDefaultInstance());
            event.accept(SpecialEventItems.MONEY.get().getDefaultInstance());
            event.accept(SpecialEventItems.RED_ENVELOPE.get().getDefaultInstance());
            event.accept(SpecialEventItems.SPRING_GOLD_COIN.get().getDefaultInstance());
            event.accept(ModItems.DEVIL_LOOT.get().getDefaultInstance());
            event.accept(ModItems.DRAGON_PREFIX.get().getDefaultInstance());
            event.accept(ModItems.MOON_LOOT.get().getDefaultInstance());
            event.accept(ModItems.CASTLE_LOOT.get().getDefaultInstance());
            event.accept(ModItems.LOTTERY_STAR.get().getDefaultInstance());
            event.accept(ModItems.LOTTERY_PREFIX.get().getDefaultInstance());

            event.accept(ModItems.SWORD_LOTTERY.get().getDefaultInstance());
            event.accept(ModItems.BOW_LOTTERY.get().getDefaultInstance());
            event.accept(ModItems.SCEPTRE_LOTTERY.get().getDefaultInstance());
            event.accept(ModItems.SWORD_LOTTERY_1.get().getDefaultInstance());
            event.accept(ModItems.BOW_LOTTERY_1.get().getDefaultInstance());
            event.accept(ModItems.SCEPTRE_LOTTERY_1.get().getDefaultInstance());

            event.accept(ModItems.NOTE_PAPER.get().getDefaultInstance());
            Item[] items = {
                    ModItems.SUPPLY_BOX_TIER_0.get(),
                    ModItems.SUPPLY_BOX_TIER_1.get(), ModItems.SUPPLY_BOX_TIER_2.get(), ModItems.SUPPLY_BOX_TIER_3.get(),
                    ModItems.ORE_SUPPLY.get(), ModItems.SENIOR_POTION_SUPPLY.get(),
                    ModItems.JUNIOR_SUPPLY.get(), ModItems.SENIOR_SUPPLY.get(),
                    ModItems.SIMPLE_TIER_1_PAPER.get(), ModItems.SIMPLE_TIER_2_PAPER.get(), ModItems.SIMPLE_TIER_3_PAPER.get(),
                    ModItems.GOLD_COIN_LOTTERY.get(), ModItems.GOLDEN_BEANS.get(),
                    ModItems.BOND.get(), ModItems.SPECIAL_BOND.get(), ModItems.MILLION_MONEY.get(),
                    ModItems.ESTATE_KEY.get(), ModItems.REAL_ESTATE_KEY.get(),
                    ModItems.TP_TICKET.get(),
                    ModItems.TP_PASS_1DAY.get(), ModItems.TP_PASS_2DAY.get(), ModItems.TP_PASS_3DAY.get()
            };
            for (Item item : items) event.accept(item.getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.CURIOS_AND_GEMS.getKey())) {
            for (Item item : Utils.curiosList) {
                event.accept(item.getDefaultInstance());
            }
            for (Item item : Display.gemList) {
                event.accept(item.getDefaultInstance());
            }
        }

        if (event.getTabKey().equals(ModCreativeModeTab.MISC_TAB.getKey())) {
            event.accept(ModItems.MAIN_0.get().getDefaultInstance());
            event.accept(ModItems.FOR_NEW.get().getDefaultInstance());
            event.accept(ModItems.BACK_SPAWN_TICKET.get().getDefaultInstance());
            event.accept(ModItems.SKY_CITY_TICKET.get().getDefaultInstance());
            event.accept(ModItems.ID_CARD.get().getDefaultInstance());
            event.accept(ModItems.PS_BOTTLE_0.get().getDefaultInstance());
            event.accept(ModItems.PS_BOTTLE_1.get().getDefaultInstance());
            event.accept(ModItems.PS_BOTTLE_2.get().getDefaultInstance());
            event.accept(ModItems.PARKOUR_MEDAL.get().getDefaultInstance());
            event.accept(ModItems.KILL_PAPER_LOOT.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.DEVELOPMENT_TAB.getKey())) {
            event.accept(ModItems.RAILWAY_PILLAR_SET_TOOL.get().getDefaultInstance());
            event.accept(ModItems.BARRIER_SET.get().getDefaultInstance());
            event.accept(ModItems.WATER_SET.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.WORLD_SOUL.getKey())) {
            event.accept(ModItems.WORLD_SOUL_1.get().getDefaultInstance());
            event.accept(ModItems.WORLD_SOUL_2.get().getDefaultInstance());
            event.accept(ModItems.WORLD_SOUL_3.get().getDefaultInstance());
            event.accept(ModItems.WORLD_SOUL_4.get().getDefaultInstance());
            event.accept(ModItems.WORLD_SOUL_5.get().getDefaultInstance());
            event.accept(ModItems.SOUL_SWORD.get().getDefaultInstance());
            event.accept(ModItems.SOUL_BOW.get().getDefaultInstance());
            event.accept(ModItems.SOUL_SCEPTRE.get().getDefaultInstance());
            event.accept(ModItems.SKILL_RESET.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.KILL_PAPER.getKey())) {
            Item[] items = {
                    ModItems.KILL_PAPER.get(), ModItems.KILL_PAPER_L.get()
            };
            for (Item item : items) event.accept(item.getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.FURNACE.getKey())) {


            OreItems.ITEMS.getEntries()
                    .stream().map(RegistryObject::get)
                    .forEach(event::accept);

            WraqPickaxe.PICKAXE_ITEM_LIST.forEach(event::accept);

            event.accept(ModItems.CRUDE_COAL.get().getDefaultInstance());
            event.accept(ModItems.HOT_COAL.get().getDefaultInstance());
            event.accept(ModItems.REFINING_COAL.get().getDefaultInstance());
            event.accept(ModItems.BLAZE_COAL.get().getDefaultInstance());
            event.accept(ModItems.CRUDE_IRON.get().getDefaultInstance());
            event.accept(ModItems.HOT_IRON.get().getDefaultInstance());
            event.accept(ModItems.REFINING_IRON.get().getDefaultInstance());
            event.accept(ModItems.CRUDE_COPPER.get().getDefaultInstance());
            event.accept(ModItems.HOT_COPPER.get().getDefaultInstance());
            event.accept(ModItems.REFINING_COPPER.get().getDefaultInstance());
            event.accept(ModItems.CRUDE_GOLD.get().getDefaultInstance());
            event.accept(ModItems.BLAZE_GOLD.get().getDefaultInstance());
            event.accept(ModItems.REFINING_GOLD.get().getDefaultInstance());
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
        if (event.getTabKey().equals(ModCreativeModeTab.COMPOSITES.getKey())) {
            CompositesItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.DIVINE_ISLAND.getKey())) {
            DivineIslandItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.HOLY.getKey())) {
            HolyItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
            IceHolyItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.NEW_AREA.getKey())) {
            NewAreaItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.MANA_TOWER.getKey())) {
            ExtraordinaryItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.KILL_PAPER.getKey())) {
            MopUpPaperItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.COOKING.getKey())) {
            CookingItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.CRYSTAL.getKey())) {
            CrystalItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(event::accept);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.ALL.getKey())) {
            ForgeRegistries.ITEMS.getEntries()
                    .stream()
                    .filter(entry -> entry.getKey().location().getNamespace().equals(Utils.MOD_ID))
                    .map(Map.Entry::getValue)
                    .forEach(event::accept);
        }
    }
}



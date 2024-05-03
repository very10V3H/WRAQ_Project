package com.very.wraq;

import com.very.wraq.blocks.entity.ModBlockEntities;
import com.very.wraq.entities.entities.Boss2.Boss2;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.entities.entities.MainBoss.MainBoss;
import com.very.wraq.entities.entities.SakuraMob.SakuraMob;
import com.very.wraq.entities.entities.Scarecrow.Scarecrow;
import com.very.wraq.events.core.BlockEvent;
import com.very.wraq.files.ConfigTest;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.process.lottery.NewLotteries;
import com.very.wraq.process.plan.PlanPlayer;
import com.very.wraq.process.tower.Tower;
import com.very.wraq.render.gui.blocks.BrewingScreen;
import com.very.wraq.render.gui.blocks.ForgingBlockScreen;
import com.very.wraq.render.gui.blocks.FurnaceScreen;
import com.very.wraq.render.gui.blocks.InjectBlockScreen;
import com.very.wraq.render.gui.testAndHelper.ModMenuTypes;
import com.very.wraq.render.mobEffects.ModEffects;
import com.very.wraq.render.mobEffects.ModPotions;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.valueAndTools.*;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModBlocks;
import com.very.wraq.valueAndTools.registry.ModCreativeModeTab;
import com.very.wraq.valueAndTools.registry.ModItems;
import com.very.wraq.valueAndTools.registry.ModSounds;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
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
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.sql.SQLException;

@Mod(Utils.MOD_ID)
@Mod.EventBusSubscriber
public class VMD{

    public VMD() {
        IEventBus modEvenBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modEvenBus);
        ModBlocks.BLOCKS.register(modEvenBus);
        ModEntityType.ENTITY_TYPES.register(modEvenBus);
        modEvenBus.addListener(this::enqueueIMC);
        modEvenBus.addListener(this::SetUp);
        HAttribute.ATTRIBUTES.register(modEvenBus);
        ModSounds.register(modEvenBus);
        ModEffects.register(modEvenBus);
        ModMenuTypes.register(modEvenBus);
        ModBlockEntities.Register(modEvenBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigTest.COMMON_CONFIG);
        modEvenBus.addListener(this::Attribute);
        ModPotions.register(modEvenBus);
        ModParticles.register(modEvenBus);
        ModCreativeModeTab.register(modEvenBus);
        modEvenBus.addListener(this::AddItemToTab);
        modEvenBus.addListener(this::SetUp0);
    }

    private void SetUp0(FMLCommonSetupEvent event) {
        ModNetworking.register();
        replaceAttributeValue((RangedAttribute) Attributes.MAX_HEALTH,Double.MAX_VALUE);
        replaceAttributeValue((RangedAttribute) Attributes.ARMOR,Double.MAX_VALUE);
        replaceAttributeValue((RangedAttribute) Attributes.ARMOR_TOUGHNESS,Double.MAX_VALUE);
        Utils.Init();
    }
    private void SetUp(FMLClientSetupEvent event)
    {
        ModItemProperties.addCustomBowProperties();
        MenuScreens.register(ModMenuTypes.FIRST_MENU.get(), ForgingBlockScreen::new);
        MenuScreens.register(ModMenuTypes.BREWING_MENU.get(), BrewingScreen::new);
        MenuScreens.register(ModMenuTypes.INJECT_BLOCK_MENU.get(), InjectBlockScreen::new);
        MenuScreens.register(ModMenuTypes.Furnace_Menu.get(), FurnaceScreen::new);

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
        if(ModList.get().isLoaded(CuriosApi.MODID)) {
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(5).build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().size(2).build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().size(2).build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BRACELET.getMessageBuilder().size(2).build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BODY.getMessageBuilder().build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().size(4).build());
        }
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
    public static void serverStartEvent(ServerStartingEvent event) throws SQLException {
        PlanPlayer.readFromSqlOnStart();
    }

    @SubscribeEvent
    public static void serverStopEvent(ServerStoppingEvent event) throws SQLException {
        BlockEvent.MineAndWoodReset(event.getServer().getLevel(Level.OVERWORLD));
        Compute.RemoveAllArmorStandForDisplay();
        Compute.ClearWoodenStake();
        PlanPlayer.writeToSqlOnStopping();
        Tower.writeToDataBase();
        NewLotteries.writeToDataBase();
    }
    @SubscribeEvent
    public static void comps(RenderTooltipEvent.GatherComponents event) {
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
                    ModItems.LifeTeleportTicket.get(), ModItems.WindTeleportTicket.get(),
                    ModItems.StoneTeleportTicket.get(), ModItems.FireTeleportTicket.get(),
                    ModItems.WaterTeleportTicket.get(), ModItems.LightningTeleportTicket.get(),
                    ModItems.IceTeleportTicket.get(),
                    ModItems.LifeHolyStone0.get(), ModItems.LifeHolyStone1.get(), ModItems.LifeHolyStone2.get(),
                    ModItems.WaterHolyStone0.get(), ModItems.WaterHolyStone1.get(), ModItems.WaterHolyStone2.get(),
                    ModItems.FireHolyStone0.get(), ModItems.FireHolyStone1.get(), ModItems.FireHolyStone2.get(),
                    ModItems.StoneHolyStone0.get(), ModItems.StoneHolyStone1.get(), ModItems.StoneHolyStone2.get(),
                    ModItems.IceHolyStone0.get(), ModItems.IceHolyStone1.get(), ModItems.IceHolyStone2.get(),
                    ModItems.LightningHolyStone0.get(), ModItems.LightningHolyStone1.get(), ModItems.LightningHolyStone2.get(),
                    ModItems.WindHolyStone0.get(), ModItems.WindHolyStone1.get(), ModItems.WindHolyStone2.get(),
            };
            for (Item item : items) event.accept(item);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.QINGMING.getKey())) {
            Item[] items = {
                    ModItems.QingTuan.get(), ModItems.QingMingPrefix.get(), ModItems.QingMingGem.get(),
                    ModItems.QingMingForgePaper.get(),
                    ModItems.QingMingAttackRing.get(), ModItems.QingMingBowRing.get(), ModItems.QingMingManaRing.get()
            };
            for (Item item : items) event.accept(item);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.LABOUR_DAY.getKey())) {
            Item[] items = {
                    ModItems.OldSilverCoin.get(), ModItems.OldGoldCoin.get(),
                    ModItems.LabourDayForgePaper.get(), ModItems.LabourDayIronPickaxe.get(), ModItems.LabourDayIronHoe.get(),
                    ModItems.LabourDayLottery.get(), ModItems.LabourDayPrefix.get(), ModItems.LabourDayGem.get()
            };
            for (Item item : items) event.accept(item);
        }
        if (event.getTabKey().equals(ModCreativeModeTab.SWORD_TAB.getKey())) {
            Item[] items = {
                    ModItems.Knife.get(),
                    ModItems.PlainSword0.get(), ModItems.PlainSword1.get(), ModItems.PlainSword2.get(), ModItems.PlainSword3.get(),
                    ModItems.PlainBow0.get(), ModItems.PlainBow1.get(), ModItems.PlainBow2.get(), ModItems.PlainBow3.get(),
                    ModItems.ForestSword0.get(), ModItems.ForestSword1.get(), ModItems.ForestSword2.get(), ModItems.ForestSword3.get(),
                    ModItems.ForestBow0.get(), ModItems.ForestBow1.get(), ModItems.ForestBow2.get(), ModItems.ForestBow3.get(),
                    ModItems.LakeSword0.get(), ModItems.LakeSword1.get(), ModItems.LakeSword2.get(), ModItems.LakeSword3.get(),
                    ModItems.VolcanoSword0.get(), ModItems.VolcanoSword1.get(), ModItems.VolcanoSword2.get(), ModItems.VolcanoSword3.get(),
                    ModItems.VolcanoBow0.get(), ModItems.VolcanoBow1.get(), ModItems.VolcanoBow2.get(), ModItems.VolcanoBow3.get(),
                    ModItems.MineSword0.get(), ModItems.MineSword1.get(), ModItems.MineSword2.get(), ModItems.MineSword3.get(),
                    ModItems.FieldSword0.get(), ModItems.FieldSword1.get(), ModItems.FieldSword2.get(), ModItems.FieldSword3.get(),
                    ModItems.SnowSword0.get(), ModItems.SnowSword1.get(), ModItems.SnowSword2.get(), ModItems.SnowSword3.get(),
                    ModItems.SkyBow.get(), ModItems.EvokerSword.get(),
                    ModItems.PlainSceptre0.get(), ModItems.PlainSceptre1.get(), ModItems.PlainSceptre2.get(), ModItems.PlainSceptre3.get(), ModItems.PlainSceptre4.get(),
                    ModItems.WitherBonePower.get(), ModItems.PigLinPower.get(), ModItems.WitherBoneMealPower.get(), ModItems.NetherPower.get(),
                    ModItems.NetherBow.get(), ModItems.ManaSword.get(), ModItems.QuartzSword.get(), ModItems.QuartzSabre.get(),
                    ModItems.EvokerBook0.get(), ModItems.EvokerBook1.get(), ModItems.EvokerBook2.get(), ModItems.EvokerBook3.get(),
                    ModItems.SeaSword0.get(), ModItems.SeaSword1.get(), ModItems.SeaSword2.get(), ModItems.SeaSword3.get(),
                    ModItems.BlackForestSword0.get(), ModItems.BlackForestSword1.get(), ModItems.BlackForestSword2.get(), ModItems.BlackForestSword3.get(),
                    ModItems.MagmaPower.get(), ModItems.GoldSword0.get(),
                    ModItems.KazeSword0.get(), ModItems.KazeSword1.get(), ModItems.KazeSword2.get(), ModItems.KazeSword3.get(), ModItems.KazeSword4.get(),
                    ModItems.BlackForestSword4.get(), ModItems.SeaSword4.get(), ModItems.ManaSword1.get(),
                    ModItems.SnowSword4.get(), ModItems.ForestSword4.get(), ModItems.VolcanoSword4.get(),
                    ModItems.ForestBossSword.get(), ModItems.VolcanoBossSword.get(), ModItems.LakeBossSword.get(), ModItems.SkyBossBow.get(),
                    ModItems.SakuraDemonSword.get(),
                    ModItems.WitherSword0.get(), ModItems.WitherSword1.get(), ModItems.WitherSword2.get(), ModItems.WitherSword3.get(),
                    ModItems.WitherBow0.get(), ModItems.WitherBow1.get(), ModItems.WitherBow2.get(), ModItems.WitherBow3.get(),
                    ModItems.MagmaSceptre0.get(), ModItems.MagmaSceptre1.get(), ModItems.MagmaSceptre2.get(), ModItems.MagmaSceptre3.get(),
                    ModItems.PlainManaBook.get(), ModItems.ForestManaBook.get(), ModItems.LakeManaBook.get(), ModItems.VolcanoManaBook.get(), ModItems.SnowManaBook.get(),
                    ModItems.SoulSword.get(), ModItems.SoulSceptre.get(), ModItems.SoulBow.get(),
                    ModItems.PlainPower.get(), ModItems.PlainPower1.get(), ModItems.PlainPower2.get(), ModItems.PlainPower3.get(),
                    ModItems.ForestPower.get(), ModItems.ForestPower1.get(), ModItems.ForestPower2.get(), ModItems.ForestPower3.get(),
                    ModItems.LakePower.get(), ModItems.LakePower1.get(), ModItems.LakePower2.get(), ModItems.LakePower3.get(),
                    ModItems.VolcanoPower.get(), ModItems.VolcanoPower1.get(), ModItems.VolcanoPower2.get(), ModItems.VolcanoPower3.get(),
                    ModItems.SnowPower.get(), ModItems.SnowPower1.get(), ModItems.SnowPower2.get(), ModItems.SnowPower3.get(),
                    ModItems.SeaManaCore.get(), ModItems.BlackForestManaCore.get(), ModItems.KazeManaCore.get(), ModItems.SakuraBow.get(), ModItems.SakuraCore.get(),
                    ModItems.PurpleIronPickaxe0.get(), ModItems.PurpleIronPickaxe1.get(), ModItems.PurpleIronPickaxe2.get(), ModItems.PurpleIronPickaxe3.get(),
                    ModItems.IceBook.get(), ModItems.IceBracelet.get(), ModItems.SeaBow.get(),
                    ModItems.MineBow0.get(), ModItems.MineBow1.get(), ModItems.MineBow2.get(), ModItems.MineBow3.get(),
                    ModItems.IceSword.get(), ModItems.IceBow.get(), ModItems.IceSceptre.get(),
                    ModItems.MineShield.get(),
                    ModItems.ShipSword.get(), ModItems.ShipBow.get(), ModItems.ShipSceptre.get(),
                    ModItems.NetherSceptre.get(),
                    ModItems.SnowShield.get(), ModItems.NetherShield.get(), ModItems.GoldenShield.get(), ModItems.ManaShield.get(),
                    ModItems.ManaKnife.get(),
                    ModItems.WitherBook.get(), ModItems.EarthBook.get(), ModItems.GoldenBook.get(),
                    ModItems.MoonShield.get(), ModItems.MoonKnife.get(), ModItems.MoonBook.get(),
                    ModItems.DevilSword.get(), ModItems.DevilBow.get(), ModItems.DevilSceptre.get(),
                    ModItems.MoonSword.get(), ModItems.MoonBow.get(), ModItems.MoonSceptre.get(),
                    ModItems.EarthPower.get(),
                    ModItems.CastleSword.get(), ModItems.CastleBow.get(), ModItems.CastleSceptre.get(),
                    ModItems.BeaconBow.get(), ModItems.BeaconBow1.get(), ModItems.BeaconBow2.get(), ModItems.BeaconBow3.get(),
                    ModItems.BlazeSword.get(), ModItems.BlazeSword1.get(), ModItems.BlazeSword2.get(), ModItems.BlazeSword3.get(),
                    ModItems.TreeSceptre.get(), ModItems.TreeSceptre1.get(), ModItems.TreeSceptre2.get(), ModItems.TreeSceptre3.get(),
                    ModItems.EndPower.get(), ModItems.EndPower1.get(), ModItems.EndPower2.get(), ModItems.EndPower3.get(),
                    ModItems.PurpleIronSword.get(), ModItems.PurpleIronSword1.get(), ModItems.PurpleIronSword2.get(), ModItems.PurpleIronSword3.get(),
                    ModItems.PurpleIronBow.get(), ModItems.PurpleIronBow1.get(), ModItems.PurpleIronBow2.get(), ModItems.PurpleIronBow3.get(),
                    ModItems.PurpleIronSceptre.get(), ModItems.PurpleIronSceptre1.get(), ModItems.PurpleIronSceptre2.get(), ModItems.PurpleIronSceptre3.get(),
                    ModItems.LifeElementSword.get(), ModItems.LifeElementBow.get(), ModItems.LifeElementSceptre.get(),
                    ModItems.WaterElementSword.get(), ModItems.WaterElementBow.get(), ModItems.WaterElementSceptre.get(),
                    ModItems.FireElementSword.get(), ModItems.FireElementBow.get(), ModItems.FireElementSceptre.get()
            };
            for (Item item : items) event.accept(item.getDefaultInstance());
        }

        if (event.getTabKey().equals(ModCreativeModeTab.ARMOR_TAB.getKey())) {
            Item[] items = {
                    ModItems.PlainArmorHelmet.get(), ModItems.PlainArmorChest.get(), ModItems.PlainArmorLeggings.get(), ModItems.PlainArmorBoots.get(),
                    ModItems.ForestArmorHelmet.get(), ModItems.ForestArmorChest.get(), ModItems.ForestArmorLeggings.get(), ModItems.ForestArmorBoots.get(),
                    ModItems.LakeArmorHelmet.get(), ModItems.LakeArmorChest.get(), ModItems.LakeArmorLeggings.get(), ModItems.LakeArmorBoots.get(),
                    ModItems.VolcanoArmorHelmet.get(), ModItems.VolcanoArmorChest.get(), ModItems.VolcanoArmorLeggings.get(), ModItems.VolcanoArmorBoots.get(),
                    ModItems.LifeManaArmorHelmet.get(), ModItems.LifeManaArmorChest.get(), ModItems.LifeManaArmorLeggings.get(), ModItems.LifeManaArmorBoots.get(),
                    ModItems.ObsiManaArmorHelmet.get(), ModItems.ObsiManaArmorChest.get(), ModItems.ObsiManaArmorLeggings.get(), ModItems.ObsiManaArmorBoots.get(),
                    ModItems.IslandArmorHelmet.get(), ModItems.IslandArmorChest.get(), ModItems.IslandArmorLeggings.get(), ModItems.IslandArmorBoots.get(),
                    ModItems.SkyArmorHelmet.get(), ModItems.SkyArmorChest.get(), ModItems.SkyArmorLeggings.get(), ModItems.SkyArmorBoots.get(),
                    ModItems.NetherArmorHelmet.get(), ModItems.NetherArmorChest.get(), ModItems.NetherArmorLeggings.get(), ModItems.NetherArmorBoots.get(),
                    ModItems.KazeBoots.get(),
                    ModItems.SHelmet.get(), ModItems.SChest.get(), ModItems.SLeggings.get(), ModItems.SBoots.get(),
                    ModItems.ISArmorHelmet.get(), ModItems.ISArmorChest.get(), ModItems.ISArmorLeggings.get(), ModItems.ISArmorBoots.get(),
                    ModItems.ILArmorHelmet.get(), ModItems.ILArmorChest.get(), ModItems.ILArmorLeggings.get(), ModItems.ILArmorBoots.get(),
                    ModItems.SakuraArmorHelmet.get(), ModItems.WheatArmorChest.get(), ModItems.MinePants.get(),
                    ModItems.MineArmorHelmet.get(), ModItems.MineArmorChest.get(), ModItems.MineArmorLeggings.get(), ModItems.MineArmorBoots.get(),
                    ModItems.SnowArmorHelmet.get(), ModItems.SnowArmorChest.get(), ModItems.SnowArmorLeggings.get(), ModItems.SnowArmorBoots.get(),
                    ModItems.PiglinHelmet0.get(), ModItems.PiglinHelmet1.get(), ModItems.PiglinHelmet2.get(), ModItems.PiglinHelmet3.get(),
                    ModItems.SnowBossArmorChest.get(), ModItems.KazeBoots.get(),
                    ModItems.PurpleIronArmorHelmet.get(), ModItems.PurpleIronArmorChest.get(), ModItems.PurpleIronArmorLeggings.get(), ModItems.PurpleIronArmorBoots.get(),
                    ModItems.IceArmorHelmet.get(), ModItems.IceArmorChest.get(), ModItems.IceArmorLeggings.get(), ModItems.IceArmorBoots.get(),
                    ModItems.LeatherArmorHelmet.get(), ModItems.LeatherArmorChest.get(), ModItems.LeatherArmorLeggings.get(), ModItems.LeatherArmorBoots.get(),
                    ModItems.LifeManaArmorHelmetE.get(), ModItems.LifeManaArmorChestE.get(), ModItems.LifeManaArmorLeggingsE.get(), ModItems.LifeManaArmorBootsE.get(),
                    ModItems.ObsiManaArmorHelmetE.get(), ModItems.ObsiManaArmorChestE.get(), ModItems.ObsiManaArmorLeggingsE.get(), ModItems.ObsiManaArmorBootsE.get(),
                    ModItems.MineHat.get(),
                    ModItems.NetherManaArmorHelmet.get(), ModItems.NetherManaArmorChest.get(), ModItems.NetherManaArmorLeggings.get(), ModItems.NetherManaArmorBoots.get(),
                    ModItems.SpringAttackArmorHelmet.get(), ModItems.SpringAttackArmorChest.get(), ModItems.SpringAttackArmorLeggings.get(), ModItems.SpringAttackArmorBoots.get(),
                    ModItems.SpringSwiftArmorHelmet.get(), ModItems.SpringSwiftArmorChest.get(), ModItems.SpringSwiftArmorLeggings.get(), ModItems.SpringSwiftArmorBoots.get(),
                    ModItems.SpringManaArmorHelmet.get(), ModItems.SpringManaArmorChest.get(), ModItems.SpringManaArmorLeggings.get(), ModItems.SpringManaArmorBoots.get(),
                    ModItems.EarthManaArmorHelmet.get(), ModItems.EarthManaArmorChest.get(), ModItems.EarthManaArmorLeggings.get(), ModItems.EarthManaArmorBoots.get(),
                    ModItems.BloodManaArmorHelmet.get(), ModItems.BloodManaArmorChest.get(), ModItems.BloodManaArmorLeggings.get(), ModItems.BloodManaArmorBoots.get(),
                    ModItems.DevilAttackChest.get(), ModItems.DevilSwiftBoots.get(), ModItems.DevilManaHelmet.get(),
                    ModItems.SlimeBoots.get(),
                    ModItems.MoonLeggings.get(), ModItems.MoonHelmet.get(),
                    ModItems.TabooAttackLeggings.get(), ModItems.TabooSwiftHelmet.get(), ModItems.TabooManaBoots.get(),
                    ModItems.CastleAttackHelmet.get(), ModItems.CastleAttackChest.get(), ModItems.CastleAttackLeggings.get(), ModItems.CastleAttackBoots.get(),
                    ModItems.CastleSwiftHelmet.get(), ModItems.CastleSwiftChest.get(), ModItems.CastleSwiftLeggings.get(), ModItems.CastleSwiftBoots.get(),
                    ModItems.CastleManaHelmet.get(), ModItems.CastleManaChest.get(), ModItems.CastleManaLeggings.get(), ModItems.CastleManaBoots.get(),
                    ModItems.StarHelmet.get(), ModItems.StarLeggings.get(),
            };
            for (Item item : items) event.accept(item.getDefaultInstance());
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
            event.accept(ModItems.RunePiece.get().getDefaultInstance());
            event.accept(ModItems.PlainSoul.get().getDefaultInstance());
            event.accept(ModItems.PlainRune.get().getDefaultInstance());
            event.accept(ModItems.ForestSoul.get().getDefaultInstance());
            event.accept(ModItems.ForestRune.get().getDefaultInstance());
            event.accept(ModItems.GemPiece.get().getDefaultInstance());
            event.accept(ModItems.LakeSoul.get().getDefaultInstance());
            event.accept(ModItems.LakeRune.get().getDefaultInstance());
            event.accept(ModItems.VolcanoSoul.get().getDefaultInstance());
            event.accept(ModItems.VolcanoRune.get().getDefaultInstance());
            event.accept(ModItems.MineSoul.get().getDefaultInstance());
            event.accept(ModItems.MineSoul1.get().getDefaultInstance());
            event.accept(ModItems.MineRune.get().getDefaultInstance());
            event.accept(ModItems.FieldSoul.get().getDefaultInstance());
            event.accept(ModItems.FieldRune.get().getDefaultInstance());
            event.accept(ModItems.SnowSoul.get().getDefaultInstance());
            event.accept(ModItems.SnowRune.get().getDefaultInstance());
            event.accept(ModItems.Main1Crystal.get().getDefaultInstance());
            event.accept(ModItems.SkySoul.get().getDefaultInstance());
            event.accept(ModItems.SkyRune.get().getDefaultInstance());
            event.accept(ModItems.EvokerSoul.get().getDefaultInstance());
            event.accept(ModItems.ManaBucket.get().getDefaultInstance());
            event.accept(ModItems.EvokerRune.get().getDefaultInstance());
            event.accept(ModItems.ManaBalance_Empty.get().getDefaultInstance());
            event.accept(ModItems.ManaBalance_filled.get().getDefaultInstance());
            event.accept(ModItems.plainmana.get().getDefaultInstance());
            event.accept(ModItems.forestmana.get().getDefaultInstance());
            event.accept(ModItems.lakemana.get().getDefaultInstance());
            event.accept(ModItems.volcanomana.get().getDefaultInstance());
            event.accept(ModItems.Ruby.get().getDefaultInstance());
            event.accept(ModItems.NetherSoul.get().getDefaultInstance());
            event.accept(ModItems.NetherRune.get().getDefaultInstance());
            event.accept(ModItems.NetherSwordModel.get().getDefaultInstance());
            event.accept(ModItems.witherBone.get().getDefaultInstance());
            event.accept(ModItems.QuartzSoul.get().getDefaultInstance());
            event.accept(ModItems.QuartzRune.get().getDefaultInstance());
            event.accept(ModItems.PowerModel.get().getDefaultInstance());
            event.accept(ModItems.NetherSabreModel.get().getDefaultInstance());
            event.accept(ModItems.SeaSoul.get().getDefaultInstance());
            event.accept(ModItems.SeaRune.get().getDefaultInstance());
            event.accept(ModItems.LightningSoul.get().getDefaultInstance());
            event.accept(ModItems.LightningRune.get().getDefaultInstance());
            event.accept(ModItems.BlackForestSoul.get().getDefaultInstance());
            event.accept(ModItems.BlackForestRune.get().getDefaultInstance());
            event.accept(ModItems.SunPower.get().getDefaultInstance());
            event.accept(ModItems.NetherMagmaPower.get().getDefaultInstance());
            event.accept(ModItems.KazeSoul.get().getDefaultInstance());
            event.accept(ModItems.KazeRune.get().getDefaultInstance());
            event.accept(ModItems.LakeCore.get().getDefaultInstance());
            event.accept(ModItems.SpiderSoul.get().getDefaultInstance());
            event.accept(ModItems.SpiderRune.get().getDefaultInstance());
            event.accept(ModItems.VolcanoCore.get().getDefaultInstance());
            event.accept(ModItems.SilverFishSoul.get().getDefaultInstance());
            event.accept(ModItems.BlackForestRecallBook.get().getDefaultInstance());
            event.accept(ModItems.EvokerRecallBook.get().getDefaultInstance());
            event.accept(ModItems.ForestRecallBook.get().getDefaultInstance());
            event.accept(ModItems.KazeRecallBook.get().getDefaultInstance());
            event.accept(ModItems.LakeRecallBook.get().getDefaultInstance());
            event.accept(ModItems.LightningRecallBook.get().getDefaultInstance());
            event.accept(ModItems.NetherRecallBook1.get().getDefaultInstance());
            event.accept(ModItems.NetherRecallBook2.get().getDefaultInstance());
            event.accept(ModItems.PlainRecallBook.get().getDefaultInstance());
            event.accept(ModItems.SeaRecallBook.get().getDefaultInstance());
            event.accept(ModItems.SkyRecallBook.get().getDefaultInstance());
            event.accept(ModItems.SnowRecallBook.get().getDefaultInstance());
            event.accept(ModItems.SpiderRecallBook.get().getDefaultInstance());
            event.accept(ModItems.VolcanoRecallBook.get().getDefaultInstance());
            event.accept(ModItems.RecallPiece.get().getDefaultInstance());
            event.accept(ModItems.KazeRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.IntensifiedKazeSoul.get().getDefaultInstance());
            event.accept(ModItems.SpiderRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.IntensifiedSpiderSoul.get().getDefaultInstance());
            event.accept(ModItems.BlackForestRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.IntensifiedBlackForestSoul.get().getDefaultInstance());
            event.accept(ModItems.SeaRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.IntensifiedSeaSoul.get().getDefaultInstance());
            event.accept(ModItems.NetherRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.IntensifiedNetherRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.SnowRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.IntensifiedSnowRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.ForestRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.IntensifiedForestRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.VolcanoRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.IntensifiedVolcanoRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.LightningRecallSoul.get().getDefaultInstance());
            event.accept(ModItems.IntensifiedLightningSoul.get().getDefaultInstance());
            event.accept(ModItems.ForestBossCore.get().getDefaultInstance());
            event.accept(ModItems.VolcanoBossCore.get().getDefaultInstance());
            event.accept(ModItems.ForestBossCentral.get().getDefaultInstance());
            event.accept(ModItems.VolcanoBossCentral.get().getDefaultInstance());
            event.accept(ModItems.ForestBossPocket.get().getDefaultInstance());
            event.accept(ModItems.VolcanoBossPocket.get().getDefaultInstance());
            event.accept(ModItems.LakeBossCore.get().getDefaultInstance());
            event.accept(ModItems.LakeBossCentral.get().getDefaultInstance());
            event.accept(ModItems.LakeBossPocket.get().getDefaultInstance());
            event.accept(ModItems.SkyBossCore.get().getDefaultInstance());
            event.accept(ModItems.SkyBossCentral.get().getDefaultInstance());
            event.accept(ModItems.SkyBossPocket.get().getDefaultInstance());
            event.accept(ModItems.SakuraPetal.get().getDefaultInstance());
            event.accept(ModItems.Wheat.get().getDefaultInstance());
            event.accept(ModItems.PigLinSoul.get().getDefaultInstance());
            event.accept(ModItems.netherbonemeal.get().getDefaultInstance());
            event.accept(ModItems.NetherQuartz.get().getDefaultInstance());
            event.accept(ModItems.WitherRune.get().getDefaultInstance());
            event.accept(ModItems.PiglinRune.get().getDefaultInstance());
            event.accept(ModItems.NetherSkeletonRune.get().getDefaultInstance());
            event.accept(ModItems.MagmaRune.get().getDefaultInstance());
            event.accept(ModItems.SnowBossCore.get().getDefaultInstance());
            event.accept(ModItems.SnowBossCentral.get().getDefaultInstance());
            event.accept(ModItems.SnowBossPocket.get().getDefaultInstance());
            event.accept(ModItems.Spawn2.get().getDefaultInstance());
            event.accept(ModItems.Boss2Piece.get().getDefaultInstance());
            event.accept(ModItems.PlainSoulBag.get().getDefaultInstance());
            event.accept(ModItems.ForestSoulBag.get().getDefaultInstance());
            event.accept(ModItems.LakeSoulBag.get().getDefaultInstance());
            event.accept(ModItems.VolcanoSoulBag.get().getDefaultInstance());
            event.accept(ModItems.PlainBossSoul.get().getDefaultInstance());
            event.accept(ModItems.PurpleIron.get().getDefaultInstance());
            event.accept(ModItems.PurpleIronPiece.get().getDefaultInstance());
            event.accept(ModItems.IceSoul.get().getDefaultInstance());
            event.accept(ModItems.PlainCompleteGem.get().getDefaultInstance());
            event.accept(ModItems.IceCompleteGem.get().getDefaultInstance());
            event.accept(ModItems.LeatherSoul.get().getDefaultInstance());
            event.accept(ModItems.LeatherRune.get().getDefaultInstance());
            event.accept(ModItems.OreSoul.get().getDefaultInstance());
            event.accept(ModItems.OreRune.get().getDefaultInstance());
            event.accept(ModItems.NaturalCore.get().getDefaultInstance());
            event.accept(ModItems.ShipPiece.get().getDefaultInstance());
            event.accept(ModItems.IceHeart.get().getDefaultInstance());
            event.accept(ModItems.SpringSoul.get().getDefaultInstance());
            event.accept(ModItems.SpringHeart.get().getDefaultInstance());
            event.accept(ModItems.GiantTicket.get().getDefaultInstance());
            event.accept(ModItems.GiantMedal.get().getDefaultInstance());
            event.accept(ModItems.CropBag.get().getDefaultInstance());
            event.accept(ModItems.LogBag.get().getDefaultInstance());
            event.accept(ModItems.EarthManaSoul.get().getDefaultInstance());
            event.accept(ModItems.BloodManaSoul.get().getDefaultInstance());
            event.accept(ModItems.EarthManaRune.get().getDefaultInstance());
            event.accept(ModItems.BloodManaRune.get().getDefaultInstance());
            event.accept(ModItems.DevilBlood.get().getDefaultInstance());
            event.accept(ModItems.DevilAttackSoul.get().getDefaultInstance());
            event.accept(ModItems.DevilSwiftSoul.get().getDefaultInstance());
            event.accept(ModItems.DevilManaSoul.get().getDefaultInstance());
            event.accept(ModItems.MoonSoul.get().getDefaultInstance());
            event.accept(ModItems.MoonCompleteGem.get().getDefaultInstance());
            event.accept(ModItems.SlimeBall.get().getDefaultInstance());
            event.accept(ModItems.BigSlimeBall.get().getDefaultInstance());
            event.accept(ModItems.IntensifiedDevilBlood.get().getDefaultInstance());
            event.accept(ModItems.TabooPiece.get().getDefaultInstance());
            event.accept(ModItems.PurpleIronConstraintStone.get().getDefaultInstance());
            event.accept(ModItems.ConstrainTaboo.get().getDefaultInstance());
            event.accept(ModItems.CastlePiece.get().getDefaultInstance());
            event.accept(ModItems.CastleIngot.get().getDefaultInstance());
            event.accept(ModItems.CastleSwordPiece.get().getDefaultInstance());
            event.accept(ModItems.CastleBowPiece.get().getDefaultInstance());
            event.accept(ModItems.CastleSceptrePiece.get().getDefaultInstance());
            event.accept(ModItems.CastleArmorPiece.get().getDefaultInstance());
            event.accept(ModItems.CastleSoul.get().getDefaultInstance());
            event.accept(ModItems.BeaconSoul.get().getDefaultInstance());
            event.accept(ModItems.BeaconRune.get().getDefaultInstance());
            event.accept(ModItems.BlazeSoul.get().getDefaultInstance());
            event.accept(ModItems.BlazeRune.get().getDefaultInstance());
            event.accept(ModItems.TreeSoul.get().getDefaultInstance());
            event.accept(ModItems.TreeRune.get().getDefaultInstance());
            event.accept(ModItems.CastleTabooPiece.get().getDefaultInstance());
            event.accept(ModItems.CastleKnightStone.get().getDefaultInstance());
            event.accept(ModItems.CastleCrystal.get().getDefaultInstance());
            event.accept(ModItems.CastleCrystalNorth.get().getDefaultInstance());
            event.accept(ModItems.CastleCrystalSouth.get().getDefaultInstance());
            event.accept(ModItems.PurpleIronBud1.get().getDefaultInstance());
            event.accept(ModItems.PurpleIronBud2.get().getDefaultInstance());
            event.accept(ModItems.PurpleIronBud3.get().getDefaultInstance());
            event.accept(ModItems.QingTuan.get().getDefaultInstance());
            event.accept(ModItems.StarSoul.get().getDefaultInstance());
            event.accept(ModItems.StarRune.get().getDefaultInstance());
            event.accept(ModItems.StarStar.get().getDefaultInstance());
            event.accept(ModItems.ShulkerSoul.get().getDefaultInstance());
            event.accept(ModItems.EnderMiteSoul.get().getDefaultInstance());
            event.accept(ModItems.EndCrystal.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.FORGING_TAB.getKey())) {
            event.accept(ModItems.randomsword.get().getDefaultInstance());
            event.accept(ModItems.SpeIron.get().getDefaultInstance());
            event.accept(ModItems.OpenSlot.get().getDefaultInstance());
            event.accept(ModItems.SkyGem.get().getDefaultInstance());
            event.accept(ModItems.ForgingStone0.get().getDefaultInstance());
            event.accept(ModItems.ForgingStone1.get().getDefaultInstance());
            event.accept(ModItems.ForgingStone2.get().getDefaultInstance());
            event.accept(ModItems.EvokerGem.get().getDefaultInstance());
            event.accept(ModItems.PlainGem.get().getDefaultInstance());
            event.accept(ModItems.ForestGem.get().getDefaultInstance());
            event.accept(ModItems.LakeGem.get().getDefaultInstance());
            event.accept(ModItems.VolcanoGem.get().getDefaultInstance());
            event.accept(ModItems.SnowGem.get().getDefaultInstance());
            event.accept(ModItems.ForgeImprove0.get().getDefaultInstance());
            event.accept(ModItems.ForgeImprove1.get().getDefaultInstance());
            event.accept(ModItems.ForgeImprove2.get().getDefaultInstance());
            event.accept(ModItems.ForgeEnhance0.get().getDefaultInstance());
            event.accept(ModItems.ForgeEnhance1.get().getDefaultInstance());
            event.accept(ModItems.ForgeEnhance2.get().getDefaultInstance());
            event.accept(ModItems.ForgeProtect.get().getDefaultInstance());

            event.accept(ModItems.IslandHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.IslandChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.IslandLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.IslandBootsForgeDraw.get().getDefaultInstance());

            event.accept(ModItems.SkyHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SkyChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SkyLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SkyBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SeaSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.BlackForestSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.NetherHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.NetherChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.NetherLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.NetherBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.KazeSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.KazeBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SakuraSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SakuraReForge.get().getDefaultInstance());
            event.accept(ModItems.MinePantsForgingDraw.get().getDefaultInstance());
            event.accept(ModItems.WheatReForge.get().getDefaultInstance());
            event.accept(ModItems.ForestBossSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.VolcanoBossSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.LakeBossSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SkyBossBowForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SnowBossChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.Dismantle.get().getDefaultInstance());
            event.accept(ModItems.SeaManaCoreForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.BlackForestManaCoreForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.KazeManaCoreForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SakuraBowForgingDraw.get().getDefaultInstance());
            event.accept(ModItems.SakuraCoreForgingDraw.get().getDefaultInstance());
            event.accept(ModItems.MinePantsForgingDraw.get().getDefaultInstance());
            event.accept(ModItems.IceHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.IceChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.IceLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.IceBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SeaBowForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.IceSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.IceBowForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.IceSceptreForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.ShipSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.ShipBowForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.ShipSceptreForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.NetherManaHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.NetherManaChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.NetherManaLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.NetherManaBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.NetherSceptreForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringAttackHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringAttackChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringAttackLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringAttackBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringSwiftHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringSwiftChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringSwiftLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringSwiftBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringManaHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringManaChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringManaLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.SpringManaBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.GoldenShieldForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.DevilAttackChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.DevilSwiftBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.DevilManaHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.MoonShieldForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.MoonKnifeForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.MoonBookForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.MoonLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.MoonSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.MoonBowForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.MoonSceptreForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.MoonBeltForgeDraw.get().getDefaultInstance());

            event.accept(ModItems.TabooAttackLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.TabooSwiftHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.TabooManaBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.MoonHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleAttackHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleAttackChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleAttackLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleAttackBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleSwiftHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleSwiftChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleSwiftLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleSwiftBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleManaHelmetForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleManaChestForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleManaLeggingsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleManaBootsForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleBowForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.CastleSceptreForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.StarBottleForgeDraw.get().getDefaultInstance());

            event.accept(ModItems.LifeElementSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.LifeElementBowForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.LifeElementSceptreForgeDraw.get().getDefaultInstance());

            event.accept(ModItems.WaterElementSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.WaterElementBowForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.WaterElementSceptreForgeDraw.get().getDefaultInstance());

            event.accept(ModItems.FireElementSwordForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.FireElementBowForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.FireElementSceptreForgeDraw.get().getDefaultInstance());

            event.accept(ModItems.EndCuriosForgeDraw.get().getDefaultInstance());
            event.accept(ModItems.EndCurios1ForgeDraw.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.MONEYANDMISSION_TAB.getKey())) {
            event.accept(ModItems.RoseGoldCoin.get().getDefaultInstance());
            event.accept(ModItems.GoldCoin.get().getDefaultInstance());
            event.accept(ModItems.SilverCoin.get().getDefaultInstance());
            event.accept(ModItems.SignInReward.get().getDefaultInstance());
            event.accept(ModItems.SmartPhone.get().getDefaultInstance());
            event.accept(ModItems.DailyMission.get().getDefaultInstance());
            event.accept(ModItems.AttackUp_PotionBag.get().getDefaultInstance());
            event.accept(ModItems.Breakdefenceup_potionBag.get().getDefaultInstance());
            event.accept(ModItems.CritRateUp_PotionBag.get().getDefaultInstance());
            event.accept(ModItems.CritDamageUp_PotionBag.get().getDefaultInstance());
            event.accept(ModItems.ManaDamageUp_PotionBag.get().getDefaultInstance());
            event.accept(ModItems.ManaBreakdefenceup_potionBag.get().getDefaultInstance());
            event.accept(ModItems.ManaReplyUp_PotionBag.get().getDefaultInstance());
            event.accept(ModItems.CoolDownDecreaseUp_PotionBag.get().getDefaultInstance());
            event.accept(ModItems.HealStealUp_PotionBag.get().getDefaultInstance());
            event.accept(ModItems.defenceup_potionBag.get().getDefaultInstance());
            event.accept(ModItems.Manadefenceup_potionBag.get().getDefaultInstance());
            event.accept(ModItems.SpeedUp_PotionBag.get().getDefaultInstance());
            event.accept(ModItems.GoldCoinBag.get().getDefaultInstance());
            event.accept(ModItems.BackPackTickets.get().getDefaultInstance());
            event.accept(ModItems.SakuraPocket.get().getDefaultInstance());
            event.accept(ModItems.WheatPocket.get().getDefaultInstance());
            event.accept(ModItems.CompleteGem.get().getDefaultInstance());
            event.accept(ModItems.ReputationMedal.get().getDefaultInstance());
            event.accept(ModItems.CommonLotteries.get().getDefaultInstance());
            event.accept(ModItems.UnCommonLotteries.get().getDefaultInstance());
            event.accept(ModItems.RevelationBook.get().getDefaultInstance());
            event.accept(ModItems.U_Disk.get().getDefaultInstance());
            event.accept(ModItems.IceLoot.get().getDefaultInstance());
            event.accept(ModItems.FireWorkGun.get().getDefaultInstance());
            event.accept(ModItems.SpringMoney.get().getDefaultInstance());
            event.accept(ModItems.RedEnvelope.get().getDefaultInstance());
            event.accept(ModItems.SpringGoldCoin.get().getDefaultInstance());
            event.accept(ModItems.DevilLoot.get().getDefaultInstance());
            event.accept(ModItems.DragonPrefix.get().getDefaultInstance());
            event.accept(ModItems.MoonLoot.get().getDefaultInstance());
            event.accept(ModItems.CastleLoot.get().getDefaultInstance());
            event.accept(ModItems.LotteryStar.get().getDefaultInstance());
            event.accept(ModItems.LotteryPrefix.get().getDefaultInstance());

            event.accept(ModItems.SwordLottery.get().getDefaultInstance());
            event.accept(ModItems.BowLottery.get().getDefaultInstance());
            event.accept(ModItems.SceptreLottery.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.RUNESANDCURIOS_TAB.getKey())) {
            event.accept(ModItems.PlainRune0.get().getDefaultInstance());
            event.accept(ModItems.PlainRune1.get().getDefaultInstance());
            event.accept(ModItems.PlainRune2.get().getDefaultInstance());
            event.accept(ModItems.PlainRune3.get().getDefaultInstance());
            event.accept(ModItems.ForestRune0.get().getDefaultInstance());
            event.accept(ModItems.ForestRune1.get().getDefaultInstance());
            event.accept(ModItems.ForestRune2.get().getDefaultInstance());
            event.accept(ModItems.ForestRune3.get().getDefaultInstance());
            event.accept(ModItems.PlainRing.get().getDefaultInstance());
            event.accept(ModItems.ForestRing.get().getDefaultInstance());
            event.accept(ModItems.LakeRing.get().getDefaultInstance());
            event.accept(ModItems.VolcanoRing.get().getDefaultInstance());
            event.accept(ModItems.Breathe.get().getDefaultInstance());
            event.accept(ModItems.FireResistent.get().getDefaultInstance());
            event.accept(ModItems.Climb.get().getDefaultInstance());
            event.accept(ModItems.PlainCord.get().getDefaultInstance());
            event.accept(ModItems.ForestCord.get().getDefaultInstance());
            event.accept(ModItems.LakeCord.get().getDefaultInstance());
            event.accept(ModItems.VolcanoCord.get().getDefaultInstance());
            event.accept(ModItems.PlainForestCord.get().getDefaultInstance());
            event.accept(ModItems.LakeVolcanoCord.get().getDefaultInstance());
            event.accept(ModItems.FinalCord.get().getDefaultInstance());
            event.accept(ModItems.NewCurios.get().getDefaultInstance());
            event.accept(ModItems.manaRune0.get().getDefaultInstance());
            event.accept(ModItems.manaRune1.get().getDefaultInstance());
            event.accept(ModItems.manaRune2.get().getDefaultInstance());
            event.accept(ModItems.manaRune3.get().getDefaultInstance());
            event.accept(ModItems.volcanoRune0.get().getDefaultInstance());
            event.accept(ModItems.volcanoRune1.get().getDefaultInstance());
            event.accept(ModItems.volcanoRune2.get().getDefaultInstance());
            event.accept(ModItems.volcanoRune3.get().getDefaultInstance());
            event.accept(ModItems.LevelReward5.get().getDefaultInstance());
            event.accept(ModItems.LevelReward10.get().getDefaultInstance());
            event.accept(ModItems.LevelReward15.get().getDefaultInstance());
            event.accept(ModItems.LevelReward20.get().getDefaultInstance());
            event.accept(ModItems.LevelReward25.get().getDefaultInstance());
            event.accept(ModItems.LevelReward30.get().getDefaultInstance());
            event.accept(ModItems.LevelReward35.get().getDefaultInstance());
            event.accept(ModItems.LevelReward40.get().getDefaultInstance());
            event.accept(ModItems.LevelReward45.get().getDefaultInstance());
            event.accept(ModItems.LevelReward50.get().getDefaultInstance());
            event.accept(ModItems.LevelReward55.get().getDefaultInstance());
            event.accept(ModItems.LevelReward60.get().getDefaultInstance());
            event.accept(ModItems.Main1HandGem.get().getDefaultInstance());
            event.accept(ModItems.Main1BeltGem.get().getDefaultInstance());
            event.accept(ModItems.Main1necklaceGem.get().getDefaultInstance());
            event.accept(ModItems.Main1BraceletGem.get().getDefaultInstance());
            event.accept(ModItems.GemBag.get().getDefaultInstance());
            event.accept(ModItems.RandomGemPiece.get().getDefaultInstance());
            event.accept(ModItems.NetherRune0.get().getDefaultInstance());
            event.accept(ModItems.NetherRune1.get().getDefaultInstance());
            event.accept(ModItems.NetherRune2.get().getDefaultInstance());
            event.accept(ModItems.NetherRune3.get().getDefaultInstance());
            event.accept(ModItems.SnowRune0.get().getDefaultInstance());
            event.accept(ModItems.SnowRune1.get().getDefaultInstance());
            event.accept(ModItems.SnowRune2.get().getDefaultInstance());
            event.accept(ModItems.SnowRune3.get().getDefaultInstance());
            event.accept(ModItems.NetherGem.get().getDefaultInstance());
            event.accept(ModItems.NetherGemPiece.get().getDefaultInstance());
            event.accept(ModItems.NetherGemPieceBag1.get().getDefaultInstance());
            event.accept(ModItems.NetherGemPieceBag2.get().getDefaultInstance());
            event.accept(ModItems.NetherGemPieceBag3.get().getDefaultInstance());
            event.accept(ModItems.NetherGemPieceBag4.get().getDefaultInstance());
            event.accept(ModItems.ForestBossGem.get().getDefaultInstance());
            event.accept(ModItems.VolcanoBossGem.get().getDefaultInstance());
            event.accept(ModItems.LakeBossGem.get().getDefaultInstance());
            event.accept(ModItems.SkyBossGem.get().getDefaultInstance());
            event.accept(ModItems.PlainBracelet.get().getDefaultInstance());
            event.accept(ModItems.ForestBracelet.get().getDefaultInstance());
            event.accept(ModItems.LakeBracelet.get().getDefaultInstance());
            event.accept(ModItems.VolcanoBracelet.get().getDefaultInstance());
            event.accept(ModItems.MineBracelet.get().getDefaultInstance());
            event.accept(ModItems.SnowBracelet.get().getDefaultInstance());
            event.accept(ModItems.SkyBracelet.get().getDefaultInstance());
            event.accept(ModItems.PlainCrest0.get().getDefaultInstance());
            event.accept(ModItems.PlainCrest1.get().getDefaultInstance());
            event.accept(ModItems.PlainCrest2.get().getDefaultInstance());
            event.accept(ModItems.PlainCrest3.get().getDefaultInstance());
            event.accept(ModItems.PlainCrest4.get().getDefaultInstance());
            event.accept(ModItems.ForestCrest0.get().getDefaultInstance());
            event.accept(ModItems.ForestCrest1.get().getDefaultInstance());
            event.accept(ModItems.ForestCrest2.get().getDefaultInstance());
            event.accept(ModItems.ForestCrest3.get().getDefaultInstance());
            event.accept(ModItems.ForestCrest4.get().getDefaultInstance());
            event.accept(ModItems.LakeCrest0.get().getDefaultInstance());
            event.accept(ModItems.LakeCrest1.get().getDefaultInstance());
            event.accept(ModItems.LakeCrest2.get().getDefaultInstance());
            event.accept(ModItems.LakeCrest3.get().getDefaultInstance());
            event.accept(ModItems.LakeCrest4.get().getDefaultInstance());
            event.accept(ModItems.VolcanoCrest0.get().getDefaultInstance());
            event.accept(ModItems.VolcanoCrest1.get().getDefaultInstance());
            event.accept(ModItems.VolcanoCrest2.get().getDefaultInstance());
            event.accept(ModItems.VolcanoCrest3.get().getDefaultInstance());
            event.accept(ModItems.VolcanoCrest4.get().getDefaultInstance());
            event.accept(ModItems.MineCrest0.get().getDefaultInstance());
            event.accept(ModItems.MineCrest1.get().getDefaultInstance());
            event.accept(ModItems.MineCrest2.get().getDefaultInstance());
            event.accept(ModItems.MineCrest3.get().getDefaultInstance());
            event.accept(ModItems.MineCrest4.get().getDefaultInstance());
            event.accept(ModItems.SnowCrest0.get().getDefaultInstance());
            event.accept(ModItems.SnowCrest1.get().getDefaultInstance());
            event.accept(ModItems.SnowCrest2.get().getDefaultInstance());
            event.accept(ModItems.SnowCrest3.get().getDefaultInstance());
            event.accept(ModItems.SnowCrest4.get().getDefaultInstance());
            event.accept(ModItems.SkyCrest0.get().getDefaultInstance());
            event.accept(ModItems.SkyCrest1.get().getDefaultInstance());
            event.accept(ModItems.SkyCrest2.get().getDefaultInstance());
            event.accept(ModItems.SkyCrest3.get().getDefaultInstance());
            event.accept(ModItems.SkyCrest4.get().getDefaultInstance());
            event.accept(ModItems.ManaCrest0.get().getDefaultInstance());
            event.accept(ModItems.ManaCrest1.get().getDefaultInstance());
            event.accept(ModItems.ManaCrest2.get().getDefaultInstance());
            event.accept(ModItems.ManaCrest3.get().getDefaultInstance());
            event.accept(ModItems.ManaCrest4.get().getDefaultInstance());
            event.accept(ModItems.PlainAttackRing0.get().getDefaultInstance());
            event.accept(ModItems.PlainAttackRing1.get().getDefaultInstance());
            event.accept(ModItems.PlainAttackRing2.get().getDefaultInstance());
            event.accept(ModItems.PlainAttackRing3.get().getDefaultInstance());
            event.accept(ModItems.PlainManaAttackRing0.get().getDefaultInstance());
            event.accept(ModItems.PlainManaAttackRing1.get().getDefaultInstance());
            event.accept(ModItems.PlainManaAttackRing2.get().getDefaultInstance());
            event.accept(ModItems.PlainManaAttackRing3.get().getDefaultInstance());
            event.accept(ModItems.PlainHealthRing0.get().getDefaultInstance());
            event.accept(ModItems.PlainHealthRing1.get().getDefaultInstance());
            event.accept(ModItems.PlainHealthRing2.get().getDefaultInstance());
            event.accept(ModItems.PlainHealthRing3.get().getDefaultInstance());
            event.accept(ModItems.PlainDefenceRing0.get().getDefaultInstance());
            event.accept(ModItems.PlainDefenceRing1.get().getDefaultInstance());
            event.accept(ModItems.PlainDefenceRing2.get().getDefaultInstance());
            event.accept(ModItems.PlainDefenceRing3.get().getDefaultInstance());
            event.accept(ModItems.FantasyMedal.get().getDefaultInstance());
            event.accept(ModItems.FantasyBracelet.get().getDefaultInstance());
            event.accept(ModItems.SpringRing0.get().getDefaultInstance());
            event.accept(ModItems.SpringRing1.get().getDefaultInstance());
            event.accept(ModItems.SpringRing2.get().getDefaultInstance());
            event.accept(ModItems.SpringRing3.get().getDefaultInstance());
            event.accept(ModItems.SpringHand0.get().getDefaultInstance());
            event.accept(ModItems.SpringHand1.get().getDefaultInstance());
            event.accept(ModItems.SpringHand2.get().getDefaultInstance());
            event.accept(ModItems.SpringHand3.get().getDefaultInstance());
            event.accept(ModItems.SpringNecklace0.get().getDefaultInstance());
            event.accept(ModItems.SpringNecklace1.get().getDefaultInstance());
            event.accept(ModItems.SpringNecklace2.get().getDefaultInstance());
            event.accept(ModItems.SpringNecklace3.get().getDefaultInstance());
            event.accept(ModItems.SpringBelt0.get().getDefaultInstance());
            event.accept(ModItems.SpringBelt1.get().getDefaultInstance());
            event.accept(ModItems.SpringBelt2.get().getDefaultInstance());
            event.accept(ModItems.SpringBelt3.get().getDefaultInstance());
            event.accept(ModItems.SpringLoot.get().getDefaultInstance());
            event.accept(ModItems.EndRune0.get().getDefaultInstance());
            event.accept(ModItems.EndRune1.get().getDefaultInstance());
            event.accept(ModItems.EndRune2.get().getDefaultInstance());
            event.accept(ModItems.EndRune3.get().getDefaultInstance());
            event.accept(ModItems.Boss2AttackRing0.get().getDefaultInstance());
            event.accept(ModItems.Boss2AttackRing1.get().getDefaultInstance());
            event.accept(ModItems.Boss2AttackRing2.get().getDefaultInstance());
            event.accept(ModItems.Boss2AttackRing3.get().getDefaultInstance());
            event.accept(ModItems.Boss2ManaAttackRing0.get().getDefaultInstance());
            event.accept(ModItems.Boss2ManaAttackRing1.get().getDefaultInstance());
            event.accept(ModItems.Boss2ManaAttackRing2.get().getDefaultInstance());
            event.accept(ModItems.Boss2ManaAttackRing3.get().getDefaultInstance());
            event.accept(ModItems.Boss2HealthRing0.get().getDefaultInstance());
            event.accept(ModItems.Boss2HealthRing1.get().getDefaultInstance());
            event.accept(ModItems.Boss2HealthRing2.get().getDefaultInstance());
            event.accept(ModItems.Boss2HealthRing3.get().getDefaultInstance());
            event.accept(ModItems.Boss2DefenceRing0.get().getDefaultInstance());
            event.accept(ModItems.Boss2DefenceRing1.get().getDefaultInstance());
            event.accept(ModItems.Boss2DefenceRing2.get().getDefaultInstance());
            event.accept(ModItems.Boss2DefenceRing3.get().getDefaultInstance());
            event.accept(ModItems.EarthManaCurios.get().getDefaultInstance());
            event.accept(ModItems.BloodManaCurios.get().getDefaultInstance());
            event.accept(ModItems.FieldGem.get().getDefaultInstance());
            event.accept(ModItems.MineGem.get().getDefaultInstance());
            event.accept(ModItems.LifeManaGem.get().getDefaultInstance());
            event.accept(ModItems.ObsiManaGem.get().getDefaultInstance());
            event.accept(ModItems.NetherSkeletonGem.get().getDefaultInstance());
            event.accept(ModItems.MagmaGem.get().getDefaultInstance());
            event.accept(ModItems.WitherGem.get().getDefaultInstance());
            event.accept(ModItems.PiglinGem.get().getDefaultInstance());
            event.accept(ModItems.SakuraGem.get().getDefaultInstance());
            event.accept(ModItems.ShipGem.get().getDefaultInstance());
            event.accept(ModItems.DevilBloodManaCurios.get().getDefaultInstance());
            event.accept(ModItems.DevilEarthManaCurios.get().getDefaultInstance());
            event.accept(ModItems.MoonCurios.get().getDefaultInstance());
            event.accept(ModItems.MoonAttackGem.get().getDefaultInstance());
            event.accept(ModItems.MoonManaGem.get().getDefaultInstance());
            event.accept(ModItems.MoonBelt.get().getDefaultInstance());
            event.accept(ModItems.SkyGemD.get().getDefaultInstance());
            event.accept(ModItems.EvokerGemD.get().getDefaultInstance());
            event.accept(ModItems.PlainGemD.get().getDefaultInstance());
            event.accept(ModItems.ForestGemD.get().getDefaultInstance());
            event.accept(ModItems.LakeGemD.get().getDefaultInstance());
            event.accept(ModItems.VolcanoGemD.get().getDefaultInstance());
            event.accept(ModItems.SnowGemD.get().getDefaultInstance());
            event.accept(ModItems.FieldGemD.get().getDefaultInstance());
            event.accept(ModItems.MineGemD.get().getDefaultInstance());
            event.accept(ModItems.LifeManaGemD.get().getDefaultInstance());
            event.accept(ModItems.ObsiManaGemD.get().getDefaultInstance());
            event.accept(ModItems.NetherSkeletonGemD.get().getDefaultInstance());
            event.accept(ModItems.MagmaGemD.get().getDefaultInstance());
            event.accept(ModItems.WitherGemD.get().getDefaultInstance());
            event.accept(ModItems.PiglinGemD.get().getDefaultInstance());
            event.accept(ModItems.SakuraGemD.get().getDefaultInstance());
            event.accept(ModItems.ShipGemD.get().getDefaultInstance());
            event.accept(ModItems.MoonAttackGemD.get().getDefaultInstance());
            event.accept(ModItems.MoonManaGemD.get().getDefaultInstance());
            event.accept(ModItems.ParkourGloves.get().getDefaultInstance());
            event.accept(ModItems.BeaconBracelet.get().getDefaultInstance());
            event.accept(ModItems.BlazeBracelet.get().getDefaultInstance());
            event.accept(ModItems.TreeBracelet.get().getDefaultInstance());
            event.accept(ModItems.CastleNecklace.get().getDefaultInstance());
            event.accept(ModItems.CastleBrooch.get().getDefaultInstance());
            event.accept(ModItems.RubyNecklace.get().getDefaultInstance());
            event.accept(ModItems.RubyNecklace1.get().getDefaultInstance());
            event.accept(ModItems.RubyNecklace2.get().getDefaultInstance());
            event.accept(ModItems.RubyNecklace3.get().getDefaultInstance());
            event.accept(ModItems.SapphireNecklace.get().getDefaultInstance());
            event.accept(ModItems.SapphireNecklace1.get().getDefaultInstance());
            event.accept(ModItems.SapphireNecklace2.get().getDefaultInstance());
            event.accept(ModItems.SapphireNecklace3.get().getDefaultInstance());
            event.accept(ModItems.FancySapphireNecklace.get().getDefaultInstance());
            event.accept(ModItems.FancySapphireNecklace1.get().getDefaultInstance());
            event.accept(ModItems.FancySapphireNecklace2.get().getDefaultInstance());
            event.accept(ModItems.FancySapphireNecklace3.get().getDefaultInstance());
            event.accept(ModItems.CastleWeaponGem.get().getDefaultInstance());
            event.accept(ModItems.CastleArmorGem.get().getDefaultInstance());
            event.accept(ModItems.CastleCuriosPowder.get().getDefaultInstance());
            event.accept(ModItems.LakeRune0.get().getDefaultInstance());
            event.accept(ModItems.LakeRune1.get().getDefaultInstance());
            event.accept(ModItems.LakeRune2.get().getDefaultInstance());
            event.accept(ModItems.LakeRune3.get().getDefaultInstance());
            event.accept(ModItems.StarBottle.get().getDefaultInstance());

            event.accept(ModItems.EndCurios.get().getDefaultInstance());
            event.accept(ModItems.EndCurios1.get().getDefaultInstance());
        }

        if (event.getTabKey().equals(ModCreativeModeTab.MISC_TAB.getKey())) {
            event.accept(ModItems.Main0.get().getDefaultInstance());
            event.accept(ModItems.Main0_1.get().getDefaultInstance());
            event.accept(ModItems.Main0_2.get().getDefaultInstance());
            event.accept(ModItems.Main0_3.get().getDefaultInstance());
            event.accept(ModItems.Main0_4.get().getDefaultInstance());
            event.accept(ModItems.Main0_5.get().getDefaultInstance());
            event.accept(ModItems.Main1_0.get().getDefaultInstance());
            event.accept(ModItems.Main1_1.get().getDefaultInstance());
            event.accept(ModItems.Main1_2.get().getDefaultInstance());
            event.accept(ModItems.Main1_3.get().getDefaultInstance());
            event.accept(ModItems.Main1_4.get().getDefaultInstance());
            event.accept(ModItems.Main1_5.get().getDefaultInstance());
            event.accept(ModItems.Note_0.get().getDefaultInstance());
            event.accept(ModItems.ExploreNote.get().getDefaultInstance());
            event.accept(ModItems.ForNew.get().getDefaultInstance());
            event.accept(ModItems.BackSpawn.get().getDefaultInstance());
            event.accept(ModItems.main1reward.get().getDefaultInstance());
            event.accept(ModItems.tickettosky.get().getDefaultInstance());
            event.accept(ModItems.quest.get().getDefaultInstance());
            event.accept(ModItems.spawn1.get().getDefaultInstance());
            event.accept(ModItems.profession_bow.get().getDefaultInstance());
            event.accept(ModItems.profession_sword.get().getDefaultInstance());
            event.accept(ModItems.profession_barker.get().getDefaultInstance());
            event.accept(ModItems.Note_1.get().getDefaultInstance());
            event.accept(ModItems.Note_2.get().getDefaultInstance());
            event.accept(ModItems.Note_3.get().getDefaultInstance());
            event.accept(ModItems.drug0.get().getDefaultInstance());
            event.accept(ModItems.LightningChange.get().getDefaultInstance());
            event.accept(ModItems.ID_Card.get().getDefaultInstance());
            event.accept(ModItems.Ps_Bottle0.get().getDefaultInstance());
            event.accept(ModItems.Ps_Bottle1.get().getDefaultInstance());
            event.accept(ModItems.Ps_Bottle2.get().getDefaultInstance());
            event.accept(ModItems.ParkourMedal.get().getDefaultInstance());
            event.accept(ModItems.ParkourTicket.get().getDefaultInstance());
            event.accept(ModItems.KillPaperLoot.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.DEVELOPMENT_TAB.getKey())) {
            event.accept(ModItems.SignInReset.get().getDefaultInstance());
            event.accept(ModItems.SignInGet.get().getDefaultInstance());
            event.accept(ModItems.GetTime.get().getDefaultInstance());
            event.accept(ModItems.ItemIDCheck.get().getDefaultInstance());
            event.accept(ModItems.attributecheck.get().getDefaultInstance());
            event.accept(ModItems.hovertest.get().getDefaultInstance());
            event.accept(ModItems.ArrowItem.get().getDefaultInstance());
            event.accept(ModItems.Extraction.get().getDefaultInstance());
            event.accept(ModItems.Security.get().getDefaultInstance());
            event.accept(ModItems.ResetSecurity.get().getDefaultInstance());
            event.accept(ModItems.tick.get().getDefaultInstance());
            event.accept(ModItems.EntityCopy.get().getDefaultInstance());
            event.accept(ModItems.BlockReset.get().getDefaultInstance());
            event.accept(ModItems.Shoot.get().getDefaultInstance());
            event.accept(ModItems.FeiLeiShen.get().getDefaultInstance());
            event.accept(ModItems.quartzcheck.get().getDefaultInstance());
            event.accept(ModItems.GuiOpen.get().getDefaultInstance());
            event.accept(ModItems.BarrierSet.get().getDefaultInstance());
            event.accept(ModItems.WaterSet.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.WORLD_SOUL.getKey())) {
            event.accept(ModItems.WorldSoul1.get().getDefaultInstance());
            event.accept(ModItems.WorldSoul2.get().getDefaultInstance());
            event.accept(ModItems.WorldSoul3.get().getDefaultInstance());
            event.accept(ModItems.WorldSoul4.get().getDefaultInstance());
            event.accept(ModItems.WorldSoul5.get().getDefaultInstance());
            event.accept(ModItems.SoulSword.get().getDefaultInstance());
            event.accept(ModItems.SoulBow.get().getDefaultInstance());
            event.accept(ModItems.SoulSceptre.get().getDefaultInstance());
            event.accept(ModItems.SkillReset.get().getDefaultInstance());
            event.accept(ModItems.TpToSky.get().getDefaultInstance());
            event.accept(ModItems.TpToSakura.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.KILL_PAPER.getKey())) {
            Item[] items = {
                    ModItems.PlainZombieKillPaper.get(), ModItems.ForestSkeletonKillPaper.get(),
                    ModItems.ForestZombieKillPaper.get(), ModItems.FieldZombieKillPaper.get(),
                    ModItems.LakeDrownedKillPaper.get(), ModItems.VolcanoBlazeKillPaper.get(),
                    ModItems.MineZombieKillPaper.get(), ModItems.MineSkeletonKillPaper.get(),
                    ModItems.SnowStrayKillPaper.get(), ModItems.SkyVexKillPaper.get(),
                    ModItems.EvokerKillPaper.get(), ModItems.EvokerMasterKillPaper.get(),
                    ModItems.SeaGuardianKillPaper.get(), ModItems.LightingZombieKillPaper.get(),
                    ModItems.HuskKillPaper.get(), ModItems.KazeZombieKillPaper.get(),
                    ModItems.SpiderKillPaper.get(), ModItems.SilverFishKillPaper.get(),
                    ModItems.WitherSkeletonKillPaper.get(), ModItems.PiglinKillPaper.get(),
                    ModItems.NetherSkeletonKillPaper.get(), ModItems.NetherMagmaKillPaper.get(),
                    ModItems.EnderManKillPaper.get(), ModItems.SakuraMobKillPaper.get(),
                    ModItems.ScarecrowKillPaper.get(), ModItems.MineWorkerKillPaper.get(),
                    ModItems.ShipWorkerKillPaper.get(), ModItems.IceHunterKillPaper.get(),
                    ModItems.EarthManaKillPaper.get(), ModItems.BloodManaKillPaper.get(),
                    ModItems.SlimeKillPaper.get(), ModItems.BeaconKillPaper.get(),
                    ModItems.TreeKillPaper.get(), ModItems.BlazeKillPaper.get(),
                    ModItems.StarKillPaper.get(), ModItems.LifeElementKillPaper.get(),
                    ModItems.WaterElementKillPaper.get(), ModItems.FireElementKillPaper.get(),
                    ModItems.StoneElementKillPaper.get(), ModItems.IceElementKillPaper.get(),
                    ModItems.LightningElementKillPaper.get(), ModItems.WindElementKillPaper.get(),
                    ModItems.ShulkerKillPaper.get(), ModItems.EnderMiteKillPaper.get(),

                    ModItems.CastleMopUpPaper.get(), ModItems.CastleSecondFloorMopUpPaper.get(),
                    ModItems.DevilMopUpPaper.get(), ModItems.IceKnightMopUpPaper.get(),
                    ModItems.LightningMopUpPaper.get(), ModItems.Main1BossMopUpPaper.get(),
                    ModItems.MoonMopUpPaper.get(), ModItems.NetherMopUpPaper.get(),
                    ModItems.PlainMopUpPaper.get(), ModItems.PurpleIronKnightMopUpPaper.get(),
                    ModItems.SakuraBossMopUpPaper.get(), ModItems.TabooDevilMopUpPaper.get(),
            };
            for (Item item : items) event.accept(item.getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.FURNACE.getKey())) {
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
        if (event.getTabKey().equals(ModCreativeModeTab.SPRING.getKey())) {
            event.accept(ModItems.FireWorkGun.get().getDefaultInstance());
            event.accept(ModItems.SpringMoney.get().getDefaultInstance());
            event.accept(ModItems.SpringGoldCoin.get().getDefaultInstance());
            event.accept(ModItems.SpringHeart.get().getDefaultInstance());
            event.accept(ModItems.SpringSoul.get().getDefaultInstance());
            event.accept(ModItems.SpringLoot.get().getDefaultInstance());
            event.accept(ModItems.FireCracker.get().getDefaultInstance());
            event.accept(ModItems.SpringAttackArmorHelmet.get().getDefaultInstance());
            event.accept(ModItems.SpringAttackArmorChest.get().getDefaultInstance());
            event.accept(ModItems.SpringAttackArmorLeggings.get().getDefaultInstance());
            event.accept(ModItems.SpringAttackArmorBoots.get().getDefaultInstance());
            event.accept(ModItems.SpringSwiftArmorHelmet.get().getDefaultInstance());
            event.accept(ModItems.SpringSwiftArmorChest.get().getDefaultInstance());
            event.accept(ModItems.SpringSwiftArmorLeggings.get().getDefaultInstance());
            event.accept(ModItems.SpringSwiftArmorBoots.get().getDefaultInstance());
            event.accept(ModItems.SpringManaArmorHelmet.get().getDefaultInstance());
            event.accept(ModItems.SpringManaArmorChest.get().getDefaultInstance());
            event.accept(ModItems.SpringManaArmorLeggings.get().getDefaultInstance());
            event.accept(ModItems.SpringManaArmorBoots.get().getDefaultInstance());
            event.accept(ModItems.SpringRing0.get().getDefaultInstance());
            event.accept(ModItems.SpringRing1.get().getDefaultInstance());
            event.accept(ModItems.SpringRing2.get().getDefaultInstance());
            event.accept(ModItems.SpringRing3.get().getDefaultInstance());
            event.accept(ModItems.SpringHand0.get().getDefaultInstance());
            event.accept(ModItems.SpringHand1.get().getDefaultInstance());
            event.accept(ModItems.SpringHand2.get().getDefaultInstance());
            event.accept(ModItems.SpringHand3.get().getDefaultInstance());
            event.accept(ModItems.SpringBelt0.get().getDefaultInstance());
            event.accept(ModItems.SpringBelt1.get().getDefaultInstance());
            event.accept(ModItems.SpringBelt2.get().getDefaultInstance());
            event.accept(ModItems.SpringBelt3.get().getDefaultInstance());
            event.accept(ModItems.SpringNecklace0.get().getDefaultInstance());
            event.accept(ModItems.SpringNecklace1.get().getDefaultInstance());
            event.accept(ModItems.SpringNecklace2.get().getDefaultInstance());
            event.accept(ModItems.SpringNecklace3.get().getDefaultInstance());
            event.accept(ModItems.SpringPiece.get().getDefaultInstance());
            event.accept(ModItems.SpringScale0.get().getDefaultInstance());
            event.accept(ModItems.SpringScale1.get().getDefaultInstance());
            event.accept(ModItems.SpringScale2.get().getDefaultInstance());
            event.accept(ModItems.SpringScale3.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.CUSTOMIZED.getKey())) {
            Item[] items = {
                    ModItems.LXYZOSword.get(), ModItems.LXYZOSwordPaper.get(),
                    ModItems.ShangMengLi_Sceptre.get(), ModItems.ShangMengLiSceptre_Paper.get(),
                    ModItems.XiangLiPickaxe.get(), ModItems.XiangLiPickaxe_Paper.get(),
                    ModItems.ZeusCurios.get(), ModItems.Crush1CuriosPaper.get(),
                    ModItems.ZeusSword.get(), ModItems.ZeusSwordPaper.get(),
                    ModItems.ShangMengLiCurios.get(), ModItems.ShangMengLiCuriosPaper.get(),
                    ModItems.LiuLiXianCurios.get(), ModItems.LiuLiXianCuriosPaper.get(),
                    ModItems.VeryNewCurios.get(), ModItems.VeryNewCuriosPaper.get(),
                    ModItems.ShowDickerBow.get(), ModItems.ShowDickerBowPaper.get(),
                    ModItems.LiuLiXianSword.get(), ModItems.LiuLiXianSceptre.get(), ModItems.LiuLiXianSwordPaper.get(),
                    ModItems.ShangMengLiCurios1.get(), ModItems.ShangMengLiCurios1Paper.get(),
                    ModItems.GuangYiBow.get(), ModItems.GuangYiBowPaper.get(),
                    ModItems.FengxiaojuCurios.get(), ModItems.FengxiaojuCuriosPaper.get(),
                    ModItems.XiangLiSmoke.get(), ModItems.XiangLiSmokePaper.get(),
                    ModItems.WcBow.get(), ModItems.WcBowPaper.get(),
                    ModItems.XiangLiCurios.get(), ModItems.XiangLiCuriosPaper.get(), ModItems.XiangLiPrefix.get(),
                    ModItems.BlackFeisaSceptre.get(), ModItems.BlackFeisaSceptrePaper.get(),
                    ModItems.EliaoiBook.get(), ModItems.EliaoiBookPaper.get(),
                    ModItems.EliaoiCurios.get(), ModItems.EliaoiCuriosPaper.get(),
                    ModItems.DING_ZHEN_MUSIC_DISC.get(),
                    ModItems.LiuLiXianCurios1.get(), ModItems.LiuLiXianCurios1Paper.get(),
                    ModItems.ShangMengLiCurios2.get(), ModItems.ShangMengLiCurios2Paper.get(),
                    ModItems.BlackFeisaCurios.get(), ModItems.BlackFeisaCuriosPaper.get(),
                    ModItems.QiFuBow.get(), ModItems.QiFuSceptrePaper.get(),
                    ModItems.BlackFeisaCurios2.get(), ModItems.BlackFeisaCurios2Paper.get(),
                    ModItems.ShangMengLiSword.get(), ModItems.ShangMengLiSwordPaper.get(),
                    ModItems.YuanShiRenSceptre.get(), ModItems.YuanShiRenSceptrePaper.get(),
                    ModItems.YuanShiRenCurios.get(), ModItems.YuanShiRenCuriosPaper.get(),
                    ModItems.QiFuCurios.get(), ModItems.QiFuCuriosPaper.get(),
                    ModItems.BlackFeisaCurios3.get(), ModItems.BlackFeisaCurios3Paper.get(),
                    ModItems.CgswdCurios.get(), ModItems.CgswdCuriosPaper.get(),
                    ModItems.LeiyanCurios.get(), ModItems.LeiyanCuriosPaper.get(),
                    ModItems.CgswdSceptre.get(), ModItems.CgswdSceptrePaper.get(),
                    ModItems.YxwgBow.get(), ModItems.YxwgBowPaper.get(),
                    ModItems.MyMissionBow.get(), ModItems.MyMissionBowPaper.get(),
                    ModItems.YxwgCurios.get(), ModItems.YxwgCuriosPaper.get(),
                    ModItems.YxwgCurios1.get(), ModItems.YxwgCurios1Paper.get(),
                    ModItems.SoraCurios1.get(), ModItems.SoraCurios1Paper.get(),
                    ModItems.MyMissionCurios.get(), ModItems.MyMissionCuriosPaper.get(),
                    ModItems.MyMissionCurios1.get(), ModItems.MyMissionCurios1Paper.get(),
                    ModItems.LeiyanBow.get(), ModItems.LeiyanBowPaper.get(),
                    ModItems.FengxiaojuCurios_1.get(), ModItems.FengxiaojuCurios_1Paper.get(),
                    ModItems.EliaoiCurios1.get(), ModItems.EliaoiCurios1Paper.get(),
                    ModItems.LiulixianCurios2.get(), ModItems.LiulixianCurios2Paper.get(),
                    ModItems.SoraCurios2.get(), ModItems.SoraCurios2Paper.get(),
                    ModItems.AttackCurios0.get(), ModItems.BowCurios0.get(), ModItems.ManaCurios0.get(),
                    ModItems.YxwgCurios2.get(), ModItems.YxwgCurios2Paper.get(),
                    ModItems.WcCurios.get(), ModItems.WcCuriosPaper.get(),
                    ModItems.QiFuCurios1.get(), ModItems.QiFuCurios1Paper.get(),
                    ModItems.ShangmengliCurios3.get(), ModItems.ShangmengliCurios3Paper.get(),
                    ModItems.BlackFeisaCurios4.get(), ModItems.BlackFeisaCurios4Paper.get(),
                    ModItems.SoraSword.get(), ModItems.SoraSwordPaper.get(),
                    ModItems.EliaoiCurios2.get(), ModItems.EliaoiCurios2Paper.get(),
                    ModItems.ZuosiCurios.get(), ModItems.ZuosiCuriosPaper.get(),
                    ModItems.LiulixianCurios3.get(), ModItems.LiulixianCurios3Paper.get(),
                    ModItems.BlackFeisaCurios5.get(), ModItems.BlackFeisaCurios5Paper.get(),
                    ModItems.HeihuangCurios.get(), ModItems.HeihuangCuriosPaper.get(),
                    ModItems.HgjCurios.get(), ModItems.HgjCuriosPaper.get(),
                    ModItems.AttackCurios1.get(), ModItems.AttackCurios2.get(),
                    ModItems.BowCurios1.get(), ModItems.BowCurios2.get(),
                    ModItems.ManaCurios1.get(), ModItems.ManaCurios2.get(),
                    ModItems.LifeCurios0.get(), ModItems.WaterCurios0.get(), ModItems.FireCurios0.get(),
                    ModItems.StoneCurios0.get(), ModItems.IceCurios0.get(), ModItems.WindCurios0.get(),
                    ModItems.LightningCurios0.get(),
                    ModItems.ShaoFengCurios.get(), ModItems.ShaoFengCuriosPaper.get(),
                    ModItems.LittleartCurios.get(), ModItems.LittleartCuriosPaper.get(),
                    ModItems.LiulixianCurios4.get(), ModItems.LiulixianCurios4Paper.get(),
            };
            for (Item item : items) event.accept(item.getDefaultInstance());
        }
    }

/*    @SubscribeEvent
    public static void SpeedSet(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        double SpeedUp = Compute.PlayerSpeedImprove(player);
        if(!player.getLevel().isClientSide) player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1D+0.1*speedup);
        CompoundTag data = player.getPersistentData();
        if(!player.getLevel().isClientSide)
        {
            if(event.player.tickCount % 100 == 0 && data.contains("Green") && data.getInt("Green") == 2 && event.phase == TickEvent.Phase.START)
            {
                Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                        Component.literal("平原的风为你指引道路").withStyle(ChatFormatting.GREEN));
                Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                        Component.literal("平原符石-临危制变为你提供了").withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("%.2f",100*(((player.getMaxHealth()-player.getHealth())*1.0 / player.getMaxHealth()) / 2.0))).withStyle(ChatFormatting.GREEN)).append(Component.literal("%")).append(Component.literal("·移动速度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD)));
            }
        }
    }*/
/*    @SubscribeEvent
    public static void RightClick(PlayerInteractEvent event)
    {
        Player player = event.getEntity();
        if(event.getEntity().getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline))
        {
            event.setCanceled(true);
            event.getEntity().sendSystemMessage(Component.literal("Please Input Password First!"));
        }
        Item item = player.getMainHandItem().getItem();
        if(Utils.MainHandTag.containsKey(item) || Utils.Defence.Contains(item)){
            ItemStack mainhand = player.getMainHandItem();
            CompoundTag data = mainhand.getOrCreateTagElement(Utils.MOD_ID);
            if(!data.contains(InventoryCheck.owner))
            {
                data.putString(InventoryCheck.owner,player.getName().getString());
                player.getMainHandItem().hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
            }
        }
    }*/
/*    @SubscribeEvent
    public static void ToolTipChange(ItemTooltipEvent event)
    {
        if(event.getItemStack().getTagElement(Utils.MOD_ID)!=null)
        {
            ItemStack equip = event.getItemStack();
            CompoundTag data = event.getItemStack().getOrCreateTagElement(Utils.MOD_ID);
            if(data.contains("attackdamage")) event.getToolTip().add(Component.literal("·基础攻击 ").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("attackdamage"))).withStyle(ChatFormatting.WHITE)));
            if(data.contains("breakDefence")) event.getToolTip().add(Component.literal("·护甲穿透+").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("breakDefence")*100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("criticalrate")) event.getToolTip().add(Component.literal("·暴击几率+").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("criticalrate")*100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("criticaldamage")) event.getToolTip().add(Component.literal("·暴击伤害+").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("criticaldamage")*100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("healsteal")) event.getToolTip().add(Component.literal("·生命偷取+").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("healsteal")*100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("speedup")) event.getToolTip().add(Component.literal("·移动速度+").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getDouble("speedup")*100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(Utils.MainHandTag.containsKey(event.getItemStack().getItem())) event.getToolTip().add(Component.literal("KillCount:"+event.getItemStack().getTagElement(Utils.MOD_ID).getInt("KillCount")).withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.UNDERLINE));
            if(data.contains("randomsword"))
            {
                event.getToolTip().add(Component.literal("··········································").withStyle(ChatFormatting.DARK_GRAY).withStyle(ChatFormatting.OBFUSCATED).withStyle(ChatFormatting.BOLD));
                event.getToolTip().add(Component.literal(" "));
                event.getToolTip().add(Component.literal("Forging-Sword-I").withStyle(ChatFormatting.GRAY));
                event.getToolTip().add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
            }
            if(data.contains("Slot") || data.contains("Gems1")) event.getToolTip().add(Component.literal(" "));
            if(data.contains("Slot") || data.contains("Gems1")) event.getToolTip().add(Component.literal("—————宝石孔位信息—————").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
            if(data.contains("Gems1") && data.getString("Gems1").equals("skyGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈天空宝石◈").withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems2") && data.getString("Gems2").equals("skyGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈天空宝石◈").withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems3") && data.getString("Gems3").equals("skyGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈天空宝石◈").withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)));
            if(data.contains("Slot"))
            {
                for(int i = 0; i < data.getInt("Slot") ; i++)
                {
                    event.getToolTip().add(Component.literal("[未镶嵌]").withStyle(ChatFormatting.GRAY));
                }
            }
            if(data.contains("Slot") || data.contains("Gems1")) event.getToolTip().add(Component.literal("—————宝石属性总和—————").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
            if(data.contains("Slot") || data.contains("Gems1"))
            {
                if(Compute.ItemBaseDamageGems(data) > 0) event.getToolTip().add(Component.literal("·基础攻击 + ").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",Compute.ItemBaseDamageGems(data))).withStyle(ChatFormatting.WHITE)));
                if(Compute.ItemSpeedUpGems(data) > 0) event.getToolTip().add(Component.literal("·移动速度 + ").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,Compute.ItemSpeedUpGems(data)*100)).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            }
            if(data.contains("Forging"))
            {
                event.getToolTip().add(Component.literal("—————强化属性信息—————").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));
                event.getToolTip().add(Component.literal("强化属性:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.WHITE));
                if(Utils.BaseDamage.containsKey(equip.getItem())) {
                    double DamageUp = Utils.BaseDamage.getValue(equip.getItem()).getValue() * (data.getInt("Forging") / 24.0d);
                    event.getToolTip().add(Component.literal("·基础攻击 + ").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",DamageUp)).withStyle(ChatFormatting.WHITE)));
                }
                if(Utils.ManaDamage.Contains(equip.getItem())){
                    float DamageUp = Utils.ManaDamage.getValue(equip.getItem()).getValue() * (data.getInt("Forging") / 24.0d);
                    event.getToolTip().add(Component.literal("·魔法攻击 + ").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",DamageUp)).withStyle(ChatFormatting.WHITE)));
                }
                if(Utils.Defence.Contains(equip.getItem())) {
                    double DefenceUp = Utils.Defence.getValue(equip.getItem()).getValue() * (data.getInt("Forging") / 24.0d);
                    event.getToolTip().add(Component.literal("·基础护甲 + ").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",DefenceUp)).withStyle(ChatFormatting.WHITE)));
                }
            }
            if(data.contains(InventoryCheck.owner))
            {
                event.getToolTip().add(Component.literal("Owner:"+data.getString(InventoryCheck.owner)).withStyle(ChatFormatting.AQUA));
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
        }
    }*/
/*    @SubscribeEvent
    public static void Clone(PlayerEvent.Clone event)
    {
        if(event.isWasDeath())
        {
            Compute.Broad(event.getEntity().level, Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal(event.getEntity().getName().getString()+"在探索过程中身负重伤，经过救治恢复了活力。").withStyle(ChatFormatting.WHITE)));
            event.getEntity().getPersistentData().merge(event.getOriginal().getPersistentData());
        }
    }*/
/*    @SubscribeEvent
    public static void Login0(PlayerEvent.PlayerLoggedInEvent event) throws ParseException {
        Player player = event.getEntity();
        player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("欢迎来到 ").withStyle(ChatFormatting.WHITE).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA))));
        CompoundTag data = player.getPersistentData();
        data.putDouble("DX",player.getX());
        data.putDouble("DY",player.getY());
        data.putDouble("DZ",player.getZ());
        data.putString(StringUtils.Login.Status,StringUtils.Login.Offline);
        data.putString("Name",player.getName().getString());
        if(!data.contains("MANA") || !data.contains("MAXMANA"))
        {
            data.putInt("MANA",100);
            data.putInt("MAXMANA",100);
            ModNetworking.sendToClient(new ManaSyncS2CPacket(9),(ServerPlayer) player);
        }
        if(data.contains("SignIn"))
        {
            String DateString = data.getString("SignIn");
            SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date1 = tmpDate.parse(DateString);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            cal.add(Calendar.HOUR_OF_DAY,22);
            Calendar cal1 = Calendar.getInstance();
            if(cal1.after(cal))
            {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("击杀一只怪物来获取签到奖励！").withStyle(ChatFormatting.WHITE)));
                data.putBoolean("SignAward",true);
            }
            else
            {
                String DateString0 = data.getString("SignIn");
                SimpleDateFormat tmpDate0 = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date10 = tmpDate0.parse(DateString0);
                Calendar cal0 = Calendar.getInstance();
                cal0.setTime(date10);
                cal0.add(Calendar.HOUR_OF_DAY,22);
                int year = cal0.get(Calendar.YEAR);
                int month = cal0.get(Calendar.MONTH)+1;
                int day = cal0.get(Calendar.DAY_OF_MONTH);
                int hour = cal0.get(Calendar.HOUR_OF_DAY);
                int minutes = cal0.get(Calendar.MINUTE);
                int seconds = cal0.get(Calendar.SECOND);
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("签到奖励将在 "+year+"年"+month+"月"+day+"日"+hour+"时"+minutes+"分"+seconds+"秒 可用").withStyle(ChatFormatting.WHITE)));

            }
        }
        else
        {
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("击杀一只怪物来获取签到奖励！").withStyle(ChatFormatting.WHITE)));
            data.putBoolean("SignAward",true);
        }
        if(!player.getTags().contains("player")) player.addTag("player");
        if(data.contains(StringUtils.Login.Password))
        {
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("使用/vmd login (密码)来登录").withStyle(ChatFormatting.WHITE)));
        }
        else
        {
            player.addItem(Moditems.ForNew.get().getDefaultInstance());
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("使用/vmd register (密码)来注册").withStyle(ChatFormatting.WHITE)));
        }
        */
    /*if(event.getEntity().serializeNBT().contains(StringUtils.Login.Password))
        {
            String string = "159357";
            event.getEntity().setTicksFrozen(999);
            if(event.getEntity().serializeNBT().getString(StringUtils.Login.Password).equals(string))
            {
                event.getEntity().setTicksFrozen(0);
            }
            else
            {
                event.getEntity().sendSystemMessage(Component.literal(".."));
            }
        }
        else
        {
            event.setCanceled(true);
        }*//*
    }*/
/*    @SubscribeEvent
    public static void AllTickEvent(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        CompoundTag data = player.getPersistentData();
        Level level = player.level;
        if(level.isClientSide)
        {
            if(ClientUtils.UseOfSnowSword)
            {
                ClientUtils.UseOfSnowSword = false;
                HitResult hitResult = player.pick(10,0,true); //第一个参数为最远距离
                double X = hitResult.getLocation().x;
                double Y = hitResult.getLocation().y;
                double Z = hitResult.getLocation().z;
                player.playSound(SoundEvents.FOX_TELEPORT);
                ModNetworking.sendToServer(new SnowSwordParticleC2SPacket(X,Y,Z,player.getX(),player.getY(),player.getZ()));
                ModNetworking.sendToServer(new MoveToC2SPacket());
            }
            if(ClientUtils.UseOfSnowSwordParticle)
            {
                ClientUtils.UseOfSnowSwordParticle = false;
                double X = ClientUtils.X;
                double Y = ClientUtils.Y;
                double Z = ClientUtils.Z;
                double X1 = ClientUtils.X1;
                double Y1 = ClientUtils.Y1;
                double Z1 = ClientUtils.Z1;
                level.addParticle(ParticleTypes.FLASH,X1,Y1,Z1,0,0,0);
                level.addParticle(ParticleTypes.FLASH,X,Y,Z,0,0,0);
                level.addParticle(ParticleTypes.FLASH,X+2,Y,Z,0,0,0);
                level.addParticle(ParticleTypes.FLASH,X-2,Y,Z,0,0,0);
                level.addParticle(ParticleTypes.FLASH,X,Y,Z+2,0,0,0);
                level.addParticle(ParticleTypes.FLASH,X,Y,Z-2,0,0,0);
                level.addParticle(ParticleTypes.FLASH,X,Y+2,Z,0,0,0);
                level.addParticle(ParticleTypes.FLASH,X,Y-2,Z,0,0,0);
                level.addParticle(ParticleTypes.FIREWORK,X,Y,Z,0,0,0);
                for(int i = 0 ; i <= 100 ; i++) {
                    double x = 2.0*Math.cos(i * Math.PI / 10);
                    double z = 2.0*Math.sin(i * Math.PI / 10);
                    double y = 3.0 * (i / 100.0);
                    level.addParticle(ParticleTypes.FIREWORK, X + x, Y + y, Z + z, 0, 0, 0);
                }
                for(int i = 0 ; i <= 100 ; i++) {
                    double x = 2.0*Math.cos(i * Math.PI / 10);
                    double z = 2.0*Math.sin(i * Math.PI / 10);
                    double y = 3.0 * (i / 100.0);
                    level.addParticle(ParticleTypes.FIREWORK, X1 + x, Y1 + y, Z1 + z, 0, 0, 0);
                }
                for(int i = 0 ; i <= 100 ; i++) {
                    double x = 3.0*Math.cos(i * Math.PI / 10);
                    double z = 3.0*Math.sin(i * Math.PI / 10);
                    level.addParticle(ParticleTypes.SNOWFLAKE, X + x, Y + 5 ,Z + z, 0, 0, 0);
                }
            }
            if(ClientUtils.UseOfLakeSword)
            {
                ClientUtils.UseOfLakeSword = false;
                player.playSound(SoundEvents.AMBIENT_UNDERWATER_EXIT);
                player.setDeltaMovement(2*player.getViewVector(5).x,2*player.getViewVector(5).y,2*player.getViewVector(5).z);
            }
        }
        if(event.side.isServer())
        {
            */
    /*int TmpNum = player.tickCount;
            if(event.phase == TickEvent.Phase.START && TmpNum % 20 == 0 ) {
                if(data.contains("MANA") && data.contains("MAXMANA"))
                {
                    data.putInt("MANA", Math.min(data.getInt("MANA") + 5 + (int) Compute.PlayerManaReply(player), data.getInt("MAXMANA")));
                    Compute.ManaStatusUpdate(player);
                }
            }
            if(event.phase == TickEvent.Phase.START && TmpNum % 20 == 0 && !player.isDeadOrDying()) player.setHealth(Math.min(player.getHealth()+Compute.HealReplyImprove(player),player.getMaxHealth()));
            if(event.phase == TickEvent.Phase.START && data.contains("volcanobow") && data.getInt("volcanobow") > 0) data.putInt("volcanobow",data.getInt("volcanobow")-1);
            if(event.phase == TickEvent.Phase.START && data.contains("plainbow") && data.getInt("plainbow") > 0) data.putInt("plainbow",data.getInt("plainbow")-1);
            if(event.phase == TickEvent.Phase.START && data.contains("Critical0") && data.getInt("Critical0") > 0) data.putInt("Critical0",data.getInt("Critical0")-1);
            if(event.phase == TickEvent.Phase.START && data.contains("Critical1") && data.getInt("Critical1") > 0) data.putInt("Critical1",data.getInt("Critical1")-1);
            if(event.phase == TickEvent.Phase.START && data.contains("Critical2") && data.getInt("Critical2") > 0) data.putInt("Critical2",data.getInt("Critical2")-1);
            if(event.phase == TickEvent.Phase.START && data.contains("Critical3") && data.getInt("Critical3") > 0) data.putInt("Critical3",data.getInt("Critical3")-1);
            if(event.phase == TickEvent.Phase.START && data.contains("lakesword") && data.getInt("lakesword") > 0) data.putInt("lakesword",data.getInt("lakesword")-1);
            if(event.phase == TickEvent.Phase.START && data.contains("arrowflyingForBug") && data.getInt("arrowflyingForBug") > 0) data.putInt("arrowflyingForBug",data.getInt("arrowflyingForBug")-1);
            if(event.phase == TickEvent.Phase.START && data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 3 && data.contains("DGreen3") && data.getInt("DGreen3") >0)
            {
                data.putInt("DGreen3",data.getInt("DGreen3")-1);
                if(data.getInt("DGreen3") == 0) {
                    Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("森林符石-生长汲取").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal("已就绪!").withStyle(ChatFormatting.WHITE)));
                }
            }
            if(event.phase == TickEvent.Phase.START && data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 2 && data.contains(StringUtils.ForestRune.ForestRune2) && data.getInt(StringUtils.ForestRune.ForestRune2) >0)
            {
                data.putInt(StringUtils.ForestRune.ForestRune2,data.getInt(StringUtils.ForestRune.ForestRune2)-1);
                if(data.getInt(StringUtils.ForestRune.ForestRune2) == 0) {
                    Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("森林符石-狂野生长").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal("已就绪!").withStyle(ChatFormatting.WHITE)));
                }
            }
            if(event.phase == TickEvent.Phase.START && data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 1 && data.contains("DGreen1") && data.getInt("DGreen1") >0)
            {
                data.putInt("DGreen1",data.getInt("DGreen1")-1);
            }
            if(event.phase == TickEvent.Phase.START && data.contains("ManaRune") && data.getInt("ManaRune") == 2 && data.contains("ManaRune2") && data.getInt("ManaRune2") > 0)
            {
                data.putInt("ManaRune2",data.getInt("ManaRune2")-1);
                if(data.getInt("ManaRune2") == 0) {
                    Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("魔源符石-苍雷之怒").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal("已就绪!").withStyle(ChatFormatting.WHITE)));
                }
            }
            if(event.phase == TickEvent.Phase.START && data.contains("BreakDefence") && data.getInt("BreakDefence") >0)
            {
                data.putInt("BreakDefence",data.getInt("BreakDefence")-1);
            }
            if(player.getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline) && TmpNum > 400)
            {
                ServerPlayer serverPlayer = (ServerPlayer) player;
                serverPlayer.connection.disconnect(Component.literal("too long time to login"));
            }
            if(player.getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline)) player.teleportTo(data.getDouble("DX"),data.getDouble("DY"),data.getDouble("DZ"));
            if(data.contains("Green") && (data.getInt("Green") == 0) && (!player.getLevel().isClientSide()) && (TmpNum % 100 == 0) && player instanceof ServerPlayer && event.phase == TickEvent.Phase.START)
            {
                player.setHealth(Math.min(player.getHealth()+(player.getMaxHealth()-player.getHealth())*0.1F,player.getMaxHealth()));
                if((player.getMaxHealth()-player.getHealth())*0.1F > 0.5F)
                {
                    Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("平原的风吹向你，为你恢复了生息。").withStyle(ChatFormatting.GREEN));
                    Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("平原符石-复苏之风").withStyle(ChatFormatting.GREEN).append(Component.literal("为你回复了")).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("%.2f",(player.getMaxHealth()-player.getHealth())*0.1F)).withStyle(ChatFormatting.GREEN)).append(Component.literal("生命值")));
                }
            }
            else
            {
                if(data.contains("Green") && (data.getInt("Green") == 1) && (!player.getLevel().isClientSide()) && player instanceof ServerPlayer && event.phase == TickEvent.Phase.START)
                {
                    player.getFoodData().setFoodLevel(8);
                }
                if(data.getInt("Green") == 3 && data.getInt("Green3") < 200 && event.phase == TickEvent.Phase.START)
                {
                    if(data.getInt("Green3") == 199)
                    {
                        Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                                Component.literal("平原符石-平原加护").withStyle(ChatFormatting.GREEN).append(Component.literal("已就绪!").withStyle(ChatFormatting.WHITE)));
                        data.putInt("Green3",data.getInt("Green3")+1);
                    }
                    else data.putInt("Green3",data.getInt("Green3")+1);
                }
            }
            if(TmpNum % 10 == 0 && event.phase == TickEvent.Phase.START)
            {
                double MaxHealth = Compute.MaxHealImprove(player);
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D+MaxHealth);
                int ManaUp = (int) Compute.PlayerManaUp(player);
                data.putInt("MAXMANA",100 + ManaUp);
                if(event.side.isServer()) Compute.ManaStatusUpdate(player);
            }*//*
*/
    /*            if(player.tickCount % 20 == 0 && event.phase == TickEvent.Phase.START)
            {
                Item head = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
                Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
                Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
                Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
                if(head instanceof forestarmorhelmet && chest instanceof forestarmorchest && leggings instanceof forestarmorleggings && boots instanceof forestarmorboots){
                    player.addEffect(new MobEffectInstance(ModEffects.FORESTARMOR.get(),400));
                    player.removeEffect(ModEffects.PLAINARMOR.get());
                    player.removeEffect(ModEffects.LAKEARMOR.get());
                    player.removeEffect(ModEffects.VOLCANOARMOR.get());
                    player.removeEffect(ModEffects.LIFEMANA.get());
                    player.removeEffect(ModEffects.OBSIMANA.get());
                }
                if(head instanceof PlainArmorHelmet && chest instanceof PlainArmorChest && leggings instanceof PlainArmorLeggings && boots instanceof PlainArmorBoots){
                    player.addEffect(new MobEffectInstance(ModEffects.PLAINARMOR.get(),400));
                    player.removeEffect(ModEffects.FORESTARMOR.get());
                    player.removeEffect(ModEffects.LAKEARMOR.get());
                    player.removeEffect(ModEffects.VOLCANOARMOR.get());
                    player.removeEffect(ModEffects.LIFEMANA.get());
                    player.removeEffect(ModEffects.OBSIMANA.get());
                }
                if(head instanceof lakearmorhelmet && chest instanceof lakearmorchest && leggings instanceof lakearmorleggings && boots instanceof lakearmorboots){
                    player.addEffect(new MobEffectInstance(ModEffects.LAKEARMOR.get(),400));
                    player.removeEffect(ModEffects.FORESTARMOR.get());
                    player.removeEffect(ModEffects.PLAINARMOR.get());
                    player.removeEffect(ModEffects.VOLCANOARMOR.get());
                    player.removeEffect(ModEffects.LIFEMANA.get());
                    player.removeEffect(ModEffects.OBSIMANA.get());
                }
                if(head instanceof volcanoarmorhelmet && chest instanceof volcanoarmorchest && leggings instanceof volcanoarmorleggings && boots instanceof volcanoarmorboots){
                    player.addEffect(new MobEffectInstance(ModEffects.VOLCANOARMOR.get(),400));
                    player.removeEffect(ModEffects.FORESTARMOR.get());
                    player.removeEffect(ModEffects.PLAINARMOR.get());
                    player.removeEffect(ModEffects.LAKEARMOR.get());
                    player.removeEffect(ModEffects.LIFEMANA.get());
                    player.removeEffect(ModEffects.OBSIMANA.get());
                }
                if(boots instanceof LifeManaarmorboots && leggings instanceof LifeManaarmorleggings
                        && chest instanceof LifeManaarmorchest && head instanceof LifeManaarmorhelmet)
                {
                    player.addEffect(new MobEffectInstance(ModEffects.LIFEMANA.get(),400));
                    player.removeEffect(ModEffects.FORESTARMOR.get());
                    player.removeEffect(ModEffects.PLAINARMOR.get());
                    player.removeEffect(ModEffects.LAKEARMOR.get());
                    player.removeEffect(ModEffects.VOLCANOARMOR.get());
                    player.removeEffect(ModEffects.OBSIMANA.get());
                }
                if(boots instanceof ObsiManaarmorboots && leggings instanceof ObsiManaarmorleggings
                        && chest instanceof ObsiManaarmorchest && head instanceof ObsiManaarmorhelmet)
                {
                    player.addEffect(new MobEffectInstance(ModEffects.OBSIMANA.get(),400));
                    player.removeEffect(ModEffects.FORESTARMOR.get());
                    player.removeEffect(ModEffects.PLAINARMOR.get());
                    player.removeEffect(ModEffects.LAKEARMOR.get());
                    player.removeEffect(ModEffects.VOLCANOARMOR.get());
                    player.removeEffect(ModEffects.LIFEMANA.get());
                }
            }*//*
        }
    }*/
/*    @SubscribeEvent
    public static void TossCheck(ItemTossEvent event)
    {
        ItemStack itemStack = event.getEntity().getItem();
        Player player = event.getPlayer();
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        if(player.getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline))
        {
            player.sendSystemMessage(Component.literal("Please Input Password First!"));
            ItemStack item = event.getEntity().getItem();
            event.getPlayer().addItem(item);
        }
        if(data.contains(InventoryCheck.owner) && !data.getString(InventoryCheck.owner).equals(player.getName().getString())) event.setCanceled(true);
    }*/
/*    @SubscribeEvent
    public static void LogOut(PlayerEvent.PlayerLoggedOutEvent event)
    {
        Player player = event.getEntity();
        player.getPersistentData().putString(StringUtils.Login.Status,StringUtils.Login.Offline);
    }*/
/*    @SubscribeEvent
    public static void Fish(ItemFishedEvent event)
    {
        Player player = event.getEntity();
        Random r = new Random();
        int RanNum = r.nextInt(100);
        if(RanNum < 5)
        {
            player.addItem(Moditems.GoldCoin.get().getDefaultInstance());
            player.sendSystemMessage(Component.literal("你通过钓鱼额外获得了:"));
            player.sendSystemMessage(Component.literal("金币 *1").withStyle(ChatFormatting.GOLD));
        }
        else
        {
            if(RanNum < 15)
            {
                player.addItem(Moditems.SilverCoin.get().getDefaultInstance());
                player.sendSystemMessage(Component.literal("你通过钓鱼额外获得了:"));
                player.sendSystemMessage(Component.literal("银币 *1").withStyle(ChatFormatting.GRAY));
            }
            else
            {
                if(RanNum <20)
                {
                    player.addItem(Moditems.gemspiece.get().getDefaultInstance());
                    player.sendSystemMessage(Component.literal("你通过钓鱼额外获得了:"));
                    player.sendSystemMessage(Component.literal("1*").append(Moditems.gemspiece.get().getDefaultInstance().getDisplayName()));
                }
            }
        }

    }*/
/*    @SubscribeEvent
    public static void Chat1(ClientChatReceivedEvent event)
    {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String showTime = format.format(time);
        event.setMessage(Component.literal("["+showTime+"] >> ").withStyle(ChatFormatting.GRAY).append(Component.literal(event.getMessage().getString()).withStyle(ChatFormatting.WHITE)));
    }*/
/*    @SubscribeEvent
    public static void BroadAndSecurity(TickEvent.LevelTickEvent event)
    {
        if(!Utils.MonsterAttributeDataProvider.isEmpty() && event.side.isServer() && Utils.AttributeDataTick != event.level.getServer().getTickCount())
        {
            Utils.AttributeDataTick = event.level.getServer().getTickCount();
            Iterator<Monster> iterator = Utils.MonsterAttributeDataProvider.iterator();
            while(iterator.hasNext())
            {
                Monster monster = iterator.next();
                CompoundTag data = monster.getPersistentData();
                data.putInt("ManaRune2",data.getInt("ManaRune2")-1);
                if(data.getInt("ManaRune2") == 0) Utils.MonsterAttributeDataProvider.remove(monster);
            }
        }
        if(event.side.isServer() && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD)))
        {
            if(!Utils.OverWorldLevelIsNight &&
                    event.level.getDayTime() % 24000 >= 12000 && event.level.getDayTime() % 24000 < 24000) {
                Utils.OverWorldLevelIsNight = true;
                Compute.Broad(event.level, Component.literal("[时间]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                        append(Component.literal("天渐暗，怪物们得到了来自月光的强化。").withStyle(ChatFormatting.WHITE)));
            }
            if(Utils.OverWorldLevelIsNight &&
                    event.level.dayTime() == 1) {
                Utils.OverWorldLevelIsNight = false;
                Compute.Broad(event.level,Component.literal("[时间]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                        append(Component.literal("维瑞阿契迎来的新的一天。").withStyle(ChatFormatting.WHITE)));
            }
            if(event.level.getServer().getTickCount() % 4000 == 0 && event.level.getServer().getTickCount() != Utils.securityCount1 && Utils.Security)
            {
                Utils.securityCount1 = event.level.getServer().getTickCount();
                Compute.Broad(event.level,Component.literal("当前股市行情:").withStyle(ChatFormatting.GOLD));
                Compute.Broad(event.level,Component.literal("——————————").withStyle(ChatFormatting.AQUA));
                Compute.Broad(event.level,Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)));
                Compute.Broad(event.level,Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)));
                Compute.Broad(event.level,Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)));
                Compute.Broad(event.level,Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)));
            }
            if(event.level.getServer().getTickCount() % 24000 == 0 && event.level.getServer().getTickCount() != Utils.securityCount && Utils.Security)
            {
                */
    /*                LogUtils.getLogger().info("log");
                Utils.securityCount = event.level.getServer().getTickCount();
                Compute.Broad(event.level,Component.literal("今日股市行情:").withStyle(ChatFormatting.GOLD));
                Compute.Broad(event.level,Component.literal("——————————").withStyle(ChatFormatting.AQUA));
                Random r = new Random();
                double sec0 = ConfigTest.Security0.get();
                double sec1 = ConfigTest.Security1.get();
                double sec2 = ConfigTest.Security2.get();
                double sec3 = ConfigTest.Security3.get();
                Utils.security0 = (float) sec0;
                Utils.security1 = (float) sec1;
                Utils.security2 = (float) sec2;
                Utils.security3 = (float) sec3;

                float r1 = r.nextDouble(0.1F);
                Utils.security0 *= (0.9525+r1);
                if(r1 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)).append("[").append(Component.literal(String.format("%.2f",(r1-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]")));
                else Compute.Broad(event.level,Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)).append("[").append(Component.literal("+"+String.format("%.2f",(r1-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]")));

                float r2 = r.nextDouble(0.1F);
                Utils.security1 *= (0.9525+r2);
                if(r2 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)).append("[").append(Component.literal(String.format("%.2f",(r2-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]")));
                else Compute.Broad(event.level,Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)).append("[").append(Component.literal("+"+String.format("%.2f",(r2-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]")));

                float r3 = r.nextDouble(0.1F);
                Utils.security2 *= (0.9525+r3);
                if(r3 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)).append("[").append(Component.literal(String.format("%.2f",(r3-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]")));
                else Compute.Broad(event.level,Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)).append("[").append(Component.literal("+"+String.format("%.2f",(r3-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]")));

                float r4 = r.nextDouble(0.1F);
                Utils.security3 *= (0.9525+r4);
                if(r4 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)).append("[").append(Component.literal(String.format("%.2f",(r4-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]")));
                else Compute.Broad(event.level,Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)).append("[").append(Component.literal("+"+String.format("%.2f",(r4-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]")));

                ConfigTest.Security0.set( Utils.security0);
                ConfigTest.Security1.set( Utils.security1);
                ConfigTest.Security2.set( Utils.security2);
                ConfigTest.Security3.set( Utils.security3);
            }
        }
        int BroadCount = event.level.getServer().getTickCount()%13200;
        Utils.tick = event.level.getServer().getTickCount();
        if(event.phase == TickEvent.Phase.START)
        {
            if(BroadCount == 0 && Utils.Count != 0)
            {
                Utils.Count = 0;
                Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                        append(Component.literal("你知道吗，每日签到的刷新时间是22小时。").withStyle(ChatFormatting.WHITE)));
            }
            else
            {
                if(BroadCount == 1200 && Utils.Count != 1200)
                {
                    Utils.Count = 1200;
                    Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                            append(Component.literal("维瑞阿契的测试阶段，所有的怪物均在天空城下方周围。").withStyle(ChatFormatting.WHITE)));
                }
                else
                {
                    if(BroadCount == 2400 && Utils.Count != 2400)
                    {
                        Utils.Count = 2400;
                        Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                append(Component.literal("据说，符石是维瑞阿契世界最为强大的物品，在世界各地均有符石祭坛。").withStyle(ChatFormatting.WHITE)));
                    }
                    else
                    {
                        if(BroadCount == 3600 && Utils.Count != 3600)
                        {
                            Utils.Count = 3600;
                            Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                    append(Component.literal("你知道吗，在移速加成达到100%后，更高的移速加成不会使角视场发生变化，但是移速加成仍然有效。").withStyle(ChatFormatting.WHITE)));
                        }
                        else
                        {
                            if(BroadCount == 4800 && Utils.Count != 4800)
                            {
                                Utils.Count = 4800;
                                Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                        append(Component.literal("当有两堆同种物品无法堆叠时，将他们放在手上后，就可以堆叠了，是不是很神奇呢？(大部分物品已修复，赶紧向very_H提交不能堆叠的物品吧！)").withStyle(ChatFormatting.WHITE)));
                            }
                            else
                            {
                                if(BroadCount == 6000 && Utils.Count != 6000)
                                {
                                    Utils.Count = 6000;
                                    Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                            append(Component.literal("你知道吗，右键就可以把武器的原版攻击速度和攻击伤害隐藏。").withStyle(ChatFormatting.WHITE)));
                                }
                                else
                                {
                                    if(BroadCount == 7200 && Utils.Count != 7200)
                                    {
                                        Utils.Count = 7200;
                                        Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                append(Component.literal("悄悄告诉你，弓在蓄力1.5s后伤害达最大值，最大伤害的箭矢会附带粒子特效。所以，多瞄准一会儿吧！").withStyle(ChatFormatting.WHITE)));
                                    }
                                    else
                                    {
                                        if(BroadCount == 8400 && Utils.Count != 8400)
                                        {
                                            Utils.Count = 8400;
                                            Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                    append(Component.literal("觉得爬山难？游泳慢？想要各种除战斗属性外的增益？集赞宝石碎片，找天空城珠宝商人兑换珠宝吧！").withStyle(ChatFormatting.WHITE)));
                                        }
                                        else
                                        {
                                            if(BroadCount == 9600 && Utils.Count != 9600)
                                            {
                                                Utils.Count = 9600;
                                                Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                        append(Component.literal("护甲穿透属性，是先进行固定穿透计算后再进行百分比穿透的计算哦。").withStyle(ChatFormatting.WHITE)));
                                            }
                                            else
                                            {
                                                if(BroadCount == 10800 && Utils.Count != 10800)
                                                {
                                                    Utils.Count = 10800;
                                                    Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                            append(Component.literal("箭矢命中生物后的0.15s，所有生命偷取以及暴击信息提示都会失效。(是为了修复箭矢的一些bug T_T 这些bug还暂时不能得到解决)(已于2023.5.26得到解决)").withStyle(ChatFormatting.WHITE)));
                                                }
                                                else
                                                {
                                                    if(BroadCount == 12000 && Utils.Count != 12000)
                                                    {
                                                        Utils.Count = 12000;
                                                        Compute.Broad(event.level,Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).
                                                                append(Component.literal("你可以在维瑞阿契放置与破坏方块！别担心，very_H会帮你还原地图的！").withStyle(ChatFormatting.WHITE)));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void Dimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        Player player = event.getEntity();
        if(player.experienceLevel < 50 && event.getTo().equals(ServerLevel.NETHER))
        {
            player.changeDimension(player.getLevel().getServer().getLevel(Level.OVERWORLD));
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("50级后再来探索吧！").withStyle(ChatFormatting.WHITE)));
        }
    }*/
/*    @SubscribeEvent
    public static void plainzombie(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 0 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                int [] indexX = {245,238,243,236,231,239,238,238};
                int [] indexY = {69,74,83,94,104,109,112,114};
                int [] indexZ = {1037,1056,1081,1108,1137,1160,1182,1194};
                Utils.SummonTick = level.getServer().getTickCount();
                for(int i=0;i<10;i++)
                {
                    Random r = new Random();
                    if(Utils.plainzombie[i] == null || !Utils.plainzombie[i].isAlive())
                    {
                        Utils.plainzombie[i] = new Zombie(EntityType.ZOMBIE,level);
                        Utils.plainzombie[i].setCustomName(Component.literal("平原僵尸").withStyle(ChatFormatting.GREEN));
                        Utils.plainzombie[i].setItemSlot(EquipmentSlot.HEAD , Moditems.Armor1.get().getDefaultInstance());
                        Utils.plainzombie[i].setItemSlot(EquipmentSlot.MAINHAND , Items.WOODEN_SWORD.getDefaultInstance());
                        Utils.plainzombie[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(25.0D);
                        Utils.plainzombie[i].setHealth(Utils.plainzombie[i].getMaxHealth());
                        int tmpnum = r.nextInt(8);
                        Utils.plainzombie[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        level.addFreshEntity(Utils.plainzombie[i]);
                    }
                    double x = Utils.plainzombie[i].getX();
                    double y = Utils.plainzombie[i].getY();
                    double z = Utils.plainzombie[i].getZ();
                    if(x > 265 || x < 214 || y > 136 || y < 62 || z > 1204 || z < 1027)
                    {
                        int tmpnum = r.nextInt(8);
                        Utils.plainzombie[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void forestskeleton(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 20 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                int [] indexX = {128,135,146,138,149,143,160,149};
                int [] indexY = {98,89,86,81,79,80,74,70};
                int [] indexZ = {1122,1109,1093,1077,1062,1044,1024,1001};
                Utils.SummonTick = level.getServer().getTickCount();
                for(int i=0;i<10;i++)
                {
                    Random r = new Random();
                    if(Utils.forestskeleton[i] == null || !Utils.forestskeleton[i].isAlive())
                    {
                        Utils.forestskeleton[i] = new Skeleton(EntityType.SKELETON,level);
                        Utils.forestskeleton[i].setCustomName(Component.literal("森林骷髅").withStyle(ChatFormatting.DARK_GREEN));
                        Utils.forestskeleton[i].setItemSlot(EquipmentSlot.HEAD , Moditems.Armor2.get().getDefaultInstance());
                        Utils.forestskeleton[i].setItemSlot(EquipmentSlot.MAINHAND , Items.BOW .getDefaultInstance());
                        Utils.forestskeleton[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(100.0D);
                        Utils.forestskeleton[i].setHealth(Utils.forestskeleton[i].getMaxHealth());
                        int tmpnum = r.nextInt(8);
                        Utils.forestskeleton[i].moveTo(indexX[tmpnum] +r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        level.addFreshEntity(Utils.forestskeleton[i]);
                    }
                    double x = Utils.forestskeleton[i].getX();
                    double y = Utils.forestskeleton[i].getY();
                    double z = Utils.forestskeleton[i].getZ();
                    if(x > 176 || x < 121 || y > 128 || y < 63 || z > 1128 || z < 977)
                    {
                        int tmpnum = r.nextInt(8);
                        Utils.forestskeleton[i].moveTo(indexX[tmpnum] +r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void forestzombie(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 60 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                int [] indexX = {99,82,65,33,20,3};
                int [] indexY = {71,73,74,69,67,74};
                int [] indexZ = {948,934,944,914,934,918};
                Utils.SummonTick = level.getServer().getTickCount();
                for(int i=0;i<8;i++)
                {
                    Random r = new Random();
                    if(Utils.forestzombie[i] == null || !Utils.forestzombie[i].isAlive())
                    {
                        Utils.forestzombie[i] = new Zombie(EntityType.ZOMBIE,level);
                        Utils.forestzombie[i].setCustomName(Component.literal("森林僵尸").withStyle(ChatFormatting.DARK_GREEN));
                        Utils.forestzombie[i].setItemSlot(EquipmentSlot.HEAD , Moditems.Armorforest2.get().getDefaultInstance());
                        Utils.forestzombie[i].setItemSlot(EquipmentSlot.MAINHAND , Items.IRON_SWORD .getDefaultInstance());
                        Utils.forestzombie[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(200.0D);
                        Utils.forestzombie[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
                        Utils.forestzombie[i].setHealth(Utils.forestzombie[i].getMaxHealth());
                        int tmpnum = r.nextInt(6);
                        Utils.forestzombie[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        level.addFreshEntity(Utils.forestzombie[i]);
                    }
                    double x = Utils.forestzombie[i].getX();
                    double y = Utils.forestzombie[i].getY();
                    double z = Utils.forestzombie[i].getZ();
                    if(x > 126 || x < -15 || y > 90 || y < 59 || z > 979 || z < 907)
                    {
                        int tmpnum = r.nextInt(6);
                        Utils.forestzombie[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void minezombie(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 80 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                int [] indexX = {63,56,73,90,107,98};
                int [] indexY = {0,1,-1,-6,-7,-11};
                int [] indexZ = {912,934,923,916,926,934};
                Utils.SummonTick = level.getServer().getTickCount();
                for(int i=0;i<6;i++)
                {
                    Random r = new Random();
                    if(Utils.minezombie[i] == null || !Utils.minezombie[i].isAlive())
                    {
                        Utils.minezombie[i] = new Zombie(EntityType.ZOMBIE,level);
                        Utils.minezombie[i].setCustomName(Component.literal("矿洞僵尸").withStyle(ChatFormatting.GRAY));
                        Utils.minezombie[i].setItemSlot(EquipmentSlot.HEAD , Moditems.armormine.get().getDefaultInstance());
                        Utils.minezombie[i].setItemSlot(EquipmentSlot.MAINHAND , Items.DIAMOND_PICKAXE .getDefaultInstance());
                        Utils.minezombie[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(300.0D);
                        Utils.minezombie[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
                        Utils.minezombie[i].setHealth(Utils.minezombie[i].getMaxHealth());
                        int tmpnum = r.nextInt(6);
                        Utils.minezombie[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        level.addFreshEntity(Utils.minezombie[i]);
                    }
                    double x = Utils.minezombie[i].getX();
                    double y = Utils.minezombie[i].getY();
                    double z = Utils.minezombie[i].getZ();
                    if(x > 126 || x < 42 || y > 11 || y < -15 || z > 957 || z < 896)
                    {
                        int tmpnum = r.nextInt(6);
                        Utils.minezombie[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void mineskeleton(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 100 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                int [] indexX = {63,56,73,90,107,98};
                int [] indexY = {0,1,-1,-6,-7,-11};
                int [] indexZ = {912,934,923,916,926,934};
                Utils.SummonTick = level.getServer().getTickCount();
                for(int i=0;i<6;i++)
                {
                    Random r = new Random();
                    if(Utils.mineskeleton[i] == null || !Utils.mineskeleton[i].isAlive())
                    {
                        Utils.mineskeleton[i] = new Skeleton(EntityType.SKELETON,level);
                        Utils.mineskeleton[i].setCustomName(Component.literal("矿洞骷髅").withStyle(ChatFormatting.GRAY));
                        Utils.mineskeleton[i].setItemSlot(EquipmentSlot.HEAD , Moditems.armormine.get().getDefaultInstance());
                        Utils.mineskeleton[i].setItemSlot(EquipmentSlot.MAINHAND , Items.DIAMOND_PICKAXE .getDefaultInstance());
                        Utils.mineskeleton[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(300.0D);
                        Utils.mineskeleton[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
                        Utils.mineskeleton[i].setHealth(Utils.mineskeleton[i].getMaxHealth());
                        int tmpnum = r.nextInt(6);
                        Utils.mineskeleton[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        level.addFreshEntity(Utils.mineskeleton[i]);
                    }
                    double x = Utils.mineskeleton[i].getX();
                    double y = Utils.mineskeleton[i].getY();
                    double z = Utils.mineskeleton[i].getZ();
                    if(x > 126 || x < 42 || y > 11 || y < -15 || z > 957 || z < 896)
                    {
                        int tmpnum = r.nextInt(6);
                        Utils.mineskeleton[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void snowstray(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 120 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick = level.getServer().getTickCount();
                int [] indexX = {-93,-95,-53,-39,-40,-55,-55,-66};
                int [] indexY = {171,187,168,161,159,196,191,194};
                int [] indexZ = {1350,1361,1368,1394,1478,1454,1423,1393};
                for(int i=0;i<8;i++)
                {
                    if(Utils.snowstray[i] == null || !Utils.snowstray[i].isAlive())
                    {
                        Utils.snowstray[i] = new Stray(EntityType.STRAY,level);
                        Utils.snowstray[i].setCustomName(Component.literal("冰川流浪者").withStyle(ChatFormatting.AQUA));
                        Utils.snowstray[i].setItemSlot(EquipmentSlot.HEAD , Moditems.armorsnow.get().getDefaultInstance());
                        ItemStack itemStack = Items.BOW.getDefaultInstance();
                        Map <Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
                        map.put(Enchantments.POWER_ARROWS,8);
                        EnchantmentHelper.setEnchantments(map,itemStack);
                        Utils.snowstray[i].setItemSlot(EquipmentSlot.MAINHAND , itemStack);
                        Utils.snowstray[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(300.0D);
                        Utils.snowstray[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.3D);
                        Utils.snowstray[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(10);
                        Utils.snowstray[i].setHealth(Utils.snowstray[i].getMaxHealth());
                        Utils.snowstray[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.snowstray[i]);
                    }
                    double x = Utils.snowstray[i].getX();
                    double y = Utils.snowstray[i].getY();
                    double z = Utils.snowstray[i].getZ();
                    if(x > 0 || x < -182 || y > 200 || y < 144 || z > 1499 || z < 1291)
                    {
                        Utils.snowstray[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void lakedrowned(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 140 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                double [] indexX = {35.5,64.5,42.5,10.5,12.6,-4.7};
                double [] indexY = {60,51,53,55,49,53};
                double [] indexZ = {943.3,971.7,980.4,965.6,993.3,979.3};
                Utils.SummonTick = level.getServer().getTickCount();
                for(int i=0;i<7;i++)
                {
                    Random r = new Random();
                    if(Utils.lakedrowned[i] == null || !Utils.lakedrowned[i].isAlive())
                    {
                        Utils.lakedrowned[i] = new Drowned(EntityType.DROWNED,level);
                        Utils.lakedrowned[i].setCustomName(Component.literal("湖泊守卫者").withStyle(ChatFormatting.BLUE));
                        Utils.lakedrowned[i].setItemSlot(EquipmentSlot.HEAD , Moditems.armordrown.get().getDefaultInstance());
                        Utils.lakedrowned[i].setItemSlot(EquipmentSlot.MAINHAND , Items.TRIDENT .getDefaultInstance());
                        Utils.lakedrowned[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(300.0D);
                        Utils.lakedrowned[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
                        Utils.lakedrowned[i].setHealth(Utils.lakedrowned[i].getMaxHealth());
                        int tmpnum = r.nextInt(6);
                        Utils.lakedrowned[i].moveTo(indexX[tmpnum],indexY[tmpnum],indexZ[tmpnum]);
                        level.addFreshEntity(Utils.lakedrowned[i]);
                    }
                    double x = Utils.lakedrowned[i].getX();
                    double y = Utils.lakedrowned[i].getY();
                    double z = Utils.lakedrowned[i].getZ();
                    if(x > 84 || x < -32 || y > 65 || y < 17 || z > 1014 || z < 926)
                    {
                        int tmpnum = r.nextInt(6);
                        Utils.lakedrowned[i].moveTo(indexX[tmpnum],indexY[tmpnum],indexZ[tmpnum]);
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void volcanoblaze(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 160 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick = level.getServer().getTickCount();
                int [] indexX = {28,50,42,29,17,5};
                int [] indexY = {-52,-46,-47,-46,-48,-46};
                int [] indexZ = {1052,1045,1073,1088,1104,1059};
                for(int i=0;i<7;i++)
                {
                    Random r = new Random();
                    if(Utils.blazes[i] == null || !Utils.blazes[i].isAlive())
                    {
                        Utils.blazes[i] = new Blaze(EntityType.BLAZE,level);
                        Utils.blazes[i].setCustomName(Component.literal("火山烈焰").withStyle(ChatFormatting.YELLOW));
                        Utils.blazes[i].setItemSlot(EquipmentSlot.HEAD , Moditems.Armorblaze.get().getDefaultInstance());
                        Utils.blazes[i].setItemSlot(EquipmentSlot.MAINHAND , Items.DIAMOND_PICKAXE .getDefaultInstance());
                        Utils.blazes[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(300.0D);
                        Utils.blazes[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
                        Utils.blazes[i].setHealth(Utils.blazes[i].getMaxHealth());
                        int tmpnum = r.nextInt(6);
                        Utils.blazes[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                        level.addFreshEntity(Utils.blazes[i]);
                    }
                    double x = Utils.blazes[i].getX();
                    double y = Utils.blazes[i].getY();
                    double z = Utils.blazes[i].getZ();
                    if(x > 69 || x < -12 || y > -18 || y < -57 || z > 1123 || z < 1007)
                    {
                        int tmpnum = r.nextInt(6);
                        Utils.blazes[i].moveTo(indexX[tmpnum]+r.nextInt(6),indexY[tmpnum],indexZ[tmpnum]+r.nextInt(6));
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void vex(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 180 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                for(int i=0;i<6;i++)
                {
                    Random r = new Random();
                    if(Utils.vex[i] == null || !Utils.vex[i].isAlive())
                    {
                        Utils.vex[i] = new Vex(EntityType.VEX,level);
                        Utils.vex[i].setCustomName(Component.literal("天空城的不速之客").withStyle(ChatFormatting.AQUA));
                        Utils.vex[i].setItemSlot(EquipmentSlot.HEAD , Moditems.armorsky.get().getDefaultInstance());
                        Utils.vex[i].setItemSlot(EquipmentSlot.MAINHAND , Items.IRON_SWORD.getDefaultInstance());
                        Utils.vex[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(100D);
                        Utils.vex[i].setHealth(Utils.vex[i].getMaxHealth());
                        Utils.vex[i].addEffect(new MobEffectInstance(MobEffects.GLOWING,88888));
                        Utils.vex[i].moveTo(23+r.nextInt(10) , 175+r.nextInt(10) , 1003+r.nextInt(10));
                        level.addFreshEntity(Utils.vex[i]);
                    }
                    double x = Utils.vex[i].getX();
                    double y = Utils.vex[i].getY();
                    double z = Utils.vex[i].getZ();
                    if(x > 49 || x < 6 || y > 200 || y < 159 || z > 1040 || z < 998)
                    {
                        Utils.vex[i].moveTo(23+r.nextInt(10) , 175+r.nextInt(10) , 1003+r.nextInt(10));
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void Evoker(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 200 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick = level.getServer().getTickCount();
                int [] indexX = {137,173,213,210,197};
                int [] indexY = {132,134,131,129,134};
                int [] indexZ = {922,944,967,1011,1058};
                for(int i=0;i<5;i++)
                {
                    if(Utils.evokers[i] == null || !Utils.evokers[i].isAlive())
                    {
                        Utils.evokers[i] = new Evoker(EntityType.EVOKER,level);
                        Utils.evokers[i].setCustomName(Component.literal("森林唤魔者").withStyle(ChatFormatting.LIGHT_PURPLE));
                        Utils.evokers[i].setItemSlot(EquipmentSlot.HEAD , Moditems.armorevoker.get().getDefaultInstance());
                        Utils.evokers[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(800.0D);
                        Utils.evokers[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.evokers[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(50);
                        Utils.evokers[i].setHealth(Utils.evokers[i].getMaxHealth());
                        Utils.evokers[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.evokers[i]);
                    }
                    double x = Utils.evokers[i].getX();
                    double y = Utils.evokers[i].getY();
                    double z = Utils.evokers[i].getZ();
                    if(x > 239 || x < 123 || y > 151 || y < 122 || z > 1099 || z < 896)
                    {
                        Utils.evokers[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void WitherSkeleton(TickEvent.LevelTickEvent event)
    {
        Level level = event.level;
        Level level1 = event.level.getServer().getLevel(Level.NETHER);
        if(level.getServer().getTickCount() % 400 == 220 && level.equals(level1)
                && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
        {
            Utils.SummonTick = level.getServer().getTickCount();
            int [] indexX = {89,88,88,107,107};
            int [] indexY = {67,67,67,67,67};
            int [] indexZ = {235,208,262,235,254};
            for(int i=0;i<5;i++)
            {
                if(Utils.witherSkeletons[i] == null || !Utils.witherSkeletons[i].isAlive())
                {
                    Utils.witherSkeletons[i] = new WitherSkeleton(EntityType.WITHER_SKELETON,level);
                    Utils.witherSkeletons[i].setCustomName(Component.literal("下界骷髅").withStyle(ChatFormatting.RED));
                    Utils.witherSkeletons[i].setItemSlot(EquipmentSlot.HEAD , Moditems.armorwitherskeleton.get().getDefaultInstance());
                    Utils.witherSkeletons[i].setItemSlot(EquipmentSlot.MAINHAND , Items.IRON_SWORD.getDefaultInstance());
                    Utils.witherSkeletons[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(1000.0D);
                    Utils.witherSkeletons[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                    Utils.witherSkeletons[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(50);
                    Utils.witherSkeletons[i].setHealth(Utils.witherSkeletons[i].getMaxHealth());
                    Utils.witherSkeletons[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                    level.addFreshEntity(Utils.witherSkeletons[i]);
                }
                double x = Utils.witherSkeletons[i].getX();
                double y = Utils.witherSkeletons[i].getY();
                double z = Utils.witherSkeletons[i].getZ();
                if(x > 132 || x < 55 || y > 80 || y < 60 || z > 270 || z < 200)
                {
                    Utils.witherSkeletons[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void EvokerMaster(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if(level.getServer().getTickCount() % 400 == 240 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount() && event.phase == TickEvent.Phase.START)
            {
                Utils.SummonTick = level.getServer().getTickCount();
                int [] indexX = {101,46,-24,-59,-110};
                int [] indexY = {136,142,136,144,141};
                int [] indexZ = {906,879,885,916,951};
                for(int i=0;i<5;i++)
                {
                    if(Utils.evokerMaster[i] == null || !Utils.evokerMaster[i].isAlive())
                    {
                        Utils.evokerMaster[i] = new Evoker(EntityType.EVOKER,level);
                        Utils.evokerMaster[i].setCustomName(Component.literal("森林唤魔大师").withStyle(ChatFormatting.LIGHT_PURPLE));
                        Utils.evokerMaster[i].setItemSlot(EquipmentSlot.HEAD , Moditems.armorevokermaster.get().getDefaultInstance());
                        Utils.evokerMaster[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(4000.0D);
                        Utils.evokerMaster[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.evokerMaster[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(75);
                        Utils.evokerMaster[i].setHealth(Utils.evokerMaster[i].getMaxHealth());
                        Utils.evokerMaster[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.evokerMaster[i]);
                    }
                    double x = Utils.evokerMaster[i].getX();
                    double y = Utils.evokerMaster[i].getY();
                    double z = Utils.evokerMaster[i].getZ();
                    if(x > 130 || x < -130 || y > 151 || y < 126 || z > 971 || z < 886)
                    {
                        Utils.evokerMaster[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void Xp(PlayerXpEvent.LevelChange event)
    {
        Player player = event.getEntity();
        int Levels = player.experienceLevel;
        if(Levels == 9)
        {
            player.sendSystemMessage(Component.literal("10级！"));
        }
        else
        {
            if(Levels == 19)
            {
                player.sendSystemMessage(Component.literal("20级！"));
            }
            else
            {
                if(Levels == 29)
                {
                    player.sendSystemMessage(Component.literal("30级！"));
                }
                else
                {
                    if(Levels == 39)
                    {
                        player.sendSystemMessage(Component.literal("40级！"));
                    }
                    else
                    {
                        if(Levels == 49)
                        {
                            player.sendSystemMessage(Component.literal("50级！"));
                        }
                        else
                        {
                            if(Levels == 59)
                            {
                                player.sendSystemMessage(Component.literal("60级！"));
                            }
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void XpL(PlayerXpEvent.XpChange event)
    {
        Player player = event.getEntity();
        if(player.experienceLevel == 60) event.setCanceled(true);
    }*/
/*    @SubscribeEvent
    public static void Bow(ArrowLooseEvent event)
    {
        Player player = event.getEntity();
        Level level = event.getLevel();
        player.playSound(SoundEvents.ARROW_SHOOT);
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if(itemStack.sameItem(Moditems.skybow.get().getDefaultInstance()))
        {
            int time = event.getCharge();
            if(time >= 15) time = 15;
            float damage = PlayerAttributes.PlayerAttackDamage(player);
            double CriticalHitRate = PlayerAttributes.PlayerCriticalHitRate(player);
            double CHitDamage = PlayerAttributes.PlayerCriticalHitDamage(player);
            double BreakDefence = PlayerAttributes.PlayerBreakDefence(player);
            float ExpUp = Compute.ExpGetImprove(player);
            double BreakDefence0 = PlayerAttributes.PlayerBreakDefence0(player);
            MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /10),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND));
            if(time == 15)
            {
                arrow.setCritArrow(true);
                player.playSound(SoundEvents.CHAIN_BREAK);
                level.addParticle(ParticleTypes.FLASH,player.getX()+2.0*player.getViewVector(1).x,player.getY()+2.0*player.getViewVector(1).y,player.getZ()+2.0*player.getViewVector(1).z,0,0,0);
            }
            arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0d,3.0d*((float) time /10),1.0d);
            level.addFreshEntity(arrow);

        }
        else
        {
            int time = event.getCharge();
            if(time >= 30) time = 30;
            float damage = PlayerAttributes.PlayerAttackDamage(player);
            double CriticalHitRate = PlayerAttributes.PlayerCriticalHitRate(player);
            double CHitDamage = PlayerAttributes.PlayerCriticalHitDamage(player);
            double BreakDefence = PlayerAttributes.PlayerBreakDefence(player);
            float ExpUp = Compute.ExpGetImprove(player);
            double BreakDefence0 = PlayerAttributes.PlayerBreakDefence0(player);
            MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /20),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND));
            if(time == 30) arrow.setCritArrow(true);
            arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0d,3.0d*((float) time /20),1.0d);
            level.addFreshEntity(arrow);
        }
        event.setCanceled(true);
        player.getPersistentData().putBoolean("arrowflying",true);
    }*/
/*    @SubscribeEvent
    public static void PreventBreak(BlockEvent.FarmlandTrampleEvent event)
    {
        event.setCanceled(true);
    }*/
/*    @SubscribeEvent
    public static void PreventRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        Player player = event.getEntity();
        BlockPos blockPos = event.getHitVec().getBlockPos();
        BlockState blockState = player.getLevel().getBlockState(blockPos);
        Item result = blockState.getBlock().asItem();
        if(!event.getEntity().getName().getString().equals("very_H"))
        {
            if(result.equals(Items.ACACIA_TRAPDOOR) ||
                    result.equals(Items.BIRCH_TRAPDOOR) ||
                    result.equals(Items.IRON_TRAPDOOR) ||
                    result.equals(Items.OAK_TRAPDOOR) ||
                    result.equals(Items.CRIMSON_TRAPDOOR) ||
                    result.equals(Items.DARK_OAK_TRAPDOOR) ||
                    result.equals(Items.JUNGLE_TRAPDOOR) ||
                    result.equals(Items.MANGROVE_TRAPDOOR) ||
                    result.equals(Items.SPRUCE_TRAPDOOR) ||
                    result.equals(Items.WARPED_TRAPDOOR) ||
                    result.equals(Items.CHEST)
            ) event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void BlockPlace(BlockEvent.EntityPlaceEvent event)
    {
        BlockPos blockPos = event.getPos();
        if(event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            if(!player.level.isClientSide)
            {
                if(Utils.blockPosBreakQueue.contains(blockPos))
                {
                    event.setCanceled(true);
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("此处是原始地图方块位置，不能放置方块。").withStyle(ChatFormatting.WHITE)));
                }
                else
                {
                    if(!player.isCreative()) Utils.blockPosQueue.add(blockPos);
                }
            }
        }
    }
    @SubscribeEvent
    public static void BlockPlaceTick(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer())
        {
            int TickCount =  event.level.getServer().getTickCount();
            if(TickCount % 1200 == 0)
            {
                Queue <BlockPos> queue = Utils.blockPosQueue;
                if(queue.size() > 100)
                {
                    for(int i = queue.size() ; i > 100 ; i--)
                    {
                        event.level.destroyBlock(queue.remove(),false);
                    }
                }
            }
            if(TickCount % 6000 == 0)
            {
                Queue <BlockPos> posQueue = Utils.blockPosBreakQueue;
                Queue <BlockState> stateQueue = Utils.blockStateQueue;
                while(!posQueue.isEmpty())
                {
                    event.level.setBlockAndUpdate(posQueue.remove(),stateQueue.remove());
                }
            }
        }
    }
    @SubscribeEvent
    public static void Dig(BlockEvent.BreakEvent event)
    {
        Player player = event.getPlayer();
        BlockPos blockPos = event.getPos();
        BlockState blockState = event.getState();
        if(!player.level.isClientSide && !player.isCreative() && !Utils.blockPosQueue.contains(blockPos))
        {
            Item BlockItem = blockState.getBlock().asItem();
            if(Utils.ItemCheck.size() == 0) Utils.ItemCheckInit();
            if(!player.isCreative() && blockPos.getX() >= -9 && blockPos.getX() <= 104 && blockPos.getY() >= 70 && blockPos.getY() <= 204 && blockPos.getZ() >= 991 && blockPos.getZ() <= 1099 )
            {
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("天空城区域受结界保护，不能破坏这里的方块T_T").withStyle(ChatFormatting.WHITE)));
                event.setCanceled(true);
            }
            else
            {
                if(Utils.ItemCheck.containsKey(BlockItem) && Utils.ItemCheck.get(BlockItem))
                {
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("此类方块被用来保护地图了，它不能被破坏T_T").withStyle(ChatFormatting.WHITE)));
                    event.setCanceled(true);
                }
                else
                {
                    Utils.blockPosBreakQueue.add(blockPos);
                    Utils.blockStateQueue.add(blockState);
                    if(event.getState().getBlock().asItem().equals(MinecartItem.byId(45)))
                    {
                        ItemStack itemStack = Items.RAW_IRON.getDefaultInstance();
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        event.getPlayer().addItem(itemStack);
                    }
                    else
                    {
                        if(event.getState().getBlock().asItem().equals(MinecartItem.byId(46)))
                        {
                            ItemStack itemStack = Items.RAW_IRON.getDefaultInstance();
                            itemStack.getOrCreateTagElement(Utils.MOD_ID);
                            event.getPlayer().addItem(itemStack);
                        }
                        else
                        {
                            if(event.getState().getBlock().asItem().equals(MinecartItem.byId(49)))
                            {
                                ItemStack itemStack = Items.RAW_GOLD.getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                event.getPlayer().addItem(itemStack);
                            }
                            else
                            {
                                if(event.getState().getBlock().asItem().equals(MinecartItem.byId(50)))
                                {
                                    ItemStack itemStack = Items.RAW_GOLD.getDefaultInstance();
                                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                    event.getPlayer().addItem(itemStack);
                                }
                            }
                        }
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void Particle(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        Level level = player.level;
        if(ClientUtils.ParticleFlag && level.isClientSide && event.side.isClient())
        {
            ClientUtils.ParticleFlag = false;
            switch (ClientUtils.ParticleRandom) {
                case 0 -> {
                    for (double x = -171.5; x <= -164.5; x++) {
                        for (double z = 1409.5; z <= 1416.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK,x,116,z,0,0,0);
                        }
                    }
                }
                case 1 -> {
                    for (double x = -162.5; x <= -155.5; x++) {
                        for (double z = 1409.5; z <= 1416.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK,x,116,z,0,0,0);
                        }
                    }
                }
                case 2 -> {
                    for (double x = -162.5; x <= -155.5; x++) {
                        for (double z = 1418.5; z <= 1425.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK,x,116,z,0,0,0);
                        }
                    }
                }
                case 3 -> {
                    for (double x = -171.5; x <= -164.5; x++) {
                        for (double z = 1418.5; z <= 1425.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK,x,116,z,0,0,0);
                        }
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void InventoryCheck(TickEvent.PlayerTickEvent event)
    {
        if(event.player.tickCount % 20 == 0 && event.side.isServer() && event.phase == TickEvent.Phase.START)
        {
            Player player = event.player;
            Inventory inventory = player.getInventory();
            for(int i = 0; i < inventory.getMaxStackSize(); i++)
            {
                CompoundTag data = inventory.getItem(i).getOrCreateTagElement(Utils.MOD_ID);
                ItemStack itemStack = inventory.getItem(i);
                if(data.contains(InventoryCheck.owner) && !data.getString(InventoryCheck.owner).equals(player.getName().getString()))
                {
                    Player ItemOwner = player.getServer().getPlayerList().getPlayerByName(data.getString(InventoryCheck.owner));
                    if(ItemOwner == null)
                    {
                        LogUtils.getLogger().info("ItemOwner is null!");
                        inventory.removeItem(itemStack);
                    }
                    else
                    {
                        ItemOwner.addItem(itemStack);
                        inventory.removeItem(itemStack);
                        Compute.Broad(player.getLevel(),Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal("已将玩家"+player.getName().getString()+"背包中不属于他的").withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()).append(Component.literal("转移到"+ItemOwner.getName().getString()+"的背包中。"))));
                    }
                }
            }
        }
    }*/
/*    @SubscribeEvent
    public static void FeiLeiShenParticle(TickEvent.PlayerTickEvent event)
    {
        if(event.player.tickCount % 20 == 0 && Utils.FeiLeiShenMap.get(event.player) != null && event.side.isClient())
        {
            Player player = event.player;
            Level level = player.level;
            Queue<Vec3> Fei = Utils.FeiLeiShenMap.get(player);
            Iterator<Vec3> iterator = Fei.iterator();
            while(iterator.hasNext())
            {
                Vec3 FeiPos = iterator.next();
                level.addParticle(ParticleTypes.WITCH,FeiPos.x,FeiPos.y,FeiPos.z,0,0,0);
            }
        }
    }*/
/*    @SubscribeEvent
    public static void MusicPlayerServerSide(TickEvent.LevelTickEvent event)
    {
        if(event.level.getServer().getTickCount() % 12000 == 0 && event.side.isServer() && event.phase.equals(TickEvent.Phase.START))
        {
            List<ServerPlayer> playerList = event.level.getServer().getPlayerList().getPlayers();
            Iterator<ServerPlayer> iterator = playerList.iterator();
            while(iterator.hasNext())
            {
                ModNetworking.sendToClient(new MusicIdolS2CPacket(true),iterator.next());
            }
        }
    }
    @SubscribeEvent
    public static void MusicPlayerClientSide(TickEvent.PlayerTickEvent event)
    {
        if(event.side.isClient())
        {
            if(ClientUtils.MusicPlayerIdol)
            {
                ClientUtils.MusicPlayerIdol = false;
                Player player = event.player;
                player.level.playLocalSound(player.getX(),player.getY(),player.getZ(),ModSounds.IDOL.get(),SoundSource.RECORDS,1f,1f,true);
            }
        }
    }*/
}



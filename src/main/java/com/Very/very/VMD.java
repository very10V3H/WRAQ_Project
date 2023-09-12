package com.Very.very;

import com.Very.very.Entity.SakuraMob;
import com.Very.very.Entity.Villagers.ModVillagers;
import com.Very.very.Files.ConfigTest;
import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.ValueAndTools.*;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Blocks.entity.ModBlockEntities;
import com.Very.very.Render.Effects.ModEffects;
import com.Very.very.Render.Effects.ModPotions;
import com.Very.very.Entity.HEntity;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.ValueAndTools.registry.ModBlocks;
import com.Very.very.ValueAndTools.registry.ModCreativeModeTab;
import com.Very.very.ValueAndTools.registry.Moditems;
import com.Very.very.Render.Gui.Blocks.BrewingScreen;
import com.Very.very.Render.Gui.Blocks.FirstBlockScreen;
import com.Very.very.Render.Gui.TestAndHelper.ModMenuTypes;
import dev.kosmx.playerAnim.api.layered.IAnimation;
import dev.kosmx.playerAnim.api.layered.ModifierLayer;
import dev.kosmx.playerAnim.minecraftApi.PlayerAnimationFactory;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
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

@Mod(Utils.MOD_ID)
@Mod.EventBusSubscriber
public class VMD{
    public VMD() {
        IEventBus modEvenBus = FMLJavaModLoadingContext.get().getModEventBus();
        Moditems.ITEMS.register(modEvenBus);
        ModBlocks.BLOCKS.register(modEvenBus);
        EntityInit.ENTITY_TYPES.register(modEvenBus);
        modEvenBus.addListener(this::enqueueIMC);
        modEvenBus.addListener(this::SetUp);
        HAttribute.ATTRIBUTES.register(modEvenBus);
        modEvenBus.addListener(this::SetUp0);
        ModEffects.register(modEvenBus);
        ModMenuTypes.register(modEvenBus);
        ModBlockEntities.Register(modEvenBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigTest.COMMON_CONFIG);
        modEvenBus.addListener(this::Attribute);
/*        ModSounds.register(modEvenBus);*/
        ModPotions.register(modEvenBus);
        ModParticles.register(modEvenBus);
        ModCreativeModeTab.register(modEvenBus);
        modEvenBus.addListener(this::AddItemToTab);
/*        ModVillagers.register(modEvenBus);*/
    }
    private void SetUp0(FMLCommonSetupEvent event){
        ModNetworking.register();
/*        ModVillagers.registerPOIs();*/
        replaceAttributeValue((RangedAttribute) Attributes.MAX_HEALTH,666666.0D);
    }
    private void SetUp(FMLClientSetupEvent event)
    {
        ModItemProperties.addCustomBowProperties();
        MenuScreens.register(ModMenuTypes.FIRST_MENU.get(), FirstBlockScreen::new);
        MenuScreens.register(ModMenuTypes.BREWING_MENU.get(), BrewingScreen::new);

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
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(4).build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CURIO.getMessageBuilder().build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HANDS.getMessageBuilder().build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BRACELET.getMessageBuilder().build());
            InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BODY.getMessageBuilder().build());
        }
    }
    private void Attribute(EntityAttributeCreationEvent event) {
        event.put(EntityInit.HETITY.get(),HEntity.HBossAttributes().build());
        event.put(EntityInit.SakuraMob.get(), SakuraMob.setAttributes());
    }
    @SubscribeEvent
    public static void onEntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
        event.add(EntityInit.HETITY.get(), HAttribute.MAXHEALTH.get());
    }
    private void AddItemToTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(ModCreativeModeTab.SWORD_TAB.getKey())) {
            event.accept(Moditems.PlainSword0.get().getDefaultInstance());
            event.accept(Moditems.PlainSword1.get().getDefaultInstance());
            event.accept(Moditems.PlainSword2.get().getDefaultInstance());
            event.accept(Moditems.PlainSword3.get().getDefaultInstance());
            event.accept(Moditems.plainbow.get().getDefaultInstance());
            event.accept(Moditems.forestsword0.get().getDefaultInstance());
            event.accept(Moditems.forestsword1.get().getDefaultInstance());
            event.accept(Moditems.forestsword2.get().getDefaultInstance());
            event.accept(Moditems.forestsword3.get().getDefaultInstance());
            event.accept(Moditems.lakesword0.get().getDefaultInstance());
            event.accept(Moditems.lakesword1.get().getDefaultInstance());
            event.accept(Moditems.lakesword2.get().getDefaultInstance());
            event.accept(Moditems.lakesword3.get().getDefaultInstance());
            event.accept(Moditems.volcanosword0.get().getDefaultInstance());
            event.accept(Moditems.volcanosword1.get().getDefaultInstance());
            event.accept(Moditems.volcanosword2.get().getDefaultInstance());
            event.accept(Moditems.volcanosword3.get().getDefaultInstance());
            event.accept(Moditems.volcanobow.get().getDefaultInstance());
            event.accept(Moditems.forestbow.get().getDefaultInstance());
            event.accept(Moditems.minesword0.get().getDefaultInstance());
            event.accept(Moditems.minesword1.get().getDefaultInstance());
            event.accept(Moditems.minesword2.get().getDefaultInstance());
            event.accept(Moditems.minesword3.get().getDefaultInstance());
            event.accept(Moditems.fieldsword0.get().getDefaultInstance());
            event.accept(Moditems.fieldsword1.get().getDefaultInstance());
            event.accept(Moditems.fieldsword2.get().getDefaultInstance());
            event.accept(Moditems.fieldsword3.get().getDefaultInstance());
            event.accept(Moditems.snowsword0.get().getDefaultInstance());
            event.accept(Moditems.snowsword1.get().getDefaultInstance());
            event.accept(Moditems.snowsword2.get().getDefaultInstance());
            event.accept(Moditems.snowsword3.get().getDefaultInstance());
            event.accept(Moditems.skybow.get().getDefaultInstance());
            event.accept(Moditems.evokersword.get().getDefaultInstance());
            event.accept(Moditems.plainsceptre0.get().getDefaultInstance());
            event.accept(Moditems.plainsceptre1.get().getDefaultInstance());
            event.accept(Moditems.plainsceptre2.get().getDefaultInstance());
            event.accept(Moditems.plainsceptre3.get().getDefaultInstance());
            event.accept(Moditems.plainsceptre4.get().getDefaultInstance());
            event.accept(Moditems.WitherBonePower.get().getDefaultInstance());
            event.accept(Moditems.PigLinPower.get().getDefaultInstance());
            event.accept(Moditems.WitherBoneMealPower.get().getDefaultInstance());
            event.accept(Moditems.NetherPower.get().getDefaultInstance());
            event.accept(Moditems.NetherBow.get().getDefaultInstance());
            event.accept(Moditems.ManaSword.get().getDefaultInstance());
            event.accept(Moditems.QuartzSword.get().getDefaultInstance());
            event.accept(Moditems.QuartzSabre.get().getDefaultInstance());
            event.accept(Moditems.EvokerBook0.get().getDefaultInstance());
            event.accept(Moditems.EvokerBook1.get().getDefaultInstance());
            event.accept(Moditems.EvokerBook2.get().getDefaultInstance());
            event.accept(Moditems.EvokerBook3.get().getDefaultInstance());
            event.accept(Moditems.SeaSword0.get().getDefaultInstance());
            event.accept(Moditems.SeaSword1.get().getDefaultInstance());
            event.accept(Moditems.SeaSword2.get().getDefaultInstance());
            event.accept(Moditems.SeaSword3.get().getDefaultInstance());
            event.accept(Moditems.BlackForestSword0.get().getDefaultInstance());
            event.accept(Moditems.BlackForestSword1.get().getDefaultInstance());
            event.accept(Moditems.BlackForestSword2.get().getDefaultInstance());
            event.accept(Moditems.BlackForestSword3.get().getDefaultInstance());
            event.accept(Moditems.MagmaPower.get().getDefaultInstance());
            event.accept(Moditems.KazeSword0.get().getDefaultInstance());
            event.accept(Moditems.KazeSword1.get().getDefaultInstance());
            event.accept(Moditems.KazeSword2.get().getDefaultInstance());
            event.accept(Moditems.KazeSword3.get().getDefaultInstance());
            event.accept(Moditems.GoldSword0.get().getDefaultInstance());
            event.accept(Moditems.KazeSword4.get().getDefaultInstance());
            event.accept(Moditems.BlackForestSword4.get().getDefaultInstance());
            event.accept(Moditems.SeaSword4.get().getDefaultInstance());
            event.accept(Moditems.ManaSword1.get().getDefaultInstance());
            event.accept(Moditems.snowsword4.get().getDefaultInstance());
            event.accept(Moditems.forestsword4.get().getDefaultInstance());
            event.accept(Moditems.VolcanoSword4.get().getDefaultInstance());
            event.accept(Moditems.ForestBossSword.get().getDefaultInstance());
            event.accept(Moditems.VolcanoBossSword.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.ARMOR_TAB.getKey())) {
            event.accept(Moditems.plainarmorhelmet.get().getDefaultInstance());
            event.accept(Moditems.plainarmorchest.get().getDefaultInstance());
            event.accept(Moditems.plainarmorleggings.get().getDefaultInstance());
            event.accept(Moditems.plainarmorboots.get().getDefaultInstance());
            event.accept(Moditems.forestarmorhelmet.get().getDefaultInstance());
            event.accept(Moditems.forestarmorchest.get().getDefaultInstance());
            event.accept(Moditems.forestarmorleggings.get().getDefaultInstance());
            event.accept(Moditems.forestarmorboots.get().getDefaultInstance());
            event.accept(Moditems.lakearmorhelmet.get().getDefaultInstance());
            event.accept(Moditems.lakearmorchest.get().getDefaultInstance());
            event.accept(Moditems.lakearmorleggings.get().getDefaultInstance());
            event.accept(Moditems.lakearmorboots.get().getDefaultInstance());
            event.accept(Moditems.volcanoarmorhelmet.get().getDefaultInstance());
            event.accept(Moditems.volcanoarmorchest.get().getDefaultInstance());
            event.accept(Moditems.volcanoarmorleggings.get().getDefaultInstance());
            event.accept(Moditems.volcanoarmorboots.get().getDefaultInstance());
            event.accept(Moditems.lifemanaarmorhelmet.get().getDefaultInstance());
            event.accept(Moditems.lifemanaarmorchest.get().getDefaultInstance());
            event.accept(Moditems.lifemanaarmorleggings.get().getDefaultInstance());
            event.accept(Moditems.lifemanaarmorboots.get().getDefaultInstance());
            event.accept(Moditems.obsimanaarmorhelmet.get().getDefaultInstance());
            event.accept(Moditems.obsimanaarmorchest.get().getDefaultInstance());
            event.accept(Moditems.obsimanaarmorleggings.get().getDefaultInstance());
            event.accept(Moditems.obsimanaarmorboots.get().getDefaultInstance());
            event.accept(Moditems.IslandArmorHelmet.get().getDefaultInstance());
            event.accept(Moditems.IslandArmorChest.get().getDefaultInstance());
            event.accept(Moditems.IslandArmorLeggings.get().getDefaultInstance());
            event.accept(Moditems.IslandArmorBoots.get().getDefaultInstance());
            event.accept(Moditems.SkyArmorHelmet.get().getDefaultInstance());
            event.accept(Moditems.SkyArmorChest.get().getDefaultInstance());
            event.accept(Moditems.SkyArmorLeggings.get().getDefaultInstance());
            event.accept(Moditems.SkyArmorBoots.get().getDefaultInstance());
            event.accept(Moditems.NetherArmorHelmet.get().getDefaultInstance());
            event.accept(Moditems.NetherArmorChest.get().getDefaultInstance());
            event.accept(Moditems.NetherArmorLeggings.get().getDefaultInstance());
            event.accept(Moditems.NetherArmorBoots.get().getDefaultInstance());
            event.accept(Moditems.KazeBoots.get().getDefaultInstance());
            event.accept(Moditems.SHelmet.get().getDefaultInstance());
            event.accept(Moditems.SChest.get().getDefaultInstance());
            event.accept(Moditems.SLeggings.get().getDefaultInstance());
            event.accept(Moditems.SBoots.get().getDefaultInstance());
            event.accept(Moditems.ISArmorHelmet.get().getDefaultInstance());
            event.accept(Moditems.ISArmorChest.get().getDefaultInstance());
            event.accept(Moditems.ISArmorLeggings.get().getDefaultInstance());
            event.accept(Moditems.ISArmorBoots.get().getDefaultInstance());
            event.accept(Moditems.ILArmorHelmet.get().getDefaultInstance());
            event.accept(Moditems.ILArmorChest.get().getDefaultInstance());
            event.accept(Moditems.ILArmorLeggings.get().getDefaultInstance());
            event.accept(Moditems.ILArmorBoots.get().getDefaultInstance());
            event.accept(Moditems.SakuraArmorHelmet.get().getDefaultInstance());
            event.accept(Moditems.WheatArmorChest.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.BREWING_TAB.getKey())) {
            event.accept(Moditems.Purifier.get().getDefaultInstance());
            event.accept(Moditems.PurifiedWater.get().getDefaultInstance());
            event.accept(Moditems.BrewingNote.get().getDefaultInstance());
            event.accept(Moditems.PlainSolidifiedSoul.get().getDefaultInstance());
            event.accept(Moditems.ForestSolidifiedSoul.get().getDefaultInstance());
            event.accept(Moditems.LakeSolidifiedSoul.get().getDefaultInstance());
            event.accept(Moditems.VolcanoSolidifiedSoul.get().getDefaultInstance());
            event.accept(Moditems.SnowSolidifiedSoul.get().getDefaultInstance());
            event.accept(Moditems.SkySolidifiedSoul.get().getDefaultInstance());
            event.accept(Moditems.EvokerSolidifiedSoul.get().getDefaultInstance());
            event.accept(Moditems.NetherSolidifiedSoul.get().getDefaultInstance());
            event.accept(Moditems.Solidifier.get().getDefaultInstance());
            event.accept(Moditems.Stabilizer.get().getDefaultInstance());
            event.accept(Moditems.Concentrater.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.CODEMANA_TAB.getKey())) {
            event.accept(Moditems.CodeSceptre.get().getDefaultInstance());
            event.accept(Moditems.BreakMana.get().getDefaultInstance());
            event.accept(Moditems.DamageMana.get().getDefaultInstance());
            event.accept(Moditems.EffectMana.get().getDefaultInstance());
            event.accept(Moditems.GatherMana.get().getDefaultInstance());
            event.accept(Moditems.KazeMana.get().getDefaultInstance());
            event.accept(Moditems.LightningMana.get().getDefaultInstance());
            event.accept(Moditems.RangeMana.get().getDefaultInstance());
            event.accept(Moditems.SnowMana.get().getDefaultInstance());
            event.accept(Moditems.LightningPower.get().getDefaultInstance());
            event.accept(Moditems.ManaModel.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.SARMOR_TAB.getKey())) {
            event.accept(Moditems.SHelmet.get().getDefaultInstance());
            event.accept(Moditems.SChest.get().getDefaultInstance());
            event.accept(Moditems.SLeggings.get().getDefaultInstance());
            event.accept(Moditems.SBoots.get().getDefaultInstance());
            event.accept(Moditems.SunOintment0.get().getDefaultInstance());
            event.accept(Moditems.SunOintment1.get().getDefaultInstance());
            event.accept(Moditems.SunOintment2.get().getDefaultInstance());
            event.accept(Moditems.LakeOintment0.get().getDefaultInstance());
            event.accept(Moditems.LakeOintment1.get().getDefaultInstance());
            event.accept(Moditems.LakeOintment2.get().getDefaultInstance());
            event.accept(Moditems.VolcanoOintment0.get().getDefaultInstance());
            event.accept(Moditems.VolcanoOintment1.get().getDefaultInstance());
            event.accept(Moditems.VolcanoOintment2.get().getDefaultInstance());
            event.accept(Moditems.SnowOintment0.get().getDefaultInstance());
            event.accept(Moditems.SnowOintment1.get().getDefaultInstance());
            event.accept(Moditems.SnowOintment2.get().getDefaultInstance());
            event.accept(Moditems.SkyOintment0.get().getDefaultInstance());
            event.accept(Moditems.SkyOintment1.get().getDefaultInstance());
            event.accept(Moditems.SkyOintment2.get().getDefaultInstance());
            event.accept(Moditems.ManaOintment0.get().getDefaultInstance());
            event.accept(Moditems.ManaOintment1.get().getDefaultInstance());
            event.accept(Moditems.ManaOintment2.get().getDefaultInstance());
            event.accept(Moditems.NetherOintment0.get().getDefaultInstance());
            event.accept(Moditems.NetherOintment1.get().getDefaultInstance());
            event.accept(Moditems.NetherOintment2.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.DROPSANDMATERIAL_TAB.getKey())) {
            event.accept(Moditems.Piece.get().getDefaultInstance());
            event.accept(Moditems.PlainSoul.get().getDefaultInstance());
            event.accept(Moditems.PlainRune.get().getDefaultInstance());
            event.accept(Moditems.ForestSoul.get().getDefaultInstance());
            event.accept(Moditems.ForestRune.get().getDefaultInstance());
            event.accept(Moditems.gemspiece.get().getDefaultInstance());
            event.accept(Moditems.WaterSoul.get().getDefaultInstance());
            event.accept(Moditems.WaterRune.get().getDefaultInstance());
            event.accept(Moditems.VolcanoSoul.get().getDefaultInstance());
            event.accept(Moditems.VolcanoRune.get().getDefaultInstance());
            event.accept(Moditems.MineSoul.get().getDefaultInstance());
            event.accept(Moditems.MineSoul1.get().getDefaultInstance());
            event.accept(Moditems.MineRune.get().getDefaultInstance());
            event.accept(Moditems.FieldSoul.get().getDefaultInstance());
            event.accept(Moditems.FieldRune.get().getDefaultInstance());
            event.accept(Moditems.SnowSoul.get().getDefaultInstance());
            event.accept(Moditems.SnowRune.get().getDefaultInstance());
            event.accept(Moditems.main1crystal.get().getDefaultInstance());
            event.accept(Moditems.SkySoul.get().getDefaultInstance());
            event.accept(Moditems.SkyRune.get().getDefaultInstance());
            event.accept(Moditems.EvokerSoul.get().getDefaultInstance());
            event.accept(Moditems.manabucket.get().getDefaultInstance());
            event.accept(Moditems.EvokerRune.get().getDefaultInstance());
            event.accept(Moditems.manabalance_empty.get().getDefaultInstance());
            event.accept(Moditems.manabalance_filled.get().getDefaultInstance());
            event.accept(Moditems.plainmana.get().getDefaultInstance());
            event.accept(Moditems.forestmana.get().getDefaultInstance());
            event.accept(Moditems.lakemana.get().getDefaultInstance());
            event.accept(Moditems.volcanomana.get().getDefaultInstance());
            event.accept(Moditems.ruby.get().getDefaultInstance());
            event.accept(Moditems.NetherSoul.get().getDefaultInstance());
            event.accept(Moditems.NetherRune.get().getDefaultInstance());
            event.accept(Moditems.NetherSwordModel.get().getDefaultInstance());
            event.accept(Moditems.witherBone.get().getDefaultInstance());
            event.accept(Moditems.QuartzSoul.get().getDefaultInstance());
            event.accept(Moditems.QuartzRune.get().getDefaultInstance());
            event.accept(Moditems.PowerModel.get().getDefaultInstance());
            event.accept(Moditems.NetherSabreModel.get().getDefaultInstance());
            event.accept(Moditems.SeaSoul.get().getDefaultInstance());
            event.accept(Moditems.SeaRune.get().getDefaultInstance());
            event.accept(Moditems.LightningSoul.get().getDefaultInstance());
            event.accept(Moditems.LightningRune.get().getDefaultInstance());
            event.accept(Moditems.BlackForestSoul.get().getDefaultInstance());
            event.accept(Moditems.BlackForestRune.get().getDefaultInstance());
            event.accept(Moditems.SunPower.get().getDefaultInstance());
            event.accept(Moditems.NetherMagmaPower.get().getDefaultInstance());
            event.accept(Moditems.KazeSoul.get().getDefaultInstance());
            event.accept(Moditems.KazeRune.get().getDefaultInstance());
            event.accept(Moditems.LakeCore.get().getDefaultInstance());
            event.accept(Moditems.SpiderSoul.get().getDefaultInstance());
            event.accept(Moditems.SpiderRune.get().getDefaultInstance());
            event.accept(Moditems.VolcanoCore.get().getDefaultInstance());
            event.accept(Moditems.SilverFishSoul.get().getDefaultInstance());
            event.accept(Moditems.BlackForestRecall.get().getDefaultInstance());
            event.accept(Moditems.EvokerRecallBook.get().getDefaultInstance());
            event.accept(Moditems.ForestRecallBook.get().getDefaultInstance());
            event.accept(Moditems.KazeRecallBook.get().getDefaultInstance());
            event.accept(Moditems.LakeRecallBook.get().getDefaultInstance());
            event.accept(Moditems.LightningRecallBook.get().getDefaultInstance());
            event.accept(Moditems.NetherRecallBook1.get().getDefaultInstance());
            event.accept(Moditems.NetherRecallBook2.get().getDefaultInstance());
            event.accept(Moditems.PlainRecallBook.get().getDefaultInstance());
            event.accept(Moditems.SeaRecallBook.get().getDefaultInstance());
            event.accept(Moditems.SkyRecallBook.get().getDefaultInstance());
            event.accept(Moditems.SnowRecallBook.get().getDefaultInstance());
            event.accept(Moditems.SpiderRecallBook.get().getDefaultInstance());
            event.accept(Moditems.VolcanoRecallBook.get().getDefaultInstance());
            event.accept(Moditems.RecallPiece.get().getDefaultInstance());
            event.accept(Moditems.KazeRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.IntensifiedKazeSoul.get().getDefaultInstance());
            event.accept(Moditems.SpiderRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.IntensifiedSpiderSoul.get().getDefaultInstance());
            event.accept(Moditems.BlackForestRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.IntensifiedBlackForestSoul.get().getDefaultInstance());
            event.accept(Moditems.SeaRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.IntensifiedSeaSoul.get().getDefaultInstance());
            event.accept(Moditems.NetherRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.IntensifiedNetherRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.SnowRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.IntensifiedSnowRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.ForestRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.IntensifiedForestRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.VolcanoRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.IntensifiedVolcanoRecallSoul.get().getDefaultInstance());
            event.accept(Moditems.ForestBossCore.get().getDefaultInstance());
            event.accept(Moditems.VolcanoBossCore.get().getDefaultInstance());
            event.accept(Moditems.ForestBossCentral.get().getDefaultInstance());
            event.accept(Moditems.VolcanoBossCentral.get().getDefaultInstance());
            event.accept(Moditems.ForestBossPocket.get().getDefaultInstance());
            event.accept(Moditems.VolcanoBossPocket.get().getDefaultInstance());
            event.accept(Moditems.SakuraPetal.get().getDefaultInstance());
            event.accept(Moditems.Wheat.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.FORGING_TAB.getKey())) {
            event.accept(Moditems.randomsword.get().getDefaultInstance());
            event.accept(Moditems.SpeIron.get().getDefaultInstance());
            event.accept(Moditems.openslot.get().getDefaultInstance());
            event.accept(Moditems.skygem.get().getDefaultInstance());
            event.accept(Moditems.ForgingStone0.get().getDefaultInstance());
            event.accept(Moditems.ForgingStone1.get().getDefaultInstance());
            event.accept(Moditems.ForgingStone2.get().getDefaultInstance());
            event.accept(Moditems.EvokerGem.get().getDefaultInstance());
            event.accept(Moditems.PlainGem.get().getDefaultInstance());
            event.accept(Moditems.ForestGem.get().getDefaultInstance());
            event.accept(Moditems.LakeGem.get().getDefaultInstance());
            event.accept(Moditems.VolcanoGem.get().getDefaultInstance());
            event.accept(Moditems.SnowGem.get().getDefaultInstance());
            event.accept(Moditems.LAHForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.LACForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.LALForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.LABForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.SkyHForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.SkyCForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.SkyLForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.SkyBForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.SeaSwordForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.BlackForestForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.NetherHForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.NetherCForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.NetherLForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.NetherBForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.KazeSwordFG.get().getDefaultInstance());
            event.accept(Moditems.KazeBootsFG.get().getDefaultInstance());
            event.accept(Moditems.SakuraSwordForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.SakuraReForge.get().getDefaultInstance());
            event.accept(Moditems.MineLeggingsForgingDrawing.get().getDefaultInstance());
            event.accept(Moditems.WheatReForge.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.MONEYANDMISSION_TAB.getKey())) {
            event.accept(Moditems.GoldCoin.get().getDefaultInstance());
            event.accept(Moditems.SilverCoin.get().getDefaultInstance());
            event.accept(Moditems.SignInReward.get().getDefaultInstance());
            event.accept(Moditems.SmartPhone.get().getDefaultInstance());
            event.accept(Moditems.DailyMission.get().getDefaultInstance());
            event.accept(Moditems.AttackUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.BreakDefenceUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.CritRateUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.CritDamageUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.ManaDamageUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.ManaBreakDefenceUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.ManaReplyUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.CoolDownDecreaseUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.HealStealUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.DefenceUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.ManaDefenceUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.SpeedUp_PotionBag.get().getDefaultInstance());
            event.accept(Moditems.GoldCoinBag.get().getDefaultInstance());
            event.accept(Moditems.BackPackTickets.get().getDefaultInstance());
            event.accept(Moditems.SakuraPocket.get().getDefaultInstance());
            event.accept(Moditems.WheatPocket.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.RUNESANDCURIOS_TAB.getKey())) {
            event.accept(Moditems.GreenRunes_0.get().getDefaultInstance());
            event.accept(Moditems.GreenRunes_1.get().getDefaultInstance());
            event.accept(Moditems.GreenRunes_2.get().getDefaultInstance());
            event.accept(Moditems.GreenRunes_3.get().getDefaultInstance());
            event.accept(Moditems.plaingems0.get().getDefaultInstance());
            event.accept(Moditems.forestrune0.get().getDefaultInstance());
            event.accept(Moditems.forestrune1.get().getDefaultInstance());
            event.accept(Moditems.forestrune2.get().getDefaultInstance());
            event.accept(Moditems.forestrune3.get().getDefaultInstance());
            event.accept(Moditems.plaingems.get().getDefaultInstance());
            event.accept(Moditems.forestgems.get().getDefaultInstance());
            event.accept(Moditems.lakegems.get().getDefaultInstance());
            event.accept(Moditems.volcanogems.get().getDefaultInstance());
            event.accept(Moditems.Breathe.get().getDefaultInstance());
            event.accept(Moditems.FireResistent.get().getDefaultInstance());
            event.accept(Moditems.Climb.get().getDefaultInstance());
            event.accept(Moditems.main1Cord1.get().getDefaultInstance());
            event.accept(Moditems.main1Cord2.get().getDefaultInstance());
            event.accept(Moditems.main1Cord3.get().getDefaultInstance());
            event.accept(Moditems.main1Cord4.get().getDefaultInstance());
            event.accept(Moditems.main1Cord12.get().getDefaultInstance());
            event.accept(Moditems.main1Cord34.get().getDefaultInstance());
            event.accept(Moditems.main1cord5.get().getDefaultInstance());
            event.accept(Moditems.NewCurios.get().getDefaultInstance());
            event.accept(Moditems.manaRune0.get().getDefaultInstance());
            event.accept(Moditems.manaRune1.get().getDefaultInstance());
            event.accept(Moditems.manaRune2.get().getDefaultInstance());
            event.accept(Moditems.manaRune3.get().getDefaultInstance());
            event.accept(Moditems.PigLinSoul.get().getDefaultInstance());
            event.accept(Moditems.netherbonemeal.get().getDefaultInstance());
            event.accept(Moditems.NetherQuartz.get().getDefaultInstance());
            event.accept(Moditems.volcanoRune0.get().getDefaultInstance());
            event.accept(Moditems.volcanoRune1.get().getDefaultInstance());
            event.accept(Moditems.volcanoRune2.get().getDefaultInstance());
            event.accept(Moditems.volcanoRune3.get().getDefaultInstance());
            event.accept(Moditems.LevelReward5.get().getDefaultInstance());
            event.accept(Moditems.LevelReward10.get().getDefaultInstance());
            event.accept(Moditems.LevelReward15.get().getDefaultInstance());
            event.accept(Moditems.LevelReward20.get().getDefaultInstance());
            event.accept(Moditems.LevelReward25.get().getDefaultInstance());
            event.accept(Moditems.LevelReward30.get().getDefaultInstance());
            event.accept(Moditems.LevelReward35.get().getDefaultInstance());
            event.accept(Moditems.LevelReward40.get().getDefaultInstance());
            event.accept(Moditems.LevelReward45.get().getDefaultInstance());
            event.accept(Moditems.LevelReward50.get().getDefaultInstance());
            event.accept(Moditems.LevelReward55.get().getDefaultInstance());
            event.accept(Moditems.LevelReward60.get().getDefaultInstance());
            event.accept(Moditems.Main1HandGem.get().getDefaultInstance());
            event.accept(Moditems.Main1BeltGem.get().getDefaultInstance());
            event.accept(Moditems.Main1necklaceGem.get().getDefaultInstance());
            event.accept(Moditems.Main1BraceletGem.get().getDefaultInstance());
            event.accept(Moditems.GemBag.get().getDefaultInstance());
            event.accept(Moditems.RandomGemPiece.get().getDefaultInstance());
            event.accept(Moditems.NetherRune0.get().getDefaultInstance());
            event.accept(Moditems.NetherRune1.get().getDefaultInstance());
            event.accept(Moditems.NetherRune2.get().getDefaultInstance());
            event.accept(Moditems.NetherRune3.get().getDefaultInstance());
            event.accept(Moditems.SnowRune0.get().getDefaultInstance());
            event.accept(Moditems.SnowRune1.get().getDefaultInstance());
            event.accept(Moditems.SnowRune2.get().getDefaultInstance());
            event.accept(Moditems.SnowRune3.get().getDefaultInstance());
            event.accept(Moditems.NetherGem.get().getDefaultInstance());
            event.accept(Moditems.NetherGemPiece.get().getDefaultInstance());
            event.accept(Moditems.NetherGemPieceBag1.get().getDefaultInstance());
            event.accept(Moditems.NetherGemPieceBag2.get().getDefaultInstance());
            event.accept(Moditems.NetherGemPieceBag3.get().getDefaultInstance());
            event.accept(Moditems.NetherGemPieceBag4.get().getDefaultInstance());
            event.accept(Moditems.ForestBossGem.get().getDefaultInstance());
            event.accept(Moditems.VolcanoBossGem.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.MISC_TAB.getKey())) {
            event.accept(Moditems.Main0.get().getDefaultInstance());
            event.accept(Moditems.Main0_1.get().getDefaultInstance());
            event.accept(Moditems.Main0_2.get().getDefaultInstance());
            event.accept(Moditems.Main0_3.get().getDefaultInstance());
            event.accept(Moditems.Main0_4.get().getDefaultInstance());
            event.accept(Moditems.Main0_5.get().getDefaultInstance());
            event.accept(Moditems.Main1_0.get().getDefaultInstance());
            event.accept(Moditems.Main1_1.get().getDefaultInstance());
            event.accept(Moditems.Main1_2.get().getDefaultInstance());
            event.accept(Moditems.Main1_3.get().getDefaultInstance());
            event.accept(Moditems.Main1_4.get().getDefaultInstance());
            event.accept(Moditems.Main1_5.get().getDefaultInstance());
            event.accept(Moditems.Note_0.get().getDefaultInstance());
            event.accept(Moditems.ExploreNote.get().getDefaultInstance());
            event.accept(Moditems.ForNew.get().getDefaultInstance());
            event.accept(Moditems.backspawn.get().getDefaultInstance());
            event.accept(Moditems.main1reward.get().getDefaultInstance());
            event.accept(Moditems.tickettosky.get().getDefaultInstance());
            event.accept(Moditems.quest.get().getDefaultInstance());
            event.accept(Moditems.spawn1.get().getDefaultInstance());
            event.accept(Moditems.profession_bow.get().getDefaultInstance());
            event.accept(Moditems.profession_sword.get().getDefaultInstance());
            event.accept(Moditems.profession_barker.get().getDefaultInstance());
            event.accept(Moditems.Note_1.get().getDefaultInstance());
            event.accept(Moditems.Note_2.get().getDefaultInstance());
            event.accept(Moditems.Note_3.get().getDefaultInstance());
            event.accept(Moditems.drug0.get().getDefaultInstance());
            event.accept(Moditems.LightningChange.get().getDefaultInstance());
            event.accept(Moditems.ID_Card.get().getDefaultInstance());
        }
        if (event.getTabKey().equals(ModCreativeModeTab.DEVELOPMENT_TAB.getKey())) {
            event.accept(Moditems.SignInReset.get().getDefaultInstance());
            event.accept(Moditems.SignInGet.get().getDefaultInstance());
            event.accept(Moditems.GetTime.get().getDefaultInstance());
            event.accept(Moditems.ItemIDCheck.get().getDefaultInstance());
            event.accept(Moditems.attributecheck.get().getDefaultInstance());
            event.accept(Moditems.hovertest.get().getDefaultInstance());
            event.accept(Moditems.Mybow.get().getDefaultInstance());
            event.accept(Moditems.ArrowItem.get().getDefaultInstance());
            event.accept(Moditems.Extraction.get().getDefaultInstance());
            event.accept(Moditems.Security.get().getDefaultInstance());
            event.accept(Moditems.resetSecurity.get().getDefaultInstance());
            event.accept(Moditems.bowsnow.get().getDefaultInstance());
            event.accept(Moditems.tick.get().getDefaultInstance());
            event.accept(Moditems.EntityCopy.get().getDefaultInstance());
            event.accept(Moditems.BlockReset.get().getDefaultInstance());
            event.accept(Moditems.Shoot.get().getDefaultInstance());
            event.accept(Moditems.FeiLeiShen.get().getDefaultInstance());
            event.accept(Moditems.IndexCheck.get().getDefaultInstance());
            event.accept(Moditems.quartzcheck.get().getDefaultInstance());
            event.accept(Moditems.GuiOpen.get().getDefaultInstance());
            event.accept(Moditems.BarrierSet.get().getDefaultInstance());
            event.accept(Moditems.WaterSet.get().getDefaultInstance());
            event.accept(Moditems.ANIMATED_ITEM.get().getDefaultInstance());
        }
    }
/*    @SubscribeEvent
    public static void SpeedSet(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        float speedup = Compute.PlayerSpeedImprove(player);
        if(!player.getLevel().isClientSide) player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1D+0.1*speedup);
        CompoundTag data = player.getPersistentData();
        if(!player.getLevel().isClientSide)
        {
            if(event.player.tickCount % 100 == 0 && data.contains("Green") && data.getInt("Green") == 2 && event.phase == TickEvent.Phase.START)
            {
                Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                        Component.literal("平原的风为你指引道路").withStyle(ChatFormatting.GREEN));
                Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                        Component.literal("平原符石-临危制变为你提供了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("%.2f",100*(((player.getMaxHealth()-player.getHealth())*1.0 / player.getMaxHealth()) / 2.0))).withStyle(ChatFormatting.GREEN)).append(Component.literal("%").withStyle(ChatFormatting.RESET)).append(Component.literal("·移动速度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.RESET)));
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
            if(!data.contains("Owner"))
            {
                data.putString("Owner",player.getName().getString());
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
            if(data.contains("attackdamage")) event.getToolTip().add(Component.literal("·基础攻击 ").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("attackdamage"))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            if(data.contains("breakdefence")) event.getToolTip().add(Component.literal("·护甲穿透+").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("breakdefence")*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("criticalrate")) event.getToolTip().add(Component.literal("·暴击几率+").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("criticalrate")*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("criticaldamage")) event.getToolTip().add(Component.literal("·暴击伤害+").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("criticaldamage")*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("healsteal")) event.getToolTip().add(Component.literal("·生命偷取+").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("healsteal")*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            if(data.contains("speedup")) event.getToolTip().add(Component.literal("·移动速度+").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,event.getItemStack().getTagElement(Utils.MOD_ID).getFloat("speedup")*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
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
            if(data.contains("Gems1") && data.getString("Gems1").equals("skyGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈天空宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems2") && data.getString("Gems2").equals("skyGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈天空宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            if(data.contains("Gems3") && data.getString("Gems3").equals("skyGem")) event.getToolTip().add(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("◈天空宝石◈").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
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
                if(Compute.ItemBaseDamageGems(data) > 0) event.getToolTip().add(Component.literal("·基础攻击 + ").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",Compute.ItemBaseDamageGems(data))).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                if(Compute.ItemSpeedUpGems(data) > 0) event.getToolTip().add(Component.literal("·移动速度 + ").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.1f" ,Compute.ItemSpeedUpGems(data)*100)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("%"))));
            }
            if(data.contains("Forging"))
            {
                event.getToolTip().add(Component.literal("—————强化属性信息—————").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));
                event.getToolTip().add(Component.literal("强化属性:").withStyle(ChatFormatting.WHITE).withStyle(ChatFormatting.WHITE));
                if(Utils.BaseDamage.containsKey(equip.getItem())) {
                    float DamageUp = Utils.BaseDamage.getValue(equip.getItem()).getValue() * (data.getInt("Forging") / 24.0F);
                    event.getToolTip().add(Component.literal("·基础攻击 + ").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",DamageUp)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
                if(Utils.ManaDamage.Contains(equip.getItem())){
                    float DamageUp = Utils.ManaDamage.getValue(equip.getItem()).getValue() * (data.getInt("Forging") / 24.0F);
                    event.getToolTip().add(Component.literal("·魔法攻击 + ").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",DamageUp)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
                if(Utils.Defence.Contains(equip.getItem())) {
                    float DefenceUp = Utils.Defence.getValue(equip.getItem()).getValue() * (data.getInt("Forging") / 24.0F);
                    event.getToolTip().add(Component.literal("·基础护甲 + ").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD).append(Component.literal(String.format("%.2f",DefenceUp)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
            }
            if(data.contains("Owner"))
            {
                event.getToolTip().add(Component.literal("Owner:"+data.getString("Owner")).withStyle(ChatFormatting.AQUA));
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
            Compute.Broad(event.getEntity().level, Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal(event.getEntity().getName().getString()+"在探索过程中身负重伤，经过救治恢复了活力。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            event.getEntity().getPersistentData().merge(event.getOriginal().getPersistentData());
        }
    }*/
/*    @SubscribeEvent
    public static void Login0(PlayerEvent.PlayerLoggedInEvent event) throws ParseException {
        Player player = event.getEntity();
        player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("欢迎来到 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA))));
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
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("击杀一只怪物来获取签到奖励！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
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
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("签到奖励将在 "+year+"年"+month+"月"+day+"日"+hour+"时"+minutes+"分"+seconds+"秒 可用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

            }
        }
        else
        {
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("击杀一只怪物来获取签到奖励！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            data.putBoolean("SignAward",true);
        }
        if(!player.getTags().contains("player")) player.addTag("player");
        if(data.contains(StringUtils.Login.Password))
        {
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("使用/vmd login (密码)来登录").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        }
        else
        {
            player.addItem(Moditems.ForNew.get().getDefaultInstance());
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("使用/vmd register (密码)来注册").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
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
                            Component.literal("森林符石-生长汲取").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal("已就绪!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
            }
            if(event.phase == TickEvent.Phase.START && data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 2 && data.contains(StringUtils.ForestRune.ForestRune2) && data.getInt(StringUtils.ForestRune.ForestRune2) >0)
            {
                data.putInt(StringUtils.ForestRune.ForestRune2,data.getInt(StringUtils.ForestRune.ForestRune2)-1);
                if(data.getInt(StringUtils.ForestRune.ForestRune2) == 0) {
                    Compute.FormatMSGSend(player,Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("森林符石-狂野生长").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal("已就绪!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
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
                            Component.literal("魔源符石-苍雷之怒").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal("已就绪!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
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
                            Component.literal("平原符石-复苏之风").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN).append(Component.literal("为你回复了")).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("%.2f",(player.getMaxHealth()-player.getHealth())*0.1F)).withStyle(ChatFormatting.GREEN)).append(Component.literal("生命值").withStyle(ChatFormatting.RESET)));
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
                                Component.literal("平原符石-平原加护").withStyle(ChatFormatting.GREEN).append(Component.literal("已就绪!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        data.putInt("Green3",data.getInt("Green3")+1);
                    }
                    else data.putInt("Green3",data.getInt("Green3")+1);
                }
            }
            if(TmpNum % 10 == 0 && event.phase == TickEvent.Phase.START)
            {
                float HealUp = Compute.MaxHealImprove(player);
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D+HealUp);
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
        if(data.contains("Owner") && !data.getString("Owner").equals(player.getName().getString())) event.setCanceled(true);
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
        event.setMessage(Component.literal("["+showTime+"] >> ").withStyle(ChatFormatting.GRAY).append(Component.literal(event.getMessage().getString()).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
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

                float r1 = r.nextFloat(0.1F);
                Utils.security0 *= (0.9525+r1);
                if(r1 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)).append("[").append(Component.literal(String.format("%.2f",(r1-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));
                else Compute.Broad(event.level,Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)).append("[").append(Component.literal("+"+String.format("%.2f",(r1-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));

                float r2 = r.nextFloat(0.1F);
                Utils.security1 *= (0.9525+r2);
                if(r2 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)).append("[").append(Component.literal(String.format("%.2f",(r2-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));
                else Compute.Broad(event.level,Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)).append("[").append(Component.literal("+"+String.format("%.2f",(r2-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));

                float r3 = r.nextFloat(0.1F);
                Utils.security2 *= (0.9525+r3);
                if(r3 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)).append("[").append(Component.literal(String.format("%.2f",(r3-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));
                else Compute.Broad(event.level,Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)).append("[").append(Component.literal("+"+String.format("%.2f",(r3-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));

                float r4 = r.nextFloat(0.1F);
                Utils.security3 *= (0.9525+r4);
                if(r4 <= 0.0475) Compute.Broad(event.level,Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)).append("[").append(Component.literal(String.format("%.2f",(r4-0.0475)*100)).withStyle(ChatFormatting.GREEN)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));
                else Compute.Broad(event.level,Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)).append("[").append(Component.literal("+"+String.format("%.2f",(r4-0.0475)*100)).withStyle(ChatFormatting.RED)).append(Component.literal("%]").withStyle(ChatFormatting.RESET)));

                ConfigTest.Security0.set((double) Utils.security0);
                ConfigTest.Security1.set((double) Utils.security1);
                ConfigTest.Security2.set((double) Utils.security2);
                ConfigTest.Security3.set((double) Utils.security3);
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
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("50级后再来探索吧！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
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
            float damage = Compute.PlayerAttackDamage(player);
            float CriticalHitRate = Compute.PlayerCriticalHitRate(player);
            float CHitDamage = Compute.PlayerCriticalHitDamage(player);
            float BreakDefence = Compute.PlayerBreakDefence(player);
            float ExpUp = Compute.ExpGetImprove(player);
            float BreakDefence0 = Compute.PlayerBreakDefence0(player);
            MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /10),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND));
            if(time == 15)
            {
                arrow.setCritArrow(true);
                player.playSound(SoundEvents.CHAIN_BREAK);
                level.addParticle(ParticleTypes.FLASH,player.getX()+2.0*player.getViewVector(1).x,player.getY()+2.0*player.getViewVector(1).y,player.getZ()+2.0*player.getViewVector(1).z,0,0,0);
            }
            arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,3.0F*((float) time /10),1.0F);
            level.addFreshEntity(arrow);

        }
        else
        {
            int time = event.getCharge();
            if(time >= 30) time = 30;
            float damage = Compute.PlayerAttackDamage(player);
            float CriticalHitRate = Compute.PlayerCriticalHitRate(player);
            float CHitDamage = Compute.PlayerCriticalHitDamage(player);
            float BreakDefence = Compute.PlayerBreakDefence(player);
            float ExpUp = Compute.ExpGetImprove(player);
            float BreakDefence0 = Compute.PlayerBreakDefence0(player);
            MyArrow arrow = new MyArrow(EntityType.ARROW,player,level,player,damage*((float) time /20),CriticalHitRate,CHitDamage,BreakDefence,ExpUp,BreakDefence0,player.getItemInHand(InteractionHand.MAIN_HAND));
            if(time == 30) arrow.setCritArrow(true);
            arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,3.0F*((float) time /20),1.0F);
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
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("此处是原始地图方块位置，不能放置方块。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
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
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("天空城区域受结界保护，不能破坏这里的方块T_T").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                event.setCanceled(true);
            }
            else
            {
                if(Utils.ItemCheck.containsKey(BlockItem) && Utils.ItemCheck.get(BlockItem))
                {
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("此类方块被用来保护地图了，它不能被破坏T_T").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
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
                if(data.contains("Owner") && !data.getString("Owner").equals(player.getName().getString()))
                {
                    Player ItemOwner = player.getServer().getPlayerList().getPlayerByName(data.getString("Owner"));
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



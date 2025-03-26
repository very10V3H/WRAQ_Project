package fun.wraq.common.registry;

import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.SkillImage;
import fun.wraq.entities.entities.Civil.CivilRender;
import fun.wraq.entities.entities.SakuraMob.SakuraMobRender;
import fun.wraq.entities.entities.SoraSword.SoraRedSwordAirRender;
import fun.wraq.entities.entities.SoraSword.SoraSwordAirRender;
import fun.wraq.entities.render.*;
import fun.wraq.process.func.guide.GuideHud;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustmentHud;
import fun.wraq.process.system.skill.skillv2.SkillV2Hud;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHud;
import fun.wraq.render.hud.main.*;
import fun.wraq.render.hud.ShieldHud;
import fun.wraq.render.hud.ManaHud;
import fun.wraq.render.particles.*;
import fun.wraq.render.toolTip.NewTooltip;
import fun.wraq.render.toolTip.TraditionalTooltip;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEventSubscriber {
    @SubscribeEvent
    public static void OnClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.SakuraMob.get(), SakuraMobRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.Scarecrow.get(), ScarecrowRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.NEW_ARROW_PLAIN.get(), NewArrowPlainRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.NEW_ARROW.get(), NewArrowRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.NEW_ARROW_MAGMA.get(), NewArrowMagmaRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.NEW_ARROW_WORLD.get(), NewArrowWorldRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.NEW_ARROW_NETHER.get(), NewArrowNetherRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.FIRE_WORK.get(), FireworkRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.NEW_ARROW_SEA.get(), NewArrowSeaRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.NEW_ARROW_SAKURA.get(), NewArrowSakuraRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.NEW_ARROW_SNOW.get(), NewArrowSnowRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.METEORITE.get(), MeteoriteRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.SWORD_AIR.get(), SwordAirRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.SORA_SWORD_AIR.get(), SoraSwordAirRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.SORA_RED_SWORD_AIR.get(), SoraRedSwordAirRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.BLAZE_SWORD.get(), BlazeSwordRender::new);
        EntityRenderers.register(fun.wraq.common.registry.ModEntityType.CIVIL.get(), CivilRender::new);


        ClientUtils.SwordSkillsResource.init();
        ClientUtils.BowSkillsResource.init();
        ClientUtils.ManaSkillsResource.init();
        ClientUtils.Demon_Image[1] = new SkillImage(0, 0, 0);
        ClientUtils.Demon_Image[2] = new SkillImage(0, 0, 0);
    }

    @SubscribeEvent
    public static void onRegisterRender(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(fun.wraq.common.registry.ModEntityType.HETITY.get(), MainBossRender::new);
        event.registerEntityRenderer(ModEntityType.Boss2.get(), Boss2Render::new);
    }
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.FIRST_PARTICLE.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BREAKDefence_MANA.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.DAMAGE_MANA.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.EFFECT_MANA.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.GATHER_MANA.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.KAZE_MANA.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LIGHTNING_MANA.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.RANGE_MANA.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SNOW_MANA.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.END_PARTICLE.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BLACKFOREST.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BLACKFOREST_RECALL.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.FOREST.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.KAZE.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LAKE.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LIGHTNINGISLAND.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_LIGHTNINGISLAND.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.EVOKER.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.NETHER.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.PLAIN.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SEA.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SKY.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SNOW.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SPIDER.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.VOLCANO.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_VOLCANO.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.ENTROPY.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_ENTROPY.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BLADE0.get(), BigSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BLADE1.get(), BigSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.TEST_CIRCLE.get(), BigSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BREAK_Defence.get(), OneTickBigSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.MANA_BREAK_Defence.get(), OneTickBigSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SLOW_DOWN.get(), OneTickBigSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.DAMAGE_DECREASE.get(), OneTickBigSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_FOREST.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_LAKE.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_PLAIN.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_SEA.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_SKY.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_SNOW.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_SPIDER.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WORLD.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.VOLCANO_TP.get(), F0Particle.Provider::new);
        event.registerSpriteSet(ModParticles.SPRING.get(), OneTickBigSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_SPRING.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.RED_SPELL.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LONG_RED_SPELL.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WHITE_SPELL.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.YSR.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.YSR1.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LiuliSpell.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BIG.get(), BigSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.PurpleIronBig.get(), BigSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.PurpleIronOneTick.get(), OneTickParticle.Provider::new);

        event.registerSpriteSet(ModParticles.LifeElement.get(), OneTickMidSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WaterElement.get(), OneTickMidSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.FireElement.get(), OneTickMidSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.IceElement.get(), OneTickMidSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.StoneElement.get(), OneTickMidSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LightningElement.get(), OneTickMidSizeParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WindElement.get(), OneTickMidSizeParticle.Provider::new);

        event.registerSpriteSet(ModParticles.LifeElementParticle.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WaterElementParticle.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.FireElementParticle.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.IceElementParticle.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.StoneElementParticle.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LightningElementParticle.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WindElementParticle.get(), FirstParticle.Provider::new);

        event.registerSpriteSet(ModParticles.LifeElement100TickParticle.get(), Element100TickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WaterElement100TickParticle.get(), Element100TickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.FireElement100TickParticle.get(), Element100TickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.IceElement100TickParticle.get(), Element100TickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.StoneElement100TickParticle.get(), Element100TickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LightningElement100TickParticle.get(), Element100TickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WindElement100TickParticle.get(), Element100TickParticle.Provider::new);

        event.registerSpriteSet(ModParticles.LifeElement1TickParticle.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WaterElement1TickParticle.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.FireElement1TickParticle.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.IceElement1TickParticle.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.StoneElement1TickParticle.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LightningElement1TickParticle.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WindElement1TickParticle.get(), OneTickParticle.Provider::new);

        event.registerSpriteSet(ModParticles.SoraSwordParticle.get(), FirstParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        MinecraftForge.EVENT_BUS.addListener(ClientModEventSubscriber::Disabled);
        event.registerBelowAll("mana", ManaHud.HUD_MANA);
        event.registerBelowAll("shield", ShieldHud.HUD_SHIELD);

        // 玩家主操作界面HUD
        event.registerAboveAll("buff_hud", BuffHud.HUD_ATTRIBUTE);
        event.registerAboveAll("player_self_attributes_hud", PlayerSelfAttributesHud.SELF_ATTRIBUTES_HUD);
        event.registerAboveAll("mob_attributes_hud", MobAttributesHud.MOB_ATTRIBUTES_HUD);
        event.registerAboveAll("player_other_attributes_hud", PlayerOtherAttributesHud.OTHER_PLAYER_HUD);
        event.registerAboveAll("item_exp_get_hud", ItemAndExpGetHud.ITEM_EXP_GET_HUD);

        event.registerAboveAll("guide_hud", GuideHud.GUIDE_HUD);
        event.registerAboveAll("mob_kill_entrustment_hud", MobKillEntrustmentHud.ENTRUSTMENT_HUD);
        event.registerAboveAll("new_team_instance_hud", NewTeamInstanceHud.NEW_TEAM_INSTANCE_HUD);

        event.registerAboveAll("quick_use_hud", QuickUseHud.QUICK_USE_HUD);
        event.registerAboveAll("skill_v2_hud", SkillV2Hud.SKILL_V2_HUD);
    }

    private static final List<ResourceLocation> overlays = List.of(VanillaGuiOverlay.ARMOR_LEVEL.id(),
            VanillaGuiOverlay.MOUNT_HEALTH.id());

    public static void Disabled(RenderGuiOverlayEvent event) {
        NamedGuiOverlay overlay = event.getOverlay();
        if (overlays.contains(overlay.id())) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void tooltipComps(RegisterClientTooltipComponentFactoriesEvent e) {
        e.register(TraditionalTooltip.MyTooltip.class, TraditionalTooltip::new);
        e.register(NewTooltip.MyNewTooltip.class, NewTooltip::new);
    }
}

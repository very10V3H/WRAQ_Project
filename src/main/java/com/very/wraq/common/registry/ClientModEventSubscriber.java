package com.very.wraq.common.registry;

import com.very.wraq.entities.entities.Civil.CivilRender;
import com.very.wraq.entities.entities.SakuraMob.SakuraMobRender;
import com.very.wraq.entities.entities.SoraSword.SoraRedSwordAirRender;
import com.very.wraq.entities.entities.SoraSword.SoraSwordAirRender;
import com.very.wraq.entities.render.*;
import com.very.wraq.process.func.guide.GuideHud;
import com.very.wraq.process.system.teamInstance.NewTeamInstanceHud;
import com.very.wraq.render.hud.AttributeHud;
import com.very.wraq.render.hud.ShieldHud;
import com.very.wraq.render.particles.*;
import com.very.wraq.render.toolTip.NewTooltip;
import com.very.wraq.render.toolTip.TraditionalTooltip;
import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.struct.SkillImage;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.hud.manaHud;
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
        EntityRenderers.register(ModEntityType.SakuraMob.get(), SakuraMobRender::new);
        EntityRenderers.register(ModEntityType.Scarecrow.get(), ScarecrowRender::new);
        EntityRenderers.register(ModEntityType.NEW_ARROW_PLAIN.get(), NewArrowPlainRender::new);
        EntityRenderers.register(ModEntityType.NEW_ARROW.get(), NewArrowRender::new);
        EntityRenderers.register(ModEntityType.NEW_ARROW_MAGMA.get(), NewArrowMagmaRender::new);
        EntityRenderers.register(ModEntityType.NEW_ARROW_WORLD.get(), NewArrowWorldRender::new);
        EntityRenderers.register(ModEntityType.NEW_ARROW_NETHER.get(), NewArrowNetherRender::new);
        EntityRenderers.register(ModEntityType.FIRE_WORK.get(), FireworkRender::new);
        EntityRenderers.register(ModEntityType.NEW_ARROW_SEA.get(), NewArrowSeaRender::new);
        EntityRenderers.register(ModEntityType.NEW_ARROW_SAKURA.get(), NewArrowSakuraRender::new);
        EntityRenderers.register(ModEntityType.NEW_ARROW_SNOW.get(), NewArrowSnowRender::new);
        EntityRenderers.register(ModEntityType.METEORITE.get(), MeteoriteRender::new);
        EntityRenderers.register(ModEntityType.SWORD_AIR.get(), SwordAirRender::new);
        EntityRenderers.register(ModEntityType.SHANGMENGLI_SWORD_AIR.get(), ShangMengLiSwordAirRender::new);
        EntityRenderers.register(ModEntityType.SORA_SWORD_AIR.get(), SoraSwordAirRender::new);
        EntityRenderers.register(ModEntityType.SORA_RED_SWORD_AIR.get(), SoraRedSwordAirRender::new);
        EntityRenderers.register(ModEntityType.BLAZE_SWORD.get(), BlazeSwordRender::new);
        EntityRenderers.register(ModEntityType.CIVIL.get(), CivilRender::new);


        ClientUtils.SwordSkillsResource.init();
        ClientUtils.BowSkillsResource.init();
        ClientUtils.ManaSkillsResource.init();
        ClientUtils.Demon_Image[1] = new SkillImage(0, 0, 0);
        ClientUtils.Demon_Image[2] = new SkillImage(0, 0, 0);
    }

    @SubscribeEvent
    public static void onRegisterRender(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityType.HETITY.get(), MainBossRender::new);
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
        event.registerSpriteSet(ModParticles.MANAFOREST.get(), OneTickParticle.Provider::new);
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
        event.registerBelowAll("mana", manaHud.HUD_MANA);
        event.registerBelowAll("shield", ShieldHud.HUD_SHIELD);
        event.registerAboveAll("attributehud", AttributeHud.HUD_ATTRIBUTE);
        event.registerAboveAll("guide_hud", GuideHud.GUIDE_HUD);
        event.registerAboveAll("new_team_instance_hud", NewTeamInstanceHud.NEW_TEAM_INSTANCE_HUD);
    }

    private static final List<ResourceLocation> overlays = List.of(VanillaGuiOverlay.ARMOR_LEVEL.id(),
            VanillaGuiOverlay.PLAYER_HEALTH.id(), VanillaGuiOverlay.MOUNT_HEALTH.id());

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

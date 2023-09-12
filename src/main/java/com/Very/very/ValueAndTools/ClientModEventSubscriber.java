package com.Very.very.ValueAndTools;

import com.Very.very.Entity.render.SakuraMobRender;
import com.Very.very.Render.Hud.AttributeHud;
import com.Very.very.Render.Hud.ShieldHud;
import com.Very.very.Projectile.Mana.NewArrowLifeRender;
import com.Very.very.Projectile.Mana.NewArrowPlainRender;
import com.Very.very.Projectile.Mana.NewArrowRender;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Entity.model.HEntityModel;
import com.Very.very.Entity.render.RenderH;
import com.Very.very.Render.Hud.manaHud;
import com.Very.very.Render.Particles.FirstParticle;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.Render.Particles.OneTickParticle;
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

@Mod.EventBusSubscriber(modid = Utils.MOD_ID,value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEventSubscriber {
    @SubscribeEvent
    public static void OnClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityInit.SakuraMob.get(),SakuraMobRender::new);
        ClientUtils.SwordSkillsResource.init();
        ClientUtils.BowSkillsResource.init();
        ClientUtils.ManaSkillsResource.init();
    }
    @SubscribeEvent
    public static void OnRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(HEntityModel.LAYER_LOCATION, HEntityModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void onRegisterRender(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(EntityInit.HETITY.get(), RenderH::new);
        event.registerEntityRenderer(EntityInit.NEW_ARROW.get(), NewArrowRender::new);
        event.registerEntityRenderer(EntityInit.NEW_ARROW_PLAIN.get(), NewArrowPlainRender::new);
        event.registerEntityRenderer(EntityInit.NEW_ARROW_LIFE.get(), NewArrowLifeRender::new);
    }
/*    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.FIRST_PARTICLE.get(),
                FirstParticle.Provider::new);
    }*/
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
/*        Minecraft.getInstance().particleEngine.register(ModParticles.FIRST_PARTICLE.get(),
                FirstParticle.Provider::new);*/

        event.registerSpriteSet(ModParticles.FIRST_PARTICLE.get(),FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BREAKDEFENCE_MANA.get(),FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.DAMAGE_MANA.get(),FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.EFFECT_MANA.get(),FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.GATHER_MANA.get(),FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.KAZE_MANA.get(),FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LIGHTNING_MANA.get(),FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.RANGE_MANA.get(),FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SNOW_MANA.get(),FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.END_PARTICLE.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BLACKFOREST.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.BLACKFOREST_RECALL.get(), FirstParticle.Provider::new);
        event.registerSpriteSet(ModParticles.FOREST.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.KAZE.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LAKE.get(), OneTickParticle.Provider::new);
        event.registerSpriteSet(ModParticles.LIGHTNINGISLAND.get(), OneTickParticle.Provider::new);
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
    }
    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event)
    {

        MinecraftForge.EVENT_BUS.addListener(ClientModEventSubscriber::Disabled);
        event.registerBelowAll("mana", manaHud.HUD_MANA);
        event.registerBelowAll("shield", ShieldHud.HUD_SHIELD);
        event.registerAboveAll("attributehud", AttributeHud.HUD_ATTRIBUTE);
    }
    private static final List<ResourceLocation> overlays = List.of(VanillaGuiOverlay.AIR_LEVEL.id(),VanillaGuiOverlay.ARMOR_LEVEL.id(),
            VanillaGuiOverlay.PLAYER_HEALTH.id(),VanillaGuiOverlay.MOUNT_HEALTH.id());
    public static void Disabled(RenderGuiOverlayEvent event) {
        NamedGuiOverlay overlay = event.getOverlay();
        if (overlays.contains(overlay.id())) event.setCanceled(true);
    }
}

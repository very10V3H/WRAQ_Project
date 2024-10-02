package fun.wraq.render.hud;

import com.lootbeams.ClientSetup;
import com.lootbeams.Configuration;
import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.element.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.Map;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class Common {
    @SubscribeEvent
    public static void render(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay().equals(VanillaGuiOverlay.CROSSHAIR.type())
                && (Boolean)Configuration.ADVANCED_TOOLTIPS.get()
                && (Minecraft.getInstance().screen == null || Minecraft.getInstance().screen instanceof ChatScreen)) {
            Player player = Minecraft.getInstance().player;
            /*ItemStack itemStack = ModItems.PlainSword0.get().getDefaultInstance();*/
/*            Vec3 pos = new Vec3(1099, 81, 40);*/
/*            Vec3 tooltipWorldPos = pos.add(0.0, Math.min(1.0, player.position().distanceToSqr(pos) * 0.025) +
                    (Double) Configuration.NAMETAG_Y_OFFSET.get() +
                    (double) ((float) Screen.getTooltipFromItem(Minecraft.getInstance(), itemStack).size() / 100.0F), 0.0);*/
/*            float partialTick = event.getPartialTick();
            Vector3f desiredScreenSpacePos = ClientSetup.worldToScreenSpace(pos, 1);*/
/*            float var10002 = Mth.clamp(desiredScreenSpacePos.x(), 0.0F, (float)event.getWindow().getGuiScaledWidth());
            float var10003 = desiredScreenSpacePos.y();
            int var10005 = event.getWindow().getGuiScaledHeight();
            Objects.requireNonNull(Minecraft.getInstance().font);
            desiredScreenSpacePos = new Vector3f(var10002, Mth.clamp(var10003, 0.0F,
                    (float)(var10005 - 9 * Screen.getTooltipFromItem(Minecraft.getInstance(), itemStack).size())), desiredScreenSpacePos.z());*/

/*            event.getGuiGraphics().renderTooltip(Minecraft.getInstance().font, itemStack,
                    (int) desiredScreenSpacePos.x(), (int) desiredScreenSpacePos.y());*/

            Compute.getPlayerRayMobList(player, 1, 1, 16)
                    .forEach(mob -> {
                        renderMobElement(Element.lifeElementParticle, Element.life, mob, player, event);
                        renderMobElement(Element.waterElementParticle, Element.water, mob, player, event);
                        renderMobElement(Element.fireElementParticle, Element.fire, mob, player, event);
                        renderMobElement(Element.stoneElementParticle, Element.stone, mob, player, event);
                        renderMobElement(Element.iceElementParticle, Element.ice, mob, player, event);
                        renderMobElement(Element.windElementParticle, Element.wind, mob, player, event);
                        renderMobElement(Element.lightningElementParticle, Element.lightning, mob, player, event);
                    });
        }
    }
    
    private static void renderMobElement(Map<Entity, Integer> map, String type, Mob mob, Player player, RenderGuiOverlayEvent.Post event) {
        if (map.containsKey(mob)) {
            Vec3 dis = mob.getEyePosition().subtract(player.getEyePosition());
            Vec3 vec3 = mob.getEyePosition().add(dis.normalize().scale(-0.75)
                    .add(player.getHandHoldingItemAngle(ModItems.PlainSword0.get())));
            Vector3f toScreenPos = ClientSetup.worldToScreenSpace(vec3, 0);
            event.getGuiGraphics().blit(Element.getResource(type), (int) toScreenPos.x, (int) toScreenPos.y,
                    0, 0, 16,16, 16, 16);
        }
    }
}

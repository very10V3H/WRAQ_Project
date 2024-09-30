package fun.wraq.render.hud;

import com.lootbeams.ClientSetup;
import com.lootbeams.Configuration;
import fun.wraq.common.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector3f;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = {Dist.CLIENT})
public class Common {
    @SubscribeEvent
    public static void render(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay().equals(VanillaGuiOverlay.CROSSHAIR.type()) && (Boolean)Configuration.ADVANCED_TOOLTIPS.get() && (Minecraft.getInstance().screen == null || Minecraft.getInstance().screen instanceof ChatScreen)) {
            Player player = Minecraft.getInstance().player;
            ItemStack itemStack = ModItems.PlainSword0.get().getDefaultInstance();
            Vec3 pos = new Vec3(1099, 81, 40);
            Vec3 tooltipWorldPos = pos.add(0.0, Math.min(1.0, player.position().distanceToSqr(pos) * 0.025) +
                    (Double) Configuration.NAMETAG_Y_OFFSET.get() +
                    (double) ((float) Screen.getTooltipFromItem(Minecraft.getInstance(), itemStack).size() / 100.0F), 0.0);
            Vector3f desiredScreenSpacePos = ClientSetup.worldToScreenSpace(tooltipWorldPos, event.getPartialTick());
            float var10002 = Mth.clamp(desiredScreenSpacePos.x(), 0.0F, (float)event.getWindow().getGuiScaledWidth());
            float var10003 = desiredScreenSpacePos.y();
            int var10005 = event.getWindow().getGuiScaledHeight();
            Objects.requireNonNull(Minecraft.getInstance().font);
            desiredScreenSpacePos = new Vector3f(var10002, Mth.clamp(var10003, 0.0F,
                    (float)(var10005 - 9 * Screen.getTooltipFromItem(Minecraft.getInstance(), itemStack).size())), desiredScreenSpacePos.z());

            event.getGuiGraphics().renderTooltip(Minecraft.getInstance().font, itemStack,
                    (int) desiredScreenSpacePos.x(), (int) desiredScreenSpacePos.y());
        }
    }
}

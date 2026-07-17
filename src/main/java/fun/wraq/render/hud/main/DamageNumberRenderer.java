package fun.wraq.render.hud.main;

import com.mojang.blaze3d.vertex.PoseStack;
import fun.wraq.common.util.struct.DamageNumber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
/** AI-Generated, 2026-05-10 */
public class DamageNumberRenderer {
    private static final List<DamageNumber> damageNumbers = new ArrayList<>();
    private static final int MAX_ENTRIES = 200;
    private static final int DEFAULT_DURATION_MS = 1000;

    public static void addDamageNumber(Vec3 position, Component component, int durationMs) {
        if (damageNumbers.size() >= MAX_ENTRIES) {
            damageNumbers.remove(0);
        }
        long expireAt = System.currentTimeMillis() + (durationMs > 0 ? durationMs : DEFAULT_DURATION_MS);
        damageNumbers.add(new DamageNumber(position, component, expireAt));
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        if (damageNumbers.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        PoseStack poseStack = event.getPoseStack();
        Vec3 camPos = event.getCamera().getPosition();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        Font font = mc.font;
        long now = System.currentTimeMillis();

        Iterator<DamageNumber> it = damageNumbers.iterator();
        while (it.hasNext()) {
            DamageNumber entry = it.next();

            long remainingMs = entry.getExpireAtMs() - now;
            if (remainingMs <= 0) {
                it.remove();
                continue;
            }

            Vec3 pos = entry.getPosition();
            double dx = pos.x - camPos.x;
            double dy = pos.y - camPos.y;
            double dz = pos.z - camPos.z;

            if (!event.getFrustum().isVisible(new AABB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z))) continue;

            float elapsedFraction = 1.0f - (float) remainingMs / DEFAULT_DURATION_MS;
            float floatUp = elapsedFraction * 0.8f;

            int alpha = remainingMs > 200 ? 255 : (int) (remainingMs / 200.0f * 255);
            if (alpha <= 0) {
                it.remove();
                continue;
            }
            int color = (alpha << 24) | 0x00FFFFFF;

            poseStack.pushPose();
            poseStack.translate(dx, dy + floatUp + 0.5, dz);
            poseStack.mulPose(mc.gameRenderer.getMainCamera().rotation());
            poseStack.scale(-0.025F, -0.025F, 0.025F);

            Matrix4f matrix4f = poseStack.last().pose();
            Component text = entry.getComponent();
            float halfWidth = -font.width(text) / 2f;

            font.drawInBatch(text, halfWidth, 0, color, false, matrix4f, buffer,
                    Font.DisplayMode.SEE_THROUGH, 0, LightTexture.FULL_BRIGHT);

            poseStack.popPose();
        }

        buffer.endBatch();
    }
}

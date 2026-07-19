/** AI-Generated, 2026-07-19 */
package fun.wraq.render.hud.main;

import com.mojang.blaze3d.vertex.PoseStack;
import fun.wraq.process.system.worldtext.WorldTextDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端世界文字渲染器。
 * 通过 {@link WorldTextS2CPacket} 接收服务端下发的世界文字条目，
 * 并在 {@link RenderLevelStageEvent} 中以公告牌模式渲染文本。
 * <p>
 * 设计目标：逐步替代 {@code Compute.summonArmorStand()} 的盔甲架渲染方案。
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldTextRenderer {

    private static final Minecraft mc = Minecraft.getInstance();
    private static final double DISPLAY_RANGE = 64.0;
    private static final double LABEL_SCALE = 0.025;

    /** 按维度存储当前帧应渲染的文字条目 */
    private static final Map<ResourceKey<Level>, List<WorldTextDataManager.Entry>> ENTRIES = new ConcurrentHashMap<>();

    /**
     * 由 {@link WorldTextS2CPacket#handle} 在主线程调用，替换指定维度的条目快照。
     */
    public static void updateEntries(ResourceKey<Level> dimension, List<WorldTextDataManager.Entry> entries) {
        if (entries.isEmpty()) {
            ENTRIES.remove(dimension);
        } else {
            ENTRIES.put(dimension, Collections.unmodifiableList(entries));
        }
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        Player player = mc.player;
        if (player == null) return;

        ResourceKey<Level> currentDim = player.level().dimension();
        List<WorldTextDataManager.Entry> entries = ENTRIES.get(currentDim);
        if (entries == null || entries.isEmpty()) return;

        Vec3 camPos = event.getCamera().getPosition();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        Font font = mc.font;

        for (WorldTextDataManager.Entry entry : entries) {
            Vec3 pos = entry.pos();
            double dx = pos.x - camPos.x;
            double dy = pos.y - camPos.y;
            double dz = pos.z - camPos.z;
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

            // 距离剔除
            if (dist > DISPLAY_RANGE) continue;

            // 视锥体裁剪
            if (!event.getFrustum().isVisible(new AABB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z)))
                continue;

            // 距离缩放：远处文本变小
            float distScale = (float) Math.min(1.0, 48.0 / Math.max(dist, 8.0));

            renderLabel(poseStack, buffer, font, dx, dy, dz, entry.text(), distScale);
        }

        buffer.endBatch();
    }

    private static void renderLabel(PoseStack poseStack, MultiBufferSource buffer, Font font,
                                    double dx, double dy, double dz, Component text,
                                    float distScale) {
        poseStack.pushPose();
        poseStack.translate(dx, dy, dz);
        poseStack.mulPose(mc.gameRenderer.getMainCamera().rotation());
        poseStack.scale((float) (-LABEL_SCALE * distScale), (float) (-LABEL_SCALE * distScale),
                (float) (LABEL_SCALE * distScale));

        Matrix4f matrix4f = poseStack.last().pose();
        float halfWidth = -font.width(text) / 2f;

        font.drawInBatch(text, halfWidth, 0, 0xFFFFFFFF, false, matrix4f, buffer,
                Font.DisplayMode.SEE_THROUGH, 0, LightTexture.FULL_BRIGHT);

        poseStack.popPose();
    }
}

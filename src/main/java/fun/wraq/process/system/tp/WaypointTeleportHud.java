/** AI-Generated, 2026-07-18 */
package fun.wraq.process.system.tp;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.Map;

/**
 * 客户端世界渲染：在传送锚点坐标处渲染名称与解锁状态（类似原神传送锚点）
 * 参考 DamageNumberRenderer 使用 RenderLevelStageEvent + PoseStack 实现世界空间文字
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WaypointTeleportHud {

    private static final Minecraft mc = Minecraft.getInstance();
    private static final double DISPLAY_RANGE = 64.0;
    private static final double UNLOCK_HINT_RANGE = 6.0;
    private static final double LABEL_SCALE = 0.025;

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        Player player = mc.player;
        if (player == null) return;
        if (!player.level().dimension().equals(Level.OVERWORLD)) return;

        Vec3 camPos = event.getCamera().getPosition();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        Font font = mc.font;

        for (Map.Entry<String, Vec3> entry : WaypointTeleportHandler.ALLOWED_WAYPOINTS.entrySet()) {
            String name = entry.getKey();
            Vec3 wpPos = entry.getValue();

            double dx = wpPos.x - camPos.x;
            double dy = wpPos.y - camPos.y;
            double dz = wpPos.z - camPos.z;
            double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

            if (dist > DISPLAY_RANGE) continue;

            // 视锥体裁剪
            if (!event.getFrustum().isVisible(new AABB(wpPos.x, wpPos.y, wpPos.z, wpPos.x, wpPos.y, wpPos.z)))
                continue;

            boolean unlocked = WaypointTeleportClientData.isUnlocked(name);

            // 锚点名称（金）
            Component nameComp = Component.literal("传送锚点 - " + name).withStyle(Style.EMPTY.withColor(0xFFD700));

            // 解锁状态（绿/红）
            Component statusComp = unlocked
                    ? Component.literal("已解锁").withStyle(Style.EMPTY.withColor(0x55FF55))
                    : Component.literal("未解锁").withStyle(Style.EMPTY.withColor(0xFF5555));

            // 根据距离微调缩放，远处变小
            float distScale = (float) Math.min(1.0, 48.0 / Math.max(dist, 8.0));

            // 渲染锚点名称 (y 偏移 +2.5)
            renderLabel(poseStack, buffer, font, dx, dy + 2.5, dz, nameComp, distScale);

            // 渲染解锁状态 (y 偏移 +2.0，在名称下方)
            renderLabel(poseStack, buffer, font, dx, dy + 2.0, dz, statusComp, distScale);

            // 未解锁且玩家在提示范围内 → 显示"右键以解锁"
            if (!unlocked && dist < UNLOCK_HINT_RANGE) {
                Component hintComp = Component.literal("右键以解锁")
                        .withStyle(Style.EMPTY.withColor(0xFFFF55));
                renderLabel(poseStack, buffer, font, dx, dy + 1.5, dz, hintComp, distScale);
            }
        }

        buffer.endBatch();
    }

    /**
     * 在 3D 世界空间渲染一个面向摄像机的文字标签
     */
    private static void renderLabel(PoseStack poseStack, MultiBufferSource buffer, Font font,
                                    double dx, double dy, double dz, Component text,
                                    float distScale) {
        poseStack.pushPose();
        poseStack.translate(dx, dy, dz);
        poseStack.mulPose(mc.gameRenderer.getMainCamera().rotation());
        poseStack.scale((float) (-LABEL_SCALE * distScale), (float) (-LABEL_SCALE * distScale), (float) (LABEL_SCALE * distScale));

        Matrix4f matrix4f = poseStack.last().pose();
        float halfWidth = -font.width(text) / 2f;

        font.drawInBatch(text, halfWidth, 0, 0xFFFFFFFF, false, matrix4f, buffer,
                Font.DisplayMode.SEE_THROUGH, 0, LightTexture.FULL_BRIGHT);

        poseStack.popPose();
    }
}

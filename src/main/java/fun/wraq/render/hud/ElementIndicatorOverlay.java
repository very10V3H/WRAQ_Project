package fun.wraq.render.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.process.system.element.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

/**
 * 在世界空间怪物头顶渲染元素图标 Billboard。
 * 遵循 DamageNumberRenderer 的渲染模式。
 * AI-Generated, 2026-07-26
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ElementIndicatorOverlay {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // 元素类型由 MobAttributeC2SPacket/S2CPacket 流水线维护
        String elementType = ClientUtils.mobElementType;
        if (elementType == null || elementType.equals("empty") || !Element.elementList.contains(elementType))
            return;

        Mob mob = ClientUtils.mobAttribute;
        if (mob == null || !mob.isAlive()) return;

        ResourceLocation icon = Element.getResource(elementType);
        if (icon == null) return;

        Vec3 camPos = event.getCamera().getPosition();
        PoseStack poseStack = event.getPoseStack();
        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();

        Vec3 worldPos = mob.getEyePosition().add(0, 1.0, 0);
        double dx = worldPos.x - camPos.x;
        double dy = worldPos.y - camPos.y;
        double dz = worldPos.z - camPos.z;

        // 视锥体裁剪
        if (!event.getFrustum().isVisible(new AABB(worldPos.x, worldPos.y, worldPos.z,
                worldPos.x, worldPos.y, worldPos.z)))
            return;

        poseStack.pushPose();
        poseStack.translate(dx, dy, dz);
        poseStack.mulPose(mc.gameRenderer.getMainCamera().rotation());
        poseStack.scale(-0.025F, -0.025F, 0.025F);

        Matrix4f matrix4f = poseStack.last().pose();
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityTranslucent(icon));
        int light = LightTexture.FULL_BRIGHT;
        int overlay = OverlayTexture.NO_OVERLAY;

        // NEW_ENTITY 格式: POSITION, COLOR, TEX, OVERLAY, LIGHTMAP, NORMAL
        consumer.vertex(matrix4f, -8, -8, 0).color(255, 255, 255, 255).uv(0, 0)
                .overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();
        consumer.vertex(matrix4f, 8, -8, 0).color(255, 255, 255, 255).uv(1, 0)
                .overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();
        consumer.vertex(matrix4f, 8, 8, 0).color(255, 255, 255, 255).uv(1, 1)
                .overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();
        consumer.vertex(matrix4f, -8, 8, 0).color(255, 255, 255, 255).uv(0, 1)
                .overlayCoords(overlay).uv2(light).normal(0, 1, 0).endVertex();

        poseStack.popPose();
        buffer.endBatch();
    }
}

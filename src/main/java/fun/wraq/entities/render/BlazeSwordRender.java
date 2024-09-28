package fun.wraq.entities.render;

import com.mojang.blaze3d.vertex.PoseStack;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.model.BlazeSwordModel;
import fun.wraq.projectiles.mana.BlazeSword;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.List;

public class BlazeSwordRender extends GeoEntityRenderer<BlazeSword> {

    public BlazeSwordRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BlazeSwordModel());
    }

    @Override
    public ResourceLocation getTextureLocation(BlazeSword animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/blaze_sword.png");
    }

    @Override
    public void render(BlazeSword entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.model.getBone("bone").ifPresent(geoBone -> {
            Vec3 vec3 = entity.getDeltaMovement();
            if (vec3.length() > 0.1) {
                Vec3 vec3NoY = new Vec3(vec3.x, 0, vec3.z);
                Vec3 zNormal = new Vec3(0, 0, -1);
                double angle = Math.acos(vec3NoY.dot(zNormal) / (vec3NoY.length() * zNormal.length()));
                if (vec3.x > 0) angle = -angle;

                geoBone.setRotY((float) ((angle)));
                geoBone.setRotZ(0);

                angle = Math.atan(vec3.y / Math.abs(Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)));
                geoBone.setRotX((float) -angle);
            } else {
                List<Player> playerList = entity.level().getEntitiesOfClass(Player.class, AABB.ofSize(entity.position(), 15, 15, 15));
                if (playerList.size() > 0) {
                    vec3 = playerList.get(0).pick(10, 0, false).getLocation().subtract(entity.position());
                    Vec3 vec3NoY = new Vec3(vec3.x, 0, vec3.z);
                    Vec3 zNormal = new Vec3(0, 0, -1);
                    double angle = Math.acos(vec3NoY.dot(zNormal) / (vec3NoY.length() * zNormal.length()));
                    if (vec3.x > 0) angle = -angle;

                    geoBone.setRotY((float) ((angle)));
                    geoBone.setRotZ(0);

                    angle = Math.atan(vec3.y / Math.abs(Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)));
                    geoBone.setRotX((float) -angle);
                }
            }
        });
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

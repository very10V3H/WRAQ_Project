package fun.wraq.entities.render;


import com.mojang.blaze3d.vertex.PoseStack;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.model.FireworkModel;
import fun.wraq.projectiles.firework.Firework;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireworkRender extends GeoEntityRenderer<Firework> {

    public FireworkRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FireworkModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Firework animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/fire_work.png");
    }

    @Override
    public void render(Firework entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

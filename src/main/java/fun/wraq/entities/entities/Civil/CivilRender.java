package fun.wraq.entities.entities.Civil;

import com.mojang.blaze3d.vertex.PoseStack;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.entities.entities.Civil.CivilModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CivilRender extends GeoEntityRenderer<fun.wraq.entities.entities.Civil.Civil> {

    public CivilRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CivilModel());
    }

    @Override
    public ResourceLocation getTextureLocation(fun.wraq.entities.entities.Civil.Civil animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/civil.png");
    }

    @Override
    public void render(Civil entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

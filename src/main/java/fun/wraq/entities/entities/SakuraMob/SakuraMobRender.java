package fun.wraq.entities.entities.SakuraMob;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import fun.wraq.entities.entities.SakuraMob.SakuraMob;
import fun.wraq.entities.entities.SakuraMob.SakuraMobModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SakuraMobRender extends GeoEntityRenderer<fun.wraq.entities.entities.SakuraMob.SakuraMob> {

    public SakuraMobRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SakuraMobModel());
    }

    @Override
    public ResourceLocation getTextureLocation(fun.wraq.entities.entities.SakuraMob.SakuraMob animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/sakuramob.png");
    }

    @Override
    public void render(SakuraMob entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

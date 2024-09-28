package fun.wraq.entities.render;


import com.mojang.blaze3d.vertex.PoseStack;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.model.ManaArrowModel;
import fun.wraq.projectiles.mana.ManaArrow;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NewArrowRender extends GeoEntityRenderer<ManaArrow> {

    public NewArrowRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ManaArrowModel());
    }

    @Override
    public ResourceLocation getTextureLocation(ManaArrow animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/evoker_mana_arrow.png");
    }

    @Override
    public void render(ManaArrow entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}

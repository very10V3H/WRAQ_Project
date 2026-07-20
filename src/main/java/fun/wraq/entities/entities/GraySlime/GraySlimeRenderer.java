/**
 * AI-Generated, 2026-07-20
 * 灰色史莱姆渲染器 — 动态生成灰色纹理（将原版史莱姆纹理去色）
 */
package fun.wraq.entities.entities.GraySlime;

import com.mojang.blaze3d.platform.NativeImage;
import fun.wraq.common.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

import java.io.IOException;
import java.io.InputStream;

public class GraySlimeRenderer extends SlimeRenderer {

    private static final ResourceLocation VANILLA_SLIME =
            new ResourceLocation("textures/entity/slime/slime.png");
    private static final ResourceLocation DYNAMIC_GRAY_LOC =
            new ResourceLocation(Utils.MOD_ID, "dynamic_gray_slime");
    private static ResourceLocation cachedTexture;
    private static boolean attempted = false;

    public GraySlimeRenderer(EntityRendererProvider.Context context) {
        super(context);
        if (cachedTexture == null && !attempted) {
            cachedTexture = generateGrayTexture();
        }
    }

    /**
     * 加载原版史莱姆纹理并转换为灰度图，注册为动态纹理并返回其 ResourceLocation。
     * 如果转换失败则回退到原版纹理。
     */
    private static ResourceLocation generateGrayTexture() {
        attempted = true;
        try {
            NativeImage image;
            try (InputStream is = Minecraft.getInstance().getResourceManager()
                    .getResource(VANILLA_SLIME).orElseThrow().open()) {
                image = NativeImage.read(is);
            }

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int color = image.getPixelRGBA(x, y);
                    int a = (color >> 24) & 0xFF;
                    int b = (color >> 16) & 0xFF;
                    int g = (color >> 8) & 0xFF;
                    int r = color & 0xFF;
                    if (a > 0) {
                        int gray = (r + g + b) / 3;
                        image.setPixelRGBA(x, y,
                                (a << 24) | (gray << 16) | (gray << 8) | gray);
                    }
                }
            }

            DynamicTexture texture = new DynamicTexture(image);
            Minecraft.getInstance().getTextureManager().register(DYNAMIC_GRAY_LOC, texture);
            return DYNAMIC_GRAY_LOC;

        } catch (IOException e) {
            return VANILLA_SLIME;
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Slime entity) {
        return cachedTexture != null ? cachedTexture : VANILLA_SLIME;
    }
}

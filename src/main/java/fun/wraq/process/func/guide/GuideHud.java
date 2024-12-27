package fun.wraq.process.func.guide;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;
import java.util.List;

public class GuideHud {
    public static boolean display = true;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    public static final IGuiOverlay GUIDE_HUD = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height / 2;
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        if (display && mc.screen == null) {
            if (Guide.clientStage == -1 || Guide.clientStage >= Guide.getGuides().size()) return;
            Guide guide = Guide.getGuides().get(Guide.clientStage);
            List<Component> components = new ArrayList<>(guide.description);
            if (guide.trigXpLevel != 0) {
                components.add(Te.s("完成此指引，需要达到 ", Utils.getLevelDescription(guide.trigXpLevel)));
            }
            components.add(Component.literal("").withStyle(ChatFormatting.WHITE));
            components.add(Te.s("按下", "[Tab]", ChatFormatting.AQUA, "以打开/关闭此栏"));
            components.add(Te.s("你也可以前往", "按键绑定", CustomStyle.styleOfStone, "修改此开关按键"));
            guiGraphics.renderComponentTooltip(fontRenderer, components, 0, (int) (y / 1.5));
        }
    });

}

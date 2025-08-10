package fun.wraq.render.gui;

import fun.wraq.common.fast.Te;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WraqScreen extends Screen {

    public static final Minecraft mc = Minecraft.getInstance();

    public static final Font font = mc.font;

    public static Component clientDisplayInfo;

    public static int infoLifeCycleTicks = 0;

    protected WraqScreen(Component component) {
        super(component);
    }

    public void render(GuiGraphics guiGraphics, int x, int y, float v) {
        if (clientDisplayInfo != null && infoLifeCycleTicks > 0) {
            guiGraphics.renderTooltip(font, Te.s(clientDisplayInfo),
                    this.width / 2 - 30, this.height / 2 + 120);
        }
        super.render(guiGraphics, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

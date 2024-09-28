package fun.wraq.render.toolTip;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NewTooltip implements ClientTooltipComponent {

    private final MyNewTooltip myNewTooltip;

    public NewTooltip(MyNewTooltip myNewTooltip) {
        this.myNewTooltip = myNewTooltip;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public int getWidth(Font font) {
        return 10;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        guiGraphics.blit(myNewTooltip.resourceLocation , x - 2, y - 2, 0, 0, 12, 12, 12, 12);
        guiGraphics.drawString(font, myNewTooltip.component, x + 12, y, 0);
    }

    public record MyNewTooltip(Component component, ResourceLocation resourceLocation) implements TooltipComponent {
    }
}

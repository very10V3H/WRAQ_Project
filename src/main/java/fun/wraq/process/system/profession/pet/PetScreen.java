package fun.wraq.process.system.profession.pet;

import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class PetScreen extends AbstractSimiContainerScreen<PetMenu> {

    public PetScreen(PetMenu petMenu, Inventory inventory, Component component) {
        super(petMenu, inventory, component);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float p_97788_, int x, int y) {
        this.renderPlayerInventory(graphics, x, y);
    }
}

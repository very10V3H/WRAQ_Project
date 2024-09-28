package fun.wraq.render.gui.testAndHelper;

import fun.wraq.render.gui.market.SmartPhone;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OpenGui {
    public OpenGui() {
        Minecraft.getInstance().setScreen(new SmartPhone(true));
    }

}

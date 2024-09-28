package fun.wraq.render.gui.testAndHelper;

import fun.wraq.render.gui.skills.IdCardGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OpenSkillGui {
    public OpenSkillGui() {
        Minecraft.getInstance().setScreen(new IdCardGui(true));
    }
}

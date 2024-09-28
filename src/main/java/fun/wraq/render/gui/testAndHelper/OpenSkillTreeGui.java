package fun.wraq.render.gui.testAndHelper;

import fun.wraq.render.gui.skills.SkillTreeGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OpenSkillTreeGui {
    public OpenSkillTreeGui() {
        Minecraft.getInstance().setScreen(new SkillTreeGui(true));
    }
}

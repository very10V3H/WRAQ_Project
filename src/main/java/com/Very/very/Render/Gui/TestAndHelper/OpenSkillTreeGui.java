package com.Very.very.Render.Gui.TestAndHelper;

import com.Very.very.Render.Gui.Skills.SkillTreeGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OpenSkillTreeGui {
    public OpenSkillTreeGui(){
        Minecraft.getInstance().setScreen(new SkillTreeGui(true));
    }
}

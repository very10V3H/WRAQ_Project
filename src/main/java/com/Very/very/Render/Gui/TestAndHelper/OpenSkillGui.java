package com.Very.very.Render.Gui.TestAndHelper;

import com.Very.very.Render.Gui.Skills.SkillGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OpenSkillGui {
    public OpenSkillGui(){
        Minecraft.getInstance().setScreen(new SkillGui(true));
    }
}

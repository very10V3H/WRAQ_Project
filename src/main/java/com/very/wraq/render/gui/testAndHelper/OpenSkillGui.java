package com.very.wraq.render.gui.testAndHelper;

import com.very.wraq.render.gui.skills.SkillGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OpenSkillGui {
    public OpenSkillGui(){
        Minecraft.getInstance().setScreen(new SkillGui(true));
    }
}

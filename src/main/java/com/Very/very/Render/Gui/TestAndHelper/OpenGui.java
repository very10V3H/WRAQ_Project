package com.Very.very.Render.Gui.TestAndHelper;

import com.Very.very.Render.Gui.Market.SmartPhone;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OpenGui {
    public OpenGui(){
        Minecraft.getInstance().setScreen(new SmartPhone(true));
    }

}

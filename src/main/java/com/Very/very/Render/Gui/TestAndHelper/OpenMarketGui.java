package com.Very.very.Render.Gui.TestAndHelper;

import com.Very.very.Render.Gui.Market.MarketSereen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OpenMarketGui {
    public OpenMarketGui(){
        Minecraft.getInstance().setScreen(new MarketSereen(true));
    }
}

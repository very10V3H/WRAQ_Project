package com.very.wraq.render.gui.testAndHelper;

import com.very.wraq.render.gui.market.MarketSereen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OpenMarketGui {
    public OpenMarketGui(){
        Minecraft.getInstance().setScreen(new MarketSereen(true));
    }
}

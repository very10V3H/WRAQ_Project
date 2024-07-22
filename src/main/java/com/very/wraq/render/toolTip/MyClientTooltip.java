package com.very.wraq.render.toolTip;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MyClientTooltip implements ClientTooltipComponent {
    private final MyTooltip myTooltip;

    public MyClientTooltip(MyTooltip myTooltip) {
        this.myTooltip = myTooltip;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public int getWidth(Font font) {
        return 10;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        guiGraphics.blit(resourcelocation[myTooltip.type], x - 2, y - 2, 0, 0, 12, 12, 12, 12);
        guiGraphics.drawString(font, myTooltip.mutableComponent, x + 12, y, 0);
    }

    public static record MyTooltip(MutableComponent mutableComponent, int type) implements TooltipComponent {
    }

    public static final ResourceLocation[] resourcelocation = {

            new ResourceLocation(Utils.MOD_ID, "textures/hud/attack0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/manadamage0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/defence0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/manadefence0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/maxhealth.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/healthrecover.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/breakdefence.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/breakdefence0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/critrate0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/critdamage0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/healsteal0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/manacost.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/maxmana.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/manadefencebreak0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/manabreakdefence0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/manareply0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cooldown0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/speed0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/speed0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/attack0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/critrate0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/critdamage0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/manadamage0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/manareply0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/defence0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/maxhealth.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/healthrecover.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/cooldown0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/speed0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/expup.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/swift.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/mana_health_steal.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/mana_health_steal.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/health_strength.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/health_strength.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/breakdefence0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/manabreakdefence0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/expup.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/lucky_up.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/lucky_up.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/breakdefence.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/manadefencebreak0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/healsteal0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/manadefence0.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/life_element.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/water_element.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/fire_element.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/stone_element.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/ice_element.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/lightning_element.png"),
            new ResourceLocation(Utils.MOD_ID, "textures/hud/wind_element.png"),
    };

}

package com.very.wraq.render.toolTip;

import com.very.wraq.common.util.Utils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TraditionalTooltip implements ClientTooltipComponent {

    private final MyTooltip myTooltip;

    public TraditionalTooltip(MyTooltip myTooltip) {
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
        guiGraphics.drawString(font, myTooltip.component, x, y, 0);
    }

    public record MyTooltip(Component component, int type) implements TooltipComponent {
    }

    public static ResourceLocation attackDamage = new ResourceLocation(Utils.MOD_ID, "textures/hud/attack0.png");
    public static ResourceLocation manaDamage = new ResourceLocation(Utils.MOD_ID, "textures/hud/manadamage0.png");
    public static ResourceLocation defence = new ResourceLocation(Utils.MOD_ID, "textures/hud/defence0.png");
    public static ResourceLocation manaDefence = new ResourceLocation(Utils.MOD_ID, "textures/hud/manadefence0.png");
    public static ResourceLocation maxHealth = new ResourceLocation(Utils.MOD_ID, "textures/hud/maxhealth.png");
    public static ResourceLocation healthRecover = new ResourceLocation(Utils.MOD_ID, "textures/hud/healthrecover.png");
    public static ResourceLocation defencePenetration = new ResourceLocation(Utils.MOD_ID, "textures/hud/breakdefence.png");
    public static ResourceLocation defencePenetration0 = new ResourceLocation(Utils.MOD_ID, "textures/hud/breakdefence0.png");
    public static ResourceLocation critRate = new ResourceLocation(Utils.MOD_ID, "textures/hud/critrate0.png");
    public static ResourceLocation critDamage = new ResourceLocation(Utils.MOD_ID, "textures/hud/critdamage0.png");
    public static ResourceLocation healthSteal = new ResourceLocation(Utils.MOD_ID, "textures/hud/healsteal0.png");
    public static ResourceLocation manaCost = new ResourceLocation(Utils.MOD_ID, "textures/hud/manacost.png");
    public static ResourceLocation maxMana = new ResourceLocation(Utils.MOD_ID, "textures/hud/maxmana.png");
    public static ResourceLocation manaPenetration = new ResourceLocation(Utils.MOD_ID, "textures/hud/manadefencebreak0.png");
    public static ResourceLocation manaPenetration0 = new ResourceLocation(Utils.MOD_ID, "textures/hud/manabreakdefence0.png");
    public static ResourceLocation manaRecover = new ResourceLocation(Utils.MOD_ID, "textures/hud/manareply0.png");
    public static ResourceLocation releaseSpeed = new ResourceLocation(Utils.MOD_ID, "textures/hud/cooldown0.png");
    public static ResourceLocation movementSpeed = new ResourceLocation(Utils.MOD_ID, "textures/hud/speed0.png");
    public static ResourceLocation expUp = new ResourceLocation(Utils.MOD_ID, "textures/hud/expup.png");
    public static ResourceLocation swiftnessUp = new ResourceLocation(Utils.MOD_ID, "textures/hud/swift.png");
    public static ResourceLocation manaHealthSteal = new ResourceLocation(Utils.MOD_ID, "textures/hud/mana_health_steal.png");
    public static ResourceLocation healthStrength = new ResourceLocation(Utils.MOD_ID, "textures/hud/health_strength.png");
    public static ResourceLocation luckyUp = new ResourceLocation(Utils.MOD_ID, "textures/hud/lucky_up.png");
    public static ResourceLocation lifeElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/life_element.png");
    public static ResourceLocation waterElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/water_element.png");
    public static ResourceLocation fireElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/fire_element.png");
    public static ResourceLocation stoneElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/stone_element.png");
    public static ResourceLocation iceElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/ice_element.png");
    public static ResourceLocation lightningElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/lightning_element.png");
    public static ResourceLocation windElement = new ResourceLocation(Utils.MOD_ID, "textures/hud/wind_element.png");
    public static ResourceLocation forge = new ResourceLocation(Utils.MOD_ID, "textures/hud/forge.png");

}

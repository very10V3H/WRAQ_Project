package com.very.wraq.render.hud;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.ArrayList;
import java.util.List;

public class InformationOnInventory {
    public static List<Component> components = new ArrayList<>() {{
        add(Component.literal("测试"));
    }};

    public static final Minecraft mc = Minecraft.getInstance();
    public static final IGuiOverlay INFO = ((gui, poseStack, partialTick, width, height) -> {
        if (mc.screen instanceof InventoryScreen) {
            int x = width / 2;
            int y = height / 2;
            int offsetX = -86;
            int offsetY = -92;
            int mouseX = (int) mc.mouseHandler.xpos();
            int mouseY = (int) mc.mouseHandler.ypos();

            poseStack.drawString(gui.getFont(), Component.literal("光标移动至此处可查看简单文字教程").withStyle(ChatFormatting.WHITE), x + offsetX, x + offsetY, 0);
            if (mouseX > x + offsetX && mouseX < x + 56 && mouseY > y + offsetY - 6 && mouseY < y + offsetY + 12) {
                poseStack.renderComponentTooltip(gui.getFont(), components, mouseX, mouseY);
            }
        }
    });
}

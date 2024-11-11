package fun.wraq.render.hud.main;

import com.google.common.collect.ImmutableMap;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.KeyBoradInput;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Map;

public class QuickUseHud {

    public static int mode = 1;

    public static final String DISPLAY_KEY = "QUICK_USE_HUD_DISPLAY";

    public static final ResourceLocation cross = new ResourceLocation(Utils.MOD_ID, "textures/hud/mainhand_active.png");

    public static final IGuiOverlay QUICK_USE_HUD = ((gui, poseStack, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;
        Font font = mc.font;
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());

        int x = width / 2;
        int y = height;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            Item item = stack.getItem();
            if (item instanceof ActiveItem) {
                if (!player.getCooldowns().isOnCooldown(item)) {
                    if (i > 2) {
                        guiGraphics.drawCenteredString(font, Te.s(getKeyName(i - 3), ChatFormatting.AQUA),
                                x - 88 + 20 * i, y - 23, 0);
                    }
                    guiGraphics.blit(ClientUtils.CdResourceLocation[12], x - 88 + 20 * i, y - 19,
                            0, 0, 16, 16, 16, 16);
                }
            }
            if (!(item instanceof ActiveItem)) {
                if (i > 2) {
                    guiGraphics.drawCenteredString(font, Te.s(getKeyName(i - 3), ChatFormatting.GRAY),
                            x - 88 + 20 * i, y - 23, 0);
                }
            }
        }

        if (mode != -1 && ClientUtils.isInBattle) {
            Item mainHandItem = player.getMainHandItem().getItem();
            if (mainHandItem instanceof ActiveItem) {
                if (!player.getCooldowns().isOnCooldown(mainHandItem)) {
                    guiGraphics.blit(cross, x - 5 + (width % 2 != 0 ? 1 : 0), y / 2 - 5 + (height % 2 != 0 ? 1 : 0),
                            0, 0,
                            16, 16, 16, 16);
                }
            }
        }

        int xOffset;
        int yOffset;

        if (mode == 0 && ClientUtils.isInBattle) {
            xOffset = -72;
            yOffset = -48;

            for (int i = 0; i < 3; i++) {
                ItemStack stack = player.getInventory().getItem(i + 3);
                Item item = stack.getItem();
                if (item instanceof ActiveItem) {
                    if (!player.getCooldowns().isOnCooldown(item)) {
                        guiGraphics.drawCenteredString(font, Te.s(getKeyName(i), ChatFormatting.AQUA),
                                x + xOffset, y / 2 + yOffset + 40 * i, 0);
                        guiGraphics.renderItem(stack, x + xOffset, y / 2 + yOffset + 40 * i);
                    }
                }
            }

            for (int i = 0; i < 3; i++) {
                ItemStack stack = player.getInventory().getItem(i + 6);
                Item item = stack.getItem();
                if (item instanceof ActiveItem) {
                    if (!player.getCooldowns().isOnCooldown(item)) {
                        guiGraphics.drawCenteredString(font, Te.s(getKeyName(i + 3), ChatFormatting.AQUA),
                                x + xOffset + 128, y / 2 + yOffset + 40 * i, 0);
                        guiGraphics.renderItem(stack, x + xOffset + 128, y / 2 + yOffset + 40 * i);
                    }
                }
            }
        }

        if (mode == 1 && ClientUtils.isInBattle) {
            xOffset = -68;
            yOffset = 8;
            for (int i = 0; i < 6; i++) {
                ItemStack stack = player.getInventory().getItem(i + 3);
                Item item = stack.getItem();
                if (item instanceof ActiveItem) {
                    if (!player.getCooldowns().isOnCooldown(item)) {
                        guiGraphics.drawCenteredString(font, Te.s(getKeyName(i), ChatFormatting.AQUA),
                                x + xOffset + 24 * i, y / 2 + yOffset, 0);
                        guiGraphics.renderItem(stack, x + xOffset + 24 * i, y / 2 + yOffset);
                    }
                }
            }
        }
    });

    public static String getKeyName(int keyIndex) {
        String name = keyMappings.get(keyIndex).getKey().getName();
        return name.substring(13).toUpperCase();
    }

    public static final Map<Integer, KeyMapping> keyMappings = ImmutableMap.of(
            0, KeyBoradInput.USE1,
            1, KeyBoradInput.USE2,
            2, KeyBoradInput.USE3,
            3, KeyBoradInput.USE4,
            4, KeyBoradInput.USE5,
            5, KeyBoradInput.USE6
    );
}

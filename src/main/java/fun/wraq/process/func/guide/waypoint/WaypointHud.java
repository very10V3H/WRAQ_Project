/** AI-Generated, 2026-05-17 */
package fun.wraq.process.func.guide.waypoint;

import com.lootbeams.ClientSetup;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.joml.Vector3f;

import java.util.Comparator;
import java.util.List;

public class WaypointHud {

    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font font = mc.font;

    public static final IGuiOverlay WAYPOINT_HUD = ((gui, guiGraphics, partialTick, width, height) -> {
        LocalPlayer player = mc.player;
        if (player == null || mc.screen != null) return;

        List<WaypointData> allWaypoints = WaypointClientManager.getWaypoints();
        if (allWaypoints.isEmpty()) return;

        String currentDim = player.level().dimension().location().toString();
        Vec3 playerPos = player.getPosition(partialTick);

        List<WaypointData> visible = allWaypoints.stream()
                .filter(wp -> wp.dimension.equals(currentDim))
                .sorted(Comparator.comparingDouble(wp -> wp.distanceTo(playerPos.x, playerPos.y, playerPos.z)))
                .toList();
        if (visible.isEmpty()) return;

        GuiGraphics offGraph = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        int count = 0;
        for (WaypointData wp : visible) {
            if (count >= 10) break;

            Vec3 worldPos = new Vec3(wp.x, wp.y + 0.5, wp.z);
            Vector3f screenPos = ClientSetup.worldToScreenSpace(worldPos, partialTick);

            if (screenPos.z() <= 0) continue;

            int sx = (int) screenPos.x();
            int sy = (int) screenPos.y();

            double distance = wp.distanceTo(playerPos.x, playerPos.y, playerPos.z);
            Component nameComp = Component.literal(wp.name).withStyle(ChatFormatting.GOLD);
            Component distComp = Component.literal(String.format("%.1f m", distance)).withStyle(ChatFormatting.WHITE);

            int nameWidth = font.width(nameComp);
            int distWidth = font.width(distComp);
            int maxWidth = Math.max(nameWidth, distWidth);

            int drawX = sx - maxWidth / 2;
            int drawY = sy - 14;

            offGraph.fill(drawX - 2, drawY - 1, drawX + maxWidth + 2, drawY + 21,
                    (int) (0.35f * 255) << 24);

            offGraph.drawString(font, nameComp,
                    drawX + (maxWidth - nameWidth) / 2, drawY, 0xFFFFFFFF);
            offGraph.drawString(font, distComp,
                    drawX + (maxWidth - distWidth) / 2, drawY + 10, 0xFFFFFFFF);

            count++;
        }
    });
}

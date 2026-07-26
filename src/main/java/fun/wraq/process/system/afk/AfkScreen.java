package fun.wraq.process.system.afk;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.afk.AfkOperationC2SPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;

/**
 * AFK挂机刷怪系统 GUI
 * AI-Generated, 2026-05-17
 */
@OnlyIn(Dist.CLIENT)
public class AfkScreen extends Screen {

    private static final ResourceLocation GUI_TEXTURE =
            new ResourceLocation(Utils.MOD_ID, "textures/gui/forge_old.png");
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Font font = mc.font;

    private int page = 0;
    private static final int ITEMS_PER_PAGE = 12;
    private int totalPages;
    private List<Map<String, String>> mobTypeList;

    public AfkScreen() {
        super(Component.translatable("menu.afk"));
        this.mobTypeList = new ArrayList<>(ClientUtils.afkMobTypeList);
        this.totalPages = Math.max(1, (mobTypeList.size() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE);
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // 关闭按钮
        this.addRenderableWidget(Button.builder(Component.literal("×"), (btn) -> {
            this.minecraft.setScreen(null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(centerX + 136, centerY - 98).size(12, 12).build());

        // 上一页
        this.addRenderableWidget(Button.builder(Component.literal("←"), (btn) -> {
            if (page > 0) {
                page--;
                rebuildWidgets();
            }
        }).pos(centerX - 120, centerY + 82).size(20, 20).build());

        // 下一页
        this.addRenderableWidget(Button.builder(Component.literal("→"), (btn) -> {
            if (page < totalPages - 1) {
                page++;
                rebuildWidgets();
            }
        }).pos(centerX + 100, centerY + 82).size(20, 20).build());

        // 收获按钮
        this.addRenderableWidget(Button.builder(
                Component.literal("收取收益").withStyle(ChatFormatting.GREEN),
                (btn) -> ModNetworking.sendToServer(new AfkOperationC2SPacket(AfkOperationC2SPacket.OP_HARVEST))
        ).pos(centerX + 40, centerY - 60).size(60, 20).build());

        // 刷新按钮
        this.addRenderableWidget(Button.builder(
                Component.literal("刷新").withStyle(ChatFormatting.AQUA),
                (btn) -> ModNetworking.sendToServer(new AfkOperationC2SPacket(AfkOperationC2SPacket.OP_REFRESH))
        ).pos(centerX + 40, centerY - 34).size(60, 20).build());

        // 选择按钮 - 每页12个，4列3行
        for (int i = 0; i < ITEMS_PER_PAGE; i++) {
            int index = page * ITEMS_PER_PAGE + i;
            if (index >= mobTypeList.size()) break;

            int col = i % 4;
            int row = i / 4;
            int x = centerX - 130 + col * 68;
            int y = centerY - 72 + row * 44;

            Map<String, String> entry = mobTypeList.get(index);
            String mobId = entry.get("id");
            String mobName = entry.get("name");
            boolean isSelected = mobId.equals(ClientUtils.afkSelectedMobTypeId);

            Component label = Component.literal(isSelected ? "✓ " + mobName : mobName);
            if (isSelected) {
                label = label.copy().withStyle(ChatFormatting.GREEN);
            } else if (mobName.length() > 6) {
                label = Component.literal(mobName.substring(0, 5) + "..");
            }

            this.addRenderableWidget(Button.builder(label, (btn) -> {
                ModNetworking.sendToServer(new AfkOperationC2SPacket(mobId));
            }).pos(x, y).size(64, 20).build());
        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // 背景
        graphics.blit(GUI_TEXTURE, centerX - 150, centerY - 100, 0, 0, 300, 200, 300, 200);

        // 标题
        graphics.drawCenteredString(font, Component.literal("离线挂机扫荡").withStyle(ChatFormatting.GOLD),
                centerX, centerY - 88, 0);

        // 已选怪物信息
        String selectedName = "未选择";
        if (!ClientUtils.afkSelectedMobTypeId.isEmpty()) {
            for (Map<String, String> entry : mobTypeList) {
                if (entry.get("id").equals(ClientUtils.afkSelectedMobTypeId)) {
                    selectedName = entry.get("name");
                    break;
                }
            }
        }
        graphics.drawString(font, "扫荡目标: " + selectedName, centerX + 30, centerY - 80, 0xFFFFFF);

        // 时长信息
        long elapsedTicks = ClientUtils.afkServerTick - ClientUtils.afkLastHarvestTime;
        if (ClientUtils.afkLastHarvestTime > 0 && elapsedTicks > 0) {
            double elapsedSeconds = elapsedTicks / 20.0;
            double estimatedKills = elapsedSeconds * AfkSystem.KILLS_PER_SECOND;
            String timeStr;
            if (elapsedSeconds < 60) {
                timeStr = String.format("%.0f秒", elapsedSeconds);
            } else if (elapsedSeconds < 3600) {
                timeStr = String.format("%.1f分钟", elapsedSeconds / 60);
            } else {
                timeStr = String.format("%.2f小时", elapsedSeconds / 3600);
            }
            graphics.drawString(font, "挂机时长: " + timeStr, centerX + 30, centerY - 66, 0xAAAAAA);
            graphics.drawString(font, String.format("预计击杀: %.0f只", estimatedKills),
                    centerX + 30, centerY - 54, 0xAAAAAA);
        }

        // 页数
        graphics.drawCenteredString(font,
                Component.literal((page + 1) + " / " + totalPages).withStyle(ChatFormatting.WHITE),
                centerX, centerY + 90, 0);

        // 分类标签
        graphics.drawString(font, Component.literal("普通怪物").withStyle(ChatFormatting.WHITE),
                centerX - 128, centerY - 84, 0xFFFFFF);

        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

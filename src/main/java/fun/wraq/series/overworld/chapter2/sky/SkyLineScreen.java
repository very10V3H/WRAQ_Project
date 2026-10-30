package fun.wraq.series.overworld.chapter2.sky;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * AI-Generated, 2026-10-30
 * Sky-Line 配置界面。
 * 左侧为四个配置槽，右侧展示可放入的意志材料与对应属性。
 */
public class SkyLineScreen extends AbstractContainerScreen<SkyLineMenu> {

    private static final int OPTION_SIZE = 18;

    public SkyLineScreen(SkyLineMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 260;
        this.imageHeight = 166;
        this.inventoryLabelY = 72;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;
        guiGraphics.fill(x, y, x + imageWidth, y + imageHeight, 0xE0101820);
        guiGraphics.fill(x + 3, y + 3, x + imageWidth - 3, y + imageHeight - 3, 0xD0202C38);
        guiGraphics.fill(x + 38, y + 22, x + 138, y + 52, 0x80204050);
        // 右侧可选材料面板
        guiGraphics.fill(x + 210, y + 16, x + 252, y + imageHeight - 8, 0x60204050);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderOptions(guiGraphics, mouseX, mouseY);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    private void renderOptions(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = leftPos + 214;
        int y = topPos + 20;
        guiGraphics.drawString(font, "可选", x, y - 10, 0xFFB0D8E8);
        List<ItemStack> options = SkyLineData.getOptionItems();
        for (int i = 0; i < options.size(); i++) {
            ItemStack stack = options.get(i);
            int itemX = x + 2;
            int itemY = y + i * 20;
            guiGraphics.renderItem(stack, itemX, itemY);
            guiGraphics.renderItemDecorations(font, stack, itemX, itemY);
            if (mouseX >= itemX && mouseX < itemX + OPTION_SIZE
                    && mouseY >= itemY && mouseY < itemY + OPTION_SIZE) {
                guiGraphics.renderComponentTooltip(font, optionTooltip(stack), mouseX, mouseY);
            }
        }
        guiGraphics.drawString(font, "拖入左侧", x - 4, topPos + imageHeight - 28, 0xFFA0A0A0);
        guiGraphics.drawString(font, "配置槽", x - 4, topPos + imageHeight - 18, 0xFFA0A0A0);
    }

    private List<Component> optionTooltip(ItemStack stack) {
        List<Component> lines = new ArrayList<>();
        lines.add(stack.getHoverName().copy().withStyle(ChatFormatting.AQUA));
        if (stack.is(ModItems.PLAIN_RUNE.get())) {
            lines.add(Te.s("每个 +0.5% 生命回复", CustomStyle.styleOfLife));
        } else if (stack.is(ModItems.FOREST_RUNE.get())) {
            lines.add(Te.s("每个 +5 护甲 / +5 魔法抗性", CustomStyle.styleOfForest));
        } else if (stack.is(ModItems.LAKE_RUNE.get())) {
            lines.add(Te.s("每个 +5 技能急速 / +5 法力回复", CustomStyle.styleOfLake));
        } else if (stack.is(ModItems.VOLCANO_RUNE.get())) {
            lines.add(Te.s("每个 +2.5% 物理攻击 / 魔法攻击", CustomStyle.styleOfVolcano));
        }
        lines.add(Te.s("放入左侧配置槽后生效", ChatFormatting.GRAY));
        return lines;
    }
}

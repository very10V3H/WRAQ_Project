package fun.wraq.render.gui.blocks;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.blocks.blocks.brew.BrewingRecipe;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.Limit.ScreenCloseC2SPacket;
import fun.wraq.render.gui.blocks.BrewingMenu;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class BrewingScreen extends AbstractContainerScreen<fun.wraq.render.gui.blocks.BrewingMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Utils.MOD_ID, "textures/gui/brewing_gui.png");

    public BrewingScreen(BrewingMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float PartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        if (menu.isCrafting()) {
            guiGraphics.blit(TEXTURE, x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());
        }
    }

    public static List<Component> components = new ArrayList<>() {{
        add(Component.literal("维瑞阿契酿造").withStyle(CustomStyle.styleOfBrew));
        add(Component.literal("1.配方").withStyle(ChatFormatting.AQUA));
        add(Component.literal(" 注意，所需材料放置于右侧两个材料槽位，两个槽位是有序的，不同顺序可能会影响产物").withStyle(CustomStyle.styleOfBrew));
        if (BrewingRecipe.recipeList.isEmpty()) BrewingRecipe.setRecipeList();
        for (BrewingRecipe brewingRecipe : BrewingRecipe.recipeList) {
            if (BrewingRecipe.recipeList.indexOf(brewingRecipe) == 13) {
                add(Component.literal(" 以下为进阶酿造配方，需达到").withStyle(CustomStyle.styleOfSakura).
                        append(Component.literal("酿造大师").withStyle(ChatFormatting.LIGHT_PURPLE)).
                        append(Component.literal("等阶方可进行酿造").withStyle(CustomStyle.styleOfBrew)));
            }
            int m1Count = brewingRecipe.material1.getCount();
            int m2Count = brewingRecipe.material2.getCount();
            String m1CountString = m1Count > 1 ? "*" + m1Count : "";
            String m2CountString = m2Count > 1 ? "*" + m2Count : "";
            add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                    append(brewingRecipe.output.getDisplayName()).
                    append(Component.literal(" = ").withStyle(CustomStyle.styleOfBrew)).
                    append(brewingRecipe.material1.getDisplayName()).
                    append(Component.literal(m1CountString).withStyle(CustomStyle.styleOfBrew)).
                    append(Component.literal(" + ").withStyle(CustomStyle.styleOfBrew)).
                    append(brewingRecipe.material2.getDisplayName()).
                    append(Component.literal(m2CountString).withStyle(CustomStyle.styleOfBrew)));
        }
        add(Component.literal("2.浓缩/稳定/膨化").withStyle(ChatFormatting.AQUA));
        add(Component.literal(" 在材料槽位放置").withStyle(ChatFormatting.WHITE).
                append(ModItems.Concentrater.get().getDefaultInstance().getDisplayName()).
                append(Component.literal(" / ").withStyle(CustomStyle.styleOfBrew)).
                append(ModItems.Stabilizer.get().getDefaultInstance().getDisplayName()).
                append(Component.literal(" / ").withStyle(CustomStyle.styleOfBrew)).
                append(ModItems.Splasher.get().getDefaultInstance().getDisplayName()));
        add(Component.literal(" 注意，浓缩与稳定只能使用没有被浓缩/稳定后的药水").withStyle(CustomStyle.styleOfBrew));
        add(Component.literal(" 酿造喷溅药水需要达到").withStyle(CustomStyle.styleOfBrew).
                append(Component.literal("酿造大师等阶").withStyle(ChatFormatting.LIGHT_PURPLE)));
        add(Component.literal("3.固化材料").withStyle(ChatFormatting.AQUA));
        add(Component.literal(" 在酿造槽放置对应根源，在右侧材料槽放置").withStyle(ChatFormatting.WHITE).
                append(ModItems.Solidifier.get().getDefaultInstance().getDisplayName()));
        add(Component.literal("4.酿造等阶说明").withStyle(ChatFormatting.AQUA));
        add(Component.literal(" 0.酿造初识").withStyle(ChatFormatting.GRAY));
        add(Component.literal(" 1.酿造入门").withStyle(ChatFormatting.GREEN).
                append(Component.literal(" 任意4条基础经验高于50").withStyle(CustomStyle.styleOfBrew)));
        add(Component.literal(" 2.酿造初级").withStyle(ChatFormatting.BLUE).
                append(Component.literal(" 任意5条基础经验高于100").withStyle(CustomStyle.styleOfBrew)));
        add(Component.literal(" 3.酿造中级").withStyle(ChatFormatting.YELLOW).
                append(Component.literal(" 任意5条基础经验高于200").withStyle(CustomStyle.styleOfBrew)));
        add(Component.literal(" 4.酿造高级").withStyle(ChatFormatting.AQUA).
                append(Component.literal(" 任意6条基础经验高于400").withStyle(CustomStyle.styleOfBrew)));
        add(Component.literal(" 5.酿造学士").withStyle(ChatFormatting.GOLD).
                append(Component.literal(" 所有基础酿造经验高于800").withStyle(CustomStyle.styleOfBrew)));
        add(Component.literal(" 6.酿造大师").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal(" 所有基础酿造经验高于1500").withStyle(CustomStyle.styleOfBrew)));
    }};

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);

        Minecraft mc = Minecraft.getInstance();
        Font fontRenderer = mc.font;
        int X = this.width / 2;
        int Y = this.height / 2;

        int offsetX = -86;
        int offsetY = -92;
        guiGraphics.drawString(fontRenderer, Component.literal("光标移动至此处可查看简易配方与教程").withStyle(ChatFormatting.WHITE), X + offsetX, Y + offsetY, 0);
        if (mouseX > X + offsetX && mouseX < X + 56 && mouseY > Y + offsetY - 6 && mouseY < Y + offsetY + 12) {
            guiGraphics.renderComponentTooltip(fontRenderer, components, mouseX, mouseY);
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        ModNetworking.sendToServer(new ScreenCloseC2SPacket());
    }

}

package fun.wraq.render.gui.illustrate;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.render.gui.illustrate.mobinfo.MobInfoGui;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class Illustrate extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/illustrate.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private int type = 0;

    public Illustrate(boolean p_96308_, int type) {
        super(p_96308_ ? Component.translatable("menu.illustrate") : Component.translatable("menu.illustrate0"));
        this.showPauseMenu = p_96308_;
        this.type = type;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
        }
    }

    private void createMenu() {
        int X = this.width / 2;
        int Y = this.height / 2;
        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) {
                page--;
            }
        }).pos(X - 39 + 5, Y - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < ((getItems().size() - 1) / 45)) {
                page++;
            }
        }).pos(X + 20 + 5, Y - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X + 136, Y - 98).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.literal("怪物及其掉落"), (p_280814_) -> {
            this.minecraft.setScreen(new MobInfoGui());
        }).pos(X + 150 - 88, Y - 98 - 20).size(28 * 3, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("武器"), (p_280814_) -> {
            this.type = 0;
            this.page = 0;
        }).pos(X + 150, Y - 98).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("防具"), (p_280814_) -> {
            this.type = 1;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("饰品"), (p_280814_) -> {
            this.type = 2;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 2).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("符石"), (p_280814_) -> {
            this.type = 7;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 3).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("宝石"), (p_280814_) -> {
            this.type = 6;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 4).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("制式"), (p_280814_) -> {
            this.type = 3;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 5).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("酿造"), (p_280814_) -> {
            this.type = 4;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 6).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("材料"), (p_280814_) -> {
            this.type = 5;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 7).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Component.literal("镐子"), (p_280814_) -> {
            this.type = 17;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 8).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("近战", CustomStyle.styleOfPower), (p_280814_) -> {
            this.type = 8;
            this.page = 0;
        }).pos(X - 178, Y - 98).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("弓", CustomStyle.styleOfFlexible), (p_280814_) -> {
            this.type = 9;
            this.page = 0;
        }).pos(X - 178, Y - 98 + 20).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("法杖", CustomStyle.styleOfMana), (p_280814_) -> {
            this.type = 10;
            this.page = 0;
        }).pos(X - 178, Y - 98 + 20 * 2).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("法术", CustomStyle.styleOfMana), (p_280814_) -> {
            this.type = 16;
            this.page = 0;
        }).pos(X - 178, Y - 98 + 20 * 3).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("副手", CustomStyle.styleOfGold), (p_280814_) -> {
            this.type = 11;
            this.page = 0;
        }).pos(X - 178, Y - 98 + 20 * 4).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("头盔", CustomStyle.styleOfStone), (p_280814_) -> {
            this.type = 12;
            this.page = 0;
        }).pos(X - 178, Y - 98 + 20 * 5).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("胸甲", CustomStyle.styleOfStone), (p_280814_) -> {
            this.type = 13;
            this.page = 0;
        }).pos(X - 178, Y - 98 + 20 * 6).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("护腿", CustomStyle.styleOfStone), (p_280814_) -> {
            this.type = 14;
            this.page = 0;
        }).pos(X - 178, Y - 98 + 20 * 7).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("靴子", CustomStyle.styleOfStone), (p_280814_) -> {
            this.type = 15;
            this.page = 0;
        }).pos(X - 178, Y - 98 + 20 * 8).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("纪念", CustomStyle.styleOfGold), (p_280814_) -> {
            this.type = 18;
            this.page = 0;
        }).pos(X - 178, Y - 98 + 20 * 9).size(28, 16).build());
        this.addRenderableWidget(Button.builder(Te.s("新品", CustomStyle.styleOfRed), (p_280814_) -> {
            this.type = 19;
            this.page = 0;
        }).pos(X + 150, Y - 98 + 20 * 9).size(28, 16).build());
    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100,
                0, 0, 300, 200, textureWidth, textureHeight);
        int xOffset = -28;
        List<Item> list = getItems();
        sameModule(list, guiGraphics, x, y, xOffset);
        guiGraphics.drawCenteredString(fontRenderer, Te.s("" + (page + 1)),
                this.width / 2 + 5, this.height / 2 - 22 + 105, 0);
        guiGraphics.drawCenteredString(fontRenderer,
                Te.s("共" + ((list.size() - 1) / 45 + 1) + "页 " + (list.size()) + "件物品"),
                this.width / 2 + 80, this.height / 2 + 83, 0);
        super.render(p_96310_, x, y, v);
    }

    private List<Item> getItems() {
        List<Item> list = new ArrayList<>();
        switch (type) {
            case 0 -> list = Utils.weaponList;
            case 1 -> list = Utils.armorList;
            case 2 -> list = Utils.curiosList;
            case 3 -> list = Utils.customizedList;
            case 4 -> list = Display.getBrewingList();
            case 5 -> list = Display.materialList;
            case 6 -> list = Display.gemList;
            case 7 -> list = Display.runeList;
            case 8 -> list = Display.swordList;
            case 9 -> list = Display.bowList;
            case 10 -> list = Display.sceptreList;
            case 11 -> list = Display.offHandList;
            case 12 -> list = Display.helmetList;
            case 13 -> list = Display.chestList;
            case 14 -> list = Display.leggingsList;
            case 15 -> list = Display.bootsList;
            case 16 -> list = Display.powerList;
            case 17 -> list = Display.pickAxeList;
            case 18 -> list = Display.getSouvenirsList();
            case 19 -> list = Display.getNewItemList();
        }
        return list;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static final String DISPLAY_FLAG = "illustrate_display_flag";
    public void sameModule(List<Item> list, GuiGraphics guiGraphics, int x, int y, int xOffset) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (page * 45 + i * 9 + j < list.size()) {
                    ItemStack itemStack = list.get(page * 45 + i * 9 + j).getDefaultInstance();
                    Item item = itemStack.getItem();
                    itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                    if (!Screen.hasControlDown()
                            && (Utils.mainHandTag.containsKey(item) || Utils.armorTag.containsKey(item)
                            || Utils.offHandTag.containsKey(item) || item instanceof WraqCurios)) {
                        itemStack.getOrCreateTagElement(Utils.MOD_ID).putBoolean(DISPLAY_FLAG, true);
                        if (!Utils.offHandTag.containsKey(item) && !(item instanceof WraqCurios)) {
                            ForgeEquipUtils.setForgeQualityOnEquip(itemStack,
                                    ClientUtils.clientPlayerTick / 20 % 13);
                        }
                        Compute.forgingHoverName(itemStack);
                    }
                    guiGraphics.renderItem(itemStack,
                            this.width / 2 - 100 + j * 30 + xOffset, this.height / 2 - 73 + 32 * i);
                    if (x > this.width / 2 - 100 + j * 30 + xOffset && x < this.width / 2 - 100 + 16 + j * 30 + xOffset
                            && y > this.height / 2 - 73 + 32 * i && y < this.height / 2 - 73 + 32 * i + 16) {
                        guiGraphics.renderTooltip(font, itemStack, x, y);
                    }
                }
            }
        }
    }
}

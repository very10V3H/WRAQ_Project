package fun.wraq.process.system.skill.skillv2;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.skill.skillv2.network.SkillV2PlayerTryToChooseProfessionTypeC2SPacket;
import fun.wraq.process.system.skill.skillv2.network.SkillV2PlayerTryToEquipSkillC2SPacket;
import fun.wraq.process.system.skill.skillv2.network.SkillV2PlayerTryToUpgradeSkillC2SPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SkillV2Screen extends Screen {

    public static final Minecraft mc = Minecraft.getInstance();
    public static int professionType = 0;

    public SkillV2Screen(int professionType) {
        super(Te.s("menu.skill_v2_screen"));
        this.professionType = professionType;
    }

    protected void init() {
        this.createMenu();
    }

    int xDifference = 48;
    int yDifference = 48;

    int xOffset = -150;
    int yOffset = -106;

    private void createMenu() {
        int X = this.width / 2;
        int Y = this.height / 2;
        if (this.minecraft == null) return;

        List<SkillV2> skillV2List = SkillV2.getSkillV2ListByProfession(professionType);
        for (int i = 0 ; i < 5 ; i ++) {
            int skillType = i;
            List<SkillV2> typeSkillV2List = skillV2List
                    .stream().filter(skillV2 -> skillV2.skillType == skillType).toList();
            for (int j = 0 ; j < typeSkillV2List.size() ; j ++) {
                int serial = j;

                this.addRenderableWidget(Button.builder(Te.s("装备"), (p_280814_) -> {
                    ModNetworking.sendToServer(
                            new SkillV2PlayerTryToEquipSkillC2SPacket(SkillV2.clientProfessionType, skillType, serial));
                }).pos(X + xOffset + j * xDifference, Y + yOffset + i * yDifference).size(32, 32).build());

                this.addRenderableWidget(Button.builder(Te.s("升级"), (p_280814_) -> {
                    ModNetworking.sendToServer(
                            new SkillV2PlayerTryToUpgradeSkillC2SPacket(SkillV2.clientProfessionType, skillType, serial));
                }).pos(X + xOffset + j * xDifference + 4, Y + yOffset + 34 + i * yDifference).size(24, 12).build());
            }
        }

        this.addRenderableWidget(Button.builder(Te.s("战士", CustomStyle.styleOfPower), (p_280814_) -> {
            ModNetworking.sendToServer(
                    new SkillV2PlayerTryToChooseProfessionTypeC2SPacket(0));
        }).pos(X + xOffset, Y + yOffset - 16).size(24, 12).build());

        this.addRenderableWidget(Button.builder(Te.s("游侠", CustomStyle.styleOfFlexible), (p_280814_) -> {
            ModNetworking.sendToServer(
                    new SkillV2PlayerTryToChooseProfessionTypeC2SPacket(1));
        }).pos(X + xOffset + xDifference, Y + yOffset - 16).size(24, 12).build());

        this.addRenderableWidget(Button.builder(Te.s("法师", CustomStyle.styleOfMana), (p_280814_) -> {
            ModNetworking.sendToServer(
                    new SkillV2PlayerTryToChooseProfessionTypeC2SPacket(2));
        }).pos(X + xOffset + xDifference * 2, Y + yOffset - 16).size(24, 12).build());
    }

    public void tick() {
        super.tick();
        if (SkillV2.screenRefreshFlag) {
            SkillV2.screenRefreshFlag = false;
            mc.setScreen(new SkillV2Screen(SkillV2.clientProfessionType));
        }
    }

    public void render(@NotNull GuiGraphics graphics, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        int X = this.width / 2;
        int Y = this.height / 2;

        List<SkillV2> skillV2List = SkillV2.getSkillV2ListByProfession(professionType);
        for (int i = 0 ; i < 5 ; i ++) {
            int skillType = i;
            List<SkillV2> typeSkillV2List = skillV2List
                    .stream().filter(skillV2 -> skillV2.skillType == skillType).toList();
            for (int j = 0 ; j < typeSkillV2List.size() ; j ++) {
                int serial = j;
                SkillV2 skillV2 = typeSkillV2List.get(serial);
                if (SkillV2.clientCurrentSkillSerial.get(skillType) == j) {
                    guiGraphics.blit(skillV2.getTexture1(), X + xOffset + j * xDifference, Y + yOffset + i * yDifference,
                            0, 0, 32,  32, 32, 32);
                } else {
                    guiGraphics.blit(skillV2.getTexture0(), X + xOffset + j * xDifference, Y + yOffset + i * yDifference,
                            0, 0, 32,  32, 32, 32);
                }
                List<Integer> skillLevelList;
                switch (professionType) {
                    default -> skillLevelList = SkillV2.clientSwordSkillLevel;
                    case 1 -> skillLevelList = SkillV2.clientBowSkillLevel;
                    case 2 -> skillLevelList = SkillV2.clientManaSkillLevel;
                }

                int skillIndex = skillV2List.indexOf(skillV2);
                if (skillIndex < skillLevelList.size()) {
                    int skillLevel = skillLevelList.get(skillIndex);

                    guiGraphics.drawCenteredString(font, Te.s(String.valueOf(skillLevel)),
                            X + xOffset + j * xDifference + 28, Y + yOffset + i * yDifference + 24, 0);
                    if (x >= X + xOffset + j * xDifference && x <= X + xOffset + j * xDifference + 32
                            && y >= Y + yOffset + i * yDifference && y <= Y + yOffset + i * yDifference + 32) {
                        guiGraphics.renderComponentTooltip(font, skillV2.getSkillDescription(skillLevel), x, y);
                    }
                }
            }
        }

        this.renderables.forEach(renderable -> {
            if (renderable instanceof Button button) {
                if (!button.getMessage().getString().contains("装备")) {
                    renderable.render(graphics, x, y, v);
                }
            } else {
                renderable.render(graphics, x, y, v);
            }
        });
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

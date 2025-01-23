package fun.wraq.process.system.skill.skillv2;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.hud.main.QuickUseHud;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.List;

public class SkillV2Hud {
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font font = mc.font;
    public static final IGuiOverlay SKILL_V2_HUD = ((gui, poseStack, partialTick, width, height) -> {

        if (ClientUtils.isInBattle || SkillV2.clientLastReleaseTick + 100 > ClientUtils.serverTick) {
            int x = width / 2;
            int y = height / 2;

            int xOffset = 2;
            int yOffset = -110;
            int yDifference = 32;
            GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

            int professionType = SkillV2.clientProfessionType;
            List<SkillV2> skillV2List = SkillV2.getSkillV2ListByProfession(professionType);
            for (int i = 0; i < 5; i++) {
                int skillType = i;
                int serial = SkillV2.clientCurrentSkillSerial.get(skillType);
                List<SkillV2> typeSkillV2List = skillV2List
                        .stream().filter(skillV2 -> skillV2.skillType == skillType).toList();
                SkillV2 skillV2 = typeSkillV2List.get(serial);

                Style keyStyle;
                ResourceLocation skillTexture;
                ResourceLocation cdTexture;
                if (SkillV2.clientLeftCooldownTick.containsKey(skillType)
                        && SkillV2.clientLeftCooldownTick.get(skillType) > 0
                        && SkillV2.clientOriginCooldownTick.containsKey(skillType)) {
                    int originCooldownTick = SkillV2.clientOriginCooldownTick.get(skillType);
                    int leftCooldownTick = Math.max(0, SkillV2.clientLeftCooldownTick.get(skillType));
                    int textureIndex = leftCooldownTick * 12 / originCooldownTick;

                    skillTexture = skillV2.getTexture0();
                    cdTexture = new ResourceLocation(Utils.MOD_ID,
                            "textures/hud/new_skill_cd" + textureIndex + ".png");
                    keyStyle = CustomStyle.styleOfStone;
                } else {
                    skillTexture = skillV2.getTexture1();
                    cdTexture = new ResourceLocation(Utils.MOD_ID,
                            "textures/hud/new_skill_cd0.png");
                    keyStyle = CustomStyle.styleOfWorld;
                }

                if (skillV2.manaCost > Mana.getCurrentMana()) {
                    skillTexture = skillV2.getTexture0();
                    keyStyle = CustomStyle.styleOfStone;
                }

                guiGraphics.blit(skillTexture, xOffset, y + yOffset + i * yDifference,
                        0, 0, 24, 24, 24, 24);
                guiGraphics.blit(cdTexture, 0, y + yOffset + i * yDifference - 2,
                        0, 0, 28, 28, 28, 28);

                if (i > 0) {
                    guiGraphics.drawCenteredString(font, Te.s(QuickUseHud.getKeyName(i - 1), keyStyle),
                            xOffset + 24, y + yOffset + i * yDifference + 20, 0);
                }
            }
            guiGraphics.drawString(font,
                    Te.s("[" + QuickUseHud.getKeyName(6) + "]配置"),
                    xOffset, y + yOffset + 5 * yDifference + 4, 0);

/*            List<Integer> skillLevelList;
            switch (professionType) {
                default -> skillLevelList = SkillV2.clientSwordSkillLevel;
                case 1 -> skillLevelList = SkillV2.clientBowSkillLevel;
                case 2 -> skillLevelList = SkillV2.clientManaSkillLevel;
            }
            int skillIndex = skillV2List.indexOf(skillV2);
            if (skillIndex < skillLevelList.size()) {
                int skillLevel = skillLevelList.get(skillIndex);
                guiGraphics.drawCenteredString(font, Te.s(String.valueOf(skillLevel)), xOffset,
                        y + yOffset + i * yDifference + 24, 0);
            }*/
        }
    });
}

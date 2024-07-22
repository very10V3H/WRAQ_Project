package com.very.wraq.render.gui.team;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.TeamPackets.InstanceChooseC2SPacket;
import com.very.wraq.networking.misc.TeamPackets.QuickChooseC2SPacket;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class InstanceScreen extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/teamscreen.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;
    private EditBox nameSearchBox;

    public InstanceScreen(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.teamscreen") : Component.translatable("menu.teamscreen6"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
            this.nameSearchBox = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 92, 200, 20, Component.translatable("teamsearch.search"));
            this.addWidget(this.nameSearchBox);
        }
    }

    private void createMenu() {

        this.addRenderableWidget(Button.builder(Component.translatable("快捷挑战"), (p_280814_) -> {
            ModNetworking.sendToServer(new QuickChooseC2SPacket());
        }).pos(this.width / 2 - 85, this.height / 2 + 70).size(48, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (this.page < Utils.instanceList.size() / 4) this.page++;
        }).pos(this.width / 2 + 30, this.height / 2 + 70).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (this.page > 0) this.page--;
        }).pos(this.width / 2 - 30, this.height / 2 + 70).size(20, 20).build());

        this.addRenderableWidget(Button.builder(Component.translatable("返回"), (p_280814_) -> {
            this.minecraft.setScreen(new TeamManageScreen(true));
        }).pos(this.width / 2 + 108, this.height / 2 - 92).size(32, 16).build());

        for (int i = 0; i < 4; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("炼狱").withStyle(ChatFormatting.RED), (p_280814_) -> {
                ModNetworking.sendToServer(new InstanceChooseC2SPacket(page * 4 + finalI, 2));
            }).pos(this.width / 2 + 108, this.height / 2 - 62 + 32 * i).size(32, 20).build());

            this.addRenderableWidget(Button.builder(Component.translatable("困难").withStyle(ChatFormatting.LIGHT_PURPLE), (p_280814_) -> {
                ModNetworking.sendToServer(new InstanceChooseC2SPacket(page * 4 + finalI, 1));
            }).pos(this.width / 2 + 68, this.height / 2 - 62 + 32 * i).size(32, 20).build());

            this.addRenderableWidget(Button.builder(Component.translatable("一般").withStyle(ChatFormatting.GREEN), (p_280814_) -> {
                ModNetworking.sendToServer(new InstanceChooseC2SPacket(page * 4 + finalI, 0));
            }).pos(this.width / 2 + 28, this.height / 2 - 62 + 32 * i).size(32, 20).build());

        }

    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("搜索副本:").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK),
                this.width / 2 - 128, this.height / 2 - 88, 0);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.valueOf(this.page + 1)).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLACK),
                this.width / 2 + 10, this.height / 2 + 74, 0);

        for (int i = 0; i < 4; i++) {
            if (page * 4 + i < Utils.instanceList.size()) {
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("副本: ").withStyle(ChatFormatting.BLACK).
                                append(Utils.instanceList.get(page * 4 + i).getInstanceName()),
                        this.width / 2 - 100, this.height / 2 - 64 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("人数需求: ").withStyle(ChatFormatting.BLACK).
                                append(String.valueOf(Utils.instanceList.get(page * 4 + i).getLeastPeopleNum())),
                        this.width / 2 - 20, this.height / 2 - 64 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("地点: ").withStyle(ChatFormatting.BLACK).
                                append(Utils.instanceList.get(page * 4 + i).getPosition()),
                        this.width / 2 - 100, this.height / 2 - 48 + i * 32, 0);
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("等级需求: ").withStyle(ChatFormatting.BLACK).
                                append(Component.literal(String.valueOf(Utils.instanceList.get(page * 4 + i).getLevelRequire())).
                                        withStyle(Utils.levelStyleList.get(Utils.instanceList.get(page * 4 + i).getLevelRequire() / 25))),
                        this.width / 2 - 20, this.height / 2 - 48 + i * 32, 0);
            }
        }

        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);

        int XOffset1 = -150;
        int XOffset2 = 150;
        int YOffset = -100;

        if (page == 0) {
            YOffset += 32;
            if (x > this.width / 2 + XOffset1 && x < this.width / 2 + XOffset2 && y > this.height / 2 + YOffset && y < this.height / 2 + YOffset + 32) {
                List<Component> components = new ArrayList<>();
                components.add(Component.literal("副本：普莱尼").withStyle(CustomStyle.styleOfPlain));
                components.add(Component.literal(" 普莱尼").withStyle(CustomStyle.styleOfPlain).
                        append(Component.literal("拥有两个交替施放的技能:").withStyle(ChatFormatting.WHITE)));

                components.add(Component.literal(" 1.对半径6格以内的玩家造成").withStyle(ChatFormatting.WHITE).
                        append(Compute.AttributeDescription.ManaDamageValue("200 * 难度系数")).
                        append(Component.literal("，但对半径6格以外的玩家治疗").withStyle(ChatFormatting.WHITE)).
                        append(Compute.AttributeDescription.Health("100 * 难度系数")).
                        append(Component.literal("，同时其回复与造成伤害相同的生命值。").withStyle(ChatFormatting.WHITE)));

                components.add(Component.literal(" 2.对半径6格以外的玩家造成").withStyle(ChatFormatting.WHITE).
                        append(Compute.AttributeDescription.ManaDamageValue("125 * 难度系数")).
                        append(Component.literal("，但对半径6格以内的玩家治疗").withStyle(ChatFormatting.WHITE)).
                        append(Compute.AttributeDescription.Health("75 * 难度系数")).
                        append(Component.literal("，同时其回复与造成伤害相同的生命值。").withStyle(ChatFormatting.WHITE)));

                components.add(Component.literal(" 技能每2.5s施放一次，并且交替施放。").withStyle(ChatFormatting.WHITE));
                guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
            }
        }

        if (page == 1) {
            YOffset += 32;
            YOffset += 32;
            if (x > this.width / 2 + XOffset1 && x < this.width / 2 + XOffset2 && y > this.height / 2 + YOffset && y < this.height / 2 + YOffset + 32) {
                List<Component> components = new ArrayList<>();
                components.add(Component.literal("副本：冰霜骑士").withStyle(CustomStyle.styleOfIce));
                components.add(Component.literal(" 注意！在寒冷的环境或水中，你将会逐渐进入").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("寒冷状态").withStyle(CustomStyle.styleOfIce)));
                components.add(Component.literal(" 进入").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("寒冷状态").withStyle(CustomStyle.styleOfIce)).
                        append(Component.literal("后，你对").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("冰霜骑士").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("造成的所有伤害值减半").withStyle(ChatFormatting.WHITE)));
                components.add(Component.literal(" 推荐使用").withStyle(ChatFormatting.WHITE).
                        append(ModItems.magmaSoul.get().getDefaultInstance().getDisplayName()).
                        append(Component.literal("来抵御寒冷。").withStyle(ChatFormatting.WHITE)));
                components.add(Component.literal(" 你也可以前往").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("冰原").withStyle(CustomStyle.styleOfIce)).
                        append(Component.literal("击杀").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("冰原猎手").withStyle(CustomStyle.styleOfIce)).
                        append(Component.literal("制作冰原猎手装备来抵御寒冷。").withStyle(ChatFormatting.WHITE)));
                guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
            }

            YOffset += 32;
            if (x > this.width / 2 + XOffset1 && x < this.width / 2 + XOffset2 && y > this.height / 2 + YOffset && y < this.height / 2 + YOffset + 32) {
                List<Component> components = new ArrayList<>();
                components.add(Component.literal("副本：旧世复生魔王").withStyle(CustomStyle.styleOfBloodMana));
                components.add(Component.literal(" 旧世复生魔王").withStyle(CustomStyle.styleOfBloodMana).
                        append(Component.literal("对魔法的渴望达到了极点!").withStyle(ChatFormatting.WHITE)));
                components.add(Component.literal(" ·魔王会根据你的当前").withStyle(ChatFormatting.WHITE).
                        append(Compute.AttributeDescription.MaxMana("")).
                        append(Component.literal("来提升技能造成的").withStyle(ChatFormatting.WHITE)).
                        append(Compute.AttributeDescription.ManaDamageValue("")));
                guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
            }

            YOffset += 32;
            if (x > this.width / 2 + XOffset1 && x < this.width / 2 + XOffset2 && y > this.height / 2 + YOffset && y < this.height / 2 + YOffset + 32) {
                List<Component> components = new ArrayList<>();
                components.add(Component.literal("副本：尘月宫").withStyle(CustomStyle.styleOfMoon));
                components.add(Component.literal(" 在").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("尘月宫").withStyle(CustomStyle.styleOfMoon)).
                        append(Component.literal("中:需要你对距离有所把控").withStyle(ChatFormatting.WHITE)));
                components.add(Component.literal(" ·明镜:").withStyle(CustomStyle.styleOfMoon).
                        append(Component.literal("距其距离大于6格的玩家对其造成的伤害减半").withStyle(ChatFormatting.WHITE)));
                components.add(Component.literal(" ·天镜:").withStyle(CustomStyle.styleOfMoon1).
                        append(Component.literal("距其距离小于6格的玩家对其造成的伤害减半").withStyle(ChatFormatting.WHITE)));
                components.add(Component.literal(" ·在").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("尘月宫").withStyle(CustomStyle.styleOfMoon)).
                        append(Component.literal("意外失足，将会受到来自").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("尘月").withStyle(CustomStyle.styleOfMoon)).
                        append(Component.literal("的神秘力量，将你牵引会月宫中心。").withStyle(ChatFormatting.WHITE)));
                components.add(Component.literal(" ·若天镜与明镜的生命值百分比差值超过50%，则其会回复全部").withStyle(ChatFormatting.WHITE).
                        append(Compute.AttributeDescription.Health("")).
                        append(Component.literal("并对玩家造成大量").withStyle(ChatFormatting.WHITE)).
                        append(Compute.AttributeDescription.ManaDamageValue("")));
                guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
            }
        }

        if (page == 2) {
            YOffset += 32;
            if (x > this.width / 2 + XOffset1 && x < this.width / 2 + XOffset2 && y > this.height / 2 + YOffset && y < this.height / 2 + YOffset + 32) {
                List<Component> components = new ArrayList<>();
                components.add(Component.literal("副本：新世禁法魔物").withStyle(CustomStyle.styleOfBloodMana));
                components.add(Component.literal(" 想要召唤出新世禁法与旧世魔力的混合产物 - ").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("新世禁法魔物").withStyle(CustomStyle.styleOfBloodMana)).
                        append(Component.literal("你需要在副本场地将旧世复生魔王的").withStyle(ChatFormatting.WHITE)).
                        append(Compute.AttributeDescription.Health("")).
                        append(Component.literal("削减至10%，随后将").withStyle(ChatFormatting.WHITE)).
                        append(ModItems.IntensifiedDevilBlood.get().getDefaultInstance().getDisplayName()).
                        append(Component.literal("丢弃出去，让旧世复生魔王吸收新世禁法，召唤:").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("新世禁法魔物").withStyle(CustomStyle.styleOfBloodMana)));
                components.add(Component.literal(" ·新世禁法魔物每当血量下降至75%/50%/25%时，会在场地四周召唤").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("禁法方块").withStyle(CustomStyle.styleOfBloodMana)));
                components.add(Component.literal(" ·每个").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("禁法方块").withStyle(CustomStyle.styleOfBloodMana)).
                        append(Component.literal("每秒会为魔物回复").withStyle(ChatFormatting.WHITE)).
                        append(Compute.AttributeDescription.MaxHealth("1%")));
                components.add(Component.literal(" ·你可以通过挖掘").withStyle(ChatFormatting.WHITE).
                        append(Component.literal("禁法方块").withStyle(CustomStyle.styleOfBloodMana)).
                        append(Component.literal("来获取").withStyle(ChatFormatting.WHITE)).
                        append(ModItems.DevilBlood.get().getDefaultInstance().getDisplayName()).
                        append(Component.literal("(10%)以及新世禁法魔物系列锻造装备锻造图。").withStyle(ChatFormatting.WHITE)));
                guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
            }
            YOffset += 32;
            if (x > this.width / 2 + XOffset1 && x < this.width / 2 + XOffset2 && y > this.height / 2 + YOffset && y < this.height / 2 + YOffset + 32) {
                List<Component> components = new ArrayList<>();
                components.add(Component.literal("副本：暗黑城堡").withStyle(CustomStyle.styleOfCastle));
                components.add(Component.literal(" 暗黑城堡").withStyle(CustomStyle.styleOfCastle).
                        append(Component.literal("拥有8个房间，北4，南4，其中:").withStyle(ChatFormatting.WHITE)));
                components.add(Component.literal(" ·北部的每个房间有四只怪物，其").withStyle(ChatFormatting.WHITE).
                        append(Compute.AttributeDescription.ManaDefence("")).
                        append(Component.literal("较低，推荐法师清理。").withStyle(ChatFormatting.WHITE)));
                components.add(Component.literal(" ·南部的每个房间只有一只怪物，其").withStyle(ChatFormatting.WHITE).
                        append(Compute.AttributeDescription.Defence("")).
                        append(Component.literal("较低，推荐战士与弓箭手清理。").withStyle(ChatFormatting.WHITE)));
                components.add(Component.literal(" ·在清理完所有房间后，请回到城堡大门，迎接回防的城堡守备军!").withStyle(ChatFormatting.WHITE));
                guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
            }

        }

        this.nameSearchBox.render(guiGraphics, x, y, v);
        super.render(p_96310_, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

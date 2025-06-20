package fun.wraq.render.gui.illustrate.mobinfo;

import com.mojang.blaze3d.systems.RenderSystem;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.events.mob.jungle.JungleMobSpawn;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.teamInstance.NewTeamInstanceHandler;
import fun.wraq.render.gui.illustrate.Illustrate;
import fun.wraq.render.toolTip.CustomStyle;
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
import java.util.Comparator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class MobInfoGui extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/tower.png");
    private final boolean showPauseMenu;
    private EditBox nameSearchBox;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;
    private int page = 0;

    public MobInfoGui() {
        super(Component.translatable("menu.mob_info"));
        this.showPauseMenu = true;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.createMenu();
            this.nameSearchBox = new EditBox(this.font, this.width / 2 - 120, this.height / 2 - 124, 200, 20,
                    Component.translatable("mob_info.search"));
            this.addWidget(this.nameSearchBox);
        }
    }

    private void createMenu() {
        int X = this.width / 2;
        int Y = this.height / 2;
        this.addRenderableWidget(Button.builder(Component.translatable("←"), (p_280814_) -> {
            if (page > 0) page --;
        }).pos(X - 39 + 5, Y - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("→"), (p_280814_) -> {
            if (page < 100) page ++;
        }).pos(X + 20 + 5, Y - 20 + 97).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.translatable("x"), (p_280814_) -> {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
        }).pos(X + 136, Y - 98).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.literal("返回图鉴"), (p_280814_) -> {
            this.minecraft.setScreen(new Illustrate(true, 0));
        }).pos(X + 150 - 60, Y - 98 - 20).size(28 * 2, 16).build());
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
        String searchValue = this.nameSearchBox.getValue();
        List<MobInfo> mobInfoList = new ArrayList<>(getMobInfoList());
        if (!searchValue.isEmpty()) {
            List<MobInfo> newMobInfoList = mobInfoList.stream().filter(mobInfo -> {
                boolean nameContain = mobInfo.name.getString().contains(searchValue);
                boolean dropContain = mobInfo.dropList.stream().anyMatch(itemAndRate -> {
                    return Component.translatable("item.vmd." + itemAndRate.getItemStack().getItem())
                            .getString().contains(searchValue);
                });
                return nameContain || dropContain;
            }).toList();
            mobInfoList.clear();
            mobInfoList.addAll(newMobInfoList);
        }
        mobInfoList.sort(new Comparator<MobInfo>() {
            @Override
            public int compare(MobInfo o1, MobInfo o2) {
                if (Math.abs(o1.mobLevel - mc.player.experienceLevel) == Math.abs(o2.mobLevel - mc.player.experienceLevel)) {
                    return o2.mobLevel - o1.mobLevel;
                }
                return Math.abs(o1.mobLevel - mc.player.experienceLevel) - Math.abs(o2.mobLevel - mc.player.experienceLevel);
            }
        });

        for (int i = 0 ; i < 5 ; i ++) {
            if (page * 5 + i < mobInfoList.size()) {
                MobInfo mobInfo = mobInfoList.get(page * 5 + i);
                guiGraphics.drawCenteredString(font, mobInfo.name, this.width / 2 - 100,
                        this.height / 2 - 73 + 32 * i,0);
                guiGraphics.drawCenteredString(font, Te.s("「掉落物表」", ChatFormatting.AQUA),
                        this.width / 2, this.height / 2 - 73 + 32 * i,0);
                if (mobInfo.introduction != null) {
                    guiGraphics.drawCenteredString(font, Te.s("「特殊机制」", ChatFormatting.RED),
                            this.width / 2 + 100, this.height / 2 - 73 + 32 * i,0);
                    if (x > this.width / 2 + 100 - 20 && x < this.width / 2 + 100 + 20
                            && y > this.height / 2 - 73 + 32 * i - 4 && y < this.height / 2 - 73 + 32 * i + 8) {
                        List<Component> description = new ArrayList<>();
                        description.add(Te.s("特殊机制", ChatFormatting.RED));
                        description.addAll(mobInfo.introduction);
                        guiGraphics.renderComponentTooltip(fontRenderer, description, x, y);
                    }
                }
                if (mobInfo.attributes != null) {
                    guiGraphics.drawCenteredString(font, Te.s("「属性」", ChatFormatting.GREEN),
                            this.width / 2 + 50, this.height / 2 - 73 + 32 * i,0);
                    if (x > this.width / 2 + 50 - 20 && x < this.width / 2 + 50 + 20
                            && y > this.height / 2 - 73 + 32 * i - 4 && y < this.height / 2 - 73 + 32 * i + 8) {
                        List<Component> description = new ArrayList<>();
                        description.add(Te.s("怪物(主要)属性", CustomStyle.styleOfRed));
                        MobAttributes attributes = mobInfo.attributes;
                        description.add(Te.s(" · 基础攻击 ", CustomStyle.styleOfVolcano,
                                String.format("%.0f", attributes.attackDamage)));
                        description.add(Te.s(" · 基础护甲 ", CustomStyle.styleOfStone,
                                String.format("%.0f", attributes.defence)));
                        description.add(Te.s(" - 以你目前的穿透属性，对其造成的物理伤害倍率为",
                                String.format("%.1f%%", Damage.defenceDamageDecreaseRate(attributes.defence,
                                        ClientUtils.BreakDefenceC, ClientUtils.BreakDefence0C) * 100),
                                CustomStyle.styleOfVolcano));
                        description.add(Te.s(" · 基础魔抗 ", ChatFormatting.BLUE,
                                String.format("%.0f", attributes.manaDefence)));
                        description.add(Te.s(" - 以你目前的穿透属性，对其造成的魔法伤害倍率为",
                                String.format("%.1f%%", Damage.defenceDamageDecreaseRate(attributes.defence,
                                        ClientUtils.BreakManaDefenceC, ClientUtils.BreakManaDefence0C) * 100),
                                CustomStyle.styleOfMana));
                        description.add(Te.s(" · 暴击几率 ", ChatFormatting.LIGHT_PURPLE,
                                String.format("%.0f%%", attributes.critRate * 100)));
                        description.add(Te.s(" · 暴击伤害 ", ChatFormatting.BLUE,
                                String.format("%.0f%%", attributes.critDamage * 100)));
                        description.add(Te.s(" · 护甲穿透 ", CustomStyle.styleOfStone,
                                String.format("%.0f%%", attributes.defencePenetration * 100)));
                        description.add(Te.s(" · 护甲穿透 ", CustomStyle.styleOfStone,
                                String.format("%.0f", attributes.defencePenetration0)));
                        description.add(Te.s(" - 以你目前的抗性，其对你造成的物理伤害倍率为",
                                String.format("%.1f%%", Damage.defenceDamageDecreaseRate(ClientUtils.DefenceC,
                                        attributes.defencePenetration, attributes.defencePenetration0) * 100),
                                CustomStyle.styleOfVolcano));
                        description.add(Te.s(" · 生命偷取 ", CustomStyle.styleOfRed,
                                String.format("%.0f%%", attributes.healthSteal * 100)));
                        description.add(Te.s(" · 最大生命值 ", CustomStyle.styleOfLife,
                                String.format("%.0f", attributes.maxHealth)));
                        description.add(Te.s(" · 额外移速 ", CustomStyle.styleOfFlexible,
                                String.format("%.0f%%", (1 + attributes.movementSpeed * 100))));
                        description.add(Te.s(" - 若想更精确比对穿透数值，可以将主武器放在物品栏第一位"));
                        description.add(Te.s(" - 打开身份卡时会自动将当前选定物品切换至物品栏第一格"));
                        guiGraphics.renderComponentTooltip(fontRenderer, description, x, y);
                    }
                }
                if (x > this.width / 2 - 20 && x < this.width / 2 + 20
                        && y > this.height / 2 - 73 + 32 * i - 4 && y < this.height / 2 - 73 + 32 * i + 8) {
                    List<Component> description = new ArrayList<>();
                    description.add(Te.s("掉落物及其概率:"));
                    mobInfo.dropList.forEach(itemAndRate -> {
                        description.add(Te.m("  ").append(itemAndRate.getItemStack().getDisplayName())
                                .append(Te.m(" * " + itemAndRate.getItemStack().getCount(), ChatFormatting.AQUA))
                                .append(Te.m(" [", ChatFormatting.AQUA))
                                .append(Te.m(BasicAttributeDescription.getDecimal(itemAndRate.getRate() * 100, 3) + "%"))
                                .append(Te.m("]", ChatFormatting.AQUA)));
                    });
                    guiGraphics.renderComponentTooltip(fontRenderer, description, x, y);
                }
            }
        }

        guiGraphics.drawString(fontRenderer, Te.s("?查看帮助", ChatFormatting.WHITE),
                this.width / 2 - 146, this.height / 2 - 95, 0);

        if (x > this.width / 2 - 146 - 8 && x < this.width / 2 - 146 + 36
                && y > this.height / 2 - 96 && y < this.height / 2 - 96 + 8) {
            List<Component> components = List.of(
                    Te.s("1.在搜索栏中，输入", "材料名称", CustomStyle.styleOfLife,
                            " / ", "怪物名称", ChatFormatting.RED, "即可找到对应怪物及其掉落信息"),
                    Te.s("2.条目会按照自身等级与怪物的差值进行排序，等级差值越小排序越前")
            );
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("" + (page + 1))
                .withStyle(ChatFormatting.WHITE), this.width / 2 + 5, this.height / 2 - 22 + 105, 0);
        this.nameSearchBox.render(guiGraphics, x, y, v);
        super.render(p_96310_, x, y, v);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public record MobInfo(Component name, int mobLevel, List<ItemAndRate> dropList,
                          List<Component> introduction, MobAttributes attributes) {}

    public static List<MobInfo> mobInfoList = new ArrayList<>();

    public List<MobInfo> getMobInfoList() {
        if (mobInfoList.isEmpty()) {
            NoTeamInstanceModule.getAllInstance().forEach(noTeamInstance -> {
                mobInfoList.add(new MobInfo(Te.s("领主级 - ", ChatFormatting.RED,
                        Utils.getLevelDescription(noTeamInstance.level), " ",
                        noTeamInstance.name), noTeamInstance.level,
                        noTeamInstance.getRewardList(), noTeamInstance.getIntroduction(),
                        noTeamInstance.getMainMobAttributes()));
            });
            MobSpawn.getAllControllers(false).forEach(mobSpawnController -> {
                mobInfoList.add(new MobInfo(Te.s(Utils.getLevelDescription(mobSpawnController.averageLevel),
                        " ", mobSpawnController.mobName),
                        mobSpawnController.averageLevel, mobSpawnController.getDropList(), null,
                        mobSpawnController.getMobAttributes()));
            });
            NewTeamInstanceHandler.instances.forEach(newTeamInstance -> {
                mobInfoList.add(new MobInfo(newTeamInstance.description, newTeamInstance.levelRequire,
                        newTeamInstance.getRewardList(), null, newTeamInstance.getMainMobAttributes()));
            });
            JungleMobSpawn.getOverworldController().forEach(controller -> {
                mobInfoList.add(new MobInfo(Te.s(Utils.getLevelDescription(controller.mobXpLevel),
                        " ", controller.name),
                        controller.mobXpLevel, controller.getRewardItemList(), null, controller.getMobAttributes()));
            });
        }
        return mobInfoList;
    }
}

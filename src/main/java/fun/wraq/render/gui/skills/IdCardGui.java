package fun.wraq.render.gui.skills;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SkillPackets.AbilityDataC2SPacket;
import fun.wraq.networking.misc.SkillPackets.SkillDataC2SPacket;
import fun.wraq.networking.misc.SkillPackets.SkillRequestC2SPacket;
import fun.wraq.process.func.guide.networking.GuideFinishC2SPacket;
import fun.wraq.process.func.plan.DailySupply;
import fun.wraq.process.func.plan.networking.DailySupplyC2SPacket;
import fun.wraq.process.system.missions.netWorking.MissionScreenOpenC2SPacket;
import fun.wraq.process.system.point.Point;
import fun.wraq.process.system.tower.TowerScreen;
import fun.wraq.process.system.vp.VpStoreScreen;
import fun.wraq.render.gui.illustrate.Illustrate;
import fun.wraq.render.gui.mission.OldMissionScreen;
import fun.wraq.render.gui.mission.ReputationStore;
import fun.wraq.render.gui.testAndHelper.OpenSkillTreeGui;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class IdCardGui extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/skill.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;

    public IdCardGui(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.vmd0") : Component.translatable("menu.vmd1"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.showSkillGui();
        }
    }

    private void showSkillGui() {

        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            if (ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used > 0 && ClientUtils.AbilityChangeCache.Power > ClientUtils.AbilityCache.Power) {
                ClientUtils.AbilityChangeCache.Power--;
            }
        }).pos(this.width / 2 + 75, this.height / 2 - 17 - 68).size(12, 12).build());

        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            if (ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used > 0 && ClientUtils.AbilityChangeCache.Intelligent > ClientUtils.AbilityCache.Intelligent) {
                ClientUtils.AbilityChangeCache.Intelligent--;
            }
        }).pos(this.width / 2 + 75, this.height / 2 - 2 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            if (ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used > 0 && ClientUtils.AbilityChangeCache.Flexibility > ClientUtils.AbilityCache.Flexibility) {
                ClientUtils.AbilityChangeCache.Flexibility--;
            }
        }).pos(this.width / 2 + 75, this.height / 2 + 13 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            if (ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used > 0 && ClientUtils.AbilityChangeCache.Lucky > ClientUtils.AbilityCache.Lucky) {
                ClientUtils.AbilityChangeCache.Lucky--;
            }
        }).pos(this.width / 2 + 75, this.height / 2 + 28 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            if (ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used > 0 && ClientUtils.AbilityChangeCache.Vitality > ClientUtils.AbilityCache.Vitality) {
                ClientUtils.AbilityChangeCache.Vitality--;
            }
        }).pos(this.width / 2 + 75, this.height / 2 + 43 - 68).size(12, 12).build());

        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total) {
                ClientUtils.AbilityChangeCache.Power++;
            }
        }).pos(this.width / 2 + 125, this.height / 2 - 17 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total) {
                ClientUtils.AbilityChangeCache.Intelligent++;
            }
        }).pos(this.width / 2 + 125, this.height / 2 - 2 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total) {
                ClientUtils.AbilityChangeCache.Flexibility++;
            }
        }).pos(this.width / 2 + 125, this.height / 2 + 13 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total) {
                ClientUtils.AbilityChangeCache.Lucky++;
            }
        }).pos(this.width / 2 + 125, this.height / 2 + 28 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total) {
                ClientUtils.AbilityChangeCache.Vitality++;
            }
        }).pos(this.width / 2 + 125, this.height / 2 + 43 - 68).size(12, 12).build());


        this.addRenderableWidget(Button.builder(Component.translatable("×"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
        }).pos(this.width / 2 + 110, this.height / 2 + 72 - 68).size(12, 12).build());

        this.addRenderableWidget(Button.builder(Component.translatable("○"), (p_280814_) -> {
            ModNetworking.sendToServer(new AbilityDataC2SPacket(ClientUtils.AbilityChangeCache.Power, ClientUtils.AbilityChangeCache.Intelligent,
                    ClientUtils.AbilityChangeCache.Flexibility, ClientUtils.AbilityChangeCache.Lucky, ClientUtils.AbilityChangeCache.Vitality));
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
        }).pos(this.width / 2 + 125, this.height / 2 + 72 - 68).size(12, 12).build());

        this.addRenderableWidget(Button.builder(Component.translatable("×"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
        }).pos(this.width / 2 + 110, this.height / 2 + 94 - 68).size(12, 12).build());

        this.addRenderableWidget(Button.builder(Component.translatable("○"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillDataC2SPacket(ClientUtils.SkillChangeCache.Sword, ClientUtils.SkillChangeCache.Bow,
                    ClientUtils.SkillChangeCache.Mana));
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
        }).pos(this.width / 2 + 125, this.height / 2 + 94 - 68).size(12, 12).build());


        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            if (ClientUtils.PlayerSkillPoint_Total - ClientUtils.PlayerSkillPoint_Used > 0 && ClientUtils.SkillChangeCache.Sword > ClientUtils.SkillCache.Sword) {
                ClientUtils.SkillChangeCache.Sword--;
            }
        }).pos(this.width / 2 - 130, this.height / 2 + 98 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            if (ClientUtils.SkillChangeCache.SumPoint() < ClientUtils.PlayerSkillPoint_Total) {
                ClientUtils.SkillChangeCache.Sword++;
            }
        }).pos(this.width / 2 - 95, this.height / 2 + 98 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            if (ClientUtils.PlayerSkillPoint_Total - ClientUtils.PlayerSkillPoint_Used > 0 && ClientUtils.SkillChangeCache.Bow > ClientUtils.SkillCache.Bow) {
                ClientUtils.SkillChangeCache.Bow--;
            }
        }).pos(this.width / 2 - 68, this.height / 2 + 98 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            if (ClientUtils.SkillChangeCache.SumPoint() < ClientUtils.PlayerSkillPoint_Total) {
                ClientUtils.SkillChangeCache.Bow++;
            }
        }).pos(this.width / 2 - 33, this.height / 2 + 98 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
            if (ClientUtils.PlayerSkillPoint_Total - ClientUtils.PlayerSkillPoint_Used > 0 && ClientUtils.SkillChangeCache.Mana > ClientUtils.SkillCache.Mana) {
                ClientUtils.SkillChangeCache.Mana--;
            }
        }).pos(this.width / 2 - 6, this.height / 2 + 98 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            if (ClientUtils.SkillChangeCache.SumPoint() < ClientUtils.PlayerSkillPoint_Total) {
                ClientUtils.SkillChangeCache.Mana++;
            }
        }).pos(this.width / 2 + 29, this.height / 2 + 98 - 68).size(12, 12).build());
        this.addRenderableWidget(Button.builder(Component.literal("精通树").withStyle(CustomStyle.styleOfPower), (p_280814_) -> {
            ClientUtils.SwordSkillPoint.SwordSkillPointCacheInit();
            ClientUtils.BowSkillPoint.BowSkillPointCacheInit();
            ClientUtils.ManaSkillPoint.ManaSkillPointCacheInit();
            DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> OpenSkillTreeGui::new);
        }).pos(this.width / 2 - 122, this.height / 2 + 113 - 68).size(30, 12).build());
        this.addRenderableWidget(Button.builder(Component.literal("精通树").withStyle(CustomStyle.styleOfFlexible), (p_280814_) -> {
            ClientUtils.SwordSkillPoint.SwordSkillPointCacheInit();
            ClientUtils.BowSkillPoint.BowSkillPointCacheInit();
            ClientUtils.ManaSkillPoint.ManaSkillPointCacheInit();
            DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> OpenSkillTreeGui::new);
        }).pos(this.width / 2 - 60, this.height / 2 + 113 - 68).size(30, 12).build());
        this.addRenderableWidget(Button.builder(Component.literal("精通树").withStyle(CustomStyle.styleOfIntelligent), (p_280814_) -> {
            ClientUtils.SwordSkillPoint.SwordSkillPointCacheInit();
            ClientUtils.BowSkillPoint.BowSkillPointCacheInit();
            ClientUtils.ManaSkillPoint.ManaSkillPointCacheInit();
            DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> OpenSkillTreeGui::new);
        }).pos(this.width / 2 + 2, this.height / 2 + 113 - 68).size(30, 12).build());

/*        this.addRenderableWidget(Button.builder(Component.literal("组队/副本").withStyle(ChatFormatting.AQUA), (p_280814_) -> {
            ModNetworking.sendToServer(new TeamScreenOpenRequestC2SPacket());
        }).pos(this.width / 2 + 36, this.height / 2 + 113 - 40).size(48,16).build());*/

        this.addRenderableWidget(Button.builder(Component.literal("领取补给").withStyle(ChatFormatting.GOLD), (p_280814_) -> {
            ModNetworking.sendToServer(new DailySupplyC2SPacket());
        }).pos(this.width / 2 + 90, this.height / 2 + 113 - 68).size(48, 16).build());

        assert minecraft != null;
        this.addRenderableWidget(Button.builder(Component.literal("每日/悬赏").withStyle(CustomStyle.styleOfKaze), (p_280814_) -> {
            this.minecraft.setScreen(new OldMissionScreen());
        }).pos(this.width / 2 + 90, this.height / 2 + 113 - 40).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("任务列表").withStyle(CustomStyle.styleOfKaze), (p_280814_) -> {
            ModNetworking.sendToServer(new MissionScreenOpenC2SPacket(2));
        }).pos(this.width / 2 + 36, this.height / 2 + 113 - 40).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("声望商店").withStyle(ChatFormatting.YELLOW), (p_280814_) -> {
            this.minecraft.setScreen(new ReputationStore(true));
        }).pos(this.width / 2 - 18, this.height / 2 + 113 - 40).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("vp商店").withStyle(ChatFormatting.AQUA), (p_280814_) -> {
            this.minecraft.setScreen(new VpStoreScreen());
        }).pos(this.width / 2 - 18, this.height / 2 + 113 - 40 + 18).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("物品图鉴").withStyle(ChatFormatting.LIGHT_PURPLE), (p_280814_) -> {
            this.minecraft.setScreen(new Illustrate(true, 0));
            ModNetworking.sendToServer(new GuideFinishC2SPacket(2));
        }).pos(this.width / 2 - 72, this.height / 2 + 113 - 40).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld), (p_280814_) -> {
            this.minecraft.setScreen(new TowerScreen(0));
        }).pos(this.width / 2 - 126, this.height / 2 + 113 - 40).size(48, 16).build());
    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics graphics, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);
        this.renderables.forEach(renderable -> {
            if (renderable instanceof Button button) {
                if (button.getMessage().getString().contains("vp商店") || button.getMessage().getString().contains("声望商店")) {
                    if (x > this.width / 2 - 18 && x < this.width / 2 - 18 + 48 && y > this.height / 2 + 113 - 40
                            && y < this.height / 2 + 113 - 40 + 18 + 16) {
                        renderable.render(graphics, x, y, v);
                    } else {
                        guiGraphics.drawString(fontRenderer, Component.literal("「商店列表」").withStyle(ChatFormatting.WHITE), this.width / 2 - 18, this.height / 2 + 113 - 40 + 3, 0);
                    }
                } else renderable.render(graphics, x, y, v);
            } else renderable.render(graphics, x, y, v);
        });

        ItemStack dailyItem = DailySupply.getRewardItemMap().getOrDefault(DailySupply.clientSupplyStatus, new ItemStack(Items.AIR));
        if (dailyItem.getCount() != 0) {
            guiGraphics.renderItem(dailyItem, this.width / 2 + 63, this.height / 2 + 113 - 67);
            if (x > this.width / 2 + 63 && x < this.width / 2 + 63 + 16 && y > this.height / 2 + 113 - 67 && y < this.height / 2 + 113 - 67 + 16)
                guiGraphics.renderTooltip(font, dailyItem, x, y);
            if (dailyItem.getCount() > 9)
                guiGraphics.drawCenteredString(font, Component.literal("" + dailyItem.getCount()).withStyle(ChatFormatting.WHITE),
                        this.width / 2 + 63 + 22, this.height / 2 + 113 - 67 + 8, 0);
            else
                guiGraphics.drawCenteredString(font, Component.literal("" + dailyItem.getCount()).withStyle(ChatFormatting.WHITE),
                        this.width / 2 + 63 + 20, this.height / 2 + 113 - 67 + 8, 0);
        }


        assert Minecraft.getInstance().player != null;
        guiGraphics.drawCenteredString(fontRenderer, Minecraft.getInstance().player.getName(), this.width / 2 - 100, this.height / 2 - 88, 5636095);

        /* - */

        if (!Point.clientData.isEmpty()) {
            for (int i = 1 ; i <= Point.DESCRIPTION.size() ; i ++) {
                String type = Point.DESCRIPTION.keySet().stream().toList().get(i - 1);
                Component typeDescription = Point.TYPE.get(type);
                double value = Point.clientData.get(i - 1);
                guiGraphics.drawCenteredString(fontRenderer, typeDescription,
                        this.width / 2 - 116 + 62 * (i / 5), this.height / 2 - 88 + 17 * (i % 5), 0);
                guiGraphics.drawCenteredString(fontRenderer, Te.m(Compute.getSimplifiedNumberDescription(value)),
                        this.width / 2 - 87 + 62 * (i / 5), this.height / 2 - 88 + 17 * (i % 5), 0);
                if (x > this.width / 2 - 116 + 62 * (i / 5) - 10 && x < this.width / 2 - 116 + 62 * (i / 5) + 10
                        && y > this.height / 2 - 88 + 17 * (i % 5) && y < this.height / 2 - 88 + 17 * (i % 5) + 10) {
                    List<Component> components = new ArrayList<>();
                    components.add(Point.DESCRIPTION.get(type));
                    components.add(Te.s("当前拥有" + String.format("%.2f", value) + "点"));
                    guiGraphics.renderComponentTooltip(font, components, x, y);
                }
            }
        }

/*        guiGraphics.drawCenteredString(fontRenderer, Component.literal("基础攻击").withStyle(ChatFormatting.AQUA),
                this.width / 2 - 116, this.height / 2 - 71, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.AttackDamageC)),
                this.width / 2 - 87, this.height / 2 - 71, 5636095);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("护甲穿透").withStyle(ChatFormatting.GRAY),
                this.width / 2 - 116, this.height / 2 - 54, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.BreakDefenceC * 100)),
                this.width / 2 - 87, this.height / 2 - 54, 11184810);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE),
                this.width / 2 - 116, this.height / 2 - 37, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.CritRateC * 100)),
                this.width / 2 - 87, this.height / 2 - 37, 16733695);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("暴击伤害").withStyle(ChatFormatting.BLUE),
                this.width / 2 - 116, this.height / 2 - 20, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.CritDamageC * 100)),
                this.width / 2 - 87, this.height / 2 - 20, 5592575);


        guiGraphics.drawCenteredString(fontRenderer, Component.literal("法术攻击").withStyle(ChatFormatting.LIGHT_PURPLE),
                this.width / 2 - 54, this.height / 2 - 88, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.ManaDamageC)),
                this.width / 2 - 25, this.height / 2 - 88, 16733695);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("法术穿透").withStyle(ChatFormatting.BLUE),
                this.width / 2 - 54, this.height / 2 - 71, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.BreakManaDefenceC * 100)),
                this.width / 2 - 25, this.height / 2 - 71, 5592575);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("法力回复").withStyle(ChatFormatting.LIGHT_PURPLE),
                this.width / 2 - 54, this.height / 2 - 54, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.ManaReplyC + 5)),
                this.width / 2 - 25, this.height / 2 - 54, 16733695);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("技能急速").withStyle(ChatFormatting.AQUA),
                this.width / 2 - 54, this.height / 2 - 37, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.CoolDownC * 100)),
                this.width / 2 - 25, this.height / 2 - 37, 5636095);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("移动速度").withStyle(ChatFormatting.GREEN),
                this.width / 2 - 54, this.height / 2 - 20, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.SpeedC * 100)),
                this.width / 2 - 25, this.height / 2 - 20, 5635925);


        guiGraphics.drawCenteredString(fontRenderer, Component.literal("生命偷取").withStyle(ChatFormatting.RED),
                this.width / 2 + 8, this.height / 2 - 88, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f%%", ClientUtils.HealStealC * 100)),
                this.width / 2 + 37, this.height / 2 - 88, 16733525);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("固定穿甲").withStyle(ChatFormatting.GRAY),
                this.width / 2 + 8, this.height / 2 - 71, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.BreakDefence0C)),
                this.width / 2 + 37, this.height / 2 - 71, 11184810);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("基础护甲").withStyle(ChatFormatting.GRAY),
                this.width / 2 + 8, this.height / 2 - 54, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.DefenceC)),
                this.width / 2 + 37, this.height / 2 - 54, 11184810);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("法术抗性").withStyle(ChatFormatting.BLUE),
                this.width / 2 + 8, this.height / 2 - 37, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", ClientUtils.ManaDefenceC)),
                this.width / 2 + 37, this.height / 2 - 37, 5592575);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("生命值").withStyle(ChatFormatting.GREEN),
                this.width / 2 + 8, this.height / 2 - 20, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%.0f", Minecraft.getInstance().player.getMaxHealth())),
                this.width / 2 + 37, this.height / 2 - 20, 5635925);*/

        /* - */
        int PowerOffsetX = 100;
        int PowerOffsetY = -84;
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("力量").withStyle(CustomStyle.styleOfPower), this.width / 2 + 100, this.height / 2 - 16 - 68, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%d", ClientUtils.AbilityChangeCache.Power)).withStyle(CustomStyle.styleOfPower), this.width / 2 + 116, this.height / 2 - 16 - 68, 0);

        int IntelligentOffsetX = 100;
        int IntelligentOffsetY = -69;
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("智力").withStyle(CustomStyle.styleOfIntelligent), this.width / 2 + 100, this.height / 2 - 1 - 68, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%d", ClientUtils.AbilityChangeCache.Intelligent)).withStyle(CustomStyle.styleOfIntelligent), this.width / 2 + 116, this.height / 2 - 1 - 68, 0);

        int FlexibleOffsetX = 100;
        int FlexibleOffsetY = -54;
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("精巧").withStyle(CustomStyle.styleOfFlexible), this.width / 2 + 100, this.height / 2 + 14 - 68, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%d", ClientUtils.AbilityChangeCache.Flexibility)).withStyle(CustomStyle.styleOfFlexible), this.width / 2 + 116, this.height / 2 + 14 - 68, 0);

        int LuckyOffsetX = 100;
        int LuckyOffsetY = -39;
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("启迪").withStyle(CustomStyle.styleOfLucky), this.width / 2 + 100, this.height / 2 + 29 - 68, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%d", ClientUtils.AbilityChangeCache.Lucky)).withStyle(CustomStyle.styleOfLucky), this.width / 2 + 116, this.height / 2 + 29 - 68, 0);

        int VitalityOffsetX = 100;
        int VitalityOffsetY = -24;
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("活力").withStyle(CustomStyle.styleOfVitality), this.width / 2 + 100, this.height / 2 + 44 - 68, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%d", ClientUtils.AbilityChangeCache.Vitality)).withStyle(CustomStyle.styleOfVitality), this.width / 2 + 116, this.height / 2 + 44 - 68, 0);

        int SwordOffsetX = -100;
        int SwordOffsetY = 9;
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("剑术精通").withStyle(CustomStyle.styleOfPower), this.width / 2 - 111, this.height / 2 + 77 - 68, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%d", ClientUtils.SkillChangeCache.Sword)).withStyle(CustomStyle.styleOfPower), this.width / 2 - 107, this.height / 2 + 100 - 68, 0);

        int BowOffsetX = -38;
        int BowOffsetY = 9;
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("弓术精通").withStyle(CustomStyle.styleOfFlexible), this.width / 2 - 49, this.height / 2 + 77 - 68, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%d", ClientUtils.SkillChangeCache.Bow)).withStyle(CustomStyle.styleOfFlexible), this.width / 2 - 45, this.height / 2 + 100 - 68, 0);

        int ManaOffsetX = 24;
        int ManaOffsetY = 9;
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("法术精通").withStyle(CustomStyle.styleOfIntelligent), this.width / 2 + 13, this.height / 2 + 77 - 68, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%d", ClientUtils.SkillChangeCache.Mana)).withStyle(CustomStyle.styleOfIntelligent), this.width / 2 + 17, this.height / 2 + 100 - 68, 0);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%d", ClientUtils.PlayerAbilityPoint_Total - ClientUtils.AbilityChangeCache.SumPoint())).withStyle(ChatFormatting.AQUA), this.width / 2 + 88, this.height / 2 + 6, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%d", ClientUtils.PlayerSkillPoint_Total - ClientUtils.SkillChangeCache.SumPoint())).withStyle(ChatFormatting.AQUA), this.width / 2 + 88, this.height / 2 + 28, 0);

        if (x > this.width / 2 + PowerOffsetX - 10 && x < this.width / 2 + PowerOffsetX + 8 && y > this.height / 2 + PowerOffsetY - 1 && y < this.height / 2 + PowerOffsetY + 10) {
            Style style = CustomStyle.styleOfPower;
            String Ability = "力量";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("能力 ").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("▶" + Ability).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("力量是探索的基础，提升力量将使你的攻击变得更加强力！").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal("当前").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(Ability).withStyle(style)).
                    append(Component.literal("能力点数属性加成:").withStyle(ChatFormatting.WHITE)));
            double ExAttack = ClientUtils.AbilityChangeCache.Power;
            double ExDefence = ClientUtils.AbilityChangeCache.Power;
            double ExCritDamage = ClientUtils.AbilityChangeCache.Power;
            ComponentUtils.emojiDescriptionExAttackDamage(components, ExAttack);
            ComponentUtils.emojiDescriptionDefence(components, ExDefence);
            ComponentUtils.emojiDescriptionCritDamage(components, ExCritDamage * 0.01);
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal(Ability).withStyle(style).
                    append(Component.literal("能力与属性点数关系:").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("每一点能力获得:"));
            ComponentUtils.emojiDescriptionExAttackDamage(components, 1);
            ComponentUtils.emojiDescriptionDefence(components, 1);
            ComponentUtils.emojiDescriptionCritDamage(components, 0.01);
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        if (x > this.width / 2 + IntelligentOffsetX - 10 && x < this.width / 2 + IntelligentOffsetX + 8 && y > this.height / 2 + IntelligentOffsetY - 1 && y < this.height / 2 + IntelligentOffsetY + 10) {
            Style style = CustomStyle.styleOfIntelligent;
            String Ability = "智力";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("能力 ").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("▶" + Ability).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("创造与扭曲，智胜区区凡人。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal("当前").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(Ability).withStyle(style)).
                    append(Component.literal("能力点数属性加成:").withStyle(ChatFormatting.WHITE)));
            double ExManaDamage = ClientUtils.AbilityChangeCache.Intelligent;
            double ExManaRecover = ClientUtils.AbilityChangeCache.Intelligent;
            double ExMaxMana = ClientUtils.AbilityChangeCache.Intelligent;
            ComponentUtils.emojiDescriptionManaAttackDamage(components, ExManaDamage * 2);
            ComponentUtils.emojiDescriptionManaRecover(components, ExManaRecover * 0.3);
            ComponentUtils.emojiDescriptionMaxMana(components, ExMaxMana);
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal(Ability).withStyle(style).
                    append(Component.literal("能力与属性点数关系:").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("每一点能力获得:"));
            ComponentUtils.emojiDescriptionManaAttackDamage(components, 2);
            ComponentUtils.emojiDescriptionManaRecover(components, 0.3);
            ComponentUtils.emojiDescriptionMaxMana(components, 1);
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        if (x > this.width / 2 + FlexibleOffsetX - 10 && x < this.width / 2 + FlexibleOffsetX + 8 && y > this.height / 2 + FlexibleOffsetY - 1 && y < this.height / 2 + FlexibleOffsetY + 10) {
            Style style = CustomStyle.styleOfFlexible;
            String Ability = "精巧";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("能力 ").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("▶" + Ability).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("精密与灵活。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal("当前").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(Ability).withStyle(style)).
                    append(Component.literal("能力点数属性加成:").withStyle(ChatFormatting.WHITE)));

            double ExSwiftness = ClientUtils.AbilityChangeCache.Flexibility;
            double ExMoveSpeed = ClientUtils.AbilityChangeCache.Flexibility;
            double ExDefencePenetration = ClientUtils.AbilityChangeCache.Flexibility;

            ComponentUtils.emojiDescriptionSwiftness(components, ExSwiftness * 0.1);
            ComponentUtils.emojiDescriptionCommonMovementSpeed(components, ExMoveSpeed * 0.003);
            ComponentUtils.emojiDescriptionDefencePenetration0(components, ExDefencePenetration);

            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal(Ability).withStyle(style).
                    append(Component.literal("能力与属性点数关系:").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("每一点能力获得:"));

            ComponentUtils.emojiDescriptionSwiftness(components, 0.1);
            ComponentUtils.emojiDescriptionCommonMovementSpeed(components, 0.003);
            ComponentUtils.emojiDescriptionDefencePenetration0(components, 1);

            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal("").append(ComponentUtils.AttributeDescription.swiftness("")).
                    append(Component.literal("将会提升你的翻滚距离与体力值回复速度，").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY)));
            components.add(Component.literal(" 并为你提供箭矢攻击增幅，同时提升你的闪避几率。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        if (x > this.width / 2 + LuckyOffsetX - 10 && x < this.width / 2 + LuckyOffsetX + 8 && y > this.height / 2 + LuckyOffsetY - 1 && y < this.height / 2 + LuckyOffsetY + 10) {
            Style style = CustomStyle.styleOfLucky;
            String Ability = "启迪";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("能力 ").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("▶" + Ability).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("诡术秘法").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal("当前").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(Ability).withStyle(style)).
                    append(Component.literal("能力点数属性加成:").withStyle(ChatFormatting.WHITE)));
            double ExCritRate = ClientUtils.AbilityChangeCache.Lucky;
            double ExExpUp = ClientUtils.AbilityChangeCache.Lucky;
            double exCooldown = ClientUtils.AbilityChangeCache.Lucky;

            ComponentUtils.emojiDescriptionCritRate(components, ExCritRate * 0.001);
            ComponentUtils.emojiDescriptionExpUp(components, ExExpUp * 0.01);
            ComponentUtils.emojiDescriptionCoolDown(components, exCooldown * 0.01);

            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal(Ability).withStyle(style).
                    append(Component.literal("能力与属性点数关系:").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("每一点能力获得:"));

            ComponentUtils.emojiDescriptionCritRate(components, 0.001);
            ComponentUtils.emojiDescriptionExpUp(components, 0.01);
            ComponentUtils.emojiDescriptionCoolDown(components, 0.01);

            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        if (x > this.width / 2 + VitalityOffsetX - 10 && x < this.width / 2 + VitalityOffsetX + 8 && y > this.height / 2 + VitalityOffsetY - 1 && y < this.height / 2 + VitalityOffsetY + 10) {
            Style style = CustomStyle.styleOfVitality;
            String Ability = "活力";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("能力 ").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("▶" + Ability).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("提供生机与回复。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal("当前").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(Ability).withStyle(style)).
                    append(Component.literal("能力点数属性加成:").withStyle(ChatFormatting.WHITE)));
            double ExHealReply = ClientUtils.AbilityChangeCache.Vitality;
            double ExMaxHeal = ClientUtils.AbilityChangeCache.Vitality;
            double ExHealAmplitude = ClientUtils.AbilityChangeCache.Vitality;

            ComponentUtils.emojiDescriptionHealthRecover(components, ExHealReply);
            ComponentUtils.emojiDescriptionMaxHealth(components, ExMaxHeal * 10);
            ComponentUtils.emojiDescriptionHealAmplification(components, ExHealAmplitude * 0.01);

            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal(Ability).withStyle(style).
                    append(Component.literal("能力与属性点数关系:").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("每一点能力获得:"));

            ComponentUtils.emojiDescriptionHealthRecover(components, 1);
            ComponentUtils.emojiDescriptionMaxHealth(components, 10);
            ComponentUtils.emojiDescriptionHealAmplification(components, 0.01);

            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        if (x > this.width / 2 + SwordOffsetX - 30 && x < this.width / 2 + SwordOffsetX + 7 && y > this.height / 2 + SwordOffsetY - 2 && y < this.height / 2 + SwordOffsetY + 9) {
            Style style = CustomStyle.styleOfPower;
            String Skill = "剑术";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("精通 ").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("◉" + Skill).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("对剑术的精通将会提升你的近战能力，使你的近战攻击更加精确、有力。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            components.add(Component.literal("高于45的精通点数每点将会转化为0.33%物理伤害加成。").withStyle(ChatFormatting.GRAY));
            components.add(Component.literal("-当前物理伤害加成：").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(String.format("%.2f%%", Math.max(0, (ClientUtils.SkillChangeCache.Sword - 45) * 0.33))).withStyle(CustomStyle.styleOfPower)));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        if (x > this.width / 2 + BowOffsetX - 30 && x < this.width / 2 + BowOffsetX + 7 && y > this.height / 2 + BowOffsetY - 2 && y < this.height / 2 + BowOffsetY + 9) {
            Style style = CustomStyle.styleOfHealth;
            String Skill = "弓术";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("精通 ").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("◉" + Skill).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("对弓术的精通将会使你人弓合一。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            components.add(Component.literal("高于45的精通点数每点将会转化为0.33%物理伤害加成。").withStyle(ChatFormatting.GRAY));
            components.add(Component.literal("-当前物理伤害加成：").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(String.format("%.2f%%", Math.max(0, (ClientUtils.SkillChangeCache.Bow - 45) * 0.33))).withStyle(CustomStyle.styleOfFlexible)));

            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        if (x > this.width / 2 + ManaOffsetX - 30 && x < this.width / 2 + ManaOffsetX + 7 && y > this.height / 2 + ManaOffsetY - 2 && y < this.height / 2 + ManaOffsetY + 9) {
            Style style = CustomStyle.styleOfMana;
            String Skill = "法术";
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("精通 ").withStyle(ChatFormatting.AQUA).
                    append(Component.literal("◉" + Skill).withStyle(ChatFormatting.BOLD).withStyle(style)));
            components.add(Component.literal("对法术的精通将使你的法术得到各个维度的强化。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            components.add(Component.literal("高于45的精通点数每点将会转化为0.5%魔法伤害加成。").withStyle(ChatFormatting.GRAY));
            components.add(Component.literal("-当前魔法伤害加成：").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(String.format("%.2f%%", Math.max(0, (ClientUtils.SkillChangeCache.Mana - 45) * 0.5))).withStyle(CustomStyle.styleOfMana)));

            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        if (ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used > 0 && ClientUtils.AbilityChangeCache.Power > ClientUtils.AbilityCache.Power) {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GOLD), this.width / 2 + PowerOffsetX - 19, this.height / 2 + PowerOffsetY + 1, 0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GRAY), this.width / 2 + PowerOffsetX - 19, this.height / 2 + PowerOffsetY + 1, 0);

        }

        if (ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used > 0 && ClientUtils.AbilityChangeCache.Intelligent > ClientUtils.AbilityCache.Intelligent) {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GOLD), this.width / 2 + IntelligentOffsetX - 19, this.height / 2 + IntelligentOffsetY + 1, 0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GRAY), this.width / 2 + IntelligentOffsetX - 19, this.height / 2 + IntelligentOffsetY + 1, 0);

        }

        if (ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used > 0 && ClientUtils.AbilityChangeCache.Flexibility > ClientUtils.AbilityCache.Flexibility) {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GOLD), this.width / 2 + FlexibleOffsetX - 19, this.height / 2 + FlexibleOffsetY + 1, 0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GRAY), this.width / 2 + FlexibleOffsetX - 19, this.height / 2 + FlexibleOffsetY + 1, 0);

        }

        if (ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used > 0 && ClientUtils.AbilityChangeCache.Lucky > ClientUtils.AbilityCache.Lucky) {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GOLD), this.width / 2 + LuckyOffsetX - 19, this.height / 2 + LuckyOffsetY + 1, 0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GRAY), this.width / 2 + LuckyOffsetX - 19, this.height / 2 + LuckyOffsetY + 1, 0);

        }

        if (ClientUtils.PlayerAbilityPoint_Total - ClientUtils.PlayerAbilityPoint_Used > 0 && ClientUtils.AbilityChangeCache.Vitality > ClientUtils.AbilityCache.Vitality) {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GOLD), this.width / 2 + VitalityOffsetX - 19, this.height / 2 + VitalityOffsetY + 1, 0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GRAY), this.width / 2 + VitalityOffsetX - 19, this.height / 2 + VitalityOffsetY + 1, 0);

        }

        int[] OffsetX = {
                PowerOffsetX, IntelligentOffsetX, FlexibleOffsetX, LuckyOffsetX, VitalityOffsetX
        };
        int[] OffsetY = {
                PowerOffsetY, IntelligentOffsetY, FlexibleOffsetY, LuckyOffsetY, VitalityOffsetY
        };

        for (int i = 0; i < 5; i++) {
            if (ClientUtils.AbilityChangeCache.SumPoint() < ClientUtils.PlayerAbilityPoint_Total) {
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("+").withStyle(ChatFormatting.GOLD), this.width / 2 + OffsetX[i] + 31, this.height / 2 + OffsetY[i] + 1, 0);
            } else {
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("+").withStyle(ChatFormatting.GRAY), this.width / 2 + OffsetX[i] + 31, this.height / 2 + OffsetY[i] + 1, 0);
            }
        }

        if (ClientUtils.PlayerSkillPoint_Total - ClientUtils.PlayerSkillPoint_Used > 0 && ClientUtils.SkillChangeCache.Sword > ClientUtils.SkillCache.Sword) {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GOLD), this.width / 2 + SwordOffsetX - 24, this.height / 2 + SwordOffsetY + 23, 0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GRAY), this.width / 2 + SwordOffsetX - 24, this.height / 2 + SwordOffsetY + 23, 0);
        }

        if (ClientUtils.PlayerSkillPoint_Total - ClientUtils.PlayerSkillPoint_Used > 0 && ClientUtils.SkillChangeCache.Bow > ClientUtils.SkillCache.Bow) {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GOLD), this.width / 2 + BowOffsetX - 24, this.height / 2 + BowOffsetY + 23, 0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GRAY), this.width / 2 + BowOffsetX - 24, this.height / 2 + BowOffsetY + 23, 0);
        }

        if (ClientUtils.PlayerSkillPoint_Total - ClientUtils.PlayerSkillPoint_Used > 0 && ClientUtils.SkillChangeCache.Mana > ClientUtils.SkillCache.Mana) {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GOLD), this.width / 2 + ManaOffsetX - 24, this.height / 2 + ManaOffsetY + 23, 0);
        } else {
            guiGraphics.drawCenteredString(fontRenderer, Component.literal("-").withStyle(ChatFormatting.GRAY), this.width / 2 + ManaOffsetX - 24, this.height / 2 + ManaOffsetY + 23, 0);
        }

        int[] SkillOffsetX = {
                SwordOffsetX, BowOffsetX, ManaOffsetX
        };

        int[] SkillOffsetY = {
                SwordOffsetY, BowOffsetY, ManaOffsetY
        };

        for (int i = 0; i < 3; i++) {
            if (ClientUtils.SkillChangeCache.SumPoint() < ClientUtils.PlayerSkillPoint_Total) {
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("+").withStyle(ChatFormatting.GOLD), this.width / 2 + SkillOffsetX[i] + 11, this.height / 2 + SkillOffsetY[i] + 23, 0);
            } else {
                guiGraphics.drawCenteredString(fontRenderer, Component.literal("+").withStyle(ChatFormatting.GRAY), this.width / 2 + SkillOffsetX[i] + 11, this.height / 2 + SkillOffsetY[i] + 23, 0);

            }
        }
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

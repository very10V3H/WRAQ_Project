package fun.wraq.render.gui.skills;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.system.mastery.MasteryAllocation;
import fun.wraq.process.system.mastery.MasteryBonusCache;
import fun.wraq.process.system.mastery.MasteryClientData;
import fun.wraq.process.system.mastery.MasteryStats;
import fun.wraq.process.system.mastery.MasteryTree;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SkillPackets.AbilityDataC2SPacket;
import fun.wraq.networking.misc.SkillPackets.SkillDataC2SPacket;
import fun.wraq.networking.misc.SkillPackets.SkillRequestC2SPacket;
import fun.wraq.networking.misc.SmartPhonePackets.MarketScreenC2SPacket;
import fun.wraq.networking.misc.TeamPackets.TeamScreenOpenRequestC2SPacket;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.guide.networking.GuideFinishC2SPacket;
import fun.wraq.process.func.plan.DailySupply;
import fun.wraq.process.func.plan.networking.DailySupplyC2SPacket;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.system.missions.netWorking.MissionScreenOpenC2SPacket;
import fun.wraq.process.system.point.Point;
import fun.wraq.process.system.reason.Reason;
import fun.wraq.process.system.tower.TowerScreen;
import fun.wraq.process.system.vp.VpStoreScreen;
import fun.wraq.render.gui.illustrate.Illustrate;
import fun.wraq.render.gui.mission.ReputationStore;
import fun.wraq.render.gui.testAndHelper.OpenSkillTreeGui;
import fun.wraq.render.gui.trade.weekly.WeeklyStoreScreenC2SPacket;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events._7shade.SevenShadePiece;
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

        // AI-Generated, 2026-10-18 打开身份卡时刷新精通/技能数据
        ModNetworking.sendToServer(new SkillRequestC2SPacket());

        // AI-Generated, 2026-10-18 精通系统：独立的五列精通界面
        this.addRenderableWidget(Button.builder(Component.literal("精通").withStyle(CustomStyle.styleOfWorld), (p_280814_) -> {
            MasteryScreen.tryOpen(this);
        }).pos(this.width / 2 - 63, this.height / 2 + 113 - 68).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("领取补给").withStyle(ChatFormatting.GOLD), (p_280814_) -> {
            ModNetworking.sendToServer(new DailySupplyC2SPacket());
        }).pos(this.width / 2 + 90, this.height / 2 + 113 - 68).size(48, 16).build());

        assert minecraft != null;
        this.addRenderableWidget(Button.builder(Te.s("研发采购", CustomStyle.styleOfWorld), (p_280814_) -> {
            ModNetworking.sendToServer(new WeeklyStoreScreenC2SPacket());
        }).pos(this.width / 2 + 90, this.height / 2 + 113 - 40).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("任务列表").withStyle(CustomStyle.styleOfKaze), (p_280814_) -> {
            ModNetworking.sendToServer(new MissionScreenOpenC2SPacket(2));
        }).pos(this.width / 2 + 36, this.height / 2 + 113 - 40).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("全球市场").withStyle(ChatFormatting.GOLD), (p_280814_) -> {
            ModNetworking.sendToServer(new MarketScreenC2SPacket(0));
        }).pos(this.width / 2 - 18, this.height / 2 + 113 - 40).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("声望商店").withStyle(ChatFormatting.YELLOW), (p_280814_) -> {
            this.minecraft.setScreen(new ReputationStore(true));
        }).pos(this.width / 2 - 18, this.height / 2 + 113 - 40 + 18).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("vp商店").withStyle(ChatFormatting.AQUA), (p_280814_) -> {
            this.minecraft.setScreen(new VpStoreScreen());
        }).pos(this.width / 2 - 18, this.height / 2 + 113 - 40 + 18 * 2).size(48, 16).build());

/*
        this.addRenderableWidget(Button.builder(Te.s("股市", CustomStyle.styleOfEnd), (p_280814_) -> {
            this.minecraft.setScreen(new StockScreen());
        }).pos(this.width / 2 - 18, this.height / 2 + 113 - 40 + 18 * 4).size(48, 16).build());
*/

        this.addRenderableWidget(Button.builder(Component.literal("图鉴").withStyle(CustomStyle.styleOfSunIsland), (p_280814_) -> {
            this.minecraft.setScreen(new Illustrate(true, 0));
            ModNetworking.sendToServer(new GuideFinishC2SPacket(Guide.StageV2.ILLUSTRATE));
        }).pos(this.width / 2 - 72, this.height / 2 + 113 - 40).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld), (p_280814_) -> {
            this.minecraft.setScreen(new TowerScreen(0));
        }).pos(this.width / 2 - 126, this.height / 2 + 113 - 40).size(48, 16).build());

        this.addRenderableWidget(Button.builder(Component.literal("组队/副本").withStyle(ChatFormatting.AQUA), (p_280814_) -> {
            ModNetworking.sendToServer(new TeamScreenOpenRequestC2SPacket());
        }).pos(this.width / 2 - 126, this.height / 2 + 113 - 40 + 18).size(48, 16).build());
    }

    public void tick() {
        super.tick();
    }

    public void render(GuiGraphics graphics, int x, int y, float v) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100,
                0, 0, 300, 200, textureWidth, textureHeight);
        this.renderables.forEach(renderable -> {
            if (renderable instanceof Button button) {
                if (button.getMessage().getString().contains("vp商店")
                        || button.getMessage().getString().contains("声望商店")
                        || button.getMessage().getString().contains("全球市场")
                        || button.getMessage().getString().contains("股市")) {
                    if (x > this.width / 2 - 18 && x < this.width / 2 - 18 + 48 && y > this.height / 2 + 113 - 40
                            && y < this.height / 2 + 113 - 40 + 18 * 3 + 16) {
                        renderable.render(graphics, x, y, v);
                    } else {
                        guiGraphics.drawString(fontRenderer, Component.literal("「市场/商店」")
                                .withStyle(ChatFormatting.WHITE), this.width / 2 - 18, this.height / 2 + 113 - 40 + 3, 0);
                    }
                } else if (button.getMessage().getString().contains("本源回廊") || button.getMessage().getString().contains("组队/副本")) {
                    if (x > this.width / 2 - 126 && x < this.width / 2 - 126 + 48 && y > this.height / 2 + 113 - 40
                            && y < this.height / 2 + 113 - 40 + 18 + 16) {
                        renderable.render(graphics, x, y, v);
                    } else {
                        guiGraphics.drawString(fontRenderer, Component.literal("「特殊挑战」").withStyle(ChatFormatting.RED),
                                this.width / 2 - 126, this.height / 2 + 113 - 40 + 3, 0);
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
            for (int i = 1; i <= Point.DESCRIPTION.size(); i++) {
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

        guiGraphics.drawCenteredString(fontRenderer, Te.s("理智", CustomStyle.styleOfLife),
                this.width / 2 + 8, this.height / 2 - 20, 0);
        guiGraphics.drawCenteredString(fontRenderer, Te.s(String.valueOf(Reason.clientReasonValue)),
                this.width / 2 + 37, this.height / 2 - 20, 0);


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

        /* - 精通（与能力合并）：五条路径的总点数 - */
        int PowerOffsetX = 100;
        int PowerOffsetY = -84;
        int IntelligentOffsetX = 100;
        int IntelligentOffsetY = -69;
        int FlexibleOffsetX = 100;
        int FlexibleOffsetY = -54;
        int LuckyOffsetX = 100;
        int LuckyOffsetY = -39;
        int VitalityOffsetX = 100;
        int VitalityOffsetY = -24;

        MasteryTree[] summaryTrees = {
                MasteryTree.PRECISION, MasteryTree.DOMINATION, MasteryTree.SORCERY,
                MasteryTree.RESOLVE, MasteryTree.INSPIRATION
        };
        int[] summaryOffsetY = {
                PowerOffsetY, IntelligentOffsetY, FlexibleOffsetY, LuckyOffsetY, VitalityOffsetY
        };
        for (int i = 0; i < summaryTrees.length; i++) {
            MasteryTree tree = summaryTrees[i];
            int total = MasteryAllocation.treeTotal(MasteryClientData.allocation[tree.ordinal()]);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(tree.getDisplayName())
                            .withStyle(Style.EMPTY.withColor(tree.getNameColor())),
                    this.width / 2 + 100, this.height / 2 + summaryOffsetY[i], 0);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.format("%d", total))
                            .withStyle(Style.EMPTY.withColor(tree.getNameColor())),
                    this.width / 2 + 116, this.height / 2 + summaryOffsetY[i], 0);
        }


        guiGraphics.drawCenteredString(fontRenderer, Component.literal("可用精通点 " + MasteryClientData.available())
                .withStyle(CustomStyle.styleOfWorld), this.width / 2 + 100, this.height / 2 + 6, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("专精与能力已合并")
                .withStyle(ChatFormatting.GRAY), this.width / 2 - 72, this.height / 2 + 16, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("点击「精通」分配五条路径")
                .withStyle(ChatFormatting.GRAY), this.width / 2 - 72, this.height / 2 + 28, 0);

        for (int i = 0; i < summaryTrees.length; i++) {
            MasteryTree tree = summaryTrees[i];
            int offsetY = summaryOffsetY[i];
            if (x > this.width / 2 + 90 && x < this.width / 2 + 140
                    && y > this.height / 2 + offsetY - 1 && y < this.height / 2 + offsetY + 10) {
                Style style = Style.EMPTY.withColor(tree.getNameColor());
                int tier = MasteryAllocation.treeTotal(MasteryClientData.allocation[tree.ordinal()]);
                List<Component> components = new ArrayList<>();
                components.add(Component.literal("精通 ").withStyle(ChatFormatting.AQUA)
                        .append(Component.literal("▶" + tree.getDisplayName()).withStyle(ChatFormatting.BOLD).withStyle(style)));
                components.add(Component.literal(tree.getDescription()).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
                ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
                components.add(Component.literal("当前").withStyle(ChatFormatting.WHITE)
                        .append(Component.literal(tree.getDisplayName()).withStyle(style))
                        .append(Component.literal("精通加成（" + tier + " 点）:").withStyle(ChatFormatting.WHITE)));
                appendMasteryAbilityDescription(components, tree, tier);
                ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
                components.add(Component.literal("每一点" + tree.getDisplayName() + "精通获得:").withStyle(ChatFormatting.WHITE));
                appendMasteryAbilityDescription(components, tree, 1);
                ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
                components.add(Component.literal("精通点数在「精通」界面中分配，该路径的总点数即为对应能力。")
                        .withStyle(ChatFormatting.GRAY));
                guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
            }
        }



        for (int i = 0; i < SevenShadePiece.clientActivePieces.size(); i++) {
            ItemStack itemStack = SevenShadePiece.clientActivePieces.get(i).getDefaultInstance();
            int posX = this.width / 2 - 150 + i * 16;
            int posY = this.height / 2 - 116;
            guiGraphics.renderItem(itemStack, posX, posY);
            if (x > posX && x < posX + 16 && y > posY && y < posY + 16) {
                guiGraphics.renderTooltip(font, itemStack, x, y);
            }
        }
        if (RankData.clientPlayerCurrentRankMap.containsKey(ClientUtils.clientPlayer.getUUID())) {
            String rank = RankData.clientPlayerCurrentRankMap.get(ClientUtils.clientPlayer.getUUID());
            List<Component> rankDescription = new ArrayList<>();
            rankDescription.add(Te.s("职级 - ", CustomStyle.styleOfWorld, RankData.getRankName(rank), " ",
                    rank, RankData.rankStyleMap.get(rank)));
            if (!rank.equals("null")) {
                rankDescription.add(Te.s("你当前拥有的职权:", CustomStyle.styleOfWorld));
                rankDescription.addAll(RankData.getRankRightDescription(rank));
            } else {
                rankDescription.add(Te.s("在车站或传送中枢找到贝尔,"));
                rankDescription.add(Te.s("完成", "委托任务", CustomStyle.styleOfWorld,
                        "获得", ModItems.SPECIAL_BOND.get()));
                rankDescription.add(Te.s("来提升自己的职级吧!"));
            }
            rankDescription.add(Te.s(""));
            String nextTierRank = RankData.getNextTierRank(rank);
            rankDescription.add(Te.s("下一职级: ", ChatFormatting.GOLD,
                    RankData.getRankName(nextTierRank), " ",
                    nextTierRank, RankData.rankStyleMap.get(nextTierRank)));
            rankDescription.add(Te.s("下一职级的职权:", CustomStyle.styleOfWorld));
            rankDescription.addAll(RankData.getRankRightDescription(nextTierRank));
            guiGraphics.renderComponentTooltip(font, rankDescription,
                    this.width / 2 - 320, this.height / 2 - 80);
        }
    }

    /**
     * AI-Generated, 2026-10-18
     * 精通路径总点数 -> 旧「能力」五维属性的换算展示。
     */
    private static void appendMasteryAbilityDescription(List<Component> components, MasteryTree tree, double tier) {
        switch (tree) {            case DOMINATION -> {
                ComponentUtils.emojiDescriptionExAttackDamage(components, tier * 2);
                ComponentUtils.emojiDescriptionDamageDirectDecrease(components, tier * 5);
                ComponentUtils.emojiDescriptionToughness(components, tier * 0.4);
            }
            case SORCERY -> {
                ComponentUtils.emojiDescriptionManaAttackDamage(components, tier * 2);
                ComponentUtils.emojiDescriptionMaxMana(components, tier * 10);
            }
            case PRECISION -> {
                if (mc.player != null) {
                    MasteryStats stats = MasteryBonusCache.stats(mc.player);
                    if (stats.attackSpeed() > 0) {
                        components.add(ComponentUtils.AttributeDescription.getAttackSpeed(
                                "+" + pct(stats.attackSpeed())));
                    }
                    if (stats.tenacity() > 0) {
                        components.add(ComponentUtils.AttributeDescription.toughness(
                                "+" + pct(stats.tenacity())));
                    }
                    if (stats.moveSpeed() > 0) {
                        components.add(ComponentUtils.AttributeDescription.movementSpeed(
                                "+" + pct(stats.moveSpeed())));
                    }
                    if (stats.haste() > 0) {
                        components.add(ComponentUtils.AttributeDescription.coolDown(
                                "+" + pct(stats.haste())));
                    }
                    if (stats.omnivamp() > 0) {
                        components.add(ComponentUtils.AttributeDescription.skillHealthSteal(
                                "+" + pct(stats.omnivamp())));
                    }
                    if (stats.armorPen() > 0) {
                        components.add(ComponentUtils.AttributeDescription.defencePenetration(
                                "+" + pct(stats.armorPen())));
                    }
                    if (stats.magicPen() > 0) {
                        components.add(ComponentUtils.AttributeDescription.manaPenetration(
                                "+" + pct(stats.magicPen())));
                    }
                    if (stats.damageDealt() > 0) {
                        components.add(ComponentUtils.AttributeDescription.commonDamage(
                                "+" + pct(stats.damageDealt())));
                    }
                    if (stats.damageTaken() > 0) {
                        components.add(ComponentUtils.AttributeDescription.damageTaken(
                                "+" + pct(stats.damageTaken())));
                    }
                    components.add(Component.literal("详细效果请在精通界面查看")
                            .withStyle(ChatFormatting.DARK_GRAY));
                }
            }
            case INSPIRATION -> {
                ComponentUtils.emojiDescriptionCritRate(components, tier * 0.001);
                ComponentUtils.emojiDescriptionExpUp(components, tier * 0.01);
                ComponentUtils.emojiDescriptionCoolDown(components, tier * 0.01);
            }
            case RESOLVE -> {
                ComponentUtils.emojiDescriptionHealthRecover(components, tier);
                ComponentUtils.emojiDescriptionMaxHealth(components, tier * 40);
                ComponentUtils.emojiDescriptionHealAmplification(components, tier * 0.01);
            }
        }
    }

    private static String pct(double value) {
        return String.format("%.2f%%", value * 100);
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

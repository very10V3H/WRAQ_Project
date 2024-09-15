package com.very.wraq.render.gui.skills;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.SkillPackets.SkillDataC2SPacket;
import com.very.wraq.networking.misc.SkillPackets.SkillRequestC2SPacket;
import com.very.wraq.networking.misc.SkillPackets.SkillSaveC2SPacket;
import com.very.wraq.render.gui.testAndHelper.OpenSkillGui;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
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
import net.minecraftforge.fml.DistExecutor;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SkillTreeGui extends Screen {
    ResourceLocation GUI_TEXTURE = new ResourceLocation(Utils.MOD_ID, "textures/gui/skilltree.png");
    private final boolean showPauseMenu;
    public static final Minecraft mc = Minecraft.getInstance();
    private static final Font fontRenderer = mc.font;

    public SkillTreeGui(boolean p_96308_) {
        super(p_96308_ ? Component.translatable("menu.vmd0") : Component.translatable("menu.vmd1"));
        this.showPauseMenu = p_96308_;
    }

    protected void init() {
        if (this.showPauseMenu) {
            this.showSkillGui();
        }

    }

    private void showSkillGui() {
        int OffsetX = 0;
        int OffsetY = 5;

        int[] DecOffsetX = {
                0, 20, 55, 13, 37, 61
        };

        int[] DecOffsetY = {
                0, 37, 37, 65, 65, 65
        };

        int[] AddOffsetX = {
                0, 34, 69, 27, 51, 75
        };

        int[] AddOffsetY = {
                0, 37, 37, 65, 65, 65
        };

        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
                if (ClientUtils.SwordSkillPoint.PointCache[finalI] > 0) {
                    ClientUtils.SwordSkillPoint.PointCache[finalI]--;
                }
            }).pos(this.width / 2 - 150 + DecOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 1; i <= 2; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword
                        && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(1, 2) < 10) {
                    ClientUtils.SwordSkillPoint.PointCache[finalI]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }
        for (int i = 3; i <= 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword && ClientUtils.SwordSkillPoint.PointCache[finalI] < 10
                        && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(1, 2) == 10 && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(3, 5) < 5) {
                    ClientUtils.SwordSkillPoint.PointCache[finalI]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + AddOffsetY[finalI] + OffsetY).size(7, 7).build());
        }


        OffsetY = 62;

        for (int i = 6; i <= 10; i++) {
            int finalI = i - 5;
            this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
                if (ClientUtils.SwordSkillPoint.PointCache[finalI + 5] > 0) {
                    ClientUtils.SwordSkillPoint.PointCache[finalI + 5]--;
                }
            }).pos(this.width / 2 - 150 + DecOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 6; i <= 7; i++) {
            int finalI = i - 5;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword
                        && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(3, 5) == 5
                        && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(6, 7) < 10) {
                    ClientUtils.SwordSkillPoint.PointCache[finalI + 5]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 8; i <= 10; i++) {
            int finalI = i - 5;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword
                        && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(6, 7) == 10
                        && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(8, 10) < 5) {
                    ClientUtils.SwordSkillPoint.PointCache[finalI + 5]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }


        OffsetY = 119;

        for (int i = 11; i <= 15; i++) {
            int finalI = i - 10;
            this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
                if (ClientUtils.SwordSkillPoint.PointCache[finalI + 10] > 0) {
                    ClientUtils.SwordSkillPoint.PointCache[finalI + 10]--;
                }
            }).pos(this.width / 2 - 150 + DecOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 11; i <= 12; i++) {
            int finalI = i - 10;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword
                        && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(8, 10) == 5
                        && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(11, 12) < 10) {
                    ClientUtils.SwordSkillPoint.PointCache[finalI + 10]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 13; i <= 15; i++) {
            int finalI = i - 10;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword
                        && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(11, 12) == 10
                        && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(13, 15) < 5) {
                    ClientUtils.SwordSkillPoint.PointCache[finalI + 10]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }


        OffsetX = 86;
        OffsetY = 5;

        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
                if (ClientUtils.BowSkillPoint.PointCache[finalI] > 0) {
                    ClientUtils.BowSkillPoint.PointCache[finalI]--;
                }
            }).pos(this.width / 2 - 150 + DecOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 1; i <= 2; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow
                        && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(1, 2) < 10) {
                    ClientUtils.BowSkillPoint.PointCache[finalI]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }
        for (int i = 3; i <= 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow && ClientUtils.BowSkillPoint.PointCache[finalI] < 10
                        && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(1, 2) == 10 && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(3, 5) < 5) {
                    ClientUtils.BowSkillPoint.PointCache[finalI]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + AddOffsetY[finalI] + OffsetY).size(7, 7).build());
        }


        OffsetY = 62;

        for (int i = 6; i <= 10; i++) {
            int finalI = i - 5;
            this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
                if (ClientUtils.BowSkillPoint.PointCache[finalI + 5] > 0) {
                    ClientUtils.BowSkillPoint.PointCache[finalI + 5]--;
                }
            }).pos(this.width / 2 - 150 + DecOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 6; i <= 7; i++) {
            int finalI = i - 5;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow
                        && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(3, 5) == 5
                        && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(6, 7) < 10) {
                    ClientUtils.BowSkillPoint.PointCache[finalI + 5]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 8; i <= 10; i++) {
            int finalI = i - 5;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow
                        && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(6, 7) == 10
                        && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(8, 10) < 5) {
                    ClientUtils.BowSkillPoint.PointCache[finalI + 5]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        OffsetY = 119;

        for (int i = 11; i <= 15; i++) {
            int finalI = i - 10;
            this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
                if (ClientUtils.BowSkillPoint.PointCache[finalI + 10] > 0) {
                    ClientUtils.BowSkillPoint.PointCache[finalI + 10]--;
                }
            }).pos(this.width / 2 - 150 + DecOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 11; i <= 12; i++) {
            int finalI = i - 10;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow
                        && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(8, 10) == 5
                        && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(11, 12) < 10) {
                    ClientUtils.BowSkillPoint.PointCache[finalI + 10]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 13; i <= 15; i++) {
            int finalI = i - 10;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow
                        && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(11, 12) == 10
                        && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(13, 15) < 5) {
                    ClientUtils.BowSkillPoint.PointCache[finalI + 10]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        OffsetX = 172;
        OffsetY = 5;

        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
                if (ClientUtils.ManaSkillPoint.PointCache[finalI] > 0) {
                    ClientUtils.ManaSkillPoint.PointCache[finalI]--;
                }
            }).pos(this.width / 2 - 150 + DecOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 1; i <= 2; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana
                        && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(1, 2) < 10) {
                    ClientUtils.ManaSkillPoint.PointCache[finalI]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }
        for (int i = 3; i <= 5; i++) {
            int finalI = i;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana && ClientUtils.ManaSkillPoint.PointCache[finalI] < 10
                        && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(1, 2) == 10 && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(3, 5) < 5) {
                    ClientUtils.ManaSkillPoint.PointCache[finalI]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + AddOffsetY[finalI] + OffsetY).size(7, 7).build());
        }


        OffsetY = 62;

        for (int i = 6; i <= 10; i++) {
            int finalI = i - 5;
            this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
                if (ClientUtils.ManaSkillPoint.PointCache[finalI + 5] > 0) {
                    ClientUtils.ManaSkillPoint.PointCache[finalI + 5]--;
                }
            }).pos(this.width / 2 - 150 + DecOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 6; i <= 7; i++) {
            int finalI = i - 5;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana
                        && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(3, 5) == 5
                        && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(6, 7) < 10) {
                    ClientUtils.ManaSkillPoint.PointCache[finalI + 5]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 8; i <= 10; i++) {
            int finalI = i - 5;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana
                        && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(6, 7) == 10
                        && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(8, 10) < 5) {
                    ClientUtils.ManaSkillPoint.PointCache[finalI + 5]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }


        OffsetY = 119;

        for (int i = 11; i <= 15; i++) {
            int finalI = i - 10;
            this.addRenderableWidget(Button.builder(Component.translatable("-"), (p_280814_) -> {
                if (ClientUtils.ManaSkillPoint.PointCache[finalI + 10] > 0) {
                    ClientUtils.ManaSkillPoint.PointCache[finalI + 10]--;
                }
            }).pos(this.width / 2 - 150 + DecOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 11; i <= 12; i++) {
            int finalI = i - 10;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana
                        && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(8, 10) == 5
                        && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(11, 12) < 10) {
                    ClientUtils.ManaSkillPoint.PointCache[finalI + 10]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        for (int i = 13; i <= 15; i++) {
            int finalI = i - 10;
            this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
                if (ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana
                        && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(11, 12) == 10
                        && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(13, 15) < 5) {
                    ClientUtils.ManaSkillPoint.PointCache[finalI + 10]++;
                }
            }).pos(this.width / 2 - 150 + AddOffsetX[finalI] + OffsetX, this.height / 2 - 100 + DecOffsetY[finalI] + OffsetY).size(7, 7).build());
        }

        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            String SwordSkillData = "", BowSkillData = "", ManaSkillData = "";
            for (int i = 1; i <= 15; i++) {
                if (ClientUtils.SwordSkillPoint.PointCache[i] == 10) {
                    SwordSkillData = SwordSkillData + 'X';
                } else SwordSkillData = SwordSkillData + ClientUtils.SwordSkillPoint.PointCache[i];

                if (ClientUtils.BowSkillPoint.PointCache[i] == 10) {
                    BowSkillData = BowSkillData + 'X';
                } else BowSkillData = BowSkillData + ClientUtils.BowSkillPoint.PointCache[i];

                if (ClientUtils.ManaSkillPoint.PointCache[i] == 10) {
                    ManaSkillData = ManaSkillData + 'X';
                } else ManaSkillData = ManaSkillData + ClientUtils.ManaSkillPoint.PointCache[i];
            }
            ModNetworking.sendToServer(new SkillSaveC2SPacket(SwordSkillData, BowSkillData, ManaSkillData));
            ModNetworking.sendToServer(new SkillDataC2SPacket(ClientUtils.SkillChangeCache.Sword, ClientUtils.SkillChangeCache.Bow,
                    ClientUtils.SkillChangeCache.Mana));
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
        }).pos(this.width / 2 - 150 + 265, this.height / 2 - 100 + 20).size(27, 22).build());

        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            ClientUtils.SwordSkillPoint.SwordSkillPointRangeClear(1, 15);
            ClientUtils.BowSkillPoint.BowSkillPointRangeClear(1, 15);
            ClientUtils.ManaSkillPoint.ManaSkillPointRangeClear(1, 15);
            ModNetworking.sendToServer(new SkillSaveC2SPacket("", "", ""));
        }).pos(this.width / 2 - 150 + 265, this.height / 2 - 100 + 49).size(27, 22).build());

        this.addRenderableWidget(Button.builder(Component.translatable("+"), (p_280814_) -> {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
            DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> OpenSkillGui::new);
        }).pos(this.width / 2 - 150 + 265, this.height / 2 - 100 + 77).size(27, 22).build());

    }

    public void tick() {
        ClientUtils.SwordSkillPoint.SwordSkillPointCacheCheck();
        ClientUtils.BowSkillPoint.BowSkillPointCacheCheck();
        ClientUtils.ManaSkillPoint.ManaSkillPointCacheCheck();
        super.tick();
    }

    public void render(GuiGraphics p_96310_, int x, int y, float p_96313_) {
        GuiGraphics guiGraphics = new GuiGraphics(mc, mc.renderBuffers().bufferSource());
        this.renderBackground(guiGraphics);
        ResourceLocation allowAdd = new ResourceLocation(Utils.MOD_ID, "textures/gui/allowadd.png");
        ResourceLocation allowDec = new ResourceLocation(Utils.MOD_ID, "textures/gui/allowdec.png");
        ResourceLocation emptySword = new ResourceLocation(Utils.MOD_ID, "textures/gui/emptysword.png");

        ResourceLocation allowAddBow = new ResourceLocation(Utils.MOD_ID, "textures/gui/allowaddbow.png");
        ResourceLocation allowDecBow = new ResourceLocation(Utils.MOD_ID, "textures/gui/allowdecbow.png");
        ResourceLocation emptyBow = new ResourceLocation(Utils.MOD_ID, "textures/gui/emptybow.png");

        ResourceLocation allowAddMana = new ResourceLocation(Utils.MOD_ID, "textures/gui/allowaddmana.png");
        ResourceLocation allowDecMana = new ResourceLocation(Utils.MOD_ID, "textures/gui/allowdecmana.png");
        ResourceLocation emptyMana = new ResourceLocation(Utils.MOD_ID, "textures/gui/emptymana.png");

        int textureWidth = 300;
        int textureHeight = 200;

        guiGraphics.blit(GUI_TEXTURE, this.width / 2 - 150, this.height / 2 - 100, 0, 0, 300, 200, textureWidth, textureHeight);

        ClientUtils.SwordSkillsResource.init();
        ClientUtils.ManaSkillsResource.init();
        ClientUtils.BowSkillsResource.init();

        int[] OffsetX = {
                0, 21, 56, 14, 38, 62, 21, 56, 14, 38, 62, 21, 56, 14, 38, 62
        };
        int[] OffsetY = {
                0, 17, 17, 45, 45, 45, 74, 74, 102, 102, 102, 131, 131, 159, 159, 159
        };

        for (int i = 1; i <= 15; i++) {
            OffsetY[i] += 5;
        }

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("剑术精通").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfPower), this.width / 2 - 150 + 31, this.height / 2 - 100 + 11, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() + " / " + ClientUtils.SkillChangeCache.Sword).withStyle(CustomStyle.styleOfPower), this.width / 2 - 150 + 69, this.height / 2 - 100 + 11, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("弓术精通").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfFlexible), this.width / 2 - 150 + 31 + 86, this.height / 2 - 100 + 11, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(ClientUtils.BowSkillPoint.BowSkillPointCacheCount() + " / " + ClientUtils.SkillChangeCache.Bow).withStyle(CustomStyle.styleOfFlexible), this.width / 2 - 150 + 69 + 86, this.height / 2 - 100 + 11, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("法术精通").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfMana), this.width / 2 - 150 + 31 + 172, this.height / 2 - 100 + 11, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal(ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() + " / " + ClientUtils.SkillChangeCache.Mana).withStyle(CustomStyle.styleOfMana), this.width / 2 - 150 + 69 + 172, this.height / 2 - 100 + 11, 0);

        guiGraphics.drawCenteredString(fontRenderer, Component.literal("保存").withStyle(ChatFormatting.AQUA), this.width / 2 - 150 + 278, this.height / 2 - 100 + 25, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("重置").withStyle(ChatFormatting.AQUA), this.width / 2 - 150 + 278, this.height / 2 - 100 + 54, 0);
        guiGraphics.drawCenteredString(fontRenderer, Component.literal("返回").withStyle(ChatFormatting.AQUA), this.width / 2 - 150 + 278, this.height / 2 - 100 + 82, 0);


        for (int i = 1; i <= 15; i++) {
            if (ClientUtils.SwordSkillPoint.PointCache[i] > 0) {
                guiGraphics.blit(ClientUtils.SwordSkillsResource.resourceLocations[i], this.width / 2 - 150 + OffsetX[i], this.height / 2 - 100 + OffsetY[i], 0, 0, 19, 19, 19, 19);
                guiGraphics.blit(allowDec, this.width / 2 - 150 + OffsetX[i] - 1, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else {
                guiGraphics.blit(ClientUtils.SwordSkillsResource.resourceLocations0[i], this.width / 2 - 150 + OffsetX[i], this.height / 2 - 100 + OffsetY[i], 0, 0, 19, 19, 19, 19);
            }

            if (ClientUtils.BowSkillPoint.PointCache[i] > 0) {
                guiGraphics.blit(ClientUtils.BowSkillsResource.resourceLocations[i], this.width / 2 - 150 + OffsetX[i] + 86, this.height / 2 - 100 + OffsetY[i], 0, 0, 19, 19, 19, 19);
                guiGraphics.blit(allowDecBow, this.width / 2 - 150 + OffsetX[i] - 1 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else {
                guiGraphics.blit(ClientUtils.BowSkillsResource.resourceLocations0[i], this.width / 2 - 150 + OffsetX[i] + 86, this.height / 2 - 100 + OffsetY[i], 0, 0, 19, 19, 19, 19);
            }

            if (ClientUtils.ManaSkillPoint.PointCache[i] > 0) {
                guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations[i], this.width / 2 - 150 + OffsetX[i] + 172, this.height / 2 - 100 + OffsetY[i], 0, 0, 19, 19, 19, 19);
                guiGraphics.blit(allowDecMana, this.width / 2 - 150 + OffsetX[i] - 1 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else {
                guiGraphics.blit(ClientUtils.ManaSkillsResource.resourceLocations0[i], this.width / 2 - 150 + OffsetX[i] + 172, this.height / 2 - 100 + OffsetY[i], 0, 0, 19, 19, 19, 19);
            }


            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.valueOf(ClientUtils.SwordSkillPoint.PointCache[i])).withStyle(CustomStyle.styleOfPower), this.width / 2 - 150 + 10 + OffsetX[i], this.height / 2 - 100 + 19 + OffsetY[i], 0);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.valueOf(ClientUtils.BowSkillPoint.PointCache[i])).withStyle(CustomStyle.styleOfFlexible), this.width / 2 - 150 + 10 + OffsetX[i] + 86, this.height / 2 - 100 + 19 + OffsetY[i], 0);
            guiGraphics.drawCenteredString(fontRenderer, Component.literal(String.valueOf(ClientUtils.ManaSkillPoint.PointCache[i])).withStyle(CustomStyle.styleOfMana), this.width / 2 - 150 + 10 + OffsetX[i] + 172, this.height / 2 - 100 + 19 + OffsetY[i], 0);


            if (i <= 2 && ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword
                    && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(1, 2) < 10) {
                guiGraphics.blit(allowAdd, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 5 && ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword
                    && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(3, 5) < 5
                    && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(1, 2) == 10) {
                guiGraphics.blit(allowAdd, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 7 && ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword
                    && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(6, 7) < 10
                    && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(3, 5) == 5) {
                guiGraphics.blit(allowAdd, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 10 && ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword
                    && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(8, 10) < 5
                    && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(6, 7) == 10) {
                guiGraphics.blit(allowAdd, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 12 && ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword
                    && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(11, 12) < 10
                    && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(8, 10) == 5) {
                guiGraphics.blit(allowAdd, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (ClientUtils.SwordSkillPoint.SwordSkillPointCacheCount() < ClientUtils.SkillChangeCache.Sword
                    && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(13, 15) < 5
                    && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(11, 12) == 10) {
                guiGraphics.blit(allowAdd, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            }

            if (i <= 2 && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(1, 2) == 10) {
                guiGraphics.blit(emptySword, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 5 && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(3, 5) == 5) {
                guiGraphics.blit(emptySword, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 7 && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(6, 7) == 10) {
                guiGraphics.blit(emptySword, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 10 && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(8, 10) == 5) {
                guiGraphics.blit(emptySword, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 12 && ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(11, 12) == 10) {
                guiGraphics.blit(emptySword, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (ClientUtils.SwordSkillPoint.SwordSkillPointCacheRangeCount(13, 15) == 5) {
                guiGraphics.blit(emptySword, this.width / 2 - 150 + OffsetX[i] + 13, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            }


            if (i <= 2 && ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow
                    && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(1, 2) < 10) {
                guiGraphics.blit(allowAddBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 5 && ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow
                    && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(3, 5) < 5
                    && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(1, 2) == 10) {
                guiGraphics.blit(allowAddBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 7 && ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow
                    && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(6, 7) < 10
                    && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(3, 5) == 5) {
                guiGraphics.blit(allowAddBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 10 && ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow
                    && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(8, 10) < 5
                    && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(6, 7) == 10) {
                guiGraphics.blit(allowAddBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 12 && ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow
                    && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(11, 12) < 10
                    && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(8, 10) == 5) {
                guiGraphics.blit(allowAddBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (ClientUtils.BowSkillPoint.BowSkillPointCacheCount() < ClientUtils.SkillChangeCache.Bow
                    && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(13, 15) < 5
                    && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(11, 12) == 10) {
                guiGraphics.blit(allowAddBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            }

            if (i <= 2 && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(1, 2) == 10) {
                guiGraphics.blit(emptyBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 5 && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(3, 5) == 5) {
                guiGraphics.blit(emptyBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 7 && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(6, 7) == 10) {
                guiGraphics.blit(emptyBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 10 && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(8, 10) == 5) {
                guiGraphics.blit(emptyBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 12 && ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(11, 12) == 10) {
                guiGraphics.blit(emptyBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (ClientUtils.BowSkillPoint.BowSkillPointCacheRangeCount(13, 15) == 5) {
                guiGraphics.blit(emptyBow, this.width / 2 - 150 + OffsetX[i] + 13 + 86, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            }

            if (i <= 2 && ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana
                    && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(1, 2) < 10) {
                guiGraphics.blit(allowAddMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 5 && ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana
                    && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(3, 5) < 5
                    && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(1, 2) == 10) {
                guiGraphics.blit(allowAddMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 7 && ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana
                    && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(6, 7) < 10
                    && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(3, 5) == 5) {
                guiGraphics.blit(allowAddMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 10 && ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana
                    && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(8, 10) < 5
                    && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(6, 7) == 10) {
                guiGraphics.blit(allowAddMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 12 && ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana
                    && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(11, 12) < 10
                    && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(8, 10) == 5) {
                guiGraphics.blit(allowAddMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (ClientUtils.ManaSkillPoint.ManaSkillPointCacheCount() < ClientUtils.SkillChangeCache.Mana
                    && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(13, 15) < 5
                    && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(11, 12) == 10) {
                guiGraphics.blit(allowAddMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            }

            if (i <= 2 && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(1, 2) == 10) {
                guiGraphics.blit(emptyMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 5 && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(3, 5) == 5) {
                guiGraphics.blit(emptyMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 7 && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(6, 7) == 10) {
                guiGraphics.blit(emptyMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 10 && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(8, 10) == 5) {
                guiGraphics.blit(emptyMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (i <= 12 && ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(11, 12) == 10) {
                guiGraphics.blit(emptyMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            } else if (ClientUtils.ManaSkillPoint.ManaSkillPointCacheRangeCount(13, 15) == 5) {
                guiGraphics.blit(emptyMana, this.width / 2 - 150 + OffsetX[i] + 13 + 172, this.height / 2 - 100 + OffsetY[i] + 20, 0, 0, 7, 7, 7, 7);
            }

        }
        int index = 1;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-剑术热诚").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("你的近战攻击额外造成").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.AttackDamage(ClientUtils.SwordSkillPoint.PointCache[index] + "%")).
                    append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("真实伤害。").withStyle(CustomStyle.styleOfSea)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 2;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-燃烧宝石").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("持有近战武器时，削减").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + "%").withStyle(CustomStyle.styleOfPower)).
                    append(Component.literal("来自怪物的伤害").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 3;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-战斗渴望").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("击杀一个单位时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ExAttackDamage(ClientUtils.SwordSkillPoint.PointCache[index] * 2 + "%")).
                    append(Component.literal("，持续10s。").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 4;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-破绽观察").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("对一名目标的持续攻击，可以使你对该目标的伤害至多提升至").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] * 2 + "%").withStyle(CustomStyle.styleOfPower)).
                    append(Component.literal("，在10次攻击后达到最大值。").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 5;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-双刃剑").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("手持近战武器时额外造成").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] * 3 + "%").withStyle(CustomStyle.styleOfPower)).
                    append(Component.literal("的伤害值，额外受到").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] * 1.5 + "%").withStyle(CustomStyle.styleOfPower)).
                    append(Component.literal("伤害值。").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 6;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-狂暴").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("造成暴击后，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ExAttackDamage(ClientUtils.SwordSkillPoint.PointCache[index] * 1.5 + "%")).
                    append(Component.literal(",持续3s").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 7;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-完美").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("持续造成暴击，将获得至多").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ExAttackDamage(ClientUtils.SwordSkillPoint.PointCache[index] * 3 + "%")).
                    append(Component.literal(",持续10s，在十次暴击后达最大值，在未造成暴击时重置层数。").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 8;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-冷静").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("手持近战武器时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.CoolDown(String.valueOf(ClientUtils.SwordSkillPoint.PointCache[index] * 3))));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 9;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-洞悉").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("手持近战武器时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ManaRecover(ClientUtils.SwordSkillPoint.PointCache[index] + "")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 10;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-剑舞").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("手持近战武器时，获得").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.movementSpeed(ClientUtils.SwordSkillPoint.PointCache[index] * 5 + "%")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 11;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-残暴").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("手持近战武器时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.DefencePenetration("[玩家等级 * 0.5 * " + ClientUtils.SwordSkillPoint.PointCache[index] + "] ")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 12;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-刺击").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("手持近战武器时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.AttackRange(ClientUtils.SwordSkillPoint.PointCache[index] * 0.2 + "")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 13;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-刀光剑影").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("移动、攻击将会获得充能，当充能满时，").withStyle(ChatFormatting.WHITE));
            components.add(Component.literal("下一次攻击将造成额外").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] * 40 + "%").withStyle(CustomStyle.styleOfPower)).
                    append(Component.literal("伤害值。").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("并对以自身为中心一定范围的目标造成一次").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] * 20 + "%").withStyle(CustomStyle.styleOfPower)).
                    append(Component.literal("攻击特效。").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 14;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-战争热诚").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("攻击将会提供1层充能，暴击提供2层充能，持续6秒，至多可叠加至12层。").withStyle(ChatFormatting.WHITE));
            components.add(Component.literal("每层充能将会提升").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] * 0.25 + "%").withStyle(CustomStyle.styleOfPower)).
                    append(Component.literal("额外伤害值，并获得等同于额外伤害的治疗量。").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 15;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 && x < this.width / 2 - 150 + OffsetX[index] + 19
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲剑术专精-恃强凌弱").withStyle(CustomStyle.styleOfPower));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("对生命值百分比低于你的目标造成至多").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] * 5 + "%").withStyle(CustomStyle.styleOfPower)).
                    append(Component.literal("额外伤害值，在百分比差值达66%时达到最大值。").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("当受到生命值百分比高于你的目标的伤害使伤害额外提升同样的数值").withStyle(ChatFormatting.WHITE));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfPower, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfPower));
            components.add(Component.literal(ClientUtils.SwordSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfPower));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 1;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-弓术热诚").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("你的箭矢额外造成").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.AttackDamage(ClientUtils.BowSkillPoint.PointCache[index] + "%")).
                    append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("真实伤害。").withStyle(CustomStyle.styleOfSea)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-原野护符").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("手持弓时，获得").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.movementSpeedWithoutBattle(ClientUtils.BowSkillPoint.PointCache[index] * 3 + "%")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-狩猎渴望").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("箭矢击杀一个单位时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ExAttackDamage(ClientUtils.BowSkillPoint.PointCache[index] * 2 + "%")).
                    append(Component.literal("，持续10s。").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-习惯获取").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("对一名目标的持续攻击，可以使你对该目标的伤害至多提升").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] * 2 + "%").withStyle(CustomStyle.styleOfFlexible)));
            components.add(Component.literal("在三次攻击后达到最大值。").withStyle(ChatFormatting.WHITE));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-专注训练").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("手持弓时额外造成").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] * 3 + "%").withStyle(CustomStyle.styleOfFlexible)).
                    append(Component.literal("伤害，额外受到").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] * 1.5 + "%").withStyle(CustomStyle.styleOfFlexible)).
                    append(Component.literal("伤害。").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-狂暴").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("造成暴击后，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ExAttackDamage(ClientUtils.BowSkillPoint.PointCache[index] + "%")).
                    append(Component.literal("，持续5s。").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-完美").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("持续命中目标的箭矢，将获得至多").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ExAttackDamage(ClientUtils.BowSkillPoint.PointCache[index] * 3 + "%")).
                    append(Component.literal("，持续10s。").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("在三次命中后达最大值，在未命中时重置层数。").withStyle(ChatFormatting.WHITE));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-锻矢-锋利").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("手持弓时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.CritDamage(ClientUtils.BowSkillPoint.PointCache[index] * 10 + "%")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-锻弦-力量").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("手持弓时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ExAttackDamage(ClientUtils.BowSkillPoint.PointCache[index] * 2 + "%")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-猎手本能").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("手持弓时，获得").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.movementSpeedWithoutBattle(ClientUtils.BowSkillPoint.PointCache[index] * 8 + "%")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-锻矢-材质").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("手持弓时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.DefencePenetration("[玩家等级 * 0.5 * " + ClientUtils.BowSkillPoint.PointCache[index] + "] ")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-锻弦-平衡").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("手持弓时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.CritRate(ClientUtils.BowSkillPoint.PointCache[index] * 2 + "%")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-盈能攻击").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("移动、攻击将会获得充能，当充能满时").withStyle(ChatFormatting.WHITE));
            components.add(Component.literal("下一次攻击将造成").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.AttackDamage(ClientUtils.BowSkillPoint.PointCache[index] * 40 + "%")).
                    append(Component.literal("的额外伤害").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("并在以目标为中心的范围内造成").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.AttackDamage(ClientUtils.BowSkillPoint.PointCache[index] * 80 + "%")).
                    append(Component.literal("的额外伤害").withStyle(ChatFormatting.WHITE)));
            components.add(ComponentUtils.getCritDamageInfluenceDescription());
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-箭雨").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("每过10s，下次攻击将在目标处造成形成箭雨。").withStyle(ChatFormatting.WHITE));
            components.add(Component.literal("箭雨将对范围内的敌人造成").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] * 100 + "%")).
                    append(Component.literal("伤害值。").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 86 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 86
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲弓术专精-全神贯注").withStyle(CustomStyle.styleOfFlexible));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("完成一次攻击后，基于时间将在下次攻击额外造成至多").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.AttackDamage(ClientUtils.BowSkillPoint.PointCache[index] * 200 + "%")).
                    append(Component.literal("的额外伤害。").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("在攻击间隔时间大于20s后达到最大值。").withStyle(ChatFormatting.WHITE));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfFlexible, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfFlexible));
            components.add(Component.literal(ClientUtils.BowSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfFlexible));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 1;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-法术热诚").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("你的法术攻击额外造成").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.ManaDamage(ClientUtils.ManaSkillPoint.PointCache[index] + "%")).
                    append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("真实伤害。").withStyle(CustomStyle.styleOfSea)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 2;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-仙女护符").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("持有法杖时，额外获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ManaRecover(ClientUtils.ManaSkillPoint.PointCache[index] * 0.5 + "")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 3;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-魔力汲取").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("击杀一个单位时，提升").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.ManaDamage(ClientUtils.ManaSkillPoint.PointCache[index] * 2 + "%")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 4;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-机体解构").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("对一名目标的持续法术攻击，可以使你对该目标的伤害至多提升至").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] * 2 + "%").withStyle(CustomStyle.styleOfMana)));
            components.add(Component.literal("在5次攻击后达到最大值。").withStyle(ChatFormatting.WHITE));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index = 5;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-法术专注").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("手持法杖时额外造成").withStyle(ChatFormatting.WHITE).
                    append(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] * 3 + "%").withStyle(CustomStyle.styleOfMana)).
                    append(Component.literal("伤害值。").withStyle(CustomStyle.styleOfMana)).
                    append(Component.literal("额外受到").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] * 1.5 + "%").withStyle(CustomStyle.styleOfMana)).
                    append(Component.literal("伤害值。").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-危机意识").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("当生命值低于80%时，造成").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.ManaDamage(ClientUtils.ManaSkillPoint.PointCache[index] * 2 + "%")).
                    append(Component.literal("的额外真实伤害").withStyle(CustomStyle.styleOfSea)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-完美").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("持续命中目标，将至多造成").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.ManaDamage(ClientUtils.ManaSkillPoint.PointCache[index] * 2 + "%")).
                    append(Component.literal("的额外真实伤害").withStyle(CustomStyle.styleOfSea)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-冷静").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("手持法杖时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.CoolDown(String.valueOf(ClientUtils.ManaSkillPoint.PointCache[index] * 6))));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-洞悉").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("手持法杖时，获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ManaRecover(ClientUtils.ManaSkillPoint.PointCache[index] + "")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-魔法扫帚").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("手持法杖时，获得").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.movementSpeed(ClientUtils.ManaSkillPoint.PointCache[index] * 5 + "%")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-力凝魔核").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("手持法杖时:"));
            components.add(Component.literal(" ·将你的").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ManaRecover(ClientUtils.ManaSkillPoint.PointCache[index] * 10 + "%")).
                    append(Component.literal("转化为").withStyle(ChatFormatting.WHITE)).
                    append(Compute.AttributeDescription.CritRate("")));
            components.add(Component.literal(" ·减少你的").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.MaxMana(ClientUtils.ManaSkillPoint.PointCache[index] * 10 + "%最大")));
            components.add(Component.literal(" ·将你的").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.MaxMana(ClientUtils.ManaSkillPoint.PointCache[index] * 3 + "%额外")).
                    append(Component.literal("转化为").withStyle(ChatFormatting.WHITE)).
                    append(Compute.AttributeDescription.CritDamage("")));
            components.add(Component.literal(" ·你每拥有").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.CoolDown("100")).
                    append(Component.literal("，为你提供").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("15%魔法伤害提升").withStyle(CustomStyle.styleOfMana)));
            components.add(Component.literal(" ·你的").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("普通法球攻击").withStyle(ChatFormatting.LIGHT_PURPLE)).
                    append(Component.literal("将受到").withStyle(ChatFormatting.WHITE)).
                    append(Compute.AttributeDescription.CritRate("")).
                    append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                    append(Compute.AttributeDescription.CritDamage("")).
                    append(Component.literal(String.format("%.0f%%", ClientUtils.ManaSkillPoint.PointCache[index] * 12.5)).withStyle(ChatFormatting.LIGHT_PURPLE)).
                    append(Component.literal("提供的法球攻击增幅。").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal(" ·你的普通法球攻击将不会消耗").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.MaxMana("")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("Idea From:liulixian_").withStyle(ChatFormatting.LIGHT_PURPLE));

            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();

            components.add(Component.literal("▲法术专精-术法全析").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("当你手持法杖时，你的法球将失去基础伤害。").withStyle(ChatFormatting.WHITE));
            components.add(Component.literal("但是，你将获得:").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.CoolDown(String.valueOf(ClientUtils.ManaSkillPoint.PointCache[index] * 5))).
                    append(Component.literal("以及").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] * 10 + "%法术伤害提升").withStyle(ChatFormatting.LIGHT_PURPLE)));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("Idea From:liulixian_").withStyle(ChatFormatting.LIGHT_PURPLE));
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 10).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-盈能攻击").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("移动、攻击将会获得充能，当充能满时").withStyle(ChatFormatting.WHITE));
            components.add(Component.literal("下一次攻击将额外造成").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.ManaDamage(ClientUtils.ManaSkillPoint.PointCache[index] * 40 + "%")).
                    append(Component.literal("的伤害").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("并在以目标为中心的范围内造成").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.ManaDamage(ClientUtils.ManaSkillPoint.PointCache[index] * 100 + "%")).
                    append(Component.literal("的额外伤害").withStyle(ChatFormatting.WHITE)));
            components.add(ComponentUtils.getCritDamageInfluenceDescription());
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-法术收集").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("移动、攻击将会获得充能，当充能满时").withStyle(ChatFormatting.WHITE));
            components.add(Component.literal("下一次攻击将基于目标周围实体数量提供至多").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.ManaDamage(ClientUtils.ManaSkillPoint.PointCache[index] * 200 + "%")).
                    append(Component.literal("的范围伤害").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("并回复自身").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.MaxMana(ClientUtils.ManaSkillPoint.PointCache[index] * 5 + "%已损失")));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

        index++;
        if (x > this.width / 2 - 150 + OffsetX[index] - 1 + 172 && x < this.width / 2 - 150 + OffsetX[index] + 19 + 172
                && y > this.height / 2 - 100 + OffsetY[index] - 1 && y < this.height / 2 - 100 + OffsetY[index] + 19) {
            List<Component> components = new ArrayList<>();
            components.add(Component.literal("▲法术专精-应急激化").withStyle(CustomStyle.styleOfMana));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("当你的生命值小于50%时，受到伤害将对周围的单位造成至多").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.ManaDamage(ClientUtils.ManaSkillPoint.PointCache[index] * 1000 + "%")).
                    append(Component.literal("的法术伤害").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("并获得基于周围实体数量的护盾数量").withStyle(ChatFormatting.WHITE));
            Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfMana, ChatFormatting.WHITE);
            components.add(Component.literal("▶专精等级:").withStyle(CustomStyle.styleOfMana));
            components.add(Component.literal(ClientUtils.ManaSkillPoint.PointCache[index] + " / " + 5).withStyle(CustomStyle.styleOfMana));
            guiGraphics.renderComponentTooltip(fontRenderer, components, x, y);
        }

    }

    @Override
    public void onClose() {
        ClientUtils.SwordSkillPoint.SwordSkillPointRangeClear(1, 15);
        ClientUtils.BowSkillPoint.BowSkillPointRangeClear(1, 15);
        ClientUtils.ManaSkillPoint.ManaSkillPointRangeClear(1, 15);
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

package com.very.wraq.series.worldsoul;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class SoulEquipAttribute {
    public static class BaseAttribute {
        public static class SoulSword {
            public static double AttackDamage = 512;
            public static double CritRate = 0.4;
            public static double CritDamage = 0.55;
            public static double DefencePenetration = 0.3;
            public static double DefencePenetration0 = 1200;
            public static double HealthSteal = 0.04;
            public static double MovementSpeed = 0.4;
        }

        public static class SoulBow {
            public static double AttackDamage = 512;
            public static double CritRate = 0.2;
            public static double CritDamage = 1.05;
            public static double DefencePenetration = 0.3;
            public static double DefencePenetration0 = 1200;
            public static double MovementSpeed = 0.4;
        }

        public static class SoulSceptre {
            public static double ManaAttackDamage = 1280;
            public static double ManaCost = 60;
            public static double ManaRecover = 15;
            public static double ManaPenetration = 0.4;
            public static double ManaPenetration0 = 1200;
            public static double MovementSpeed = 0.4;
            public static double MaxMana = 40;
        }
    }

    public static class ForgingAddition {
        public static double AttackDamage = 16;
        public static double CritRate = 0.02;
        public static double CritDamage = 0.16;
        public static double DefencePenetration = 0.04;
        public static double DefencePenetration0 = 64;
        public static double HealthSteal = 0.01;
        public static double MovementSpeed = 0.04;
        public static double ManaAttackDamage = 64;
        public static double ManaRecover = 4;
        public static double ManaPenetration = 0.04;
        public static double ManaPenetration0 = 16;
        public static double ManaCost = 1;
        public static double MaxMana = 4;
    }

    public static void Forging(ItemStack itemStack, Player player) throws IOException {

        if (Compute.IsSoulEquip(itemStack)) {
            AtomicBoolean IsNearSoulBlock = new AtomicBoolean(false);
            Utils.WorldEntropyPos.forEach(worldEntropy -> {
                if (worldEntropy.getVec3().subtract(player.position()).length() < 10) IsNearSoulBlock.set(true);
            });
            if (!IsNearSoulBlock.get()) return;

            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            int CurrentLevel = 0;
            Inventory inventory = player.getInventory();
            Item item = ModItems.WorldSoul3.get();
            if (data.contains(StringUtils.SoulEquipForge)) {
                CurrentLevel = data.getInt(StringUtils.SoulEquipForge);
            }
            if (CurrentLevel < 5) {
                if (Compute.ItemStackCheck(inventory, item, 2)) {
                    Compute.itemStackRemove(inventory, item, 2);
                    data.putInt(StringUtils.SoulEquipForge, CurrentLevel + 1);
                    Compute.formatBroad(player.level(), Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("").withStyle(ChatFormatting.WHITE).
                                    append(player.getDisplayName()).
                                    append(Component.literal("将").withStyle(ChatFormatting.WHITE)).
                                    append(itemStack.getDisplayName()).
                                    append(Component.literal("强化至了").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal("->").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal("" + (CurrentLevel + 1)).withStyle(CustomStyle.styleOfWorld)).
                                    append(Component.literal("!").withStyle(ChatFormatting.WHITE)));
                } else {
                    Compute.formatMSGSend(player, Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("强化所需材料不足。").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("需要2个").withStyle(ChatFormatting.WHITE)).
                                    append(item.getDefaultInstance().getDisplayName()));
                }
            } else if (CurrentLevel < 9) {
                if (Compute.ItemStackCheck(inventory, item, 4)) {
                    Compute.itemStackRemove(inventory, item, 4);
                    data.putInt(StringUtils.SoulEquipForge, CurrentLevel + 1);
                    data.putInt(StringUtils.SoulEquipForge, CurrentLevel + 1);
                    Compute.formatBroad(player.level(), Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("").withStyle(ChatFormatting.WHITE).
                                    append(player.getDisplayName()).
                                    append(Component.literal("将").withStyle(ChatFormatting.WHITE)).
                                    append(itemStack.getDisplayName()).
                                    append(Component.literal("强化至了").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal("->").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal("" + (CurrentLevel + 1)).withStyle(CustomStyle.styleOfWorld)).
                                    append(Component.literal("!").withStyle(ChatFormatting.WHITE)));

                } else {
                    Compute.formatMSGSend(player, Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("强化所需材料不足。").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("需要4个").withStyle(ChatFormatting.WHITE)).
                                    append(item.getDefaultInstance().getDisplayName()));
                }
            } else if (CurrentLevel < 13) {
                if (Compute.ItemStackCheck(inventory, item, 8)) {
                    Compute.itemStackRemove(inventory, item, 8);
                    data.putInt(StringUtils.SoulEquipForge, CurrentLevel + 1);
                    data.putInt(StringUtils.SoulEquipForge, CurrentLevel + 1);
                    Compute.formatBroad(player.level(), Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("").withStyle(ChatFormatting.WHITE).
                                    append(player.getDisplayName()).
                                    append(Component.literal("将").withStyle(ChatFormatting.WHITE)).
                                    append(itemStack.getDisplayName()).
                                    append(Component.literal("强化至了").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal("->").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal("" + (CurrentLevel + 1)).withStyle(CustomStyle.styleOfWorld)).
                                    append(Component.literal("!").withStyle(ChatFormatting.WHITE)));

                } else {
                    Compute.formatMSGSend(player, Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("强化所需材料不足。").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("需要8个").withStyle(ChatFormatting.WHITE)).
                                    append(item.getDefaultInstance().getDisplayName()));
                }
            } else if (CurrentLevel < 16) {
                if (Compute.ItemStackCheck(inventory, item, 16)) {
                    Compute.itemStackRemove(inventory, item, 16);
                    data.putInt(StringUtils.SoulEquipForge, CurrentLevel + 1);
                    data.putInt(StringUtils.SoulEquipForge, CurrentLevel + 1);
                    Compute.formatBroad(player.level(), Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("").withStyle(ChatFormatting.WHITE).
                                    append(player.getDisplayName()).
                                    append(Component.literal("将").withStyle(ChatFormatting.WHITE)).
                                    append(itemStack.getDisplayName()).
                                    append(Component.literal("强化至了").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal("->").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal("" + (CurrentLevel + 1)).withStyle(CustomStyle.styleOfWorld)).
                                    append(Component.literal("!").withStyle(ChatFormatting.WHITE)));

                } else {
                    Compute.formatMSGSend(player, Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("强化所需材料不足。").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("需要16个").withStyle(ChatFormatting.WHITE)).
                                    append(item.getDefaultInstance().getDisplayName()));
                }
            } else {
                Compute.formatMSGSend(player, Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                        Component.literal("已经无法再将物品嵌入了。").withStyle(ChatFormatting.WHITE));
            }
        }
    }

}

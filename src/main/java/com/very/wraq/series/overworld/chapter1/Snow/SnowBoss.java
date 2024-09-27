package com.very.wraq.series.overworld.chapter1.Snow;

import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.BasicAttributeDescription;
import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.item.InventoryOperation;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class SnowBoss {
    public static Style chatFormatting = CustomStyle.styleOfSnow;
    public static Component SeriesTag = Component.literal("Item_SnowBoss").withStyle(chatFormatting).withStyle(ChatFormatting.ITALIC);
    public static String Name = "冰川";
    public static Item[] Rewards = {
            ModItems.SnowRune.get(), ModItems.ForgingStone0.get(),
            ModItems.goldCoin.get(), ModItems.SnowBossGem.get(),
            ModItems.SnowBossCore.get(), ModItems.SnowBossCore.get(),
            ModItems.SnowBossChestForgeDraw.get(), ModItems.SnowBossCentral.get()
    };
    public static double[] RewardsRate = {
            1, 0.25,
            0.2, 0.1,
            0.08, 0.04,
            0.04, 0.04
    };

    public static class SnowBossCentral extends Item {
        public SnowBossCentral(Properties p_41383_) {
            super(p_41383_);
        }

        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
            Compute.MaterialLevelDescription.Epic(components);
            components.add(Component.literal(" "));
            components.add(Component.literal(Name + "次元能量中枢").withStyle(chatFormatting));
            components.add(Component.literal(" "));
            components.add(SeriesTag);
            super.appendHoverText(stack, p_41422_, components, p_41424_);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

            return super.use(level, player, interactionHand);
        }
    }

    public static class SnowBossCore extends Item {
        public SnowBossCore(Properties p_41383_) {
            super(p_41383_);
        }

        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
            Compute.MaterialLevelDescription.Rare(components);
            components.add(Component.literal(" "));
            components.add(Component.literal(Name + "次元能量核心").withStyle(chatFormatting));
            components.add(Component.literal(" "));
            components.add(SeriesTag);
            super.appendHoverText(stack, p_41422_, components, p_41424_);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
            if (!level.isClientSide) {
                CompoundTag data = player.getPersistentData();
                String Entropy = StringUtils.Entropy.Snow;
                if (data.contains(Entropy)) data.putInt(Entropy, data.getInt(Entropy) + 1);
                else data.putInt(Entropy, 1);
                Compute.sendFormatMSG(player, Component.literal("次元能量").withStyle(CustomStyle.styleOfEntropy),
                        Component.literal("你的").withStyle(ChatFormatting.WHITE).
                                append(Component.literal(Name + "次元能量").withStyle(chatFormatting)).
                                append(Component.literal("得到了提升。").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("[" + data.getInt(Entropy) + "]").withStyle(ChatFormatting.GRAY)));
                Compute.EntropyPacketSend(player);
                Compute.playerItemUseWithRecord(player);
            }
            return super.use(level, player, interactionHand);
        }
    }

    public static class SnowBossGem extends Item {
        public SnowBossGem(Properties p_41383_) {
            super(p_41383_);
        }

        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
            components.add(Component.literal("·饰品").withStyle(ChatFormatting.LIGHT_PURPLE));
            components.add(Component.literal(" "));
            components.add(Component.literal(Name + "次元能量凝聚").withStyle(chatFormatting));
            components.add(Component.literal(" "));
            components.add(SeriesTag);
            super.appendHoverText(stack, p_41422_, components, p_41424_);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

            return super.use(level, player, interactionHand);
        }
    }

    public static class SnowBossPocket extends Item {
        public SnowBossPocket(Properties p_41383_) {
            super(p_41383_);
        }

        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
            components.add(Component.literal("·材料/次元口袋").withStyle(ChatFormatting.LIGHT_PURPLE));
            components.add(Component.literal(" "));
            components.add(Component.literal(Name + "能量凝聚物").withStyle(chatFormatting));
            components.add(Component.literal(" "));
            components.add(Component.literal("·将手伸入次元口袋，你将得到:").withStyle(ChatFormatting.GOLD));
            for (int i = 0; i < 8; i++) {
                components.add(Component.literal("  " + (i + 1) + ".").withStyle(ChatFormatting.GRAY).
                        append(Rewards[i].getDefaultInstance().getDisplayName()).
                        append(Component.literal(" *1").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal(" (" + String.format("%.0f%%", RewardsRate[i] * 100) + ")")));

            }
            components.add(Component.literal(" "));
            components.add(SeriesTag);
            super.appendHoverText(stack, p_41422_, components, p_41424_);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
            if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
                Random random = new Random();
                for (int i = 0; i < 8; i++) {
                    if (random.nextDouble(1) < RewardsRate[i]) {
                        Compute.sendFormatMSG(player, Component.literal("次元能量").withStyle(CustomStyle.styleOfEntropy),
                                Component.literal("你通过次元口袋获得了").withStyle(ChatFormatting.WHITE).
                                        append(Rewards[i].getDefaultInstance().getDisplayName()));
                        InventoryOperation.itemStackGive(player, Rewards[i].getDefaultInstance());
                        Compute.formatBroad(level, Component.literal("次元").withStyle(CustomStyle.styleOfEntropy),
                                Component.literal("玩家").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("通过次元口袋获得了").withStyle(ChatFormatting.WHITE)).
                                        append(Rewards[i].getDefaultInstance().getDisplayName()));
                    }
                }
                Compute.playerItemUseWithRecord(player);
            }
            return super.use(level, player, interactionHand);
        }

        @Override
        public boolean isFoil(ItemStack p_41453_) {
            return true;
        }
    }

    public static class SnowBossArmorChest extends ArmorItem {
        private static final Style style = CustomStyle.styleOfSnow;

        public SnowBossArmorChest(ItemMaterial Material, Type Slots) {
            super(Material, Slots, new Properties().rarity(CustomStyle.EntropyItalic));
            Utils.maxHealth.put(this, 300d);
            Utils.defence.put(this, 1d);
            Utils.armorTag.put(this, 1d);
            Utils.armorList.add(this);
        }

        @Override
        public void appendHoverText(ItemStack stack, @javax.annotation.Nullable Level level, List<Component> components, TooltipFlag flag) {
            Compute.forgingHoverName(stack);
            components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("胸甲").withStyle(style)));
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            ComponentUtils.descriptionOfBasic(components);
            BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
            ComponentUtils.descriptionOfAddition(components);
            Compute.DescriptionPassive(components, Component.literal("寒玉皑皑").withStyle(style));
            components.add(Component.literal("每过3s，削减周围所有单位至多").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.Defence("50%")).
                    append(Component.literal("并造成").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("缓速").withStyle(style)).
                    append(Component.literal("效果。").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("护甲削减与缓速效果持续2s,并且效果不可叠加。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
            SnowSuitDescription.SuitDescription(components);
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
            components.add(Component.literal("Dimension-Snow").withStyle(style).withStyle(ChatFormatting.ITALIC));
            ComponentUtils.suffixOfChapterI(components);
            super.appendHoverText(stack, level, components, flag);
        }
    }
}


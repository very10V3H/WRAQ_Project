package fun.wraq.series.overworld.chapter1.waterSystem.bossItems;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class LakeBoss {
    public static ChatFormatting chatFormatting = ChatFormatting.BLUE;
    public static Component SeriesTag = Component.literal("Item_LakeBoss").withStyle(chatFormatting).withStyle(ChatFormatting.ITALIC);
    public static String Name = "湖泊";
    public static Item[] Rewards = {
            ModItems.LakeRune.get(), ModItems.ForgingStone0.get(),
            ModItems.goldCoin.get(), ModItems.LakeBossGem.get(),
            ModItems.LakeBossCore.get(), ModItems.LakeBossCore.get(),
            ModItems.LakeBossSwordForgeDraw.get(), ModItems.LakeBossCentral.get()
    };
    public static double[] RewardsRate = {
            1, 0.25,
            0.2, 0.1,
            0.08, 0.04,
            0.04, 0.04
    };

    public static class LakeBossCentral extends Item {
        public LakeBossCentral(Properties p_41383_) {
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

    public static class LakeBossCore extends Item {
        public LakeBossCore(Properties p_41383_) {
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
                String Entropy = StringUtils.Entropy.Lake;
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

    public static class LakeBossGem extends Item {
        public LakeBossGem(Properties p_41383_) {
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

    public static class LakeBossPocket extends Item {
        public LakeBossPocket(Properties p_41383_) {
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

    public static class LakeBossSword extends SwordItem {
        public LakeBossSword(Tier tier, int num1, float num2) {
            super(tier, num1, num2, new Properties().rarity(CustomStyle.EntropyItalic));
            Utils.attackDamage.put(this, this.BaseDamage);
            Utils.defencePenetration.put(this, this.BreakDefence);
            Utils.healthSteal.put(this, this.HealSteal);
            Utils.critRate.put(this, this.CriticalHitRate);
            Utils.critDamage.put(this, this.CHitDamage);
            Element.WaterElementValue.put(this, 1.25);
            Utils.mainHandTag.put(this, 1d);
            Utils.weaponList.add(this);
            Utils.swordTag.put(this, 1d);
        }

        private final double BaseDamage = 160.0d;
        private final double BreakDefence = 0.3F;
        private final double CriticalHitRate = 0.3F;
        private final double CHitDamage = 0.4;
        private final double HealSteal = 0.08F;

        @Override
        public void appendHoverText(ItemStack stack, @javax.annotation.Nullable Level level, List<Component> components, TooltipFlag flag) {
            ChatFormatting MainStyle = ChatFormatting.BLUE;
            Compute.forgingHoverName(stack);
            components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.ShortHandleSword));
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
            ComponentUtils.descriptionOfBasic(components);
            BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
            ComponentUtils.descriptionOfAddition(components);
            components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("澈源维度展开: ").withStyle(MainStyle)).
                    append(Component.literal("澈源制造者释放制造澈源次元的能量，将持有者净化。").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("∰1.重置持有者全部").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.releaseSpeed("")));
            components.add(Component.literal("冷却时间:").withStyle(ChatFormatting.WHITE).
                    append(Component.literal("60s~20s").withStyle(ChatFormatting.AQUA)).
                    append(Component.literal("（冷却时间根据澈源次元能量的增加而减少）").withStyle(ChatFormatting.GRAY)));
            ComponentUtils.manaCostDescription(components, 180);
            ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, MainStyle, ChatFormatting.WHITE);
            components.add(Component.literal("Dimension-Lake").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
            ComponentUtils.suffixOfChapterI(components);
            super.appendHoverText(stack, level, components, flag);
        }

        @Override
        public boolean hurtEnemy(ItemStack p_43278_, LivingEntity p_43279_, LivingEntity p_43280_) {
            p_43278_.hurtAndBreak(0, p_43280_, (p_43296_) -> {
                p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
            return true;
        }

        @Override
        public boolean mineBlock(ItemStack p_40998_, Level p_40999_, BlockState p_41000_, BlockPos p_41001_, LivingEntity p_41002_) {
            if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0d) {
                p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                    p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });
            }

            return true;
        }

        @Override
        public boolean isFoil(ItemStack p_41453_) {
            return true;
        }
    }
}


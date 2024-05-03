package com.very.wraq.series.overWorld.MainStory_II.Sky.BossItems;

import com.very.wraq.process.element.Element;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class SkyBoss {
    public static Style chatFormatting = CustomStyle.styleOfSky;
    public static Component SeriesTag = Component.literal("Item_SkyBoss").withStyle(chatFormatting).withStyle(ChatFormatting.ITALIC);
    public static String Name = "天空";
    public static Item[] Rewards = {
            ModItems.SkyRune.get(), ModItems.ForgingStone0.get(),
            ModItems.GoldCoin.get(), ModItems.SkyBossGem.get(),
            ModItems.SkyBossCore.get(), ModItems.SkyBossCore.get(),
            ModItems.SkyBossBowForgeDraw.get(), ModItems.SkyBossCentral.get()
    };
    public static double[] RewardsRate = {
            1,0.25,
            0.2,0.1,
            0.08,0.04,
            0.04,0.04
    };
    public static class SkyBossCentral extends Item {
        public SkyBossCentral(Properties p_41383_) {
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
    public static class SkyBossCore extends Item {
        public SkyBossCore(Properties p_41383_) {
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
                String Entropy = StringUtils.Entropy.Sky;
                if (data.contains(Entropy)) data.putInt(Entropy,data.getInt(Entropy) + 1);
                else data.putInt(Entropy,1);
                Compute.FormatMSGSend(player,Component.literal("次元能量").withStyle(CustomStyle.styleOfEntropy),
                        Component.literal("你的").withStyle(ChatFormatting.WHITE).
                                append(Component.literal(Name + "次元能量").withStyle(chatFormatting)).
                                append(Component.literal("得到了提升。").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("[" + data.getInt(Entropy) + "]").withStyle(ChatFormatting.GRAY)));
                Compute.EntropyPacketSend(player);
                try {
                    Compute.PlayerItemUseWithRecord(player);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return super.use(level, player, interactionHand);
        }
    }
    public static class SkyBossGem extends Item {
        public SkyBossGem(Properties p_41383_) {
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
    public static class SkyBossPocket extends Item {
        public SkyBossPocket(Properties p_41383_) {
            super(p_41383_);
        }
        @Override
        public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
            components.add(Component.literal("·材料/次元口袋").withStyle(ChatFormatting.LIGHT_PURPLE));
            components.add(Component.literal(" "));
            components.add(Component.literal(Name + "能量凝聚物").withStyle(chatFormatting));
            components.add(Component.literal(" "));
            components.add(Component.literal("·将手伸入次元口袋，你将得到:").withStyle(ChatFormatting.GOLD));
            for (int i = 0; i < 8; i ++) {
                components.add(Component.literal("  " + (i+1) + ".").withStyle(ChatFormatting.GRAY).
                        append(Rewards[i].getDefaultInstance().getDisplayName()).
                        append(Component.literal(" *1").withStyle(ChatFormatting.GRAY)).
                        append(Component.literal(" (" + String.format("%.0f%%",RewardsRate[i] * 100) + ")")));

            }
            components.add(Component.literal(" "));
            components.add(SeriesTag);
            super.appendHoverText(stack, p_41422_, components, p_41424_);
        }

        @Override
        public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
            if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
                Random random = new Random();
                for(int i = 0; i < 8; i ++) {
                    if (random.nextDouble(1) < RewardsRate[i] * (1 + Compute.PlayerLuckyUp(player))) {
                        Compute.FormatMSGSend(player,Component.literal("次元能量").withStyle(CustomStyle.styleOfEntropy),
                                Component.literal("你通过次元口袋获得了").withStyle(ChatFormatting.WHITE).
                                        append(Rewards[i].getDefaultInstance().getDisplayName()));
                        try {
                            Compute.ItemStackGive(player,Rewards[i].getDefaultInstance());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Compute.FormatBroad(level,Component.literal("次元").withStyle(CustomStyle.styleOfEntropy),
                                Component.literal("玩家").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("通过次元口袋获得了").withStyle(ChatFormatting.WHITE)).
                                        append(Rewards[i].getDefaultInstance().getDisplayName()));
                    }
                }
                try {
                    Compute.PlayerItemUseWithRecord(player);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return super.use(level, player, interactionHand);
        }

        @Override
        public boolean isFoil(ItemStack p_41453_) {
            return true;
        }
    }
    public static class SkyBossBow extends WraqBow {
        private final double BaseDamage = 100.0d;
        private final double BreakDefence = 0.25F;
        private final double CriticalHitRate = 0.5F;
        private final double CHitDamage = 0.85;
        private final double SpeedUp = 0.6F;
        public SkyBossBow(Properties properties) {
            super(properties);
            Utils.AttackDamage.put(this,this.BaseDamage);
            Utils.DefencePenetration.put(this,this.BreakDefence);
            Utils.CritRate.put(this,this.CriticalHitRate);
            Utils.CritDamage.put(this,this.CHitDamage);
            Utils.MovementSpeed.put(this,this.SpeedUp);
            Element.WindElementValue.put(this, 1.25);
            Utils.MainHandTag.put(this,1d);
            Utils.WeaponList.add(this);
            Utils.BowTag.put(this,1.0d);
        }
        @Override
        public void appendHoverText(ItemStack stack, @javax.annotation.Nullable Level level, List<Component> components, TooltipFlag flag)
        {
            Style MainStyle = CustomStyle.styleOfSky;

            Compute.ForgingHoverName(stack,Component.literal("<次元级>").withStyle(CustomStyle.styleOfEntropy).append(Component.literal("寰宇之耀").withStyle(CustomStyle.styleOfSky).withStyle(ChatFormatting.BOLD)));
            components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
            Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfSky,ChatFormatting.WHITE);
            Compute.DescriptionOfBasic(components);
            BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
            Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfSky,ChatFormatting.WHITE);
            Compute.DescriptionOfAddtion(components);

            components.add(Component.literal(" - ").withStyle(ChatFormatting.GRAY).
                    append(Component.literal("天空维度展开: ").withStyle(MainStyle)).
                    append(Component.literal("天空制造者释放制造天空次元的能量，使持有者具有凌驾天空的力量。").withStyle(ChatFormatting.WHITE)));
            components.add(Component.literal("∰1.使持有者至多获得").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.CritDamage("320%")));

            Compute.DescriptionPassive(components,Component.literal("鹰隼之速").withStyle(ChatFormatting.AQUA));
            components.add(Component.literal("每").withStyle(ChatFormatting.WHITE).
                    append(Compute.AttributeDescription.ExMovementSpeed("8%")).
                    append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                    append(Compute.AttributeDescription.DefencePenetration("3%")));
            Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfSky,ChatFormatting.WHITE);
            components.add(Component.literal("Dimension-Sky").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.UNDERLINE));

            Compute.SuffixOfMainStoryII(components);
            super.appendHoverText(stack,level,components,flag);
        }

        @Override
        public boolean isFoil(ItemStack p_41453_) {
            return true;
        }
    }
}


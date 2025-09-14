package fun.wraq.series.overworld.chapter1.waterSystem.bossItems;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.BasicAttributeDescription;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class LakeBoss {
    public static ChatFormatting chatFormatting = ChatFormatting.BLUE;
    public static String Name = "湖泊";

    public static class LakeBossSword extends SwordItem {
        public LakeBossSword(Tier tier, int num1, float num2) {
            super(tier, num1, num2, new Properties().rarity(CustomStyle.EntropyItalic));
            Utils.attackDamage.put(this, this.BaseDamage);
            Utils.defencePenetration.put(this, this.BreakDefence);
            Utils.healthSteal.put(this, this.HealSteal);
            Utils.critRate.put(this, this.CriticalHitRate);
            Element.waterElementValue.put(this, 1.25);
            Utils.mainHandTag.put(this, 1d);
            Utils.weaponList.add(this);
        }

        private final double BaseDamage = 160.0d;
        private final double BreakDefence = 0.3F;
        private final double CriticalHitRate = 0.3F;
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


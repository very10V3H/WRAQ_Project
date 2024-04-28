package com.very.wraq.series.overWorld.MainStory_I.Plain.Bow;

import com.very.wraq.process.element.Element;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PlainBow extends BowItem {
    private final int Num;
    public PlainBow(Properties p_40524_, int Num) {
        super(p_40524_);
        this.Num = Num;
        Utils.AttackDamage.put(this,PlainBowAttributes.BaseDamage[Num]);
        Utils.DefencePenetration0.put(this,PlainBowAttributes.DefencePenetration0[Num]);
        Utils.CritRate.put(this,PlainBowAttributes.CriticalRate[Num]);
        Utils.CritDamage.put(this,PlainBowAttributes.CriticalDamage[Num]);
        Utils.MovementSpeed.put(this,PlainBowAttributes.SpeedUp[Num]);
        Element.LifeElementValue.put(this, PlainBowAttributes.LifeElementValue[Num]);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.BowTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("平原长弓" + PlainBowAttributes.Number[Num]).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("平原祝福").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal("箭矢命中目标后提供持续2s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MovementSpeed("40%")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("Plain-Bow" + PlainBowAttributes.Number[Num]).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }


    /*    @Override
        public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
            if (p_40669_ instanceof Player player) {
                boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, p_40667_) > 0;
                ItemStack itemstack = player.getProjectile(p_40667_);

                int i = this.getUseDuration(p_40667_) - p_40670_;
                i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(p_40667_, p_40668_, player, i, !itemstack.isEmpty() || flag);
                if (i < 0) return;

                if (!itemstack.isEmpty() || flag) {
                    if (itemstack.isEmpty()) {
                        itemstack = new ItemStack(Items.ARROW);
                    }

                    float f = getPowerForTime(i);
                    if (!(f < 0.1D)) {
                        boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, p_40667_, player));
                        if (!p_40668_.isClientSide) {
                            ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                            AbstractArrow abstractarrow = arrowitem.createArrow(p_40668_, itemstack, player);
                            abstractarrow = customArrow(abstractarrow);
                            abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0d, f * 3.0d, 1.0d);
                            if (f == 1.0d) {
                                abstractarrow.setCritArrow(true);
                            }

                            int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, p_40667_);
                            if (j > 0) {
                                abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + j * 0.5D + 0.5D);
                            }

                            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, p_40667_);
                            if (k > 0) {
                                abstractarrow.setKnockback(k);
                            }

                            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, p_40667_) > 0) {
                                abstractarrow.setSecondsOnFire(100);
                            }

                            p_40667_.hurtAndBreak(0, player, (p_40665_) -> {
                                p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                            });
                            if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                                abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                            }

                            p_40668_.addFreshEntity(abstractarrow);
                        }

                        p_40668_.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0d, 1.0d / (p_40668_.getRandom().nextDouble() * 0.4F + 1.2F) + f * 0.5F);
                        if (!flag1 && !player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                player.getInventory().removeItem(itemstack);
                            }
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                    }
                }
            }
        }*/
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.BowAttack(player,Utils.BowNumMap.get(StringUtils.BowNameString.PlainBow));
        return interactionHand.equals(InteractionHand.MAIN_HAND)
                ? InteractionResultHolder.success(player.getMainHandItem()) : InteractionResultHolder.success(player.getOffhandItem());    }
}

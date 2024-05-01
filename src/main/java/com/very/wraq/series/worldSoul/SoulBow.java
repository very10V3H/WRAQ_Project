package com.very.wraq.series.worldSoul;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public class SoulBow extends BowItem {
    public SoulBow(Properties p_40524_) {
        super(p_40524_);
        Utils.AttackDamage.put(this,SoulEquipAttribute.BaseAttribute.SoulBow.AttackDamage);
        Utils.CritRate.put(this,SoulEquipAttribute.BaseAttribute.SoulBow.CritRate);
        Utils.CritDamage.put(this,SoulEquipAttribute.BaseAttribute.SoulBow.CritDamage);
        Utils.DefencePenetration0.put(this,SoulEquipAttribute.BaseAttribute.SoulBow.DefencePenetration0);
        Utils.MovementSpeed.put(this,SoulEquipAttribute.BaseAttribute.SoulBow.MovementSpeed);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.BowTag.put(this,1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Utils.WeaponTypeComponents.Bow));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfWorld,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfWorld,ChatFormatting.WHITE);
        Compute.SuffixOfWorldSoul(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
/*        if (p_40669_ instanceof Player player) {
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
        }*/
    }

/*    @Override
    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
        ItemStack itemstack = p_40673_.getItemInHand(p_40674_);
        boolean flag = true;
        InteractionResultHolder<ItemStack> ret = ForgeEventFactory.onArrowNock(itemstack, p_40672_, p_40673_, p_40674_, flag);
        if (ret != null) {
            return ret;
        } else if (!p_40673_.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            p_40673_.startUsingItem(p_40674_);
            return InteractionResultHolder.consume(itemstack);
        }
    }*/

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.BowAttack(player,Utils.BowNumMap.get(StringUtils.BowNameString.WorldBow));
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND) && player.isShiftKeyDown()) {
            try {
                SoulEquipAttribute.Forging(player.getItemInHand(InteractionHand.MAIN_HAND),player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return interactionHand.equals(InteractionHand.MAIN_HAND)
                ? InteractionResultHolder.success(player.getMainHandItem()) : InteractionResultHolder.success(player.getOffhandItem());
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

package fun.wraq.Items.Money;

import fun.wraq.common.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class GoldSword0 extends SwordItem {
    private double BaseDamage = 0.0d;
    private double BreakDefence = 0.0d;
    private double CriticalHitRate = 0.0d;
    private double CHitDamage = 0.0d;
    private double HealSteal = 0.00d;
    private double SpeedUp = 0.0d;
    private final double AttackSpeedUp = -2f;

    public GoldSword0(Tier tier, int num1, float num2) {
        super(tier, num1, num2, new Properties());
        Utils.attackDamage.put(this, this.BaseDamage);
        Utils.defencePenetration.put(this, this.BreakDefence);
        Utils.healthSteal.put(this, this.HealSteal);
        Utils.critRate.put(this, this.CriticalHitRate);
        Utils.critDamage.put(this, this.CHitDamage);
        Utils.movementSpeedWithoutBattle.put(this, this.SpeedUp);
        Utils.attackSpeedUp.put(this, AttackSpeedUp);
        Utils.mainHandTag.put(this, 1d);
        Utils.weaponList.add(this);

        Utils.swordTag.put(this, 1d);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        return super.use(level, player, useHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
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
}

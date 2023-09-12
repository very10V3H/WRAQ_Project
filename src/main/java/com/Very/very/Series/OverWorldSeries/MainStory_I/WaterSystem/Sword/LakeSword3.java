package com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Sword;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
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

import javax.annotation.Nullable;
import java.util.List;

public class LakeSword3 extends SwordItem {
    public LakeSword3(Tier tier, int num1, float num2){
        super(tier,num1,num2,new Item.Properties());
        Utils.BaseDamage.put(this,this.BaseDamage);
        Utils.BreakDefence.put(this,this.BreakDefence);
        Utils.HealSteal.put(this,this.HealSteal);
        Utils.CriticalHitRate.put(this,this.CriticalHitRate);
        Utils.CHitDamage.put(this,this.CHitDamage);
        Utils.SpeedUp.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1.0F);
        Utils.SwordTag.put(this,1.0F);
    }
    private final float BaseDamage = 80.0F;
    private final float BreakDefence = 0.3F;
    private final float CriticalHitRate = 0.3F;
    private final float CHitDamage = 1.25F;
    private final float HealSteal = 0.15F;
    private final float SpeedUp = 0.75F;
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("蛟龙-III").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长柄武器").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.BasicSwordDescription(components,BaseDamage,BreakDefence,CriticalHitRate,CHitDamage,HealSteal,SpeedUp);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("潜泳").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE));
        Compute.DescriptionNum(components,"攻击后获得持续3秒的",Compute.AttributeDescription.MovementSpeed("50%"),"");
        Compute.DescriptionActive(components,Component.literal("出水").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE));
        components.add(Component.literal("右键向前冲刺"));
        Compute.CoolDownTimeDescription(components,8);
        Compute.EmojiDescriptionManaCost(components,20);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("Lake-Spear-III").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
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
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0F) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
/*        HitResult hitResult = player.pick(10,0,true); //第一个参数为最远距离
        double X = hitResult.getLocation().x;
        double Y = hitResult.getLocation().y;
        double Z = hitResult.getLocation().z;*/
/*        double dX = X-player.getX();
        double dY = Y-player.getY();
        double dZ = Z-player.getZ();
        player.playSound(SoundEvents.AMBIENT_UNDERWATER_EXIT);*/
/*        player.setDeltaMovement(dX/5,dY/5,dZ/5);*/
/*        player.sendSystemMessage(Component.literal(String.valueOf(player.getViewVector(5).x)));
        player.sendSystemMessage(Component.literal(String.valueOf(player.getViewVector(5).y)));
        player.sendSystemMessage(Component.literal(String.valueOf(player.getViewVector(5).z)));*/
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND))
        {
            CompoundTag data = player.getPersistentData();
            if(data.getInt("MANA") < 20) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("装备使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("魔力不足。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            Compute.USE(player,player.getMainHandItem().getItem());
        }
        return super.use(level, player, interactionHand);
    }
}

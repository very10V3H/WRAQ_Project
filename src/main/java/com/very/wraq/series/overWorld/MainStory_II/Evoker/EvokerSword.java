package com.very.wraq.series.overWorld.MainStory_II.Evoker;

import com.very.wraq.process.element.Element;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
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

public class EvokerSword extends SwordItem{
    public EvokerSword(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        Utils.ManaDamage.put(this,this.ManaDamage);
        Utils.ManaRecover.put(this,this.ManaReply);
        Utils.ManaPenetration0.put(this,this.ManaPenetration0);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.ManaCost.put(this,(double) ManaCost);
        Element.LightningElementValue.put(this, 0.8);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SceptreTag.put(this,1.0d);
    }
    private final double ManaDamage = 400.0d;
    private final double ManaReply = 10.0d;
    private final double ManaPenetration0 = 800;
    private final double SpeedUp = 0.4F;
    public static final int ManaCost = 20;
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("魔源之杖").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
        Compute.ManaCoreAddition(stack,components);
        components.add(Component.literal("Evoker-Sceptre").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity p_40995_, LivingEntity p_40996_) {
        p_40994_.hurtAndBreak(0, p_40996_, (p_41007_) -> {
            p_41007_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.ManaAttack(player,2);
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND,new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
/*        HitResult result = player.pick(2,0,true);
        Compute.ParticleWITCH(result.getLocation().x, result.getLocation().y,result.getLocation().z,level,0.5);*/
        return super.use(level, player, interactionHand);
    }
}

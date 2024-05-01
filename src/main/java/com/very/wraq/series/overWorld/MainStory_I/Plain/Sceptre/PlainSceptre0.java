package com.very.wraq.series.overWorld.MainStory_I.Plain.Sceptre;

import com.very.wraq.process.element.Element;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class PlainSceptre0 extends SwordItem {
    public PlainSceptre0(Tier p_42961_, int p_42962_, float p_42963_, Item.Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        Utils.ManaDamage.put(this,this.ManaDamage);
        Utils.ManaRecover.put(this,this.ManaReply);
        Utils.ManaPenetration0.put(this,this.ManaPenetration0);
        Utils.MovementSpeed.put(this,this.SpeedUp);
        Utils.ManaCost.put(this,(double) ManaCost);
        Element.LifeElementValue.put(this, 0.2);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.SceptreTag.put(this,1.0d);
    }
    private final double ManaDamage = 40.0d;
    private final double ManaPenetration0 = 100;
    private final double ManaReply = 2.0d;
    private final double SpeedUp = 0.1F;
    public static final int ManaCost = 15;
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("平原权杖").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
/*
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        PlainSceptre0.PlainSceptreDescription(components);
        Compute.ManaCoreAddition(stack,components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("Plain-Sceptre-O").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
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
        Compute.ManaAttack(player,0);
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND,new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public static void PlainSceptreNormalAttack(Player player, Level level) {
        CompoundTag data = player.getPersistentData();
        if(!level.isClientSide)
        {
            if(data.getInt("MANA") < ManaCost) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("法力不足").withStyle(ChatFormatting.WHITE)));
            else
            {
                Compute.PlayerManaAddOrCost(player,-ManaCost);
                ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_PLAIN.get(),
                        player,level,PlayerAttributes.PlayerManaDamage(player),
                        PlayerAttributes.PlayerManaPenetration(player),
                        PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Plain);
                newArrow.setSilent(true);
                newArrow.setNoGravity(true);
                
                newArrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0,3,1);
                ProjectileUtil.rotateTowardsMovement(newArrow,0);
                level.addFreshEntity(newArrow);
            }
            ParticleProvider.FaceCircleCreate((ServerPlayer) player,1,0.75,20, ParticleTypes.SCRAPE);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player,1.5,0.5,16, ParticleTypes.SCRAPE);
        }
    }

    public static void PlainSceptreDescription(List<Component> components) {
        Compute.DescriptionPassive(components,Component.literal("平原的加护").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal("当法球命中单位时，获得:"));
        Compute.EmojiDescriptionDefence(components,40);
        Compute.EmojiDescriptionManaDefence(components,40);
        components.add(Component.literal("并回复").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxHealth("1%")));
    }
}

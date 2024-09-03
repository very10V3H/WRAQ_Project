package com.very.wraq.series.overworld.chapter2.codeMana;

import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.NewArrow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.common.attribute.BasicAttributeDescription;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.struct.Power;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.render.toolTip.CustomStyle;
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
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class CodeSceptre extends PickaxeItem {
    public CodeSceptre(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        Utils.manaDamage.put(this, this.ManaDamage);
        Utils.manaRecover.put(this, this.ManaReply);
        Utils.manaPenetration.put(this, this.ManaBreakDefence);
        Utils.movementSpeedWithoutBattle.put(this, this.SpeedUp);
        Utils.manaCost.put(this, (double) ManaCost);
        Utils.mainHandTag.put(this, 1d);
        Utils.weaponList.add(this);

        Utils.sceptreTag.put(this, 1.0d);
    }

    private double ManaDamage = 50;
    private double ManaReply = 10.0f;
    private double ManaBreakDefence = 0.3F;
    private double SpeedUp = 0.4F;
    private final double ManaCost = 21;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        components.add(Component.literal("能量激化:").withStyle(ChatFormatting.BOLD).withStyle(CustomStyle.styleOfVolcano));
        components.add(Component.literal("能使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("魔符").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("来强化下一次").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("魔法攻击").withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        Compute.ManaCoreAddition(stack, components);
        components.add(Component.literal("Evoker-Sceptre-X").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryII-I.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
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
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0f) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        CompoundTag data = player.getPersistentData();
        if (!level.isClientSide) {
            Power power = Utils.PowerMap.get((ServerPlayer) player);
            int Effect = power.get1Count() + 1;
            int Range = power.get2Count();
            int Damage = power.get3Count();
            int Break = power.get4Count();
            int Kaze = power.get5Count();
            int Snow = power.get6Count();
            int Lightning = power.get7Count();
            int Gather = power.get8Count();
            int ManaCost = 10;
            ManaCost += Effect * 10;
            ManaCost += Range * 15;
            ManaCost += Damage * 10;
            ManaCost += Break * 10;
            ManaCost += Kaze * 15;
            ManaCost += Snow * 15;
            ManaCost += Lightning * 10;
            ManaCost += Gather * 20;
            if (data.getInt("MANA") < ManaCost)
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.GRAY).append(Component.literal("法力不足").withStyle(ChatFormatting.WHITE)));
            else {
                data.putInt("MANA", data.getInt("MANA") - ManaCost);
                Compute.ManaStatusUpdate(player);
                NewArrow newArrow = new NewArrow(player, level, PlayerAttributes.manaDamage(player), PlayerAttributes.manaPenetration(player), PlayerAttributes.expUp(player), true, PlayerAttributes.manaPenetration0(player));
                newArrow.setSilent(true);
                newArrow.setNoGravity(true);

                newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.5F, 1.0f);
                WraqSceptre.adjustOrb(newArrow, player);
                level.addFreshEntity(newArrow);
                ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
                ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
            }
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data1 = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data1.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Utils.ManaCoreMap.get(data1.getString(StringUtils.ManaCore.ManaCore))));
                data1.remove(StringUtils.ManaCore.ManaCore);
            }
        }
/*        HitResult result = player.pick(2,0,true);
        Compute.ParticleWITCH(result.getLocation().x, result.getLocation().y,result.getLocation().z,level,0.5);*/
        return super.use(level, player, interactionHand);
    }
}

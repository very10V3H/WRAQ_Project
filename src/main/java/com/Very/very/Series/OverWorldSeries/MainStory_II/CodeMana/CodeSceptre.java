package com.Very.very.Series.OverWorldSeries.MainStory_II.CodeMana;

import com.Very.very.Projectile.Mana.NewArrow;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Struct.Power;
import com.Very.very.ValueAndTools.Utils.Utils;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class CodeSceptre extends PickaxeItem{
    public CodeSceptre(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        Utils.ManaDamage.put(this,this.ManaDamage);
        Utils.ManaReply.put(this,this.ManaReply);
        Utils.ManaBreakDefence.put(this,this.ManaBreakDefence);
        Utils.SpeedUp.put(this,this.SpeedUp);
        Utils.MainHandTag.put(this,1.0F);
        Utils.SceptreTag.put(this,1.0F);
    }
    private float ManaDamage = 100.0F;
    private float ManaReply = 10.0F;
    private float ManaBreakDefence = 0.3F;
    private float SpeedUp = 0.4F;
    private final float ManaCost = 7;
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("<唤雷能量激化>").withStyle(Utils.styleOfVolcano).append(Component.literal("魔源之杖").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("法杖").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.BasicSceptreDescription(components,ManaDamage,ManaCost,ManaBreakDefence,ManaReply,SpeedUp);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        components.add(Component.literal("能量激化:").withStyle(ChatFormatting.BOLD).withStyle(Utils.styleOfVolcano));
        components.add(Component.literal("能使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Component.literal("魔符").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("来强化下一次").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Component.literal("魔法攻击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.LIGHT_PURPLE,ChatFormatting.WHITE);
        components.add(Component.literal("Evoker-Sceptre-X").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryII-I.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
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
        if (!p_40999_.isClientSide && p_41000_.getDestroySpeed(p_40999_, p_41001_) != 0.0F) {
            p_40998_.hurtAndBreak(0, p_41002_, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        CompoundTag data = player.getPersistentData();
        if(!level.isClientSide)
        {
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
            if(data.getInt("MANA") < ManaCost) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("法力不足").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                data.putInt("MANA",data.getInt("MANA") - ManaCost);
                Compute.ManaStatusUpdate(player);
                NewArrow newArrow = new NewArrow(player,level,Compute.PlayerManaDamage(player),Compute.PlayerManaBreakDefence(player),Compute.ExpGetImprove(player),true,Compute.PlayerManaBreakDefence0(player));
                newArrow.setSilent(true);
                newArrow.setNoGravity(true);
                player.getPersistentData().putBoolean("IsAttack",false);
                newArrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,5.0F,1.0F);
                level.addFreshEntity(newArrow);
                Compute.FaceCircleCreate((ServerPlayer) player,1,0.75,20, ParticleTypes.WITCH);
                Compute.FaceCircleCreate((ServerPlayer) player,1.5,0.5,16, ParticleTypes.WITCH);
            }
        }
/*        HitResult result = player.pick(2,0,true);
        Compute.ParticleWITCH(result.getLocation().x, result.getLocation().y,result.getLocation().z,level,0.5);*/
        return super.use(level, player, interactionHand);
    }
}

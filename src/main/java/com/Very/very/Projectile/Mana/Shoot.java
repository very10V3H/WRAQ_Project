package com.Very.very.Projectile.Mana;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Shoot extends Item {

    public Shoot(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        CompoundTag data = player.getPersistentData();
        if(!level.isClientSide)
        {
            if(data.getInt("MANA") < 10) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("法力不足").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            else
            {
                data.putInt("MANA",data.getInt("MANA")-10);
                Compute.ManaStatusUpdate(player);
                NewArrow newArrow = new NewArrow(player,level);
                newArrow.setSilent(true);
                newArrow.setNoGravity(true);
                newArrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,5.0F,1.0F);
                level.addFreshEntity(newArrow);
            }
        }
        return super.use(level, player, interactionHand);
    }
}


package com.Very.very.Items.MobItem;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class spawn1 extends Item {

    public spawn1(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
/*        BlockPos blockpos = new BlockPos(player.getX(),player.getY(),player.getZ());*/
       /* CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("id", HEntity.ID_TAG);
        Entity entity = EntityType.loadEntityRecursive(compoundTag, level, (p_138828_) -> {
            p_138828_.moveTo(player.getX(), player.getY(), player.getZ(), p_138828_.getYRot(), p_138828_.getXRot());
            return p_138828_;
        });
        ((Mob)entity).finalizeSpawn(entity.getServer().overworld(),level.getCurrentDifficultyAt(blockpos), MobSpawnType.COMMAND, (SpawnGroupData)null, (CompoundTag)null);
      */
        if(!player.level().isClientSide)
        {
            if(player.getX() >= -168.5 && player.getX() <= -158.5 && player.getY() >= 115 && player.getY() <= 120 && player.getZ() >= 1412.5 && player.getZ() <= 1422.5)
            {
                if(Utils.HBossDelay != -2) {
                    Compute.FormatMSGSend(player,Component.literal("BOSS").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_RED),
                            Component.literal("请等待BOSS召唤完毕。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                }
                ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                itemStack.setCount(itemStack.getCount()-1);
                player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
                Utils.HBossDelay = 7;
                Utils.HBossPlayer = player;
                Compute.FormatBroad(player.level(),Component.literal("BOSS").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_RED),
                        Component.literal(player.getName().getString()+"召唤了MainIBoss").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
            }
            else player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal("唤魔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("不能在此处召唤boss").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

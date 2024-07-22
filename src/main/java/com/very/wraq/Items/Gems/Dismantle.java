package com.very.wraq.Items.Gems;

import com.very.wraq.common.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Dismantle extends Item {

    public Dismantle(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("用于拆解已经镶嵌于装备上的宝石。").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal(" "));
        components.add(Component.literal("Gems-Item").withStyle(ChatFormatting.GOLD));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack, level, components, flag);
    }

/*    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(interactionHand.equals(InteractionHand.OFF_HAND) && player.isShiftKeyDown())
        {
            ItemStack mainhand = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = mainhand.getOrCreateTagElement(Utils.MOD_ID);
            if(!Utils.BaseDamage.containsKey(mainhand.getItem()))
            {
                if(!level.isClientSide) player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("宝石").withStyle(ChatFormatting.LIGHT_PURPLE)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)).append(Component.literal("这件物品似乎不能被开孔。").withStyle(ChatFormatting.WHITE)));

            }
            else
            {
                if(data.getInt("Slot") == 3 && !level.isClientSide)
                {
                    player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("宝石").withStyle(ChatFormatting.LIGHT_PURPLE)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)).append(Component.literal("似乎超过了能开孔的数量。").withStyle(ChatFormatting.WHITE)));
                }
                else
                {
                    player.playSound(SoundEvents.PLAYER_LEVELUP);
                    if(!level.isClientSide)
                    {
                        player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("宝石").withStyle(ChatFormatting.LIGHT_PURPLE)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)).append(Component.literal("开孔成功。").withStyle(ChatFormatting.WHITE)));
                        ItemStack offhand = player.getItemInHand(InteractionHand.OFF_HAND);
                        offhand.setCount(offhand.getCount()-1);
                        player.setItemInHand(InteractionHand.OFF_HAND,offhand);
                        data.putInt("Slot",data.getInt("Slot")+1);
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }*/

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

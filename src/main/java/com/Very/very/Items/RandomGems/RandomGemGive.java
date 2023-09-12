package com.Very.very.Items.RandomGems;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class RandomGemGive extends Item {

    public RandomGemGive(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Random r = new Random();
            int num = r.nextInt(100);
            ItemStack itemStack = null;
            if (num < 25){
                itemStack = Moditems.Main1BeltGem.get().getDefaultInstance();
            }
            else if (num < 50){
                itemStack = Moditems.Main1necklaceGem.get().getDefaultInstance();
            }
            else if (num < 75){
                itemStack = Moditems.Main1BraceletGem.get().getDefaultInstance();
            }
            else{
                itemStack = Moditems.Main1HandGem.get().getDefaultInstance();
            }
            Compute.RandomAttributeProvider(itemStack,1,1,player);
            Compute.RandomAttributeProvider(itemStack,1,1,player);
            Compute.RandomAttributeProvider(itemStack,1,0.75,player);
            Compute.RandomAttributeProvider(itemStack,1,0.5,player);
            Compute.FormatBroad(player.level(), Component.literal("BOSS").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_RED),
                    Component.literal(player.getName().getString()+"通过四元饰品袋").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                            append(Component.literal("获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(itemStack.getDisplayName()));
            Compute.ItemStackGive(player,itemStack);
            ItemStack Bag = player.getItemInHand(InteractionHand.MAIN_HAND);
            Bag.setCount(Bag.getCount()-1);
            player.setItemInHand(InteractionHand.MAIN_HAND,Bag);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("奖励包-饰品").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal("右键获取随机四元饰品。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" "));
        components.add(Component.literal("Packet-Curios").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStory-I").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }
}

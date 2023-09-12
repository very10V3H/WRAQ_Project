package com.Very.very.Items.gems;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class plaingems0 extends Item {
    public plaingems0(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("这是一个测试宝石"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Prologue-O").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(interactionHand.equals(InteractionHand.OFF_HAND) && !level.isClientSide)
        {
            ItemStack MainHandItem = player.getMainHandItem();
            if(Utils.BaseDamage.containsKey(MainHandItem.getItem()))
            {
                MainHandItem.getOrCreateTagElement(Utils.MOD_ID).putString("Gems1","In!");
                player.setItemInHand(InteractionHand.OFF_HAND, MinecartItem.byId(0).getDefaultInstance());
                player.sendSystemMessage(Component.literal("宝石镶嵌完成"));
            }
        }
/*        MySnowBall snowball = new MySnowBall(level,player);
        snowball.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5F, 1.0F);
        level.addFreshEntity(snowball);*/
/*        MyArrow arrow = new MyArrow(EntityType.ARROW,player,level);
        arrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0.0F,3.0F,1.0F);
        level.addFreshEntity(arrow);*/
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}

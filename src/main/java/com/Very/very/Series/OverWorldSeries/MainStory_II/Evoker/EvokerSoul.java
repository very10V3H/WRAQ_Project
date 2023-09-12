package com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker;

import com.Very.very.ValueAndTools.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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

public class EvokerSoul extends Item {
    public EvokerSoul(Item.Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Normal(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("唤魔者的魔力，似乎被轻微污染了。").withStyle(ChatFormatting.DARK_PURPLE));
        components.add(Component.literal("右键饮用：恢复50点魔力，扣除20%当前生命值。"));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-evoker").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if(interactionHand == InteractionHand.MAIN_HAND && !level.isClientSide)
        {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            itemStack.setCount(itemStack.getCount()-1);
            player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
            player.setHealth(player.getHealth()*0.8F);
            CompoundTag data = player.getPersistentData();
            data.putInt("MANA", Math.min(data.getInt("MANA") + 50, data.getInt("MAXMANA")));
            Compute.ManaStatusUpdate(player);
            player.getCooldowns().addCooldown(this,200);
        }
        return super.use(level, player, interactionHand);
    }
}

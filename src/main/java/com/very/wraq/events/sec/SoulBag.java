package com.very.wraq.events.sec;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
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

public class SoulBag extends Item {

    private final Item item;

    public SoulBag(Properties p_41383_, Item item) {
        super(p_41383_);
        this.item = item;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        if (Utils.SoulBagsMap.isEmpty()) Utils.SoulBagsInit();
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        int Count = data.getInt(Utils.SoulBagsMap.get(item));
        if (Count == 0) Count = 64;
        components.add(Component.literal("内含：").withStyle(ChatFormatting.AQUA).
                append(item.getDefaultInstance().getDisplayName()).
                append(Component.literal(" * " + Count).withStyle(ChatFormatting.WHITE)));
        super.appendHoverText(stack, p_41422_, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (Utils.SoulBagsMap.isEmpty()) Utils.SoulBagsInit();
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
            int Count = data.getInt(Utils.SoulBagsMap.get(item));
            if (Count == 0) Count = 64;
            Compute.itemStackGive(player, new ItemStack(item, Count));
            Compute.playerItemUseWithRecord(player);
        }
        return super.use(level, player, interactionHand);
    }
}

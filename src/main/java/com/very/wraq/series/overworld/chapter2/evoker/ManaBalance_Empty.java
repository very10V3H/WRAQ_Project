package com.very.wraq.series.overworld.chapter2.evoker;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.hud.Mana;
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

public class ManaBalance_Empty extends Item {
    public ManaBalance_Empty(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·材料/容器").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal(" "));
        components.add(Component.literal("魔力稳定石，用于存储魔力。").withStyle(ChatFormatting.DARK_PURPLE));
        components.add(Component.literal("右键使用，将自身全部魔力存入稳定石。"));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-evoker").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (interactionHand == InteractionHand.MAIN_HAND && !level.isClientSide) {
            Compute.playerItemUseWithRecord(player);
            CompoundTag data = player.getPersistentData();
            ItemStack itemStack1 = ModItems.ManaBalance_filled.get().getDefaultInstance();
            itemStack1.getOrCreateTagElement(Utils.MOD_ID).putInt("MANA", data.getInt("MANA"));
            data.putInt("MANA", 0);
            player.addItem(itemStack1);
            Mana.updateManaStatus(player);
        }
        return super.use(level, player, interactionHand);
    }
}

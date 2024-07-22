package com.very.wraq.Items.MainStory_1;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class NewCurios extends Item implements ICurioItem {
    public NewCurios(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        stack.setHoverName(Component.literal("新人的馈赠").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal(" "));
        components.add(Component.literal("欢迎你的到来！").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("维瑞阿契的星光永远伴随着你！").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" "));
        components.add(Component.literal("Present").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Player player = (Player) slotContext.entity();
        Level level = player.level();
        for (int i = 0; i <= 10; i++) {
            double x = 2.0 * Math.cos(i * Math.PI / 5);
            double z = 2.0 * Math.sin(i * Math.PI / 5);
            level.addParticle(ParticleTypes.FIREWORK, player.getX() + x, player.getY() + 0.5, player.getZ() + z, 0, 0, 0);
        }
        ICurioItem.super.curioTick(slotContext, stack);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

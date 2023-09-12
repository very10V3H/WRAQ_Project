package com.Very.very.Items.ProfessionAndQuest;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Profession_Bow extends Item {

    public Profession_Bow(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        CompoundTag data = player.getPersistentData();
        if(!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND)
        {
            if(!data.contains("QuestCounts") || data.getInt("QuestCounts") < 20) player.sendSystemMessage(Component.literal("需要先完成20次冒险者任务才能获得该职位。"));
            else
            {
                if(data.contains("Bow")) player.sendSystemMessage(Component.literal("当前弓箭手职业击杀数:").withStyle(ChatFormatting.GOLD).append(Component.literal(String.valueOf(data.getInt("Bow"))).withStyle(ChatFormatting.RESET)));
                else
                {
                    data.putInt("Bow",0);
                    player.sendSystemMessage(Component.literal("你成功获得了弓箭手职业！").withStyle(ChatFormatting.GOLD));
                }
                if(data.contains("Sword")) data.remove("Sword");
                if(data.contains("Mana")) data.remove("Mana");
                if(data.contains("Barker")) data.remove("Barker");
            }
        }
        return super.use(level, player, interactionHand);
    }
}

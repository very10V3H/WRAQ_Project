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

public class Profession_Sword extends Item {
    public Profession_Sword(Properties p_41383_) {
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
                if(data.contains("Sword")) player.sendSystemMessage(Component.literal("当前剑士职业击杀数:").withStyle(ChatFormatting.GOLD).append(Component.literal(String.valueOf(data.getInt("Sword"))).withStyle(ChatFormatting.RESET)));
                else
                {
                    data.putInt("Sword",0);
                    player.sendSystemMessage(Component.literal("你成功获得了剑士职业！").withStyle(ChatFormatting.GOLD));
                }
                if(data.contains("Mana")) data.remove("Mana");
                if(data.contains("Bow")) data.remove("Bow");
                if(data.contains("Barker")) data.remove("Barker");
            }
        }
        return super.use(level, player, interactionHand);
    }
}

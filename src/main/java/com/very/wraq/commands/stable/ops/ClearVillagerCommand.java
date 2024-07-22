package com.very.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ClearVillagerCommand implements Command<CommandSourceStack> {
    public static ClearVillagerCommand instance = new ClearVillagerCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayer();
        List<Villager> villagers = player.level().getEntitiesOfClass(Villager.class, AABB.ofSize(player.position(), 100, 100, 100));
        int size = villagers.size();
        villagers.forEach(villager -> {
            if (villager.getVillagerData().getLevel() != 5) villager.remove(Entity.RemovalReason.KILLED);
        });
        player.sendSystemMessage(Component.literal("清理" + (size - villagers.size()) + "只村民"));
        return 0;
    }
}

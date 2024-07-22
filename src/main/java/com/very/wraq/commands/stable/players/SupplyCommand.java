package com.very.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import vazkii.patchouli.api.PatchouliAPI;

import java.util.List;

public class SupplyCommand implements Command<CommandSourceStack> {
    public static SupplyCommand instance = new SupplyCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        List<ItemStack> supply = List.of(
                new ItemStack(ModItems.windBottle.get()),
                new ItemStack(ModItems.ID_Card.get()),
                PatchouliAPI.get().getBookStack(new ResourceLocation(Utils.MOD_ID, "guide"))
        );
        supply.forEach(stack -> {
            Compute.itemStackGive(player, stack);
        });
        return 0;
    }
}

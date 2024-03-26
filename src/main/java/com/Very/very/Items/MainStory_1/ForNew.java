package com.Very.very.Items.MainStory_1;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import vazkii.patchouli.api.PatchouliAPI;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ForNew extends Item {
    public ForNew(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {

            try {
                Compute.PlayerItemUseWithRecord(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ItemStack food = Items.GOLDEN_CARROT.getDefaultInstance();
            food.setCount(64);
            try {
                Compute.ItemStackGive(player,food);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Compute.ItemStackGive(player,ModItems.ExploreNote.get().getDefaultInstance());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Compute.ItemStackGive(player,ModItems.Knife.get().getDefaultInstance());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Compute.ItemStackGive(player,ModItems.BackSpawn.get().getDefaultInstance());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ItemStack[] itemStacks = {
                    new ItemStack(ModItems.PlainSword0.get()),
                    new ItemStack(ModItems.PlainBow0.get()),
                    new ItemStack(ModItems.PlainSceptre0.get()),
                    new ItemStack(ModItems.BackPackTickets.get()),
                    new ItemStack(ModItems.SmartPhone.get()),
            };

            for (ItemStack stack : itemStacks) {
                try {
                    Compute.ItemStackGive(player, stack);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            ItemStack itemStack = Items.ELYTRA.getDefaultInstance();
            itemStack.getOrCreateTag().putBoolean("Unbreakable",true);
            Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
            map.put(Enchantments.UNBREAKING,5);
            EnchantmentHelper.setEnchantments(map,itemStack);
            try {
                Compute.ItemStackGive(player,itemStack);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("右键打开它!"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Present").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}

package com.very.wraq.Items.ProfessionAndQuest;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class Quest extends Item {

    public Quest(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        CompoundTag data = player.getPersistentData();
        if (interactionHand == InteractionHand.MAIN_HAND) {
            ItemStack Quest = player.getItemInHand(InteractionHand.MAIN_HAND);
            player.getCooldowns().addCooldown(Quest.getItem(), 20);
            if (!player.level().isClientSide) {
                if (data.contains("Quest") && data.getInt("Quest") != -1 && Quest.getOrCreateTagElement(Utils.MOD_ID).contains("Quest")) {
                    if (data.getInt("Quest") == 0) {
                        ItemStack itemStack0 = ModItems.PlainSoul.get().getDefaultInstance();
                        itemStack0.setCount(64);
                        if (Compute.ItemCheck(player, itemStack0) != -1) {
                            ItemStack playerItem = player.getInventory().getItem(Compute.ItemCheck(player, itemStack0));
                            if (playerItem.getCount() == 64) {
                                player.getInventory().removeItem(Compute.ItemCheck(player, itemStack0), 64);
                                if (!data.contains("QuestCounts")) data.putInt("QuestCounts", 1);
                                else data.putInt("QuestCounts", data.getInt("QuestCounts") + 1);
                                data.putInt("Quest", -1);
                                player.sendSystemMessage(Component.literal("你成功提交了任务！"));
                                Quest.getOrCreateTagElement(Utils.MOD_ID).putInt("Quest", -1);
                            } else player.sendSystemMessage(Component.literal("似乎没有足够的物品用于提交任务。"));
                        } else player.sendSystemMessage(Component.literal("似乎没有物品用于提交任务。"));
                    } else {
                        if (data.getInt("Quest") == 1) {
                            ItemStack itemStack1 = ModItems.ForestSoul.get().getDefaultInstance();
                            itemStack1.setCount(64);
                            if (Compute.ItemCheck(player, itemStack1) != -1) {
                                ItemStack playerItem = player.getInventory().getItem(Compute.ItemCheck(player, itemStack1));
                                if (playerItem.getCount() == 64) {
                                    player.getInventory().removeItem(Compute.ItemCheck(player, itemStack1), 64);
                                    if (!data.contains("QuestCounts")) data.putInt("QuestCounts", 1);
                                    else data.putInt("QuestCounts", data.getInt("QuestCounts") + 1);
                                    data.putInt("Quest", -1);
                                    player.sendSystemMessage(Component.literal("你成功提交了任务！"));
                                    Quest.getOrCreateTagElement(Utils.MOD_ID).putInt("Quest", -1);
                                } else player.sendSystemMessage(Component.literal("似乎没有足够的物品用于提交任务。"));
                            } else player.sendSystemMessage(Component.literal("似乎没有物品用于提交任务。"));
                        } else {
                            if (data.getInt("Quest") == 2) {
                                ItemStack itemStack2 = ModItems.LakeSoul.get().getDefaultInstance();
                                itemStack2.setCount(64);
                                if (Compute.ItemCheck(player, itemStack2) != -1) {
                                    ItemStack playerItem = player.getInventory().getItem(Compute.ItemCheck(player, itemStack2));
                                    if (playerItem.getCount() == 64) {
                                        player.getInventory().removeItem(Compute.ItemCheck(player, itemStack2), 64);
                                        if (!data.contains("QuestCounts")) data.putInt("QuestCounts", 1);
                                        else data.putInt("QuestCounts", data.getInt("QuestCounts") + 1);
                                        data.putInt("Quest", -1);
                                        player.sendSystemMessage(Component.literal("你成功提交了任务！"));
                                        Quest.getOrCreateTagElement(Utils.MOD_ID).putInt("Quest", -1);
                                    } else
                                        player.sendSystemMessage(Component.literal("似乎没有足够的物品用于提交任务。"));
                                } else player.sendSystemMessage(Component.literal("似乎没有物品用于提交任务。"));
                            } else {
                                ItemStack itemStack3 = ModItems.VolcanoSoul.get().getDefaultInstance();
                                itemStack3.setCount(64);
                                if (Compute.ItemCheck(player, itemStack3) != -1) {
                                    ItemStack playerItem = player.getInventory().getItem(Compute.ItemCheck(player, itemStack3));
                                    if (playerItem.getCount() == 64) {
                                        player.getInventory().removeItem(Compute.ItemCheck(player, itemStack3), 64);
                                        if (!data.contains("QuestCounts")) data.putInt("QuestCounts", 1);
                                        else data.putInt("QuestCounts", data.getInt("QuestCounts") + 1);
                                        data.putInt("Quest", -1);
                                        player.sendSystemMessage(Component.literal("你成功提交了任务！"));
                                        Quest.getOrCreateTagElement(Utils.MOD_ID).putInt("Quest", -1);
                                    } else
                                        player.sendSystemMessage(Component.literal("似乎没有足够的物品用于提交任务。"));
                                } else player.sendSystemMessage(Component.literal("似乎没有物品用于提交任务。"));
                            }
                        }
                    }
                } else {
                    Random r = new Random();
                    int tmpnum = r.nextInt(100);
                    if (tmpnum <= 25) {
                        data.putInt("Quest", 0);
                        Quest.getOrCreateTagElement(Utils.MOD_ID).putInt("Quest", 0);
                        player.sendSystemMessage(Component.literal("你接取了任务:64*平原根源"));
                    } else {
                        if (tmpnum <= 50) {
                            data.putInt("Quest", 1);
                            Quest.getOrCreateTagElement(Utils.MOD_ID).putInt("Quest", 1);
                            player.sendSystemMessage(Component.literal("你接取了任务:64*森林根源"));
                        } else {
                            if (tmpnum <= 75) {
                                data.putInt("Quest", 2);
                                Quest.getOrCreateTagElement(Utils.MOD_ID).putInt("Quest", 2);
                                player.sendSystemMessage(Component.literal("你接取了任务:64*湖泊根源"));
                            } else {
                                data.putInt("Quest", 3);
                                Quest.getOrCreateTagElement(Utils.MOD_ID).putInt("Quest", 3);
                                player.sendSystemMessage(Component.literal("你接取了任务:64*火山根源"));
                            }
                        }
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}

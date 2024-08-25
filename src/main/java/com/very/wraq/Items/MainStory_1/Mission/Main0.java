package com.very.wraq.Items.MainStory_1.Mission;

import com.very.wraq.common.Compute;
import com.very.wraq.common.DelayOperationWithAnimation;
import com.very.wraq.common.Tick;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.WeakHashMap;

public class Main0 extends Item {

    public Main0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("欢迎你来到").append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("这是一个任务物品，你可以看到它有着一个感叹号。"));
        components.add(Component.literal("在维瑞阿契，任务可以引导你探索这个世界。"));
        components.add(Component.literal("如果这个物品属于任务物品，你将可以看到："));
        components.add(Component.literal("在它底部描述有着它所属的任务章节。"));
        components.add(Component.literal("本任务属于维瑞阿契的序章，不是剧情的正式开始。"));
        components.add(Component.literal("维瑞阿契的序章，将会介绍维瑞阿契的各种要素。"));
        components.add(Component.literal("关于其他序章没有提及的要素，请在游戏过程中积极探索。"));
        components.add(Component.literal("希望你能有一个美好的游戏体验！"));
        components.add(Component.literal("本章作者：").append(Component.literal("very_H").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Prologue-O").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        Compute.suffixOfElement(components);
        super.appendHoverText(stack, p_41422_, components, flag);
    }

    public static WeakHashMap<Mob, Integer> testMap = new WeakHashMap<>();

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (!level.isClientSide && !player.isShiftKeyDown()) {
            String name = player.getName().getString();
            CompoundTag data = player.getPersistentData();

            DelayOperationWithAnimation.addToQueue(
                    new DelayOperationWithAnimation(DelayOperationWithAnimation.Animation.samurai,
                            Tick.get() + 10, player) {
                @Override
                public void trig() {
                    player.sendSystemMessage(Component.literal("trig!"));
                }
            });
        }

        if (!level.isClientSide && player.isShiftKeyDown()) {
            player.sendSystemMessage(Component.literal("" + testMap));
        }

        if (level.isClientSide && !player.isShiftKeyDown()) {

        }

        if (level.isClientSide && player.isShiftKeyDown()) {

        }


        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }


}

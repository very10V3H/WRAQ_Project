package com.Very.very.Items.MainStory_1.Mission;

import com.Very.very.Process.Security.Security;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

public class Main0 extends Item{

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
        super.appendHoverText(stack, p_41422_, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (level.isClientSide && !player.isShiftKeyDown()) {
            try {
                Security.WriteName(player.getName().getString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (level.isClientSide && player.isShiftKeyDown()) {
            try {
                player.sendSystemMessage(Component.literal("" + Security.ReadName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!level.isClientSide && player.isShiftKeyDown()) {

        }

        if (!level.isClientSide && !player.isShiftKeyDown()) {
/*            SakuraMob sakuraMob = new SakuraMob(ModEntityType.SakuraMob.get(), level);
            sakuraMob.moveTo(player.position());

            Zombie zombie = new Zombie(EntityType.ZOMBIE,level);
            zombie.goalSelector.addGoal(0,new NearestAttackableTargetGoal<SakuraMob>(zombie, SakuraMob.class,true));
            zombie.goalSelector.addGoal(1,new MoveTowardsTargetGoal(zombie,0.1,0.1f));
            zombie.moveTo(player.position());

            level.addFreshEntity(zombie);
            level.addFreshEntity(sakuraMob);*/
        }

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }


}

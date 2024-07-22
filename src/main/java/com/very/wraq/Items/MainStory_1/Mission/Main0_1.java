package com.very.wraq.Items.MainStory_1.Mission;

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

import java.util.List;

public class Main0_1 extends Item {
    public Main0_1(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("本节将介绍维瑞阿契的").append(Component.literal("剧情推动方式").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("维瑞阿契将以世界探索为主线，展开剧情。"));
        components.add(Component.literal("大多数任务物品的兑换与获取都是通过").append(Component.literal("村民交易").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("这一Minecraft传统交易方式。"));
        components.add(Component.literal("并以右键信物/奖励/宝箱的方式为辅获取一些主线或其他物品。"));
        components.add(Component.literal("前面提到维瑞阿契将以世界探索作为主线，"));
        components.add(Component.literal("那么，跑图与开放世界是主线剧情推动的主要元素。"));
        components.add(Component.literal("在探索的过程中，感受Minecraft原生环境与"));
        components.add(Component.literal("维瑞阿契附加内容，是作者的本意。"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Prologue-I").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (level.isClientSide) {

        }
        if (!level.isClientSide) {

        }
        return super.use(level, player, interactionHand);
    }
}

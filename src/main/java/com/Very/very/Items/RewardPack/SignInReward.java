package com.Very.very.Items.RewardPack;

import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class SignInReward extends Item {
    public SignInReward(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        player.playSound(SoundEvents.PLAYER_LEVELUP);
        if(!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Random r = new Random();
            int tmpNum = r.nextInt(99);
            ItemStack RewardPack = player.getItemInHand(InteractionHand.MAIN_HAND);
            int num = RewardPack.getCount();
            ItemStack RewardPack0 = Moditems.SignInReward.get().getDefaultInstance();
            RewardPack0.setCount(num-1);
            player.setItemInHand(InteractionHand.MAIN_HAND,RewardPack0);
            if(tmpNum<5)
            {
                ItemStack GoldCoin = Moditems.GoldCoin.get().getDefaultInstance();
                GoldCoin.setCount(3);
                player.addItem(GoldCoin);
                player.sendSystemMessage(Component.literal("你从签到奖励包中获得了:"));
                player.sendSystemMessage(Component.literal("3*").append(Component.literal("金币").withStyle(ChatFormatting.GOLD)).append(" 5%"));
                PlayerList list =  player.getServer().getPlayerList();
                List<ServerPlayer> list1 = list.getPlayers();
                ListIterator<ServerPlayer> iterator = list1.listIterator();
                while(true)
                {
                    if(iterator.hasNext())
                    {
                        Player TmpPlayer = iterator.next();
                        TmpPlayer.sendSystemMessage(Component.literal(" "+player.getScoreboardName()+"从签到奖励包中获得了超级奖励！"));
                    }
                    else break;
                }
            }
            else
            {
                if(tmpNum>=5 && tmpNum<15)
                {
                    ItemStack GoldCoin = Moditems.GoldCoin.get().getDefaultInstance();
                    GoldCoin.setCount(2);
                    player.addItem(GoldCoin);
                    player.sendSystemMessage(Component.literal("你从签到奖励包中获得了:"));
                    player.sendSystemMessage(Component.literal("2*").append(Component.literal("金币").withStyle(ChatFormatting.GOLD)).append(" 10%"));

                }
                else
                {
                    if(tmpNum>=15 && tmpNum<30)
                    {
                        ItemStack GoldCoin = Moditems.GoldCoin.get().getDefaultInstance();
                        GoldCoin.setCount(1);
                        player.addItem(GoldCoin);
                        player.sendSystemMessage(Component.literal("你从签到奖励包中获得了:"));
                        player.sendSystemMessage(Component.literal("1*").append(Component.literal("金币").withStyle(ChatFormatting.GOLD)).append(" 15%"));
                    }
                    else
                    {
                        ItemStack SilverCoin = Moditems.SilverCoin.get().getDefaultInstance();
                        SilverCoin.setCount(5);
                        player.addItem(SilverCoin);
                        player.sendSystemMessage(Component.literal("你从签到奖励包中获得了:"));
                        player.sendSystemMessage(Component.literal("5*").append(Component.literal("银币").withStyle(ChatFormatting.GRAY)).append(" 70%"));
                    }
                }
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("右键打开奖励包!"));
        components.add(Component.literal("奖池:").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("3*").append(Component.literal("金币").withStyle(ChatFormatting.GOLD)).append(" 5%"));
        components.add(Component.literal("2*").append(Component.literal("金币").withStyle(ChatFormatting.GOLD)).append(" 10%"));
        components.add(Component.literal("1*").append(Component.literal("金币").withStyle(ChatFormatting.GOLD)).append(" 15%"));
        components.add(Component.literal("5*").append(Component.literal("银币").withStyle(ChatFormatting.GRAY)).append(" 70%"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("SignAward").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}

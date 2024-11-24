package fun.wraq.Items.RewardPack;

import fun.wraq.common.registry.ModItems;
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

public class Boss2 extends Item {

    public Boss2(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        player.playSound(SoundEvents.PLAYER_LEVELUP);
        if (!level.isClientSide && interactionHand == InteractionHand.MAIN_HAND) {
            Random r = new Random();
            int tmpNum = r.nextInt(99);
            ItemStack RewardPack = player.getItemInHand(InteractionHand.MAIN_HAND);
            int num = RewardPack.getCount();
            ItemStack RewardPack0 = ModItems.bossaward2.get().getDefaultInstance();
            RewardPack0.setCount(num - 1);
            player.setItemInHand(InteractionHand.MAIN_HAND, RewardPack0);
            ItemStack goldcoin = ModItems.GOLD_COIN.get().getDefaultInstance();
            ItemStack silvercoin = ModItems.silverCoin.get().getDefaultInstance();
            ItemStack gemspiece = ModItems.gemPiece.get().getDefaultInstance();
            ItemStack plainsoul = ModItems.ForestSoul.get().getDefaultInstance();
            ItemStack plainrune = ModItems.ForestRune.get().getDefaultInstance();
            if (tmpNum < 5) {
                plainrune.setCount(1);
                player.addItem(plainrune);
                PlayerList list = player.getServer().getPlayerList();
                List<ServerPlayer> list1 = list.getPlayers();
                ListIterator<ServerPlayer> iterator = list1.listIterator();
                while (iterator.hasNext()) {
                    Player TmpPlayer = iterator.next();
                    TmpPlayer.sendSystemMessage(Component.literal(" " + player.getScoreboardName() + "从Boss奖励包中获得了超级奖励:" + "森林意志*1"));
                }
            } else {
                if (tmpNum < 20) {
                    gemspiece.setCount(5);
                    player.addItem(gemspiece);
                } else {
                    if (tmpNum < 50) {
                        goldcoin.setCount(2);
                        player.addItem(goldcoin);
                    } else {
                        silvercoin.setCount(32);
                        player.addItem(silvercoin);
                    }
                }
            }
            plainsoul.setCount(16);
            player.addItem(plainsoul);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("右键打开奖励包!").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("BossAward2").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}

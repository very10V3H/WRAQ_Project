package com.very.wraq.process.system.instance;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MopUpPaperLoot extends Item {

    public MopUpPaperLoot(Properties p_41383_) {
        super(p_41383_);
    }

    public static List<Item> mopUpPaperList = new ArrayList<>();

    public static void setMopUpPaperList() {
        mopUpPaperList.addAll(List.of(
                ModItems.CastleMopUpPaper.get(),
                ModItems.DevilMopUpPaper.get(),
                ModItems.IceKnightMopUpPaper.get(),
                ModItems.MoonMopUpPaper.get(),
                ModItems.PlainMopUpPaper.get(),
                ModItems.PurpleIronKnightMopUpPaper.get(),
                ModItems.SakuraBossMopUpPaper.get()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (mopUpPaperList.isEmpty()) setMopUpPaperList();
            Random rand = new Random();
            ItemStack stack = new ItemStack(mopUpPaperList.get(rand.nextInt(mopUpPaperList.size())));
            Compute.playerItemUseWithRecord(player);
            Compute.itemStackGive(player, stack);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使用以随机获得1张副本征讨券").withStyle(ChatFormatting.RED));
        components.add(Component.literal(" 可能获取的征讨券有：").withStyle(ChatFormatting.WHITE));
        if (mopUpPaperList.isEmpty()) setMopUpPaperList();
        mopUpPaperList.forEach((item) -> {
            components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                    append(item.getDefaultInstance().getDisplayName()));
        });
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}

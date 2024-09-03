package com.very.wraq.process.system.parkour;

import com.very.wraq.Items.KillPaper.KillPaper;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.Utils;
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

import java.util.List;
import java.util.Random;

public class KillPaperLoot extends Item {
    public KillPaperLoot(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使用以随机抽取一张征讨券！").withStyle(ChatFormatting.LIGHT_PURPLE));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Random rand = new Random();
            String tag = KillPaper.getDropListMap().keySet().stream().toList().get(rand.nextInt(KillPaper.getDropListMap().size()));
            ItemStack itemStack = new ItemStack(ModItems.killPaper.get());
            itemStack.getOrCreateTagElement(Utils.MOD_ID).putString(KillPaper.killPaperType, tag);
            Compute.itemStackGive(player, itemStack);
            Compute.playerItemUseWithRecord(player);
        }
        return super.use(level, player, interactionHand);
    }
}

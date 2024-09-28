package fun.wraq.Items.Lotteries;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.struct.ItemAndWeight;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
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
import java.util.concurrent.atomic.AtomicInteger;

public class CommonLotteries extends Item {

    public CommonLotteries(Properties p_41383_) {
        super(p_41383_);
    }

    private List<ItemAndWeight> itemAndWeightList = new ArrayList<>();

    private void setItemAndWeightList() {
        itemAndWeightList.add(new ItemAndWeight(ModItems.FantasyMedal.get(), 1));
        itemAndWeightList.add(new ItemAndWeight(ModItems.FantasyBracelet.get(), 1));
        itemAndWeightList.add(new ItemAndWeight(ModItems.WorldSoul3.get(), 1));
        itemAndWeightList.add(new ItemAndWeight(ModItems.NewRevelationBook.get(), 1));
        itemAndWeightList.add(new ItemAndWeight(ModItems.KillPaperLoot.get(), 5));
        itemAndWeightList.add(new ItemAndWeight(ModItems.MopUpPaperLoot.get(), 10));
        itemAndWeightList.add(new ItemAndWeight(ModItems.ForgeEnhance2.get(), 10));
        itemAndWeightList.add(new ItemAndWeight(ModItems.goldCoin.get(), 30));
        itemAndWeightList.add(new ItemAndWeight(ModItems.WorldSoul2.get(), 30));
    }

    private int TotalWeight() {
        if (itemAndWeightList.isEmpty()) setItemAndWeightList();
        AtomicInteger weight = new AtomicInteger();
        itemAndWeightList.forEach(itemAndWeight -> {
            weight.addAndGet(itemAndWeight.getWeight());
        });
        return weight.get();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        if (itemAndWeightList.isEmpty()) setItemAndWeightList();
        int totalWeight = TotalWeight();
        components.add(Component.literal(""));
        components.add(Component.literal("从彩券中，你可能会得到:").withStyle(CustomStyle.styleOfFantasy));
        itemAndWeightList.forEach(itemAndWeight -> {
            components.add(Component.literal("  ").withStyle(ChatFormatting.WHITE).append(itemAndWeight.getItem().getDefaultInstance().getDisplayName()).append(Component.literal(String.format(" %.2f%%", itemAndWeight.getWeight() * 100.0 / totalWeight)).withStyle(CustomStyle.styleOfFantasy)));
        });
        super.appendHoverText(itemStack, p_41422_, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            Random random = new Random();
            AtomicInteger RandomIndex = new AtomicInteger(random.nextInt(1, TotalWeight() + 1));
            if (itemAndWeightList.isEmpty()) setItemAndWeightList();
            itemAndWeightList.forEach(itemAndWeight -> {
                if (RandomIndex.get() > 0) {
                    RandomIndex.addAndGet(-itemAndWeight.getWeight());
                    if (RandomIndex.get() <= 0) {
                        if (itemAndWeight.getWeight() < 10)
                            Compute.formatBroad(level, Component.literal("彩券").withStyle(CustomStyle.styleOfFantasy), Component.literal("").withStyle(ChatFormatting.WHITE).append(player.getDisplayName()).append(Component.literal(" 通过").withStyle(ChatFormatting.WHITE)).append(itemStack.getDisplayName()).append(Component.literal("获得了:").withStyle(ChatFormatting.WHITE)).append(itemAndWeight.getItem().getDefaultInstance().getDisplayName()));
                        InventoryOperation.itemStackGive(player, itemAndWeight.getItem().getDefaultInstance());
                    }
                }
            });
            Compute.playerItemUseWithRecord(player);
        }
        return super.use(level, player, interactionHand);
    }
}

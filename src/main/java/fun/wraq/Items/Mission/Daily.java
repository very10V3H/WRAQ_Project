package fun.wraq.Items.Mission;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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

public class Daily extends Item {
    public Daily(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.literal("·任务").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("-每日任务").withStyle(ChatFormatting.AQUA));

        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            boolean flag = true;
            CompoundTag data = player.getPersistentData();
            CompoundTag ItemData = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
/*
            if(ItemData.contains("DailyMission1") && data.contains("DailyMission1") && data.getInt("DailyMission1") < ItemData.getInt("DailyMission1")) flag = false;
            if(ItemData.contains("DailyMission2") && data.contains("DailyMission2") && data.getInt("DailyMission2") < ItemData.getInt("DailyMission2")) flag = false;
            if(ItemData.contains("DailyMission3") && data.contains("DailyMission3") && data.getInt("DailyMission3") < ItemData.getInt("DailyMission3")) flag = false;
            if(ItemData.contains("DailyMission4") && data.contains("DailyMission4") && data.getInt("DailyMission4") < ItemData.getInt("DailyMission4")) flag = false;
            if(ItemData.contains("DailyMission5") && data.contains("DailyMission5") && data.getInt("DailyMission5") < ItemData.getInt("DailyMission5")) flag = false;
            if(ItemData.contains("DailyMission6") && data.contains("DailyMission6") && data.getInt("DailyMission6") < ItemData.getInt("DailyMission6")) flag = false;
            if(ItemData.contains("DailyMission7") && data.contains("DailyMission7") && data.getInt("DailyMission7") < ItemData.getInt("DailyMission7")) flag = false;
            if(ItemData.contains("DailyMission8") && data.contains("DailyMission8") && data.getInt("DailyMission8") < ItemData.getInt("DailyMission8")) flag = false;
            if(ItemData.contains("DailyMission9") && data.contains("DailyMission9") && data.getInt("DailyMission9") < ItemData.getInt("DailyMission9")) flag = false;
            if(ItemData.contains("DailyMission10") && data.contains("DailyMission10") && data.getInt("DailyMission10") < ItemData.getInt("DailyMission10")) flag = false;
            if(ItemData.contains("DailyMission11") && data.contains("DailyMission11") && data.getInt("DailyMission11") < ItemData.getInt("DailyMission11")) flag = false;
            if(ItemData.contains("DailyMission12") && data.contains("DailyMission12") && data.getInt("DailyMission12") < ItemData.getInt("DailyMission12")) flag = false;
            if(ItemData.contains("DailyMission13") && data.contains("DailyMission13") && data.getInt("DailyMission13") < ItemData.getInt("DailyMission13")) flag = false;
            if(ItemData.contains("DailyMission14") && data.contains("DailyMission14") && data.getInt("DailyMission14") < ItemData.getInt("DailyMission14")) flag = false;
            if(ItemData.contains("DailyMission15") && data.contains("DailyMission15") && data.getInt("DailyMission15") < ItemData.getInt("DailyMission15")) flag = false;
            if(ItemData.contains("DailyMission16") && data.contains("DailyMission16") && data.getInt("DailyMission16") < ItemData.getInt("DailyMission16")) flag = false;
*/
            if (!ItemData.getString(InventoryCheck.owner).equals(player.getName().getString())) {
                Compute.sendFormatMSG(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                        Component.literal("这个任务似乎不是你的任务。"));
            } else {
                for (int i = 1; i <= 17; i++) {
                    if (ItemData.contains("DailyMission" + i) && data.contains("DailyMission" + i) && data.getInt("DailyMission" + i) < ItemData.getInt("DailyMission" + i))
                        flag = false;
                    if (!flag) break;
                }
                if (!flag) {
                    Compute.sendFormatMSG(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                            Component.literal("任务还未完成。"));
                } else {
                    ItemStack GemPiece = ModItems.GEM_PIECE.get().getDefaultInstance();
                    GemPiece.setCount(player.experienceLevel / 2);
                    InventoryOperation.itemStackGive(player, GemPiece);
                    Compute.RandomPotionBagProvider(player, 6, 0.75);
                    ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                    itemStack.setCount(itemStack.getCount() - 1);
                    player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
                    Compute.givePercentExpToPlayer(player, 0.5, 0, player.experienceLevel);
                    Compute.formatBroad(level, Component.literal("每日任务").withStyle(ChatFormatting.AQUA),
                            Component.literal(player.getName().getString() + "完成了每日任务！").withStyle(ChatFormatting.WHITE));
                    for (int i = 1; i <= 17; i++) data.putInt("DailyMission" + i, 0);
                    if (player.experienceLevel >= 60) {
                        ItemStack ironlove = ModItems.IronLove.get().getDefaultInstance();
                        ironlove.getOrCreateTagElement(Utils.MOD_ID).putString(InventoryCheck.owner, player.getName().getString());
                        InventoryOperation.itemStackGive(player, ironlove);
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}

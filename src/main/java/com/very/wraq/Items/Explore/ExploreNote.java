package com.very.wraq.Items.Explore;

import com.very.wraq.common.registry.ModItems;
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

public class ExploreNote extends Item {
    public ExploreNote(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        CompoundTag data = player.getPersistentData();
        double LocationX = player.getX();
        double LocationY = player.getY();
        double LocationZ = player.getZ();
        if ((!level.isClientSide) && LocationX >= 398 && LocationX <= 414 && LocationY >= 70 && LocationY <= 81 && LocationZ >= 998 && LocationZ <= 1014) {
            if (!data.contains("note0")) {
                player.addItem(ModItems.Note_0.get().getDefaultInstance());
                data.putBoolean("note0", true);
                player.sendSystemMessage(Component.literal("已将此处记录到笔记中。"));
            } else player.sendSystemMessage(Component.literal("此处已被记录到笔记中。"));
        } //符文祭坛-平原 note0
        else {
            if ((!level.isClientSide) && LocationX >= -94 && LocationX <= -80 && LocationY >= 96 && LocationY <= 106 && LocationZ >= 971 && LocationZ <= 985) {
                if (!data.contains("note1")) {
                    player.addItem(ModItems.Note_1.get().getDefaultInstance());
                    data.putBoolean("note1", true);
                    player.sendSystemMessage(Component.literal("已将此处记录到笔记中。"));
                } else player.sendSystemMessage(Component.literal("此处已被记录到笔记中。"));
            } //符文祭坛-森林 note1
            else {
                if ((!level.isClientSide) && LocationX >= 31 && LocationX <= 41 && LocationY >= 8 && LocationY <= 13 && LocationZ >= 1100 && LocationZ <= 1112) {
                    if (!data.contains("note2")) {
                        player.addItem(ModItems.Note_2.get().getDefaultInstance());
                        data.putBoolean("note2", true);
                        player.sendSystemMessage(Component.literal("已将此处记录到笔记中。"));
                    } else player.sendSystemMessage(Component.literal("此处已被记录到笔记中。"));
                } //符文祭坛-火山 note2
                else {
                    if ((!level.isClientSide) && LocationX >= 30 && LocationX <= 40 && LocationY >= -54 && LocationY <= -46 && LocationZ >= 993 && LocationZ <= 1002) {
                        if (!data.contains("note3")) {
                            player.addItem(ModItems.Note_3.get().getDefaultInstance());
                            data.putBoolean("note3", true);
                            player.sendSystemMessage(Component.literal("已将此处记录到笔记中。"));
                        } else player.sendSystemMessage(Component.literal("此处已被记录到笔记中。"));
                    } //符文祭坛-湖泊 note3
                }
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("地质学家的笔记，用于记录特殊的地质景观。"));
        components.add(Component.literal("Tips:在拥有").append(Component.literal("标记").withStyle(ChatFormatting.AQUA)).append(Component.literal("的自然景观观察点处右键")));
        components.add(Component.literal("以获取地质笔记。"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("MainStory-Explore").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.BOLD));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}

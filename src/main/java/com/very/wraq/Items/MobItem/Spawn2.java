package com.very.wraq.Items.MobItem;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
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

public class Spawn2 extends Item {

    public Spawn2(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!player.level().isClientSide) {
/*
            if (player.getX() >= 1933.5 && player.getX() <= 1952.5
                    && player.getY() >= 165 && player.getY() <= 170
                    && player.getZ() >= 1043.5 && player.getZ() <= 1050.5) {
                if (Utils.boss2.isAlive()) {
                    Compute.FormatMSGSend(player,Component.literal("唤魔").withStyle(ChatFormatting.RED),
                            Component.literal("已经有一个Boss挑战正在进行了！").withStyle(ChatFormatting.WHITE));
                }
                else {
                    ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                    itemStack.setCount(itemStack.getCount()-1);
                    player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
                    Utils.boss2 = new Boss2(EntityInit.Boss2.get(), level);
                    Compute.SetMobCustomName(Utils.boss2,ModItems.ArmorBoss2.get(),
                            Component.literal("突见忍").withStyle(CustomStyle.styleOfSakura));
                    Utils.boss2.getAttribute(Attributes.MAX_HEALTH).setBaseValue(Boss2.MaxHealth * (1 + Utils.Boss2DeadTimes));
                    Utils.boss2.setHealth(Utils.boss2.getMaxHealth());
                    Utils.boss2.setItemSlot(EquipmentSlot.HEAD, ModItems.ArmorBoss2.get().getDefaultInstance());
                    Utils.boss2.moveTo(1941,167,1047);
                    level.addFreshEntity(Utils.boss2);
                    for (int i = 0; i < 3; i ++) {
                        for (int j = 0; j < 5; j ++) {
                            BlockPos blockPos = new BlockPos(1925,169 + j,1042 + i);
                            level.setBlockAndUpdate(blockPos, Blocks.BARRIER.defaultBlockState());
                        }
                    }

                    Utils.Boss2DeadTick = level.getServer().getTickCount() + 12000;

                    Compute.FormatBroad(level,Component.literal("黄金屋").withStyle(ChatFormatting.GOLD),
                            Component.literal("突见忍出现在了黄金屋！").withStyle(ChatFormatting.WHITE));
                    if (Utils.Boss2DeadTimes > 0) Compute.FormatBroad(level,Component.literal("黄金屋").withStyle(ChatFormatting.GOLD),
                            Component.literal("突见忍的实力提升了").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal((Utils.Boss2DeadTimes) + "倍").withStyle(ChatFormatting.RED)));

                }
            }
            else player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("唤魔").withStyle(ChatFormatting.RED)).append(Component.literal("]").withStyle(ChatFormatting.GRAY)).append(Component.literal("不能在此处召唤boss").withStyle(ChatFormatting.WHITE)));
*/
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Rare(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("一个诱饵，用来引诱纸醉金迷的人。").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Sakura").withStyle(CustomStyle.styleOfSakura).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

}

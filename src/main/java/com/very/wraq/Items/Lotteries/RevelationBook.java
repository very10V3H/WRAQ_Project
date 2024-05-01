package com.very.wraq.Items.Lotteries;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
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
import java.util.Random;

public class RevelationBook extends Item {

    public RevelationBook(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(" 使用以获得当前等级下的5% - 10%经验值").withStyle(CustomStyle.styleOfFantasy));
        super.appendHoverText(itemStack, p_41422_, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {

            try {
                Compute.PlayerItemUseWithRecord(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Random random = new Random();
            Compute.ExpPercentGetAndMSGSend(player,random.nextDouble(0.05,0.1),0,player.experienceLevel);

        }
        return super.use(level, player, interactionHand);
    }
}

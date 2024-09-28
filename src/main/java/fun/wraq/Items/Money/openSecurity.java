package fun.wraq.Items.Money;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class openSecurity extends Item {
    public openSecurity(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Utils.Vplayer = player;
        Utils.Security = true;
        if (!level.isClientSide) {
            if (player.getPersistentData().contains("PersistentSecurity0"))
                Utils.security0 = player.getPersistentData().getDouble("PersistentSecurity0");
            if (player.getPersistentData().contains("PersistentSecurity1"))
                Utils.security1 = player.getPersistentData().getDouble("PersistentSecurity1");
            if (player.getPersistentData().contains("PersistentSecurity2"))
                Utils.security2 = player.getPersistentData().getDouble("PersistentSecurity2");
            if (player.getPersistentData().contains("PersistentSecurity3"))
                Utils.security3 = player.getPersistentData().getDouble("PersistentSecurity3");
            Compute.broad(level, Component.literal("[公告]").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD).append(Component.literal("股市已开盘！").withStyle(ChatFormatting.WHITE)));
            Compute.broad(level, Component.literal("当前股市行情:").withStyle(ChatFormatting.GOLD));
            Compute.broad(level, Component.literal("——————————").withStyle(ChatFormatting.AQUA));
            Compute.broad(level, Component.literal("维瑞库尤酒店：").append(String.valueOf(Utils.security0)));
            Compute.broad(level, Component.literal("维瑞库尤矿业：").append(String.valueOf(Utils.security1)));
            Compute.broad(level, Component.literal("维瑞库尤渔业：").append(String.valueOf(Utils.security2)));
            Compute.broad(level, Component.literal("维瑞库尤建设：").append(String.valueOf(Utils.security3)));
        }
        return super.use(level, player, interactionHand);
    }
}

package com.very.wraq.Items.SkillItems;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.SkillPackets.SkillRequestC2SPacket;
import com.very.wraq.netWorking.misc.TeamPackets.ScreenSetS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ID_Card extends Item{
    public ID_Card(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("维瑞阿契的身份卡。"));
        components.add(Component.literal("右键查看属性、能力与精通信息。"));
        components.add(Component.literal(" "));
        components.add(Component.literal("ID Card").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (level.isClientSide) ModNetworking.sendToServer(new SkillRequestC2SPacket());
        if (!level.isClientSide) {
            ModNetworking.sendToClient(new ScreenSetS2CPacket(3),(ServerPlayer) player);
        }
        return super.use(level, player, interactionHand);
    }
}

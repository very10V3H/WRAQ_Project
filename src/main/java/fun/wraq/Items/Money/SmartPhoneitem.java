package fun.wraq.Items.Money;

import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SmartPhonePackets.OpenSmartPhoneS2CPacket;
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

public class SmartPhoneitem extends Item {

    public SmartPhoneitem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            ModNetworking.sendToClient(new OpenSmartPhoneS2CPacket(), (ServerPlayer) player);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("一部智能手机，右键打开界面。"));
        components.add(Component.literal("Items-SmartPhone").withStyle(ChatFormatting.GOLD));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }
}

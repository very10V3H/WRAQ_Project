package fun.wraq.series.events.spring2024;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.projectiles.firework.Firework;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FireCracker extends WraqItem {

    public FireCracker(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 新年快乐！").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" 右键点燃一枚鞭炮").withStyle(ChatFormatting.WHITE));
        components.add(Te.s(" 命中", "年兽", CustomStyle.styleOfSpring,
                "会对其造成", "减速", CustomStyle.styleOfStone, "与",
                "202.5w - 2025w", CustomStyle.styleOfPower, "真实伤害", CustomStyle.styleOfSea));
        components.add(Component.literal("Idea From:Guang_Yi & LengCheng").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(ComponentUtils.getSuffixOfSpring2025());
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Compute.playerItemUseWithRecord(player);
            player.getCooldowns().addCooldown(this, 5);
            Firework firework = new Firework(ModEntityType.FIRE_WORK.get(), player, level);
            firework.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1f, 1);
            firework.setSilent(true);
            level.addFreshEntity(firework);
            ModNetworking.sendToClient(new SoundsS2CPacket(7), (ServerPlayer) player);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}

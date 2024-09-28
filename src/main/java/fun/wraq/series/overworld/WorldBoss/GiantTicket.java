package fun.wraq.series.overworld.WorldBoss;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
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

import java.util.List;

public class GiantTicket extends Item {

    public GiantTicket(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使用以预约下一次巨人僵尸挑战!").withStyle(CustomStyle.styleOfPower));
        components.add(Component.literal(" Idea From:LXYZO").withStyle(ChatFormatting.LIGHT_PURPLE));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            if (Utils.giant == null || !Utils.giant.isAlive()) {
                if (!Utils.GiantPlayerList.contains(player.getName().getString())) {
                    Utils.GiantPlayerList.add(player.getName().getString());
                    Compute.sendFormatMSG(player, Component.literal("世界Boss").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(" 您已成功预约下一次世界Boss挑战！").withStyle(ChatFormatting.WHITE));
                    Compute.playerItemUseWithRecord(player);

                } else {
                    Compute.sendFormatMSG(player, Component.literal("世界Boss").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal(" 你已经预约下次世界Boss挑战了！").withStyle(ChatFormatting.WHITE));

                }
            } else {
                Compute.sendFormatMSG(player, Component.literal("世界Boss").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal(" 世界Boss正在被挑战，请等待世界Boss死亡。").withStyle(ChatFormatting.WHITE));
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }


}

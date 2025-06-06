package fun.wraq.series.end.recallBooks;

import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
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

public class SpiderRecallBook extends Item {
    public SpiderRecallBook(Properties p_41383_) {
        super(p_41383_);
        Display.materialList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·回忆录").withStyle(CustomStyle.styleOfEnd));
        components.add(Component.literal(" "));
        components.add(Component.literal("微光森林的回忆").withStyle(CustomStyle.styleOfSpider));
        components.add(Component.literal(" "));
        components.add(Component.literal("Recall-Spider").withStyle(CustomStyle.styleOfEnd).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (Compute.RecallPlayerCheck((ServerPlayer) player)) {
                Compute.sendFormatMSG(player, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                        Component.literal("先专心回忆这段经历吧！").withStyle(ChatFormatting.WHITE));
            } else {
                if (Utils.spiderRecall.recallPlayer == null && Utils.spiderRecall.recallCount == -1 && level.equals(level.getServer().getLevel(Level.END))) {
                    Compute.playerItemUseWithRecord(player);
                    Utils.spiderRecall.recallCount = 620;
                    Utils.spiderRecall.recallPlayer = (ServerPlayer) player;
                    Utils.spiderRecall.recallTimeLimit = 12000;
                    Utils.spiderRecall.playerName = player.getDisplayName();
                    Utils.spiderRecall.style = CustomStyle.styleOfSpider;
                    Utils.spiderRecall.zoneName = "微光森林";
                } else {
                    if (!level.equals(level.getServer().getLevel(Level.END))) {
                        Compute.sendFormatMSG(player, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("在这个维度，似乎不需要回忆什么。").withStyle(ChatFormatting.WHITE));

                    } else {
                        Compute.sendFormatMSG(player, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("有玩家正在进行这项回忆，请等一下。").withStyle(ChatFormatting.WHITE));
                    }
                }
            }
        }
        return super.use(level, player, interactionHand);
    }
}

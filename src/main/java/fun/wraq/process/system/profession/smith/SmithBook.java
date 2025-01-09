package fun.wraq.process.system.profession.smith;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SmithBook extends WraqItem {

    public SmithBook(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s("习得", "「初等工匠学」", CustomStyle.styleOfStone));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (level.isClientSide || !interactionHand.equals(InteractionHand.MAIN_HAND)) {
            return super.use(level, player, interactionHand);
        }
        if (SmithPlayerData.getTier(player) > 0) {
            SmithPlayerData.sendMSG(player, Te.s("你已经习得了", "「初等工匠学」", CustomStyle.styleOfStone,
                    "，不必再次学习。"));
        } else {
            Compute.playerItemUseWithRecord(player);
            SmithPlayerData.setTier(player, 1);
            InventoryOperation.itemStackGiveWithMSG(player, SmithItems.STONE_HAMMER.get());
            SmithPlayerData.sendMSG(player, Te.s("成功习得了", "「初等工匠学」", CustomStyle.styleOfStone));
        }
        return super.use(level, player, interactionHand);
    }
}

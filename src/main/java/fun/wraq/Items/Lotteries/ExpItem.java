package fun.wraq.Items.Lotteries;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
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
import java.util.Random;

public class ExpItem extends Item {

    private final double rateOrigin;
    private final double rateBound;
    public ExpItem(Properties properties, double rateOrigin, double rateBound) {
        super(properties);
        this.rateOrigin = rateOrigin;
        this.rateBound = rateBound;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        components.add(Te.s(" 使用以获得当前等级下的", String.format("%.0f%%", rateOrigin * 100),
                " ~ ", String.format("%.0f%%", rateBound * 100), "经验值", CustomStyle.styleOfFantasy,
                "(达125级后可能与实际值有差异)"));
        super.appendHoverText(itemStack, p_41422_, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.playerItemUseWithRecord(player);
            Random random = new Random();
            Compute.givePercentExpToPlayer(player, random.nextDouble(rateOrigin, rateBound),
                    0, player.experienceLevel);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

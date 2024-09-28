package fun.wraq.Items.Lotteries;

import fun.wraq.common.Compute;
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

public class RevelationBook extends Item {

    private final boolean isNew;

    public RevelationBook(Properties p_41383_, boolean isNew) {
        super(p_41383_);
        this.isNew = isNew;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        if (!isNew)
            components.add(Component.literal(" 使用以获得当前等级下的5% - 10%经验值（受等级上限影响）").withStyle(CustomStyle.styleOfFantasy));
        else components.add(Component.literal(" 使用以获得当前等级下的5%经验值（达125级后可能与实际值有差异）").withStyle(CustomStyle.styleOfFantasy));
        super.appendHoverText(itemStack, p_41422_, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {

            Compute.playerItemUseWithRecord(player);

            if (isNew) {
                Compute.givePercentExpToPlayer(player, 0.05, 0, player.experienceLevel);
            } else {
                Random random = new Random();
                Compute.givePercentExpToPlayer(player, random.nextDouble(0.05, 0.1), 0, player.experienceLevel);
            }

        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return isNew;
    }
}

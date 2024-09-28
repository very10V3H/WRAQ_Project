package fun.wraq.Items.Ps;

import fun.wraq.common.Compute;
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

public class PsBottle extends Item {
    private final int Num;

    public PsBottle(Properties p_41383_, int Num) {
        super(p_41383_);
        this.Num = Num;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.addOrCostPlayerPsValueIgnoreLimit(player, Num);
            Compute.playerItemUseWithRecord(player);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("使用以补充").withStyle(ChatFormatting.WHITE).
                append(Component.literal(Num + "体力").withStyle(ChatFormatting.YELLOW)));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}

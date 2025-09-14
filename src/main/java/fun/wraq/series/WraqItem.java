package fun.wraq.series;

import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.render.gui.illustrate.Display;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WraqItem extends Item implements UsageOrGetWayDescriptionItem {

    public OnPickupListener onPickupListener;
    private boolean isFoiled = false;

    public interface OnPickupListener {
        void onPickup(Player player);
    }

    public WraqItem(Properties properties) {
        super(properties);
        Display.materialList.add(this);
    }

    public WraqItem(Properties properties, boolean isMaterial, OnPickupListener onPickupListener) {
        super(properties);
        if (isMaterial) {
            Display.materialList.add(this);
        }
        this.onPickupListener = onPickupListener;
    }

    public WraqItem(Properties properties, boolean isMaterial, boolean isFoiled) {
        super(properties);
        if (isMaterial) {
            Display.materialList.add(this);
        }
        this.isFoiled = isFoiled;
    }

    private List<Component> description = List.of();
    public WraqItem(Properties properties, boolean isMaterial, boolean isFoiled, List<Component> description) {
        this(properties, isMaterial, isFoiled);
        this.description = description;
    }

    private List<Component> getWayDescription;
    public WraqItem(Properties properties, boolean isFoiled, List<Component> description) {
        this(properties, true, isFoiled);
        this.getWayDescription = description;
    }

    @Override
    public List<Component> getWayDescription() {
        return getWayDescription == null ? List.of() : getWayDescription;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        if (!description.isEmpty()) {
            components.addAll(description);
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isFoiled;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            useOnServerSide(player);
        }
        return super.use(level, player, interactionHand);
    }

    public void useOnServerSide(Player player) {
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
}

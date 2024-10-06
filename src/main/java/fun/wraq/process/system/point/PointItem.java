package fun.wraq.process.system.point;

import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PointItem extends Item {

    private final String type;
    public PointItem(String type) {
        super(new Properties().rarity(Rarity.create(type + "_rariry", style -> {
            return Point.STYLE.get(type);
        })));
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level p_41422_, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, p_41422_, components, tooltipFlag);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        if (!level.isClientSide) {
            Point.increment(player, type, stack.getCount());
            InventoryOperation.removeItem(player.getInventory(), this, stack.getCount());
        }
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }
}

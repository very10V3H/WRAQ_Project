package fun.wraq.process.system.potion;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
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

public class PotionBag extends Item {

    private final Item potion;

    public PotionBag(Properties properties, Item potion) {
        super(properties);
        this.potion = potion;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("右键使用").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.GOLD));
        components.add(Component.literal("获得:").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(potion.getDefaultInstance().getDisplayName()).
                append(Component.literal("药水 * 5").withStyle(ChatFormatting.WHITE)));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack itemStack = new ItemStack(potion, 5);
            InventoryOperation.giveItemStack(player, itemStack);
            Compute.playerItemUseWithRecord(player);
        }
        return super.use(level, player, interactionHand);
    }

    public static List<Item> getPotionBagItems() {
        return List.of(
                ModItems.ATTACK_UP_POTION_BAG.get(),
                ModItems.DEFENCE_PENETRATION_POTION_BAG.get(),
                ModItems.CRIT_RATE_UP_POTIONBAG.get(),
                ModItems.CRIT_DAMAGE_UP_POTION_BAG.get(),
                ModItems.MANA_DAMAGE_UP_POTION_BAG.get(),
                ModItems.MANA_PENETRATION_POTION_BAG.get(),
                ModItems.MANA_RECOVER_POTION_BAG.get(),
                ModItems.POWER_RELEASE_SPEED_POTION_BAG.get(),
                ModItems.HEALTH_STEAL_UP_POTION_BAG.get(),
                ModItems.DEFENCE_UP_POTION_BAG.get(),
                ModItems.MANA_DEFENCE_UP_POTION_BAG.get(),
                ModItems.MOVEMENT_SPEED_UP_POTION_BAG.get(),
                ModItems.HEALTH_RECOVER_POTION_BAG.get()
        );
    }
}

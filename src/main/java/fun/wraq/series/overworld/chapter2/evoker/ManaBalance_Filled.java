package fun.wraq.series.overworld.chapter2.evoker;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.render.hud.Mana;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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

public class ManaBalance_Filled extends Item {
    public ManaBalance_Filled(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·材料/容器").withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" "));
        components.add(Component.literal("魔力稳定石，用于存储魔力。").withStyle(ChatFormatting.DARK_PURPLE));
        components.add(Component.literal("右键使用，获得稳定石中的所有魔力。"));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-evoker").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (interactionHand == InteractionHand.MAIN_HAND && !level.isClientSide) {
            Compute.playerItemUseWithRecord(player);
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag data = player.getPersistentData();
            data.putInt("MANA", itemStack.getOrCreateTagElement(Utils.MOD_ID).getInt("MANA"));
            ItemStack itemStack1 = ModItems.ManaBalance_Empty.get().getDefaultInstance();
            itemStack1.getOrCreateTagElement(Utils.MOD_ID);
            player.addItem(itemStack1);
            Mana.updateManaStatus(player);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

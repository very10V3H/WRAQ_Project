package com.very.wraq.series.overworld.chapter2.evoker;

import com.very.wraq.common.Compute;
import com.very.wraq.render.hud.Mana;
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

public class ManaBucket extends Item {
    public ManaBucket(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·材料/药水").withStyle(ChatFormatting.LIGHT_PURPLE));
        components.add(Component.literal(" "));
        components.add(Component.literal("桶装的唤魔者的魔力，似乎被轻微污染了。").withStyle(ChatFormatting.DARK_PURPLE));
        components.add(Component.literal("按住潜行右键饮用：恢复全部魔力，扣除50%当前生命值。"));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-evoker").withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (interactionHand == InteractionHand.MAIN_HAND && !level.isClientSide && player.isShiftKeyDown()) {
            Compute.playerItemUseWithRecord(player);
            player.setHealth(player.getHealth() * 0.5F);
            CompoundTag data = player.getPersistentData();
            data.putDouble("MANA", data.getDouble("MAXMANA"));
            Mana.updateManaStatus(player);
            player.getCooldowns().addCooldown(this, 200);
        }
        return super.use(level, player, interactionHand);
    }

}

package fun.wraq.series.overworld.chapter2.sea;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SeaCore extends Item {
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (interactionHand.equals(InteractionHand.OFF_HAND)
                && Utils.sceptreTag.containsKey(player.getItemInHand(InteractionHand.MAIN_HAND).getItem())
                && player.isShiftKeyDown()) {
            ItemStack MainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag MainHandData = MainHand.getOrCreateTagElement(Utils.MOD_ID);
            if (MainHandData.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Utils.ManaCoreMap.get(MainHandData.getString(StringUtils.ManaCore.ManaCore))));
                MainHandData.putString(StringUtils.ManaCore.ManaCore, StringUtils.ManaCore.SeaCore);
            } else {
                player.setItemInHand(InteractionHand.OFF_HAND, Items.AIR.getDefaultInstance());
                MainHandData.putString(StringUtils.ManaCore.ManaCore, StringUtils.ManaCore.SeaCore);
            }
            Compute.forgingHoverName(player.getItemInHand(InteractionHand.MAIN_HAND));

        }
        return super.use(level, player, interactionHand);
    }

    public SeaCore(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.ManaCoreDescription(components);
        Compute.DescriptionPassive(components, Component.literal("灵魂救赎").withStyle(CustomStyle.styleOfSea));
        components.add(Component.literal("使法球附带:").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal("基于目标已损失生命值造成至多").withStyle(CustomStyle.styleOfSea).
                append(ComponentUtils.exTrueDamage("100%")));
        components.add(Component.literal("倍率随目标已损失生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

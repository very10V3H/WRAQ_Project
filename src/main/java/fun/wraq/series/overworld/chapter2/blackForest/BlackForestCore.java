package fun.wraq.series.overworld.chapter2.blackForest;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.SceptreAttribute;
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

public class BlackForestCore extends Item {
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (interactionHand.equals(InteractionHand.OFF_HAND)
                && SceptreAttribute.isHandling(player) && player.isShiftKeyDown()) {
            ItemStack MainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            CompoundTag MainHandData = MainHand.getOrCreateTagElement(Utils.MOD_ID);
            if (MainHandData.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Utils.ManaCoreMap.get(MainHandData.getString(StringUtils.ManaCore.ManaCore))));
                MainHandData.putString(StringUtils.ManaCore.ManaCore, StringUtils.ManaCore.BlackForestCore);
            } else {
                player.setItemInHand(InteractionHand.OFF_HAND, Items.AIR.getDefaultInstance());
                MainHandData.putString(StringUtils.ManaCore.ManaCore, StringUtils.ManaCore.BlackForestCore);
            }
            Compute.forgingHoverName(player.getItemInHand(InteractionHand.MAIN_HAND));

        }
        return super.use(level, player, interactionHand);
    }

    public BlackForestCore(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.ManaCoreDescription(components);
        Compute.DescriptionPassive(components, Component.literal("灵魂收割").withStyle(CustomStyle.styleOfHusk));
        components.add(Component.literal("使法球附带:").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal("基于目标当前生命值造成至多").withStyle(CustomStyle.styleOfHusk).
                append(ComponentUtils.exManaDamage("100%")));
        components.add(Component.literal("倍率随目标当前生命值线性增长").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

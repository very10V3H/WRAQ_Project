package com.very.wraq.series.specialevents.springFes;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.render.toolTip.CustomStyle;
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

public class SpringScale extends Item implements ActiveItem {

    private final int tier;

    public SpringScale(Properties p_41383_, int tier) {
        super(p_41383_);
        this.tier = tier;
    }

    private final String[] rate = {
            "10%", "20%", "30%", "40%"
    };

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 金龙身上的一片龙鳞").withStyle(CustomStyle.styleOfSpring));
        Compute.DescriptionActive(components, Component.literal("锣鼓驱年！").withStyle(CustomStyle.styleOfSpring));
        components.add(Component.literal(" 根据精通等级的最高值，提供").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" 1.剑术精通:").withStyle(CustomStyle.styleOfPower).
                append(Compute.AttributeDescription.AttackDamage(rate[tier])));
        components.add(Component.literal(" 2.弓术精通:").withStyle(CustomStyle.styleOfPower).
                append(ComponentUtils.AttributeDescription.Swiftness("" + (tier + 1))));
        components.add(Component.literal(" 3.法术精通:").withStyle(CustomStyle.styleOfPower).
                append(ComponentUtils.AttributeDescription.ManaDamage(rate[tier])));
        components.add(Component.literal(" 效果持续60s").withStyle(CustomStyle.styleOfSpring));
        ComponentUtils.coolDownTimeDescription(components, 60);
        components.add(Component.literal("SpringFestival~2024").withStyle(ChatFormatting.ITALIC).withStyle(CustomStyle.styleOfSpring));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) Compute.use(player, this);
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }


    @Override
    public void active(Player player) {
        int tickCount = player.getServer().getTickCount();
        Utils.SpringScaleTime.put(player, tickCount + 1200);
        Utils.SpringScaleEffect.put(player, tier);
        Compute.sendEffectLastTime(player, ModItems.SpringScale0.get().getDefaultInstance(), 1200);
        Compute.playerItemCoolDown(player, ModItems.SpringScale0.get(), 60);
        Compute.playerItemCoolDown(player, ModItems.SpringScale1.get(), 60);
        Compute.playerItemCoolDown(player, ModItems.SpringScale2.get(), 60);
        Compute.playerItemCoolDown(player, ModItems.SpringScale3.get(), 60);
    }
}

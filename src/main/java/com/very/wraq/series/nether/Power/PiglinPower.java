package com.very.wraq.series.nether.Power;

import com.very.wraq.process.element.Element;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PiglinPower extends Item {

    public PiglinPower(Properties p_41383_) {
        super(p_41383_);
        Utils.PowerTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.USE(player);
        return super.use(level, player, interactionHand);
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("·[对魔]").withStyle(ChatFormatting.LIGHT_PURPLE).
                append(Component.literal("基于周围怪物数量，对周围每个怪物造成:").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamageValue("200%*怪物数量")));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        components.add(Component.literal("·[对人]").withStyle(ChatFormatting.AQUA).
                append(Component.literal("基于周围玩家数量，提升每个玩家:").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MovementSpeed("10%*玩家数量")).
                append(Component.literal("以及").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("3%")).
                append(Component.literal("额外攻击力").withStyle(ChatFormatting.YELLOW)).
                append(Component.literal("，并移除玩家的负面效果。").withStyle(ChatFormatting.AQUA)));

        Compute.CoolDownTimeDescription(components,10);
        Compute.ManaCostDescription(components,80);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("Powers-Piglin").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

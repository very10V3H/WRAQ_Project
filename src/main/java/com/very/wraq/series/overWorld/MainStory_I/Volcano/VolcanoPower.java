 package com.very.wraq.series.overWorld.MainStory_I.Volcano;

import com.very.wraq.process.element.Element;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.ToolTip.CustomStyle;
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

public class VolcanoPower extends Item {

    private final int Level;
    public VolcanoPower(Properties p_41383_, int level) {
        super(p_41383_);
        this.Level = level;
        Utils.PowerTag.put(this,1d);
        Utils.WeaponList.add(this);
    }

    public int getLevel() {
        return Level;
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·法术").withStyle(CustomStyle.styleOfMana));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfMana,ChatFormatting.WHITE);
        Compute.DescriptionActive(components,Component.literal("爆裂之焰").withStyle(CustomStyle.styleOfVolcano));
        components.add(Component.literal("-使周围所有敌人爆裂，在每个敌人位置处产生小范围").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamageValue((Effect[Level] * 100) +"%")).
                append(Component.literal("并造成2s减速效果。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" - 这个伤害会附带").withStyle(ChatFormatting.WHITE).
                append(Element.Description.FireElement("1 + 100%")));
        Compute.CoolDownTimeDescription(components,CoolDownTime[Level]);
        Compute.ManaCostDescription(components,ManaCost[Level]);

        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("Powers-Volcano").withStyle(CustomStyle.styleOfVolcano));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.USE(player);
        return super.use(level, player, interactionHand);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int[] ManaCost = {
            60,50,50,40
    };

    public static int[] CoolDownTime = {
            10,9,8,7
    };

    public static int[] Effect = {
            1,2,3,4
    };
}

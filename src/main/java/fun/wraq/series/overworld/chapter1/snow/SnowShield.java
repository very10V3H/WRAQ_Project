package fun.wraq.series.overworld.chapter1.snow;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SnowShield extends WraqOffHandItem {
    public SnowShield() {
        super(new Properties().rarity(CustomStyle.SnowItalic).stacksTo(1),
                Te.s("手盾", CustomStyle.styleOfMine));
        Utils.defence.put(this, 2d);
        Utils.maxHealth.put(this, 350d);
        Utils.attackDamage.put(this, 10d);
        Utils.critRate.put(this, 0.05);
        Utils.expUp.put(this, 0.4);
        Utils.shieldTag.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSnow;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("破碎冰玉").withStyle(getMainStyle()));
        components.add(Component.literal(" 造成暴击后，击碎目标").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.defence("25%")).
                append(Component.literal("并提升自身等额").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.defence("")).
                append(Component.literal(" 持续2s").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}

package fun.wraq.series.overworld.chapter1.mine;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MineShield extends WraqOffHandItem {

    public MineShield() {
        super(new Properties().rarity(CustomStyle.VolcanoItalic), Te.s("手盾", CustomStyle.styleOfMine));
        Utils.maxHealth.put(this, 800d);
        Utils.expUp.put(this, 0.3);
        Utils.defence.put(this, 1d);
        Utils.shieldTag.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMine;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("沉重之铁").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal("受到来自怪物的伤害时，会为你提供").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.defence("等级*0.4")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static double defenceEnhance(Player player) {
        if (Utils.shieldTag.containsKey(player.getOffhandItem().getItem())
                && Utils.swordTag.containsKey(player.getMainHandItem().getItem())) {
            return 0.25;
        }
        return 0;
    }
}

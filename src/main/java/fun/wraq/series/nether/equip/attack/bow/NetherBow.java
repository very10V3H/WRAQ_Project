package fun.wraq.series.nether.equip.attack.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NetherBow extends WraqBow {

    public NetherBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 240d);
        Utils.defencePenetration0.put(this, 9d);
        Utils.critRate.put(this, 0.25);
        Element.FireElementValue.put(this, 1d);
        Utils.levelRequire.put(this, 80);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("夸塔兹激化箭矢").withStyle(CustomStyle.styleOfQuartz));
        components.add(Component.literal(" 使用").withStyle(ChatFormatting.WHITE).
                append(Component.literal("下界能量石英").withStyle(CustomStyle.styleOfNether)).
                append(Component.literal("制作而成的长弓。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 箭矢").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("不会下坠，并造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%额外伤害。").withStyle(ChatFormatting.YELLOW)));
        components.add(Te.s(" 箭矢", CustomStyle.styleOfFlexible, "根据飞行距离，至多提供",
                "100%额外伤害（在100格达到最大值）", ChatFormatting.YELLOW));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    protected boolean hasGravity() {
        return false;
    }
}

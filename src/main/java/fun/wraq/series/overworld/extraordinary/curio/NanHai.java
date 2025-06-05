package fun.wraq.series.overworld.extraordinary.curio;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NanHai extends WraqCurios {

    public NanHai(Properties properties, boolean isAttack) {
        super(properties);
        if (isAttack) {
            Utils.defencePenetration.put(this, 0.1);
            Utils.defencePenetration0.put(this, 20d);
        } else {
            Utils.manaPenetration.put(this, 0.1);
            Utils.manaPenetration0.put(this, 20d);
        }
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Te.s("解离", style));
        components.add(Te.s(" 对", "生命值", CustomStyle.styleOfHealth, "低于",
                "40%", CustomStyle.styleOfHealth, "的怪物，无视其",
                ComponentUtils.AttributeDescription.defence(""), "与",
                ComponentUtils.AttributeDescription.manaDefence("")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.MANA_TOWER_STYLE;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfManaTower();
    }
}

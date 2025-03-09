package fun.wraq.series.overworld.chapter2.evoker;

import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EvokerSceptre extends WraqSceptre {
    public EvokerSceptre(Properties p_42964_, int tier) {
        super(p_42964_);
        Utils.manaDamage.put(this, new double[]{220, 260, 300, 360}[tier]);
        Utils.manaRecover.put(this, new double[]{13, 14, 15, 16}[tier]);
        Utils.manaPenetration0.put(this, new double[]{4, 5, 6, 7}[tier]);
        Utils.coolDownDecrease.put(this, new double[]{0.2, 0.2, 0.2, 0.2}[tier]);
        Element.LightningElementValue.put(this, new double[]{0.8, 0.9, 1, 1.2}[tier]);
        Utils.levelRequire.put(this, 56);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }
}

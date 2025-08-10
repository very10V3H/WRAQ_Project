package fun.wraq.customized.uniform.mana.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.UniformItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ManaCurios3 extends WraqManaUniformCurios {

    public ManaCurios3(Properties p_41383_) {
        super(p_41383_);
        Utils.attackSpeedEnhance.put(this, 0.2);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Te.s("魔磁轨道", hoverMainStyle()));
        components.add(Te.s(" 法球速度", CustomStyle.styleOfFlexible, "+50%", hoverMainStyle()));
        components.add(Te.s(" 法术的研究者，也是亚瑟王的挚友和导师——梅林，给予新生法师的礼物。", style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("超凡掌控").withStyle(hoverMainStyle());
    }

    public static float getManaArrowExFlySpeed(Player player) {
        if (Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(UniformItems.MANA_CURIOS_3.get())) {
            return 1.5f;
        }
        return 0;
    }
}

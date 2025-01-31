package fun.wraq.series.events.spring2025;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Spring2025Helmet extends WraqArmor implements OnHitEffectEquip {

    public Spring2025Helmet(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public Style getMainStyle() {
        return null;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("烟花", getMainStyle()));
        components.add(Te.s(" 每过5s", CustomStyle.styleOfStone,
                "你的下一次命中目标的", "普攻", CustomStyle.styleOfStone));
        components.add(Te.s(" 将会在", "目标位置", ChatFormatting.RED,
                "释放", "一枚烟花", CustomStyle.styleOfPower));
        components.add(Te.s(" 造成", "减速", CustomStyle.styleOfStone,
                "并提高目标", "15%承受伤害", CustomStyle.styleOfRed, "，持续5s"));
        ComponentUtils.descriptionPassive(components, Te.s("凝视", getMainStyle()));

        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSpring2025();
    }

    @Override
    public void onHit(Player player, Mob mob) {

    }
}

package fun.wraq.series.instance.series.mushroom.gem;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.damage.SputteringDamage;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemOnNormalAttackHit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MushroomSputterGem extends WraqPassiveGem implements GemOnNormalAttackHit {

    public MushroomSputterGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle, Component oneLineDescription, Component suffix) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("孢子扩散", CustomStyle.MUSHROOM_STYLE));
        components.add(Te.s(" 普通攻击", CustomStyle.styleOfStone, "对目标造成伤害时"));
        components.add(Te.s(" 会向周围目标", "扩散3次", hoverStyle, "真实伤害", CustomStyle.styleOfSea));
        components.add(Te.s(" 每次的扩散会使伤害降低50%", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public void onHit(Player player, Mob mob, double damage) {
        SputteringDamage.addSputteringDamageOnMob(mob, player, damage, 2, 3);
    }
}

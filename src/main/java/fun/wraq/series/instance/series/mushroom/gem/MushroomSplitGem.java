package fun.wraq.series.instance.series.mushroom.gem;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemOnCauseDamage;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class MushroomSplitGem extends WraqPassiveGem implements GemOnCauseDamage {

    public MushroomSplitGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle, Component oneLineDescription, Component suffix) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s(" ", hoverStyle));
        components.add(Te.s(" 造成的", "任意伤害", hoverStyle, "将额外造成自身",
                ComponentUtils.AttributeDescription.maxHealth(""), "的", "真实伤害", CustomStyle.styleOfSea));
        return components;
    }

    @Override
    public void onCauseDamage(Player player, Mob mob, double damage) {
        Damage.causeTrueDamageToMonster(player, mob, player.getMaxHealth());
    }
}

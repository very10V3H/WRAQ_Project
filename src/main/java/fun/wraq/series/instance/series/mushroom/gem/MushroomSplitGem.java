package fun.wraq.series.instance.series.mushroom.gem;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemOnCauseDamage;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MushroomSplitGem extends WraqPassiveGem implements GemOnCauseDamage, Decomposable {

    private final boolean isEnhanced;
    public MushroomSplitGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle,
                            Component oneLineDescription, Component suffix, boolean isEnhanced) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
        this.isEnhanced = isEnhanced;
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("分生", hoverStyle));
        components.add(Te.s(" 造成的", "任意伤害", hoverStyle, "将额外造成自身",
                ComponentUtils.AttributeDescription.maxHealth(isEnhanced ? "150%" : "100%"),
                "的", "真实伤害", CustomStyle.styleOfSea));
        return components;
    }

    public static Set<Mob> causingDamageMobs = new HashSet<>();

    @Override
    public void onCauseDamage(Player player, Mob mob, double damage) {
        if (!causingDamageMobs.contains(mob)) {
            causingDamageMobs.add(mob);
            Damage.causeTrueDamageToMonster(player, mob, player.getMaxHealth() * (isEnhanced ? 1.5 : 1));
            causingDamageMobs.remove(mob);
        }
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(MushroomItems.MUSHROOM_GEM_PIECE.get(), 6);
    }
}

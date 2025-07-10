package fun.wraq.series.instance.series.mushroom.gem;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.damage.SputteringDamage;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.summer2025.Summer2025;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemOnNormalAttackHit;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MushroomSputterGem extends WraqPassiveGem implements GemOnNormalAttackHit, Decomposable {

    private final boolean isEnhanced;
    public MushroomSputterGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle,
                              Component oneLineDescription, Component suffix, boolean isEnhanced) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
        this.isEnhanced = isEnhanced;
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("孢子扩散", CustomStyle.MUSHROOM_STYLE));
        components.add(Te.s(" 普通攻击", CustomStyle.styleOfStone, "对目标造成伤害时"));
        components.add(Te.s(" 会向周围目标", "连锁扩散", (isEnhanced ? 4 : 3) + "次", hoverStyle,
                "真实伤害", CustomStyle.styleOfSea));
        components.add(Te.s(" 真实伤害", CustomStyle.styleOfSea, "等同于对目标造成的伤害"));
        components.add(Te.s(" 每次的连锁扩散会使伤害降低50%", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s(" 每次的连锁扩散将会选定当前连锁目标半径9格内的所有目标", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public void onHit(Player player, Mob mob, double damage) {
        if (Summer2025.canBeSpread(mob)) {
            SputteringDamage.addSputteringDamageOnMob(mob, player, damage, 2,
                    isEnhanced ? 4 : 3, CustomStyle.MUSHROOM_STYLE);
        }
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(MushroomItems.MUSHROOM_GEM_PIECE.get(), 6);
    }
}

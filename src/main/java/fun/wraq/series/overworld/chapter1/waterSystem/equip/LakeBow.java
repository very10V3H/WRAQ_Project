package fun.wraq.series.overworld.chapter1.waterSystem.equip;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class LakeBow extends WraqBow implements OnHitEffectEquip {
    private final int tier;

    public LakeBow(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{50, 60, 70, 80}[tier]);
        Utils.defencePenetration0.put(this, new double[]{2, 2, 3, 3}[tier]);
        Utils.critRate.put(this, new double[]{0.4, 0.4, 0.4, 0.4}[tier]);
        Element.waterElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[tier]);
    }

    @Override
    public void onHit(Player player, Mob mob) {
        mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 12, 12, 12))
                .stream()
                .filter(mob1 -> mob1.distanceTo(mob) <= 6 && !mob1.equals(mob))
                .forEach(mob1 -> {
                    Damage.causeRateAdDamageToMonsterWithCritJudge(player, mob1, 0.25 * (1 + tier));
                    Compute.addSlowDownEffect(mob1, 40, 2);
                });
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWater;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("迟滞之矢").withStyle(style));
        components.add(Component.literal(" 箭矢").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("命中目标后，对目标周围单位造成").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.attackDamageValue(25 * (1 + tier) + "%")));
        components.add(Component.literal(" 并施加2s").withStyle(ChatFormatting.WHITE).
                append(Component.literal("减速效果").withStyle(CustomStyle.styleOfMine)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }
}

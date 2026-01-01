package fun.wraq.series.instance.series.mushroom.gem;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemOnKillMob;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MushroomSputterGem extends WraqPassiveGem implements Decomposable, GemOnKillMob {

    private final boolean isEnhanced;
    public MushroomSputterGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle,
                              Component oneLineDescription, Component suffix, boolean isEnhanced) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
        this.isEnhanced = isEnhanced;
    }

    private int getRadius() {
        return isEnhanced ? 12 : 8;
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("孢子扩散", CustomStyle.MUSHROOM_STYLE));
        components.add(Te.s(" 击杀怪物时，会向其周围怪物散布",
                "其孢子数 + 1的孢子", CustomStyle.MUSHROOM_STYLE));
        components.add(Te.s(" 散布的半径为" + getRadius() + "格", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s(" 并对其周围怪物造成相当于其",
                (isEnhanced ? "12%" : "8%") + " * (孢子数 + 1)", CustomStyle.MUSHROOM_STYLE, "最大生命值的", "真实伤害"));
        return components;
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(MushroomItems.MUSHROOM_GEM_PIECE.get(), 6);
    }

    public static Map<Mob, Integer> mobCount = new WeakHashMap<>();

    @Override
    public void onKill(Player player, Mob mob) {
        int count = mobCount.getOrDefault(mob, 0);
        Compute.getNearMob(mob, getRadius()).forEach(nearMob -> {
            if (nearMob != mob) {
                Damage.causeTrueDamageToMonster(player, nearMob,
                        mob.getMaxHealth() * (isEnhanced ? 0.12 : 0.08) * (count + 1));
                ParticleProvider.createLineEffectParticle(mob.level(), (int) nearMob.distanceTo(mob) * 5,
                        mob.getEyePosition(), nearMob.getEyePosition(), hoverStyle);
                Compute.sendMobEffectHudToNearPlayer(nearMob, "item/mushroom_gem", "MushroomSputterGem",
                        8888, count + 1, true);
                mobCount.compute(nearMob, (k, v) -> v == null ? 1 : v + 1);
            }
        });
    }
}

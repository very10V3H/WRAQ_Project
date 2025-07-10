package fun.wraq.series.instance.series.warden.gem;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.summer2025.Summer2025;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemOnCauseDamage;
import fun.wraq.series.gems.passive.impl.GemTickHandler;
import fun.wraq.series.instance.series.warden.WardenItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class AncientDarknessGem extends WraqPassiveGem implements GemTickHandler, GemOnCauseDamage, Decomposable {

    private final int tier;
    public AncientDarknessGem(Properties properties, List<AttributeMapValue> attributeMapValues,
                              Style hoverStyle, Component oneLineDescription, Component suffix, int tier) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
        this.tier = tier;
    }

    private final double[] rate = new double[]{0.06, 0.07, 0.08, 0.09};
    private final int[] integerRate = new int[]{6, 7, 8, 9};

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfWarden;
        ComponentUtils.solePassiveDescription(components, Te.s("收集", style));
        components.add(Te.s(" 你将", "斩杀", style, "拥有少于",
                ComponentUtils.AttributeDescription.health(integerRate[tier] + "%"), "或", "少于你",
                ComponentUtils.AttributeDescription.health("")));
        components.add(Te.s(" 的", "周围", integerRate[tier] + "格", style,
                "内的敌人或攻击目标", ChatFormatting.AQUA));
        return components;
    }

    public static Map<Mob, Boolean> causedMobMap = new WeakHashMap<>();

    @Override
    public void tick(Player player) {
        Compute.getNearEntity(player, Mob.class, integerRate[tier])
                .stream()
                .map(mob -> (Mob) mob)
                .filter(mob -> !MobSpawn.getMobOriginName(mob).equals(Summer2025.mobName))
                .forEach(mob -> {
                    passive(player, mob);
        });
    }

    @Override
    public void onCauseDamage(Player player, Mob mob, double damage) {
        passive(player, mob);
    }

    private void passive(Player player, Mob mob) {
        if (!causedMobMap.containsKey(mob)
                && (mob.getHealth() / mob.getMaxHealth() < rate[tier] || mob.getHealth() < player.getHealth())) {
            causedMobMap.put(mob, true);
            Damage.causeDirectDamageToMob(player, mob, mob.getHealth() * Math.pow(10, 4));
            ParticleProvider.createLineEffectParticle(player.level(), (int) mob.distanceTo(player) * 5,
                    player.getEyePosition(),mob.getEyePosition(), CustomStyle.styleOfWarden);
        }
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(WardenItems.WARDEN_SOUL_INGOT.get(), 10);
    }
}

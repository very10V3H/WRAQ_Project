package fun.wraq.series.instance.series.warden.gem;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.passive.WraqPassiveGem;
import fun.wraq.series.gems.passive.impl.GemOnCauseDamage;
import fun.wraq.series.gems.passive.impl.GemTickHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class AncientDarknessGem extends WraqPassiveGem implements GemTickHandler, GemOnCauseDamage {

    public AncientDarknessGem(Properties properties, List<AttributeMapValue> attributeMapValues, Style hoverStyle, Component oneLineDescription, Component suffix) {
        super(properties, attributeMapValues, hoverStyle, oneLineDescription, suffix);
    }

    @Override
    public List<Component> getAdditionDescription() {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfWarden;
        ComponentUtils.solePassiveDescription(components, Te.s("收集", style));
        components.add(Te.s(" 你将", "斩杀", style, "拥有少于",
                ComponentUtils.AttributeDescription.health("8%"), "或", "少于你",
                ComponentUtils.AttributeDescription.maxHealth("")));
        components.add(Te.s(" 的", "周围敌人或攻击目标", ChatFormatting.AQUA));
        return components;
    }

    public static Map<Mob, Boolean> causedMobMap = new WeakHashMap<>();

    @Override
    public void tick(Player player) {
        Compute.getNearEntity(player, Mob.class, 8)
                .stream()
                .map(mob -> (Mob) mob)
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
                && (mob.getHealth() / mob.getMaxHealth() < 0.08 || mob.getHealth() < player.getHealth())) {
            causedMobMap.put(mob, true);
            Damage.causeDirectDamageToMob(player, mob, mob.getHealth() * Math.pow(10, 4));
            ParticleProvider.createLineEffectParticle(player.level(), (int) mob.distanceTo(player) * 5,
                    player.getEyePosition(),mob.getEyePosition(), CustomStyle.styleOfWarden);
        }
    }
}

package fun.wraq.series.instance.series.warden;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.damage.OnCauseFinalDamageEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WardenOffhandItem extends WraqOffHandItem implements OnCauseFinalDamageEquip {

    public WardenOffhandItem(Properties properties, Component type) {
        super(properties, type);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWarden;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("远古帘幕", getMainStyle()));
        components.add(Te.s(" 每过3s，切换一次", "明暗效果", getMainStyle()));
        components.add(Te.s(" 明:", CustomStyle.styleOfSunIsland, "提供持续3s的"));
        components.add(Te.s(" 1.", ComponentUtils.AttributeDescription.movementSpeed("40%")));
        components.add(Te.s(" 2.", ComponentUtils.AttributeDescription.healAmplification("40%")));
        components.add(Te.s(" 3.", ComponentUtils.AttributeDescription.manaRecover("100")));
        components.add(Te.s(" 暗:", getMainStyle(), "在3s内对怪物造成的", "33%伤害", ChatFormatting.RED, "将被存储"));
        components.add(Te.s(" 在下次", "切换明暗", getMainStyle(), "时，会将存储的伤害再次施加至怪物"));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfAncient();
    }

    public static Map<Player, Map<Mob, Double>> targetIntervalDamageMap = new HashMap<>();

    @Override
    public void onCauseFinalDamage(Player player, Mob mob, double damage) {
        if (player.tickCount % 120 < 60) {
            if (!targetIntervalDamageMap.containsKey(player)) {
                targetIntervalDamageMap.put(player, new HashMap<>());
            }
            Map<Mob, Double> map = targetIntervalDamageMap.get(player);
            double storedDamage = damage * 0.33;
            map.compute(mob, (k, v) -> v == null ? storedDamage : v + storedDamage);;
        }
    }

    @Override
    public void tick(Player player) {
        if (player.tickCount % 120 == 60) {
            if (!targetIntervalDamageMap.containsKey(player)) {
                targetIntervalDamageMap.put(player, new HashMap<>());
            }
            Map<Mob, Double> map = targetIntervalDamageMap.get(player);
            map.forEach((mob, damage) -> {
                Damage.causeDirectDamageToMob(player, mob, damage);
                Compute.summonValueItemEntity(mob.level(), player, mob,
                        Te.s(String.format("%.0f", damage), CustomStyle.styleOfSea), 2);
            });
            map.clear();

            StableAttributesModifier.addM(player, StableAttributesModifier.playerMovementSpeedModifier,
                    "WardenOffHandMovementSpeed", 0.4, Tick.get() + 60);
            StableAttributesModifier.addM(player, StableAttributesModifier.playerHealAmplifierModifier,
                    "WardenOffHandHealAmplifier", 0.4, Tick.get() + 60);
            StableAttributesModifier.addM(player, StableAttributesModifier.playerManaRecoverModifier,
                    "WardenOffHandManaRecover", 100, Tick.get() + 60);
            Compute.sendEffectLastTime(player, this, 60);
        }
        super.tick(player);
    }
}

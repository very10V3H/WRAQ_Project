package fun.wraq.series.instance.series.warden;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.damage.OnCauseFinalDamageEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.ChangedAttributesModifier;
import fun.wraq.process.func.EnhanceNormalAttack;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WardenOffhandItem extends WraqOffHandItem implements OnCauseFinalDamageEquip {

    private final boolean isMoon;

    public WardenOffhandItem(Properties properties, Component type, boolean isMoon) {
        super(properties, type);
        this.isMoon = isMoon;
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
        if (isMoon) {
            ComponentUtils.descriptionPassive(components, Te.s("天明隐月", getMainStyle()));
            components.add(Te.s(" 每次", "远古幕帘", getMainStyle(), "切换时，", "强化下次普攻", ChatFormatting.AQUA));
            components.add(Te.s(" 使下次普通命中时，吸收目标周围所有敌人",
                    ComponentUtils.AttributeDescription.attackDamage("")));
            if (type.getString().equals(WraqOffHandItem.KNIFE) || type.getString().equals(WraqOffHandItem.SHIELD)) {
                components.add(Te.s(" 1.", ChatFormatting.AQUA, "提供持续3s且持续衰减等同于吸收量的",
                        ComponentUtils.AttributeDescription.attackDamage("50%")));
            } else {
                components.add(Te.s(" 1.", ChatFormatting.AQUA, "提供持续3s且持续衰减等同于吸收量的",
                        ComponentUtils.AttributeDescription.manaDamage("100%")));
            }
            components.add(Te.s(" 2.", ChatFormatting.AQUA, " 提供持续3s的", "等额护盾", CustomStyle.styleOfStone));
        }
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
            map.compute(mob, (k, v) -> v == null ? storedDamage : v + storedDamage);
            ;
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
            Compute.sendEffectLastTime(player, WardenItems.WARDEN_HEART.get(), 60);
        }
        if (isMoon && player.tickCount % 60 == 0) {
            boolean isAttackDamage = true;
            int enhanceNormalAttackType = 0;
            Item icon = ModItems.MOON_SWORD.get();
            if (type.getString().equals(WraqOffHandItem.SHIELD)) {
                icon = ModItems.MOON_SWORD.get();
            } else if (type.getString().equals(WraqOffHandItem.KNIFE)) {
                enhanceNormalAttackType = 1;
                icon = ModItems.MOON_BOW.get();
            } else if (type.getString().equals(WraqOffHandItem.BOOK)) {
                isAttackDamage = false;
                enhanceNormalAttackType = 2;
                icon = ModItems.MOON_SCEPTRE.get();
            }
            Item finalIcon = icon;
            boolean finalIsAttackDamage = isAttackDamage;
            EnhanceNormalAttackModifier.addModifier(player, new EnhanceNormalAttackModifier("darkMoonOffHandPassive",
                    enhanceNormalAttackType, new EnhanceNormalAttack() {
                @Override
                public void hit(Player player, Mob mob) {
                    Compute.sendEffectLastTime(player, finalIcon, Tick.s(3));
                    double attackDamageSum = mob.level().getEntitiesOfClass(Mob.class,
                                    AABB.ofSize(mob.position(), 15, 15, 15))
                            .stream().filter(mob1 -> mob1.distanceTo(mob) <= 6)
                            .mapToDouble(mob1 -> MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob1,
                                    MobSpawn.MobBaseAttributes.attackDamage))
                            .sum();
                    ChangedAttributesModifier.addAttributeModifier(player,
                            finalIsAttackDamage ?
                                    ChangedAttributesModifier.exAttackDamage : ChangedAttributesModifier.exManaDamage,
                            "darkMoonOffHandPassive", attackDamageSum * (finalIsAttackDamage ? 0.5 : 1),
                            Tick.s(3), true);
                    Shield.providePlayerShield(player, Tick.s(3), attackDamageSum);
                }
            }));
            Compute.sendEffectLastTime(player, finalIcon, 0, true);
        }
        super.tick(player);
    }
}

package fun.wraq.series.overworld.chapter2.lightningIsland.Armor;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectArmor;
import fun.wraq.common.impl.tick.TickArmor;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.*;

public class LightningArmor extends WraqArmor implements TickArmor, OnHitEffectArmor {

    public LightningArmor(ItemMaterial material, Type type, Properties properties, double maxHealth, double defence) {
        super(material, type, properties);
        Utils.defence.put(this, defence);
        Utils.maxHealth.put(this, maxHealth);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.35);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLightingIsland;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("唤雷").withStyle(style));
        components.add(Component.literal(" 每过4s，强化下一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通近战攻击").withStyle(CustomStyle.styleOfPower)).
                append(Te.m("或")).
                append(Te.m("普通箭矢攻击", CustomStyle.styleOfFlexible)));
        components.add(Component.literal(" 向攻击首个目标及其附近的单位降下多道").withStyle(ChatFormatting.WHITE).
                append(Component.literal("落雷").withStyle(style)));
        components.add(Component.literal(" 每道落雷").withStyle(style).
                append(Component.literal("对范围内单位造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%自适应伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Te.m(" 多件唤雷装备能够线性提升这个伤害数值", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static Map<Player, Integer> enhanceNormalAttackTickMap = new WeakHashMap<>();
    @Override
    public void tick(Player player) {
        if (enhanceNormalAttackTickMap.getOrDefault(player, 0) == Tick.get()) {
            EnhanceNormalAttackModifier.addModifier(player,
                    new EnhanceNormalAttackModifier("LightningArmor passive", true, 0, 0, (player1, mob) -> {
                        Compute.getNearEntity(mob, Mob.class, 8).
                                forEach(soleMob -> {
                                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, soleMob.level());
                                    lightningBolt.setVisualOnly(true);
                                    lightningBolt.moveTo(soleMob.position());
                                    soleMob.level().addFreshEntity(lightningBolt);

                                    double rate = 0.25 * SuitCount.getLightningArmorCount(player1);
                                    Random random = new Random();
                                    if (random.nextDouble() < PlayerAttributes.critRate(player1))
                                        rate *= (1 + PlayerAttributes.critDamage(player1));
                                    double finalRate = rate;
                                    soleMob.level()
                                            .getEntitiesOfClass(Mob.class, AABB.ofSize(soleMob.position(), 4, 4, 4))
                                            .stream()
                                            .filter(mob1 -> mob1.distanceTo(soleMob) < 2)
                                            .forEach(target -> {
                                                Damage.causeAutoAdaptionRateDamageToMob(player1, target, finalRate);
                                            });
                                });
                        Compute.removeEffectLastTime(player, ModItems.LIGHTNING_CHEST.get());
                        Compute.coolDownTimeSend(player, ModItems.LIGHTNING_CHEST.get(), 80);
                    }));
            Compute.sendEffectLastTime(player, ModItems.LIGHTNING_CHEST.get(), 0, true);
        }
    }

    @Override
    public void onHit(Player player, Mob mob) {
        if (enhanceNormalAttackTickMap.getOrDefault(player, 0) < Tick.get()) {
            enhanceNormalAttackTickMap.put(player, Tick.get() + 80);
        }
    }
}
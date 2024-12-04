package fun.wraq.series.newrunes.chapter2;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.chapter2.LightningZombieController;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.newrunes.RuneItem;
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

public class LightningNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public LightningNewRune(Properties properties) {
        super(properties);
        Utils.defencePenetration.put(this, 0.08);
        Utils.manaPenetration.put(this, 0.08);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("唤雷").withStyle(style));
        components.add(Component.literal(" 每过8s，强化下一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("攻击").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal(" 向攻击首个目标及其附近的单位降下多道").withStyle(ChatFormatting.WHITE).
                append(Component.literal("落雷").withStyle(style)));
        components.add(Component.literal(" 每道落雷").withStyle(style).
                append(Component.literal("对范围内单位造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%自适应伤害").withStyle(CustomStyle.styleOfSea)));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfLightning;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(LightningZombieController.mobName).withStyle(CustomStyle.styleOfLightning)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    public static Map<String, Integer> cooldownMap = new HashMap<>();

    public static boolean isOn(Player player) {
        return WraqCurios.isOn(LightningNewRune.class, player);
    }

    @Override
    public void tick(Player player) {
        if (!isOn(player)) {
            Compute.removeEffectLastTime(player, NewRuneItems.lightningNewRune.get());
            return;
        }
        int tick = Tick.get();
        String name = player.getName().getString();
        if (!cooldownMap.containsKey(name) || cooldownMap.get(name) == tick) {
            cooldownMap.put(name, tick);
            Compute.sendEffectLastTime(player, NewRuneItems.lightningNewRune.get(), 0, true);
        }
    }

    public static void onHit(Player player, Mob mob) {
        if (!isOn(player)) return;
        int tick = Tick.get();
        String name = player.getName().getString();
        Random random = new Random();
        if (!cooldownMap.containsKey(name) || cooldownMap.get(name) < tick) {
            cooldownMap.put(name, tick + 160);
            Compute.sendCoolDownTime(player, NewRuneItems.lightningNewRune.get(), 160);
            List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 16, 16, 16));
            mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 8);
            mobList.forEach(soleMob -> {
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, soleMob.level());
                lightningBolt.setVisualOnly(true);
                lightningBolt.moveTo(soleMob.position());
                soleMob.level().addFreshEntity(lightningBolt);

                List<Mob> nearMobList = soleMob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(soleMob.position(), 4, 4, 4));
                nearMobList.removeIf(mob1 -> mob1.distanceTo(soleMob) > 2);
                double rate = 0.5;
                if (random.nextDouble() < PlayerAttributes.critRate(player))
                    rate *= (1 + PlayerAttributes.critDamage(player));

                double finalRate = rate;
                nearMobList.forEach(target -> {
                    Damage.causeAutoAdaptionRateDamageToMob(player, target, finalRate, false);
                });
            });
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}

package fun.wraq.series.instance.series.moon.Equip;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.damage.OnCauseFinalDamageCurios;
import fun.wraq.common.impl.damage.OnWithStandDamageCurios;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MoonBelt extends WraqCurios implements OnCauseFinalDamageCurios, OnWithStandDamageCurios {

    public MoonBelt(Properties p_41383_) {
        super(p_41383_);
        Utils.attackDamage.put(this, 150d);
        Utils.manaDamage.put(this, 300d);
        Utils.defencePenetration0.put(this, 2d);
        Utils.manaPenetration0.put(this, 2d);
        Utils.coolDownDecrease.put(this, 0.2);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("苍白之瀑").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 当你对一名敌人造成伤害或受到伤害，将会激活").withStyle(ChatFormatting.WHITE).
                append(Component.literal(" 尘月玉缠 ").withStyle(hoverMainStyle())).
                append(Component.literal("在接下来的10s，将你造成伤害的10%和受到伤害的1000%转化为").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("苍月能量").withStyle(CustomStyle.styleOfMoon1)).
                append(Component.literal("，并存储于尘月玉缠之中。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 10s后，对周围所有敌人造成等同于存储的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("苍月能量").withStyle(CustomStyle.styleOfMoon1)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)).
                append(Component.literal("并为你提供等同于").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("苍月能量1%").withStyle(CustomStyle.styleOfMoon1)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾值").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal(" -护盾值不会超过最大生命值的200%").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        ComponentUtils.coolDownTimeDescription(components, 10);
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMoon;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfMoon();
    }

    public static WeakHashMap<Player, Integer> coolDown = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> damageTick = new WeakHashMap<>();
    public static WeakHashMap<Player, Double> storedDamage = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> statusType = new WeakHashMap<>(); // 0 - 等待状态 1 - 蓄能状态

    @Override
    public void tick(Player player) {
        int tick = Tick.get();
        if (statusType.containsKey(player) && statusType.get(player) == 1
                && damageTick.containsKey(player) && damageTick.get(player) < tick
                && storedDamage.containsKey(player)) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 15, 15, 15));
            mobList.forEach(mob -> {
                if (mob.distanceTo(player) < 6) {
                    Damage.causeTrueDamageToMonster(player, mob, storedDamage.get(player));
                }
            });
            Shield.providePlayerShield(player, 200, Math.min(player.getMaxHealth() * 2, storedDamage.get(player) * 0.01));
            statusType.put(player, 0);
            storedDamage.put(player, 0d);
            coolDown.put(player, tick + 200);
            Compute.sendCoolDownTime(player, ModItems.MoonBelt.get().getDefaultInstance(), 200);
            ParticleProvider.DisperseParticle(player.position(), (ServerLevel) player.level(), 1, 1, 120, ParticleTypes.FIREWORK, 1);
            ParticleProvider.DisperseParticle(player.position(), (ServerLevel) player.level(), 1.5, 1, 120, ParticleTypes.FIREWORK, 1);
        }
    }

    @Override
    public void onCauseFinalDamage(Player player, Mob mob, double damage) {
        int tick = Tick.get();
        if (!coolDown.containsKey(player) || coolDown.get(player) < tick) {
            if (!statusType.containsKey(player)) statusType.put(player, 0);
            switch (statusType.get(player)) {
                case 0 -> {
                    statusType.put(player, 1);
                    storedDamage.put(player, damage * 0.1);
                    damageTick.put(player, tick + 200);
                    Compute.sendEffectLastTime(player, ModItems.MoonBelt.get().getDefaultInstance(), 200);
                }
                case 1 -> {
                    storedDamage.put(player, storedDamage.get(player) + damage * 0.1);
                }
            }
        }
    }

    @Override
    public void onWithStandDamage(Player player, Mob mob, double damage) {
        int tick = Tick.get();
        if (!coolDown.containsKey(player) || coolDown.get(player) < tick) {
            if (!statusType.containsKey(player)) statusType.put(player, 0);
            switch (statusType.get(player)) {
                case 0 -> {
                    statusType.put(player, 1);
                    storedDamage.put(player, damage * 10);
                    damageTick.put(player, tick + 200);
                    Compute.sendEffectLastTime(player, ModItems.MoonBelt.get().getDefaultInstance(), 200);
                }
                case 1 -> {
                    storedDamage.put(player, storedDamage.get(player) + damage * 10);
                }
            }
        }
    }
}

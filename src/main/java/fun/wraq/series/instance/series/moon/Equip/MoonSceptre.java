package fun.wraq.series.instance.series.moon.Equip;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.ChangedAttributesModifier;
import fun.wraq.process.func.EnhanceNormalAttack;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class MoonSceptre extends WraqSceptre implements ActiveItem, OnHitEffectEquip {

    private final double activeRate;
    public MoonSceptre(Properties properties, double activeRate) {
        super(properties);
        Utils.manaDamage.put(this, 2400d);
        Utils.manaRecover.put(this, 30d);
        Utils.coolDownDecrease.put(this, 0.5);
        Utils.manaPenetration0.put(this, 29d);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Utils.manaCost.put(this, 45d);
        this.activeRate = activeRate;
    }

    @Override
    protected AbstractArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player, level,
                PlayerAttributes.manaDamage(player) * rate, PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sea);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 3, 1);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        WraqSceptre.adjustOrb(newArrow, player);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.FIREWORK);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.FIREWORK);
        return newArrow;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon1;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("行星之引").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("会将目标周围半径6内的其他敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("小幅牵引").withStyle(style)).
                append(Component.literal("至目标位置").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionActive(components, Component.literal("噬星之月 ").withStyle(style));
        components.add(Component.literal(" 你的下一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("吸收").withStyle(style)).
                append(Component.literal("目标周围半径6内所有单位的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")));
        components.add(Component.literal("，提供在10s内持续衰减的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExManaDamage(String.format("%.0f%%", activeRate * 100))));
        components.add(Component.literal(" 并为你提供持续20s的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("100%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾").withStyle(ChatFormatting.GRAY)));
        ComponentUtils.coolDownTimeDescription(components, 27);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoon();
    }

    @Override
    public void active(Player player) {
        Compute.playerItemCoolDown(player, ModItems.MoonSceptre.get(), 27);
        EnhanceNormalAttackModifier.addModifier(player, new EnhanceNormalAttackModifier("moonSceptreActive", 2, new EnhanceNormalAttack() {
            @Override
            public void hit(Player player, Mob mob) {
                Shield.providePlayerShield(player, 400, PlayerAttributes.manaDamage(player));
                Compute.sendEffectLastTime(player, ModItems.MoonSceptre.get().getDefaultInstance(), 200);
                List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 15, 15, 15));
                mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6);
                double attackDamage = 0;
                for (Mob mob1 : mobList) {
                    attackDamage += MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob1, MobSpawn.MobBaseAttributes.attackDamage);
                }
                ChangedAttributesModifier.addAttributeModifier(player, ChangedAttributesModifier.exManaDamage,
                        "moonSceptreActive", attackDamage * activeRate, 200, true);
            }
        }));
        Compute.sendEffectLastTime(player, ModItems.MoonSceptre.get().getDefaultInstance(), 8888, 0, true);
    }

    @Override
    public void onHit(Player player, Mob mob) {
        mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 15, 15, 15))
                .stream().filter(mob1 -> mob1.distanceTo(mob) <= 6 && !mob1.equals(mob))
                .forEach(mob1 -> Compute.MonsterGatherProvider(mob1, 2, mob.position()));
    }
}

package fun.wraq.series.instance.series.moon.Equip;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.MyArrow;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.common.impl.onhit.OnHitEffectMainHandWeapon;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MoonBow extends WraqBow implements OnHitEffectMainHandWeapon {

    private final int exTargetCount;
    public MoonBow(Properties properties, int exTargetCount) {
        super(properties);
        Utils.attackDamage.put(this, 1200d);
        Utils.defencePenetration0.put(this, 29d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.35);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        this.exTargetCount = exTargetCount;
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
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("会将目标周围半径6内的其他敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("小幅牵引").withStyle(style)).
                append(Component.literal("至目标位置").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components, Component.literal("尘月之风").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("将额外释放").withStyle(ChatFormatting.WHITE)).
                append(Component.literal(exTargetCount + "支箭矢").withStyle(CustomStyle.styleOfFlexible)));
        components.add(Component.literal(" 额外箭矢").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("25%伤害").withStyle(CustomStyle.styleOfMoon)));
        components.add(Component.literal(" 额外箭矢").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("会自动锁定自身半径30格内的目标").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoon();
    }

    private void shootExArrow(Player player) {
        player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 80, 80, 80))
                .stream().filter(mob -> mob.distanceTo(player) <= 30 && !(mob instanceof Civil))
                .sorted(new Comparator<Mob>() {
                    @Override
                    public int compare(Mob o1, Mob o2) {
                        if (o1.distanceTo(player) - o2.distanceTo(player) < 0) return -1;
                        else if (o1.distanceTo(player) - o2.distanceTo(player) == 0) return 0;
                        else return 1;
                    }
                })
                .limit(exTargetCount)
                .forEach(mob -> {
                    MyArrow myArrow = new MyArrow(EntityType.ARROW, player.level(), player, true, 0.25);
                    myArrow.setDeltaMovement(mob.position().add(0, 1, 0).subtract(player.position().add(0, 1.5, 0)).normalize().scale(4.5));
                    myArrow.moveTo(player.pick(0.5, 0, false).getLocation());
                    myArrow.setCritArrow(true);
                    myArrow.setNoGravity(true);
                    ProjectileUtil.rotateTowardsMovement(myArrow, 1);
                    player.level().addFreshEntity(myArrow);
                    ParticleProvider.LineParticle(player.level(), (int) mob.distanceTo(player),
                            player.pick(0.5, 0, false).getLocation(),
                            mob.position().add(0, 1, 0), ParticleTypes.FIREWORK);
                });
    }

    @Override
    protected MyArrow summonArrow(ServerPlayer serverPlayer, double rate) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true, rate);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
        shootExArrow(serverPlayer);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        MySound.soundToNearPlayer(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.FIREWORK);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.FIREWORK);
        return arrow;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 15, 15, 15))
                .stream().filter(mob1 -> mob1.distanceTo(mob) <= 6 && !mob1.equals(mob))
                .forEach(mob1 -> Compute.MonsterGatherProvider(mob1, 2, mob.position()));
    }
}
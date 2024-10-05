package fun.wraq.process.system.element.equipAndCurios.fireElement;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class FireElementSceptre extends WraqSceptre implements ActiveItem {

    public FireElementSceptre(Properties p_42964_) {
        super(p_42964_);
        Utils.manaDamage.put(this, 1774d);
        Utils.manaRecover.put(this, 30d);
        Utils.manaPenetration0.put(this, 40d);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Utils.manaCost.put(this, 45d);
        Utils.coolDownDecrease.put(this, 0.2);
        Element.FireElementValue.put(this, 2d);
    }

    public static void IgniteEffect(Player player, Mob mob) {
        if (mob.getRemainingFireTicks() > 0 && player.getMainHandItem().is(ModItems.FireElementSceptre.get())) {
            fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword.playerFireElementValueEnhanceTickMap.put(player, player.getServer().getTickCount() + 40);
        }
    }

    public static void Tick(Player player) {
        if (!player.getMainHandItem().is(ModItems.FireElementSceptre.get())) return;
        if (!fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword.playerIgniteMobMap.containsKey(player))
            fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword.IgniteMob> list = fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < player.getServer().getTickCount());
        if (list.size() > 0)
            Compute.sendEffectLastTime(player, ModItems.FireElementSceptre.get().getDefaultInstance(), 8888, Math.min(3, list.size()), true);
        else
            Compute.sendEffectLastTime(player, ModItems.FireElementSceptre.get().getDefaultInstance(), 0, Math.min(3, list.size()), true);
    }

    public static void PlayerIgniteMobEffect(Player player, Mob mob) {
        if (!player.getMainHandItem().is(ModItems.FireElementSceptre.get())) return;
        if (!fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword.playerIgniteMobMap.containsKey(player))
            fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword.playerIgniteMobMap.put(player, new ArrayList<>());
        List<fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword.IgniteMob> list = fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword.playerIgniteMobMap.get(player);
        list.removeIf(igniteMob -> igniteMob.tick() < player.getServer().getTickCount());
        if (mob.getRemainingFireTicks() == 0) {
            list.add(new fun.wraq.process.system.element.equipAndCurios.fireElement.FireElementSword.IgniteMob(mob.getId(), player.getServer().getTickCount() + 60));
        }
    }

    @Override
    protected AbstractArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_MAGMA.get(), player, level,
                PlayerAttributes.manaDamage(player) * rate,
                PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.FireElement1TickParticle);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 3, 1);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        WraqSceptre.adjustOrb(newArrow, player);
        level.addFreshEntity(newArrow);
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.FireElementParticle.get());
        ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ModParticles.FireElementParticle.get());
        return newArrow;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfFire;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionActive(components, Component.literal("引燃").withStyle(style));
        components.add(Component.literal(" 释放一道").withStyle(ChatFormatting.WHITE).
                append(Component.literal("激光").withStyle(style)).
                append(Component.literal("，对沿途的目标").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("造成").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.exManaDamage("50%")).
                append(Component.literal("，并").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("点燃").withStyle(style)).
                append(Component.literal("目标4s").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.coolDownTimeDescription(components, 7);
        Compute.DescriptionPassive(components, Component.literal("燃烬").withStyle(style));
        components.add(Component.literal(" 对处于").withStyle(ChatFormatting.WHITE).
                append(Component.literal("燃烧状态").withStyle(style)).
                append(Component.literal("的目标造成伤害后，提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%归一化炽焰元素强度").withStyle(style)).
                append(Component.literal("，持续2s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 对未处于").withStyle(ChatFormatting.WHITE).
                append(Component.literal("燃烧状态").withStyle(style)).
                append(Component.literal("的目标造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("燃烧").withStyle(style)).
                append(Component.literal("，会为你提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("20%伤害提升").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，至多叠加至").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("60%").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("，每个目标持续3s").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfElement();
    }

    @Override
    public void active(Player player) {
        if (Compute.PlayerUseWithHud(player, FireElementSword.playerActiveCoolDownMap, ModItems.FireElementSceptre.get(), 0, 7)) {
            Compute.playerItemCoolDown(player, this, 7);
            List<Mob> mobList = Compute.OneShotLaser(player, true, PlayerAttributes.manaDamage(player) * 0.5, ModParticles.LONG_RED_SPELL.get());
            mobList.forEach(mob -> Compute.IgniteMob(player, mob, 80));
        }
    }
}

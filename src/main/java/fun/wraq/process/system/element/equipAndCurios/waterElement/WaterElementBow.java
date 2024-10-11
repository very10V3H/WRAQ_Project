package fun.wraq.process.system.element.equipAndCurios.waterElement;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.MyArrow;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class WaterElementBow extends WraqBow implements ActiveItem {

    public WaterElementBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 600d);
        Utils.defencePenetration0.put(this, 40d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.45);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        Element.WaterElementValue.put(this, 2d);
    }

    @Override
    protected MyArrow summonArrow(ServerPlayer serverPlayer, double rate) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true, rate);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5f, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        MySound.soundToNearPlayer(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.WaterElementParticle.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.WaterElementParticle.get());
        return arrow;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWater;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionActive(components, Component.literal("水皆缥碧").withStyle(style));
        components.add(Component.literal(" 对").withStyle(ChatFormatting.WHITE).
                append(Component.literal("指针位置").withStyle(CustomStyle.styleOfMoon)).
                append(Component.literal("一定范围内的目标施加").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%碧水元素").withStyle(style)).
                append(Component.literal("，并削减其").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.defence("50%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDefence("50%")).
                append(Component.literal("，持续7s").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.coolDownTimeDescription(components, 25);
        Compute.DescriptionPassive(components, Component.literal("千丈见底").withStyle(style));
        components.add(Component.literal(" 对目标完成一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("碧水元素").withStyle(style)).
                append(Component.literal("主动参与的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("元素反应").withStyle(ChatFormatting.LIGHT_PURPLE)).
                append(Component.literal("后，使").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("归一化碧水元素强度").withStyle(style)).
                append(Component.literal("提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%").withStyle(style)).
                append(Component.literal("，持续7s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 从流飘荡，任意东西").withStyle(ChatFormatting.ITALIC).withStyle(style));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfElement();
    }

    public static void Passive(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && player.getMainHandItem().is(ModItems.WaterElementSword.get())) {
            WaterElementSword.playerElementEnhanceTickMap.put(player, player.getServer().getTickCount() + 140);
            Compute.sendEffectLastTime(player, ModItems.WaterElementSword.get().getDefaultInstance(), 140);
        }
    }

    @Override
    public void active(Player player) {
        if (Compute.PlayerUseWithHud(player, WaterElementSword.playerActiveCoolDownMap, ModItems.WaterElementSword.get(), 0, 25)) {
            Compute.playerItemCoolDown(player, this, 25);
            Vec3 pos = Compute.MyPlayerPickLocation(player, 15);
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 15, 15, 15));
            mobList.removeIf(mob -> mob.position().distanceTo(pos) > 6);
            mobList.forEach(mob -> {
                Element.ElementEffectAddToEntity(player, mob, Element.water, ElementValue.PlayerWaterElementValue(player), true, PlayerAttributes.attackDamage(player) * 4);
                WaterElementSword.mobDefenceDecreaseTickMap.put(mob, player.getServer().getTickCount() + 140);
            });
        }
    }
}

package fun.wraq.process.system.element.equipAndCurios.waterElement;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class WaterElementSword extends WraqSword implements ActiveItem {

    public WaterElementSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 600d);
        Utils.defencePenetration0.put(this, 40d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.9);
        Element.WaterElementValue.put(this, 2d);
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

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static Map<Entity, Integer> mobDefenceDecreaseTickMap = new HashMap<>();
    public static WeakHashMap<Player, Integer> playerElementEnhanceTickMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> playerActiveCoolDownMap = new WeakHashMap<>();

    public static double MobDefenceDecrease(Mob mob) {
        if (mobDefenceDecreaseTickMap.containsKey(mob) && mobDefenceDecreaseTickMap.get(mob) > mob.getServer().getTickCount()) {
            return 0.5;
        }
        return 1;
    }

    public static void Passive(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && player.getMainHandItem().is(ModItems.WaterElementSword.get())) {
            playerElementEnhanceTickMap.put(player, player.getServer().getTickCount() + 140);
            Compute.sendEffectLastTime(player, ModItems.WaterElementSword.get().getDefaultInstance(), 140);
        }
    }

    public static double PlayerWaterElementValueEnhance(Player player) {
        if (playerElementEnhanceTickMap.containsKey(player) && playerElementEnhanceTickMap.get(player) > player.getServer().getTickCount()) {
            return 2;
        }
        return 1;
    }


    @Override
    public void active(Player player) {
        if (Compute.PlayerUseWithHud(player, playerActiveCoolDownMap, ModItems.WaterElementSword.get(), 0, 25)) {
            Compute.playerItemCoolDown(player, this, 25);
            Vec3 pos = Compute.MyPlayerPickLocation(player, 15);
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, 15, 15, 15));
            mobList.removeIf(mob -> mob.position().distanceTo(pos) > 6);
            mobList.forEach(mob -> {
                Element.ElementEffectAddToEntity(player, mob, Element.water, ElementValue.PlayerWaterElementValue(player), true, PlayerAttributes.attackDamage(player) * 4);
                mobDefenceDecreaseTickMap.put(mob, player.getServer().getTickCount() + 140);
            });
            ParticleProvider.DisperseParticle(pos, (ServerLevel) player.level(), 1, 1, 120, ModParticles.WaterElementParticle.get(), 1);
            ParticleProvider.DisperseParticle(pos, (ServerLevel) player.level(), 1.5, 1, 120, ModParticles.WaterElementParticle.get(), 1);
        }
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}

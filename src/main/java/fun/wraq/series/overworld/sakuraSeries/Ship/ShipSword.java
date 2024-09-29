package fun.wraq.series.overworld.sakuraSeries.Ship;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter1.forest.ForestPowerEffectMob;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ShipSword extends WraqSword implements ActiveItem {

    public ShipSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 450d);
        Utils.defencePenetration0.put(this, 22d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.65);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
        Element.WaterElementValue.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfShip;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionActive(components, Component.literal("锚定").withStyle(style));
        components.add(Component.literal(" 将周围怪物").withStyle(ChatFormatting.WHITE).
                append(Component.literal("牵引").withStyle(style)).
                append(Component.literal("至自身位置").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 并击碎其").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("10-40")).
                append(Component.literal(" 持续5s").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" - 击碎的护甲数额随牵引的怪物数量增长。").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.coolDownTimeDescription(components, 12);
        ComponentUtils.manaCostDescription(components, 75);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterV();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 75)) {
            Level dimension = player.level();
            Compute.playerItemCoolDown(player, this, 12);
            Vec3 desPos = player.position();
            List<Mob> mobList = dimension.getEntitiesOfClass(Mob.class,
                            AABB.ofSize(player.position(), 20, 20, 20))
                    .stream().filter(mob -> mob.position().distanceTo(desPos) <= 6).toList();
            mobList.forEach(mob -> {
                Utils.ForestPowerEffectMobList.add(new ForestPowerEffectMob(desPos, 20, mob));
                Compute.addSlowDownEffect(mob, 40, 2);
                Compute.addDefenceDecreaseEffectParticle(mob, 100);
                StableAttributesModifier.addM(mob, StableAttributesModifier.mobDefenceModifier,
                        "ShipSword active", -10 * Math.min(4, mobList.size()), Tick.get() + 100, this);
            });
            MySound.soundToNearPlayer(player, SoundEvents.ANVIL_LAND);

            ParticleProvider.GatherParticle(desPos, (ServerLevel) player.level(), 1, 6, 120, ModParticles.LONG_LAKE.get(), 0.25);
            ParticleProvider.GatherParticle(desPos, (ServerLevel) player.level(), 1.5, 6, 120, ModParticles.LONG_LAKE.get(), 0.25);
        }
    }
}

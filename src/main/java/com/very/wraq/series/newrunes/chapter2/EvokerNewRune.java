package com.very.wraq.series.newrunes.chapter2;

import com.very.wraq.events.mob.chapter2.EvokerSpawnController;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.series.newrunes.RuneItem;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class EvokerNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public EvokerNewRune(Properties properties) {
        super(properties);
        Utils.manaPenetration.put(this, 0.1);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("魔源汲取").withStyle(style));
        components.add(Component.literal(" 每过4s，对半径8格内的所有怪物造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.ManaDamageValue("100%")));
        components.add(Component.literal(" 并基于怪物数量，为你回复至多").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.MaxMana("25%")));
        components.add(Component.literal(" 倍率线性增长").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    public int levelRequirement() {
        return 0;
    }

    @Override
    public boolean isArbitrarily() {
        return false;
    }

    public static void tick(Player player) {
        if (WraqCurios.isOn(EvokerNewRune.class, player)) {
            int tick = player.getServer().getTickCount();
            if (tick % 80 == 0) {
                List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 16, 16, 16));
                mobList.removeIf(mob -> mob.distanceTo(player) > 8);
                if (!mobList.isEmpty()) {
                    mobList.forEach(mob -> {
                        Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 1, false);
                        ParticleProvider.LineParticle(player.level(), (int) (mob.distanceTo(player) * 5),
                                player.position().add(0, 1, 0), mob.getEyePosition(), ParticleTypes.WITCH);
                    });
                    Compute.playerManaAddOrCost(player, Compute.PlayerMaxManaNum(player) * Math.min(5, mobList.size()) * 0.05);
                    Compute.coolDownTimeSend(player, NewRuneItems.evokerNewRune.get(), 80);
                }
            }
        }
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal(EvokerSpawnController.mobName).withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}

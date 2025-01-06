package fun.wraq.series.instance.series.lava.rune;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.newrunes.RuneItem;
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

public class TabooRune extends WraqCurios implements RuneItem {
    public TabooRune(Properties properties) {
        super(properties);
        Utils.manaPenetration.put(this, 0.1);
        Utils.healingAmplification.put(this, 0.1);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("魔源汲取").withStyle(style));
        components.add(Component.literal(" 每过4s，对半径8格内的所有怪物造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamageValue("100%")));
        components.add(Component.literal(" 并基于怪物数量，为你回复至多").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaValue("10%")));
        components.add(Component.literal(" 倍率线性增长").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        ComponentUtils.descriptionPassive(components, Component.literal("灵魂收集").withStyle(style));
        components.add(Component.literal(" 击杀怪物时，会收集其").withStyle(ChatFormatting.WHITE).
                append(Component.literal("灵魂").withStyle(style)));
        components.add(Component.literal(" 每枚").withStyle(ChatFormatting.WHITE).
                append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.getCommonDamageEnhance("1%")));
        components.add(Component.literal(" 至多可收集20枚，每枚存活5min").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public void tick(Player player) {
        int tick = Tick.get();
        if (tick % 80 == 0) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),
                    16, 16, 16));
            mobList.removeIf(mob -> mob.distanceTo(player) > 8);
            if (!mobList.isEmpty()) {
                mobList.forEach(mob -> {
                    Damage.causeManaDamageToMonster_RateApDamage(player, mob, 1, false);
                    ParticleProvider.createLineParticle(player.level(), (int) (mob.distanceTo(player) * 5),
                            player.position().add(0, 1, 0), mob.getEyePosition(), ParticleTypes.WITCH);
                });
                Mana.addOrCostPlayerMana(player, Mana.getPlayerMaxManaNum(player) * Math.min(5, mobList.size()) * 0.02);
                Compute.sendCoolDownTime(player, NewRuneItems.evokerNewRune.get(), 80);
            }
        }
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfAlchemy();
    }
}

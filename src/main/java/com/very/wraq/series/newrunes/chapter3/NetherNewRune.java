package com.very.wraq.series.newrunes.chapter3;

import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.registry.MySound;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.damage.Damage;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.UsageOrGetWayDescriptionItem;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.newrunes.RuneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class NetherNewRune extends WraqCurios implements RuneItem, UsageOrGetWayDescriptionItem {

    public NetherNewRune(Properties properties) {
        super(properties);
        Utils.critDamage.put(this, 0.2);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("熔岩爆裂").withStyle(style));
        components.add(Component.literal(" 击杀一名敌人时，会在其位置造成一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("小范围爆炸").withStyle(style)));
        components.add(Component.literal(" 爆炸").withStyle(style).
                append(Component.literal("将直接对范围内的目标造成所击杀敌人的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.maxHealth("8%")).
                append(Component.literal("物理伤害").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 并且附带").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamageValue("100%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" 物理伤害仅会受护甲影响，不受任何伤害提升").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixChapterIII();
    }

    @Override
    public int levelRequirement() {
        return 0;
    }


    public static void onKill(Player player, Mob mob) {
        if (!WraqCurios.isOn(NetherNewRune.class, player)) return;
        List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 6, 6, 6));
        List<Player> players = mob.level().getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 16, 16, 16));
        ParticleProvider.SpaceRangeParticle((ServerLevel) player.level(), mob.position(), 3, 10, ParticleTypes.FLAME);

        players.forEach(player1 -> {
            MySound.soundToPlayer(player1, SoundEvents.BLAZE_SHOOT, mob.getEyePosition());
        });

        mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 3 || !mob1.isAlive());
        mobList.forEach(mob1 -> {
            Damage.causeAttackDamageToMonsterOnlyComputeDefence(player, mob1, mob.getMaxHealth() * 0.08);
            Damage.causeIgNoreDefenceDamageToMonster(player, mob1, PlayerAttributes.attackDamage(player));
        });
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal("下界怪物").withStyle(CustomStyle.styleOfNether)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}

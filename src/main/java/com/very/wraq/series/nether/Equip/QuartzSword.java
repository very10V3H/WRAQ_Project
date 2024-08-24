package com.very.wraq.series.nether.Equip;

import com.very.wraq.common.MySound;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuartzSword extends WraqSword implements ActiveItem {

    public QuartzSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 80d);
        Utils.manaDamage.put(this, 65d);
        Utils.defencePenetration0.put(this, 1200d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.45);
        Utils.critDamage.put(this, 0.35);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Element.FireElementValue.put(this, 0.8);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPower;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("主动：").withStyle(ChatFormatting.AQUA).
                append(Component.literal("夸塔兹能量涌动").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#ea7552")))));
        components.add(Component.literal("对周围所有单位雷击，造成").withStyle(ChatFormatting.WHITE).
                append(Component.literal("燃烧").withStyle(ChatFormatting.YELLOW)).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("250%")));
        Compute.CoolDownTimeDescription(components, 5);
        Compute.ManaCostDescription(components, 90);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterIII();
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 90)) {
            Compute.PlayerPowerParticle(player);
            Level level = player.level();
            player.getCooldowns().addCooldown(ModItems.QuartzSword.get(), (int) (100 - 100 * PlayerAttributes.coolDownDecrease(player)));
            List<Player> playerList = level.getNearbyPlayers(TargetingConditions.DEFAULT, player, AABB.ofSize(player.position(), 10, 10, 10));
            Iterator<Player> iterator = playerList.iterator();
            while (iterator.hasNext()) {
                Player player1 = iterator.next();
                Compute.Damage.manaDamageToPlayer(player, player1, 2.5f);
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt.setCause((ServerPlayer) player);
                lightningBolt.setSilent(true);
                lightningBolt.setVisualOnly(true);
                lightningBolt.setDamage(0);
                lightningBolt.moveTo(player1.position());
                level.addFreshEntity(lightningBolt);
            }
            List<Mob> monsterList = level.getNearbyEntities(Mob.class, TargetingConditions.DEFAULT, player, AABB.ofSize(player.position(), 10, 10, 10));
            Iterator<Mob> iterator1 = monsterList.iterator();
            while (iterator1.hasNext()) {
                Mob monster = iterator1.next();
                Compute.Damage.ManaDamageToMonster_RateApDamage(player, monster, 2.5f, true);
                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
                lightningBolt.setCause((ServerPlayer) player);
                lightningBolt.setSilent(true);
                lightningBolt.setVisualOnly(true);
                lightningBolt.setDamage(0);
                lightningBolt.moveTo(monster.position());
                level.addFreshEntity(lightningBolt);
            }
            MySound.SoundToAll(player, ModSounds.Nether_Power.get());
        }
    }
}

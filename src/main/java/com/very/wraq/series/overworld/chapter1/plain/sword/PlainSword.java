package com.very.wraq.series.overworld.chapter1.plain.sword;

import com.very.wraq.common.registry.MySound;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;

import java.util.ArrayList;
import java.util.List;

public class PlainSword extends WraqSword implements ActiveItem {
    private final int tier;

    public PlainSword(Properties properties, int num) {
        super(properties);
        this.tier = num;
        Utils.attackDamage.put(this, PlainSwordAttributes.BaseDamage[num]);
        Utils.critRate.put(this, PlainSwordAttributes.CriticalRate[num]);
        Utils.critDamage.put(this, PlainSwordAttributes.CriticalDamage[num]);
        Utils.movementSpeedWithoutBattle.put(this, PlainSwordAttributes.MovementSpeed[num]);
        Utils.attackSpeedUp.put(this, PlainSwordAttributes.AttackSpeedUp[num]);
        Element.LifeElementValue.put(this, PlainSwordAttributes.LifeElementValue[num]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionActive(components, Component.literal("平原洗礼").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal("右键恢复").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health(PlainSwordAttributes.Effect[tier])));
        ComponentUtils.coolDownTimeDescription(components, 20);
        ComponentUtils.manaCostDescription(components, 60);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 60)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.75 * 2, 1, 12, ParticleTypes.HEART);
            ParticleProvider.RandomMoveParticle(serverPlayer, 0.75 * 2, 1, 8, ParticleTypes.COMPOSTER);
            Compute.playerHeal(player, player.getMaxHealth() * PlainSwordAttributes.EffectNum[tier]);
            player.getCooldowns().addCooldown(ModItems.PlainSword0.get(), (int) (400 - 400.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.PlainSword1.get(), (int) (400 - 400.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.PlainSword2.get(), (int) (400 - 400.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.PlainSword3.get(), (int) (400 - 400.0 * PlayerAttributes.coolDownDecrease(player)));
            MySound.SoundToAll(player, ModSounds.Healing.get());
        }
    }
}

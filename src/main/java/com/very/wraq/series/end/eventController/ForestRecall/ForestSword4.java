package com.very.wraq.series.end.eventController.ForestRecall;

import com.very.wraq.common.MySound;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForestSword4 extends WraqSword implements ActiveItem {
    public ForestSword4(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 50d);
        Utils.defencePenetration0.put(this, 1800d);
        Utils.healthSteal.put(this, 0.05);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.4);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Element.LifeElementValue.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfForest;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionActive(components, Component.literal("切割/砍伐").withStyle(ChatFormatting.DARK_RED));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("15%+[250]")).
                append(Component.literal("持续20s").withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components, 30);
        Compute.ManaCostDescription(components, 60);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getIntensifiedSuffix();
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 60)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            CompoundTag data = serverPlayer.getPersistentData();
            int tickCount = serverPlayer.getServer().getTickCount();
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5 * 2, 0.25, 12, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.25 * 2, 0.5, 16, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.0 * 2, 0.75, 20, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.75 * 2, 1, 24, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.5 * 2, 1.25, 28, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.25 * 2, 1.5, 32, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.0 * 2, 1.75, 36, ParticleTypes.COMPOSTER);
            data.putInt(StringUtils.ForestSwordSkill1, tickCount + 400);
            player.getCooldowns().addCooldown(ModItems.ForestSword0.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword1.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword2.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword3.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword4.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
            MySound.SoundToAll(player, ModSounds.Attack.get());
        }
    }
}

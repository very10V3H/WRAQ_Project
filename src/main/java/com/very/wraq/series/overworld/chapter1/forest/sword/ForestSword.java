package com.very.wraq.series.overworld.chapter1.forest.sword;

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

public class ForestSword extends WraqSword implements ActiveItem {

    private final int tier;
    public ForestSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, ForestSwordAttributes.BaseDamage[tier]);
        Utils.defencePenetration0.put(this, ForestSwordAttributes.DefencePenetration0[tier]);
        Utils.healthSteal.put(this, ForestSwordAttributes.HealthSteal[tier]);
        Utils.critRate.put(this, ForestSwordAttributes.CriticalRate[tier]);
        Utils.critDamage.put(this, ForestSwordAttributes.CriticalDamage[tier]);
        Element.LifeElementValue.put(this, ForestSwordAttributes.LifeElementValue[tier]);
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
                append(Compute.AttributeDescription.DefencePenetration("15%+[50]")).
                append(Component.literal("，持续" + (int) ForestSwordAttributes.EffectNum[tier] + "s").withStyle(ChatFormatting.WHITE)));
        Compute.CoolDownTimeDescription(components, 30);
        Compute.ManaCostDescription(components, 60);
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
            CompoundTag data = serverPlayer.getPersistentData();
            int tickCount = serverPlayer.getServer().getTickCount();
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5 * 2, 0.25, 12, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.25 * 2, 0.5, 16, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 1.0 * 2, 0.75, 20, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.75 * 2, 1, 24, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.5 * 2, 1.25, 28, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.25 * 2, 1.5, 32, ParticleTypes.COMPOSTER);
            ParticleProvider.VerticleCircleParticle(serverPlayer, 0.0 * 2, 1.75, 36, ParticleTypes.COMPOSTER);
            Compute.sendEffectLastTime(player, this, (int) ForestSwordAttributes.EffectNum[tier] * 20);
            data.putInt(StringUtils.ForestSwordSkill0, tickCount + (int) ForestSwordAttributes.EffectNum[tier] * 20);
            player.getCooldowns().addCooldown(ModItems.ForestSword0.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword1.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword2.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword3.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.ForestSword4.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
            Compute.SoundToAll(player, ModSounds.Attack.get());
        }
    }
}

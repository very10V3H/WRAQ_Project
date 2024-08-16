package com.very.wraq.series.overworld.chapter1.volcano.sword;

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

public class VolcanoSword extends WraqSword implements ActiveItem {

    private final int tier;
    public VolcanoSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, VolcanoSwordAttributes.BaseDamage[tier]);
        Utils.defencePenetration0.put(this, VolcanoSwordAttributes.DefencePenetration0[tier]);
        Utils.critRate.put(this, VolcanoSwordAttributes.CriticalRate[tier]);
        Utils.critDamage.put(this, VolcanoSwordAttributes.CriticalDamage[tier]);
        Utils.attackSpeedUp.put(this, VolcanoSwordAttributes.AttackSpeedUp[tier]);
        Element.FireElementValue.put(this, VolcanoSwordAttributes.FireElementValue[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfVolcano;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionActive(components, Component.literal("喷发").withStyle(ChatFormatting.YELLOW));
        Compute.DescriptionNum(components, "1.获得", Compute.AttributeDescription.CritDamage((int) (VolcanoSwordAttributes.ExCritDamage[tier] * 100) + "%"), "");
        Compute.DescriptionNum(components, "2.获得", Compute.AttributeDescription.ExAttackDamage((int) VolcanoSwordAttributes.ExAttackDamage[tier] + " "), "");
        components.add(Component.literal("持续5s").withStyle(ChatFormatting.WHITE));
        Compute.CoolDownTimeDescription(components, 10);
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
            CompoundTag data = player.getPersistentData();
            int tickCount = player.getServer().getTickCount();
            ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 0.25, 1, 16, ParticleTypes.ANGRY_VILLAGER);
            ParticleProvider.RandomMoveParticle((ServerPlayer) player, 0, 0.25, 32, ParticleTypes.ASH);
            String[] EffectString = {
                    StringUtils.VolcanoSwordSkill.Skill0,
                    StringUtils.VolcanoSwordSkill.Skill1,
                    StringUtils.VolcanoSwordSkill.Skill2,
                    StringUtils.VolcanoSwordSkill.Skill3
            };
            Compute.sendEffectLastTime(player, ModItems.VolcanoSword3.get().getDefaultInstance(), 100);
            data.putInt(EffectString[tier], tickCount + 100);
            player.getCooldowns().addCooldown(ModItems.VolcanoSword0.get(), (int) (200 - 200.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword1.get(), (int) (200 - 200.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword2.get(), (int) (200 - 200.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword3.get(), (int) (200 - 200.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword4.get(), (int) (200 - 200.0 * PlayerAttributes.coolDownDecrease(player)));
            Compute.SoundToAll(player, ModSounds.Lava.get());
        }
    }
}

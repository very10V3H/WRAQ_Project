package com.very.wraq.series.worldsoul;

import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.ModEntityType;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoulSceptre extends WraqSceptre {

    public SoulSceptre(Properties p_42964_) {
        super(p_42964_);
        Utils.manaDamage.put(this, SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaAttackDamage);
        Utils.manaRecover.put(this, SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaRecover);
        Utils.manaPenetration0.put(this, SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaPenetration0);
        Utils.movementSpeedWithoutBattle.put(this, SoulEquipAttribute.BaseAttribute.SoulSceptre.MovementSpeed);
        Utils.manaCost.put(this, SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaCost);
        Utils.mainHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.sceptreTag.put(this, 1d);
    }

    public static final int ManaCost = 20;

    @Override
    public void shoot(Player player) {
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        double ManaCost = SoulSceptre.getManaCost(player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID));
        if (Compute.ManaSkillLevelGet(data, 10) > 0 || Compute.playerManaCost(player, (int) ManaCost)) {
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player, level,
                    PlayerAttributes.manaDamage(player), PlayerAttributes.manaPenetration(player),
                    PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sky);
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);

            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.WORLD.get());
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ModParticles.WORLD.get());
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 2, 0.25, 12, ModParticles.WORLD.get());
            Compute.SoundToAll(player, ModSounds.Mana.get());
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWorld;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionActive(components, Component.literal("本源打击-陨石").withStyle(style));
        components.add(Component.literal(" 指针处将坠下一枚陨石，陨石会对一定范围内的怪物随机造成持续").withStyle(ChatFormatting.WHITE).
                append(Component.literal("4s").withStyle(style)).
                append(Component.literal("的负面效果").withStyle(ChatFormatting.GRAY)));
        components.add(Component.literal("  1.中毒：每秒造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("30%")));
        components.add(Component.literal("  2.缓慢：减缓目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.movementSpeedWithoutBattle("")));
        components.add(Component.literal("  3.燃烧：每秒造成").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("35%")));
        components.add(Component.literal("  4.致残：大幅降低目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.movementSpeedWithoutBattle("")));
        components.add(Component.literal(" 对一定范围内的玩家随机造成").withStyle(ChatFormatting.WHITE).
                append(Component.literal("增益效果").withStyle(ChatFormatting.GREEN)));
        components.add(Component.literal("  1.治疗：回复已损失生命值10%的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("")));
        components.add(Component.literal("  2.抗性：持续4秒的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("5%")).
                append(Compute.AttributeDescription.Defence("")).
                append(Compute.AttributeDescription.ManaDamage("5%")).
                append(Compute.AttributeDescription.ManaDefence("")));
        components.add(Component.literal("  3.攻击增幅：持续4秒的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("10%")).
                append(Compute.AttributeDescription.ManaDamage("10%")));
        components.add(Component.literal("  4.穿透增幅：持续4秒的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("20%")).
                append(Compute.AttributeDescription.ManaPenetration("20%")));
        Compute.CoolDownTimeDescription(components, 8);
        Compute.ManaCostDescription(components, 120);
        components.add(Component.literal(" Idea From:Mr_RED").withStyle(ChatFormatting.LIGHT_PURPLE));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Wraq();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND) && player.isShiftKeyDown()) {
            try {
                SoulEquipAttribute.Forging(player.getItemInHand(InteractionHand.MAIN_HAND), player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Compute.manaAttack(player);
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Items.AIR)
                && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains(StringUtils.ManaCore.ManaCore)) {
                if (Utils.ManaCoreMap.isEmpty()) Utils.setManaCoreMap();
                player.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(Utils.ManaCoreMap.get(data.getString(StringUtils.ManaCore.ManaCore))));
                data.remove(StringUtils.ManaCore.ManaCore);
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static double getManaCost(CompoundTag data) {
        if (data.contains(StringUtils.SoulEquipForge))
            return SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaCost - SoulEquipAttribute.ForgingAddition.ManaCost;
        else return SoulEquipAttribute.BaseAttribute.SoulSceptre.ManaCost;
    }
}



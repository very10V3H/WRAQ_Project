package com.very.wraq.series.worldsoul;

import com.very.wraq.core.MyArrow;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoulBow extends WraqBow {
    public SoulBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, SoulEquipAttribute.BaseAttribute.SoulBow.AttackDamage);
        Utils.critRate.put(this, SoulEquipAttribute.BaseAttribute.SoulBow.CritRate);
        Utils.critDamage.put(this, SoulEquipAttribute.BaseAttribute.SoulBow.CritDamage);
        Utils.defencePenetration0.put(this, SoulEquipAttribute.BaseAttribute.SoulBow.DefencePenetration0);
        Utils.movementSpeedWithoutBattle.put(this, SoulEquipAttribute.BaseAttribute.SoulBow.MovementSpeed);
    }


    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWorld;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Wraq();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        Compute.bowAttack(player);
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND) && player.isShiftKeyDown()) {
            try {
                SoulEquipAttribute.Forging(player.getItemInHand(InteractionHand.MAIN_HAND), player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return interactionHand.equals(InteractionHand.MAIN_HAND)
                ? InteractionResultHolder.success(player.getMainHandItem()) : InteractionResultHolder.success(player.getOffhandItem());
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.WORLD.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.WORLD.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 2, 0.25, 12, ModParticles.WORLD.get());
    }
}

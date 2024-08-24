package com.very.wraq.series.overworld.chapter7.vd;

import com.very.wraq.common.Compute;
import com.very.wraq.common.MySound;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.core.MyArrow;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.ForgeItem;
import com.very.wraq.projectiles.MainHandTickItem;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter7.C7Items;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class VdBow extends WraqBow implements ForgeItem, ActiveItem, MainHandTickItem, VdWeaponCommon {

    public VdBow(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 1800d);
        Utils.defencePenetration0.put(this, 4000d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.55);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWorld;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return VdWeaponCommon.additionalDescriptions(stack);
    }

    @Override
    public Component oneLineDescription() {
        return Component.literal("「至高科技结晶」").withStyle(getMainStyle()).withStyle(ChatFormatting.BOLD);
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix2Wraq();
    }

    @Override
    public void active(Player player) {
        VdWeaponCommon.active(player, this);
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.DevilBow.get(), 1));
            add(new ItemStack(C7Items.vdSoul.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 32));
            add(new ItemStack(ModItems.ReputationMedal.get(), 128));
            add(new ItemStack(ModItems.RefiningGold.get(), 8));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }};
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        MySound.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.WORLD.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.WORLD.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 2, 0.25, 12, ModParticles.WORLD.get());
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void tick(Player player) {
        VdWeaponCommon.tick(player);
    }
}

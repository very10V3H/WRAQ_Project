package com.very.wraq.series.overworld.chapter7.vd;

import com.very.wraq.common.Compute;
import com.very.wraq.common.ModEntityType;
import com.very.wraq.common.MySound;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.registry.ModSounds;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.ForgeItem;
import com.very.wraq.projectiles.MainHandTickItem;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter7.C7Items;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class VdSceptre extends WraqSceptre implements ForgeItem, ActiveItem, MainHandTickItem, VdWeaponCommon {
    public VdSceptre(Properties properties) {
        super(properties);
        Utils.manaDamage.put(this, 3600d);
        Utils.manaRecover.put(this, 30d);
        Utils.manaPenetration0.put(this, 4000d);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Utils.manaCost.put(this, 45d);
        Utils.coolDownDecrease.put(this, 0.2);
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
            add(new ItemStack(ModItems.DevilSceptre.get(), 1));
            add(new ItemStack(C7Items.vdSoul.get(), 256));
            add(new ItemStack(ModItems.completeGem.get(), 32));
            add(new ItemStack(ModItems.ReputationMedal.get(), 128));
            add(new ItemStack(ModItems.RefiningGold.get(), 8));
            add(new ItemStack(ModItems.WorldSoul3.get(), 8));
        }};
    }

    @Override
    public void shoot(Player player) {
        CompoundTag data = player.getPersistentData();
        Level level = player.level();
        double ManaCost = 45;
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
            MySound.SoundToAll(player, ModSounds.Mana.get());
        }
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

package com.very.wraq.customized.players.sceptre.liulixian_;

import com.very.wraq.coreAttackModule.ManaAttackModule;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.mana.SwordAir;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import com.very.wraq.valueAndTools.registry.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class liulixian_ {
    public static void LiuLiXian(Player player) {
        if (Utils.LiuLiXian != null && Utils.LiuLiXian.equals(player)) {
            if (Utils.LiuLiXianCountsLastTick < player.getServer().getTickCount()) Utils.LiuLiXianCounts = 0;
            if (Utils.LiuLiXianManaAttackDelay > 0) Utils.LiuLiXianManaAttackDelay --;
            if (Utils.LiuLiXianManaAttackDelay == 0 && !player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get())) {
                Utils.LiuLiXianManaAttackDelay --;
                Level level = player.level();
                ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player, level,
                        Compute.PlayerAttributes.PlayerManaDamage(player),
                        Compute.PlayerAttributes.PlayerManaPenetration(player),
                        Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Sea,true);
                newArrow.setSilent(true);
                newArrow.setNoGravity(true);
                
                newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
                ProjectileUtil.rotateTowardsMovement(newArrow, 0);
                level.addFreshEntity(newArrow);
                ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
                ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.SNOWFLAKE);
                Compute.SoundToAll(player, ModSounds.Mana.get());
            }
            if (Utils.LiuLiXianManaAttackDelay == 0 && player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get())) {
                Utils.LiuLiXianManaAttackDelay --;
                Level level = player.level();
                Vec3 vec3 = player.pick(4,0,false).getLocation();
                Vec3 delta = vec3.subtract(player.getEyePosition());
                List<Mob> mobList = level.getEntitiesOfClass(Mob.class, player.getBoundingBox().expandTowards(delta));
                AtomicReference<Mob> NearestMob = new AtomicReference<>();
                AtomicReference<Double> Distance = new AtomicReference<>((double) 20);
                mobList.forEach(mob -> {
                    if (mob.distanceTo(player) < Distance.get()) {
                        NearestMob.set(mob);
                        Distance.set((double) mob.distanceTo(player));
                    }
                });
                if (NearestMob.get() != null && player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get())) {
                    CompoundTag data = player.getPersistentData();
                    ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SAKURA.get(), player, level,
                            Compute.PlayerAttributes.PlayerManaDamage(player),
                            Compute.PlayerAttributes.PlayerManaPenetration(player),
                            Compute.PlayerAttributes.PlayerManaPenetration0(player), StringUtils.ParticleTypes.Sea);
                    ManaAttackModule.BasicAttack(player,NearestMob.get(),Compute.PlayerAttributes.PlayerManaDamage(player),
                            Compute.PlayerAttributes.PlayerManaPenetration(player),Compute.PlayerAttributes.PlayerManaPenetration0(player),
                            player.level(),newArrow);
                }
            }
        }
    }

    public static double LiuLiXianFirstManaAttack(Player player, Mob mob) {
        if (Utils.LiuLiXian != null && Utils.LiuLiXian.equals(player) && !Utils.LiuLiXianManaFlag) {
            return LiulixianCurios.FirstAttack(player);
        }
        return 0;
    }

    public static double LiuLiXianSecondManaAttack(Player player, Mob mob) {
        if (Utils.LiuLiXian != null && Utils.LiuLiXian.equals(player) && Utils.LiuLiXianManaFlag) {
            return LiulixianCurios.SecondAttack(player, mob);
        }
        return 0;
    }

    public static void LiuLiXianSwitch(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get())) {
            CompoundTag originalData = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
            ItemStack newSceptre = ModItems.LiuLiXianSceptre.get().getDefaultInstance();
            newSceptre.getOrCreateTagElement(Utils.MOD_ID).merge(originalData);
            player.setItemInHand(InteractionHand.MAIN_HAND, newSceptre);
        } else {
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSceptre.get())) {
                CompoundTag originalData = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
                ItemStack newSceptre = ModItems.LiuLiXianSword.get().getDefaultInstance();
                newSceptre.getOrCreateTagElement(Utils.MOD_ID).merge(originalData);
                player.setItemInHand(InteractionHand.MAIN_HAND, newSceptre);
            }
        }
    }

    public static void LiuLiXianActive(Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSword.get())
                || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.LiuLiXianSceptre.get())) {
            int TickCount = player.getServer().getTickCount();
            if (Utils.LiuLiXianTick < TickCount) {
                SwordAir swordAir = new SwordAir(ModEntityType.SWORD_AIR.get(),player,player.level(),false);
                swordAir.shootFromRotation(player,player.getXRot(),player.getYRot(),1,0.1f,0);
                swordAir.setNoGravity(true);
                swordAir.setSilent(true);
                SwordAir swordAir1 = new SwordAir(ModEntityType.SWORD_AIR.get(), player,player.level(),true);
                swordAir1.shootFromRotation(player,player.getXRot(),player.getYRot() + 20,1,0.1f,0);
                swordAir1.setNoGravity(true);
                swordAir1.setSilent(true);
                swordAir1.setInvisible(true);
                SwordAir swordAir2 = new SwordAir(ModEntityType.SWORD_AIR.get(), player,player.level(),true);
                swordAir2.shootFromRotation(player,player.getXRot(),player.getYRot() - 20,1,0.1f,0);
                swordAir2.setNoGravity(true);
                swordAir2.setSilent(true);
                swordAir2.setInvisible(true);
                player.level().addFreshEntity(swordAir);
                player.level().addFreshEntity(swordAir1);
                player.level().addFreshEntity(swordAir2);
                Utils.LiuLiXianTick = TickCount + 160;
                Compute.CoolDownTimeSend(player, ModItems.LiuLiXianSword.get().getDefaultInstance(), 160);
            }
        }
    }
}

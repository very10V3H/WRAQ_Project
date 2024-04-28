package com.very.wraq.coreAttackModule;

import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisa;
import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisaCurios1;
import com.very.wraq.customized.players.sceptre.FengXiaoju.FengXiaoJuCurios1;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanillaSword;
import com.very.wraq.customized.players.sword.ZuoSI.ZuoSiCurios;
import com.very.wraq.process.element.equipAndCurios.fireElement.FireElementSceptre;
import com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementSceptre;
import com.very.wraq.process.element.equipAndCurios.waterElement.WaterElementSceptre;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.process.Power.PowerLogic;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.mana.NewArrow;
import com.very.wraq.projectiles.mana.NewArrowMagma;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.series.instance.Castle.CastleManaArmor;
import com.very.wraq.series.nether.Equip.MagmaSceptre.MagmaSceptre;
import com.very.wraq.series.nether.Equip.MagmaSceptre.MagmaSceptreAttributes;
import com.very.wraq.series.overWorld.MainStory_I.Plain.Sceptre.PlainSceptre0;
import com.very.wraq.series.overWorld.MainStory_I.Plain.Sceptre.PlainSceptre4;
import com.very.wraq.series.overWorld.MainStory_II.Evoker.EvokerSword;
import com.very.wraq.series.worldSoul.SoulSceptre;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.ModEntityType;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaAttackRequestC2SPacket {
    private final int count;

    public ManaAttackRequestC2SPacket(int count) {
        this.count = count;
    }

    public ManaAttackRequestC2SPacket(FriendlyByteBuf buf) {
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            Player player = (Player) serverPlayer;
            Level level = player.level();
            CompoundTag data = player.getPersistentData();
            int tick = serverPlayer.getServer().getTickCount();
            if (Utils.PlayerManaAttackTime.containsKey(serverPlayer) && tick - Utils.PlayerManaAttackTime.get(serverPlayer) < 9) return;
            if (Utils.LiuLiXian != null && Utils.LiuLiXian.equals(player) && Utils.LiuLiXianCore) {
                Utils.LiuLiXianManaAttackDelay = 3;
            }
            BlackFeisaCurios1.Passive2(player);
            FengXiaoJuCurios1.ExAttackCount(player);
            if (Compute.ManaSkillLevelGet(player.getPersistentData(), 10) == 10) CastleManaArmor.NormalAttack(player); //
            Utils.PlayerManaAttackTime.put(serverPlayer,tick);
            if (ZuoSiCurios.IsPlayer(player)) return;
            switch (count) {
                case 0 -> {
                    if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player,PlainSceptre0.ManaCost)) {
                        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_PLAIN.get(), player,level,
                                Compute.PlayerAttributes.PlayerManaDamage(player),Compute.PlayerAttributes.PlayerManaPenetration(player),
                                Compute.PlayerAttributes.PlayerManaPenetration0(player), StringUtils.ParticleTypes.Plain);
                        newArrow.setSilent(true);
                        newArrow.setNoGravity(true);
                        newArrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0,3,1);
                        ProjectileUtil.rotateTowardsMovement(newArrow,0);
                        level.addFreshEntity(newArrow);
                        ParticleProvider.FaceCircleCreate((ServerPlayer) player,1,0.75,20, ParticleTypes.SCRAPE);
                        ParticleProvider.FaceCircleCreate((ServerPlayer) player,1.5,0.5,16, ParticleTypes.SCRAPE);
                    }
                }
                case 1 -> {
                    if (!level.isClientSide) {
                        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player,PlainSceptre4.ManaCost)) {
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_PLAIN.get(),
                                    player, level, Compute.PlayerAttributes.PlayerManaDamage(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Plain);
                            newArrow.setSilent(true);
                            newArrow.setNoGravity(true);
                            
                            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
                            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
                            level.addFreshEntity(newArrow);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.COMPOSTER);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 2, 0.25, 12, ParticleTypes.COMPOSTER);
                            Compute.SoundToAll(player, ModSounds.Mana.get());
                        }
                    }
                }
                case 2 -> {
                    if (!level.isClientSide) {
                        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player,EvokerSword.ManaCost)) {
                            NewArrow newArrow = new NewArrow(player, level, Compute.PlayerAttributes.PlayerManaDamage(player), Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerExpUp(player), false, Compute.PlayerAttributes.PlayerManaPenetration0(player));
                            newArrow.setSilent(true);
                            newArrow.setNoGravity(true);
                            
                            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
                            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
                            level.addFreshEntity(newArrow);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
                            Compute.SoundToAll(player, ModSounds.Mana.get());
                        }
                    }
                }
                case 3 -> {
                    if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof MagmaSceptre) {
                        MagmaSceptre magmaSceptre = (MagmaSceptre) player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                        int Num = magmaSceptre.getNum();
                        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) MagmaSceptreAttributes.ManaCost[Num])) {
                            NewArrowMagma newArrow = new NewArrowMagma(player, level, Compute.PlayerAttributes.PlayerManaDamage(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerExpUp(player), Compute.PlayerAttributes.PlayerManaPenetration0(player));
                            newArrow.setSilent(true);
                            newArrow.setNoGravity(true);
                            
                            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
                            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
                            level.addFreshEntity(newArrow);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.LONG_VOLCANO.get());
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ModParticles.LONG_VOLCANO.get());
                            Compute.SoundToAll(player, ModSounds.Mana.get());
                        }
                    }
                }
                case 4 -> {
                    if (!level.isClientSide) {
                        double ManaCost = SoulSceptre.getManaCost(player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID));
                        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) ManaCost)) {
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player, level,
                                    Compute.PlayerAttributes.PlayerManaDamage(player), Compute.PlayerAttributes.PlayerManaPenetration(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Sky);
                            newArrow.setSilent(true);
                            newArrow.setNoGravity(true);
                            
                            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
                            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
                            level.addFreshEntity(newArrow);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.WORLD.get());
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ModParticles.WORLD.get());
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 2, 0.25, 12, ModParticles.WORLD.get());
                            Compute.SoundToAll(player, ModSounds.Mana.get());
                        }
                    }
                }
                case 5 -> {
                    if (!level.isClientSide) {
                        double ManaCost = 15;
                        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) ManaCost)) {
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player,
                                    level, Compute.PlayerAttributes.PlayerManaDamage(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Sky);
                            newArrow.setSilent(true);
                            newArrow.setNoGravity(true);
                            
                            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
                            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
                            level.addFreshEntity(newArrow);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.SNOWFLAKE);
                            Compute.SoundToAll(player, ModSounds.Mana.get());
                        }
                    }
                }
                case 6 -> {
                    if (!level.isClientSide) {
                        double ManaCost = 10;
                        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) ManaCost)) {
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player,
                                    level, Compute.PlayerAttributes.PlayerManaDamage(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Sky);
                            newArrow.setSilent(true);
                            newArrow.setNoGravity(true);
                            
                            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
                            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
                            level.addFreshEntity(newArrow);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.DRIPPING_WATER);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.DRIPPING_WATER);
                            Compute.SoundToAll(player, ModSounds.Mana.get());
                        }
                    }
                }
                case 7 -> {
                    if (!level.isClientSide) {
                        double ManaCost = 15;
                        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) ManaCost)) {
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_NETHER.get(),
                                    player, level, Compute.PlayerAttributes.PlayerManaDamage(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Entropy);
                            newArrow.setSilent(true);
                            newArrow.setNoGravity(true);
                            
                            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
                            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
                            level.addFreshEntity(newArrow);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.LONG_ENTROPY.get());
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ModParticles.LONG_ENTROPY.get());
                            Compute.SoundToAll(player, ModSounds.Mana.get());
                        }
                    }
                }
                case 8 -> {
                    if (!level.isClientSide) {
                        double ManaCost = 15;
                        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) ManaCost)) {
                            Utils.ShangMengLi = player;
                            Utils.ShangMengLiManaCount = true;
                            Utils.ShangMengLiDoubleManaAttackDelay = 3;
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SEA.get(), player, level,
                                    Compute.PlayerAttributes.PlayerManaDamage(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Sea);
                            newArrow.setSilent(true);
                            newArrow.setNoGravity(true);
                            
                            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
                            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
                            level.addFreshEntity(newArrow);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.DRIPPING_WATER);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.DRIPPING_WATER);
                            Compute.SoundToAll(player, ModSounds.Mana.get());
                        }
                    }
                }
                case 9 -> {
                    if (!level.isClientSide) {
                        double ManaCost = 15;
                        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) ManaCost)) {
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(),
                                    player, level, Compute.PlayerAttributes.PlayerManaDamage(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.World,true);
                            newArrow.setSilent(true);
                            newArrow.setNoGravity(true);
                            
                            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 2.5f, 1.0f);
                            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
                            level.addFreshEntity(newArrow);
                            Compute.FaceIceParticleCreate(player,player.level());
                            Compute.SoundToAll(player, ModSounds.Mana.get());
                        }
                    }
                }
                case 10 -> {
                    if (!level.isClientSide) {
                        double ManaCost = 15;
                        if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player, (int) ManaCost)) {
                            BlackFeisa.Player = player;
                            BlackFeisa.BlackFeisaManaCount = true;
                            BlackFeisa.DoubleManaAttackDelay = 3;
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), player, level,
                                    Compute.PlayerAttributes.PlayerManaDamage(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.EndParticle);
                            newArrow.setSilent(true);
                            newArrow.setNoGravity(true);
                            
                            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
                            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
                            level.addFreshEntity(newArrow);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ParticleTypes.WITCH);
                            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ParticleTypes.WITCH);
                            Compute.SoundToAll(player, ModSounds.Mana.get());
                        }
                    }
                }
                case 11 -> {
                    if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player,15)) {
                        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player,level,
                                Compute.PlayerAttributes.PlayerManaDamage(player),Compute.PlayerAttributes.PlayerManaPenetration(player),
                                Compute.PlayerAttributes.PlayerManaPenetration0(player),StringUtils.ParticleTypes.Sea);
                        newArrow.setSilent(true);
                        newArrow.setNoGravity(true);

                        newArrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0,3,1);
                        ProjectileUtil.rotateTowardsMovement(newArrow,0);
                        level.addFreshEntity(newArrow);
                        ParticleProvider.FaceCircleCreate((ServerPlayer) player,1,0.75,20, ParticleTypes.FIREWORK);
                        ParticleProvider.FaceCircleCreate((ServerPlayer) player,1.5,0.5,16, ParticleTypes.FIREWORK);
                    }
                }
                case 12 -> {
                    if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player,15)) {
                        PowerLogic.PlayerReleasePowerType(player,10);
                        PowerLogic.PlayerPowerRelease(player);
                        SoraVanillaSword.SwordAirShoot(player);
                    }
                }
                case 13 -> LifeElementSceptre.Shoot(player);
                case 14 -> WaterElementSceptre.Shoot(player);
                case 15 -> FireElementSceptre.Shoot(player);
            }
        });
        return true;
    }
}

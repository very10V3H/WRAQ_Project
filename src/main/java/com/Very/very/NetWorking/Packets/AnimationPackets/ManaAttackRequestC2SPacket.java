package com.Very.very.NetWorking.Packets.AnimationPackets;

import com.Very.very.Customize.Players.Black_Feisa_.BlackFeisa;
import com.Very.very.Customize.Players.FengXiaoju.FengXiaoJuCurios1;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.Projectile.Mana.ManaArrow;
import com.Very.very.Projectile.Mana.NewArrow;
import com.Very.very.Projectile.Mana.NewArrowMagma;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.Series.InstanceSeries.Castle.CastleManaArmor;
import com.Very.very.Series.NetherSeries.Equip.MagmaSceptre.MagmaSceptre;
import com.Very.very.Series.NetherSeries.Equip.MagmaSceptre.MagmaSceptreAttributes;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sceptre.PlainSceptre0;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Sceptre.PlainSceptre4;
import com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.EvokerSword;
import com.Very.very.Series.WorldSoulSeries.SoulSceptre;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.ModEntityType;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModSounds;
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
            FengXiaoJuCurios1.ExAttackCount(player);
            if (Compute.ManaSkillLevelGet(player.getPersistentData(), 10) == 10) CastleManaArmor.NormalAttack(player); //
            Utils.PlayerManaAttackTime.put(serverPlayer,tick);
            switch (count) {
                case 0 -> {
                    if (Compute.ManaSkillLevelGet(data,10) > 0 || Compute.PlayerManaCost(player,PlainSceptre0.ManaCost)) {
                        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_PLAIN.get(), player,level,Compute.PlayerAttributes.PlayerManaDamage(player),Compute.PlayerAttributes.PlayerManaPenetration(player),Compute.PlayerAttributes.PlayerManaPenetration0(player),1);
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
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_PLAIN.get(), player, level, Compute.PlayerAttributes.PlayerManaDamage(player), Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),1);
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
                            NewArrow newArrow = new NewArrow(player, level, Compute.PlayerAttributes.PlayerManaDamage(player), Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerExpUp(player), false, Compute.PlayerAttributes.PlayerManaPenetration0(player));
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
                                    Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerExpUp(player), Compute.PlayerAttributes.PlayerManaPenetration0(player));
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
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player, level, Compute.PlayerAttributes.PlayerManaDamage(player), Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),3);
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
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player, level, Compute.PlayerAttributes.PlayerManaDamage(player), Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),3);
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
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player, level, Compute.PlayerAttributes.PlayerManaDamage(player), Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),3);
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
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_NETHER.get(), player, level, Compute.PlayerAttributes.PlayerManaDamage(player), Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),4);
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
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SEA.get(), player, level, Compute.PlayerAttributes.PlayerManaDamage(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),5);
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
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_WORLD.get(), player, level, Compute.PlayerAttributes.PlayerManaDamage(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),3,true);
                            newArrow.setSilent(true);
                            newArrow.setNoGravity(true);
                            
                            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 3, 1.0f);
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
                            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW.get(), player, level, Compute.PlayerAttributes.PlayerManaDamage(player),
                                    Compute.PlayerAttributes.PlayerManaPenetration(player), Compute.PlayerAttributes.PlayerManaPenetration0(player),0);
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
                        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player,level,Compute.PlayerAttributes.PlayerManaDamage(player),Compute.PlayerAttributes.PlayerManaPenetration(player),Compute.PlayerAttributes.PlayerManaPenetration0(player),1);
                        newArrow.setSilent(true);
                        newArrow.setNoGravity(true);

                        newArrow.shootFromRotation(player,player.getXRot(),player.getYRot(),0,3,1);
                        ProjectileUtil.rotateTowardsMovement(newArrow,0);
                        level.addFreshEntity(newArrow);
                        ParticleProvider.FaceCircleCreate((ServerPlayer) player,1,0.75,20, ParticleTypes.FIREWORK);
                        ParticleProvider.FaceCircleCreate((ServerPlayer) player,1.5,0.5,16, ParticleTypes.FIREWORK);
                    }
                }
            }
        });
        return true;
    }
}

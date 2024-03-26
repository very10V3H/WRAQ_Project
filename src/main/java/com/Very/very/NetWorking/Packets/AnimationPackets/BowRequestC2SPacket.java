package com.Very.very.NetWorking.Packets.AnimationPackets;

import com.Very.very.CoreAttackModule.MyArrow;
import com.Very.very.Customize.Players.Qi_Fu.QiFuCurios;
import com.Very.very.Customize.Players.Qi_Fu.QiFuCurios1;
import com.Very.very.Customize.Players.Wcndymlgb.Wcndymlgb;
import com.Very.very.Customize.Players.Yxwg.YxwgBow;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.Series.InstanceSeries.Castle.CastleSwiftArmor;
import com.Very.very.Series.InstanceSeries.Moon.Equip.MoonBow;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BowRequestC2SPacket {
    private final int count;

    public BowRequestC2SPacket(int count) {
        this.count = count;
    }

    public BowRequestC2SPacket(FriendlyByteBuf buf) {
        this.count = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.count);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            int tick = serverPlayer.getServer().getTickCount();
            if (Utils.PlayerArrowAttackTime.containsKey(serverPlayer) && tick - Utils.PlayerArrowAttackTime.get(serverPlayer) < 9) return;
            CastleSwiftArmor.NormalAttack(serverPlayer);
            Utils.PlayerArrowAttackTime.put(serverPlayer,tick);
            double damage = Compute.PlayerAttributes.PlayerAttackDamage(serverPlayer);
            double CriticalHitRate = Compute.PlayerAttributes.PlayerCritRate(serverPlayer);
            double CHitDamage = Compute.PlayerAttributes.PlayerCritDamage(serverPlayer);
            double BreakDefence = Compute.PlayerAttributes.PlayerDefencePenetration(serverPlayer);
            double ExpUp = Compute.PlayerExpUp(serverPlayer);
            double BreakDefence0 = Compute.PlayerAttributes.PlayerDefencePenetration0(serverPlayer);
            switch (count) {
                default -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 3F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WAX_OFF);
                }
                case 3 -> {
                    if (Compute.PlayerCurrentManaNum(serverPlayer) > 40) {
                        Compute.PlayerManaAddOrCost(serverPlayer, -40);
                        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                                serverPlayer, damage, true);
                        
                        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
                        arrow.setCritArrow(true);
                        arrow.setNoGravity(true);
                        serverPlayer.level().addFreshEntity(arrow);
                        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WITCH);
                    } else {
                        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                                serverPlayer, damage, true);
                        
                        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 3F, 1.0f);
                        arrow.setCritArrow(true);
                        serverPlayer.level().addFreshEntity(arrow);
                        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WAX_OFF);
                    }
                }
                case 6 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.WORLD.get());
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.WORLD.get());
                    ParticleProvider.FaceCircleCreate(serverPlayer, 2, 0.25, 12, ModParticles.WORLD.get());
                }
                case 7 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.CHERRY_LEAVES);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.CHERRY_LEAVES);
                }
                case 8 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.BLACKFOREST_RECALL.get());
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.LONG_SEA.get());
                }
                case 10 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.SNOWFLAKE);
                }
                case 11 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.DRIPPING_WATER);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.DRIPPING_WATER);
                }
                case 12 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.SNOWFLAKE);
                }
                case 13 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WAX_OFF);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.WAX_OFF);
                    Utils.GuangYiSecondArrowDelay = 3;
                }
                case 14 -> {
                    if (Utils.WcBowStatus) {
                        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                                serverPlayer, damage, true,true,ParticleTypes.COMPOSTER);
                        
                        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 1.5f, 1.0f);
                        arrow.setCritArrow(true);
                        arrow.setNoGravity(true);
                        serverPlayer.level().addFreshEntity(arrow);
                        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.COMPOSTER);
                        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);
                        Wcndymlgb.WcBowAttackTick = 6;
                    }
                    else {
                        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                                serverPlayer, damage, true);
                        
                        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5f, 1.0f);
                        arrow.setCritArrow(true);
                        serverPlayer.level().addFreshEntity(arrow);
                        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.COMPOSTER);
                        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);

                    }
                }
                case 15 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
                    MoonBow.MoonBowExShoot(serverPlayer);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.FIREWORK);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.FIREWORK);
                }
                case 16 -> {
                    double rate = 1;
                    if (YxwgBow.ActiveFlag) {
                        if (YxwgBow.ActiveEffect(serverPlayer)) rate = 4;
                    }
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage * rate, true, YxwgBow.IsManaArrow(serverPlayer),ParticleTypes.COMPOSTER);

                    YxwgBow.BattleTick(serverPlayer);
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, YxwgBow.IsManaArrow(serverPlayer) ? 1.5f : 4.5f, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.COMPOSTER);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);

                }
                case 17 -> {

                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage, true, true,ParticleTypes.COMPOSTER);

                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 1.5f, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.COMPOSTER);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);

                }

                case 18 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer, serverPlayer.level(),
                            serverPlayer, damage, true, false,ParticleTypes.COMPOSTER);
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5f, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.COMPOSTER);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.COMPOSTER);
                }

                case 19 -> {
                    QiFuCurios.Shoot(serverPlayer,true,false);
                    QiFuCurios1.Passive(serverPlayer);
                }
            }
        });
        return true;
    }
}

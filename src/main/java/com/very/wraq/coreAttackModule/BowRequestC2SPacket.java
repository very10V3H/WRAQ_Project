package com.very.wraq.coreAttackModule;

import com.very.wraq.customized.players.bow.Hgj.HgjCurios;
import com.very.wraq.customized.players.bow.Qi_Fu.QiFuCurios;
import com.very.wraq.customized.players.bow.Qi_Fu.QiFuCurios1;
import com.very.wraq.customized.players.bow.Wcndymlgb.Wcndymlgb;
import com.very.wraq.customized.players.bow.Yxwg.YxwgBow;
import com.very.wraq.customized.players.sword.ZuoSI.ZuoSiCurios;
import com.very.wraq.process.element.equipAndCurios.fireElement.FireElementBow;
import com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementBow;
import com.very.wraq.process.element.equipAndCurios.waterElement.WaterElementBow;
import com.very.wraq.process.Particle.ParticleProvider;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.series.instance.Castle.CastleSwiftArmor;
import com.very.wraq.series.instance.Moon.Equip.MoonBow;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
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
            if (ZuoSiCurios.IsPlayer(serverPlayer)) return;
            HgjCurios.PlayerArrowAttack(serverPlayer);
            double damage = Compute.PlayerAttributes.PlayerAttackDamage(serverPlayer);
            switch (count) {
                default -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, damage, true);
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 3F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WAX_OFF);
                }
                case 3 -> {
                    if (Compute.PlayerCurrentManaNum(serverPlayer) > 40) {
                        Compute.PlayerManaAddOrCost(serverPlayer, -40);
                        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, damage, true);
                        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
                        arrow.setCritArrow(true);
                        arrow.setNoGravity(true);
                        serverPlayer.level().addFreshEntity(arrow);
                        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WITCH);
                    } else {
                        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, damage, true);
                        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 3F, 1.0f);
                        arrow.setCritArrow(true);
                        serverPlayer.level().addFreshEntity(arrow);
                        Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.WAX_OFF);
                    }
                }
                case 6 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, damage, true);
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.WORLD.get());
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.WORLD.get());
                    ParticleProvider.FaceCircleCreate(serverPlayer, 2, 0.25, 12, ModParticles.WORLD.get());
                }
                case 7 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, damage, true);
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.CHERRY_LEAVES);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.CHERRY_LEAVES);
                }
                case 8 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.BLACKFOREST_RECALL.get());
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.LONG_SEA.get());
                }
                case 10 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.SNOWFLAKE);
                }
                case 11 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.DRIPPING_WATER);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.DRIPPING_WATER);
                }
                case 12 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, damage, true);
                    
                    arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5F, 1.0f);
                    arrow.setCritArrow(true);
                    serverPlayer.level().addFreshEntity(arrow);
                    Compute.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ParticleTypes.SNOWFLAKE);
                    ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ParticleTypes.SNOWFLAKE);
                }
                case 13 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, damage, true);
                    
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
                        Compute.TraceArrowShoot(serverPlayer,ParticleTypes.COMPOSTER);
                    }
                }
                case 15 -> {
                    MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, damage, true);
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
                case 20 -> LifeElementBow.Shoot(serverPlayer);
                case 21 -> WaterElementBow.Shoot(serverPlayer);
                case 22 -> FireElementBow.Shoot(serverPlayer);
            }

        });
        return true;
    }
}

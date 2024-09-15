package com.very.wraq.projectiles.mana;

import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.MobAttributes;
import com.very.wraq.common.attribute.PlayerAttributes;
import com.very.wraq.common.registry.ModEntityType;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.util.struct.Power;
import com.very.wraq.core.ManaAttackModule;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.series.overworld.chapter2.codeMana.CodeSceptre;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import static java.lang.Math.*;

public class NewArrow extends AbstractArrow {
    public double BaseDamage;
    public double BreakDefence;
    public double BreakDefence0;
    public double ExpUp;
    public Player player;
    public double PositionX;
    public double PositionY;
    public double PositionZ;
    public boolean flag;
    public boolean AdjustOneTimes = true;
    public Vec3 InWaterVec3;
    public boolean mainShoot = true;

    public NewArrow(EntityType<? extends AbstractArrow> p_36721_, Level p_36722_) {
        super(p_36721_, p_36722_);
    }

    public NewArrow(LivingEntity shooter, Level level) {
        super(ModEntityType.NEW_ARROW.get(), shooter, level);
    }

    public NewArrow(LivingEntity shooter, Level level, double BaseDamage, double BreakDefence, double ExpUp,
                    boolean flag, double BreakDefence0) {
        super(ModEntityType.NEW_ARROW.get(), shooter, level);
        this.player = (Player) shooter;
        this.BaseDamage = BaseDamage;
        this.BreakDefence = BreakDefence;
        this.ExpUp = ExpUp;
        this.flag = flag;
        this.BreakDefence0 = BreakDefence0;
    }

    public NewArrow(LivingEntity shooter, Level level, double BaseDamage, double BreakDefence, double ExpUp,
                    boolean flag, double BreakDefence0, boolean mainShoot) {
        super(ModEntityType.NEW_ARROW.get(), shooter, level);
        this.player = (Player) shooter;
        this.BaseDamage = BaseDamage;
        this.BreakDefence = BreakDefence;
        this.ExpUp = ExpUp;
        this.flag = flag;
        this.BreakDefence0 = BreakDefence0;
        this.mainShoot = mainShoot;
    }

    @Override
    public ItemStack getPickupItem() {
        return MinecartItem.byId(0).getDefaultInstance();
    }

    @Override
    protected void onHitBlock(BlockHitResult p_36755_) {
        super.onHitBlock(p_36755_);
        if (this.player != null && !this.level().isClientSide) {
            CompoundTag data = player.getPersistentData();
            if (data.contains("snowRune") && data.getInt("snowRune") == 0) {
                data.putInt("snowRune0", 0);
                data.putInt("snowRune0Time", 0);
            }
            ManaAttackModule.ManaSkill6Attack(data, player, false);
        }
        this.remove(RemovalReason.KILLED);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
/*        if(this.player != null && !flag && !level().isClientSide) {
            Entity entity = result.getEntity();
            ManaAttackModule.BasicAttack(player,entity,BaseDamage,BreakDefence,BreakDefence0,level(),this);
            ModNetworking.sendToClient(new ManaAttackParticleS2CPacket(entity.getX(),entity.getY(),entity.getZ(),0),(ServerPlayer) player);
        }*/
        if (this.player != null && flag && Utils.PowerMap.containsKey((ServerPlayer) player)) {
            Power power = Utils.PowerMap.get((ServerPlayer) player);
            int Effect = power.get1Count() + 1;
            int Range = power.get2Count();
            int Damage = power.get3Count();
            int Break = power.get4Count();
            int Kaze = power.get5Count();
            int Snow = power.get6Count();
            int Lightning = power.get7Count();
            int Gather = power.get8Count();
            Entity entity = result.getEntity();
            CompoundTag data = player.getPersistentData();
            if (data.contains("snowRune") && data.getInt("snowRune") == 0) {
                if (!data.contains("snowRune0") || data.getInt("snowRune0") == 0) {
                    data.putInt("snowRune0", 1);
                    data.putInt("snowRune0Time", 100);
                } else {
                    if (data.getInt("snowRune0") < 5) {
                        data.putInt("snowRune0", data.getInt("snowRune0") + 1);
                        data.putInt("snowRune0Time", 100);
                    } else {
                        data.putInt("snowRune0Time", 100);
                    }
                }
            }
            if (entity instanceof Mob && !(entity instanceof Villager)) {
                double damage;
                Mob monster = (Mob) entity;
                double Defence = MobAttributes.manaDefence(monster);
                if (Break > 0) BreakDefence = (1 - (1 - BreakDefence) * (1 - 0.15f * Break));
                if (data.contains("ManaRune") && data.getInt("ManaRune") == 2 && data.contains("ManaRune2") && data.getInt("ManaRune2") == 0) {
                    data.putInt("ManaRune2", 200);
                    monster.getPersistentData().putInt("ManaRune2", 60);
                    Utils.MonsterAttributeDataProvider.add(monster);
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level());
                    lightningBolt.setCause((ServerPlayer) player);
                    lightningBolt.setDamage(0);
                    lightningBolt.setVisualOnly(true);
                    lightningBolt.moveTo(monster.position());
                    lightningBolt.setSilent(true);
                    level().addFreshEntity(lightningBolt);
                    if (Defence == 0) {
                        damage = BaseDamage * (1.0d - (0.25F * log(((monster.getAttribute(Attributes.ARMOR).getValue() * (1.0d - BreakDefence) * (E * E / 100) + 1)))));
                        damage *= (1 + Damage * Effect);
                        if (Range >= 0)
                            CodeSceptre.hitMonster(level(), monster, Range, (ServerPlayer) player, damage, Kaze, Effect, Snow, Lightning, Gather);
                        monster.hurt(monster.damageSources().playerAttack(player), (float) damage * 3);

                    } else {
                        damage = BaseDamage * (1.0d - (0.25F * log(((Defence) * (1.0d - BreakDefence)) * (E * E / 100) + 1)));
                        damage *= (1 + Damage * Effect);
                        if (Range >= 0)
                            CodeSceptre.hitMonster(level(), monster, Range, (ServerPlayer) player, damage, Kaze, Effect, Snow, Lightning, Gather);
                        monster.hurt(monster.damageSources().playerAttack(player), (float) damage * 3);
                    }
                } else {
                    if (Defence == 0) {
                        damage = BaseDamage * (1.0d - (0.25F * log(((monster.getAttribute(Attributes.ARMOR).getValue() * (1.0d - BreakDefence) * (E * E / 100) + 1)))));
                        damage *= (1 + Damage * Effect);
                        if (Range >= 0)
                            CodeSceptre.hitMonster(level(), monster, Range, (ServerPlayer) player, damage, Kaze, Effect, Snow, Lightning, Gather);
                        monster.hurt(monster.damageSources().playerAttack(player), (float) damage);
                    } else {
                        damage = BaseDamage * (1.0d - (0.25F * log(((Defence) * (1.0d - BreakDefence)) * (E * E / 100) + 1)));
                        damage *= (1 + Damage * Effect);
                        if (Range >= 0)
                            CodeSceptre.hitMonster(level(), monster, Range, (ServerPlayer) player, damage, Kaze, Effect, Snow, Lightning, Gather);
                        monster.hurt(monster.damageSources().playerAttack(player), (float) damage);
                    }
                }
                if (data.contains("MagmaPower") && data.getBoolean("MagmaPower")) {
                    Compute.MagmaPower(monster, level(), player);
                    data.putBoolean("MagmaPower", false);
                }
            }
            if (entity instanceof Player) {
                double damage;
                Player hurter = (Player) entity;
                double Defence = PlayerAttributes.manaDefence(hurter);
                if (Defence == 0) {
                    damage = BaseDamage * (1.0d - (0.25F * log(((hurter.getAttribute(Attributes.ARMOR).getValue() * (1.0d - BreakDefence) * (E * E / 100) + 1)))));
                    if (Range >= 0)
                        CodeSceptre.hitPlayer(level(), hurter, Range, (ServerPlayer) player, damage, Kaze, Effect, Snow, Lightning, Gather);
                    hurter.hurt(hurter.damageSources().playerAttack(player), (float) damage * 0.1f);
                } else {
                    damage = BaseDamage * (1.0d - (0.25F * log(((Defence) * (1.0d - BreakDefence)) * (E * E / 100) + 1)));
                    if (Range >= 0)
                        CodeSceptre.hitPlayer(level(), hurter, Range, (ServerPlayer) player, damage, Kaze, Effect, Snow, Lightning, Gather);
                    hurter.hurt(hurter.damageSources().playerAttack(player), (float) damage * 0.1f);
                }
                if (data.contains("MagmaPower") && data.getBoolean("MagmaPower")) {
                    Compute.MagmaPower(hurter, level(), player);
                    data.putBoolean("MagmaPower", false);
                }
            }
        }
        this.discard();
    }

    @Override
    public void tick() {
/*        if (!this.level().isClientSide && player != null) {
            List<Entity> list = this.level().getEntitiesOfClass(Entity.class,this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D));
            if (list.size() > 0 && list.get(0) instanceof Mob) {
                Vec3 vec = this.getDeltaMovement().normalize();
                double distance = 100;
                Mob nearestMob = null;
                for (int i = 0 ; i < 20 ; i ++) {
                    Vec3 pos = this.position().add(vec.scale(0.25 * i));
                    List<Mob> mobList = this.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos,1.5,1.5,1.5));
                    for (Mob mob : mobList) {
                        if (mob.getEyePosition().distanceTo(pos) < distance) {
                            distance = mob.getEyePosition().distanceTo(pos);
                            nearestMob = mob;
                        }
                    }
                }
                if (nearestMob != null) {
                    ManaAttackModule.BasicAttack(player,nearestMob,BaseDamage,BreakDefence,BreakDefence0,level(),this);
                    this.remove(RemovalReason.KILLED);
                }
            }
        }*/
        super.tick();
/*        if (!this.level().isClientSide && this.tickCount > 8) {
            if (AdjustOneTimes) {
                List<Mob> mobList = this.level().getEntitiesOfClass(Mob.class, AABB.ofSize(this.getPosition(1),20,20,20));
                if (mobList.size() > 0) {
                    Mob mob = null;
                    double length = 30;
                    for (Mob mob1 : mobList) {
                        if (mob1.isAlive() && mob1.position().distanceTo(this.position()) < length) {
                            mob = mob1;
                            length = mob1.position().distanceTo(this.position());
                        }
                    }
                    if (mob != null) {
                        Vec3 Delta = mob.getPosition(1).add(0,1,0).subtract(this.getPosition(1));
                        Delta.normalize();
                        if (Delta.length() > 0.1) {
                            this.setDeltaMovement(Delta.scale(0.2));
                            this.InWaterVec3 = Delta.scale(0.2);
                        }
                        AdjustOneTimes = false;
                    }
                }
            }
        }*/
        boolean flag1 = true;
        if ((this.tickCount >= 100) && player != null) {
            if (!this.level().isClientSide) {
                CompoundTag data = player.getPersistentData();
                ManaAttackModule.ManaSkill6Attack(data, player, false);
            }
            this.remove(RemovalReason.KILLED);
            flag1 = false;
        }
        if (this.getDeltaMovement().length() <= 0.05) {
            if (this.isInWater()) {
                if (this.InWaterVec3 == null) {
                    Vec3 Delta = this.getDeltaMovement();
                    this.InWaterVec3 = Delta.normalize().scale(1);
                }
                this.setDeltaMovement(this.InWaterVec3);
            } else this.remove(RemovalReason.KILLED);
            flag1 = false;
        }
        if (flag1) {
            if (abs(this.getDeltaMovement().x) + abs(this.getDeltaMovement().y) + abs(this.getDeltaMovement().z) <= 0.05)
                this.remove(RemovalReason.KILLED);
            double DX = this.getX() - this.PositionX;
            double DY = this.getY() - this.PositionY;
            double DZ = this.getZ() - this.PositionZ;
            if (!level().isClientSide) {
                if (flag) {
                    Power power = Utils.PowerMap.get((ServerPlayer) player);
                    int Effect = power.get1Count();
                    int Range = power.get2Count();
                    int Damage = power.get3Count();
                    int Break = power.get4Count();
                    int Kaze = power.get5Count();
                    int Snow = power.get6Count();
                    int Lightning = power.get7Count();
                    int Gather = power.get8Count();
                    int Count = 0;
                    while (Effect > 0) {
                        ParticleProvider.EntityFaceConnectCircleCreate(this, this.getDeltaMovement(), new Vec3(this.PositionX, this.PositionY, this.PositionZ), 0, 0.5, 80,
                                ModParticles.EFFECT_MANA.get(), DX, DY, DZ, Count * 0.5);
                        Effect--;
                        Count++;
                    }
                    while (Range > 0) {
                        ParticleProvider.EntityFaceConnectCircleCreate(this, this.getDeltaMovement(), new Vec3(this.PositionX, this.PositionY, this.PositionZ), 0, 0.5, 80,
                                ModParticles.RANGE_MANA.get(), DX, DY, DZ, Count * 0.5);
                        Range--;
                        Count++;
                    }
                    while (Damage > 0) {
                        ParticleProvider.EntityFaceConnectCircleCreate(this, this.getDeltaMovement(), new Vec3(this.PositionX, this.PositionY, this.PositionZ), 0, 0.5, 80,
                                ModParticles.DAMAGE_MANA.get(), DX, DY, DZ, Count * 0.5);
                        Damage--;
                        Count++;
                    }
                    while (Break > 0) {
                        ParticleProvider.EntityFaceConnectCircleCreate(this, this.getDeltaMovement(), new Vec3(this.PositionX, this.PositionY, this.PositionZ), 0, 0.5, 80,
                                ModParticles.BREAKDefence_MANA.get(), DX, DY, DZ, Count * 0.5);
                        Break--;
                        Count++;
                    }
                    while (Kaze > 0) {
                        ParticleProvider.EntityFaceConnectCircleCreate(this, this.getDeltaMovement(), new Vec3(this.PositionX, this.PositionY, this.PositionZ), 0, 0.5, 80,
                                ModParticles.KAZE_MANA.get(), DX, DY, DZ, Count * 0.5);
                        Kaze--;
                        Count++;
                    }
                    while (Snow > 0) {
                        ParticleProvider.EntityFaceConnectCircleCreate(this, this.getDeltaMovement(), new Vec3(this.PositionX, this.PositionY, this.PositionZ), 0, 0.5, 80,
                                ModParticles.SNOW_MANA.get(), DX, DY, DZ, Count * 0.5);
                        Snow--;
                        Count++;
                    }
                    while (Lightning > 0) {
                        ParticleProvider.EntityFaceConnectCircleCreate(this, this.getDeltaMovement(), new Vec3(this.PositionX, this.PositionY, this.PositionZ), 0, 0.5, 80,
                                ModParticles.LIGHTNING_MANA.get(), DX, DY, DZ, Count * 0.5);
                        Lightning--;
                        Count++;
                    }
                    while (Gather > 0) {
                        ParticleProvider.EntityFaceConnectCircleCreate(this, this.getDeltaMovement(), new Vec3(this.PositionX, this.PositionY, this.PositionZ), 0, 0.5, 80,
                                ModParticles.GATHER_MANA.get(), DX, DY, DZ, Count * 0.5);
                        Gather--;
                        Count++;
                    }
                    for (double i = 0.1; i <= 1; i += 0.1) {
                        ParticleProvider.ParticleWITCH(this.PositionX + i * DX, this.PositionY + i * DY, this.PositionZ + i * DZ, level(), 0.2, ModParticles.EFFECT_MANA.get());
                    }
                } else {
/*                    Compute.ParticleWITCH(this.PositionX,this.PositionY,this.PositionZ,level(),0.2,ParticleTypes.WITCH);
                    for(double i = 0.1 ;i <= 1 ;i += 0.1)
                    {
                        Compute.ParticleWITCH(this.PositionX+i*DX,this.PositionY+i*DY,this.PositionZ+i*DZ,level(),0.2,ParticleTypes.WITCH);
                    }*/
                }
            }
            this.PositionX = this.getX();
            this.PositionY = this.getY();
            this.PositionZ = this.getZ();
        }
        if (!this.level().isClientSide && this.isNoGravity() && player != null && this.distanceTo(player) > 60)
            this.remove(RemovalReason.KILLED);
    }
}

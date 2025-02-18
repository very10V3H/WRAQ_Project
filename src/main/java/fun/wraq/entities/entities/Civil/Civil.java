package fun.wraq.entities.entities.Civil;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.*;

public class Civil extends PathfinderMob implements GeoEntity {

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public Player owner;
    public Civil(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    public Civil(EntityType<? extends PathfinderMob> entityType, Level level, Player player) {
        super(entityType, level);
        owner = player;
    }

    public static AttributeSupplier setAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10000)
                .add(Attributes.ATTACK_DAMAGE, 20)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        super.registerGoals();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("animation.model.normal", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource p_21239_) {
        return SoundEvents.PLAYER_HURT;
    }

    public static Map<Player, List<Civil>> playerSpawnCivilMap = new HashMap<>();
    public static void spawnToTestDps(Player player) {
        Vec3 pos = player.pick(4, 0, false).getLocation();
        if (!player.isCreative() && (pos.x < 1758 || pos.x > 1790 || pos.z < 326 || pos.z > 352 || pos.y < 82 || pos.y > 85)) {
            sendFormatMSG(player, Te.s("只有在指定区域才能生成木桩:"));
            sendFormatMSG(player, Te.s("旭升岛", CustomStyle.styleOfSunIsland,
                    "本源解析装置", CustomStyle.styleOfWorld, "二楼"));
            return;
        }
        sendFormatMSG(player, Te.s("使用/vmd setTietou [参数] [数值]来制定木桩属性"));
        sendParaMSG(player);
        if (!playerSpawnCivilMap.containsKey(player)) {
            playerSpawnCivilMap.put(player, new ArrayList<>());
        }
        if (playerSpawnCivilMap.get(player).size() >= 4) {
            playerSpawnCivilMap.get(player).forEach(civil -> {
                civil.remove(RemovalReason.KILLED);
            });
            playerSpawnCivilMap.get(player).clear();
        }
        Level level = player.level();
        Civil civil = new Civil(ModEntityType.CIVIL.get(), level);
        civil.setCustomName(Te.s(player.getName().getString(), "-木桩-" + playerSpawnCivilMap.get(player).size()));
        civil.moveTo(pos);
        level.addFreshEntity(civil);
        playerSpawnCivilMap.get(player).add(civil);
    }

    public static void handleTick(Player player) {
        if (playerSpawnCivilMap.containsKey(player)) {
            playerSpawnCivilMap.get(player).forEach(civil -> {
                if (civil.distanceTo(player) > 16) {
                    civil.remove(RemovalReason.KILLED);
                }
            });
            playerSpawnCivilMap.get(player).removeIf(civil -> civil.distanceTo(player) > 16);
        }
    }

    public static void onPlayerLogOut(Player player) {
        if (playerSpawnCivilMap.containsKey(player)) {
            playerSpawnCivilMap.get(player).forEach(civil -> {
                civil.remove(RemovalReason.KILLED);
            });
            playerSpawnCivilMap.remove(player);

            for (int i = 0 ; i < 4 ; i ++) {
                String name = player.getName().getString() + "-木桩-" + i;
                MobSpawn.MobBaseAttributes.attackDamage.remove(name);
                MobSpawn.MobBaseAttributes.defence.remove(name);
                MobSpawn.MobBaseAttributes.manaDefence.remove(name);
                MobSpawn.MobBaseAttributes.critRate.remove(name);
                MobSpawn.MobBaseAttributes.critDamage.remove(name);
                MobSpawn.MobBaseAttributes.defencePenetration.remove(name);
                MobSpawn.MobBaseAttributes.defencePenetration0.remove(name);
                MobSpawn.MobBaseAttributes.healthSteal.remove(name);
                MobSpawn.MobBaseAttributes.movementSpeed.remove(name);
            }
        }
    }

    public static final Set<String> attributeTypeSet = Set.of("attackDamage",
            "defence", "manaDefence", "maxHealth", "xpLevel");
    public static void setCivilAttributes(Player player, String attributeType, double value) {
        if (!attributeTypeSet.contains(attributeType)) {
            sendFormatMSG(player, Te.s("请选择以下参数:"));
            sendParaMSG(player);
            return;
        }
        if (!playerSpawnCivilMap.containsKey(player)) {
            sendFormatMSG(player, Te.s("请先使用/vmd tietou来召唤一个木桩"));
            return;
        }
        Mob mob = Compute.detectPlayerPickMob(player);
        if (mob instanceof Civil) {
            setAttribute(mob, attributeType, value, player);
        } else {
            if (mob == null) {
                playerSpawnCivilMap.get(player).forEach(civil -> {
                    setAttribute(civil, attributeType, value, player);
                });
            }
        }
    }

    public static void sendParaMSG(Player player) {
        sendFormatMSG(player, Te.s("attackDamage - 攻击力", CustomStyle.styleOfAttack));
        sendFormatMSG(player, Te.s("defence - 护甲", CustomStyle.styleOfDefence));
        sendFormatMSG(player, Te.s("manaDefence - 魔抗", CustomStyle.styleOfManaDefence));
        sendFormatMSG(player, Te.s("maxHealth - 最大生命值", CustomStyle.styleOfLife));
        sendFormatMSG(player, Te.s("xpLevel - 等级", CustomStyle.styleOfLucky));
    }

    public static void setAttribute(Mob mob, String attributeType, double value, Player player) {
        String name = MobSpawn.getMobOriginName(mob);
        switch (attributeType) {
            case "attackDamage" -> {
                MobSpawn.MobBaseAttributes.attackDamage.put(name, value);
                sendFormatMSG(player, Te.s("已调整", mob,
                        ComponentUtils.AttributeDescription.attackDamage("")));
            }
            case "defence" -> {
                MobSpawn.MobBaseAttributes.defence.put(name, value);
                sendFormatMSG(player, Te.s("已调整", mob,
                        ComponentUtils.AttributeDescription.defence("")));
            }
            case "manaDefence" -> {
                MobSpawn.MobBaseAttributes.manaDefence.put(name, value);
                sendFormatMSG(player, Te.s("已调整", mob,
                        ComponentUtils.AttributeDescription.manaDefence("")));
            }
            case "maxHealth" -> {
                mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(value);
                mob.setHealth(mob.getMaxHealth());
                sendFormatMSG(player, Te.s("已调整", mob,
                        ComponentUtils.AttributeDescription.maxHealth("")));
            }
            case "xpLevel" -> {
                MobSpawn.MobBaseAttributes.xpLevel.put(name, Math.min(260, (int) value));
                sendFormatMSG(player, Te.s("已调整", mob, "等级", CustomStyle.styleOfLucky));
            }
        }
    }

    public static void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("木桩", CustomStyle.styleOfSky), content);
    }
}

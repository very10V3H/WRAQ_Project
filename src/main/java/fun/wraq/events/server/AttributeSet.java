package fun.wraq.events.server;

import com.mojang.datafixers.util.Pair;
import com.simibubi.create.content.trains.entity.CarriageContraptionEntity;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.hud.SwiftData;
import fun.wraq.series.newrunes.chapter1.LakeNewRune;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttributeSet {
    @SubscribeEvent
    public static void SpeedSet(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            ServerPlayer serverPlayer = (ServerPlayer) event.player;
            Player player = event.player;
            String name = player.getName().getString();
            CompoundTag data = player.getPersistentData();

            ThreadPools.attributeExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    // 移动速度
                    double speedUp = PlayerAttributes.movementSpeedCurrent(player);
                    // 手动调整
                    if (data.contains(StringUtils.MovementSpeedRate)) {
                        speedUp *= data.getInt(StringUtils.MovementSpeedRate) * 0.01;
                    }
                    double finalMovementSpeed = (0.1 + 0.1 * (float) speedUp) * SpecialEffectOnPlayer.slowdownEffectValue(player);
                    if (SpecialEffectOnPlayer.inImprison(player)) {
                        finalMovementSpeed = 0;
                        Vec3 pos = SpecialEffectOnPlayer.imprisonPosMap.get(name);
                        if (SpecialEffectOnPlayer.inVertigo(player)) {
                            Pair<Float, Float> rot = SpecialEffectOnPlayer.imprisonRotMap.get(name);
                            serverPlayer.teleportTo(serverPlayer.serverLevel(), pos.x, pos.y, pos.z, rot.getSecond(), rot.getFirst());
                        } else {
                            serverPlayer.teleportTo(pos.x, pos.y, pos.z);
                        }
                    }
                    player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(finalMovementSpeed);

                    // 游泳速度
                    double exSwimSpeed = 0;
                    exSwimSpeed += LakeNewRune.exSwimSpeed(player);
                    exSwimSpeed += 2;
                    player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(1 + exSwimSpeed);

                    // 最大生命值、攻击距离修改。
                    double MaxHealth = PlayerAttributes.maxHealth(player);
                    player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.max(1, MaxHealth));

                    if (player.getHealth() > player.getMaxHealth()) player.setHealth(player.getMaxHealth());

                    // 攻击距离 过时
                    double RangeUp = PlayerAttributes.attackRangeUp(player);
                    player.getAttribute(ForgeMod.ENTITY_REACH.get()).setBaseValue(3 + RangeUp);

                    // 敏捷
                    if (data.contains(StringUtils.Swift) && data.contains(StringUtils.MaxSwift)) {
                        SwiftData.changePlayerSwift(player, (5 + Math.min(5, PlayerAttributes.extraSwiftness(player))) / 20);
                    }

                    // 重力调整 - 用于近战击杀恼鬼场景
                    if (Compute.inLowGravityEnvironment(player) && player.level().dimension().equals(Level.OVERWORLD)) {
                        player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.01);
                    } else {
                        player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.08);
                    }

                    if (Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(ModItems.PlainRing.get())) {
                        if (!Compute.getNearEntity(player, CarriageContraptionEntity.class, 16).isEmpty()) {
                            player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0);
                        } else {
                            player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).setBaseValue(0.5);
                        }
                    }
                }
            });

            // 生命回复与最大法力值
            if (!player.isDeadOrDying()) {
                double HealthRecover = PlayerAttributes.healthRecover(player);
                Compute.playerHeal(player, HealthRecover / 20);

                double ManaUp = PlayerAttributes.maxMana(player);
                data.putDouble("MAXMANA", ManaUp);
            }

            // 最大法力值 与 法力回
            if (data.contains("MANA") && data.contains("MAXMANA")) {
                double manaRecover = 10 + PlayerAttributes.manaRecover(player);
                Mana.addOrCostPlayerMana(player, manaRecover / 20);
            }
        }
    }
}

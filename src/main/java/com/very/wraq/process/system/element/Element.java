package com.very.wraq.process.system.element;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ElementEffectTimeS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.EffectParticle.DefencePenetrationParticleS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.EffectParticle.ManaDefencePenetrationParticleS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.ElementParticle.*;
import com.very.wraq.process.system.element.equipAndCurios.waterElement.WaterElementSword;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.ItemEntityAndResetTime;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Element {
    public static String clientResonanceType;

    public static String Prefix = "Element";

    public static String life = Prefix + "Life";
    public static String water = Prefix + "Water";
    public static String fire = Prefix + "Fire";
    public static String stone = Prefix + "Stone";
    public static String ice = Prefix + "Ice";
    public static String lightning = Prefix + "Lightning";
    public static String wind = Prefix + "Wind";

    public static Map<Item, Double> LifeElementValue = new HashMap<>();
    public static Map<Item, Double> WaterElementValue = new HashMap<>();
    public static Map<Item, Double> FireElementValue = new HashMap<>();
    public static Map<Item, Double> StoneElementValue = new HashMap<>();
    public static Map<Item, Double> IceElementValue = new HashMap<>();
    public static Map<Item, Double> LightningElementValue = new HashMap<>();
    public static Map<Item, Double> WindElementValue = new HashMap<>();

    public static Map<Integer, Integer> lifeElementParticle = new HashMap<>();
    public static Map<Integer, Integer> waterElementParticle = new HashMap<>();
    public static Map<Integer, Integer> fireElementParticle = new HashMap<>();
    public static Map<Integer, Integer> stoneElementParticle = new HashMap<>();
    public static Map<Integer, Integer> iceElementParticle = new HashMap<>();

    public static Map<Integer, Integer> lightningElementParticle = new HashMap<>();
    public static Map<Integer, Integer> windElementParticle = new HashMap<>();

    public record Unit(String type, double value) {
    }

    public static Map<Integer, Unit> entityElementUnit = new HashMap<>();

    public static Map<Player, String> PlayerResonanceType = new HashMap<>();

    public static void ElementProvider(LivingEntity livingEntity, String type, double value) {
        Map<String, Item> map = new HashMap<>() {{
            put(life, ModItems.LifeElement.get());
            put(water, ModItems.WaterElement.get());
            put(fire, ModItems.FireElement.get());
            put(stone, ModItems.StoneElement.get());
            put(ice, ModItems.IceElement.get());
            put(lightning, ModItems.LightningElement.get());
            put(wind, ModItems.WindElement.get());
        }};
        entityElementUnit.put(livingEntity.getId(), new Unit(type, value));
        if (livingEntity instanceof Player player) ElementEffectTimeSend(player,
                map.get(type).getDefaultInstance(), 8888, (int) (value * 100), true);
    }

    public static void ElementProvider(Player player, String type, double value, int lastTick) {
        Map<String, Item> map = new HashMap<>() {{
            put(life, ModItems.LifeElement.get());
            put(water, ModItems.WaterElement.get());
            put(fire, ModItems.FireElement.get());
            put(stone, ModItems.StoneElement.get());
            put(ice, ModItems.IceElement.get());
            put(lightning, ModItems.LightningElement.get());
            put(wind, ModItems.WindElement.get());
        }};
        entityElementUnit.put(player.getId(), new Unit(type, value));
        ElementEffectTimeSend(player, map.get(type).getDefaultInstance(), lastTick, (int) (value * 100), false);
    }

    public static double ElementEffectAddToEntity(LivingEntity active, LivingEntity passive, String type, double value, boolean isAd, double damage) {
        if (!entityElementUnit.containsKey(passive.getId())) entityElementUnit.put(passive.getId(), new Unit(life, 0));
        Unit passiveUnit = entityElementUnit.get(passive.getId());

        if (passiveUnit.value == 0 && value == 0) return 1;
        if (passiveUnit.value == 0) {
            entityElementUnit.put(passive.getId(), new Unit(type, value));
            ElementParticleProvider(passive);
            return 1;
        }

        double reactionElementValue = Math.min(passiveUnit.value, value);
/*        double elementValue = 0;
        if (active instanceof Player player) {
            if (type.equals(Life)) {
                elementValue = ElementValue.PlayerLifeElementValue(player);
            }
            if (type.equals(Water)) {
                elementValue = ElementValue.PlayerWaterElementValue(player);
            }
            if (type.equals(Fire)) {
                elementValue = ElementValue.PlayerFireElementValue(player);
            }
            if (type.equals(Stone)) {
                elementValue = ElementValue.PlayerStoneElementValue(player);
            }
            if (type.equals(Ice)) {
                elementValue = ElementValue.PlayerIceElementValue(player);
            }
            if (type.equals(Lightning)) {
                elementValue = ElementValue.PlayerLightningElementValue(player);
            }
            if (type.equals(Wind)) {
                elementValue = ElementValue.PlayerWindElementValue(player);
            }
        }*/

        double strongRate = 0.5;
        double weakRate = -0.5;

        if (passiveUnit.type.equals(type)) {
            if (passiveUnit.value < value) entityElementUnit.put(passive.getId(), new Unit(type, value));
            return 1;
        } else {
            if (passiveUnit.value >= value)
                entityElementUnit.put(passive.getId(), new Unit(passiveUnit.type, passiveUnit.value - value));
            else entityElementUnit.put(passive.getId(), new Unit(type, value - passiveUnit.value));
        }

        if (type.equals(life)) {
            if (passiveUnit.type.equals(water)) {
                SummonReactionTypeItem(passive, Component.literal("滋养").withStyle(CustomStyle.styleOfLife));
                LifeAndWaterReaction(passive, reactionElementValue);
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(fire)) {
                SummonReactionTypeItem(passive, Component.literal("燃料").withStyle(CustomStyle.styleOfFire));
                return 1 + (weakRate * LifeAndFire(reactionElementValue));
            }
            if (passiveUnit.type.equals(stone)) {
                SummonReactionTypeItem(passive, Component.literal("汲取").withStyle(CustomStyle.styleOfLife));
                LifeAndStone(active, reactionElementValue);
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(ice)) {
                SummonReactionTypeItem(passive, Component.literal("霜冻").withStyle(CustomStyle.styleOfLife));
                LifeAndIce(passive, reactionElementValue);
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(lightning)) {
                SummonReactionTypeItem(passive, Component.literal("氮化").withStyle(CustomStyle.styleOfLightning));
                LifeAndLightning(passive, reactionElementValue);
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(wind)) {
                SummonReactionTypeItem(passive, Component.literal("断裂").withStyle(CustomStyle.styleOfWind));
                LifeAndWind(passive, reactionElementValue);
                return 1 + weakRate;
            }
        }
        if (type.equals(water)) {
            WaterElementSword.Passive(active);
            if (passiveUnit.type.equals(life)) {
                SummonReactionTypeItem(passive, Component.literal("滋养").withStyle(CustomStyle.styleOfLife));
                LifeAndWaterReaction(passive, reactionElementValue);
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(fire)) {
                SummonReactionTypeItem(passive, Component.literal("蒸发").withStyle(CustomStyle.styleOfWater));
                return 1 + (WaterAndFire(reactionElementValue) * strongRate);
            }
            if (passiveUnit.type.equals(stone)) {
                SummonReactionTypeItem(passive, Component.literal("泥化").withStyle(CustomStyle.styleOfHusk));
                WaterAndStone(passive, reactionElementValue);
            }
            if (passiveUnit.type.equals(ice)) {
                SummonReactionTypeItem(passive, Component.literal("冰水").withStyle(CustomStyle.styleOfIce));
                WaterAndIce(passive, reactionElementValue);
            }
            if (passiveUnit.type.equals(lightning)) {
                SummonReactionTypeItem(passive, Component.literal("感电").withStyle(CustomStyle.styleOfLightning));
                WaterAndLightning(passive, reactionElementValue);
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(wind)) {
                SummonReactionTypeItem(passive, Component.literal("风浪").withStyle(CustomStyle.styleOfWater));
                WaterAndWind(passive, reactionElementValue);
                return 1 + strongRate;
            }
        }
        if (type.equals(fire)) {
            if (passiveUnit.type.equals(life)) {
                SummonReactionTypeItem(passive, Component.literal("燃料").withStyle(CustomStyle.styleOfPower));
                return 1 + (LifeAndFire(reactionElementValue) * strongRate);
            }
            if (passiveUnit.type.equals(water)) {
                SummonReactionTypeItem(passive, Component.literal("蒸发").withStyle(CustomStyle.styleOfWater));
                return 1 + (WaterAndFire(reactionElementValue) * weakRate);
            }
            if (passiveUnit.type.equals(stone)) {
                SummonReactionTypeItem(passive, Component.literal("灰化").withStyle(CustomStyle.styleOfHusk));
                return 1 + (FireAndStone(reactionElementValue) * weakRate);
            }
            if (passiveUnit.type.equals(ice)) {
                SummonReactionTypeItem(passive, Component.literal("融化").withStyle(CustomStyle.styleOfFire));
                return 1 + (FireAndIce(reactionElementValue) * strongRate);
            }
            if (passiveUnit.type.equals(lightning)) {
                SummonReactionTypeItem(passive, Component.literal("爆裂").withStyle(CustomStyle.styleOfFire));
                FireAndLightning(active, passive, reactionElementValue, damage, isAd);
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(wind)) {
                SummonReactionTypeItem(passive, Component.literal("旺盛").withStyle(CustomStyle.styleOfFire));
                FireAndWind(passive, reactionElementValue);
                return 1 + strongRate;
            }
        }
        if (type.equals(stone)) {
            if (passiveUnit.type.equals(life)) {
                SummonReactionTypeItem(passive, Component.literal("汲取").withStyle(CustomStyle.styleOfLife));
                LifeAndStone(active, reactionElementValue);
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(water)) {
                SummonReactionTypeItem(passive, Component.literal("泥化").withStyle(CustomStyle.styleOfHusk));
                WaterAndStone(passive, reactionElementValue);
            }
            if (passiveUnit.type.equals(fire)) {
                SummonReactionTypeItem(passive, Component.literal("灰化").withStyle(ChatFormatting.GRAY));
                return 1 + (FireAndStone(reactionElementValue) * strongRate);
            }
            if (passiveUnit.type.equals(ice)) {
                SummonReactionTypeItem(passive, Component.literal("冻土").withStyle(CustomStyle.styleOfIce));
                StoneAndIce(passive, reactionElementValue);
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(lightning)) {
                SummonReactionTypeItem(passive, Component.literal("接地").withStyle(CustomStyle.styleOfLightning));
                StoneAndLightning(passive, reactionElementValue);
            }
            if (passiveUnit.type.equals(wind)) {
                SummonReactionTypeItem(passive, Component.literal("风化").withStyle(CustomStyle.styleOfWind));
                StoneAndWind(passive, reactionElementValue);
                return 1 + strongRate;
            }
        }
        if (type.equals(ice)) {
            if (passiveUnit.type.equals(life)) {
                SummonReactionTypeItem(passive, Component.literal("霜冻").withStyle(CustomStyle.styleOfIce));
                LifeAndIce(passive, reactionElementValue);
            }
            if (passiveUnit.type.equals(water)) {
                SummonReactionTypeItem(passive, Component.literal("冰水").withStyle(CustomStyle.styleOfIce));
                WaterAndIce(passive, reactionElementValue);
            }
            if (passiveUnit.type.equals(fire)) {
                SummonReactionTypeItem(passive, Component.literal("融化").withStyle(CustomStyle.styleOfFire));
                return 1 + (FireAndIce(reactionElementValue) * weakRate);
            }
            if (passiveUnit.type.equals(stone)) {
                SummonReactionTypeItem(passive, Component.literal("冻土").withStyle(CustomStyle.styleOfIce));
                StoneAndIce(passive, reactionElementValue);
            }
            if (passiveUnit.type.equals(lightning)) {
                SummonReactionTypeItem(passive, Component.literal("超导").withStyle(CustomStyle.styleOfLightning));
                IceAndLightning(passive, reactionElementValue);
            }
            if (passiveUnit.type.equals(wind)) {
                SummonReactionTypeItem(passive, Component.literal("风寒").withStyle(CustomStyle.styleOfWind));
                IceAndWind(passive, reactionElementValue);
            }
        }
        if (type.equals(lightning)) {
            if (passiveUnit.type.equals(life)) {
                SummonReactionTypeItem(passive, Component.literal("氮化").withStyle(CustomStyle.styleOfLife));
                LifeAndLightning(passive, reactionElementValue);
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(water)) {
                SummonReactionTypeItem(passive, Component.literal("感电").withStyle(CustomStyle.styleOfLightning));
                WaterAndLightning(passive, reactionElementValue);
            }
            if (passiveUnit.type.equals(fire)) {
                SummonReactionTypeItem(passive, Component.literal("爆裂").withStyle(CustomStyle.styleOfFire));
                FireAndLightning(active, passive, reactionElementValue, damage, isAd);
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(stone)) {
                SummonReactionTypeItem(passive, Component.literal("接地").withStyle(CustomStyle.styleOfLightning));
                StoneAndLightning(passive, reactionElementValue);
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(ice)) {
                SummonReactionTypeItem(passive, Component.literal("超导").withStyle(CustomStyle.styleOfLightning));
                IceAndLightning(passive, reactionElementValue);
            }
            if (passiveUnit.type.equals(wind)) {
                SummonReactionTypeItem(passive, Component.literal("灾难").withStyle(CustomStyle.styleOfLightning));
                LightningAndWind(active, passive, reactionElementValue, damage, isAd);
                return 1 + weakRate;
            }
        }
        if (type.equals(wind)) {
            if (passiveUnit.type.equals(life)) {
                SummonReactionTypeItem(passive, Component.literal("断裂").withStyle(CustomStyle.styleOfLife));
                LifeAndWind(passive, reactionElementValue);
                return 1 + strongRate;
            }
            if (passiveUnit.type.equals(water)) {
                SummonReactionTypeItem(passive, Component.literal("风浪").withStyle(CustomStyle.styleOfWater));
                WaterAndWind(passive, reactionElementValue);
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(fire)) {
                SummonReactionTypeItem(passive, Component.literal("旺盛").withStyle(CustomStyle.styleOfFire));
                FireAndWind(passive, reactionElementValue);
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(stone)) {
                SummonReactionTypeItem(passive, Component.literal("风化").withStyle(CustomStyle.styleOfWind));
                StoneAndWind(passive, reactionElementValue);
                return 1 + weakRate;
            }
            if (passiveUnit.type.equals(ice)) {
                SummonReactionTypeItem(passive, Component.literal("风寒").withStyle(CustomStyle.styleOfIce));
                IceAndWind(passive, reactionElementValue);
            }
            if (passiveUnit.type.equals(lightning)) {
                SummonReactionTypeItem(passive, Component.literal("灾难").withStyle(CustomStyle.styleOfLightning));
                LightningAndWind(active, passive, reactionElementValue, damage, isAd);
                return 1 + strongRate;
            }
        }
        ElementParticleProvider(passive);
        return 1;
    }

    // 滋养
    public static void LifeAndWaterReaction(LivingEntity passive, double reactionElementValue) {
        if (passive instanceof Player player)
            Compute.playerHeal(player, player.getMaxHealth() * reactionElementValue * 0.1);
        else passive.heal((float) (passive.getMaxHealth() * reactionElementValue * 0.1));
    }

    // 燃料
    public static double LifeAndFire(double reactionElementValue) {
        return reactionElementValue;
    }

    // 汲取
    public static void LifeAndStone(LivingEntity active, double reactionElementValue) {
        if (active instanceof Player player)
            Compute.playerHeal(player, player.getMaxHealth() * reactionElementValue * 0.1);
        else active.heal((float) (active.getMaxHealth() * reactionElementValue * 0.1));
    }

    // 霜冻
    public static Map<Integer, Integer> lifeAndIceTickMap = new HashMap<>();
    public static Map<Integer, Double> lifeAndIceEffectMap = new HashMap<>();

    public static void LifeAndIce(LivingEntity passive, double reactionElementValue) {
        if (lifeAndIceTickMap.containsKey(passive.getId()) && lifeAndIceTickMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            lifeAndIceTickMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
        } else {
            lifeAndIceTickMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
            lifeAndIceEffectMap.put(passive.getId(), reactionElementValue);
        }
    }

    public static double LifeAndIceWithStandDamageEnhance(LivingEntity passive) {
        if (lifeAndIceTickMap.containsKey(passive.getId()) && lifeAndIceTickMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            return lifeAndIceEffectMap.get(passive.getId());
        }
        return 0;
    }

    // 氮化
    public static Map<Integer, Integer> lifeAndLightningTimesMap = new HashMap<>();
    public static Map<Integer, Double> lifeAndLightningEffectMap = new HashMap<>();

    public static void LifeAndLightning(LivingEntity passive, double reactionElementValue) {
        if (lifeAndLightningTimesMap.containsKey(passive.getId()) && lifeAndLightningTimesMap.get(passive.getId()) > 0) {
            lifeAndLightningTimesMap.put(passive.getId(), 3);
        } else {
            lifeAndLightningTimesMap.put(passive.getId(), 3);
            lifeAndLightningEffectMap.put(passive.getId(), reactionElementValue);
        }
    }

    public static void LifeAndLightningTick(Level level) {
        lifeAndLightningTimesMap.forEach((integer, integer2) -> {
            Entity entity = level.getEntity(integer);
            if (entity instanceof LivingEntity livingEntity) {
                if (integer2 > 0) {
                    if (livingEntity instanceof Player player) {
                        Compute.playerHeal(player, 0.05 * lifeAndLightningEffectMap.get(integer));
                    } else livingEntity.heal((float) (0.05 * lifeAndLightningEffectMap.get(integer)));
                    lifeAndLightningTimesMap.put(integer, integer2 - 1);
                }
            }
        });
        lifeAndLightningTimesMap.entrySet().removeIf(entry -> entry.getValue() == 0);
    }

    // 断裂
    public static Map<Integer, Integer> lifeAndWindTimesMap = new HashMap<>();
    public static Map<Integer, Double> lifeAndWindEffectMap = new HashMap<>();

    public static void LifeAndWind(LivingEntity passive, double reactionElementValue) {
        if (lifeAndWindTimesMap.containsKey(passive.getId()) && lifeAndWindTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            lifeAndWindTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
        } else {
            lifeAndWindTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
            lifeAndWindEffectMap.put(passive.getId(), reactionElementValue);
        }
        List<Player> players = passive.level().getEntitiesOfClass(Player.class, AABB.ofSize(passive.position(), 50, 50, 50));
        players.removeIf(player -> player.distanceTo(passive) > 20);
        players.forEach(player -> ModNetworking.sendToClient(new ManaDefencePenetrationParticleS2CPacket(passive.getId(), 100), (ServerPlayer) player));
        players.forEach(player -> ModNetworking.sendToClient(new DefencePenetrationParticleS2CPacket(passive.getId(), 100), (ServerPlayer) player));
    }

    public static double LifeAndWindDefenceDecrease(LivingEntity passive) {
        if (lifeAndWindTimesMap.containsKey(passive.getId()) && lifeAndWindTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            return lifeAndWindEffectMap.get(passive.getId()) * 0.1;
        }
        return 0;
    }

    // 蒸发
    public static double WaterAndFire(double reactionElementValue) {
        return reactionElementValue;
    }

    // 泥化
    public static Map<Integer, Integer> waterAndStoneTimesMap = new HashMap<>();
    public static Map<Integer, Double> waterAndStoneEffectMap = new HashMap<>();

    public static void WaterAndStone(LivingEntity passive, double reactionElementValue) {
        if (waterAndStoneTimesMap.containsKey(passive.getId()) && waterAndStoneTimesMap.get(passive.getId()) > 0) {
            waterAndStoneTimesMap.put(passive.getId(), 3);
        } else {
            waterAndStoneTimesMap.put(passive.getId(), 3);
            waterAndStoneEffectMap.put(passive.getId(), reactionElementValue);
        }
    }

    public static double WaterAndStoneWithStandDamageDecrease(LivingEntity passive) {
        if (waterAndStoneTimesMap.containsKey(passive.getId()) && waterAndStoneTimesMap.get(passive.getId()) > 0) {
            waterAndStoneTimesMap.put(passive.getId(), waterAndStoneTimesMap.get(passive.getId()) - 1);
            return waterAndStoneEffectMap.get(passive.getId());
        }
        if (waterAndStoneTimesMap.containsKey(passive.getId()) && waterAndStoneTimesMap.get(passive.getId()) == 0) {
            waterAndStoneTimesMap.remove(passive.getId());
            waterAndStoneEffectMap.remove(passive.getId());
        }
        return 0;
    }

    // 冰水
    public static Map<Integer, Integer> waterAndIceTimesMap = new HashMap<>();
    public static Map<Integer, Double> waterAndIceEffectMap = new HashMap<>();

    public static void WaterAndIce(LivingEntity passive, double reactionElementValue) {
        if (waterAndIceTimesMap.containsKey(passive.getId()) && waterAndIceTimesMap.get(passive.getId()) > 0) {
            waterAndIceTimesMap.put(passive.getId(), 3);
        } else {
            waterAndIceTimesMap.put(passive.getId(), 3);
            waterAndIceEffectMap.put(passive.getId(), reactionElementValue);
        }
    }

    public static void WaterAndIceReflection() {
    }

    // 感电
    public static Map<Integer, Integer> waterAndLightningTimesMap = new HashMap<>();
    public static Map<Integer, Double> waterAndLightningEffectMap = new HashMap<>();

    public static void WaterAndLightning(LivingEntity passive, double reactionElementValue) {
        if (waterAndLightningTimesMap.containsKey(passive.getId()) && waterAndLightningTimesMap.get(passive.getId()) > 0) {
            waterAndLightningTimesMap.put(passive.getId(), 3);
        } else {
            waterAndLightningTimesMap.put(passive.getId(), 3);
            waterAndLightningEffectMap.put(passive.getId(), reactionElementValue);
        }
    }

    public static void WaterAndLightning(Level level) {
        waterAndLightningTimesMap.forEach((integer, integer2) -> {
            Entity entity = level.getEntity(integer);
            if (entity instanceof Mob mob) {
                if (integer2 > 0) {
                    if (MobSpawn.MobBaseAttributes.xpLevel.containsKey(MobSpawn.getMobOriginName(mob))) {
                        int xpLevel = MobSpawn.MobBaseAttributes.xpLevel.get(MobSpawn.getMobOriginName(mob));
                        mob.setHealth((float) Math.max(1, mob.getHealth() - xpLevel * waterAndLightningEffectMap.get(integer) * 500));
                    }
                    waterAndLightningTimesMap.put(integer, integer2 - 1);
                }
            }
        });
        waterAndLightningTimesMap.entrySet().removeIf(entry -> entry.getValue() == 0);
    }

    // 风浪
    public static void WaterAndWind(LivingEntity passive, double reactionElementValue) {
        passive.addDeltaMovement(new Vec3(0, reactionElementValue * 2, 0));
    }

    // 灰化
    public static double FireAndStone(double reactionElementValue) {
        return reactionElementValue;
    }

    // 融化
    public static double FireAndIce(double reactionElementValue) {
        return reactionElementValue;
    }

    // 爆裂
    public static void FireAndLightning(LivingEntity active, LivingEntity passive, double reactionElementValue, double damage, boolean isAd) {
        List<LivingEntity> list = passive.level().getEntitiesOfClass(LivingEntity.class, AABB.ofSize(passive.position(), 15, 15, 15));
        if (active instanceof Player player) {
            list.forEach(livingEntity -> {
                if (livingEntity instanceof Mob mob) {
                    if (isAd) {
                        Compute.Damage.AttackDamageToMonster_AdDamage(player, mob, damage * reactionElementValue);
                    } else {
                        Compute.Damage.ManaDamageToMonster_ApDamage(player, mob, damage * reactionElementValue);
                    }
                }
            });
        }
    }

    // 旺盛
    public static Map<Integer, Integer> fireAndWindTimesMap = new HashMap<>();
    public static Map<Integer, Double> fireAndWindEffectMap = new HashMap<>();

    public static void FireAndWind(LivingEntity passive, double reactionElementValue) {
        if (fireAndWindTimesMap.containsKey(passive.getId()) && fireAndWindTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            fireAndWindTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
        } else {
            fireAndWindTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
            fireAndWindEffectMap.put(passive.getId(), reactionElementValue);
        }
    }

    public static double FireAndWindDamageEnhance(LivingEntity passive) {
        if (fireAndWindTimesMap.containsKey(passive.getId()) && fireAndWindTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            return fireAndWindEffectMap.get(passive.getId()) * 0.1;
        }
        return 0;
    }

    // 冻土
    public static Map<Integer, Integer> stoneAndIceTimesMap = new HashMap<>();
    public static Map<Integer, Double> stoneAndIceEffectMap = new HashMap<>();

    public static void StoneAndIce(LivingEntity passive, double reactionElementValue) {
        if (stoneAndIceTimesMap.containsKey(passive.getId()) && stoneAndIceTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            stoneAndIceTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
        } else {
            stoneAndIceTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
            stoneAndIceEffectMap.put(passive.getId(), reactionElementValue);
        }
    }

    public static double StoneAndIceWithstandDamageEnhance(LivingEntity passive) {
        if (stoneAndIceTimesMap.containsKey(passive.getId()) && stoneAndIceTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            return stoneAndIceEffectMap.get(passive.getId()) * 0.1;
        }
        return 0;
    }

    // 接地
    public static Map<Integer, Integer> stoneAndLightningTimesMap = new HashMap<>();
    public static Map<Integer, Double> stoneAndLightningEffectMap = new HashMap<>();

    public static void StoneAndLightning(LivingEntity passive, double reactionElementValue) {
        if (stoneAndLightningTimesMap.containsKey(passive.getId()) && stoneAndLightningTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            stoneAndLightningTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
        } else {
            stoneAndLightningTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
            stoneAndLightningEffectMap.put(passive.getId(), reactionElementValue);
        }
    }

    public static double StoneAndLightningDamageEnhance(LivingEntity passive) {
        if (stoneAndLightningTimesMap.containsKey(passive.getId()) && stoneAndLightningTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            return stoneAndLightningEffectMap.get(passive.getId()) * 0.1;
        }
        return 0;
    }

    // 风化
    public static Map<Integer, Integer> stoneAndWindTimesMap = new HashMap<>();
    public static Map<Integer, Double> stoneAndWindEffectMap = new HashMap<>();

    public static void StoneAndWind(LivingEntity passive, double reactionElementValue) {
        if (stoneAndWindTimesMap.containsKey(passive.getId()) && stoneAndWindTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            stoneAndWindTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
        } else {
            stoneAndWindTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
            stoneAndWindEffectMap.put(passive.getId(), reactionElementValue);
        }
    }

    public static double StoneAndWindWithstandDamageEnhance(LivingEntity passive) {
        if (stoneAndWindTimesMap.containsKey(passive.getId()) && stoneAndWindTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            return stoneAndWindEffectMap.get(passive.getId()) * 0.1;
        }
        return 0;
    }

    // 超导
    public static Map<Integer, Integer> iceAndLightningTimesMap = new HashMap<>();
    public static Map<Integer, Double> iceAndLightningEffectMap = new HashMap<>();

    public static void IceAndLightning(LivingEntity passive, double reactionElementValue) {
        if (iceAndLightningTimesMap.containsKey(passive.getId()) && iceAndLightningTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            iceAndLightningTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
        } else {
            iceAndLightningTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
            iceAndLightningEffectMap.put(passive.getId(), reactionElementValue);
        }
        List<Player> players = passive.level().getEntitiesOfClass(Player.class, AABB.ofSize(passive.position(), 50, 50, 50));
        players.removeIf(player -> player.distanceTo(passive) > 20);
        players.forEach(player -> ModNetworking.sendToClient(new ManaDefencePenetrationParticleS2CPacket(passive.getId(), 100), (ServerPlayer) player));
        players.forEach(player -> ModNetworking.sendToClient(new DefencePenetrationParticleS2CPacket(passive.getId(), 100), (ServerPlayer) player));
    }

    public static double IceAndLightningDefenceDecrease(LivingEntity passive) {
        if (iceAndLightningTimesMap.containsKey(passive.getId()) && iceAndLightningTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            return iceAndLightningEffectMap.get(passive.getId()) * 0.1;
        }
        return 0;
    }

    // 风寒
    public static Map<Integer, Integer> iceAndWindTimesMap = new HashMap<>();
    public static Map<Integer, Double> iceAndWindEffectMap = new HashMap<>();

    public static void IceAndWind(LivingEntity passive, double reactionElementValue) {
        if (iceAndWindTimesMap.containsKey(passive.getId()) && iceAndWindTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            iceAndWindTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
        } else {
            iceAndWindTimesMap.put(passive.getId(), passive.getServer().getTickCount() + 100);
            iceAndWindEffectMap.put(passive.getId(), reactionElementValue);
        }
    }

    public static double IceAndWindHealDecrease(LivingEntity passive) {
        if (iceAndWindTimesMap.containsKey(passive.getId()) && iceAndWindTimesMap.get(passive.getId()) > passive.getServer().getTickCount()) {
            return iceAndWindEffectMap.get(passive.getId()) * 0.1;
        }
        return 0;
    }

    // 灾难
    public static void LightningAndWind(LivingEntity active, LivingEntity passive, double reactionElementValue, double damage, boolean isAd) {
        List<LivingEntity> list = passive.level().getEntitiesOfClass(LivingEntity.class, AABB.ofSize(passive.position(), 15, 15, 15));
        if (active instanceof Player player) {
            list.forEach(livingEntity -> {
                if (livingEntity instanceof Mob mob) {
                    if (isAd) {
                        Compute.Damage.AttackDamageToMonster_AdDamage(player, mob, damage * reactionElementValue);
                    } else {
                        Compute.Damage.ManaDamageToMonster_ApDamage(player, mob, damage * reactionElementValue);
                    }
                }
            });
        }
    }

    public static void ClientTick(Level level) {
        ParticleCreate(lifeElementParticle, level, life);
        ParticleCreate(waterElementParticle, level, water);
        ParticleCreate(fireElementParticle, level, fire);
        ParticleCreate(stoneElementParticle, level, stone);
        ParticleCreate(iceElementParticle, level, ice);
        ParticleCreate(lightningElementParticle, level, lightning);
        ParticleCreate(windElementParticle, level, wind);
    }

    public static void ParticleCreate(Map<Integer, Integer> map, Level level, String element) {
        Map<String, ParticleOptions> particleOptionsMap = new HashMap<>() {{
            put(life, ModParticles.LifeElement.get());
            put(water, ModParticles.WaterElement.get());
            put(fire, ModParticles.FireElement.get());
            put(stone, ModParticles.StoneElement.get());
            put(ice, ModParticles.IceElement.get());
            put(lightning, ModParticles.LightningElement.get());
            put(wind, ModParticles.WindElement.get());
        }};
        map.keySet().forEach(integer -> {
            Entity entity = level.getEntity(integer);
            if (entity != null) {
                Vec3 dis = entity.getEyePosition().subtract(Minecraft.getInstance().player.getEyePosition());
                Vec3 vec3 = entity.getEyePosition().add(dis.normalize().scale(-0.75).add(Minecraft.getInstance().player.getHandHoldingItemAngle(ModItems.PlainSword0.get())));
                level.addParticle(particleOptionsMap.get(element), vec3.x, vec3.y, vec3.z, 0, 0, 0);

                if (map.get(integer) > 0) map.put(integer, map.get(integer) - 1);
            }
        });
        map.keySet().removeIf(integer -> map.get(integer) == 0);
    }

    public static void SummonReactionTypeItem(LivingEntity passive, MutableComponent component) {
        Level level = passive.level();
        ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, level);
        itemEntity.setItem(ModItems.Value.get().getDefaultInstance());
        itemEntity.setCustomName(component);
        itemEntity.setCustomNameVisible(true);
        itemEntity.moveTo(passive.getEyePosition().add(0, 0.2, 0));
        itemEntity.setNoGravity(true);
        itemEntity.setPickUpDelay(200);
        itemEntity.setDeltaMovement(new Vec3(0, 0.1, 0));
        Utils.valueItemEntity.add(new ItemEntityAndResetTime(itemEntity, level.getServer().getTickCount() + 12));
        level.addFreshEntity(itemEntity);
    }

    public static void ElementParticleProvider(LivingEntity passive) {
        Unit unit = entityElementUnit.getOrDefault(passive.getId(), new Unit(life, 0));
        List<Player> players = passive.level().getEntitiesOfClass(Player.class, AABB.ofSize(passive.position(), 50, 50, 50));
        players.removeIf(player -> player.distanceTo(passive) > 20);
        if (unit.value > 0) {
            if (unit.type.equals(life)) players.forEach(player ->
                    ModNetworking.sendToClient(new LifeElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(water)) players.forEach(player ->
                    ModNetworking.sendToClient(new WaterElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(fire)) players.forEach(player ->
                    ModNetworking.sendToClient(new FireElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(stone)) players.forEach(player ->
                    ModNetworking.sendToClient(new StoneElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(ice)) players.forEach(player ->
                    ModNetworking.sendToClient(new IceElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(lightning)) players.forEach(player ->
                    ModNetworking.sendToClient(new LightningElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
            if (unit.type.equals(wind)) players.forEach(player ->
                    ModNetworking.sendToClient(new WindElementParticleS2CPacket(passive.getId(), 200), (ServerPlayer) player));
        } else {
            players.forEach(player -> {
                ModNetworking.sendToClient(new ClearElementParticleS2CPacket(passive.getId()), (ServerPlayer) player);
            });
        }
    }

    public static void ElementParticleCreate(Player player) {
        if (player.tickCount % 50 == 0) {
            List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 50, 50, 50));
            mobList.forEach(Element::ElementParticleProvider);
        }
    }

    public static void Tick(Level level) {
        LifeAndLightningTick(level);
        WaterAndLightning(level);
    }

    public static double ElementDamageEnhance(LivingEntity passive) {
        double rate = 1;
        rate += FireAndWindDamageEnhance(passive);
        rate += StoneAndLightningDamageEnhance(passive);
        return rate;
    }

    public static double ElementWithstandDamageEnhance(LivingEntity passive) {
        double rate = 0;
        rate += LifeAndIceWithStandDamageEnhance(passive);
        rate += WaterAndStoneWithStandDamageDecrease(passive);
        rate += StoneAndIceWithstandDamageEnhance(passive);
        rate += StoneAndWindWithstandDamageEnhance(passive);
        return rate;
    }

    public static double ElementDefenceDecrease(LivingEntity passive) {
        double rate = 1;
        rate -= LifeAndWindDefenceDecrease(passive);
        rate -= IceAndLightningDefenceDecrease(passive);
        return rate;
    }

    public static double ElementHealDecrease(LivingEntity passive) {
        double rate = 1;
        rate -= IceAndWindHealDecrease(passive);
        return rate;
    }

    public static void PlayerTick(Player player) {
        ElementParticleCreate(player);
        if (player.tickCount % 5 == 0) {
            if (!ElementPieceOnWeapon(player)) ResonanceEffectGive(player);
        }
    }

    public static void ClientRemoveEntityParticle(int id) {
        Element.fireElementParticle.remove(id);
        Element.iceElementParticle.remove(id);
        Element.lifeElementParticle.remove(id);
        Element.lightningElementParticle.remove(id);
        Element.stoneElementParticle.remove(id);
        Element.waterElementParticle.remove(id);
        Element.windElementParticle.remove(id);
    }

    public static List<Item> elementList = new ArrayList<>();

    public static void setElementList() {
        Item[] items = {
                ModItems.LifeElement.get(),
                ModItems.WaterElement.get(),
                ModItems.FireElement.get(),
                ModItems.StoneElement.get(),
                ModItems.IceElement.get(),
                ModItems.LightningElement.get(),
                ModItems.WindElement.get()
        };
        elementList.addAll(List.of(items));
    }

    public static Vec3 calculateViewVector(float p_20172_, float p_20173_) {
        float f = p_20172_ * ((float) Math.PI / 180F);
        float f1 = -p_20173_ * ((float) Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3((double) (f3 * f4), (double) (-f5), (double) (f2 * f4));
    }

    public static Vec3 getPos(Entity entity) {
        return calculateViewVector(0.0F, entity.getYRot() + 80).scale(0.5D);
    }

    public static void RangeElementProvider(LivingEntity active, String type, double value, boolean isAd, double damage, double r) {
        Map<String, ParticleOptions> map = new HashMap<>() {{
            put(life, ModParticles.LifeElementParticle.get());
            put(water, ModParticles.WaterElementParticle.get());
            put(fire, ModParticles.FireElementParticle.get());
            put(stone, ModParticles.StoneElementParticle.get());
            put(ice, ModParticles.IceElementParticle.get());
            put(lightning, ModParticles.LightningElementParticle.get());
            put(wind, ModParticles.WindElementParticle.get());
        }};
        List<Mob> mobList = active.level().getEntitiesOfClass(Mob.class, AABB.ofSize(active.position(), r * Math.sqrt(2), r * Math.sqrt(2), r * Math.sqrt(2)));
        mobList.removeIf(mob -> mob.distanceTo(active) > r);
        mobList.forEach(mob -> {
            ElementEffectAddToEntity(active, mob, type, value, isAd, damage);
        });
        ParticleProvider.DisperseParticle(active.position(), (ServerLevel) active.level(), 1, 1, 120, map.get(type), 1);
        ParticleProvider.DisperseParticle(active.position(), (ServerLevel) active.level(), 1.5, 1, 120, map.get(type), 1);
    }

    public static boolean ElementPieceOnWeapon(Player player) {
        if (!Utils.mainHandTag.containsKey(player.getMainHandItem().getItem())) return false;
        int slotIndex = player.getInventory().selected + 27;

        Map<Item, String> map = new HashMap<>() {{
            put(ModItems.LifeElementPiece2.get(), life);
            put(ModItems.WaterElementPiece2.get(), water);
            put(ModItems.FireElementPiece2.get(), fire);
            put(ModItems.StoneElementPiece2.get(), stone);
            put(ModItems.IceElementPiece2.get(), ice);
            put(ModItems.LightningElementPiece2.get(), lightning);
            put(ModItems.WindElementPiece2.get(), wind);
        }};

        ItemStack itemStack = player.getInventory().getItem(slotIndex);
        if (!map.containsKey(itemStack.getItem())) return false;
        String type = map.get(itemStack.getItem());
        ElementProvider(player, type, ElementValue.ElementValueJudgeByType(player, type) * 0.01 * itemStack.getCount());
        return itemStack.getCount() == 0;
    }

    public static void ResonanceEffectGive(Player player) {
        if (PowerResonanceElementCoverTickMap.containsKey(player) && PowerResonanceElementCoverTickMap.get(player) > player.getServer().getTickCount()) {
            ElementProvider(player, PowerResonanceElementCoverTypeMap.get(player),
                    ElementValue.ElementValueJudgeByType(player, PowerResonanceElementCoverTypeMap.get(player)),
                    player.getServer().getTickCount() - PowerResonanceElementCoverTickMap.get(player));
        } else if (PlayerResonanceType.containsKey(player)) {
            ElementProvider(player, PlayerResonanceType.get(player), ElementValue.ElementValueJudgeByType(player, PlayerResonanceType.get(player)));
        }
    }

    public static Map<String, String> nameMap = new HashMap<>() {{
        put(life, "生机元素");
        put(water, "碧水元素");
        put(fire, "炽焰元素");
        put(stone, "层岩元素");
        put(ice, "凛冰元素");
        put(lightning, "怒雷元素");
        put(wind, "澄风元素");
    }};

    public static Map<String, Style> styleMap = new HashMap<>() {{
        put(life, CustomStyle.styleOfLife);
        put(water, CustomStyle.styleOfWater);
        put(fire, CustomStyle.styleOfFire);
        put(stone, CustomStyle.styleOfStone);
        put(ice, CustomStyle.styleOfIce);
        put(lightning, CustomStyle.styleOfLightning);
        put(wind, CustomStyle.styleOfWind);
    }};

    public static void resonance(Player player, String type) {
        if (Compute.playerIsInBattle(player)) {
            Compute.sendFormatMSG(player, Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("请脱离战斗状态后重试").withStyle(ChatFormatting.WHITE));
            return;
        }

        if (PlayerResonanceType.containsKey(player) && PlayerResonanceType.get(player).equals(type)) {
            PlayerResonanceType.remove(player);
            Compute.sendFormatMSG(player, Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("共鸣").withStyle(CustomStyle.styleOfMoon).
                            append(Component.literal("类型已变更为 ").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("无")).withStyle(ChatFormatting.WHITE));
            return;
        }

        player.getPersistentData().putString(StringUtils.ResonanceType, type);
        PlayerResonanceType.put(player, type);
        Map<String, String> componentMap = new HashMap<>() {{
            put(life, "「春风化雨，生机盎然」");
            put(water, "「碧水如镜，映照山峦」");
            put(fire, "「炽焰奔腾，破云而出」");
            put(stone, "「层岩叠嶂，峭壁千仞」");
            put(ice, "「凛冰覆地，寒光闪烁」");
            put(lightning, "「怒雷轰鸣，震天撼地」");
            put(wind, "「澄风轻拂，云卷云舒」");
        }};
        if (!nameMap.containsKey(type)) return;
        Compute.sendFormatMSG(player, Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                Component.literal("共鸣").withStyle(CustomStyle.styleOfMoon).
                        append(Component.literal("类型已变更为 ").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(nameMap.get(type))).withStyle(styleMap.get(type)));
        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal(nameMap.get(type)).withStyle(styleMap.get(type)));
        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                new ClientboundSetSubtitleTextPacket(Component.literal(componentMap.get(type)).withStyle(styleMap.get(type)));
        ((ServerPlayer) player).connection.send(clientboundSetTitleTextPacket);
        ((ServerPlayer) player).connection.send(clientboundSetSubtitleTextPacket);
    }

    public static Map<BlockPos, String> ResonancePosMap = new HashMap<>() {{
        put(new BlockPos(406, 72, 1006), life);
        put(new BlockPos(35, -52, 997), water);
        put(new BlockPos(36, 11, 1106), fire);
        put(new BlockPos(3, -53, 913), stone);
        put(new BlockPos(-217, 195, 1340), ice);
        put(new BlockPos(-146, 144, 986), lightning);
        put(new BlockPos(-24, 93, 1516), wind);
    }};

    public static Map<String, Integer> ResonanceLevelRequireMap = new HashMap<>() {{
        put(life, 10);
        put(water, 25);
        put(fire, 32);
        put(stone, 40);
        put(ice, 40);
        put(wind, 70);
        put(lightning, 70);
    }};

    public static Map<Player, String> PowerResonanceElementCoverTypeMap = new HashMap<>();
    public static Map<Player, Integer> PowerResonanceElementCoverTickMap = new HashMap<>();

    public static void PowerResonanceElement(Player player, String type, int lastTick) {
        PowerResonanceElementCoverTypeMap.put(player, type);
        PowerResonanceElementCoverTickMap.put(player, player.getServer().getTickCount() + lastTick);
    }

    public static void ElementEffectTimeSend(Player player, ItemStack itemStack, int tickCount, int level, boolean NoTime) {
        ModNetworking.sendToClient(new ElementEffectTimeS2CPacket(itemStack, tickCount, level, NoTime), (ServerPlayer) player);
    }

    public static class Description {
        public static Component LifeElement(String content) {
            return Component.literal("「" + content + "生机元素" + "」").withStyle(CustomStyle.styleOfLife);
        }

        public static Component WaterElement(String content) {
            return Component.literal("「" + content + "碧水元素" + "」").withStyle(CustomStyle.styleOfWater);
        }

        public static Component FireElement(String content) {
            return Component.literal("「" + content + "炽焰元素" + "」").withStyle(CustomStyle.styleOfFire);
        }

        public static Component StoneElement(String content) {
            return Component.literal("「" + content + "层岩元素" + "」").withStyle(CustomStyle.styleOfStone);
        }

        public static Component IceElement(String content) {
            return Component.literal("「" + content + "凛冰元素" + "」").withStyle(CustomStyle.styleOfIce);
        }

        public static Component LightningElement(String content) {
            return Component.literal("「" + content + "怒雷元素" + "」").withStyle(CustomStyle.styleOfLightning);
        }

        public static Component WindElement(String content) {
            return Component.literal("「" + content + "澄风元素" + "」").withStyle(CustomStyle.styleOfWind);
        }

        public static Component LifeElementValue(String content) {
            return Component.literal("「" + content + "归一化生机元素强度" + "」").withStyle(CustomStyle.styleOfLife);
        }

        public static Component WaterElementValue(String content) {
            return Component.literal("「" + content + "归一化碧水元素强度" + "」").withStyle(CustomStyle.styleOfWater);
        }

        public static Component FireElementValue(String content) {
            return Component.literal("「" + content + "归一化炽焰元素强度" + "」").withStyle(CustomStyle.styleOfFire);
        }

        public static Component StoneElementValue(String content) {
            return Component.literal("「" + content + "归一化层岩元素强度" + "」").withStyle(CustomStyle.styleOfStone);
        }

        public static Component IceElementValue(String content) {
            return Component.literal("「" + content + "归一化凛冰元素强度" + "」").withStyle(CustomStyle.styleOfIce);
        }

        public static Component LightningElementValue(String content) {
            return Component.literal("「" + content + "归一化怒雷元素强度" + "」").withStyle(CustomStyle.styleOfLightning);
        }

        public static Component WindElementValue(String content) {
            return Component.literal("「" + content + "归一化澄风元素强度" + "」").withStyle(CustomStyle.styleOfWind);
        }
    }
}

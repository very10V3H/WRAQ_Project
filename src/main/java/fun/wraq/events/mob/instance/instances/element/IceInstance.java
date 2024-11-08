package fun.wraq.events.mob.instance.instances.element;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.missions.series.dailyMission.DailyMission;
import fun.wraq.render.hud.ColdData;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class IceInstance extends NoTeamInstance {

    private static IceInstance instance;

    public static String mobName = "冰霜骑士";

    public static IceInstance getInstance() {
        if (instance == null) {
            instance = new IceInstance(new Vec3(1565, 63, -2924), 50, 200, new Vec3(1565, 63, -2924),
                    Component.literal(mobName).withStyle(CustomStyle.styleOfIce));
        }
        return instance;
    }

    public IceInstance(Vec3 pos, double range, int delayTick, Vec3 armorStandPos, MutableComponent name) {
        super(pos, range, delayTick, armorStandPos, name);
    }

    @Override
    public void tickModule() {
        if (mobList.isEmpty()) return;
        Mob mob = this.mobList.get(0);
        int tick = mob.tickCount;
        Level level = mob.level();
        List<Player> players = getPlayerList(level);
        if (tick % 100 == 0) {
            skill(mob, tick, players); // 技能
            AtomicReference<Player> NearestPlayer = new AtomicReference<>();
            AtomicReference<Double> distance = new AtomicReference<>((double) 50);
            players.forEach(player -> {
                if (player.distanceTo(mob) < distance.get()) {
                    distance.set((double) player.distanceTo(mob));
                    NearestPlayer.set(player);
                }
            });


            if (NearestPlayer.get() != null) {
                Damage.AttackDamageToPlayer(mob, NearestPlayer.get(), 1200);
                NearestPlayer.get().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 3, false, false, false));

                BlockPos blockPos = new BlockPos((int) NearestPlayer.get().getX(), (int) (NearestPlayer.get().getY() + 0.9), (int) NearestPlayer.get().getZ());
                if (NearestPlayer.get().level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                    NearestPlayer.get().level().setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                    NearestPlayer.get().level().destroyBlock(blockPos, false);
                }
            }

            players.forEach(player -> {
                if (player != null && player.distanceTo(mob) < 50) {
                    Damage.manaDamageToPlayer(mob, player, 600);
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false, false));
                    BlockPos blockPos = new BlockPos((int) player.getX(), (int) (player.getY() + 0.9), (int) player.getZ());
                    if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                        player.level().setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                        player.level().destroyBlock(blockPos, false);
                    }
                }
            });

            ParticleProvider.createBallDisperseParticle(ParticleTypes.SNOWFLAKE, (ServerLevel) level, mob.position(), 0.75, 30);
        }
        Element.ElementProvider(mob, Element.ice, 4);
    }

    @Override
    public void summonModule(Level level) {
        Stray stray = new Stray(EntityType.STRAY, level);

        MobSpawn.setMobCustomName(stray, Component.literal(mobName).withStyle(CustomStyle.styleOfIce), 135);

        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(stray), 135);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(stray, 1250, 90, 90, 0.35,
                3, 0.2, 30, 0, 75 * Math.pow(10, 4), 0.35);

        stray.setHealth(stray.getMaxHealth());
        stray.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorIceHelmet.get().getDefaultInstance());
        stray.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorIceChest.get().getDefaultInstance());
        stray.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorIceLeggings.get().getDefaultInstance());
        stray.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorIceBoots.get().getDefaultInstance());
        stray.setItemSlot(EquipmentSlot.MAINHAND, ModItems.IceSword.get().getDefaultInstance());

        stray.moveTo(pos);
        level.addFreshEntity(stray);
        mobList.add(stray);

        ServerBossEvent serverBossEvent = (ServerBossEvent) (new ServerBossEvent(stray.getDisplayName(), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        getPlayerList(level).forEach(player -> {
            serverBossEvent.addPlayer((ServerPlayer) player);
        });
        bossInfoList.add(serverBossEvent);
    }

    @Override
    public void rewardModule(Player player) {
        List<ItemAndRate> rewardList = getRewardList();
        rewardList.forEach(itemAndRate -> {
            itemAndRate.sendWithMSG(player, 1);
        });
        DailyMission.addCount(player, DailyMission.iceKnightInstanceCountMap);

        String name = player.getName().getString();
        if (!MobSpawn.tempKillCount.containsKey(name)) MobSpawn.tempKillCount.put(name, new HashMap<>());
        Map<String, Integer> map = MobSpawn.tempKillCount.get(name);
        map.put(mobName, map.getOrDefault(mobName, 0) + 1);
        Compute.givePercentExpToPlayer(player, 0.02, PlayerAttributes.expUp(player), 135);
    }

    @Override
    public boolean allowReward(Player player) {
        return NoTeamInstanceModule.getPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.iceKnight);
    }

    @Override
    public Component allowRewardCondition() {
        return Component.literal("需要至少").withStyle(ChatFormatting.WHITE).
                append(Component.literal("获得").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("过").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1件").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("紫晶骑士器灵").withStyle(CustomStyle.styleOfPurpleIron)).
                append(Component.literal("，方能获取奖励。").withStyle(ChatFormatting.WHITE));
    }

    public static List<ItemAndRate> getRewardList() {
        return List.of(new ItemAndRate(ModItems.IceLoot.get(), 1),
                new ItemAndRate(ModItems.IceHeart.get(), 0.05),
                new ItemAndRate(ModItems.WorldSoul2.get(), 0.25),
                new ItemAndRate(ModItems.GoldCoinBag.get(), 0.1),
                new ItemAndRate(ModItems.iceBelt.get(), 0.08));
    }

    public static void skill(Mob mob, int instanceTick, List<Player> playerList) {
        int[] Tick = {60, 80, 100, 140, 200, 200};
        int Frequency = Tick[(int) (mob.getHealth() / mob.getMaxHealth())];
        Random random = new Random();
        int ChooseSkill = random.nextInt(5);
        Level level = mob.level();
        Style style = CustomStyle.styleOfIce;
        if (instanceTick % Frequency == 0) {
            switch (ChooseSkill) {
                case 0 -> {
                    playerList.forEach(player -> {
                        if (player.distanceTo(mob) < 50) {
                            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 99, false, false, false));
                            BlockPos blockPos = new BlockPos((int) player.getX(), (int) (player.getY() + 0.9), (int) player.getZ());
                            if (player.level().getBlockState(blockPos).getBlock() == Blocks.AIR) {
                                player.level().setBlockAndUpdate(blockPos, Blocks.ICE.defaultBlockState());
                                player.level().destroyBlock(blockPos, false);
                            }
/*                            Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.m("寒意释放", style),
                                    Te.m(""), 0, 20, 10);*/
                        }
                    });
                    ParticleProvider.createBallDisperseParticle(ParticleTypes.SNOWFLAKE, (ServerLevel) level, mob.position(), 1, 40);
                } // 寒意释放
                case 1 -> {
                    playerList.forEach(player -> {
                        if (player.distanceTo(mob) < 50) {
                            Vec3 vec3 = player.position().subtract(mob.position());
                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                    new ClientboundSetEntityMotionPacket(player.getId(), vec3.normalize().scale(3));
                            ServerPlayer serverPlayer = (ServerPlayer) player;
                            serverPlayer.connection.send(clientboundSetEntityMotionPacket);
/*                            Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.m("高压寒风", style),
                                    Te.m(""), 0, 20, 10);*/
                        }
                    });
                    ParticleProvider.GatherParticle(mob.position(), (ServerLevel) level,
                            1, 1, 120, ModParticles.LONG_SKY.get(), 1);
                    ParticleProvider.GatherParticle(mob.position(), (ServerLevel) level,
                            1.5, 1, 120, ModParticles.LONG_SKY.get(), 1);
                } // 凝聚寒意
                case 2 -> {
                    playerList.forEach(player -> {
                        if (player.distanceTo(mob) < 50) {
                            if (ColdData.PlayerCurrentColdNum(player) >= 50) {
                                Damage.AttackDamageToPlayer(mob, player, player.getMaxHealth() * 1.5);
                            }
                            Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.m("迸晶裂玉", style),
                                    Te.m("对寒冷值低的玩家造成致命伤害", style), 0, 20, 10);
                        }
                    });
                    ParticleProvider.createBallDisperseParticle(ModParticles.LONG_SKY.get(), (ServerLevel) level, mob.position(), 1, 40);
                } // 迸晶裂玉
                case 3 -> {
                    playerList.forEach(player -> {
                        if (player.distanceTo(mob) < 50) {
                            Vec3 vec3 = mob.position().subtract(player.position());
                            if (vec3.length() <= 8) {
                                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                        new ClientboundSetEntityMotionPacket(player.getId(), vec3.normalize().scale(Math.min(2, 8 / vec3.length())));
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetEntityMotionPacket);
/*                                Compute.setPlayerTitleAndSubTitle((ServerPlayer) player, Te.m("凝寒聚意", style),
                                        Te.m(""), 0, 20, 10);*/
                            }
                        }
                    });
                    ParticleProvider.GatherParticle(mob.position(), (ServerLevel) level,
                            1, 1, 120, ModParticles.LONG_SKY.get(), 1);
                    ParticleProvider.GatherParticle(mob.position(), (ServerLevel) level,
                            1.5, 1, 120, ModParticles.LONG_SKY.get(), 1);
                }
                case 4 -> {
                    for (int i = 0; i < 4; i++) {
                        if (Utils.IceHunterForIceKnight[i] == null || Utils.IceHunterForIceKnight[i].isDeadOrDying()) {
                            if (Utils.IceHunterForIceKnight[i] != null) {
                                Utils.IceHunterForIceKnight[i].remove(Entity.RemovalReason.KILLED);
                            }
                            Utils.IceHunterForIceKnight[i] = new Stray(EntityType.STRAY, mob.level());
                            Mob iceHunter = Utils.IceHunterForIceKnight[i];
                            MobSpawn.setMobCustomName(iceHunter, ModItems.MobArmorIceHunterHelmet.get(),
                                    Component.literal("冰原猎手").withStyle(CustomStyle.styleOfIce));

                            MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(iceHunter), 100);
                            MobSpawn.MobBaseAttributes.setMobBaseAttributes(iceHunter, 625, 90, 90, 0.35,
                                    3, 0.2, 30, 20, 75000, 0.35);

                            iceHunter.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorIceHunterHelmet.get().getDefaultInstance());
                            iceHunter.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorIceHunterChest.get().getDefaultInstance());
                            iceHunter.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorIceHunterLeggings.get().getDefaultInstance());
                            iceHunter.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorIceHunterBoots.get().getDefaultInstance());
                            iceHunter.setItemSlot(EquipmentSlot.MAINHAND, Items.BOW.getDefaultInstance());

                            iceHunter.moveTo(mob.position().add(1 - random.nextInt(2), 1 - random.nextInt(2), 1 - random.nextInt(2)));

                            level.addFreshEntity(iceHunter);
                        }
                    }
                } // 召唤冰原猎手
            }
        }
    }

    public static double IceKnightHealthAttackDamageFix(Mob mob) {
        if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorIceHelmet.get())) {
            if (mob.getHealth() / mob.getMaxHealth() > 2.0 / 3) return -0.5;
            if (mob.getHealth() / mob.getMaxHealth() < 1.0 / 3) return 0.5;
        }
        return 0;
    }

    public static double IceKnightHealthManaDamageFix(Mob mob) {
        if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorIceHelmet.get())) {
            if (mob.getHealth() / mob.getMaxHealth() > 2.0 / 3) return 0.5;
            if (mob.getHealth() / mob.getMaxHealth() < 1.0 / 3) return -0.5;
        }
        return 0;
    }
}

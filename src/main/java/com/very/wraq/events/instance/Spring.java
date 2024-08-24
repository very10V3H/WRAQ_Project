package com.very.wraq.events.instance;

import com.very.wraq.common.MySound;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.unSorted.SpringInstanceS2CPacket;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.specialevents.springFes.FireWorkGun;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.Boss2Damage;
import com.very.wraq.common.Utils.Struct.Instance;
import com.very.wraq.common.Utils.Struct.PlayerAndDistance;
import com.very.wraq.common.Utils.Struct.PlayerTeam;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/*@Mod.EventBusSubscriber*/
public class Spring {
    /*@SubscribeEvent*/
    public static void SpringEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            int InstanceIndex = 6;
            if (Utils.instanceList.get(InstanceIndex).isChallenge()) {
                Instance instance = Utils.instanceList.get(InstanceIndex);
                instance.addTick();
                int instanceTick = instance.getTick();
                PlayerTeam playerTeam = instance.getCurrentChallengePlayerTeam();
                List<Player> playerList = playerTeam.getPlayerList();
                List<Player> playerListGetByName = new ArrayList<>();
                Level level = event.level;
                int[] difficultyEnhance = {1, 2, 4};
                int difficultyEnhanceRate = difficultyEnhance[instance.getDifficulty()];
                playerList.forEach(player -> {
                    if (event.level.getServer().getPlayerList().getPlayerByName(player.getName().getString()) != null)
                        playerListGetByName.add(event.level.getServer().getPlayerList().getPlayerByName(player.getName().getString()));
                });
                int playerNum = playerListGetByName.size();
                if (instanceTick % 100 == 0) {
                    playerListGetByName.forEach(player -> {
                        FireWorkGun.RandomSummonFireworkRocket(level, player);
                    });
                }
                // 挑战前提示
                if (instanceTick <= 200) {
                    if (instanceTick % 20 == 0) {
                        if (instanceTick == 200) {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("年兽挑战").withStyle(CustomStyle.styleOfSpring));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("在十分钟内击杀年兽!").withStyle(ChatFormatting.RED));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        } else {
                            ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                    new ClientboundSetTitleTextPacket(Component.literal("年兽挑战").withStyle(CustomStyle.styleOfSpring));
                            ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                    new ClientboundSetSubtitleTextPacket(Component.literal("做好准备，离年兽出现还有 " + (10 - instanceTick / 20) + " 秒!"));
                            playerList.forEach(player -> {
                                ServerPlayer serverPlayer = (ServerPlayer) player;
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                            });
                        }
                        if (instanceTick < 200) {
                            List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(1432, 80, 1170), 100, 100, 100));
                            list.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
                        }
                    }
                }

                // 怪物生成
                if (instanceTick == 200) {

                    instance.setMobList(new ArrayList<>() {{
                        add(new Zombie(EntityType.ZOMBIE, level));
                    }});
                    Mob entity = instance.getMobList().get(0);

                    Compute.SetMobCustomName(entity, ModItems.MobArmorSpringHelmet.get(),
                            Component.literal("年兽").withStyle(CustomStyle.styleOfSpring));

                    entity.getAttribute(Attributes.MAX_HEALTH).setBaseValue(25000000 * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75));
                    entity.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(100 * difficultyEnhanceRate);
                    entity.setHealth(entity.getMaxHealth());
                    entity.setItemSlot(EquipmentSlot.HEAD, ModItems.MobArmorSpringHelmet.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.CHEST, ModItems.MobArmorSpringChest.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.LEGS, ModItems.MobArmorSpringLeggings.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.FEET, ModItems.MobArmorSpringBoots.get().getDefaultInstance());
                    entity.setItemSlot(EquipmentSlot.MAINHAND, ModItems.NetherSceptre.get().getDefaultInstance());

                    entity.moveTo(1432, 80, 1170);
                    level.addFreshEntity(entity);
                }

                if ((instanceTick - 200) != 0 && (instanceTick - 200) % 300 == 260 && instance.getMobList().get(0) != null) {
                    playerListGetByName.forEach(player -> {
                        ServerPlayer serverPlayer = (ServerPlayer) player;
                        ModNetworking.sendToClient(new SpringInstanceS2CPacket(), serverPlayer);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("锣鼓驱年").withStyle(CustomStyle.styleOfSpring));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("即将造成高额伤害!!!").withStyle(CustomStyle.styleOfSpring));
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                    });
                }
                if ((instanceTick - 200) != 0 && (instanceTick - 200) % 300 == 0 && instance.getMobList().get(0) != null) {
                    Mob mob = instance.getMobList().get(0);
                    ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                            1, 1, 120, ModParticles.LONG_SPRING.get(), 2);
                    ParticleProvider.DisperseParticle(mob.position(), (ServerLevel) level,
                            1.5, 1, 120, ModParticles.LONG_SPRING.get(), 2);
                    List<PlayerAndDistance> playerAndDistances = new ArrayList<>();
                    playerListGetByName.forEach(player -> {
                        if (player.distanceTo(mob) < 100) {
                            ServerPlayer serverPlayer = (ServerPlayer) player;
                            playerAndDistances.add(new PlayerAndDistance(player, player.distanceTo(mob)));
                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                    new ClientboundSetEntityMotionPacket(player.getId(), new Vec3(0, 5, 0));
                            serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                            ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                                    new ClientboundLevelParticlesPacket(ParticleTypes.EXPLOSION_EMITTER, true,
                                            player.getX(), player.getY(), player.getZ(),
                                            0, 0, 0, 0, 0);
                            serverPlayer.connection.send(clientboundLevelParticlesPacket);
                        }
                        MySound.SoundToAll(player, SoundEvents.DRAGON_FIREBALL_EXPLODE);
                    });
                    playerAndDistances.sort(Comparator.comparing(PlayerAndDistance::distance));
                    int Count = 0;
                    double[] Damage = {
                            12000, 8000, 4000, 2000
                    };
                    for (PlayerAndDistance playerAndDistance : playerAndDistances) {
                        Compute.Damage.AttackDamageToPlayer(mob, playerAndDistance.player(), Damage[Count]);
                        Count++;
                    }
                }

                MutableComponent difficulty = Component.literal("");
                switch (instance.getDifficulty()) {
                    case 0 -> difficulty = Component.literal("一般").withStyle(ChatFormatting.GREEN);
                    case 1 -> difficulty = Component.literal("困难").withStyle(ChatFormatting.LIGHT_PURPLE);
                    case 2 -> difficulty = Component.literal("炼狱").withStyle(ChatFormatting.RED);
                }

                // 击杀奖励
                if (instanceTick >= 200 && instance.getMobList().get(0) != null && Utils.instanceKillCount[InstanceIndex] >= 1) {
                    Utils.instanceKillCount[InstanceIndex] = 0;
                    Random random = new Random();
                    playerListGetByName.forEach(player -> {
                        ItemStack itemStack = new ItemStack(Items.AIR);
                        if (difficultyEnhanceRate == 4) {
                            Compute.itemStackGive(player, new ItemStack(ModItems.SpringPiece.get(), 1));
                        }

                        if (random.nextDouble() < 0.01 * difficultyEnhanceRate) {
                            switch (random.nextInt(12)) {
                                case 0 -> itemStack = new ItemStack(ModItems.SpringAttackHelmetForgeDraw.get());
                                case 1 -> itemStack = new ItemStack(ModItems.SpringAttackChestForgeDraw.get());
                                case 2 -> itemStack = new ItemStack(ModItems.SpringAttackLeggingsForgeDraw.get());
                                case 3 -> itemStack = new ItemStack(ModItems.SpringAttackBootsForgeDraw.get());
                                case 4 -> itemStack = new ItemStack(ModItems.SpringSwiftHelmetForgeDraw.get());
                                case 5 -> itemStack = new ItemStack(ModItems.SpringSwiftChestForgeDraw.get());
                                case 6 -> itemStack = new ItemStack(ModItems.SpringSwiftLeggingsForgeDraw.get());
                                case 7 -> itemStack = new ItemStack(ModItems.SpringSwiftBootsForgeDraw.get());
                                case 8 -> itemStack = new ItemStack(ModItems.SpringManaHelmetForgeDraw.get());
                                case 9 -> itemStack = new ItemStack(ModItems.SpringManaChestForgeDraw.get());
                                case 10 -> itemStack = new ItemStack(ModItems.SpringManaLeggingsForgeDraw.get());
                                case 11 -> itemStack = new ItemStack(ModItems.SpringManaBootsForgeDraw.get());
                            }
                        }
                        if (!itemStack.is(Items.AIR)) {
                            Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                                    Component.literal("").
                                            append(player.getDisplayName()).
                                            append(Component.literal("获得了:").withStyle(ChatFormatting.WHITE)).
                                            append(itemStack.getDisplayName()));
                            Compute.itemStackGive(player, itemStack);

                        }

                        Compute.itemStackGive(player, new ItemStack(ModItems.SpringLoot.get(), difficultyEnhanceRate));

                        Compute.respawnPlayer(player);
                    });
                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍:").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(playerTeam.getTeamName() + " 用时:").withStyle(ChatFormatting.WHITE)).
                                    append(Component.literal((instanceTick - 200) / 20 + "s ").withStyle(CustomStyle.styleOfSpring)).
                                    append(Component.literal("成功挑战了").withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty));

                    playerListGetByName.forEach(player -> {
                        Compute.sendFormatMSG(player, Component.literal("副本").withStyle(ChatFormatting.RED),
                                Component.literal("  征讨伤害排名如下：").withStyle(ChatFormatting.WHITE));

                        Utils.SpringDamageList.sort(Comparator.comparing(Boss2Damage::getDamage).reversed());
                        AtomicInteger index = new AtomicInteger(1);

                        Utils.SpringDamageList.forEach(boss2Damage -> {
                            if (boss2Damage.getPlayer() != null) {
                                Player player1 = boss2Damage.getPlayer();
                                double damage = boss2Damage.getDamage();
                                Compute.sendFormatMSG(player, Component.literal(index + ".").withStyle(ChatFormatting.RED),
                                        Component.literal(" ").withStyle(ChatFormatting.WHITE).
                                                append(player1.getDisplayName()).
                                                append(Component.literal("  DMG:" + damage + "[" + String.format("%.2f", damage * 100 / (25000000 * difficultyEnhanceRate * (1 + (playerNum - 1) * 0.75))) + "%]").withStyle(ChatFormatting.WHITE)));
                                index.incrementAndGet();
                            }
                        });
                    });

                    playerListGetByName.forEach(player -> {
                        CompoundTag data = player.getPersistentData();
                        data.putInt(StringUtils.InstanceTimes.IceKnight, data.getInt(StringUtils.InstanceTimes.IceKnight + 1));
                    });
                    instance.reset();
                    Utils.SpringDamageList.clear();
                }

                // 失败
                if (instanceTick == 12200 || playerListGetByName.size() == 0 || instance.getDeadTimes() >= playerListGetByName.size()) {
                    Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                            Component.literal("队伍：" + playerTeam.getTeamName()).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("挑战副本:").withStyle(ChatFormatting.WHITE)).
                                    append(instance.getInstanceName()).
                                    append(Component.literal("-").withStyle(ChatFormatting.GRAY)).
                                    append(difficulty).
                                    append(Component.literal(" 失败。").withStyle(ChatFormatting.WHITE)));
                    Utils.instanceKillCount[InstanceIndex] = 0;
                    instance.reset();
                    Utils.SpringDamageList.clear();
                    playerList.forEach(player -> {
                        Compute.respawnPlayer(player);
                    });
                }
            }
        }
    }

}

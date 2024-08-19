package com.very.wraq.process.func.particle;

import com.very.wraq.common.Compute;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ParticlePackets.NewParticlePackets.*;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class ParticleProvider {
    public static void dustParticle(Player player, Vec3 pos, double r, int num, int color) {
        player.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> {
            if (serverPlayer.position().distanceTo(pos) <= 30 && serverPlayer.level().dimension().equals(player.level().dimension())) {
                int ignoreLevel = Math.max(1, serverPlayer.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new DustParticleS2CPacket(pos.toVector3f(), r, num,
                            String.valueOf(color), new Vector3f(0, 0, 0)), serverPlayer);
                }
            }
        });
    }

    public static void SpaceRangeParticle(ServerLevel serverLevel, Vec3 des, double r, int num, ParticleOptions particleOptions) {
        List<ServerPlayer> serverPlayerList = serverLevel.getServer().getPlayerList().getPlayers();
        serverPlayerList.forEach(serverPlayer1 -> {
            if (serverPlayer1.level().equals(serverLevel) && serverPlayer1.position().distanceTo(des) < 40) {
                int ignoreLevel = Math.max(1, serverPlayer1.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new SpaceRangeParticleS2CPacket(
                            des.toVector3f(), r, num / ignoreLevel, Utils.ParticleToParticleStringMap.get(particleOptions)), serverPlayer1);
                }
            }
        });
    }

    public static void FaceCircleCreate(ServerPlayer serverPlayer, double pickDistance, double r, int num, ParticleOptions particleOptions) {
        Vec3 PickVec = serverPlayer.pick(10, 0, true).getLocation();
        Vec3 FaceVec = serverPlayer.pick(pickDistance, 0, true).getLocation();

        List<ServerPlayer> serverPlayerList = serverPlayer.getServer().getPlayerList().getPlayers();
        serverPlayerList.forEach(serverPlayer1 -> {
            if (serverPlayer1.level().equals(serverPlayer.level()) && serverPlayer1.distanceTo(serverPlayer) < 40) {
                int ignoreLevel = Math.max(1, serverPlayer1.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new FaceCircleParticleS2CPacket(
                            PickVec.toVector3f(), FaceVec.toVector3f(), pickDistance, r, num / ignoreLevel, Utils.ParticleToParticleStringMap.get(particleOptions)
                    ), serverPlayer1);
                }
            }
        });
    }

    public static void EntityFaceCircleCreate(Entity entity, Vec3 Position, Vec3 Location, double pickDistance, double r, int num, ParticleOptions particleOptions, boolean flag) {
        if (flag) {
            List<ServerPlayer> serverPlayerList = entity.getServer().getPlayerList().getPlayers();
            serverPlayerList.forEach(serverPlayer -> {
                if (serverPlayer.level().equals(entity.level()) && serverPlayer.distanceTo(entity) < 40) {
                    int ignoreLevel = Math.max(1, serverPlayer.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                    if (ignoreLevel < 10) {
                        ModNetworking.sendToClient(new EntityFaceCircleParticleS2CPacket(
                                Position.toVector3f(), Location.toVector3f(), pickDistance, r, num / ignoreLevel, Utils.ParticleToParticleStringMap.get(particleOptions)
                        ), serverPlayer);
                    }
                }
            });
        } else {
            Vec3 nVec = Position;
            Vec3 iVec = new Vec3(1, 0, 0);
            Vec3 jVec = new Vec3(0, 1, 0);
            Vec3 kVec = new Vec3(0, 0, 1);
            Vec3 aVec;
            if (nVec.cross(iVec).length() == 0) {
                aVec = nVec.cross(jVec);
            } else aVec = nVec.cross(iVec);
            aVec = aVec.normalize();
            Vec3 bVec = nVec.cross(aVec).normalize();
            for (int i = 0; i < num; i++) {
                double angle = (2 * Math.PI / num) * (i);
                Vec3 Point = new Vec3(Location.x + r * Math.cos(angle) * aVec.x + r * Math.sin(angle) * bVec.x,
                        Location.y + r * Math.cos(angle) * aVec.y + r * Math.sin(angle) * bVec.y,
                        Location.z + r * Math.cos(angle) * aVec.z + r * Math.sin(angle) * bVec.z);
                entity.level().addParticle(particleOptions, Point.x, Point.y, Point.z, 0, 0, 0);

            }
        }
    }

    public static void EntityFaceConnectCircleCreate(Entity entity, Vec3 Position, Vec3 Location, double pickDistance, double r, int num, ParticleOptions particleOptions, double DX, double DY, double DZ, double Start) {

        List<ServerPlayer> serverPlayerList = entity.getServer().getPlayerList().getPlayers();

        serverPlayerList.forEach(serverPlayer -> {
            if (serverPlayer.level().equals(entity.level()) && serverPlayer.distanceTo(entity) < 40) {
                int ignoreLevel = Math.max(1, serverPlayer.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new EntityFaceConnectCircleParticleS2CPacket(
                            Position.toVector3f(), Location.toVector3f(), pickDistance, r, num / ignoreLevel, Utils.ParticleToParticleStringMap.get(particleOptions), DX, DY, DZ, Start
                    ), serverPlayer);
                }
            }
        });
    }

    public static void VerticleCircleParticle(Vec3 bottomPos, ServerLevel serverLevel, double pickDistance, double r, int num, ParticleOptions particleOptions) {
        List<ServerPlayer> playerList = serverLevel.getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer1 -> {
            if (serverPlayer1.serverLevel().equals(serverLevel) && serverPlayer1.position().distanceTo(bottomPos) < 40) {
                int ignoreLevel = Math.max(1, serverPlayer1.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new VerticleCircleParticleS2CPacket(
                            bottomPos.toVector3f(), pickDistance, r, num / ignoreLevel, Utils.ParticleToParticleStringMap.get(particleOptions)), serverPlayer1);
                }
            }
        });
    }

    public static void DisperseParticle(Vec3 bottomPos, ServerLevel serverLevel, double pickDistance, double r, int num, ParticleOptions particleOptions, int scale) {
        List<ServerPlayer> playerList = serverLevel.getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer1 -> {
            if (serverPlayer1.serverLevel().equals(serverLevel) && serverPlayer1.position().distanceTo(bottomPos) < 40) {
                int ignoreLevel = Math.max(1, serverPlayer1.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new DisperseParticleS2CPacket(
                            bottomPos.toVector3f(), pickDistance, r, num, Utils.ParticleToParticleStringMap.get(particleOptions), scale), serverPlayer1);
                }
            }
        });
    }

    public static void GatherParticle(Vec3 bottomPos, ServerLevel serverLevel, double pickDistance, double r, int num, ParticleOptions particleOptions, double scale) {
        List<ServerPlayer> playerList = serverLevel.getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer1 -> {
            if (serverPlayer1.serverLevel().equals(serverLevel) && serverPlayer1.position().distanceTo(bottomPos) < 40) {
                int ignoreLevel = Math.max(1, serverPlayer1.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new GatherParticleS2CPacket(
                            bottomPos.toVector3f(), pickDistance, r, num, Utils.ParticleToParticleStringMap.get(particleOptions), scale), serverPlayer1);
                }
            }
        });
    }


    public static void VerticleCircleParticle(ServerPlayer serverPlayer, double pickDistance, double r, int num, ParticleOptions particleOptions) {
        Vec3 bottomPos = new Vec3(serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ());

        List<ServerPlayer> list = serverPlayer.getServer().getPlayerList().getPlayers();
        list.forEach(serverPlayer1 -> {
            if (serverPlayer1.level().equals(serverPlayer.level()) && serverPlayer1.distanceTo(serverPlayer) < 40) {
                int ignoreLevel = Math.max(1, serverPlayer1.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new VerticleCircleParticleS2CPacket(
                            bottomPos.toVector3f(), pickDistance, r, num, Utils.ParticleToParticleStringMap.get(particleOptions)), serverPlayer1);
                }
            }
        });
    }

    public static void VerticleCircleParticle(Vec3 bottomPos, ServerPlayer serverPlayer, double pickDistance, double r, int num, ParticleOptions particleOptions) {
        List<ServerPlayer> list = serverPlayer.getServer().getPlayerList().getPlayers();
        list.forEach(serverPlayer1 -> {
            if (serverPlayer1.level().equals(serverPlayer.level()) && serverPlayer1.position().distanceTo(bottomPos) < 40) {
                int ignoreLevel = Math.max(1, serverPlayer1.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new VerticleCircleParticleS2CPacket(
                            bottomPos.toVector3f(), pickDistance, r, num, Utils.ParticleToParticleStringMap.get(particleOptions)), serverPlayer1);
                }
            }
        });
    }

    public static void VerticleCircleParticle(Vec3 bottomPos, Level level, double pickDistance, double r, int num, ParticleOptions particleOptions) {
        for (int i = 0; i < num; i++) {
            double angle = (2 * Math.PI / num) * (i);
            Vec3 Point = new Vec3(bottomPos.x + r * cos(angle), bottomPos.y + pickDistance, bottomPos.z + r * sin(angle));
            level.addParticle(particleOptions, Point.x, Point.y, Point.z, 0, 0, 0);
        }
    }

    public static void RandomMoveParticle(Entity entity, double pickDistance, double r, int num, ParticleOptions particleOptions) {
        Vec3 bottomPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
        List<ServerPlayer> serverPlayerList = entity.getServer().getPlayerList().getPlayers();
        serverPlayerList.forEach(serverPlayer -> {
            if (serverPlayer.level().equals(entity.level()) && serverPlayer.distanceTo(entity) < 40) {
                int ignoreLevel = Math.max(1, serverPlayer.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new RandomMoveParticleS2CPacket(
                            bottomPos.toVector3f(), pickDistance, r, num, Utils.ParticleToParticleStringMap.get(particleOptions)
                    ), serverPlayer);
                }
            }
        });
    }

    public static void EntityEffectVerticleCircleParticle(Entity entity, double pickDistance, double r, int num, ParticleOptions particleOptions, int tick) {
        Vec3 bottomPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());

        List<ServerPlayer> serverPlayerList = entity.getServer().getPlayerList().getPlayers();
        serverPlayerList.forEach(serverPlayer -> {
            if (serverPlayer.level().equals(entity.level()) && serverPlayer.distanceTo(entity) < 40) {
                int ignoreLevel = Math.max(1, serverPlayer.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new EntityEffectVerticleCircleParticleS2CPacket(
                            bottomPos.toVector3f(), pickDistance, r, num, Utils.ParticleToParticleStringMap.get(particleOptions)
                    ), serverPlayer);
                }
            }
        });
    }

    public static void EntityEffectVerticleCircleParticle(Entity entity, double pickDistance, double r, int num, ParticleOptions particleOptions, int tick, boolean flag) {
        Vec3 bottomPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());

        if (flag) {
            List<ServerPlayer> serverPlayerList = entity.getServer().getPlayerList().getPlayers();
            serverPlayerList.forEach(serverPlayer -> {
                if (serverPlayer.level().equals(entity.level()) && serverPlayer.distanceTo(entity) < 40) {
                    int ignoreLevel = Math.max(1, serverPlayer.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                    if (ignoreLevel < 10) {
                        ModNetworking.sendToClient(new EntityEffectVerticleCircleParticleS2CPacket(
                                bottomPos.toVector3f(), pickDistance, r, num, Utils.ParticleToParticleStringMap.get(particleOptions)
                        ), serverPlayer);
                    }
                }
            });
        } else {
            for (int i = 0; i < num; i++) {
                double angle = (2 * Math.PI / num) * (i);
                Vec3 Point = new Vec3(bottomPos.x + r * cos(angle), bottomPos.y + pickDistance, bottomPos.z + r * sin(angle));
                entity.level().addParticle(particleOptions, Point.x, Point.y, Point.z, 0, 0, 0);
            }
        }
    }

    public static void PlayerPowerParticle(Player player) {
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1.5, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1.25, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 1, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.75, 0.4, 8, ParticleTypes.WITCH, 0);
        ParticleProvider.EntityEffectVerticleCircleParticle(player, 0.5, 0.4, 8, ParticleTypes.WITCH, 0);
        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(ParticleTypes.WITCH, true,
                player.getX(),
                player.getY(),
                player.getZ(),
                0.5f,
                0.5f,
                0.5f,
                1,
                0);
        List<ServerPlayer> list = player.getServer().getPlayerList().getPlayers();
        for (ServerPlayer serverPlayer1 : list) {
            serverPlayer1.connection.send(clientboundLevelParticlesPacket);
        }
    }

    public static void CurveParticle(Level level, int num, Vec3 startVec, Vec3 endVec, Vec3 delta, ParticleOptions particleOptions) {
        List<ServerPlayer> list = level.getServer().getPlayerList().getPlayers();
        list.forEach(serverPlayer -> {
            if (serverPlayer.level().equals(level) && serverPlayer.position().distanceTo(startVec) < 80) {
                int ignoreLevel = Math.max(1, serverPlayer.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new CurveParticleS2CPacket(
                            endVec.toVector3f(), startVec.toVector3f(), delta.toVector3f(),
                            0, 0, num, Utils.ParticleToParticleStringMap.get(particleOptions)
                    ), serverPlayer);
                }
            }
        });
    }

    public static void LineParticle(Level level, int num, Vec3 startVec, Vec3 endVec, ParticleOptions particleOptions) {
        List<ServerPlayer> list = level.getServer().getPlayerList().getPlayers();
        list.forEach(serverPlayer -> {
            if (serverPlayer.level().equals(level) && serverPlayer.position().distanceTo(startVec) < 80) {
                int ignoreLevel = Math.max(1, serverPlayer.getPersistentData().getInt(StringUtils.IgnoreParticleLevel));
                if (ignoreLevel < 10) {
                    ModNetworking.sendToClient(new LineParticleS2CPacket(
                            endVec.toVector3f(), startVec.toVector3f(), 0, 0, num / ignoreLevel, Utils.ParticleToParticleStringMap.get(particleOptions)
                    ), serverPlayer);
                }
            }
        });
    }

    public static void ParticleWITCH(double X, double Y, double Z, Level level, double r, ParticleOptions particleOptions) {
        double XArray[] = {
                X + r,
                X - r,
                X, X, X, X,
                X + r / Math.sqrt(2), X + r / Math.sqrt(2), X + r / Math.sqrt(2), X + r / Math.sqrt(2),
                X - r / Math.sqrt(2), X - r / Math.sqrt(2), X - r / Math.sqrt(2), X - r / Math.sqrt(2)
        };
        double YArray[] = {
                Y, Y, Y, Y, Y + r, Y - r,
                Y + r / Math.sqrt(2), Y + r / Math.sqrt(2), Y - r / Math.sqrt(2), Y - r / Math.sqrt(2),
                Y + r / Math.sqrt(2), Y + r / Math.sqrt(2), Y - r / Math.sqrt(2), Y - r / Math.sqrt(2),
        };
        double ZArray[] = {
                Z, Z, Z + r, Z - r, Z, Z,
                Z + r / Math.sqrt(2), Z - r / Math.sqrt(2),
                Z + r / Math.sqrt(2), Z - r / Math.sqrt(2),
                Z + r / Math.sqrt(2), Z - r / Math.sqrt(2),
                Z + r / Math.sqrt(2), Z - r / Math.sqrt(2),
        };
        List<ServerPlayer> list = level.getServer().getPlayerList().getPlayers();
        ClientboundLevelParticlesPacket clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                particleOptions, true, X + r, Y, Z, 0, 0, 0, 0, 0
        );
        for (ServerPlayer serverPlayer : list) {
            serverPlayer.connection.send(clientboundLevelParticlesPacket);
        }
        for (int i = 1; i < 14; i++) {
            clientboundLevelParticlesPacket = new ClientboundLevelParticlesPacket(
                    particleOptions, true, XArray[i], YArray[i], ZArray[i], 0, 0, 0, 0, 0
            );
            for (ServerPlayer serverPlayer : list) {
                serverPlayer.connection.send(clientboundLevelParticlesPacket);
            }
        }
    }

    public static void ParticleSCRAPE(double X, double Y, double Z, Level level, double r) {
        level.addParticle(ParticleTypes.SCRAPE, X + r, Y, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X - r, Y, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X, Y, Z + r, 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X, Y, Z - r, 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X, Y + r, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X, Y - r, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X + r / Math.sqrt(2), Y + r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X + r / Math.sqrt(2), Y + r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X + r / Math.sqrt(2), Y - r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X + r / Math.sqrt(2), Y - r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X - r / Math.sqrt(2), Y + r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X - r / Math.sqrt(2), Y + r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X - r / Math.sqrt(2), Y - r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.SCRAPE, X - r / Math.sqrt(2), Y - r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
    }

    public static void ParticleComposter(double X, double Y, double Z, Level level, double r) {
        level.addParticle(ParticleTypes.COMPOSTER, X + r, Y, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X - r, Y, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X, Y, Z + r, 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X, Y, Z - r, 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X, Y + r, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X, Y - r, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X + r / Math.sqrt(2), Y + r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X + r / Math.sqrt(2), Y + r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X + r / Math.sqrt(2), Y - r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X + r / Math.sqrt(2), Y - r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X - r / Math.sqrt(2), Y + r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X - r / Math.sqrt(2), Y + r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X - r / Math.sqrt(2), Y - r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.COMPOSTER, X - r / Math.sqrt(2), Y - r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
    }

    public static void ParticleLava(double X, double Y, double Z, Level level, double r) {
        level.addParticle(ParticleTypes.LAVA, X + r, Y, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X - r, Y, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X, Y, Z + r, 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X, Y, Z - r, 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X, Y + r, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X, Y - r, Z, 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X + r / Math.sqrt(2), Y + r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X + r / Math.sqrt(2), Y + r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X + r / Math.sqrt(2), Y - r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X + r / Math.sqrt(2), Y - r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X - r / Math.sqrt(2), Y + r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X - r / Math.sqrt(2), Y + r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X - r / Math.sqrt(2), Y - r / Math.sqrt(2), Z + r / Math.sqrt(2), 0, 0, 0);
        level.addParticle(ParticleTypes.LAVA, X - r / Math.sqrt(2), Y - r / Math.sqrt(2), Z - r / Math.sqrt(2), 0, 0, 0);
    }

    public static void createSingleParticle(Entity entity, double range, ParticleOptions particle, Vec3 pos) {
        Compute.getNearEntity(entity, Player.class, range).stream().filter(e -> e instanceof ServerPlayer)
                .map(player -> (ServerPlayer) player).forEach(serverPlayer -> {
                    ClientboundLevelParticlesPacket clientboundLevelParticlesPacket =
                            new ClientboundLevelParticlesPacket(particle, true, pos.x, pos.y, pos.z, 0, 0, 0, 0, 1);
                    serverPlayer.connection.send(clientboundLevelParticlesPacket);
        });
    }
}

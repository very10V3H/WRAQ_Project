package fun.wraq.events.client;

import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.instance.instances.tower.ManaTowerInstance;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.series.overworld.divine.DivineUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ParticleEvent {
    @SubscribeEvent
    public static void Particle(TickEvent.PlayerTickEvent event) {
        MobParticle(event);
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START)
                && event.player.equals(Minecraft.getInstance().player)) {
            Level level = event.player.level();
            ClientLevel clientLevel = (ClientLevel) level;
            Player player = event.player;

            ParticleProvider.handleClientLevelTick(clientLevel);

            Random random = new Random();
            boolean isInOverWorld = clientLevel.dimension().equals(Level.OVERWORLD);
            if (isInOverWorld && player.getX() > 2169 && player.getX() < 2333
                    && player.getZ() > 1325 && player.getZ() < 1488) {
                for (int i = 0; i < 75; i++) {
                    level.addParticle(ModParticles.LONG_ENTROPY.get(),
                            player.getX() + random.nextInt(100) - 50,
                            player.getY() + random.nextInt(100) - 50,
                            player.getZ() + random.nextInt(100) - 50,
                            0, 0.5, 0);
                }
            }

            if (isInOverWorld && DivineUtils.isInDivineIsland(player)) {
                for (int i = 0; i < 75; i++) {
                    level.addParticle(ParticleTypes.END_ROD,
                            player.getX() + random.nextInt(100) - 50,
                            player.getY() + random.nextInt(100) - 50,
                            player.getZ() + random.nextInt(100) - 50,
                            0, 0.5, 0);
                }
            }

            if (isInOverWorld) {
                List<Vec3> pos = List.of(
                        new Vec3(2397, 168, 1633),
                        new Vec3(2394, 164, 1610),
                        new Vec3(2413, 176, 1599),
                        new Vec3(2436, 173, 1583),
                        new Vec3(2408, 157, 1575),
                        new Vec3(2433, 151, 1552),
                        new Vec3(2448, 158, 1543),
                        new Vec3(2400, 129, 1509),
                        new Vec3(2423, 141, 1527),
                        new Vec3(2449, 149, 1515),
                        new Vec3(2468, 158, 1501)
                );
                boolean near = false;
                for (Vec3 po : pos) {
                    if (player.position().distanceTo(po) < 20) {
                        near = true;
                        break;
                    }
                }
                if (near) {
                    for (int i = 0; i < 75; i++) {
                        level.addParticle(ParticleTypes.WITCH, player.getX() + random.nextInt(100) - 50,
                                player.getY() + random.nextInt(100) - 50, player.getZ() + random.nextInt(100) - 50,
                                0, 0.5, 0);
                    }
                }
            }

            if (player.tickCount % 20 == 0) {
                ClientUtils.EndRune2Pos.forEach(posAndLastTime -> {
                    posAndLastTime.TickCount -= 20;
                    for (int i = 0; i < 100; i++) {
                        level.addParticle(ParticleTypes.DRAGON_BREATH,
                                posAndLastTime.vec3.x + random.nextDouble(10) - 5,
                                posAndLastTime.vec3.y + 1,
                                posAndLastTime.vec3.z + random.nextDouble(10) - 5, 0, 0, 0);
                    }
                });
                ClientUtils.EndRune2Pos.removeIf(posAndLastTime -> posAndLastTime.TickCount < 0);
            } //下界符石 末影龙息

            Utils.WorldEntropyPos.forEach(worldEntropy -> {
                ParticleProvider.RandomToDesParticle(random.nextInt(20), worldEntropy.getVec3().add(0.5, 0, 0.5), level, 5);
            });

            ManaTowerInstance.provideClientParticle(player);

/*            SpinParticleCreate(player, new Vec3(52.5, -53.5, 1039.5), 1.5, ModParticles.VOLCANO_TP.get(), 20);
            SpinParticleCreate(player, new Vec3(1138.5, 67.5, 384.5), 3, ModParticles.VOLCANO_TP.get(), 20);

            SpinParticleCreate(player, new Vec3(1124.5, 122, 1076.5), 3, ModParticles.LifeElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(861, 64, 380), 3, ModParticles.WaterElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(25.5, 105, 519.5), 3, ModParticles.FireElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(1221.5, 64, 1438.5), 3, ModParticles.StoneElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(1660.5, 72, 1985.5), 3, ModParticles.IceElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(1679.5, 69, 528.5), 3, ModParticles.LightningElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(1158.5, 67, 356.5), 3, ModParticles.WindElement100TickParticle.get(), 50);

            SpinParticleCreate(player, new Vec3(406.5, 72, 1006.5), 3, ModParticles.LifeElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(35.5, -52, 997.5), 3, ModParticles.WaterElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(36.5, 11, 1106.5), 3, ModParticles.FireElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(3, -53, 913.5), 3, ModParticles.StoneElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(-216.5, 195, 1340.5), 3, ModParticles.IceElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(-145.5, 144, 986.5), 3, ModParticles.LightningElement100TickParticle.get(), 50);
            SpinParticleCreate(player, new Vec3(-23.5, 93, 1516.5), 3, ModParticles.WindElement100TickParticle.get(), 50);*/
        }


        Player player = event.player;
        Level level = player.level();
        if (ClientUtils.ParticleFlag && level.isClientSide && event.side.isClient() && event.player.equals(Minecraft.getInstance().player)) {
            ClientUtils.ParticleFlag = false;
            switch (ClientUtils.ParticleRandom) {
                case 0 -> {
                    for (double x = -171.5; x <= -164.5; x++) {
                        for (double z = 1409.5; z <= 1416.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK, x, 116, z, 0, 0, 0);
                        }
                    }
                }
                case 1 -> {
                    for (double x = -162.5; x <= -155.5; x++) {
                        for (double z = 1409.5; z <= 1416.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK, x, 116, z, 0, 0, 0);
                        }
                    }
                }
                case 2 -> {
                    for (double x = -162.5; x <= -155.5; x++) {
                        for (double z = 1418.5; z <= 1425.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK, x, 116, z, 0, 0, 0);
                        }
                    }
                }
                case 3 -> {
                    for (double x = -171.5; x <= -164.5; x++) {
                        for (double z = 1418.5; z <= 1425.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK, x, 116, z, 0, 0, 0);
                        }
                    }
                }
            }
        }
    }

    public static void createSpinParticle(Player player, Vec3 pos, double r, ParticleOptions particleOptions, int cycle) {
        int tick = player.tickCount % cycle;
        Vec3 delta = new Vec3(0, 4.0 / cycle, 0);
        double angle = (2 * Math.PI / cycle) * (tick);
        Vec3 Point = new Vec3(pos.x + r * cos(angle), pos.y, pos.z + r * sin(angle));
        player.level().addParticle(particleOptions, Point.x, Point.y, Point.z,
                delta.x, delta.y, delta.z);
    }

    public static void MobParticle(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {
            Player player = event.player;
            Level level = player.level();
            int TickCount = player.tickCount;
            List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 100, 100, 100));
            for (Mob mob : list) {

                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ARMOR_KAZE_RECALL.get())) {
                    ParticleFour(mob, ModParticles.KAZE.get(), ModParticles.KAZE.get());
                    List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 20, 20, 20));
                    for (Player player1 : playerList) {
                        if (player1.position().distanceTo(mob.position()) <= 2.5) {
                            player1.setDeltaMovement(player1.position().subtract(mob.position()));
                        }
                    }
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ARMOR_SPIDER_RECALL.get())) {
                    ParticleFour(mob, ModParticles.SPIDER.get(), ModParticles.SPIDER.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ARMOR_HUSK_RECALL.get())) {
                    if (ClientUtils.BlackForestParticle) {
                        ClientUtils.BlackForestParticle = false;
                        SkillParticle(level, mob, ModParticles.BLACKFOREST_RECALL.get());
                    }
                    ParticleFour(mob, ModParticles.BLACKFOREST.get(), ModParticles.BLACKFOREST.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ARMOR_LIGHTNING_RECALL.get())) {
                    ParticleFour(mob, ModParticles.LIGHTNINGISLAND.get(), ModParticles.LIGHTNINGISLAND.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ARMOR_NETHER_RECALL.get())) {
                    if (ClientUtils.NetherParticle) {
                        ClientUtils.NetherParticle = false;
                        SkillParticle(level, mob, ModParticles.NETHER.get());
                    }
                    ParticleFour(mob, ModParticles.NETHER.get(), ModParticles.NETHER.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ARMOR_SNOW_RECALL.get())) {
                    ParticleFour(mob, ModParticles.SNOW.get(), ModParticles.SNOW.get());
                }
            }

            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.getPosition(1), 100, 100, 100));
            Random random = new Random();
            for (Player player1 : playerList) {
                if (TickCount % 20 == 0 && player1.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SAKURA_HELMET.get())) {
                    level.addParticle(ParticleTypes.CHERRY_LEAVES, player1.getX() + random.nextDouble(-0.5, 0.5), player1.getY() + 1.7, player1.getZ() + random.nextDouble(-0.5, 0.5), 0, 0, 0);
                }
            }
            ClientUtils.DefencePenetrationParticle.keySet().forEach(integer -> {
                Entity entity = level.getEntity(integer);
                if (entity != null) {
                    double r = 0.5;
                    double Dx = r * Math.cos(2 * Math.PI * (TickCount % 20) / 20);
                    double Dz = r * Math.sin(2 * Math.PI * (TickCount % 20) / 20);
                    level.addParticle(ModParticles.BREAK_Defence.get(), entity.getX() + Dx, entity.getY() + 1.5, entity.getZ() + Dz, 0, 0, 0);
                    if (ClientUtils.DefencePenetrationParticle.get(integer) > 0)
                        ClientUtils.DefencePenetrationParticle.put(integer, ClientUtils.DefencePenetrationParticle.get(integer) - 1);

                }
            });
            ClientUtils.DefencePenetrationParticle.keySet().removeIf(integer -> ClientUtils.DefencePenetrationParticle.get(integer) == 0);

            ClientUtils.ManaDefencePenetrationParticle.keySet().forEach(integer -> {
                Entity entity = level.getEntity(integer);
                if (entity != null) {
                    double r = 0.5;
                    double Dx = r * Math.cos(2 * Math.PI * ((TickCount + 10) % 20) / 20);
                    double Dz = r * Math.sin(2 * Math.PI * ((TickCount + 10) % 20) / 20);
                    level.addParticle(ModParticles.MANA_BREAK_Defence.get(), entity.getX() + Dx, entity.getY() + 1.5, entity.getZ() + Dz, 0, 0, 0);
                    if (ClientUtils.ManaDefencePenetrationParticle.get(integer) > 0)
                        ClientUtils.ManaDefencePenetrationParticle.put(integer, ClientUtils.ManaDefencePenetrationParticle.get(integer) - 1);
                }
            });
            ClientUtils.ManaDefencePenetrationParticle.keySet().removeIf(integer -> ClientUtils.ManaDefencePenetrationParticle.get(integer) == 0);

            ClientUtils.SlowDownParticle.keySet().forEach(integer -> {
                Entity entity = level.getEntity(integer);
                if (entity != null) {
                    for (int i = 0; i < 4; i++) {
                        double r = 0.5;
                        double Dx = r * Math.cos(2 * Math.PI * ((TickCount + i * 5) % 20) / 20);
                        double Dz = r * Math.sin(2 * Math.PI * ((TickCount + i * 5) % 20) / 20);
                        level.addParticle(ModParticles.SLOW_DOWN.get(), entity.getX() + Dx, entity.getY() + 0.5, entity.getZ() + Dz, 0, 0, 0);
                    }
                    if (ClientUtils.SlowDownParticle.get(integer) > 0)
                        ClientUtils.SlowDownParticle.put(integer, ClientUtils.SlowDownParticle.get(integer) - 1);
                }
            });
            ClientUtils.SlowDownParticle.keySet().removeIf(integer -> ClientUtils.SlowDownParticle.get(integer) == 0);

            ClientUtils.DamageDecreaseParticle.keySet().forEach(integer -> {
                Entity entity = level.getEntity(integer);
                if (entity != null) {
                    double r = 0.5;
                    double Dx = r * Math.cos(2 * Math.PI * ((TickCount + 5) % 20) / 20);
                    double Dz = r * Math.sin(2 * Math.PI * ((TickCount + 5) % 20) / 20);
                    level.addParticle(ModParticles.DAMAGE_DECREASE.get(), entity.getX() + Dx, entity.getY() + 1.5, entity.getZ() + Dz, 0, 0, 0);
                    if (ClientUtils.DamageDecreaseParticle.get(integer) > 0)
                        ClientUtils.DamageDecreaseParticle.put(integer, ClientUtils.DamageDecreaseParticle.get(integer) - 1);
                }
            });
            ClientUtils.DamageDecreaseParticle.keySet().removeIf(integer -> ClientUtils.DamageDecreaseParticle.get(integer) == 0);

        }
    }


    public static void ParticleDouble(Mob mob, ParticleOptions particleOptions) {
        Vec3 Pos = mob.position();
        int num = 20;
        int i = mob.tickCount % 20;
        double r = 1;
        double angle = (2 * Math.PI / num) * (i);
        double X = Pos.x + Math.cos(angle) * r;
        double Z = Pos.z + Math.sin(angle) * r;
        double Y = Pos.y + 1;
        Vec3 Point = new Vec3(X, Y, Z);
        Vec3 Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 2, Pos.z), 0, 2, 40, particleOptions, false);
        i = 20 - i;
        angle = (2 * Math.PI / num) * (i);
        X = Pos.x + Math.cos(angle) * r;
        Z = Pos.z + Math.sin(angle) * r;
        Y = Pos.y + 1;
        Point = new Vec3(X, Y, Z);
        Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 2, Pos.z), 0, 2, 40, particleOptions, false);
    }

    public static void ParticleFour(Mob mob, ParticleOptions particleOptions0, ParticleOptions particleOptions1) {
        Vec3 Pos = mob.position();
        int num = 20;
        int i = mob.tickCount % 20;
        double r = 1;
        double angle = (2 * Math.PI / num) * (i);
        double X = Pos.x + Math.cos(angle) * r;
        double Z = Pos.z + Math.sin(angle) * r;
        double Y = Pos.y + 1;
        Vec3 Point = new Vec3(X, Y, Z);
        Vec3 Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position,
                new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, particleOptions0, false);
        i += 5;
        angle = (2 * Math.PI / num) * (i);
        X = Pos.x + Math.cos(angle) * r;
        Z = Pos.z + Math.sin(angle) * r;
        Y = Pos.y + 1;
        Point = new Vec3(X, Y, Z);
        Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position,
                new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, particleOptions1, false);
        i += 5;
        angle = (2 * Math.PI / num) * (i);
        X = Pos.x + Math.cos(angle) * r;
        Z = Pos.z + Math.sin(angle) * r;
        Y = Pos.y + 1;
        Point = new Vec3(X, Y, Z);
        Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position,
                new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, particleOptions0, false);
        i += 5;
        angle = (2 * Math.PI / num) * (i);
        X = Pos.x + Math.cos(angle) * r;
        Z = Pos.z + Math.sin(angle) * r;
        Y = Pos.y + 1;
        Point = new Vec3(X, Y, Z);
        Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position,
                new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, particleOptions1, false);
    }

    public static void SkillParticle(Level level, Mob mob, ParticleOptions particleOptions) {
        int num = 160;
        for (int i = 0; i < num; i++) {
            double angle = (2 * Math.PI / num) * (i);
            double X = Math.cos(angle) * 0.5;
            double Y = 0;
            double Z = Math.sin(angle) * 0.5;
            level.addParticle(particleOptions, mob.getX(), mob.getY() + 1.5, mob.getZ(),
                    X, Y, Z);
        }
        for (int i = 0; i < num; i++) {
            double angle = (2 * Math.PI / num) * (i);
            double X = Math.cos(angle) * 0.25;
            double Y = 0;
            double Z = Math.sin(angle) * 0.25;
            level.addParticle(particleOptions, mob.getX(), mob.getY() + 2, mob.getZ(),
                    X, Y, Z);
        }
        for (int i = 0; i < num; i++) {
            double angle = (2 * Math.PI / num) * (i);
            double X = Math.cos(angle) * 0.25;
            double Y = 0;
            double Z = Math.sin(angle) * 0.25;
            level.addParticle(particleOptions, mob.getX(), mob.getY() + 1, mob.getZ(),
                    X, Y, Z);
        }
    }
}

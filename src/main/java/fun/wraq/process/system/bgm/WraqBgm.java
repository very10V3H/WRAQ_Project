package fun.wraq.process.system.bgm;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.process.system.wayPoints.MyWayPoint;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class WraqBgm {

    public static List<SoundEvent> getSoundEvents(String prefix) {
        List<SoundEvent> soundEvents = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            soundEvents
                    .add(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("wraq_sound", prefix + "_" + i)));
        }
        return soundEvents;
    }

    public static SoundEvent getRandomSoundEvent(String prefix) {
        List<SoundEvent> soundEvents = getSoundEvents(prefix);
        return soundEvents.get(RandomUtils.nextInt(0, soundEvents.size()));
    }

    public static final Vec3 SKY_CITY_0 = new Vec3(901, 200, -50);
    public static final Vec3 SKY_CITY_1 = new Vec3(1033, 308, 75);
    public static final Pair<Vec3, Vec3> SKY_CITY = new ImmutablePair<>(SKY_CITY_0, SKY_CITY_1);

    public static final Vec3 LIVING_AREA_0 = new Vec3(705, 182, -76);
    public static final Vec3 LIVING_AREA_1 = new Vec3(822, 239, 61);
    public static final Pair<Vec3, Vec3> LIVING_AREA_YULIN = new ImmutablePair<>(LIVING_AREA_0, LIVING_AREA_1);
    public static final Vec3 LIVING_AREA_2 = new Vec3(1031, 75, -92);
    public static final Vec3 LIVING_AREA_3 = new Vec3(1147, 138, -2);
    public static final Pair<Vec3, Vec3> LIVING_AREA_CLOUD = new ImmutablePair<>(LIVING_AREA_2, LIVING_AREA_3);

    public static boolean inArea(Player player, List<Pair<Vec3, Vec3>> points) {
        return points.stream().anyMatch(point -> {
            return player.getX() > point.getLeft().x
                    && player.getY() > point.getLeft().y
                    && player.getZ() > point.getLeft().z
                    && player.getX() < point.getRight().x
                    && player.getY() < point.getRight().y
                    && player.getZ() < point.getRight().z;
        });
    }

    public static boolean inArea(Player player, List<Vec3> points, double distance) {
        return points.stream().anyMatch(point -> {
            return player.position().distanceTo(point) < distance;
        });
    }

    public static boolean isRain = false;

    public static void handleClientPlayerTick(Player player) {
        if (hasSoundMod() && !ClientUtils.isInBattle && player.level().dimension().equals(Level.OVERWORLD)
                && ClientUtils.clientPlayerTick % Tick.min(10) == Tick.s(10)) {
            if (ClientUtils.clientPlayerTick == Tick.s(10)) {
                player.sendSystemMessage(Te.s("正在播放BGM，音量可在[唱片机/音符盒]调节.", ChatFormatting.AQUA));
            }
            if (player.level().isRaining()) {
                if (!isRain) {
                    isRain = true;
                    playSound(getSoundEvents("rain").get(0), player);
                } else {
                    normalPlay(player);
                }
            } else {
                isRain = false;
                normalPlay(player);
            }
        }
    }

    public static boolean hasSoundMod() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("wraq_sound", "sky_city_1")) != null;
    }

    public static void normalPlay(Player player) {
        if (inArea(player, List.of(SKY_CITY))) {
            playSound(getRandomSoundEvent("sky_city"), player);
        } else if (inArea(player, List.of(MyWayPoint.VillageWayPoint.SAKURA_VILLAGE.pos), 400)) {
            playSound(getRandomSoundEvent("sakura"), player);
        } else if (inArea(player, List.of(LIVING_AREA_YULIN, LIVING_AREA_CLOUD))) {
            playSound(getRandomSoundEvent("living_area"), player);
        } else if (inArea(player, MyWayPoint.VillageWayPoint.getAllVillageWayPoints()
                .stream().map(myWayPoint -> myWayPoint.pos).toList(), 100)) {
            playSound(getRandomSoundEvent("town"), player);
        } else {
            if (RandomUtils.nextInt(0, 100) < 20) {
                playSound(getRandomSoundEvent("special"), player);
            } else {
                playSound(getRandomSoundEvent("jungle"), player);
            }
        }
    }

    public static void playSound(SoundEvent soundEvent, Player player) {
        if (soundEvent == null) {
            return;
        }
        Minecraft.getInstance()
                .getSoundManager()
                .play(new EntityBoundSoundInstance(soundEvent, SoundSource.RECORDS, 1, 1, player, 0));
    }
}

package fun.wraq.process.system.miles;

import fun.wraq.common.fast.Tick;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Miles {
    public static Map<String, Integer> lastComputeTime = new HashMap<>();
    public static Map<String, Integer> nextComputeTime = new HashMap<>();
    public static Map<String, Vec3> lastLocation = new HashMap<>();
    public static Map<String, Vec3> overworldLocation = new HashMap<>();
    public static Map<String, Integer> onGroundTickCounts = new HashMap<>();
    public static Map<String, Integer> inWaterTickCounts = new HashMap<>();
    public static String playerMiles = "playerMiles";

    public static void tick(TickEvent.PlayerTickEvent event) {
        count(event.player);
    }

    public static void count(Player player) {
        String name = player.getName().getString();
        int tick = Tick.get();
        int nextTick = nextComputeTime.getOrDefault(name, 0);
        Vec3 pos = player.position();
        Random random = new Random();
        CompoundTag tag = player.getPersistentData();
        double onGroundRate = 4 / 13d;
        double inSkyRate = 8 / 13d;
        double inWaterRate = 1 / 13d;

        if (tick >= nextTick) {
            int nextComputeTick = tick + random.nextInt(1200, 12000); // 测试 减少时间间隔 统计时发送统计数
            if (!lastLocation.containsKey(name)) {
                lastComputeTime.put(name, tick);
                nextComputeTime.put(name, nextComputeTick);
                lastLocation.put(name, pos);
            } else {
                Vec3 lastPos = lastLocation.get(name);
                int tickLength = tick - lastComputeTime.get(name);
                Vec3 currentPos = overworldLocation.getOrDefault(name, lastPos);
                double distance = Math.sqrt(Math.pow(currentPos.x - lastPos.x, 2) + Math.pow(currentPos.z - lastPos.z, 2));
                int onGroundTick = onGroundTickCounts.getOrDefault(name, 0);
                int inWaterTick = inWaterTickCounts.getOrDefault(name, 0);
                int inSkyTick = tickLength - onGroundTick - inWaterTick;

                double sum = distance * (onGroundRate * onGroundTick + inSkyRate * inSkyTick + inWaterRate * inWaterTick) / tickLength;
                if (!tag.contains(playerMiles)) tag.putString(playerMiles, "0");
                BigInteger bigInteger = new BigInteger(tag.getString(playerMiles));
                bigInteger = bigInteger.add(BigInteger.valueOf((long) sum));
                tag.putString(playerMiles, bigInteger.toString());

                lastComputeTime.put(name, tick);
                nextComputeTime.put(name, nextComputeTick);
                lastLocation.put(name, currentPos);
                onGroundTickCounts.put(name, 0);
                inWaterTickCounts.put(name, 0);
            }
        } else {
            if (player.onGround()) onGroundTickCounts.put(name, onGroundTickCounts.getOrDefault(name, 0) + 1);
            else if (player.isInWater()) onGroundTickCounts.put(name, inWaterTickCounts.getOrDefault(name, 0) + 1);
            if (player.level().dimension().equals(Level.OVERWORLD)) overworldLocation.put(name, pos);
        }
    }

    // 路程奖励
    public static void fromSumReward(Player player, double sum) {

    }
}

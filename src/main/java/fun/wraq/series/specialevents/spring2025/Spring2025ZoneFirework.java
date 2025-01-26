package fun.wraq.series.specialevents.spring2025;

import fun.wraq.process.system.wayPoints.MyWayPoint;
import fun.wraq.series.specialevents.springFes.FireWorkGun;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

public class Spring2025ZoneFirework {
    public static void handleServerPlayerTick(Player player) {
        if (player.level().dimension().equals(Level.OVERWORLD)) {
            MyWayPoint.VillageWayPoint.getAllVillageWayPoints()
                    .stream().filter(myWayPoint -> myWayPoint.pos.distanceTo(player.position()) < 80)
                    .forEach(myWayPoint -> {
                        for (int i = 0; i < 3; i++) {
                            Vec3 offset = new Vec3(RandomUtils.nextInt(0, 160) - 80,
                                    10 + RandomUtils.nextInt(0, 18), RandomUtils.nextInt(0, 160) - 80);
                            Vec3 pos = myWayPoint.pos.add(offset);
                            if (!hasBlock(player.level(), pos)) {
                                FireWorkGun.summonFireWork(player.level(), pos);
                            }
                        }
                    });
        }
    }

    public static boolean hasBlock(Level level, Vec3 pos) {
        BlockPos blockPos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);
        for (int i = 0; i < 20; i++) {
            if (!level.getBlockState(blockPos.above(i)).isAir()) {
                return true;
            }
        }
        return false;
    }
}

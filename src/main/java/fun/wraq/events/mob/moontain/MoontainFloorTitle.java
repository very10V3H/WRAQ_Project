package fun.wraq.events.mob.moontain;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 为玩家提供望山阁楼层提示
 */
public class MoontainFloorTitle {

    private static final int GROUND_FLOOR_Y = 151;

    private static final int EACH_FLOOR_DIFFERENCE = 8;

    private static Map<Player, Integer> playerLastFloorMap = new WeakHashMap<>();

    public static void tick(Player player) {
        if (Compute.isEntityInTwoPoint(player, MoontainEntities.commonDownPos, MoontainEntities.commonUpPos)) {
            int y = (int) player.getY() - GROUND_FLOOR_Y;
            int downY = EACH_FLOOR_DIFFERENCE * (y / EACH_FLOOR_DIFFERENCE);
            int upY = EACH_FLOOR_DIFFERENCE * (y / EACH_FLOOR_DIFFERENCE + 1);
            int nearFloor;
            if (Math.abs(player.getY() - downY) < 2) {
                nearFloor = downY / 8;
            } else {
                nearFloor = upY / 8;
            }
            nearFloor -= 1;
            if (!playerLastFloorMap.containsKey(player) || playerLastFloorMap.get(player) != nearFloor) {
                playerLastFloorMap.put(player, nearFloor);
                Compute.setPlayerTitleAndSubTitle((ServerPlayer) player,
                        Te.s("望山阁" + (nearFloor) + "层", CustomStyle.styleOfMoontain),
                        Te.s(getFloorContent(nearFloor) + "出没", CustomStyle.styleOfMoontain));
            }
        }
    }

    private static String getFloorContent(int floor) {
        switch (floor) {
            case 0, 1, 7 -> {
                return  "望山孤魂";
            }
            case 2, 3, 9 -> {
                return  "望山魂驹";
            }
            case 4, 5, 10 -> {
                return "望山阁灵";
            }
            case 6 -> {
                return "望山武士";
            }
            case 8 -> {
                return "望山骑士";
            }
            case 11 -> {
                return "望山武魂";
            }
        }
        return "";
    }
}

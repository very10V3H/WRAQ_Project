package fun.wraq.process.system.channel;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.violetmoon.quark.content.world.block.MyaliteCrystalBlock;

import java.util.List;

public class SakuraIslandChannel {
    public static List<Vec3> channelNodes = List.of(
            new Vec3(1895, 82, 1687), // 沙岸村经线顶部
            new Vec3(1895, -17, 1687), // 沙岸村经线底部
            new Vec3(1895, -17, 1798), // 鹰眼工厂
            new Vec3(1895, -17, 1902), // 拐点 (北熔线 - 沙鹰线)
            new Vec3(1752, -17, 1902), // 北望村经线底部
            new Vec3(1752, 116, 1902), // 北望村经线顶部
            new Vec3(2404, -17, 1902), // 拐点 (北熔线 - 绯樱线)
            new Vec3(2404, -17, 1752), // 绯樱村经线底部
            new Vec3(2404, 180, 1752), // 绯樱村经线顶部
            new Vec3(1752, -17, 1902) // 拐点 (金古线 - 北沉线)
    );

    public static void onPlayerUse(Player player) {
        if (!(player.getBlockStateOn().getBlock() instanceof MyaliteCrystalBlock)) {
            sendFormatMSG(player, Te.s("需要在节点方块上才能传送"));
            return;
        }
        Vec3 pos = getFacePos(player);
        if (pos.x == -1 && pos.y == -1 && pos.z == -1) {
            sendFormatMSG(player, Te.s("没有找到这个方向的节点"));
            return;
        }
        Vec3 standOn = null;
        for (Vec3 channelNode : channelNodes) {
            if (channelNode.distanceTo(player.position()) < 2) {
                standOn = channelNode;
            }
        }
        if (standOn == null) {
            sendFormatMSG(player, Te.s("离节点太远了"));
            return;
        }
        Vec3 target = null;
        double distance = Double.MAX_VALUE;
        for (Vec3 channelNode : channelNodes) {
            if ((pos.x == 0 || (channelNode.x - standOn.x) * pos.x > 0)
                    && (pos.y == 0 || (channelNode.y - standOn.y) * pos.y > 0)
                    && (pos.z == 0 || (channelNode.z - standOn.z) * pos.z > 0)
                    && !standOn.equals(channelNode)) {
                if (channelNode.distanceTo(standOn) < distance) {
                    distance = channelNode.distanceTo(standOn);
                    target = channelNode;
                }
            }
        }
        if (target == null) {
            sendFormatMSG(player, Te.s("没有找到这个方向的节点"));
            return;
        }
        ((ServerPlayer) player).teleportTo(Tick.server.getLevel(Level.OVERWORLD),
                target.x + 0.5, target.y + 1, target.z + 0.5,
                player.getYRot(), player.getXRot());
    }

    public static Vec3 getFacePos(Player player) {
        if (player.getXRot() < -80) {
            return new Vec3(0, 1, 0); // 向上
        } else if (player.getXRot() > 80) {
            return new Vec3(0, -1, 0); // 向下
        } else if (Math.abs(player.getYRot() - 0) < 20) {
            return new Vec3(0, 0, 1); // 向南
        } else if (Math.abs(Math.abs(player.getYRot()) - 90) < 20) {
            if (player.getYRot() > 0) {
                return new Vec3(-1, 0, 0); // 向西
            } else {
                return new Vec3(1, 0, 0); // 向东
            }
        } else if (Math.abs(Math.abs(player.getYRot()) - 180) < 20) {
            return new Vec3(0, 0, -1); // 向北
        } else {
            return new Vec3(0, 0, 0); // 无效
        }
    }

    public static void sendFormatMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("樱岛隐秘经纬", CustomStyle.styleOfSakura), content);
    }
}

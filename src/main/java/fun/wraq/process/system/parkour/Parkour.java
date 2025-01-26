package fun.wraq.process.system.parkour;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parkour {
    public static List<Vec3> Parkour = new ArrayList<>() {{
        add(new Vec3(425, 89, 756));
        add(new Vec3(424, 92, 701));
        add(new Vec3(425, 92, 639));
        add(new Vec3(425, 92, 579));
        add(new Vec3(425, 92, 491));

        add(new Vec3(426, 92, 441));
        add(new Vec3(426, 98, 403));
        add(new Vec3(426, 101, 361));
        add(new Vec3(426, 101, 324));
        add(new Vec3(426, 101, 295));

        add(new Vec3(418, 112, 287));
        add(new Vec3(426, 127, 286));
        add(new Vec3(481, 128, 285));
        add(new Vec3(518, 128, 285));
        add(new Vec3(552, 128, 284));

        add(new Vec3(552, 128, 264));
        add(new Vec3(522, 128, 193));
        add(new Vec3(473, 130, 193));
        add(new Vec3(467, 177, 190));
        add(new Vec3(406, 164, 193));

        add(new Vec3(392, 166, 193));
        add(new Vec3(309, 158, 193));
        add(new Vec3(261, 160, 193));
        add(new Vec3(86, 184, 193));
        add(new Vec3(7, 184, 193));

        add(new Vec3(44, 184, 157));
        add(new Vec3(44, 184, 119));
        add(new Vec3(135, 129, 119));
        add(new Vec3(170, 133, 104));
    }};

    public static void SetParkourPointNum(Player player, int num) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Parkour.RecordPointNum, num);
    }

    public static void SetParkourLastRewardNum(Player player, int num) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Parkour.LastRewardRecordNum, num);
    }

    public static void AddGetRewardTimes(Player player) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Parkour.GetRewardTimes, data.getInt(StringUtils.Parkour.GetRewardTimes) + 1);
    }

    public static int GetRewardTimes(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getInt(StringUtils.Parkour.GetRewardTimes);
    }

    public static Vec3 FindParkourPoint(Player player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(StringUtils.Parkour.RecordPointNum)) data.putInt(StringUtils.Parkour.RecordPointNum, 0);
        return Parkour.get(data.getInt(StringUtils.Parkour.RecordPointNum));
    }

    public static int GetPlayerCurrentParkourPoint(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getInt(StringUtils.Parkour.RecordPointNum);
    }

    public static int GetPlayerLastRewardNum(Player player) {
        CompoundTag data = player.getPersistentData();
        return data.getInt(StringUtils.Parkour.LastRewardRecordNum);
    }

    public static int FindParkourPointNum(Player player) {
        Vec3 playerPos = player.position();
        for (Vec3 vec3 : Parkour) {
            if (playerPos.distanceTo(vec3) < 2) {
                return Parkour.indexOf(vec3);
            }
        }
        return 0;
    }

    public static void ParkourInitial(Player player) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(StringUtils.Parkour.RecordPointNum)) {
            SetParkourPointNum(player, 0);
            SetParkourLastRewardNum(player, 0);
        }
    }

    public static void ParkourReset(Player player) {
        SetParkourPointNum(player, 0);
        SetParkourLastRewardNum(player, 0);
    }

    public static Item[] rewardItems = {
            ModItems.Ps_Bottle1.get(),
            ModItems.COMPLETE_GEM.get(),
            ModItems.ReputationMedal.get(),
            ModItems.ParkourMedal.get(),
            ModItems.ParkourMedal.get(),
            ModItems.ParkourMedal.get()
    };

    public static void Tick(Player player) throws IOException {
        int TickCount = Tick.get();
        if (TickCount % 5 == 2) {
            int point = FindParkourPointNum(player);
            int currentPoint = GetPlayerCurrentParkourPoint(player);
            int lastRewardPoint = GetPlayerLastRewardNum(player);
            if (point == currentPoint + 1) {
                SetParkourPointNum(player, point);
                Compute.sendFormatMSG(player, Component.literal("跑酷").withStyle(CustomStyle.styleOfFlexible),
                        Component.literal("已经保存记录点！(" + point + ")").withStyle(ChatFormatting.WHITE));
                ModNetworking.sendToClient(new SoundsS2CPacket(6), (ServerPlayer) player);

                if (point % 5 == 0 && lastRewardPoint != point) {
                    SetParkourLastRewardNum(player, GetPlayerCurrentParkourPoint(player));
                    InventoryOperation.giveItemStack(player, rewardItems[GetPlayerCurrentParkourPoint(player) / 5 - 1].getDefaultInstance());
                    Compute.formatBroad(player.level(), Component.literal("跑酷").withStyle(CustomStyle.styleOfFlexible),
                            Component.literal("").withStyle(ChatFormatting.WHITE).
                                    append(player.getDisplayName()).
                                    append(Component.literal(" 完成了一个跑酷阶段!  ").withStyle(CustomStyle.styleOfFlexible)).
                                    append(Component.literal("(" + GetRewardTimes(player) + ")").withStyle(CustomStyle.styleOfFlexible)));
                    AddGetRewardTimes(player);
                    ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                } // 完成阶段 获取奖励

            }
        }
    }

    public static void TpToCurrentParkourPointPos(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        Vec3 pos = FindParkourPoint(player);
        serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD),
                pos.x, pos.y + 1, pos.z, 180, 0);
        Compute.sendFormatMSG(player, Component.literal("跑酷").withStyle(CustomStyle.styleOfFlexible),
                Component.literal("你已回到跑酷记录点/起点，继续你的跑酷吧！").withStyle(ChatFormatting.WHITE));
        serverPlayer.removeAllEffects();
    }

    public static boolean PlayerIsInParkourRange(Player player) {
        Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
        double boundaryX1 = -30, boundaryX2 = 625, boundaryZ1 = 0, boundaryZ2 = 800;
        return player.level().equals(overWorld) && player.getX() > boundaryX1 && player.getX() < boundaryX2
                && player.getZ() > boundaryZ1 && player.getZ() < boundaryZ2 && player.getY() > 80;
    } // MovementSpeed

}

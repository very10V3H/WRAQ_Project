package fun.wraq.process.system.point;

import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.point.network.PointDataS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class Point {

    public static List<Integer> clientData = new ArrayList<>();
    /**
     * 勘探点数
     */
    public static final String EXPT = "EXPT";
    /**
     * 德朗斯蒂克探索点数
     */
    public static final String DSPT = "DSPT";
    /**
     * 艾里蒙特探索点数
     */
    public static final String ELPT = "ELPT";
    /**
     * 绯樱岛探索点数
     */
    public static final String SKPT = "SKPT";
    /**
     * 下界征讨点数
     */
    public static final String NTPT = "NTPT";
    /**
     * 终界征讨点数
     */
    public static final String EDPT = "EDPT";
    /**
     * 暗黑城堡征讨点数
     */
    public static final String BCPT = "BCPT";
    /**
     * 望山探索点数
     */
    public static final String MTPT = "MTPT";
    /**
     * 海洋探索点数
     */
    public static final String OCPT = "OCPT";
    /**
     * 北洋探索点数
     */
    public static final String NOPT = "NOPT";
    /**
     * 考勤点数
     */
    public static final String ATPT = "ATPT";

    public static final Map<String, Component> DESCRIPTION = new LinkedHashMap<>() {{
        put(EXPT, Te.m("探勘点数", CustomStyle.styleOfWorld));
        put(DSPT, Te.m("德朗斯蒂克探索点", CustomStyle.styleOfPlain));
        put(ELPT, Te.m("艾里蒙特探索点", CustomStyle.styleOfMoon));
        put(SKPT, Te.m("绯樱岛探索点", CustomStyle.styleOfSakura));
        put(NTPT, Te.m("下界征讨点数", ChatFormatting.RED));
        put(EDPT, Te.m("终界征讨点数", CustomStyle.styleOfEnd));
        put(BCPT, Te.m("暗黑城堡征讨点数", CustomStyle.styleOfCastleCrystal));
        put(MTPT, Te.m("望山探索点数", CustomStyle.styleOfMoontain));
        put(OCPT, Te.m("海洋探索点数", CustomStyle.styleOfSea));
        put(NOPT, Te.m("北洋探索点", CustomStyle.styleOfIce));
        put(ATPT, Te.m("考勤点数", CustomStyle.styleOfGold));
    }};

    public static final Map<String, Component> TYPE = new LinkedHashMap<>() {{
        put(EXPT, Te.m("EXPT", CustomStyle.styleOfWorld));
        put(DSPT, Te.m("DSPT", CustomStyle.styleOfPlain));
        put(ELPT, Te.m("ELPT", CustomStyle.styleOfMoon));
        put(SKPT, Te.m("SKPT", CustomStyle.styleOfSakura));
        put(NTPT, Te.m("NTPT", ChatFormatting.RED));
        put(EDPT, Te.m("EDPT", CustomStyle.styleOfEnd));
        put(BCPT, Te.m("BCPT", CustomStyle.styleOfCastleCrystal));
        put(MTPT, Te.m("MTPT", CustomStyle.styleOfMoontain));
        put(OCPT, Te.m("OCPT", CustomStyle.styleOfSea));
        put(NOPT, Te.m("NOPT", CustomStyle.styleOfIce));
        put(ATPT, Te.m("ATPT", CustomStyle.styleOfGold));
    }};

    private static final String TAG_KEY = "wraq_point";

    private static CompoundTag getTag(Player player) {
        CompoundTag tag = player.getPersistentData();
        if (!tag.contains(TAG_KEY)) {
            tag.put(TAG_KEY, new CompoundTag());
        }
        return tag.getCompound(TAG_KEY);
    }

    public static void increment(Player player, String type, int num) {
        CompoundTag tag = getTag(player);
        tag.putInt(type, tag.getInt(type) + num);
    }

    public static boolean decrement(Player player, String type, int num) {
        CompoundTag tag = getTag(player);
        if (tag.getInt(type) < num) {
            return false;
        }
        tag.putInt(type, tag.getInt(type) - num);
        return true;
    }

    private static List<Integer> getAllData(Player player) {
        List<Integer> data = new ArrayList<>();
        CompoundTag tag = getTag(player);
        DESCRIPTION.keySet().forEach(key -> {
            data.add(tag.getInt(key));
        });
        return data;
    }

    public static void sendDataToClient(Player player) {
        ModNetworking.sendToClient(new PointDataS2CPacket(getAllData(player)), (ServerPlayer) player);
    }
}

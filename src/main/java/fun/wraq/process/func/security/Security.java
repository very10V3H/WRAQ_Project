package fun.wraq.process.func.security;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.ItemStack;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class Security {

    public static final String SYSTEM = "system";

    public static String getMACAddress(InetAddress ia) throws Exception {
        // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        // 下面代码是把mac地址拼装成String
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            // System.out.println("--------------");
            // System.err.println(s);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }

    public static void recordItemStream(String from, String to, ItemStack itemStack, String type) {
        LogUtils.getLogger().info("ItemStream: " + from + " -> " + to + " " +
                itemStack.getItem() + "*" + itemStack.getCount() + " by " + type);
    }

    public static void recordItemStream(String getPlayerName, ItemStack itemStack, String type) {
        recordItemStream(SYSTEM, getPlayerName, itemStack, type);
    }

    public static void recordVBStream(String from, String to, double value, String type) {
        LogUtils.getLogger().info("VBStream: " + from + " -> " + to + " " +
                value + " by " + type);
    }

    public static void recordVBStream(String getPlayerName, double value, String type) {
        recordVBStream(SYSTEM, getPlayerName, value, type);
    }

    public static void recordGBStream(String from, String to, double value, String type) {
        LogUtils.getLogger().info("GBStream: " + from + " -> " + to + " " +
                value + " by " + type);
    }

    public static void recordGBStream(String getPlayerName, double value, String type) {
        recordGBStream(SYSTEM, getPlayerName, value, type);
    }

    public static void recordVPStream(String from, String to, double value, String type) {
        LogUtils.getLogger().info("VPStream: " + from + " -> " + to + " " +
                value + " by " + type);
    }

    public static void recordVPStream(String getPlayerName, double value, String type) {
        recordVPStream(SYSTEM, getPlayerName, value, type);
    }

    public static void recordToss(String playerName, ItemStack itemStack) {
        recordItemStream(playerName, "GROUND", itemStack, "丢弃物品");
    }

    public static class RecordType {
        public static final String WORLD_SOUL_5_VP_PAY = "vp商店聚星支付";
        public static final String VP_PAY = "vp商店vp支付";
        public static final String TOSS_PICK = "拾取地上物品";
        public static final String BONUS_CHEST_REWARD = "奖励箱获取";
        public static final String SMELT_CANCEL = "取消炼造进程";
        public static final String REST = "休息";
    }
}

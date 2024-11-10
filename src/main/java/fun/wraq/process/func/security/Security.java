package fun.wraq.process.func.security;

import com.mojang.logging.LogUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.unSorted.ClientLimitWrongC2SPacket;
import net.minecraft.world.item.ItemStack;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;

public class Security {

    public static final String SYSTEM = "system";

    public static void ClientPropertiesSet(String string) throws IOException {
        WriteName(string);
    }

    public static void ClientPropertiesCheck(String string) throws IOException {
        if (!ReadName().equals(string) && ReadName() != null)
            ModNetworking.sendToServer(new ClientLimitWrongC2SPacket(ReadName()));
    }

    public static File file;

    public static void Init() throws IOException {
        String dir = System.getProperty("user.home") + "\\McLog.txt";
        file = new File(dir);
        if (file.exists()) file.delete();
        file.createNewFile();
    }

    public static void WriteName(String name) throws IOException {
        Init();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write(name);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static String ReadName() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String strLine = bufferedReader.readLine();
        bufferedReader.close();
        return strLine;
    }

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
    }
}

/** AI-Generated, 2026-10-18 */
package fun.wraq.process.system.mastery;

/**
 * 客户端精通数据缓存，由 {@code MasteryDataS2CPacket} 更新。
 */
public final class MasteryClientData {

    public static int total = 0;
    public static boolean synced = false;
    public static int revision = 0;
    public static int[][] allocation = MasteryAllocation.empty();

    private MasteryClientData() {
    }

    public static int used() {
        return MasteryAllocation.used(allocation);
    }

    public static int available() {
        return Math.max(0, total - used());
    }

    public static void update(int newTotal, int[][] newAllocation) {
        total = Math.max(0, newTotal);
        allocation = MasteryAllocation.copy(newAllocation);
        revision++;
        synced = true;
    }
}

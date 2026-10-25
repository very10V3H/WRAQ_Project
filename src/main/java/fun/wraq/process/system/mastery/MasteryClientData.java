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
    public static final String[] presetNames = new String[MasteryData.PRESET_COUNT];

    static {
        for (int page = 0; page < presetNames.length; page++) {
            presetNames[page] = "配置 " + (page + 1);
        }
    }
    public static int activePresetPage = 0;

    private MasteryClientData() {
    }

    public static int used() {
        return MasteryAllocation.used(allocation);
    }

    public static int available() {
        return Math.max(0, total - used());
    }

    public static String presetName(int page) {
        int index = Math.max(0, Math.min(presetNames.length - 1, page));
        String name = presetNames[index];
        return name == null || name.isBlank() ? "配置 " + (index + 1) : name;
    }

    public static void update(int newTotal, int[][] newAllocation) {
        update(newTotal, newAllocation, presetNames, activePresetPage);
    }

    public static void update(int newTotal, int[][] newAllocation,
                              String[] newPresetNames, int newActivePresetPage) {
        total = Math.max(0, newTotal);
        allocation = MasteryAllocation.copy(newAllocation);
        if (newPresetNames != null) {
            for (int page = 0; page < presetNames.length; page++) {
                presetNames[page] = page < newPresetNames.length && newPresetNames[page] != null
                        && !newPresetNames[page].isBlank()
                        ? newPresetNames[page] : "配置 " + (page + 1);
            }
        }
        activePresetPage = Math.max(0, Math.min(presetNames.length - 1, newActivePresetPage));
        revision++;
        synced = true;
    }
}

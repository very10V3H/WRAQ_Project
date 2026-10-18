/** AI-Generated, 2026-10-17 */
package fun.wraq.process.system.npc;

import fun.wraq.process.system.entrustment.mob.MobKillEntrustment;
import fun.wraq.render.gui.villagerTrade.TradeListNew;
import fun.wraq.series.crystal.OriginStone;
import fun.wraq.series.events.midautumn.MidAutumnStoreRecipe;
import fun.wraq.series.events.summer2025.Summer2025StoreRecipe;
import fun.wraq.series.overworld.cold.sc5.dragon.IceDragonTpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * NPC 对话标识（dialogId）的唯一事实源。
 * <p>
 * 改造前，NPC 的「身份」直接是它的显示名：{@code VillagerEvents} 里 12 个 {@code switch} 分支、
 * {@code DialogTrees} 里的 {@code equals} 全部拿中文字符串比较。后果是改一次文案就静默断链
 * （{@code switch} 没有 default，玩家右键毫无反应且无日志）。
 * <p>
 * 现在：显示名只负责显示，路由一律走这里的常量。
 * 对未补写 id 的存量实体，{@link NpcIdResolver} 会退回「显示名 → id」映射（见 {@link #byDisplayName}），
 * 因此旧村民无需任何数据迁移也能继续工作。
 */
public final class NpcIds {

    // ---- 对话型 NPC ----
    /** 联合研院秘书 - 贝尔（委托任务）。 */
    public static final String ENTRUSTMENT = "entrustment";
    /** 联合银行职员。 */
    public static final String BANK_CLERK = "bank_clerk";
    /** 悦灵学者。 */
    public static final String ALLAY_SCHOLAR = "allay_scholar";
    /** 工匠学者。 */
    public static final String SMITH_SCHOLAR = "smith_scholar";
    /** 逆熵学者。 */
    public static final String NEGENTROPY_SCHOLAR = "negentropy_scholar";
    /** 联合研院大吃货 - 老八（烹饪）。 */
    public static final String LAOBA = "laoba";
    /** 粽子大王。 */
    public static final String ZONGZI_KING = "zongzi_king";
    /** 联合研院采购（每周商店）。 */
    public static final String WEEKLY_STORE = "weekly_store";
    /** 多彩宝石商人（本源石）。 */
    public static final String ORIGIN_STONE = "origin_stone";
    /** 极寒冰龙征服科科长。 */
    public static final String ICE_DRAGON = "ice_dragon";
    /** 暑期活动兑换。 */
    public static final String SUMMER_2025_STORE = "summer2025_store";
    /** 天官 - 饕餮（中秋活动兑换）。 */
    public static final String MID_AUTUMN_STORE = "midautumn_store";

    // ---- 主线任务 NPC ----
    /** 联合研院秘书 - 贝贝（旭升岛轻轨站）。 */
    public static final String MAIN_BEIBEI_STATION = "secretary_beibei_station";
    /** 联合研院秘书 - 贝贝（天空城传送中枢）。 */
    public static final String MAIN_BEIBEI = "secretary_beibei";
    /** 天空城城主 - 索尔。 */
    public static final String MAIN_SOL = "sky_mayor_sol";
    /** 平原村村长 - PP。 */
    public static final String MAIN_PP = "plain_chief_pp";
    /** 雨林村村长 - Chen。 */
    public static final String MAIN_CHEN = "rainforest_chief_chen";
    /** 海岸村村长 - Ushio。 */
    public static final String MAIN_USHIO = "coast_chief_ushio";
    /** 薰曦村 - 铁铁铁铁铁头。 */
    public static final String MAIN_TIETOU = "xunxi_tietou";
    /** 炼金术士 - 辛Gi德。 */
    public static final String MAIN_SINGID = "alchemist_singid";
    /** 北洋村 - 夫夫。 */
    public static final String MAIN_FUFU = "beifu_fufu";
    /** 风之高地 - 索索。 */
    public static final String MAIN_SOSO = "wind_soso";
    /** PM - 托里。 */
    public static final String MAIN_TOLI = "pm_toli";
    /** 樱。 */
    public static final String MAIN_YING = "sakura_ying";
    /** 禁忌 - 慎。 */
    public static final String MAIN_SHEN = "taboo_shen";

    /**
     * 显示名 → dialogId。
     * <p>
     * 仅用于「实体上没写 id」的兼容回退，不应作为新代码的路由依据。
     * 其中的显示名全部走各自的常量引用（编译期常量会被内联，不会触发对方类初始化）。
     */
    private static final Map<String, String> BY_DISPLAY_NAME = new HashMap<>();

    /** dialogId → 默认显示名（创建 NPC 时的名字板默认值，可用 /vmd npc set name 覆盖）。 */
    private static final Map<String, String> DISPLAY_NAME_BY_ID = new HashMap<>();

    static {
        put(MobKillEntrustment.VILLAGER_NAME, ENTRUSTMENT);
        put("联合银行职员", BANK_CLERK);
        put("悦灵学者", ALLAY_SCHOLAR);
        put("工匠学者", SMITH_SCHOLAR);
        put("逆熵学者", NEGENTROPY_SCHOLAR);
        put("联合研院大吃货 - 老八", LAOBA);
        put("粽子大王", ZONGZI_KING);
        put(TradeListNew.WEEKLY_STORE_VILLAGER_NAME, WEEKLY_STORE);
        put(OriginStone.VILLAGER_NAME, ORIGIN_STONE);
        put(IceDragonTpUtil.VILLAGER_NAME, ICE_DRAGON);
        put(Summer2025StoreRecipe.VILLAGER_NAME, SUMMER_2025_STORE);
        put(MidAutumnStoreRecipe.VILLAGER_NAME, MID_AUTUMN_STORE);
        // 主线 NPC：两个贝贝显示名相同，路由以实体上显式写入的 dialogId 为准
        put("秘书 - 贝贝", MAIN_BEIBEI_STATION);
        put("秘书 - 贝贝", MAIN_BEIBEI);
        put("天空城城主 - 索尔", MAIN_SOL);
        put("平原村村长 - PP", MAIN_PP);
        put("雨林村村长 - Chen", MAIN_CHEN);
        put("海岸村村长 - Ushio", MAIN_USHIO);
        put("铁铁铁铁铁头", MAIN_TIETOU);
        put("炼金术士 - 辛Gi德", MAIN_SINGID);
        put("北洋村 - 夫夫", MAIN_FUFU);
        put("风之高地 - 索索", MAIN_SOSO);
        put("PM - 托里", MAIN_TOLI);
        put("樱", MAIN_YING);
        put("禁忌 - 慎", MAIN_SHEN);
    }

    private NpcIds() {
    }

    private static void put(String displayName, String id) {
        BY_DISPLAY_NAME.put(displayName, id);
        DISPLAY_NAME_BY_ID.put(id, displayName);
    }

    /**
     * 按显示名反查 dialogId（兼容回退用）。
     *
     * @param displayName 实体名字板文本（无样式）
     * @return 匹配的 dialogId；未登记返回 {@code null}
     */
    public static String byDisplayName(String displayName) {
        if (displayName == null) {
            return null;
        }
        // 名字板文本可能带前后空白（GM 手输），做一次 trim 兜底
        return BY_DISPLAY_NAME.get(displayName.trim());
    }

    /** 是否为本系统已知的 dialogId。 */
    public static boolean isKnown(String dialogId) {
        return dialogId != null && DISPLAY_NAME_BY_ID.containsKey(dialogId);
    }

    /** 交易类 NPC 的 dialogId 前缀：{@code trade:<显示名>}。 */
    public static final String TRADE_PREFIX = "trade:";

    /** dialogId → 默认显示名；未登记返回 {@code null}。 */
    public static String displayNameOf(String dialogId) {
        if (dialogId == null) {
            return null;
        }
        if (dialogId.startsWith(TRADE_PREFIX)) {
            return dialogId.substring(TRADE_PREFIX.length());
        }
        return DISPLAY_NAME_BY_ID.get(dialogId);
    }

    /**
     * 判定实体显示名对应哪个 dialogId：
     * <ol>
     *     <li>对话型 NPC → {@link #byDisplayName} 的常量 id；</li>
     *     <li>交易型 NPC → {@code trade:<显示名>}（交易系统仍以显示名为键，属既定范围边界）；</li>
     *     <li>都不是 → {@code null}（不是 VMD NPC）。</li>
     * </ol>
     */
    public static String resolveKnown(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            return null;
        }
        String id = byDisplayName(displayName);
        if (id != null) {
            return id;
        }
        return isMerchantName(displayName) ? TRADE_PREFIX + displayName : null;
    }

    /** 该显示名是否是交易系统登记的商人。 */
    public static boolean isMerchantName(String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            return false;
        }
        for (net.minecraft.network.chat.MutableComponent value : fun.wraq.common.util.StringUtils.VillagerNameMap.values()) {
            if (value.getString().equals(displayName)) {
                return true;
            }
        }
        for (net.minecraft.network.chat.MutableComponent value : fun.wraq.render.gui.villagerTrade.MyVillagerData.villagerNameMap.values()) {
            if (value.getString().equals(displayName)) {
                return true;
            }
        }
        return false;
    }

    /** 已登记的全部 dialogId（命令补全用）。 */
    public static java.util.Collection<String> knownIds() {
        return DISPLAY_NAME_BY_ID.keySet();
    }
}

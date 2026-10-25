/** AI-Generated, 2026-10-25 */
package fun.wraq.process.system.mastery;

import java.util.List;

/**
 * 第 6 排基石定义。
 *
 * <p>每条路径三枚基石，顺序与界面从左到右一致；新增基石只在这里追加，
 * 数据容量已经支持每排最多 6 个位置。</p>
 */
public final class MasteryKeystones {

    private static final List<MasteryEffectId> PRECISION = List.of(
            MasteryEffectId.CONQUEROR,
            MasteryEffectId.PRESS_THE_ATTACK,
            MasteryEffectId.LETHAL_TEMPO);

    private static final List<MasteryEffectId> DOMINATION = List.of(
            MasteryEffectId.D_AMBUSH,
            MasteryEffectId.D_TEN_HITS,
            MasteryEffectId.D_THIRD_HIT);

    private static final List<MasteryEffectId> SORCERY = List.of(
            MasteryEffectId.S_NETHERFIRE,
            MasteryEffectId.S_PERIODIC_EMPOWER,
            MasteryEffectId.S_MANA_BURST);

    private static final List<MasteryEffectId> RESOLVE = List.of(
            MasteryEffectId.R_PERIODIC_SHIELD,
            MasteryEffectId.R_MISSING_HEALTH,
            MasteryEffectId.R_UNDYING);

    private static final List<MasteryEffectId> INSPIRATION = List.of(
            MasteryEffectId.I_TRAVELER,
            MasteryEffectId.I_FIRST_STRIKE,
            MasteryEffectId.I_SLOW_IMMUNE);

    private MasteryKeystones() {
    }

    public static List<MasteryEffectId> forTree(MasteryTree tree) {
        return switch (tree) {
            case PRECISION -> PRECISION;
            case DOMINATION -> DOMINATION;
            case SORCERY -> SORCERY;
            case RESOLVE -> RESOLVE;
            case INSPIRATION -> INSPIRATION;
        };
    }

    public static int count(MasteryTree tree) {
        return forTree(tree).size();
    }

    public static MasteryEffectId effect(MasteryTree tree, int index) {
        List<MasteryEffectId> list = forTree(tree);
        return index >= 0 && index < list.size() ? list.get(index) : null;
    }

    public static String name(MasteryTree tree, int index) {
        MasteryEffectId effect = effect(tree, index);
        return effect == null ? "基石" : effect.displayName();
    }
}

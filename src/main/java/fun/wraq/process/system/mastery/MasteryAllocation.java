/** AI-Generated, 2026-10-18 */
package fun.wraq.process.system.mastery;

/**
 * 精通点数的纯计算工具（客户端/服务端共用，不依赖任何 Minecraft 类）。
 *
 * <p>数据结构：每棵树固定 {@link #NODES_PER_TREE} 个 int（6 排 × 6 个位置），
 * 普通排只用前 2-3 个位置，基石排可扩展到 6 个位置；未使用的节点位置保持为 0。
 * 每排的点数上限、节点数见 {@link #ROW_CAP} / {@link #ROW_NODES}。</p>
 *
 * <p>规则：</p>
 * <ul>
 *     <li>一排的总点数不能超过该排上限；</li>
 *     <li>上一排必须点满（等于上限）才能给下一排加点；</li>
 *     <li>同一棵树最多 {@link #TREE_CAP} 点；</li>
 *     <li>所有树合计不能超过玩家总精通点。</li>
 * </ul>
 */
public final class MasteryAllocation {

    public static final int TREE_COUNT = 5;
    public static final int ROW_COUNT = 6;
    /** 每排的数据容量；普通排最多用 3 个，基石排可扩展到 6 个 */
    public static final int MAX_NODES_PER_ROW = 6;
    public static final int NODES_PER_TREE = ROW_COUNT * MAX_NODES_PER_ROW;
    public static final int TREE_CAP = 45;

    /** 第 1 - 6 排的点数上限 */
    public static final int[] ROW_CAP = {10, 5, 10, 5, 10, 5};

    /** 第 1 - 6 排可分配的节点数（第 6 排为基石，按路径在 MasteryKeystones 中定义） */
    public static final int[] ROW_NODES = {3, 2, 3, 2, 3, 3};

    private MasteryAllocation() {
    }

    public static int index(int row, int node) {
        return row * MAX_NODES_PER_ROW + node;
    }

    public static int rowNodeCount(int row) {
        return ROW_NODES[row];
    }

    public static int rowCap(int row) {
        return ROW_CAP[row];
    }

    public static int nodeCountByRow(int row) {
        return ROW_NODES[row];
    }

    public static boolean isValidNode(int row, int node) {
        return row >= 0 && row < ROW_COUNT && node >= 0 && node < ROW_NODES[row];
    }

    public static int[][] empty() {
        return new int[TREE_COUNT][NODES_PER_TREE];
    }

    public static int[][] copy(int[][] source) {
        int[][] copy = empty();
        if (source == null) {
            return copy;
        }
        for (int tree = 0; tree < TREE_COUNT; tree++) {
            if (source.length > tree && source[tree] != null) {
                System.arraycopy(source[tree], 0, copy[tree], 0,
                        Math.min(source[tree].length, NODES_PER_TREE));
            }
        }
        return copy;
    }

    public static int rowTotal(int[] tree, int row) {
        int sum = 0;
        int base = index(row, 0);
        for (int node = 0; node < ROW_NODES[row]; node++) {
            sum += Math.max(0, tree[base + node]);
        }
        return sum;
    }

    public static int treeTotal(int[] tree) {
        int sum = 0;
        for (int row = 0; row < ROW_COUNT; row++) {
            sum += rowTotal(tree, row);
        }
        return sum;
    }

    public static int used(int[][] allocation) {
        int sum = 0;
        for (int tree = 0; tree < TREE_COUNT; tree++) {
            sum += treeTotal(allocation[tree]);
        }
        return sum;
    }

    /** 第 row 排是否已解锁（第一排始终解锁，其余排需要上一排点满） */
    public static boolean rowUnlocked(int[] tree, int row) {
        return row == 0 || rowTotal(tree, row - 1) == ROW_CAP[row - 1];
    }

    /** 清空第 row 排以下的全部节点（保留 row 本排） */
    public static void clearBelow(int[] tree, int row) {
        for (int r = row + 1; r < ROW_COUNT; r++) {
            int base = index(r, 0);
            for (int node = 0; node < MAX_NODES_PER_ROW; node++) {
                tree[base + node] = 0;
            }
        }
    }

    /** 第 row 排以下的点数合计 */
    public static int pointsBelow(int[] tree, int row) {
        int sum = 0;
        for (int r = row + 1; r < ROW_COUNT; r++) {
            sum += rowTotal(tree, r);
        }
        return sum;
    }

    /**
     * 服务端权威校验：把任意输入整理成合法配置。
     * 规则：节点非负、每排不超上限、上一排不满则清空其下所有排、总点数不超过 budget。
     */
    public static int[][] sanitize(int[][] input, int budget) {
        int[][] result = copy(input);
        for (int tree = 0; tree < TREE_COUNT; tree++) {
            int[] points = result[tree];
            for (int i = 0; i < NODES_PER_TREE; i++) {
                int row = i / MAX_NODES_PER_ROW;
                int node = i % MAX_NODES_PER_ROW;
                int allowed = row == ROW_COUNT - 1
                        ? MasteryKeystones.count(MasteryTree.byIndex(tree))
                        : ROW_NODES[row];
                if (node >= allowed) {
                    points[i] = 0;
                } else {
                    points[i] = Math.max(0, Math.min(ROW_CAP[row], points[i]));
                }
            }
            for (int row = 0; row < ROW_COUNT; row++) {
                if (row > 0 && !rowUnlocked(points, row)) {
                    clearBelow(points, row - 1);
                    break;
                }
                trimRow(points, row);
            }
        }
        int used = used(result);
        if (used > budget) {
            // 从最后一棵树的最后一排开始回收点数：先清高空闲的高排，不会破坏低排的前置条件
            for (int tree = TREE_COUNT - 1; tree >= 0 && used > budget; tree--) {
                int[] points = result[tree];
                for (int i = NODES_PER_TREE - 1; i >= 0 && used > budget; i--) {
                    while (points[i] > 0 && used > budget) {
                        points[i]--;
                        used--;
                    }
                }
            }
        }
        return result;
    }

    private static void trimRow(int[] tree, int row) {
        int base = index(row, 0);
        int sum = rowTotal(tree, row);
        for (int node = ROW_NODES[row] - 1; node >= 0 && sum > ROW_CAP[row]; node--) {
            int reduce = Math.min(tree[base + node], sum - ROW_CAP[row]);
            tree[base + node] -= reduce;
            sum -= reduce;
        }
    }

    /** 第 row 排的显示名 */
    public static String rowName(int row) {
        return switch (row) {
            case 0 -> "第一排";
            case 1 -> "第二排";
            case 2 -> "第三排";
            case 3 -> "第四排";
            case 4 -> "第五排";
            default -> "基石（强力）";
        };
    }

    /** 单个节点的显示名（占位，不含具体效果） */
    public static String nodeName(int row, int node) {
        if (row == ROW_COUNT - 1) {
            return "基石 " + roman(node + 1);
        }
        return "精通 " + roman(node + 1);
    }

    public static String roman(int value) {
        return switch (value) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            default -> String.valueOf(value);
        };
    }
}

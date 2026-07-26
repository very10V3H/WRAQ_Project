package fun.wraq.common.impl.display;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ForgeItem {
    List<ItemStack> forgeRecipe();

    /**
     * @return 该装备可被锻造的区域坐标边界列表。
     * 返回空列表表示通过其他方式（如 ForgeEquipUtils 内的静态 Map）确定锻造区域。
     * 实现类可返回 {@link fun.wraq.process.system.forge.ForgeEquipUtils} 中定义的区域常量，
     * 例如 {@code List.of(ForgeEquipUtils.PLAIN_VILLAGE)}。
     */
    default List<Zone> forgeZones() {
        return List.of();
    }

    /**
     * 锻造区域矩形坐标边界。
     * 玩家位于 boundaryX2 < X < boundaryX1 且 boundaryZ2 < Z < boundaryZ1 时，
     * 可以锻造属于该区域的装备。
     */
    record Zone(int boundaryX1, int boundaryZ1, int boundaryX2, int boundaryZ2) {}
}

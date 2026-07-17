package fun.wraq.common.impl.onshoot;

import fun.wraq.common.equip.WraqCurios;
import net.minecraft.world.entity.player.Player;

public interface OnReleaseSkillCurios {
    void onReleaseSkill(Player player);

    static void release(Player player) {
        WraqCurios.CuriosAttribute.getDistinctCuriosList(player).stream()
                .filter(stack -> stack.getItem() instanceof OnReleaseSkillCurios)
                .map(stack -> (OnReleaseSkillCurios) stack.getItem())
                .forEach(curios -> curios.onReleaseSkill(player));
    }
}

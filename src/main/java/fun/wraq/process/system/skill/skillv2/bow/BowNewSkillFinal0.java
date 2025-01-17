package fun.wraq.process.system.skill.skillv2.bow;

import fun.wraq.common.fast.Te;
import fun.wraq.process.system.skill.skillv2.SkillV2FinalSkill;
import fun.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class BowNewSkillFinal0 extends SkillV2FinalSkill {

    public BowNewSkillFinal0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected List<Component> getUpgradeConditionDescription() {
        return List.of();
    }

    @Override
    protected void upgradeOperation(Player player) {

    }

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        WraqQuiver.batchAddExShoot(player, skillLevel * 0.1, 10);
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("向前方射出多支箭矢"));
        return components;
    }
}

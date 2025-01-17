package fun.wraq.process.system.skill.skillv2.bow;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2PassiveSkill;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class BowNewSkillPassive0 extends SkillV2PassiveSkill {

    public BowNewSkillPassive0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
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
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("普攻命中目标，将使目标受到任意来源的伤害提升1%"));
        components.add(Te.s("持续5s，至多可叠加至10层"));
        return components;
    }

    public static void onArrowHit(Player player, Mob mob) {
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 0);
        if (skillV2 instanceof BowNewSkillPassive0) {
            StableTierAttributeModifier.addM(mob, StableTierAttributeModifier.monsterWithstandDamageEnhance,
                    "bowNewSkillPassive0WithstandDamageEnhance", 0.01,
                    Tick.get() + 100, 10, skillV2.getTexture1Url());
        }
    }
}

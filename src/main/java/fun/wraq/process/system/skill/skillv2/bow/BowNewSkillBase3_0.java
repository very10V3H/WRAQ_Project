package fun.wraq.process.system.skill.skillv2.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class BowNewSkillBase3_0 extends SkillV2BaseSkill {

    public BowNewSkillBase3_0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
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
        Compute.sendForwardMotionPacketToPlayer(player, 1);
        effectExpiredTickMap.put(player, Tick.get() + Tick.s(5));
        Compute.sendEffectLastTime(player, getTexture1Url(), Tick.s(5), 0, false);
    }

    public static Map<Player, Integer> effectExpiredTickMap = new WeakHashMap<>();

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("向前突进，并获得持续5s的箭矢穿透效果"));
        return components;
    }
}

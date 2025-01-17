package fun.wraq.process.system.skill.skillv2.sword;

import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2PassiveSkill;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SwordNewSkillPassive0 extends SkillV2PassiveSkill {

    public SwordNewSkillPassive0(int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected List<Component> getUpgradeConditionDescription() {
        return List.of();
    }

    @Override
    protected boolean canUpgrade(Player player) {
        boolean hasEnoughGoldenCoin = InventoryOperation.checkPlayerHasItem(player, ModItems.GOLD_COIN.get(), 1);
        return super.canUpgrade(player) && hasEnoughGoldenCoin;
    }

    @Override
    protected void upgradeOperation(Player player) {
        InventoryOperation.removeItem(player, ModItems.GOLD_COIN.get(), 1);
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("提升普通攻击非主要目标的伤害"));
        return components;
    }

    public static double exTargetsDamageEnhance(Player player) {
        SkillV2 skillV2 = SkillV2.getPlayerCurrentSkillByType(player, 0);
        if (skillV2 instanceof SwordNewSkillPassive0) {
            int skillLevel = SkillV2.getPlayerSkillLevelBySkillV2(player, skillV2);
            return skillLevel * 0.1;
        }
        return 0;
    }
}

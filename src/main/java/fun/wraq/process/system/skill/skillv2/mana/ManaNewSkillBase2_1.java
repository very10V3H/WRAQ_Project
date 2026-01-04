package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.uniform.mana.normal.ManaCurios5;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.skill.skillv2.SkillV2AllowReleaseAnyTime;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.process.system.skill.skillv2.bow.BowNewSkillBase2_1;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ManaNewSkillBase2_1 extends SkillV2BaseSkill implements SkillV2AllowReleaseAnyTime {
    public ManaNewSkillBase2_1(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        Mob mob = Compute.getDefaultTarget(player, 16 * (1 + ManaCurios5.getExSkillRangeRate(player)));
        if (mob == null) {
            mob = Compute.getNearestMob(player, 16 * (1 + ManaCurios5.getExSkillRangeRate(player)));
        }
        if (mob == null) {
            return;
        }

        int level = getPlayerSkillLevel(player);
        double rate = 5 + 0.5 * level;
        Damage.causeRateApDamageWithElement(player, mob,
                rate * (1 + ManaCurios5.getExBaseDamageRate(player, mob)), true);
        ParticleProvider.createLineParticle(player.level(), (int) (mob.distanceTo(player) * 5),
                player.getEyePosition(), mob.getEyePosition(), ParticleTypes.WITCH);

        StableAttributesModifier.addM(mob, StableAttributesModifier.mobPercentManaDefenceModifier,
                "manaNewSkillBase2_1", -0.2, Tick.get() + Tick.s(5));
        Compute.addSlowDownEffect(mob, Tick.s(5), 1);
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("发射一支", "魔法箭矢", CustomStyle.styleOfMana, "造成",
                getRateDescription(5, 0.5, level), CustomStyle.styleOfMana, "伤害"));
        components.add(Te.s("造成", "减速", CustomStyle.styleOfStone, "并降低敌人",
                ComponentUtils.AttributeDescription.manaDefence("20%"), " 持续5s."));
        return components;
    }

    @Override
    protected boolean canUpgrade(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        return InventoryOperation.checkPlayerHasItem(player, getUpgradeMaterials(skillLevel))
                && BowNewSkillBase2_1.canUpgradeExJudge(player, skillLevel);
    }

    @Override
    protected List<Component> getUpgradeConditionDescription(int skillLevel) {
        return BowNewSkillBase2_1.getNewUpgradeConditionDescription(skillLevel, getUpgradeMaterials(skillLevel));
    }

    @Override
    protected List<ItemStack> getUpgradeMaterials(int skillLevel) {
        return BowNewSkillBase2_1.getNewUpgradeMaterials(skillLevel);
    }
}

package fun.wraq.process.system.skill.skillv2.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.endlessinstance.item.EndlessInstanceItems;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2PassiveSkill;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import fun.wraq.series.overworld.chapter7.C7Items;
import fun.wraq.series.overworld.extraordinary.ExtraordinaryItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BowNewSkillBase2_1 extends SkillV2PassiveSkill {
    public BowNewSkillBase2_1(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("箭矢命中敌人会形成小范围爆炸，造成",
                getRateDescription(0.2, 0.02, level) + "物理伤害", ChatFormatting.YELLOW));
        components.add(Te.s(ComponentUtils.getCritDamageInfluenceDescription()));
        components.add(Te.s("受到范围爆炸影响的敌人，会削减",
                ComponentUtils.AttributeDescription.defence("1"),
                ComponentUtils.AttributeDescription.manaDefence("1")));
        components.add(Te.s("至多可削减", 10 + 5 * level, CustomStyle.styleOfFlexible, "的双抗."));
        return components;
    }

    public static void onArrowHit(Player player, Mob mob) {
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 2);
        if (!(skillV2 instanceof BowNewSkillBase2_1)) {
            return;
        }

        int level = skillV2.getPlayerSkillLevel(player);
        double rate = 0.2 + 0.02 * level;

        Compute.getNearMob(mob, 2).forEach(eachMob -> {
            Damage.causeRateAdDamageToMonsterWithCritJudge(player, eachMob, rate);
            StableTierAttributeModifier.addM(eachMob, StableTierAttributeModifier.mobDefence,
                    "BowNewSkillBase2_1", -1, Tick.get() + Tick.s(10), 10 + 5 * level,
                    "skills/v2/bow/bow2_1");
            StableTierAttributeModifier.addM(eachMob, StableTierAttributeModifier.mobManaDefence,
                    "BowNewSkillBase2_1", -1, Tick.get() + Tick.s(10), 10 + 5 * level);
        });
        ParticleProvider.createSingleParticleToNearPlayer(player, mob.level(), mob.getEyePosition(),
                ParticleTypes.EXPLOSION);
    }

    @Override
    protected boolean canUpgrade(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        return InventoryOperation.checkPlayerHasItem(player, getUpgradeMaterials(skillLevel, professionType))
                && canUpgradeExJudge(player, skillLevel);
    }

    public static boolean canUpgradeExJudge(Player player, int level) {
        return player.experienceLevel >= Math.min(80 + (level + 1) * 20, 300);
    }

    @Override
    protected List<Component> getUpgradeConditionDescription(int skillLevel) {
        return getNewUpgradeConditionDescription(skillLevel, getUpgradeMaterials(skillLevel, 1));
    }

    public static List<Component> getNewUpgradeConditionDescription(int skillLevel, List<ItemStack> upgradeNeedMaterial) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("1.", CustomStyle.styleOfWorld, "达到",
                Utils.getLevelDescription(Math.min(80 + (skillLevel + 1) * 20, 300))));
        for (int i = 0; i < upgradeNeedMaterial.size(); i++) {
            ItemStack itemStack = upgradeNeedMaterial.get(i);
            components.add(Te.s((i + 2) + ".", CustomStyle.styleOfWorld,
                    itemStack, " * " + itemStack.getCount(), ChatFormatting.AQUA));
        }
        return components;
    }

    @Override
    protected List<ItemStack> getUpgradeMaterials(int skillLevel, int professionType) {
        return getNewUpgradeMaterials(skillLevel);
    }

    public static List<ItemStack> getNewUpgradeMaterials(int level) {
        switch (level) {
            case -1 -> {
                return List.of(
                        new ItemStack(ModItems.NETHER_SKELETON_RUNE.get(), 4),
                        new ItemStack(ModItems.PIGLIN_RUNE.get(), 4),
                        new ItemStack(ModItems.WITHER_RUNE.get(), 4),
                        new ItemStack(ModItems.MAGMA_RUNE.get(), 4)
                );
            }
            case 0 -> {
                return List.of(
                        new ItemStack(ModItems.NETHER_RUNE.get(), 2),
                        new ItemStack(ModItems.QUARTZ_RUNE.get(), 2)
                );
            }
            case 1 -> {
                return List.of(
                        new ItemStack(ModItems.PURPLE_IRON_WEAPON_PIECE.get(), 1),
                        new ItemStack(ModItems.BOND.get(), 1)
                );
            }
            case 2 -> {
                return List.of(
                        new ItemStack(ModItems.LIGHTNING_RUNE.get(), 16),
                        new ItemStack(ModItems.REFINED_PIECE.get(), 16)
                );
            }
            case 3 -> {
                return List.of(
                        new ItemStack(ModItems.HUSK_RUNE.get(), 8),
                        new ItemStack(EndlessInstanceItems.MANA_PLAIN_PLANT.get(), 16)
                );
            }
            case 4 -> {
                return List.of(
                        new ItemStack(ModItems.BIG_SLIME_BALL.get(), 8),
                        new ItemStack(ModItems.REPUTATION_MEDAL.get(), 4)
                );
            }
            case 5 -> {
                return List.of(
                        new ItemStack(ModItems.ICE_COMPLETE_GEM.get(), 1),
                        new ItemStack(ModItems.RANDOM_EVENT_MEDAL.get(), 4)
                );
            }
            case 6 -> {
                return List.of(
                        new ItemStack(ModItems.MOON_COMPLETE_GEM.get(), 1),
                        new ItemStack(ModItems.GOLDEN_BEANS.get(), 8)
                );
            }
            case 7 -> {
                return List.of(
                        new ItemStack(ModItems.DEVIL_BLOOD.get(), 2),
                        new ItemStack(ModItems.EARTH_MANA_RUNE.get(), 4),
                        new ItemStack(ModItems.BLOOD_MANA_RUNE.get(), 4)
                );
            }
            case 8 -> {
                return List.of(
                        new ItemStack(C7Items.VD_SOUL.get(), 256),
                        new ItemStack(C7Items.BONE_IMP_SOUL.get(), 256)
                );
            }
            case 9 -> {
                return List.of(
                        new ItemStack(MushroomItems.NETHER_MUSHROOM.get()),
                        new ItemStack(HarbingerItems.SAKURA_INDUSTRY_INGOT.get())
                );
            }
            case 10 -> {
                return List.of(
                        new ItemStack(ExtraordinaryItems.DAZZLING_DIAMOND.get())
                );
            }
            case 11 -> {
                return List.of(
                        new ItemStack(ExtraordinaryItems.DAZZLING_DIAMOND.get(), 2)
                );
            }
        }
        return List.of(new ItemStack(ExtraordinaryItems.DAZZLING_DIAMOND.get(), 64));
    }
}

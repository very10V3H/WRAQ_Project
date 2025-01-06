package fun.wraq.process.system.profession.pet.allay.skill;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.profession.pet.allay.AllayPetPlayerData;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.allay.Allay;

public class AllaySkills {

    public static final Component ATTACK_LEVEL_DESCRIPTION = Te.s("每秒对最近的怪物造成",
            ComponentUtils.getAutoAdaptDamageDescription("10% * 技能等级"));
    // 每秒最高200%自适应伤害
    public static void tickOfAttack(Allay allay, ServerPlayer serverPlayer, Mob mob) {
        int attackSkillTier = AllayPetPlayerData.getSkillLevel(serverPlayer, AllayPetPlayerData.ATTACK_LEVEL_KEY);
        Damage.causeAutoAdaptionRateDamageToMob(serverPlayer, mob, attackSkillTier * 0.1, false);
        ParticleProvider.createLineEffectParticle(serverPlayer.level(), (int) mob.distanceTo(allay) * 5,
                allay.getEyePosition(), mob.getEyePosition(), CustomStyle.styleOfWorld);
    }

    public static final Component HEALING_LEVEL_DESCRIPTION = Te.s("每2秒为你回复",
            ComponentUtils.AttributeDescription.health("40 + 0.5 * 技能等级 * 玩家等级"));
    // 每2秒回复 40 + 0.5 * 技能等级 * 等级生命值
    public static void healing(Allay allay, ServerPlayer serverPlayer) {
        int healingSkillTier = AllayPetPlayerData.getSkillLevel(serverPlayer, AllayPetPlayerData.HEALING_LEVEL_KEY);
        if (allay.distanceTo(serverPlayer) < 6) {
            Compute.playerHeal(serverPlayer, 40 + 0.5 * healingSkillTier * serverPlayer.experienceLevel);
            ParticleProvider.createLineParticle(serverPlayer.level(),
                    (int) allay.distanceTo(serverPlayer) * 5, allay.getEyePosition(),
                    serverPlayer.getEyePosition().add(0, -1, 0), ParticleTypes.COMPOSTER);
        }
    }

    public static final Component GEM_PIECE_LEVEL_DESCRIPTION = Te.s("击杀怪物时，", CustomStyle.styleOfRed,
            "0.5% + 0.05% * 技能等级", ChatFormatting.LIGHT_PURPLE, "的概率为你提供", ModItems.GEM_PIECE);
    // 只要持有悦灵，都将为玩家提供0.5% + 0.05% * 等级的额外水晶碎片掉率
    public static double getExGemPieceRate(ServerPlayer serverPlayer) {
        if (AllayPetPlayerData.getAllayXpLevel(serverPlayer) < 1) return 0;
        return 0.01
                * (0.5 + 0.05 * AllayPetPlayerData.getSkillLevel(serverPlayer, AllayPetPlayerData.GEM_PIECE_LEVEL_KEY));
    }
}

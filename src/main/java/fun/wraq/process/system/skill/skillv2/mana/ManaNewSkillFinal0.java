package fun.wraq.process.system.skill.skillv2.mana;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.customized.uniform.mana.normal.ManaCurios5;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2AllowReleaseAnyTime;
import fun.wraq.process.system.skill.skillv2.SkillV2FinalSkill;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class ManaNewSkillFinal0 extends SkillV2FinalSkill implements SkillV2AllowReleaseAnyTime {

    public ManaNewSkillFinal0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    public static Map<Player, Integer> countMap = new WeakHashMap<>();
    public static Map<Player, Integer> nextReleaseTickMap = new WeakHashMap<>();
    public static Map<Player, Double> rateMap = new WeakHashMap<>();
    public static Map<Player, Vec3> targetPosMap = new WeakHashMap<>();

    @Override
    protected void releaseOperation(Player player) {
        countMap.put(player, 3);
        nextReleaseTickMap.put(player, Tick.get() + 1);
        rateMap.put(player, ManaNewSkill.modifyDamage(player,
                5 + getPlayerSkillLevel(player) * 0.5) * (1 + getEnhanceRate(player)));
        Vec3 targetPos = WraqPower.getDefaultTargetPos(player);
        targetPosMap.put(player, targetPos);
        ParticleProvider.createLineDustParticleFromRightHand(player, targetPos,
                Element.getManaSkillParticleStyle(player));
    }

    public static void handleServerPlayerTickEvent(ServerPlayer player) {
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 4);
        if (skillV2 instanceof ManaNewSkillFinal0 && countMap.getOrDefault(player, 0) > 0
                && nextReleaseTickMap.getOrDefault(player, 0) == Tick.get()
                && targetPosMap.containsKey(player)) {
            countMap.compute(player, (k, v) -> v == null ? 0 : v - 1);
            nextReleaseTickMap.put(player, Tick.get() + 3);
            Vec3 pos = targetPosMap.get(player);
            MySound.soundToNearPlayer(player.level(), pos, SoundEvents.DRAGON_FIREBALL_EXPLODE);
            ParticleProvider.createSingleParticleToNearPlayer(player, player.level(), pos,
                    ParticleTypes.EXPLOSION_EMITTER);
            ParticleProvider.createRandomMoveParticle(player, pos, 1, 1, 6, ParticleTypes.ASH);
            ParticleProvider.dustParticle(player, pos, 6 * (1 + ManaCurios5.getExSkillRangeRate(player)), 80,
                    Element.getManaSkillParticleStyle(player).getColor().getValue());
            double radius = 10 * (1 + ManaCurios5.getExSkillRangeRate(player));
            player.level().getEntitiesOfClass(Mob.class, AABB.ofSize(pos, radius, radius, radius))
                    .forEach(eachMob -> {
                        Damage.causeRateApDamageWithElement(player, eachMob,
                                rateMap.getOrDefault(player, 0d)
                                        * (1 + ManaCurios5.getExBaseDamageRate(player, eachMob)), true);
                        ManaNewSkillPassive0.onManaPowerHit(player, eachMob);
                    });
            Element.giveResonanceElement(player);
        }
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("连续轰炸准星区域", "3次", CustomStyle.styleOfMana));
        components.add(Te.s("爆炸会对其范围内的所有目标造成",
                getRateDescription(5, 0.5, level), CustomStyle.styleOfMana, "伤害"));
        components.add(Te.s("并额外引爆一层", " 渗", CustomStyle.styleOfMana));
        components.add(Te.s("每触发一次", " 渗", CustomStyle.styleOfMana,
                "，将减少该技能", "0.5s冷却时间", ChatFormatting.AQUA));
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 30;
    }
}

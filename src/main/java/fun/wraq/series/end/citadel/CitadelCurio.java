package fun.wraq.series.end.citadel;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onhit.OnHitEffectCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class CitadelCurio extends WraqCurios implements OnHitEffectCurios, Decomposable {

    private final int tier;
    public CitadelCurio(Properties properties, int tier) {
        super(properties);
        Utils.expUp.put(this, new double[]{0.44, 0.66, 0.88, 1.11}[tier]);
        Utils.levelRequire.put(this, 215);
        this.tier = tier;
        citadelCurios.add(this);
    }

    private final double[] rate = new double[]{0.08, 0.12, 0.16, 0.2};

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionActive(components, Te.s("影割", hoverMainStyle()));
        components.add(Te.s(" 普攻", CustomStyle.styleOfStone, "将使一名",
                "主要目标", ChatFormatting.AQUA, "陷入", "「割裂」", hoverMainStyle()));
        components.add(Te.s(" 「割裂」", hoverMainStyle(), "会将其受到的技能与普攻伤害",
                "影射", hoverMainStyle(), "至周围敌人"));
        components.add(Te.s(" 影射", CustomStyle.styleOfEnd, "的效率为: ",
                String.format("%.0f%%", rate[tier] * 100), CustomStyle.styleOfEnd));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfEnd;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfEnd();
    }

    public static Map<String, Mob> playerTargetMap = new HashMap<>();
    public static Map<Mob, Boolean> mobEffectMap = new WeakHashMap<>();

    @Override
    public void onHit(Player player, Mob mob) {
        if (!playerTargetMap.containsKey(Name.get(player))) {
            playerTargetMap.put(Name.get(player), mob);
            mobEffectMap.put(mob, true);
            Compute.sendMobEffectHudToNearPlayer(mob,
                    "item/citadel_curio", "citadel curio passive", 0, 0, true);
        } else {
            Mob oldMob = playerTargetMap.get(Name.get(player));
            if (!oldMob.equals(mob)) {
                mobEffectMap.remove(oldMob);
                Compute.removeMobEffectHudToNearPlayer(oldMob, "item/citadel_curio", "citadel curio passive");
                Compute.sendMobEffectHudToNearPlayer(mob, "item/citadel_curio",
                        "citadel curio passive", 0, 0, true);
                playerTargetMap.put(Name.get(player), mob);
                mobEffectMap.put(mob, true);
            }
        }
    }

    public static List<Item> citadelCurios = new ArrayList<>();

    public static void onNormalAttackOrSkillHit(Player player, Mob mob, double damage, boolean isAd) {
        Compute.CuriosAttribute.getClientCuriosSet(player).stream()
                .filter(curio -> citadelCurios.contains(curio))
                .findAny()
                .ifPresent(citadelCurio -> {
                    CitadelCurio curio = (CitadelCurio) citadelCurio;
                    if (player.experienceLevel < Utils.levelRequire.get(curio)) {
                        return;
                    }
                    double damageRate = curio.rate[curio.tier];
                    if (mobEffectMap.containsKey(mob)) {
                        Compute.getNearEntity(mob, Mob.class, 10)
                                .stream().map(entity -> (Mob) entity)
                                .forEach(eachMob -> {
                                    if (!eachMob.equals(mob) && eachMob.isAlive()) {
                                        if (isAd) {
                                            Damage.causeAttackDamageToMonster(player,
                                                    eachMob, damage * damageRate);
                                        } else {
                                            Damage.causeManaDamageToMonster(player,
                                                    eachMob, damage * damageRate);
                                        }
                                        ParticleProvider.createLineEffectParticle(player.level(),
                                                (int) eachMob.distanceTo(player) * 5, player.getEyePosition(),
                                                eachMob.getEyePosition(), CustomStyle.styleOfEnd);
                                    }
                                });
                    }
        });
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(CitadelItems.CITADEL_PIECE.get(), 10);
    }
}

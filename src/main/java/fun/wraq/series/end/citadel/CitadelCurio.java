package fun.wraq.series.end.citadel;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.PlayerHashMap;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.impl.onkill.OnKillEffectCurios;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class CitadelCurio extends WraqCurios implements Decomposable, OnKillEffectCurios,
        InCuriosOrEquipSlotAttributesModify {

    public static List<Item> citadelCurios = new ArrayList<>();

    private final int tier;
    public CitadelCurio(Properties properties, int tier) {
        super(properties);
        Utils.expUp.put(this, new double[]{0.44, 0.66, 0.88, 1.11}[tier]);
        Utils.levelRequire.put(this, 215);
        this.tier = tier;
        citadelCurios.add(this);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    private int getRange() {
        return new int[]{6, 7, 8, 9}[tier];
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Component fadeShade = Te.s("虚影", hoverMainStyle());
        ComponentUtils.descriptionPassive(components, Te.s("击杀怪物时，在其位置留下", fadeShade, "."));
        components.add(Te.s(" 最多创造10个", fadeShade, "."));
        components.add(Te.s(" ", fadeShade, "每秒对", "主要目标", ChatFormatting.ITALIC,
                "造成", ComponentUtils.getAutoAdaptDamageDescription("10%")));
        // 饰品等阶对最大攻击范围/主要目标选定范围有影响
        components.add(Te.s(" ", fadeShade, "的最大攻击范围为: ", 16, hoverMainStyle(), "."));
        components.add(Te.s(" ", "主要目标", ChatFormatting.ITALIC, "为最后一个受到普攻的目标."));
        components.add(Te.s(" ", "如主要目标死亡，则选定", getRange() + "格内", hoverMainStyle(), "距离你最近的敌人."));
        ComponentUtils.descriptionPassive(components, Te.s("归终之石", hoverMainStyle()));
        components.add(Te.s(" 当你受到", "致命伤害", ChatFormatting.RED, "时,")); // 向玩家播放凋零生成音效
        components.add(Te.s(" 为你回复", ComponentUtils.AttributeDescription.health("100%")));
        components.add(Te.s(" 在接下来的每30s:"));
        components.add(Te.s(" · Phase 1: ", hoverMainStyle(), "每秒损失10%生命值", "+20%ad/ap", "+30%治疗强度"));
        components.add(Te.s(" · Phase 2: ", hoverMainStyle(), "每秒损失15%生命值", "+30%ad/ap", "+30%治疗强度", "+30%双穿"));
        components.add(Te.s(" · Phase 3: ", hoverMainStyle(), "每秒损失20%生命值", "+40%ad/ap", "+40%治疗强度", "+无视怪物双抗"));
        components.add(Te.s(" 90s后保持", "Phase 3", hoverMainStyle(), "，但无法获得任何治疗效果."));
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

    @Override
    public ItemStack getProduct() {
        return new ItemStack(CitadelItems.CITADEL_PIECE.get(), 10);
    }

    private static PlayerHashMap<Queue<Vec3>> fadeShadePos = new PlayerHashMap<>();

    private static PlayerHashMap<Integer> nearDeadStatusStartTick = new PlayerHashMap<>();

    private int getPhase(Player player) {
        if (nearDeadStatusStartTick.withoutKey(player)) {
            return -1;
        }

        int startTick = nearDeadStatusStartTick.get(player);
        int differenceTick = Tick.get() - startTick;
        if (differenceTick < Tick.s(30)) {
            return 1;
        } else if (differenceTick < Tick.s(60)) {
            return 2;
        } else if (differenceTick < Tick.s(90)) {
            return 3;
        } else {
            return 4;
        }
    }

    public static boolean onPlayerNearToDead(Player player) {
        if (player.isDeadOrDying()) {
            return false;
        }
        // 如果有，则说明已经触发过被动，则死亡
        if (nearDeadStatusStartTick.containsKey(player)) {
            nearDeadStatusStartTick.remove(player);
            Compute.removeEffectLastTime(player, "item/citadel_curio");
            SpecialEffectOnPlayer.cleanse(player);
            Compute.removeDebuffTime(player, "item/citadel_curio");
            return false;
        }
        // 如果没有，触发被动
        nearDeadStatusStartTick.put(player, Tick.get());
        player.setHealth(player.getMaxHealth());
        Compute.sendEffectLastTime(player, "item/citadel_curio", Tick.s(30), 1, false);
        MySound.soundToPlayer(player, SoundEvents.WITHER_AMBIENT);
        return true;
    }

    @Override
    public void onKill(Player player, Mob mob, ItemStack stack) {
        if (!fadeShadePos.containsKey(player)) {
            fadeShadePos.put(player, new ConcurrentLinkedDeque<>());
        }
        Queue<Vec3> fadeShadePosQueue = fadeShadePos.get(player);
        while (fadeShadePosQueue.size() >= 10) {
            fadeShadePosQueue.poll();
        }
        fadeShadePosQueue.add(mob.getEyePosition());
    }

    @Override
    public void tick(Player player) {
        // 被动：虚影
        // 找到目标，要么是最近普攻命中的怪物，要么是range格内最近的怪物
        if (fadeShadePos.containsKey(player)) {
            Queue<Vec3> fadeShadePosQueue = fadeShadePos.get(player);
            for (Vec3 pos : fadeShadePosQueue) {
                int index = fadeShadePosQueue.stream().toList().indexOf(pos);
                if (player.tickCount % 20 == index * 2) {
                    // 造成伤害
                    Mob target = getTarget(player, getRange());
                    if (target != null && target.position().distanceTo(pos) < 16) {
                        Damage.causeAutoAdaptionRateDamageToMob(player, target, 0.1, false);
                        ParticleProvider.createLineParticle(player.level(), (int) target.distanceTo(player) * 3,
                                pos, target.getEyePosition(), ParticleTypes.END_ROD);
                    } else {
                        ParticleProvider.createVerticalCircleParticle(player.level().dimension(), pos, 1, 0.5, 8, ParticleTypes.END_ROD);
                    }
                }
            }
        }

        // 被动：归终之石 每秒损失百分比生命值
        if (player.tickCount % 20 == 4) {
            int phase = getPhase(player);
            double maxHealth = player.getMaxHealth();
            double rate = 0;
            switch (phase) {
                case 1 -> rate = 0.1;
                case 2 -> rate = 0.15;
                case 3, 4 -> rate = 0.2;
            }
            if (phase == 4) {
                SpecialEffectOnPlayer.addHealingReduction(player, "CitadelPhase4", 1, Tick.get() + Tick.s(10));
                Compute.sendDebuffTime(player, "item/citadel_curio", Tick.get() + Tick.s(10), 0, false);
            }
            Compute.decreasePlayerHealth(player, maxHealth * rate, Te.s("已归终.", hoverMainStyle()));
        }

        // 被动：归终之石Buff图标
        if (nearDeadStatusStartTick.containsKey(player)) {
            int startTick = nearDeadStatusStartTick.get(player);
            int difference = Tick.get() - startTick;
            if (difference == Tick.s(30)) {
                Compute.sendEffectLastTime(player, "item/citadel_curio", Tick.s(30), 2, false);
            } else if (difference == Tick.s(60)) {
                Compute.sendEffectLastTime(player, "item/citadel_curio", Tick.s(60), 3, false);
            } else if (difference == Tick.s(90)) {
                Compute.sendEffectLastTime(player, "item/citadel_curio", Tick.s(90), 4, false);
            }
        }
    }

    private @Nullable Mob getTarget(Player player, double radius) {
        Mob target = Compute.getPlayerMainAttackTarget(player);
        if (target == null) {
            target = Compute.getNearestMob(player, radius);
        }
        return target;
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        int phase = getPhase(player);
        switch (phase) {
            case 1 -> {
                return List.of(
                        new Attribute(Utils.percentAttackDamageEnhance, 0.2),
                        new Attribute(Utils.percentManaDamageEnhance, 0.2),
                        new Attribute(Utils.healingAmplification, 0.3)
                );
            }
            case 2 -> {
                return List.of(
                        new Attribute(Utils.percentAttackDamageEnhance, 0.3),
                        new Attribute(Utils.percentManaDamageEnhance, 0.3),
                        new Attribute(Utils.healingAmplification, 0.3),
                        new Attribute(Utils.defencePenetration, 0.3),
                        new Attribute(Utils.manaPenetration, 0.3)
                );
            }
            case 3,4 -> {
                return List.of(
                        new Attribute(Utils.percentAttackDamageEnhance, 0.4),
                        new Attribute(Utils.percentManaDamageEnhance, 0.4),
                        new Attribute(Utils.healingAmplification, phase == 3 ? 0.4 : -10),
                        new Attribute(Utils.defencePenetration, 1),
                        new Attribute(Utils.manaPenetration, 1)
                );
            }
        }
        return List.of();
    }
}

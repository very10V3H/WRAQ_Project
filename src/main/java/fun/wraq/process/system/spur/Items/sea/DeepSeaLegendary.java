package fun.wraq.process.system.spur.Items.sea;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.PersistentRangeEffectOperation;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.spur.events.FishEvent;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class DeepSeaLegendary extends WraqSceptre implements ActiveItem, InCuriosOrEquipSlotAttributesModify {

    public final int tier;
    public static List<Item> list = new ArrayList<>();
    public DeepSeaLegendary(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.manaDamage.put(this, new double[]{120, 240, 1200, 2400, 3600}[tier] * 2);
        Utils.manaRecover.put(this, new double[]{16, 24, 32, 45, 60}[tier]);
        Utils.manaPenetration0.put(this, new double[]{6, 18, 29, 36, 80}[tier]);
        Utils.coolDownDecrease.put(this, new double[]{0.1, 0.15, 0.2, 0.25, 0.3}[tier]);
        Utils.levelRequire.put(this, 50 * (tier + 1));
        list.add(this);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSea;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("深海祝福", getMainStyle()));
        components.add(Te.s(" 基于", "钓鱼等级", getMainStyle(), "获得:"));
        components.add(Te.s(" · ", ComponentUtils.getManaDamageEnhance("5% * 钓鱼等级")));
        components.add(Te.s(" · ", ComponentUtils.AttributeDescription.manaRecover("10 * 钓鱼等级")));
        if (tier < 2) {
            return components;
        }
        ComponentUtils.descriptionActive(components, Te.s("幽闭深海", getMainStyle()));
        components.add(Te.s(" 制造一个领域，使领域内的怪物"));
        components.add(Te.s(" · ", ComponentUtils.AttributeDescription.movementSpeed("降低")));
        components.add(Te.s(" · ", "造成伤害 -25%", ChatFormatting.RED));
        components.add(Te.s(" · ", ComponentUtils.AttributeDescription
                .manaDefence(String.format("%.0f%%", getReductionRate() * 100))));
        components.add(Te.s(" · 冷却时间 10s", ChatFormatting.AQUA));
        return components;
    }

    public double getReductionRate() {
        return 0.2 + (tier - 1) * 0.05;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfDeepSea();
    }

    @Override
    public void active(Player player) {
        if (tier < 2) {
            return;
        }
        Vec3 pos = WraqPower.getDefaultTargetPos(player, 32);
        PersistentRangeEffect.addEffect(player, WraqPower.getDefaultTargetPos(player, 32), 8, new PersistentRangeEffectOperation() {
            @Override
            public void operation(PersistentRangeEffect effect) {
                effect.getRangeMob().forEach(mob -> {
                    mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 2));
                    StableAttributesModifier.addM(mob, StableAttributesModifier.mobCauseDamageRateModifier,
                            "DeepSeaLegendaryCauseDamageReduction",
                            -0.25, Tick.get() + 20, "item/deep_sea_legendary");
                    StableAttributesModifier.addM(mob, StableAttributesModifier.mobPercentManaDefenceModifier,
                            "DeepSeaLegendaryManaDefenceReduction", -getReductionRate(), Tick.get() + 20);
                });
            }
        }, 10, Tick.s(3));
        ParticleProvider.createSpaceEffectParticle(player.level(), pos, 8, 100, getMainStyle(), Tick.s(3));
        Compute.playerItemCoolDown(player, this, 10);
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    @Override
    protected EntityType<ManaArrow> getArrowType() {
        return ModEntityType.NEW_ARROW_WORLD.get();
    }

    @Override
    protected String getParticleType() {
        return StringUtils.ParticleTypes.Sea;
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        int level = FishEvent.getLevel(player);
        return List.of(
                new Attribute(Utils.manaDamageEnhance, level * 0.05),
                new Attribute(Utils.manaRecover, level * 10)
        );
    }
}

package fun.wraq.process.system.spur.Items.crop;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.PersistentRangeEffectOperation;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.process.system.spur.Items.mine.SilverDragonAssassinPickaxe;
import fun.wraq.process.system.spur.events.CropSpur;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class INaRiBow extends WraqBow implements ActiveItem {

    public final int tier;
    public static List<Item> list = new ArrayList<>();
    public INaRiBow(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{120, 240, 1200, 2400, 3600}[tier]);
        Utils.defencePenetration0.put(this, new double[]{6, 18, 29, 36, 80}[tier]);
        Utils.critRate.put(this, 0.35);
        Utils.critDamage.put(this, new double[]{0.02, 0.05, 0.08, 0.12, 0.2}[tier]);
        Utils.levelRequire.put(this, (tier + 1) * 50);
        list.add(this);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfField;
    }

    public double getArrowAttackDamageRate() {
        return 0.2 + (tier - 1) * 0.05;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("稻荷", getMainStyle()));
        components.add(Te.s(" 基于", "采收等级", getMainStyle(),
                "增幅", ComponentUtils.AttributeDescription.critDamage("2% * 采收等级")));
        if (tier < 2) {
            return components;
        }
        ComponentUtils.descriptionActive(components, Te.s("稻荷神赋", getMainStyle()));
        components.add(Te.s(" 制造一片", "稻荷领域", getMainStyle()));
        components.add(Te.s(" · ", "对范围内的敌方持续造成",
                String.format("%.0f%%", getArrowAttackDamageRate() * 100) + "箭矢攻击伤害", CustomStyle.styleOfFlexible));
        components.add(Te.s(" · ", "对范围内的玩家提供", "治疗",
                "与", ComponentUtils.getCommonDamageEnhance("25%")));
        components.add(Te.s(" · 冷却时间 8s", ChatFormatting.AQUA));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfINaRI();
    }

    @Override
    public void active(Player player) {
        if (tier < 2) {
            return;
        }
        Vec3 pos = WraqPower.getDefaultTargetPos(player, 32);
        PersistentRangeEffect.addEffect(player, pos, 8, new PersistentRangeEffectOperation() {
            @Override
            public void operation(PersistentRangeEffect effect) {
                effect.getRangeMob().forEach(mob -> {
                    MyArrow.causeDamage(player, mob, getArrowAttackDamageRate());
                });
                effect.getRangePlayer().forEach(eachPlayer -> {
                    StableAttributesModifier.addM(eachPlayer,
                            StableAttributesModifier.playerCommonDamageEnhance, "INaRiBowCommonDamageEnhance",
                            0.25, Tick.get() + 20, "item/inari_bow");
                    Compute.playerHeal(eachPlayer, eachPlayer.getMaxHealth() * 0.05);
                });
            }
        }, 10, Tick.s(3));
        ParticleProvider.createSpaceEffectParticle(player.level(), pos, 8, 100, getMainStyle(), Tick.s(3));
        Compute.playerItemCoolDown(player, this, 8);
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    public static double getCritDamageEnhanceRate(Player player) {
        if (player.getMainHandItem().getItem() instanceof SilverDragonAssassinPickaxe) {
            return (CropSpur.getPlayerGardeningLevel(player) + 1) * 0.02;
        }
        return 0;
    }
}

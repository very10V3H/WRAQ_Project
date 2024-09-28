package fun.wraq.series.overworld.sakuraSeries.SakuraMob;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectMainHandWeapon;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class SakuraBow extends WraqBow implements OnHitEffectMainHandWeapon {

    public SakuraBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 400d);
        Utils.defencePenetration0.put(this, 18d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 0.95);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        Element.LifeElementValue.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSakura;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("妖化箭矢").withStyle(style));
        components.add(Component.literal(" 第一发箭矢命中目标后，提供").withStyle(ChatFormatting.WHITE));
        components.add(Te.s(ComponentUtils.AttributeDescription.defence("3")));
        components.add(Te.s(ComponentUtils.AttributeDescription.manaDefence("3")));
        components.add(Te.s(Te.m("持续6s，至多叠加至8层")));
        components.add(Component.literal(" 第二发箭矢回复").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("5%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterV();
    }

    @Override
    protected float getArrowSpeed() {
        return 4;
    }

    @Override
    protected ShootParticle getShootParticle() {
        return new ShootParticle(ParticleTypes.CHERRY_LEAVES, 2);
    }

    private static final Map<Player, Boolean> passiveMap = new WeakHashMap<>();
    @Override
    public void onHit(Player player, Mob mob) {
        boolean isSecond = passiveMap.getOrDefault(player, false);
        if (isSecond) {
            Compute.playerHeal(player, player.getMaxHealth() * 0.05);
        } else {
            StableTierAttributeModifier.addM(player, StableTierAttributeModifier.defence, "SakuraBow passive",
                    3, Tick.get() + 120, 8, ModItems.SakuraBow.get());
            StableTierAttributeModifier.addM(player, StableTierAttributeModifier.manaDefence, "SakuraBow passive",
                    3, Tick.get() + 120, 8, ModItems.SakuraBow.get());
        }
        passiveMap.put(player, !isSecond);
    }
}

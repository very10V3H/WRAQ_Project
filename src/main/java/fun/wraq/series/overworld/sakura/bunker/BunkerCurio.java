package fun.wraq.series.overworld.sakura.bunker;

import com.github.L_Ender.cataclysm.init.ModEffect;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.damage.OnWithStandDamageCurios;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.ElementValue;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.sakura.bunker.weapon.off.BunkerOffHand;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BunkerCurio extends WraqCurios implements OnWithStandDamageCurios, ForgeItem {

    private final int tier;
    public BunkerCurio(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.defence.put(this, 40d);
        Utils.manaDefence.put(this, 40d);
        Element.FireElementValue.put(this, 0.6);
        if (tier > 0) {
            Utils.percentDefenceEnhance.put(this, 0.08);
            Utils.percentManaDefenceEnhance.put(this, 0.08);
            Element.FireElementValue.put(this, 0.8);
        }
        Utils.levelRequire.put(this, 230);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Te.s("炉护", style));
        components.add(Te.s(" 受到来自怪物的伤害后，获得3s的", "护盾", CustomStyle.styleOfStone));
        components.add(Te.s(" 护盾值", CustomStyle.styleOfStone,
                "受益于", "等级", ChatFormatting.LIGHT_PURPLE, "与", "火元素强度", CustomStyle.styleOfFire));
        ComponentUtils.descriptionPassive(components, Te.s("爆沸", style));
        components.add(Te.s(" 若受击后", ComponentUtils.AttributeDescription.health(""),
                "低于30%", "，击退附近怪物并施加1s", "眩晕", CustomStyle.styleOfStone));
        components.add(Te.s(" 并获得持续10s的", ComponentUtils.getCommonDamageEnhance("25%")));
        ComponentUtils.coolDownTimeDescription(components, 10);
        components.add(Te.s(" 护盾值 = 等级 * 100 * (1 + 火元素强度 * 0.5)",
                ChatFormatting.ITALIC, ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.BUNKER_STYLE;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfBunker();
    }

    @Override
    public void onWithStandDamage(Player player, Mob mob, double damage) {
        double shieldValue = player.experienceLevel * 100
                * (1 + ElementValue.getPlayerFireElementValue(player) * 0.5);
        Shield.providePlayerShield(player, Tick.s(3), shieldValue);
        if (!player.getCooldowns().isOnCooldown(this) && player.getHealth() / player.getMaxHealth() < 0.3) {
            player.getCooldowns().addCooldown(this, Tick.s(10));
            Compute.repelMob(player, player.position(), 6, 6, 2);
            Compute.getNearMob(player, 6).forEach(eachMob -> {
                eachMob.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN.get(), 20));
            });
            StableAttributesModifier.addM(player, StableAttributesModifier.playerCommonDamageEnhance,
                    "BunkerCurioCommonDamageEnhance", 0.25,
                    Tick.get() + Tick.s(10), "item/bunker_curio");
            ParticleProvider.createBallDisperseParticle(ParticleTypes.FLAME, (ServerLevel) player.level(),
                    player.position(),6, 100);
            MySound.soundToPlayer(player, SoundEvents.BLAZE_SHOOT);
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (tier > 0) {
            return BunkerOffHand.getForgeRecipe(BunkerItems.BUNKER_CURIO_0.get());
        }
        return List.of();
    }
}

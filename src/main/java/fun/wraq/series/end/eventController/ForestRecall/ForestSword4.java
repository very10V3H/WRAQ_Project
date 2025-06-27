package fun.wraq.series.end.eventController.ForestRecall;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForestSword4 extends WraqSword implements ActiveItem {
    public ForestSword4(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 50d);
        Utils.defencePenetration0.put(this, 18d);
        Utils.healthSteal.put(this, 0.05);
        Utils.critRate.put(this, 0.3);
        Element.lifeElementValue.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfForest;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionActive(components, Component.literal("切割/砍伐").withStyle(ChatFormatting.DARK_RED));
        components.add(Component.literal("获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.defencePenetration("20%+[25]")).
                append(Component.literal("持续20s").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.coolDownTimeDescription(components, 30);
        ComponentUtils.manaCostDescription(components, 60);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getIntensifiedSuffix();
    }

    @Override
    public void active(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5 * 2, 0.25, 12, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 1.25 * 2, 0.5, 16, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 1.0 * 2, 0.75, 20, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 0.75 * 2, 1, 24, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 0.5 * 2, 1.25, 28, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 0.25 * 2, 1.5, 32, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 0.0 * 2, 1.75, 36, ParticleTypes.COMPOSTER);
        player.getCooldowns().addCooldown(ModItems.FOREST_SWORD_0.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
        player.getCooldowns().addCooldown(ModItems.FOREST_SWORD_1.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
        player.getCooldowns().addCooldown(ModItems.FOREST_SWORD_2.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
        player.getCooldowns().addCooldown(ModItems.FOREST_SWORD_3.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
        player.getCooldowns().addCooldown(ModItems.FOREST_SWORD_4.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
        MySound.soundToNearPlayer(player, ModSounds.Attack.get());
        StableAttributesModifier.addM(player, StableAttributesModifier.playerDefencePenetrationModifier,
                "forestSwordDefencePenetration",0.2, Tick.get() + 400);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerDefencePenetration0Modifier,
                "forestSwordDefencePenetration0",25, Tick.get() + 400, this);
    }

    @Override
    public double manaCost(Player player) {
        return 60;
    }
}

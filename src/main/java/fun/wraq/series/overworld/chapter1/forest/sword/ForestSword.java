package fun.wraq.series.overworld.chapter1.forest.sword;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForestSword extends WraqSword implements ActiveItem {

    private final int tier;

    public ForestSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, ForestSwordAttributes.BaseDamage[tier]);
        Utils.defencePenetration0.put(this, ForestSwordAttributes.DefencePenetration0[tier]);
        Utils.healthSteal.put(this, ForestSwordAttributes.HealthSteal[tier]);
        Utils.critRate.put(this, ForestSwordAttributes.CriticalRate[tier]);
        Utils.critDamage.put(this, ForestSwordAttributes.CriticalDamage[tier]);
        Element.LifeElementValue.put(this, ForestSwordAttributes.LifeElementValue[tier]);
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
                append(ComponentUtils.AttributeDescription.defencePenetration("15%+[50]")).
                append(Component.literal("，持续" + (int) ForestSwordAttributes.EffectNum[tier] + "s").withStyle(ChatFormatting.WHITE)));
        ComponentUtils.coolDownTimeDescription(components, 30);
        ComponentUtils.manaCostDescription(components, 30);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void active(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        CompoundTag data = serverPlayer.getPersistentData();
        int tickCount = serverPlayer.getServer().getTickCount();
        ParticleProvider.VerticleCircleParticle(serverPlayer, 1.5 * 2, 0.25, 12, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 1.25 * 2, 0.5, 16, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 1.0 * 2, 0.75, 20, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 0.75 * 2, 1, 24, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 0.5 * 2, 1.25, 28, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 0.25 * 2, 1.5, 32, ParticleTypes.COMPOSTER);
        ParticleProvider.VerticleCircleParticle(serverPlayer, 0.0 * 2, 1.75, 36, ParticleTypes.COMPOSTER);
        Compute.sendEffectLastTime(player, this, (int) ForestSwordAttributes.EffectNum[tier] * 20);
        data.putInt(StringUtils.ForestSwordSkill0, tickCount + (int) ForestSwordAttributes.EffectNum[tier] * 20);
        player.getCooldowns().addCooldown(ModItems.ForestSword0.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
        player.getCooldowns().addCooldown(ModItems.ForestSword1.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
        player.getCooldowns().addCooldown(ModItems.ForestSword2.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
        player.getCooldowns().addCooldown(ModItems.ForestSword3.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
        player.getCooldowns().addCooldown(ModItems.ForestSword4.get(), (int) (600 - 600.0 * PlayerAttributes.coolDownDecrease(player)));
        MySound.soundToNearPlayer(player, ModSounds.Attack.get());
    }

    @Override
    public double manaCost(Player player) {
        return 30;
    }
}

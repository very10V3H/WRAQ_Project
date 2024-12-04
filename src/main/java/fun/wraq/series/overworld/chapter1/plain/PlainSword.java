package fun.wraq.series.overworld.chapter1.plain;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.WraqSword;
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

public class PlainSword extends WraqSword implements ActiveItem {
    private final int tier;

    public PlainSword(Properties properties, int num) {
        super(properties);
        this.tier = num;
        Utils.attackDamage.put(this, new double[]{20, 25, 30, 40}[num]);
        Utils.critRate.put(this, new double[]{0.25, 0.25, 0.25, 0.25}[num]);
        Utils.critDamage.put(this, new double[]{0.15, 0.2, 0.25, 0.3}[num]);
        Element.LifeElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8}[num]);
    }

    private final double[] effectRate = new double[]{0.1, 0.12, 0.15, 0.20};

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionActive(components, Component.literal("平原洗礼").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal("右键恢复").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.health(String.format("%.0f%%", effectRate[tier] * 100))));
        ComponentUtils.coolDownTimeDescription(components, 20);
        ComponentUtils.manaCostDescription(components, 60);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public void active(Player player) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        ParticleProvider.VerticleCircleParticle(serverPlayer, 0.75 * 2, 1, 12, ParticleTypes.HEART);
        ParticleProvider.RandomMoveParticle(serverPlayer, 0.75 * 2, 1, 8, ParticleTypes.COMPOSTER);
        Compute.playerHeal(player, player.getMaxHealth() * effectRate[tier]);
        player.getCooldowns().addCooldown(ModItems.PlainSword0.get(), (int) (400 - 400.0 * PlayerAttributes.coolDownDecrease(player)));
        player.getCooldowns().addCooldown(ModItems.PlainSword1.get(), (int) (400 - 400.0 * PlayerAttributes.coolDownDecrease(player)));
        player.getCooldowns().addCooldown(ModItems.PlainSword2.get(), (int) (400 - 400.0 * PlayerAttributes.coolDownDecrease(player)));
        player.getCooldowns().addCooldown(ModItems.PlainSword3.get(), (int) (400 - 400.0 * PlayerAttributes.coolDownDecrease(player)));
        MySound.soundToNearPlayer(player, ModSounds.Healing.get());
    }

    @Override
    public double manaCost(Player player) {
        return 60;
    }
}

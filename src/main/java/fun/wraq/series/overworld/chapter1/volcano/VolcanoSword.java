package fun.wraq.series.overworld.chapter1.volcano;

import fun.wraq.common.Compute;
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

public class VolcanoSword extends WraqSword implements ActiveItem {

    private final int tier;

    public VolcanoSword(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.attackDamage.put(this, new double[]{80, 85, 90, 100, 120}[tier]);
        Utils.defencePenetration0.put(this, new double[]{2, 2, 3, 3, 6}[tier]);
        Utils.critRate.put(this, new double[]{0.2, 0.2, 0.2, 0.2, 0.2}[tier]);
        Element.fireElementValue.put(this, new double[]{0.2, 0.4, 0.6, 0.8, 1}[tier]);
    }

    private final double[] exCritDamage = new double[]{0.04, 0.06, 0.08, 0.1, 0.15};
    private final double[] exAttackDamage = new double[]{30, 45, 60, 90, 300};

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfVolcano;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionActive(components, Component.literal("喷发").withStyle(ChatFormatting.YELLOW));
        ComponentUtils.descriptionNum(components, "1.获得",
                ComponentUtils.AttributeDescription.critDamage((int) (exCritDamage[tier] * 100) + "%"), "");
        ComponentUtils.descriptionNum(components, "2.获得",
                ComponentUtils.AttributeDescription.exAttackDamage((int) exAttackDamage[tier] + " "), "");
        components.add(Component.literal("持续10s").withStyle(ChatFormatting.WHITE));
        ComponentUtils.coolDownTimeDescription(components, 18);
        ComponentUtils.manaCostDescription(components, 30);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public void active(Player player) {
        ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 0.25, 1, 16, ParticleTypes.ANGRY_VILLAGER);
        ParticleProvider.createRandomMoveParticle((ServerPlayer) player, 0, 0.25, 32, ParticleTypes.ASH);
        Compute.sendEffectLastTime(player, ModItems.VOLCANO_SWORD_3.get().getDefaultInstance(), 200);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerAttackDamageModifier,
                "Volcano Sword Active", exAttackDamage[tier], Tick.get() + 200);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerCritDamageModifier,
                "Volcano Sword Active", exCritDamage[tier], Tick.get() + 200);
        List.of(ModItems.VOLCANO_SWORD_0.get(), ModItems.VOLCANO_SWORD_1.get(),
                        ModItems.VOLCANO_SWORD_2.get(), ModItems.VOLCANO_SWORD_3.get(), ModItems.VOLCANO_SWORD_4.get())
                .forEach(item -> {
                    Compute.playerItemCoolDown(player, item, 18);
                });
        MySound.soundToNearPlayer(player, ModSounds.Lava.get());
    }

    @Override
    public double manaCost(Player player) {
        return 30;
    }
}

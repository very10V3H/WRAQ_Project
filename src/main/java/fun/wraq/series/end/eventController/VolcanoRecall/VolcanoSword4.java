package fun.wraq.series.end.eventController.VolcanoRecall;

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

public class VolcanoSword4 extends WraqSword implements ActiveItem {
    public VolcanoSword4(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 80d);
        Utils.defencePenetration0.put(this, 18d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.2);
        Utils.critDamage.put(this, 0.25);
        Utils.movementSpeedWithoutBattle.put(this, 0.4d);
        Element.FireElementValue.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfVolcano;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionActive(components, Component.literal("喷发").withStyle(ChatFormatting.YELLOW));
        ComponentUtils.descriptionNum(components, "1.获得", Compute.AttributeDescription.CritDamage(120 + "%"), "");
        ComponentUtils.descriptionNum(components, "2.获得", Compute.AttributeDescription.ExAttackDamage(75 + "%"), "");
        components.add(Component.literal("持续5s").withStyle(ChatFormatting.WHITE));
        ComponentUtils.coolDownTimeDescription(components, 10);
        ComponentUtils.manaCostDescription(components, 60);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getIntensifiedSuffix();
    }

    @Override
    public void active(Player player) {
        if (Compute.playerManaCost(player, 60)) {
            CompoundTag data = player.getPersistentData();
            int tickCount = player.getServer().getTickCount();
            ParticleProvider.VerticleCircleParticle((ServerPlayer) player, 0.25, 1, 16, ParticleTypes.ANGRY_VILLAGER);
            ParticleProvider.RandomMoveParticle((ServerPlayer) player, 0, 0.25, 32, ParticleTypes.ASH);
            data.putInt(StringUtils.VolcanoSwordSkill.Skill4, tickCount + 100);
            Compute.sendEffectLastTime(player, ModItems.VolcanoSword3.get().getDefaultInstance(), 100);
            player.getCooldowns().addCooldown(ModItems.VolcanoSword0.get(), (int) (200 - 200.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword1.get(), (int) (200 - 200.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword2.get(), (int) (200 - 200.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword3.get(), (int) (200 - 200.0 * PlayerAttributes.coolDownDecrease(player)));
            player.getCooldowns().addCooldown(ModItems.VolcanoSword4.get(), (int) (200 - 200.0 * PlayerAttributes.coolDownDecrease(player)));
            MySound.soundToNearPlayer(player, ModSounds.Lava.get());
        }
    }
}
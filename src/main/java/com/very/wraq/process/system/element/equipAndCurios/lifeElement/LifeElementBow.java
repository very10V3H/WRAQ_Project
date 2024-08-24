package com.very.wraq.process.system.element.equipAndCurios.lifeElement;

import com.very.wraq.common.MySound;
import com.very.wraq.core.MyArrow;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LifeElementBow extends WraqBow implements ActiveItem {

    public LifeElementBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 600d);
        Utils.defencePenetration0.put(this, 4000d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.45);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
        Element.LifeElementValue.put(this, 2d);
    }

    @Override
    public void shoot(ServerPlayer serverPlayer) {
        MyArrow arrow = new MyArrow(EntityType.ARROW, serverPlayer.level(), serverPlayer, true);
        arrow.shootFromRotation(serverPlayer, serverPlayer.getXRot(), serverPlayer.getYRot(), 0.0f, 4.5f, 1.0f);
        arrow.setCritArrow(true);
        WraqBow.adjustArrow(arrow, serverPlayer);
        serverPlayer.level().addFreshEntity(arrow);
        MySound.SoundToAll(serverPlayer, SoundEvents.ARROW_SHOOT);
        ParticleProvider.FaceCircleCreate(serverPlayer, 1, 0.75, 20, ModParticles.LifeElementParticle.get());
        ParticleProvider.FaceCircleCreate(serverPlayer, 1.5, 0.5, 16, ModParticles.LifeElementParticle.get());
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLife;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionActive(components, Component.literal("化作春泥").withStyle(style));
        components.add(Component.literal(" 失去").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("80%当前")).
                append(Component.literal("，并在10s内为你回复").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Health("200%消耗的等额")));
        Compute.CoolDownTimeDescription(components, 25);

        Compute.DescriptionPassive(components, Component.literal("护花").withStyle(style));
        components.add(Component.literal(" 根据5s内回复的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("")).
                append(Component.literal("，为你提供等同于回复量12.5%的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")));
        components.add(Component.literal(" 多件生机武器的效果将不会叠加").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" 落红不是无情物，化作春泥更护花\uD83C\uDF37").withStyle(ChatFormatting.ITALIC).withStyle(style));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfElement();
    }

    public static void Tick(Player player) {
        if (LifeElementSword.lifeElementActiveLastTick.containsKey(player) && LifeElementSword.lifeElementActiveLastTick.get(player) >= player.getServer().getTickCount()) {
            int tickCount = LifeElementSword.lifeElementActiveLastTick.get(player) - player.getServer().getTickCount();
            Compute.sendEffectLastTime(player, ModItems.LifeElementSword.get().getDefaultInstance(), tickCount, tickCount, true);
            Compute.playerHeal(player, LifeElementSword.lifeElementActiveHealth.get(player) * 0.01);
        }
    }

    public static void StoreToList(Player player, double num) {
        if (LifeElementSword.lifeElementActiveLastTick.containsKey(player) && LifeElementSword.lifeElementActiveLastTick.get(player) > player.getServer().getTickCount()) {
            if (!LifeElementSword.playerShortTimeStoreHealthMap.containsKey(player))
                LifeElementSword.playerShortTimeStoreHealthMap.put(player, new ArrayList<>());
            List<LifeElementSword.ShortTimeStoreHealth> list = LifeElementSword.playerShortTimeStoreHealthMap.get(player);
            list.removeIf(shortTimeStoreHealth -> shortTimeStoreHealth.tickCount() < player.getServer().getTickCount());
            list.add(new LifeElementSword.ShortTimeStoreHealth(player.getServer().getTickCount() + 100, num));
        }
    }

    public static double ComputeStoreNum(Player player) {
        if (!LifeElementSword.playerShortTimeStoreHealthMap.containsKey(player)) return 0;
        List<LifeElementSword.ShortTimeStoreHealth> list = LifeElementSword.playerShortTimeStoreHealthMap.get(player);
        double sum = 0;
        list.removeIf(shortTimeStoreHealth -> shortTimeStoreHealth.tickCount() < player.getServer().getTickCount());
        for (LifeElementSword.ShortTimeStoreHealth shortTimeStoreHealth : list) sum += shortTimeStoreHealth.num();
        return sum;
    }

    public static double ExAttackDamage(Player player) {
        if (Utils.bowTag.containsKey(player.getMainHandItem().getItem())) return ComputeStoreNum(player) * 0.125;
        return 0;
    }

    @Override
    public void active(Player player) {
        if (Compute.PlayerUseWithHud(player, LifeElementSword.lifeElementActiveCoolDown, this, LifeElementSword.lifeElementActiveLastTick, 100, 0, 25)) {
            Compute.playerItemCoolDown(player, this, 25);
            LifeElementSword.lifeElementActiveHealth.put(player, player.getHealth() * 0.8);
            Compute.PlayerHealthDecrease(player, player.getHealth() * 0.8, Component.literal(" 被生机元素吞噬了。").withStyle(CustomStyle.styleOfLife));
        }
    }
}

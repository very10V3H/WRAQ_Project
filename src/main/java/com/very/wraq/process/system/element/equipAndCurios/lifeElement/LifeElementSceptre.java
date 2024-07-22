package com.very.wraq.process.system.element.equipAndCurios.lifeElement;

import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.mana.ManaArrow;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.ModEntityType;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class LifeElementSceptre extends WraqSceptre implements ActiveItem {

    public LifeElementSceptre(Properties p_42964_) {
        super(p_42964_);
        Utils.manaDamage.put(this, 1774d);
        Utils.manaRecover.put(this, 30d);
        Utils.manaPenetration0.put(this, 4000d);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Utils.manaCost.put(this, 45d);
        Utils.coolDownDecrease.put(this, 0.2);
        Element.LifeElementValue.put(this, 2d);
    }

    public static void Tick(Player player) {
        if (LifeElementSword.lifeElementActiveLastTick.containsKey(player) && LifeElementSword.lifeElementActiveLastTick.get(player) >= player.getServer().getTickCount()) {
            int tickCount = LifeElementSword.lifeElementActiveLastTick.get(player) - player.getServer().getTickCount();
            Compute.effectLastTimeSend(player, ModItems.LifeElementSword.get().getDefaultInstance(), tickCount, tickCount, true);
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

    public static double ExManaDamage(Player player) {
        if (Utils.sceptreTag.containsKey(player.getMainHandItem().getItem())) return ComputeStoreNum(player) * 0.5;
        return 0;
    }

    @Override
    public void shoot(Player player) {
        Level level = player.level();
        CompoundTag data = player.getPersistentData();
        if (Compute.ManaSkillLevelGet(data, 10) > 0 || Compute.playerManaCost(player, 45)) {
            ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_PLAIN.get(), player, level,
                    PlayerAttributes.manaDamage(player),
                    PlayerAttributes.manaPenetration(player),
                    PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.LifeElement1TickParticle);
            newArrow.setSilent(true);
            newArrow.setNoGravity(true);
            newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 3, 1);
            ProjectileUtil.rotateTowardsMovement(newArrow, 0);
            WraqSceptre.adjustOrb(newArrow, player);
            level.addFreshEntity(newArrow);
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1, 0.75, 20, ModParticles.LifeElementParticle.get());
            ParticleProvider.FaceCircleCreate((ServerPlayer) player, 1.5, 0.5, 16, ModParticles.LifeElementParticle.get());
        }
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
                append(Component.literal("，为你提供等同于回复量50%的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.ManaDamage("")));
        components.add(Component.literal(" 多件生机武器的效果将不会叠加").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" 落红不是无情物，化作春泥更护花\uD83C\uDF37").withStyle(ChatFormatting.ITALIC).withStyle(style));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfElement();
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

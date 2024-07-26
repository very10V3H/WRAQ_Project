package com.very.wraq.events.core;

import com.very.wraq.series.newrunes.chapter1.LakeNewRune;
import com.very.wraq.series.overworld.chapter2.sea.Breath;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.attributeValues.SpecialEffectOnPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttributeSet {
    @SubscribeEvent
    public static void SpeedSet(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            Player player = event.player;
            CompoundTag data = player.getPersistentData();
            double speedUp = PlayerAttributes.movementSpeedCurrent(player);

            // 移速手动调整
            if (data.contains(StringUtils.MovementSpeedRate)) {
                speedUp *= data.getInt(StringUtils.MovementSpeedRate) * 0.01;
            }

            double finalMovementSpeed = (0.1 + 0.1 * (float) speedUp) * SpecialEffectOnPlayer.slowdownEffectValue(player);
            if (SpecialEffectOnPlayer.inImprison(player)) finalMovementSpeed = 0;

            // 移动速度
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(finalMovementSpeed);

            // 游泳速度
            double exSwimSpeed = 0;
            if (Breath.onPlayerMap.containsKey(player) && Breath.onPlayerMap.get(player)) exSwimSpeed += 1;
            exSwimSpeed += LakeNewRune.exSwimSpeed(player);
            exSwimSpeed += 2;
            player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(1 + exSwimSpeed);

            // 生命回复与魔力回复
            if (!player.isDeadOrDying()) {
                double HealthRecover = PlayerAttributes.healthRecover(player);
                Compute.playerHeal(player, HealthRecover / 20);

                double ManaUp = PlayerAttributes.maxManaUp(player);
                data.putDouble("MAXMANA", 1000 + player.experienceLevel + ManaUp);
            }

            //最大生命值、魔力、攻击距离修改。
            double MaxHealth = PlayerAttributes.maxHealth(player);
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MaxHealth);

            if (player.getHealth() > player.getMaxHealth()) player.setHealth(player.getMaxHealth());

            // 攻击距离 过时
            double RangeUp = PlayerAttributes.attackRangeUp(player);
            player.getAttribute(ForgeMod.ENTITY_REACH.get()).setBaseValue(3 + RangeUp);

            // 最大法力值 与 法力回
            if (data.contains("MANA") && data.contains("MAXMANA")) {
                double MaxMana = data.getDouble("MAXMANA");
                double ManaRecover = 10 + PlayerAttributes.manaRecover(player);
                Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                if (Compute.ManaSkillLevelGet(data, 10) > 0 && Utils.sceptreTag.containsKey(mainhand)) {
                    MaxMana *= (1 - Compute.ManaSkillLevelGet(data, 10) * 0.1);
                    ManaRecover *= (1 - Compute.ManaSkillLevelGet(data, 10) * 0.1);
                }
                data.putDouble("MANA", Math.min(data.getDouble("MANA") + ManaRecover / 20, MaxMana));
                Compute.ManaStatusUpdate(player);
            }

            //
            if (data.contains(StringUtils.Swift) && data.contains(StringUtils.MaxSwift)) {
                Compute.PlayerSwiftChange(player, (5 + Math.min(5, PlayerAttributes.extraSwiftness(player))) / 20);
            }
        }
    }
}

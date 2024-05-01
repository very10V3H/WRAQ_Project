package com.very.wraq.events.core;

import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisaCurios4;
import com.very.wraq.process.parkour.Parkour;
import com.very.wraq.series.overWorld.MainStory_II.Sea.Breath;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
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
            double SpeedUp = PlayerAttributes.PlayerMovementSpeed(player);

            if (data.contains(StringUtils.MovementSpeedRate)) {
                SpeedUp *= data.getInt(StringUtils.MovementSpeedRate) * 0.01;
            } // 移速手动调整

            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1D+0.1* (float) SpeedUp);
            if (Parkour.PlayerIsInParkourRange(player)) player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1);
            if (event.player.tickCount % 200 == 0 && data.contains("Green") && data.getInt("Green") == 2) {
                if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                    Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("平原的风为你指引道路").withStyle(ChatFormatting.GREEN));
                if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                    Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                            Component.literal("平原符石-临危制变为你提供了").withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("%.2f", 100 * (((player.getMaxHealth() - player.getHealth()) * 1.0 / player.getMaxHealth()) / 2.0))).withStyle(ChatFormatting.GREEN)).append(Component.literal("%")).append(Component.literal("·移动速度").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.BOLD)));
            }

            double ExSwimSpeed = 0;
            if (data.contains(StringUtils.LakeRune) && data.getInt(StringUtils.LakeRune) == 2) ExSwimSpeed += 2;
            if (Breath.onPlayerMap.containsKey(player) && Breath.onPlayerMap.get(player)) ExSwimSpeed += 1;
            player.getAttribute(ForgeMod.SWIM_SPEED.get()).setBaseValue(1 + ExSwimSpeed);

            if (!player.isDeadOrDying()) {
                double HealthRecover = PlayerAttributes.PlayerHealthRecover(player);
                Compute.PlayerHeal(player,HealthRecover / 20);

                double ManaUp = PlayerAttributes.PlayerMaxMana(player);
                data.putDouble("MAXMANA", 100 + ManaUp);
            } // 生命回复与魔力回复

            //最大生命值、魔力、攻击距离修改。
            double MaxHealth = PlayerAttributes.PlayerMaxHealth(player) + 200;
            if (BlackFeisaCurios4.IsPlayer(player)) MaxHealth *= 0.5;
            player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(MaxHealth);

            if (player.getHealth() > player.getMaxHealth()) player.setHealth(player.getMaxHealth());

            double RangeUp = PlayerAttributes.PlayerAttackRangeUp(player);
            player.getAttribute(ForgeMod.ENTITY_REACH.get()).setBaseValue(3 + RangeUp);

            double AttackSpeedUp = PlayerAttributes.PlayerAttackSpeedUp(player);
            player.getAttribute(Attributes.ATTACK_SPEED).setBaseValue(4 + AttackSpeedUp);

            if (data.contains("MANA") && data.contains("MAXMANA")) {
                double MaxMana = data.getDouble("MAXMANA");
                double ManaRecover = 5 + PlayerAttributes.PlayerManaRecover(player);
                Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                if (Compute.ManaSkillLevelGet(data, 10) > 0 && Utils.SceptreTag.containsKey(mainhand)) {
                    MaxMana *= (1 - Compute.ManaSkillLevelGet(data,10) * 0.1);
                    ManaRecover *= (1 - Compute.ManaSkillLevelGet(data,10) * 0.1);
                }
                data.putDouble("MANA",  Math.min(data.getDouble("MANA") + ManaRecover / 20, MaxMana));
                Compute.ManaStatusUpdate(player);
            } // 最大法力值 与 法力回复

            if (data.contains(StringUtils.Swift) && data.contains(StringUtils.MaxSwift)) {
                Compute.PlayerSwiftChange(player,(5 + Math.min(5, PlayerAttributes.PlayerExtraSwiftness(player))) / 20);
            }
        }
    }
}

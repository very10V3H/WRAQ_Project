package com.Very.very.Events.MainEvents;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.AttributePackets.*;
import com.Very.very.NetWorking.Packets.SoundsPackets.SoundsS2CPacket;
import com.Very.very.NetWorking.Packets.ToolTipPackets.DailyMissionS2CPacket;
import com.Very.very.Series.NetherSeries.Equip.NetherArmorBoots;
import com.Very.very.Series.NetherSeries.Equip.NetherArmorChest;
import com.Very.very.Series.NetherSeries.Equip.NetherArmorHelmet;
import com.Very.very.Series.NetherSeries.Equip.NetherArmorLeggings;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Struct.Shield;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.ParseException;
import java.util.*;

@Mod.EventBusSubscriber

public class ServerPlayerTickEvent {
    @SubscribeEvent
    public static void PersisData(TickEvent.PlayerTickEvent event) throws ParseException {

        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            ServerPlayer serverPlayer = (ServerPlayer) player;
/*            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket = new ClientboundSetEntityMotionPacket(player.getId(),player.getDeltaMovement().add(0.05,0,0));
            serverPlayer.connection.send(clientboundSetEntityMotionPacket);*/
            CompoundTag data = player.getPersistentData();
            int TmpNum = player.tickCount;
/*            if(TmpNum % 1200 == 0) {
                if(Utils.GemsForPlain) serverPlayer.connection.disconnect(Component.literal("Null"));
                if(Utils.GemsForForest) serverPlayer.connection.disconnect(Component.literal("Null"));
            }*/
            if (TmpNum % 6000 == 0) {
                if (data.contains("XRot") && data.contains("YRot")) {
                    if (player.getXRot() == data.getFloat("XRot")
                            && player.getYRot() == data.getFloat("YRot")) {
                        serverPlayer.connection.disconnect(Component.literal("挂机会占用服务器资源，休息好了再回来吧！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN));
                    }
                }
                data.putFloat("XRot", player.getXRot());
                data.putFloat("YRot", player.getYRot());
            }
            if (TmpNum % 20 == 0) {
                if (data.contains("MANA") && data.contains("MAXMANA")) {
                    data.putInt("MANA", Math.min(data.getInt("MANA") + 5 + (int) Compute.PlayerManaReply(player), data.getInt("MAXMANA")));
                    Compute.ManaStatusUpdate(player);
                }
            }
            if (TmpNum % 40 == 0) {
                int LightningArmorCount = Compute.LightningArmorCount(player);
                if (Utils.IsLandBarrier && !player.isCreative() && player.level().equals(player.getServer().getLevel(Level.OVERWORLD))) {
                    if (LightningArmorCount == 0 && player.getX() < 1523 && player.getX() > 1182 && player.getZ() < 633 && player.getZ() > 371) {
                        Compute.FormatMSGSend(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                                Component.literal("岛上的神秘力量正在阻止你向岛内前进。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 3));
                        player.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 10));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, player.level());
                        lightningBolt.setDamage(10);
                        lightningBolt.moveTo(player.getPosition(1.0f));
                        player.level().addFreshEntity(lightningBolt);
                    }
                }
                if (data.contains("Lighting") && data.getBoolean("Lighting")) {
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, player.level());
                    lightningBolt.setDamage(100);
                    lightningBolt.moveTo(player.getPosition(1.0f));
                    player.level().addFreshEntity(lightningBolt);
                    data.putBoolean("Lighting", false);
                }
            }
            if (data.contains("arrowflyingForBug") && data.getInt("arrowflyingForBug") > 0)
                data.putInt("arrowflyingForBug", data.getInt("arrowflyingForBug") - 1);
            if (data.contains("ManaSwordActive") && data.getInt("ManaSwordActive") > 0)
                data.putInt("ManaSwordActive", data.getInt("ManaSwordActive") - 1);
            if (data.contains("pigLinPower") && data.getInt("pigLinPower") > 0)
                data.putInt("pigLinPower", data.getInt("pigLinPower") - 1);
            if (data.contains("QuartzSabre") && data.getInt("QuartzSabre") > 0)
                data.putInt("QuartzSabre", data.getInt("QuartzSabre") - 1);
            if (data.contains("QuartzSabreSpeedUp") && data.getInt("QuartzSabreSpeedUp") > 0)
                data.putInt("QuartzSabreSpeedUp", data.getInt("QuartzSabreSpeedUp") - 1);
            if (data.contains("snowRune0Time") && data.getInt("snowRune0Time") > 0) {
                data.putInt("snowRune0Time", data.getInt("snowRune0Time") - 1);
                if (data.getInt("snowRune0Time") == 0) {
                    data.putInt("snowRune0", 0);
                }
            }
            if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0)
                data.putInt("NetherRecallBuff", data.getInt("NetherRecallBuff") - 1);
            if (data.contains("snowRune") && data.getInt("snowRune") == 2 && data.contains("snowRune2") && data.getInt("snowRune2") > 0) {
                data.putInt("snowRune2", data.getInt("snowRune2") - 1);
                if (data.getInt("snowRune2") == 0) {
                    if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                        Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                                Component.literal("冰川符石-凛冬之意").withStyle(ChatFormatting.AQUA).append(Component.literal("已就绪!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                }
            }
            if (data.contains("snowRune") && data.getInt("snowRune") == 3 && data.contains("snowRune3") && data.getInt("snowRune3") > 0) {
                data.putInt("snowRune3", data.getInt("snowRune3") - 1);
                if (data.getInt("snowRune3") == 0) {
                    if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                        Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                                Component.literal("冰川符石-冰霜箭矢").withStyle(ChatFormatting.AQUA).append(Component.literal("已就绪!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                }
            }
            if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 3 && data.contains("DGreen3") && data.getInt("DGreen3") > 0) {
                data.putInt("DGreen3", data.getInt("DGreen3") - 1);
                if (data.getInt("DGreen3") == 0) {
                    if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                        Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                                Component.literal("森林符石-生长汲取").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal("已就绪!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
            }
            if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 2 && data.contains(StringUtils.ForestRune.ForestRune2) && data.getInt(StringUtils.ForestRune.ForestRune2) > 0) {
                data.putInt(StringUtils.ForestRune.ForestRune2, data.getInt(StringUtils.ForestRune.ForestRune2) - 1);
                if (data.getInt(StringUtils.ForestRune.ForestRune2) == 0) {
                    if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                        Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                                Component.literal("森林符石-狂野生长").withStyle(ChatFormatting.DARK_GREEN).append(Component.literal("已就绪!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
            }
            if (data.contains("ManaRune") && data.getInt("ManaRune") == 2 && data.contains("ManaRune2") && data.getInt("ManaRune2") > 0) {
                data.putInt("ManaRune2", data.getInt("ManaRune2") - 1);
                if (data.getInt("ManaRune2") == 0) {
                    if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                        Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                                Component.literal("魔源符石-苍雷之怒").withStyle(ChatFormatting.LIGHT_PURPLE).append(Component.literal("已就绪!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                }
            }
            if (player.getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline) && TmpNum > 800) {
                serverPlayer.connection.disconnect(Component.literal("似乎用了太长的时间在登录上。"));
            }
            if (player.getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline))
                player.teleportTo(data.getDouble("DX"), data.getDouble("DY"), data.getDouble("DZ"));
            if (data.contains("Green") && data.getInt("Green") == 0 && !player.level().isClientSide() && TmpNum % 100 == 0) {
                float HealEffectUp = Compute.PlayerHealEffectUp(player);
                player.setHealth(Math.min((player.getHealth() + (player.getMaxHealth() - player.getHealth()) * 0.1F) * (1 + HealEffectUp), player.getMaxHealth()));
                if ((player.getMaxHealth() - player.getHealth()) * 0.1F > 0.5F) {
                    if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                        Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                                Component.literal("平原的风吹向你，为你恢复了生息。").withStyle(ChatFormatting.GREEN));
                    if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                        Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                                Component.literal("平原符石-复苏之风").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN).append(Component.literal("为你回复了")).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("%.2f", ((player.getMaxHealth() - player.getHealth()) * 0.1F) * (1 + HealEffectUp))).withStyle(ChatFormatting.GREEN)).append(Component.literal("生命值").withStyle(ChatFormatting.RESET)));
                }
            } else {
                if (data.contains("Green") && data.getInt("Green") == 1 && !player.level().isClientSide()) {
                    player.getFoodData().setFoodLevel(8);
                }
                if (data.getInt("Green") == 3 && data.getInt("Green3") < 200) {
                    if (data.getInt("Green3") == 199) {
                        if (!data.contains("IgnoreRune") || (!data.getBoolean("IgnoreRune")))
                            Compute.FormatMSGSend(player, Component.literal("符石").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#bfbcbc"))),
                                    Component.literal("平原符石-平原加护").withStyle(ChatFormatting.GREEN).append(Component.literal("已就绪!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        data.putInt("Green3", data.getInt("Green3") + 1);
                    } else data.putInt("Green3", data.getInt("Green3") + 1);
                }
            }
            if (TmpNum % 20 == 0 && !player.isDeadOrDying()) {
                float HealEffectUp = Compute.PlayerHealEffectUp(player);
                float HealthRecover = Compute.PlayerHealthRecover(player);
                Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
                Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
                Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
                Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
                if (boots instanceof NetherArmorBoots && leggings instanceof NetherArmorLeggings
                        && chest instanceof NetherArmorChest && helmet instanceof NetherArmorHelmet) HealthRecover = 0;
                player.heal(HealthRecover * (1 + HealEffectUp));

                int ManaUp = (int) Compute.PlayerManaRecover(player);
                data.putInt("MAXMANA", 100 + ManaUp);

            } // 生命回复与魔力回复
            if (TmpNum % 10 == 0) //最大生命值、魔力、攻击距离修改。
            {
                float HealUp = Compute.MaxHealImprove(player);
                Item helmet = player.getItemBySlot(EquipmentSlot.HEAD).getItem();
                Item chest = player.getItemBySlot(EquipmentSlot.CHEST).getItem();
                Item leggings = player.getItemBySlot(EquipmentSlot.LEGS).getItem();
                Item boots = player.getItemBySlot(EquipmentSlot.FEET).getItem();
                if (boots instanceof NetherArmorBoots && leggings instanceof NetherArmorLeggings
                        && chest instanceof NetherArmorChest && helmet instanceof NetherArmorHelmet) {
                    HealUp = 0;
                } // 下界套装效果赋予
                player.getAttribute(Attributes.MAX_HEALTH).setBaseValue(200.0D + HealUp);
                if (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline)) player.setHealth(player.getMaxHealth());

                float RangeUp = Compute.PlayerAttackRangeUp(player);
                player.getAttribute(ForgeMod.ENTITY_REACH.get()).setBaseValue(3 + RangeUp);
            }
            if (TmpNum % 20 == 0) //每日任务击杀数发包
            {
                int[] Tmp = new int[20];
                for (int i = 1; i <= 17; i++) {
                    Tmp[i] = data.getInt("DailyMission" + i);
                }
                ModNetworking.sendToClient(new DailyMissionS2CPacket(Tmp), (ServerPlayer) player);
            }
            if (TmpNum % 10 == 0) //等级机制改革
            {
                if (data.contains("Xp")) {
                    double Xp = data.getDouble("Xp");
                    int XpLevel = player.experienceLevel;
                    double needXpForLevelUp = Math.pow(Math.E, 3 + (XpLevel / 50d) * 7);
                    double Rate = Xp / needXpForLevelUp;
                    double needXpForLevelUpOriginal = Compute.NeedExpForLevelUpOriginal(XpLevel);
                    if (Rate >= 1) {

                        if (XpLevel % 2 == 1) {
                            if (!data.contains(StringUtils.SkillPoint_Total)) {
                                data.putInt(StringUtils.SkillPoint_Total,XpLevel/2 + 1);
                            } else {
                                data.putInt(StringUtils.SkillPoint_Total,data.getInt(StringUtils.SkillPoint_Total) + 1);
                            }
                            Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                                    Component.literal("你获得了一点专精点，当前剩余的专精点为: " + (data.getInt(StringUtils.SkillPoint_Total) - data.getInt(StringUtils.SkillPoint_Used)) ).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                            if (!data.contains(StringUtils.AbilityPoint_Total)) {
                                data.putInt(StringUtils.AbilityPoint_Total,XpLevel/2 + 1);
                            } else {
                                data.putInt(StringUtils.AbilityPoint_Total,data.getInt(StringUtils.AbilityPoint_Total) + 1);
                            }
                            Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                                    Component.literal("你获得了一点能力点，当前剩余的能力点为: " + (data.getInt(StringUtils.AbilityPoint_Total) - data.getInt(StringUtils.AbilityPoint_Used)) ).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                        }
                        ((ServerPlayer) player).setExperiencePoints(0);
                        ((ServerPlayer) player).setExperienceLevels(XpLevel + 1);
                        data.putDouble("Xp", Xp - needXpForLevelUp);
                        player.experienceProgress = (float) Rate;
                        if (player.experienceLevel == 5 && !data.contains("5Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("5Level", true);
                            ItemStack itemStack = Moditems.NewCurios.get().getDefaultInstance();
                            ItemStack itemStack1 = Moditems.LevelReward5.get().getDefaultInstance();
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack1.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.ItemStackGive(player, itemStack1);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("5").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));

                        } else if (player.experienceLevel == 10 && !data.contains("10Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("10Level", true);
                            ItemStack itemStack = Moditems.LevelReward10.get().getDefaultInstance();
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("10").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        } else if (player.experienceLevel == 15 && !data.contains("15Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("15Level", true);
                            ItemStack itemStack = Moditems.LevelReward15.get().getDefaultInstance();
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("15").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        } else if (player.experienceLevel == 20 && !data.contains("20Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("20Level", true);
                            ItemStack itemStack = Moditems.LevelReward20.get().getDefaultInstance();
                            ItemStack itemStack1 = Moditems.BackPackTickets.get().getDefaultInstance();
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack1.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.ItemStackGive(player, itemStack1);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("20").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        } else if (player.experienceLevel == 25 && !data.contains("25Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("25Level", true);
                            ItemStack itemStack = Moditems.LevelReward25.get().getDefaultInstance();
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("25").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        } else if (player.experienceLevel == 30 && !data.contains("30Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("30Level", true);
                            ItemStack itemStack = Moditems.LevelReward30.get().getDefaultInstance();
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            player.addItem(itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("30").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        } else if (player.experienceLevel == 35 && !data.contains("35Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("35Level", true);
                            ItemStack itemStack = Moditems.LevelReward35.get().getDefaultInstance();
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("35").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        } else if (player.experienceLevel == 40 && !data.contains("40Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("40Level", true);
                            ItemStack itemStack = Moditems.LevelReward40.get().getDefaultInstance();
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("40").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        } else if (player.experienceLevel == 45 && !data.contains("45Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("45Level", true);
                            ItemStack itemStack = Moditems.LevelReward45.get().getDefaultInstance();
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("45").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        } else if (player.experienceLevel == 50 && !data.contains("50Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("50Level", true);
                            ItemStack itemStack = Moditems.LevelReward50.get().getDefaultInstance();
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("50").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        } else if (player.experienceLevel == 55 && !data.contains("55Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("55Level", true);
                            ItemStack itemStack = Moditems.LevelReward55.get().getDefaultInstance();
                            itemStack.getOrCreateTagElement(Utils.MOD_ID);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("55").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        } else if (player.experienceLevel == 60 && !data.contains("60Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean("60Level", true);
                            ItemStack itemStack = Moditems.LevelReward60.get().getDefaultInstance();
                            itemStack.getOrCreateTagElement(Utils.MOD_ID);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("60").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        } else if (player.experienceLevel % 5 == 0 && !data.contains(player.experienceLevel + "Level")) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean(player.experienceLevel + "Level", true);
                            ItemStack itemStack = Moditems.gemspiece.get().getDefaultInstance();
                            itemStack.setCount(player.experienceLevel);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                            append(Component.literal(String.valueOf(player.experienceLevel)).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        }
                    } else {
                        ((ServerPlayer) player).setExperiencePoints(0);
                        ((ServerPlayer) player).setExperienceLevels(XpLevel);
                        /*player.giveExperiencePoints((int) (Rate*needXpForLevelUpOriginal));*/
                        player.experienceProgress = (float) Rate;
                    }
                }
            }
            if (TmpNum % 10 == 0) // 出生点保护
            {
                if (player.getX() >= 330 && player.getX() <= 371 && player.getZ() >= 868 && player.getZ() <= 909) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 3));
                    player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 100, 3));
                }
            }
            if (TmpNum % 10 == 5) // 属性赋予
            {
                ModNetworking.sendToClient(new Attribute0S2CPacket(Compute.PlayerAttackDamage(player),
                        Compute.PlayerBreakDefence(player),
                        Compute.PlayerCriticalHitRate(player),
                        Compute.PlayerCriticalHitDamage(player)), (ServerPlayer) player);
                ModNetworking.sendToClient(new Attribute1S2CPacket(Compute.PlayerManaDamage(player),
                        Compute.PlayerManaBreakDefence(player),
                        Compute.PlayerManaReply(player),
                        Compute.PlayerCoolDownDecrease(player)), (ServerPlayer) player);
                ModNetworking.sendToClient(new Attribute2S2CPacket(Compute.PlayerHealSteal(player),
                        Compute.PlayerDefence(player),
                        Compute.PlayerManaDefence(player),
                        Compute.PlayerSpeedImprove(player)), (ServerPlayer) player);
                ModNetworking.sendToClient(new Attribute3S2CPacket(Compute.PlayerBreakDefence0(player),
                        Compute.PlayerManaBreakDefence0(player),
                        Compute.PlayerAttackRangeUp(player),
                        Compute.ExpGetImprove(player)), (ServerPlayer) player);
                int Plain = -1;
                int Forest = -1;
                int Volcano = -1;
                int Mana = -1;
                int Nether = -1;
                int Snow = -1;
                if (data.contains("Green")) Plain = data.getInt("Green");
                if (data.contains(StringUtils.ForestRune.ForestRune)) Forest = data.getInt(StringUtils.ForestRune.ForestRune);
                if (data.contains("volcanoRune")) Volcano = data.getInt("volcanoRune");
                if (data.contains("ManaRune")) Mana = data.getInt("ManaRune");
                if (data.contains("NetherRune")) Nether = data.getInt("NetherRune");
                if (data.contains("snowRune")) Snow = data.getInt("snowRune");
                ModNetworking.sendToClient(new RuneHud0S2CPacket(Plain, Forest, Volcano, Mana, Nether, Snow), (ServerPlayer) player);
            }
            Queue<Shield> shieldQueue = Utils.PlayerShieldQueue.get(player);
            boolean flag = true;
            if (shieldQueue != null && !shieldQueue.isEmpty()) // 护盾值持续时间计算
            {
                Iterator<Shield> iterator = shieldQueue.iterator();
                while (iterator.hasNext()) {
                    Shield shield = iterator.next();
                    if (shield.getTick() > 0) {
                        flag = false;
                        shield.setTick(shield.getTick() - 1);
                    }
                }
            }
            if (flag && shieldQueue != null) shieldQueue.clear();
            if (TmpNum % 10 == 0) Compute.PlayerShieldCompute(player);
            if (TmpNum % 10 == 0) // 天空属性赋予
            {
                ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
                ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
                ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
                ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
                if (helmet.is(Moditems.SkyArmorHelmet.get())
                        && chest.is(Moditems.SkyArmorChest.get())
                        && leggings.is(Moditems.SkyArmorLeggings.get())
                        && boots.is(Moditems.SkyArmorBoots.get())) {
                    if (player.getHealth() / player.getMaxHealth() > 0.8) {
                        data.putBoolean("SkyArmor80", true);
                        data.putBoolean("SkyArmor40", false);
                    } else if (player.getHealth() / player.getMaxHealth() > 0.4) {
                        data.putBoolean("SkyArmor40", true);
                        data.putBoolean("SkyArmor80", false);
                    } else {
                        if (TmpNum % 200 == 0)
                            Compute.PlayerShieldProvider(player, 200, Compute.PlayerAttackDamage(player) * 0.1);
                    }
                } else {
                    data.putBoolean("SkyArmor80", false);
                    data.putBoolean("SkyArmor40", false);
                }
            }
        }
    }
}

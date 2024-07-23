package com.very.wraq.events.core;

import com.github.alexthe666.alexsmobs.entity.EntityLeafcutterAnt;
import com.very.wraq.Items.Forging.ForgeDraw;
import com.very.wraq.Items.MainStory_1.BackSpawn;
import com.very.wraq.commands.stable.players.DpsCommand;
import com.very.wraq.commands.stable.ops.RoadCommand;
import com.very.wraq.core.MyArrow;
import com.very.wraq.customized.Customize;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.VersionCheckS2CPacket;
import com.very.wraq.networking.misc.AttributePackets.*;
import com.very.wraq.networking.misc.ParticlePackets.EffectParticle.DefencePenetrationParticleS2CPacket;
import com.very.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import com.very.wraq.networking.misc.SmartPhonePackets.SmartPhoneS2CPacket;
import com.very.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.networking.misc.TeamPackets.TeamInfoRequestC2SPacket;
import com.very.wraq.networking.unSorted.ClientLimitCheckS2CPacket;
import com.very.wraq.networking.unSorted.PacketLimitS2CPacket;
import com.very.wraq.networking.unSorted.TimeS2CPacket;
import com.very.wraq.process.func.guide.Guide;
import com.very.wraq.process.func.plan.PlanPlayer;
import com.very.wraq.process.system.border.WorldBorder;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import com.very.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementBow;
import com.very.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSceptre;
import com.very.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword;
import com.very.wraq.process.series.labourDay.LabourDayIronHoe;
import com.very.wraq.process.series.labourDay.LabourDayIronPickaxe;
import com.very.wraq.process.system.element.networking.CurrentSeasonAndResonanceTypeS2CPacket;
import com.very.wraq.process.system.missions.Mission;
import com.very.wraq.process.system.missions.series.dailyMission.DailyMission;
import com.very.wraq.process.func.particle.ParticleProvider;
import com.very.wraq.process.system.respawn.MyRespawnRule;
import com.very.wraq.process.system.season.MySeason;
import com.very.wraq.process.system.tower.TowerMob;
import com.very.wraq.projectiles.MainHandTickItem;
import com.very.wraq.projectiles.mana.BlazeSword;
import com.very.wraq.projectiles.mana.SwordAir;
import com.very.wraq.render.mobEffects.ModEffects;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.Castle.CastleAttackArmor;
import com.very.wraq.series.instance.Castle.CastleManaArmor;
import com.very.wraq.series.instance.Castle.CastleSwiftArmor;
import com.very.wraq.series.instance.Moon.Equip.MoonBelt;
import com.very.wraq.series.newrunes.chapter1.MineNewRune;
import com.very.wraq.series.newrunes.chapter2.EvokerNewRune;
import com.very.wraq.series.newrunes.chapter2.HuskNewRune;
import com.very.wraq.series.newrunes.chapter2.LightningNewRune;
import com.very.wraq.series.newrunes.chapter2.SkyNewRune;
import com.very.wraq.series.newrunes.chapter6.MoonNewRune;
import com.very.wraq.series.overworld.sakuraSeries.Boss2.GoldenAttackOffhand;
import com.very.wraq.series.overworld.sakuraSeries.Boss2.GoldenBook;
import com.very.wraq.series.overworld.chapter2.lavender.LavenderBracelet;
import com.very.wraq.series.overworld.chapter7.star.StarBottle;
import com.very.wraq.series.overworld.sakuraSeries.EarthMana.EarthBook;
import com.very.wraq.series.overworld.sakuraSeries.Slime.SlimeBoots;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Struct.PosAndLastTime;
import com.very.wraq.common.Utils.Struct.Shield;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.attributeValues.PlayerAttributes;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.series.specialevents.summer.SummerEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Mod.EventBusSubscriber
public class ServerPlayerTickEvent {

    public static List<Component> changeLog = new ArrayList<>() {{
        add(Component.literal(" 盾击 ： 每100护甲 提供10%近战攻击增幅 -> 根据护甲值，至多为你提供100%近战攻击增幅。 公式为 = 1 - (800 / (800 + Defence))"));
        add(Component.literal(" 敏捷 ： 每1敏捷 提供50%箭矢攻击增幅 -> 根据敏捷值，至多为你提供150%箭矢攻击增幅。 公式为 = 1.5 - (12 / (8 + Swiftness))"));
        add(Component.literal(" 冰霜系列护甲 ： 雪上覆霜 对移动速度受损的目标造成的伤害提升50% -> 伤害提升15%"));
        add(Component.literal(" 下界混沌护甲 ： 基于目标的魔法抗性提升35% -> 12%"));
        add(Component.literal(" 尘月防具 ： 被动 - 阴晴圆缺 伤害提升 50% -> 15%"));
        add(Component.literal(" 梦月防具 ： 被动 - 披星戴月 伤害提升 75% -> 25%"));
        add(Component.literal(" 暗金项链、黑金胸针：各属性的伤害提升最高值"));
        add(Component.literal(" 锐利：25% -> 10%"));
        add(Component.literal(" 扇风：50% -> 15%"));
        add(Component.literal(" 破冰：50% -> 15%"));
        add(Component.literal(" 凌弱：50% -> 15%"));
        add(Component.literal(" 克强：50% -> 15%"));
        add(Component.literal(" 星星瓶： 被动 - 聚星集屑 每10层提供7%伤害提升 -> 提供3%伤害提升"));
        add(Component.literal(" 遗骸铸盾： 被动 - 碎骨化灰 基于护甲差值提供的伤害提升 -> 基于护甲差值至多提供50%近战攻击增幅"));
        add(Component.literal(" 城堡系列武器： 主动 - 噬魔注能 伤害提升 80% -> 25%"));
        add(Component.literal(" 紫晶器灵被动： 被动 - 晶体析构 每1000差值提供25%伤害提升 -> 基于差值至多提供50%伤害提升"));
        add(Component.literal(" 力凝魔核：每100技能急速 提供50%伤害提升 -> 15%伤害提升"));
    }};
    @SubscribeEvent
    public static void ServerPlayerTick(TickEvent.PlayerTickEvent event) throws IOException, ParseException, SQLException {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            ServerPlayer serverPlayer = (ServerPlayer) player;
            int TickCount = player.getServer().getTickCount();
            Customize.CustomizeTickEvent(player);
            /*Parkour.Tick(player);*/ // 跑酷
            MoonBelt.Tick(player); // 尘月玉缠
            CastleAttackArmor.Tick(player);
            CastleSwiftArmor.Tick(player);
            CastleManaArmor.Tick(player);
            DpsCommand.Tick(player);
            StarBottle.Tick(player);
            Element.PlayerTick(player);
            LifeElementSword.Tick(player);
            LifeElementBow.Tick(player);
            LifeElementSceptre.Tick(player);
            FireEquip.Tick(player);
            /*LabourDayMobSummon.playerTick(event);*/
            Mission.autoSubmit(player); // 自动检测任务是否完成
            LabourDayIronHoe.tick(player);
            LabourDayIronPickaxe.tick(player);
            /*LabourDayMission.sendPacketsToPlayer(player);*/
            DailyMission.statusPacketSend(player);
            RoadCommand.tick(player);
            BackSpawn.tick(player);
            MyRespawnRule.setPlayerSpawnPoint(player);
            ForgeDraw.tick(event);
            LavenderBracelet.tick(player);
            PlanPlayer.setFoodData(serverPlayer);
            GoldenBook.tick(player);
            GoldenAttackOffhand.tick(player);
            EvokerNewRune.tick(player);
            LightningNewRune.tick(player);
            HuskNewRune.tick(player);
            MineNewRune.tick(player);
            WorldBorder.playerTick(event);
            SkyNewRune.tick(player);
            MoonNewRune.tick(player);
            SummerEvent.tick(player);

            if (player.getMainHandItem().getItem() instanceof MainHandTickItem mainHandTickItem) mainHandTickItem.tick(player);

            if (player.tickCount % 20 == 4) {
                List<EntityLeafcutterAnt> ants = serverPlayer.level().getEntitiesOfClass(EntityLeafcutterAnt.class, AABB.ofSize(serverPlayer.position(), 100, 100, 100));
                ants.forEach(ant -> ant.remove(Entity.RemovalReason.KILLED));
            } // 清理切叶蚁

            if (player.tickCount % 5 == 3) {
                CompoundTag data = player.getPersistentData();
                ModNetworking.sendToClient(new SmartPhoneS2CPacket(data.getDouble("VB"), data.getDouble("security0"),
                        data.getDouble("security1"), data.getDouble("security2"), data.getDouble("security3"), data.getDouble("securityGet")), serverPlayer);
            }
            if (player.tickCount % 5 == 1) {
                TeamInfoRequestC2SPacket.module(serverPlayer);
            }

            if (player.tickCount % 20 == 0 && TowerMob.playerIsChallenging3FloorAndInFire(player)) {
                Compute.PlayerHealthDecrease(player, player.getMaxHealth() * 0.1, Component.literal("被烈焰吞噬了").withStyle(CustomStyle.styleOfFire));
            }

            if (player.level().equals(player.getServer().getLevel(Level.END))) {
                Vec3 vec3 = new Vec3(24.5, 88, -135.5);
                if (player.position().distanceTo(vec3) < 1) {
                    serverPlayer.teleportTo(player.getServer().getLevel(Level.END), 100.5, 49, 0.5, 90, -45);
                }
            }

            if (player.tickCount % 250 == 0) Mission.autoAcceptMission(player);

            if (player.tickCount == 300) {
                if (Mission.inProgressMission(player) > 0) {
                    Compute.formatMSGSend(player, Component.literal("任务").withStyle(CustomStyle.styleOfFlexible),
                            Component.literal("你有").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("" + Mission.inProgressMission(player))).
                                    append(Component.literal("尚未完成的任务，使用身份卡或快捷按键打开任务列表").withStyle(ChatFormatting.WHITE)));
                }
                Guide.sendStageToClient(player);
                ModNetworking.sendToClient(new CurrentSeasonAndResonanceTypeS2CPacket(MySeason.currentSeason, Element.PlayerResonanceType.getOrDefault(player, "")), serverPlayer);
            }

            ModNetworking.sendToClient(new PacketLimitS2CPacket(150), serverPlayer);
            if (player.tickCount % 1200 == 0)
                ModNetworking.sendToClient(new ClientLimitCheckS2CPacket(player.getName().getString()), serverPlayer);


            if (player.tickCount % 100 == 27) {
/*                List<Chicken> chickenList = player.level().getEntitiesOfClass(Chicken.class,AABB.ofSize(player.position(),30,30,30));
                List<Cow> cowList = player.level().getEntitiesOfClass(Cow.class,AABB.ofSize(player.position(),30,30,30));
                List<Sheep> sheepList = player.level().getEntitiesOfClass(Sheep.class,AABB.ofSize(player.position(),30,30,30));
                List<Pig> pigList = player.level().getEntitiesOfClass(Pig.class,AABB.ofSize(player.position(),30,30,30));*/
                List<BlazeSword> blazeSwordList = player.level().getEntitiesOfClass(BlazeSword.class, AABB.ofSize(player.position(), 100, 100, 100));
                List<Civil> civilList = player.level().getEntitiesOfClass(Civil.class, AABB.ofSize(player.position(), 100, 100, 100));
/*                chickenList.forEach(chicken -> chicken.remove(Entity.RemovalReason.KILLED));
                cowList.forEach(cow -> cow.remove(Entity.RemovalReason.KILLED));
                sheepList.forEach(sheep -> sheep.remove(Entity.RemovalReason.KILLED));
                pigList.forEach(pig -> pig.remove(Entity.RemovalReason.KILLED));*/
                if (SlimeBoots.IsOn(player))
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 2, false, false, false));
                List<MyArrow> arrowList = player.level().getEntitiesOfClass(MyArrow.class, AABB.ofSize(player.position(), 100, 100, 100));
                arrowList.removeIf(arrow -> arrow.tickCount > 100);
            }

            if (player.tickCount % 20 == 0) EarthBook.earthBookPlayerEffect(player);

            if (player.tickCount % 20 == 0) {
                List<ItemEntity> list = player.level().getEntitiesOfClass(ItemEntity.class, AABB.ofSize(player.position(), 15, 15, 15));
                list.forEach(itemEntity -> {
                    if (itemEntity.getItem().is(ModItems.Value.get())
                            && itemEntity.tickCount > 20) itemEntity.remove(Entity.RemovalReason.KILLED);
                });

                List<SwordAir> list1 = player.level().getEntitiesOfClass(SwordAir.class, AABB.ofSize(player.position(), 15, 15, 15));
                list1.forEach(swordAir -> {
                    if (swordAir.tickCount > 200) swordAir.remove(Entity.RemovalReason.KILLED);
                });
            }

            if (player.tickCount % 10 == 0 && Utils.EndRune2Pos.containsKey(player)) {
                PosAndLastTime posAndLastTime = Utils.EndRune2Pos.get(player);
                List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,
                        AABB.ofSize(posAndLastTime.vec3, 15, 15, 15));
                mobList.forEach(mob -> {
                    if (mob.position().distanceTo(posAndLastTime.vec3) <= 5) {
                        Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, 0.5, false);
                        Compute.Damage.AttackDamageToMonster_RateAdDamage(player, mob, 1);
                    }
                });
                posAndLastTime.TickCount -= 10;
                if (posAndLastTime.TickCount < 0) Utils.EndRune2Pos.remove(player);
            }

            if (player.tickCount % 20 == 0) ModNetworking.sendToClient(new VersionCheckS2CPacket(), serverPlayer);

/*            if (TickCount % 200 == 0 && player.level().equals(serverPlayer.getServer().getLevel(Level.OVERWORLD))
                    && (!Utils.PlayerFireWorkFightCoolDown.containsKey(player) || Utils.PlayerFireWorkFightCoolDown.get(player) < TickCount)) {
                FireWorkGun.RandomSummonFireworkRocket(player.level(),player);
            }*/

            if (TickCount % 60 == 5 && Compute.ArmorCount.Ice(player) > 0 && player.isAlive()) {
                Level level = player.level();
                List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 15, 15, 15));
                mobList.forEach(mob -> {
                    if (mob.distanceTo(player) <= 6) {
                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2, false, false));
                        player.getServer().getPlayerList().getPlayers().forEach(TmpServerPlayer -> {
                            ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 40), TmpServerPlayer);
                        });
                        if (level.getBlockState(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ())).is(Blocks.AIR)) {
                            level.setBlockAndUpdate(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), Blocks.ICE.defaultBlockState());
                            level.destroyBlock(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), false);
                        }
                        Compute.Damage.AttackDamageToMonster_RateAdDamage(player, mob, Compute.ArmorCount.Ice(player) * 0.5);
                        Compute.Damage.ManaDamageToMonster_RateApDamage(player, mob, Compute.ArmorCount.Ice(player) * 0.15, false);
                        Compute.AddSlowDownEffect(mob, 40, 2);
                    }
                });
                ParticleProvider.VerticleCircleParticle(serverPlayer, 1, 6, 80, ParticleTypes.SNOWFLAKE);
            }

            if (TickCount % 20 == 3) {
                Inventory inventory = player.getInventory();
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    if (inventory.getItem(i).is(ModItems.Value.get())
                            || inventory.getItem(i).is(Items.ARROW)) {
                        inventory.removeItem(i, 64);
                    }
                    if (inventory.getItem(i).is(Items.NETHER_STAR)) {
                        ItemStack itemStack = inventory.getItem(i);
                        int count = itemStack.getCount();
                        inventory.setItem(i, new ItemStack(ModItems.LotteryStar.get(), count));
                    }
                }
            }

            if (event.player.level().equals(event.player.getServer().getLevel(Level.NETHER))
                    && player.getZ() > 250 && player.getZ() < 253
                    && player.getX() > -12 && player.getX() < -10
                    && player.getY() == 80) {
                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                        new ClientboundSetEntityMotionPacket(event.player.getId(), new Vec3(0, 1.2, 0));
                serverPlayer.connection.send(clientboundSetEntityMotionPacket);
            }

/*            if (!player.isCreative() && !player.isSpectator() && event.player.level().equals(event.player.getServer().getLevel(Level.OVERWORLD))
                    && (player.getZ() > 2550 || player.getZ() < 100 || player.getX() > 2225 || player.getX() < -500)) {
                if (player.getZ() > 2550) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),player.getX(),player.getY(),2550,0,0);
                if (player.getZ() < 100) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),player.getX(),player.getY(),100,0,0);
                if (player.getX() > 2550) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),2550,player.getY(),player.getZ(),0,0);
                if (player.getX() < -500) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),-500,player.getY(),player.getZ(),0,0);
                Compute.FormatMSGSend(player,Component.literal("边界").withStyle(ChatFormatting.RED),
                        Component.literal("前面的区域，以后再来探索吧!").withStyle(ChatFormatting.WHITE));
            }

            if (!player.isCreative() && !player.isSpectator() && event.player.level().equals(event.player.getServer().getLevel(Level.END))
                    && (player.getZ() > 150 || player.getZ() < -300 || player.getX() > 128 || player.getX() < -80)) {
                if (player.getZ() > 150) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),player.getX(),player.getY(),150,0,0);
                if (player.getZ() < -100) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),player.getX(),player.getY(),-100,0,0);
                if (player.getX() > 110) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),110,player.getY(),player.getZ(),0,0);
                if (player.getX() < -90) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),-90,player.getY(),player.getZ(),0,0);
                Compute.FormatMSGSend(player,Component.literal("边界").withStyle(ChatFormatting.RED),
                        Component.literal("前面的区域，以后再来探索吧!").withStyle(ChatFormatting.WHITE));
            }*/

            if (player.tickCount % 1200 == 0) {
                Utils.dayOnlineCount.put(player.getName().getString(), Utils.dayOnlineCount.getOrDefault(player.getName().getString(), 0) + 1);
            }

            if (player.tickCount % 200 == 0 && Compute.ArmorCount.PurpleIron(player) > 0) {
                int Rate = Compute.ArmorCount.PurpleIron(player);
                Compute.playerShieldProvider(player, 100, player.getMaxHealth() * 0.1 * Rate);
                Compute.effectLastTimeSend(player, ModItems.PurpleIron.get().getDefaultInstance(), 100);
            }

            if (player.tickCount % 10 == 0) Compute.PlayerColdNumStatusUpdate(player);

            if (player.tickCount % 20 == 0) {
                if ((player.level().getBiome(player.getOnPos()).get().coldEnoughToSnow(player.getOnPos())
                        || player.isUnderWater()) && player.getEffect(ModEffects.WARM.get()) == null) {
                    if (player.isUnderWater()) Compute.PlayerColdNumAddOrCost(player, 0.1);
                    else {
                        if (Compute.ArmorCount.Leather(player) > 0) Compute.PlayerColdNumAddOrCost(player, 0.1);
                        else Compute.PlayerColdNumAddOrCost(player, 1);
                    }
                } else Compute.PlayerColdNumAddOrCost(player, -1);

                ModNetworking.sendToClient(new TimeS2CPacket(Compute.CalendarToString(Calendar.getInstance())), serverPlayer);
            }

            if (player.tickCount % 200 == 0 && !player.isCreative()) {
                if (Compute.PlayerCurrentColdNum(player) >= Compute.PlayerMaxColdNum(player)) {
                    Compute.formatMSGSend(player, Component.literal("寒冷").withStyle(CustomStyle.styleOfIce),
                            Component.literal("你的体温正在急剧下降！").withStyle(ChatFormatting.WHITE));
                    player.setHealth(player.getHealth() - player.getMaxHealth() * 0.1f);
                }
            }

/*            if (player.tickCount % 20 == 0) {
                if (player.blockPosition().equals(new BlockPos(52,-53,1039))) {
                    Utils.VolcanoTpCounts.put(player,Utils.VolcanoTpCounts.getOrDefault(player,0) + 1);
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(
                                    Component.literal("传送将在" + (3 - Utils.VolcanoTpCounts.get(player) + "s内开始。。。")).
                                            withStyle(ChatFormatting.RESET).withStyle(CustomStyle.styleOfSky));
                    serverPlayer.connection.send(clientboundSetTitleTextPacket);
                    if (Utils.VolcanoTpCounts.get(player).equals(3)) {
                        serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD),Utils.WorldEntropyPos.get(1).getVec3().x,
                                Utils.WorldEntropyPos.get(1).getVec3().y,
                                Utils.WorldEntropyPos.get(1).getVec3().z,
                                0,0);
                    }
                }
                else Utils.VolcanoTpCounts.remove(player);
            }*/

/*            if (player.tickCount % 36000 == 0) {
                Compute.addOrCostPlayerPsValue(player, 10);
            }*/

/*            if (player.tickCount % 55 == 0) {
                ModNetworking.sendToClient(new PsValueS2CPacket(Compute.getPlayerPsValue(player)),serverPlayer);
            }*/

            CompoundTag data = player.getPersistentData();
            int TmpNum = player.tickCount;

/*            if(TmpNum % 1200 == 0) {
                if(Utils.GemsForPlain) serverPlayer.connection.disconnect(Component.literal("Null"));
                if(Utils.GemsForForest) serverPlayer.connection.disconnect(Component.literal("Null"));
            }*/

/*            if (TmpNum % 12000 == 0) {
                if (data.contains("XRot") && data.contains("YRot")) {
                    if (player.getXRot() == data.getDouble("XRot")
                            && player.getYRot() == data.getDouble("YRot")) {
                        Utils.PlayerAFKMap.put(serverPlayer,true);
                    }
                }
                data.putDouble("XRot", player.getXRot());
                data.putDouble("YRot", player.getYRot());
            }*/

            if (TmpNum % 20 == 0 && Utils.PlayerAFKMap.containsKey(serverPlayer)) {
                if (player.getXRot() != data.getDouble("XRot")
                        || player.getYRot() != data.getDouble("YRot")) {
                    Utils.PlayerAFKMap.remove(serverPlayer);
                    Utils.PlayerAFKSecondsMap.put(serverPlayer, 0);
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("欢迎回来！").withStyle(ChatFormatting.AQUA));
                    serverPlayer.connection.send(clientboundSetTitleTextPacket);
                    ModNetworking.sendToClient(new SoundsS2CPacket(3), serverPlayer);
                    ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                            new ClientboundSetSubtitleTextPacket(Component.literal("").withStyle(ChatFormatting.AQUA));
                    serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                } else {
                    int Seconds = Utils.PlayerAFKSecondsMap.getOrDefault(serverPlayer, 0);
                    Seconds++;
                    Utils.PlayerAFKSecondsMap.put(serverPlayer, Seconds);
                    if (Seconds > 5) {
                        serverPlayer.connection.disconnect(Component.literal("挂机会占用服务器资源，休息好了再来玩吧！").withStyle(ChatFormatting.GREEN));
                    } else {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("赶快动一动！").withStyle(ChatFormatting.AQUA));
                        ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                new ClientboundSetSubtitleTextPacket(Component.literal("还有 " + (5 - Seconds) + "s 你就要被踢出服务器了！").withStyle(ChatFormatting.AQUA));
                        serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                        serverPlayer.connection.send(clientboundSetTitleTextPacket);
                        ModNetworking.sendToClient(new SoundsS2CPacket(6), serverPlayer);
                    }
                }
            }

            if (TmpNum % 20 == 0) {
/*                if (data.contains(StringUtils.Missions.Mission) && data.getInt(StringUtils.Missions.Mission) <= 10) {
                    int MissionNum = data.getInt(StringUtils.Missions.Mission);
                    Vec3 Des = Utils.MissionMap.get(MissionNum).getDes();
                    Vec3 Pos = player.getPosition(1);
                    if (Des.distanceTo(Pos) < 10) {
                        if (MissionNum < 10) {
                            ModNetworking.sendToClient(new MissionCompletedS2CPacket(Utils.MissionMap.get(MissionNum).getTitle()),serverPlayer);
                            ModNetworking.sendToClient(new SoundsS2CPacket(3),serverPlayer);

                            switch (MissionNum) {
                                case 1 -> Compute.ItemStackGive(player,ModItems.PlainSword0.get().getDefaultInstance());
                                case 2 -> Compute.ItemStackGive(player,ModItems.PlainSceptre0.get().getDefaultInstance());
                                case 3 -> Compute.ItemStackGive(player,ModItems.PlainBow0.get().getDefaultInstance());
                                case 4 -> Compute.ItemStackGive(player,ModItems.BackPackTickets.get().getDefaultInstance());
                                case 8 -> {
                                    ItemStack itemStack = Items.ELYTRA.getDefaultInstance();
                                    itemStack.getOrCreateTag().putBoolean("Unbreakable",true);
                                    Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemStack);
                                    map.put(Enchantments.UNBREAKING,5);
                                    EnchantmentHelper.setEnchantments(map,itemStack);
                                    Compute.ItemStackGive(player,itemStack);
                                }
                            }
                            Compute.ItemStackGive(player,ModItems.SilverCoin.get().getDefaultInstance());
                            Compute.ExpPercentGetAndMSGSend(player,0.5,0,player.experienceLevel);
                            MissionNum ++;
                            data.putInt(StringUtils.Missions.Mission,MissionNum);
                            ModNetworking.sendToClient(new MissionInfoS2CPacket(Utils.MissionMap.get(MissionNum).getTitle(),
                                    Utils.MissionMap.get(MissionNum).getContent(),
                                    Utils.MissionMap.get(MissionNum).getDes().x,Utils.MissionMap.get(MissionNum).getDes().y,
                                    Utils.MissionMap.get(MissionNum).getDes().z),serverPlayer);
                            Compute.FormatMSGSend(player,Component.literal("任务").withStyle(CustomStyle.styleOfSpider),
                                    Component.literal("您收到了一个新任务。").withStyle(ChatFormatting.WHITE));

                        }
                        else {
                            ModNetworking.sendToClient(new MissionCompletedS2CPacket(Utils.MissionMap.get(MissionNum).getTitle()),serverPlayer);
                            ModNetworking.sendToClient(new SoundsS2CPacket(3),serverPlayer);
                            MissionNum ++;
                            data.putInt(StringUtils.Missions.Mission,MissionNum);
                            Compute.FormatMSGSend(player,Component.literal("任务").withStyle(CustomStyle.styleOfSpider),
                                    Component.literal("您已经完成了全部新手引导任务。").withStyle(ChatFormatting.WHITE));
                            Compute.ItemStackGive(player,ModItems.GoldCoin.get().getDefaultInstance());
                            Compute.ItemStackGive(player,ModItems.SmartPhone.get().getDefaultInstance());
                            Compute.ExpPercentGetAndMSGSend(player,1,0,player.experienceLevel);
                        }
                    }
                }*/
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
            if (data.contains("NetherRecallBuff") && data.getInt("NetherRecallBuff") > 0)
                data.putInt("NetherRecallBuff", data.getInt("NetherRecallBuff") - 1);
            if (TickCount % 4 == 0 && data.contains(StringUtils.SakuraDemonSword) && data.getInt(StringUtils.SakuraDemonSword) > TickCount) {
                ParticleProvider.VerticleCircleParticle(serverPlayer, 0.2, 1, 20, ParticleTypes.CHERRY_LEAVES);
            }
/*
            if (player.getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline))
                player.teleportTo(data.getDouble("DX"), data.getDouble("DY"), data.getDouble("DZ"));
*/

            //等级机制改革
            {
                serverPlayer.setExperienceLevels(data.getInt(StringUtils.ExpLevel));
                if (data.contains("Xp")) {
                    double Xp = data.getDouble("Xp");
                    int XpLevel = player.experienceLevel;
                    double needXpForLevelUp = Math.pow(Math.E, 3 + (XpLevel / 100d) * 7);
                    double Rate = Xp / needXpForLevelUp;
                    if (Rate >= 1) {
/*                        Element.ResonanceLevelRequireMap.forEach((s, integer) -> {
                            if (XpLevel == integer) {
                                String name = Element.nameMap.get(s);
                                Style style = Element.styleMap.get(s);
                                ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                        new ClientboundSetTitleTextPacket(Component.literal("已解锁" + name).withStyle(style));
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                            }
                            if (XpLevel == 10) Mission.acceptMission(player,
                                    Mission.nameToSerailNumMap.get(Mission.MissionName.LifeElementAltar));
                            if (XpLevel == 25) Mission.acceptMission(player,
                                    Mission.nameToSerailNumMap.get(Mission.MissionName.WaterElementAltar));
                            if (XpLevel == 32) Mission.acceptMission(player,
                                    Mission.nameToSerailNumMap.get(Mission.MissionName.FireElementAltar));
                            if (XpLevel == 40) Mission.acceptMission(player,
                                    Mission.nameToSerailNumMap.get(Mission.MissionName.StoneElementAltar));
                            if (XpLevel == 40) Mission.acceptMission(player,
                                    Mission.nameToSerailNumMap.get(Mission.MissionName.IceElementAltar));
                            if (XpLevel == 70) Mission.acceptMission(player,
                                    Mission.nameToSerailNumMap.get(Mission.MissionName.WindElementAltar));
                            if (XpLevel == 70) Mission.acceptMission(player,
                                    Mission.nameToSerailNumMap.get(Mission.MissionName.LightningElementAltar));
                        });*/

                        if (XpLevel == 99)
                            Compute.itemStackGive(player, ModItems.SkillReset.get().getDefaultInstance());
                        ModNetworking.sendToClient(new SoundsS2CPacket(3), serverPlayer);
                        if (!data.contains(StringUtils.AbilityPoint_Total)) {
                            data.putInt(StringUtils.AbilityPoint_Total, XpLevel);
                        } else {
                            data.putInt(StringUtils.AbilityPoint_Total, data.getInt(StringUtils.AbilityPoint_Total) + 1);
                        }
                        Compute.formatMSGSend(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                                Component.literal("你获得了一点能力点，当前剩余的能力点为: " +
                                        (data.getInt(StringUtils.AbilityPoint_Total) - data.getInt(StringUtils.AbilityPoint_Used))).withStyle(ChatFormatting.WHITE));

                        if (!data.contains(StringUtils.SkillPoint_Total)) {
                            data.putInt(StringUtils.SkillPoint_Total, XpLevel);
                        } else {
                            data.putInt(StringUtils.SkillPoint_Total, data.getInt(StringUtils.SkillPoint_Total) + 1);
                        }
                        Compute.formatMSGSend(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                                Component.literal("你获得了一点专精点，当前剩余的专精点为: " +
                                        (data.getInt(StringUtils.SkillPoint_Total) - data.getInt(StringUtils.SkillPoint_Used))).withStyle(ChatFormatting.WHITE));


                        ((ServerPlayer) player).setExperiencePoints(0);
                        ((ServerPlayer) player).setExperienceLevels(XpLevel + 1);
                        data.putInt(StringUtils.ExpLevel, XpLevel + 1);
                        data.putDouble("Xp", Xp - needXpForLevelUp);
                        player.experienceProgress = (float) Rate;
                        if (player.experienceLevel % 5 == 0 && player.experienceLevel <= 60) {
                            int num = player.experienceLevel;

                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            Compute.formatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("" + num).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.WHITE)));
                        }
                        if (player.experienceLevel % 5 == 0) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            ItemStack itemStack = ModItems.gemPiece.get().getDefaultInstance();
                            itemStack.setCount(player.experienceLevel);
                            Compute.formatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.itemStackGive(player, itemStack);
                            Compute.formatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.WHITE).
                                            append(Component.literal(String.valueOf(player.experienceLevel)).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.WHITE)));
                        }
                    } else {
                        ((ServerPlayer) player).setExperiencePoints(0);
                        ((ServerPlayer) player).setExperienceLevels(XpLevel);
                        player.experienceProgress = (float) Rate;
                    }
                }
            }

            // 出生点保护
            if (TmpNum % 10 == 0) {
                if (player.getX() >= 330 && player.getX() <= 371 && player.getZ() >= 868 && player.getZ() <= 909) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 10));
                    player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 100, 10));
                }
            }

            // 属性赋予
            ModNetworking.sendToClient(new Attribute0S2CPacket(PlayerAttributes.attackDamage(player),
                    PlayerAttributes.defencePenetration(player),
                    PlayerAttributes.critRate(player),
                    PlayerAttributes.critDamage(player)), (ServerPlayer) player);
            ModNetworking.sendToClient(new Attribute1S2CPacket(PlayerAttributes.manaDamage(player),
                    PlayerAttributes.manaPenetration(player),
                    PlayerAttributes.manaRecover(player),
                    PlayerAttributes.powerReleaseSpeed(player)), (ServerPlayer) player);
            ModNetworking.sendToClient(new Attribute2S2CPacket(PlayerAttributes.healthSteal(player),
                    PlayerAttributes.defence(player),
                    PlayerAttributes.manaDefence(player),
                    PlayerAttributes.movementSpeedCurrent(player)), (ServerPlayer) player);
            ModNetworking.sendToClient(new Attribute3S2CPacket(PlayerAttributes.defencePenetration0(player),
                    PlayerAttributes.manaPenetration0(player),
                    PlayerAttributes.attackRangeUp(player),
                    PlayerAttributes.expUp(player)), (ServerPlayer) player);
            ModNetworking.sendToClient(new Attribute4S2CPacket(PlayerAttributes.healthRecover(player),
                    PlayerAttributes.extraSwiftness(player),
                    PlayerAttributes.manaHealthSteal(player),
                    PlayerAttributes.dodgeRate(player)), (ServerPlayer) player);

            List<Shield> shieldQueue = Utils.playerShieldQueue.get(player.getName().getString());
            boolean flag = true;
            if (shieldQueue != null && !shieldQueue.isEmpty()) // 护盾值持续时间计算
            {
                for (Shield shield : shieldQueue) {
                    if (shield.getTick() > 0) {
                        flag = false;
                        shield.setTick(shield.getTick() - 1);
                    }
                }
            }
            if (flag && shieldQueue != null) shieldQueue.clear();

            Compute.PlayerShieldCompute(player);

            if (Compute.ArmorCount.Sky(player) > 0 && TmpNum % 200 == 0 && player.getHealth() / player.getMaxHealth() <= 0.4) {
                Compute.playerShieldProvider(player, 200, PlayerAttributes.attackDamage(player) * 0.1 * Compute.SkySuitEffectRate(player));
            }

            if (TmpNum % 20 == 0) {
                if (data.getInt(StringUtils.MineMonsterEffect) >= TickCount) {
                    if ((player.getMaxHealth() * 0.02f + 10) > player.getHealth()) {
                        Compute.formatBroad(player.level(), Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                                Component.literal(player.getName().getString()).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal("失血过多而死。").withStyle(ChatFormatting.WHITE)));
                    }
                    player.setHealth(player.getHealth() - (player.getMaxHealth() * 0.02f + 10));
                    ModNetworking.sendToClient(new SoundsS2CPacket(2), (ServerPlayer) player);
                }
            }

            if (TmpNum % 60 == 0) {
                if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SnowBossArmorChest.get())) {
                    Level level = player.level();
                    List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(player.getX(), player.getY(), player.getZ()), 6, 6, 6));
                    mobList.forEach(mob -> {
                        CompoundTag MobData = mob.getPersistentData();
                        MobData.putInt(StringUtils.SnowBossSwordActive.PareTime, TickCount + 40);
                        MobData.putDouble(StringUtils.SnowBossSwordActive.Pare, 0.1 + 0.01 * Compute.EntropyRate(data.getInt(StringUtils.Entropy.Snow)));
                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2, false, false));
                        player.getServer().getPlayerList().getPlayers().forEach(TmpServerPlayer -> {
                            ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 40), TmpServerPlayer);
                            ModNetworking.sendToClient(new DefencePenetrationParticleS2CPacket(mob.getId(), 40), TmpServerPlayer);
                        });
                        if (level.getBlockState(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ())).is(Blocks.AIR)) {
                            level.setBlockAndUpdate(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), Blocks.ICE.defaultBlockState());
                            level.destroyBlock(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), false);
                        }
                    });
                    ParticleProvider.VerticleCircleParticle(serverPlayer, 1, 5, 60, ParticleTypes.SNOWFLAKE);
                }
            }
        }
    }
}

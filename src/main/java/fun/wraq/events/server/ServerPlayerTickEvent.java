package fun.wraq.events.server;

import fun.wraq.commands.stable.ops.RoadCommand;
import fun.wraq.commands.stable.players.DpsCommand;
import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.equip.WraqMainHandEquip;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.PosAndLastTime;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.customized.Customize;
import fun.wraq.entities.entities.Civil.Civil;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.moontain.MoontainFloorTitle;
import fun.wraq.items.forge.WraqForge;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.AttributePackets.*;
import fun.wraq.networking.misc.ParticlePackets.EffectParticle.DefencePenetrationParticleS2CPacket;
import fun.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import fun.wraq.networking.misc.SmartPhonePackets.SmartPhoneS2CPacket;
import fun.wraq.networking.misc.SoundsPackets.SoundsS2CPacket;
import fun.wraq.networking.misc.TeamPackets.TeamInfoRequestC2SPacket;
import fun.wraq.networking.unSorted.PacketLimitS2CPacket;
import fun.wraq.networking.unSorted.ServerTickS2CPacket;
import fun.wraq.networking.unSorted.TimeS2CPacket;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.func.security.Security;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.process.system.border.WorldBorder;
import fun.wraq.process.system.cold.ColdSystem;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.element.equipAndCurios.fireElement.FireEquip;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementBow;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSceptre;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword;
import fun.wraq.process.system.endlessinstance.item.special.HoursExHarvestPotion;
import fun.wraq.process.system.entrustment.mob.MobKillEntrustment;
import fun.wraq.process.system.missions.mission2.MissionV2;
import fun.wraq.process.system.point.Point;
import fun.wraq.process.system.profession.pet.allay.AllayPet;
import fun.wraq.process.system.randomevent.impl.special.SpringMobEvent;
import fun.wraq.process.system.reason.Reason;
import fun.wraq.process.system.respawn.MyRespawnRule;
import fun.wraq.process.system.restzone.RestZone;
import fun.wraq.process.system.skill.skillv2.mana.ManaNewSkillFinal0;
import fun.wraq.process.system.skill.skillv2.sword.SwordNewSkillBase3_0;
import fun.wraq.process.system.smelt.Smelt;
import fun.wraq.process.system.spur.events.FishEvent;
import fun.wraq.process.system.spur.events.MineSpur;
import fun.wraq.process.system.spur.events.WoodSpur;
import fun.wraq.process.system.tower.TowerMob;
import fun.wraq.process.system.wayPoints.MyWayPoint;
import fun.wraq.projectiles.mana.swordair.SwordAir;
import fun.wraq.render.hud.networking.PlayerIsInBattleS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.end.curios.EndCrystal;
import fun.wraq.series.events._7shade.SevenShadePiece;
import fun.wraq.series.events.labourDay.LabourDayIronHoe;
import fun.wraq.series.events.labourDay.LabourDayIronPickaxe;
import fun.wraq.series.gems.passive.impl.GemTickHandler;
import fun.wraq.series.instance.mixture.WraqMixture;
import fun.wraq.series.instance.quiver.WraqQuiver;
import fun.wraq.series.instance.series.castle.CastleAttackArmor;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerMainHand;
import fun.wraq.series.instance.series.mushroom.gem.MushroomParasitismGem;
import fun.wraq.series.moontain.equip.weapon.MoontainUtils;
import fun.wraq.series.overworld.divine.DivineUtils;
import fun.wraq.series.overworld.sakura.Boss2.GoldenAttackOffhand;
import fun.wraq.series.overworld.sakura.Boss2.GoldenBook;
import fun.wraq.series.overworld.sakura.Slime.SlimeBoots;
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
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

@Mod.EventBusSubscriber
public class ServerPlayerTickEvent {
    @SubscribeEvent
    public static void ServerPlayerTick(TickEvent.PlayerTickEvent event) throws IOException, ParseException {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            ServerPlayer serverPlayer = (ServerPlayer) player;
            serverPlayer.setPortalCooldown(100);
            int TickCount = Tick.get();
            Customize.CustomizeTickEvent(player);
            /*Parkour.Tick(player);*/ // 跑酷
            CastleAttackArmor.Tick(player);
            DpsCommand.Tick(player);
            Element.handlePlayerTick(player);
            LifeElementSword.Tick(player);
            LifeElementBow.Tick(player);
            LifeElementSceptre.Tick(player);
            FireEquip.Tick(player);
            /*LabourDayMobSummon.playerTick(event);*/
            LabourDayIronHoe.tick(player);
            LabourDayIronPickaxe.tick(player);
            RoadCommand.tick(player);
            MyRespawnRule.setPlayerSpawnPoint(player);
            WraqForge.tick(event);
            PlanPlayer.setFoodData(serverPlayer);
            WorldBorder.playerTick(event);
            /*SummerEvent.tick(player);*/
            MoontainFloorTitle.tick(player);
            GoldenAttackOffhand.handleTick(player);
            GoldenBook.handleTick(player);
            MoontainUtils.tick(player);
            WraqMainHandEquip.serverTick(player);
            WraqOffHandItem.serverTick(player);
            WraqArmor.serverTick(player);
            EndCrystal.tick(player);
            HoursExHarvestPotion.tick(player);
            RestZone.tick(serverPlayer);
            GemTickHandler.handleTick(player);
            HarbingerMainHand.tick(player);
            MyWayPoint.zoneTick(player);
            Civil.handleTick(player);
            Reason.tip(player);
            ModNetworking.sendToClient(new ServerTickS2CPacket(Tick.get()), serverPlayer);
            MobKillEntrustment.handleTick(player);
            AllayPet.handleServerPlayerTick(serverPlayer);
            DelayOperationWithAnimation.playerTick(player);
            WraqQuiver.handleServerPlayerTick(player);
            WraqMixture.handleServerPlayerTick(player);
            ManaNewSkillFinal0.handleServerPlayerTickEvent(serverPlayer);
            MissionV2.handlePlayerTick(player);
            SpringMobEvent.handleServerPlayerTick(player);
            MobSpawn.handlePlayerTick(player);
            SwordNewSkillBase3_0.handleServerPlayerTick(player);
            DivineUtils.handlePlayerTick(player);
            HarbingerMainHand.fix(player);
            Security.fixDuplicatedBackpack(player);
            SevenShadePiece.sendDataToClient(player);
            MineSpur.handleTick(player);
            FishEvent.handleTick(player);
            WoodSpur.handleTick(player);
            ColdSystem.handleTick(player);
            ModNetworking.sendToClient(new TimeS2CPacket(Compute.CalendarToString(Calendar.getInstance())), player);
            MushroomParasitismGem.handlePlayerTick(player);
            Guide.handlePlayerTick(player);

            if (player.tickCount % 10 == 0
                    && (player.isOnFire()
                    || (player.getBlockStateOn().is(Blocks.MAGMA_BLOCK) && !player.isShiftKeyDown()))) {
                if (!player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.REVENANT_GOLDEN_HELMET.get())) {
                    Compute.decreasePlayerHealth(player, player.getHealth() * 0.03,
                            Te.s("被火焰吞没了", CustomStyle.styleOfPower));
                }
            }
            if (player.tickCount % 20 == 0) {
                ModNetworking.sendToClient(new PlayerIsInBattleS2CPacket(Compute.playerIsInBattle(player)), serverPlayer);
            }
            if (player.tickCount % 6000 == 0) {
                Smelt.checkIfAnyProgressFinished(player);
            }
            // 探索点数发包
            if (Tick.get() % 20 == 0) {
                Point.sendDataToClient(player);
            }
            if (player.tickCount % 5 == 3) {
                CompoundTag data = player.getPersistentData();
                ModNetworking.sendToClient(new SmartPhoneS2CPacket(data.getDouble("VB"), data.getDouble("security0"),
                        data.getDouble("security1"), data.getDouble("security2"), data.getDouble("security3"), data.getDouble("securityGet")), serverPlayer);
            }
            if (player.tickCount % 5 == 1) {
                TeamInfoRequestC2SPacket.module(serverPlayer);
            }
            if (player.tickCount % 20 == 0 && TowerMob.playerIsChallenging3FloorAndInFire(player)) {
                Compute.decreasePlayerHealth(player, player.getMaxHealth() * 0.1, Component.literal("被烈焰吞噬了").withStyle(CustomStyle.styleOfFire));
            }
            if (player.level().equals(player.getServer().getLevel(Level.END))) {
                Vec3 vec3 = new Vec3(24.5, 88, -135.5);
                if (player.position().distanceTo(vec3) < 1) {
                    serverPlayer.teleportTo(player.getServer().getLevel(Level.END), 137.5, 50, 0.5, 90, -45);
                }
            }
            if (player.tickCount % 20 == 0) {
                Guide.sendStageToClientV2(player);
            }
            if (player.tickCount % 40 == 0) {
                ModNetworking.sendToClient(new PacketLimitS2CPacket(200), serverPlayer);
            }
            if (player.tickCount % 100 == 27) {
                if (SlimeBoots.IsOn(player))
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 2, false, false, false));
                List<MyArrow> arrowList = player.level().getEntitiesOfClass(MyArrow.class, AABB.ofSize(player.position(), 100, 100, 100));
                arrowList.removeIf(arrow -> arrow.tickCount > 100);
            }
            if (player.tickCount % 20 == 0) {
                List<ItemEntity> list = player.level().getEntitiesOfClass(ItemEntity.class, AABB.ofSize(player.position(), 15, 15, 15));
                list.forEach(itemEntity -> {
                    if (itemEntity.getItem().is(ModItems.VALUE.get())
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
                        Damage.causeRateApDamageToMonster(player, mob, 0.5, false);
                        Damage.causeAttackDamageToMonster_RateAdDamage(player, mob, 1);
                    }
                });
                posAndLastTime.TickCount -= 10;
                if (posAndLastTime.TickCount < 0) Utils.EndRune2Pos.remove(player);
            }
            if (TickCount % 60 == 5 && SuitCount.getIceSuitCount(player) > 0 && player.isAlive()) {
                Level level = player.level();
                List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 15, 15, 15));
                mobList.forEach(mob -> {
                    if (mob.distanceTo(player) <= 6) {
                        Compute.addSlowDownEffect(mob, 40, 2);
                        player.getServer().getPlayerList().getPlayers().forEach(TmpServerPlayer -> {
                            ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), 40), TmpServerPlayer);
                        });
                        if (level.getBlockState(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ())).is(Blocks.AIR)) {
                            level.setBlockAndUpdate(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), Blocks.ICE.defaultBlockState());
                            level.destroyBlock(new BlockPos(mob.getBlockX(), mob.getBlockY() + 1, mob.getBlockZ()), false);
                        }
                        Damage.causeAttackDamageToMonster_RateAdDamage(player, mob, SuitCount.getIceSuitCount(player) * 0.5);
                        Damage.causeRateApDamageToMonster(player, mob, SuitCount.getIceSuitCount(player) * 0.15, false);
                        Compute.addSlowDownEffect(mob, 40, 2);
                    }
                });
                ParticleProvider.VerticleCircleParticle(serverPlayer, 1, 6, 80, ParticleTypes.SNOWFLAKE);
            }

            if (TickCount % 20 == 3) {
                Inventory inventory = player.getInventory();
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    if (inventory.getItem(i).is(ModItems.VALUE.get())
                            || inventory.getItem(i).is(Items.ARROW)) {
                        inventory.removeItem(i, 64);
                    }
                    if (inventory.getItem(i).is(Items.NETHER_STAR)) {
                        ItemStack itemStack = inventory.getItem(i);
                        int count = itemStack.getCount();
                        inventory.setItem(i, new ItemStack(ModItems.LOTTERY_STAR.get(), count));
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

            if (player.tickCount % 1200 == 0) {
                Utils.dayOnlineCount.put(player.getName().getString(), Utils.dayOnlineCount.getOrDefault(player.getName().getString(), 0) + 1);
            }

            if (player.tickCount % 200 == 0 && SuitCount.getPurpleIronSuitCount(player) > 0) {
                int Rate = SuitCount.getPurpleIronSuitCount(player);
                Shield.providePlayerShield(player, 100, player.getMaxHealth() * 0.1 * Rate);
                Compute.sendEffectLastTime(player, ModItems.PURPLE_IRON_INGOT.get().getDefaultInstance(), 100);
            }
            CompoundTag data = player.getPersistentData();
            int TmpNum = player.tickCount;
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

            //等级机制改革
            {
                serverPlayer.setExperienceLevels(data.getInt(StringUtils.ExpLevel));
                if (data.contains("Xp")) {
                    double xpValue = data.getDouble("Xp");
                    int oldLevel = player.experienceLevel;
                    double needXpForLevelUp = Compute.getCurrentXpLevelUpNeedXpPoint(player.experienceLevel);
                    double rate = xpValue / needXpForLevelUp;
                    if (rate >= 1) {
                        ((ServerPlayer) player).setExperiencePoints(0);
                        ((ServerPlayer) player).setExperienceLevels(oldLevel + 1);
                        data.putInt(StringUtils.ExpLevel, oldLevel + 1);
                        data.putDouble("Xp", xpValue - needXpForLevelUp);
                        player.experienceProgress = (float) rate;

                        if ((oldLevel + 1) % 2 == 0) {
                            if (!data.contains(StringUtils.AbilityPoint_Total)) {
                                data.putInt(StringUtils.AbilityPoint_Total, (oldLevel + 1) / 2);
                            } else {
                                data.putInt(StringUtils.AbilityPoint_Total, data.getInt(StringUtils.AbilityPoint_Total) + 1);
                            }
                            Compute.sendFormatMSG(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                                    Component.literal("你获得了一点能力点，当前剩余的能力点为: " +
                                            (data.getInt(StringUtils.AbilityPoint_Total) - data.getInt(StringUtils.AbilityPoint_Used))).withStyle(ChatFormatting.WHITE));

                            if (!data.contains(StringUtils.SkillPoint_Total)) {
                                data.putInt(StringUtils.SkillPoint_Total, (oldLevel + 1) / 2);
                            } else {
                                data.putInt(StringUtils.SkillPoint_Total, data.getInt(StringUtils.SkillPoint_Total) + 1);
                            }
                            Compute.sendFormatMSG(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                                    Component.literal("你获得了一点专精点，当前剩余的专精点为: " +
                                            (data.getInt(StringUtils.SkillPoint_Total) - data.getInt(StringUtils.SkillPoint_Used))).withStyle(ChatFormatting.WHITE));
                        }
                        if (player.experienceLevel % 5 == 0) {
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            Compute.formatBroad(player.level(), Te.m("经验", ChatFormatting.LIGHT_PURPLE),
                                    Te.s(player.getName(), " 通过探索，达到了", String.valueOf(player.experienceLevel), ChatFormatting.LIGHT_PURPLE, "级"));
                        }
                        if (oldLevel == 99) {
                            InventoryOperation.giveItemStack(player, ModItems.SKILL_RESET.get().getDefaultInstance());
                        }
                        ModNetworking.sendToClient(new SoundsS2CPacket(3), serverPlayer);
                    } else {
                        ((ServerPlayer) player).setExperiencePoints(0);
                        ((ServerPlayer) player).setExperienceLevels(oldLevel);
                        player.experienceProgress = (float) rate;
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
            if (player.tickCount % 2 == 0) {
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
            }
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
            Shield.computePlayerShield(player);
            if (SuitCount.getSkySuitCount(player) > 0 && TmpNum % 200 == 0 && player.getHealth() / player.getMaxHealth() <= 0.4) {
                Shield.providePlayerShield(player, 200, PlayerAttributes.attackDamage(player) * 0.1 * Compute.SkySuitEffectRate(player));
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
                if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SNOW_BOSS_CHEST.get())) {
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

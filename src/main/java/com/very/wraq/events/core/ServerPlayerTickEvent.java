package com.very.wraq.events.core;

import com.very.wraq.coreAttackModule.MyArrow;
import com.very.wraq.customized.Customize;
import com.very.wraq.customized.players.bow.Wcndymlgb.WcndymlgbCurios;
import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanilla1;
import com.very.wraq.customized.players.sceptre.liulixian_.LiuLiXianCurios1F;
import com.very.wraq.entities.entities.Civil.Civil;
import com.very.wraq.commands.stable.DpsCommand;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.AttributePackets.*;
import com.very.wraq.netWorking.misc.EndRune3TypeS2CPacket;
import com.very.wraq.netWorking.misc.ParticlePackets.EffectParticle.DefencePenetrationParticleS2CPacket;
import com.very.wraq.netWorking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import com.very.wraq.netWorking.misc.PsValueS2CPacket;
import com.very.wraq.netWorking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.netWorking.unSorted.ClientLimitCheckS2CPacket;
import com.very.wraq.netWorking.unSorted.PacketLimitS2CPacket;
import com.very.wraq.netWorking.unSorted.TimeS2CPacket;
import com.very.wraq.netWorking.VersionCheckS2CPacket;
import com.very.wraq.process.element.Element;
import com.very.wraq.process.element.equipAndCurios.fireElement.FireEquip;
import com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementBow;
import com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementSceptre;
import com.very.wraq.process.element.equipAndCurios.lifeElement.LifeElementSword;
import com.very.wraq.process.labourDay.LabourDayIronHoe;
import com.very.wraq.process.labourDay.LabourDayIronPickaxe;
import com.very.wraq.process.labourDay.LabourDayMobSummon;
import com.very.wraq.process.missions.series.labourDay.LabourDayMission;
import com.very.wraq.process.parkour.Parkour;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.process.missions.Mission;
import com.very.wraq.projectiles.mana.BlazeSword;
import com.very.wraq.projectiles.mana.SwordAir;
import com.very.wraq.render.mobEffects.ModEffects;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.Castle.CastleAttackArmor;
import com.very.wraq.series.instance.Castle.CastleManaArmor;
import com.very.wraq.series.instance.Castle.CastleSwiftArmor;
import com.very.wraq.series.instance.Moon.Equip.MoonBelt;
import com.very.wraq.series.overWorld.MainStoryVII.StarBottle;
import com.very.wraq.series.overWorld.SakuraSeries.EarthMana.EarthBook;
import com.very.wraq.series.overWorld.SakuraSeries.Slime.SlimeBoots;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Struct.PosAndLastTime;
import com.very.wraq.valueAndTools.Utils.Struct.Shield;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
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
import java.util.*;

@Mod.EventBusSubscriber
public class ServerPlayerTickEvent {
    public static List<Component> changeLog = new ArrayList<>(){{
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
    public static void PersisData(TickEvent.PlayerTickEvent event) throws ParseException, IOException {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            ServerPlayer serverPlayer = (ServerPlayer) player;
            int TickCount = player.getServer().getTickCount();
            Customize.CustomizeTickEvent(player);
            Parkour.Tick(player);
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
            LabourDayMobSummon.playerTick(event);
            Mission.autoSubmit(player); // 自动检测任务是否完成
            LabourDayIronHoe.tick(player);
            LabourDayIronPickaxe.tick(player);
            LabourDayMission.sendPacketsToPlayer(player);

            if (player.level().equals(player.getServer().getLevel(Level.END))) {
                Vec3 vec3 = new Vec3(24.5, 88, -135.5);
                if (player.position().distanceTo(vec3) < 1) {
                    serverPlayer.teleportTo(player.getServer().getLevel(Level.END),100.5,50,0.5,90,-45);
                }
            }

            if (player.tickCount == 200) {
                player.sendSystemMessage(Component.literal("1.0.1版本更新内容：").withStyle(ChatFormatting.AQUA));
                player.sendSystemMessage(Component.literal(" 元素大幅改动").withStyle(ChatFormatting.AQUA));
                player.sendSystemMessage(Component.literal(" 1.新增元素祭坛，玩家达到祭坛需求的等级后，右键祭坛指定方块，可将元素共鸣切换至祭坛对应元素。右击与当前共鸣元素相同的元素将会使当前的共鸣元素变为无。").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(Component.literal(" 2.元素共鸣：为你的主要攻击（近战/箭矢/法球/法术）附带基于归一化元素强度的元素，参与元素反应。").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(Component.literal(" 3.对绝大多数的主手武器添加了对应的归一化元素强度。").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(Component.literal(" 4.为绝大多数区域的怪物添加了对应的元素附着。").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(Component.literal(" 5.为绝大多数法术添加的对应的元素附着。").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(Component.literal(" 6.绝大多数区域的怪物都将会掉落对应元素的微型元素碎片。").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(Component.literal(" 7.小型元素碎片的兑换宝石更改为群屑。").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(Component.literal(" 8.现在，玩家下次攻击的元素附着将会显示在十字准星左下方。").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(Component.literal(" 9.终末寂域新增返回终末之地。").withStyle(ChatFormatting.WHITE));
                player.sendSystemMessage(Component.literal(" 推荐加入更新通知群：以获取更新信息:693292427").withStyle(ChatFormatting.WHITE));
            }

            if (player.tickCount % 250 == 0) Mission.autoAcceptMission(player);

            if (player.tickCount == 300) {
                if (Mission.inProgressMission(player) > 0) {
                    Compute.FormatMSGSend(player, Component.literal("任务").withStyle(CustomStyle.styleOfFlexible),
                            Component.literal("你有").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("" + Mission.inProgressMission(player))).
                                    append(Component.literal("尚未完成的任务").withStyle(ChatFormatting.WHITE)));
                }
            }

            ModNetworking.sendToClient(new PacketLimitS2CPacket(150),serverPlayer);
            if (player.tickCount % 1200 == 0) ModNetworking.sendToClient(new ClientLimitCheckS2CPacket(player.getName().getString()),serverPlayer);

            /*            if (player.tickCount % 20 == 0) {
                Inventory inventory = player.getInventory();
                for (int i = 0 ; i < inventory.getMaxStackSize() ; i ++) {
                    ItemStack itemStack = inventory.getItem(i);
                    if (itemStack.getTagElement(Utils.MOD_ID) != null
                            && itemStack.getTagElement(Utils.MOD_ID).isEmpty())
                        inventory.setItem(i,new ItemStack(itemStack.getItem(),itemStack.getCount()));
                }
            } // 尝试修复物品无法堆叠bug。(自动转换不可堆叠物品)*/

            if (player.tickCount % 100 == 27) {
                List<Chicken> chickenList = player.level().getEntitiesOfClass(Chicken.class,AABB.ofSize(player.position(),30,30,30));
                List<Cow> cowList = player.level().getEntitiesOfClass(Cow.class,AABB.ofSize(player.position(),30,30,30));
                List<Sheep> sheepList = player.level().getEntitiesOfClass(Sheep.class,AABB.ofSize(player.position(),30,30,30));
                List<Pig> pigList = player.level().getEntitiesOfClass(Pig.class,AABB.ofSize(player.position(),30,30,30));
                List<BlazeSword> blazeSwordList = player.level().getEntitiesOfClass(BlazeSword.class,AABB.ofSize(player.position(),100,100,100));
                List<Civil> civilList = player.level().getEntitiesOfClass(Civil.class,AABB.ofSize(player.position(),100,100,100));
                chickenList.forEach(chicken -> chicken.remove(Entity.RemovalReason.KILLED));
                cowList.forEach(cow -> cow.remove(Entity.RemovalReason.KILLED));
                sheepList.forEach(sheep -> sheep.remove(Entity.RemovalReason.KILLED));
                pigList.forEach(pig -> pig.remove(Entity.RemovalReason.KILLED));
                if (SlimeBoots.IsOn(player) && !Parkour.PlayerIsInParkourRange(player)) player.addEffect(new MobEffectInstance(MobEffects.JUMP,200,2,false,false,false));
                List<MyArrow> arrowList = player.level().getEntitiesOfClass(MyArrow.class,AABB.ofSize(player.position(),100,100,100));
                arrowList.removeIf(arrow -> arrow.tickCount > 100);
                blazeSwordList.forEach(SoraVanilla1::Remove);
                civilList.forEach(WcndymlgbCurios::Remove);
            }

            if (player.tickCount % 20 == 0) EarthBook.EarthBookPlayerEffect(player);

            if (player.tickCount % 20 == 0) {
                List<ItemEntity> list = player.level().getEntitiesOfClass(ItemEntity.class,AABB.ofSize(player.position(),15,15,15));
                list.forEach(itemEntity -> {
                    if (itemEntity.getItem().is(ModItems.Value.get())
                            && itemEntity.tickCount > 20) itemEntity.remove(Entity.RemovalReason.KILLED);
                });

                List<SwordAir> list1 = player.level().getEntitiesOfClass(SwordAir.class,AABB.ofSize(player.position(),15,15,15));
                list1.forEach(swordAir -> {
                    if (swordAir.tickCount > 200) swordAir.remove(Entity.RemovalReason.KILLED);
                });
            }

            if (player.tickCount % 10 == 0 && Utils.EndRune2Pos.containsKey(player)) {
                PosAndLastTime posAndLastTime = Utils.EndRune2Pos.get(player);
                List<Mob> mobList = player.level().getEntitiesOfClass(Mob.class,
                        AABB.ofSize(posAndLastTime.vec3,15,15,15));
                mobList.forEach(mob -> {
                    if (mob.position().distanceTo(posAndLastTime.vec3) <= 5) {
                        Compute.Damage.ManaDamageToMonster_RateApDamage(player,mob,0.5,false);
                        Compute.Damage.AttackDamageToMonster_RateAdDamage(player,mob,1);
                    }
                });
                posAndLastTime.TickCount -= 10;
                if (posAndLastTime.TickCount < 0) Utils.EndRune2Pos.remove(player);
            }

            if (player.tickCount % 20 == 0) ModNetworking.sendToClient(new VersionCheckS2CPacket(),serverPlayer);

/*            if (TickCount % 200 == 0 && player.level().equals(serverPlayer.getServer().getLevel(Level.OVERWORLD))
                    && (!Utils.PlayerFireWorkFightCoolDown.containsKey(player) || Utils.PlayerFireWorkFightCoolDown.get(player) < TickCount)) {
                FireWorkGun.RandomSummonFireworkRocket(player.level(),player);
            }*/

            if (TickCount % 60 == 5 && Compute.ArmorCount.Ice(player) > 0 && player.isAlive()) {
                Level level = player.level();
                List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),15,15,15));
                mobList.forEach(mob -> {
                    if (mob.distanceTo(player) <= 6) {
                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,2,false,false));
                        player.getServer().getPlayerList().getPlayers().forEach(TmpServerPlayer -> {
                            ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(),40),TmpServerPlayer);
                        });
                        if (level.getBlockState(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ())).is(Blocks.AIR)) {
                            level.setBlockAndUpdate(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ()),Blocks.ICE.defaultBlockState());
                            level.destroyBlock(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ()),false);
                        }
                        Compute.Damage.AttackDamageToMonster_RateAdDamage(player,mob,Compute.ArmorCount.Ice(player) * 0.5);
                        Compute.Damage.ManaDamageToMonster_RateApDamage(player,mob,Compute.ArmorCount.Ice(player) * 0.15,false);
                        Compute.AddSlowDownEffect(mob,40,2);
                    }
                });
                ParticleProvider.VerticleCircleParticle(serverPlayer,1,6,80, ParticleTypes.SNOWFLAKE);
            }

            if (TickCount % 20 == 3) {
                Inventory inventory = player.getInventory();
                for (int i = 0; i < inventory.getContainerSize(); i ++) {
                    if (inventory.getItem(i).is(ModItems.Value.get())
                            || inventory.getItem(i).is(Items.ARROW)) {
                        inventory.removeItem(i,64);
                    }
                    if (inventory.getItem(i).is(Items.NETHER_STAR)) {
                        ItemStack itemStack = inventory.getItem(i);
                        int count = itemStack.getCount();
                        inventory.setItem(i,new ItemStack(ModItems.LotteryStar.get(),count));
                    }
                }
            }

            if (event.player.level().equals(event.player.getServer().getLevel(Level.NETHER))
                    && player.getZ() > 250 && player.getZ() < 253
                    && player.getX() > -12 && player.getX() < -10
                    && player.getY() == 80) {
                ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                        new ClientboundSetEntityMotionPacket(event.player.getId(),new Vec3(0,1.2,0));
                serverPlayer.connection.send(clientboundSetEntityMotionPacket);
            }

            if (!player.isCreative() && event.player.level().equals(event.player.getServer().getLevel(Level.OVERWORLD))
                    && (player.getZ() > 2550 || player.getZ() < 100 || player.getX() > 2225 || player.getX() < -500)) {
                if (player.getZ() > 2550) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),player.getX(),player.getY(),2550,0,0);
                if (player.getZ() < 100) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),player.getX(),player.getY(),100,0,0);
                if (player.getX() > 2550) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),2550,player.getY(),player.getZ(),0,0);
                if (player.getX() < -500) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),-500,player.getY(),player.getZ(),0,0);
                Compute.FormatMSGSend(player,Component.literal("边界").withStyle(ChatFormatting.RED),
                        Component.literal("前面的区域，以后再来探索吧!").withStyle(ChatFormatting.WHITE));
            }

            if (!player.isCreative() && event.player.level().equals(event.player.getServer().getLevel(Level.END))
                    && (player.getZ() > 150 || player.getZ() < -300 || player.getX() > 128 || player.getX() < -80)) {
                if (player.getZ() > 150) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),player.getX(),player.getY(),150,0,0);
                if (player.getZ() < -100) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),player.getX(),player.getY(),-100,0,0);
                if (player.getX() > 110) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),110,player.getY(),player.getZ(),0,0);
                if (player.getX() < -90) ((ServerPlayer) player).teleportTo((ServerLevel) player.level(),-90,player.getY(),player.getZ(),0,0);
                Compute.FormatMSGSend(player,Component.literal("边界").withStyle(ChatFormatting.RED),
                        Component.literal("前面的区域，以后再来探索吧!").withStyle(ChatFormatting.WHITE));
            }

            if (player.tickCount % 1200 == 0) {
                Utils.dayOnlineCount.put(player.getName().getString(),Utils.dayOnlineCount.getOrDefault(player.getName().getString(),0) + 1);
            }

            if (player.tickCount % 200 == 0 && Compute.ArmorCount.PurpleIron(player) > 0) {
                int Rate = Compute.ArmorCount.PurpleIron(player);
                Compute.PlayerShieldProvider(player,100,player.getMaxHealth() * 0.1 * Rate);
                Compute.EffectLastTimeSend(player,ModItems.PurpleIron.get().getDefaultInstance(),100);
            }

            if (player.tickCount % 10 == 0) Compute.PlayerColdNumStatusUpdate(player);

            if (player.tickCount % 20 == 0) {
                if ((player.level().getBiome(player.getOnPos()).get().coldEnoughToSnow(player.getOnPos())
                        || player.isUnderWater()) && player.getEffect(ModEffects.WARM.get()) == null) {
                    if (player.isUnderWater()) Compute.PlayerColdNumAddOrCost(player,0.1);
                    else {
                        if (Compute.ArmorCount.Leather(player) > 0) Compute.PlayerColdNumAddOrCost(player,0.1);
                        else Compute.PlayerColdNumAddOrCost(player,1);
                    }
                }
                else Compute.PlayerColdNumAddOrCost(player,-1);

                ModNetworking.sendToClient(new TimeS2CPacket(Compute.CalendarToString(Calendar.getInstance())),serverPlayer);
            }

            if (player.tickCount % 200 == 0 && !player.isCreative()) {
                if (Compute.PlayerCurrentColdNum(player) >= Compute.PlayerMaxColdNum(player)) {
                    Compute.FormatMSGSend(player,Component.literal("寒冷").withStyle(CustomStyle.styleOfIce),
                            Component.literal("你的体温正在急剧下降！").withStyle(ChatFormatting.WHITE));
                    player.setHealth(player.getHealth() - player.getMaxHealth() * 0.1f);
                }
            }

            if (player.tickCount % 20 == 0) {
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
            }

            if (player.tickCount % 36000 == 0) {
                Compute.addOrCostPlayerPsValue(player, 10);
            }

            if (player.tickCount % 55 == 0) {
                ModNetworking.sendToClient(new PsValueS2CPacket(Compute.getPlayerPsValue(player)),serverPlayer);
            }

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
                    Utils.PlayerAFKSecondsMap.put(serverPlayer,0);
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("欢迎回来！").withStyle(ChatFormatting.AQUA));
                    serverPlayer.connection.send(clientboundSetTitleTextPacket);
                    ModNetworking.sendToClient(new SoundsS2CPacket(3),serverPlayer);
                    ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                            new ClientboundSetSubtitleTextPacket(Component.literal("").withStyle(ChatFormatting.AQUA));
                    serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
                }
                else {
                    int Seconds = Utils.PlayerAFKSecondsMap.getOrDefault(serverPlayer,0);
                    Seconds ++;
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
                        ModNetworking.sendToClient(new SoundsS2CPacket(6),serverPlayer);
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
/*            if (TmpNum % 40 == 0) {
                int LightningArmorCount = Compute.LightningArmorCount(player);
                if (Utils.IsLandBarrier && !player.isCreative() && player.level().equals(player.getServer().getLevel(Level.OVERWORLD))) {
                    if (LightningArmorCount == 0 && player.getX() < 1523 && player.getX() > 1182 && player.getZ() < 633 && player.getZ() > 371
                            && player.getY() < 74 && player.getY() > 60) {
                        Compute.FormatMSGSend(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                                Component.literal("岛上的神秘力量正在阻止你向岛内前进。").withStyle(ChatFormatting.WHITE));
                        player.addEffect(new MobEffectInstance(MobEffects.WITHER, 60, 10));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, player.level());
                        lightningBolt.setDamage(10);
                        lightningBolt.moveTo(player.position());
                        player.level().addFreshEntity(lightningBolt);
                    }
                }
                if (data.contains("Lighting") && data.getBoolean("Lighting")) {
                    LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, player.level());
                    lightningBolt.setDamage(100);
                    lightningBolt.moveTo(player.position());
                    player.level().addFreshEntity(lightningBolt);
                    data.putBoolean("Lighting", false);
                }
            }*/
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
            }
            if (data.contains("snowRune") && data.getInt("snowRune") == 3 && data.contains("snowRune3") && data.getInt("snowRune3") > 0) {
                data.putInt("snowRune3", data.getInt("snowRune3") - 1);
            }
            if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 3 && data.contains("DGreen3") && data.getInt("DGreen3") > 0) {
                data.putInt("DGreen3", data.getInt("DGreen3") - 1);
            }
            if (data.contains(StringUtils.ForestRune.ForestRune) && data.getInt(StringUtils.ForestRune.ForestRune) == 2 && data.contains(StringUtils.ForestRune.ForestRune2) && data.getInt(StringUtils.ForestRune.ForestRune2) > 0) {
                data.putInt(StringUtils.ForestRune.ForestRune2, data.getInt(StringUtils.ForestRune.ForestRune2) - 1);
            }
            if (data.contains("ManaRune") && data.getInt("ManaRune") == 2 && data.contains("ManaRune2") && data.getInt("ManaRune2") > 0) {
                data.putInt("ManaRune2", data.getInt("ManaRune2") - 1);
            }
            if (TickCount % 4 == 0 && data.contains(StringUtils.SakuraDemonSword) && data.getInt(StringUtils.SakuraDemonSword) > TickCount) {
                ParticleProvider.VerticleCircleParticle(serverPlayer,0.2,1,20,ParticleTypes.CHERRY_LEAVES);
            }
/*            if (player.getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline) && TmpNum > 800) {
                serverPlayer.connection.disconnect(Component.literal("似乎用了太长的时间在登录上。"));
            }*/
/*
            if (player.getPersistentData().getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline))
                player.teleportTo(data.getDouble("DX"), data.getDouble("DY"), data.getDouble("DZ"));
*/
            if (data.contains("Green") && data.getInt("Green") == 0 && !player.level().isClientSide() && TmpNum % 100 == 0) {
                Compute.PlayerHeal(player,(player.getMaxHealth() - player.getHealth()) * 0.1);
            } else {
                if (LiuLiXianCurios1F.IsLiuLiXian(player)) player.getFoodData().setFoodLevel(20);
                else {
                    if (data.contains("Green") && data.getInt("Green") == 1 && !player.level().isClientSide()) {
                        player.getFoodData().setFoodLevel(8);
                    }
                }
                if (data.getInt("Green") == 3 && data.getInt("Green3") < 200) {
                   data.putInt("Green3", data.getInt("Green3") + 1);
                }
            }

/*            if (TmpNum % 20 == 0) //每日任务击杀数发包
            {
                int[] Tmp = new int[20];
                for (int i = 1; i <= 17; i++) {
                    Tmp[i] = data.getInt("DailyMission" + i);
                }
                ModNetworking.sendToClient(new DailyMissionS2CPacket(Tmp), (ServerPlayer) player);
            }*/
            //等级机制改革
            {
                serverPlayer.setExperienceLevels(data.getInt(StringUtils.ExpLevel));
                if (data.contains("Xp") && data.contains("XpReset")) {
                    double Xp = data.getDouble("Xp");
                    int XpLevel = player.experienceLevel;
                    double needXpForLevelUp = Math.pow(Math.E, 3 + (XpLevel / 100d) * 7);
                    double Rate = Xp / needXpForLevelUp;
                    if (Rate >= 1) {
                        Element.ResonanceLevelRequireMap.forEach((s, integer) -> {
                            if (XpLevel == integer) {
                                String name = Element.nameMap.get(s);
                                Style style = Element.styleMap.get(s);
                                ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                        new ClientboundSetTitleTextPacket(Component.literal("已解锁" + name).withStyle(style));
                                ClientboundSetSubtitleTextPacket clientboundSetSubtitleTextPacket =
                                        new ClientboundSetSubtitleTextPacket(Component.literal("前往" + name + "祭坛与之共鸣").withStyle(style));
                                serverPlayer.connection.send(clientboundSetTitleTextPacket);
                                serverPlayer.connection.send(clientboundSetSubtitleTextPacket);
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
                        });

                        if (XpLevel == 99) Compute.ItemStackGive(player,ModItems.SkillReset.get().getDefaultInstance());
                        ModNetworking.sendToClient(new SoundsS2CPacket(3),serverPlayer);
                        if (!data.contains(StringUtils.AbilityPoint_Total)) {
                            data.putInt(StringUtils.AbilityPoint_Total, XpLevel);
                        } else {
                            data.putInt(StringUtils.AbilityPoint_Total, data.getInt(StringUtils.AbilityPoint_Total) + 1);
                        }
                        Compute.FormatMSGSend(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                                Component.literal("你获得了一点能力点，当前剩余的能力点为: " +
                                        (data.getInt(StringUtils.AbilityPoint_Total) - data.getInt(StringUtils.AbilityPoint_Used))).withStyle(ChatFormatting.WHITE));

                        if (!data.contains(StringUtils.SkillPoint_Total)) {
                            data.putInt(StringUtils.SkillPoint_Total, XpLevel);
                        } else {
                            data.putInt(StringUtils.SkillPoint_Total, data.getInt(StringUtils.SkillPoint_Total) + 1);
                        }
                        Compute.FormatMSGSend(player, Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                                Component.literal("你获得了一点专精点，当前剩余的专精点为: " +
                                        (data.getInt(StringUtils.SkillPoint_Total) - data.getInt(StringUtils.SkillPoint_Used))).withStyle(ChatFormatting.WHITE));


                        ((ServerPlayer) player).setExperiencePoints(0);
                        ((ServerPlayer) player).setExperienceLevels(XpLevel + 1);
                        data.putInt(StringUtils.ExpLevel,XpLevel + 1);
                        data.putDouble("Xp", Xp - needXpForLevelUp);
                        player.experienceProgress = (float) Rate;
                        if (player.experienceLevel % 5 == 0 && player.experienceLevel <= 60) {
                            int num = player.experienceLevel;
                            List<ItemStack> itemStackList = new ArrayList<>(){{
                                add(ModItems.LevelReward5.get().getDefaultInstance());
                                add(ModItems.LevelReward10.get().getDefaultInstance());
                                add(ModItems.LevelReward15.get().getDefaultInstance());
                                add(ModItems.LevelReward20.get().getDefaultInstance());
                                add(ModItems.LevelReward25.get().getDefaultInstance());
                                add(ModItems.LevelReward30.get().getDefaultInstance());
                                add(ModItems.LevelReward35.get().getDefaultInstance());
                                add(ModItems.LevelReward40.get().getDefaultInstance());
                                add(ModItems.LevelReward45.get().getDefaultInstance());
                                add(ModItems.LevelReward50.get().getDefaultInstance());
                                add(ModItems.LevelReward55.get().getDefaultInstance());
                                add(ModItems.LevelReward60.get().getDefaultInstance());
                            }};

                            ItemStack itemStack = itemStackList.get(num / 5 - 1);
                            if (num == 5) Compute.ItemStackGive(player, ModItems.LevelReward5.get().getDefaultInstance());
                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean(num + "Level", true);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal(player.getName().getString() + "通过探索，达到了").withStyle(ChatFormatting.WHITE).
                                            append(Component.literal("" + num).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                            append(Component.literal("级").withStyle(ChatFormatting.WHITE)));
                        }
                        if (player.experienceLevel % 5 == 0 && !data.contains(player.experienceLevel + "Level")) {

                            ModNetworking.sendToClient(new SoundsS2CPacket(3), (ServerPlayer) player);
                            data.putBoolean(player.experienceLevel + "Level", true);
                            ItemStack itemStack = ModItems.GemPiece.get().getDefaultInstance();
                            itemStack.setCount(player.experienceLevel);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("通过提升等级，你获得了").withStyle(ChatFormatting.WHITE).append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player, itemStack);
                            Compute.FormatMSGSend(player, Component.literal("经验").withStyle(ChatFormatting.LIGHT_PURPLE),
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
            ModNetworking.sendToClient(new Attribute0S2CPacket(PlayerAttributes.PlayerAttackDamage(player),
                    PlayerAttributes.PlayerDefencePenetration(player),
                    PlayerAttributes.PlayerCritRate(player),
                    PlayerAttributes.PlayerCritDamage(player)), (ServerPlayer) player);
            ModNetworking.sendToClient(new Attribute1S2CPacket(PlayerAttributes.PlayerManaDamage(player),
                    PlayerAttributes.PlayerManaPenetration(player),
                    PlayerAttributes.PlayerManaRecover(player),
                    PlayerAttributes.PlayerPowerReleaseSpeed(player)), (ServerPlayer) player);
            ModNetworking.sendToClient(new Attribute2S2CPacket(PlayerAttributes.PlayerHealthSteal(player),
                    PlayerAttributes.PlayerDefence(player),
                    PlayerAttributes.PlayerManaDefence(player),
                    PlayerAttributes.PlayerMovementSpeed(player)), (ServerPlayer) player);
            ModNetworking.sendToClient(new Attribute3S2CPacket(PlayerAttributes.PlayerDefencePenetration0(player),
                    PlayerAttributes.PlayerManaPenetration0(player),
                    PlayerAttributes.PlayerAttackRangeUp(player),
                    PlayerAttributes.PlayerExpUp(player)), (ServerPlayer) player);
            int Plain = -1;
            int Forest = -1;
            int Volcano = -1;
            int Mana = -1;
            int Nether = -1;
            int Snow = -1;
            int End = -1;
            int Lake = -1;
            if (data.contains("Green")) Plain = data.getInt("Green");
            if (data.contains(StringUtils.ForestRune.ForestRune))
                Forest = data.getInt(StringUtils.ForestRune.ForestRune);
            if (data.contains("volcanoRune")) Volcano = data.getInt("volcanoRune");
            if (data.contains("ManaRune")) Mana = data.getInt("ManaRune");
            if (data.contains("NetherRune")) Nether = data.getInt("NetherRune");
            if (data.contains("snowRune")) Snow = data.getInt("snowRune");
            if (data.contains(StringUtils.EndRune)) End = data.getInt(StringUtils.EndRune);
            if (data.contains(StringUtils.LakeRune)) Lake = data.getInt(StringUtils.LakeRune);
            ModNetworking.sendToClient(new RuneHud0S2CPacket(Plain, Forest, Volcano, Mana,
                    Nether, Snow, End, Lake), (ServerPlayer) player);
            if (End == 3)
                ModNetworking.sendToClient(
                        new EndRune3TypeS2CPacket(data.getInt(StringUtils.RecallEndRune3)), serverPlayer);

            List<Shield> shieldQueue = Utils.PlayerShieldQueue.get(player);
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
                Compute.PlayerShieldProvider(player, 200, PlayerAttributes.PlayerAttackDamage(player) * 0.1 * Compute.SkySuitEffectRate(player));
            }

            if (TmpNum % 20 == 0) {
                if (data.getInt(StringUtils.MineMonsterEffect) >= TickCount) {
                    if ((player.getMaxHealth() * 0.02f + 10) > player.getHealth()) {
                        Compute.FormatBroad(player.level(),Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA),
                                Component.literal(player.getName().getString()).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal("失血过多而死。").withStyle(ChatFormatting.WHITE)));
                    }
                    player.setHealth(player.getHealth() - (player.getMaxHealth() * 0.02f + 10));
                    ModNetworking.sendToClient(new SoundsS2CPacket(2),(ServerPlayer) player);
                }
            }

            if (TmpNum % 60 == 0) {
                if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SnowBossArmorChest.get())) {
                    Level level = player.level();
                    List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(new Vec3(player.getX(),player.getY(),player.getZ()),6,6,6));
                    mobList.forEach(mob -> {
                        CompoundTag MobData = mob.getPersistentData();
                        MobData.putInt(StringUtils.SnowBossSwordActive.PareTime,TickCount + 40);
                        MobData.putDouble(StringUtils.SnowBossSwordActive.Pare,0.1 + 0.01 * Compute.EntropyRate(data.getInt(StringUtils.Entropy.Snow)));
                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,2,false,false));
                        player.getServer().getPlayerList().getPlayers().forEach(TmpServerPlayer -> {
                            ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(),40),TmpServerPlayer);
                            ModNetworking.sendToClient(new DefencePenetrationParticleS2CPacket(mob.getId(),40),TmpServerPlayer);
                        });
                        if (level.getBlockState(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ())).is(Blocks.AIR)) {
                            level.setBlockAndUpdate(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ()),Blocks.ICE.defaultBlockState());
                            level.destroyBlock(new BlockPos(mob.getBlockX(),mob.getBlockY() + 1,mob.getBlockZ()),false);
                        }
                    });
                    ParticleProvider.VerticleCircleParticle(serverPlayer,1,5,60, ParticleTypes.SNOWFLAKE);
                }
            }
        }
    }
}

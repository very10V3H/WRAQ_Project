package com.Very.very.Events.ClientEvents;

import com.Very.very.Customize.Players.shangmengli.ShangMengLiSword;
import com.Very.very.NetWorking.*;
import com.Very.very.NetWorking.Packets.CodeSceptrePackets.CodeC2SPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.ChargedFullC2SPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.SkillRequestC2SPacket;
import com.Very.very.NetWorking.Packets.SmartPhonePackets.GoldC2SPacket;
import com.Very.very.NetWorking.Packets.SmartPhonePackets.RequestC2SPacket;
import com.Very.very.NetWorking.Packets.SmartPhonePackets.SilverC2SPacket;
import com.Very.very.NetWorking.Packets.TeamPackets.TeamInfoRequestC2SPacket;
import com.Very.very.NetWorking.Packets.WaterBlockCountsC2SPacket;
import com.Very.very.Process.Particle.ParticleProvider;
import com.Very.very.Render.Gui.Skills.SkillGui;
import com.Very.very.Render.Gui.Team.PlayerRequestScreen;
import com.Very.very.Render.Gui.Team.TeamInfoScreen;
import com.Very.very.Render.Gui.Team.TeamManageScreen;
import com.Very.very.Render.Gui.Team.TeamSearchScreen;
import com.Very.very.Render.Gui.VillagerTrade.Trade;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.EarthMana.EarthPower;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Struct.ClientPlayerTeam;
import com.Very.very.ValueAndTools.Utils.Struct.ManaAttackParticle;
import com.Very.very.ValueAndTools.Utils.Struct.Mission;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientTickEvent {
    @SubscribeEvent
    public static void ClientTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {

            if (ClientUtils.clientPlayer == null) ClientUtils.clientPlayer = Minecraft.getInstance().player;
            if (ShangMengLiSword.ShangMengLiAnimationClientTick > 0) ShangMengLiSword.ShangMengLiAnimationClientTick --;

            if (event.player.tickCount % 5 == 0) {
                if (ClientUtils.EarthPowerCompute) {
                    ClientUtils.EarthPowerCompute = false;
                    ClientUtils.EarthPowerType = EarthPower.ElementType(event.player);
                }
                else {
                    ClientUtils.EarthPowerType = -1;
                }
            }
            if (ClientUtils.EarthPowerFlag) {
                ClientUtils.EarthPowerFlag = false;
                ModNetworking.sendToServer(new EarthPowerC2SPacket(EarthPower.ElementType(event.player)));
            } // 地蕴法术

            ClientUtils.effectTimeLasts.forEach(effectTimeLast -> {
                if (!effectTimeLast.NoTime) effectTimeLast.TickCount--;
            });

            ClientUtils.effectTimeLasts.removeIf(effectTimeLast -> effectTimeLast.TickCount < 0);

            ClientUtils.coolDownTimes.forEach(effectTimeLast -> {
                effectTimeLast.TickCount--;
            });
            ClientUtils.coolDownTimes.removeIf(effectTimeLast -> effectTimeLast.TickCount < 0);

            ClientUtils.debuffTimes.forEach(effectTimeLast -> {
                effectTimeLast.TickCount--;
            });
            ClientUtils.debuffTimes.removeIf(effectTimeLast -> effectTimeLast.TickCount < 0);


            if (event.player.tickCount % 20 == 0 && event.player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MineHat.get())) {
                if (event.player.level().getLightEngine().getRawBrightness(event.player.blockPosition(), 0) <= 8) {
                    ModNetworking.sendToServer(new MineHatConfirmC2SPacket());
                }
            }

            if (event.player.tickCount % 20 == 0) {
                if (event.player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.ShipSceptre.get())) {
                    Player player = event.player;
                    Level level = player.level();
                    int X = player.getBlockX();
                    int Y = player.getBlockY();
                    int Z = player.getBlockZ();
                    int WaterBlockCount = 0;
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            for (int k = 0; k < 10; k++) {
                                if (level.getBlockState(new BlockPos(X + i - 5, Y + j - 5, Z + k - 5)).is(Blocks.WATER))
                                    WaterBlockCount++;
                            }
                        }
                    }
                    ModNetworking.sendToServer(new WaterBlockCountsC2SPacket(WaterBlockCount));
                }
            }

            if (event.player.tickCount % 20 == 0) {
                if (Compute.ItemStackCheck(event.player.getInventory(), ModItems.U_Disk.get(), 1)) {

                    if (Compute.ItemStackCheck(event.player.getInventory(), ModItems.SilverCoin.get(), 1))
                        ModNetworking.sendToServer(new SilverC2SPacket(0));
                    if (Compute.ItemStackCheck(event.player.getInventory(), ModItems.GoldCoin.get(), 1))
                        ModNetworking.sendToServer(new GoldC2SPacket(0));
                    if (Compute.ItemStackCheck(event.player.getInventory(), ModItems.WorldSoul1.get(), 64))
                        ModNetworking.sendToServer(new UdiskWorldSoulC2SPacket());

                }
            }

            Vec3 vec3 = event.player.pick(8, 0, false).getLocation();
            Vec3 delta = vec3.subtract(event.player.getEyePosition());
            List<Mob> mobList = event.player.level().getEntitiesOfClass(Mob.class, event.player.getBoundingBox().expandTowards(delta));
            if (mobList.size() == 0) ClientUtils.mobAttribute = null;
            else {
                AtomicReference<Double> Distance = new AtomicReference<>((double) 100);
                AtomicReference<Mob> nearestMob = new AtomicReference<>();
                mobList.forEach(mob -> {
                    if (mob.distanceTo(event.player) < Distance.get()) {
                        Distance.set((double) mob.distanceTo(event.player));
                        nearestMob.set(mob);
                    }
                });
                ClientUtils.mobAttribute = nearestMob.get();
            }

            List<ArmorStand> list = event.player.level().getEntitiesOfClass(ArmorStand.class, AABB.ofSize(event.player.position(), 15, 15, 15));
            list.forEach(armorStand -> {
                armorStand.setBoundingBox(AABB.ofSize(armorStand.position(), 0, 0, 0));
            });

            if (ClientUtils.TradeScreenOpenFlag) {
                Minecraft.getInstance().setScreen(new Trade(true, ClientUtils.TradeScreenType));
                ClientUtils.TradeScreenOpenFlag = !ClientUtils.TradeScreenOpenFlag;
            }

            if (event.player.tickCount % 20 == 0 && Compute.hasBonfireNearBy(event.player))
                ModNetworking.sendToServer(new PlayerIsNearbyCampfireC2SPacket());

            if (ClientUtils.clientTeamScreenFlag) {
                if (ClientUtils.clientPlayerTeamMap.containsKey(Minecraft.getInstance().player.getName().getString())) {

                    ClientPlayerTeam clientPlayerTeam = ClientUtils.clientPlayerTeamMap.get(Minecraft.getInstance().player.getName().getString());
                    if (clientPlayerTeam.getLeaderName().equals(Minecraft.getInstance().player.getName().getString())) {
                        Minecraft.getInstance().setScreen(new TeamManageScreen(true));
                    } else Minecraft.getInstance().setScreen(new TeamInfoScreen(true));

                } else Minecraft.getInstance().setScreen(new TeamSearchScreen(true));

                ClientUtils.clientTeamScreenFlag = false;
            }

            Minecraft mc = Minecraft.getInstance();
            if (ClientUtils.clientScreenSetFlag != -1) {
                switch (ClientUtils.clientScreenSetFlag) {
                    case 0 -> {
                        mc.setScreen((Screen) null);
                        mc.mouseHandler.grabMouse();
                    }
                    case 1 -> mc.setScreen(new TeamInfoScreen(true));
                    case 2 -> mc.setScreen(new PlayerRequestScreen(true));
                    case 3 -> mc.setScreen(new SkillGui(true));

                }
                ClientUtils.clientScreenSetFlag = -1;
            }

            ClientUtils.clientLevel = mc.player.level();
            if (event.player.tickCount % 20 == 0) ModNetworking.sendToServer(new TeamInfoRequestC2SPacket());

            if (ClientUtils.ItemComponentMap.isEmpty()) ClientUtils.ItemComponentMapInit();

            ItemStack itemStack = event.player.getItemBySlot(EquipmentSlot.MAINHAND);

            if (ClientUtils.ItemComponentMap.containsKey(itemStack.getItem())) {
                Compute.ForgingHoverName(itemStack, ClientUtils.ItemComponentMap.get(itemStack.getItem()));
            }

            if (event.player.tickCount % 100 == 0) {
                List<Player> playerList = (List<Player>) event.player.level().players();
                playerList.forEach(Player::refreshDisplayName);
            }

            if (ClientUtils.MissionList.size() > 0 && ClientUtils.NavigateIndex != -1) {
                Mission mission = ClientUtils.MissionList.get(ClientUtils.ListIndex);
                Vec3 Des = mission.getDes();
                Player player = event.player;
                Vec3 Pick = player.pick(0.5, 0, true).getLocation();
                Vec3 Pos = Des.subtract(Pick).normalize();
                for (int i = 0; i < 4; i++) {
                    Vec3 ParticlePos = Pick.add(Pos.scale(0.1 * i));
                    event.player.level().addParticle(ModParticles.KAZE.get(), ParticlePos.x, ParticlePos.y, ParticlePos.z, 0, 0, 0);
                }
                Vec3 ParticlePos = Pick.add(Pos.scale(0.1 * 4));
                event.player.level().addParticle(ModParticles.NETHER.get(), ParticlePos.x, ParticlePos.y, ParticlePos.z, 0, 0, 0);
            }
            if (ClientUtils.Mission && event.player.tickCount % 100 == 0 && ClientUtils.NavigateIndex == -1) {
                ClientUtils.MissionChange = true;
            }
            if (ClientUtils.Mission && ClientUtils.MissionIndex > 0) ClientUtils.MissionIndex -= 5;
            if (!ClientUtils.Mission && ClientUtils.MissionIndex < 100) ClientUtils.MissionIndex += 5;
            if (ClientUtils.RollingFlag) {
                Player player = Minecraft.getInstance().player;
                Vec3 CurrentDeltaMovement = player.getDeltaMovement();
                Vec3 Pick = player.pick(2, 0, true).getLocation();
                Vec3 MoveVec = Pick.subtract(player.getEyePosition());
                MoveVec = new Vec3(MoveVec.x, 0, MoveVec.z);
                MoveVec = MoveVec.normalize();
                MoveVec.scale(10 * (1 + ClientUtils.RollingRate));
                player.setDeltaMovement(CurrentDeltaMovement.add(MoveVec));
                ClientUtils.RollingTick = player.tickCount;
                ClientUtils.RollingFlag = false;
            }
        }
    }

    @SubscribeEvent
    public static void TestForContainer(ScreenEvent event) {
/*        if(event.getScreen() instanceof FirstBlockScreen && ClientUtils.TickCount % 20 == 0) {
            ModNetworking.sendToServer(new LimitC2SPacket());
        }
        if(event.getScreen() instanceof BrewingScreen && ClientUtils.TickCount % 20 == 0){
            ModNetworking.sendToServer(new LimitC2SPacket());
        }*/
    }

    @SubscribeEvent
    public static void PacketsLimited(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {
            for (int i = 1; i <= 15; i++) {
                if (ClientUtils.Sword_Image[i] != null && ClientUtils.Sword_Image[i].getTickTime() > 0)
                    ClientUtils.Sword_Image[i].setTickTime(ClientUtils.Sword_Image[i].getTickTime() - 1);
                if (ClientUtils.Bow_Image[i] != null && ClientUtils.Bow_Image[i].getTickTime() > 0)
                    ClientUtils.Bow_Image[i].setTickTime(ClientUtils.Bow_Image[i].getTickTime() - 1);
                if (ClientUtils.Mana_Image[i] != null && ClientUtils.Mana_Image[i].getTickTime() > 0)
                    ClientUtils.Mana_Image[i].setTickTime(ClientUtils.Mana_Image[i].getTickTime() - 1);
                if (ClientUtils.Demon_Image[i] != null && ClientUtils.Demon_Image[i].getTickTime() > 0)
                    ClientUtils.Demon_Image[i].setTickTime(ClientUtils.Demon_Image[i].getTickTime() - 1);
                if (ClientUtils.Rune_Image[i] != null && ClientUtils.Rune_Image[i].getTickTime() > 0)
                    ClientUtils.Rune_Image[i].setTickTime(ClientUtils.Rune_Image[i].getTickTime() - 1);
            }
        }
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {
            ClientUtils.TickCount++;
            Player player = event.player;
            Item MainHand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            float Speed = player.getSpeed();
            if (ClientUtils.ChargedCountsSwordSkill12 < 100 && player.getDeltaMovement().length() > 0.1 && ClientUtils.SwordSkillPoint.Point[13] > 0)
                ClientUtils.ChargedCountsSwordSkill12 += Speed;
            if (ClientUtils.ChargedCountsBowSkill12 < 100 && player.getDeltaMovement().length() > 0.1 && ClientUtils.BowSkillPoint.Point[13] > 0)
                ClientUtils.ChargedCountsBowSkill12 += Speed;
            if (ClientUtils.ChargedCountsManaSkill12 < 100 && player.getDeltaMovement().length() > 0.1 && ClientUtils.ManaSkillPoint.Point[13] > 0)
                ClientUtils.ChargedCountsManaSkill12 += Speed;
            if (ClientUtils.ChargedCountsManaSkill13 < 100 && player.getDeltaMovement().length() > 0.1 && ClientUtils.ManaSkillPoint.Point[14] > 0)
                ClientUtils.ChargedCountsManaSkill13 += Speed;
            if (ClientUtils.ChargedCountsSakuraDemonSword < 100 && player.getDeltaMovement().length() > 0.1
                    && MainHand.equals(ModItems.SakuraDemonSword.get()) && ClientUtils.Demon_Image[1] != null && ClientUtils.Demon_Image[1].getTickTime() == 0)
                ClientUtils.ChargedCountsSakuraDemonSword += Speed;
/*            if (ClientUtils.ChargedCountsZeusSword < 100 && player.getDeltaMovement().length() > 0.1
                    && MainHand.equals(ModItems.ZeusSword.get()) && ClientUtils.Demon_Image[2] != null && ClientUtils.Demon_Image[2].getTickTime() == 0)
                ClientUtils.ChargedCountsZeusSword += Speed;*/

            if (ClientUtils.ChargedCountsSwordSkill12 >= 100 && ClientUtils.ChargedFlagSwordSkill12) {
                ModNetworking.sendToServer(new ChargedFullC2SPacket(0));
                ClientUtils.ChargedFlagSwordSkill12 = false;
            }
            if (ClientUtils.ChargedCountsBowSkill12 >= 100 && ClientUtils.ChargedFlagBowSkill12) {
                ModNetworking.sendToServer(new ChargedFullC2SPacket(1));
                ClientUtils.ChargedFlagBowSkill12 = false;
            }
            if (ClientUtils.ChargedCountsManaSkill12 >= 100 && ClientUtils.ChargedFlagManaSkill12) {
                ModNetworking.sendToServer(new ChargedFullC2SPacket(2));
                ClientUtils.ChargedFlagManaSkill12 = false;
            }
            if (ClientUtils.ChargedCountsManaSkill13 >= 100 && ClientUtils.ChargedFlagManaSkill13) {
                ModNetworking.sendToServer(new ChargedFullC2SPacket(3));
                ClientUtils.ChargedFlagManaSkill13 = false;
            }
            if (ClientUtils.ChargedCountsSakuraDemonSword >= 100 && ClientUtils.ChargedFlagSakuraDemonSword) {
                ModNetworking.sendToServer(new ChargedFullC2SPacket(4));
                ClientUtils.ChargedFlagSakuraDemonSword = false;
            }
            if (ClientUtils.ChargedCountsZeusSword >= 100 && ClientUtils.ChargedFlagZeusSword) {
                ModNetworking.sendToServer(new ChargedFullC2SPacket(5));
                ClientUtils.ChargedFlagZeusSword = false;
            }
            if (ClientUtils.TickCount % 20 == 0) {
                ClientUtils.ChargedFlagSwordSkill12 = true;
                ClientUtils.ChargedFlagBowSkill12 = true;
                ClientUtils.ChargedFlagManaSkill12 = true;
                ClientUtils.ChargedFlagManaSkill13 = true;
                ClientUtils.ChargedFlagSakuraDemonSword = true;
                ClientUtils.ChargedFlagZeusSword = true;
            }

/*            if (player.tickCount % 20 == 0) {
                ClientUtils.PacketsLimit = 10;
            }*/
            Vec2 vec2 = new Vec2((float) player.getX(), (float) player.getZ());
            if (Compute.DotIn(vec2, Utils.KazeDot1, Utils.KazeDot2, Utils.KazeDot3, Utils.KazeDot4)) {
                player.setDeltaMovement(player.getDeltaMovement().add(0.01, 0, -0.01));
                if (player.tickCount % 200 == 0 && event.phase.equals(TickEvent.Phase.START)) {
                    Compute.FormatMSGSend(player, Component.literal("风之谷").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfKaze),
                            Component.literal("来自西南的风不断地吹打着你。"));
                }
            }
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.CodeSceptre.get())) {
                Inventory inventory = player.getInventory();
                ClientUtils.PowerInSlot.put(0, 0);
                ClientUtils.PowerInSlot.put(1, ClientUtils.ItemToNum.getOrDefault(inventory.getItem(5).getItem(), 0));

                ClientUtils.PowerInSlot.put(2, ClientUtils.ItemToNum.getOrDefault(inventory.getItem(6).getItem(), 0));
                ClientUtils.PowerInSlot.put(3, ClientUtils.ItemToNum.getOrDefault(inventory.getItem(7).getItem(), 0));
                ClientUtils.PowerInSlot.put(4, ClientUtils.ItemToNum.getOrDefault(inventory.getItem(8).getItem(), 0));
                ClientUtils.IsHandlingPower = true;
                int power1 = 0, power2 = 0, power3 = 0, power4 = 0;
                Iterator<Integer> iterator = ClientUtils.PowerQueue.iterator();
                if (iterator.hasNext()) power1 = iterator.next();
                if (iterator.hasNext()) power2 = iterator.next();
                if (iterator.hasNext()) power3 = iterator.next();
                if (iterator.hasNext()) power4 = iterator.next();
                ModNetworking.sendToServer(new CodeC2SPacket(ClientUtils.PowerInSlot.get(power1), ClientUtils.PowerInSlot.get(power2),
                        ClientUtils.PowerInSlot.get(power3), ClientUtils.PowerInSlot.get(power4)));
            } else ClientUtils.IsHandlingPower = false;
            if (ClientUtils.SkillCacheFlag) {
                ModNetworking.sendToServer(new SkillRequestC2SPacket());
                ClientUtils.SkillCacheFlag = !ClientUtils.SkillCacheFlag;
            }
        }
    }

    @SubscribeEvent
    public static void PacketsSendTo(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.player.equals(Minecraft.getInstance().player)) {
            if (event.player.tickCount % 20 == 0 && event.phase.equals(TickEvent.Phase.START)) {
                ModNetworking.sendToServer(new RequestC2SPacket());
            }
        }
    }

    @SubscribeEvent
    public static void ClientParticleEvent(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.player.equals(Minecraft.getInstance().player)) {
            Player player = event.player;
            Level level = player.level();
            for (ManaAttackParticle manaAttackParticle : ClientUtils.manaAttackParticleArrayList) {
                if (manaAttackParticle.getType() == 2)
                    Compute.BallParticle(manaAttackParticle.getPos(), level, 2 * (20 - manaAttackParticle.getTickTime()) / 20.0, ClientUtils.particleOptionsHashMap.get(manaAttackParticle.getType()), 10);
                else
                    Compute.BallParticle(manaAttackParticle.getPos(), level, 2 * (20 - manaAttackParticle.getTickTime()) / 20.0, ClientUtils.particleOptionsHashMap.get(manaAttackParticle.getType()), 20);
                manaAttackParticle.setTickTime(Math.max(-1, manaAttackParticle.getTickTime() - 1));
            }

            ClientUtils.manaAttackParticleArrayList.removeIf(manaAttackParticle -> manaAttackParticle.getTickTime() == -1);
        }

        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {
            Player player = event.player;
            Level level = player.level();
            int TickCount = player.tickCount;
            List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(), 100, 100, 100));
            for (Mob mob : list) {
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorMoonAttack.get())) {
                    ParticleDouble(mob, ModParticles.LIGHTNINGISLAND.get());
                }

                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorMoonMana.get())) {
                    ParticleDouble(mob, ModParticles.ENTROPY.get());
                }

                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorEnderMan.get())) {
                    ParticleDouble(mob, ModParticles.END_PARTICLE.get());
                }

                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorIceHelmet.get())) {
                    ParticleDouble(mob, ModParticles.SNOW.get());
                }

                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorSpringHelmet.get())) {
                    ParticleDouble(mob, ModParticles.SPRING.get());
                }

                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorDevilHelmet.get())) {
                    ParticleDouble(mob, ModParticles.ENTROPY.get());
                }

                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorSilverFish.get())) {
                    ParticleProvider.EntityEffectVerticleCircleParticle(mob, 0, 2, 40, ModParticles.END_PARTICLE.get(), 10, false);
                }

                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorKazeRecall.get())) {
                    ParticleFour(mob, ModParticles.KAZE.get(), ModParticles.KAZE.get());
                    List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(), 20, 20, 20));
                    for (Player player1 : playerList) {
                        if (player1.position().distanceTo(mob.position()) <= 2.5) {
                            player1.setDeltaMovement(player1.position().subtract(mob.position()));
                        }
                    }
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorSpiderRecall.get())) {
                    ParticleFour(mob, ModParticles.SPIDER.get(), ModParticles.SPIDER.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorHuskRecall.get())) {
                    if (ClientUtils.BlackForestParticle) {
                        ClientUtils.BlackForestParticle = false;
                        SkillParticle(level, mob, ModParticles.BLACKFOREST_RECALL.get());
                    }
                    ParticleFour(mob, ModParticles.BLACKFOREST.get(), ModParticles.BLACKFOREST.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorLightningRecall.get())) {
                    ParticleFour(mob, ModParticles.LIGHTNINGISLAND.get(), ModParticles.LIGHTNINGISLAND.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorNetherRecall.get())) {
                    if (ClientUtils.NetherParticle) {
                        ClientUtils.NetherParticle = false;
                        SkillParticle(level, mob, ModParticles.NETHER.get());
                    }
                    ParticleFour(mob, ModParticles.NETHER.get(), ModParticles.NETHER.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorSnowRecall.get())) {
                    ParticleFour(mob, ModParticles.SNOW.get(), ModParticles.SNOW.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorForestBoss.get())) {
                    ParticleFour(mob, ModParticles.ENTROPY.get(), ModParticles.FOREST.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorVolcanoBoss.get())) {
                    ParticleFour(mob, ModParticles.ENTROPY.get(), ModParticles.VOLCANO.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorLakeBoss.get())) {
                    ParticleFour(mob, ModParticles.ENTROPY.get(), ModParticles.LAKE.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorSkyBoss.get())) {
                    ParticleFour(mob, ModParticles.ENTROPY.get(), ModParticles.SKY.get());
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorSnowBoss.get())) {
                    ParticleFour(mob, ModParticles.ENTROPY.get(), ModParticles.SNOW.get());
                }
            }

            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(player.getPosition(1), 100, 100, 100));
            Random random = new Random();
            for (Player player1 : playerList) {
                if (TickCount % 20 == 0 && player1.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SakuraArmorHelmet.get())) {
                    level.addParticle(ParticleTypes.CHERRY_LEAVES, player1.getX() + random.nextDouble(-0.5, 0.5), player1.getY() + 1.7, player1.getZ() + random.nextDouble(-0.5, 0.5), 0, 0, 0);
                }
            }
            ClientUtils.DefencePenetrationParticle.keySet().forEach(integer -> {
                Entity entity = level.getEntity(integer);
                if (entity != null) {
                    double r = 0.5;
                    double Dx = r * Math.cos(2 * Math.PI * (TickCount % 20) / 20);
                    double Dz = r * Math.sin(2 * Math.PI * (TickCount % 20) / 20);
                    level.addParticle(ModParticles.BREAK_Defence.get(), entity.getX() + Dx, entity.getY() + 1.5, entity.getZ() + Dz, 0, 0, 0);
                    if (ClientUtils.DefencePenetrationParticle.get(integer) > 0)
                        ClientUtils.DefencePenetrationParticle.put(integer, ClientUtils.DefencePenetrationParticle.get(integer) - 1);

                }
            });
            ClientUtils.DefencePenetrationParticle.keySet().removeIf(integer -> ClientUtils.DefencePenetrationParticle.get(integer) == 0);

            ClientUtils.ManaDefencePenetrationParticle.keySet().forEach(integer -> {
                Entity entity = level.getEntity(integer);
                if (entity != null) {
                    double r = 0.5;
                    double Dx = r * Math.cos(2 * Math.PI * ((TickCount + 10) % 20) / 20);
                    double Dz = r * Math.sin(2 * Math.PI * ((TickCount + 10) % 20) / 20);
                    level.addParticle(ModParticles.MANA_BREAK_Defence.get(), entity.getX() + Dx, entity.getY() + 1.5, entity.getZ() + Dz, 0, 0, 0);
                    if (ClientUtils.ManaDefencePenetrationParticle.get(integer) > 0)
                        ClientUtils.ManaDefencePenetrationParticle.put(integer, ClientUtils.ManaDefencePenetrationParticle.get(integer) - 1);
                }
            });
            ClientUtils.ManaDefencePenetrationParticle.keySet().removeIf(integer -> ClientUtils.ManaDefencePenetrationParticle.get(integer) == 0);

            ClientUtils.SlowDownParticle.keySet().forEach(integer -> {
                Entity entity = level.getEntity(integer);
                if (entity != null) {
                    for (int i = 0; i < 4; i++) {
                        double r = 0.5;
                        double Dx = r * Math.cos(2 * Math.PI * ((TickCount + i * 5) % 20) / 20);
                        double Dz = r * Math.sin(2 * Math.PI * ((TickCount + i * 5) % 20) / 20);
                        level.addParticle(ModParticles.SLOW_DOWN.get(), entity.getX() + Dx, entity.getY() + 0.5, entity.getZ() + Dz, 0, 0, 0);
                    }
                    if (ClientUtils.SlowDownParticle.get(integer) > 0)
                        ClientUtils.SlowDownParticle.put(integer, ClientUtils.SlowDownParticle.get(integer) - 1);
                }
            });
            ClientUtils.SlowDownParticle.keySet().removeIf(integer -> ClientUtils.SlowDownParticle.get(integer) == 0);

            ClientUtils.DamageDecreaseParticle.keySet().forEach(integer -> {
                Entity entity = level.getEntity(integer);
                if (entity != null) {
                    double r = 0.5;
                    double Dx = r * Math.cos(2 * Math.PI * ((TickCount + 5) % 20) / 20);
                    double Dz = r * Math.sin(2 * Math.PI * ((TickCount + 5) % 20) / 20);
                    level.addParticle(ModParticles.DAMAGE_DECREASE.get(), entity.getX() + Dx, entity.getY() + 1.5, entity.getZ() + Dz, 0, 0, 0);
                    if (ClientUtils.DamageDecreaseParticle.get(integer) > 0)
                        ClientUtils.DamageDecreaseParticle.put(integer, ClientUtils.DamageDecreaseParticle.get(integer) - 1);
                }
            });
            ClientUtils.DamageDecreaseParticle.keySet().removeIf(integer -> ClientUtils.DamageDecreaseParticle.get(integer) == 0);

        }
    }

    public static void ParticleDouble(Mob mob, ParticleOptions particleOptions) {
        Vec3 Pos = mob.position();
        int num = 20;
        int i = mob.tickCount % 20;
        double r = 1;
        double angle = (2 * Math.PI / num) * (i);
        double X = Pos.x + Math.cos(angle) * r;
        double Z = Pos.z + Math.sin(angle) * r;
        double Y = Pos.y + 1;
        Vec3 Point = new Vec3(X, Y, Z);
        Vec3 Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 2, Pos.z), 0, 2, 40, particleOptions, false);
        i = 20 - i;
        angle = (2 * Math.PI / num) * (i);
        X = Pos.x + Math.cos(angle) * r;
        Z = Pos.z + Math.sin(angle) * r;
        Y = Pos.y + 1;
        Point = new Vec3(X, Y, Z);
        Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 2, Pos.z), 0, 2, 40, particleOptions, false);
    }

    public static void ParticleFour(Mob mob, ParticleOptions particleOptions0, ParticleOptions particleOptions1) {
        Vec3 Pos = mob.position();
        int num = 20;
        int i = mob.tickCount % 20;
        double r = 1;
        double angle = (2 * Math.PI / num) * (i);
        double X = Pos.x + Math.cos(angle) * r;
        double Z = Pos.z + Math.sin(angle) * r;
        double Y = Pos.y + 1;
        Vec3 Point = new Vec3(X, Y, Z);
        Vec3 Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position,
                new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, particleOptions0, false);
        i += 5;
        angle = (2 * Math.PI / num) * (i);
        X = Pos.x + Math.cos(angle) * r;
        Z = Pos.z + Math.sin(angle) * r;
        Y = Pos.y + 1;
        Point = new Vec3(X, Y, Z);
        Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position,
                new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, particleOptions1, false);
        i += 5;
        angle = (2 * Math.PI / num) * (i);
        X = Pos.x + Math.cos(angle) * r;
        Z = Pos.z + Math.sin(angle) * r;
        Y = Pos.y + 1;
        Point = new Vec3(X, Y, Z);
        Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position,
                new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, particleOptions0, false);
        i += 5;
        angle = (2 * Math.PI / num) * (i);
        X = Pos.x + Math.cos(angle) * r;
        Z = Pos.z + Math.sin(angle) * r;
        Y = Pos.y + 1;
        Point = new Vec3(X, Y, Z);
        Position = Point.subtract(Pos);
        ParticleProvider.EntityFaceCircleCreate(mob, Position,
                new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, particleOptions1, false);
    }

    public static void SkillParticle(Level level, Mob mob, ParticleOptions particleOptions) {
        int num = 160;
        for (int i = 0; i < num; i++) {
            double angle = (2 * Math.PI / num) * (i);
            double X = Math.cos(angle) * 0.5;
            double Y = 0;
            double Z = Math.sin(angle) * 0.5;
            level.addParticle(particleOptions, mob.getX(), mob.getY() + 1.5, mob.getZ(),
                    X, Y, Z);
        }
        for (int i = 0; i < num; i++) {
            double angle = (2 * Math.PI / num) * (i);
            double X = Math.cos(angle) * 0.25;
            double Y = 0;
            double Z = Math.sin(angle) * 0.25;
            level.addParticle(particleOptions, mob.getX(), mob.getY() + 2, mob.getZ(),
                    X, Y, Z);
        }
        for (int i = 0; i < num; i++) {
            double angle = (2 * Math.PI / num) * (i);
            double X = Math.cos(angle) * 0.25;
            double Y = 0;
            double Z = Math.sin(angle) * 0.25;
            level.addParticle(particleOptions, mob.getX(), mob.getY() + 1, mob.getZ(),
                    X, Y, Z);
        }
    }
}

package com.very.wraq.events.client;

import com.very.wraq.Items.MainStory_1.BackSpawn;
import com.very.wraq.Items.MainStory_1.Mission.Main0;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.struct.ClientPlayerTeam;
import com.very.wraq.common.util.struct.ManaAttackParticle;
import com.very.wraq.common.util.struct.OldMission;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.AttributePackets.MobAttributeC2SPacket;
import com.very.wraq.networking.misc.CodeSceptrePackets.CodeC2SPacket;
import com.very.wraq.networking.misc.EarthPower.EarthPowerC2SPacket;
import com.very.wraq.networking.misc.SkillPackets.Charging.ChargedFullC2SPacket;
import com.very.wraq.networking.misc.SkillPackets.SkillRequestC2SPacket;
import com.very.wraq.networking.misc.SmartPhonePackets.Currency.AllCurrencyC2SPacket;
import com.very.wraq.networking.misc.WaterBlockCountsC2SPacket;
import com.very.wraq.networking.unSorted.MineHatConfirmC2SPacket;
import com.very.wraq.networking.unSorted.PlayerIsNearbyCampfireC2SPacket;
import com.very.wraq.networking.unSorted.UdiskWorldSoulC2SPacket;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.endlessinstance.DailyEndlessInstance;
import com.very.wraq.process.system.forge.ForgeScreen;
import com.very.wraq.process.system.missions.MissionScreen;
import com.very.wraq.process.system.respawn.MyRespawnRule;
import com.very.wraq.process.system.wayPoints.MyWayPoint;
import com.very.wraq.projectiles.OnCuriosSlotTickEffect;
import com.very.wraq.render.gui.market.MarketScreen;
import com.very.wraq.render.gui.skills.IdCardGui;
import com.very.wraq.render.gui.team.PlayerRequestScreen;
import com.very.wraq.render.gui.team.TeamInfoScreen;
import com.very.wraq.render.gui.team.TeamManageScreen;
import com.very.wraq.render.gui.team.TeamSearchScreen;
import com.very.wraq.render.gui.villagerTrade.TradeScreen;
import com.very.wraq.render.hud.networking.AttributeDataC2SPacket;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.series.overworld.sakuraSeries.EarthMana.EarthPower;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class ClientPlayerTickEvent {
    @SubscribeEvent
    public static void ClientTick(TickEvent.PlayerTickEvent event) {
        if (!event.player.equals(Minecraft.getInstance().player)) return;
        MyWayPoint.clientTick(event);
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START)) {
            OnCuriosSlotTickEffect.tickEvent(event.player);
            Main0.clientTick(event.player);
        }
        if (event.side.isClient() && event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();

            ClientUtils.clientMobEffectMap.entrySet().removeIf(e -> {
                return !e.getKey().isAlive();
            });
            // tick重置后需要对一些使用到的数据结构进行清理
            if (mc.player.tickCount < ClientUtils.clientPlayerTick) {
                ClientUtils.clientMobEffectMap.clear();
            }
            ClientUtils.clientPlayerTick = mc.player.tickCount;

            if (DailyEndlessInstance.clientLastTick > 0) DailyEndlessInstance.clientLastTick --;

            if (ClientUtils.receiveMarketInfo) {
                ClientUtils.receiveMarketInfo = false;
                if (MarketScreen.openFlag) {
                    MarketScreen.openFlag = false;
                    mc.setScreen(new MarketScreen(true, MarketScreen.tempPage, MarketScreen.tempSortByPrice));
                }
            }

            if (ClientUtils.ipFlag == 1) {
                ClientUtils.ipFlag = 0;

                String serverAddress = "wraq.fun:25565";

                if (mc.level != null) {
                    mc.level.disconnect();
                }

                if (mc.isLocalServer()) {
                    mc.clearLevel(new GenericDirtMessageScreen(Component.translatable("menu.savingLevel")));
                } else {
                    mc.clearLevel();
                }

                mc.setScreen(new JoinMultiplayerScreen(new TitleScreen()));
                ConnectScreen.startConnecting(mc.screen, mc, ServerAddress.parseString(serverAddress),
                        new ServerData(serverAddress, serverAddress, false), false);

                return;

            }

        }
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {

            if (ClientUtils.missionScreenFlag != -1) {
                Minecraft.getInstance().setScreen(new MissionScreen(ClientUtils.missionScreenFlag, 0));
                ClientUtils.missionScreenFlag = -1;
            }
            if (event.player.tickCount % 200 == 0) {
                Inventory inventory = event.player.getInventory();
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    ItemStack itemStack = inventory.getItem(i);
                    Item item = itemStack.getItem();
                    if (!(Utils.mainHandTag.containsKey(item) || Utils.armorTag.containsKey(item)
                            || Utils.curiosList.contains(item) || Utils.passiveEquipTag.containsKey(item))) {
                        itemStack.resetHoverName();
                    }
                    if (itemStack.is(ModItems.BackSpawn.get())) {
                        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        int index = data.getInt(BackSpawn.spawnPointIndex);
                        itemStack.setHoverName(Component.literal("回城卷轴 - ").withStyle(ChatFormatting.BLUE).
                                append(MyRespawnRule.overworldSpawnPos.get(index).zoneName()));
                    }
                }
            }

            if (Utils.mainHandTag.containsKey(event.player.getMainHandItem().getItem()))
                Compute.forgingHoverName(event.player.getMainHandItem());


            if (event.player.position().distanceTo(new Vec3(1138.5, 67.5, 384.5)) < 2)
                event.player.addDeltaMovement(new Vec3(0, 11, 0));

            Element.ClientTick(event.player.level());

            if (ClientUtils.clientPlayer == null) ClientUtils.clientPlayer = Minecraft.getInstance().player;

            if (event.player.tickCount % 5 == 0) {
                if (ClientUtils.EarthPowerCompute) {
                    ClientUtils.EarthPowerCompute = false;
                    ClientUtils.EarthPowerType = EarthPower.ElementType(event.player);
                } else {
                    ClientUtils.EarthPowerType = -1;
                }
            }
            if (ClientUtils.EarthPowerFlag) {
                ClientUtils.EarthPowerFlag = false;
                ModNetworking.sendToServer(new EarthPowerC2SPacket(EarthPower.ElementType(event.player)));
            } // 地蕴法术

            ClientUtils.effectTimeLasts.forEach(effectTimeLast -> {
                if (!effectTimeLast.forever) effectTimeLast.lastTick--;
            });

            ClientUtils.effectTimeLasts.removeIf(effectTimeLast -> effectTimeLast.lastTick < 0);

            if (ClientUtils.elementEffects != null) {
                if (!ClientUtils.elementEffects.forever) ClientUtils.elementEffects.lastTick--;
                if (ClientUtils.elementEffects.lastTick < 0) ClientUtils.elementEffects = null;
            }

            ClientUtils.coolDownTimes.forEach(effectTimeLast -> {
                effectTimeLast.lastTick--;
            });
            ClientUtils.coolDownTimes.removeIf(effectTimeLast -> effectTimeLast.lastTick < 0);

            ClientUtils.debuffTimes.forEach(effectTimeLast -> {
                effectTimeLast.lastTick--;
            });
            ClientUtils.debuffTimes.removeIf(effectTimeLast -> effectTimeLast.lastTick < 0);


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
                Inventory inventory = event.player.getInventory();
                if (!(Minecraft.getInstance().screen instanceof TradeScreen) && Compute.ItemStackCheck(inventory, ModItems.U_Disk.get(), 1)) {
                    if (Compute.ItemStackCheck(inventory, ModItems.copperCoin.get(), 1)
                            || Compute.ItemStackCheck(inventory, ModItems.goldCoin.get(), 1)
                            || Compute.ItemStackCheck(inventory, ModItems.silverCoin.get(), 1)) {
                        ModNetworking.sendToServer(new AllCurrencyC2SPacket(false));
                    }
                    if (Compute.ItemStackCheck(inventory, ModItems.WorldSoul1.get(), 64))
                        ModNetworking.sendToServer(new UdiskWorldSoulC2SPacket());
                }
            }

            if (event.player.tickCount % 8 == 0) {
                ClientUtils.mobAttribute = (Mob) Compute.detectPlayerPickEntity(event.player, 16, 0.5, Mob.class);
                if (ClientUtils.mobAttribute != null) {
                    ModNetworking.sendToServer(new MobAttributeC2SPacket(ClientUtils.mobAttribute.getId()));
                }

                ClientUtils.playerAttribute = (Player) Compute.detectPlayerPickEntity(event.player, 8, 0.5, Player.class);
                if (ClientUtils.playerAttribute != null) {
                    ModNetworking.sendToServer(new AttributeDataC2SPacket(ClientUtils.playerAttribute.getId()));
                }
            }

            if (ClientUtils.TradeScreenOpenFlag) {
                Minecraft.getInstance().setScreen(new TradeScreen(true, ClientUtils.TradeScreenType));
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
                    case 3 -> mc.setScreen(new IdCardGui(true));
                    case 4 -> mc.setScreen(new ForgeScreen());

                }
                ClientUtils.clientScreenSetFlag = -1;
            }

            ClientUtils.clientLevel = mc.player.level();

            if (event.player.tickCount % 100 == 0) {
                List<Player> playerList = (List<Player>) event.player.level().players();
                playerList.forEach(Player::refreshDisplayName);
            }

            if (ClientUtils.oldMissionList.size() > 0 && ClientUtils.NavigateIndex != -1) {
                OldMission oldMission = ClientUtils.oldMissionList.get(ClientUtils.ListIndex);
                Vec3 Des = oldMission.getDes();
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
                MoveVec = MoveVec.scale(1 + ClientUtils.RollingRate);
                MoveVec = MoveVec.add(0, 0.35, 0);
                player.setDeltaMovement(CurrentDeltaMovement.add(MoveVec));
                ClientUtils.RollingTick = player.tickCount;
                ClientUtils.RollingFlag = false;
            }
        }
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
            if (ClientUtils.ChargedCountsSakuraDemonSword < 100 && MainHand.equals(ModItems.SakuraDemonSword.get())
                    && ClientUtils.Demon_Image[1] != null && ClientUtils.Demon_Image[1].getTickTime() == 0) {
                if (player.getDeltaMovement().length() > 0.1) ClientUtils.ChargedCountsSakuraDemonSword += Speed;
                ClientUtils.ChargedCountsSakuraDemonSword += 0.5;
            }

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
    public static void ClientParticleEvent(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.player.equals(Minecraft.getInstance().player)) {
            Player player = event.player;
            Level level = player.level();

            for (ManaAttackParticle manaAttackParticle : ClientUtils.manaAttackParticleArrayList) {
/*                if (manaAttackParticle.getType() == 2)
                    Compute.BallParticle(manaAttackParticle.getPos(), level, 2 * (20 - manaAttackParticle.getTickTime()) / 20.0, ClientUtils.manaAttackParticleTypeMap.get(manaAttackParticle.getType()), 10);
                else*/
                Compute.BallParticle(manaAttackParticle.getPos(), level, 2 * (20 - manaAttackParticle.getTickTime()) / 20.0, Utils.ParticleStringToParticleMap.get(manaAttackParticle.getType()), 20);
                manaAttackParticle.setTickTime(Math.max(-1, manaAttackParticle.getTickTime() - 1));
            }

            ClientUtils.manaAttackParticleArrayList.removeIf(manaAttackParticle -> manaAttackParticle.getTickTime() == -1);
        }
    }
}

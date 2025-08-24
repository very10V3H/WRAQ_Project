package fun.wraq.events.client;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.equip.WraqMainHandEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.ClientPlayerTeam;
import fun.wraq.common.util.struct.ManaAttackParticle;
import fun.wraq.common.util.struct.OldMission;
import fun.wraq.items.m.BackSpawn;
import fun.wraq.items.m.Main0;
import fun.wraq.items.money.UDisk;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.AttributePackets.MobAttributeC2SPacket;
import fun.wraq.networking.misc.EarthPower.EarthPowerC2SPacket;
import fun.wraq.networking.misc.SkillPackets.Charging.ChargedFullC2SPacket;
import fun.wraq.networking.misc.SkillPackets.SkillRequestC2SPacket;
import fun.wraq.networking.misc.SmartPhonePackets.Currency.AllCurrencyC2SPacket;
import fun.wraq.networking.misc.WaterBlockCountsC2SPacket;
import fun.wraq.networking.unSorted.MineHatConfirmC2SPacket;
import fun.wraq.networking.unSorted.PlayerIsNearbyCampfireC2SPacket;
import fun.wraq.networking.unSorted.UdiskWorldSoulC2SPacket;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.system.bgm.WraqBgm;
import fun.wraq.process.system.cooking.item.FoodCoinStore;
import fun.wraq.process.system.element.networking.ElementPieceC2SPacket;
import fun.wraq.process.system.element.piece.ElementPieceGui;
import fun.wraq.process.system.element.piece.ElementPieceRecipe;
import fun.wraq.process.system.endlessinstance.DailyEndlessInstance;
import fun.wraq.process.system.endlessinstance.EndlessCoreScreen;
import fun.wraq.process.system.forge.ForgeScreen;
import fun.wraq.process.system.missions.MissionScreen;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.smelt.SmeltRecipeScreen;
import fun.wraq.process.system.wayPoints.MyWayPoint;
import fun.wraq.render.gui.market.MarketScreen;
import fun.wraq.render.gui.skills.IdCardGui;
import fun.wraq.render.gui.team.PlayerRequestScreen;
import fun.wraq.render.gui.team.TeamInfoScreen;
import fun.wraq.render.gui.team.TeamManageScreen;
import fun.wraq.render.gui.team.TeamSearchScreen;
import fun.wraq.render.gui.villagerTrade.TradeListNew;
import fun.wraq.render.gui.villagerTrade.TradeScreen;
import fun.wraq.render.hud.main.ItemAndExpGetHud;
import fun.wraq.render.hud.networking.AttributeDataC2SPacket;
import fun.wraq.render.particles.ModParticles;
import fun.wraq.series.crystal.CrystalScreen;
import fun.wraq.series.events.dragonboat.DragonBoatStore;
import fun.wraq.series.events.summer2025.Summer2025Store;
import fun.wraq.series.overworld.cold.sc4.BlizzardBoots;
import fun.wraq.series.overworld.sakura.EarthMana.EarthPower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.core.BlockPos;
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

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class ClientPlayerTickEvent {
    @SubscribeEvent
    public static void ClientTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (!player.equals(Minecraft.getInstance().player)) return;
        MyWayPoint.clientTick(event);
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START)) {
            windLand(player);
            Main0.clientTick(player);
            Compute.setDownDeltaInLowGravityEnvironment(player);
            ItemAndExpGetHud.clientTick();
            if (SpecialEffectOnPlayer.clientSilentTick > 0) --SpecialEffectOnPlayer.clientSilentTick;
            if (SpecialEffectOnPlayer.clientBlindTick > 0) --SpecialEffectOnPlayer.clientBlindTick;
            ParticleProvider.spaceEffectParticleHandleClientTick();
            SkillV2.clientTick();
            WraqBgm.handleClientPlayerTick(player);
            BlizzardBoots.handleTick(player);
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
                mc.setScreen(new MarketScreen(true, MarketScreen.tempPage, MarketScreen.tempSortByPrice, MarketScreen.tempSortByType));
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
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START)
                && event.player.equals(Minecraft.getInstance().player)) {
            if (ClientUtils.missionScreenFlag != -1) {
                Minecraft.getInstance().setScreen(new MissionScreen(ClientUtils.missionScreenFlag, 0));
                ClientUtils.missionScreenFlag = -1;
            }
            if (event.player.tickCount % 100 == 0) {
                Inventory inventory = event.player.getInventory();
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    ItemStack itemStack = inventory.getItem(i);
                    Item item = itemStack.getItem();
                    if (!(Utils.mainHandTag.containsKey(item) || Utils.armorTag.containsKey(item)
                            || Utils.curiosList.contains(item) || Utils.passiveEquipTag.containsKey(item))) {
                        if (!itemStack.is(ModItems.RAILWAY_PILLAR_SET_TOOL.get())) {
                            itemStack.resetHoverName();
                        }
                    }
                }
            }

            if (event.player.tickCount % 20 == 0) {
                BackSpawn.setName(event.player);
            }

            Item mainhandItem = event.player.getMainHandItem().getItem();
            if (mainhandItem instanceof WraqMainHandEquip || mainhandItem instanceof WraqArmor) {
                Compute.forgingHoverName(event.player.getMainHandItem());
            }

            if (event.player.position().distanceTo(new Vec3(1138.5, 67.5, 384.5)) < 2)
                event.player.addDeltaMovement(new Vec3(0, 11, 0));

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


            if (event.player.tickCount % 20 == 0 && event.player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MINE_HAT.get())) {
                if (event.player.level().getLightEngine().getRawBrightness(event.player.blockPosition(), 0) <= 8) {
                    ModNetworking.sendToServer(new MineHatConfirmC2SPacket());
                }
            }

            if (event.player.tickCount % 20 == 0) {
                if (event.player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.SHIP_SCEPTRE.get())) {
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
                boolean hasUDisk = InventoryOperation
                        .checkPlayerHasItem(inventory, ModItems.U_DISK.get(), 1);
                Screen screen = Minecraft.getInstance().screen;
                if (hasUDisk) {
                    if (!(screen instanceof TradeScreen)) {
                        if (InventoryOperation.checkPlayerHasItem(inventory, ModItems.COPPER_COIN.get(), 1)
                                || InventoryOperation.checkPlayerHasItem(inventory, ModItems.GOLD_COIN.get(), 1)
                                || InventoryOperation.checkPlayerHasItem(inventory, ModItems.SILVER_COIN.get(), 1)) {
                            ModNetworking.sendToServer(new AllCurrencyC2SPacket(false));
                        }
                        if (InventoryOperation.checkPlayerHasItem(inventory, ModItems.WORLD_SOUL_1.get(), 64)) {
                            ModNetworking.sendToServer(new UdiskWorldSoulC2SPacket());
                        }
                    }
                    if (!(screen instanceof ElementPieceGui)) {
                        boolean open = false;
                        for (int i = 0 ; i < inventory.getContainerSize() ; i ++) {
                            ItemStack stack = inventory.getItem(i);
                            Item item = stack.getItem();
                            if (item instanceof UDisk) {
                                open = UDisk.getElementCollectionStatus(stack);
                                break;
                            }
                        }
                        if (open) {
                            for (int i = 0 ; i < inventory.getContainerSize() ; i ++) {
                                ItemStack stack = inventory.getItem(i);
                                Item item = stack.getItem();
                                if (ElementPieceRecipe.getItemSet().contains(item)) {
                                    ModNetworking.sendToServer(new ElementPieceC2SPacket("convert", -1, -1));
                                    break;
                                }
                            }
                        }
                    }
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
                    case 5 -> mc.setScreen(new SmeltRecipeScreen());
                    case 6 -> mc.setScreen(new EndlessCoreScreen());
                    case 7 -> mc.setScreen(new ElementPieceGui());
                    case 8 -> mc.setScreen(new FoodCoinStore());
                    case 9 -> mc.setScreen(new DragonBoatStore());
                    case 10 -> mc.setScreen(new TradeScreen(true, TradeListNew.WEEKLY_STORE_VILLAGER_NAME));
                    case 11 -> mc.setScreen(new CrystalScreen());
                    case 12 -> mc.setScreen(new Summer2025Store());
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
            if (ClientUtils.ChargedCountsSakuraDemonSword < 100 && MainHand.equals(ModItems.SAKURA_SWORD.get())
                    && ClientUtils.Demon_Image[1] != null && ClientUtils.Demon_Image[1].getTickTime() == 0) {
                if (player.getDeltaMovement().length() > 0.1) ClientUtils.ChargedCountsSakuraDemonSword += Speed;
                ClientUtils.ChargedCountsSakuraDemonSword += 0.5;
            }

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
                ClientUtils.ChargedFlagSakuraDemonSword = true;
                ClientUtils.ChargedFlagZeusSword = true;
            }

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
                ParticleProvider.BallParticle(manaAttackParticle.getPos(), level,
                        2 * (20 - manaAttackParticle.getTickTime()) / 20.0,
                        Utils.ParticleStringToParticleMap.get(manaAttackParticle.getType()), 20);
                manaAttackParticle.setTickTime(Math.max(-1, manaAttackParticle.getTickTime() - 1));
            }
            ClientUtils.manaAttackParticleArrayList.removeIf(manaAttackParticle -> manaAttackParticle.getTickTime() == -1);
        }
    }

    private static Vec3 windVec = Vec3.ZERO;

    private static void windLand(Player player) {
        if (player.level().dimension().equals(Level.OVERWORLD)
                && player.position().distanceTo(new Vec3(1813, 159, -1553)) < 100) {
            if (player.tickCount % 100 == 0) {
                Random random = new Random();
                windVec = new Vec3(-0.1 + random.nextDouble(0.2),
                        0, -0.1 + random.nextDouble(0.2));
            }
            player.setDeltaMovement(player.getDeltaMovement().add(windVec));
        }
    }
}

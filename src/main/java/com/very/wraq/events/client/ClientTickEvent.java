package com.very.wraq.events.client;

import com.very.wraq.customized.players.sceptre.liulixian_.LiulixianCurios2;
import com.very.wraq.customized.players.sceptre.shangmengli.ShangMengLiSword;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.AttributePackets.MobAttributeC2SPacket;
import com.very.wraq.netWorking.misc.CodeSceptrePackets.CodeC2SPacket;
import com.very.wraq.netWorking.misc.EarthPower.EarthPowerC2SPacket;
import com.very.wraq.netWorking.misc.SkillPackets.Charging.ChargedFullC2SPacket;
import com.very.wraq.netWorking.misc.SkillPackets.SkillRequestC2SPacket;
import com.very.wraq.netWorking.misc.SmartPhonePackets.GoldC2SPacket;
import com.very.wraq.netWorking.misc.SmartPhonePackets.RequestC2SPacket;
import com.very.wraq.netWorking.misc.SmartPhonePackets.SilverC2SPacket;
import com.very.wraq.netWorking.misc.TeamPackets.TeamInfoRequestC2SPacket;
import com.very.wraq.netWorking.misc.WaterBlockCountsC2SPacket;
import com.very.wraq.netWorking.unSorted.MineHatConfirmC2SPacket;
import com.very.wraq.netWorking.unSorted.PlayerIsNearbyCampfireC2SPacket;
import com.very.wraq.netWorking.unSorted.UdiskWorldSoulC2SPacket;
import com.very.wraq.process.element.Element;
import com.very.wraq.render.gui.skills.SkillGui;
import com.very.wraq.render.gui.team.PlayerRequestScreen;
import com.very.wraq.render.gui.team.TeamInfoScreen;
import com.very.wraq.render.gui.team.TeamManageScreen;
import com.very.wraq.render.gui.team.TeamSearchScreen;
import com.very.wraq.render.gui.villagerTrade.Trade;
import com.very.wraq.render.Particles.ModParticles;
import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.series.overWorld.SakuraSeries.EarthMana.EarthPower;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.ClientUtils;
import com.very.wraq.valueAndTools.Utils.Struct.ClientPlayerTeam;
import com.very.wraq.valueAndTools.Utils.Struct.ManaAttackParticle;
import com.very.wraq.valueAndTools.Utils.Struct.Mission;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
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
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientTickEvent {
    @SubscribeEvent
    public static void ClientTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {

            if (LiulixianCurios2.flySpeed == 0 && !event.player.isCreative()) {
                event.player.getAbilities().flying = false;
            }

            if (event.player.tickCount % 200 == 0) {
                Inventory inventory = event.player.getInventory();
                for (int i = 0 ; i < inventory.getContainerSize(); i ++) {
                    ItemStack itemStack = inventory.getItem(i);
                    Item item = itemStack.getItem();
                    if (!(Utils.MainHandTag.containsKey(item) || Utils.ArmorTag.containsKey(item)
                            || Utils.CuriosList.contains(item) || Utils.PassiveEquipTag.containsKey(item))) {
                        itemStack.resetHoverName();
                    }
                }
            }

            if (Utils.MainHandTag.containsKey(event.player.getMainHandItem().getItem()))
                Compute.ForgingHoverName(event.player.getMainHandItem(),Component.literal(""));


            if (event.player.position().distanceTo(new Vec3(1138.5, 67.5, 384.5)) < 2) event.player.addDeltaMovement(new Vec3(0,11,0));

            Element.ClientTick(event.player.level());

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

            if (ClientUtils.elementEffects != null) {
                if (!ClientUtils.elementEffects.NoTime) ClientUtils.elementEffects.TickCount --;
                if (ClientUtils.elementEffects.TickCount < 0) ClientUtils.elementEffects = null;
            }

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

            ClientUtils.mobAttribute = Compute.powerDetectPlayerPickMob(event.player,8,0.5);
            if (ClientUtils.mobAttribute != null) {
                ModNetworking.sendToServer(new MobAttributeC2SPacket(ClientUtils.mobAttribute.getId()));
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
                    Compute.FormatMSGSend(player, Component.literal("风之谷").withStyle(CustomStyle.styleOfKaze),
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

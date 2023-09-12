package com.Very.very.Events.SecEvents;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Mod.EventBusSubscriber
public class NSLevelEvent {
    @SubscribeEvent
    public static void NSLevel(TickEvent.LevelTickEvent event){
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.NETHER))) {
            Level level = event.level;
            int Tick = event.level.getServer().getTickCount();
            if (Tick % 20 == 0) {
                if (Utils.NSPlayerController.size() > 0) {
                    Player removePlayer = null;
                    boolean removeFlag = false;
                    for (Player player : Utils.NSPlayerController) {
                        CompoundTag data = player.getPersistentData();
                        if (data.contains(StringUtils.Login.Status) && data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Offline)) {
                            removePlayer = player;
                            removeFlag = true;
                            data.putBoolean("NSConfirm",false);
                        }
                    }
                    if(removeFlag) {
                        Utils.NSPlayerController.remove(removePlayer);
                    }
                }
                if (Utils.NSController > 0) Utils.NSController--;
                if (Utils.NSPlayerController.size() > 0 && Utils.NSController == -1) {
                    boolean flag = true;
                    boolean removeFlag = false;
                    Player removePlayer = null;
                    for (Player player : Utils.NSPlayerController) {
                        CompoundTag data = player.getPersistentData();
                        if (data.contains("NSConfirm") && !data.getBoolean("NSConfirm")) flag = false;
                        if (!data.contains("NSConfirm")) flag = false;
                        if(!(player.level().equals(player.getServer().getLevel(Level.NETHER)) &&
                                player.getX() > 27 && player.getX() < 30 &&
                                player.getZ() > 266 && player.getZ() < 269 && player.getY() == 32)) {
                            removePlayer = player;
                            removeFlag = true;
                            data.putBoolean("NSConfirm",false);
                        }
                    }
                    if (removeFlag) {
                        Compute.FormatMSGSend(removePlayer,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                Component.literal("已取消确认！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                        Utils.NSPlayerController.remove(removePlayer);
                    }
                    if (flag) {
                        for (Player player : Utils.NSPlayerController) {
                            CompoundTag data = player.getPersistentData();
                            Calendar cal = Calendar.getInstance();
                            Date date = cal.getTime();
                            SimpleDateFormat tmpDate = new SimpleDateFormat("yyyyMMddHHmmss");
                            String DateString = tmpDate.format(date);
                            data.putString("NSDATE",DateString);
                            data.putInt("NSTIME",120);
                            Compute.FormatMSGSend(player, Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                    withStyle(Utils.styleOfNether),Component.literal("已激活！现在你有两分钟的时间可以击杀副本怪物！"));
                            Compute.FormatBroad(player.level(),Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).
                                    withStyle(Utils.styleOfNether),Component.literal(player.getName().getString()+"已激活").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal("下界副本！").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether)));
                            player.moveTo(3,80,249);
                            Utils.NSPlayerInController.add(player);
                            data.putBoolean("NSConfirm",false);
                        }
                        Utils.NSController = 120;
                        Utils.NSPlayerController.clear();
                        Utils.NSS1 = true;
                        Utils.NSS2 = true;
                    }
                    else {
                        String playerList = "";
                        int Count = 0;
                        for (Player player : Utils.NSPlayerController) {
                            CompoundTag data = player.getPersistentData();
                            if (data.contains("NSConfirm") && !data.getBoolean("NSConfirm")) {
                                playerList += player.getName().getString() + ",";
                                Count++;
                            }
                        }
                        if(playerList.length() != 0) playerList = playerList.substring(0,playerList.length()-1);
                        for (Player player : Utils.NSPlayerController) {
                            CompoundTag data = player.getPersistentData();
                            if (data.contains("NSConfirm") && data.getBoolean("NSConfirm")) {
                                Compute.FormatMSGSend(player, Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                        Component.literal("还有"+Count+"名玩家未确认。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                                Compute.FormatMSGSend(player, Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                        Component.literal("Ta们是"+playerList).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                            }
                        }
                    }
                }
                if (Utils.NSController > 0) {
                    double rate = 0.5 + (Utils.NSPlayerInController.size() * 0.5);
                    if (Utils.NSS1) {
                        if (Utils.NSWitherSkeleton1 != null) Utils.NSWitherSkeleton1.kill();
                        Utils.NSWitherSkeleton1 = new WitherSkeleton(EntityType.WITHER_SKELETON,level);
                        Utils.NSWitherSkeleton1.getAttribute(Attributes.MAX_HEALTH).setBaseValue(38400 * rate);
                        Utils.NSWitherSkeleton1.setHealth(Utils.NSWitherSkeleton1.getMaxHealth());
                        Utils.NSWitherSkeleton1.setItemSlot(EquipmentSlot.HEAD,Moditems.ArmorNSHelmet.get().getDefaultInstance());
                        Utils.NSWitherSkeleton1.setItemSlot(EquipmentSlot.CHEST,Moditems.ArmorNetherSkeletonChest.get().getDefaultInstance());
                        Utils.NSWitherSkeleton1.setItemSlot(EquipmentSlot.LEGS,Moditems.ArmorNetherSkeletonLeggings.get().getDefaultInstance());
                        Utils.NSWitherSkeleton1.setItemSlot(EquipmentSlot.FEET,Moditems.ArmorNetherSkeletonBoots.get().getDefaultInstance());
                        Utils.NSWitherSkeleton1.setItemSlot(EquipmentSlot.MAINHAND, Items.NETHERITE_SWORD.getDefaultInstance());
                        Utils.NSWitherSkeleton1.moveTo(-1.5,80,245.5);
                        level.addFreshEntity(Utils.NSWitherSkeleton1);

                        if (Utils.NSWitherSkeleton2 != null) Utils.NSWitherSkeleton2.kill();
                        Utils.NSWitherSkeleton2 = new WitherSkeleton(EntityType.WITHER_SKELETON,level);
                        Utils.NSWitherSkeleton2.getAttribute(Attributes.MAX_HEALTH).setBaseValue(38400 * rate);
                        Utils.NSWitherSkeleton2.setHealth(Utils.NSWitherSkeleton2.getMaxHealth());
                        Utils.NSWitherSkeleton2.setItemSlot(EquipmentSlot.HEAD,Moditems.ArmorNSHelmet.get().getDefaultInstance());
                        Utils.NSWitherSkeleton2.setItemSlot(EquipmentSlot.CHEST,Moditems.ArmorNetherSkeletonChest.get().getDefaultInstance());
                        Utils.NSWitherSkeleton2.setItemSlot(EquipmentSlot.LEGS,Moditems.ArmorNetherSkeletonLeggings.get().getDefaultInstance());
                        Utils.NSWitherSkeleton2.setItemSlot(EquipmentSlot.FEET,Moditems.ArmorNetherSkeletonBoots.get().getDefaultInstance());
                        Utils.NSWitherSkeleton2.setItemSlot(EquipmentSlot.MAINHAND, Items.NETHERITE_SWORD.getDefaultInstance());
                        Utils.NSWitherSkeleton2.moveTo(-7.5,80,251.5);
                        level.addFreshEntity(Utils.NSWitherSkeleton2);
                        Utils.NSS1 = false;
                    }
                    if (Utils.NSS2 && ((Utils.NSWitherSkeleton1.isDeadOrDying() && Utils.NSWitherSkeleton2.isDeadOrDying()) || Utils.NSController == 60)) {
                        for (Player player : Utils.NSPlayerInController) {
                            Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                    Component.literal("第二层怪物已刷新！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                        }
                        if (Utils.NSZombifiedPiglin1 != null) Utils.NSZombifiedPiglin1.kill();
                        Utils.NSZombifiedPiglin1 = new ZombifiedPiglin(EntityType.ZOMBIFIED_PIGLIN,level);
                        Utils.NSZombifiedPiglin1.getAttribute(Attributes.MAX_HEALTH).setBaseValue(66666 * rate);
                        Utils.NSZombifiedPiglin1.setHealth(Utils.NSZombifiedPiglin1.getMaxHealth());
                        Utils.NSZombifiedPiglin1.setItemSlot(EquipmentSlot.HEAD,Moditems.ArmorNSHelmet.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin1.setItemSlot(EquipmentSlot.CHEST,Moditems.ArmorNetherSkeletonChest.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin1.setItemSlot(EquipmentSlot.LEGS,Moditems.ArmorNetherSkeletonLeggings.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin1.setItemSlot(EquipmentSlot.FEET,Moditems.ArmorNetherSkeletonBoots.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin1.setItemSlot(EquipmentSlot.MAINHAND, Items.GOLDEN_SWORD.getDefaultInstance());
                        Utils.NSZombifiedPiglin1.moveTo(-18.5,88,255.5);
                        level.addFreshEntity(Utils.NSZombifiedPiglin1);

                        if (Utils.NSZombifiedPiglin2 != null) Utils.NSZombifiedPiglin2.kill();
                        Utils.NSZombifiedPiglin2 = new ZombifiedPiglin(EntityType.ZOMBIFIED_PIGLIN,level);
                        Utils.NSZombifiedPiglin2.getAttribute(Attributes.MAX_HEALTH).setBaseValue(66666 * rate);
                        Utils.NSZombifiedPiglin2.setHealth(Utils.NSZombifiedPiglin2.getMaxHealth());
                        Utils.NSZombifiedPiglin2.setItemSlot(EquipmentSlot.HEAD,Moditems.ArmorNSHelmet.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin2.setItemSlot(EquipmentSlot.CHEST,Moditems.ArmorNetherSkeletonChest.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin2.setItemSlot(EquipmentSlot.LEGS,Moditems.ArmorNetherSkeletonLeggings.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin2.setItemSlot(EquipmentSlot.FEET,Moditems.ArmorNetherSkeletonBoots.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin2.setItemSlot(EquipmentSlot.MAINHAND, Items.GOLDEN_SWORD.getDefaultInstance());
                        Utils.NSZombifiedPiglin2.moveTo(-18.5,87,242.5);
                        level.addFreshEntity(Utils.NSZombifiedPiglin2);

                        if (Utils.NSZombifiedPiglin3 != null) Utils.NSZombifiedPiglin3.kill();
                        Utils.NSZombifiedPiglin3 = new ZombifiedPiglin(EntityType.ZOMBIFIED_PIGLIN,level);
                        Utils.NSZombifiedPiglin3.getAttribute(Attributes.MAX_HEALTH).setBaseValue(66666 * rate);
                        Utils.NSZombifiedPiglin3.setHealth(Utils.NSZombifiedPiglin3.getMaxHealth());
                        Utils.NSZombifiedPiglin3.setItemSlot(EquipmentSlot.HEAD,Moditems.ArmorNSHelmet.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin3.setItemSlot(EquipmentSlot.CHEST,Moditems.ArmorNetherSkeletonChest.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin3.setItemSlot(EquipmentSlot.LEGS,Moditems.ArmorNetherSkeletonLeggings.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin3.setItemSlot(EquipmentSlot.FEET,Moditems.ArmorNetherSkeletonBoots.get().getDefaultInstance());
                        Utils.NSZombifiedPiglin3.setItemSlot(EquipmentSlot.MAINHAND, Items.GOLDEN_SWORD.getDefaultInstance());
                        Utils.NSZombifiedPiglin3.moveTo(-29.5,87,236.5);
                        level.addFreshEntity(Utils.NSZombifiedPiglin3);

                        Utils.NSS2 = false;
                    }

                    if(!Utils.NSS2 && Utils.NSZombifiedPiglin1 != null && Utils.NSZombifiedPiglin2 != null && Utils.NSZombifiedPiglin3 != null && Utils.NSZombifiedPiglin1.isDeadOrDying() && Utils.NSZombifiedPiglin2.isDeadOrDying() && Utils.NSZombifiedPiglin3.isDeadOrDying()) Utils.NSClear = true;
                }
                if (Utils.NSController == 0) {
                    for (Player player : Utils.NSPlayerInController) {
                        CompoundTag data = player.getPersistentData();
                        data.putBoolean("NSConfirm",false);
                        Compute.FormatMSGSend(player,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                Component.literal("时间耗尽,挑战失败。"));
                    }
                    Utils.NSClear = false;
                    Utils.NSPlayerInController.clear();
                    Utils.NSController = -1;
                }
                if (Utils.NSPlayerInController.size() > 0 && Utils.NSController != -1 && Utils.NSClear) {
                    String playerList = "";
                    int Count = 0;
                    for (Player player : Utils.NSPlayerInController) {
                        CompoundTag data = player.getPersistentData();
                        playerList += player.getName().getString() + ",";
                        Count++;
                        data.putBoolean("NSConfirm",false);
                    }
                    if(playerList.length() != 0) playerList.substring(0,playerList.length()-1);
                    double rank = 0;
                    if (Utils.NSController > 90) {
                        Compute.FormatBroad(level,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                Component.literal(Count+"名玩家在"+(120-Utils.NSController)+"秒内完成了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal("下界副本挑战。").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether)).
                                        append(Component.literal("获得").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal("传说").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GOLD)).
                                        append(Component.literal("评级!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        rank = 2.5;
                    }
                    else if (Utils.NSController > 60) {
                        Compute.FormatBroad(level,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                Component.literal(Count+"名玩家在"+(120-Utils.NSController)+"秒内完成了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal("下界副本挑战。").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether)).
                                        append(Component.literal("获得").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal("史诗").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)).
                                        append(Component.literal("评级!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        rank = 2;
                    }
                    else if (Utils.NSController > 30) {
                        Compute.FormatBroad(level,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                Component.literal(Count+"名玩家在"+(120-Utils.NSController)+"秒内完成了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal("下界副本挑战。").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether)).
                                        append(Component.literal("获得").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal("优秀").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).
                                        append(Component.literal("评级!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        rank = 1.5;
                    }
                    else {
                        Compute.FormatBroad(level,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                                Component.literal(Count+"名玩家在"+(120-Utils.NSController)+"秒内完成了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal("下界副本挑战。").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether)).
                                        append(Component.literal("获得").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal("合格").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).
                                        append(Component.literal("评级!").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        rank = 1;
                    }
                    Compute.FormatBroad(level,Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfNether),
                            Component.literal("Ta们是"+playerList).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
                    for (Player player : Utils.NSPlayerInController) {
                        ItemStack itemStack = Moditems.ruby.get().getDefaultInstance();
                        ItemStack itemStack1 = Moditems.NetherQuartz.get().getDefaultInstance();
                        itemStack.setCount(Utils.NSController);
                        itemStack1.setCount(Utils.NSController/2);
                        Compute.ItemStackGive(player,itemStack);
                        Compute.ItemStackGive(player,itemStack1);
                        ItemStack NetherGem = Moditems.NetherGem.get().getDefaultInstance();
                        NetherGem.getOrCreateTagElement(Utils.MOD_ID);
                        Compute.RandomAttributeProvider(NetherGem,1,rank,player);
                        Compute.RandomAttributeProvider(NetherGem,1,rank,player);
                        Compute.RandomAttributeProvider(NetherGem,1,1,player);
                        Compute.RandomAttributeProvider(NetherGem,1,0.8,player);
                        Compute.RandomAttributeProvider(NetherGem,1,0.65,player);
                        Compute.RandomAttributeProvider(NetherGem,1,0.5,player);
                        Compute.FormatBroad(player.level(),Component.literal("隐藏副本").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.RED),
                                Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(player.getName().getString()+"获得了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                        append(NetherGem.getDisplayName()));
                        Compute.ItemStackGive(player,NetherGem);
                    }
                    Utils.NSClear = false;
                    Utils.NSPlayerInController.clear();
                    Utils.NSController = -1;
                }
            }
        }
    }
}

package fun.wraq.series.end.eventController.SpiderRecall;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter2.dimension.ToEnd;
import fun.wraq.series.overworld.wind.WindItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class SpiderRecallEvent {
    @SubscribeEvent
    public static void Kaze(TickEvent.LevelTickEvent event) throws IOException {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "微光森林";
                Style style = CustomStyle.styleOfSpider;
                double ZoneX = -167;
                double ZoneY = 124;
                double ZoneZ = 1241;
                double PlatformX = 0;
                double PlatformY = 159;
                double PlatformZ = -40;
                if (Utils.spiderRecall.recallCount != -1) {
                    if (Utils.spiderRecall.recallPlayer == null) {
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(Utils.spiderRecall.playerName).
                                        append(Component.literal("尝试回想其在").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        if (Utils.SpiderRecallSpider != null)
                            Utils.SpiderRecallSpider.remove(Entity.RemovalReason.KILLED);

                        Utils.spiderRecall.Reset();
                        return;
                    }
                    if (Utils.spiderRecall.killCount == -1 && Utils.spiderRecall.recallCount != 400 && Utils.spiderRecall.recallCount != 380)
                        Utils.spiderRecall.recallCount--;
                    if (Utils.spiderRecall.recallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始" + ZoneName + "的回忆").withStyle(style));
                        Utils.spiderRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.spiderRecall.recallPlayer.getName().getString() + "开始回忆其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.spiderRecall.recallCount == 540) {
                        Utils.spiderRecall.killCount = 20;
                        Utils.spiderRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只微光蜘蛛").withStyle(style));
                        Utils.spiderRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.spiderRecall.recallCount--;
                    }
                    if (Utils.spiderRecall.killCount == 0) {
                        Utils.spiderRecall.killCount = -1;
                    }
                    if (Utils.spiderRecall.recallCount == 500) {
                        Utils.spiderRecall.recallPlayer.teleportTo(level1, PlatformX, PlatformY, PlatformZ, 0, 0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.spiderRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.spiderRecall.recallCount == 400) {
                        if (Utils.SpiderRecallSpider == null || !Utils.SpiderRecallSpider.isAlive()) {
                            if (Utils.SpiderRecallSpider != null)
                                Utils.SpiderRecallSpider.remove(Entity.RemovalReason.KILLED);
                            Utils.SpiderRecallSpider = new Spider(EntityType.SPIDER, level1);
                            MobSpawn.setMobCustomName(Utils.SpiderRecallSpider, ModItems.ARMOR_SPIDER_RECALL.get(), Component.literal("模糊记忆中的微光蜘蛛").withStyle(style));
                            Utils.SpiderRecallSpider.setItemSlot(EquipmentSlot.HEAD, ModItems.ARMOR_SPIDER_RECALL.get().getDefaultInstance());
                            Utils.SpiderRecallSpider.setItemSlot(EquipmentSlot.CHEST, ModItems.ARMOR_KAZE_CHEST.get().getDefaultInstance());
                            Utils.SpiderRecallSpider.setItemSlot(EquipmentSlot.LEGS, ModItems.ARMOR_KAZE_LEGGINGS.get().getDefaultInstance());
                            Utils.SpiderRecallSpider.setItemSlot(EquipmentSlot.FEET, ModItems.ARMOR_KAZE_BOOTS.get().getDefaultInstance());
                            Utils.SpiderRecallSpider.setItemSlot(EquipmentSlot.MAINHAND, WindItems.WIND_SWORD_3.get().getDefaultInstance());
                            Utils.SpiderRecallSpider.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.SpiderRecallSpider.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.SpiderRecallSpider.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.SpiderRecallSpider.setHealth(Utils.SpiderRecallSpider.getMaxHealth());
                            Utils.SpiderRecallSpider.moveTo(PlatformX, PlatformY, PlatformZ);
                            level1.addFreshEntity(Utils.SpiderRecallSpider);
                            Utils.spiderRecall.recallCount--;
                            Utils.spiderRecall.mob = Utils.SpiderRecallSpider;
                        }
                    }
                    if (Utils.spiderRecall.recallCount == 380) {
                        if (Utils.spiderRecall.recallSuccess) {
                            Utils.spiderRecall.recallCount--;
                        }
                    }
                    if (Utils.spiderRecall.recallCount == 340) {
                        if (Utils.spiderRecall.recallSuccess) {
                            CompoundTag data = Utils.spiderRecall.recallPlayer.getPersistentData();
                            String RecallTimes = "SpiderRecallTimes";
                            String RecallSuccessTimes = "SpiderRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime, 20)) {
                                data.putInt(RecallTimes, 0);
                                data.putInt(RecallSuccessTimes, data.getInt(RecallSuccessTimes) + 1);
                                Utils.spiderRecall.recallPlayer.teleportTo(overWorld, ZoneX, ZoneY, ZoneZ, 0, 0);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.spiderRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("成功").withStyle(ChatFormatting.GREEN)).
                                                append(Component.literal("回想起了在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的所经所厉。").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("这是Ta第").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.spiderRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.SPIDER_RECALL_SOUL.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.giveItemStack(Utils.spiderRecall.recallPlayer, itemStack);
                            } else {
                                data.putInt(RecallTimes, data.getInt(RecallTimes) + 1);
                                ToEnd.toEndSpawnPos(Utils.spiderRecall.recallPlayer);
                                Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.spiderRecall.recallPlayer.getName().getString() + "尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.WHITE)));
                                Compute.sendFormatMSG(Utils.spiderRecall.recallPlayer, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal("" + data.getInt(RecallTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.SPIDER_RUNE.get().getDefaultInstance();
                                itemStack.setCount(1);
                                InventoryOperation.giveItemStack(Utils.spiderRecall.recallPlayer, itemStack);
                            }
                        }
                    }
                    if (Utils.spiderRecall.recallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.spiderRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.spiderRecall.Reset();
                        for (int i = 0; i < 9; i++) {
                            if (Utils.SpiderNetBlockPos[i] != null) {
                                if (level1.getBlockState(Utils.SpiderNetBlockPos[i]).getBlock().equals(Blocks.COBWEB))
                                    level1.destroyBlock(Utils.SpiderNetBlockPos[i], false);
                            }
                        }
                    }
                }

                if (Utils.SpiderRecallSpider != null && Utils.SpiderRecallSpider.isAlive()) { // AI
                    List<Player> list = level1.getEntitiesOfClass(Player.class, AABB.ofSize(Utils.SpiderRecallSpider.position(), 20, 20, 20));
                    for (Player player : list) {
                        if (player.position().distanceTo(Utils.SpiderRecallSpider.position()) <= 2.5) {
                            player.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 32));
                            if (Tick.get() % 20 == 0)
                                player.setHealth(player.getHealth() - player.getHealth() * 0.1f);
                        }
                    }
                    if (Tick.get() % 200 == 0) {
                        for (int i = 0; i < 9; i++) {
                            if (Utils.SpiderNetBlockPos[i] != null) {
                                if (level1.getBlockState(Utils.SpiderNetBlockPos[i]).getBlock().equals(Blocks.COBWEB))
                                    level1.destroyBlock(Utils.SpiderNetBlockPos[i], false);
                            }
                        }
                        int X = Utils.spiderRecall.recallPlayer.getBlockX();
                        int Y = Utils.spiderRecall.recallPlayer.getBlockY();
                        int Z = Utils.spiderRecall.recallPlayer.getBlockZ();
                        Utils.SpiderNetBlockPos[0] = new BlockPos(X, Y, Z);
                        Utils.SpiderNetBlockPos[1] = new BlockPos(X + 1, Y, Z + 1);
                        Utils.SpiderNetBlockPos[2] = new BlockPos(X + 1, Y, Z - 1);
                        Utils.SpiderNetBlockPos[3] = new BlockPos(X - 1, Y, Z + 1);
                        Utils.SpiderNetBlockPos[4] = new BlockPos(X - 1, Y, Z - 1);
                        Utils.SpiderNetBlockPos[5] = new BlockPos(X + 1, Y, Z);
                        Utils.SpiderNetBlockPos[6] = new BlockPos(X, Y, Z + 1);
                        Utils.SpiderNetBlockPos[7] = new BlockPos(X - 1, Y, Z);
                        Utils.SpiderNetBlockPos[8] = new BlockPos(X, Y, Z - 1);
                        for (int i = 0; i < 9; i++) {
                            if (level1.getBlockState(Utils.SpiderNetBlockPos[i]).getBlock().equals(Blocks.AIR))
                                level1.setBlockAndUpdate(Utils.SpiderNetBlockPos[i], Blocks.COBWEB.defaultBlockState());
                        }
                    }
                }

                if (Utils.spiderRecall.recallTimeLimit > 0) Utils.spiderRecall.recallTimeLimit--;
                else {
                    if (Utils.spiderRecall.recallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.spiderRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.spiderRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        ToEnd.toEndSpawnPos(Utils.spiderRecall.recallPlayer);
                        if (Utils.SpiderRecallSpider != null)
                            Utils.SpiderRecallSpider.remove(Entity.RemovalReason.KILLED);
                        Utils.spiderRecall.Reset();
                        for (int i = 0; i < 9; i++) {
                            if (Utils.SpiderNetBlockPos[i] != null) {
                                if (level1.getBlockState(Utils.SpiderNetBlockPos[i]).getBlock().equals(Blocks.COBWEB))
                                    level1.destroyBlock(Utils.SpiderNetBlockPos[i], false);
                            }
                        }
                    }
                }
                if (Utils.spiderRecall.recallTimeLimit > 0 && Utils.spiderRecall.recallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.spiderRecall.recallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.formatBroad(level, Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                            Component.literal(Utils.spiderRecall.recallPlayer.getName().getString() + "尝试回想其在").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                    ToEnd.toEndSpawnPos(Utils.spiderRecall.recallPlayer);
                    if (Utils.SpiderRecallSpider != null) Utils.SpiderRecallSpider.remove(Entity.RemovalReason.KILLED);
                    Utils.spiderRecall.Reset();
                    for (int i = 0; i < 9; i++) {
                        if (Utils.SpiderNetBlockPos[i] != null) {
                            if (level1.getBlockState(Utils.SpiderNetBlockPos[i]).getBlock().equals(Blocks.COBWEB))
                                level1.destroyBlock(Utils.SpiderNetBlockPos[i], false);
                        }
                    }
                }
            }
        }
    }
}

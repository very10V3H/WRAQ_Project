package fun.wraq.series.events.midautumn;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.randomevent.RandomAdditionalRewardEvent;
import fun.wraq.process.system.randomevent.impl.killmob.KillMobEvent;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.crystal.CrystalItems;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MidAutumnRabbitEvent extends KillMobEvent {

    public MidAutumnRabbitEvent(ResourceKey<Level> dimension, Vec3 pos, List<Component> readyAnnouncement,
                                List<Component> beginAnnouncement, List<Component> finishAnnouncement,
                                List<Component> overTimeAnnouncement, MinecraftServer server,
                                List<ItemAndRate> rewardList,
                                RandomAdditionalRewardEvent randomAdditionalRewardEvent) {
        super(dimension, pos, readyAnnouncement, beginAnnouncement, finishAnnouncement, overTimeAnnouncement,
                server, rewardList, randomAdditionalRewardEvent);
    }

    private ServerBossEvent serverBossEvent;

    private static final Style style = CustomStyle.styleOfMoon;

    private static final double MAX_HEALTH = 8.15 * Math.pow(10, 4);

    public static final String mobName = "超凶玉兔";

    private int lastHealthRate = 0;

    private int spawnedTick = -1;

    @Override
    protected void summonAndSetMobList() {
        Rabbit mob = new Rabbit(EntityType.RABBIT, level());
        mob.setVariant(Rabbit.Variant.EVIL);
        MobSpawn.setMobCustomName(mob, Te.s(mobName, style), 185);
        MobSpawn.MobBaseAttributes.xpLevel.put(MobSpawn.getMobOriginName(mob), 185);
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob,
                new MobAttributes(15000, 800, 800, 0.4, 3, 0.6, 600, 25, MAX_HEALTH, 0.3));
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, Tick.min(30)));
        mob.moveTo(pos);
        level().addFreshEntity(mob);
        mobList.add(mob);
        MobSpawn.setCanNotAddSlowDownOrImprison(mob);
        serverBossEvent = (ServerBossEvent) (new ServerBossEvent(mob.getDisplayName(),
                BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);
        spawnedTick = Tick.get();
    }

    @Override
    protected void tick() {
        super.tick();
        Mob mob = mobList.isEmpty() ? null : mobList.get(0);
        if (mob != null && mob.tickCount % 20 == 0) {
            players.stream().filter(player -> player.distanceTo(mob) <= 60).max(new Comparator<Player>() {
                @Override
                public int compare(Player o1, Player o2) {
                    return (int) (o1.distanceTo(mob) - o2.distanceTo(mob));
                }
            }).ifPresent(player -> {
                Compute.decreasePlayerHealth(player, player.getMaxHealth() * 0.05,
                        Te.s("被月轮割裂", style));
            });
            Random random = new Random();
            if ((int) (mob.getHealth() * 5 / mob.getMaxHealth()) != lastHealthRate) {
                lastHealthRate = (int) (mob.getHealth() * 5 / mob.getMaxHealth());
                players.forEach(player -> {
                    Compute.causeGatherEffect(player, Tick.s(1),
                            mob.position().add(
                                    4 - random.nextDouble(8),
                                    2,
                                    4 - random.nextDouble(8)));
                });
            }
        }
        if (serverBossEvent != null) {
            if (mob != null) {
                serverBossEvent.setProgress((float) (mob.getHealth() / MAX_HEALTH));
            }
            players.forEach(player -> {
                if (player.position().distanceTo(pos) < 32) {
                    serverBossEvent.addPlayer((ServerPlayer) player);
                } else {
                    serverBossEvent.removePlayer((ServerPlayer) player);
                }
            });
        }
    }

    @Override
    protected void finishAction() {
        if (serverBossEvent != null) {
            serverBossEvent.removeAllPlayers();
        }
        Random random = new Random();
        players.forEach(player -> {
            List.of(
                    new ItemStack(SpecialEventItems.MOON_OSMANTHUS.get(), 2),
                    new ItemStack(ModItems.REVELATION_HEART.get())
            ).forEach(itemStack -> {
                InventoryOperation.giveItemStackWithMSG(player, itemStack);
            });
            if (random.nextDouble() < 0.1) {
                InventoryOperation.giveItemStackWithMSG(player, CrystalItems.YELLOW_CRYSTAL_C.get());
            }
            if (Tick.get() - spawnedTick < Tick.min(1)) {
                sendFormatMSG(player, Te.s("在", "1min内", ChatFormatting.AQUA,
                        "捉到了", "超凶玉兔", style, "，获得了额外奖励!"));
                List.of(
                        new ItemStack(SpecialEventItems.MOON_OSMANTHUS.get(), 1),
                        new ItemStack(SpecialEventItems.OSMANTHUS.get(), 5)
                ).forEach(itemStack -> {
                    InventoryOperation.giveItemStackWithMSG(player, itemStack);
                });
            }
        });
    }

    public static double getAdjustedDamage(Mob mob, double damage) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            return MAX_HEALTH * 0.001;
        }
        return damage;
    }
}

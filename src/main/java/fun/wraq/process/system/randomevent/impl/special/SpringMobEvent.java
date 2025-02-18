package fun.wraq.process.system.randomevent.impl.special;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.randomevent.RandomAdditionalRewardEvent;
import fun.wraq.process.system.randomevent.impl.killmob.KillMobEvent;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.events.spring2024.FireworkGun;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class SpringMobEvent extends KillMobEvent {

    public SpringMobEvent(ResourceKey<Level> dimension, Vec3 pos, List<Component> readyAnnouncement,
                          List<Component> beginAnnouncement, List<Component> finishAnnouncement,
                          List<Component> overTimeAnnouncement, MinecraftServer server,
                          List<ItemAndRate> rewardList, RandomAdditionalRewardEvent randomAdditionalRewardEvent) {
        super(dimension, pos, readyAnnouncement, beginAnnouncement, finishAnnouncement, overTimeAnnouncement,
                server, rewardList, randomAdditionalRewardEvent);
    }

    public static final String mobName = "年兽";
    public static int luckyNumber = 0;
    public static final Style style = CustomStyle.styleOfSpring;
    public static double clientDamage = 0;
    public static int clientDamageDisplayExpiredTick = 0;
    public static int clientLuckyNumber = 0;

    private Mob boss;

    @Override
    protected void beginAction() {
        super.beginAction();
        luckyNumber = RandomUtils.nextInt(0, 10);
        Compute.formatBroad(Te.s("年兽", style),
                Te.s("幸运数字", ChatFormatting.LIGHT_PURPLE, "为 ", luckyNumber, ChatFormatting.LIGHT_PURPLE));
    }

    @Override
    protected void summonAndSetMobList() {
        boss = new Giant(EntityType.GIANT, level());
        MobSpawn.MobBaseAttributes.setMobBaseAttributes(boss, Te.s(mobName, style),
                2025, 2025, 2025, 2025, 2.025, 2.025,
                0.2025, 2025, 0.2025, 20252025, 0.5);
        MobSpawn.setStainArmorOnMob(boss, style);
        mobList.add(boss);
        boss.moveTo(pos);
        level().addFreshEntity(boss);
    }

    @Override
    protected void tick() {
        super.tick();
        if (boss == null) {
            return;
        }
        if (boss.tickCount % 100 == 25) {
            skill1();
        } else if (boss.tickCount % 100 == 75) {
            skill2();
        }
        if (boss.tickCount % Tick.s(10) == 1) {
            getAllPlayers().forEach(player -> {
                InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.MONEY.get());
                InventoryOperation.giveItemStackWithMSG(player, ModItems.GOLD_COIN.get());
                if (playerCausedDamageMap.containsKey(player.getName().getString())) {
                    sendFormatMSG(player, Te.s("目前你已经对年兽造成了 ",
                            String.format("%.0f", playerCausedDamageMap.get(player.getName().getString())),
                            style, "伤害值!"));
                }
            });
        }
    }

    // 对周围所有玩家造成当前生命值10% + 最大生命值10%的真实伤害
    private void skill1() {
        getAllPlayers().stream().filter(player -> player.distanceTo(boss) < 64)
                .forEach(player -> {
                    FireworkGun.summonFireWork(level(), player.position());
                    Damage.causeTrueDamageToPlayer(boss, player,
                            player.getHealth() * 0.1 + player.getMaxHealth() * 0.1);
                });
    }

    // 召唤下坠烟花，对爆炸命中的玩家造成50%最大生命值伤害
    private void skill2() {
        getAllPlayers().stream().filter(player -> player.distanceTo(boss) < 64)
                .forEach(player -> {
                    SpringMobFirework firework
                            = new SpringMobFirework(boss, new Vec3(player.getX(), player.getY() + 20, player.getZ()));
                    firework.setDeltaMovement(new Vec3(0, -2, 0));
                    level().addFreshEntity(firework);

                    FireworkGun.summonFireWork(level(), player.position().add(0, 3, 0));
                });
    }

    @Override
    protected void finishAction() {
        announceCurrentCausedDamageCountThenReward();
    }

    @Override
    protected boolean finishCondition() {
        return super.finishCondition() || (Tick.get() - beginTick == Tick.min(3));
    }

    @Override
    public void reset() {
        super.reset();
        playerCausedDamageMap.clear();
        causedDamageCount = BigDecimal.ZERO;
    }

    public static Map<String, Double> playerCausedDamageMap = new HashMap<>();
    public static BigDecimal causedDamageCount = BigDecimal.ZERO;

    public static void onPlayerCauseDamage(Player player, Mob mob, double damage) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            String name = player.getName().getString();
            playerCausedDamageMap.compute(name, (k, v) -> v == null ? damage : v + damage);
            causedDamageCount = causedDamageCount.add(BigDecimal.valueOf(damage));
        }
    }

    public static double onMobWithStandDamage(Mob mob) {
        return MobSpawn.getMobOriginName(mob).equals(mobName) ? 0 : 1;
    }

    public record PlayerNumberCount(Player player, int count) {
    }

    public void announceCurrentCausedDamageCountThenReward() {
        List<PlayerNumberCount> list = new ArrayList<>();
        playerCausedDamageMap.forEach((k, v) -> {
            ServerPlayer serverPlayer = Compute.getPlayerByName(k);
            if (serverPlayer != null) {
                list.add(new PlayerNumberCount(serverPlayer, getDamageLuckyNumberCount(v)));
            }
        });
        list.sort(new Comparator<PlayerNumberCount>() {
            @Override
            public int compare(PlayerNumberCount o1, PlayerNumberCount o2) {
                return o2.count - o1.count;
            }
        });
        Compute.broad(level(), Te.s(mobName, style, "已逃窜!"));
        Compute.formatBroad(Te.s("年兽", style),
                Te.s("幸运数字", ChatFormatting.LIGHT_PURPLE, "为",
                        luckyNumber, ChatFormatting.LIGHT_PURPLE));
        Compute.formatBroad(Te.s("年兽", style),
                Te.s("幸运数字", ChatFormatting.LIGHT_PURPLE, "个数排名:"));
        for (int i = 0; i < Math.min(list.size(), 5); i++) {
            PlayerNumberCount playerNumberCount = list.get(i);
            double damage = playerCausedDamageMap.get(playerNumberCount.player.getName().getString());
            Compute.broad(level(), Te.s((i + 1) + ".", style, playerNumberCount.player, " ",
                    getLuckyNumberDamageFormatString(damage, luckyNumber), " 共",
                    playerNumberCount.count, style, "个", "幸运数字", ChatFormatting.LIGHT_PURPLE));
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        Compute.broad(level(), Te.s("共", playerCausedDamageMap.size(), CustomStyle.styleOfPower,
                "名玩家一起对年兽造成了", decimalFormat.format(causedDamageCount), CustomStyle.styleOfPower, "伤害!"));
        list.forEach(playerNumberCount -> {
            Player player = playerNumberCount.player;
            if (player.experienceLevel >= 50) {
                int count = playerNumberCount.count;
                Item goldCoin = SpecialEventItems.SPRING_GOLD_COIN.get();
                InventoryOperation.giveItemStackWithMSG(player, goldCoin, count + 2);
                givePlayerSpringCurios(player, count);
            } else {
                Compute.sendFormatMSG(player, Te.s("年兽", style),
                        Te.s("需要达到", Utils.getLevelDescription(50), "才能获取", "年兽奖励", style));
            }
        });
    }

    public static final String SPRING_CURIO_TAG = "SPRING_CURIO";
    public static final String SPRING_2025_BELT = "SPRING_2025_BELT";
    public static final String SPRING_2025_HAND = "SPRING_2025_HAND";
    public static final String SPRING_2025_NECKLACE = "SPRING_2025_NECKLACE";
    public static final String SPRING_2025_RING = "SPRING_2025_RING";

    public static final List<String> springCuriosDataKeys = List.of(SPRING_2025_RING, SPRING_2025_HAND,
            SPRING_2025_NECKLACE, SPRING_2025_BELT);

    private static Map<String, Item> getSpringCurioMap() {
        return new HashMap<>() {{
            put(SPRING_2025_RING, SpecialEventItems.RING.get());
            put(SPRING_2025_HAND, SpecialEventItems.HAND.get());
            put(SPRING_2025_NECKLACE, SpecialEventItems.NECKLACE.get());
            put(SPRING_2025_BELT, SpecialEventItems.BELT.get());
        }};
    }

    public static void givePlayerSpringCurios(Player player, int count) {
        if (RandomUtils.nextInt(0, 100) < 5 + count) {
            CompoundTag data = Compute.getPlayerSpecificKeyCompoundTagData(player, SPRING_CURIO_TAG);
            List<String> curiosDataKeys = new ArrayList<>(springCuriosDataKeys);
            Item msgItem;
            if (curiosDataKeys.stream().allMatch(data::contains)) {
                InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.SPRING_GOLD_INGOT.get());
                msgItem = SpecialEventItems.SPRING_GOLD_INGOT.get();
            } else {
                curiosDataKeys.removeIf(data::contains);
                String dataKey = curiosDataKeys.get(RandomUtils.nextInt(0, curiosDataKeys.size()));
                InventoryOperation.giveItemStackWithMSG(player, getSpringCurioMap().get(dataKey));
                data.putBoolean(dataKey, true);
                msgItem = getSpringCurioMap().get(dataKey);
            }
            Compute.formatBroad(Te.s("年兽", style),
                    Te.s(player, "很幸运地捡到了", "年兽", style, "逃跑时掉落的 ", msgItem));
        }
    }

    public static Component getLuckyNumberDamageFormatString(double damage, int luckyNumber) {
        String damageNumber = String.valueOf((long) damage);
        MutableComponent formatString = Te.s("");
        for (int i = 0; i < damageNumber.length(); i++) {
            if (damageNumber.charAt(i) == '0' + luckyNumber) {
                formatString.append(Te.s(String.valueOf(damageNumber.charAt(i)), ChatFormatting.LIGHT_PURPLE));
            } else {
                formatString.append(Te.s(String.valueOf(damageNumber.charAt(i))));
            }
        }
        return formatString;
    }

    private int getDamageLuckyNumberCount(double damage) {
        int count = 0;
        long tmp = (long) damage;
        while (tmp > 0) {
            if (tmp % 10 == luckyNumber) {
                ++count;
            }
            tmp = tmp / 10;
        }
        return count;
    }

    private Set<ServerPlayer> getNearPlayers() {
        return new HashSet<>(Compute.getNearEntity(boss, Player.class, 32)
                .stream().map(entity -> (ServerPlayer) entity).toList());
    }

    private Set<ServerPlayer> getCausedPlayers() {
        Set<ServerPlayer> causedPlayers = new HashSet<>();
        playerCausedDamageMap.forEach((k, v) -> {
            ServerPlayer serverPlayer = Compute.getPlayerByName(k);
            if (serverPlayer == null) return;
            causedPlayers.add(serverPlayer);
        });
        return causedPlayers;
    }

    private Set<ServerPlayer> getAllPlayers() {
        Set<ServerPlayer> players = new HashSet<>(getNearPlayers());
        players.addAll(getCausedPlayers());
        return players;
    }

    public static void handleServerPlayerTick(Player player) {
        if (playerCausedDamageMap.containsKey(player.getName().getString())) {
            ModNetworking.sendToClient(
                    new SpringMobDamageS2CPacket(playerCausedDamageMap.get(player.getName().getString()),
                            Tick.get() + Tick.s(5), luckyNumber), player);
        }
    }

    public static void onPlayerLogin(Player player) {
        ModNetworking.sendToClient(new SpringMobDamageS2CPacket(0, 0, 0), player);
    }
}

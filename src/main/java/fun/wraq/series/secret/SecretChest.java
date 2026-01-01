package fun.wraq.series.secret;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.Utils;
import fun.wraq.events.client.ParticleEvent;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.bonuschest.BonusChestPlayerData;
import fun.wraq.render.particles.ModParticles;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;

public class SecretChest {

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("隐秘箱", ChatFormatting.AQUA), content);
    }

    private static void broadMSG(Component content) {
        Compute.formatBroad(Te.s("隐秘箱", ChatFormatting.AQUA), content);
    }

    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getSide().isServer()) {
            return;
        }

        Player player = event.getEntity();
        Level level = player.level();
        BlockPos blockPos = event.getHitVec().getBlockPos();

        SecretChestInfo chestInfo = currentSecretChestInfos.stream().filter(info
                        -> level.dimension().equals(info.dimension()) && info.blockPos.equals(blockPos))
                .findAny()
                .orElse(null);

        BlockEntity blockEntity = player.level().getBlockEntity(blockPos);
        if (!(blockEntity instanceof ChestBlockEntity)) {
            return;
        }

        ChestBlockEntity chestBlockEntity = (ChestBlockEntity) blockEntity;

        if (!allowOpen(player, event, level, blockPos, chestInfo, chestBlockEntity)) {
            return;
        }

        ItemStack itemStack = player.getMainHandItem();
        SecretKey.addUsedCount(player, itemStack, getTierUsedCount(chestInfo.tier));

        int getTierValue = getTierValue(chestInfo.tier);

        List<ItemStack> commonContent = SecretChestContent
                .generateContent(6, getTierValue / 6);
        ItemStack specialItem = SecretChestContent
                .generateContent(1, getTierValue / 2,
                        getUpperValue(chestInfo.tier), getNumBelowValue(chestInfo.tier)).get(0);

        // 在物品实体内设置物品
        for (int i = 0; i < commonContent.size(); i++) {
            chestBlockEntity.setItem(itemPos[i], commonContent.get(i));
        }
        chestBlockEntity.setItem(13, specialItem);

        BonusChestPlayerData.openBonusChestMap.put(Name.get(player), chestBlockEntity);
    }

    private static boolean allowOpen(Player player, PlayerInteractEvent.RightClickBlock event,
                                     Level level, BlockPos blockPos, SecretChestInfo chestInfo,
                                     ChestBlockEntity chestBlockEntity) {
        BlockState blockState = player.level().getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (!(block instanceof ChestBlock)) {
            return false;
        }

        if (currentSecretChestInfos.stream().noneMatch(info
                -> level.dimension().equals(info.dimension()) && info.blockPos.equals(blockPos))) {
            return false;
        }

        if (Utils.playerIsUsingBlockBlockPosMap.containsValue(blockPos)) {
            sendMSG(player, Te.s("有其他玩家正在获取这个奖励箱的内容，请稍等片刻。"));
            event.setCanceled(true);
            return false;
        }

        if (chestBlockEntity == null) {
            sendMSG(player, Te.s("出错了，联系铁头!"));
            event.setCanceled(true);
            return false;
        }

        if (chestInfo == null) {
            sendMSG(player, Te.s("出错了，联系铁头!"));
            event.setCanceled(true);
            return false;
        }

        ItemStack itemStack = player.getMainHandItem();
        boolean hasKey = itemStack.getItem() instanceof SecretKey;
        if (!hasKey) {
            sendMSG(player, Te.s("开启这个箱子需要使用", "隐秘箱钥匙", ChatFormatting.AQUA));
            event.setCanceled(true);
            return false;
        }

        SecretKey secretKey = (SecretKey) itemStack.getItem();
        boolean seriesMatch = secretKey.getSeries().equals(chestInfo.series);
        if (!seriesMatch) {
            sendMSG(player, Te.s("开启这个箱子需要", chestInfo.series.description, "系列的钥匙"));
            event.setCanceled(true);
            return false;
        }

        boolean tierMatch = secretKey.getTier() >= chestInfo.tier;
        if (!tierMatch) {
            sendMSG(player, Te.s("开启这个箱子需要", getTierDescription(chestInfo.tier),
                    "的", chestInfo.series.description, "钥匙"));
            event.setCanceled(true);
            return false;
        }

        if (playerHasOpenedSecretChest(player, chestInfo)) {
            sendMSG(player, Te.s("你最近已经打开过这个箱子了."));
            event.setCanceled(true);
            return false;
        }
        return true;
    }


    private static final Map<String, Set<SecretChestInfo>> playerOpenedSecretChestMap = new HashMap<>();

    private static boolean playerHasOpenedSecretChest(Player player, SecretChestInfo info) {
        if (!playerOpenedSecretChestMap.containsKey(Name.get(player))) {
            playerOpenedSecretChestMap.put(Name.get(player), new HashSet<>());
        }
        Set<SecretChestInfo> infoSet = playerOpenedSecretChestMap.get(Name.get(player));
        if (!infoSet.contains(info)) {
            infoSet.add(info);
            return false;
        }
        return true;
    }

    private record SecretChestInfo(ResourceKey<Level> dimension, BlockPos blockPos,
                                   SecretSeries series, int tier, int expiredTick, boolean renderParticle) {}

    private static final List<SecretChestInfo> currentSecretChestInfos = new ArrayList<>();

    public static boolean isSecretChest(Level level, BlockPos pos) {
        return currentSecretChestInfos.stream().anyMatch(info
                -> info.dimension.equals(level.dimension()) && info.blockPos.equals(pos));
    }

    public static void generateSecretChestInLevel(Level level, BlockPos pos, SecretSeries series,
                                                  int tier, boolean renderParticle) {
        Block block = SecretSeries.getChestBlock(series);
        level.setBlockAndUpdate(pos, block.defaultBlockState());
        currentSecretChestInfos.add(new SecretChestInfo(level.dimension(),
                pos, series, tier, Tick.get() + Tick.min(10), renderParticle));
    }

    public static void onServerStop() {
        removeAll();
    }

    public static void removeAll() {
        currentSecretChestInfos.forEach(info -> {
            Objects.requireNonNull(Tick.server.getLevel(info.dimension))
                    .setBlockAndUpdate(info.blockPos, Blocks.AIR.defaultBlockState());
        });
        currentSecretChestInfos.clear();
    }

    public static void handleServerTick() {
        removeExpired();
    }

    public static void removeExpired() {
        currentSecretChestInfos.stream().filter(info -> info.expiredTick < Tick.get())
                .forEach(info -> {
                    Objects.requireNonNull(Tick.server.getLevel(info.dimension))
                            .setBlockAndUpdate(info.blockPos, Blocks.AIR.defaultBlockState());
                    if (info.renderParticle) {
                        Compute.getPlayers().forEach(SecretChest::removeParticlePosToClient);
                    }
                });
        currentSecretChestInfos.removeIf(info -> info.expiredTick < Tick.get());
    }

    public static void randomSpawnChest(Level level) {
        List<SecretChestInfo> candidateInfos = getCandidateSecretChestInfos().stream()
                .filter(info -> info.dimension.equals(level.dimension()))
                .toList();
        SecretChestInfo info = candidateInfos.get(RandomUtils.nextInt(0, candidateInfos.size()));
        batchGenerateSecretChestInLevel(level, info.blockPos, info.series, RandomUtils.nextInt(2, 5));
        broadMSG(Te.s("在", "(", info.blockPos.toShortString(), ")",
                "处刷新了", info.series.description, "隐秘箱!"));
    }

    private static final List<BlockPos> offset = List.of(
            new BlockPos(1, 0, 1), new BlockPos(-1, 0, 1),
            new BlockPos(1, 0, -1), new BlockPos(-1, 0, -1)
    );

    private static void batchGenerateSecretChestInLevel(Level level, BlockPos centerPos,
                                                        SecretSeries series, int tier) {
        generateSecretChestInLevel(level, centerPos, series, tier, true);
        level.setBlockAndUpdate(centerPos.below(), getGlassBlock(tier).defaultBlockState());
        for (BlockPos offsetPos : offset) {
            BlockPos blockPos = centerPos.offset(offsetPos);
            int eachChestTier = RandomUtils.nextInt(0, tier);
            generateSecretChestInLevel(level, blockPos, series, eachChestTier, false);
            level.setBlockAndUpdate(blockPos.below(), getGlassBlock(eachChestTier).defaultBlockState());
        }
        Compute.getPlayers().forEach(serverPlayer -> {
            sendParticlePosToClient(serverPlayer, level.dimension(), centerPos, tier);
        });
    }

    private static Block getGlassBlock(int tier) {
        switch (tier) {
            case 0 -> {
                return Blocks.WHITE_STAINED_GLASS;
            }
            case 1 -> {
                return Blocks.LIGHT_BLUE_STAINED_GLASS;
            }
            case 2 -> {
                return Blocks.PURPLE_STAINED_GLASS;
            }
            case 3 -> {
                return Blocks.YELLOW_STAINED_GLASS;
            }
            case 4 -> {
                return Blocks.RED_STAINED_GLASS;
            }
        }
        return Blocks.WHITE_STAINED_GLASS;
    }

    private static final int[] tierUsedCount = new int[]{1, 2, 4, 8, 16};

    public static int getTierUsedCount(int tier) {
        return tierUsedCount[tier];
    }

    private static final int[] tierValue
            = new int[]{20000, 50000, 200000, 1000000, 5000000};

    public static int getTierValue(int tier) {
        return tierValue[tier];
    }

    private static final int[] upperValue
            = new int[]{25000, 100000, 500000, (int) Math.pow(10, 8), (int) Math.pow(10, 8)};

    public static int getUpperValue(int tier) {
        return upperValue[tier];
    }

    private static final int[] numBelowValue
            = new int[]{25000, 100000, 500000, 3000000, 15000000};

    public static int getNumBelowValue(int tier) {
        return numBelowValue[tier];
    }

    private static final int[] itemPos = new int[]{2, 6, 10, 16, 20, 24};

    public static Component getTierDescription(int tier) {
        switch (tier) {
            case 0 -> {
                return Te.s("白色");
            }
            case 1 -> {
                return Te.s("蓝色", ChatFormatting.AQUA);
            }
            case 2 -> {
                return Te.s("紫色", ChatFormatting.LIGHT_PURPLE);
            }
            case 3 -> {
                return Te.s("金色", ChatFormatting.GOLD);
            }
            case 4 -> {
                return Te.s("红色", ChatFormatting.RED);
            }
        }
        return Te.s("白色");
    }

    private static final List<SecretChestInfo> candidateSecretChestInfos = new ArrayList<>();

    private static List<SecretChestInfo> getCandidateSecretChestInfos() {
        if (candidateSecretChestInfos.isEmpty()) {
            candidateSecretChestInfos.add(
                    new SecretChestInfo(Level.OVERWORLD,
                            new BlockPos(711, 83, 290), SecretSeries.HOLY, 4, 0, true));
        }
        return candidateSecretChestInfos;
    }

    public static BlockPos clientCenterPos;

    public static int clientDimension;

    public static int clientTier;

    private static ResourceKey<Level> getDimension(int dimension) {
        switch (dimension) {
            case 0 -> {
                return Level.OVERWORLD;
            }
            case 1 -> {
                return Level.NETHER;
            }
            case 2 -> {
                return Level.END;
            }
        }
        return Level.OVERWORLD;
    }

    private static int getDimension(ResourceKey<Level> dimension) {
        if (dimension == Level.OVERWORLD) {
            return 0;
        } else if (dimension == Level.NETHER) {
            return 1;
        } else if (dimension == Level.END) {
            return 2;
        }
        return 0;
    }

    public static void onPlayerLogin(Player player) {
        currentSecretChestInfos.forEach(info -> {
            if (info.renderParticle) {
                sendParticlePosToClient(player, info.dimension, info.blockPos, info.tier);
            }
        });
    }

    private static void sendParticlePosToClient(Player player, ResourceKey<Level> dimension, BlockPos pos, int tier) {
        ModNetworking.sendToClient(new SecretChestS2CPacket(pos, getDimension(dimension), tier), player);
    }

    private static void removeParticlePosToClient(Player player) {
        ModNetworking.sendToClient(new SecretChestS2CPacket(BlockPos.ZERO, -1, -1), player);
    }

    @OnlyIn(Dist.CLIENT)
    public static void provideClientParticle(Player player) {
        if (clientCenterPos != null && !clientCenterPos.equals(BlockPos.ZERO)
                && player.level().dimension().equals(getDimension(clientDimension))) {
            ParticleEvent.createSpinParticle(player, clientCenterPos.getCenter(), 1.5,
                    ModParticles.VOLCANO_TP.get(), 20);
        }
    }
}

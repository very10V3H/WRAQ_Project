package fun.wraq.process.system.spur.events;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.BlockAndResetTime;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

public class WoodSpur {
    public static void woodEvent(net.minecraftforge.event.level.BlockEvent.BreakEvent event) {
        if (!event.getPlayer().level().isClientSide && !event.getPlayer().isCreative()) {
            Player player = event.getPlayer();
            BlockPos blockPos = event.getPos();
            Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
            Level level = player.level();
            BlockState blockState = level.getBlockState(blockPos);
            if (level.equals(overWorld)) {
                Block block = blockState.getBlock();
                if (block.toString().contains("log") && !block.toString().contains("stripped")) {
                    boolean hasLeafOnRoof = false;
                    BlockPos blockPos1 = blockPos.above();
                    while (!level.getBlockState(blockPos1).is(Blocks.AIR)) {
                        Block block1 = level.getBlockState(blockPos1).getBlock();
                        if (block1.toString().contains("leaves")) {
                            hasLeafOnRoof = true;
                            break;
                        }
                        blockPos1 = blockPos1.above();
                        if (level.getBlockState(blockPos1).is(Blocks.AIR)) {
                            BlockPos tmp = new BlockPos(blockPos1.getX(), blockPos1.getY(), blockPos1.getZ());
                            for (int i = 0; i < 3; i++) {
                                tmp = tmp.above();
                                if (level.getBlockState(tmp).getBlock().toString().contains("leaves")) {
                                    hasLeafOnRoof = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (hasLeafOnRoof) {
                        do {
                            blockPos1 = blockPos1.below();
                        } while (level.getBlockState(blockPos1).getBlock().toString().contains("stripped"));
                        logReward(player);
                        InventoryOperation.giveItemStack(player, new ItemStack(blockState.getBlock().asItem(), 2));
                        Utils.worldWoodList.add(new BlockAndResetTime(blockState, blockPos1, Tick.get() + 36000));
                        level.setBlockAndUpdate(blockPos1, getStrippedLog(level.getBlockState(blockPos1).getBlock()).defaultBlockState());
                        Random random = new Random();
                        if (random.nextDouble() < Compute.playerExHarvest(player)) {
                            logReward(player);
                            InventoryOperation.giveItemStack(player, new ItemStack(blockState.getBlock().asItem(), 2));
                        }
                    }
                }
            }
        }
    }

    public static String logPieceGetTimes = "logPieceGetTimes";

    public static void logReward(Player player) {
        CompoundTag data = player.getPersistentData();
        addPlayerLopExp(player, 2);
        Compute.givePercentExpToPlayer(player, 0.005, 0, Math.min(player.experienceLevel, 50));
        Utils.dayLopCount.put(player.getName().getString(), Utils.dayLopCount.getOrDefault(player.getName().getString(), 0) + 1);
        Random random = new Random();
        if (random.nextDouble() < 0.05) {
            data.putInt(logPieceGetTimes, data.getInt(logPieceGetTimes) + 1);
            InventoryOperation.giveItemStack(player, new ItemStack(SpurItems.LOG_PIECE.get()));
        }
        if (Compute.exHarvestItemGive(player, new ItemStack(SpurItems.LOG_PIECE.get()), 0.05)) {
            data.putInt(logPieceGetTimes, data.getInt(logPieceGetTimes) + 1);
        }
    }

    public static Block getStrippedLog(Block block) {
        String s = block.toString();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '{') {
                s = s.substring(i + 1, s.length() - 1);
                break;
            }
        }
        String namespace = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ':') {
                namespace = s.substring(0, i + 1) + "stripped_" + s.substring(i + 1);
                break;
            }
        }
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(namespace));
    }

    public static final String EXP_DATA_KEY = "LopXp";
    public static int getPlayerLopExp(Player player) {
        return player.getPersistentData().getInt(EXP_DATA_KEY);
    }

    public static int getPlayerLopLevel(Player player) {
        int mineXp = getPlayerLopExp(player);
        if (mineXp <= 100) return 1;
        else if (mineXp <= 1000) return 2;
        else if (mineXp <= 5000) return 3;
        else if (mineXp <= 20000) return 4;
        else if (mineXp <= 100000) return 5;
        return 0;
    }

    public static void addPlayerLopExp(Player player, int num) {
        CompoundTag data = player.getPersistentData();
        data.putInt(EXP_DATA_KEY, data.getInt(EXP_DATA_KEY) + num);
    }

    public static void handleTick(Player player) {
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof AxeItem && player.tickCount % 5 == 1) {
            int exp = getPlayerLopExp(player);
            int level = getPlayerLopLevel(player);
            Compute.sendActionBarTextContentToPlayer(player,
                    Te.s("伐木经验 ", CustomStyle.styleOfHusk, exp, ChatFormatting.YELLOW,
                            "  伐木等级 ", CustomStyle.styleOfHusk, level, ChatFormatting.LIGHT_PURPLE,
                            "  挖掘速度 ", CustomStyle.styleOfMine,
                            String.format("%.0f%%", (1 + PlayerAttributes.getMineSpeedEnhanceRate(player)) * 100),
                            ChatFormatting.AQUA));
        }
    }
}

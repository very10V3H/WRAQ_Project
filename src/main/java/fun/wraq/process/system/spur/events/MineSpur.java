package fun.wraq.process.system.spur.events;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModBlocks;
import fun.wraq.common.util.ItemAndRate;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.BlockAndResetTime;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.missions.series.labourDay.LabourDayMission;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class MineSpur {

    public static void mineEvent(net.minecraftforge.event.level.BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        BlockPos blockPos = event.getPos();
        BlockState blockState = event.getState();
        Level level = player.level();
        Level OverWorld = player.getServer().getLevel(Level.OVERWORLD);
        if (!player.isCreative() && blockState.getBlock().toString().length() > 12
                && blockState.getBlock().toString().startsWith("create", 6)) event.setCanceled(true);

        if (!player.isCreative()) {
            if (!level.isClientSide && level.equals(OverWorld)) {
                int tickCount = level.getServer().getTickCount();
                Block block = blockState.getBlock();
                if (blockPos.getY() <= 11 || mineRewardMap.containsKey(block)) {
                    if (canBeDigBlockList.contains(block)) {
                        if (mineRewardMap.containsKey(block)) {
                            BlockAndResetTime blockAndResetTime = new BlockAndResetTime(blockState, blockPos, tickCount + 36000);
                            if (!Utils.posEvenBeenDigOrPlace.contains(blockPos)) {
                                Utils.worldMineList.add(blockAndResetTime);
                                Utils.posEvenBeenDigOrPlace.add(blockPos);
                            }
                            level.destroyBlock(blockPos, false);
                            ItemAndRate.summonItemEntity(mineRewardMap.get(block).item().getDefaultInstance(),
                                    blockPos.getCenter(), level);

                            mineReward(player, blockState, blockPos);

                            Random random = new Random();
                            if (random.nextDouble() < Compute.playerExHarvest(player)) {
                                Compute.sendFormatMSG(player, Component.literal("额外产出").withStyle(ChatFormatting.GOLD),
                                        Component.literal("为你提供了额外产物！").withStyle(ChatFormatting.WHITE));
                                ItemAndRate.summonItemEntity(mineRewardMap.get(block).item().getDefaultInstance(),
                                        blockPos.getCenter(), level);
                                mineReward(player, blockState, blockPos);
                            }
                        } else {
                            BlockAndResetTime blockAndResetTime = new BlockAndResetTime(blockState, blockPos, tickCount + 1200);
                            String name = player.getName().getString();
                            if (!Utils.noMineDigMap.containsKey(name))
                                Utils.noMineDigMap.put(name, new LinkedList<>());
                            if (!Utils.posEvenBeenDigOrPlace.contains(blockPos)) {
                                Utils.noMineDigMap.get(name).add(blockAndResetTime);
                                Utils.posEvenBeenDigOrPlace.add(blockPos);
                            }
                            if ((blockState.is(Blocks.STONE) || blockState.is(Blocks.COBBLESTONE))) {
                                player.addItem(Items.COBBLESTONE.getDefaultInstance());
                            }
                            if ((blockState.is(Blocks.DEEPSLATE) || blockState.is(Blocks.COBBLED_DEEPSLATE))) {
                                player.addItem(Items.COBBLED_DEEPSLATE.getDefaultInstance());
                            }
                            if (blockState.is(Blocks.ANDESITE)) player.addItem(Items.ANDESITE.getDefaultInstance());
                            if (blockState.is(Blocks.DIORITE)) player.addItem(Items.DIORITE.getDefaultInstance());
                            level.destroyBlock(blockPos, false);
                            if (Utils.noMineDigMap.get(name).size() > 80) {
                                BlockAndResetTime blockAndResetTime1 = Utils.noMineDigMap.get(name).poll();
                                level.setBlockAndUpdate(blockAndResetTime1.getBlockPos(), blockAndResetTime1.getBlockState());
                                Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime1.getBlockPos());
                            }
                        }
                    }
                }
            }
        }
    }

    public static String minePieceGetTimes = "minePieceGetTimes";

    public static String fromMineReward = "fromMineReward";

    public static void mineReward(Player player, BlockState blockState, BlockPos blockPos) {
        double rate = mineRewardMap.get(blockState.getBlock()).exp();
        addPlayerMineExp(player, (int) (rate * 100));
        rate *= (1 + getPlayerMineLevel(player)) * 0.25;
        ItemAndRate.dropOrbs(Math.min(player.experienceLevel, 50), rate, player.level(), blockPos.getCenter(), fromMineReward);

        ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.EXPERIENCE_ORB_PICKUP), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.5f, 1, 1);
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSoundPacket);
        Utils.dayMineCount.put(player.getName().getString(), Utils.dayMineCount.getOrDefault(player.getName().getString(), 0) + 1);
        LabourDayMission.count(player, LabourDayMission.mineCounts);

        Random random = new Random();
        if (random.nextDouble() < 0.08) {
            CompoundTag tag = player.getPersistentData();
            tag.putInt(minePieceGetTimes, tag.getInt(minePieceGetTimes) + 1);
            InventoryOperation.itemStackGive(player, new ItemStack(SpurItems.minePiece.get()));
        }
        if (Compute.exHarvestItemGive(player, new ItemStack(SpurItems.minePiece.get()), 0.08)) {
            CompoundTag tag = player.getPersistentData();
            tag.putInt(minePieceGetTimes, tag.getInt(minePieceGetTimes) + 1);
        }
    }

    public static int getPlayerMineLevel(Player player) {
        CompoundTag data = player.getPersistentData();
        int MineXp = data.getInt(StringUtils.Mine.Exp);
        if (MineXp <= 100) return 1;
        else if (MineXp <= 500) return 2;
        else if (MineXp <= 2000) return 3;
        else if (MineXp <= 5000) return 4;
        else if (MineXp <= 10000) return 5;
        return 0;
    }

    public static Map<Player, Integer> lastMineTickMap = new WeakHashMap<>();

    public static void addPlayerMineExp(Player player, int Num) {
        CompoundTag data = player.getPersistentData();
        data.putInt(StringUtils.Mine.Exp, data.getInt(StringUtils.Mine.Exp) + Num);
        ClientboundSetActionBarTextPacket clientboundSetActionBarTextPacket =
                new ClientboundSetActionBarTextPacket(Component.literal("采矿经验").withStyle(ChatFormatting.LIGHT_PURPLE).
                        append(Component.literal(" + ").withStyle(ChatFormatting.DARK_GREEN)).
                        append(Component.literal("" + Num).withStyle(ChatFormatting.GREEN)).
                        append(Component.literal(" (" + data.getInt(StringUtils.Mine.Exp) + ")").withStyle(ChatFormatting.GRAY)));
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSetActionBarTextPacket);

        if (getPlayerMineLevel(player) > 0
                && (!lastMineTickMap.containsKey(player) || lastMineTickMap.get(player) + 12000 < Tick.get())) {
            Compute.sendFormatMSG(player, Te.s("采矿", CustomStyle.styleOfStone),
                    Te.s("你丰富的采矿经验为你额外提供了",
                            getPlayerMineLevel(player) * 100 + "%挖掘速度", CustomStyle.styleOfStone));
            lastMineTickMap.put(player, Tick.get());
        }
    }

    public record DropAndExp(Item item, double exp) {
    }

    public static Map<Block, DropAndExp> mineRewardMap = new HashMap<>() {{
        put(Blocks.COAL_ORE, new DropAndExp(Items.COAL, 0.01));
        put(Blocks.DEEPSLATE_COAL_ORE, new DropAndExp(Items.COAL, 0.01));
        put(Blocks.COPPER_ORE, new DropAndExp(Items.RAW_COPPER, 0.02));
        put(Blocks.DEEPSLATE_COPPER_ORE, new DropAndExp(Items.RAW_COPPER, 0.02));
        put(Blocks.RAW_COPPER_BLOCK, new DropAndExp(Items.RAW_COPPER, 0.02));
        put(Blocks.IRON_ORE, new DropAndExp(Items.RAW_IRON, 0.03));
        put(Blocks.DEEPSLATE_IRON_ORE, new DropAndExp(Items.RAW_IRON, 0.03));
        put(Blocks.RAW_IRON_BLOCK, new DropAndExp(Items.RAW_IRON, 0.03));
        put(Blocks.LAPIS_ORE, new DropAndExp(Items.LAPIS_LAZULI, 0.03));
        put(Blocks.DEEPSLATE_LAPIS_ORE, new DropAndExp(Items.LAPIS_LAZULI, 0.03));
        put(Blocks.REDSTONE_ORE, new DropAndExp(Items.REDSTONE, 0.03));
        put(Blocks.DEEPSLATE_REDSTONE_ORE, new DropAndExp(Items.REDSTONE, 0.03));
        put(Blocks.GOLD_ORE, new DropAndExp(Items.RAW_GOLD, 0.04));
        put(Blocks.DEEPSLATE_GOLD_ORE, new DropAndExp(Items.RAW_GOLD, 0.04));
        put(Blocks.RAW_GOLD_BLOCK, new DropAndExp(Items.RAW_GOLD, 0.04));
        put(Blocks.DIAMOND_ORE, new DropAndExp(Items.DIAMOND, 0.05));
        put(Blocks.DEEPSLATE_DIAMOND_ORE, new DropAndExp(Items.DIAMOND, 0.05));
        put(Blocks.EMERALD_ORE, new DropAndExp(Items.EMERALD, 0.05));
        put(Blocks.DEEPSLATE_EMERALD_ORE, new DropAndExp(Items.EMERALD, 0.05));

        put(ModBlocks.WRAQ_ORE_1.get(), new DropAndExp(OreItems.WRAQ_ORE_1_ITEM.get(), 0.05));
        put(ModBlocks.WRAQ_ORE_2.get(), new DropAndExp(OreItems.WRAQ_ORE_2_ITEM.get(), 0.05));
        put(ModBlocks.WRAQ_ORE_3.get(), new DropAndExp(OreItems.WRAQ_ORE_3_ITEM.get(), 0.05));
        put(ModBlocks.WRAQ_ORE_4.get(), new DropAndExp(OreItems.WRAQ_ORE_4_ITEM.get(), 0.05));
    }};

    public static List<Item> getVanillaIngotItems() {
        return List.of(
                Items.COPPER_INGOT,
                Items.IRON_INGOT,
                Items.LAPIS_LAZULI,
                Items.REDSTONE,
                Items.GOLD_INGOT,
                Items.DIAMOND,
                Items.EMERALD
        );
    }

    public static Set<Block> canBeDigBlockList = new HashSet<>() {{
        Block[] blocks = {
                Blocks.DIRT,
                Blocks.COBBLESTONE,
                Blocks.STONE,
                Blocks.GRAVEL,
                Blocks.SMOOTH_BASALT,
                Blocks.TUFF,
                Blocks.DIORITE,
                Blocks.GRANITE,
                Blocks.ANDESITE,
                Blocks.DEEPSLATE,
                Blocks.COBBLED_DEEPSLATE,
                Blocks.SCULK,
                Blocks.COAL_ORE,
                Blocks.DEEPSLATE_COAL_ORE,
                Blocks.COPPER_ORE,
                Blocks.DEEPSLATE_COPPER_ORE,
                Blocks.RAW_COPPER_BLOCK,
                Blocks.IRON_ORE,
                Blocks.DEEPSLATE_IRON_ORE,
                Blocks.RAW_IRON_BLOCK,
                Blocks.LAPIS_ORE,
                Blocks.DEEPSLATE_LAPIS_ORE,
                Blocks.REDSTONE_ORE,
                Blocks.DEEPSLATE_REDSTONE_ORE,
                Blocks.GOLD_ORE,
                Blocks.DEEPSLATE_GOLD_ORE,
                Blocks.RAW_GOLD_BLOCK,
                Blocks.DIAMOND_ORE,
                Blocks.DEEPSLATE_DIAMOND_ORE,
                Blocks.EMERALD_ORE,
                Blocks.DEEPSLATE_EMERALD_ORE,
                Blocks.POINTED_DRIPSTONE,
                ModBlocks.WRAQ_ORE_1.get(), ModBlocks.WRAQ_ORE_2.get(),
                ModBlocks.WRAQ_ORE_3.get(), ModBlocks.WRAQ_ORE_4.get()
        };
        this.addAll(Arrays.asList(blocks));
        this.addAll(mineRewardMap.keySet());
    }};
}

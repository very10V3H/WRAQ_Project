package com.very.wraq.blocks.entity;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.very.wraq.Items.Forging.ForgeEnhance;
import com.very.wraq.Items.Forging.ForgeProtect;
import com.very.wraq.Items.Gems.Dismantle;
import com.very.wraq.Items.Gems.SlotOpen;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.events.mob.loot.RandomLootEquip;
import com.very.wraq.process.system.forge.ForgeEquipUtils;
import com.very.wraq.projectiles.WraqUniformCurios;
import com.very.wraq.render.gui.blocks.ForgingBlockMenu;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.gems.GemItems;
import com.very.wraq.series.gems.WraqGem;
import com.very.wraq.series.instance.Castle.CastleAttackArmor;
import com.very.wraq.series.instance.Castle.CastleManaArmor;
import com.very.wraq.series.instance.Castle.CastleSwiftArmor;
import com.very.wraq.series.newrunes.NewRuneItems;
import com.very.wraq.series.newrunes.RuneItem;
import com.very.wraq.series.overworld.forging.ForgingStone0;
import com.very.wraq.series.overworld.forging.ForgingStone1;
import com.very.wraq.series.overworld.forging.ForgingStone2;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ForgingBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 40;

    public ForgingBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FIRST_BLOCK_ENTITY.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ForgingBlockEntity.this.progress;
                    case 1 -> ForgingBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ForgingBlockEntity.this.progress = value;
                    case 1 -> ForgingBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("维瑞阿契锻造台");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ForgingBlockMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return LazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        LazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        LazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        compoundTag.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(compoundTag);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        itemStackHandler.deserializeNBT(compoundTag.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void drops(Player player) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            player.addItem(itemStackHandler.getStackInSlot(i));
            itemStackHandler.setStackInSlot(i, Items.AIR.getDefaultInstance());
        }
    }

    public void clear() {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemStackHandler.setStackInSlot(i, Items.AIR.getDefaultInstance());
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, ForgingBlockEntity blockEntity) {
        if (!level.isClientSide) {
            for (int i = 0 ; i < blockEntity.itemStackHandler.getSlots() ; i ++) {
                blockEntity.itemStackHandler.getStackInSlot(i).hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
            }
        }
        if (level.isClientSide()) {
            if (blockEntity.progress >= blockEntity.maxProgress) {
                Player player = level.getNearestPlayer(TargetingConditions.DEFAULT, pos.getX(), pos.getY(), pos.getZ());
                player.playSound(SoundEvents.ANVIL_USE);
            }
            return;
        }
        if (hasRecipe(blockEntity) || hasRecipe1(blockEntity) || hasRecipe2(blockEntity)
                || hasRecipe8(blockEntity) || hasRecipeOfDismantle(blockEntity) || hasRecipeOfCastleArmor(blockEntity)
                || hasRecipeOfForgePaper(blockEntity) || hasRecipeOfEquipPiece(blockEntity) || hasRecipeOfEquipPieceForge(blockEntity)) {
            /*blockEntity.progress++;*/
            setChanged(level, pos, blockState);
            if (blockEntity.progress >= blockEntity.maxProgress) {

                try {
                    craftItem(blockEntity);
                } catch (CommandSyntaxException e) {
                    throw new RuntimeException(e);
                }

                blockEntity.resetProgress();
            }
        } else {
            blockEntity.resetProgress();
            setChanged(level, pos, blockState);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    protected static void craftItem(ForgingBlockEntity blockEntity) throws CommandSyntaxException {

        String PlayerName = Utils.whoIsUsingBlock.get(blockEntity.getBlockPos());
        Player player = null;
        if (blockEntity.level.getServer().getPlayerList().getPlayerByName(PlayerName) != null)
            player = blockEntity.level.getServer().getPlayerList().getPlayerByName(PlayerName);

        if (hasRecipeOfForgePaper(blockEntity)) {
            ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(4);
            ItemStack forgePaper = blockEntity.itemStackHandler.getStackInSlot(3);
            CompoundTag data = equip.getTagElement(Utils.MOD_ID);

            if (forgePaper.is(ModItems.QingMingForgePaper.get())) data.putBoolean(StringUtils.QingMingForgePaper, true);
            if (forgePaper.is(ModItems.LabourDayForgePaper.get()))
                data.putBoolean(StringUtils.LabourDayForgePaper, true);

            Compute.forgingHoverName(equip);
            if (player != null) {
                Compute.formatBroad(player.level(), Component.literal("突破").withStyle(CustomStyle.styleOfPower),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 使用 ").withStyle(ChatFormatting.WHITE)).
                                append(forgePaper.getDisplayName()).
                                append(Component.literal(" 增幅了 ").withStyle(CustomStyle.styleOfPower)).
                                append(equip.getDisplayName()));
            }

            blockEntity.itemStackHandler.setStackInSlot(2, equip);
            blockEntity.itemStackHandler.extractItem(3, 1, false);
            blockEntity.itemStackHandler.extractItem(4, 1, false);
        } // 清符

        if (hasRecipeOfCastleArmor(blockEntity)) {
            ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack north = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack south = blockEntity.itemStackHandler.getStackInSlot(4);
            if (equip.getItem() instanceof CastleAttackArmor) {
                CastleAttackArmor.ForgeArmor(equip, north.is(ModItems.CastleCrystalNorth.get()), south.is(ModItems.CastleCrystalSouth.get()));
            } else if (equip.getItem() instanceof CastleSwiftArmor) {
                CastleSwiftArmor.ForgeArmor(equip, north.is(ModItems.CastleCrystalNorth.get()), south.is(ModItems.CastleCrystalSouth.get()));
            } else if (equip.getItem() instanceof CastleManaArmor) {
                CastleManaArmor.ForgeArmor(equip, north.is(ModItems.CastleCrystalNorth.get()), south.is(ModItems.CastleCrystalSouth.get()));
            }

            if (player != null) {
                Compute.formatBroad(player.level(), Component.literal("魔能注入").withStyle(CustomStyle.styleOfCastleCrystal),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 对 ").withStyle(ChatFormatting.WHITE)).
                                append(equip.getDisplayName()).
                                append(Component.literal(" 完成了一次").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("魔能注入").withStyle(CustomStyle.styleOfCastleCrystal)));
            }

            blockEntity.itemStackHandler.setStackInSlot(2, equip);
            blockEntity.itemStackHandler.extractItem(0, 1, false);
            blockEntity.itemStackHandler.extractItem(1, 1, false);
            if (north.is(ModItems.CastleCrystalNorth.get())) blockEntity.itemStackHandler.extractItem(3, 1, false);
            if ((south.is(ModItems.CastleCrystalSouth.get()))) blockEntity.itemStackHandler.extractItem(4, 1, false);
        }

        // 拆卸
        if (hasRecipeOfDismantle(blockEntity)) {
            ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(4);
            CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
            int index = data.getInt("newMaxSlot") - data.getInt("newSlot");

            String gemName = data.getString("newGems" + index);
            ItemStack gem = Compute.getItemFromString(gemName);

            data.remove("newGems" + index);
            data.putInt("newSlot", data.getInt("newSlot") + 1);
            blockEntity.itemStackHandler.setStackInSlot(2, sword);
            blockEntity.itemStackHandler.extractItem(4, 1, false);
            blockEntity.itemStackHandler.setStackInSlot(0, gem);
            blockEntity.itemStackHandler.extractItem(3, 1, false);
        }

        // 镶嵌
        if (hasRecipe(blockEntity)) {
            ItemStack gem = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(1);
            CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
            data.putInt("newSlot", data.getInt("newSlot") - 1);

            if (gem.getItem() instanceof WraqGem) {
                for (int i = 1; i <= 3; i++) {
                    if (!data.contains("newGems" + i)) {
                        data.putString("newGems" + i, Compute.getItemStackStringWithoutSlash(gem.getItem().getDefaultInstance()));
                        break;
                    }
                }
            }

            blockEntity.itemStackHandler.setStackInSlot(2, sword);
            blockEntity.itemStackHandler.extractItem(0, 1, false);
            blockEntity.itemStackHandler.extractItem(1, 1, false);
            if (player != null) Compute.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
        }

        // 开孔
        if (hasRecipe1(blockEntity)) {
            ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(4);
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);

            Random r = new Random();
            if (!data.contains("newMaxSlot")) {
                if (r.nextDouble() < 0.9) {
                    data.putInt("newSlot", 1);
                    data.putInt("newMaxSlot", 1);
                    if (player != null) {
                        Compute.sendFormatMSG(player, Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔成功。").withStyle(ChatFormatting.WHITE));
                        Compute.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
                    }
                } else {
                    if (player != null) {
                        Compute.sendFormatMSG(player, Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔失败。").withStyle(ChatFormatting.WHITE));
                        Compute.soundToPlayer(player, SoundEvents.ANVIL_DESTROY, blockEntity.getBlockPos().getCenter());
                    }
                }
            } else if (data.getInt("newMaxSlot") == 1) {
                if (r.nextDouble() < 0.6) {
                    data.putInt("newSlot", data.getInt("newSlot") + 1);
                    data.putInt("newMaxSlot", 2);
                    if (player != null) {
                        Compute.sendFormatMSG(player, Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔成功。").withStyle(ChatFormatting.WHITE));
                        Compute.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
                    }
                } else {
                    if (player != null) {
                        Compute.sendFormatMSG(player, Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔失败。").withStyle(ChatFormatting.WHITE));
                        Compute.soundToPlayer(player, SoundEvents.ANVIL_DESTROY, blockEntity.getBlockPos().getCenter());
                    }
                }
            } else if (data.getInt("newMaxSlot") == 2) {
                if (r.nextDouble() < 0.3) {
                    data.putInt("newSlot", data.getInt("newSlot") + 1);
                    data.putInt("newMaxSlot", 3);
                    if (player != null) {
                        Compute.sendFormatMSG(player, Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔成功。").withStyle(ChatFormatting.WHITE));
                        Compute.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
                    }
                } else {
                    if (player != null) {
                        Compute.sendFormatMSG(player, Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔失败。").withStyle(ChatFormatting.WHITE));
                        Compute.soundToPlayer(player, SoundEvents.ANVIL_DESTROY, blockEntity.getBlockPos().getCenter());
                    }
                }
            }

            blockEntity.itemStackHandler.setStackInSlot(2, equip);
            blockEntity.itemStackHandler.extractItem(3, 1, false);
            blockEntity.itemStackHandler.extractItem(4, 1, false);
        }

        // 强化
        if (hasRecipe2(blockEntity)) {
            ItemStack stone = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(1);

            ItemStack enhancePaper = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack protect = blockEntity.itemStackHandler.getStackInSlot(4);

            ItemStack slot3Item = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack slot4Item = blockEntity.itemStackHandler.getStackInSlot(4);
            if (slot3Item.is(ModItems.ForgeProtect.get())) protect = slot3Item;
            if (slot4Item.is(ModItems.ForgeProtect.get())) protect = slot4Item;
            List<Item> items = List.of(ModItems.ForgeEnhance0.get(), ModItems.ForgeEnhance1.get(), ModItems.ForgeEnhance2.get());
            if (items.contains(slot3Item.getItem())) enhancePaper = slot3Item;
            if (items.contains(slot4Item.getItem())) enhancePaper = slot4Item;

            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            Compute.forgingHoverName(equip);
            int forgelevel = 0;
            Random r = new Random();
            if (data.contains("Forging")) forgelevel = data.getInt("Forging");
            boolean flag = true;
            if (stone.getItem() instanceof ForgingStone0) {
                double successRate = (double) (10 - forgelevel) / 10;
                if (enhancePaper.is(ModItems.ForgeEnhance0.get())) successRate *= (1.25);
                if (enhancePaper.is(ModItems.ForgeEnhance1.get())) successRate *= (1.5);
                if (enhancePaper.is(ModItems.ForgeEnhance2.get())) successRate *= (2);
                if (r.nextDouble(1) < successRate) {
                    data.putInt("Forging", forgelevel + 1);
                    flag = false;
                }
            }

            if (stone.getItem() instanceof ForgingStone1) {
                double successRate = (double) (20 - forgelevel) / 20;
                if (enhancePaper.is(ModItems.ForgeEnhance0.get())) successRate *= (1.25);
                if (enhancePaper.is(ModItems.ForgeEnhance1.get())) successRate *= (1.5);
                if (enhancePaper.is(ModItems.ForgeEnhance2.get())) successRate *= (2);
                if (r.nextDouble(1) < successRate) {
                    data.putInt("Forging", forgelevel + 1);
                    flag = false;
                }
            }

            if (stone.getItem() instanceof ForgingStone2) {
                double successRate = (double) (24 - forgelevel) / 24;
                if (enhancePaper.is(ModItems.ForgeEnhance0.get())) successRate *= (1.25);
                if (enhancePaper.is(ModItems.ForgeEnhance1.get())) successRate *= (1.5);
                if (enhancePaper.is(ModItems.ForgeEnhance2.get())) successRate *= (2);
                if (r.nextDouble(1) < successRate) {
                    data.putInt("Forging", forgelevel + 1);
                    flag = false;
                }
            }

            if (stone.getItem().equals(ModItems.worldForgeStone.get())) {
                if (forgelevel < 24) {
                    data.putInt("Forging", forgelevel + 1);
                    flag = false;
                } else {
                    int minus = 32 - forgelevel;
                    double rate = 0.01 + minus * 0.005;
                    if (enhancePaper.is(ModItems.ForgeEnhance0.get())) rate *= (1.25);
                    if (enhancePaper.is(ModItems.ForgeEnhance1.get())) rate *= (1.5);
                    if (enhancePaper.is(ModItems.ForgeEnhance2.get())) rate *= (2);
                    if (r.nextDouble() < rate) {
                        data.putInt("Forging", forgelevel + 1);
                        flag = false;
                    }
                }
            }

            boolean useProtect = false;
            if (player != null) {
                if (flag) {
                    if (forgelevel >= 24 && !protect.is(ModItems.ForgeProtect.get())) {
                        int level = Math.max(forgelevel - 2, 23);
                        data.putInt("Forging", level);
                        Compute.formatBroad(player.level(), Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，装备等级掉至 ").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal("+" + level).withStyle(CustomStyle.styleOfWorld)));
                    }

                    if (forgelevel >= 17 && forgelevel < 19 && !protect.is(ModItems.ForgeProtect.get())) {
                        Compute.formatBroad(player.level(), Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，装备等级掉至 16").withStyle(ChatFormatting.WHITE)));
                        data.putInt("Forging", 16);
                    }
                    if (forgelevel >= 20 && forgelevel < 22 && !protect.is(ModItems.ForgeProtect.get())) {
                        Compute.formatBroad(player.level(), Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，装备等级掉至 19").withStyle(ChatFormatting.WHITE)));
                        data.putInt("Forging", 19);
                    }
                    if (forgelevel == 23 && !protect.is(ModItems.ForgeProtect.get())) {
                        Compute.formatBroad(player.level(), Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，装备等级掉至 22").withStyle(ChatFormatting.WHITE)));
                        data.putInt("Forging", 22);
                    }

                    if (forgelevel >= 17 && protect.is(ModItems.ForgeProtect.get())) {
                        useProtect = true;
                        Compute.sendFormatMSG(player, Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("使用了强化保护符，防止了强化等级掉落。").withStyle(ChatFormatting.WHITE));
                        Compute.formatBroad(player.level(), Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，好在使用了强化保护符。").withStyle(ChatFormatting.WHITE)));
                    }

                    Compute.sendFormatMSG(player, Component.literal("强化").withStyle(ChatFormatting.AQUA),
                            Component.literal("强化失败。").withStyle(ChatFormatting.GRAY));
                    Compute.soundToPlayer(player, SoundEvents.ANVIL_DESTROY, blockEntity.getBlockPos().getCenter());

                } else {
                    if (forgelevel >= 17)
                        Compute.broad(blockEntity.level, (Component.literal("[").withStyle(ChatFormatting.GRAY).
                                append(Component.literal("强化").withStyle(ChatFormatting.AQUA)).
                                append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                                append(player.getDisplayName()).
                                append(" 成功将 ").withStyle(ChatFormatting.WHITE)).
                                append(equip.getDisplayName()).
                                append(Component.literal(" 强化至" + "+" + data.getInt("Forging")).withStyle(ChatFormatting.WHITE)));
                    Compute.sendFormatMSG(player, Component.literal("强化").withStyle(ChatFormatting.AQUA),
                            Component.literal("强化成功!").withStyle(ChatFormatting.AQUA));
                    Compute.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
                }
            }

            Compute.forgingHoverName(equip);
            blockEntity.itemStackHandler.setStackInSlot(2, equip);
            blockEntity.itemStackHandler.extractItem(0, 1, false);
            blockEntity.itemStackHandler.extractItem(1, 1, false);
            if (enhancePaper.getItem() instanceof ForgeEnhance) {
                if (items.contains(slot3Item.getItem())) blockEntity.itemStackHandler.extractItem(3, 1, false);
                else if (items.contains(slot4Item.getItem())) blockEntity.itemStackHandler.extractItem(4, 1, false);
            }
            if (useProtect && protect.getItem() instanceof ForgeProtect) {
                if (slot3Item.is(ModItems.ForgeProtect.get())) blockEntity.itemStackHandler.extractItem(3, 1, false);
                else if (slot4Item.is(ModItems.ForgeProtect.get()))
                    blockEntity.itemStackHandler.extractItem(4, 1, false);
            }
        }
        if (hasRecipe8(blockEntity)) {
            ItemStack Ointment = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack Equip = blockEntity.itemStackHandler.getStackInSlot(1);
            CompoundTag data = Equip.getOrCreateTagElement(Utils.MOD_ID);
            if (!data.contains("SIndex")) data.putInt("SIndex", 1);
            String Slot = "#Slot#" + data.getInt("SIndex");
            String SlotName[] = {
                    Slot + "#SunPower",
                    Slot + "#LakePower",
                    Slot + "#VolcanoPower",
                    Slot + "#SnowPower",
                    Slot + "#SkyPower",
                    Slot + "#ManaPower",
                    Slot + "#NetherPower",
            };
            for (int i = 0; i < 7; i++) {
                data.remove(SlotName[i]);
            }
            Random r = new Random();
            if (Ointment.is(ModItems.SunOintment0.get()) || Ointment.is(ModItems.SunOintment1.get()) || Ointment.is(ModItems.SunOintment2.get())) {
                String SlotPName = Slot + "#SunPower";
                if (Ointment.is(ModItems.SunOintment0.get())) {
                    data.putDouble(SlotPName, r.nextDouble(0, 7.5f));
                } else if (Ointment.is(ModItems.SunOintment1.get())) {
                    double num = r.nextDouble(2.5d, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                } else if (Ointment.is(ModItems.SunOintment2.get())) {
                    double num = r.nextDouble(5d, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.LakeOintment0.get()) || Ointment.is(ModItems.LakeOintment1.get()) || Ointment.is(ModItems.LakeOintment2.get())) {
                String SlotPName = Slot + "#LakePower";
                if (Ointment.is(ModItems.LakeOintment0.get())) {
                    data.putDouble(SlotPName, r.nextDouble(0, 7.5f));
                } else if (Ointment.is(ModItems.LakeOintment1.get())) {
                    double num = r.nextDouble(2.5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                } else if (Ointment.is(ModItems.LakeOintment2.get())) {
                    double num = r.nextDouble(5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.VolcanoOintment0.get()) || Ointment.is(ModItems.VolcanoOintment1.get()) || Ointment.is(ModItems.VolcanoOintment2.get())) {
                String SlotPName = Slot + "#VolcanoPower";
                if (Ointment.is(ModItems.VolcanoOintment0.get())) {
                    data.putDouble(SlotPName, r.nextDouble(0, 7.5f));
                } else if (Ointment.is(ModItems.VolcanoOintment1.get())) {
                    double num = r.nextDouble(2.5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                } else if (Ointment.is(ModItems.VolcanoOintment2.get())) {
                    double num = r.nextDouble(5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.SnowOintment0.get()) || Ointment.is(ModItems.SnowOintment1.get()) || Ointment.is(ModItems.SnowOintment2.get())) {
                String SlotPName = Slot + "#SnowPower";
                if (Ointment.is(ModItems.SnowOintment0.get())) {
                    data.putDouble(SlotPName, r.nextDouble(0, 7.5f));
                } else if (Ointment.is(ModItems.SnowOintment1.get())) {
                    double num = r.nextDouble(2.5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                } else if (Ointment.is(ModItems.SnowOintment2.get())) {
                    double num = r.nextDouble(5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.SkyOintment0.get()) || Ointment.is(ModItems.SkyOintment1.get()) || Ointment.is(ModItems.SkyOintment2.get())) {
                String SlotPName = Slot + "#SkyPower";
                if (Ointment.is(ModItems.SkyOintment0.get())) {
                    data.putDouble(SlotPName, r.nextDouble(0, 7.5f));
                } else if (Ointment.is(ModItems.SkyOintment1.get())) {
                    double num = r.nextDouble(2.5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                } else if (Ointment.is(ModItems.SkyOintment2.get())) {
                    double num = r.nextDouble(5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.ManaOintment0.get()) || Ointment.is(ModItems.ManaOintment1.get()) || Ointment.is(ModItems.ManaOintment2.get())) {
                String SlotPName = Slot + "#ManaPower";
                if (Ointment.is(ModItems.ManaOintment0.get())) {
                    data.putDouble(SlotPName, r.nextDouble(0, 7.5f));
                } else if (Ointment.is(ModItems.ManaOintment1.get())) {
                    double num = r.nextDouble(2.5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                } else if (Ointment.is(ModItems.ManaOintment2.get())) {
                    double num = r.nextDouble(5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.NetherOintment0.get()) || Ointment.is(ModItems.NetherOintment1.get()) || Ointment.is(ModItems.NetherOintment2.get())) {
                String SlotPName = Slot + "#NetherPower";
                if (Ointment.is(ModItems.NetherOintment0.get())) {
                    data.putDouble(SlotPName, r.nextDouble(0, 7.5f));
                } else if (Ointment.is(ModItems.NetherOintment1.get())) {
                    double num = r.nextDouble(2.5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                } else if (Ointment.is(ModItems.NetherOintment2.get())) {
                    double num = r.nextDouble(5f, 10d);
                    data.putDouble(SlotPName, num);
                    if (num >= 9 && data.contains(InventoryCheck.owner))
                        Compute.formatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                                Component.literal(data.getString(InventoryCheck.owner) + "使用").withStyle(ChatFormatting.WHITE).
                                        append(Ointment.getDisplayName()).
                                        append(Component.literal("在")).
                                        append(Equip.getDisplayName()).
                                        append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (data.getInt("SIndex") == 5) data.putInt("SIndex", 1);
            else data.putInt("SIndex", data.getInt("SIndex") + 1);
            blockEntity.itemStackHandler.setStackInSlot(2, Equip);
            blockEntity.itemStackHandler.extractItem(0, 1, false);
            blockEntity.itemStackHandler.extractItem(1, 1, false);
        }

        if (hasRecipeOfEquipPiece(blockEntity)) {
            ItemStack materialStack = blockEntity.itemStackHandler.getStackInSlot(0);
            Item material = materialStack.getItem();
            int materialTier = ForgeEquipUtils.getEquipPieceTier(material);
            Item productItem = ForgeEquipUtils.getEquipPiece(materialTier + 1);
            ItemStack productSlotStack = blockEntity.itemStackHandler.getStackInSlot(2);
            int productCount = productSlotStack.getCount();
            if (productSlotStack.getCount() == 0) productSlotStack = new ItemStack(productItem);
            else productSlotStack.setCount(productCount + 1);

            blockEntity.itemStackHandler.setStackInSlot(2, productSlotStack);
            blockEntity.itemStackHandler.extractItem(0, 4, false);
            if (player != null) Compute.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
        }

        if (hasRecipeOfEquipPieceForge(blockEntity)) {
            ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(1);
            int equipTier = ForgeEquipUtils.getForgeQualityOnEquip(equip);
            ForgeEquipUtils.setForgeQualityOnEquip(equip, equipTier + 1);

            if (player != null) {
                Compute.sendFormatMSG(player, Component.literal("锻造").withStyle(ChatFormatting.AQUA),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 将").withStyle(ChatFormatting.WHITE)).
                                append(equip.getDisplayName()).
                                append(Component.literal(" 提升至 ").withStyle(ChatFormatting.WHITE)).
                                append(ForgeEquipUtils.description.get(equipTier + 1)).
                                append(Component.literal(" 品质").withStyle(ChatFormatting.AQUA)));
            }
            blockEntity.itemStackHandler.setStackInSlot(2, equip);
            blockEntity.itemStackHandler.extractItem(0, 4, false);
            blockEntity.itemStackHandler.extractItem(1, 1, false);
            if (player != null) Compute.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
        }
    }

    private static boolean hasRecipeOfDismantle(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack gem = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(4);

        boolean hasDismantleInFirstSlot = blockEntity.itemStackHandler.getStackInSlot(3).getItem() instanceof Dismantle;

        if (sword.getTagElement(Utils.MOD_ID) == null) return false;

        CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
        if (data.getInt("newMaxSlot") == 0 || data.getInt("newMaxSlot") == data.getInt("newSlot")) return false;
        return hasDismantleInFirstSlot && gem.is(Items.AIR);
    }

    private static boolean hasRecipe(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack gem = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(1);
        boolean hasGemInFirstSlot = Utils.gemsTag.containsKey(gem.getItem());
        boolean hasSwordBowSceptreInSecondSlot = (Utils.mainHandTag.containsKey(equip.getItem()) ||
                Utils.armorTag.containsKey(equip.getItem()));

        if (equip.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (data.getInt("newSlot") == 0) return false;
        }

        boolean correctType = (!gem.getItem().equals(GemItems.castleWeaponGem.get()) || Utils.mainHandTag.containsKey(equip.getItem()))
                && (!gem.getItem().equals(GemItems.castleArmorGem.get()) || Utils.armorTag.containsKey(equip.getItem()));
        return hasGemInFirstSlot && canInsertItemIntoOutPutSlot(inventory) && hasSwordBowSceptreInSecondSlot &&
                inventory.getItem(2).isEmpty() && correctType;
    }   //镶嵌

    private static boolean hasRecipe1(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }

        boolean hasOpenSlotInFirstSlot = blockEntity.itemStackHandler.getStackInSlot(3).getItem() instanceof SlotOpen;

        ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(4);

        boolean hasSwordBowSceptreInSecondSlot = (Utils.mainHandTag.containsKey(equip.getItem()) ||
                Utils.armorTag.containsKey(equip.getItem()));

        if (equip.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains("newMaxSlot") && data.getInt("newMaxSlot") == 3) return false;
            if (Utils.armorTag.containsKey(equip.getItem()) && data.contains("newMaxSlot") && data.getInt("newMaxSlot") == 1)
                return false;
        }

        return hasOpenSlotInFirstSlot && canInsertItemIntoOutPutSlot(inventory) && hasSwordBowSceptreInSecondSlot &&
                inventory.getItem(2).isEmpty() && !Compute.IsSoulEquip(equip);

    }   //开孔

    private static boolean hasRecipe2(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }

        Item forgeStone = blockEntity.itemStackHandler.getStackInSlot(0).getItem();
        boolean hasGemInFirstSlot = (forgeStone.equals(ModItems.ForgingStone0.get())
                || forgeStone.equals(ModItems.ForgingStone1.get())
                || forgeStone.equals(ModItems.ForgingStone2.get())
                || forgeStone.equals(ModItems.worldForgeStone.get()));

        boolean hasSwordInSecondSlot = false;
        ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(1);
        if (equip.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (data.contains("Forging") && data.getInt("Forging") >= 32) return false;
        }

        if ((Utils.mainHandTag.containsKey(equip.getItem())) || (Utils.armorTag.containsKey(equip.getItem()))) {
            hasSwordInSecondSlot = true;
        }

        return hasSwordInSecondSlot && canInsertItemIntoOutPutSlot(inventory) && hasGemInFirstSlot &&
                inventory.getItem(2).isEmpty() && !Compute.IsSoulEquip(equip);
    }   //强化

    private static boolean hasRecipe8(ForgingBlockEntity blockEntity) { //涂附
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack Ointment = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack Equip = blockEntity.itemStackHandler.getStackInSlot(1);
        boolean OintmentCorrect = Ointment.is(ModItems.SunOintment0.get()) || Ointment.is(ModItems.SunOintment1.get()) || Ointment.is(ModItems.SunOintment2.get())
                || Ointment.is(ModItems.LakeOintment0.get()) || Ointment.is(ModItems.LakeOintment1.get()) || Ointment.is(ModItems.LakeOintment2.get())
                || Ointment.is(ModItems.VolcanoOintment0.get()) || Ointment.is(ModItems.VolcanoOintment1.get()) || Ointment.is(ModItems.VolcanoOintment2.get())
                || Ointment.is(ModItems.SnowOintment0.get()) || Ointment.is(ModItems.SnowOintment1.get()) || Ointment.is(ModItems.SnowOintment2.get())
                || Ointment.is(ModItems.SkyOintment0.get()) || Ointment.is(ModItems.SkyOintment1.get()) || Ointment.is(ModItems.SkyOintment2.get())
                || Ointment.is(ModItems.ManaOintment0.get()) || Ointment.is(ModItems.ManaOintment1.get()) || Ointment.is(ModItems.ManaOintment2.get())
                || Ointment.is(ModItems.NetherOintment0.get()) || Ointment.is(ModItems.NetherOintment1.get()) || Ointment.is(ModItems.NetherOintment2.get());
        boolean EquipCorrect = Equip.is(ModItems.SBoots.get()) || Equip.is(ModItems.SLeggings.get())
                || Equip.is(ModItems.SChest.get()) || Equip.is(ModItems.SHelmet.get())
                || Equip.is(ModItems.ISArmorBoots.get()) || Equip.is(ModItems.ISArmorLeggings.get())
                || Equip.is(ModItems.ISArmorChest.get()) || Equip.is(ModItems.ISArmorHelmet.get());
        return OintmentCorrect && EquipCorrect && canInsertItemIntoOutPutSlot(inventory);
    }

    private static boolean hasRecipeOfCastleArmor(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        return blockEntity.itemStackHandler.getStackInSlot(0).is(ModItems.CastleCrystal.get())
                && (blockEntity.itemStackHandler.getStackInSlot(1).getItem() instanceof CastleAttackArmor
                || blockEntity.itemStackHandler.getStackInSlot(1).getItem() instanceof CastleSwiftArmor
                || blockEntity.itemStackHandler.getStackInSlot(1).getItem() instanceof CastleManaArmor);
    }

    private static boolean hasRecipeOfForgePaper(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(4);
        CompoundTag data = null;
        if (equip.getTagElement(Utils.MOD_ID) != null) data = equip.getTagElement(Utils.MOD_ID);
        else return false;

        boolean canUseQingMingForgePaper = !data.contains(StringUtils.QingMingForgePaper) && blockEntity.itemStackHandler.getStackInSlot(3).is(ModItems.QingMingForgePaper.get());
        boolean canUseLabourDayForgePaper = !data.contains(StringUtils.LabourDayForgePaper) && blockEntity.itemStackHandler.getStackInSlot(3).is(ModItems.LabourDayForgePaper.get());

        boolean hasEquipCanBeForged = (Utils.mainHandTag.containsKey(equip.getItem()) ||
                Utils.armorTag.containsKey(equip.getItem()));

        return (canUseQingMingForgePaper || canUseLabourDayForgePaper) && data.contains("Forging")
                && canInsertItemIntoOutPutSlot(inventory) && hasEquipCanBeForged &&
                inventory.getItem(2).isEmpty() && !Compute.IsSoulEquip(equip);
    }

    private static boolean hasRecipeOfEquipPiece(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack equipPieceStack = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack equipPiece0Stack = blockEntity.itemStackHandler.getStackInSlot(1);
        Item equipPiece = equipPieceStack.getItem();
        ItemStack product = blockEntity.itemStackHandler.getStackInSlot(2);

        int materialTier = ForgeEquipUtils.getEquipPieceTier(equipPiece);
        boolean hasNextTierAndCountIsEnough = equipPieceStack.getCount() >= 4
                && materialTier < ForgeEquipUtils.getEquipPieceList().size() - 1;
        Item nextTirePiece = ForgeEquipUtils.getEquipPiece(materialTier + 1);
        boolean canInsertIntoProductSlot = (product.is(nextTirePiece) || product.is(Items.AIR)) && product.getCount() < 64;
        return hasNextTierAndCountIsEnough && canInsertIntoProductSlot && equipPiece0Stack.is(ModItems.equipPiece0.get());
    }

    private static boolean hasRecipeOfEquipPieceForge(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack equipPieceStack = blockEntity.itemStackHandler.getStackInSlot(0);
        int pieceTier = ForgeEquipUtils.getEquipPieceTier(equipPieceStack.getItem());
        ItemStack equipStack = blockEntity.itemStackHandler.getStackInSlot(1);

        if (equipStack.getTagElement(Utils.MOD_ID) == null) return false;
        CompoundTag tag = equipStack.getTagElement(Utils.MOD_ID);
        boolean canBeForged = tag != null && tag.contains(ForgeEquipUtils.itemTag);

        int equipTier = ForgeEquipUtils.getForgeQualityOnEquip(equipStack);
        ItemStack productSlot = blockEntity.itemStackHandler.getStackInSlot(2);
        return canBeForged && pieceTier - equipTier == 1 && productSlot.getCount() == 0 && equipPieceStack.getCount() >= 2;
    }

    private static boolean canInsertItemIntoOutPutSlot(SimpleContainer inventory) {
        return inventory.getItem(2).isEmpty();
    }

    public void craft() throws CommandSyntaxException {
        craftItem(this);
    }

    public boolean decompose() {
        ItemStack equip = this.itemStackHandler.getStackInSlot(2);

        if (ForgeEquipUtils.itemContainForgeQuality(equip) && equip.getItem() instanceof RandomLootEquip) {
            int tier = ForgeEquipUtils.getForgeQualityOnEquip(equip);
            ItemStack piece = ForgeEquipUtils.getEquipPiece(tier).getDefaultInstance();
            this.itemStackHandler.setStackInSlot(2, piece);
            return true;
        }

        if (equip.is(ModItems.plainNecklace.get())) {
            this.itemStackHandler.setStackInSlot(2, new ItemStack(ModItems.PlainBossSoul.get()));
            return true;
        }
        if (equip.is(ModItems.lavenderBracelet.get())) {
            this.itemStackHandler.setStackInSlot(2, new ItemStack(ModItems.PurpleIronPiece.get(), 8));
            return true;
        }
        if (equip.is(ModItems.netherHand.get())) {
            this.itemStackHandler.setStackInSlot(2, new ItemStack(ModItems.NetherQuartz.get(), 8));
            return true;
        }
        if (equip.is(ModItems.iceBelt.get())) {
            this.itemStackHandler.setStackInSlot(2, new ItemStack(ModItems.IceSoul.get()));
            return true;
        }
        if (equip.is(ModItems.CastleNecklace.get())) {
            this.itemStackHandler.setStackInSlot(2, new ItemStack(ModItems.CastlePiece.get()));
            return true;
        }
        if (equip.getItem() instanceof WraqUniformCurios) {
            this.itemStackHandler.setStackInSlot(2, new ItemStack(ModItems.uniformPiece.get()));
            return true;
        }
        if (equip.getItem() instanceof RuneItem) {
            this.itemStackHandler.setStackInSlot(2, new ItemStack(NewRuneItems.emptyRune.get()));
            return true;
        }
        return false;
    }

    public ItemStackHandler getItemStackHandler() {
        return this.itemStackHandler;
    }
}

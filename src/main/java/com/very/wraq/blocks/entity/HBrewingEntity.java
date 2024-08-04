package com.very.wraq.blocks.entity;


import com.very.wraq.blocks.blocks.BrewingRecipe;
import com.very.wraq.blocks.brewing.BrewingNote;
import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.process.series.potion.NewThrowablePotion;
import com.very.wraq.render.gui.blocks.BrewingMenu;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ModItems;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HBrewingEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(11) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 40;

    public HBrewingEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.HBrewing_BLOCK_ENTITY.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> HBrewingEntity.this.progress;
                    case 1 -> HBrewingEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> HBrewingEntity.this.progress = value;
                    case 1 -> HBrewingEntity.this.maxProgress = value;
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
        return Component.literal("维瑞阿契酿造台");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BrewingMenu(id, inventory, this, this.data);
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

    public static void tick(Level level, BlockPos pos, BlockState blockState, HBrewingEntity blockEntity) {
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
                || hasRecipe3(blockEntity) || hasRecipe4(blockEntity) || hasRecipe5(blockEntity)
                || hasRecipeOfSplash(blockEntity)) {
            blockEntity.progress++;
            setChanged(level, pos, blockState);
            if (blockEntity.progress >= blockEntity.maxProgress) {
                craftItem(blockEntity);
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

    protected static void craftItem(HBrewingEntity blockEntity) {

        String PlayerName = Utils.whoIsUsingBlock.get(blockEntity.getBlockPos());
        Player player = null;
        if (blockEntity.level.getServer().getPlayerList().getPlayerByName(PlayerName) != null)
            player = blockEntity.level.getServer().getPlayerList().getPlayerByName(PlayerName);

        if (hasRecipe(blockEntity)) { //基础酿造
            ItemStack material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack material2 = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack potion = blockEntity.itemStackHandler.getStackInSlot(6);
            ItemStack brewingNote = blockEntity.itemStackHandler.getStackInSlot(7);
            CompoundTag data = brewingNote.getOrCreateTagElement(Utils.MOD_ID);
            List<ItemStack> purifiedWater = new ArrayList<>();
            for (int i = 2; i <= 5; i++) purifiedWater.add(blockEntity.itemStackHandler.getStackInSlot(i));
            int purifiedWaterCount = 0;
            for (ItemStack itemStack : purifiedWater)
                if (itemStack.is(ModItems.PurifiedWater.get())) purifiedWaterCount++;
            BrewingRecipe brewingRecipe = BrewingRecipe.getRecipe(new ItemStack(ModItems.PurifiedWater.get()), material1, material2);
            if (brewingRecipe == null) return;
            ItemStack output = brewingRecipe.output;
            if (output == null) return;
            BrewingNote.addExp(brewingNote, material1.getItem(), purifiedWaterCount);
            BrewingNote.addExp(brewingNote, material2.getItem(), purifiedWaterCount);
            if (player != null) InventoryCheck.addOwnerTagToItemStack(player, brewingNote);
            blockEntity.itemStackHandler.setStackInSlot(6, new ItemStack(output.getItem(), potion.getCount() + purifiedWaterCount));

            int BrewingLevel = Compute.BrewingLevel(brewingNote);
            boolean flag = false;
            if (player != null) {
                flag = Compute.BrewingLevelReward(player, BrewingLevel, data);
                CompoundTag playerTag = player.getPersistentData();
                playerTag.putInt("BrewingLevel", Compute.BrewingLevel(brewingNote));
            }

            if (!flag) {
                blockEntity.itemStackHandler.extractItem(0, brewingRecipe.material1.getCount(), false);
                blockEntity.itemStackHandler.extractItem(1, brewingRecipe.material2.getCount(), false);
            }

            for (int i = 2; i <= 5; i++) {
                if (blockEntity.itemStackHandler.getStackInSlot(i).is(ModItems.PurifiedWater.get()))
                    blockEntity.itemStackHandler.extractItem(i, 1, false);
            }
            blockEntity.resetProgress();
        }
        if (hasRecipe1(blockEntity)) { //水质净化
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack purifiedWater = blockEntity.itemStackHandler.getStackInSlot(6);
            int waterBottleCount = 0;
            for (int i = 2; i <= 5; i++) {
                if (blockEntity.itemStackHandler.getStackInSlot(i).is(ModItems.WaterBottle.get())) {
                    blockEntity.itemStackHandler.extractItem(i, 1, false);
                    waterBottleCount++;
                }
            }

            ItemStack itemStack = ModItems.PurifiedWater.get().getDefaultInstance();
            itemStack.setCount(waterBottleCount + purifiedWater.getCount());
            blockEntity.itemStackHandler.setStackInSlot(6, itemStack);

            if (Material1.is(ModItems.Purifier.get())) blockEntity.itemStackHandler.extractItem(0, 1, false);
            else blockEntity.itemStackHandler.extractItem(1, 1, false);
            blockEntity.resetProgress();
        }
        if (hasRecipe2(blockEntity)) { // 延长药水时效
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack brewedPotion = blockEntity.itemStackHandler.getStackInSlot(6);
            ItemStack potion = null;
            int potionCount = 0;
            for (int i = 2; i <= 5; i++) {
                ItemStack tempPotion = blockEntity.itemStackHandler.getStackInSlot(i);
                if (!tempPotion.isEmpty()) {
                    potionCount++;
                    if (potion == null) potion = tempPotion;
                    else {
                        if (!potion.is(tempPotion.getItem())) return;
                    }
                }
            }
            if (BrewingRecipe.basicToLongMap.isEmpty()) BrewingRecipe.setBasicToLongMap();
            if (potion == null) return;
            Item outputItem = BrewingRecipe.getLongPotionItem(potion.getItem());
            if (outputItem == null) return;
            ItemStack output = new ItemStack(outputItem, brewedPotion.getCount() + potionCount);
            if (potion.getItem() instanceof NewThrowablePotion newThrowablePotion) {
                CompoundTag data = output.getOrCreateTag();
                data.putString("Potion", "vmd:" + newThrowablePotion.getType());
            }
            blockEntity.itemStackHandler.setStackInSlot(6, output);
            for (int i = 2; i <= 5; i++) blockEntity.itemStackHandler.extractItem(i, 1, false);
            if (Material1.is(ModItems.Stabilizer.get())) blockEntity.itemStackHandler.extractItem(0, 1, false);
            else blockEntity.itemStackHandler.extractItem(1, 1, false);
            blockEntity.resetProgress();
        }

        if (hasRecipeOfSplash(blockEntity)) { // 喷溅
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack brewedPotion = blockEntity.itemStackHandler.getStackInSlot(6);
            ItemStack potion = null;
            int potionCount = 0;
            for (int i = 2; i <= 5; i++) {
                ItemStack tempPotion = blockEntity.itemStackHandler.getStackInSlot(i);
                if (!tempPotion.isEmpty()) {
                    potionCount++;
                    if (potion == null) potion = tempPotion;
                    else {
                        if (!potion.is(tempPotion.getItem())) return;
                    }
                }
            }
            if (potion == null) return;
            Item splashPotion = NewThrowablePotion.getSplashPotion(potion.getItem());
            ItemStack output = new ItemStack(splashPotion, brewedPotion.getCount() + potionCount);
            if (splashPotion instanceof NewThrowablePotion newThrowablePotion) {
                CompoundTag data = output.getOrCreateTag();
                data.putString("Potion", "vmd:long_" + newThrowablePotion.getType());
            }
            blockEntity.itemStackHandler.setStackInSlot(6, output);
            for (int i = 2; i <= 5; i++) blockEntity.itemStackHandler.extractItem(i, 1, false);
            if (Material1.is(ModItems.Splasher.get())) blockEntity.itemStackHandler.extractItem(0, 1, false);
            else blockEntity.itemStackHandler.extractItem(1, 1, false);
            blockEntity.resetProgress();
        }

        if (hasRecipe3(blockEntity)) { //固化剂
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack NormalSoul1 = blockEntity.itemStackHandler.getStackInSlot(2);
            ItemStack NormalSoul2 = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack NormalSoul3 = blockEntity.itemStackHandler.getStackInSlot(4);
            ItemStack NormalSoul4 = blockEntity.itemStackHandler.getStackInSlot(5);
            ItemStack SolidifiedSlot = blockEntity.itemStackHandler.getStackInSlot(6);
            int Count = 0;
            ItemStack Solidified_Soul = Items.AIR.getDefaultInstance();
            if (!NormalSoul1.isEmpty()) {
                Count++;
                Solidified_Soul = Utils.BrewSoulMap.get(NormalSoul1.getItem()).getDefaultInstance();
            }
            if (!NormalSoul2.isEmpty()) {
                Count++;
                Solidified_Soul = Utils.BrewSoulMap.get(NormalSoul2.getItem()).getDefaultInstance();
            }
            if (!NormalSoul3.isEmpty()) {
                Count++;
                Solidified_Soul = Utils.BrewSoulMap.get(NormalSoul3.getItem()).getDefaultInstance();
            }
            if (!NormalSoul4.isEmpty()) {
                Count++;
                Solidified_Soul = Utils.BrewSoulMap.get(NormalSoul4.getItem()).getDefaultInstance();
            }
            Solidified_Soul.setCount(SolidifiedSlot.getCount() + Count);
            Solidified_Soul.getOrCreateTagElement(Utils.MOD_ID);
            blockEntity.itemStackHandler.setStackInSlot(6, Solidified_Soul);
            blockEntity.itemStackHandler.extractItem(2, 1, false);
            blockEntity.itemStackHandler.extractItem(3, 1, false);
            blockEntity.itemStackHandler.extractItem(4, 1, false);
            blockEntity.itemStackHandler.extractItem(5, 1, false);
            if (Material1.is(ModItems.Solidifier.get())) blockEntity.itemStackHandler.extractItem(0, 1, false);
            else blockEntity.itemStackHandler.extractItem(1, 1, false);
            blockEntity.resetProgress();
        }
        if (hasRecipe4(blockEntity)) { // 浓缩
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack brewedPotion = blockEntity.itemStackHandler.getStackInSlot(6);
            ItemStack potion = null;
            int potionCount = 0;
            for (int i = 2; i <= 5; i++) {
                ItemStack tempPotion = blockEntity.itemStackHandler.getStackInSlot(i);
                if (!tempPotion.isEmpty()) {
                    potionCount++;
                    if (potion == null) potion = tempPotion;
                    else {
                        if (!potion.is(tempPotion.getItem())) return;
                    }
                }
            }
            if (BrewingRecipe.basicToConMap.isEmpty()) BrewingRecipe.setBasicToConMap();
            if (potion == null) return;
            Item outputItem = BrewingRecipe.getConPotionItem(potion.getItem());
            if (outputItem == null) return;
            ItemStack output = new ItemStack(outputItem, brewedPotion.getCount() + potionCount);
            if (potion.getItem() instanceof NewThrowablePotion newThrowablePotion) {
                CompoundTag data = output.getOrCreateTag();
                data.putString("Potion", "vmd:con_" + newThrowablePotion.getType());
            }
            blockEntity.itemStackHandler.setStackInSlot(6, output);
            for (int i = 2; i <= 5; i++) blockEntity.itemStackHandler.extractItem(i, 1, false);
            if (Material1.is(ModItems.Concentrater.get())) blockEntity.itemStackHandler.extractItem(0, 1, false);
            else blockEntity.itemStackHandler.extractItem(1, 1, false);
            blockEntity.resetProgress();
        }
        if (hasRecipe5(blockEntity)) { // 涂膏
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack BrewingNote = blockEntity.itemStackHandler.getStackInSlot(7);
            int Count = 0;
            if (blockEntity.itemStackHandler.getStackInSlot(2).is(ModItems.PurifiedWater.get())) Count++;
            if (blockEntity.itemStackHandler.getStackInSlot(3).is(ModItems.PurifiedWater.get())) Count++;
            if (blockEntity.itemStackHandler.getStackInSlot(4).is(ModItems.PurifiedWater.get())) Count++;
            if (blockEntity.itemStackHandler.getStackInSlot(5).is(ModItems.PurifiedWater.get())) Count++;
            int BrewingLevel = Compute.BrewingLevel(BrewingNote);
            int Rate1 = BrewingLevel;
            Random random = new Random();
            blockEntity.itemStackHandler.extractItem(2, 1, false);
            blockEntity.itemStackHandler.extractItem(3, 1, false);
            blockEntity.itemStackHandler.extractItem(4, 1, false);
            blockEntity.itemStackHandler.extractItem(5, 1, false);
            if (Material1.is(ModItems.SunPower.get()) || Material2.is(ModItems.SunPower.get())) {
                if (Material1.is(ModItems.SunPower.get())) {
                    blockEntity.itemStackHandler.extractItem(0, 6, false);
                    blockEntity.itemStackHandler.extractItem(1, 1, false);
                } else {
                    blockEntity.itemStackHandler.extractItem(0, 1, false);
                    blockEntity.itemStackHandler.extractItem(1, 6, false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = ModItems.SunOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                } else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = ModItems.SunOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    } else {
                        ItemStack itemStack = ModItems.SunOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    }
                }
            }
            if (Material1.is(ModItems.LakeCore.get()) || Material2.is(ModItems.LakeCore.get())) {
                if (Material1.is(ModItems.LakeCore.get())) {
                    blockEntity.itemStackHandler.extractItem(0, 6, false);
                    blockEntity.itemStackHandler.extractItem(1, 1, false);
                } else {
                    blockEntity.itemStackHandler.extractItem(0, 1, false);
                    blockEntity.itemStackHandler.extractItem(1, 6, false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = ModItems.LakeOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                } else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = ModItems.LakeOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    } else {
                        ItemStack itemStack = ModItems.LakeOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    }
                }
            }
            if (Material1.is(ModItems.VolcanoCore.get()) || Material2.is(ModItems.VolcanoCore.get())) {
                if (Material1.is(ModItems.VolcanoCore.get())) {
                    blockEntity.itemStackHandler.extractItem(0, 6, false);
                    blockEntity.itemStackHandler.extractItem(1, 1, false);
                } else {
                    blockEntity.itemStackHandler.extractItem(0, 1, false);
                    blockEntity.itemStackHandler.extractItem(1, 6, false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = ModItems.VolcanoOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                } else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = ModItems.VolcanoOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    } else {
                        ItemStack itemStack = ModItems.VolcanoOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    }
                }
            }
            if (Material1.is(ModItems.SnowRune.get()) || Material2.is(ModItems.SnowRune.get())) {
                if (Material1.is(ModItems.SnowRune.get())) {
                    blockEntity.itemStackHandler.extractItem(0, 2, false);
                    blockEntity.itemStackHandler.extractItem(1, 1, false);
                } else {
                    blockEntity.itemStackHandler.extractItem(0, 1, false);
                    blockEntity.itemStackHandler.extractItem(1, 2, false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = ModItems.SnowOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                } else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = ModItems.SnowOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    } else {
                        ItemStack itemStack = ModItems.SnowOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    }
                }
            }
            if (Material1.is(ModItems.SkyRune.get()) || Material2.is(ModItems.SkyRune.get())) {
                if (Material1.is(ModItems.SkyRune.get())) {
                    blockEntity.itemStackHandler.extractItem(0, 3, false);
                    blockEntity.itemStackHandler.extractItem(1, 1, false);
                } else {
                    blockEntity.itemStackHandler.extractItem(0, 1, false);
                    blockEntity.itemStackHandler.extractItem(1, 3, false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = ModItems.SkyOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                } else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = ModItems.SkyOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    } else {
                        ItemStack itemStack = ModItems.SkyOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    }
                }
            }
            if (Material1.is(ModItems.EvokerRune.get()) || Material2.is(ModItems.EvokerRune.get())) {
                if (Material1.is(ModItems.EvokerRune.get())) {
                    blockEntity.itemStackHandler.extractItem(0, 1, false);
                    blockEntity.itemStackHandler.extractItem(1, 1, false);
                } else {
                    blockEntity.itemStackHandler.extractItem(0, 1, false);
                    blockEntity.itemStackHandler.extractItem(1, 1, false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = ModItems.ManaOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                } else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = ModItems.ManaOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    } else {
                        ItemStack itemStack = ModItems.ManaOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    }
                }
            }
            if (Material1.is(ModItems.Ruby.get()) || Material2.is(ModItems.Ruby.get())) {
                if (Material1.is(ModItems.Ruby.get())) {
                    blockEntity.itemStackHandler.extractItem(0, 64, false);
                    blockEntity.itemStackHandler.extractItem(1, 1, false);
                } else {
                    blockEntity.itemStackHandler.extractItem(0, 1, false);
                    blockEntity.itemStackHandler.extractItem(1, 64, false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = ModItems.NetherOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                } else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = ModItems.NetherOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    } else {
                        ItemStack itemStack = ModItems.NetherOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6, itemStack);
                    }
                }
            }
        }
    }

    private static boolean hasRecipe(HBrewingEntity blockEntity) { // 基础酿造
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack material1 = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack material2 = blockEntity.itemStackHandler.getStackInSlot(1);
        ItemStack brewedPotion = blockEntity.itemStackHandler.getStackInSlot(6);
        ItemStack brewingNote = blockEntity.itemStackHandler.getStackInSlot(7);

        List<ItemStack> purifiedWater = new ArrayList<>();
        for (int i = 2; i <= 5; i++) purifiedWater.add(blockEntity.itemStackHandler.getStackInSlot(i));
        int purifiedWaterCount = 0;
        for (ItemStack itemStack : purifiedWater) if (itemStack.is(ModItems.PurifiedWater.get())) purifiedWaterCount++;
        boolean hasPurifiedWater = purifiedWaterCount > 0;

        ItemStack output = BrewingRecipe.getOutput(new ItemStack(ModItems.PurifiedWater.get()), material1, material2);
        if (BrewingRecipe.outputNeedBrewingLevelMap.isEmpty()) BrewingRecipe.setOutputNeedBrewingLevelMap();
        if (output == null) return false;
        if (BrewingRecipe.outputNeedBrewingLevelMap.containsKey(output.getItem())
                && Compute.BrewingLevel(brewingNote) < BrewingRecipe.outputNeedBrewingLevelMap.get(output.getItem()))
            return false;

        boolean canInsertIntoBrewedSlot = brewedPotion.isEmpty() ||
                (brewedPotion.is(output.getItem()) && brewedPotion.getCount() + purifiedWaterCount <= 64);
        boolean brewingNoteSlotIsNoEmpty = brewingNote.is(ModItems.BrewingNote.get());
        return hasPurifiedWater && canInsertIntoBrewedSlot && brewingNoteSlotIsNoEmpty;
    }

    private static boolean hasRecipe1(HBrewingEntity blockEntity) { // 水质净化
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
        List<ItemStack> list = new ArrayList<>();
        for (int i = 2; i <= 5; i++) list.add(blockEntity.itemStackHandler.getStackInSlot(i));
        int waterBottleCount = 0;
        for (ItemStack itemStack : list) if (itemStack.is(ModItems.WaterBottle.get())) ++waterBottleCount;
        ItemStack BrewedPotion = blockEntity.itemStackHandler.getStackInSlot(6);
        ItemStack BrewingNote = blockEntity.itemStackHandler.getStackInSlot(7);
        boolean hasWaterBottle = waterBottleCount > 0;
        boolean HasPurifierInSlot = Material1.is(ModItems.Purifier.get()) || Material2.is(ModItems.Purifier.get());
        boolean canInsertIntoBrewedSlot = BrewedPotion.isEmpty()
                || (BrewedPotion.is(ModItems.PurifiedWater.get()) && BrewedPotion.getCount() + waterBottleCount <= 64);
        boolean BrewingNoteSlotIsNoEmpty = BrewingNote.is(ModItems.BrewingNote.get());
        return hasWaterBottle && HasPurifierInSlot && canInsertIntoBrewedSlot && BrewingNoteSlotIsNoEmpty;
    }

    private static boolean hasRecipe2(HBrewingEntity blockEntity) { // 药水时效延长
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
        ItemStack BrewedPotion = blockEntity.itemStackHandler.getStackInSlot(6);
        ItemStack BrewingNote = blockEntity.itemStackHandler.getStackInSlot(7);
        ItemStack potion = null;
        int potionCount = 0;
        for (int i = 2; i <= 5; i++) {
            ItemStack tempPotion = blockEntity.itemStackHandler.getStackInSlot(i);
            if (!tempPotion.isEmpty()) {
                potionCount++;
                if (potion == null) potion = tempPotion;
                else {
                    if (!potion.is(tempPotion.getItem())) return false;
                }
            }
        }
        if (potion == null) return false;
/*        if (BrewingRecipe.basicPotionList.isEmpty()) BrewingRecipe.setBasicPotionList();
        if (!BrewingRecipe.basicPotionList.contains(potion.getItem())) return false;*/
/*        if (BrewingRecipe.basicToLongMap.isEmpty()) BrewingRecipe.setBasicToLongMap();
        Item output = BrewingRecipe.basicToLongMap.get(potion.getItem());*/
        Item output = BrewingRecipe.getLongPotionItem(potion.getItem());
        if (output == null) return false;
        Utils.PotionMapInit();
        boolean HasStabilizerInSlot = (Material1.is(ModItems.Stabilizer.get()) && Material2.is(ModItems.Stabilizer.get()))
                || (Material1.is(ModItems.Stabilizer.get()) && Material2.isEmpty())
                || (Material1.isEmpty() && Material2.is(ModItems.Stabilizer.get()));
        boolean canInsertIntoBrewedSlot = BrewedPotion.isEmpty() || (BrewedPotion.is(output) && BrewedPotion.getCount() + potionCount <= 64);
        boolean BrewingNoteInSlot = BrewingNote.is(ModItems.BrewingNote.get());
        return HasStabilizerInSlot && canInsertIntoBrewedSlot && BrewingNoteInSlot;
    }

    private static boolean hasRecipe3(HBrewingEntity blockEntity) { // 固化根源
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
        ItemStack NormalSoul1 = blockEntity.itemStackHandler.getStackInSlot(2);
        ItemStack NormalSoul2 = blockEntity.itemStackHandler.getStackInSlot(3);
        ItemStack NormalSoul3 = blockEntity.itemStackHandler.getStackInSlot(4);
        ItemStack NormalSoul4 = blockEntity.itemStackHandler.getStackInSlot(5);
        ItemStack SolidifiedSoul = blockEntity.itemStackHandler.getStackInSlot(6);
        ItemStack BrewingNote = blockEntity.itemStackHandler.getStackInSlot(7);
        boolean IsSameSoul = true;
        if (!NormalSoul1.isEmpty()) {
            if (!NormalSoul2.isEmpty() && !NormalSoul2.equals(NormalSoul1, true)) IsSameSoul = false;
            if (!NormalSoul3.isEmpty() && !NormalSoul3.equals(NormalSoul1, true)) IsSameSoul = false;
            if (!NormalSoul4.isEmpty() && !NormalSoul4.equals(NormalSoul1, true)) IsSameSoul = false;
        } else {
            if (!NormalSoul2.isEmpty()) {
                if (!NormalSoul3.isEmpty() && !NormalSoul3.equals(NormalSoul2, true)) IsSameSoul = false;
                if (!NormalSoul4.isEmpty() && !NormalSoul4.equals(NormalSoul2, true)) IsSameSoul = false;
            } else {
                if (!NormalSoul3.isEmpty()) {
                    if (!NormalSoul4.isEmpty() && !NormalSoul4.equals(NormalSoul3, true)) IsSameSoul = false;
                } else if (NormalSoul4.isEmpty()) IsSameSoul = false;
            }
        }
        int Count = 0;
        Item NormalSoulItem = Items.AIR;
        if (!NormalSoul1.isEmpty()) {
            Count++;
            NormalSoulItem = NormalSoul1.getItem();
        }
        if (!NormalSoul2.isEmpty()) {
            Count++;
            NormalSoulItem = NormalSoul2.getItem();
        }
        if (!NormalSoul3.isEmpty()) {
            Count++;
            NormalSoulItem = NormalSoul3.getItem();
        }
        if (!NormalSoul4.isEmpty()) {
            Count++;
            NormalSoulItem = NormalSoul4.getItem();
        }
        boolean IsSolidifierInSlot = Material1.is(ModItems.Solidifier.get()) || Material2.is(ModItems.Solidifier.get());
        boolean SolidifiedSoulSlot = SolidifiedSoul.getCount() + Count <= SolidifiedSoul.getMaxStackSize();
        boolean IsSoulsInSlot = (Utils.BrewSoulMap.containsKey(NormalSoul1.getItem())) ||
                (Utils.BrewSoulMap.containsKey(NormalSoul2.getItem())) ||
                (Utils.BrewSoulMap.containsKey(NormalSoul3.getItem())) ||
                (Utils.BrewSoulMap.containsKey(NormalSoul4.getItem()));
        boolean IsBrewingBookInSlot = BrewingNote.is(ModItems.BrewingNote.get());
        boolean SolidifiedSlotSameNormalSlot = true;
        if (!SolidifiedSoul.isEmpty()) {
            if (Utils.BrewSoulMap.get(NormalSoulItem) == null) SolidifiedSlotSameNormalSlot = false;
            else {
                if (!SolidifiedSoul.equals(Utils.BrewSoulMap.get(NormalSoulItem).getDefaultInstance(), true))
                    SolidifiedSlotSameNormalSlot = false;
            }
        }
        return IsSameSoul && IsSolidifierInSlot && SolidifiedSoulSlot && IsBrewingBookInSlot && IsSoulsInSlot && SolidifiedSlotSameNormalSlot;
    }

    private static boolean hasRecipe4(HBrewingEntity blockEntity) { // 浓缩
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
        ItemStack BrewedPotion = blockEntity.itemStackHandler.getStackInSlot(6);
        ItemStack BrewingNote = blockEntity.itemStackHandler.getStackInSlot(7);
        ItemStack potion = null;
        int potionCount = 0;
        for (int i = 2; i <= 5; i++) {
            ItemStack tempPotion = blockEntity.itemStackHandler.getStackInSlot(i);
            if (!tempPotion.isEmpty()) {
                potionCount++;
                if (potion == null) potion = tempPotion;
                else {
                    if (!potion.is(tempPotion.getItem())) return false;
                }
            }
        }
        if (potion == null) return false;
/*        if (BrewingRecipe.basicPotionList.isEmpty()) BrewingRecipe.setBasicPotionList();
        if (!BrewingRecipe.basicPotionList.contains(potion.getItem())) return false;*/
/*        if (BrewingRecipe.basicToConMap.isEmpty()) BrewingRecipe.setBasicToConMap();
        Item output = BrewingRecipe.basicToConMap.get(potion.getItem());*/
        Item output = BrewingRecipe.getConPotionItem(potion.getItem());
        if (output == null) return false;
        Utils.PotionMapInit();
        boolean hasConcentrater = (Material1.is(ModItems.Concentrater.get()) && Material2.is(ModItems.Concentrater.get()))
                || (Material1.is(ModItems.Concentrater.get()) && Material2.isEmpty())
                || (Material1.isEmpty() && Material2.is(ModItems.Concentrater.get()));
        boolean canInsertIntoBrewedSlot = BrewedPotion.isEmpty() || (BrewedPotion.is(output) && BrewedPotion.getCount() + potionCount <= 64);
        boolean brewingNoteInSlot = BrewingNote.is(ModItems.BrewingNote.get());
        return hasConcentrater && canInsertIntoBrewedSlot && brewingNoteInSlot;
    }

    private static boolean hasRecipe5(HBrewingEntity blockEntity) { //涂料酿造
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
        ItemStack NormalPotion1 = blockEntity.itemStackHandler.getStackInSlot(2);
        ItemStack NormalPotion2 = blockEntity.itemStackHandler.getStackInSlot(3);
        ItemStack NormalPotion3 = blockEntity.itemStackHandler.getStackInSlot(4);
        ItemStack NormalPotion4 = blockEntity.itemStackHandler.getStackInSlot(5);
        ItemStack BrewedPotion = blockEntity.itemStackHandler.getStackInSlot(6);
        ItemStack BrewingNote = blockEntity.itemStackHandler.getStackInSlot(7);
        boolean MaterialCorrect = ((Material1.is(ModItems.SunPower.get()) && Material1.getCount() >= 6 && Material2.is(ModItems.Concentrater.get()))
                || (Material2.is(ModItems.SunPower.get()) && Material2.getCount() >= 6 && Material1.is(ModItems.Concentrater.get()))

                || (Material1.is(ModItems.LakeCore.get()) && Material1.getCount() >= 6 && Material2.is(ModItems.Concentrater.get()))
                || (Material2.is(ModItems.LakeCore.get()) && Material2.getCount() >= 6 && Material1.is(ModItems.Concentrater.get()))

                || (Material1.is(ModItems.VolcanoCore.get()) && Material1.getCount() >= 6 && Material2.is(ModItems.Concentrater.get()))
                || (Material2.is(ModItems.VolcanoCore.get()) && Material2.getCount() >= 6 && Material1.is(ModItems.Concentrater.get()))

                || (Material1.is(ModItems.SnowRune.get()) && Material1.getCount() >= 2 && Material2.is(ModItems.Concentrater.get()))
                || (Material2.is(ModItems.SnowRune.get()) && Material2.getCount() >= 2 && Material1.is(ModItems.Concentrater.get()))

                || (Material1.is(ModItems.SkyRune.get()) && Material1.getCount() >= 3 && Material2.is(ModItems.Concentrater.get()))
                || (Material2.is(ModItems.SkyRune.get()) && Material2.getCount() >= 3 && Material1.is(ModItems.Concentrater.get()))

                || (Material1.is(ModItems.EvokerRune.get()) && Material1.getCount() >= 1 && Material2.is(ModItems.Concentrater.get()))
                || (Material2.is(ModItems.EvokerRune.get()) && Material2.getCount() >= 1 && Material1.is(ModItems.Concentrater.get()))

                || (Material1.is(ModItems.Ruby.get()) && Material1.getCount() >= 64 && Material2.is(ModItems.Concentrater.get()))
                || (Material2.is(ModItems.Ruby.get()) && Material2.getCount() >= 64 && Material1.is(ModItems.Concentrater.get())));
        boolean HasNormalPotionInSlot = NormalPotion1.is(ModItems.PurifiedWater.get()) ||
                NormalPotion2.is(ModItems.PurifiedWater.get()) ||
                NormalPotion3.is(ModItems.PurifiedWater.get()) ||
                NormalPotion4.is(ModItems.PurifiedWater.get());
        boolean BrewingNoteInSlot = BrewingNote.is(ModItems.BrewingNote.get());
        boolean BrewedPotionIsEmpty = BrewedPotion.isEmpty();
        return MaterialCorrect && HasNormalPotionInSlot && BrewingNoteInSlot && BrewedPotionIsEmpty;
    }

    private static boolean hasRecipeOfSplash(HBrewingEntity blockEntity) { // 喷溅
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack material1 = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack material2 = blockEntity.itemStackHandler.getStackInSlot(1);

        ItemStack brewedPotion = blockEntity.itemStackHandler.getStackInSlot(6);
        ItemStack brewingNote = blockEntity.itemStackHandler.getStackInSlot(7);

        if (Compute.BrewingLevel(brewingNote) < 6) return false;
        ItemStack potion = null;
        int potionCount = 0;
        for (int i = 2; i <= 5; i++) {
            ItemStack tempPotion = blockEntity.itemStackHandler.getStackInSlot(i);
            if (!tempPotion.isEmpty()) {
                potionCount++;
                if (potion == null) potion = tempPotion;
                else {
                    if (!potion.is(tempPotion.getItem())) return false;
                }
            }
        }
        if (potion == null) return false;
        if (potion.getItem() instanceof NewThrowablePotion) return false;
        Item output = NewThrowablePotion.getSplashPotion(potion.getItem());
        if (output == null) return false;
        Utils.PotionMapInit();
        boolean hasSplasher = (material1.is(ModItems.Splasher.get()) && material2.is(ModItems.Splasher.get()))
                || (material1.is(ModItems.Splasher.get()) && material2.isEmpty())
                || (material1.isEmpty() && material2.is(ModItems.Splasher.get()));
        boolean canInsertIntoBrewedSlot = brewedPotion.isEmpty() || (brewedPotion.is(output) && brewedPotion.getCount() + potionCount <= 64);
        boolean brewingNoteInSlot = brewingNote.is(ModItems.BrewingNote.get());
        return hasSplasher && canInsertIntoBrewedSlot && brewingNoteInSlot;
    }
}

package com.very.wraq.blocks.entity;

import com.very.wraq.blocks.blocks.InjectRecipe;
import com.very.wraq.process.func.guide.Guide;
import com.very.wraq.render.gui.blocks.InjectBlockMenu;
import com.very.wraq.series.instance.Castle.CastleCurios;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
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


public class InjectBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 40;

    public InjectBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.Inject_Block_Entity.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> InjectBlockEntity.this.progress;
                    case 1 -> InjectBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> InjectBlockEntity.this.progress = value;
                    case 1 -> InjectBlockEntity.this.maxProgress = value;
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
        return Component.literal("注入装置");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new InjectBlockMenu(id, inventory, this, this.data);
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

    public ItemStack getDownSlotItemStack() {
        return itemStackHandler.getStackInSlot(1);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, InjectBlockEntity blockEntity) {
        if (level.isClientSide()) {
            if (blockEntity.progress >= blockEntity.maxProgress) {
                Player player = level.getNearestPlayer(TargetingConditions.DEFAULT, pos.getX(), pos.getY(), pos.getZ());
                player.playSound(SoundEvents.ANVIL_USE);
            }
            return;
        }
        if (hasInjectRecipe(blockEntity)) {
            blockEntity.progress++;
            setChanged(level, pos, blockState);
            if (blockEntity.progress >= blockEntity.maxProgress) {
                craftItem(blockEntity);
                String PlayerName = Utils.whoIsUsingBlock.get(blockEntity.getBlockPos());
                Player player = null;
                if (blockEntity.level.getServer().getPlayerList().getPlayerByName(PlayerName) != null)
                    player = blockEntity.level.getServer().getPlayerList().getPlayerByName(PlayerName);
                Guide.trig(player, 5);
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

    protected static void craftItem(InjectBlockEntity blockEntity) {

        String PlayerName = Utils.whoIsUsingBlock.get(blockEntity.getBlockPos());
        Player player = null;
        if (blockEntity.level.getServer().getPlayerList().getPlayerByName(PlayerName) != null)
            player = blockEntity.level.getServer().getPlayerList().getPlayerByName(PlayerName);

        if (hasInjectRecipe(blockEntity)) {

            ItemStack InjectedItem = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack Slot2Item = blockEntity.itemStackHandler.getStackInSlot(2);

            ItemStack GetItem = InjectRecipe.injectingRecipeMap.get(InjectedItem.getItem()).getForgingGetItem().getDefaultInstance();
            if (InjectedItem.getTagElement(Utils.MOD_ID) != null)
                GetItem.getOrCreateTagElement(Utils.MOD_ID).merge(InjectedItem.getOrCreateTagElement(Utils.MOD_ID));
            if (GetItem.getItem() instanceof CastleCurios) {
                GetItem.removeTagKey(Utils.MOD_ID);
                CastleCurios.randomAttributeProvide(GetItem, 6, 1);
                CastleCurios.RandomPassiveProvide(GetItem);
            }
            if (player != null) {
                Compute.formatBroad(blockEntity.level, Component.literal("打造").withStyle(ChatFormatting.GRAY),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(" 成功打造了 ").withStyle(ChatFormatting.WHITE).
                                append(GetItem.getDisplayName()));
            }

            GetItem.setCount(Slot2Item.getCount() + 1);
            blockEntity.itemStackHandler.extractItem(0, InjectRecipe.injectingRecipeMap.get(InjectedItem.getItem()).getMaterialCount(), false);
            blockEntity.itemStackHandler.extractItem(1, InjectRecipe.injectingRecipeMap.get(InjectedItem.getItem()).getOriginalMaterialNeedCount(), false);
            blockEntity.itemStackHandler.setStackInSlot(2, GetItem);
        }
    }


    private static boolean hasInjectRecipe(InjectBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }

        ItemStack Material = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack InjectedItem = blockEntity.itemStackHandler.getStackInSlot(1);
        ItemStack GetItem = blockEntity.itemStackHandler.getStackInSlot(2);

        if (InjectRecipe.injectingRecipeMap.isEmpty()) InjectRecipe.setInjectingRecipeMap();

        boolean InjectedItemHasRecipe = InjectRecipe.injectingRecipeMap.containsKey(InjectedItem.getItem());
        if (!InjectedItemHasRecipe) return false;

        boolean GetItemCanInsertIntoSlot2 = GetItem.is(Items.AIR) || (GetItem.is(InjectRecipe.injectingRecipeMap.get(InjectedItem.getItem()).getForgingGetItem())
                && GetItem.getCount() < GetItem.getMaxStackSize());
        if (!GetItemCanInsertIntoSlot2) return false;

        return InjectRecipe.injectingRecipeMap.get(InjectedItem.getItem()).getForgingNeededMaterial().
                equals(Material.getItem()) && InjectRecipe.injectingRecipeMap.get(InjectedItem.getItem()).getMaterialCount() <= Material.getCount()
                && InjectRecipe.injectingRecipeMap.get(InjectedItem.getItem()).getOriginalMaterialNeedCount() <= InjectedItem.getCount();

    }


}

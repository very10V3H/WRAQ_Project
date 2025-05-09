package fun.wraq.blocks.entity;

import fun.wraq.blocks.blocks.inject.InjectRecipe;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.instance.NoTeamInstanceModule;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.render.gui.blocks.InjectBlockMenu;
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

import java.util.Set;


public class InjectBlockEntity extends BlockEntity implements MenuProvider, Droppable {
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

    @Override
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
            /*blockEntity.progress++;*/
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

    public void craft() {
        craftItem(this);
    }

    protected static void craftItem(InjectBlockEntity blockEntity) {

        Player player = Utils.whoIsUsingBlock.getOrDefault(blockEntity.getBlockPos(), null);

        if (hasInjectRecipe(blockEntity)) {

            ItemStack injectedItem = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack slot2Item = blockEntity.itemStackHandler.getStackInSlot(2);

            ItemStack productItemStack = InjectRecipe.injectingRecipeMap.get(injectedItem.getItem()).getProduct().getDefaultInstance();
            if (injectedItem.getTagElement(Utils.MOD_ID) != null)
                productItemStack.getOrCreateTagElement(Utils.MOD_ID).merge(injectedItem.getOrCreateTagElement(Utils.MOD_ID));
            if (player != null) {
                Guide.trigV2(player, Guide.StageV2.FIRST_INJECT);
            }

            Set<Item> plainBossTier3Rings = Set.of(ModItems.PlainAttackRing3.get(), ModItems.PlainManaAttackRing3.get(),
                    ModItems.PlainHealthRing3.get(), ModItems.PlainDefenceRing3.get());
            if (plainBossTier3Rings.contains(productItemStack.getItem())) NoTeamInstanceModule
                    .putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.nether, true);

            Set<Item> devilWeapons = Set.of(ModItems.DevilSword.get(), ModItems.DevilBow.get(), ModItems.DevilSceptre.get());
            if (devilWeapons.contains(productItemStack.getItem())) {
                NoTeamInstanceModule.putPlayerAllowReward(player, NoTeamInstanceModule.AllowRewardKey.moon, true);
            }

            productItemStack.setCount(slot2Item.getCount() + 1);
            productItemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
            blockEntity.itemStackHandler.extractItem(0, InjectRecipe.injectingRecipeMap.get(injectedItem.getItem()).getMaterialCount(), false);
            blockEntity.itemStackHandler.extractItem(1, InjectRecipe.injectingRecipeMap.get(injectedItem.getItem()).getSourceItemCount(), false);
            blockEntity.itemStackHandler.setStackInSlot(2, productItemStack);

            MySound.soundToNearPlayer(player, SoundEvents.BREWING_STAND_BREW);
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

        boolean GetItemCanInsertIntoSlot2 = GetItem.is(Items.AIR) || (GetItem.is(InjectRecipe.injectingRecipeMap.get(InjectedItem.getItem()).getProduct())
                && GetItem.getCount() < GetItem.getMaxStackSize());
        if (!GetItemCanInsertIntoSlot2) return false;

        return InjectRecipe.injectingRecipeMap.get(InjectedItem.getItem()).getMaterial().
                equals(Material.getItem()) && InjectRecipe.injectingRecipeMap.get(InjectedItem.getItem()).getMaterialCount() <= Material.getCount()
                && InjectRecipe.injectingRecipeMap.get(InjectedItem.getItem()).getSourceItemCount() <= InjectedItem.getCount();

    }


}

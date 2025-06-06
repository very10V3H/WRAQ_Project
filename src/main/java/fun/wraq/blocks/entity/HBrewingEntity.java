package fun.wraq.blocks.entity;


import fun.wraq.blocks.blocks.brew.BrewingNote;
import fun.wraq.blocks.blocks.brew.BrewingRecipe;
import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.process.system.potion.NewThrowablePotion;
import fun.wraq.render.gui.blocks.BrewingMenu;
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


public class HBrewingEntity extends BlockEntity implements MenuProvider, Droppable {
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
                || hasRecipe3(blockEntity) || hasRecipe4(blockEntity) || hasRecipeOfSplash(blockEntity)) {
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

        Player player = Utils.whoIsUsingBlock.getOrDefault(blockEntity.getBlockPos(), null);

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
                if (itemStack.is(ModItems.PURIFIED_WATER.get())) purifiedWaterCount++;
            BrewingRecipe brewingRecipe = BrewingRecipe.getRecipe(new ItemStack(ModItems.PURIFIED_WATER.get()), material1, material2);
            if (brewingRecipe == null) return;
            ItemStack output = brewingRecipe.output;
            if (output == null) return;
            BrewingNote.addExp(brewingNote, material1.getItem(), purifiedWaterCount);
            BrewingNote.addExp(brewingNote, material2.getItem(), purifiedWaterCount);
            if (player != null) {
                InventoryCheck.addOwnerTagToItemStack(player, brewingNote);
            }
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
                if (blockEntity.itemStackHandler.getStackInSlot(i).is(ModItems.PURIFIED_WATER.get()))
                    blockEntity.itemStackHandler.extractItem(i, 1, false);
            }
            blockEntity.resetProgress();
        }
        if (hasRecipe1(blockEntity)) { //水质净化
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack purifiedWater = blockEntity.itemStackHandler.getStackInSlot(6);
            int waterBottleCount = 0;
            for (int i = 2; i <= 5; i++) {
                if (blockEntity.itemStackHandler.getStackInSlot(i).is(ModItems.WATER_BOTTLE.get())) {
                    blockEntity.itemStackHandler.extractItem(i, 1, false);
                    waterBottleCount++;
                }
            }

            ItemStack itemStack = ModItems.PURIFIED_WATER.get().getDefaultInstance();
            itemStack.setCount(waterBottleCount + purifiedWater.getCount());
            blockEntity.itemStackHandler.setStackInSlot(6, itemStack);

            if (Material1.is(ModItems.PURIFIER.get())) blockEntity.itemStackHandler.extractItem(0, 1, false);
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
            if (Material1.is(ModItems.STABILIZER.get())) blockEntity.itemStackHandler.extractItem(0, 1, false);
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
            if (Material1.is(ModItems.SPLASHER.get())) blockEntity.itemStackHandler.extractItem(0, 1, false);
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
            if (Material1.is(ModItems.SOLIDIFIER.get())) blockEntity.itemStackHandler.extractItem(0, 1, false);
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
            if (Material1.is(ModItems.CONCENTRATER.get())) blockEntity.itemStackHandler.extractItem(0, 1, false);
            else blockEntity.itemStackHandler.extractItem(1, 1, false);
            blockEntity.resetProgress();
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
        for (ItemStack itemStack : purifiedWater) if (itemStack.is(ModItems.PURIFIED_WATER.get())) purifiedWaterCount++;
        boolean hasPurifiedWater = purifiedWaterCount > 0;

        ItemStack output = BrewingRecipe.getOutput(new ItemStack(ModItems.PURIFIED_WATER.get()), material1, material2);
        if (BrewingRecipe.outputNeedBrewingLevelMap.isEmpty()) BrewingRecipe.setOutputNeedBrewingLevelMap();
        if (output == null) return false;
        if (BrewingRecipe.outputNeedBrewingLevelMap.containsKey(output.getItem())
                && Compute.BrewingLevel(brewingNote) < BrewingRecipe.outputNeedBrewingLevelMap.get(output.getItem()))
            return false;

        boolean canInsertIntoBrewedSlot = brewedPotion.isEmpty() ||
                (brewedPotion.is(output.getItem()) && brewedPotion.getCount() + purifiedWaterCount <= 64);
        boolean brewingNoteSlotIsNoEmpty = brewingNote.is(ModItems.BREWING_NOTE.get());
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
        for (ItemStack itemStack : list) if (itemStack.is(ModItems.WATER_BOTTLE.get())) ++waterBottleCount;
        ItemStack BrewedPotion = blockEntity.itemStackHandler.getStackInSlot(6);
        ItemStack BrewingNote = blockEntity.itemStackHandler.getStackInSlot(7);
        boolean hasWaterBottle = waterBottleCount > 0;
        boolean HasPurifierInSlot = Material1.is(ModItems.PURIFIER.get()) || Material2.is(ModItems.PURIFIER.get());
        boolean canInsertIntoBrewedSlot = BrewedPotion.isEmpty()
                || (BrewedPotion.is(ModItems.PURIFIED_WATER.get()) && BrewedPotion.getCount() + waterBottleCount <= 64);
        boolean BrewingNoteSlotIsNoEmpty = BrewingNote.is(ModItems.BREWING_NOTE.get());
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
        boolean HasStabilizerInSlot = (Material1.is(ModItems.STABILIZER.get()) && Material2.is(ModItems.STABILIZER.get()))
                || (Material1.is(ModItems.STABILIZER.get()) && Material2.isEmpty())
                || (Material1.isEmpty() && Material2.is(ModItems.STABILIZER.get()));
        boolean canInsertIntoBrewedSlot = BrewedPotion.isEmpty() || (BrewedPotion.is(output) && BrewedPotion.getCount() + potionCount <= 64);
        boolean BrewingNoteInSlot = BrewingNote.is(ModItems.BREWING_NOTE.get());
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
        boolean IsSolidifierInSlot = Material1.is(ModItems.SOLIDIFIER.get()) || Material2.is(ModItems.SOLIDIFIER.get());
        boolean SolidifiedSoulSlot = SolidifiedSoul.getCount() + Count <= SolidifiedSoul.getMaxStackSize();
        boolean IsSoulsInSlot = (Utils.BrewSoulMap.containsKey(NormalSoul1.getItem())) ||
                (Utils.BrewSoulMap.containsKey(NormalSoul2.getItem())) ||
                (Utils.BrewSoulMap.containsKey(NormalSoul3.getItem())) ||
                (Utils.BrewSoulMap.containsKey(NormalSoul4.getItem()));
        boolean IsBrewingBookInSlot = BrewingNote.is(ModItems.BREWING_NOTE.get());
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
        boolean hasConcentrater = (Material1.is(ModItems.CONCENTRATER.get()) && Material2.is(ModItems.CONCENTRATER.get()))
                || (Material1.is(ModItems.CONCENTRATER.get()) && Material2.isEmpty())
                || (Material1.isEmpty() && Material2.is(ModItems.CONCENTRATER.get()));
        boolean canInsertIntoBrewedSlot = BrewedPotion.isEmpty() || (BrewedPotion.is(output) && BrewedPotion.getCount() + potionCount <= 64);
        boolean brewingNoteInSlot = BrewingNote.is(ModItems.BREWING_NOTE.get());
        return hasConcentrater && canInsertIntoBrewedSlot && brewingNoteInSlot;
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
        boolean hasSplasher = (material1.is(ModItems.SPLASHER.get()) && material2.is(ModItems.SPLASHER.get()))
                || (material1.is(ModItems.SPLASHER.get()) && material2.isEmpty())
                || (material1.isEmpty() && material2.is(ModItems.SPLASHER.get()));
        boolean canInsertIntoBrewedSlot = brewedPotion.isEmpty() || (brewedPotion.is(output) && brewedPotion.getCount() + potionCount <= 64);
        boolean brewingNoteInSlot = brewingNote.is(ModItems.BREWING_NOTE.get());
        return hasSplasher && canInsertIntoBrewedSlot && brewingNoteInSlot;
    }
}

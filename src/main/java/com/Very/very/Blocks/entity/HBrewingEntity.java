package com.Very.very.Blocks.entity;


import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import com.Very.very.Render.Gui.Blocks.BrewingMenu;
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

import java.util.Random;


public class HBrewingEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(11)
    {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
    private LazyOptional<IItemHandler> LazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;
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
        return new BrewingMenu(id,inventory,this,this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
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
        compoundTag.put("inventory",itemStackHandler.serializeNBT());
        super.saveAdditional(compoundTag);
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        itemStackHandler.deserializeNBT(compoundTag.getCompound("inventory"));
    }
    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for(int i = 0; i < itemStackHandler.getSlots(); i++){
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, HBrewingEntity blockEntity) {
/*
        level.addParticle(ParticleTypes.ELECTRIC_SPARK,pos.getX()+0.5,pos.getY()+1,pos.getZ()+0.5,0,0.1,0);
*/
        if(level.isClientSide()){
            if(blockEntity.progress >= blockEntity.maxProgress)
            {
                Player player = level.getNearestPlayer(TargetingConditions.DEFAULT,pos.getX(),pos.getY(),pos.getZ());
                player.playSound(SoundEvents.ANVIL_USE);
            }
            return ;
        }
        if(hasRecipe(blockEntity) || hasRecipe1(blockEntity) || hasRecipe2(blockEntity)
                || hasRecipe3(blockEntity) || hasRecipe4(blockEntity) || hasRecipe5(blockEntity)){
            blockEntity.progress++;
            setChanged(level,pos,blockState);
            if(blockEntity.progress >= blockEntity.maxProgress){
                craftItem(blockEntity);
                blockEntity.resetProgress();
            }
        }
        else{
            blockEntity.resetProgress();
            setChanged(level,pos,blockState);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    protected static void craftItem(HBrewingEntity blockEntity) {
        if(hasRecipe(blockEntity)){ //基础酿造
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack BrewingNote = blockEntity.itemStackHandler.getStackInSlot(7);
            CompoundTag data = BrewingNote.getOrCreateTagElement(Utils.MOD_ID);
            int Count = 0;
            if(blockEntity.itemStackHandler.getStackInSlot(2).is(Moditems.PurifiedWater.get())) Count++;
            if(blockEntity.itemStackHandler.getStackInSlot(3).is(Moditems.PurifiedWater.get())) Count++;
            if(blockEntity.itemStackHandler.getStackInSlot(4).is(Moditems.PurifiedWater.get())) Count++;
            if(blockEntity.itemStackHandler.getStackInSlot(5).is(Moditems.PurifiedWater.get())) Count++;
            if(Material1.is(Moditems.VolcanoSolidifiedSoul.get()) && Material2.is(Moditems.VolcanoSolidifiedSoul.get())){
                ItemStack SpeedUpPotion = Items.POTION.getDefaultInstance();
                SpeedUpPotion.getOrCreateTag().putString("Potion","vmd:attackup_potion");
                SpeedUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                SpeedUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,SpeedUpPotion);
                if(!data.contains("VolcanoBrewingExp")) data.putInt("VolcanoBrewingExp",Count*2);
                else data.putInt("VolcanoBrewingExp",data.getInt("VolcanoBrewingExp")+Count*2);
            }
            if(Material1.is(Moditems.VolcanoSolidifiedSoul.get()) && Material2.is(Moditems.ForestSolidifiedSoul.get())){
                ItemStack DefenceUpPotion = Items.POTION.getDefaultInstance();
                DefenceUpPotion.getOrCreateTag().putString("Potion","vmd:breakdefenceup_potion");
                DefenceUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                DefenceUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,DefenceUpPotion);
                if(!data.contains("VolcanoBrewingExp")) data.putInt("VolcanoBrewingExp", Count);
                else data.putInt("VolcanoBrewingExp",data.getInt("VolcanoBrewingExp")+ Count);
                if(!data.contains("ForestBrewingExp")) data.putInt("ForestBrewingExp", Count);
                else data.putInt("ForestBrewingExp",data.getInt("ForestBrewingExp")+ Count);
            }
            if(Material1.is(Moditems.SnowSolidifiedSoul.get()) && Material2.is(Moditems.VolcanoSolidifiedSoul.get())){
                ItemStack CoolDownDecreaseUpPotion = Items.POTION.getDefaultInstance();
                CoolDownDecreaseUpPotion.getOrCreateTag().putString("Potion","vmd:critrateup_potion");
                CoolDownDecreaseUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                CoolDownDecreaseUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,CoolDownDecreaseUpPotion);
                if(!data.contains("VolcanoBrewingExp")) data.putInt("VolcanoBrewingExp",Count);
                else data.putInt("VolcanoBrewingExp",data.getInt("VolcanoBrewingExp")+Count);
                if(!data.contains("SnowBrewingExp")) data.putInt("SnowBrewingExp",Count);
                else data.putInt("SnowBrewingExp",data.getInt("SnowBrewingExp")+Count);
            }
            if(Material1.is(Moditems.VolcanoSolidifiedSoul.get()) && Material2.is(Moditems.SnowSolidifiedSoul.get())){
                ItemStack AttackUpPotion = Items.POTION.getDefaultInstance();
                AttackUpPotion.getOrCreateTag().putString("Potion","vmd:critdamageup_potion");
                AttackUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                AttackUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,AttackUpPotion);
                if(!data.contains("VolcanoBrewingExp")) data.putInt("VolcanoBrewingExp",Count);
                else data.putInt("VolcanoBrewingExp",data.getInt("VolcanoBrewingExp")+Count);
                if(!data.contains("SnowBrewingExp")) data.putInt("SnowBrewingExp",Count);
                else data.putInt("SnowBrewingExp",data.getInt("SnowBrewingExp")+Count);
            }
            if(Material1.is(Moditems.EvokerSolidifiedSoul.get()) && Material2.is(Moditems.VolcanoSolidifiedSoul.get())){
                ItemStack CritRateUpPotion = Items.POTION.getDefaultInstance();
                CritRateUpPotion.getOrCreateTag().putString("Potion","vmd:manadamageup_potion");
                CritRateUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                CritRateUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,CritRateUpPotion);
                if(!data.contains("VolcanoBrewingExp")) data.putInt("VolcanoBrewingExp",Count);
                else data.putInt("VolcanoBrewingExp",data.getInt("VolcanoBrewingExp")+Count);
                if(!data.contains("EvokerBrewingExp")) data.putInt("EvokerBrewingExp",Count);
                else data.putInt("EvokerBrewingExp",data.getInt("EvokerBrewingExp")+Count);
            }
            if(Material1.is(Moditems.EvokerSolidifiedSoul.get()) && Material2.is(Moditems.ForestSolidifiedSoul.get())){
                ItemStack CritRateUpPotion = Items.POTION.getDefaultInstance();
                CritRateUpPotion.getOrCreateTag().putString("Potion","vmd:manabreakdefenceup_potion");
                CritRateUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                CritRateUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,CritRateUpPotion);
                if(!data.contains("EvokerBrewingExp")) data.putInt("EvokerBrewingExp",Count);
                else data.putInt("EvokerBrewingExp",data.getInt("EvokerBrewingExp")+Count);
                if(!data.contains("ForestBrewingExp")) data.putInt("ForestBrewingExp",Count);
                else data.putInt("ForestBrewingExp",data.getInt("ForestBrewingExp")+Count);
            }
            if(Material1.is(Moditems.EvokerSolidifiedSoul.get()) && Material2.is(Moditems.PlainSolidifiedSoul.get())){
                ItemStack CritRateUpPotion = Items.POTION.getDefaultInstance();
                CritRateUpPotion.getOrCreateTag().putString("Potion","vmd:manareplyup_potion");
                CritRateUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                CritRateUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,CritRateUpPotion);
                if(!data.contains("EvokerBrewingExp")) data.putInt("EvokerBrewingExp",Count);
                else data.putInt("EvokerBrewingExp",data.getInt("EvokerBrewingExp")+Count);
                if(!data.contains("PlainBrewingExp")) data.putInt("PlainBrewingExp",Count);
                else data.putInt("PlainBrewingExp",data.getInt("PlainBrewingExp")+Count);
            }
            if(Material1.is(Moditems.LakeSolidifiedSoul.get()) && Material2.is(Moditems.PlainSolidifiedSoul.get())){
                ItemStack CritRateUpPotion = Items.POTION.getDefaultInstance();
                CritRateUpPotion.getOrCreateTag().putString("Potion","vmd:cooldownup_potion");
                CritRateUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                CritRateUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,CritRateUpPotion);
                if(!data.contains("PlainBrewingExp")) data.putInt("PlainBrewingExp",Count);
                else data.putInt("PlainBrewingExp",data.getInt("PlainBrewingExp")+Count);
                if(!data.contains("LakeBrewingExp")) data.putInt("LakeBrewingExp",Count);
                else data.putInt("LakeBrewingExp",data.getInt("LakeBrewingExp")+Count);
            }
            if(Material1.is(Moditems.NetherSolidifiedSoul.get()) && Material2.is(Moditems.VolcanoSolidifiedSoul.get())){
                ItemStack CritRateUpPotion = Items.POTION.getDefaultInstance();
                CritRateUpPotion.getOrCreateTag().putString("Potion","vmd:healstealup_potion");
                CritRateUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                CritRateUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,CritRateUpPotion);
                if(!data.contains("VolcanoBrewingExp")) data.putInt("VolcanoBrewingExp",Count);
                else data.putInt("VolcanoBrewingExp",data.getInt("VolcanoBrewingExp")+Count);
                if(!data.contains("NetherBrewingExp")) data.putInt("NetherBrewingExp",Count);
                else data.putInt("NetherBrewingExp",data.getInt("NetherBrewingExp")+Count);
            }
            if(Material1.is(Moditems.ForestSolidifiedSoul.get()) && Material2.is(Moditems.EvokerSolidifiedSoul.get())){
                ItemStack CritRateUpPotion = Items.POTION.getDefaultInstance();
                CritRateUpPotion.getOrCreateTag().putString("Potion","vmd:manadefenceup_potion");
                CritRateUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                CritRateUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,CritRateUpPotion);
                if(!data.contains("EvokerBrewingExp")) data.putInt("EvokerBrewingExp",Count);
                else data.putInt("EvokerBrewingExp",data.getInt("EvokerBrewingExp")+Count);
                if(!data.contains("ForestBrewingExp")) data.putInt("ForestBrewingExp",Count);
                else data.putInt("ForestBrewingExp",data.getInt("ForestBrewingExp")+Count);
            }
            if(Material1.is(Moditems.ForestSolidifiedSoul.get()) && Material2.is(Moditems.ForestSolidifiedSoul.get())){
                ItemStack CritRateUpPotion = Items.POTION.getDefaultInstance();
                CritRateUpPotion.getOrCreateTag().putString("Potion","vmd:defenceup_potion");
                CritRateUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                CritRateUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,CritRateUpPotion);
                if(!data.contains("ForestBrewingExp")) data.putInt("ForestBrewingExp",Count*2);
                else data.putInt("ForestBrewingExp",data.getInt("ForestBrewingExp")+Count*2);
            }
            if(Material1.is(Moditems.SkySolidifiedSoul.get()) && Material2.is(Moditems.PlainSolidifiedSoul.get())){
                ItemStack CritRateUpPotion = Items.POTION.getDefaultInstance();
                CritRateUpPotion.getOrCreateTag().putString("Potion","vmd:speedup_potion");
                CritRateUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                CritRateUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,CritRateUpPotion);
                if(!data.contains("SkyBrewingExp")) data.putInt("SkyBrewingExp",Count);
                else data.putInt("SkyBrewingExp",data.getInt("SkyBrewingExp")+Count);
                if(!data.contains("PlainBrewingExp")) data.putInt("PlainBrewingExp",Count);
                else data.putInt("PlainBrewingExp",data.getInt("PlainBrewingExp")+Count);
            }
            if(Material1.is(Moditems.PlainSolidifiedSoul.get()) && Material2.is(Moditems.PlainSolidifiedSoul.get())){
                ItemStack CritRateUpPotion = Items.POTION.getDefaultInstance();
                CritRateUpPotion.getOrCreateTag().putString("Potion","vmd:healreply_potion");
                CritRateUpPotion.getOrCreateTagElement(Utils.MOD_ID);
                CritRateUpPotion.setCount(Count);
                blockEntity.itemStackHandler.setStackInSlot(6,CritRateUpPotion);
                if(!data.contains("PlainBrewingExp")) data.putInt("PlainBrewingExp",Count * 2);
                else data.putInt("PlainBrewingExp",data.getInt("PlainBrewingExp")+Count * 2);
            }
            int BrewingLevel = Compute.BrewingLevel(BrewingNote);
            Player player = blockEntity.level.getServer().getPlayerList().getPlayerByName(data.getString("Owner"));
            if(!Compute.BrewingLevelReward(player,BrewingLevel,data)){
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
            }
            if(blockEntity.itemStackHandler.getStackInSlot(2).is(Moditems.PurifiedWater.get()))
                blockEntity.itemStackHandler.extractItem(2,1,false);
            if(blockEntity.itemStackHandler.getStackInSlot(3).is(Moditems.PurifiedWater.get()))
                blockEntity.itemStackHandler.extractItem(3,1,false);
            if(blockEntity.itemStackHandler.getStackInSlot(4).is(Moditems.PurifiedWater.get()))
                blockEntity.itemStackHandler.extractItem(4,1,false);
            if(blockEntity.itemStackHandler.getStackInSlot(5).is(Moditems.PurifiedWater.get()))
                blockEntity.itemStackHandler.extractItem(5,1,false);
            blockEntity.resetProgress();
        }
        if(hasRecipe1(blockEntity)){ //水质净化
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack NormalPotion1 = blockEntity.itemStackHandler.getStackInSlot(2);
            ItemStack NormalPotion2 = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack NormalPotion3 = blockEntity.itemStackHandler.getStackInSlot(4);
            ItemStack NormalPotion4 = blockEntity.itemStackHandler.getStackInSlot(5);
            int Count = 0;
            if(NormalPotion1.getOrCreateTag().contains("Potion") && NormalPotion1.getOrCreateTag().getString("Potion").equals("minecraft:water")){
                blockEntity.itemStackHandler.extractItem(2,1,false);
                Count++;
            }
            if(NormalPotion2.getOrCreateTag().contains("Potion") && NormalPotion2.getOrCreateTag().getString("Potion").equals("minecraft:water")) {
                blockEntity.itemStackHandler.extractItem(3,1,false);
                Count++;
            }
            if(NormalPotion3.getOrCreateTag().contains("Potion") && NormalPotion3.getOrCreateTag().getString("Potion").equals("minecraft:water")) {
                blockEntity.itemStackHandler.extractItem(4,1,false);
                Count++;
            }
            if(NormalPotion4.getOrCreateTag().contains("Potion") && NormalPotion4.getOrCreateTag().getString("Potion").equals("minecraft:water")) {
                blockEntity.itemStackHandler.extractItem(5,1,false);
                Count++;
            }
            ItemStack itemStack = Moditems.PurifiedWater.get().getDefaultInstance();
            itemStack.setCount(Count);
            itemStack.getOrCreateTagElement(Utils.MOD_ID);
            blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
            if(Material1.is(Moditems.Purifier.get())) blockEntity.itemStackHandler.extractItem(0,1,false);
            else blockEntity.itemStackHandler.extractItem(1,1,false);
            blockEntity.resetProgress();
        }
        if(hasRecipe2(blockEntity)){ // 延长药水时效
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack NormalPotion1 = blockEntity.itemStackHandler.getStackInSlot(2);
            ItemStack NormalPotion2 = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack NormalPotion3 = blockEntity.itemStackHandler.getStackInSlot(4);
            ItemStack NormalPotion4 = blockEntity.itemStackHandler.getStackInSlot(5);
            int Count = 0;
            String PotionTag = " ";
            if(!NormalPotion1.isEmpty()) {
                Count ++;
                PotionTag = NormalPotion1.getOrCreateTag().getString("Potion");
            }
            if(!NormalPotion2.isEmpty()) {
                Count ++;
                PotionTag = NormalPotion2.getOrCreateTag().getString("Potion");
            }
            if(!NormalPotion3.isEmpty()) {
                Count ++;
                PotionTag = NormalPotion3.getOrCreateTag().getString("Potion");
            }
            if(!NormalPotion4.isEmpty()) {
                Count ++;
                PotionTag = NormalPotion4.getOrCreateTag().getString("Potion");
            }
            ItemStack BrewedPotion = Items.POTION.getDefaultInstance();
            BrewedPotion.getOrCreateTag().putString("Potion","vmd:long_"+PotionTag.substring(4));
            BrewedPotion.setCount(Count);
            BrewedPotion.getOrCreateTagElement(Utils.MOD_ID);
            blockEntity.itemStackHandler.setStackInSlot(6,BrewedPotion);
            blockEntity.itemStackHandler.extractItem(2,1,false);
            blockEntity.itemStackHandler.extractItem(3,1,false);
            blockEntity.itemStackHandler.extractItem(4,1,false);
            blockEntity.itemStackHandler.extractItem(5,1,false);
            if(Material1.is(Moditems.Stabilizer.get())) blockEntity.itemStackHandler.extractItem(0,1,false);
            else blockEntity.itemStackHandler.extractItem(1,1,false);
            blockEntity.resetProgress();
        }
        if(hasRecipe3(blockEntity)){ //固化剂
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack NormalSoul1 = blockEntity.itemStackHandler.getStackInSlot(2);
            ItemStack NormalSoul2 = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack NormalSoul3 = blockEntity.itemStackHandler.getStackInSlot(4);
            ItemStack NormalSoul4 = blockEntity.itemStackHandler.getStackInSlot(5);
            ItemStack SolidifiedSlot = blockEntity.itemStackHandler.getStackInSlot(6);
            int Count = 0;
            ItemStack Solidified_Soul = Items.AIR.getDefaultInstance();
            if(!NormalSoul1.isEmpty()) {
                Count++;
                Solidified_Soul = Utils.BrewSoulMap.get(NormalSoul1.getItem()).getDefaultInstance();
            }
            if(!NormalSoul2.isEmpty()) {
                Count++;
                Solidified_Soul = Utils.BrewSoulMap.get(NormalSoul2.getItem()).getDefaultInstance();
            }
            if(!NormalSoul3.isEmpty()) {
                Count++;
                Solidified_Soul = Utils.BrewSoulMap.get(NormalSoul3.getItem()).getDefaultInstance();
            }
            if(!NormalSoul4.isEmpty()) {
                Count++;
                Solidified_Soul = Utils.BrewSoulMap.get(NormalSoul4.getItem()).getDefaultInstance();
            }
            Solidified_Soul.setCount(SolidifiedSlot.getCount()+Count);
            Solidified_Soul.getOrCreateTagElement(Utils.MOD_ID);
            blockEntity.itemStackHandler.setStackInSlot(6,Solidified_Soul);
            blockEntity.itemStackHandler.extractItem(2,1,false);
            blockEntity.itemStackHandler.extractItem(3,1,false);
            blockEntity.itemStackHandler.extractItem(4,1,false);
            blockEntity.itemStackHandler.extractItem(5,1,false);
            if(Material1.is(Moditems.Solidifier.get())) blockEntity.itemStackHandler.extractItem(0,1,false);
            else blockEntity.itemStackHandler.extractItem(1,1,false);
            blockEntity.resetProgress();
        }
        if(hasRecipe4(blockEntity)){
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack NormalPotion1 = blockEntity.itemStackHandler.getStackInSlot(2);
            ItemStack NormalPotion2 = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack NormalPotion3 = blockEntity.itemStackHandler.getStackInSlot(4);
            ItemStack NormalPotion4 = blockEntity.itemStackHandler.getStackInSlot(5);
            int Count = 0;
            String PotionTag = " ";
            if(!NormalPotion1.isEmpty()) {
                Count ++;
                PotionTag = NormalPotion1.getOrCreateTag().getString("Potion");
            }
            if(!NormalPotion2.isEmpty()) {
                Count ++;
                PotionTag = NormalPotion2.getOrCreateTag().getString("Potion");
            }
            if(!NormalPotion3.isEmpty()) {
                Count ++;
                PotionTag = NormalPotion3.getOrCreateTag().getString("Potion");
            }
            if(!NormalPotion4.isEmpty()) {
                Count ++;
                PotionTag = NormalPotion4.getOrCreateTag().getString("Potion");
            }
            ItemStack BrewedPotion = Items.POTION.getDefaultInstance();
            BrewedPotion.getOrCreateTag().putString("Potion","vmd:con_"+PotionTag.substring(4));
            BrewedPotion.setCount(Count);
            BrewedPotion.getOrCreateTagElement(Utils.MOD_ID);
            blockEntity.itemStackHandler.setStackInSlot(6,BrewedPotion);
            blockEntity.itemStackHandler.extractItem(2,1,false);
            blockEntity.itemStackHandler.extractItem(3,1,false);
            blockEntity.itemStackHandler.extractItem(4,1,false);
            blockEntity.itemStackHandler.extractItem(5,1,false);
            if(Material1.is(Moditems.Stabilizer.get())) blockEntity.itemStackHandler.extractItem(0,1,false);
            else blockEntity.itemStackHandler.extractItem(1,1,false);
            blockEntity.resetProgress();
        }
        if(hasRecipe5(blockEntity)) { // 涂膏
            ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack BrewingNote = blockEntity.itemStackHandler.getStackInSlot(7);
            int Count = 0;
            if(blockEntity.itemStackHandler.getStackInSlot(2).is(Moditems.PurifiedWater.get())) Count++;
            if(blockEntity.itemStackHandler.getStackInSlot(3).is(Moditems.PurifiedWater.get())) Count++;
            if(blockEntity.itemStackHandler.getStackInSlot(4).is(Moditems.PurifiedWater.get())) Count++;
            if(blockEntity.itemStackHandler.getStackInSlot(5).is(Moditems.PurifiedWater.get())) Count++;
            int BrewingLevel = Compute.BrewingLevel(BrewingNote);
            int Rate1 = BrewingLevel;
            Random random = new Random();
            blockEntity.itemStackHandler.extractItem(2,1,false);
            blockEntity.itemStackHandler.extractItem(3,1,false);
            blockEntity.itemStackHandler.extractItem(4,1,false);
            blockEntity.itemStackHandler.extractItem(5,1,false);
            if (Material1.is(Moditems.SunPower.get()) || Material2.is(Moditems.SunPower.get())) {
                if (Material1.is(Moditems.SunPower.get())) {
                    blockEntity.itemStackHandler.extractItem(0,6,false);
                    blockEntity.itemStackHandler.extractItem(1,1,false);
                }
                else {
                    blockEntity.itemStackHandler.extractItem(0,1,false);
                    blockEntity.itemStackHandler.extractItem(1,6,false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = Moditems.SunOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                }
                else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = Moditems.SunOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                    else {
                        ItemStack itemStack = Moditems.SunOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                }
            }
            if (Material1.is(Moditems.LakeCore.get()) || Material2.is(Moditems.LakeCore.get())) {
                if (Material1.is(Moditems.LakeCore.get())) {
                    blockEntity.itemStackHandler.extractItem(0,6,false);
                    blockEntity.itemStackHandler.extractItem(1,1,false);
                }
                else {
                    blockEntity.itemStackHandler.extractItem(0,1,false);
                    blockEntity.itemStackHandler.extractItem(1,6,false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = Moditems.LakeOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                }
                else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = Moditems.LakeOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                    else {
                        ItemStack itemStack = Moditems.LakeOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                }
            }
            if (Material1.is(Moditems.VolcanoCore.get()) || Material2.is(Moditems.VolcanoCore.get())) {
                if (Material1.is(Moditems.VolcanoCore.get())) {
                    blockEntity.itemStackHandler.extractItem(0,6,false);
                    blockEntity.itemStackHandler.extractItem(1,1,false);
                }
                else {
                    blockEntity.itemStackHandler.extractItem(0,1,false);
                    blockEntity.itemStackHandler.extractItem(1,6,false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = Moditems.VolcanoOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                }
                else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = Moditems.VolcanoOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                    else {
                        ItemStack itemStack = Moditems.VolcanoOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                }
            }
            if (Material1.is(Moditems.SnowRune.get()) || Material2.is(Moditems.SnowRune.get())) {
                if (Material1.is(Moditems.SnowRune.get())) {
                    blockEntity.itemStackHandler.extractItem(0,2,false);
                    blockEntity.itemStackHandler.extractItem(1,1,false);
                }
                else {
                    blockEntity.itemStackHandler.extractItem(0,1,false);
                    blockEntity.itemStackHandler.extractItem(1,2,false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = Moditems.SnowOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                }
                else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = Moditems.SnowOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                    else {
                        ItemStack itemStack = Moditems.SnowOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                }
            }
            if (Material1.is(Moditems.SkyRune.get()) || Material2.is(Moditems.SkyRune.get())) {
                if (Material1.is(Moditems.SkyRune.get())) {
                    blockEntity.itemStackHandler.extractItem(0,3,false);
                    blockEntity.itemStackHandler.extractItem(1,1,false);
                }
                else {
                    blockEntity.itemStackHandler.extractItem(0,1,false);
                    blockEntity.itemStackHandler.extractItem(1,3,false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = Moditems.SkyOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                }
                else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = Moditems.SkyOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                    else {
                        ItemStack itemStack = Moditems.SkyOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                }
            }
            if (Material1.is(Moditems.EvokerRune.get()) || Material2.is(Moditems.EvokerRune.get())) {
                if (Material1.is(Moditems.EvokerRune.get())) {
                    blockEntity.itemStackHandler.extractItem(0,1,false);
                    blockEntity.itemStackHandler.extractItem(1,1,false);
                }
                else {
                    blockEntity.itemStackHandler.extractItem(0,1,false);
                    blockEntity.itemStackHandler.extractItem(1,1,false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = Moditems.ManaOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                }
                else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = Moditems.ManaOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                    else {
                        ItemStack itemStack = Moditems.ManaOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                }
            }
            if (Material1.is(Moditems.ruby.get()) || Material2.is(Moditems.ruby.get())) {
                if (Material1.is(Moditems.ruby.get())) {
                    blockEntity.itemStackHandler.extractItem(0,64,false);
                    blockEntity.itemStackHandler.extractItem(1,1,false);
                }
                else {
                    blockEntity.itemStackHandler.extractItem(0,1,false);
                    blockEntity.itemStackHandler.extractItem(1,64,false);
                }
                if (random.nextInt(10) < Rate1) {
                    ItemStack itemStack = Moditems.NetherOintment2.get().getDefaultInstance();
                    itemStack.setCount(Count);
                    itemStack.getOrCreateTagElement(Utils.MOD_ID);
                    blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                }
                else {
                    if (random.nextInt(10) < Rate1) {
                        ItemStack itemStack = Moditems.NetherOintment1.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
                    }
                    else {
                        ItemStack itemStack = Moditems.NetherOintment0.get().getDefaultInstance();
                        itemStack.setCount(Count);
                        itemStack.getOrCreateTagElement(Utils.MOD_ID);
                        blockEntity.itemStackHandler.setStackInSlot(6,itemStack);
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
        ItemStack Material1 = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack Material2 = blockEntity.itemStackHandler.getStackInSlot(1);
        ItemStack NormalPotion1 = blockEntity.itemStackHandler.getStackInSlot(2);
        ItemStack NormalPotion2 = blockEntity.itemStackHandler.getStackInSlot(3);
        ItemStack NormalPotion3 = blockEntity.itemStackHandler.getStackInSlot(4);
        ItemStack NormalPotion4 = blockEntity.itemStackHandler.getStackInSlot(5);
        ItemStack BrewedPotion = blockEntity.itemStackHandler.getStackInSlot(6);
        ItemStack BrewingNote = blockEntity.itemStackHandler.getStackInSlot(7);
        boolean HasNormalPotionInSlot = NormalPotion1.is(Moditems.PurifiedWater.get()) ||
                NormalPotion2.is(Moditems.PurifiedWater.get()) ||
                NormalPotion3.is(Moditems.PurifiedWater.get()) ||
                NormalPotion4.is(Moditems.PurifiedWater.get());
        boolean MaterialIsCorrect = (Material1.is(Moditems.VolcanoSolidifiedSoul.get()) && Material2.is(Moditems.VolcanoSolidifiedSoul.get())) ||
                (Material1.is(Moditems.VolcanoSolidifiedSoul.get()) && Material2.is(Moditems.ForestSolidifiedSoul.get())) ||
                (Material1.is(Moditems.SnowSolidifiedSoul.get()) && Material2.is(Moditems.VolcanoSolidifiedSoul.get())) ||
                (Material1.is(Moditems.VolcanoSolidifiedSoul.get()) && Material2.is(Moditems.SnowSolidifiedSoul.get())) ||
                (Material1.is(Moditems.EvokerSolidifiedSoul.get()) && Material2.is(Moditems.VolcanoSolidifiedSoul.get())) ||
                (Material1.is(Moditems.EvokerSolidifiedSoul.get()) && Material2.is(Moditems.ForestSolidifiedSoul.get())) ||
                (Material1.is(Moditems.EvokerSolidifiedSoul.get()) && Material2.is(Moditems.PlainSolidifiedSoul.get())) ||
                (Material1.is(Moditems.LakeSolidifiedSoul.get()) && Material2.is(Moditems.PlainSolidifiedSoul.get())) ||
                (Material1.is(Moditems.NetherSolidifiedSoul.get()) && Material2.is(Moditems.VolcanoSolidifiedSoul.get())) ||
                (Material1.is(Moditems.ForestSolidifiedSoul.get()) && Material2.is(Moditems.EvokerSolidifiedSoul.get())) ||
                (Material1.is(Moditems.ForestSolidifiedSoul.get()) && Material2.is(Moditems.ForestSolidifiedSoul.get())) ||
                (Material1.is(Moditems.SkySolidifiedSoul.get()) && Material2.is(Moditems.PlainSolidifiedSoul.get())) ||
                (Material1.is(Moditems.PlainSolidifiedSoul.get()) && Material2.is(Moditems.PlainSolidifiedSoul.get()));
        boolean BrewedSlotIsEmpty = BrewedPotion.isEmpty();
        boolean BrewingNoteSlotIsNoEmpty = BrewingNote.is(Moditems.BrewingNote.get());
        return HasNormalPotionInSlot && MaterialIsCorrect && BrewedSlotIsEmpty && BrewingNoteSlotIsNoEmpty;
    }
    private static boolean hasRecipe1(HBrewingEntity blockEntity){ // 水质净化
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
        boolean HasUnPurifiedPotion = ((NormalPotion1.getOrCreateTag().contains("Potion") && NormalPotion1.getOrCreateTag().getString("Potion").equals("minecraft:water"))||
                (NormalPotion2.getOrCreateTag().contains("Potion") && NormalPotion2.getOrCreateTag().getString("Potion").equals("minecraft:water"))||
                (NormalPotion3.getOrCreateTag().contains("Potion") && NormalPotion3.getOrCreateTag().getString("Potion").equals("minecraft:water"))||
                (NormalPotion4.getOrCreateTag().contains("Potion") && NormalPotion4.getOrCreateTag().getString("Potion").equals("minecraft:water")));
        boolean HasPurifierInSlot = Material1.is(Moditems.Purifier.get()) || Material2.is(Moditems.Purifier.get());
        boolean BrewedSlotIsEmpty = BrewedPotion.isEmpty();
        boolean BrewingNoteSlotIsNoEmpty = BrewingNote.is(Moditems.BrewingNote.get());
        return HasUnPurifiedPotion && HasPurifierInSlot && BrewedSlotIsEmpty && BrewingNoteSlotIsNoEmpty;
    }
    private static boolean hasRecipe2(HBrewingEntity blockEntity){ // 药水时效延长
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
        String PotionTag1 = " ";
        String PotionTag2 = " ";
        String PotionTag3 = " ";
        String PotionTag4 = " ";
        if(NormalPotion1.getOrCreateTag().contains("Potion")){
            PotionTag1 = NormalPotion1.getOrCreateTag().getString("Potion");
        }
        if(NormalPotion2.getOrCreateTag().contains("Potion")){
            PotionTag2 = NormalPotion2.getOrCreateTag().getString("Potion");
        }
        if(NormalPotion3.getOrCreateTag().contains("Potion")){
            PotionTag3 = NormalPotion3.getOrCreateTag().getString("Potion");
        }
        if(NormalPotion4.getOrCreateTag().contains("Potion")){
            PotionTag4 = NormalPotion4.getOrCreateTag().getString("Potion");
        }
        boolean IsSamePotion = true;
        if(!NormalPotion1.isEmpty()){
            if(!NormalPotion2.isEmpty() && !PotionTag2.equals(PotionTag1)) IsSamePotion = false;
            if(!NormalPotion3.isEmpty() && !PotionTag3.equals(PotionTag1)) IsSamePotion = false;
            if(!NormalPotion4.isEmpty() && !PotionTag4.equals(PotionTag1)) IsSamePotion = false;
        }
        else
        {
            if(!NormalPotion2.isEmpty()){
                if(!NormalPotion3.isEmpty() && !PotionTag3.equals(PotionTag2)) IsSamePotion = false;
                if(!NormalPotion4.isEmpty() && !PotionTag4.equals(PotionTag2)) IsSamePotion = false;
            }
            else
            {
                if(!NormalPotion3.isEmpty()){
                    if(!NormalPotion4.isEmpty() && !PotionTag4.equals(PotionTag3)) IsSamePotion = false;
                }
                else if(NormalPotion4.isEmpty()) IsSamePotion = false;
            }
        }
        Utils.PotionMapInit();
        boolean HasStabilizerInSlot = (Material1.is(Moditems.Stabilizer.get()) && Material2.is(Moditems.Stabilizer.get()))
                || (Material1.is(Moditems.Stabilizer.get()) && Material2.isEmpty())
                || (Material1.isEmpty() && Material2.is(Moditems.Stabilizer.get()));
        boolean IsVmdPotionInSlot = (Utils.PotionMap.containsKey(PotionTag1)) || (Utils.PotionMap.containsKey(PotionTag2))
                || (Utils.PotionMap.containsKey(PotionTag3)) || (Utils.PotionMap.containsKey(PotionTag4));
        boolean BrewedPotionIsEmpty = BrewedPotion.isEmpty();
        boolean BrewingNoteInSlot = BrewingNote.is(Moditems.BrewingNote.get());
        return HasStabilizerInSlot && IsVmdPotionInSlot && BrewedPotionIsEmpty && BrewingNoteInSlot && IsSamePotion;
    }
    private static boolean hasRecipe3(HBrewingEntity blockEntity){ // 固化根源
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
        if(!NormalSoul1.isEmpty()){
            if(!NormalSoul2.isEmpty() && !NormalSoul2.equals(NormalSoul1,true)) IsSameSoul = false;
            if(!NormalSoul3.isEmpty() && !NormalSoul3.equals(NormalSoul1,true)) IsSameSoul = false;
            if(!NormalSoul4.isEmpty() && !NormalSoul4.equals(NormalSoul1,true)) IsSameSoul = false;
        }
        else
        {
            if(!NormalSoul2.isEmpty()){
                if(!NormalSoul3.isEmpty() && !NormalSoul3.equals(NormalSoul2,true)) IsSameSoul = false;
                if(!NormalSoul4.isEmpty() && !NormalSoul4.equals(NormalSoul2,true)) IsSameSoul = false;
            }
            else
            {
                if(!NormalSoul3.isEmpty()){
                    if(!NormalSoul4.isEmpty() && !NormalSoul4.equals(NormalSoul3,true)) IsSameSoul = false;
                }
                else if (NormalSoul4.isEmpty()) IsSameSoul = false;
            }
        }
        int Count = 0;
        Item NormalSoulItem = Items.AIR;
        if(!NormalSoul1.isEmpty()) {
            Count ++;
            NormalSoulItem = NormalSoul1.getItem();
        }
        if(!NormalSoul2.isEmpty()) {
            Count ++;
            NormalSoulItem = NormalSoul2.getItem();
        }
        if(!NormalSoul3.isEmpty()) {
            Count ++;
            NormalSoulItem = NormalSoul3.getItem();
        }
        if(!NormalSoul4.isEmpty()) {
            Count ++;
            NormalSoulItem = NormalSoul4.getItem();
        }
        Utils.BrewSoulMapInit();
        boolean IsSolidifierInSlot = Material1.is(Moditems.Solidifier.get()) || Material2.is(Moditems.Solidifier.get());
        boolean SolidifiedSoulSlot = SolidifiedSoul.getCount()+Count <= SolidifiedSoul.getMaxStackSize();
        boolean IsSoulsInSlot = (Utils.BrewSoulMap.containsKey(NormalSoul1.getItem())) ||
                (Utils.BrewSoulMap.containsKey(NormalSoul2.getItem())) ||
                (Utils.BrewSoulMap.containsKey(NormalSoul3.getItem())) ||
                (Utils.BrewSoulMap.containsKey(NormalSoul4.getItem()));
        boolean IsBrewingBookInSlot = BrewingNote.is(Moditems.BrewingNote.get());
        boolean SolidifiedSlotSameNormalSlot = true;
        if(!SolidifiedSoul.isEmpty()){
            if(Utils.BrewSoulMap.get(NormalSoulItem) == null) SolidifiedSlotSameNormalSlot = false;
            else
            {
                if(!SolidifiedSoul.equals(Utils.BrewSoulMap.get(NormalSoulItem).getDefaultInstance(),true)) SolidifiedSlotSameNormalSlot = false;
            }
        }
        return IsSameSoul && IsSolidifierInSlot && SolidifiedSoulSlot && IsBrewingBookInSlot && IsSoulsInSlot && SolidifiedSlotSameNormalSlot;
    }
    private static boolean hasRecipe4(HBrewingEntity blockEntity){ // 提升药水效果
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
        String PotionTag1 = " ";
        String PotionTag2 = " ";
        String PotionTag3 = " ";
        String PotionTag4 = " ";
        if(NormalPotion1.getOrCreateTag().contains("Potion")){
            PotionTag1 = NormalPotion1.getOrCreateTag().getString("Potion");
        }
        if(NormalPotion2.getOrCreateTag().contains("Potion")){
            PotionTag2 = NormalPotion2.getOrCreateTag().getString("Potion");
        }
        if(NormalPotion3.getOrCreateTag().contains("Potion")){
            PotionTag3 = NormalPotion3.getOrCreateTag().getString("Potion");
        }
        if(NormalPotion4.getOrCreateTag().contains("Potion")){
            PotionTag4 = NormalPotion4.getOrCreateTag().getString("Potion");
        }
        boolean IsSamePotion = true;
        if(!NormalPotion1.isEmpty()){
            if(!NormalPotion2.isEmpty() && !PotionTag2.equals(PotionTag1)) IsSamePotion = false;
            if(!NormalPotion3.isEmpty() && !PotionTag3.equals(PotionTag1)) IsSamePotion = false;
            if(!NormalPotion4.isEmpty() && !PotionTag4.equals(PotionTag1)) IsSamePotion = false;
        }
        else
        {
            if(!NormalPotion2.isEmpty()){
                if(!NormalPotion3.isEmpty() && !PotionTag3.equals(PotionTag2)) IsSamePotion = false;
                if(!NormalPotion4.isEmpty() && !PotionTag4.equals(PotionTag2)) IsSamePotion = false;
            }
            else
            {
                if(!NormalPotion3.isEmpty()){
                    if(!NormalPotion4.isEmpty() && !PotionTag4.equals(PotionTag3)) IsSamePotion = false;
                }
                else if(NormalPotion4.isEmpty()) IsSamePotion = false;
            }
        }
        Utils.PotionMapInit();
        boolean HasStabilizerInSlot = (Material1.is(Moditems.Concentrater.get()) && Material2.is(Moditems.Concentrater.get()))
                || (Material1.is(Moditems.Concentrater.get()) && Material2.isEmpty())
                || (Material1.isEmpty() && Material2.is(Moditems.Concentrater.get()));
        boolean IsVmdPotionInSlot = (Utils.PotionMap.containsKey(PotionTag1)) || (Utils.PotionMap.containsKey(PotionTag2))
                || (Utils.PotionMap.containsKey(PotionTag3)) || (Utils.PotionMap.containsKey(PotionTag4));
        boolean BrewedPotionIsEmpty = BrewedPotion.isEmpty();
        boolean BrewingNoteInSlot = BrewingNote.is(Moditems.BrewingNote.get());
        return HasStabilizerInSlot && IsVmdPotionInSlot && BrewedPotionIsEmpty && BrewingNoteInSlot && IsSamePotion;
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
        boolean MaterialCorrect = ((Material1.is(Moditems.SunPower.get()) && Material1.getCount() >= 6 && Material2.is(Moditems.Concentrater.get()))
                || (Material2.is(Moditems.SunPower.get()) && Material2.getCount() >= 6 && Material1.is(Moditems.Concentrater.get()))

                || (Material1.is(Moditems.LakeCore.get()) && Material1.getCount() >= 6 && Material2.is(Moditems.Concentrater.get()))
                || (Material2.is(Moditems.LakeCore.get()) && Material2.getCount() >= 6 && Material1.is(Moditems.Concentrater.get()))

                || (Material1.is(Moditems.VolcanoCore.get()) && Material1.getCount() >= 6 && Material2.is(Moditems.Concentrater.get()))
                || (Material2.is(Moditems.VolcanoCore.get()) && Material2.getCount() >= 6 && Material1.is(Moditems.Concentrater.get()))

                || (Material1.is(Moditems.SnowRune.get()) && Material1.getCount() >= 2 && Material2.is(Moditems.Concentrater.get()))
                || (Material2.is(Moditems.SnowRune.get()) && Material2.getCount() >= 2 && Material1.is(Moditems.Concentrater.get()))

                || (Material1.is(Moditems.SkyRune.get()) && Material1.getCount() >= 3 && Material2.is(Moditems.Concentrater.get()))
                || (Material2.is(Moditems.SkyRune.get()) && Material2.getCount() >= 3 && Material1.is(Moditems.Concentrater.get()))

                || (Material1.is(Moditems.EvokerRune.get()) && Material1.getCount() >= 1 && Material2.is(Moditems.Concentrater.get()))
                || (Material2.is(Moditems.EvokerRune.get()) && Material2.getCount() >= 1 && Material1.is(Moditems.Concentrater.get()))

                || (Material1.is(Moditems.ruby.get()) && Material1.getCount() >= 64 && Material2.is(Moditems.Concentrater.get()))
                || (Material2.is(Moditems.ruby.get()) && Material2.getCount() >= 64 && Material1.is(Moditems.Concentrater.get())));
        boolean HasNormalPotionInSlot = NormalPotion1.is(Moditems.PurifiedWater.get()) ||
                NormalPotion2.is(Moditems.PurifiedWater.get()) ||
                NormalPotion3.is(Moditems.PurifiedWater.get()) ||
                NormalPotion4.is(Moditems.PurifiedWater.get());
        boolean BrewingNoteInSlot = BrewingNote.is(Moditems.BrewingNote.get());
        boolean BrewedPotionIsEmpty = BrewedPotion.isEmpty();
        return MaterialCorrect && HasNormalPotionInSlot && BrewingNoteInSlot && BrewedPotionIsEmpty;
    }
}

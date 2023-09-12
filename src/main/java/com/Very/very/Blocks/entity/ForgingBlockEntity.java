package com.Very.very.Blocks.entity;

import com.Very.very.Items.gems.SlotOpen;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Gems.EvokerGem;
import com.Very.very.Series.OverWorldSeries.Forging.forgingstone0;
import com.Very.very.Series.OverWorldSeries.Forging.forgingstone1;
import com.Very.very.Series.OverWorldSeries.Forging.forgingstone2;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Gems.SkyGem;

import com.Very.very.Series.OverWorldSeries.MainStory_I.Gems.LakeGem;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Gems.ForestGem;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Gems.PlainGem;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Gems.SnowGem;
import com.Very.very.Series.OverWorldSeries.MainStory_I.Gems.VolcanoGem;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import com.Very.very.Render.Gui.Blocks.FirstBlockMenu;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

import net.minecraft.world.*;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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

public class ForgingBlockEntity extends BlockEntity implements MenuProvider{
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(5)
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
          return new FirstBlockMenu(id,inventory,this,this.data);
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

    public static void tick(Level level, BlockPos pos, BlockState blockState, ForgingBlockEntity blockEntity) {
        if(level.isClientSide()){
            if(blockEntity.progress >= blockEntity.maxProgress)
            {
                Player player = level.getNearestPlayer(TargetingConditions.DEFAULT,pos.getX(),pos.getY(),pos.getZ());
                player.playSound(SoundEvents.ANVIL_USE);
            }
            return ;
        }
        if(hasRecipe(blockEntity) || hasRecipe1(blockEntity) || hasRecipe2(blockEntity) || hasRecipe3(blockEntity)
                || hasRecipe4(blockEntity) || hasRecipe5(blockEntity) || hasRecipe6(blockEntity) || hasRecipe7(blockEntity)
                || hasRecipe8(blockEntity) || hasRecipe9(blockEntity) || hasRecipe10(blockEntity)) {
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

    protected static void craftItem(ForgingBlockEntity blockEntity) {
        // 镶嵌
        if(hasRecipe(blockEntity)){
            ItemStack gem = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(1);
            CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
            data.putInt("Slot",data.getInt("Slot")-1);
            if(gem.getItem() instanceof SkyGem)
            {
                if(!data.contains("Gems1")) data.putString("Gems1","skyGem");
                else
                {
                    if(!data.contains("Gems2")) data.putString("Gems2","skyGem");
                    else
                    {
                        if(!data.contains("Gems3")) data.putString("Gems3","skyGem");
                    }
                }
            }
            else if(gem.getItem() instanceof EvokerGem)
            {
                if(!data.contains("Gems1")) data.putString("Gems1","EvokerGem");
                else
                {
                    if(!data.contains("Gems2")) data.putString("Gems2","EvokerGem");
                    else
                    {
                        if(!data.contains("Gems3")) data.putString("Gems3","EvokerGem");
                    }
                }
            }
            else if(gem.getItem() instanceof PlainGem)
            {
                if(!data.contains("Gems1")) data.putString("Gems1","plainGem");
                else
                {
                    if(!data.contains("Gems2")) data.putString("Gems2","plainGem");
                    else
                    {
                        if(!data.contains("Gems3")) data.putString("Gems3","plainGem");
                    }
                }
            }
            else if(gem.getItem() instanceof ForestGem)
            {
                if(!data.contains("Gems1")) data.putString("Gems1","forestGem");
                else
                {
                    if(!data.contains("Gems2")) data.putString("Gems2","forestGem");
                    else
                    {
                        if(!data.contains("Gems3")) data.putString("Gems3","forestGem");
                    }
                }
            }
            else if(gem.getItem() instanceof LakeGem)
            {
                if(!data.contains("Gems1")) data.putString("Gems1","lakeGem");
                else
                {
                    if(!data.contains("Gems2")) data.putString("Gems2","lakeGem");
                    else
                    {
                        if(!data.contains("Gems3")) data.putString("Gems3","lakeGem");
                    }
                }
            }
            else if(gem.getItem() instanceof VolcanoGem)
            {
                if(!data.contains("Gems1")) data.putString("Gems1","volcanoGem");
                else
                {
                    if(!data.contains("Gems2")) data.putString("Gems2","volcanoGem");
                    else
                    {
                        if(!data.contains("Gems3")) data.putString("Gems3","volcanoGem");
                    }
                }
            }
            else if(gem.getItem() instanceof SnowGem)
            {
                if(!data.contains("Gems1")) data.putString("Gems1","snowGem");
                else
                {
                    if(!data.contains("Gems2")) data.putString("Gems2","snowGem");
                    else
                    {
                        if(!data.contains("Gems3")) data.putString("Gems3","snowGem");
                    }
                }
            }
            blockEntity.itemStackHandler.setStackInSlot(2,sword);
            blockEntity.itemStackHandler.extractItem(0,1,false);
            blockEntity.itemStackHandler.extractItem(1,1,false);
        }
        // 开孔
        if(hasRecipe1(blockEntity)){
            ItemStack slotOpen = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(4);
            CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
            Random r = new Random();
            if(!data.contains("MaxSlot"))
            {
                if(r.nextInt(100) < 90) {
                    data.putInt("Slot",1);
                    data.putInt("MaxSlot",1);
                    if(data.contains("Owner"))
                    {
                        Player player = blockEntity.level.getServer().getPlayerList().getPlayerByName(data.getString("Owner"));
                        Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),Component.literal("开孔成功。"));
                    }
                }
                else
                {
                    if(data.contains("Owner"))
                    {
                        Player player = blockEntity.level.getServer().getPlayerList().getPlayerByName(data.getString("Owner"));
                        Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),Component.literal("开孔失败。"));
                    }
                }
            }
            else if(data.getInt("MaxSlot") == 1)
            {
                if(r.nextInt(100) < 60) {
                    data.putInt("Slot",data.getInt("Slot")+1);
                    data.putInt("MaxSlot",2);
                    if(data.contains("Owner"))
                    {
                        Player player = blockEntity.level.getServer().getPlayerList().getPlayerByName(data.getString("Owner"));
                        Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),Component.literal("开孔成功。"));
                    }
                }
                else
                {
                    if(data.contains("Owner"))
                    {
                        Player player = blockEntity.level.getServer().getPlayerList().getPlayerByName(data.getString("Owner"));
                        Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),Component.literal("开孔失败。"));
                    }
                }
            }
            else if(data.getInt("MaxSlot") == 2)
            {
                if(r.nextInt(100) < 30) {
                    data.putInt("Slot",data.getInt("Slot")+1);
                    data.putInt("MaxSlot",3);
                    if(data.contains("Owner"))
                    {
                        Player player = blockEntity.level.getServer().getPlayerList().getPlayerByName(data.getString("Owner"));
                        Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),Component.literal("开孔成功。"));
                    }
                }
                else
                {
                    if(data.contains("Owner"))
                    {
                        Player player = blockEntity.level.getServer().getPlayerList().getPlayerByName(data.getString("Owner"));
                        Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),Component.literal("开孔失败。"));
                    }
                }
            }
            blockEntity.itemStackHandler.setStackInSlot(2,sword);
            blockEntity.itemStackHandler.extractItem(3,1,false);
            blockEntity.itemStackHandler.extractItem(4,1,false);
        }
        // 强化
        if(hasRecipe2(blockEntity)){
            ItemStack stone = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(1);
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            int forgelevel = 0;
            Random r = new Random();
            if(data.contains("Forging")) forgelevel = data.getInt("Forging");
            boolean flag = true;
            if(stone.getItem() instanceof forgingstone0){
                int tmpnum = r.nextInt(1,10);
                if(tmpnum > forgelevel) {
                    data.putInt("Forging",forgelevel + 1);
                    flag = false;
                }
            }
            if(stone.getItem() instanceof forgingstone1){
                int tmpnum = r.nextInt(1,20);
                if(tmpnum > forgelevel) {
                    data.putInt("Forging",forgelevel + 1);
                    flag = false;
                }
            }
            if(stone.getItem() instanceof forgingstone2){
                int tmpnum = r.nextInt(1,24);
                if(tmpnum > forgelevel) {
                    data.putInt("Forging",forgelevel + 1 );
                    flag = false;
                }
            }
            if(flag && data.contains("Owner")) {
                Player player = blockEntity.level.getServer().getPlayerList().getPlayerByName(data.getString("Owner"));
                player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("强化").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal("强化失败。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
            }
            if(!flag && data.contains("Owner")) Compute.Broad(blockEntity.level,(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("强化").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append(Component.literal("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)).append(Component.literal(data.getString("Owner")+"成功将 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).append(equip.getDisplayName()).append(Component.literal(" 强化至"+"+"+data.getInt("Forging")).withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE))));
            blockEntity.itemStackHandler.setStackInSlot(2,equip);
            blockEntity.itemStackHandler.extractItem(0,1,false);
            blockEntity.itemStackHandler.extractItem(1,1,false);
        }
        if(hasRecipe3(blockEntity))
        {
            ItemStack itemStack = Moditems.LightningRune.get().getDefaultInstance();
            itemStack.getOrCreateTagElement(Utils.MOD_ID);
            blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
            blockEntity.itemStackHandler.extractItem(1,64,false);
            blockEntity.itemStackHandler.extractItem(0,1,false);
        }
        if(hasRecipe4(blockEntity))
        {
            ItemStack itemStack = Moditems.BlackForestRune.get().getDefaultInstance();
            itemStack.getOrCreateTagElement(Utils.MOD_ID);
            blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
            blockEntity.itemStackHandler.extractItem(1,64,false);
            blockEntity.itemStackHandler.extractItem(0,6,false);
        }
        if(hasRecipe5(blockEntity))
        {
            ItemStack itemStack = Moditems.SeaRune.get().getDefaultInstance();
            itemStack.getOrCreateTagElement(Utils.MOD_ID);
            blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
            blockEntity.itemStackHandler.extractItem(1,64,false);
            blockEntity.itemStackHandler.extractItem(0,6,false);
        }
        if(hasRecipe6(blockEntity))
        {
            if  (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.NetherGem.get())) {
                ItemStack itemStack = Moditems.NetherGemPiece.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
            }
            else {
                ItemStack itemStack = Moditems.RandomGemPiece.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
            }
        }
        if(hasRecipe7(blockEntity))
        {
            ItemStack itemStack = Moditems.KazeRune.get().getDefaultInstance();
            itemStack.getOrCreateTagElement(Utils.MOD_ID);
            blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
            blockEntity.itemStackHandler.extractItem(1,64,false);
            blockEntity.itemStackHandler.extractItem(0,6,false);
        }
        if(hasRecipe10(blockEntity))
        {
            if (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.LightningRune.get())
                    && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 10) {
                ItemStack itemStack = Moditems.LightningPower.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
                blockEntity.itemStackHandler.extractItem(1,10,false);
                blockEntity.itemStackHandler.extractItem(0,64,false);
            }
            if (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.KazeRecallSoul.get())
                    && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4) {
                ItemStack itemStack = Moditems.IntensifiedKazeSoul.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
                blockEntity.itemStackHandler.extractItem(1,4,false);
                blockEntity.itemStackHandler.extractItem(0,64,false);
            }
            if (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.SpiderRecallSoul.get())
                    && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4) {
                ItemStack itemStack = Moditems.IntensifiedSpiderSoul.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
                blockEntity.itemStackHandler.extractItem(1,4,false);
                blockEntity.itemStackHandler.extractItem(0,64,false);
            }
            if (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.BlackForestRecallSoul.get())
                    && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4) {
                ItemStack itemStack = Moditems.IntensifiedBlackForestSoul.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
                blockEntity.itemStackHandler.extractItem(1,4,false);
                blockEntity.itemStackHandler.extractItem(0,64,false);
            }
            if (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.SeaRecallSoul.get())
                    && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4) {
                ItemStack itemStack = Moditems.IntensifiedSeaSoul.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
                blockEntity.itemStackHandler.extractItem(1,4,false);
                blockEntity.itemStackHandler.extractItem(0,64,false);
            }
            if (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.LightningRecallSoul.get())
                    && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4) {
                ItemStack itemStack = Moditems.IntensifiedLightningSoul.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
                blockEntity.itemStackHandler.extractItem(1,4,false);
                blockEntity.itemStackHandler.extractItem(0,64,false);
            }
            if (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.NetherRecallSoul.get())
                    && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4) {
                ItemStack itemStack = Moditems.IntensifiedNetherRecallSoul.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
                blockEntity.itemStackHandler.extractItem(1,4,false);
                blockEntity.itemStackHandler.extractItem(0,64,false);
            }
            if (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.SnowRecallSoul.get())
                    && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4) {
                ItemStack itemStack = Moditems.IntensifiedSnowRecallSoul.get().getDefaultInstance();
                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                blockEntity.itemStackHandler.setStackInSlot(2,itemStack);
                blockEntity.itemStackHandler.extractItem(1,4,false);
                blockEntity.itemStackHandler.extractItem(0,64,false);
            }
        }
        if (hasRecipe8(blockEntity)) {
            ItemStack Ointment = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack Equip = blockEntity.itemStackHandler.getStackInSlot(1);
            CompoundTag data = Equip.getOrCreateTagElement(Utils.MOD_ID);
            if (!data.contains("SIndex")) data.putInt("SIndex",1);
            String Slot = "#Slot#"+data.getInt("SIndex");
            String SlotName[] = {
                   Slot + "#SunPower" ,
                    Slot + "#LakePower" ,
                    Slot + "#VolcanoPower" ,
                    Slot + "#SnowPower" ,
                    Slot + "#SkyPower" ,
                    Slot + "#ManaPower" ,
                    Slot + "#NetherPower" ,
            };
            for (int i = 0; i < 7; i++) {
                data.remove(SlotName[i]);
            }
            Random r = new Random();
            if (Ointment.is(Moditems.SunOintment0.get()) || Ointment.is(Moditems.SunOintment1.get()) || Ointment.is(Moditems.SunOintment2.get())) {
                String SlotPName = Slot + "#SunPower";
                if (Ointment.is(Moditems.SunOintment0.get())) {
                    data.putFloat(SlotPName,r.nextFloat(0,7.5f));
                }
                else if (Ointment.is(Moditems.SunOintment1.get())) {
                    float num = r.nextFloat(2.5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(Moditems.SunOintment2.get())) {
                    float num = r.nextFloat(5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(Moditems.LakeOintment0.get()) || Ointment.is(Moditems.LakeOintment1.get()) || Ointment.is(Moditems.LakeOintment2.get())) {
                String SlotPName = Slot + "#LakePower";
                if (Ointment.is(Moditems.LakeOintment0.get())) {
                    data.putFloat(SlotPName,r.nextFloat(0,7.5f));
                }
                else if (Ointment.is(Moditems.LakeOintment1.get())) {
                    float num = r.nextFloat(2.5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(Moditems.LakeOintment2.get())) {
                    float num = r.nextFloat(5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(Moditems.VolcanoOintment0.get()) || Ointment.is(Moditems.VolcanoOintment1.get()) || Ointment.is(Moditems.VolcanoOintment2.get())) {
                String SlotPName = Slot + "#VolcanoPower";
                if (Ointment.is(Moditems.VolcanoOintment0.get())) {
                    data.putFloat(SlotPName,r.nextFloat(0,7.5f));
                }
                else if (Ointment.is(Moditems.VolcanoOintment1.get())) {
                    float num = r.nextFloat(2.5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(Moditems.VolcanoOintment2.get())) {
                    float num = r.nextFloat(5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(Moditems.SnowOintment0.get()) || Ointment.is(Moditems.SnowOintment1.get()) || Ointment.is(Moditems.SnowOintment2.get())) {
                String SlotPName = Slot + "#SnowPower";
                if (Ointment.is(Moditems.SnowOintment0.get())) {
                    data.putFloat(SlotPName,r.nextFloat(0,7.5f));
                }
                else if (Ointment.is(Moditems.SnowOintment1.get())) {
                    float num = r.nextFloat(2.5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(Moditems.SnowOintment2.get())) {
                    float num = r.nextFloat(5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(Moditems.SkyOintment0.get()) || Ointment.is(Moditems.SkyOintment1.get()) || Ointment.is(Moditems.SkyOintment2.get())) {
                String SlotPName = Slot + "#SkyPower";
                if (Ointment.is(Moditems.SkyOintment0.get())) {
                    data.putFloat(SlotPName,r.nextFloat(0,7.5f));
                }
                else if (Ointment.is(Moditems.SkyOintment1.get())) {
                    float num = r.nextFloat(2.5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(Moditems.SkyOintment2.get())) {
                    float num = r.nextFloat(5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(Moditems.ManaOintment0.get()) || Ointment.is(Moditems.ManaOintment1.get()) || Ointment.is(Moditems.ManaOintment2.get())) {
                String SlotPName = Slot + "#ManaPower";
                if (Ointment.is(Moditems.ManaOintment0.get())) {
                    data.putFloat(SlotPName,r.nextFloat(0,7.5f));
                }
                else if (Ointment.is(Moditems.ManaOintment1.get())) {
                    float num = r.nextFloat(2.5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(Moditems.ManaOintment2.get())) {
                    float num = r.nextFloat(5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(Moditems.NetherOintment0.get()) || Ointment.is(Moditems.NetherOintment1.get()) || Ointment.is(Moditems.NetherOintment2.get())) {
                String SlotPName = Slot + "#NetherPower";
                if (Ointment.is(Moditems.NetherOintment0.get())) {
                    data.putFloat(SlotPName,r.nextFloat(0,7.5f));
                }
                else if (Ointment.is(Moditems.NetherOintment1.get())) {
                    float num = r.nextFloat(2.5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(Moditems.NetherOintment2.get())) {
                    float num = r.nextFloat(5f,10f);
                    data.putFloat(SlotPName,num);
                    if(num >= 9 && data.contains("Owner")) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfSpider),
                            Component.literal(data.getString("Owner")+"使用").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (data.getInt("SIndex") == 5) data.putInt("SIndex",1);
            else data.putInt("SIndex",data.getInt("SIndex") + 1);
            blockEntity.itemStackHandler.setStackInSlot(2,Equip);
            blockEntity.itemStackHandler.extractItem(0,1,false);
            blockEntity.itemStackHandler.extractItem(1,1,false);
        }
        if (hasRecipe9(blockEntity)) {
            ItemStack Material = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack Equip = blockEntity.itemStackHandler.getStackInSlot(1);
            CompoundTag data = Equip.getOrCreateTagElement(Utils.MOD_ID);
            if (Equip.is(Moditems.KazeSword0.get()) && Material.is(Moditems.KazeRune.get()) && Material.getCount() >= 2) {
                ItemStack NewEquip = Moditems.KazeSword1.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,2,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.KazeSword1.get()) && Material.is(Moditems.KazeRune.get()) && Material.getCount() >= 3) {
                ItemStack NewEquip = Moditems.KazeSword2.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,3,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.KazeSword2.get()) && Material.is(Moditems.KazeRune.get()) && Material.getCount() >= 5) {
                ItemStack NewEquip = Moditems.KazeSword3.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,5,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.SeaSword0.get()) && Material.is(Moditems.SeaRune.get()) && Material.getCount() >= 2) {
                ItemStack NewEquip = Moditems.SeaSword1.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,2,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.SeaSword1.get()) && Material.is(Moditems.SeaRune.get()) && Material.getCount() >= 3) {
                ItemStack NewEquip = Moditems.SeaSword2.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,3,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.SeaSword2.get()) && Material.is(Moditems.SeaRune.get()) && Material.getCount() >= 5) {
                ItemStack NewEquip = Moditems.SeaSword3.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,5,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.BlackForestSword0.get()) && Material.is(Moditems.BlackForestRune.get()) && Material.getCount() >= 2) {
                ItemStack NewEquip = Moditems.BlackForestSword1.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,2,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.BlackForestSword1.get()) && Material.is(Moditems.BlackForestRune.get()) && Material.getCount() >= 3) {
                ItemStack NewEquip = Moditems.BlackForestSword2.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,3,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.BlackForestSword2.get()) && Material.is(Moditems.BlackForestRune.get()) && Material.getCount() >= 5) {
                ItemStack NewEquip = Moditems.BlackForestSword3.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,5,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.evokersword.get()) && Material.is(Moditems.LightningPower.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.CodeSceptre.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.KazeSword3.get()) && Material.is(Moditems.IntensifiedKazeSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.KazeSword4.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.SHelmet.get()) && Material.is(Moditems.IntensifiedSpiderSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.ISArmorHelmet.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.SChest.get()) && Material.is(Moditems.IntensifiedSpiderSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.ISArmorChest.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.SLeggings.get()) && Material.is(Moditems.IntensifiedSpiderSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.ISArmorLeggings.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.SBoots.get()) && Material.is(Moditems.IntensifiedSpiderSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.ISArmorBoots.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if  (Equip.is(Moditems.BlackForestSword3.get()) && Material.is(Moditems.IntensifiedBlackForestSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.BlackForestSword4.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if  (Equip.is(Moditems.SeaSword3.get()) && Material.is(Moditems.IntensifiedSeaSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.SeaSword4.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if  (Equip.is(Moditems.IslandArmorHelmet.get()) && Material.is(Moditems.IntensifiedLightningSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.ILArmorHelmet.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if  (Equip.is(Moditems.IslandArmorChest.get()) && Material.is(Moditems.IntensifiedLightningSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.ILArmorChest.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if  (Equip.is(Moditems.IslandArmorLeggings.get()) && Material.is(Moditems.IntensifiedLightningSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.ILArmorLeggings.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if  (Equip.is(Moditems.IslandArmorBoots.get()) && Material.is(Moditems.IntensifiedLightningSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.ILArmorBoots.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.ManaSword.get()) && Material.is(Moditems.IntensifiedNetherRecallSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.ManaSword1.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
            if (Equip.is(Moditems.snowsword3.get()) && Material.is(Moditems.IntensifiedSnowRecallSoul.get()) && Material.getCount() >= 1) {
                ItemStack NewEquip = Moditems.snowsword4.get().getDefaultInstance();
                NewEquip.getOrCreateTagElement(Utils.MOD_ID).merge(data);
                blockEntity.itemStackHandler.extractItem(0,1,false);
                blockEntity.itemStackHandler.extractItem(1,1,false);
                if (data.contains("Owner")) {
                    Compute.FormatBroad(blockEntity.level,Component.literal("打造").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY),
                            Component.literal(data.getString("Owner")+"成功打造了").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(NewEquip.getDisplayName()));
                }
                blockEntity.itemStackHandler.setStackInSlot(2,NewEquip);
            }
        }
    }

    private static boolean hasRecipe(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack gem = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(1);
        boolean hasGemInFirstSlot = (gem.getItem() instanceof SkyGem ||
                gem.getItem() instanceof EvokerGem ||
                gem.getItem() instanceof PlainGem ||
                gem.getItem() instanceof ForestGem ||
                gem.getItem() instanceof LakeGem ||
                gem.getItem() instanceof VolcanoGem ||
                gem.getItem() instanceof SnowGem);
        boolean hasSwordBowSceptreInSecondSlot = (Utils.MainHandTag.containsKey(sword.getItem()) ||
                Utils.ArmorTag.containsKey(sword.getItem()));
        CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
        if(data.getInt("Slot") == 0 ) return false;

        return hasGemInFirstSlot && canInsertItemIntoOutPutSlot(inventory) && hasSwordBowSceptreInSecondSlot &&
                inventory.getItem(2).isEmpty();
    }   //镶嵌

    private static boolean hasRecipe1(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }

        boolean hasOpenSlotInFirstSlot = blockEntity.itemStackHandler.getStackInSlot(3).getItem() instanceof SlotOpen;
        ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(4);
        boolean hasSwordBowSceptreInSecondSlot = (Utils.MainHandTag.containsKey(sword.getItem()) ||
                Utils.ArmorTag.containsKey(sword.getItem()));
        CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
        if(data.contains("MaxSlot") && data.getInt("MaxSlot") == 3) return false;
        if(Utils.ArmorTag.containsKey(sword.getItem()) && data.contains("MaxSlot") && data.getInt("MaxSlot") == 1) return false;
        return hasOpenSlotInFirstSlot && canInsertItemIntoOutPutSlot(inventory) && hasSwordBowSceptreInSecondSlot &&
                inventory.getItem(2).isEmpty();
    }   //开孔
    private static boolean hasRecipe2(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }

        boolean hasGemInFirstSlot = (blockEntity.itemStackHandler.getStackInSlot(0).getItem() instanceof forgingstone0
                || blockEntity.itemStackHandler.getStackInSlot(0).getItem() instanceof forgingstone1
                || blockEntity.itemStackHandler.getStackInSlot(0).getItem() instanceof forgingstone2);
        boolean hasSwordInSecondSlot = false;
        ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(1);
        CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
        if(data.contains("Forging") && data.getInt("Forging") >= 24) return false;
        if((Utils.MainHandTag.containsKey(sword.getItem())) || (Utils.ArmorTag.containsKey(sword.getItem())) )
        {
            hasSwordInSecondSlot = true;
        }

        return hasSwordInSecondSlot && canInsertItemIntoOutPutSlot(inventory) && hasGemInFirstSlot &&
                inventory.getItem(2).isEmpty();
    }   //强化
    private static boolean hasRecipe3(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        boolean hasLightningSoulInSecondSlot = blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.LightningSoul.get()) && blockEntity.itemStackHandler.getStackInSlot(1).getCount() == 64;
        boolean hasSpeIronInFirstSlot = blockEntity.itemStackHandler.getStackInSlot(0).is(Moditems.SpeIron.get()) && blockEntity.itemStackHandler.getStackInSlot(0).getCount() >= 1;
        return hasLightningSoulInSecondSlot && hasSpeIronInFirstSlot && canInsertItemIntoOutPutSlot(inventory);
    }
    private static boolean hasRecipe4(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        boolean hasBlackForestSoulInSecondSlot = blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.BlackForestSoul.get()) && blockEntity.itemStackHandler.getStackInSlot(1).getCount() == 64;
        boolean hasSunPowerInFirstSlot = blockEntity.itemStackHandler.getStackInSlot(0).is(Moditems.SunPower.get()) && blockEntity.itemStackHandler.getStackInSlot(0).getCount() >= 6;
        return hasSunPowerInFirstSlot && hasBlackForestSoulInSecondSlot && canInsertItemIntoOutPutSlot(inventory);
    }
    private static boolean hasRecipe5(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        boolean hasSeaSoulInSecondSlot = blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.SeaSoul.get()) && blockEntity.itemStackHandler.getStackInSlot(1).getCount() == 64;
        boolean hasSunPowerInFirstSlot = blockEntity.itemStackHandler.getStackInSlot(0).is(Moditems.SunPower.get()) && blockEntity.itemStackHandler.getStackInSlot(0).getCount() >= 6;
        return hasSunPowerInFirstSlot && hasSeaSoulInSecondSlot && canInsertItemIntoOutPutSlot(inventory);
    }
    private static boolean hasRecipe6(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        return (canInsertItemIntoOutPutSlot(inventory) && ((blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.Main1HandGem.get()))
                || (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.Main1BraceletGem.get()))
                || (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.Main1necklaceGem.get()))
                || (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.Main1BeltGem.get()))
                || (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.NetherGem.get()))));
    }
    private static boolean hasRecipe7(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        boolean hasSeaSoulInSecondSlot = blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.KazeSoul.get()) && blockEntity.itemStackHandler.getStackInSlot(1).getCount() == 64;
        boolean hasSunPowerInFirstSlot = blockEntity.itemStackHandler.getStackInSlot(0).is(Moditems.LakeCore.get()) && blockEntity.itemStackHandler.getStackInSlot(0).getCount() >= 6;
        return hasSunPowerInFirstSlot && hasSeaSoulInSecondSlot && canInsertItemIntoOutPutSlot(inventory);
    }
    private static boolean hasRecipe8(ForgingBlockEntity blockEntity) { //涂附
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack Ointment = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack Equip = blockEntity.itemStackHandler.getStackInSlot(1);
        boolean OintmentCorrect = Ointment.is(Moditems.SunOintment0.get()) || Ointment.is(Moditems.SunOintment1.get()) || Ointment.is(Moditems.SunOintment2.get())
                || Ointment.is(Moditems.LakeOintment0.get()) || Ointment.is(Moditems.LakeOintment1.get()) || Ointment.is(Moditems.LakeOintment2.get())
                || Ointment.is(Moditems.VolcanoOintment0.get()) || Ointment.is(Moditems.VolcanoOintment1.get()) || Ointment.is(Moditems.VolcanoOintment2.get())
                || Ointment.is(Moditems.SnowOintment0.get()) || Ointment.is(Moditems.SnowOintment1.get()) || Ointment.is(Moditems.SnowOintment2.get())
                || Ointment.is(Moditems.SkyOintment0.get()) || Ointment.is(Moditems.SkyOintment1.get()) || Ointment.is(Moditems.SkyOintment2.get())
                || Ointment.is(Moditems.ManaOintment0.get()) || Ointment.is(Moditems.ManaOintment1.get()) || Ointment.is(Moditems.ManaOintment2.get())
                || Ointment.is(Moditems.NetherOintment0.get()) || Ointment.is(Moditems.NetherOintment1.get()) || Ointment.is(Moditems.NetherOintment2.get());
        boolean EquipCorrect = Equip.is(Moditems.SBoots.get()) || Equip.is(Moditems.SLeggings.get())
                || Equip.is(Moditems.SChest.get()) || Equip.is(Moditems.SHelmet.get())
                || Equip.is(Moditems.ISArmorBoots.get()) || Equip.is(Moditems.ISArmorLeggings.get())
                || Equip.is(Moditems.ISArmorChest.get()) || Equip.is(Moditems.ISArmorHelmet.get());
        return OintmentCorrect && EquipCorrect && canInsertItemIntoOutPutSlot(inventory);
    }
    private static boolean hasRecipe9(ForgingBlockEntity blockEntity) { // 升级武器
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack Material = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack Equip = blockEntity.itemStackHandler.getStackInSlot(1);
        boolean UpMaterialCorrect = (Equip.is(Moditems.KazeSword0.get()) && Material.is(Moditems.KazeRune.get()) && Material.getCount() >= 2) ||
                (Equip.is(Moditems.KazeSword1.get()) && Material.is(Moditems.KazeRune.get()) && Material.getCount() >= 3) ||
                (Equip.is(Moditems.KazeSword2.get()) && Material.is(Moditems.KazeRune.get()) && Material.getCount() >= 5) ||
                (Equip.is(Moditems.SeaSword0.get()) && Material.is(Moditems.SeaRune.get()) && Material.getCount() >= 2) ||
                (Equip.is(Moditems.SeaSword1.get()) && Material.is(Moditems.SeaRune.get()) && Material.getCount() >= 3) ||
                (Equip.is(Moditems.SeaSword2.get()) && Material.is(Moditems.SeaRune.get()) && Material.getCount() >= 5) ||
                (Equip.is(Moditems.BlackForestSword0.get()) && Material.is(Moditems.BlackForestRune.get()) && Material.getCount() >= 2) ||
                (Equip.is(Moditems.BlackForestSword1.get()) && Material.is(Moditems.BlackForestRune.get()) && Material.getCount() >= 3) ||
                (Equip.is(Moditems.BlackForestSword2.get()) && Material.is(Moditems.BlackForestRune.get()) && Material.getCount() >= 5) ||
                (Equip.is(Moditems.evokersword.get()) && Material.is(Moditems.LightningPower.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.KazeSword3.get()) && Material.is(Moditems.IntensifiedKazeSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.SBoots.get()) && Material.is(Moditems.IntensifiedSpiderSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.SLeggings.get()) && Material.is(Moditems.IntensifiedSpiderSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.SChest.get()) && Material.is(Moditems.IntensifiedSpiderSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.SHelmet.get()) && Material.is(Moditems.IntensifiedSpiderSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.BlackForestSword3.get()) && Material.is(Moditems.IntensifiedBlackForestSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.SeaSword3.get()) && Material.is(Moditems.IntensifiedSeaSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.IslandArmorHelmet.get()) && Material.is(Moditems.IntensifiedLightningSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.IslandArmorChest.get()) && Material.is(Moditems.IntensifiedLightningSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.IslandArmorLeggings.get()) && Material.is(Moditems.IntensifiedLightningSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.IslandArmorBoots.get()) && Material.is(Moditems.IntensifiedLightningSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.ManaSword.get()) && Material.is(Moditems.IntensifiedNetherRecallSoul.get()) && Material.getCount() >= 1) ||
                (Equip.is(Moditems.snowsword3.get()) && Material.is(Moditems.IntensifiedSnowRecallSoul.get()) && Material.getCount() >= 1);
        return UpMaterialCorrect && canInsertItemIntoOutPutSlot(inventory);
    }

    private static boolean hasRecipe10(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        boolean hasSeaSoulInSecondSlot = (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.LightningRune.get())
                && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 10)
                || (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.KazeRecallSoul.get())
                && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4)
                || (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.SpiderRecallSoul.get())
                && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4)
                || (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.BlackForestRecallSoul.get())
                && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4)
                || (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.SeaRecallSoul.get())
                && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4)
                || (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.LightningRecallSoul.get())
                && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4)
                || (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.NetherRecallSoul.get())
                && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4)
                || (blockEntity.itemStackHandler.getStackInSlot(1).is(Moditems.SnowRecallSoul.get())
                && blockEntity.itemStackHandler.getStackInSlot(1).getCount() >= 4) ;

        boolean hasSunPowerInFirstSlot = blockEntity.itemStackHandler.getStackInSlot(0).is(Moditems.VolcanoCore.get())
                && blockEntity.itemStackHandler.getStackInSlot(0).getCount() == 64;
        return hasSunPowerInFirstSlot && hasSeaSoulInSecondSlot && canInsertItemIntoOutPutSlot(inventory);
    }
    private static boolean canInsertItemIntoOutPutSlot(SimpleContainer inventory) {
        return inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutPutSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }
    protected void signalOpenCount(Level p_155333_, BlockPos p_155334_, BlockState p_155335_, int p_155336_, int p_155337_) {
        Block block = p_155335_.getBlock();
        p_155333_.blockEvent(p_155334_, block, 1, p_155337_);
    }

}

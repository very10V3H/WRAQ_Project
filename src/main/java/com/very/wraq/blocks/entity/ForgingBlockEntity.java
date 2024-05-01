package com.very.wraq.blocks.entity;

import com.very.wraq.Items.Forging.ForgeEnhance;
import com.very.wraq.Items.Forging.ForgeImprove;
import com.very.wraq.Items.Forging.ForgeProtect;
import com.very.wraq.Items.Gems.Dismantle;
import com.very.wraq.Items.Gems.SlotOpen;
import com.very.wraq.events.core.InventoryCheck;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.render.gui.blocks.ForgingBlockMenu;
import com.very.wraq.series.instance.Castle.CastleAttackArmor;
import com.very.wraq.series.instance.Castle.CastleManaArmor;
import com.very.wraq.series.instance.Castle.CastleSwiftArmor;
import com.very.wraq.series.overWorld.Forging.forgingstone0;
import com.very.wraq.series.overWorld.Forging.forgingstone1;
import com.very.wraq.series.overWorld.Forging.forgingstone2;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
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

import java.util.*;

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
        return new ForgingBlockMenu(id,inventory,this,this.data);
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

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for(int i = 0; i < itemStackHandler.getSlots(); i++){
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level,this.worldPosition,inventory);
    }

    public void drops(Player player) {
        for(int i = 0; i < itemStackHandler.getSlots(); i++){
            player.addItem(itemStackHandler.getStackInSlot(i));
            itemStackHandler.setStackInSlot(i,Items.AIR.getDefaultInstance());
        }
    }

    public void clear() {
        for (int i = 0; i < itemStackHandler.getSlots(); i ++) {
            itemStackHandler.setStackInSlot(i,Items.AIR.getDefaultInstance());
        }
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
        if(hasRecipe(blockEntity) || hasRecipe1(blockEntity) || hasRecipe2(blockEntity) || hasRecipe6(blockEntity)
                || hasRecipe8(blockEntity) || hasRecipeOfDismantle(blockEntity) || hasRecipeOfCastleArmor(blockEntity)
                || hasRecipeOfForgePaper(blockEntity)) {
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

        String PlayerName = Utils.whoIsUsingBlock.get(blockEntity.getBlockPos());
        Player player = null;
        if (blockEntity.level.getServer().getPlayerList().getPlayerByName(PlayerName) != null)
            player = blockEntity.level.getServer().getPlayerList().getPlayerByName(PlayerName);

        if (hasRecipeOfForgePaper(blockEntity)) {
            ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(4);
            ItemStack forgePaper = blockEntity.itemStackHandler.getStackInSlot(3);
            CompoundTag data = equip.getTagElement(Utils.MOD_ID);

            if (forgePaper.is(ModItems.QingMingForgePaper.get())) data.putBoolean(StringUtils.QingMingForgePaper,true);
            if (forgePaper.is(ModItems.LabourDayForgePaper.get())) data.putBoolean(StringUtils.LabourDayForgePaper,true);

            Compute.ForgingHoverName(equip,Component.literal(""));
            if (player != null) {
                Compute.FormatBroad(player.level(),Component.literal("突破").withStyle(CustomStyle.styleOfPower),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 使用 ").withStyle(ChatFormatting.WHITE)).
                                append(forgePaper.getDisplayName()).
                                append(Component.literal(" 增幅了 ").withStyle(CustomStyle.styleOfPower)).
                                append(equip.getDisplayName()));
            }

            blockEntity.itemStackHandler.setStackInSlot(2,equip);
            blockEntity.itemStackHandler.extractItem(3,1,false);
            blockEntity.itemStackHandler.extractItem(4,1,false);
        } // 清符

        if (hasRecipeOfCastleArmor(blockEntity)) {
            ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(1);
            ItemStack north = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack south = blockEntity.itemStackHandler.getStackInSlot(4);
            if (equip.getItem() instanceof CastleAttackArmor) {
                CastleAttackArmor.ForgeArmor(equip, north.is(ModItems.CastleCrystalNorth.get()), south.is(ModItems.CastleCrystalSouth.get()));
            } else if (equip.getItem() instanceof CastleSwiftArmor) {
                CastleSwiftArmor.ForgeArmor(equip,north.is(ModItems.CastleCrystalNorth.get()), south.is(ModItems.CastleCrystalSouth.get()));
            } else if (equip.getItem() instanceof CastleManaArmor) {
                CastleManaArmor.ForgeArmor(equip,north.is(ModItems.CastleCrystalNorth.get()), south.is(ModItems.CastleCrystalSouth.get()));
            }

            if (player != null) {
                Compute.FormatBroad(player.level(),Component.literal("魔能注入").withStyle(CustomStyle.styleOfCastleCrystal),
                        Component.literal("").withStyle(ChatFormatting.WHITE).
                                append(player.getDisplayName()).
                                append(Component.literal(" 对 ").withStyle(ChatFormatting.WHITE)).
                                append(equip.getDisplayName()).
                                append(Component.literal(" 完成了一次").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("魔能注入").withStyle(CustomStyle.styleOfCastleCrystal)));
            }

            blockEntity.itemStackHandler.setStackInSlot(2,equip);
            blockEntity.itemStackHandler.extractItem(0,1,false);
            blockEntity.itemStackHandler.extractItem(1,1,false);
            if (north.is(ModItems.CastleCrystalNorth.get())) blockEntity.itemStackHandler.extractItem(3,1,false);
            if ((south.is(ModItems.CastleCrystalSouth.get()))) blockEntity.itemStackHandler.extractItem(4,1,false);
        }

        if (hasRecipeOfDismantle(blockEntity)) {
            ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(4);
            CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
            int index = data.getInt("MaxSlot") - data.getInt("Slot");

            Map<String, Item> stringItemMap = new HashMap<>(){{
                this.put(StringUtils.GemName.SkyGem,ModItems.SkyGem.get());
                this.put(StringUtils.GemName.EvokerGem,ModItems.EvokerGem.get());
                this.put(StringUtils.GemName.PlainGem,ModItems.PlainGem.get());
                this.put(StringUtils.GemName.ForestGem,ModItems.ForestGem.get());
                this.put(StringUtils.GemName.LakeGem,ModItems.LakeGem.get());
                this.put(StringUtils.GemName.VolcanoGem,ModItems.VolcanoGem.get());
                this.put(StringUtils.GemName.SnowGem,ModItems.SnowGem.get());
                this.put(StringUtils.GemName.FieldGem,ModItems.FieldGem.get());
                this.put(StringUtils.GemName.MineGem,ModItems.MineGem.get());
                this.put(StringUtils.GemName.LifeManaGem,ModItems.LifeManaGem.get());
                this.put(StringUtils.GemName.ObsiManaGem,ModItems.ObsiManaGem.get());
                this.put(StringUtils.GemName.NetherSkeletonGem,ModItems.NetherSkeletonGem.get());
                this.put(StringUtils.GemName.MagmaGem,ModItems.MagmaGem.get());
                this.put(StringUtils.GemName.WitherGem,ModItems.WitherGem.get());
                this.put(StringUtils.GemName.SakuraGem,ModItems.SakuraGem.get());
                this.put(StringUtils.GemName.ShipGem,ModItems.ShipGem.get());
                this.put(StringUtils.GemName.PiglinGem,ModItems.PiglinGem.get());
                this.put(StringUtils.GemName.MoonAttackGem,ModItems.MoonAttackGem.get());
                this.put(StringUtils.GemName.MoonManaGem,ModItems.MoonManaGem.get());
                this.put(StringUtils.GemName.SkyGemD,ModItems.SkyGemD.get());
                this.put(StringUtils.GemName.EvokerGemD,ModItems.EvokerGemD.get());
                this.put(StringUtils.GemName.PlainGemD,ModItems.PlainGemD.get());
                this.put(StringUtils.GemName.ForestGemD,ModItems.ForestGemD.get());
                this.put(StringUtils.GemName.LakeGemD,ModItems.LakeGemD.get());
                this.put(StringUtils.GemName.VolcanoGemD,ModItems.VolcanoGemD.get());
                this.put(StringUtils.GemName.SnowGemD,ModItems.SnowGemD.get());
                this.put(StringUtils.GemName.FieldGemD,ModItems.FieldGemD.get());
                this.put(StringUtils.GemName.MineGemD,ModItems.MineGemD.get());
                this.put(StringUtils.GemName.LifeManaGemD,ModItems.LifeManaGemD.get());
                this.put(StringUtils.GemName.ObsiManaGemD,ModItems.ObsiManaGemD.get());
                this.put(StringUtils.GemName.NetherSkeletonGemD,ModItems.NetherSkeletonGemD.get());
                this.put(StringUtils.GemName.MagmaGemD,ModItems.MagmaGemD.get());
                this.put(StringUtils.GemName.WitherGemD,ModItems.WitherGemD.get());
                this.put(StringUtils.GemName.SakuraGemD,ModItems.SakuraGemD.get());
                this.put(StringUtils.GemName.ShipGemD,ModItems.ShipGemD.get());
                this.put(StringUtils.GemName.PiglinGemD,ModItems.PiglinGemD.get());
                this.put(StringUtils.GemName.MoonAttackGemD,ModItems.MoonAttackGemD.get());
                this.put(StringUtils.GemName.MoonManaGemD,ModItems.MoonManaGemD.get());
                this.put(StringUtils.GemName.CastleWeaponGem,ModItems.CastleWeaponGem.get());
                this.put(StringUtils.GemName.CastleArmorGem,ModItems.CastleArmorGem.get());
                this.put(StringUtils.GemName.QingMingGem,ModItems.QingMingGem.get());
                this.put(StringUtils.GemName.LabourDayGem,ModItems.LabourDayGem.get());
            }};

            String GemName = data.getString("Gems" + index);
            ItemStack itemStack = stringItemMap.get(GemName).getDefaultInstance();
            data.remove("Gems" + index);
            data.putInt("Slot",data.getInt("Slot") + 1);
            blockEntity.itemStackHandler.setStackInSlot(2,sword);
            blockEntity.itemStackHandler.extractItem(4,1,false);
            blockEntity.itemStackHandler.setStackInSlot(0,itemStack);
            blockEntity.itemStackHandler.extractItem(3,1,false);
        }
        // 镶嵌
        if(hasRecipe(blockEntity)){
            ItemStack gem = blockEntity.itemStackHandler.getStackInSlot(0);
            ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(1);
            CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
            data.putInt("Slot",data.getInt("Slot") - 1);

            Map<Item,String> itemStringMap = new HashMap<>(){{
                put(ModItems.SkyGem.get(),StringUtils.GemName.SkyGem);
                put(ModItems.EvokerGem.get(),StringUtils.GemName.EvokerGem);
                put(ModItems.PlainGem.get(),StringUtils.GemName.PlainGem);
                put(ModItems.ForestGem.get(), StringUtils.GemName.ForestGem);
                put(ModItems.LakeGem.get(),StringUtils.GemName.LakeGem);
                put(ModItems.VolcanoGem.get(),StringUtils.GemName.VolcanoGem);
                put(ModItems.SnowGem.get(),StringUtils.GemName.SnowGem);
                put(ModItems.FieldGem.get(),StringUtils.GemName.FieldGem);
                put(ModItems.MineGem.get(),StringUtils.GemName.MineGem);
                put(ModItems.LifeManaGem.get(),StringUtils.GemName.LifeManaGem);
                put(ModItems.ObsiManaGem.get(),StringUtils.GemName.ObsiManaGem);
                put(ModItems.NetherSkeletonGem.get(),StringUtils.GemName.NetherSkeletonGem);
                put(ModItems.MagmaGem.get(),StringUtils.GemName.MagmaGem);
                put(ModItems.WitherGem.get(),StringUtils.GemName.WitherGem);
                put(ModItems.SakuraGem.get(),StringUtils.GemName.SakuraGem);
                put(ModItems.ShipGem.get(),StringUtils.GemName.ShipGem);
                put(ModItems.PiglinGem.get(),StringUtils.GemName.PiglinGem);
                put(ModItems.MoonAttackGem.get(),StringUtils.GemName.MoonAttackGem);
                put(ModItems.MoonManaGem.get(),StringUtils.GemName.MoonManaGem);
                put(ModItems.SkyGemD.get(),StringUtils.GemName.SkyGemD);
                put(ModItems.EvokerGemD.get(),StringUtils.GemName.EvokerGemD);
                put(ModItems.PlainGemD.get(),StringUtils.GemName.PlainGemD);
                put(ModItems.ForestGemD.get(), StringUtils.GemName.ForestGemD);
                put(ModItems.LakeGemD.get(),StringUtils.GemName.LakeGemD);
                put(ModItems.VolcanoGemD.get(),StringUtils.GemName.VolcanoGemD);
                put(ModItems.SnowGemD.get(),StringUtils.GemName.SnowGemD);
                put(ModItems.FieldGemD.get(),StringUtils.GemName.FieldGemD);
                put(ModItems.MineGemD.get(),StringUtils.GemName.MineGemD);
                put(ModItems.LifeManaGemD.get(),StringUtils.GemName.LifeManaGemD);
                put(ModItems.ObsiManaGemD.get(),StringUtils.GemName.ObsiManaGemD);
                put(ModItems.NetherSkeletonGemD.get(),StringUtils.GemName.NetherSkeletonGemD);
                put(ModItems.MagmaGemD.get(),StringUtils.GemName.MagmaGemD);
                put(ModItems.WitherGemD.get(),StringUtils.GemName.WitherGemD);
                put(ModItems.SakuraGemD.get(),StringUtils.GemName.SakuraGemD);
                put(ModItems.ShipGemD.get(),StringUtils.GemName.ShipGemD);
                put(ModItems.PiglinGemD.get(),StringUtils.GemName.PiglinGemD);
                put(ModItems.MoonAttackGemD.get(),StringUtils.GemName.MoonAttackGemD);
                put(ModItems.MoonManaGemD.get(),StringUtils.GemName.MoonManaGemD);

                put(ModItems.CastleWeaponGem.get(),StringUtils.GemName.CastleWeaponGem);
                put(ModItems.CastleArmorGem.get(),StringUtils.GemName.CastleArmorGem);

                put(ModItems.QingMingGem.get(),StringUtils.GemName.QingMingGem);
                put(ModItems.LabourDayGem.get(),StringUtils.GemName.LabourDayGem);
            }};

            if (itemStringMap.containsKey(gem.getItem())) {
                for (int i = 1 ; i <= 3 ; i ++) {
                    if (!data.contains("Gems" + i)) {
                        data.putString("Gems" + i,itemStringMap.get(gem.getItem()));
                        break;
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
                    if (player != null) {
                        Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔成功。").withStyle(ChatFormatting.WHITE));
                    }
                }
                else
                {
                    if (player != null) {
                        Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔失败。").withStyle(ChatFormatting.WHITE));
                    }
                }
            }
            else if(data.getInt("MaxSlot") == 1)
            {
                if(r.nextInt(100) < 60) {
                    data.putInt("Slot",data.getInt("Slot")+1);
                    data.putInt("MaxSlot",2);
                    if (player != null) {
                        Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔成功。").withStyle(ChatFormatting.WHITE));
                    }
                }
                else
                {
                    if(data.contains(InventoryCheck.owner))
                    {
                        if (player != null) {
                            Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                    Component.literal("开孔失败。").withStyle(ChatFormatting.WHITE));
                        }
                    }
                }
            }
            else if(data.getInt("MaxSlot") == 2)
            {
                if(r.nextInt(100) < 30) {
                   data.putInt("Slot",data.getInt("Slot")+1);
                    data.putInt("MaxSlot",3);
                    if (player != null) {
                        Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔成功。").withStyle(ChatFormatting.WHITE));
                    }
                }
                else
                {
                    if(data.contains(InventoryCheck.owner))
                    {
                        if (player != null) {
                            Compute.FormatMSGSend(player,Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                    Component.literal("开孔失败。").withStyle(ChatFormatting.WHITE));
                        }
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
            ItemStack EnhanceOrImprove = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack Protect = blockEntity.itemStackHandler.getStackInSlot(4);
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            Compute.ForgingHoverName(equip,Component.literal(""));
            int forgelevel = 0;
            Random r = new Random();
            if(data.contains("Forging")) forgelevel = data.getInt("Forging");
            boolean flag = true;
            if(stone.getItem() instanceof forgingstone0){
                double SuccessRate = (double) (10 - forgelevel) / 10;
                if (EnhanceOrImprove.is(ModItems.ForgeEnhance0.get())) SuccessRate *= (1.25);
                if (EnhanceOrImprove.is(ModItems.ForgeEnhance1.get())) SuccessRate *= (1.5);
                if (EnhanceOrImprove.is(ModItems.ForgeEnhance2.get())) SuccessRate *= (2);
                if (EnhanceOrImprove.is(ModItems.ForgeImprove0.get())) SuccessRate += 0.05;
                if (EnhanceOrImprove.is(ModItems.ForgeImprove1.get())) SuccessRate += 0.1;
                if (EnhanceOrImprove.is(ModItems.ForgeImprove2.get())) SuccessRate += 0.2;
                if(r.nextDouble(1) < SuccessRate) {
                    data.putInt("Forging",forgelevel + 1);
                    flag = false;
                }
            }
            if(stone.getItem() instanceof forgingstone1){
                double SuccessRate = (double) (20 - forgelevel) / 20;
                if (EnhanceOrImprove.is(ModItems.ForgeEnhance0.get())) SuccessRate *= (1.25);
                if (EnhanceOrImprove.is(ModItems.ForgeEnhance1.get())) SuccessRate *= (1.5);
                if (EnhanceOrImprove.is(ModItems.ForgeEnhance2.get())) SuccessRate *= (2);
                if (EnhanceOrImprove.is(ModItems.ForgeImprove0.get())) SuccessRate += 0.05;
                if (EnhanceOrImprove.is(ModItems.ForgeImprove1.get())) SuccessRate += 0.1;
                if (EnhanceOrImprove.is(ModItems.ForgeImprove2.get())) SuccessRate += 0.2;
                if(r.nextDouble(1) < SuccessRate) {
                    data.putInt("Forging",forgelevel + 1);
                    flag = false;
                }
            }
            if(stone.getItem() instanceof forgingstone2){
                double SuccessRate = (double) (24 - forgelevel) / 24;
                if (EnhanceOrImprove.is(ModItems.ForgeEnhance0.get())) SuccessRate *= (1.25);
                if (EnhanceOrImprove.is(ModItems.ForgeEnhance1.get())) SuccessRate *= (1.5);
                if (EnhanceOrImprove.is(ModItems.ForgeEnhance2.get())) SuccessRate *= (2);
                if (EnhanceOrImprove.is(ModItems.ForgeImprove0.get())) SuccessRate += 0.05;
                if (EnhanceOrImprove.is(ModItems.ForgeImprove1.get())) SuccessRate += 0.1;
                if (EnhanceOrImprove.is(ModItems.ForgeImprove2.get())) SuccessRate += 0.2;
                if(r.nextDouble(1) < SuccessRate) {
                    data.putInt("Forging",forgelevel + 1);
                    flag = false;
                }
            }
            boolean useProtect = false;
            if (player != null) {
                if (flag) {
                    if (forgelevel >= 17 && forgelevel < 19 && !Protect.is(ModItems.ForgeProtect.get())) {
                        Compute.FormatBroad(player.level(),Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，装备等级掉至 16").withStyle(ChatFormatting.WHITE)));
                        data.putInt("Forging",16);
                    }
                    if (forgelevel >= 20 && forgelevel < 22 && !Protect.is(ModItems.ForgeProtect.get())) {
                        Compute.FormatBroad(player.level(),Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，装备等级掉至 19").withStyle(ChatFormatting.WHITE)));
                        data.putInt("Forging",19);
                    }
                    if (forgelevel >= 23 && forgelevel <= 24 && !Protect.is(ModItems.ForgeProtect.get())) {
                        Compute.FormatBroad(player.level(),Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，装备等级掉至 22").withStyle(ChatFormatting.WHITE)));
                        data.putInt("Forging",22);
                    }

                    if (forgelevel >= 17 && Protect.is(ModItems.ForgeProtect.get())) {
                        useProtect = true;
                        Compute.FormatMSGSend(player,Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("使用了强化保护符，防止了强化等级掉落。").withStyle(ChatFormatting.WHITE));
                        Compute.FormatBroad(player.level(),Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，好在使用了强化保护符。").withStyle(ChatFormatting.WHITE)));
                    }

                    Compute.FormatMSGSend(player,Component.literal("强化").withStyle(ChatFormatting.AQUA),
                            Component.literal("强化失败。").withStyle(ChatFormatting.GRAY));

                }
                else {
                    if (forgelevel >= 17) Compute.Broad(blockEntity.level,(Component.literal("[").withStyle(ChatFormatting.GRAY).
                            append(Component.literal("强化").withStyle(ChatFormatting.AQUA)).
                            append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                            append(player.getDisplayName()).
                            append(" 成功将 ").withStyle(ChatFormatting.WHITE)).
                            append(equip.getDisplayName()).
                            append(Component.literal(" 强化至"+"+"+data.getInt("Forging")).withStyle(ChatFormatting.WHITE)));
                    Compute.FormatMSGSend(player,Component.literal("强化").withStyle(ChatFormatting.AQUA),
                            Component.literal("强化成功!").withStyle(ChatFormatting.AQUA));
                }
            }

            Compute.ForgingHoverName(equip,Component.literal("").withStyle(ChatFormatting.BOLD));
            blockEntity.itemStackHandler.setStackInSlot(2,equip);
            blockEntity.itemStackHandler.extractItem(0,1,false);
            blockEntity.itemStackHandler.extractItem(1,1,false);
            if (EnhanceOrImprove.getItem() instanceof ForgeImprove
                    || EnhanceOrImprove.getItem() instanceof ForgeEnhance) blockEntity.itemStackHandler.extractItem(3,1,false);
            if (useProtect && Protect.getItem() instanceof ForgeProtect) blockEntity.itemStackHandler.extractItem(4,1,false);
        }
        if(hasRecipe6(blockEntity))
        {
            if  (blockEntity.itemStackHandler.getStackInSlot(1).is(ModItems.NetherGem.get())) {
                blockEntity.itemStackHandler.extractItem(1,1,false);
                ItemStack product = blockEntity.itemStackHandler.getStackInSlot(2);
                blockEntity.itemStackHandler.setStackInSlot(2,new ItemStack(ModItems.NetherGemPiece.get(),product.getCount() + 1));
            }
            else {
                blockEntity.itemStackHandler.extractItem(1,1,false);
                ItemStack product = blockEntity.itemStackHandler.getStackInSlot(2);
                blockEntity.itemStackHandler.setStackInSlot(2,new ItemStack(ModItems.RandomGemPiece.get(),product.getCount() + 1));
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
            if (Ointment.is(ModItems.SunOintment0.get()) || Ointment.is(ModItems.SunOintment1.get()) || Ointment.is(ModItems.SunOintment2.get())) {
                String SlotPName = Slot + "#SunPower";
                if (Ointment.is(ModItems.SunOintment0.get())) {
                    data.putDouble(SlotPName,r.nextDouble(0,7.5f));
                }
                else if (Ointment.is(ModItems.SunOintment1.get())) {
                    double num = r.nextDouble(2.5d,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(ModItems.SunOintment2.get())) {
                    double num = r.nextDouble(5d,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.LakeOintment0.get()) || Ointment.is(ModItems.LakeOintment1.get()) || Ointment.is(ModItems.LakeOintment2.get())) {
                String SlotPName = Slot + "#LakePower";
                if (Ointment.is(ModItems.LakeOintment0.get())) {
                    data.putDouble(SlotPName,r.nextDouble(0,7.5f));
                }
                else if (Ointment.is(ModItems.LakeOintment1.get())) {
                    double num = r.nextDouble(2.5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(ModItems.LakeOintment2.get())) {
                    double num = r.nextDouble(5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.VolcanoOintment0.get()) || Ointment.is(ModItems.VolcanoOintment1.get()) || Ointment.is(ModItems.VolcanoOintment2.get())) {
                String SlotPName = Slot + "#VolcanoPower";
                if (Ointment.is(ModItems.VolcanoOintment0.get())) {
                    data.putDouble(SlotPName,r.nextDouble(0,7.5f));
                }
                else if (Ointment.is(ModItems.VolcanoOintment1.get())) {
                    double num = r.nextDouble(2.5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(ModItems.VolcanoOintment2.get())) {
                    double num = r.nextDouble(5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.SnowOintment0.get()) || Ointment.is(ModItems.SnowOintment1.get()) || Ointment.is(ModItems.SnowOintment2.get())) {
                String SlotPName = Slot + "#SnowPower";
                if (Ointment.is(ModItems.SnowOintment0.get())) {
                    data.putDouble(SlotPName,r.nextDouble(0,7.5f));
                }
                else if (Ointment.is(ModItems.SnowOintment1.get())) {
                    double num = r.nextDouble(2.5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(ModItems.SnowOintment2.get())) {
                    double num = r.nextDouble(5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.SkyOintment0.get()) || Ointment.is(ModItems.SkyOintment1.get()) || Ointment.is(ModItems.SkyOintment2.get())) {
                String SlotPName = Slot + "#SkyPower";
                if (Ointment.is(ModItems.SkyOintment0.get())) {
                    data.putDouble(SlotPName,r.nextDouble(0,7.5f));
                }
                else if (Ointment.is(ModItems.SkyOintment1.get())) {
                    double num = r.nextDouble(2.5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(ModItems.SkyOintment2.get())) {
                    double num = r.nextDouble(5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.ManaOintment0.get()) || Ointment.is(ModItems.ManaOintment1.get()) || Ointment.is(ModItems.ManaOintment2.get())) {
                String SlotPName = Slot + "#ManaPower";
                if (Ointment.is(ModItems.ManaOintment0.get())) {
                    data.putDouble(SlotPName,r.nextDouble(0,7.5f));
                }
                else if (Ointment.is(ModItems.ManaOintment1.get())) {
                    double num = r.nextDouble(2.5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(ModItems.ManaOintment2.get())) {
                    double num = r.nextDouble(5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
            }
            if (Ointment.is(ModItems.NetherOintment0.get()) || Ointment.is(ModItems.NetherOintment1.get()) || Ointment.is(ModItems.NetherOintment2.get())) {
                String SlotPName = Slot + "#NetherPower";
                if (Ointment.is(ModItems.NetherOintment0.get())) {
                    data.putDouble(SlotPName,r.nextDouble(0,7.5f));
                }
                else if (Ointment.is(ModItems.NetherOintment1.get())) {
                    double num = r.nextDouble(2.5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
                                    append(Ointment.getDisplayName()).
                                    append(Component.literal("在")).
                                    append(Equip.getDisplayName()).
                                    append(Component.literal("上完成了一次史诗涂附！")));
                }
                else if (Ointment.is(ModItems.NetherOintment2.get())) {
                    double num = r.nextDouble(5f,10d);
                    data.putDouble(SlotPName,num);
                    if(num >= 9 && data.contains(InventoryCheck.owner)) Compute.FormatBroad(blockEntity.level, Component.literal("涂附").withStyle(CustomStyle.styleOfSpider),
                            Component.literal(data.getString(InventoryCheck.owner)+"使用").withStyle(ChatFormatting.WHITE).
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
    }

    private static boolean hasRecipeOfDismantle(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack gem = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(4);

        boolean hasDismantleInFirstSlot = blockEntity.itemStackHandler.getStackInSlot(3).getItem() instanceof Dismantle;

        CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
        if (data.getInt("MaxSlot") == 0 || data.getInt("MaxSlot") == data.getInt("Slot")) return false;
        return hasDismantleInFirstSlot && gem.is(Items.AIR);
    }

    private static boolean hasRecipe(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack gem = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(1);
        boolean hasGemInFirstSlot = Utils.GemsTag.containsKey(gem.getItem());
        boolean hasSwordBowSceptreInSecondSlot = (Utils.MainHandTag.containsKey(sword.getItem()) ||
                Utils.ArmorTag.containsKey(sword.getItem()));
        CompoundTag data = sword.getOrCreateTagElement(Utils.MOD_ID);
        if(data.getInt("Slot") == 0 ) return false;
        boolean correctType = (!gem.getItem().equals(ModItems.CastleWeaponGem.get()) || Utils.MainHandTag.containsKey(sword.getItem()))
                && (!gem.getItem().equals(ModItems.CastleArmorGem.get()) || Utils.ArmorTag.containsKey(sword.getItem())) ;
        return hasGemInFirstSlot && canInsertItemIntoOutPutSlot(inventory) && hasSwordBowSceptreInSecondSlot &&
                inventory.getItem(2).isEmpty() && correctType;
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
                inventory.getItem(2).isEmpty() && !Compute.IsSoulEquip(sword);
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
                inventory.getItem(2).isEmpty() && !Compute.IsSoulEquip(sword);
    }   //强化
    private static boolean hasRecipe6(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack gem = blockEntity.itemStackHandler.getStackInSlot(1);
        ItemStack product = blockEntity.itemStackHandler.getStackInSlot(2);
        if ((gem.is(ModItems.Main1BraceletGem.get()) || gem.is(ModItems.Main1necklaceGem.get())
                || gem.is(ModItems.Main1BeltGem.get()) || gem.is(ModItems.Main1HandGem.get()))
                && (product.is(ModItems.RandomGemPiece.get()) || product.is(Items.AIR)) && product.getCount() < 64) return true;
        return gem.is(ModItems.NetherGem.get()) && (product.is(ModItems.NetherGemPiece.get()) || product.is(Items.AIR)) && product.getCount() < 64;
    }
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

        boolean hasEquipCanBeForged = (Utils.MainHandTag.containsKey(equip.getItem()) ||
                Utils.ArmorTag.containsKey(equip.getItem()));

        return (canUseQingMingForgePaper || canUseLabourDayForgePaper) && data.contains("Forging")
                && canInsertItemIntoOutPutSlot(inventory) && hasEquipCanBeForged &&
                inventory.getItem(2).isEmpty() && !Compute.IsSoulEquip(equip);
    }

    private static boolean canInsertItemIntoOutPutSlot(SimpleContainer inventory) {
        return inventory.getItem(2).isEmpty();
    }

}

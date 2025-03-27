package fun.wraq.blocks.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.Items.Forging.ForgeEnhancePaper;
import fun.wraq.Items.Forging.ForgeProtect;
import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.equip.WraqMainHandEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.render.gui.blocks.ForgingBlockMenu;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.gems.GemItems;
import fun.wraq.series.gems.WraqGem;
import fun.wraq.series.instance.series.castle.CastleAttackArmor;
import fun.wraq.series.instance.series.castle.CastleManaArmor;
import fun.wraq.series.instance.series.castle.CastleSwiftArmor;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import fun.wraq.series.instance.series.harbinger.curio.HarbingerCurio;
import fun.wraq.series.moontain.equip.MoontainEquip;
import fun.wraq.series.overworld.forging.ForgingStone0;
import fun.wraq.series.overworld.forging.ForgingStone1;
import fun.wraq.series.overworld.forging.ForgingStone2;
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

import java.util.Map;
import java.util.Random;

public class ForgingBlockEntity extends BlockEntity implements MenuProvider, Droppable {
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

    public static void tick(Level level, BlockPos pos, BlockState blockState, ForgingBlockEntity blockEntity) {
        if (!level.isClientSide) {
            for (int i = 0 ; i < blockEntity.itemStackHandler.getSlots() ; i ++) {
                ItemStack stack = blockEntity.itemStackHandler.getStackInSlot(i);
                stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
            }
        }
        if (level.isClientSide()) {
            if (blockEntity.progress >= blockEntity.maxProgress) {
                Player player = level.getNearestPlayer(TargetingConditions.DEFAULT, pos.getX(), pos.getY(), pos.getZ());
                player.playSound(SoundEvents.ANVIL_USE);
            }
            return;
        }
        if (hasRecipe(blockEntity) || hasRecipeOfOpenSlot(blockEntity) || hasRecipe2(blockEntity)
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

        Player player = Utils.whoIsUsingBlock.getOrDefault(blockEntity.getBlockPos(), null);

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
                        data.putString("newGems" + i, Compute.getItemStackString(gem.getItem().getDefaultInstance()));
                        break;
                    }
                }
            }

            blockEntity.itemStackHandler.setStackInSlot(2, sword);
            blockEntity.itemStackHandler.extractItem(0, 1, false);
            blockEntity.itemStackHandler.extractItem(1, 1, false);
            if (player != null) MySound.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
        }

        // 开孔
        if (hasRecipeOfOpenSlot(blockEntity)) {
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
                        MySound.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
                    }
                } else {
                    if (player != null) {
                        Compute.sendFormatMSG(player, Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔失败。").withStyle(ChatFormatting.WHITE));
                        MySound.soundToPlayer(player, SoundEvents.ANVIL_DESTROY, blockEntity.getBlockPos().getCenter());
                    }
                }
            } else if (data.getInt("newMaxSlot") == 1) {
                if (r.nextDouble() < 0.6) {
                    data.putInt("newSlot", data.getInt("newSlot") + 1);
                    data.putInt("newMaxSlot", 2);
                    if (player != null) {
                        Compute.sendFormatMSG(player, Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔成功。").withStyle(ChatFormatting.WHITE));
                        MySound.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
                    }
                } else {
                    if (player != null) {
                        Compute.sendFormatMSG(player, Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔失败。").withStyle(ChatFormatting.WHITE));
                        MySound.soundToPlayer(player, SoundEvents.ANVIL_DESTROY, blockEntity.getBlockPos().getCenter());
                    }
                }
            } else if (data.getInt("newMaxSlot") == 2) {
                if (r.nextDouble() < 0.3) {
                    data.putInt("newSlot", data.getInt("newSlot") + 1);
                    data.putInt("newMaxSlot", 3);
                    if (player != null) {
                        Compute.sendFormatMSG(player, Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔成功。").withStyle(ChatFormatting.WHITE));
                        MySound.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
                    }
                } else {
                    if (player != null) {
                        Compute.sendFormatMSG(player, Component.literal("开孔").withStyle(ChatFormatting.AQUA),
                                Component.literal("开孔失败。").withStyle(ChatFormatting.WHITE));
                        MySound.soundToPlayer(player, SoundEvents.ANVIL_DESTROY, blockEntity.getBlockPos().getCenter());
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

            ItemStack slot3Stack = blockEntity.itemStackHandler.getStackInSlot(3);
            ItemStack slot4Stack = blockEntity.itemStackHandler.getStackInSlot(4);
            if (slot3Stack.is(ModItems.ForgeProtect.get())) protect = slot3Stack;
            if (slot4Stack.is(ModItems.ForgeProtect.get())) protect = slot4Stack;
            if (slot3Stack.getItem() instanceof ForgeEnhancePaper) enhancePaper = slot3Stack;
            if (slot4Stack.getItem() instanceof ForgeEnhancePaper) enhancePaper = slot4Stack;

            Map<Item, Double> enhanceRateMap = ImmutableMap.of(
                    ModItems.ForgeEnhance0.get(), 1.25,
                    ModItems.ForgeEnhance1.get(), 1.5,
                    ModItems.ForgeEnhance2.get(), 2d,
                    ModItems.ForgeEnhance3.get(), 2.5d
            );
            double enhanceRate = enhanceRateMap.getOrDefault(enhancePaper.getItem(), 1d);

            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            Compute.forgingHoverName(equip);
            int forgeLevel = 0;
            Random r = new Random();
            if (data.contains("Forging")) {
                forgeLevel = data.getInt("Forging");
            }
            boolean flag = true;
            if (stone.getItem() instanceof ForgingStone0) {
                double successRate = ((10 - forgeLevel) / 10d) * enhanceRate;
                if (r.nextDouble(1) < successRate) {
                    data.putInt("Forging", forgeLevel + 1);
                    flag = false;
                }
            }

            if (stone.getItem() instanceof ForgingStone1) {
                double successRate = ((20 - forgeLevel) / 20d) * enhanceRate;
                if (r.nextDouble(1) < successRate) {
                    data.putInt("Forging", forgeLevel + 1);
                    flag = false;
                }
            }

            if (stone.getItem() instanceof ForgingStone2) {
                double successRate = ((24 - forgeLevel) / 24d) * enhanceRate;
                if (r.nextDouble(1) < successRate) {
                    data.putInt("Forging", forgeLevel + 1);
                    flag = false;
                }
            }

            if (stone.getItem().equals(ModItems.WORLD_FORGE_STONE.get())) {
                if (forgeLevel < 24) {
                    data.putInt("Forging", forgeLevel + 1);
                    flag = false;
                } else {
                    int minus = 32 - forgeLevel;
                    double rate = (0.01 + minus * 0.005) * enhanceRate;
                    if (r.nextDouble() < rate) {
                        data.putInt("Forging", forgeLevel + 1);
                        flag = false;
                    }
                }
            }

            boolean useProtect = false;
            if (player != null) {
                if (flag) {
                    if (forgeLevel >= 24 && !protect.is(ModItems.ForgeProtect.get())) {
                        int level = Math.max(forgeLevel - 2, 23);
                        data.putInt("Forging", level);
                        Compute.formatBroad(player.level(), Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，装备等级掉至 ").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal("+" + level).withStyle(CustomStyle.styleOfWorld)));
                    }

                    if (forgeLevel >= 17 && forgeLevel < 19 && !protect.is(ModItems.ForgeProtect.get())) {
                        Compute.formatBroad(player.level(), Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，装备等级掉至 16").withStyle(ChatFormatting.WHITE)));
                        data.putInt("Forging", 16);
                    }
                    if (forgeLevel >= 20 && forgeLevel < 22 && !protect.is(ModItems.ForgeProtect.get())) {
                        Compute.formatBroad(player.level(), Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，装备等级掉至 19").withStyle(ChatFormatting.WHITE)));
                        data.putInt("Forging", 19);
                    }
                    if (forgeLevel == 23 && !protect.is(ModItems.ForgeProtect.get())) {
                        Compute.formatBroad(player.level(), Component.literal("强化").withStyle(ChatFormatting.AQUA),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(player.getDisplayName()).
                                        append(Component.literal("在强化").withStyle(ChatFormatting.WHITE)).
                                        append(equip.getDisplayName()).
                                        append(Component.literal("时失败，装备等级掉至 22").withStyle(ChatFormatting.WHITE)));
                        data.putInt("Forging", 22);
                    }

                    if (forgeLevel >= 17 && protect.is(ModItems.ForgeProtect.get())) {
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
                    MySound.soundToPlayer(player, SoundEvents.ANVIL_DESTROY, blockEntity.getBlockPos().getCenter());

                } else {
                    if (forgeLevel >= 17)
                        Compute.broad(blockEntity.level, (Component.literal("[").withStyle(ChatFormatting.GRAY).
                                append(Component.literal("强化").withStyle(ChatFormatting.AQUA)).
                                append(Component.literal("]").withStyle(ChatFormatting.GRAY)).
                                append(player.getDisplayName()).
                                append(" 成功将 ").withStyle(ChatFormatting.WHITE)).
                                append(equip.getDisplayName()).
                                append(Component.literal(" 强化至" + "+" + data.getInt("Forging")).withStyle(ChatFormatting.WHITE)));
                    Compute.sendFormatMSG(player, Component.literal("强化").withStyle(ChatFormatting.AQUA),
                            Component.literal("强化成功!").withStyle(ChatFormatting.AQUA));
                    MySound.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
                }
            }

            Compute.forgingHoverName(equip);
            blockEntity.itemStackHandler.setStackInSlot(2, equip);
            blockEntity.itemStackHandler.extractItem(0, 1, false);
            blockEntity.itemStackHandler.extractItem(1, 1, false);
            if (enhancePaper.getItem() instanceof ForgeEnhancePaper) {
                if (slot3Stack.getItem() instanceof ForgeEnhancePaper) {
                    blockEntity.itemStackHandler.extractItem(3, 1, false);
                }
                else if (slot4Stack.getItem() instanceof ForgeEnhancePaper) {
                    blockEntity.itemStackHandler.extractItem(4, 1, false);
                }
            }
            if (useProtect && protect.getItem() instanceof ForgeProtect) {
                if (slot3Stack.is(ModItems.ForgeProtect.get())) {
                    blockEntity.itemStackHandler.extractItem(3, 1, false);
                }
                else if (slot4Stack.is(ModItems.ForgeProtect.get())) {
                    blockEntity.itemStackHandler.extractItem(4, 1, false);
                }
            }
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
            if (player != null) {
                MySound.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
            }
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
                                append(ForgeEquipUtils.getDescription(equipTier + 1)).
                                append(Component.literal(" 品质").withStyle(ChatFormatting.AQUA)));
            }
            blockEntity.itemStackHandler.setStackInSlot(2, equip);
            blockEntity.itemStackHandler.extractItem(0, 2, false);
            blockEntity.itemStackHandler.extractItem(1, 1, false);
            if (player != null) MySound.soundToPlayer(player, SoundEvents.ANVIL_USE, blockEntity.getBlockPos().getCenter());
        }

        if (hasRecipeOfForgeTemplateGetForgeLevel(blockEntity)) {
            craftOnForgeTemplateGetForgeLevel(blockEntity, player);
        }
        if (hasRecipeOfForgeTemplateSetForgeLevel(blockEntity)) {
            craftOnForgeTemplateSetForgeLevel(blockEntity, player);
        }
    }

    private static boolean hasRecipeOfDismantle(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack gem = blockEntity.itemStackHandler.getStackInSlot(0);
        ItemStack sword = blockEntity.itemStackHandler.getStackInSlot(4);

        boolean hasDismantleInFirstSlot = blockEntity.itemStackHandler.getStackInSlot(3).is(GemItems.DISMANTLE.get());

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
        boolean hasSwordBowSceptreInSecondSlot = Utils.mainHandTag.containsKey(equip.getItem()) ||
                Utils.armorTag.containsKey(equip.getItem()) || Utils.offHandTag.containsKey(equip.getItem());

        if (equip.getTagElement(Utils.MOD_ID) != null) {
            CompoundTag data = equip.getOrCreateTagElement(Utils.MOD_ID);
            if (data.getInt("newSlot") == 0) return false;
        }

        boolean correctType = (!gem.getItem().equals(GemItems.castleWeaponGem.get()) || Utils.mainHandTag.containsKey(equip.getItem()))
                && (!gem.getItem().equals(GemItems.castleArmorGem.get()) || Utils.armorTag.containsKey(equip.getItem()));
        return hasGemInFirstSlot && canInsertItemIntoOutPutSlot(inventory) && hasSwordBowSceptreInSecondSlot &&
                inventory.getItem(2).isEmpty() && correctType;
    }   //镶嵌

    private static boolean hasRecipeOfOpenSlot(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack equip = blockEntity.itemStackHandler.getStackInSlot(4);
        ItemStack openSlot = blockEntity.itemStackHandler.getStackInSlot(3);

        CompoundTag data = new CompoundTag();
        if (equip.getTagElement(Utils.MOD_ID) != null) {
            data = equip.getOrCreateTagElement(Utils.MOD_ID);
        }
        boolean isOpenSlotApplied = openSlot.is(GemItems.OPEN_SLOT.get())
                && (Utils.mainHandTag.containsKey(equip.getItem())
                || Utils.armorTag.containsKey(equip.getItem()))
                && data.getInt(WraqGem.NEW_MAX_SLOT_DATA_KEY) < 1;

        boolean isOpenSlotGoldenApplied = openSlot.is(GemItems.OPEN_SLOT_GOLDEN.get())
                && ((Utils.mainHandTag.containsKey(equip.getItem())
                && data.getInt(WraqGem.NEW_MAX_SLOT_DATA_KEY) == 1) || (Utils.offHandTag.containsKey(equip.getItem())
                && data.getInt(WraqGem.NEW_MAX_SLOT_DATA_KEY) < 1));

        boolean isOpenSlotDiamondApplied = openSlot.is(GemItems.OPEN_SLOT_DIAMOND.get())
                && ((Utils.mainHandTag.containsKey(equip.getItem()) && data.getInt(WraqGem.NEW_MAX_SLOT_DATA_KEY) == 2)
                || (Utils.armorTag.containsKey(equip.getItem()) && data.getInt(WraqGem.NEW_MAX_SLOT_DATA_KEY) == 1));

        return canInsertItemIntoOutPutSlot(inventory)
                && (isOpenSlotApplied || isOpenSlotGoldenApplied || isOpenSlotDiamondApplied)
                && inventory.getItem(2).isEmpty()
                && !Compute.IsSoulEquip(equip);
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
                || forgeStone.equals(ModItems.WORLD_FORGE_STONE.get()));

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
        if (materialTier >= ForgeEquipUtils.getEquipPieceList().size() - 1) {
            return false;
        }
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
        boolean canBeForged = tag != null && tag.contains(ForgeEquipUtils.itemTag)
                && !(equipStack.getItem() instanceof MoontainEquip);

        int equipTier = ForgeEquipUtils.getForgeQualityOnEquip(equipStack);
        ItemStack productSlot = blockEntity.itemStackHandler.getStackInSlot(2);
        return canBeForged && pieceTier - equipTier == 1 && productSlot.getCount() == 0 && equipPieceStack.getCount() >= 2;
    }

    private static boolean hasRecipeOfForgeTemplateGetForgeLevel(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack equip = inventory.getItem(3);
        Item equipItem = equip.getItem();
        ItemStack forgeTemplate = inventory.getItem(4);
        boolean materialTypeCorrect = forgeTemplate.getItem().equals(ModItems.FORGE_TEMPLATE.get())
                && (equipItem instanceof WraqMainHandEquip || equipItem instanceof WraqArmor);
        return materialTypeCorrect
                && ForgeEquipUtils.getForgeLevel(forgeTemplate) == 0
                && ForgeEquipUtils.getForgeLevel(equip) > 0
                && canInsertItemIntoOutPutSlot(inventory);
    }

    private static boolean hasRecipeOfForgeTemplateSetForgeLevel(ForgingBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemStackHandler.getSlots());
        for (int i = 0; i < blockEntity.itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemStackHandler.getStackInSlot(i));
        }
        ItemStack equip = inventory.getItem(0);
        Item equipItem = equip.getItem();
        ItemStack forgeTemplate = inventory.getItem(1);
        boolean materialTypeCorrect = forgeTemplate.getItem().equals(ModItems.FORGE_TEMPLATE.get())
                && (equipItem instanceof WraqMainHandEquip || equipItem instanceof WraqArmor);
        int forgeTemplateForgeLevel = ForgeEquipUtils.getForgeLevel(forgeTemplate);
        int equipForgeLevel = ForgeEquipUtils.getForgeLevel(equip);
        return materialTypeCorrect
                && forgeTemplateForgeLevel > 0
                && forgeTemplateForgeLevel > equipForgeLevel
                && canInsertItemIntoOutPutSlot(inventory);
    }

    private static void craftOnForgeTemplateGetForgeLevel(ForgingBlockEntity blockEntity, Player player) {
        ItemStackHandler stackHandler = blockEntity.itemStackHandler;
        ItemStack equip = stackHandler.getStackInSlot(3);
        ItemStack forgeTemplate = stackHandler.getStackInSlot(4);
        ForgeEquipUtils.setForgeLevel(forgeTemplate, ForgeEquipUtils.getForgeLevel(equip));
        ForgeEquipUtils.setForgeLevel(equip, 0);
        stackHandler.setStackInSlot(2, forgeTemplate);
        stackHandler.setStackInSlot(4, Items.AIR.getDefaultInstance());
        if (player != null) {
            MySound.soundToPlayer(player, SoundEvents.ANVIL_USE);
        }
    }

    private static void craftOnForgeTemplateSetForgeLevel(ForgingBlockEntity blockEntity, Player player) {
        ItemStackHandler stackHandler = blockEntity.itemStackHandler;
        ItemStack equip = stackHandler.getStackInSlot(0);
        ItemStack forgeTemplate = stackHandler.getStackInSlot(1);
        ForgeEquipUtils.setForgeLevel(equip, ForgeEquipUtils.getForgeLevel(forgeTemplate));
        stackHandler.setStackInSlot(2, equip);
        stackHandler.setStackInSlot(0, Items.AIR.getDefaultInstance());
        stackHandler.setStackInSlot(1, Items.AIR.getDefaultInstance());
        if (player != null) {
            MySound.soundToPlayer(player, SoundEvents.ANVIL_USE);
        }
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

        if (equip.getItem() instanceof Decomposable decomposable && !decomposable.getProduct().is(Items.AIR)) {
            if (equip.getItem() instanceof HarbingerCurio) {
                CompoundTag tag = equip.getOrCreateTagElement(Utils.MOD_ID);
                if (RandomCuriosAttributesUtil.attributeValueMap.keySet().stream().noneMatch(tag::contains)) {
                    return false;
                }
            }
            ItemStack stack = decomposable.getProduct();
            if (decomposable.getProduct().getCount() * equip.getCount() > 64) {
                return false;
            }
            stack.setCount(decomposable.getProduct().getCount() * equip.getCount());
            if (InventoryCheck.getBoundingList().contains(stack.getItem())) {
                Player player = Utils.whoIsUsingBlock.getOrDefault(this.worldPosition, null);
                if (player != null) {
                    InventoryCheck.addOwnerTagToItemStack(player, stack);
                }
                else {
                    stack = new ItemStack(Items.AIR);
                }
            }
            this.itemStackHandler.setStackInSlot(2, stack);
            return true;
        }
        return false;
    }

    public ItemStackHandler getItemStackHandler() {
        return this.itemStackHandler;
    }
}

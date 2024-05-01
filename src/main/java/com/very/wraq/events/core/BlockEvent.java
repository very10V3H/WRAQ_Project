package com.very.wraq.events.core;

import com.very.wraq.blocks.blocks.ForgeRecipe;
import com.very.wraq.blocks.blocks.WorldSoulBlock;
import com.very.wraq.blocks.entity.ForgingBlockEntity;
import com.very.wraq.blocks.entity.FurnaceEntity;
import com.very.wraq.blocks.entity.HBrewingEntity;
import com.very.wraq.blocks.entity.InjectBlockEntity;
import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisaCurios1;
import com.very.wraq.customized.players.sceptre.FengXiaoju.FengXiaoJuCurios;
import com.very.wraq.customized.players.sceptre.liulixian_.LiuLiXianCurios1F;
import com.very.wraq.events.instance.CastleSecondFloor;
import com.very.wraq.events.instance.TabooDevil;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.SoundsPackets.SoundsS2CPacket;
import com.very.wraq.netWorking.unSorted.PlayerCallBack;
import com.very.wraq.process.element.Element;
import com.very.wraq.process.missions.series.labourDay.LabourDayMission;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overWorld.SakuraSeries.MineWorker.PurplePickaxe;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Struct.BlockAndResetTime;
import com.very.wraq.valueAndTools.Utils.Struct.BlockSP;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModBlocks;
import com.very.wraq.valueAndTools.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber
public class BlockEvent {
    @SubscribeEvent
    public static void BreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        if (Utils.ShangMengLi != null && Utils.ShangMengLi.equals(player) && Utils.ShangMengLiCore) {
            event.setNewSpeed(200);
        }
        if (Utils.Crush1 != null && Utils.Crush1.equals(player) && Utils.Crush1ZeusIsOn) {
            event.setNewSpeed(200);
        }
        if (LiuLiXianCurios1F.IsLiuLiXian(player)) event.setNewSpeed(200);
        if (BlackFeisaCurios1.IsOn(player)) event.setNewSpeed(200);
        if (FengXiaoJuCurios.IsOn(player)) event.setNewSpeed(200);
    }
    @SubscribeEvent
    public static void PurpleIronArmor(PlayerInteractEvent.RightClickBlock event) throws IOException {
        if (!event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            int TickCount = player.getServer().getTickCount();
            if (!Utils.playerClickBlockCoolDown.containsKey(player) || Utils.playerClickBlockCoolDown.get(player) < TickCount) {
                BlockPos blockPos = event.getHitVec().getBlockPos();
                BlockState blockState = player.level().getBlockState(blockPos);
                Level level = player.level();
                Inventory inventory = player.getInventory();
                ItemStack purpleIron = ModItems.PurpleIron.get().getDefaultInstance();
                Utils.playerClickBlockCoolDown.put(player,TickCount + 10);

                if (Element.ResonancePosMap.containsKey(blockPos)) {
                    String type = Element.ResonancePosMap.get(blockPos);
                    if (player.experienceLevel < Element.ResonanceLevelRequireMap.get(type)) {
                        Compute.FormatMSGSend(player,Component.literal("元素").withStyle(ChatFormatting.LIGHT_PURPLE),
                                Component.literal("暂未达到能与该元素共鸣的等级。").withStyle(ChatFormatting.WHITE));
                    }
                    else Element.Resonance(player,type);
                }

                if (player.isShiftKeyDown() && blockPos.equals(new BlockPos(1627,70,1162))) {
                    ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                    Item item = itemStack.getItem();
                    if (item.equals(ModItems.SeaSword1.get()) || item.equals(ModItems.SeaSword2.get())
                            || item.equals(ModItems.SeaSword3.get())) item = ModItems.SeaSword0.get();
                    if (item.equals(ModItems.BlackForestSword1.get()) || item.equals(ModItems.BlackForestSword2.get())
                            || item.equals(ModItems.BlackForestSword3.get())) item = ModItems.BlackForestSword0.get();

                    if (ForgeRecipe.ForgeDrawRecipe.containsKey(item)) {
                        if (!Utils.playerRecycleMap.containsKey(player)
                                || !Utils.playerRecycleMap.get(player)) {
                            Utils.playerRecycleMap.put(player,true);
                            Compute.FormatMSGSend(player,Component.literal("回收").withStyle(ChatFormatting.GOLD),
                                    Component.literal(" 再次shift右击确定回收！").withStyle(ChatFormatting.WHITE));
                        }
                        else {
                            List<ItemStack> itemStackList = ForgeRecipe.ForgeDrawRecipe.get(item);
                            itemStackList.forEach(itemStack1 -> {
                                try {
                                    Compute.ItemStackGive(player,new ItemStack(itemStack1.getItem(),(itemStack1.getCount() + 1) / 2));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            Compute.FormatBroad(level,Component.literal("回收").withStyle(ChatFormatting.GOLD),
                                    Component.literal("").withStyle(ChatFormatting.WHITE).
                                            append(player.getDisplayName()).
                                            append(Component.literal(" 回收了:").withStyle(ChatFormatting.GOLD)).
                                            append(itemStack.getDisplayName()));
                            Compute.PlayerItemUseWithRecord(player);
                            Utils.playerRecycleMap.put(player,false);
                        }
                    }
                }

                if (blockPos.equals(new BlockPos(1682,73,1051))) {
                    if (Compute.ItemStackCheck(inventory,ModItems.PurpleIron.get(),256)) {
                        Compute.ItemStackRemove(inventory,ModItems.PurpleIron.get(),256);
                        ItemStack itemStack = ModItems.PurpleIronArmorHelmet.get().getDefaultInstance();
                        Compute.PurpleIronArmorAttributeGive(itemStack);
                        Compute.FormatBroad(level,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                Component.literal("").append(player.getDisplayName()).
                                        append(Component.literal("成功打造了：").withStyle(ChatFormatting.WHITE)).
                                        append(itemStack.getDisplayName()));
                        Compute.ItemStackGive(player,itemStack);
                        ModNetworking.sendToClient(new SoundsS2CPacket(5),(ServerPlayer) player);
                    }
                    else {
                        Compute.FormatMSGSend(player,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                Component.literal("似乎缺少").withStyle(ChatFormatting.WHITE).
                                        append(purpleIron.getDisplayName()).
                                        append(Component.literal("x" + (256 - Compute.ItemStackCount(inventory,ModItems.PurpleIron.get())))));
                    }
                }

                if (blockPos.equals(new BlockPos(1685,73,1051))) {
                    if (Compute.ItemStackCheck(inventory,ModItems.PurpleIron.get(),256)) {
                        Compute.ItemStackRemove(inventory,ModItems.PurpleIron.get(),256);
                        ItemStack itemStack = ModItems.PurpleIronArmorChest.get().getDefaultInstance();
                        Compute.PurpleIronArmorAttributeGive(itemStack);
                        Compute.FormatBroad(level,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                Component.literal("").append(player.getDisplayName()).
                                        append(Component.literal("成功打造了：").withStyle(ChatFormatting.WHITE)).
                                        append(itemStack.getDisplayName()));
                        Compute.ItemStackGive(player,itemStack);
                        ModNetworking.sendToClient(new SoundsS2CPacket(5),(ServerPlayer) player);
                    }
                    else {
                        Compute.FormatMSGSend(player,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                Component.literal("似乎缺少").withStyle(ChatFormatting.WHITE).
                                        append(purpleIron.getDisplayName()).
                                        append(Component.literal("x" + (256 - Compute.ItemStackCount(inventory,ModItems.PurpleIron.get())))));
                    }
                }

                if (blockPos.equals(new BlockPos(1693,73,1051))) {
                    if (Compute.ItemStackCheck(inventory,ModItems.PurpleIron.get(),256)) {
                        Compute.ItemStackRemove(inventory,ModItems.PurpleIron.get(),256);
                        ItemStack itemStack = ModItems.PurpleIronArmorLeggings.get().getDefaultInstance();
                        Compute.PurpleIronArmorAttributeGive(itemStack);
                        Compute.FormatBroad(level,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                Component.literal("").append(player.getDisplayName()).
                                        append(Component.literal("成功打造了：").withStyle(ChatFormatting.WHITE)).
                                        append(itemStack.getDisplayName()));
                        Compute.ItemStackGive(player,itemStack);
                        ModNetworking.sendToClient(new SoundsS2CPacket(5),(ServerPlayer) player);
                    }
                    else {
                        Compute.FormatMSGSend(player,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                Component.literal("似乎缺少").withStyle(ChatFormatting.WHITE).
                                        append(purpleIron.getDisplayName()).
                                        append(Component.literal("x" + (256 - Compute.ItemStackCount(inventory,ModItems.PurpleIron.get())))));
                    }
                }

                if (blockPos.equals(new BlockPos(1696,73,1051))) {
                    if (Compute.ItemStackCheck(inventory,ModItems.PurpleIron.get(),256)) {
                        Compute.ItemStackRemove(inventory,ModItems.PurpleIron.get(),256);
                        ItemStack itemStack = ModItems.PurpleIronArmorBoots.get().getDefaultInstance();
                        Compute.PurpleIronArmorAttributeGive(itemStack);
                        Compute.FormatBroad(level,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                Component.literal("").append(player.getDisplayName()).
                                        append(Component.literal("成功打造了：").withStyle(ChatFormatting.WHITE)).
                                        append(itemStack.getDisplayName()));
                        Compute.ItemStackGive(player,itemStack);
                        ModNetworking.sendToClient(new SoundsS2CPacket(5),(ServerPlayer) player);
                    }
                    else {
                        Compute.FormatMSGSend(player,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                Component.literal("似乎缺少").withStyle(ChatFormatting.WHITE).
                                        append(purpleIron.getDisplayName()).
                                        append(Component.literal("x" + (256 - Compute.ItemStackCount(inventory,ModItems.PurpleIron.get())))));
                    }
                }

                if (blockPos.equals(new BlockPos(1696,73,1059))) {
                    if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PurpleIronArmorHelmet.get())
                            || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PurpleIronArmorChest.get())
                            || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PurpleIronArmorLeggings.get())
                            || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PurpleIronArmorBoots.get())) {
                        if (Compute.ItemStackCheck(inventory,ModItems.PurpleIron.get(),64)) {
                            Compute.ItemStackRemove(inventory,ModItems.PurpleIron.get(),64);
                            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                            Compute.PurpleIronArmorAttributeGive(itemStack);
                            Compute.FormatBroad(level,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                    Component.literal("").append(player.getDisplayName()).
                                            append(Component.literal("成功重铸了：").withStyle(ChatFormatting.WHITE)).
                                            append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player,itemStack);
                            ModNetworking.sendToClient(new SoundsS2CPacket(5),(ServerPlayer) player);
                        }
                        else {
                            Compute.FormatMSGSend(player,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                    Component.literal("似乎缺少").withStyle(ChatFormatting.WHITE).
                                            append(purpleIron.getDisplayName()).
                                            append(Component.literal("x" + (64 - Compute.ItemStackCount(inventory,ModItems.PurpleIron.get())))));
                        }
                    }
                }

                if (blockPos.equals(new BlockPos(1685,70,1162))) {
                    if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.IceArmorHelmet.get())
                            || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.IceArmorChest.get())
                            || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.IceArmorLeggings.get())
                            || player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.IceArmorBoots.get())) {
                        if (Compute.ItemStackCheck(inventory,ModItems.IceHeart.get(),8)) {
                            Compute.ItemStackRemove(inventory,ModItems.IceHeart.get(),8);
                            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                            ItemStack oldItem = new ItemStack(itemStack.getItem(),1);
                            oldItem.getOrCreateTagElement(Utils.MOD_ID).merge(itemStack.getOrCreateTagElement(Utils.MOD_ID));
                            Compute.IceArmorAttributeGive(itemStack);
                            Compute.FormatBroad(level,Component.literal("冰霜锻造").withStyle(CustomStyle.styleOfIce),
                                    Component.literal("").append(player.getDisplayName()).
                                            append(Component.literal("成功将").withStyle(ChatFormatting.WHITE)).
                                            append(oldItem.getDisplayName()).
                                            append(Component.literal(" 重铸为: ").withStyle(ChatFormatting.WHITE)).
                                            append(itemStack.getDisplayName()));
                            Compute.ItemStackGive(player,itemStack);
                            ModNetworking.sendToClient(new SoundsS2CPacket(5),(ServerPlayer) player);
                        }
                        else {
                            Compute.FormatMSGSend(player,Component.literal("冰霜锻造").withStyle(CustomStyle.styleOfIce),
                                    Component.literal("似乎缺少").withStyle(ChatFormatting.WHITE).
                                            append(ModItems.IceHeart.get().getDefaultInstance().getDisplayName()).
                                            append(Component.literal("x" + (8 - Compute.ItemStackCount(inventory,ModItems.IceHeart.get())))));
                        }
                    }
                }

                if (blockPos.equals(new BlockPos(1693,73,1059))) {
                    if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PurpleIronPickaxe0.get())) {
                        if (Compute.ItemStackCheck(inventory,ModItems.PurpleIron.get(),128)) {
                            Compute.ItemStackRemove(inventory,ModItems.PurpleIron.get(),128);
                            CompoundTag OriginalData = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
                            ItemStack itemStack = ModItems.PurpleIronPickaxe1.get().getDefaultInstance();
                            itemStack.getOrCreateTagElement(Utils.MOD_ID).merge(OriginalData);
                            Compute.FormatBroad(level,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                    Component.literal("").append(player.getDisplayName()).
                                            append(Component.literal("成功打造了：").withStyle(ChatFormatting.WHITE)).
                                            append(itemStack.getDisplayName()));
                            player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
                            ModNetworking.sendToClient(new SoundsS2CPacket(5),(ServerPlayer) player);
                        }
                        else {
                            Compute.FormatMSGSend(player,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                    Component.literal("似乎缺少").withStyle(ChatFormatting.WHITE).
                                            append(purpleIron.getDisplayName()).
                                            append(Component.literal("x" + (128 - Compute.ItemStackCount(inventory,ModItems.PurpleIron.get())))));
                        }
                    }
                }

                if (blockPos.equals(new BlockPos(1685,73,1059))) {
                    if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PurpleIronPickaxe1.get())) {
                        if (Compute.ItemStackCheck(inventory,ModItems.PurpleIron.get(),192)) {
                            Compute.ItemStackRemove(inventory,ModItems.PurpleIron.get(),192);
                            CompoundTag OriginalData = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
                            ItemStack itemStack = ModItems.PurpleIronPickaxe2.get().getDefaultInstance();
                            itemStack.getOrCreateTagElement(Utils.MOD_ID).merge(OriginalData);
                            Compute.FormatBroad(level,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                    Component.literal("").append(player.getDisplayName()).
                                            append(Component.literal("成功打造了：").withStyle(ChatFormatting.WHITE)).
                                            append(itemStack.getDisplayName()));
                            player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
                            ModNetworking.sendToClient(new SoundsS2CPacket(5),(ServerPlayer) player);
                        }
                        else {
                            Compute.FormatMSGSend(player,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                    Component.literal("似乎缺少").withStyle(ChatFormatting.WHITE).
                                            append(purpleIron.getDisplayName()).
                                            append(Component.literal("x" + (192 - Compute.ItemStackCount(inventory,ModItems.PurpleIron.get())))));
                        }
                    }
                }

                if (blockPos.equals(new BlockPos(1682,73,1059))) {
                    if (player.getItemInHand(InteractionHand.MAIN_HAND).is(ModItems.PurpleIronPickaxe2.get())) {
                        if (Compute.ItemStackCheck(inventory,ModItems.PurpleIron.get(),256)) {
                            Compute.ItemStackRemove(inventory,ModItems.PurpleIron.get(),256);
                            CompoundTag OriginalData = player.getItemInHand(InteractionHand.MAIN_HAND).getOrCreateTagElement(Utils.MOD_ID);
                            ItemStack itemStack = ModItems.PurpleIronPickaxe3.get().getDefaultInstance();
                            itemStack.getOrCreateTagElement(Utils.MOD_ID).merge(OriginalData);
                            Compute.FormatBroad(level,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                    Component.literal("").append(player.getDisplayName()).
                                            append(Component.literal("成功打造了：").withStyle(ChatFormatting.WHITE)).
                                            append(itemStack.getDisplayName()));
                            player.setItemInHand(InteractionHand.MAIN_HAND,itemStack);
                            ModNetworking.sendToClient(new SoundsS2CPacket(5),(ServerPlayer) player);
                        }
                        else {
                            Compute.FormatMSGSend(player,Component.literal("紫晶锻造").withStyle(CustomStyle.styleOfPurpleIron),
                                    Component.literal("似乎缺少").withStyle(ChatFormatting.WHITE).
                                            append(purpleIron.getDisplayName()).
                                            append(Component.literal("x" + (256 - Compute.ItemStackCount(inventory,ModItems.PurpleIron.get())))));
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void WorldSoulTransform(PlayerInteractEvent.RightClickBlock event) throws IOException {
        if (!event.getEntity().level().isClientSide) {
            Player player = event.getEntity();
            BlockPos blockPos = event.getHitVec().getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);

            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            AtomicReference<Double> mediumNum = new AtomicReference<>(0.125);

            if (blockState.getBlock() instanceof WorldSoulBlock) {

                if (Utils.SoulList.isEmpty()) Utils.SoulListInit();
                if (Utils.SoulList.contains(itemStack.getItem())) {
                    AtomicBoolean flag = new AtomicBoolean(true);
                    Utils.WorldEntropyPos.forEach(worldEntropy -> {
                        if (worldEntropy.getVec3().equals(new Vec3(blockPos.getX(),blockPos.getY(),blockPos.getZ()))) {
                            mediumNum.set(worldEntropy.getMediumNum());
                            flag.set(false);
                        }
                    });
                    if (flag.get()) return;

                    ItemStack worldSoul1 = ModItems.WorldSoul1.get().getDefaultInstance();
                    int TransformSuccessNum = 0;
                    int OriginalItemNum = itemStack.getCount();
                    for (int i = 0; i < itemStack.getCount(); i ++) {
                        Random random = new Random();
                        if (random.nextDouble(1) < (Utils.WorldEntropyIncreaseSpeed + mediumNum.get()) * (1 + Compute.PlayerLuckyUp(player))) TransformSuccessNum ++;
                    }
                    Compute.FormatMSGSend(player,Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                            Component.literal("你在解析成功概率为").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(String.format("%.2f%%",(Utils.WorldEntropyIncreaseSpeed + mediumNum.get()) * 100)).withStyle(CustomStyle.styleOfWorld))
                                    .append(Component.literal("的介质中,用" + OriginalItemNum + "个").withStyle(ChatFormatting.WHITE))
                                    .append(itemStack.getDisplayName())
                                    .append(Component.literal("成功解析出了").withStyle(ChatFormatting.WHITE))
                                    .append(Component.literal(String.valueOf(TransformSuccessNum)).withStyle(CustomStyle.styleOfWorld))
                                    .append(Component.literal("个").withStyle(ChatFormatting.WHITE))
                                    .append(worldSoul1.getDisplayName()));

                    worldSoul1.setCount(TransformSuccessNum);
                    Compute.PlayerItemUseWithRecord(player,itemStack.getCount());
                    Compute.ItemStackGive(player,worldSoul1);
                }
                else {
                    if (Utils.WorldSoulMap.isEmpty()) Utils.WorldSoulMapInit();
                    if (itemStack.getCount() == 64 && Utils.WorldSoulMap.containsKey(itemStack.getItem())) {
                        Item NextTireSoul = Utils.WorldSoulMap.get(itemStack.getItem());
                        Compute.FormatMSGSend(player,Component.literal("世界本源").withStyle(CustomStyle.styleOfWorld),
                                Component.literal("你将").withStyle(ChatFormatting.WHITE).
                                        append(itemStack.getDisplayName()).
                                        append(Component.literal("转换成为").withStyle(ChatFormatting.WHITE)).
                                        append(NextTireSoul.getDefaultInstance().getDisplayName()));
                        Compute.PlayerItemUseWithRecord(player,64);
                        Compute.ItemStackGive(player,NextTireSoul.getDefaultInstance());
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void ForgingBlockDetect(PlayerInteractEvent.RightClickBlock event) {
        if(!event.getEntity().level().isClientSide)
        {
            Player player = event.getEntity();
            BlockPos blockPos = event.getHitVec().getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);
            if (Compute.BlockLimitContainBlockPos(blockPos) && !player.isCreative()) {
                Compute.FormatMSGSend(player,Component.literal("方块").withStyle(ChatFormatting.GREEN),
                        Component.literal("这个方块正在被使用。"));
                event.setCanceled(true);
            }
            else {
                boolean flag = false;
                if (blockState.getBlock().equals(ModBlocks.FIRST_BLOCK.get())){
                    ForgingBlockEntity forgingBlockEntity = (ForgingBlockEntity) player.level().getBlockEntity(blockPos);
                    forgingBlockEntity.clear();
                    flag = true;
                }
                if(blockState.getBlock().equals(ModBlocks.HBREWING_BLOCK.get())){
                    HBrewingEntity hBrewingEntity = (HBrewingEntity) player.level().getBlockEntity(blockPos);
                    hBrewingEntity.clear();
                    flag = true;
                }
                if(blockState.getBlock().equals(ModBlocks.Inject_Block.get())){
                    InjectBlockEntity injectBlockEntity = (InjectBlockEntity) player.level().getBlockEntity(blockPos);
                    injectBlockEntity.clear();
                    flag = true;
                }
                if(blockState.getBlock().equals(ModBlocks.Furnace.get())){
                    FurnaceEntity furnaceEntity = (FurnaceEntity) player.level().getBlockEntity(blockPos);
                    furnaceEntity.clear();
                    flag = true;
                }
                if (flag) {
                    PlayerCallBack playerCallBack = new PlayerCallBack(blockPos,player,3);
                    Utils.playerCallBackList.add(playerCallBack);
                    Utils.whoIsUsingBlock.put(blockPos,player.getName().getString());
                }
            }
            ItemStack Sword = player.getItemInHand(InteractionHand.MAIN_HAND);
            if(!blockState.is(Blocks.CHEST) && (Sword.is(ModItems.ForestSword0.get()) || Sword.is(ModItems.ForestSword1.get()) ||
                    Sword.is(ModItems.ForestSword2.get()) || Sword.is(ModItems.ForestSword3.get()))){
                event.setCanceled(true);
            }
        }
    }
    @SubscribeEvent
    public static void PreventRightClick(PlayerInteractEvent.RightClickBlock event) throws IOException {
        Player player = event.getEntity();
        BlockPos blockPos = event.getHitVec().getBlockPos();
        BlockState blockState = player.level().getBlockState(blockPos);
        Item result = blockState.getBlock().asItem();
        if(!player.isCreative()) {
            if (event.getItemStack().getItem() instanceof BoatItem) event.setCanceled(true);
            if (blockState.is(Blocks.ACACIA_TRAPDOOR)
                    || blockState.is(Blocks.BIRCH_TRAPDOOR)
                    || blockState.is(Blocks.IRON_TRAPDOOR)
                    || blockState.is(Blocks.OAK_TRAPDOOR)
                    || blockState.is(Blocks.CRIMSON_TRAPDOOR)
                    || blockState.is(Blocks.DARK_OAK_TRAPDOOR)
                    || blockState.is(Blocks.JUNGLE_TRAPDOOR)
                    || blockState.is(Blocks.MANGROVE_TRAPDOOR)
                    || blockState.is(Blocks.SPRUCE_TRAPDOOR)
                    || blockState.is(Blocks.WARPED_TRAPDOOR)
                    || blockState.is(Blocks.CRAFTING_TABLE)
                    || blockState.is(Blocks.FURNACE)
                    || blockState.is(Blocks.BLAST_FURNACE)
                    || blockState.is(Blocks.HOPPER)
                    || blockState.is(Blocks.SMOKER)
                    || blockState.is(Blocks.ANVIL)
                    || blockState.is(Blocks.ENCHANTING_TABLE)
                    || blockState.is(Blocks.DISPENSER)
                    || blockState.is(Blocks.DROPPER)
                    || blockState.is(Blocks.BARREL)
                    || blockState.getBlock() instanceof ShulkerBoxBlock
            ) event.setCanceled(true);
            if (blockState.getBlock().toString().length() > 12
                    && blockState.getBlock().toString().startsWith("create", 6)
                    && !blockState.getBlock().toString().contains("seat")) event.setCanceled(true);
/*            if (result.equals(Items.BARREL) && event.getSide().isServer()) {
                BarrelBlockEntity barrelBlockEntity = (BarrelBlockEntity) player.level().getBlockEntity(blockPos);
                for (int i = 0 ; i < 27 ; i ++) {
                    ItemStack itemStack = barrelBlockEntity.getItem(i);
                    if (!itemStack.is(Items.AIR)) {
                        Compute.PlayerItemDeleteWithRecord(player,itemStack);
                        barrelBlockEntity.setItem(i,Items.AIR.getDefaultInstance());
                    }
                }
            }*/
            if (result.equals(Items.CHEST) && event.getSide().isServer() && !Utils.rewardChestPos.contains(blockPos)) event.setCanceled(true);
            if (result.equals(Items.CHEST) && event.getSide().isServer() && Utils.rewardChestPos.contains(blockPos)) {
                if (player.getItemInHand(InteractionHand.MAIN_HAND).is(Items.AIR)) {
                    if (Utils.playerIsUsingBlockBlockPosMap.containsValue(blockPos)) {
                        Compute.FormatMSGSend(player,Component.literal("奖励箱").withStyle(ChatFormatting.LIGHT_PURPLE),
                                Component.literal("有其他玩家正在获取这个奖励箱的内容，请稍等片刻。").withStyle(ChatFormatting.WHITE));
                        event.setCanceled(true);
                    }
                    else {
                        boolean IsCoolingDown = false;
                        if (Utils.playerRewardChestCoolDown.containsKey(player.getName().getString())) {
                            Map<BlockPos,Calendar> map = Utils.playerRewardChestCoolDown.get(player.getName().getString());
                            if (map.containsKey(blockPos)) {
                                Calendar lastTime = map.get(blockPos);
                                Calendar currentTime = Calendar.getInstance();
                                if ((currentTime.getTimeInMillis() - lastTime.getTimeInMillis()) / (1000 * 60 * 60) < 24) {
                                    IsCoolingDown = true;
                                }
                            }
                        }
                        if (!IsCoolingDown) {
                            ChestBlockEntity chestBlockEntity = (ChestBlockEntity) player.level().getBlockEntity(blockPos);
                            Random random = new Random();
                            if (Utils.rewardItemList.isEmpty()) Utils.setRewardItemList();
                            ItemStack itemStack = Utils.rewardItemList.get(random.nextInt(Utils.rewardItemList.size()));
                            ItemStack reward = new ItemStack(itemStack.getItem(),itemStack.getCount());
                            for (int i = 0 ; i < 27 ; i ++) chestBlockEntity.setItem(i,Items.AIR.getDefaultInstance());
                            chestBlockEntity.setItem(random.nextInt(27),reward);
                            if (!Utils.playerRewardChestCoolDown.containsKey(player.getName().getString())) {
                                Utils.playerRewardChestCoolDown.put(player.getName().getString(),new HashMap<>());
                                Utils.playerRewardChestCoolDown.get(player.getName().getString()).put(blockPos,Calendar.getInstance());
                            }
                            else {
                                Utils.playerRewardChestCoolDown.get(player.getName().getString()).put(blockPos,Calendar.getInstance());
                            }
                            CompoundTag data = player.getPersistentData();
                            data.putInt(StringUtils.RewardChestCount,data.getInt(StringUtils.RewardChestCount) + 1);
                            Compute.FormatMSGSend(player,Component.literal("奖励箱").withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("你找到了一个奖励箱!").withStyle(ChatFormatting.WHITE));
                            Utils.playerIsUsingBlockBlockPosMap.put(player.getName().getString(),blockPos);
                        }
                        else {
                            event.setCanceled(true);
                            Compute.FormatMSGSend(player,Component.literal("奖励箱").withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("你最近已经打开过这个奖励箱了。").withStyle(ChatFormatting.WHITE));
                        }
                    }
                }
                else {
                    event.setCanceled(true);
                    Compute.FormatMSGSend(player,Component.literal("奖励箱").withStyle(ChatFormatting.LIGHT_PURPLE),
                            Component.literal("为确保物品能够正确被拾取，请空手右击箱子打开。").withStyle(ChatFormatting.WHITE));
                }
            }
        }
    }

    @SubscribeEvent
    public static void PlayerContainerCloseCheck(PlayerContainerEvent.Close event) {
        String playerName = event.getEntity().getName().getString();
        Utils.playerIsUsingBlockBlockPosMap.remove(playerName);
    }

    public static void RewardChest(PlayerInteractEvent.RightClickBlock event) {

    }

    @SubscribeEvent
    public static void Dig(net.minecraftforge.event.level.BlockEvent.BreakEvent event) throws IOException {
        if (!event.getPlayer().isCreative() && !event.getState().is(Blocks.FIRE)) event.setCanceled(true);
        MineEvent(event);
        WoodEvent(event);
        CropsInteract(event);
        TabooDevil.DetectDig(event);
        CastleSecondFloor.DetectDig(event);
    }

    public static void MineEvent(net.minecraftforge.event.level.BlockEvent.BreakEvent event) throws IOException {
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
                BlockPos blockPos1 = new BlockPos(2025,45,1280);
                BlockPos blockPos2 = new BlockPos(1982,24,1243);
                BlockPos blockPos3 = new BlockPos(1955,45,1231);
                BlockPos blockPos4 = new BlockPos(1901,24,1194);
                if ((blockPos.getX() < blockPos1.getX() && blockPos.getX() > blockPos2.getX()
                        && blockPos.getY() < blockPos1.getY() && blockPos.getY() > blockPos2.getY()
                        && blockPos.getZ() < blockPos1.getZ() && blockPos.getZ() > blockPos2.getZ())
                        || (blockPos.getX() < blockPos3.getX() && blockPos.getX() > blockPos4.getX()
                        && blockPos.getY() < blockPos3.getY() && blockPos.getY() > blockPos4.getY()
                        && blockPos.getZ() < blockPos3.getZ() && blockPos.getZ() > blockPos4.getZ())) {
                    if (event.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof PurplePickaxe) {
                        if (blockState.is(Blocks.AMETHYST_BLOCK)) {
                            Utils.playerDigCount ++;
                            Compute.ItemStackGive(player,ModItems.PurpleIronPiece.get().getDefaultInstance());
                            MineReward(player,blockState);
                        }
                        Utils.playerDigPos.add(new BlockSP(blockPos,blockState));
                        level.destroyBlock(blockPos,false);
                    }
                    else {
                        Compute.FormatMSGSend(player,Component.literal("采掘").withStyle(CustomStyle.styleOfPurpleIron),
                                Component.literal("这里的石头与矿石只有用紫晶铁镐才能采集哦！").withStyle(ChatFormatting.WHITE));
                    }
                }
                else {
                    if (blockPos.getY() <= 11) {
                        if (Utils.canBeDigBlockList.contains(blockState.getBlock())) {
                            if (Utils.mineExpMap.containsKey(blockState.getBlock())) {
                                BlockAndResetTime blockAndResetTime = new BlockAndResetTime(blockState,blockPos,tickCount + 36000);
                                if (!Utils.posEvenBeenDigOrPlace.contains(blockPos)) {
                                    Utils.worldMineList.add(blockAndResetTime);
                                    Utils.posEvenBeenDigOrPlace.add(blockPos);
                                }
                                Compute.ItemStackGive(player,Utils.mineDropMap.get(blockState.getBlock()).getDefaultInstance());
                                level.destroyBlock(blockPos,false);
                                MineReward(player,blockState);

                                Random random = new Random();
                                if (random.nextDouble() < Compute.playerExHarvest(player)) {
                                    Compute.FormatMSGSend(player, Component.literal("额外产出").withStyle(ChatFormatting.GOLD),
                                            Component.literal("为你提供了额外产物！").withStyle(ChatFormatting.WHITE));
                                    Compute.ItemStackGive(player,Utils.mineDropMap.get(blockState.getBlock()).getDefaultInstance());
                                    MineReward(player,blockState);
                                }
                            }
                            else {
                                BlockAndResetTime blockAndResetTime = new BlockAndResetTime(blockState,blockPos,tickCount + 1200);
                                if (!Utils.noMineDigMap.containsKey(player)) Utils.noMineDigMap.put(player,new LinkedList<>());
                                if (!Utils.posEvenBeenDigOrPlace.contains(blockPos)) {
                                    Utils.noMineDigMap.get(player).add(blockAndResetTime);
                                    Utils.posEvenBeenDigOrPlace.add(blockPos);
                                }
                                if ((blockState.is(Blocks.STONE) || blockState.is(Blocks.COBBLESTONE)) && Compute.ItemStackCount(player.getInventory(),Items.COBBLESTONE) < 32) {
                                    player.addItem(Items.COBBLESTONE.getDefaultInstance());
                                }
                                if ((blockState.is(Blocks.DEEPSLATE) || blockState.is(Blocks.COBBLED_DEEPSLATE)) && Compute.ItemStackCount(player.getInventory(),Items.COBBLED_DEEPSLATE) < 32) {
                                    player.addItem(Items.COBBLED_DEEPSLATE.getDefaultInstance());
                                }
                                if (blockState.is(Blocks.ANDESITE)) player.addItem(Items.ANDESITE.getDefaultInstance());
                                if (blockState.is(Blocks.DIORITE)) player.addItem(Items.DIORITE.getDefaultInstance());
                                level.destroyBlock(blockPos,false);
                                if (Utils.noMineDigMap.get(player).size() > 80) {
                                    BlockAndResetTime blockAndResetTime1 = Utils.noMineDigMap.get(player).poll();
                                    level.setBlockAndUpdate(blockAndResetTime1.getBlockPos(),blockAndResetTime1.getBlockState());
                                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime1.getBlockPos());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void BlockPlaceEvent(net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide && !player.isCreative()) {
            Level level = player.level();
            Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
            BlockState blockState = event.getPlacedBlock();
            BlockPos blockPos = event.getBlockSnapshot().getPos();
            BlockState targetState = event.getBlockSnapshot().getReplacedBlock();
            int tickCount = level.getServer().getTickCount();
            if (level.equals(overWorld) && blockPos.getY() <= 11
                    && (blockState.is(Blocks.COBBLESTONE) || blockState.is(Blocks.COBBLED_DEEPSLATE))
                    && !targetState.getFluidState().is(Fluids.WATER) && !targetState.getFluidState().is(Fluids.LAVA)) {
                BlockAndResetTime blockAndResetTime = new BlockAndResetTime(blockState,blockPos,tickCount + 1200);
                if (!Utils.blockPlaceMap.containsKey(player)) Utils.blockPlaceMap.put(player,new LinkedList<>());
                if (!Utils.posEvenBeenDigOrPlace.contains(blockPos)) {
                    Utils.blockPlaceMap.get(player).add(blockAndResetTime);
                    Utils.posEvenBeenDigOrPlace.add(blockPos);
                }
                if (Utils.blockPlaceMap.get(player).size() > 40) {
                    BlockAndResetTime blockAndResetTime1 = Utils.blockPlaceMap.get(player).poll();
                    if (blockAndResetTime1.getBlockState().is(Blocks.COBBLESTONE)) player.addItem(Items.COBBLESTONE.getDefaultInstance());
                    if (blockAndResetTime1.getBlockState().is(Blocks.DEEPSLATE)) player.addItem(Items.COBBLED_DEEPSLATE.getDefaultInstance());
                    overWorld.destroyBlock(blockAndResetTime1.getBlockPos(),false);
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime1.getBlockPos());

                }
            }
            else event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void BlockResetEvent(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            int tick = event.player.getServer().getTickCount();
            Player player = event.player;
            Level overWorld = event.player.getServer().getLevel(Level.OVERWORLD);
            if (tick % 20 == 0 && Utils.noMineDigMap.containsKey(player) && Utils.noMineDigMap.get(player).peek() != null) {
                Queue<BlockAndResetTime> queue = Utils.noMineDigMap.get(player);
                while (queue.peek() != null && queue.peek().getResetTick() < tick) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(),blockAndResetTime.getBlockState());
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
                }
            }
            if (tick % 20 == 10 && Utils.blockPlaceMap.containsKey(player) && Utils.blockPlaceMap.get(player).peek() != null) {
                Queue<BlockAndResetTime> queue = Utils.blockPlaceMap.get(player);
                while (queue.peek() != null && queue.peek().getResetTick() < tick) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    if (blockAndResetTime.getBlockState().is(Blocks.COBBLESTONE)) player.addItem(Items.COBBLESTONE.getDefaultInstance());
                    if (blockAndResetTime.getBlockState().is(Blocks.DEEPSLATE)) player.addItem(Items.COBBLED_DEEPSLATE.getDefaultInstance());
                    overWorld.destroyBlock(blockAndResetTime.getBlockPos(),false);
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
                }
            }
        }
    }

    @SubscribeEvent
    public static void MineResetEvent(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            int tick = event.level.getServer().getTickCount();
            Level overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (event.level.equals(overWorld) && tick % 20 == 15 && Utils.worldMineList.peek() != null) {
                Queue<BlockAndResetTime> queue = Utils.worldMineList;
                while (queue.peek() != null && queue.peek().getResetTick() < tick) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(),blockAndResetTime.getBlockState());
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
                }
            }
        }
    }

    public static void PlayerLogOut(Player player) {
        Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
        if (Utils.noMineDigMap.containsKey(player) && Utils.noMineDigMap.get(player).peek() != null) {
            Queue<BlockAndResetTime> queue = Utils.noMineDigMap.get(player);
            while (queue.peek() != null) {
                BlockAndResetTime blockAndResetTime = queue.poll();
                overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(),blockAndResetTime.getBlockState());
                Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
            }
        }
        if (Utils.blockPlaceMap.containsKey(player) && Utils.blockPlaceMap.get(player).peek() != null) {
            Queue<BlockAndResetTime> queue = Utils.blockPlaceMap.get(player);
            while (queue.peek() != null) {
                BlockAndResetTime blockAndResetTime = queue.poll();
                if (blockAndResetTime.getBlockState().is(Blocks.COBBLESTONE)) player.addItem(Items.COBBLESTONE.getDefaultInstance());
                if (blockAndResetTime.getBlockState().is(Blocks.DEEPSLATE)) player.addItem(Items.COBBLED_DEEPSLATE.getDefaultInstance());
                overWorld.destroyBlock(blockAndResetTime.getBlockPos(),false);
                Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
            }
        }
    }

    public static void MineAndWoodReset(Level level) {
        LogUtils.getLogger().info("Resetting Mine And Wood.");
        Level overWorld = level.getServer().getLevel(Level.OVERWORLD);
        if (Utils.worldMineList.peek() != null) {
            Queue<BlockAndResetTime> queue = Utils.worldMineList;
            while (queue.peek() != null) {
                BlockAndResetTime blockAndResetTime = queue.poll();
                overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(),blockAndResetTime.getBlockState());
                Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
            }
        }
        while (Utils.worldWoodList.peek() != null) {
            BlockAndResetTime blockAndResetTime = Utils.worldWoodList.poll();
            overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(),blockAndResetTime.getBlockState());
        }
        Utils.noMineDigMap.keySet().forEach(player -> {
            if (Utils.noMineDigMap.containsKey(player) && Utils.noMineDigMap.get(player).peek() != null) {
                Queue<BlockAndResetTime> queue = Utils.noMineDigMap.get(player);
                while (queue.peek() != null) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(),blockAndResetTime.getBlockState());
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
                }
            }
            if (Utils.blockPlaceMap.containsKey(player) && Utils.blockPlaceMap.get(player).peek() != null) {
                Queue<BlockAndResetTime> queue = Utils.blockPlaceMap.get(player);
                while (queue.peek() != null) {
                    BlockAndResetTime blockAndResetTime = queue.poll();
                    if (blockAndResetTime.getBlockState().is(Blocks.COBBLESTONE)) player.addItem(Items.COBBLESTONE.getDefaultInstance());
                    if (blockAndResetTime.getBlockState().is(Blocks.DEEPSLATE)) player.addItem(Items.COBBLED_DEEPSLATE.getDefaultInstance());
                    overWorld.destroyBlock(blockAndResetTime.getBlockPos(),false);
                    Utils.posEvenBeenDigOrPlace.remove(blockAndResetTime.getBlockPos());
                }
            }
        });
    }

    public static void MineReward(Player player, BlockState blockState) {
        double baseExpRate = Utils.mineExpMap.get(blockState.getBlock());
        Compute.playerMineExpAdd(player,(int) (baseExpRate * 100));
        baseExpRate *= (1 + Compute.playerMineLevel(player)) * 0.25;
        Compute.ExpPercentGetAndMSGSend(player, baseExpRate, 0, Math.min(player.experienceLevel, 50));
        ClientboundSoundPacket clientboundSoundPacket = new ClientboundSoundPacket(Holder.direct(SoundEvents.EXPERIENCE_ORB_PICKUP), SoundSource.PLAYERS, player.getX(), player.getY(), player.getZ(), 0.5f, 1, 1);
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(clientboundSoundPacket);
        Utils.dayMineCount.put(player.getName().getString(),Utils.dayMineCount.getOrDefault(player.getName().getString(),0) + 1);
        LabourDayMission.count(player, LabourDayMission.mineCounts);
    }

    public static void CropsInteract(net.minecraftforge.event.level.BlockEvent.BreakEvent event) throws IOException {
        if (!event.getPlayer().level().isClientSide && !event.getPlayer().isCreative()) {
            Player player = event.getPlayer();
            BlockPos blockPos = event.getPos();
            Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
            HarvestCrops(player,blockPos,overWorld);
        }
    }

    public static void HarvestCrops(Player player, BlockPos blockPos, Level overWorld) throws IOException {
        if (player.level().equals(overWorld)) {
            if (blockPos.getX() > 1265 && blockPos.getX() < 1614
                    && blockPos.getZ() > 1297 && blockPos.getZ() < 1545) return;
            BlockState blockState = overWorld.getBlockState(blockPos);
            if (blockState.getBlock() instanceof CropBlock cropBlock) {
                if (cropBlock.getAge(blockState) == cropBlock.getMaxAge()) {
                    if (cropsPickDrop.containsKey(blockState.getBlock())) {
                        Compute.ItemStackGive(player, cropsPickDrop.get(blockState.getBlock()).getDefaultInstance());
                        CropsReward(player,blockState);

                        Random random = new Random();
                        if (random.nextDouble() < Compute.playerExHarvest(player)) {
                            Compute.FormatMSGSend(player, Component.literal("额外产出").withStyle(ChatFormatting.GOLD),
                                    Component.literal("为你提供了额外产物！").withStyle(ChatFormatting.WHITE));
                            Compute.ItemStackGive(player, cropsPickDrop.get(blockState.getBlock()).getDefaultInstance());
                            CropsReward(player,blockState);
                        }
                    }
                    BlockState blockState1 = cropBlock.getStateForAge(0);
                    overWorld.destroyBlock(blockPos,false);
                    overWorld.setBlockAndUpdate(blockPos,blockState1);
                }
            }
            if (blockState.is(Blocks.TORCHFLOWER)) {
                CropsReward(player,blockState);
                BlockState blockState1 = Blocks.TORCHFLOWER_CROP.defaultBlockState();
                overWorld.destroyBlock(blockPos,false);
                overWorld.setBlockAndUpdate(blockPos,blockState1);
                Compute.ItemStackGive(player,Items.TORCHFLOWER.getDefaultInstance());

                Random random = new Random();
                if (random.nextDouble() < Compute.playerExHarvest(player)) {
                    Compute.FormatMSGSend(player, Component.literal("额外产出").withStyle(ChatFormatting.GOLD),
                            Component.literal("为你提供了额外产物！").withStyle(ChatFormatting.WHITE));
                    Compute.ItemStackGive(player,Items.TORCHFLOWER.getDefaultInstance());
                    CropsReward(player,blockState);
                }
            }
        }
    }

    @SubscribeEvent
    public static void SweetBerries(PlayerInteractEvent.RightClickBlock event) {
        if (event.getSide().isServer()) {
            Player player = event.getEntity();
            BlockPos blockPos = event.getHitVec().getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);
            if (!player.isShiftKeyDown() && blockState.is(Blocks.SWEET_BERRY_BUSH) && (blockState.getValue(SweetBerryBushBlock.AGE) == SweetBerryBushBlock.MAX_AGE || blockState.getValue(SweetBerryBushBlock.AGE) == 2)) {
                CropsReward(player,blockState);
            }
/*            BlockEntity blockEntity = event.getEntity().level().getBlockEntity(blockPos);
            if (blockEntity instanceof ChestBlockEntity chestBlockEntity) {
                chestBlockEntity.setItem(0,new ItemStack(ModItems.GoldCoin.get(),5));
            }*/
        }
    }

    public static void CropsReward(Player player, BlockState blockState) {
        CompoundTag data = player.getPersistentData();
        if (blockState.is(Blocks.WHEAT)) data.putInt(StringUtils.Gardening.Wheat,data.getInt(StringUtils.Gardening.Wheat) + 1);
        if (blockState.is(Blocks.CARROTS)) data.putInt(StringUtils.Gardening.Carrot,data.getInt(StringUtils.Gardening.Carrot) + 1);
        if (blockState.is(Blocks.POTATOES)) data.putInt(StringUtils.Gardening.Potato,data.getInt(StringUtils.Gardening.Potato) + 1);
        if (blockState.is(Blocks.BEETROOTS)) data.putInt(StringUtils.Gardening.Beet,data.getInt(StringUtils.Gardening.Beet) + 1);
        if (blockState.is(Blocks.TORCHFLOWER)) data.putInt(StringUtils.Gardening.Torch,data.getInt(StringUtils.Gardening.Torch) + 1);
        if (blockState.is(Blocks.SWEET_BERRY_BUSH)) data.putInt(StringUtils.Gardening.SweetBerries,data.getInt(StringUtils.Gardening.SweetBerries) + 1);

        Compute.playerGardeningExpAdd(player,2);
        Compute.ExpPercentGetAndMSGSend(player, 0.0025, 0, Math.min(player.experienceLevel, 50));
        Utils.dayCropCount.put(player.getName().getString(),Utils.dayCropCount.getOrDefault(player.getName().getString(),0) + 1);
        LabourDayMission.count(player, LabourDayMission.cropCounts);
    }

    public static Map<Block,Item> cropsPickDrop = new HashMap<>(){{
        put(Blocks.WHEAT, Items.WHEAT);
        put(Blocks.CARROTS, Items.CARROT);
        put(Blocks.POTATOES, Items.POTATO);
        put(Blocks.BEETROOTS, Items.BEETROOT);
        put(Blocks.TORCHFLOWER, Items.TORCHFLOWER);
    }};

    public static void WoodEvent(net.minecraftforge.event.level.BlockEvent.BreakEvent event) throws IOException {
        if (!event.getPlayer().level().isClientSide && !event.getPlayer().isCreative()) {
            Player player = event.getPlayer();
            BlockPos blockPos = event.getPos();
            Level overWorld = player.getServer().getLevel(Level.OVERWORLD);
            Level level = player.level();
            BlockState blockState = level.getBlockState(blockPos);
            if (level.equals(overWorld)) {
                if (WoodMap.containsKey(blockState.getBlock())) {
                    boolean HasLeafOnRoof = false;
                    BlockPos blockPos1 = blockPos.above();
                    while (!level.getBlockState(blockPos1).is(Blocks.AIR)) {
                        if (level.getBlockState(blockPos1).is(LeafMap.get(blockState.getBlock()))) {
                            HasLeafOnRoof = true;
                            break;
                        }
                        blockPos1 = blockPos1.above();
                    }
                    if (HasLeafOnRoof) {
                        blockPos1 = blockPos1.below();
                        while (!WoodMap.containsKey(level.getBlockState(blockPos1).getBlock())) {
                            blockPos1 = blockPos1.below();
                        }
                        WoodReward(player,blockState);
                        Compute.ItemStackGive(player,new ItemStack(blockState.getBlock().asItem(),2));
                        Utils.worldWoodList.add(new BlockAndResetTime(blockState,blockPos1,level.getServer().getTickCount() + 36000));
                        level.setBlockAndUpdate(blockPos1,WoodMap.get(level.getBlockState(blockPos1).getBlock()).defaultBlockState());

                        Random random = new Random();
                        if (random.nextDouble() < Compute.playerExHarvest(player)) {
                            Compute.FormatMSGSend(player, Component.literal("额外产出").withStyle(ChatFormatting.GOLD),
                                    Component.literal("为你提供了额外产物！").withStyle(ChatFormatting.WHITE));
                            WoodReward(player,blockState);
                            Compute.ItemStackGive(player,new ItemStack(blockState.getBlock().asItem(),2));
                        }
                    }
                }
            }
        }
    }

    public static Map<Block,Block> WoodMap = new HashMap<>(){{
        put(Blocks.OAK_LOG,Blocks.STRIPPED_OAK_LOG);
        put(Blocks.SPRUCE_LOG,Blocks.STRIPPED_SPRUCE_LOG);
        put(Blocks.BIRCH_LOG,Blocks.STRIPPED_BIRCH_LOG);
        put(Blocks.JUNGLE_LOG,Blocks.STRIPPED_JUNGLE_LOG);
        put(Blocks.ACACIA_LOG,Blocks.STRIPPED_ACACIA_LOG);
        put(Blocks.DARK_OAK_LOG,Blocks.STRIPPED_DARK_OAK_LOG);
        put(Blocks.MANGROVE_LOG,Blocks.STRIPPED_MANGROVE_LOG);
        put(Blocks.CHERRY_LOG,Blocks.STRIPPED_CHERRY_LOG);
    }};

    public static Map<Block,Block> LeafMap = new HashMap<>(){{
        put(Blocks.OAK_LOG,Blocks.OAK_LEAVES);
        put(Blocks.SPRUCE_LOG,Blocks.SPRUCE_LEAVES);
        put(Blocks.BIRCH_LOG,Blocks.BIRCH_LEAVES);
        put(Blocks.JUNGLE_LOG,Blocks.JUNGLE_LEAVES);
        put(Blocks.ACACIA_LOG,Blocks.ACACIA_LEAVES);
        put(Blocks.DARK_OAK_LOG,Blocks.DARK_OAK_LEAVES);
        put(Blocks.MANGROVE_LOG,Blocks.MANGROVE_LEAVES);
        put(Blocks.CHERRY_LOG,Blocks.CHERRY_LEAVES);
    }};

    @SubscribeEvent
    public static void WoodReset(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START)) {
            int tick = event.level.getServer().getTickCount();
            Level overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (event.level.equals(overWorld) && tick % 20 == 5) {
                if (Utils.worldWoodList.peek() != null) {
                    while (Utils.worldWoodList.peek() != null && Utils.worldWoodList.peek().getResetTick() < tick) {
                        BlockAndResetTime blockAndResetTime = Utils.worldWoodList.poll();
                        overWorld.setBlockAndUpdate(blockAndResetTime.getBlockPos(),blockAndResetTime.getBlockState());
                    }
                }
            }
        }
    }

    public static void  WoodReward(Player player, BlockState blockState) {
        CompoundTag data = player.getPersistentData();
        if (blockState.is(Blocks.OAK_LOG)) data.putInt(StringUtils.Lop.OAK,data.getInt(StringUtils.Lop.OAK) + 1);
        if (blockState.is(Blocks.SPRUCE_LOG)) data.putInt(StringUtils.Lop.SPRUCE,data.getInt(StringUtils.Lop.SPRUCE) + 1);
        if (blockState.is(Blocks.BIRCH_LOG)) data.putInt(StringUtils.Lop.BIRCH,data.getInt(StringUtils.Lop.BIRCH) + 1);
        if (blockState.is(Blocks.JUNGLE_LOG)) data.putInt(StringUtils.Lop.JUNGLE,data.getInt(StringUtils.Lop.JUNGLE) + 1);
        if (blockState.is(Blocks.ACACIA_LOG)) data.putInt(StringUtils.Lop.ACACIA,data.getInt(StringUtils.Lop.ACACIA) + 1);
        if (blockState.is(Blocks.DARK_OAK_LOG)) data.putInt(StringUtils.Lop.DARK_OAK,data.getInt(StringUtils.Lop.DARK_OAK) + 1);
        if (blockState.is(Blocks.MANGROVE_LOG)) data.putInt(StringUtils.Lop.MANGROVE,data.getInt(StringUtils.Lop.MANGROVE) + 1);
        if (blockState.is(Blocks.CHERRY_LOG)) data.putInt(StringUtils.Lop.CHERRY,data.getInt(StringUtils.Lop.CHERRY) + 1);

        Compute.playerLopExpAdd(player,2);
        Compute.ExpPercentGetAndMSGSend(player, 0.005, 0, Math.min(player.experienceLevel, 50));

        Utils.dayLopCount.put(player.getName().getString(),Utils.dayLopCount.getOrDefault(player.getName().getString(),0) + 1);

        if (data.contains(StringUtils.Lop.Xp) && !data.contains(StringUtils.LogReward)) {
            try {
                Compute.ItemStackGive(player,new ItemStack(ModItems.LogBag.get(),data.getInt(StringUtils.Lop.Xp)/256));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            data.putBoolean(StringUtils.LogReward,true);
        }

        LabourDayMission.count(player, LabourDayMission.lopCounts);
    }

    @SubscribeEvent
    public static void BoatBanEvent(PlayerInteractEvent.RightClickItem event) {
        Vec3 vec3 = event.getEntity().pick(4,0,false).getLocation();
        if (!event.getLevel().getBlockState(new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z)).is(Blocks.WATER)
                && event.getItemStack().getItem() instanceof BoatItem) event.setCanceled(true);
    }

}

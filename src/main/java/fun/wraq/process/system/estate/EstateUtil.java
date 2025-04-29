package fun.wraq.process.system.estate;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class EstateUtil {
    public static void onPlayerRightClickInfoSignBlock(Player player, BlockPos blockPos) {
        if (!(player.level().getBlockState(blockPos).getBlock() instanceof SignBlock)) {
            return;
        }
        List<EstateInfo> estateInfos = Arrays.stream(EstateInfo.values()).toList();
        EstateInfo estateInfo = estateInfos
                .stream().filter(estateInfo1 -> estateInfo1.infoSignBlockPos.equals(blockPos))
                .findFirst()
                .orElse(null);
        // 若右击告示牌非房产信息告示牌，则不执行后续逻辑。
        if (estateInfo == null) {
            return;
        }
        EstateServerData estateServerData = EstateServerData.getEstateServerData(estateInfo.ordinal());
        if (estateServerData == null) {
            // 表明当前信息牌对应房产暂时还没有所有者
            if (EstatePlayerData.hasEstate(player)) {
                // 表明当前玩家已经拥有房产，不能再进行购买
                sendMSG(player, Te.s("你已经拥有了一套房产，不能再次购买."));
            } else {
                // 判断是否购买
                if (player.isShiftKeyDown()) {
                    if (Compute.getCurrentVB(player) >= estateInfo.price) {
                        Compute.VBExpenseAndMSGSend(player, estateInfo.price);
                        EstateServerData.editEstateServerData(
                                new EstateServerData(estateInfo.ordinal(), Name.get(player), Calendar.getInstance()));
                        EstatePlayerData.setEstateData(player, estateInfo.ordinal(), "");
                        broad(Te.s(player,
                                "以", String.format(" %.0fVB ", estateInfo.price), CustomStyle.styleOfGold,
                                "购买了 ", estateInfo.estateName.getString(), CustomStyle.styleOfGold));
                        setSignBlockText(player.level(), blockPos,
                                estateInfo.estateName,
                                Te.s(estateInfo.floorInfo, ChatFormatting.BLACK),
                                Te.s(Name.get(player), ChatFormatting.BLACK),
                                Te.s("长期有效", ChatFormatting.BLACK));
                        InventoryOperation.giveItemStackWithMSG(player, ModItems.ESTATE_KEY.get());
                    } else {
                        sendMSG(player, Te.s("所需货币不足."));
                    }
                } else {
                    sendMSG(player, Te.s("潜行右键来购买该房产."));
                }
            }
        } else {
            // 若当前信息牌对应房产已被购买，则发送当前房产信息
            sendMSG(player, Te.s("房产名: ", estateInfo.estateName.getString(), CustomStyle.styleOfGold));
            sendMSG(player, Te.s("所有者: ", estateServerData.ownerId));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            sendMSG(player, Te.s("购买时间: ", dateFormat.format(estateServerData.boughtDate.getTime())));
            sendMSG(player, Te.s("购买价格: ",
                    String.format("%.0fVB", estateInfo.price), ChatFormatting.GOLD));
        }
    }

    public static boolean canPlayerRightClickDoor(Player player, BlockPos blockPos) {
        BlockState blockState = player.level().getBlockState(blockPos);
        if (!(player.level().getBlockState(blockPos).getBlock() instanceof DoorBlock)) {
            return true;
        }
        List<EstateInfo> estateInfos = Arrays.stream(EstateInfo.values()).toList();
        EstateInfo estateInfo = estateInfos
                .stream().filter(estateInfo1 -> estateInfo1.doorBlockPos1.equals(blockPos)
                        || estateInfo1.doorBlockPos1.equals(blockPos.above())
                        || estateInfo1.doorBlockPos2.equals(blockPos)
                        || estateInfo1.doorBlockPos2.equals(blockPos.above()))
                .findFirst().orElse(null);
        // 表明这并不是一个房产的门
        if (estateInfo == null) {
            return true;
        }
        EstateServerData estateServerData = EstateServerData.getEstateServerData(estateInfo.ordinal());
        if (estateServerData != null) {
            if (EstatePlayerData.getEstateSerial(player) == estateInfo.ordinal()) {
                switchDoor(player, estateInfo.doorBlockPos1);
                if (!estateInfo.doorBlockPos2.equals(estateInfo.doorBlockPos1) && blockState.is(Blocks.IRON_DOOR)) {
                    switchDoor(player, estateInfo.doorBlockPos2);
                }
            } else {
                sendMSG(player, Te.s("该房产属于 ", estateServerData.ownerId));
            }
        } else {
            switchDoor(player, estateInfo.doorBlockPos1);
            if (!estateInfo.doorBlockPos2.equals(estateInfo.doorBlockPos1) && blockState.is(Blocks.IRON_DOOR)) {
                switchDoor(player, estateInfo.doorBlockPos2);
            }
        }
        return false;
    }

    public static void switchDoor(Player player, BlockPos blockPos) {
        Level level = player.level();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block instanceof DoorBlock doorBlock) {
            doorBlock.setOpen(player, level, blockState, blockPos, !doorBlock.isOpen(blockState));
            MySound.soundToNearPlayer(player.level(), blockPos.getCenter(),
                    doorBlock.isOpen(blockState) ? SoundEvents.IRON_DOOR_OPEN : SoundEvents.IRON_DOOR_CLOSE);
        }
    }

    public static void setSignBlockText(Level level, BlockPos blockPos,
                                        Component line1Text, Component line2Text,
                                        Component line3Text, Component line4Text) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof SignBlockEntity signBlockEntity) {
            signBlockEntity.setText(new SignText(new Component[]{
                    line1Text,
                    line2Text,
                    line3Text,
                    line4Text},
                    new Component[]{
                            Te.s("被系统编辑的告示牌"),
                            Te.s("被系统编辑的告示牌"),
                            Te.s("被系统编辑的告示牌"),
                            Te.s("被系统编辑的告示牌")},
                    DyeColor.BLACK, false), true);
        }
    }

    public static void resetSignBlockText(EstateInfo estateInfo) {
        EstateUtil.setSignBlockText(Tick.server.overworld(), estateInfo.infoSignBlockPos,
                estateInfo.estateName,
                Te.s(estateInfo.floorInfo, ChatFormatting.BLACK),
                Te.s("售卖中", ChatFormatting.BLACK),
                Te.s(String.format("%.0fVB", estateInfo.price), ChatFormatting.BLACK));
    }

    public static boolean canEditSingBlock(Player player, BlockPos blockPos) {
        return EstatePlayerData.hasEstate(player)
                && EstateInfo.values()[EstatePlayerData.getEstateSerial(player)].editableSignBlockPos.equals(blockPos);
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("房产", CustomStyle.styleOfGold), content);
    }

    public static void broad(Component content) {
        Compute.formatBroad(Te.s("房产", CustomStyle.styleOfGold), content);
    }
}

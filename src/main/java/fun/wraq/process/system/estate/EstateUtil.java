package fun.wraq.process.system.estate;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class EstateUtil {

    public static int clientEstateSerialNum = -1;
    public static int clientRealEstateSerialNum = -1;

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
            int type = estateInfo.type;
            if (type == 0 && EstatePlayerData.hasEstate(player)) {
                // 表明当前玩家已经拥有房产，不能再进行购买
                sendMSG(player, Te.s("你已经拥有了一套房产，不能再次购买."));
                return;
            }
            if (type == 1 && EstatePlayerData.hasRealEstate(player)) {
                sendMSG(player, Te.s("你已经拥有了一套不动产，不能再次购买."));
                return;
            }
            if (player.isShiftKeyDown()) {
                double price = estateInfo.price;
                price *= PlanPlayer.getEstatePriceRate(player);
                if (type == 0 && Compute.getCurrentVB(player) < price) {
                    sendMSG(player, Te.s("所需货币不足. 需要",
                            (int) price + "VB", ChatFormatting.GOLD));
                    return;
                }
                if (type == 1 && !InventoryOperation
                        .checkPlayerHasItem(player, ModItems.GOLDEN_BEANS.get(), (int) price)) {
                    sendMSG(player, Te.s("所需货币不足. 需要",
                            (int) price + "GB", ChatFormatting.GOLD));
                    return;
                }
                if (type == 0) {
                    Compute.VBExpenseAndMSGSend(player, price);
                    EstatePlayerData.setEstateData(player, estateInfo.ordinal(),
                            Compute.castCalendarToString(Calendar.getInstance()));
                    InventoryOperation.giveItemStackWithMSG(player, ModItems.ESTATE_KEY.get());
                }
                if (type == 1) {
                    InventoryOperation.removeItem(player, ModItems.GOLDEN_BEANS.get(), (int) price);
                    EstatePlayerData.setRealEstateData(player, estateInfo.ordinal(),
                            Compute.castCalendarToString(Calendar.getInstance()));
                    InventoryOperation.giveItemStackWithMSG(player, ModItems.REAL_ESTATE_KEY.get());
                }
                EstateServerData.editEstateServerData(
                        new EstateServerData(estateInfo.ordinal(), Name.get(player), Calendar.getInstance()));
                if (PlanPlayer.getPlayerTier(player) > 0) {
                    broad(Te.s(player,
                            "以", String.format(" %.0f" + (type == 0 ? "VB " : "GB "), price), CustomStyle.styleOfGold,
                            "(" + PlanPlayer.getEstateDiscountRate(player) + ")", CustomStyle.styleOfWorld,
                            "购买了 ", estateInfo.estateName.getString(), CustomStyle.styleOfGold));
                } else {
                    broad(Te.s(player,
                            "以", String.format(" %.0f" + (type == 0 ? "VB " : "GB "), price), CustomStyle.styleOfGold,
                            "购买了 ", estateInfo.estateName.getString(), CustomStyle.styleOfGold));
                }
                setSignBlockText(player.level(), blockPos,
                        estateInfo.estateName,
                        Te.s(estateInfo.floorInfo, ChatFormatting.BLACK),
                        Te.s(Name.get(player), ChatFormatting.BLACK),
                        Te.s("长期有效", ChatFormatting.BLACK));
                EstatePlayerData.sendDataToClient(player);
                sendMSG(player, Te.s("每日首次打开资产门可获取收益；资产的每日收益在购买的第二天方能获取."));
                MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
            } else {
                sendMSG(player, Te.s("潜行右键来购买该资产."));
            }
        } else {
            // 若当前信息牌对应房产已被购买，则发送当前房产信息
            sendMSG(player, Te.s("资产名: ", estateInfo.estateName.getString(), CustomStyle.styleOfGold));
            sendMSG(player, Te.s("所有者: ", estateServerData.ownerId));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            sendMSG(player, Te.s("购买时间: ", dateFormat.format(estateServerData.boughtDate.getTime())));
            sendMSG(player, Te.s("购买价格: ",
                    String.format("%.0f", estateInfo.price) + (estateInfo.type == 0 ? "VB" : "GB"),
                    ChatFormatting.GOLD));
            if (EstatePlayerData.getEstateSerial(player) == estateInfo.ordinal()) {
                sendMSG(player, Te.s("使用", "/vmd sellEstate", ChatFormatting.GOLD, "来出售该资产."));
            }
            if (EstatePlayerData.getRealEstateSerial(player) == estateInfo.ordinal()) {
                sendMSG(player, Te.s("使用", "/vmd sellRealEstate", ChatFormatting.GOLD, "来出售该资产."));
            }
        }
    }

    public static void onPlayerRightClickDoor(Player player, BlockPos blockPos) {
        List<EstateInfo> estateInfos = Arrays.stream(EstateInfo.values()).toList();
        EstateInfo estateInfo = estateInfos
                .stream().filter(estateInfo1 -> estateInfo1.doorBlockPos1.equals(blockPos)
                        || estateInfo1.doorBlockPos1.equals(blockPos.above())
                        || estateInfo1.doorBlockPos2.equals(blockPos)
                        || estateInfo1.doorBlockPos2.equals(blockPos.above()))
                .findFirst().orElse(null);
        // 表明这并不是一个资产的门
        if (estateInfo == null) {
            return;
        }
        EstateServerData estateServerData = EstateServerData.getEstateServerData(estateInfo.ordinal());
        if (estateServerData != null) {
            if (EstatePlayerData.getEstateSerial(player) == estateInfo.ordinal()
                    || EstatePlayerData.getRealEstateSerial(player) == estateInfo.ordinal()) {
                EstatePlayerData.onPlayerOpenDoor(player, estateInfo.ordinal());
            } else {
                sendMSG(player, Te.s("该资产属于 ", estateServerData.ownerId));
            }
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
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        EstateUtil.setSignBlockText(Tick.server.overworld(), estateInfo.infoSignBlockPos,
                estateInfo.estateName,
                Te.s(estateInfo.floorInfo, ChatFormatting.BLACK),
                Te.s("售卖中", ChatFormatting.BLACK),
                Te.s(decimalFormat.format(estateInfo.price)
                        + (estateInfo.type == 0 ? "VB" : "GB"), ChatFormatting.BLACK));
        if (!estateInfo.editableSignBlockPos.equals(BlockPos.ZERO)) {
            EstateUtil.setSignBlockText(Tick.server.overworld(), estateInfo.editableSignBlockPos,
                    Te.s("所有者可编辑"), Te.s(""), Te.s(""), Te.s(""));
        }
    }

    public static void resetAllSignBlockText() {
        for (EstateInfo estateInfo : EstateInfo.values()) {
            EstateServerData serverData = EstateServerData.getEstateServerData(estateInfo.ordinal());
            if (serverData == null) {
                resetSignBlockText(estateInfo);
            } else {
                setSignBlockText(Tick.server.overworld(), estateInfo.infoSignBlockPos,
                        estateInfo.estateName,
                        Te.s(estateInfo.floorInfo, ChatFormatting.BLACK),
                        Te.s(serverData.ownerId, ChatFormatting.BLACK),
                        Te.s("长期有效", ChatFormatting.BLACK));
                if (!estateInfo.editableSignBlockPos.equals(BlockPos.ZERO)) {
                    EstateUtil.setSignBlockText(Tick.server.overworld(), estateInfo.editableSignBlockPos,
                            Te.s("所有者可编辑"), Te.s(""),
                            Te.s(""), Te.s(""));
                }
            }
        }
    }

    public static boolean canEditSingBlock(Player player, BlockPos blockPos) {
        if (player.level().isClientSide) {
            return (EstateUtil.clientEstateSerialNum != -1
                    && EstateInfo.values()[EstateUtil.clientEstateSerialNum].editableSignBlockPos.equals(blockPos))
                    || (EstateUtil.clientRealEstateSerialNum != -1
                    && EstateInfo.values()[EstateUtil.clientRealEstateSerialNum].editableSignBlockPos.equals(blockPos));
        } else {
            return (EstatePlayerData.hasEstate(player)
                    && EstateInfo.values()[EstatePlayerData.getEstateSerial(player)].editableSignBlockPos.equals(blockPos))
                    || EstatePlayerData.hasRealEstate(player)
                    && EstateInfo.values()[EstatePlayerData.getRealEstateSerial(player)].editableSignBlockPos.equals(blockPos);
        }
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("资产", CustomStyle.styleOfGold), content);
    }

    public static void broad(Component content) {
        Compute.formatBroad(Te.s("资产", CustomStyle.styleOfGold), content);
    }

    public static boolean clientPlayerCanOpenDoor(BlockPos blockPos) {
        boolean flag = false;
        if (clientEstateSerialNum != -1) {
            EstateInfo estateInfo = EstateInfo.values()[clientEstateSerialNum];
            flag = estateInfo.doorBlockPos1.equals(blockPos) || estateInfo.doorBlockPos1.below().equals(blockPos)
                    || estateInfo.doorBlockPos2.equals(blockPos) || estateInfo.doorBlockPos2.below().equals(blockPos);
        }
        if (flag) {
            return flag;
        }
        if (clientRealEstateSerialNum != -1) {
            EstateInfo estateInfo = EstateInfo.values()[clientRealEstateSerialNum];
            flag = estateInfo.doorBlockPos1.equals(blockPos) || estateInfo.doorBlockPos1.below().equals(blockPos)
                    || estateInfo.doorBlockPos2.equals(blockPos) || estateInfo.doorBlockPos2.below().equals(blockPos);
        }
        return flag;
    }

    public static void removeEstate(Player player) {
        int serial = EstatePlayerData.getEstateSerial(player);
        EstateInfo estateInfo = EstateInfo.values()[serial];
        EstateServerData.removeEstateServerData(serial);
        EstatePlayerData.setEstateData(player, -1, "");
        EstateUtil.sendMSG(player, Te.s("你已失去",
                estateInfo.estateName.getString(), CustomStyle.styleOfGold, "的所有权."));
        EstateUtil.resetSignBlockText(estateInfo);
    }

    public static void sellEstate(Player player) {
        int serial = EstatePlayerData.getEstateSerial(player);
        if (serial != -1) {
            EstateInfo estateInfo = EstateInfo.values()[serial];
            int price = (int) estateInfo.price;
            removeEstate(player);
            Compute.VBIncomeAndMSGSend(player, price * 0.5);
        } else {
            sendMSG(player, Te.s("暂时没有资产可以出售."));
        }
    }

    public static void removeRealEstate(Player player) {
        int serial = EstatePlayerData.getRealEstateSerial(player);
        EstateInfo estateInfo = EstateInfo.values()[serial];
        EstateServerData.removeEstateServerData(serial);
        EstatePlayerData.setRealEstateData(player, -1, "");
        EstateUtil.sendMSG(player, Te.s("你已失去",
                estateInfo.estateName.getString(), CustomStyle.styleOfGold, "的所有权."));
        EstateUtil.resetSignBlockText(estateInfo);
    }

    public static void sellRealEstate(Player player) {
        int serial = EstatePlayerData.getRealEstateSerial(player);
        if (serial != -1) {
            EstateInfo estateInfo = EstateInfo.values()[serial];
            int price = (int) estateInfo.price;
            removeRealEstate(player);
            InventoryOperation.giveItemStackWithMSG(player, ModItems.GOLDEN_BEANS.get(), (int) (price * 0.5));
        } else {
            sendMSG(player, Te.s("暂时没有资产可以出售."));
        }
    }
}

package fun.wraq.process.system.estate;

import fun.wraq.common.fast.Te;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public enum EstateInfo {

    YU_LIN_APARTMENT_201(Te.s("玉林公寓(201)", ChatFormatting.BLACK),
            "Floor 2",
            new BlockPos(1053, 87, -11), BlockPos.ZERO,
            new BlockPos(1051, 87, -12), new BlockPos(1051, 87, -12),
            7500000),
    YU_LIN_APARTMENT_301(Te.s("玉林公寓(301)", ChatFormatting.BLACK),
            "Floor 3",
            new BlockPos(1053, 93, -11), BlockPos.ZERO,
            new BlockPos(1051, 93, -12), new BlockPos(1051, 93, -12),
            3000000),
    YU_LIN_APARTMENT_302(Te.s("玉林公寓(302)", ChatFormatting.BLACK),
            "Floor 3",
            new BlockPos(1054, 93, -11), BlockPos.ZERO,
            new BlockPos(1056, 93, -12), new BlockPos(1056, 93, -12),
            3000000),
    YU_LIN_APARTMENT_401(Te.s("玉林公寓(401)", ChatFormatting.BLACK),
            "Floor 4",
            new BlockPos(1053, 99, -11), BlockPos.ZERO,
            new BlockPos(1051, 99, -12), new BlockPos(1051, 99, -12),
            3000000),
    YU_LIN_APARTMENT_402(Te.s("玉林公寓(402)", ChatFormatting.BLACK),
            "Floor 4",
            new BlockPos(1054, 99, -11), BlockPos.ZERO,
            new BlockPos(1056, 99, -12), new BlockPos(1056, 99, -12),
            3000000),
    YU_LIN_APARTMENT_501(Te.s("玉林公寓(501)", ChatFormatting.BLACK),
            "Floor 5",
            new BlockPos(1053, 105, -11), BlockPos.ZERO,
            new BlockPos(1051, 105, -12), new BlockPos(1051, 105, -12),
            3000000),
    YU_LIN_APARTMENT_502(Te.s("玉林公寓(502)", ChatFormatting.BLACK),
            "Floor 5",
            new BlockPos(1054, 105, -11), BlockPos.ZERO,
            new BlockPos(1056, 105, -12), new BlockPos(1056, 105, -12),
            3000000),
    YU_LIN_APARTMENT_601(Te.s("玉林公寓(601)", ChatFormatting.BLACK),
            "Floor 6",
            new BlockPos(1053, 111, -11), BlockPos.ZERO,
            new BlockPos(1051, 111, -12), new BlockPos(1051, 111, -12),
            4000000),
    YU_LIN_APARTMENT_602(Te.s("玉林公寓(602)", ChatFormatting.BLACK),
            "Floor 6",
            new BlockPos(1054, 111, -11), BlockPos.ZERO,
            new BlockPos(1056, 111, -12), new BlockPos(1056, 111, -12),
            4000000),

    YU_LIN_ADVANCED_APARTMENT_201(Te.s("玉林山水(201)", ChatFormatting.BLACK),
            "Floor 2",
            new BlockPos(1119, 87, -80), BlockPos.ZERO,
            new BlockPos(1118, 87, -81), new BlockPos(1118, 87, -81),
            25000000),
    YU_LIN_ADVANCED_APARTMENT_301(Te.s("玉林山水(301)", ChatFormatting.BLACK),
            "Floor 3",
            new BlockPos(1119, 93, -80), BlockPos.ZERO,
            new BlockPos(1118, 93, -81), new BlockPos(1118, 93, -81),
            27500000),
    YU_LIN_ADVANCED_APARTMENT_401(Te.s("玉林山水(401)", ChatFormatting.BLACK),
            "Floor 4",
            new BlockPos(1119, 99, -80), BlockPos.ZERO,
            new BlockPos(1118, 99, -81), new BlockPos(1118, 99, -81),
            30000000),

    YU_LIN_HOUSE_101(Te.s("玉林阁(101)", ChatFormatting.BLACK),
            "2 + 3",
            new BlockPos(1074, 81, -69), new BlockPos(1077, 81, -69),
            new BlockPos(1075, 81, -70), new BlockPos(1076, 81, -70),
            80000000),
    YU_LIN_HOUSE_201(Te.s("玉林阁(201)", ChatFormatting.BLACK),
            "0 + 2",
            new BlockPos(1079, 81, -54), new BlockPos(1083, 81, -54),
            new BlockPos(1081, 81, -51), new BlockPos(1080, 81, -51),
            70000000),
    YU_LIN_HOUSE_301(Te.s("玉林阁(301)", ChatFormatting.BLACK),
            "0 + 2",
            new BlockPos(1095, 81, -54), new BlockPos(1099, 81, -54),
            new BlockPos(1097, 81, -51), new BlockPos(1096, 81, -51),
            60000000),
    YU_LIN_HOUSE_401(Te.s("玉林阁(401)", ChatFormatting.BLACK),
            "0 + 2",
            new BlockPos(1109, 81, -54), new BlockPos(1113, 81, -54),
            new BlockPos(1112, 81, -51), new BlockPos(1111, 81, -51),
            50000000),

    TT(Te.s("TT"),
            "Floor T",
            BlockPos.ZERO, BlockPos.ZERO,
            BlockPos.ZERO, BlockPos.ZERO,
            999000000);

    public final Component estateName;
    public final String floorInfo;
    public final BlockPos infoSignBlockPos;
    public final BlockPos editableSignBlockPos;
    public final BlockPos doorBlockPos1;
    public final BlockPos doorBlockPos2;
    public final double price;

    EstateInfo(Component estateName, String floorInfo,
               BlockPos infoSignBlockPos, BlockPos editableSignBlockPos,
               BlockPos doorBlockPos1, BlockPos doorBlockPos2,
               double price) {
        this.estateName = estateName;
        this.floorInfo = floorInfo;
        this.infoSignBlockPos = infoSignBlockPos;
        this.editableSignBlockPos = editableSignBlockPos;
        this.doorBlockPos1 = doorBlockPos1;
        this.doorBlockPos2 = doorBlockPos2;
        this.price = price;
    }
}

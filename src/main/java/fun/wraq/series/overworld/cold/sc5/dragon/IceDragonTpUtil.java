package fun.wraq.series.overworld.cold.sc5.dragon;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import fun.wraq.render.gui.villagerTrade.MyVillagerData;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class IceDragonTpUtil {
    public static final Vec3 DRAGON_TP_POS = new Vec3(2171, 64, -4181);

    public static final Vec3 PRACTICE_DRAGON_TP_POS = new Vec3(1058, 71, -2678);

    public static final Style style = CustomStyle.styleOfIce;

    public static void onInteractWithVillager(Player player) {
        if (player.getPersistentData().contains(SimulateIceDragonSpawnController.DRAGON_MEDAL_DATA_KEY)) {
            sendMSG(player, Te.s("看起来你已经准备好与", "极寒冰龙", CustomStyle.styleOfIce, "的战斗了呢."));
        } else {
            sendMSG(player, Te.s("想要挑战", "极寒冰龙", CustomStyle.styleOfIce, "?你最好先进行一下模拟训练."));
        }
        Compute.sendBlankLine(player, 3);
        player.sendSystemMessage(Te.s(" ".repeat(4),
                Te.c(Te.s("「模拟训练」", style),
                        "/vmd iceDragon simulate",
                        Te.s("在", "1阶寒冷区域", style, "与", "经前哨所驯化后的", CustomStyle.styleOfMoon,
                                "极寒冰龙", style, "进行模拟训练，感受真实的极寒冰龙强度.")),
                " ".repeat(4),
                Te.c(Te.s("「前往挑战」", style),
                        "/vmd iceDragon goto",
                        Te.s("前往挑战", "极寒冰龙", style)),
                " ".repeat(4)));
        Compute.sendBlankLine(player, 4);
        MySound.soundToNearPlayer(player, SoundEvents.VILLAGER_AMBIENT);
    }

    public static final String VILLAGER_NAME = "极寒冰龙征服科科长";

    public static void setVillagerData() {
        MyVillagerData.setMyVillagerData(VILLAGER_NAME,
                "iceDragonChallenge", style, VillagerType.SNOW, VillagerProfession.BUTCHER, List.of());
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("极寒前哨所", style), content);
    }
}

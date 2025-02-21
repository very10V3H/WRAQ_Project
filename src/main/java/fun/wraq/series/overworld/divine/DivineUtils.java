package fun.wraq.series.overworld.divine;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.mob.DivineSentrySpawnController;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.util.HashSet;
import java.util.Set;

public class DivineUtils {
    public static Style style = CustomStyle.DIVINE_STYLE;
    public static String currentDayElement = Element.life;
    public static final Vec3 ToDivineIslandBoatPos = new Vec3(1899.5, 70, 295.5);
    public static final Vec3 InDivineIslandBoatPos = new Vec3(2279.5, 67, 874.5);
    public static final Vec3 ToSunIslandBoatPos = new Vec3(2270.5, 70, 874.5);
    public static final Vec3 InSunIslandBoatPos = new Vec3(1899.5, 67, 304.5);
    public static void handleServerLevelEvent(TickEvent.LevelTickEvent event) {
        Level level = event.level;
        if (event.phase == TickEvent.Phase.END && level.dimension().equals(Level.OVERWORLD) && !level.isClientSide) {
            if (level.getDayTime() % 24000 == 1) {
                currentDayElement = Element.elementList.get((int) (level.getDayTime() / 24000 % 7));
                Compute.formatBroad(level, Te.s("圣光岛", style), Te.s("圣光岛", style, "的",
                        "共鸣元素", CustomStyle.styleOfWorld,
                        "已变更为", Element.getElementDescription(currentDayElement)));
            }
        }
    }

    public static void handlePlayerTick(Player player) {
        if (player.level().dimension().equals(Level.OVERWORLD)) {
            if (player.position().distanceTo(ToDivineIslandBoatPos) < 2) {
                Compute.teleportPlayerToPos(player, InDivineIslandBoatPos);
            } else if (player.position().distanceTo(ToSunIslandBoatPos) < 2) {
                Compute.teleportPlayerToPos(player, InSunIslandBoatPos);
            }
            if (player.experienceLevel < 230 && player.getX() > 2000 && player.getX() < 2730
                    && player.getZ() > 170 && player.getZ() < 920) {
                Compute.decreasePlayerHealth(player, player.getMaxHealth() * 0.005,
                        Te.s("被", "圣光", style, "辐照飞升"));
                if (player.tickCount % 20 == 0) {
                    sendMSG(player, Te.s("未达到", Utils.getLevelDescription(230),
                            "无法承受", "圣光的辐照.", style));
                }
            }
        }
    }

    public static void handleMobTick(Mob mob) {
        Element.provideElement(mob, currentDayElement, 6);
    }

    public final static Set<String> MOB_NAME_SET = new HashSet<>() {{
        add(DivineSentrySpawnController.mobName);
    }};
    public static void onPlayerWithstandDamage(Mob mob, Player player) {
        if (MOB_NAME_SET.contains(MobSpawn.getMobOriginName(mob))) {
            SpecialEffectOnPlayer.addHealingReduction(player, "DivineMobHealingReduction", 40);
            SpecialEffectOnPlayer.slowdownEffectProvide(player, 40, 0.8);
        }
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("圣光岛", style), content);
    }
}

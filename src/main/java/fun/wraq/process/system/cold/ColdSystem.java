package fun.wraq.process.system.cold;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.render.hud.ColdData;
import fun.wraq.render.mobEffects.ModEffects;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.sakura.bunker.armor.BunkerArmor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class ColdSystem {
    public static List<Pair<Vec2, Vec2>> cold_2_zone = new ArrayList<>();
    public static List<Pair<Vec2, Vec2>> cold_3_zone = new ArrayList<>();
    public static List<Pair<Vec2, Vec2>> cold_4_zone = new ArrayList<>();
    public static List<Pair<Vec2, Vec2>> cold_5_zone = new ArrayList<>();

    public static boolean isIn(Player player, List<Pair<Vec2, Vec2>> zone) {
        return zone.stream().anyMatch(pair -> {
            return player.getX() > pair.getA().x && player.getZ() > pair.getA().y
                    && player.getX() < pair.getB().x && player.getZ() < pair.getB().y;
        });
    }

    public static int getPlayerColdLevel(Player player) {
        if (isIn(player, cold_5_zone)) {
            return 5;
        } else if (isIn(player, cold_4_zone)) {
            return 4;
        } else if (isIn(player, cold_3_zone)) {
            return 3;
        } else if (isIn(player, cold_2_zone)) {
            return 2;
        } else if ((player.level().getBiome(player.getOnPos()).get().getBaseTemperature() <= 0.5
                || player.isUnderWater())) {
            return 1;
        }
        return 0;
    }

    public static void handleTick(Player player) {
        if (!player.isCreative()) {
            if (player.tickCount % 10 == 0) {
                ColdData.updatePlayerColdNumStatus(player);
            }
            if (player.tickCount % 20 == 0) {
                int coldLevel = getPlayerColdLevel(player);
                if (coldLevel > 0) {
                    if (player.getEffect(ModEffects.WARM.get()) != null) {
                        if (coldLevel >= 2 && getPlayerHeatTier(player) < coldLevel - 1) {
                            ColdData.addPlayerColdValue(player, 1);
                        }
                    } else {
                        ColdData.addPlayerColdValue(player, 1);
                    }
                } else {
                    ColdData.addPlayerColdValue(player, -1);
                }
            }
            double currentColdValue = ColdData.getPlayerCurrentColdValue(player);
            if (currentColdValue >= 50) {
                player.setIsInPowderSnow(true);
                if (player.tickCount % 20 == 0) {
                    SpecialEffectOnPlayer.addHealingReduction(player, "coldEffect", Tick.s(2));
                    if (currentColdValue >= 75) {
                        Compute.decreasePlayerHealth(player,
                                player.getMaxHealth() * currentColdValue == 100 ? 0.2 : 0.1,
                                Te.s("因", "失温", CustomStyle.styleOfIce, "而死."));
                    }
                }
            }
        }
    }

    public static double getMovementSpeedAndDamageEffectRate(Player player) {
        double currentColdValue = ColdData.getPlayerCurrentColdValue(player);
        if (currentColdValue >= 50) {
            return -0.5;
        } else if (currentColdValue >= 25) {
            return -0.75;
        }
        return 0;
    }

    public static int getPlayerHeatTier(Player player) {
        int tier = 0;
        for (ItemStack armor : player.getArmorSlots()) {
            if (armor.getItem() instanceof BunkerArmor bunkerArmor) {
                tier += bunkerArmor.tier + 1;
            }
        }
        return tier;
    }
}

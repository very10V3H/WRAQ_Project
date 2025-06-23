package fun.wraq.process.system.cold;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.effect.SpecialEffectOnPlayer;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.hud.ColdData;
import fun.wraq.render.mobEffects.ModEffects;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.sakura.bunker.armor.BunkerArmor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColdSystem {

    public static List<Pair<Vec2, Vec2>> cold_0_zone = new ArrayList<>() {{
        add(new Pair<>(new Vec2(428, -1724), new Vec2(2840, 1436)));
    }};

    public static List<Pair<Vec2, Vec2>> cold_2_zone = new ArrayList<>() {{
        add(new Pair<>(new Vec2(1991, -3416), new Vec2(2358, -3126)));
    }};
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
        if (isIn(player, cold_0_zone)) {
            return 0;
        }
        if (isIn(player, cold_5_zone)) {
            return 5;
        } else if (isIn(player, cold_4_zone)) {
            return 4;
        } else if (isIn(player, cold_3_zone)) {
            return 3;
        } else if (isIn(player, cold_2_zone)) {
            return 2;
        } else if ((player.level().getBiome(player.getOnPos()).get().getBaseTemperature() <= 0.5)) {
            return 1;
        }
        return 0;
    }

    public static Map<String, Integer> playerLastColdLevelMap = new HashMap<>();

    public static void handleTick(Player player) {
        if (!player.isCreative()) {
            int coldLevel = getPlayerColdLevel(player);
            int heatTier = getPlayerHeatTier(player);
            player.setTicksFrozen((int) Math.min(139, 139 * ColdData.getPlayerCurrentColdValue(player) / 100));
            if (ColdData.getPlayerCurrentColdValue(player) == 100) {
                player.setTicksFrozen(200);
            }
            if (player.tickCount % 10 == 0) {
                ColdData.updatePlayerColdNumStatus(player);
            }
            if (player.tickCount % 20 == 0) {
                if (playerLastColdLevelMap.getOrDefault(Name.get(player), 0) < coldLevel) {
                    Compute.sendFormatMSG(player, Te.s("寒冷", CustomStyle.styleOfIce),
                            Te.s("你进入了", coldLevel + "阶", CustomStyle.styleOfIce, "寒冷地带."));
                    if (heatTier < coldLevel) {
                        Compute.sendFormatMSG(player, Te.s("寒冷", CustomStyle.styleOfIce),
                                Te.s("你目前无法抵御这一阶的严寒，将持续失去体温."));
                    }
                }
                playerLastColdLevelMap.put(Name.get(player), coldLevel);
                if (heatTier > 0) {
                    Compute.sendEffectLastTime(player, "item/bunker_curio", heatTier, true);
                } else {
                    Compute.removeEffectLastTime(player, "item/bunker_curio");
                }
                if (coldLevel > 0) {
                    Compute.sendDebuffTime(player, "hud/cold", 8888, coldLevel, true);
                    if (heatTier < coldLevel) {
                        if (player.isInWater()) {
                            ColdData.addPlayerColdValue(player, 5);
                        } else {
                            ColdData.addPlayerColdValue(player, 1);
                        }
                        if (player.getEffect(ModEffects.WARM.get()) == null
                                && InventoryOperation
                                .checkItemRemoveIfHas(player, List.of(new ItemStack(ModItems.MAGMA_SOUL.get())))) {
                                player.addEffect(new MobEffectInstance(ModEffects.WARM.get(), Tick.min(3)));
                        }
                    } else {
                        ColdData.addPlayerColdValue(player, -1);
                    }
                } else {
                    Compute.removeDebuffTime(player, "hud/cold");
                    ColdData.addPlayerColdValue(player, -1);
                }
            }
            double currentColdValue = ColdData.getPlayerCurrentColdValue(player);
            if (currentColdValue >= 50) {
                if (player.tickCount % 20 == 0) {
                    SpecialEffectOnPlayer.addHealingReduction(player, "coldEffect", Tick.s(2));
                    if (currentColdValue >= 75) {
                        Compute.decreasePlayerHealth(player,
                                player.getMaxHealth() * (currentColdValue == 100 ? 0.2 : 0.1),
                                Te.s("因", "失温", CustomStyle.styleOfIce, "而死."));
                    }
                }
            }
        }
    }

    public static double getMovementSpeedAndDamageEffectRate(Player player) {
        double currentColdValue = ColdData.getPlayerCurrentColdValue(player);
        if (currentColdValue >= 50) {
            return -0.75;
        } else if (currentColdValue >= 25) {
            return -0.5;
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
        if (player.getEffect(ModEffects.WARM.get()) != null) {
            ++tier;
        }
        return tier;
    }
}

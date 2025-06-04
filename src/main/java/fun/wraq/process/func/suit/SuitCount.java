package fun.wraq.process.func.suit;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.series.end.eventController.LightningIslandRecall.IntensifiedLightningArmor;
import fun.wraq.series.overworld.chapter1.forest.ForestCrest;
import fun.wraq.series.overworld.chapter1.mana.ManaNote;
import fun.wraq.series.overworld.chapter1.mine.MineCrest;
import fun.wraq.series.overworld.chapter1.plain.PlainCrest;
import fun.wraq.series.overworld.chapter1.snow.SnowCrest;
import fun.wraq.series.overworld.chapter1.volcano.VolcanoCrest;
import fun.wraq.series.overworld.chapter1.waterSystem.crest.LakeCrest;
import fun.wraq.series.overworld.chapter2.evoker.ManaCrest;
import fun.wraq.series.overworld.chapter2.lightningIsland.LightningArmor;
import fun.wraq.series.overworld.chapter2.sky.Crest.SkyCrest;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class SuitCount {
    public static int getStarSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.STAR_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.STAR_LEGGINGS.get())) count++;
        return count;
    }

    public static int getMoonSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MOON_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.MOON_LEGGINGS.get())) count++;
        return count;
    }

    public static int getPlainSuitCount(Player player) {
        int count = getPlainSuitCountWithoutCrest(player);
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof PlainCrest)) {
            count ++;
        }
        return count;
    }

    public static int getPlainSuitCountWithoutCrest(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.PLAIN_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.PLAIN_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PLAIN_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.PLAIN_BOOTS.get())) count++;
        return count;
    }

    public static int getForestSuitCount(Player player) {
        int count = getForestSuitCountWithoutCrest(player);
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof ForestCrest)) {
            count ++;
        }
        return count;
    }

    public static int getForestSuitCountWithoutCrest(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.FOREST_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.FOREST_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.FOREST_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.FOREST_BOOTS.get())) count++;
        return count;
    }

    public static int getLakeSuitCount(Player player) {
        int count = getLakeSuitCountWithoutCrest(player);
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof LakeCrest)) {
            count ++;
        }
        return count;
    }

    public static int getLakeSuitCountWithoutCrest(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LAKE_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LAKE_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LAKE_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LAKE_BOOTS.get())) count++;
        return count;
    }

    public static int getVolcanoSuitCount(Player player) {
        int count = getVolcanoCountWithoutCrest(player);
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof VolcanoCrest)) {
            count ++;
        }
        return count;
    }

    public static int getVolcanoCountWithoutCrest(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.VOLCANO_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.VOLCANO_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.VOLCANO_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.VOLCANO_BOOTS.get())) count++;
        return count;
    }

    public static int getLifeManaSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LIFE_MANA_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LIFE_MANA_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LIFE_MANA_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LIFE_MANA_BOOTS.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof ManaCrest)) {
            count ++;
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ManaNote) count++;
        return count;
    }

    public static int getLifeManaESuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LIFE_MANA_HELMET_E.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LIFE_MANA_CHEST_E.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LIFE_MANA_LEGGINGS_E.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LIFE_MANA_BOOTS_E.get())) count++;
        return count;
    }

    public static int getObsiManaSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.OBSI_MANA_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.OBSI_MANA_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.OBSI_MANA_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.OBIS_MANA_BOOTS.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof ManaCrest)) {
            count ++;
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ManaNote) count++;
        return count;
    }

    public static int getObsiManaESuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.OBSI_MANA_HELMET_E.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.OBSI_MANA_CHEST_E.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.OBSI_MANA_LEGGINGS_E.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.OBSI_MANA_BOOTS_E.get())) count++;
        return count;
    }

    public static int getMineSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MINE_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.MINE_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.MINE_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.MINE_BOOTS.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof MineCrest)) {
            count ++;
        }
        return count;
    }

    public static int getSnowSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SNOW_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SNOW_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SNOW_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SNOW_BOOTS.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof SnowCrest)) {
            count ++;
        }
        return count;
    }

    public static int getSkySuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SKY_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SKY_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SKY_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SKY_BOOTS.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof SkyCrest)) {
            count ++;
        }
        return Math.min(count, 4);
    }

    public static int getNetherSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NETHER_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.NETHER_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.NETHER_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.NETHER_BOOTS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.NETHER_POWER.get())) count++;
        return Math.min(count, 4);
    }

    public static int getLeatherSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LEATHER_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LEATHER_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LEATHER_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LEATHER_BOOTS.get())) count++;
        return count;
    }

    public static int getPurpleIronSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.PURPLE_IRON_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.PURPLE_IRON_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PURPLE_IRON_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.PURPLE_IRON_BOOTS.get())) count++;
        return count;
    }

    public static int getIceSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ICE_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ICE_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ICE_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ICE_BOOTS.get())) count++;
        return count;
    }

    public static int getNetherManaSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NETHER_MANA_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.NETHER_MANA_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.NETHER_MANA_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.NETHER_MANA_BOOTS.get())) count++;
        return count;
    }

    public static int getEarthManaSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.EARTH_MANA_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.EARTH_MANA_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.EARTH_MANA_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.EARTH_MANA_BOOTS.get())) count++;
        return count;
    }

    public static int getBloodManaSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.BLOOD_MANA_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.BLOOD_MANA_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.BLOOD_MANA_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.BLOOD_MANA_BOOTS.get())) count++;
        return count;
    }

    public static int getCastleAttackSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CASTLE_ATTACK_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CASTLE_ATTACK_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CASTLE_ATTACK_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CASTLE_ATTACK_BOOTS.get())) count++;
        return count;
    }

    public static int getCastleSwiftSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CASTLE_SWIFT_HELMET.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CASTLE_SWIFT_CHEST.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CASTLE_SWIFT_LEGGINGS.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CASTLE_SWIFT_BOOTS.get())) count++;
        return count;
    }

    public static int getCastleManaSuitCount(Player player) {
        int Count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CASTLE_MANA_HELMET.get())) Count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CASTLE_MANA_CHEST.get())) Count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CASTLE_MANA_LEGGINGS.get())) Count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CASTLE_MANA_BOOTS.get())) Count++;
        return Count;
    }

    public static int getLightningArmorCount(Player player) {
        int count = 0;
        count += (int) InventoryOperation.getArmors(player).stream()
                .filter(itemStack -> itemStack.getItem() instanceof LightningArmor)
                .count();
        count += (int) InventoryOperation.getArmors(player).stream()
                .filter(itemStack -> itemStack.getItem() instanceof IntensifiedLightningArmor)
                .count() * 2;
        return count;
    }
}

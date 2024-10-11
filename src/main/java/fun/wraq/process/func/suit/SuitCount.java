package fun.wraq.process.func.suit;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.series.end.eventController.LightningIslandRecall.IntensifiedLightningArmor;
import fun.wraq.series.overworld.chapter1.ManaBook.ManaNote;
import fun.wraq.series.overworld.chapter1.Mine.Crest.MineCrest;
import fun.wraq.series.overworld.chapter1.Snow.Crest.SnowCrest;
import fun.wraq.series.overworld.chapter1.forest.crest.ForestCrest;
import fun.wraq.series.overworld.chapter1.plain.crest.PlainCrest;
import fun.wraq.series.overworld.chapter1.volcano.crest.VolcanoCrest;
import fun.wraq.series.overworld.chapter1.waterSystem.crest.LakeCrest;
import fun.wraq.series.overworld.chapter2.evoker.Crest.ManaCrest;
import fun.wraq.series.overworld.chapter2.lightningIsland.Armor.LightningArmor;
import fun.wraq.series.overworld.chapter2.sky.Crest.SkyCrest;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class SuitCount {
    public static int getStarSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.StarHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.StarLeggings.get())) count++;
        return count;
    }

    public static int getMoonSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MoonHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.MoonLeggings.get())) count++;
        return count;
    }

    public static int getPlainSuitCount(Player player) {
        int count = getPlainSuitCountWithoutCrest(player);
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.PlainBracelet.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof PlainCrest)) {
            count ++;
        }
        return count;
    }

    public static int getPlainSuitCountWithoutCrest(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.PlainArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.PlainArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PlainArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.PlainArmorBoots.get())) count++;
        return count;
    }

    public static int getForestSuitCount(Player player) {
        int count = getForestSuitCountWithoutCrest(player);
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.ForestBracelet.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof ForestCrest)) {
            count ++;
        }
        return count;
    }

    public static int getForestSuitCountWithoutCrest(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ForestArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ForestArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ForestArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ForestArmorBoots.get())) count++;
        return count;
    }

    public static int getLakeSuitCount(Player player) {
        int count = getLakeSuitCountWithoutCrest(player);
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.LakeBracelet.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof LakeCrest)) {
            count ++;
        }
        return count;
    }

    public static int getLakeSuitCountWithoutCrest(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LakeArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LakeArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LakeArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LakeArmorBoots.get())) count++;
        return count;
    }

    public static int getVolcanoSuitCount(Player player) {
        int count = getVolcanoCountWithoutCrest(player);
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.VolcanoBracelet.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof VolcanoCrest)) {
            count ++;
        }
        return count;
    }

    public static int getVolcanoCountWithoutCrest(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.VolcanoArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.VolcanoArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.VolcanoArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.VolcanoArmorBoots.get())) count++;
        return count;
    }

    public static int getLifeManaSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LifeManaArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LifeManaArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LifeManaArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LifeManaArmorBoots.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof ManaCrest)) {
            count ++;
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ManaNote) count++;
        return count;
    }

    public static int getLifeManaESuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LifeManaArmorHelmetE.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LifeManaArmorChestE.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LifeManaArmorLeggingsE.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LifeManaArmorBootsE.get())) count++;
        return count;
    }

    public static int getObsiManaSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ObsiManaArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ObsiManaArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ObsiManaArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ObsiManaArmorBoots.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof ManaCrest)) {
            count ++;
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ManaNote) count++;
        return count;
    }

    public static int getObsiManaESuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ObsiManaArmorHelmetE.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.ObsiManaArmorChestE.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.ObsiManaArmorLeggingsE.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.ObsiManaArmorBootsE.get())) count++;
        return count;
    }

    public static int getMineSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MineArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.MineArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.MineArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.MineArmorBoots.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.MineBracelet.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof MineCrest)) {
            count ++;
        }
        return count;
    }

    public static int getSnowSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SnowArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SnowArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SnowArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SnowArmorBoots.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.SnowBracelet.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof SnowCrest)) {
            count ++;
        }
        return count;
    }

    public static int getSkySuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SkyArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SkyArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SkyArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SkyArmorBoots.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.SkyBracelet.get())) count++;
        if (Compute.CuriosAttribute.getDistinctCuriosList(player).stream()
                .anyMatch(stack -> stack.getItem() instanceof SkyCrest)) {
            count ++;
        }
        return Math.min(count, 4);
    }

    public static int getNetherSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NetherArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.NetherArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.NetherArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.NetherArmorBoots.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.OFFHAND).is(ModItems.NetherPower.get())) count++;
        return Math.min(count, 4);
    }

    public static int getLeatherSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.LeatherArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.LeatherArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.LeatherArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.LeatherArmorBoots.get())) count++;
        return count;
    }

    public static int getPurpleIronSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.PurpleIronArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.PurpleIronArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.PurpleIronArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.PurpleIronArmorBoots.get())) count++;
        return count;
    }

    public static int getIceSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.IceArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.IceArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.IceArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.IceArmorBoots.get())) count++;
        return count;
    }

    public static int getNetherManaSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.NetherManaArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.NetherManaArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.NetherManaArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.NetherManaArmorBoots.get())) count++;
        return count;
    }

    public static int getSpringAttackSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SpringAttackArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SpringAttackArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SpringAttackArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SpringAttackArmorBoots.get())) count++;
        return count;
    }

    public static int getSpringSwiftSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SpringSwiftArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SpringSwiftArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SpringSwiftArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SpringSwiftArmorBoots.get())) count++;
        return count;
    }

    public static int getSpringManaSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.SpringManaArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.SpringManaArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.SpringManaArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SpringManaArmorBoots.get())) count++;
        return count;
    }

    public static int getEarthManaSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.EarthManaArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.EarthManaArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.EarthManaArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.EarthManaArmorBoots.get())) count++;
        return count;
    }

    public static int getBloodManaSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.BloodManaArmorHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.BloodManaArmorChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.BloodManaArmorLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.BloodManaArmorBoots.get())) count++;
        return count;
    }

    public static int getCastleAttackSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CastleAttackHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CastleAttackChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CastleAttackLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CastleAttackBoots.get())) count++;
        return count;
    }

    public static int getCastleSwiftSuitCount(Player player) {
        int count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CastleSwiftHelmet.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CastleSwiftChest.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CastleSwiftLeggings.get())) count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CastleSwiftBoots.get())) count++;
        return count;
    }

    public static int getCastleManaSuitCount(Player player) {
        int Count = 0;
        if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.CastleManaHelmet.get())) Count++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CastleManaChest.get())) Count++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.CastleManaLeggings.get())) Count++;
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.CastleManaBoots.get())) Count++;
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

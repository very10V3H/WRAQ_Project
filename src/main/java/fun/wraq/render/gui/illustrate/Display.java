package fun.wraq.render.gui.illustrate;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.potion.NewPotion;
import fun.wraq.process.system.potion.NewThrowablePotion;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class Display {
    public static String DisplayString = "Display";

    public static List<Item> gemList = new ArrayList<>();

    public static List<Item> BrewingList = new ArrayList<>();

    public static List<Item> getBrewingList() {
        if (BrewingList.isEmpty()) setBrewingList();
        return BrewingList;
    }

    public static void setBrewingList() {
        Item[] items = {
                ModItems.WaterBottle.get(),
                ModItems.Purifier.get(),
                ModItems.PurifiedWater.get(),
                ModItems.BrewingNote.get(),
                ModItems.PlainSolidifiedSoul.get(),
                ModItems.ForestSolidifiedSoul.get(),
                ModItems.LakeSolidifiedSoul.get(),
                ModItems.VolcanoSolidifiedSoul.get(),
                ModItems.SnowSolidifiedSoul.get(),
                ModItems.SkySolidifiedSoul.get(),
                ModItems.EvokerSolidifiedSoul.get(),
                ModItems.NetherSolidifiedSoul.get(),
                ModItems.Solidifier.get(),
                ModItems.Stabilizer.get(),
                ModItems.Concentrater.get()
        };
        BrewingList.addAll(List.of(items));
        for (RegistryObject<Item> registryObject : ModItems.ITEMS.getEntries()) {
            Item item = registryObject.get();
            if (item instanceof NewPotion || item instanceof NewThrowablePotion) BrewingList.add(item);
        }
    }

/*    public static List<Item> allItemList = new ArrayList<>();

    public static void setAllItemList() {
        if (BrewingList.isEmpty()) setBrewingList();
        for (RegistryObject<Item> registryObject : ModItems.ITEMS.getEntries()) {
            Item item = registryObject.get();
            if (!Utils.weaponList.contains(item) && !Utils.armorList.contains(item)
                    && !Utils.curiosList.contains(item) && !Utils.customizedList.contains(item)
                    && !BrewingList.contains(item)) {
                allItemList.add(item);
            }
        }
    }*/

    public static List<Item> materialList = new ArrayList<>();

    public static void setMaterialList() {
        Item[] items = {
                ModItems.PlainSoul.get(), ModItems.PlainRune.get(),
                ModItems.ForestSoul.get(), ModItems.ForestRune.get(),
                ModItems.LakeSoul.get(), ModItems.LakeRune.get(),
                ModItems.VolcanoSoul.get(), ModItems.VolcanoRune.get(),
                ModItems.MineSoul.get(), ModItems.MineRune.get(),
                ModItems.FieldSoul.get(), ModItems.FieldRune.get(),
                ModItems.SnowSoul.get(), ModItems.SnowRune.get(),
                ModItems.SkySoul.get(), ModItems.SkyRune.get(),
                ModItems.EvokerSoul.get(), ModItems.ManaBucket.get(),
                ModItems.EvokerRune.get(), ModItems.ManaBalance_Empty.get(), ModItems.ManaBalance_filled.get(),
                ModItems.SlimeBall.get(), ModItems.BigSlimeBall.get(),
                ModItems.wolfLeather.get(),
                ModItems.plainmana.get(), ModItems.forestmana.get(), ModItems.lakemana.get(), ModItems.volcanomana.get(),
                ModItems.OreRune.get(), ModItems.NaturalCore.get(),
                ModItems.toNether.get(), ModItems.Ruby.get(),
                ModItems.witherSkeletonSoul.get(), ModItems.PigLinSoul.get(),
                ModItems.netherSkeletonSoul.get(), ModItems.magmaSoul.get(),
                ModItems.NetherSoul.get(), ModItems.NetherRune.get(),
                ModItems.NetherSwordModel.get(), ModItems.witherSkeletonSoul.get(),
                ModItems.QuartzSoul.get(), ModItems.QuartzRune.get(),
                ModItems.PowerModel.get(), ModItems.NetherSabreModel.get(),
                ModItems.NetherQuartz.get(), ModItems.WitherRune.get(),
                ModItems.PiglinRune.get(), ModItems.NetherSkeletonRune.get(),
                ModItems.MagmaRune.get(),
                ModItems.SeaSoul.get(), ModItems.SeaRune.get(),
                ModItems.LightningSoul.get(), ModItems.LightningRune.get(),
                ModItems.huskSoul.get(), ModItems.huskRune.get(),
                ModItems.SunPower.get(),
                ModItems.KazeSoul.get(), ModItems.KazeRune.get(),
                ModItems.LakeCore.get(),
                ModItems.SpiderSoul.get(), ModItems.SpiderRune.get(),
                ModItems.PurpleIronPiece.get(),
                ModItems.VolcanoCore.get(),
                ModItems.toEnd.get(), ModItems.RecallPiece.get(),
                ModItems.BlackForestRecallBook.get(), ModItems.ForestRecallBook.get(),
                ModItems.KazeRecallBook.get(), ModItems.NetherRecallBook1.get(),
                ModItems.SeaRecallBook.get(), ModItems.SnowRecallBook.get(),
                ModItems.VolcanoRecallBook.get(),
                ModItems.BlackForestRecallSoul.get(), ModItems.IntensifiedForestSoul.get(),
                ModItems.KazeRecallSoul.get(), ModItems.NetherRecallSoul.get(),
                ModItems.SeaRecallSoul.get(), ModItems.SnowRecallSoul.get(),
                ModItems.VolcanoRecallSoul.get(),
                ModItems.IntensifiedBlackForestSoul.get(), ModItems.IntensifiedForestSoul.get(),
                ModItems.IntensifiedKazeSoul.get(), ModItems.IntensifiedNetherSoul.get(),
                ModItems.IntensifiedSeaSoul.get(), ModItems.IntensifiedSnowSoul.get(),
                ModItems.IntensifiedVolcanoSoul.get(),
                ModItems.SakuraPetal.get(), ModItems.ShipPiece.get(),
                ModItems.EarthManaSoul.get(), ModItems.EarthManaRune.get(),
                ModItems.BloodManaSoul.get(), ModItems.BloodManaRune.get(),

                ModItems.PlainBossSoul.get(), ModItems.PlainCompleteGem.get(),
                ModItems.PurpleIronBud1.get(), ModItems.PurpleIronBud2.get(), ModItems.PurpleIronBud3.get(),
                ModItems.MoonSoul.get(), ModItems.MoonCompleteGem.get(),
                ModItems.Boss2Piece.get(),
                ModItems.DevilBlood.get(), ModItems.DevilAttackSoul.get(),
                ModItems.DevilSwiftSoul.get(), ModItems.DevilManaSoul.get(),
                ModItems.TabooPiece.get(), ModItems.PurpleIronConstraintStone.get(), ModItems.ConstrainTaboo.get(),
                ModItems.IntensifiedDevilBlood.get(),
                ModItems.IceSoul.get(), ModItems.IceCompleteGem.get(), ModItems.IceHeart.get(),

                ModItems.ShulkerSoul.get(), ModItems.EnderMiteSoul.get(),
                ModItems.EndCrystal.get(),

                ModItems.BeaconSoul.get(), ModItems.BeaconRune.get(),
                ModItems.BlazeSoul.get(), ModItems.BlazeRune.get(),
                ModItems.TreeSoul.get(), ModItems.TreeRune.get(),

                ModItems.StarSoul.get(), ModItems.StarRune.get(),

                ModItems.LifeElementPiece0.get(), ModItems.LifeElementPiece1.get(), ModItems.LifeElementPiece2.get(),
                ModItems.WaterElementPiece0.get(), ModItems.WaterElementPiece1.get(), ModItems.WaterElementPiece2.get(),
                ModItems.FireElementPiece0.get(), ModItems.FireElementPiece1.get(), ModItems.FireElementPiece2.get(),
                ModItems.StoneElementPiece0.get(), ModItems.StoneElementPiece1.get(), ModItems.StoneElementPiece2.get(),
                ModItems.IceElementPiece0.get(), ModItems.IceElementPiece1.get(), ModItems.IceElementPiece2.get(),
                ModItems.LightningElementPiece0.get(), ModItems.LightningElementPiece1.get(), ModItems.LightningElementPiece2.get(),
                ModItems.WindElementPiece0.get(), ModItems.WindElementPiece1.get(), ModItems.WindElementPiece2.get()
        };
        materialList.addAll(Arrays.asList(items));
    }

}

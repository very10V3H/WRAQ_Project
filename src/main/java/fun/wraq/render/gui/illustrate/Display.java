package fun.wraq.render.gui.illustrate;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.potion.NewPotion;
import fun.wraq.process.system.potion.NewThrowablePotion;
import fun.wraq.process.system.profession.pet.allay.item.AllayItems;
import fun.wraq.process.system.profession.smith.SmithItems;
import fun.wraq.series.instance.blade.BladeItems;
import fun.wraq.series.instance.mixture.MixtureItems;
import fun.wraq.series.instance.quiver.QuiverItems;
import fun.wraq.series.instance.series.mushroom.MushroomItems;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.overworld.sun.SunIslandItems;
import fun.wraq.series.specialevents.SpecialEventItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class Display {

    public static List<Item> swordList = new ArrayList<>();
    public static List<Item> bowList = new ArrayList<>();
    public static List<Item> sceptreList = new ArrayList<>();
    public static List<Item> powerList = new ArrayList<>();
    public static List<Item> offHandList = new ArrayList<>();
    public static List<Item> passiveEquipList = new ArrayList<>();
    public static List<Item> pickAxeList = new ArrayList<>();
    public static List<Item> helmetList = new ArrayList<>();
    public static List<Item> chestList = new ArrayList<>();
    public static List<Item> leggingsList = new ArrayList<>();
    public static List<Item> bootsList = new ArrayList<>();
    public static List<Item> gemList = new ArrayList<>();
    public static List<Item> BrewingList = new ArrayList<>();
    public static List<Item> runeList = new ArrayList<>();

    public static List<Item> materialList = new ArrayList<>();
    public static List<Item> souvenirsList = new ArrayList<>();

    public static List<Item> newItemList = new ArrayList<>();

    public static List<Item> getSouvenirsList() {
        if (souvenirsList.isEmpty()) {
            souvenirsList.addAll(List.of(
                    ModItems.SpringScale0.get(),
                    ModItems.LabourDayIronPickaxe.get(),
                    ModItems.QingMingGem.get(),
                    SpecialEventItems.SUMMER_CURIOS5.get(),
                    SpecialEventItems.MOON_FEATHER_0.get(),
                    SpecialEventItems.TRAIN_SOUVENIRS.get(),
                    SpecialEventItems.SOUVENIRS_2024.get()
            ));
        }
        return souvenirsList;
    }

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

    public static List<Item> getNewItemList() {
        if (newItemList.isEmpty()) {
            Item[] items = {
                    ModItems.MINE_MANA_NOTE.get(),
                    SunIslandItems.BROKEN_BLADE_0.get(),
                    SunIslandItems.FRAME_ARROW_0.get(),
                    WardenItems.DARK_MOON_BOOK.get()
            };
            newItemList.addAll(List.of(items));
            MushroomItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(item -> newItemList.add(item));
            AllayItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(item -> newItemList.add(item));
            SmithItems.ITEMS.getEntries()
                    .stream()
                    .map(entry -> entry.get().asItem())
                    .forEach(item -> newItemList.add(item));
        }
        return newItemList;
    }
}

package fun.wraq.render.gui.illustrate;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.potion.NewPotion;
import fun.wraq.process.system.potion.NewThrowablePotion;
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

    public static List<Item> helmetList = new ArrayList<>();
    public static List<Item> chestList = new ArrayList<>();
    public static List<Item> leggingsList = new ArrayList<>();
    public static List<Item> bootsList = new ArrayList<>();

    public static List<Item> gemList = new ArrayList<>();

    public static List<Item> BrewingList = new ArrayList<>();

    public static List<Item> runeList = new ArrayList<>();

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

    public static List<Item> materialList = new ArrayList<>();
}

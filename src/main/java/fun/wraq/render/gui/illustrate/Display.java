package fun.wraq.render.gui.illustrate;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.potion.NewPotion;
import fun.wraq.process.system.potion.NewThrowablePotion;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.overworld.mt.ManaTowerItems;
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
                    SpecialEventItems.SCALE_0.get(),
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
                ModItems.WATER_BOTTLE.get(),
                ModItems.PURIFIER.get(),
                ModItems.PURIFIED_WATER.get(),
                ModItems.BREWING_NOTE.get(),
                ModItems.PLAIN_SOLIDIFIED_SOUL.get(),
                ModItems.FOREST_SOLIDIFIED_SOUL.get(),
                ModItems.LAKE_SOLIDIFIED_SOUL.get(),
                ModItems.VOLCANO_SOLIDIFIED_SOUL.get(),
                ModItems.SNOW_SOLIDIFIED_SOUL.get(),
                ModItems.SKY_SOLIDIFIED_SOUL.get(),
                ModItems.EVOKER_SOLIDIFIED_SOUL.get(),
                ModItems.NETHER_SOLIDIFIED_SOUL.get(),
                ModItems.SOLIDIFIER.get(),
                ModItems.STABILIZER.get(),
                ModItems.CONCENTRATER.get()
        };
        BrewingList.addAll(List.of(items));
        for (RegistryObject<Item> registryObject : ModItems.ITEMS.getEntries()) {
            Item item = registryObject.get();
            if (item instanceof NewPotion || item instanceof NewThrowablePotion) BrewingList.add(item);
        }
    }

    public static List<Item> getNewItemList() {
        if (newItemList.isEmpty()) {
            newItemList.addAll(List.of(
                    SpecialEventItems.ZONG_LEAF.get(),
                    SpecialEventItems.SWEET_ZONG_ZI.get(),
                    SpecialEventItems.MEAT_ZONG_ZI.get(),
                    SpecialEventItems.GOLDEN_ZONG_ZI_CONDIMENT.get(),
                    SpecialEventItems.GOLDEN_ZONG_LEAF.get(),
                    SpecialEventItems.GOLDEN_ZONG_ZI.get(),
                    SpecialEventItems.DRAGON_BOAT_FES_PREFIX.get(),
                    SpecialEventItems.SEVEN_SHADE_PIECE_RICE.get(),
                    SpecialEventItems.SEVEN_SHADE_PIECE_GOLDEN_LEAF.get(),
                    SpecialEventItems.SEVEN_SHADE_PIECE_DRAGON_BOAT.get(),
                    SpecialEventItems.DRAGON_BOAT_FES_FORGE_PAPER.get(),
                    SpecialEventItems.DRAGON_DIAMOND.get(),
                    ManaTowerItems.KANUPUS_WING_F.get()
            ));
        }
        return newItemList;
    }
}

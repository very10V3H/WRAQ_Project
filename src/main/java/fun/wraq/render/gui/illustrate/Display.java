package fun.wraq.render.gui.illustrate;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.potion.NewPotion;
import fun.wraq.process.system.potion.NewThrowablePotion;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.series.crystal.CrystalItems;
import fun.wraq.series.dragon.SilverDragonItems;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.overworld.cold.SuperColdItems;
import fun.wraq.series.overworld.extraordinary.ExtraordinaryItems;
import fun.wraq.series.overworld.newarea.NewAreaItems;
import fun.wraq.series.overworld.sakura.bunker.BunkerItems;
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
                    SpurItems.SILVER_DRAGON_ASSASSIN_PICKAXE_A.get(),
                    SpurItems.INARI_BOW_A.get(),
                    SpurItems.DEEP_SEA_LEGENDARY_A.get(),
                    NewAreaItems.STONE_SPIDER_KNIFE_3.get(),
                    NewAreaItems.STONE_SPIDER_GEM_ATTACK_2.get(),
                    NewAreaItems.STONE_SPIDER_GEM_MANA_2.get(),
                    ExtraordinaryItems.KANUPUS_SWORD.get(),
                    ExtraordinaryItems.SHIRO_BOW.get(),
                    ExtraordinaryItems.NETHER_SCEPTRE_EX.get(),
                    SilverDragonItems.SILVER_DRAGON_BLOOD_SWORD.get(),
                    SilverDragonItems.SILVER_DRAGON_BLOOD_BOW.get(),
                    SilverDragonItems.SILVER_DRAGON_BLOOD_SCEPTRE.get(),
                    BunkerItems.BUNKER_HELMET_2.get(),
                    BunkerItems.BUNKER_CHEST_2.get(),
                    BunkerItems.BUNKER_LEGGINGS_2.get(),
                    BunkerItems.BUNKER_BOOTS_2.get(),
                    SuperColdItems.FLOWER.get()
            ));
            for (RegistryObject<Item> entry : CrystalItems.ITEMS.getEntries()) {
                newItemList.add(entry.get());
            }
        }
        return newItemList;
    }
}

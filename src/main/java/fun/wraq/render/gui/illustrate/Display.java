package fun.wraq.render.gui.illustrate;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.potion.NewPotion;
import fun.wraq.process.system.potion.NewThrowablePotion;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.holy.ice.IceHolyItems;
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
                    ModItems.LabourDayIronPickaxe.get(),
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
            newItemList.addAll(List.of(
                    SpecialEventItems.QING_TUAN.get(),
                    SpecialEventItems.QING_MING_COMMON_RING.get(),
                    SpecialEventItems.QING_MING_ATTACK_RING.get(),
                    SpecialEventItems.QING_MING_DEFENCE_RING.get(),
                    SpecialEventItems.QING_MING_REBORN_CHEST.get(),
                    SpecialEventItems.QING_MING_QING_TUAN_CHEST.get(),
                    SpecialEventItems.QING_MING_PREFIX_PAPER_1.get(),
                    SpecialEventItems.QING_MING_PREFIX_PAPER_2.get(),
                    SpecialEventItems.QING_MING_FORGE_PAPER.get()
            ));
            for (RegistryObject<Item> registryObject : IceHolyItems.ITEMS.getEntries()) {
                newItemList.add(registryObject.get());
            }
        }
        return newItemList;
    }
}

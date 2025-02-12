package fun.wraq.render.gui.illustrate;

import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.potion.NewPotion;
import fun.wraq.process.system.potion.NewThrowablePotion;
import fun.wraq.series.events.SpecialEventItems;
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
                    SpecialEventItems.FIRE_CRACKER.get(),
                    SpecialEventItems.MONEY.get(),
                    SpecialEventItems.RED_ENVELOPE.get(),
                    SpecialEventItems.SPRING_GOLD_COIN.get(),
                    SpecialEventItems.SPRING_GOLD_INGOT.get(),
                    SpecialEventItems.RING.get(),
                    SpecialEventItems.HAND.get(),
                    SpecialEventItems.NECKLACE.get(),
                    SpecialEventItems.BELT.get(),
                    SpecialEventItems.FIRE_WORK_GUN.get(),
                    SpecialEventItems.SCALE_PIECE.get(),
                    SpecialEventItems.BIG_RED_ENVELOPE.get(),
                    SpecialEventItems.SCALE_2025_0.get(),
                    ModItems.SKY_SWORD.get(),
                    ModItems.PLAIN_BOSS_SCEPTRE.get(),
                    ModItems.REVENANT_GOLDEN_HELMET.get(),
                    ModItems.ENHANCE_PURPLE_IRON_CHEST.get()
            };
            newItemList.addAll(List.of(items));
        }
        return newItemList;
    }
}

package fun.wraq.series.dragon;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SilverDragonItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_SWORD = ITEMS.register("silver_dragon_blood_sword",
            () -> new SilverDragonBloodSword(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 0));
    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_BOW = ITEMS.register("silver_dragon_blood_bow",
            () -> new SilverDragonBloodBow(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 0));
    public static final RegistryObject<Item> SILVER_DRAGON_BLOOD_SCEPTRE = ITEMS.register("silver_dragon_blood_sceptre",
            () -> new SilverDragonBloodSceptre(new Item.Properties().rarity(CustomStyle.SILVER_DRAGON_BOLD_RARITY), 0));
}

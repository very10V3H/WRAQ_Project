package fun.wraq.events.mob.loot;

import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.loot.RandomArmor;
import fun.wraq.events.mob.loot.RandomAttributeValue;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class C7LootItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> starSword = ITEMS.register("star_loot_sword", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.Moon1Bold), CustomStyle.styleOfMoon1,
                    ComponentUtils.getSuffixOfChapterVII(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.critDamage, 3, 4)), 200));

    public static final RegistryObject<Item> boneImpHelmet = ITEMS.register("bone_imp_helmet", () ->
            new RandomArmor(ItemMaterial.NetherAll, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.WitherBold), CustomStyle.styleOfWither,
                    ComponentUtils.getSuffixOfChapterVII(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.critRate, 0.5, 0.6)), 210));
}
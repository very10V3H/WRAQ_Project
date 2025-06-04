package fun.wraq.events.mob.loot;

import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class C7LootItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> STAR_LOOT_SWORD = ITEMS.register("star_loot_sword", () ->
            new RandomSword(new Item.Properties().rarity(CustomStyle.Moon1Bold), CustomStyle.styleOfMoon1,
                    ComponentUtils.getSuffixOfChapterStar(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.critDamage, 1, 1.5)), 200));

    public static final RegistryObject<Item> BONE_IMP_HELMET = ITEMS.register("bone_imp_helmet", () ->
            new RandomArmor(ModArmorMaterials.NetherAll, ArmorItem.Type.HELMET, new Item.Properties().rarity(CustomStyle.WitherBold), CustomStyle.styleOfWither,
                    ComponentUtils.getSuffixOfBoneImp(), List.of(new RandomAttributeValue(StringUtils.RandomAttribute.critRate, 0.5, 0.6)), 210));
}

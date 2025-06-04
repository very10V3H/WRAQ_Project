package fun.wraq.series.overworld.chapter7;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.overworld.chapter7.vd.VdBow;
import fun.wraq.series.overworld.chapter7.vd.VdSceptre;
import fun.wraq.series.overworld.chapter7.vd.VdSword;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class C7Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> BONE_IMP_SOUL = ITEMS.register("bone_imp_soul", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.WitherBold)));

    public static final RegistryObject<Item> BONE_IMP_KNIFE = ITEMS.register("bone_imp_knife", () ->
            new BoneImpKnife(new Item.Properties().rarity(CustomStyle.WitherBold), Component.literal("利刃短匕").withStyle(CustomStyle.styleOfBloodMana)));

    public static final RegistryObject<Item> VD_SOUL = ITEMS.register("vd_soul", () ->
            new WraqItem(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> VD_SWORD = ITEMS.register("vd_sword",
            () -> new VdSword(new Item.Properties().rarity(CustomStyle.WorldItalic)));

    public static final RegistryObject<Item> VD_BOW = ITEMS.register("vd_bow",
            () -> new VdBow(new Item.Properties().rarity(CustomStyle.WorldItalic)));

    public static final RegistryObject<Item> VD_SCEPTRE = ITEMS.register("vd_sceptre",
            () -> new VdSceptre(new Item.Properties().rarity(CustomStyle.WorldItalic)));
}

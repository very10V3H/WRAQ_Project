package com.very.wraq.series.overworld.chapter7;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.series.MaterialItem;
import com.very.wraq.series.overworld.chapter7.vd.VdBow;
import com.very.wraq.series.overworld.chapter7.vd.VdSceptre;
import com.very.wraq.series.overworld.chapter7.vd.VdSword;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SimpleFoiledItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class C7Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);

    public static final RegistryObject<Item> boneImpSoul = ITEMS.register("bone_imp_soul", () ->
            new MaterialItem(new Item.Properties().rarity(CustomStyle.WitherBold)));

    public static final RegistryObject<Item> boneImpKnife = ITEMS.register("bone_imp_knife", () ->
            new BoneImpKnife(new Item.Properties().rarity(CustomStyle.WitherBold), Component.literal("利刃短匕").withStyle(CustomStyle.styleOfBloodMana)));

    public static final RegistryObject<Item> vdSoul = ITEMS.register("vd_soul", () ->
            new MaterialItem(new Item.Properties().rarity(CustomStyle.WorldBold)));

    public static final RegistryObject<Item> vdSword = ITEMS.register("vd_sword",
            () -> new VdSword(new Item.Properties().rarity(CustomStyle.WorldItalic)));

    public static final RegistryObject<Item> vdBow = ITEMS.register("vd_bow",
            () -> new VdBow(new Item.Properties().rarity(CustomStyle.WorldItalic)));

    public static final RegistryObject<Item> vdSceptre = ITEMS.register("vd_sceptre",
            () -> new VdSceptre(new Item.Properties().rarity(CustomStyle.WorldItalic)));
}

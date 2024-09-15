package com.very.wraq.blocks.blocks.brew;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BrewingNote extends Item {

    public static String brewingLevel = "BrewingLevel";

    public static class ExpName {
        public static String volcano = "VolcanoBrewingExp";
        public static String forest = "ForestBrewingExp";
        public static String snow = "SnowBrewingExp";
        public static String evoker = "EvokerBrewingExp";
        public static String plain = "PlainBrewingExp";
        public static String lake = "LakeBrewingExp";
        public static String nether = "NetherBrewingExp";
        public static String sky = "SkyBrewingExp";
        public static String pear1 = "pear1BrewingExp";
        public static String pear2 = "pear2BrewingExp";
        public static String pear3 = "pear3BrewingExp";
        public static String pear4 = "pear4BrewingExp";
        public static String pear5 = "pear5BrewingExp";
        public static String pear6 = "pear6BrewingExp";
        public static String boss2Piece = "boss2PieceBrewingExp";
    }

    public static Map<Item, String> typeMap = new HashMap<>();

    public static void setTypeMap() {
        typeMap.put(ModItems.VolcanoSolidifiedSoul.get(), ExpName.volcano);
        typeMap.put(ModItems.ForestSolidifiedSoul.get(), ExpName.forest);
        typeMap.put(ModItems.SnowSolidifiedSoul.get(), ExpName.snow);
        typeMap.put(ModItems.EvokerSolidifiedSoul.get(), ExpName.evoker);
        typeMap.put(ModItems.PlainSolidifiedSoul.get(), ExpName.plain);
        typeMap.put(ModItems.LakeSolidifiedSoul.get(), ExpName.lake);
        typeMap.put(ModItems.NetherSolidifiedSoul.get(), ExpName.nether);
        typeMap.put(ModItems.SkySolidifiedSoul.get(), ExpName.sky);
        typeMap.put(ModItems.Pearl1.get(), ExpName.pear1);
        typeMap.put(ModItems.Pearl2.get(), ExpName.pear2);
        typeMap.put(ModItems.Pearl3.get(), ExpName.pear3);
        typeMap.put(ModItems.Pearl4.get(), ExpName.pear4);
        typeMap.put(ModItems.Pearl5.get(), ExpName.pear5);
        typeMap.put(ModItems.Pearl6.get(), ExpName.pear6);
        typeMap.put(ModItems.Boss2Piece.get(), ExpName.boss2Piece);
    }

    public static void addExp(ItemStack note, String type, int point) {
        CompoundTag tag = note.getOrCreateTagElement(Utils.MOD_ID);
        tag.putInt(type, tag.getInt(type) + point);
    }

    public static void addExp(ItemStack note, Item type, int point) {
        if (typeMap.isEmpty()) setTypeMap();
        CompoundTag tag = note.getOrCreateTagElement(Utils.MOD_ID);
        tag.putInt(typeMap.get(type), tag.getInt(typeMap.get(type)) + point);
    }

    public BrewingNote(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·材料-酿造").withStyle(CustomStyle.styleOfBrew));
        components.add(Component.literal("酿造师的酿造笔记。"));
        components.add(Component.literal("在笔记中记录了酿造师的酿造尝试。"));

        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            CompoundTag data = player.getPersistentData();
            data.putInt("BrewingLevel", Compute.BrewingLevel(player.getItemInHand(InteractionHand.MAIN_HAND)));
        }
        return super.use(level, player, interactionHand);
    }
}

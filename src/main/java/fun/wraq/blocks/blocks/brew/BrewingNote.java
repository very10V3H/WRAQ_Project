package fun.wraq.blocks.blocks.brew;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
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
        typeMap.put(ModItems.VOLCANO_SOLIDIFIED_SOUL.get(), ExpName.volcano);
        typeMap.put(ModItems.FOREST_SOLIDIFIED_SOUL.get(), ExpName.forest);
        typeMap.put(ModItems.SNOW_SOLIDIFIED_SOUL.get(), ExpName.snow);
        typeMap.put(ModItems.EVOKER_SOLIDIFIED_SOUL.get(), ExpName.evoker);
        typeMap.put(ModItems.PLAIN_SOLIDIFIED_SOUL.get(), ExpName.plain);
        typeMap.put(ModItems.LAKE_SOLIDIFIED_SOUL.get(), ExpName.lake);
        typeMap.put(ModItems.NETHER_SOLIDIFIED_SOUL.get(), ExpName.nether);
        typeMap.put(ModItems.SKY_SOLIDIFIED_SOUL.get(), ExpName.sky);
        typeMap.put(ModItems.PEARL_1.get(), ExpName.pear1);
        typeMap.put(ModItems.PEARL_2.get(), ExpName.pear2);
        typeMap.put(ModItems.PEARL_3.get(), ExpName.pear3);
        typeMap.put(ModItems.PEARL_4.get(), ExpName.pear4);
        typeMap.put(ModItems.PEARL_5.get(), ExpName.pear5);
        typeMap.put(ModItems.PEARL_6.get(), ExpName.pear6);
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

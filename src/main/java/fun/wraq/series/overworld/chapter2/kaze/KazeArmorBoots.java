package fun.wraq.series.overworld.chapter2.kaze;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class KazeArmorBoots extends WraqArmor implements ForgeItem {

    public KazeArmorBoots(ModArmorMaterials Material, Type Slots) {
        super(Material, Slots, new Properties().rarity(CustomStyle.KazeItalic));
        Utils.movementSpeedCommon.put(this, 0.18);
        Utils.attackDamage.put(this, 250d);
        Utils.manaDamage.put(this, 500d);
        Utils.swiftnessUp.put(this, 1d);
        Utils.levelRequire.put(this, 80);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfKaze;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.KazeRune.get(), 8),
                new ItemStack(Items.EMERALD, 16)
        );
    }
}

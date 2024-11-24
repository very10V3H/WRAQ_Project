package fun.wraq.series.overworld.chapter2.manaArmor.LifeMana;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LifeManaArmor extends WraqArmor {

    public LifeManaArmor(ModArmorMaterials material, Type type, Properties properties) {
        super(material, type, properties);
        if (type.equals(Type.HELMET)) Utils.healthRecover.put(this, 8d);
        if (type.equals(Type.CHESTPLATE)) Utils.defence.put(this, 10d);
        if (type.equals(Type.LEGGINGS)) Utils.maxHealth.put(this, 400d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.1);
        Utils.manaDamage.put(this, 50d);
        Utils.maxMana.put(this, 10d);
        Utils.manaPenetration0.put(this, 1.5d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLife;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        if (Screen.hasShiftDown()) LifeManaSuitDescription.SuitDescription(components);
        else {
            ComponentUtils.suitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static int getPlayerLifeManaArmorCount(Player player) {
        int count = 0;
        for (ItemStack armorSlot : player.getArmorSlots()) {
            count += armorSlot.getItem() instanceof LifeManaArmor ? 1 : 0;
        }
        return count;
    }
}

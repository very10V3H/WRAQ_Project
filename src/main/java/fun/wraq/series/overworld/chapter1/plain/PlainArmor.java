package fun.wraq.series.overworld.chapter1.plain;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.forge.IgnoreForgeQuality;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlainArmor extends WraqArmor implements IgnoreForgeQuality {

    public PlainArmor(ArmorMaterial armorMaterial, Type type) {
        super(armorMaterial, type, new Properties().rarity(CustomStyle.PlainItalic));
        switch (type) {
            case HELMET -> {
                Utils.maxHealth.put(this, 200d);
            }
            case CHESTPLATE -> {
                Utils.maxHealth.put(this, 320d);
            }
            case LEGGINGS -> {
                Utils.maxHealth.put(this, 280d);
            }
            case BOOTS -> {
                Utils.movementSpeedCommon.put(this, 0.08);
            }
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        if (type.equals(Type.BOOTS)) {
            Compute.DescriptionPassive(components,
                    Component.literal("平原之风").withStyle(CustomStyle.styleOfPlain));
            components.add(Component.literal(" 每1%已损失生命值提供").withStyle(ChatFormatting.WHITE).
                    append(ComponentUtils.AttributeDescription.movementSpeed("1%")));
        }
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    /**
     * 已损失生命值比例，即每损失 1% 生命值提供 1% 移动速度。
     */
    public static double getMissingHealthMovementSpeed(Player player) {
        if (!(player.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof PlainArmor)) {
            return 0;
        }
        return 1 - Math.min(1, player.getHealth() / player.getMaxHealth());
    }
}

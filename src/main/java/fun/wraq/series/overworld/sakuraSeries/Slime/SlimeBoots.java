package fun.wraq.series.overworld.sakuraSeries.Slime;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SlimeBoots extends WraqArmor implements ForgeItem {
    public SlimeBoots(ModArmorMaterials modArmorMaterials, Type Slots) {
        super(modArmorMaterials, Slots, new Properties().rarity(CustomStyle.LifeItalic));
        Utils.attackDamage.put(this, 45d);
        Utils.manaDamage.put(this, 125d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.1);
        Utils.levelRequire.put(this, 76);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfHealth;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("Q弹史莱姆！").withStyle(style));
        components.add(Component.literal(" 使穿戴者获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("跳跃提升！").withStyle(style)));
        Compute.DescriptionPassive(components, Component.literal("史莱姆缓冲！").withStyle(style));
        components.add(Component.literal(" 使你受到的伤害直接减少等同于你的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth("10%")).
                append(Component.literal("的数额！").withStyle(ChatFormatting.WHITE)));
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

    public static boolean IsOn(Player player) {
        return player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.SlimeBoots.get());
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.SlimeBall.get(), 384));
            add(new ItemStack(OreItems.WRAQ_ORE_1_ITEM.get(), 32));
        }};
    }
}

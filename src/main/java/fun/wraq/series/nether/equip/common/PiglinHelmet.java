package fun.wraq.series.nether.equip.common;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class PiglinHelmet extends WraqArmor implements ForgeItem, InCuriosOrEquipSlotAttributesModify {

    public final int tier;
    public PiglinHelmet(ItemMaterial Material, Type Slots, int tier) {
        super(Material, Slots, new Properties().rarity(CustomStyle.PiglinItalic));
        this.tier = tier;
        Utils.healthRecover.put(this, new double[]{20, 30, 40, 50}[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfGold;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("群攻").withStyle(style));
        components.add(Component.literal("基于附近怪物数量，为你提供").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.defence("怪物数量 * "
                        + String.format("%.0f", new double[]{3, 5, 8, 10}[tier]))));
        components.add(Component.literal("最大值：50").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("明明是金头盔，为什么不防猪灵呢？").withStyle(ChatFormatting.ITALIC).
                withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.STRIKETHROUGH));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.WITHER_RUNE.get(), 4));
            add(new ItemStack(ModItems.Ruby.get(), 128));
            add(new ItemStack(ModItems.NetherQuartz.get(), 32));
            add(new ItemStack(Items.RAW_GOLD, 6));
            add(new ItemStack(ModItems.goldCoin.get(), 64));
        }};
    }

    @Override
    public List<Attribute> getAttributes(Player player) {
        return List.of(new Attribute(Utils.defence, Math.min(50, new double[]{3, 5, 8, 10}[tier] * Compute.getNearEntity(player, Mob.class, 8).size())));
    }
}

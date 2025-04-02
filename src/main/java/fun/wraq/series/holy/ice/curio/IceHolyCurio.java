package fun.wraq.series.holy.ice.curio;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.holy.HolyCurio;
import fun.wraq.series.holy.ice.IceHolyItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

import java.util.List;

public abstract class IceHolyCurio extends WraqCurios implements Decomposable, HolyCurio {

    public static List<Rarity> rarities = List.of(Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC);
    private final int tier;
    public IceHolyCurio(int tier) {
        super(new Properties().rarity(rarities.get(tier)), 1);
        Utils.levelRequire.put(this, 230);
        Element.IceElementValue.put(this, new double[]{0.2, 0.4, 0.6, 1}[tier]);
        this.tier = tier;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public Component suffix() {
        return Te.s("冰之圣器", CustomStyle.styleOfIce, ChatFormatting.BOLD);
    }

    @Override
    public ItemStack getProduct() {
        List<Item> items = List.of(
                IceHolyItems.PIECE_1.get(),
                IceHolyItems.PIECE_2.get(),
                IceHolyItems.PIECE_3.get()
        );
        if (tier >= items.size()) {
            return new ItemStack(Items.AIR);
        }
        return new ItemStack(items.get(tier));
    }

    @Override
    public String getElement() {
        return Element.ice;
    }
}

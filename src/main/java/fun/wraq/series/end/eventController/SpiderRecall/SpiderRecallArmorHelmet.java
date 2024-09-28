package fun.wraq.series.end.eventController.SpiderRecall;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SpiderRecallArmorHelmet extends ArmorItem {

    public SpiderRecallArmorHelmet(ItemMaterial Materrial, Type Slots) {
        super(Materrial, Slots, new Properties().rarity(CustomStyle.MagmaItalic));

        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

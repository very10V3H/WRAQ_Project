package fun.wraq.blocks.blocks.brew;

import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PurifiedWater extends Item {

    public PurifiedWater(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("·材料-酿造").withStyle(CustomStyle.styleOfBrew));
        components.add(Component.literal("经过净化剂净化后的水。"));
        components.add(Component.literal("杂质已经被过滤，已经纯净到能够用于酿造。"));
        components.add(Component.literal("Items-Brewing").withStyle(CustomStyle.styleOfBrew));
        super.appendHoverText(itemStack, level, components, flag);
    }
}

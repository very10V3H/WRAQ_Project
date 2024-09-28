package fun.wraq.series.overworld.chapter2.codeMana;

import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LightningMana extends Item {
    public LightningMana(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·拓展魔符").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal(" "));
        components.add(Component.literal("魔符:雷击").withStyle(CustomStyle.styleOfLightingIsland));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-Code").withStyle(CustomStyle.styleOfMana).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

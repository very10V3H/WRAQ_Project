package fun.wraq.series.overworld.chapter2.lifeIsland;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LifeFruit extends Item {
    public LifeFruit(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {

        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    public static void TimeTagProvide(ItemStack itemStack) {

    }
}

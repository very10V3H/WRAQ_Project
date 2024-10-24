package fun.wraq.series.overworld.forging;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ForgingStone0 extends Item implements ForgingMaterial {
    public ForgingStone0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·材料").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" "));
        components.add(Component.literal("一颗较为").withStyle(ChatFormatting.GRAY).append(Component.literal("粗糙").withStyle(ChatFormatting.GREEN).append(Component.literal("的强化石。").withStyle(ChatFormatting.GRAY))));
        components.add(Component.literal("将力量注入物品，使物品获得更好性能。").withStyle(ChatFormatting.WHITE));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}

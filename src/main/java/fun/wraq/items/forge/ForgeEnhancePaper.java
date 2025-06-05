package fun.wraq.items.forge;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ForgeEnhancePaper extends Item {

    private int num = 0;

    public ForgeEnhancePaper(Properties p_41383_, int num) {
        super(p_41383_);
        this.num = num;
    }

    private final String[] rate = {
            "25%", "50%", "100%", "150%"
    };
    private final ChatFormatting[] chatFormattings = {
            ChatFormatting.GREEN,
            ChatFormatting.AQUA,
            ChatFormatting.LIGHT_PURPLE,
            ChatFormatting.RED
    };

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使本次强化成功率").withStyle(ChatFormatting.WHITE).
                append(Component.literal("提升").withStyle(ChatFormatting.GOLD)).
                append(Component.literal(rate[num]).withStyle(chatFormattings[num])));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

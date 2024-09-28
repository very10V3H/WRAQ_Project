package fun.wraq.Items.Forging;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ForgeImprove extends Item {

    private int Num = 0;

    public ForgeImprove(Properties p_41383_, int num) {
        super(p_41383_);
        this.Num = num;
    }

    private final String[] Rate = {
            "5%", "10%", "20%"
    };
    private final ChatFormatting[] chatFormattings = {
            ChatFormatting.GREEN,
            ChatFormatting.AQUA,
            ChatFormatting.LIGHT_PURPLE
    };

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使本次强化成功率").withStyle(ChatFormatting.WHITE).
                append(Component.literal("直接提升").withStyle(ChatFormatting.GOLD)).
                append(Component.literal(Rate[Num]).withStyle(chatFormattings[Num])));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}

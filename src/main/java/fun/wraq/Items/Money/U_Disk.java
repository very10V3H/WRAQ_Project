package fun.wraq.Items.Money;

import fun.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class U_Disk extends Item {

    public U_Disk(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 维瑞阿契银行发行的U盾。用于远程安全支付。"));
        components.add(Component.literal(" 将U盾放置在背包内，会将背包内的所有金币、银币、铜币直接存入银行账户。"));
        components.add(Component.literal(" 如果不想存入，将U盾放进你的下界合金背包就可以了，不是吗？"));
        components.add(Component.literal(" 维瑞阿契银行与本源研究所达成合作，如果你背包内有一组").withStyle(ChatFormatting.WHITE).
                append(ModItems.WorldSoul1.get().getDefaultInstance().getDisplayName()).
                append(Component.literal("那么它们将会被自动转化为").withStyle(ChatFormatting.WHITE)).
                append(ModItems.WorldSoul2.get().getDefaultInstance().getDisplayName()));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }
}

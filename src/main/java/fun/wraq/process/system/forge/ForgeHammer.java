package fun.wraq.process.system.forge;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ForgeHammer extends Item {

    private final int tier;

    public ForgeHammer(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        List<Double> rateList = ForgeEquipUtils.successForgeRate.get(tier);
        components.add(Component.literal("锻造品质几率:").withStyle(ChatFormatting.GOLD));
        for (int i = 0; i < rateList.size(); i++) {
            components.add(Component.literal("").withStyle(ChatFormatting.WHITE).
                    append(ForgeEquipUtils.getDescription(i)).
                    append(Component.literal(" - ").withStyle(ChatFormatting.GRAY)).
                    append(Component.literal(String.format("%.0f%%", (ForgeEquipUtils.successForgeRate.get(tier).get(i) * 100)))));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public int getTier() {
        return tier;
    }
}

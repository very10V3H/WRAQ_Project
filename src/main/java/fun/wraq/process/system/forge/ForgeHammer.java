package fun.wraq.process.system.forge;

import fun.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ForgeHammer extends Item {

    private final int tier;

    public ForgeHammer(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        List<Double> rateList = ForgeEquipUtils.successForgeRate.get(getTier());
        components.add(Component.literal("锻造品质几率:").withStyle(ChatFormatting.GOLD));
        for (int i = 0; i < rateList.size(); i++) {
            if (ForgeEquipUtils.successForgeRate.get(getTier()).get(i) - 0 < 1e-6) continue;
            components.add(Component.literal("").withStyle(ChatFormatting.WHITE).
                    append(ForgeEquipUtils.getDescription(i)).
                    append(Component.literal(" - ").withStyle(ChatFormatting.GRAY)).
                    append(Component.literal(String.format("%.0f%%", (ForgeEquipUtils.successForgeRate.get(getTier()).get(i) * 100)))));
        }
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public int getTier() {
        return tier;
    }

    public static List<Item> forgeHammers = new ArrayList<>();
    public static Item getForgeHammerByTier(int tier) {
        if (forgeHammers.isEmpty()) {
            forgeHammers.addAll(List.of(
                    ModItems.WoodHammer.get(),
                    ModItems.StoneHammer.get(),
                    ModItems.IronHammer.get(),
                    ModItems.CopperHammer.get(),
                    ModItems.GoldHammer.get(),
                    ModItems.DiamondHammer.get(),
                    ModItems.EMERALD_HAMMER.get(),
                    ModItems.NETHER_HAMMER.get(),
                    ModItems.END_HAMMER.get()
            ));
        }
        return forgeHammers.get(tier);
    }
}

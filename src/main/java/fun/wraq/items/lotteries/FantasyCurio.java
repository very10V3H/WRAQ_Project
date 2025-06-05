package fun.wraq.items.lotteries;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class FantasyCurio extends Item implements ICurioItem {

    private final int tier;
    public FantasyCurio(Properties properties, int tier) {
        super(properties);
        Utils.curiosList.add(this);
        this.tier = tier;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal(" 使基础属性的最终值提升" + new int[]{3, 4, 5}[tier] + "%")
                .withStyle(CustomStyle.styleOfFantasy));
        components.add(Te.s(" 暴击几率/百分比穿透不受加成", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        components.add(Te.s(" 同一槽位的幻想饰品仅生效最高加成", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }
}

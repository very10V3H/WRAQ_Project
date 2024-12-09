package fun.wraq.series.instance.series.harbinger.weapon;

import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HarbingerWeaponMaterial extends WraqItem {

    public HarbingerWeaponMaterial(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 铸造品质:", CustomStyle.styleOfHarbinger, getQualityDescription(itemStack)));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public static final String TIER_KEY = "HARBINGER_WEAPON_MATERIAL_TIER_KEY";
    public static Component getQualityDescription(ItemStack stack) {
        int tier = getQualityTier(stack);
        ForgeEquipUtils.TierValueAndDescription v = ForgeEquipUtils.tierValueAndDescriptionMap.get(tier);
        return v == null ? Te.s("") : Te.s(v.description(), v.style());
    }

    public static int getQualityTier(ItemStack stack) {
        return stack.getOrCreateTagElement(Utils.MOD_ID).getInt(TIER_KEY);
    }

    public static void setQualityTier(ItemStack stack, int tier) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putInt(TIER_KEY, tier);
    }
}

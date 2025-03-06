package fun.wraq.series.instance.series.harbinger.weapon;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.multiblockactive.rightclick.drive.EnhanceCondition;
import fun.wraq.process.func.multiblockactive.rightclick.drive.EnhanceOperation;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HarbingerWeaponMaterial extends WraqItem implements Decomposable {

    public HarbingerWeaponMaterial(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 铸造品质 - ", CustomStyle.styleOfHarbinger, getQualityDescription(itemStack)));
        int tier = getQualityTier(itemStack);
        if (itemStack.is(HarbingerItems.HARBINGER_ROD.get())) {
            components.add(Te.s(" 为成品额外提供",
                    ComponentUtils.AttributeDescription.attackDamage(String.valueOf(tier * 100)), "或",
                    ComponentUtils.AttributeDescription.manaDamage(String.valueOf(tier * 200))));
        } else if (itemStack.is(HarbingerItems.HARBINGER_WEAPON_CORE.get())) {
            components.add(Te.s(" 为成品额外提供",
                    ComponentUtils.AttributeDescription.attackDamage(tier + "%"), "或",
                    ComponentUtils.AttributeDescription.manaDamage(tier + "%")));
        } else if (itemStack.is(HarbingerItems.HARBINGER_SWORD_BLADE.get())) {
            components.add(Te.s(" 为", "长剑", CustomStyle.styleOfPower, "额外提供",
                    ComponentUtils.AttributeDescription.critDamage(tier * 4 + "%")));
        } else if (itemStack.is(HarbingerItems.HARBINGER_STRING.get())) {
            components.add(Te.s(" 为", "长弓", CustomStyle.styleOfFlexible, "额外提供",
                    ComponentUtils.AttributeDescription.swiftness(String.valueOf(tier))));
        } else if (itemStack.is(HarbingerItems.HARBINGER_MIRROR.get())) {
            components.add(Te.s(" 为", "法杖", CustomStyle.styleOfMana, "额外提供",
                    ComponentUtils.AttributeDescription.manaRecover(String.valueOf(tier * 10))));
        }
        components.add(Te.s(" 可以在", "沙岸村", CustomStyle.styleOfSunIsland, "将品阶至多强化至",
                "不可思议", CustomStyle.styleOfPower));
        components.add(Te.s(" 强化坐标:", "(1867, 81, 1696)", CustomStyle.styleOfHarbinger));
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

    @Override
    public ItemStack getProduct() {
        return new ItemStack(HarbingerItems.HARBINGER_INGOT.get(), 10);
    }

    public static final EnhanceCondition enhanceCondition = (stack -> {
        return stack.getItem() instanceof HarbingerWeaponMaterial && getQualityTier(stack) < 9;
    });

    public static final EnhanceOperation enhanceOperation = (stack -> {
        setQualityTier(stack, getQualityTier(stack) + 1);
    });
}

package fun.wraq.series.instance.series.harbinger.weapon;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public class HarbingerBow extends WraqBow implements HarbingerMainHand, ActiveItem,
        ExBaseAttributeValueEquip, ForgeItem {

    public HarbingerBow(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 2500d);
        Utils.defencePenetration0.put(this, 50d);
        Utils.critRate.put(this, 0.3);
        Utils.levelRequire.put(this, 230);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfHarbinger;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return HarbingerMainHand.getCommonAdditionalComponents(stack);
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfHarbinger();
    }

    @Override
    public void active(Player player) {
        HarbingerMainHand.active(player);
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    @Override
    public Style getQuoteStyle() {
        return CustomStyle.styleOfPower;
    }

    @Override
    public Style getExValueStyle() {
        return CustomStyle.styleOfHarbinger;
    }

    @Override
    public Map<Map<Item, Double>, TagAndEachTierValue> getTagAndRateMap() {
        return Map.of(
                Utils.attackDamage, new TagAndEachTierValue(HarbingerMainHand.ATTACK_DAMAGE, 100),
                Utils.percentAttackDamageEnhance, new TagAndEachTierValue(HarbingerMainHand.PERCENT_ATTACK_DAMAGE, 0.01),
                Utils.swiftnessUp, new TagAndEachTierValue(HarbingerMainHand.SWIFTNESS, 1)
        );
    }

    @Override
    public void beforeRemoveMaterialOnForge(ItemStack product, ItemStack removingStack) {
        if (product.getItem() instanceof HarbingerMainHand) {
            if (removingStack.is(HarbingerItems.HARBINGER_ROD.get())) {
                ExBaseAttributeValueEquip.getStackExBaseAttributeData(product)
                        .putInt(HarbingerMainHand.ATTACK_DAMAGE, HarbingerWeaponMaterial.getQualityTier(removingStack));
                HarbingerMainHand.addMaterial(product, removingStack);
            }
            if (removingStack.is(HarbingerItems.HARBINGER_WEAPON_CORE.get())) {
                ExBaseAttributeValueEquip.getStackExBaseAttributeData(product)
                        .putInt(HarbingerMainHand.PERCENT_ATTACK_DAMAGE, HarbingerWeaponMaterial.getQualityTier(removingStack));
                HarbingerMainHand.addMaterial(product, removingStack);
            }
            if (removingStack.is(HarbingerItems.HARBINGER_STRING.get())) {
                ExBaseAttributeValueEquip.getStackExBaseAttributeData(product)
                        .putInt(HarbingerMainHand.SWIFTNESS, HarbingerWeaponMaterial.getQualityTier(removingStack));
                HarbingerMainHand.addMaterial(product, removingStack);
            }
            HarbingerMainHand.setMaxCount(product, 20);
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.CASTLE_BOW_E.get()),
                new ItemStack(HarbingerItems.HARBINGER_INGOT.get(), 156),
                new ItemStack(HarbingerItems.HARBINGER_HEART.get(), 16),
                new ItemStack(HarbingerItems.HARBINGER_ROD.get()),
                new ItemStack(HarbingerItems.HARBINGER_WEAPON_CORE.get()),
                new ItemStack(HarbingerItems.HARBINGER_STRING.get()),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 22),
                new ItemStack(ModItems.ReputationMedal.get(), 56),
                new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8),
                new ItemStack(ModItems.WORLD_SOUL_3.get(), 6)
        );
    }
}

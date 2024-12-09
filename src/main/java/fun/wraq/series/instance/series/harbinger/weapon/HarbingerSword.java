package fun.wraq.series.instance.series.harbinger.weapon;

import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public class HarbingerSword extends WraqSword implements HarbingerMainHand, ActiveItem,
        ExBaseAttributeValueEquip, ForgeItem {

    public HarbingerSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 2200d);
        Utils.defencePenetration0.put(this, 50d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.35);
        Utils.critDamage.put(this, 1.2);
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
                Utils.critDamage, new TagAndEachTierValue(HarbingerMainHand.CRIT_DAMAGE, 1)
        );
    }

    @Override
    public void beforeRemoveMaterialOnForge(ItemStack product, ItemStack removingStack) {
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
        if (removingStack.is(HarbingerItems.HARBINGER_SWORD_BLADE.get())) {
            ExBaseAttributeValueEquip.getStackExBaseAttributeData(product)
                    .putInt(HarbingerMainHand.CRIT_DAMAGE, HarbingerWeaponMaterial.getQualityTier(removingStack));
            HarbingerMainHand.addMaterial(product, removingStack);
        }
        HarbingerMainHand.setMaxCount(product, 20);
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(HarbingerItems.HARBINGER_ROD.get()),
                new ItemStack(HarbingerItems.HARBINGER_WEAPON_CORE.get()),
                new ItemStack(HarbingerItems.HARBINGER_SWORD_BLADE.get())
        );
    }
}

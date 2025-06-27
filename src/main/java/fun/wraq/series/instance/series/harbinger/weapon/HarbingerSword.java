package fun.wraq.series.instance.series.harbinger.weapon;

import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public class HarbingerSword extends WraqSword implements HarbingerMainHand, ActiveItem,
        ExBaseAttributeValueEquip, ForgeItem {

    public HarbingerSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 2500d);
        Utils.defencePenetration0.put(this, 50d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.35);
        Utils.critDamage.put(this, 0.05);
        Utils.levelRequire.put(this, 225);
        Utils.maxHealth.put(this, 10000d);
        Utils.coolDownDecrease.put(this, 0.28);
        Element.fireElementValue.put(this, 1.6);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfHarbinger;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return HarbingerMainHand.getCommonAdditionalComponents(stack, getMainStyle(), 1);
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
                Utils.critDamage, new TagAndEachTierValue(HarbingerMainHand.CRIT_DAMAGE, 0.04)
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
            if (removingStack.is(HarbingerItems.HARBINGER_SWORD_BLADE.get())) {
                ExBaseAttributeValueEquip.getStackExBaseAttributeData(product)
                        .putInt(HarbingerMainHand.CRIT_DAMAGE, HarbingerWeaponMaterial.getQualityTier(removingStack));
                HarbingerMainHand.addMaterial(product, removingStack);
            }
            HarbingerMainHand.setMaxCount(product, 20);
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(HarbingerItems.HARBINGER_INGOT.get(), 156),
                new ItemStack(HarbingerItems.HARBINGER_HEART.get(), 16),
                new ItemStack(HarbingerItems.HARBINGER_ROD.get()),
                new ItemStack(HarbingerItems.HARBINGER_WEAPON_CORE.get()),
                new ItemStack(HarbingerItems.HARBINGER_SWORD_BLADE.get()),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 48),
                new ItemStack(ModItems.REPUTATION_MEDAL.get(), 160),
                new ItemStack(PickaxeItems.TINKER_GOLD.get(), 20),
                new ItemStack(ModItems.WORLD_SOUL_3.get(), 12)
        );
    }

    @Override
    public void onCauseFinalDamage(Player player, Mob mob, double damage) {
        HarbingerMainHand.onHit(mob, this);
    }
}

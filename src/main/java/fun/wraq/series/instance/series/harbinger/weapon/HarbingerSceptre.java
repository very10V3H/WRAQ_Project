package fun.wraq.series.instance.series.harbinger.weapon;

import fun.wraq.common.equip.WraqSceptre;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public class HarbingerSceptre extends WraqSceptre implements HarbingerMainHand, ActiveItem,
        ExBaseAttributeValueEquip, ForgeItem {

    public HarbingerSceptre(Properties properties) {
        super(properties);
        Utils.manaDamage.put(this, 5000d);
        Utils.manaRecover.put(this, 40d);
        Utils.manaPenetration0.put(this, 50d);
        Utils.coolDownDecrease.put(this, 0.25);
        Utils.levelRequire.put(this, 225);
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
                Utils.manaDamage, new TagAndEachTierValue(HarbingerMainHand.MANA_DAMAGE, 200),
                Utils.percentManaDamageEnhance, new TagAndEachTierValue(HarbingerMainHand.PERCENT_MANA_DAMAGE, 0.01),
                Utils.manaRecover, new TagAndEachTierValue(HarbingerMainHand.MANA_RECOVER, 10)
        );
    }

    @Override
    public void beforeRemoveMaterialOnForge(ItemStack product, ItemStack removingStack) {
        if (product.getItem() instanceof HarbingerMainHand) {
            if (removingStack.is(HarbingerItems.HARBINGER_ROD.get())) {
                ExBaseAttributeValueEquip.getStackExBaseAttributeData(product)
                        .putInt(HarbingerMainHand.MANA_DAMAGE, HarbingerWeaponMaterial.getQualityTier(removingStack));
                HarbingerMainHand.addMaterial(product, removingStack);
            }
            if (removingStack.is(HarbingerItems.HARBINGER_WEAPON_CORE.get())) {
                ExBaseAttributeValueEquip.getStackExBaseAttributeData(product)
                        .putInt(HarbingerMainHand.PERCENT_MANA_DAMAGE, HarbingerWeaponMaterial.getQualityTier(removingStack));
                HarbingerMainHand.addMaterial(product, removingStack);
            }
            if (removingStack.is(HarbingerItems.HARBINGER_MIRROR.get())) {
                ExBaseAttributeValueEquip.getStackExBaseAttributeData(product)
                        .putInt(HarbingerMainHand.MANA_RECOVER, HarbingerWeaponMaterial.getQualityTier(removingStack));
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
                new ItemStack(HarbingerItems.HARBINGER_MIRROR.get()),
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

package fun.wraq.series.overworld.divine.equip.armor;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import fun.wraq.series.overworld.divine.equip.weapon.DivineWeaponCommon;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DivineArmor extends WraqArmor implements DivineArmorCommon {

    private final double maxRate;
    private final int maxCount;
    public DivineArmor(ArmorMaterial armorMaterial, Type type, Properties properties, double maxRate, int maxCount) {
        super(armorMaterial, type, properties);
        this.maxRate = maxRate;
        this.maxCount = maxCount;
        if (type.equals(Type.HELMET)) {
            Utils.percentHealthRecover.put(this, 0.015);
            Utils.healthRecover.put(this, 200d);
            Utils.defence.put(this, 75d);
            Utils.manaDefence.put(this, 40d);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, 150d);
            Utils.manaDefence.put(this, 80d);
            Utils.maxHealth.put(this, 30000d);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 60000d);
            Utils.defence.put(this, 75d);
            Utils.manaDefence.put(this, 40d);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.16);
            Utils.maxHealth.put(this, 30000d);
        }
        Utils.levelRequire.put(this, 230);
    }

    @Override
    public Style getMainStyle() {
        return DivineArmorCommon.style;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return DivineArmorCommon.getDescription(stack, maxRate, maxCount);
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfDivine();
    }

    @Override
    public int getType(Player player) {
        switch (this.getType()) {
            case CHESTPLATE -> {
                return 4;
            }
            case HELMET -> {
                return 1;
            }
            case LEGGINGS -> {
                return 2;
            }
            case BOOTS -> {
                return 3;
            }
        }
        return 1;
    }

    @Override
    public double getEnhanceRate(Player player) {
        ItemStack stack = null;
        for (ItemStack armorStack : player.getArmorSlots()) {
            if (armorStack.getItem().equals(this)) {
                stack = armorStack;
            }
        }
        if (stack == null) {
            return 0;
        }
        return DivineArmorCommon.getCommonEnhanceRate(stack, maxRate, maxCount);
    }

    @Override
    public void onKill(Player player, Mob mob) {
        player.getArmorSlots().forEach(stack -> {
            if (stack.getItem() instanceof DivineArmor) {
                DivineWeaponCommon.addDivineCount(stack);
            }
        });
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(DivineIslandItems.DIVINE_RUNE_ARMOR.get(), 96),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 48),
                new ItemStack(ModItems.ReputationMedal.get(), 160),
                new ItemStack(PickaxeItems.TINKER_DIAMOND.get(), 20),
                new ItemStack(ModItems.WORLD_SOUL_3.get(), 12)
        );
    }
}

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DivineArmor extends WraqArmor implements DivineArmorCommon {

    private final double maxRate;
    private final int maxCount;
    private final int tier;
    public DivineArmor(ArmorMaterial armorMaterial, Type type, Properties properties, int tier) {
        super(armorMaterial, type, properties);
        this.tier = tier;
        this.maxRate = new double[]{0.3, 0.5, 0.8}[tier];
        this.maxCount = new int[]{3000, 5000, 8000}[tier];
        if (type.equals(Type.HELMET)) {
            Utils.percentHealthRecover.put(this, new double[]{0.015, 0.02, 0.025}[tier]);
            Utils.healthRecover.put(this, new double[]{200, 250, 300}[tier]);
            Utils.defence.put(this, new double[]{75d, 85d, 100d}[tier]);
            Utils.manaDefence.put(this, new double[]{40d, 45d, 50d}[tier]);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, new double[]{150d, 170d, 200d}[tier]);
            Utils.manaDefence.put(this, new double[]{80d, 90d, 100d}[tier]);
            Utils.maxHealth.put(this, new double[]{30000, 35000, 40000}[tier]);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, new double[]{60000, 70000, 80000}[tier]);
            Utils.defence.put(this, new double[]{75d, 85d, 100d}[tier]);
            Utils.manaDefence.put(this, new double[]{40d, 45d, 50d}[tier]);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.16);
            Utils.maxHealth.put(this, new double[]{30000, 35000, 40000}[tier]);
        }
        Utils.levelRequire.put(this, new int[]{230, 240, 250}[tier]);
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
        DivineWeaponCommon.onKillMob(player, mob);
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (tier == 0) {
            return List.of(
                    new ItemStack(DivineIslandItems.DIVINE_RUNE_ARMOR.get(), 96),
                    new ItemStack(ModItems.COMPLETE_GEM.get(), 48),
                    new ItemStack(ModItems.ReputationMedal.get(), 160),
                    new ItemStack(PickaxeItems.TINKER_DIAMOND.get(), 20),
                    new ItemStack(ModItems.WORLD_SOUL_3.get(), 12)
            );
        } else {
            Item tier0Equip = DivineIslandItems.DIVINE_HELMET_0.get();
            switch (this.getType()) {
                case HELMET -> {
                    tier0Equip = DivineIslandItems.DIVINE_HELMET_0.get();
                }
                case CHESTPLATE -> {
                    tier0Equip = DivineIslandItems.DIVINE_CHEST_0.get();
                }
                case LEGGINGS -> {
                    tier0Equip = DivineIslandItems.DIVINE_LEGGINGS_0.get();
                }
                case BOOTS -> {
                    tier0Equip = DivineIslandItems.DIVINE_BOOTS_0.get();
                }
            }
            return List.of(
                    new ItemStack(tier0Equip, 1),
                    new ItemStack(DivineIslandItems.DIVINE_BALANCE_STAR.get(), 999),
                    new ItemStack(ModItems.RainbowCrystal.get(), 999)
            );
        }
    }
}

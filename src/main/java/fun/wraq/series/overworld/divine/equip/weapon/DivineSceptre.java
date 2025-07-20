package fun.wraq.series.overworld.divine.equip.weapon;

import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.PreventLeftClickShoot;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.divine.DivineIslandItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DivineSceptre extends WraqSceptre implements DivineWeaponCommon, PreventLeftClickShoot {

    private final double transformRate;
    private final double upperLimitRate;
    private final int maxCount;
    private final double maxActiveDistance;
    private final int tier;
    public DivineSceptre(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        this.transformRate = new double[]{0.2, 0.5, 0.8}[tier];
        this.upperLimitRate = new double[]{0.2, 0.5, 0.8}[tier];
        this.maxCount = new int[]{3000, 5000, 8000}[tier];
        this.maxActiveDistance = new double[]{20, 32, 48}[tier];
        Utils.manaDamage.put(this, new double[]{6000, 7000, 8000}[tier]);
        Utils.manaRecover.put(this, new double[]{50, 55, 60}[tier]);
        Utils.manaPenetration0.put(this, new double[]{70, 80, 90}[tier]);
        Utils.coolDownDecrease.put(this, new double[]{0.3, 0.35, 0.4}[tier]);
        Utils.levelRequire.put(this, new int[]{230, 240, 250}[tier]);
        DivineWeaponCommon.weaponList.add(this);
    }

    @Override
    protected EntityType<ManaArrow> getArrowType() {
        return ModEntityType.NEW_ARROW_SNOW.get();
    }

    @Override
    protected String getParticleType() {
        return StringUtils.ParticleTypes.Snow;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.DIVINE_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return DivineWeaponCommon.getCommonDescription(stack, upperLimitRate, maxCount, false, maxActiveDistance);
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfDivine();
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        List<Attribute> attributes = new ArrayList<>();
        int count = DivineWeaponCommon.getDivineCount(stack);
        double rate = Math.min(1, count * 1.0 / maxCount);
        attributes.addAll(List.of(
                new Attribute(Utils.elementStrength, upperLimitRate * rate),
                new Attribute(Utils.percentManaDamageEnhance, upperLimitRate * rate)
        ));
        return attributes;
    }

    @Override
    public void onKill(Player player, Mob mob) {
        ItemStack stack = player.getMainHandItem();
        DivineWeaponCommon.addDivineCount(stack);
        DivineWeaponCommon.onKillMob(player, mob);
    }

    @Override
    public void active(Player player) {
        DivineWeaponCommon.active(player, maxActiveDistance);
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    public double getTransformRate() {
        return transformRate;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (tier == 1) {
            return List.of(
                    new ItemStack(DivineIslandItems.DIVINE_SCEPTRE_0.get(), 1),
                    new ItemStack(DivineIslandItems.DIVINE_BALANCE_STAR.get(), 64),
                    new ItemStack(DivineIslandItems.DIVINE_RUNE_WEAPON.get(), 192),
                    new ItemStack(ModItems.RAINBOW_CRYSTAL.get(), 8),
                    new ItemStack(ModItems.REPUTATION_MEDAL.get(), 192),
                    new ItemStack(PickaxeItems.TINKER_GOLD.get(), 32)
            );
        }
        return List.of(
                new ItemStack(DivineIslandItems.DIVINE_RUNE_WEAPON.get(), 128),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 48),
                new ItemStack(ModItems.REPUTATION_MEDAL.get(), 160),
                new ItemStack(PickaxeItems.TINKER_GOLD.get(), 20),
                new ItemStack(ModItems.WORLD_SOUL_3.get(), 12)
        );
    }

    @Override
    public void onCauseFinalDamage(Player player, Mob mob, double damage) {
        DivineWeaponCommon.onCauseDamageCommon(player, mob);
    }
}

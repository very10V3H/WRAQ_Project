package fun.wraq.series.overworld.divine.equip.weapon;

import fun.wraq.common.attribute.PlayerAttributes;
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class DivineSceptre extends WraqSceptre implements DivineWeaponCommon, PreventLeftClickShoot {

    private final double transformRate;
    private final double upperLimitRate;
    private final int maxCount;
    private final double maxActiveDistance;
    public DivineSceptre(Properties properties, double transformRate, double upperLimitRate, int maxCount,
                         double maxActiveDistance) {
        super(properties);
        this.transformRate = transformRate;
        this.upperLimitRate = upperLimitRate;
        this.maxCount = maxCount;
        this.maxActiveDistance = maxActiveDistance;
        Utils.manaDamage.put(this, 6000d);
        Utils.manaRecover.put(this, 50d);
        Utils.manaPenetration0.put(this, 70d);
        Utils.coolDownDecrease.put(this, 0.3);
        Utils.levelRequire.put(this, 230);
        DivineWeaponCommon.weaponList.add(this);
    }

    @Override
    protected ManaArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player, level,
                rate, PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Snow);
        newArrow.setSilent(true);
        newArrow.setNoGravity(true);
        newArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 3, 1);
        ProjectileUtil.rotateTowardsMovement(newArrow, 0);
        WraqSceptre.adjustOrb(newArrow, player);
        level.addFreshEntity(newArrow);
        return newArrow;
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
    public List<Attribute> getAttributes(Player player) {
        List<Attribute> attributes = new ArrayList<>();
        ItemStack stack = player.getMainHandItem();
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
        return List.of(
                new ItemStack(DivineIslandItems.DIVINE_RUNE_WEAPON.get(), 128),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 48),
                new ItemStack(ModItems.ReputationMedal.get(), 160),
                new ItemStack(PickaxeItems.TINKER_GOLD.get(), 20),
                new ItemStack(ModItems.WORLD_SOUL_3.get(), 12)
        );
    }
}

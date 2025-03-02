package fun.wraq.series.instance.series.castle;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.EnhancedForgedItem;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class CastleSceptre extends WraqSceptre implements ForgeItem, ActiveItem, EnhancedForgedItem {

    private final int tier;

    public CastleSceptre(Properties properties, int tier) {
        super(properties);
        Utils.manaDamage.put(this, 3000d);
        Utils.manaRecover.put(this, 26d);
        Utils.manaPenetration0.put(this, 36d);
        Utils.coolDownDecrease.put(this, 0.35);
        Utils.levelRequire.put(this, 180);
        this.tier = tier;
    }

    @Override
    protected ManaArrow summonManaArrow(Player player, double rate) {
        Level level = player.level();
        ManaArrow newArrow = new ManaArrow(ModEntityType.NEW_ARROW_SNOW.get(), player, level,
                rate, PlayerAttributes.manaPenetration(player),
                PlayerAttributes.manaPenetration0(player), StringUtils.ParticleTypes.Sea);
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
        return CustomStyle.styleOfCastle;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("暗影秘法").withStyle(style));
        components.add(Component.literal(" 你的法球攻击将使敌人被拖入暗影之中").withStyle(ChatFormatting.ITALIC).withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术伤害").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将附带造成伤害" + (tier == 0 ? "25%" : "100%") + "的")
                        .withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.attackDamageValue("")));
        if (tier == 0) {
            components.add(Te.s(" 倍率可被", "锐化至", CustomStyle.styleOfWorld, "100%", style));
        }
        Compute.DescriptionActive(components, Component.literal("噬魔注能").withStyle(style));
        components.add(Component.literal(" 扣除自身").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.health("15%当前")).
                append(Component.literal("，获得持续6s的:").withStyle(ChatFormatting.WHITE)));
        components.add(Te.s(" 1. ", ComponentUtils.getCommonDamageEnhance("25%")));
        components.add(Te.s(" 2. ", ComponentUtils.AttributeDescription.defencePenetration("25%")));
        components.add(Te.s(" 3. ", ComponentUtils.AttributeDescription.manaPenetration("25%")));
        ComponentUtils.coolDownTimeDescription(components, 15);
        components.add(Component.literal(" 多件暗黑武器的主动将会刷新持续时间，但效果将不会叠加，且共享冷却时间")
                .withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfCastle();
    }

    public static void exDamage(Player player, Mob mob, double damage) {
        Item item = player.getMainHandItem().getItem();
        if (item instanceof CastleSceptre castleSceptre) {
            Damage.causeAttackDamageToMonster_AdDamage_Direct(player, mob,
                    damage * (castleSceptre.tier == 0 ? 0.25 : 1), true);
        }
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (tier == 0) {
            return List.of(
                    new ItemStack(ModItems.CastleSceptrePiece.get(), 12),
                    new ItemStack(ModItems.CastlePiece.get(), 192),
                    new ItemStack(ModItems.TreeRune.get(), 8),
                    new ItemStack(PickaxeItems.TINKER_GOLD.get(), 12)
            );
        }
        return List.of(
                new ItemStack(ModItems.CASTLE_SCEPTRE.get()),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 26),
                new ItemStack(ModItems.ReputationMedal.get(), 104),
                new ItemStack(ModItems.WORLD_SOUL_3.get(), 6)
        );
    }

    @Override
    public void active(Player player) {
        Compute.decreasePlayerHealth(player, player.getHealth() * 0.15,
                Component.literal(" 被暗黑魔能吞噬了。").withStyle(CustomStyle.styleOfCastle));
        Compute.playerItemCoolDown(player, ModItems.CASTLE_SCEPTRE.get(), 15);
        Compute.playerItemCoolDown(player, ModItems.CASTLE_SCEPTRE_E.get(), 15);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerCommonDamageEnhance,
                "castle weapon active", 0.25, Tick.get() + 120);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerDefencePenetrationModifier,
                "CastleWeaponActiveDefencePenetration", 0.25, Tick.get() + 120);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerManaPenetrationModifier,
                "CastleWeaponActiveManaPenetration", 0.25, Tick.get() + 120,
                ModItems.CASTLE_SCEPTRE.get());
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    @Override
    public int getEnhanceTier() {
        return tier;
    }
}

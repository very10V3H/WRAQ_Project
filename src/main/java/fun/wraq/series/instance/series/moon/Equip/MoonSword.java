package fun.wraq.series.instance.series.moon.Equip;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.EnhancedForgedItem;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.EnhanceNormalAttack;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class MoonSword extends WraqSword implements ActiveItem, OnHitEffectEquip, ForgeItem, EnhancedForgedItem {

    private final double activeRate;
    private final int tier;

    public MoonSword(Properties properties, double activeRate, int tier) {
        super(properties);
        Utils.attackDamage.put(this, 1200d);
        Utils.defencePenetration0.put(this, 29d);
        Utils.healthSteal.put(this, 0.3);
        Utils.critRate.put(this, 0.3);
        this.activeRate = activeRate;
        Utils.levelRequire.put(this, 160);
        this.tier = tier;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon1;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("行星之引").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通近战攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("会将目标周围半径6内的其他敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("小幅牵引").withStyle(style)).
                append(Component.literal("至目标位置").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionActive(components, Component.literal("永升之星").withStyle(style));
        components.add(Component.literal(" 你的下一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通近战攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("将").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("吸收").withStyle(style)).
                append(Component.literal("目标周围半径6内所有敌方单位的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.attackDamage("")));
        components.add(Component.literal("，提供在持续10s的等额").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.exAttackDamage(String.format("%.0f%%",
                        100 * activeRate))));
        components.add(Te.s(" 获得的攻击力不会超过基础攻击的50%.", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        components.add(Component.literal(" 并为你提供持续10s的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamage(tier == 0 ? "100%" : "200%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾").withStyle(ChatFormatting.GRAY)));
        if (tier == 0) {
            components.add(Te.s(" 额外攻击", CustomStyle.styleOfAttack, "与", "护盾倍率", CustomStyle.styleOfStone,
                    "在", "锐化后", CustomStyle.styleOfWorld, "翻倍"));
        }
        ComponentUtils.coolDownTimeDescription(components, 27);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoon();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void active(Player player) {
        Compute.playerItemCoolDown(player, this, 27);
        EnhanceNormalAttackModifier.addModifier(player, new EnhanceNormalAttackModifier("moonSwordActive", 0, new EnhanceNormalAttack() {
            @Override
            public void hit(Player player, Mob mob) {
                Shield.providePlayerShield(player, Tick.s(10), PlayerAttributes.attackDamage(player) * 2);
                List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class,
                        AABB.ofSize(mob.position(), 15, 15, 15));
                mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6);
                double attackDamage = 0;
                for (Mob mob1 : mobList) {
                    attackDamage += MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob1, MobSpawn.MobBaseAttributes.attackDamage);
                }
                StableAttributesModifier.addM(player, StableAttributesModifier.playerAttackDamageModifier,
                        "moonWeaponActive",
                        Math.min(PlayerAttributes.getBaseAttackDamage(player) * 0.5, attackDamage * activeRate),
                        Tick.get() + Tick.s(10), ModItems.MOON_SWORD.get());
            }
        }));
        Compute.sendEffectLastTime(player, ModItems.MOON_SWORD.get().getDefaultInstance(), 8888, 0, true);
    }

    @Override
    public double manaCost(Player player) {
        return 60;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 15, 15, 15))
                .stream().filter(mob1 -> mob1.distanceTo(mob) <= 6 && !mob1.equals(mob))
                .forEach(mob1 -> Compute.causeGatherEffect(mob1, 2, mob.position()));
    }

    @Override
    public int getEnhanceTier() {
        return tier;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (tier == 0) {
            return List.of(
                    new ItemStack(ModItems.MoonCompleteGem.get(), 16),
                    new ItemStack(ModItems.GOLD_COIN.get(), 384),
                    new ItemStack(PickaxeItems.TINKER_IRON.get(), 16),
                    new ItemStack(PickaxeItems.TINKER_COPPER.get(), 16)
            );
        }
        return List.of(
                new ItemStack(ModItems.MOON_SWORD.get()),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 20),
                new ItemStack(ModItems.ReputationMedal.get(), 80),
                new ItemStack(ModItems.WORLD_SOUL_3.get(), 8)
        );
    }
}

package fun.wraq.series.instance.series.moon.Equip;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.ChangedAttributesModifier;
import fun.wraq.process.func.EnhanceNormalAttack;
import fun.wraq.process.func.EnhanceNormalAttackModifier;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.impl.onhit.OnHitEffectMainHandWeapon;
import fun.wraq.common.equip.WraqSword;
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

public class MoonSword extends WraqSword implements ActiveItem, OnHitEffectMainHandWeapon {

    private final double activeRate;
    public MoonSword(Properties properties, double activeRate) {
        super(properties);
        Utils.attackDamage.put(this, 1200d);
        Utils.defencePenetration0.put(this, 29d);
        Utils.healthSteal.put(this, 0.3);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.8);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
        this.activeRate = activeRate;
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
                append(Compute.AttributeDescription.AttackDamage("")));
        components.add(Component.literal("，提供在10s内持续衰减的等额").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExAttackDamage(String.format("%.0f%%", activeRate * 100))));
        components.add(Component.literal(" 并为你提供持续20s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("200%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("护盾").withStyle(ChatFormatting.GRAY)));
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
        if (Compute.playerManaCost(player, 60)) {
            Compute.playerItemCoolDown(player, ModItems.MoonSword.get(), 27);
            EnhanceNormalAttackModifier.addModifier(player, new EnhanceNormalAttackModifier("moonSwordActive", 0, new EnhanceNormalAttack() {
                @Override
                public void hit(Player player, Mob mob) {
                    Shield.providePlayerShield(player, 400, PlayerAttributes.attackDamage(player) * 2);
                    Compute.sendEffectLastTime(player, ModItems.MoonSword.get().getDefaultInstance(), 200);
                    List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 15, 15, 15));
                    mobList.removeIf(mob1 -> mob1.distanceTo(mob) > 6);
                    double attackDamage = 0;
                    for (Mob mob1 : mobList) {
                        attackDamage += MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob1, MobSpawn.MobBaseAttributes.attackDamage);
                    }
                    ChangedAttributesModifier.addAttributeModifier(player, ChangedAttributesModifier.exAttackDamage,
                            "moonSwordActive", attackDamage * activeRate, 200, true);
                }
            }));
            Compute.sendEffectLastTime(player, ModItems.MoonSword.get().getDefaultInstance(), 8888, 0, true);
        }
    }

    @Override
    public void onHit(Player player, Mob mob) {
        mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 15, 15, 15))
                .stream().filter(mob1 -> mob1.distanceTo(mob) <= 6 && !mob1.equals(mob))
                .forEach(mob1 -> Compute.MonsterGatherProvider(mob1, 2, mob.position()));
    }
}

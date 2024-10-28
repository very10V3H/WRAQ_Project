package fun.wraq.series.nether.equip.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.impl.onhit.OnPowerCauseDamageEquip;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NetherManaArmor extends WraqArmor implements OnHitEffectEquip, OnPowerCauseDamageEquip, ForgeItem {

    public NetherManaArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        if (type.equals(Type.HELMET)) Utils.healthRecover.put(this, 30d);
        if (type.equals(Type.CHESTPLATE)) Utils.defence.put(this, 50d);
        if (type.equals(Type.LEGGINGS)) Utils.maxHealth.put(this, 2000d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.35);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("混沌崩解").withStyle(CustomStyle.styleOfNether));
        components.add(Te.m(" 造成伤害时").
                append(Te.m("会击碎目标")).
                append(ComponentUtils.AttributeDescription.manaDefence(String.valueOf(getSuitCount(NetherManaArmor.class)))));
        components.add(Te.m(" 至多叠加至10层，每层持续5s"));
        components.add(Te.m(" 套装数量对应每层提供的固定穿透", ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableTierAttributeModifier.addM(mob, StableTierAttributeModifier.manaDefence, "NetherManaArmor passive",
                -SuitCount.getNetherManaSuitCount(player), Tick.get() + 60, 10, ModItems.MagmaRune.get());
    }

    @Override
    public void onCauseDamage(Player player, Mob mob) {
        StableTierAttributeModifier.addM(mob, StableTierAttributeModifier.manaDefence, "NetherManaArmor passive",
                -SuitCount.getNetherManaSuitCount(player), Tick.get() + 60, 10, ModItems.MagmaRune.get());
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (type.equals(Type.HELMET)) {
            return List.of(
                    new ItemStack(ModItems.WitherRune.get(), 8),
                    new ItemStack(ModItems.NetherRune.get(), 2),
                    new ItemStack(ModItems.QuartzRune.get(), 1)
            );
        }
        if (type.equals(Type.CHESTPLATE)) {
            return List.of(
                    new ItemStack(ModItems.NetherSkeletonRune.get(), 8),
                    new ItemStack(ModItems.NetherRune.get(), 2),
                    new ItemStack(ModItems.QuartzRune.get(), 1)
            );
        }
        if (type.equals(Type.LEGGINGS)) {
            return List.of(
                    new ItemStack(ModItems.MagmaRune.get(), 8),
                    new ItemStack(ModItems.NetherRune.get(), 2),
                    new ItemStack(ModItems.QuartzRune.get(), 1)
            );
        }
        if (type.equals(Type.BOOTS)) {
            return List.of(
                    new ItemStack(ModItems.PiglinRune.get(), 8),
                    new ItemStack(ModItems.NetherRune.get(), 2),
                    new ItemStack(ModItems.QuartzRune.get(), 1)
            );
        }
        return List.of();
    }
}

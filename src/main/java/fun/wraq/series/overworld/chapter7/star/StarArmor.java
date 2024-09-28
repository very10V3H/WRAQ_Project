package fun.wraq.series.overworld.chapter7.star;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StarArmor extends WraqArmor {

    public StarArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 8192d);
        Utils.attackDamage.put(this, 1050d);
        Utils.manaDamage.put(this, 2100d);
        Utils.defence.put(this, 6d);
        Utils.manaDefence.put(this, 4d);
        Utils.critDamage.put(this, 0.7);
        Utils.coolDownDecrease.put(this, 0.3);
        Utils.swiftnessUp.put(this, 3d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon1;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("披星戴月").withStyle(getMainStyle()));
        components.add(Component.literal(" - 其一 手持近战武器时:").withStyle(CustomStyle.styleOfPower));
        components.add(Component.literal(" 对距离5格以内的目标造成的伤害将获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("25%伤害提升").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" - 其二 手持远程武器时:").withStyle(CustomStyle.styleOfFlexible));
        components.add(Component.literal(" 对距离5格以外的目标造成的伤害将获得").withStyle(ChatFormatting.WHITE).
                append(Component.literal("25%伤害提升").withStyle(CustomStyle.styleOfMana)));
        components.add(Component.literal(" -多件梦月防具能够线性提升被动的伤害提升效果").withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterVII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static double DamageEnhance(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.StarLeggings.get())
                || player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.StarHelmet.get())) {
            Item weapon = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (Utils.swordTag.containsKey(weapon) && mob.distanceTo(player) <= 5)
                return SuitCount.getStarSuitCount(player) * 0.25;
            if ((Utils.bowTag.containsKey(weapon) || Utils.sceptreTag.containsKey(weapon)) && mob.distanceTo(player) >= 5)
                return SuitCount.getStarSuitCount(player) * 0.25;
        }
        return 0;
    }
}

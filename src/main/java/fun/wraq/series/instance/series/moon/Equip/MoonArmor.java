package fun.wraq.series.instance.series.moon.Equip;

import fun.wraq.common.Compute;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.process.system.ore.OreItems;
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

public class MoonArmor extends WraqArmor implements ForgeItem {

    public MoonArmor(ModArmorMaterials material, Type type, Properties properties) {
        super(material, type, properties);
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 16000d);
            Utils.defence.put(this, 20d);
        }
        if (type.equals(Type.HELMET)) {
            Utils.percentHealthRecover.put(this, 0.007);
            Utils.healthRecover.put(this, 50d);
            Utils.defence.put(this, 20d);
        }
        Utils.levelRequire.put(this, 160);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon1;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("阴晴圆缺").withStyle(style));
        components.add(Component.literal(" - 其一 手持近战武器时:").withStyle(CustomStyle.styleOfPower));
        components.add(Component.literal(" 对距离5格以内的目标造成的伤害将获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getCommonDamageEnhance("15%")));
        components.add(Component.literal(" - 其二 手持远程武器时:").withStyle(CustomStyle.styleOfFlexible));
        components.add(Component.literal(" 对距离5格以外的目标造成的伤害将获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getCommonDamageEnhance("15%")));
        components.add(Component.literal(" -多件尘月防具能够线性提升被动的伤害提升效果").withStyle(ChatFormatting.GRAY));
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

    public static double DamageEnhance(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.MoonLeggings.get())
                || player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MoonHelmet.get())) {
            Item weapon = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
            if (Utils.swordTag.containsKey(weapon) && mob.distanceTo(player) <= 5)
                return SuitCount.getMoonSuitCount(player) * 0.15;
            if ((Utils.bowTag.containsKey(weapon) || Utils.sceptreTag.containsKey(weapon)) && mob.distanceTo(player) >= 5)
                return SuitCount.getMoonSuitCount(player) * 0.15;
        }
        return 0;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.MoonCompleteGem.get(), 12),
                new ItemStack(OreItems.WRAQ_ORE_1_ITEM.get(), 64)
        );
    }
}

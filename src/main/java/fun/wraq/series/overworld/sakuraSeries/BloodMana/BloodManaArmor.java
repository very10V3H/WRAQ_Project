package fun.wraq.series.overworld.sakuraSeries.BloodMana;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Dot;
import fun.wraq.process.func.suit.SuitCount;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class BloodManaArmor extends WraqArmor implements ForgeItem {

    public BloodManaArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        if (type.equals(Type.HELMET)) Utils.healthRecover.put(this, 25d);
        if (type.equals(Type.CHESTPLATE)) Utils.defence.put(this, 40d);
        if (type.equals(Type.LEGGINGS)) Utils.maxHealth.put(this, 2000d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.35);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("腥月初升").withStyle(style));
        components.add(Component.literal(" 你的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.healthSteal("")).
                append(Component.literal("提升").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("8%").withStyle(style)));
        Compute.DescriptionPassive(components, Component.literal("永升腥月").withStyle(style));
        components.add(Component.literal(" 当你拥有高于").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.health("70%")).
                append(Component.literal("时，你的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("近战攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("或").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢攻击").withStyle(CustomStyle.styleOfFlexible)));
        components.add(Component.literal(" 将使目标").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getAttackDamageDotDescription(1, 3, "10%")));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        components.add(Component.literal(" -多件腥月魔力防具可以线性增长被动效能。").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return new ArrayList<>() {{
            add(new ItemStack(ModItems.BloodManaRune.get(), 8));
            add(new ItemStack(ModItems.wolfLeather.get(), 320));
            add(new ItemStack(Items.LEATHER, 192));
            add(new ItemStack(ModItems.goldCoin.get(), 64));
        }};
    }

    public static void onAttackOrArrowHit(Player player, Mob mob) {
        if (SuitCount.getBloodManaSuitCount(player) > 0 && (player.getHealth() / player.getMaxHealth()) >= 0.7) {
            Dot.addDotOnMob(mob, new Dot(1, PlayerAttributes.attackDamage(player) * 0.1 * SuitCount.getBloodManaSuitCount(player),
                    3, player.getServer().getTickCount() + 20, player.getName().getString(), true));
        }
    }
}

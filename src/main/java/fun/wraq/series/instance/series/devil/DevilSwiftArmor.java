package fun.wraq.series.instance.series.devil;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class DevilSwiftArmor extends WraqArmor {

    public DevilSwiftArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.movementSpeedCommon.put(this, 0.45);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("唤魔者之息").withStyle(style));
        components.add(Component.literal(" 箭矢命中目标时，为你提供等同于目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("50%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.AttackDamage("")).
                append(Component.literal("加成，持续2s").withStyle(ChatFormatting.WHITE)));
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

    public static WeakHashMap<Player, Double> DevilSwiftArmorPassiveNumMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> DevilSwiftArmorPassiveTickMap = new WeakHashMap<>();

    public static void DevilSwiftArmorPassive(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.DevilSwiftBoots.get())) {
            int TickCount = player.getServer().getTickCount();
            DevilSwiftArmorPassiveNumMap.put(player, MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob, MobSpawn.MobBaseAttributes.defence) * 0.5);
            DevilSwiftArmorPassiveTickMap.put(player, TickCount + 40);
            Compute.sendEffectLastTime(player, ModItems.DevilBlood.get().getDefaultInstance(), 40);
        }
    }

    public static double DevilSwiftArmorPassiveExDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (DevilSwiftArmorPassiveTickMap.containsKey(player) && DevilSwiftArmorPassiveTickMap.get(player) > TickCount)
            return DevilSwiftArmorPassiveNumMap.get(player);
        return 0;
    }
}

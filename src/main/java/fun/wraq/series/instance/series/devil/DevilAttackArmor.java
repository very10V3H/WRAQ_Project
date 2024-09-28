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

public class DevilAttackArmor extends WraqArmor {

    public DevilAttackArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 3072d);
        Utils.attackDamage.put(this, 450d);
        Utils.defence.put(this, 6d);
        Utils.manaDefence.put(this, 4d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("封魔者之怒").withStyle(style));
        components.add(Component.literal(" 受到来自怪物的伤害时，为你提供等同于怪物").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("50%")).
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

    public static WeakHashMap<Player, Double> DevilAttackArmorPassiveNumMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> DevilAttackArmorPassiveTickMap = new WeakHashMap<>();

    public static void DevilAttackArmorPassive(Player player, Mob mob) {
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.DevilAttackChest.get())) {
            int TickCount = player.getServer().getTickCount();
            DevilAttackArmorPassiveNumMap.put(player, MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob, MobSpawn.MobBaseAttributes.attackDamage) * 0.5);
            DevilAttackArmorPassiveTickMap.put(player, TickCount + 40);
            Compute.sendEffectLastTime(player, ModItems.DevilBlood.get().getDefaultInstance(), 40);
        }
    }

    public static double DevilAttackArmorPassiveExDamage(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (DevilAttackArmorPassiveTickMap.containsKey(player) && DevilAttackArmorPassiveTickMap.get(player) > TickCount)
            return DevilAttackArmorPassiveNumMap.get(player);
        return 0;
    }
}

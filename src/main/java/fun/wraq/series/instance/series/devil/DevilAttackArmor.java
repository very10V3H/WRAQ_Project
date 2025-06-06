package fun.wraq.series.instance.series.devil;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.system.ore.OreItems;
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

public class DevilAttackArmor extends WraqArmor implements ForgeItem {

    public DevilAttackArmor(ModArmorMaterials Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.defence.put(this, 50d);
        Utils.maxHealth.put(this, 8000d);
        Utils.manaRecover.put(this, 8d);
        Utils.levelRequire.put(this, 150);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("封魔者之怒").withStyle(style));
        components.add(Component.literal(" 受到来自怪物的伤害时，为你提供等同于怪物").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamage("50%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.attackDamage("")).
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
        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.DEVIL_ATTACK_CHEST.get())) {
            int TickCount = Tick.get();
            DevilAttackArmorPassiveNumMap.put(player,
                    MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob, MobSpawn.MobBaseAttributes.attackDamage) * 0.5);
            DevilAttackArmorPassiveTickMap.put(player, TickCount + 40);
            Compute.sendEffectLastTime(player, ModItems.DEVIL_BLOOD.get().getDefaultInstance(), 40);
        }
    }

    public static double DevilAttackArmorPassiveExDamage(Player player) {
        int TickCount = Tick.get();
        if (DevilAttackArmorPassiveTickMap.containsKey(player)
                && DevilAttackArmorPassiveTickMap.get(player) > TickCount)
            return DevilAttackArmorPassiveNumMap.get(player);
        return 0;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.DEVIL_ATTACK_SOUL.get(), 48),
                new ItemStack(OreItems.WRAQ_ORE_3_ITEM.get(), 32)
        );
    }
}

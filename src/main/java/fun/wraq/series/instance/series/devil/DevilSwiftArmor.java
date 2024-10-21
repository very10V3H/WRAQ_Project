package fun.wraq.series.instance.series.devil;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.impl.onkill.OnKillEffectEquip;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class DevilSwiftArmor extends WraqArmor implements OnKillEffectEquip, InCuriosOrEquipSlotAttributesModify, ForgeItem {

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
        Compute.DescriptionPassive(components, Component.literal("猎魔追命").withStyle(style));
        components.add(Te.s("获得", "最近击杀", getMainStyle(), "的", "5", getMainStyle(), "名目标的", ComponentUtils.AttributeDescription.attackDamage("20%")));
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

    public static Map<Player, Queue<Double>> recentKillEntityAttackDamage = new WeakHashMap<>();

    @Override
    public List<Attribute> getAttributes(Player player) {
        Queue<Double> q = recentKillEntityAttackDamage.getOrDefault(player, new ArrayDeque<>());
        return List.of(new Attribute(Utils.attackDamage, q.stream().mapToDouble(value -> value).sum()));
    }

    @Override
    public void onKill(Player player, Mob mob) {
        if (!recentKillEntityAttackDamage.containsKey(player)) {
            recentKillEntityAttackDamage.put(player, new ArrayDeque<>());
        }
        Queue<Double> q = recentKillEntityAttackDamage.get(player);
        while (q.size() >= 5) q.poll();
        q.add(MobAttributes.attackDamage(mob) * 0.2);
        Compute.sendEffectLastTime(player, this, q.size(), true);
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.DevilSwiftSoul.get(), 128),
                new ItemStack(OreItems.WRAQ_ORE_3_ITEM.get(), 32)
        );
    }
}

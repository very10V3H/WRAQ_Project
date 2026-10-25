package fun.wraq.series.overworld.chapter1.volcano;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.forge.IgnoreForgeQuality;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class VolcanoArmor extends WraqArmor implements IgnoreForgeQuality {

    public VolcanoArmor(ArmorMaterial armorMaterial, Type type) {
        super(armorMaterial, type, new Properties().rarity(CustomStyle.VolcanoItalic));
        switch (type) {
            case HELMET -> {
                Utils.attackDamage.put(this, 30d);
                Utils.manaDamage.put(this, 30d);
            }
            case CHESTPLATE -> {
                Utils.attackDamage.put(this, 16d);
                Utils.manaDamage.put(this, 32d);
            }
            case LEGGINGS -> {
                Utils.attackDamage.put(this, 14d);
                Utils.manaDamage.put(this, 28d);
            }
            case BOOTS -> {
                Utils.movementSpeedCommon.put(this, 0.05);
                Utils.attackDamage.put(this, 8d);
                Utils.manaDamage.put(this, 16d);
            }
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfVolcano;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        if (type.equals(Type.HELMET)) {
            Compute.DescriptionPassive(components,
                    Component.literal("熔火蓄势").withStyle(CustomStyle.styleOfVolcano));
            components.add(Component.literal(" 每第三次攻击必定暴击").withStyle(ChatFormatting.WHITE));
        }
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    private static final Map<Player, Integer> attackCounts = new WeakHashMap<>();
    private static final Map<Player, Integer> guaranteedCritTicks = new WeakHashMap<>();

    /**
     * 记录一次普通攻击；第三次攻击当 tick 内所有暴击判定必定成功。
     */
    public static void onAttack(Player player) {
        if (!(player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof VolcanoArmor)) {
            attackCounts.remove(player);
            guaranteedCritTicks.remove(player);
            return;
        }
        int count = attackCounts.getOrDefault(player, 0) + 1;
        if (count >= 3) {
            attackCounts.put(player, 0);
            guaranteedCritTicks.put(player, player.tickCount);
        } else {
            attackCounts.put(player, count);
        }
    }

    public static boolean isGuaranteedCrit(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof VolcanoArmor
                && guaranteedCritTicks.getOrDefault(player, -1) == player.tickCount;
    }
}

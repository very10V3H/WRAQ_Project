package fun.wraq.series.overworld.chapter1.waterSystem;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.impl.onshoot.OnPowerReleaseCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class TearCurio extends WraqCurios implements OnPowerReleaseCurios, InCuriosOrEquipSlotAttributesModify {

    public TearCurio(Properties properties) {
        super(properties);
    }

    @Override
    public Component getTypeDescription() {
        return null;
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("法力积攒", hoverMainStyle()));
        components.add(Te.s(" 战斗状态下", ChatFormatting.RED, "，释放一次法术可以为你积攒",
                ComponentUtils.AttributeDescription.manaValue("20")));
        components.add(Te.s(" 至多叠加至", ComponentUtils.AttributeDescription.manaValue("720")));
        components.add(Te.s(" 在脱离战斗后", "30s", ChatFormatting.AQUA, "，失去积攒的法力值"));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfWater;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSunIsland();
    }

    public static Map<Player, Integer> countMap = new WeakHashMap<>();

    @Override
    public void onRelease(Player player) {
        if (Compute.playerIsInBattle(player)) {
            countMap.compute(player, (k, v) -> v == null ? 20 : v + 20);
            Compute.sendEffectLastTime(player, this, 30, countMap.get(player), false);
        }
    }

    @Override
    public List<Attribute> getAttributes(Player player) {
        return List.of(
                new Attribute(Utils.maxMana, countMap.getOrDefault(player, 0))
        );
    }

    @Override
    public void tick(Player player) {
        if (Compute.playerIsInBattle(player, 600)) {
            countMap.put(player, 0);
            Compute.removeEffectLastTime(player, this);
        }
        super.tick(player);
    }
}

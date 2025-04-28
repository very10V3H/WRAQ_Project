package fun.wraq.series.overworld.sun;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.impl.onshoot.OnReleaseSkillCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.hud.Mana;
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

public class TearCurio extends WraqCurios implements OnReleaseSkillCurios, InCuriosOrEquipSlotAttributesModify {

    private final int tier;

    public TearCurio(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.maxMana.put(this, 250d);
        Utils.manaRecover.put(this, 10d);
    }

    private final int[] upperLimit = new int[]{360, 540, 720, 900};

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getFuncTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("法力积攒", hoverMainStyle()));
        components.add(Te.s(" 战斗状态下", ChatFormatting.RED, "，使用一次技能可以为你积攒",
                ComponentUtils.AttributeDescription.maxMana("20")));
        components.add(Te.s(" 至多叠加至",
                ComponentUtils.AttributeDescription.manaValue(String.valueOf(upperLimit[tier]))));
        components.add(Te.s(" 在脱离战斗后", "60s", ChatFormatting.AQUA, "，失去积攒的法力值"));
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
    public void onReleaseSkill(Player player) {
        if (Compute.playerIsInBattle(player)) {
            if (countMap.getOrDefault(player, 0) < upperLimit[tier]) {
                Mana.addOrCostPlayerMana(player, 20);
            }
            countMap.compute(player, (k, v) -> v == null ? 20 : Math.min(upperLimit[tier], v + 20));
            Compute.sendEffectLastTime(player, "item/tear_curio", 1200, countMap.get(player), false);
        }
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        return List.of(
                new Attribute(Utils.maxMana, countMap.getOrDefault(player, 0))
        );
    }

    @Override
    public void tick(Player player) {
        if (!Compute.playerIsInBattle(player, 1200)) {
            countMap.put(player, 0);
            Compute.removeEffectLastTime(player, this);
        }
        super.tick(player);
    }
}

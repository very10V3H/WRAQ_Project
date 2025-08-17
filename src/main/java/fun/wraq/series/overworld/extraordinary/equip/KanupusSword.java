package fun.wraq.series.overworld.extraordinary.equip;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter2.evoker.EvokerSceptre;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KanupusSword extends WraqSword implements ActiveItem, ForgeItem {

    public KanupusSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 3000d);
        Utils.manaDamage.put(this, 6000d);
        Utils.manaRecover.put(this, 20d);
        Utils.defencePenetration.put(this, 0.25);
        Utils.defencePenetration0.put(this, 50d);
        Utils.manaPenetration.put(this, 0.25);
        Utils.manaPenetration0.put(this, 50d);
        Utils.healthSteal.put(this, 0.08);
        Utils.manaHealthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.35);
        Utils.critDamage.put(this, 0.05);
        Utils.levelRequire.put(this, 225);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.MANA_TOWER_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("左神力", getMainStyle()));
        components.add(Te.s(" 普通近战攻击", CustomStyle.styleOfPower,
                "倍率降低50%,", CustomStyle.styleOfRed));
        components.add(Te.s(" 命中目标会附带", "50%普通箭矢攻击.", CustomStyle.styleOfFlexible));
        ComponentUtils.descriptionActive(components, Te.s("右神力", getMainStyle()));
        components.add(Te.s(" 释放一枚", "50%倍率法球,", CustomStyle.styleOfMana));
        components.add(Te.s(" 命中后附带", "50%普通近战攻击.", CustomStyle.styleOfPower));
        ComponentUtils.coolDownTimeDescription(components, 1);
        ComponentUtils.manaCostDescription(components, 25);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfManaTower();
    }

    @Override
    public void active(Player player) {
        EvokerSceptre evokerSceptre = (EvokerSceptre) ModItems.EVOKER_SWORD.get();
        evokerSceptre.shootManaArrow(player, 0.5, true);
        Compute.playerItemCoolDown(player, this, 1);
    }

    @Override
    public double manaCost(Player player) {
        return 25;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return List.of(
                new ItemStack(ModItems.COLLEGE_SENIOR_EQUIP_TICKET.get())
        );
    }
}

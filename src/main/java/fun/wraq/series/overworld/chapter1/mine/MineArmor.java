package fun.wraq.series.overworld.chapter1.mine;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.forge.ForgeAttributeEntry;
import fun.wraq.common.impl.forge.ForgeRandomEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.c1.NewC1Items;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MineArmor extends WraqArmor implements ForgeRandomEquip, ForgeItem {

    public MineArmor(ArmorMaterial armorMaterial, Type type) {
        super(armorMaterial, type, new Properties().rarity(CustomStyle.MineItalic));
        // 不注册静态属性，随机属性取代固定值
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMine;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    /* ===== ForgeItem ===== */

    @Override
    public List<ItemStack> forgeRecipe() {
        if (!getType().equals(Type.HELMET)) {
            return List.of();
        }
        return List.of(new ItemStack(NewC1Items.ARMOR_PIECE.get(), 1));
    }

    @Override
    public List<ForgeItem.Zone> forgeZones() {
        // 中心 (3978, 76, 3420) 附近 5 格
        return List.of(new ForgeItem.Zone(3983, 3425, 3973, 3415));
    }

    /* ===== ForgeRandomEquip ===== */

    @Override
    public List<ForgeAttributeEntry> getForgeAttributePool() {
        return List.of(
                ForgeRandomEquip.mandatory(StringUtils.RandomAttributes.defence, 5, 10),
                ForgeRandomEquip.mandatory(StringUtils.RandomAttributes.maxHealth, 200, 400),
                ForgeRandomEquip.mandatory(StringUtils.RandomAttributes.healthRecover, 5, 10),
                ForgeRandomEquip.prob(StringUtils.RandomAttributes.attackDamage, 0, 10, 0.2),
                ForgeRandomEquip.prob(StringUtils.RandomAttributes.critRate, 0, 0.1, 0.2),
                ForgeRandomEquip.prob(StringUtils.RandomAttributes.critDamage, 0, 0.1, 0.2),
                ForgeRandomEquip.prob(StringUtils.RandomAttributes.manaDamage, 0, 10, 0.2),
                ForgeRandomEquip.prob(StringUtils.RandomAttributes.manaPenetration0, 0, 8, 0.2)
        );
    }

    @Override
    public int minRandomAttributes() {
        return 0; // 不使用权重池模式
    }

    @Override
    public int maxRandomAttributes() {
        return 0; // 不使用权重池模式
    }

    @Override
    public boolean distinctRandomAttributes() {
        return true;
    }
}

package fun.wraq.series.instance.series.purple;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.common.impl.onhit.OnHitEffectPassiveEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PurpleIronSceptre extends WraqPassiveEquip implements PurpleIronCommon, OnHitEffectPassiveEquip, Decomposable {

    private final int tier;

    public PurpleIronSceptre(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.manaDamage.put(this, new double[]{200, 300, 400, 500}[tier]);
        Utils.xpLevelManaDamage.put(this, new double[]{2, 4, 6, 8}[tier]);
        Utils.maxMana.put(this, new double[]{25, 50, 75, 100}[tier]);
        Utils.manaPenetration0.put(this, new double[]{2, 3, 3, 4}[tier]);
        Utils.levelRequire.put(this, 120);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPurpleIron;
    }

    @Override
    public List<Component> getAdditionDescriptions() {
        List<Component> components = new ArrayList<>();
        PurpleIronCommon.setDescription(components, tier);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfPurpleIron();
    }

    @Override
    public Component getType() {
        return Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    @Override
    public int getPassiveTier() {
        return tier;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        PurpleIronCommon.onHit(player, mob, this);
    }

    @Override
    public ItemStack getProduct() {
        return new ItemStack(ModItems.PurpleIronBud2.get(), 2);
    }
}

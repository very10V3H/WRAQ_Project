package fun.wraq.series.instance.series.purple;

import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.common.impl.onhit.OnHitEffectPassiveEquip;
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

public class PurpleIronSceptre extends WraqPassiveEquip implements PurpleIronCommon, OnHitEffectPassiveEquip {

    private static final double[] ManaDamage = {
            200, 300, 400, 500
    };

    private static final double[] MaxMana = {
            25, 50, 75, 100
    };

    private static final double[] ManaPenetration0 = {
            2, 3, 3, 4
    };

    private final int tier;

    public PurpleIronSceptre(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.manaDamage.put(this, ManaDamage[tier]);
        Utils.maxMana.put(this, MaxMana[tier]);
        Utils.manaPenetration0.put(this, ManaPenetration0[tier]);
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
}

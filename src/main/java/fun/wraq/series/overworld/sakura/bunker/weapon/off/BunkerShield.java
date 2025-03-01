package fun.wraq.series.overworld.sakura.bunker.weapon.off;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.instance.series.warden.offhand.darkmoon.DarkMoonShield;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BunkerShield extends DarkMoonShield {

    public BunkerShield(Properties properties, Component type) {
        super(properties, type);
        Utils.percentAttackDamageEnhance.put(this, 0.05d);
        Utils.attackDamage.put(this, 0d);
        Utils.percentMaxHealthEnhance.put(this, 0.05d);
        Utils.critDamage.put(this, 0.12);
        Utils.expUp.put(this, 1.28);
        Utils.levelRequire.put(this, 225);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.BUNKER_STYLE;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfBunker();
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return BunkerOffHand.getForgeRecipe(WardenItems.DARK_MOON_SHIELD.get());
    }
}

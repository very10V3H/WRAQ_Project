package fun.wraq.series.overworld.sakura.bunker.weapon.off;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.warden.WardenItems;
import fun.wraq.series.instance.series.warden.offhand.darkmoon.DarkMoonBook;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BunkerBook extends DarkMoonBook {

    public BunkerBook(Properties properties, Component type) {
        super(properties, type);
        Utils.percentManaDamageEnhance.put(this, 0.05);
        Utils.manaDamage.put(this, 0d);
        Utils.manaPenetration.put(this, 0.08);
        Utils.coolDownDecrease.put(this, 0.3);
        Utils.maxMana.put(this, 120d);
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
        return BunkerOffHand.getForgeRecipe(WardenItems.DARK_MOON_BOOK.get());
    }
}

package fun.wraq.series.overworld.sakura.bunker.weapon.main;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerMainHand;
import fun.wraq.series.instance.series.harbinger.weapon.HarbingerSword;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BunkerSword extends HarbingerSword implements BunkerMainHand {

    public BunkerSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 2800d);
        Utils.defencePenetration0.put(this, 60d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.BUNKER_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return HarbingerMainHand.getCommonAdditionalComponents(stack, getMainStyle(), 2);
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfBunker();
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        return BunkerMainHand.getForgeRecipe(HarbingerItems.HARBINGER_SWORD.get());
    }
}

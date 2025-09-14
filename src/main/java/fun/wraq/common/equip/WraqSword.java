package fun.wraq.common.equip;

import fun.wraq.events.mob.loot.RandomLootEquip;
import fun.wraq.items.dev.equip.ManageEquip;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;

public abstract class WraqSword extends WraqMainHandEquip implements SwordAttribute {

    public WraqSword(Properties properties) {
        super(properties);
        if (!(this instanceof ManageEquip) && !(this instanceof RandomLootEquip)) {
            Display.swordList.add(this);
        }
    }

    @Override
    public Component getType() {
        return Component.literal("近战").withStyle(CustomStyle.styleOfPower);
    }
}

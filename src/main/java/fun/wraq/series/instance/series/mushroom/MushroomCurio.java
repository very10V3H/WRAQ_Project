package fun.wraq.series.instance.series.mushroom;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MushroomCurio extends WraqCurios {

    public MushroomCurio(Properties properties) {
        super(properties);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getFuncTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("蘑菇煲", hoverMainStyle()));
        components.add(Te.s(" 当", "饱食度", hoverMainStyle(), "不高于", "8", hoverMainStyle(), "时"));
        components.add(Te.s(" 消耗", ComponentUtils.AttributeDescription.health("50%当前"), "将",
                "饱食度", hoverMainStyle(), "恢复至", "18", hoverMainStyle()));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.MUSHROOM_STYLE;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfMushroom();
    }

    @Override
    public void tick(Player player) {
        if (player.getFoodData().getFoodLevel() <= 8) {
            player.setHealth(player.getHealth() * 0.5f);
            player.getFoodData().setFoodLevel(18);
            MySound.soundToPlayer(player, SoundEvents.GENERIC_EAT);
        }
        super.tick(player);
    }
}

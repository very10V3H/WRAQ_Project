package fun.wraq.process.system.channel;

import fun.wraq.common.equip.WraqMainHandEquip;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SakuraIndustrySceptre extends WraqMainHandEquip implements ActiveItem {

    public SakuraIndustrySceptre(Properties properties) {
        super(properties);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSakura;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionActive(components, Te.s("??位移", getMainStyle()));
        components.add(Te.s(" 在", "樱岛隐秘纬路", getMainStyle(), "中的", "节点方块", getMainStyle()));
        components.add(Te.s(" 上使用，可以", "穿行", getMainStyle(), "于节点之间"));
        ComponentUtils.coolDownTimeDescription(components, 2.5);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSakura();
    }

    @Override
    public Component getType() {
        return Te.s("仪器", CustomStyle.styleOfSakura);
    }

    @Override
    public void active(Player player) {
        SakuraIslandChannel.onPlayerUse(player);
        player.getCooldowns().addCooldown(this, Tick.s(50));
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}

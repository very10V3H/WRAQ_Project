package fun.wraq.process.system.channel;

import fun.wraq.common.equip.WraqMainHandEquip;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SakuraIndustrySceptre extends WraqMainHandEquip implements ActiveItem, UsageOrGetWayDescriptionItem {

    public SakuraIndustrySceptre(Properties properties) {
        super(properties);
        Utils.expUp.put(this, 1d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSakura;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionActive(components, Te.s("经纬位移", getMainStyle()));
        components.add(Te.s(" 在", "樱岛隐秘纬路", getMainStyle(), "中的", "节点方块", getMainStyle()));
        components.add(Te.s(" 上使用，可以", "穿行", getMainStyle(), "于节点之间"));
        ComponentUtils.coolDownTimeDescription(components, 2.5);
        components.add(Te.s("目前可以前往的地点:"));
        components.add(Te.s(" 北望村", CustomStyle.styleOfSunIsland));
        components.add(Te.s(" 沙岸村", CustomStyle.styleOfHusk));
        components.add(Te.s(" 鹰眼工厂", CustomStyle.styleOfHarbinger));
        components.add(Te.s(" 绯樱村", CustomStyle.styleOfSakura));
        components.add(Te.s(" 熔岩地堡", CustomStyle.BUNKER_STYLE));
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
        player.getCooldowns().addCooldown(this, 50);
        MySound.soundToPlayer(player, SoundEvents.PORTAL_TRIGGER);
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    @Override
    public List<Component> getWayDescription() {
        return List.of(
                Te.s("在", "旭升岛秘藏商人", CustomStyle.styleOfSunIsland, "处兑换")
        );
    }
}

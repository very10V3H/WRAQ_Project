package fun.wraq.series.overworld.chapter2.sky;

import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 天空之杖。
 * <p>
 * 与天空之锋 / 天空之隼同属天空城制式装备；模型暂时复用「澈源之杖」的贴图
 * （代码库中没有名为「本源之杖」的物品，后续换皮只需替换
 * {@code assets/vmd/textures/item/sky_sceptre.png} 并把模型 layer0 指回自身）。
 */
public class SkySceptre extends WraqSceptre {

    public SkySceptre(Properties properties) {
        super(properties);
        Utils.manaDamage.put(this, 120d);
        Utils.manaPenetration0.put(this, 6d);
        Utils.critRate.put(this, 0.25);
        Utils.manaRecover.put(this, 7d);
        Element.windElementValue.put(this, 0.8);
        Utils.levelRequire.put(this, 72);
    }

    @Override
    protected String getParticleType() {
        return StringUtils.ParticleTypes.Sky;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSky;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("澄空", getMainStyle()));
        components.add(Te.s(" 天空城制式法杖，轻便而稳定。"));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }
}

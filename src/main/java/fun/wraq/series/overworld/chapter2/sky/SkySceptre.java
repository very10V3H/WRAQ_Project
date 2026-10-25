package fun.wraq.series.overworld.chapter2.sky;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.forge.IgnoreForgeQuality;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.system.buff.BuffSystem;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
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
public class SkySceptre extends WraqSceptre implements OnHitEffectEquip, IgnoreForgeQuality {

    public SkySceptre(Properties properties) {
        super(properties);
        Utils.manaDamage.put(this, 30d);
        Utils.manaRecover.put(this, 5d);
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
        Compute.DescriptionPassive(components, Component.literal("澄空").withStyle(getMainStyle()));
        components.add(Te.s(" 法球", CustomStyle.styleOfMana, "命中后，获得持续3s的:"));
        components.add(Te.s(" 1.", getMainStyle(),
                ComponentUtils.AttributeDescription.movementSpeed("12%")));
        components.add(Te.s(" 2.", getMainStyle(),
                ComponentUtils.AttributeDescription.critRate("10%")));
        components.add(Te.s(" 3.", getMainStyle(),
                ComponentUtils.AttributeDescription.critDamage("20%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        StableAttributesModifier.addM(player, StableAttributesModifier.playerMovementSpeedModifier,
                "SkySceptre passive movementSpeed", 0.12, Tick.get() + 60);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerCritRateModifier,
                "SkySceptre passive critRate", 0.1, Tick.get() + 60);
        StableAttributesModifier.addM(player, StableAttributesModifier.playerCritDamageModifier,
                "SkySceptre passive critDamage", 0.2, Tick.get() + 60);
        BuffSystem.sendEffectLastTime(player, ModItems.SKY_SCEPTRE.get(), 60);
    }
}

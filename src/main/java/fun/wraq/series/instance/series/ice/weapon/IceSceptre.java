package fun.wraq.series.instance.series.ice.weapon;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.display.EnhancedForgedItem;
import fun.wraq.common.impl.display.ForgeItem;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.impl.onhit.OnPowerCauseDamageEquip;
import fun.wraq.common.registry.ModEntityType;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.ore.PickaxeItems;
import fun.wraq.projectiles.mana.ManaArrow;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IceSceptre extends WraqSceptre implements OnHitEffectEquip, OnPowerCauseDamageEquip, ForgeItem, EnhancedForgedItem {

    private final int tier;
    public IceSceptre(Properties properties, int tier) {
        super(properties);
        Utils.manaDamage.put(this, 1400d);
        Utils.manaRecover.put(this, 20d);
        Utils.coolDownDecrease.put(this, 0.25);
        Utils.manaPenetration0.put(this, 21d);
        Element.iceElementValue.put(this, 1.25);
        Utils.levelRequire.put(this, 135);
        this.tier = tier;
    }

    @Override
    protected EntityType<ManaArrow> getArrowType() {
        return ModEntityType.NEW_ARROW_WORLD.get();
    }

    @Override
    protected String getParticleType() {
        return StringUtils.ParticleTypes.Sky;
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfIce;
        ComponentUtils.descriptionPassive(components, Component.literal("迸晶裂玉").withStyle(style));
        components.add(Component.literal(" 攻击").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("将对目标造成持续1s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("减速").withStyle(CustomStyle.styleOfIce)));
        Compute.DescriptionPassive(components, Component.literal("凝结爆裂").withStyle(CustomStyle.styleOfIce));
        components.add(Te.m(" 对处于").
                append(Te.m("减速", CustomStyle.styleOfIce)).
                append(Te.m("状态的目标造成")).
                append(Te.m("伤害", ChatFormatting.BLUE)).
                append(Te.m("后，施加一层")).
                append(Te.m("寒冰", CustomStyle.styleOfIce)));
        components.add(Te.m(" 当目标的").
                append(Te.m("寒冰", CustomStyle.styleOfIce)).
                append(Te.m("达到8层后，下次攻击会引爆所有层数")));
        components.add(Te.m(" 对目标造成").
                append(ComponentUtils.getAutoAdaptDamageDescription("200%")));
        if (tier == 0) {
            components.add(Te.s(" 锐化后", CustomStyle.styleOfWorld, "可激活",
                    "穿透", CustomStyle.styleOfMana, "/", "暴击", CustomStyle.styleOfLucky, "效果"));
        } else {
            components.add(Te.m(" 并击碎目标").
                    append(ComponentUtils.AttributeDescription.manaDefence("25%")).
                    append(Te.m("，持续5s")));
            components.add(Te.s(" 自身获得", ComponentUtils.AttributeDescription.critRate("30%")
                    , "，持续5s"));
        }
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        IceWeaponHelper.onHit(player, mob);
        IceWeaponHelper.onCritHit(player, mob, ModItems.ICE_SCEPTRE.get(), 8, tier);
    }

    @Override
    public void onCauseDamage(Player player, Mob mob) {
        IceWeaponHelper.onHit(player, mob);
        IceWeaponHelper.onCritHit(player, mob, ModItems.ICE_SCEPTRE.get(), 8, tier);
    }

    @Override
    public int getEnhanceTier() {
        return tier;
    }

    @Override
    public List<ItemStack> forgeRecipe() {
        if (tier == 0) {
            return List.of(
                    new ItemStack(ModItems.ICE_COMPLETE_GEM.get(), 8),
                    new ItemStack(ModItems.GOLD_COIN.get(), 256),
                    new ItemStack(PickaxeItems.TINKER_GOLD.get(), 8)
            );
        }
        return List.of(
                new ItemStack(ModItems.ICE_SCEPTRE.get()),
                new ItemStack(ModItems.COMPLETE_GEM.get(), 6),
                new ItemStack(ModItems.REPUTATION_MEDAL.get(), 48),
                new ItemStack(ModItems.WORLD_SOUL_3.get(), 2)
        );
    }
}

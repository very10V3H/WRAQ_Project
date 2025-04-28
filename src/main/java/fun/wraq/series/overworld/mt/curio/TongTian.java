package fun.wraq.series.overworld.mt.curio;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onhit.OnHitDamageInfluenceCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TongTian extends WraqCurios implements OnHitDamageInfluenceCurios {

    public TongTian(Properties properties) {
        super(properties);
        Utils.commonDamageEnhance.put(this, 0.05);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("通天", hoverMainStyle()));
        components.add(Te.s(" 提升", "跳跃高度", CustomStyle.styleOfLife));
        components.add(Te.s(" 当你处于", "空中", CustomStyle.styleOfSky, "，获得",
                ComponentUtils.getCommonDamageEnhance("30%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.MANA_TOWER_STYLE;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfManaTower();
    }

    @Override
    public double onHitDamageInfluence(Player player, Mob mob) {
        if (!player.onGround() && !player.isInWater()) {
            return 0.3;
        }
        return 0;
    }

    @Override
    public void tick(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.JUMP, 400, 4));
        if (!player.onGround() && !player.isInWater()) {
            Compute.sendEffectLastTime(player, this, 0, true);
        } else {
            Compute.removeEffectLastTime(player, this);
        }
        super.tick(player);
    }
}

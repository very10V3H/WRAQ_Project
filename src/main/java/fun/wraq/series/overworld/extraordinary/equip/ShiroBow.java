package fun.wraq.series.overworld.extraordinary.equip;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onshoot.OnShootArrowCurios;
import fun.wraq.common.impl.onshoot.OnShootArrowEquip;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.core.bow.MyArrowHitBlock;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.skill.BowSkillTree;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.quiver.WraqQuiver;
import fun.wraq.series.overworld.divine.equip.weapon.DivineWeaponCommon;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShiroBow extends WraqBow {

    public ShiroBow(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 3000d);
        Utils.defencePenetration.put(this, 0.25);
        Utils.defencePenetration0.put(this, 50d);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.05);
        Utils.movementSpeedCommon.put(this, 0.2);
        Element.windElementValue.put(this, 1.5);
        Utils.levelRequire.put(this, 225);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.SHIRO_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("灵弹!", getMainStyle()));
        components.add(Te.s(" 射击时，", "不会释放箭矢", getMainStyle()));
        components.add(Te.s(" 转而对前方线形范围的所有敌人造成一次", "箭矢伤害", CustomStyle.styleOfFlexible));
        components.add(Te.s(" 线形范围:以视角射线为中轴线的半径1格圆柱", getMainStyle()));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfShiro();
    }

    @Override
    public void shoot(Player player, double rate, boolean mainShoot, boolean certainlyCritical, boolean noGravity,
                      MyArrowHitBlock myArrowHitBlock, MyArrowHitBlock myArrowHitBlockEntity) {
        rate += BowSkillTree.skillIndex14(player);
        if (mainShoot) {
            OnShootArrowCurios.shoot(player);
            OnShootArrowEquip.shoot(player);
            Damage.onPlayerReleaseNormalAttack(player);
            WraqQuiver.shootQuiverExArrow(player);
        }
        Set<Mob> set = Compute.getPlayerRayMobList(player, 0.5, 1, 40);
        double finalRate = rate;
        set.forEach(mob -> {
            MyArrow.causeDamage(player, mob, finalRate);
            DivineWeaponCommon.onKillMob(player, mob);
        });
        MySound.soundToNearPlayer(player, SoundEvents.ARROW_SHOOT);
    }
}

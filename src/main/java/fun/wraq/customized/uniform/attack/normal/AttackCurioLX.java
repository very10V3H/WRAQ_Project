package fun.wraq.customized.uniform.attack.normal;

import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.SwordAttribute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.uniform.UnCommonUniform;
import fun.wraq.process.func.damage.Dot;
import fun.wraq.process.system.element.RainbowCrystal;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AttackCurioLX extends WraqAttackUniformCurios implements OnHitEffectCurios, UnCommonUniform {
    public AttackCurioLX(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("龙吟", hoverMainStyle()));
        components.add(Te.s(" 普通近战攻击", hoverMainStyle(), "会对目标施加"));
        components.add(Te.s(" 持续2s的、可叠加的", "流血效果", hoverMainStyle()));
        components.add(Te.s(" ", ComponentUtils.getAttackDamageDotDescription(2, 8, "8%")));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return RainbowCrystal.rainBowNameFourChar("隐龙之吟");
    }

    @Override
    public String getName() {
        return "LXYZO";
    }

    @Override
    public void onHit(Player player, Mob mob) {
        if (SwordAttribute.isHandling(player)) {
            Dot.addDotOnMob(mob, new Dot(1, PlayerAttributes.attackDamage(player) * 0.08, 4,
                    Tick.get() + Tick.s(2), Name.get(player), true, null));
        }
    }
}

package fun.wraq.process.system.skill.skillv2.bow;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.process.system.skill.skillv2.SkillV2AllowInterruptNormalAttack;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class BowNewSkillBase1_0Allow extends SkillV2BaseSkill implements SkillV2AllowInterruptNormalAttack {

    public BowNewSkillBase1_0Allow(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.bowNewSkillBase1_0, 8, 8, player, 1
        ) {
            @Override
            public void trig() {
                Item item = player.getMainHandItem().getItem();
                if (item instanceof WraqBow wraqBow) {
                    wraqBow.shoot(player, (2.5 + skillLevel * 0.15) * (1 + getEnhanceRate(player)),
                            true, true, true);
                }
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("向前方射出一支",
                getRateDescription(2.5, 0.15, level), CustomStyle.styleOfFlexible, "伤害的箭矢"));
        components.add(Te.s("这支箭矢", "不会下坠", CustomStyle.styleOfMoon,
                "且", "必定暴击", CustomStyle.styleOfPower));
        components.add(Te.s("施法前摇与后摇收益于",
                ComponentUtils.AttributeDescription.getAttackSpeed("")));
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 5;
    }
}

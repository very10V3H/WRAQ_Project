package fun.wraq.process.system.skill.skillv2.bow;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class BowNewSkillBase1_0 extends SkillV2BaseSkill {

    public BowNewSkillBase1_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        DelayOperationWithAnimation.beforeReleaseSkill(player);
        int skillLevel = getPlayerSkillLevelBySkillV2(player, this);
        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.bowNewSkillBase1_0, Tick.get() + 8, player
        ) {
            @Override
            public void trig() {
                Item item = player.getMainHandItem().getItem();
                if (item instanceof WraqBow wraqBow) {
                    wraqBow.shoot(player, 2 + skillLevel * 0.1, true, true, true);
                }
            }
        });
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("向前方射出一支",
                getRateDescription(2, 0.1, level), CustomStyle.styleOfFlexible, "伤害的箭矢"));
        components.add(Te.s("这支箭矢", "不会下坠", CustomStyle.styleOfMoon,
                "且", "必定暴击", CustomStyle.styleOfPower));
        return components;
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 5;
    }
}

package fun.wraq.process.system.skill.skillv2.bow;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2PassiveSkill;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class BowNewSkillPassive0 extends SkillV2PassiveSkill {

    public BowNewSkillPassive0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        components.add(Te.s("普攻命中目标，将使目标受到",
                "任意来源", CustomStyle.styleOfLucky, "的伤害提升", "1%"));
        components.add(Te.s("持续5s，至多可叠加至", (10 + level) + "层", CustomStyle.styleOfFlexible));
        components.add(Te.s("对此技能的倍率提升会提高最大层数", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        return components;
    }

    public static void onArrowHit(Player player, Mob mob) {
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 0);
        if (skillV2 instanceof BowNewSkillPassive0) {
            StableTierAttributeModifier.addM(mob, StableTierAttributeModifier.monsterWithstandDamageEnhance,
                    "bowNewSkillPassive0WithstandDamageEnhance", 0.01,
                    Tick.get() + 100,
                    (int) ((10 + skillV2.getPlayerSkillLevel(player)) * (1 + skillV2.getEnhanceRate(player))),
                    skillV2.getTexture1Url());
        }
    }
}

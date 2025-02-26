package fun.wraq.process.system.skill.skillv2.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModSounds;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.process.system.skill.skillv2.SkillV2BaseSkill;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class BowNewSkillBase3_0 extends SkillV2BaseSkill {

    public BowNewSkillBase3_0(Component name, int cooldownTick, int manaCost, int professionType, int skillType, int serial) {
        super(name, cooldownTick, manaCost, professionType, skillType, serial);
    }

    @Override
    protected void releaseOperation(Player player) {
        MySound.soundToNearPlayer(player, ModSounds.Rolling.get());
        DelayOperationWithAnimation.beforeReleaseSkill(player);
        Compute.sendForwardMotionPacketToPlayer(player, 1 + PlayerAttributes.movementSpeedCurrent(player));
        int skillLevel = getPlayerSkillLevel(player);
        effectExpiredTickMap.put(player, Tick.get() + Tick.s(5) + 10 * skillLevel);
        Compute.sendEffectLastTime(player, getTexture1Url(),
                Tick.s(5) + 10 * skillLevel, 0, false);
    }

    public static Map<Player, Integer> effectExpiredTickMap = new WeakHashMap<>();

    @Override
    protected List<Component> getSkillDescription(int level) {
        List<Component> components = new ArrayList<>();
        String lastSeconds = String.format("%.1f", 5 + level * 0.5);
        components.add(Te.s("向前突进，并获得持续", lastSeconds + "s", CustomStyle.styleOfFlexible,
                "的箭矢穿透效果"));
        components.add(Te.s("箭矢", CustomStyle.styleOfFlexible,
                "每穿过一个敌人，会提升", "33%伤害", CustomStyle.styleOfPower));
        components.add(Te.s("箭矢", CustomStyle.styleOfFlexible, "造成",
                "暴击", CustomStyle.styleOfPower, "将减少", "1s剩余冷却时间", CustomStyle.styleOfWorld));
        components.add(Te.s("位移的距离收益于玩家当前移动速度", ChatFormatting.ITALIC, ChatFormatting.GRAY));
        return components;
    }

    public static void onCritHit(Player player) {
        SkillV2 skillV2 = getPlayerCurrentSkillByType(player, 3);
        if (skillV2 instanceof BowNewSkillBase3_0) {
            SkillV2.decreaseSkillCooldownTick(player, skillV2, Tick.s(1));
        }
    }

    @Override
    protected int getEachLevelExManaCost() {
        return 5;
    }
}

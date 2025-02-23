package fun.wraq.series.overworld.divine.equip.boss;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class DivineKnife extends WraqSword implements OnHitEffectEquip {

    public DivineKnife(Properties properties) {
        super(properties);
        Utils.healthSteal.put(this, 0.25);
        Utils.elementStrength.put(this, 0.25);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.DIVINE_STYLE;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("神圣切割", getMainStyle()));
        components.add(Te.s(" 命中目标时，将对其使用", "圣光透射", getMainStyle()));
        components.add(Te.s(" 使目标受到任意伤害来源的伤害提升", "8%", getMainStyle()));
        components.add(Te.s(" 多个玩家对一个目标造成的效果可以叠加", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        return components;
    }

    public static Map<Mob, Integer> mobCountMap = new HashMap<>();
    public static Map<Mob, Set<String>> mobToPlayerSetMap = new HashMap<>();

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfDivine();
    }

    @Override
    public void onHit(Player player, Mob mob) {
        mobCountMap.entrySet().removeIf(entry -> entry.getKey().isDeadOrDying());
        mobToPlayerSetMap.entrySet().removeIf(entry -> entry.getKey().isDeadOrDying());
        if (!mobToPlayerSetMap.containsKey(mob)) {
            mobToPlayerSetMap.put(mob, new HashSet<>());
        }
        Set<String> set = mobToPlayerSetMap.get(mob);
        if (!set.contains(Name.get(player))) {
            set.add(Name.get(player));
            mobCountMap.compute(mob, (k, v) -> v == null ? 1 : v + 1);
            Compute.sendMobEffectHudToNearPlayer(mob, this, "DivineKnifeWithstandDamageEnhance",
                    8888, mobCountMap.get(mob), true);
        }
    }
}

/** AI-Generated, 2026-05-10 */
package fun.wraq.common.util;

import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.customized.uniform.attack.normal.AttackCurios5;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.system.element.equipAndCurios.lifeElement.LifeElementSword;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.holy.ice.FrostInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class HealUtil {

    public static Map<String, Integer> nextAllowSendMSGTickMap = new HashMap<>();

    public static void playerHeal(Player player, double num) {
        if (num <= 0) return;
        double healNum = num * (PlayerAttributes.getHealingAmplification(player));
        if (healNum < 0) {
            return;
        }
        if (AttackCurios5.onHealHealthRecover(player, healNum)) return;
        healNum = Math.min(healNum, player.getMaxHealth() - player.getHealth());
        LifeElementSword.StoreToList(player, healNum);
        player.heal((float) healNum);
    }

    public static void mobHeal(Mob mob, double num) {
        if (num < 0) return;
        double healNum = num * (MobAttributes.getMobHealAmplifier(mob));
        healNum = Math.min(healNum, mob.getMaxHealth() - mob.getHealth());
        mob.heal((float) healNum);
    }

    public static void mobHealthRecover(Mob mob, double percent) {
        if (mob.tickCount % 20 == 5) {
            mobHeal(mob, mob.getMaxHealth() * percent);
        }
    }

    public static void healByHealthSteal(Player player, Mob mob, double damage) {
        double rate = PlayerAttributes.healthSteal(player);
        double distance = player.distanceTo(mob);
        if (distance > 5) {
            rate *= (1 - (Math.min(20, distance) - 5) / 15);
        }
        if (MobSpawn.getMobOriginName(mob).equals(FrostInstance.mobName)) {
            rate = Math.max(0, rate - 0.2);
        }
        double healNum = damage * rate * 0.1;
        if (healNum > player.getMaxHealth() * 0.02) {
            healNum = Math.min(healNum, player.getMaxHealth() * 0.02);
            if (nextAllowSendMSGTickMap.getOrDefault(Name.get(player), 0) < Tick.get()) {
                MessageUtil.sendFormatMSG(player, Te.s("治疗承受", CustomStyle.styleOfHealth),
                        Te.s("单次生命偷取的数额将不会超过",
                                ComponentUtils.AttributeDescription.maxHealth("2%")));
                nextAllowSendMSGTickMap.put(Name.get(player), Tick.get() + Tick.min(10));
            }
        }
        playerHeal(player, healNum);
    }
}

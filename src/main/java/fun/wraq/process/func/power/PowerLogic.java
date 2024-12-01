package fun.wraq.process.func.power;

import fun.wraq.events.mob.instance.instances.element.WardenInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Map;
import java.util.WeakHashMap;

import static fun.wraq.common.Compute.ChargingModule;
import static fun.wraq.core.ManaAttackModule.ManaSkill12Attack;
import static fun.wraq.core.ManaAttackModule.ManaSkill13Attack;

public class PowerLogic {

    public static WeakHashMap<Player, WraqPower> playerLastTimeReleasePower = new WeakHashMap<>();

    public static WeakHashMap<Player, Map<Item, Integer>> playerPowerCoolDownRecord = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> playerLastTimeReleasePowerCoolDownTime = new WeakHashMap<>();
    public static WeakHashMap<Player, Double> playerLastTimeReleasePowerManaCost = new WeakHashMap<>();

    public static void ReleaseLastTime(Player player) {
        if (playerLastTimeReleasePower.containsKey(player)) {
            playerLastTimeReleasePower.get(player).release(player);
        }
    }

    public static void PlayerReleasePowerType(Player player, int index) {

    }

    public static void PlayerPowerEffectToMob(Player player, Mob mob) {

    }

    public static void playerReleasePower(Player player) {
        CompoundTag data = player.getPersistentData();

        ChargingModule(data, player);
        ManaSkill12Attack(data, player); // 盈能攻击（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将造成额外200%伤害，并在以目标为中心的范围内造成100%伤害）
        ManaSkill13Attack(data, player); // 法术收集（移动、攻击以及受到攻击将会获得充能，当充能满时，下一次攻击将基于目标周围实体数量提供至多1000%的范围伤害，并回复自身50%的法力值）
        WardenInstance.onPlayerNormalAttackOrReleasePower(player);
    }
}

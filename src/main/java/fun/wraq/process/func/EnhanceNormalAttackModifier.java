package fun.wraq.process.func;

import com.google.common.util.concurrent.AtomicDouble;
import fun.wraq.process.func.EnhanceNormalAttack;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 强化普攻 可选强化数值 可选普攻后造成效果<br/>
 * status 为 true 时，才会激活普攻数值修正<br/>
 * 填写 enhanceNormalAttack参数时，才会激活普攻后效果
*/
public class EnhanceNormalAttackModifier {

    private final String tag;
    private boolean status; // status为true时，将会使用数值参数对本次强化普攻的数值进行修正
    private double enhanceRate; // 若status为true时才生效
    private fun.wraq.process.func.EnhanceNormalAttack enhanceNormalAttack;
    private final int type; // 0 - sword 1 - bow 2 - mana

    public EnhanceNormalAttackModifier(String tag, boolean status, double enhanceRate, int type,
                                       fun.wraq.process.func.EnhanceNormalAttack enhanceNormalAttack) {
        this.tag = tag;
        this.status = status;
        this.enhanceRate = enhanceRate;
        this.enhanceNormalAttack = enhanceNormalAttack;
        this.type = type;
    }

    public EnhanceNormalAttackModifier(String tag, double enhanceRate, int type) {
        this.tag = tag;
        this.status = true;
        this.enhanceRate = enhanceRate;
        this.type = type;
    }

    public EnhanceNormalAttackModifier(String tag, int type, EnhanceNormalAttack enhanceNormalAttack) {
        this.tag = tag;
        status = false;
        enhanceRate = 0;
        this.enhanceNormalAttack = enhanceNormalAttack;
        this.type = type;
    }

    public static Map<Player, List<EnhanceNormalAttackModifier>> playerMap = new HashMap<>();

    public static List<EnhanceNormalAttackModifier> getModifierList(Player player) {
        if (!playerMap.containsKey(player)) playerMap.put(player, new ArrayList<>());
        return playerMap.get(player);
    }

    public static void addModifier(Player player, EnhanceNormalAttackModifier enhanceNormalAttackModifier) {
        List<EnhanceNormalAttackModifier> list = getModifierList(player);
        if (list.stream().noneMatch(modifier -> modifier.tag.equals(enhanceNormalAttackModifier.tag))) {
            list.add(enhanceNormalAttackModifier);
        }
    }

    public static double onHitDamageEnhance(Player player, int type) {
        List<EnhanceNormalAttackModifier> list = getModifierList(player);
        AtomicDouble atomicDouble = new AtomicDouble();
        list.forEach(modifier -> {
            if (modifier.status && modifier.type == type) {
                atomicDouble.addAndGet(modifier.enhanceRate);
            }
        });
        return atomicDouble.get();
    }

    public static void onHitEffect(Player player, Mob mob, int type) {
        List<EnhanceNormalAttackModifier> list = getModifierList(player);
        List<EnhanceNormalAttackModifier> removeList = new ArrayList<>();
        list.forEach(modifier -> {
            if (modifier.type == type) {
                removeList.add(modifier);
                if (modifier.enhanceNormalAttack != null) {
                    modifier.enhanceNormalAttack.hit(player, mob);
                }
            }
        });
        list.removeAll(removeList);
    }
}

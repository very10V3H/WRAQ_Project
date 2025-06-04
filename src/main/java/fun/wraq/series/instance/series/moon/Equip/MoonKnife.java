package fun.wraq.series.instance.series.moon.Equip;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnArrowHitEffectCurios;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.*;

public class MoonKnife extends WraqOffHandItem implements OnArrowHitEffectCurios {

    public static Map<String, Mob> PlayerMoonKnifeMap = new HashMap<>();
    public static WeakHashMap<Player, Integer> PlayerMoonKnifeCountMap = new WeakHashMap<>();

    public MoonKnife() {
        super(new Properties().rarity(CustomStyle.MoonItalic).stacksTo(1),
                Te.s("玉钩", CustomStyle.styleOfMoon));
        Utils.attackDamage.put(this, 177d);
        Utils.defencePenetration0.put(this, 6d);
        Utils.critRate.put(this, 0.17);
        Utils.critDamage.put(this, 0.07);
        Utils.expUp.put(this, 0.77);
        Utils.levelRequire.put(this, 160);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("清辉夜凝").withStyle(style));
        components.add(Component.literal("箭矢攻击").withStyle(CustomStyle.styleOfFlexible).
                append(Component.literal("将会标记一个敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("当你对该敌人造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("7").withStyle(style)).
                append(Component.literal("次伤害后，").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 你将会引爆标记，并对周围所有目标造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getAutoAdaptTrueDamageDescription("1400%")));
        components.add(Component.literal(" - 引爆标记后，你将获得持续3s的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamage("12%总")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoon();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static WeakHashMap<Player, Integer> playerDamageEnhanceTickMap = new WeakHashMap<>();

    public static double damageEnhance(Player player) {
        int TickCount = Tick.get();
        if (playerDamageEnhanceTickMap.containsKey(player) && playerDamageEnhanceTickMap.get(player) > TickCount) {
            return 0.12;
        }
        return 0;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        int TickCount = Tick.get();
        if (PlayerMoonKnifeMap.containsKey(Name.get(player)) && !PlayerMoonKnifeMap.get(Name.get(player)).equals(mob)) {
            Mob oldMob = PlayerMoonKnifeMap.get(Name.get(player));
            oldMob.removeEffect(MobEffects.GLOWING);
            PlayerMoonKnifeCountMap.put(player, 0);
            Compute.removeMobEffectHudToNearPlayer(oldMob, ModItems.MOON_SOUL.get(), "MoonKnifeCount");
        }
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 88888, 1, false, false));
        PlayerMoonKnifeMap.put(Name.get(player), mob);
        int count = PlayerMoonKnifeCountMap.getOrDefault(player, 0);
        PlayerMoonKnifeCountMap.put(player, ++count);
        if (count == 7) {
            PlayerMoonKnifeCountMap.put(player, 0);
            List<Mob> mobList = mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 15, 15, 15));
            mobList.forEach(mob1 -> {
                if (mob1.distanceTo(mob) < 6) {
                    Damage.causeAutoAdaptionRateDamageToMob(player, mob1, 14, true);
                }
            });
            playerDamageEnhanceTickMap.put(player, TickCount + 60);
            Compute.sendEffectLastTime(player, ModItems.MOON_SOUL.get(), 60);
            Compute.removeMobEffectHudToNearPlayer(mob, ModItems.MOON_SOUL.get(), "MoonKnifeCount");
        } else {
            Compute.sendMobEffectHudToNearPlayer(mob, ModItems.MOON_SOUL.get(), "MoonKnifeCount", 8888, count, true);
        }
    }
}

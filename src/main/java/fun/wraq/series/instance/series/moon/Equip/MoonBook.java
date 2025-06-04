package fun.wraq.series.instance.series.moon.Equip;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
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

import java.util.*;

public class MoonBook extends WraqOffHandItem implements OnHitEffectEquip {

    public static Map<String, Mob> PlayerMoonBookMap = new HashMap<>();
    public static WeakHashMap<Player, Integer> PlayerMoonBookCountMap = new WeakHashMap<>();

    public MoonBook() {
        super(new Properties().rarity(CustomStyle.MoonItalic).stacksTo(1),
                Te.s("魔导书", CustomStyle.styleOfMana));
        Utils.manaDamage.put(this, 277d);
        Utils.manaPenetration0.put(this, 7.7d);
        Utils.maxMana.put(this, 77d);
        Utils.coolDownDecrease.put(this, 0.17);
        Utils.expUp.put(this, 0.77);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
        Utils.levelRequire.put(this, 160);
    }

    private final Style style = CustomStyle.styleOfGold;

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("折镜").withStyle(style));
        components.add(Component.literal("法球攻击").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("将会标记一个敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("当你对该敌人造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("7").withStyle(style)).
                append(Component.literal("次伤害后，").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 你将会引爆标记，并对该敌人造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getAutoAdaptTrueDamageDescription("1400%")));
        components.add(Component.literal(" - 引爆标记后，你将获得持续3s的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("12%总")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoon();
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
        if (PlayerMoonBookMap.containsKey(Name.get(player)) && !PlayerMoonBookMap.get(Name.get(player)).equals(mob)) {
            Mob oldMob = PlayerMoonBookMap.get(Name.get(player));
            oldMob.removeEffect(MobEffects.GLOWING);
            PlayerMoonBookCountMap.put(player, 0);
            Compute.removeMobEffectHudToNearPlayer(oldMob, ModItems.MOON_SOUL.get(), "MoonBookCount");
        }
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 88888, 1, false, false));
        PlayerMoonBookMap.put(Name.get(player), mob);
        int count = PlayerMoonBookCountMap.getOrDefault(player, 0);
        PlayerMoonBookCountMap.put(player, ++count);
        if (count == 7) {
            count = 0;
            PlayerMoonBookCountMap.put(player, count);
            playerDamageEnhanceTickMap.put(player, TickCount + 60);
            Compute.sendEffectLastTime(player, ModItems.MOON_SOUL.get().getDefaultInstance(), 60);
            Compute.removeMobEffectHudToNearPlayer(mob, ModItems.MOON_SOUL.get(), "MoonBookCount");
            Damage.causeAutoAdaptionRateDamageToMob(player, mob, 14, true);
        } else {
            Compute.sendMobEffectHudToNearPlayer(mob, ModItems.MOON_SOUL.get(), "MoonBookCount", 8888, count, true);
        }
    }
}

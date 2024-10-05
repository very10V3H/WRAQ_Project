package fun.wraq.series.instance.series.moon.Equip;

import fun.wraq.common.Compute;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.WeakHashMap;

public class MoonBook extends Item implements OnHitEffectEquip {

    public static WeakHashMap<Player, Mob> PlayerMoonBookMap = new WeakHashMap<>();
    public static WeakHashMap<Player, Integer> PlayerMoonBookCountMap = new WeakHashMap<>();

    public MoonBook() {
        super(new Properties().rarity(CustomStyle.MoonItalic).stacksTo(1));
        Utils.manaDamage.put(this, 577d);
        Utils.manaPenetration0.put(this, 777d);
        Utils.maxMana.put(this, 77d);
        Utils.movementSpeedWithoutBattle.put(this, 1.27);
        Utils.coolDownDecrease.put(this, 0.27);
        Utils.expUp.put(this, 1.57);
        Utils.offHandTag.put(this, 1d);
        Utils.weaponList.add(this);
    }

    private final Style style = CustomStyle.styleOfGold;

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.setHoverName(Component.literal("天玉明镜").withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(CustomStyle.styleOfMana)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("折镜").withStyle(style));
        components.add(Component.literal("法球攻击").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("将会标记一个敌人").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("当你对该敌人造成").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("7").withStyle(style)).
                append(Component.literal("次伤害后，").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 你将会引爆标记，并对该敌人造成").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getAutoAdaptIgnoreDefenceDamageDescription("1400%")));
        components.add(Component.literal(" - 引爆标记后，你将获得持续3s的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("12%总")));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfMoon(components);
        super.appendHoverText(stack, level, components, flag);
    }

    public static WeakHashMap<Player, Integer> playerDamageEnhanceTickMap = new WeakHashMap<>();

    public static double damageEnhance(Player player) {
        int TickCount = player.getServer().getTickCount();
        if (playerDamageEnhanceTickMap.containsKey(player) && playerDamageEnhanceTickMap.get(player) > TickCount) {
            return 0.12;
        }
        return 0;
    }

    @Override
    public void onHit(Player player, Mob mob) {
        int TickCount = player.getServer().getTickCount();
        if (PlayerMoonBookMap.containsKey(player) && !PlayerMoonBookMap.get(player).equals(mob)) {
            Mob oldMob = PlayerMoonBookMap.get(player);
            oldMob.removeEffect(MobEffects.GLOWING);
            PlayerMoonBookCountMap.put(player, 0);
            Compute.removeMobEffectHudToNearPlayer(oldMob, ModItems.MoonSoul.get(), "MoonBookCount");
        }
        mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 88888, 1, false, false));
        PlayerMoonBookMap.put(player, mob);
        int count = PlayerMoonBookCountMap.getOrDefault(player, 0);
        PlayerMoonBookCountMap.put(player, ++count);
        if (count == 7) {
            count = 0;
            PlayerMoonBookCountMap.put(player, count);
            playerDamageEnhanceTickMap.put(player, TickCount + 60);
            Compute.sendEffectLastTime(player, ModItems.MoonSoul.get().getDefaultInstance(), 60);
            Compute.removeMobEffectHudToNearPlayer(mob, ModItems.MoonSoul.get(), "MoonBookCount");
            Damage.causeAutoAdaptionRateDamageToMob(player, mob, 14, true);
        } else {
            Compute.sendMobEffectHudToNearPlayer(mob, ModItems.MoonSoul.get(), "MoonBookCount", 8888, count, true);
        }
    }
}

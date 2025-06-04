package fun.wraq.series.instance.series.moon;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onshoot.OnShootArrowCurios;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.WeakHashMap;

public class MoonCurios extends Item implements ICurioItem, OnShootArrowCurios {

    public MoonCurios(Properties p_41383_) {
        super(p_41383_);
        Utils.curiosList.add(this);
        Display.souvenirsList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Style style = CustomStyle.styleOfMoon1;
        CompoundTag data = stack.getOrCreateTagElement(Utils.MOD_ID);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        Compute.DescriptionPassive(components, Component.literal("朔望轮转").withStyle(style));
        components.add(Component.literal(" 每过10s，使你的下次攻击").withStyle(ChatFormatting.WHITE).
                append(Component.literal("附带").withStyle(style)).
                append(Component.literal("目标").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.maxHealth("1%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        components.add(Component.literal(" - 以此致敬维瑞阿契的开拓者 - " + data.getString(StringUtils.MoonCuriosPlayerName)).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.AQUA));
        components.add(ComponentUtils.getSuffixOfSouvenirs());
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    public static boolean isOn(Player player) {
        return Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(ModItems.MOON_CURIOS.get());
    }

    public static double getExCommonDamageEnhance(Player player) {
        return isOn(player) ? 5 : 0;
    }

    public static double getElementValueEnhance(Player player) {
        return isOn(player) ? 1 : 0;
    }

    public static double getDefencePenetration(Player player) {
        return isOn(player) ? 0.2 : 0;
    }

    public static double getCritDamage(Player player) {
        return isOn(player) ? 0.5 : 0;
    }

    @Override
    public void onShoot(Player player) {
        WraqQuiver.batchAddExShoot(player, 1, 1);
    }

    public static WeakHashMap<Player, Integer> passiveCoolDownMap = new WeakHashMap<>();

    public static double Passive(Player player, Mob mob) {
        if (Compute.CuriosAttribute.getDistinctCuriosSet(player).contains(ModItems.MOON_CURIOS.get())) {
            int TickCount = Tick.get();
            if (!passiveCoolDownMap.containsKey(player) || TickCount > passiveCoolDownMap.get(player)) {
                passiveCoolDownMap.put(player, TickCount + 200);
                Compute.sendCoolDownTime(player, ModItems.MOON_CURIOS.get().getDefaultInstance(), 200);
                Shield.providePlayerShield(player, 200, player.experienceLevel * 20);
                return mob.getMaxHealth() * 0.01;
            }
        }
        return 0;
    }

    public static void MonthCuriosAttributeProvide(Player player, ItemStack curios) {
        CompoundTag data = curios.getOrCreateTagElement(Utils.MOD_ID);
        double[] AttributeBase = {
                2, 8, 2, 8
        };
        int baseValueIndex = 0;
        int playerXpLevel = player.experienceLevel;

        if (!data.contains(StringUtils.MoonCuriosXpLevel)) {
            data.putInt(StringUtils.MoonCuriosXpLevel, playerXpLevel);
            data.putString(StringUtils.MoonCuriosPlayerName, player.getName().getString());
        }
        playerXpLevel = data.getInt(StringUtils.MoonCuriosXpLevel);
        data.putInt(StringUtils.RandomCuriosAttribute.attackDamage, (int) (playerXpLevel * AttributeBase[baseValueIndex++]));
        data.putInt(StringUtils.RandomCuriosAttribute.manaDamage, (int) (playerXpLevel * AttributeBase[baseValueIndex++]));
        data.putInt(StringUtils.RandomCuriosAttribute.defence, (int) (playerXpLevel * AttributeBase[baseValueIndex++]));
        data.putInt(StringUtils.RandomCuriosAttribute.maxHealth, (int) (playerXpLevel * AttributeBase[baseValueIndex++]));
    }
}

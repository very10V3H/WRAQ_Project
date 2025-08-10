package fun.wraq.series.events.labourDay;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.impl.onkill.OnKillEffectCurios;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.customized.uniform.UnCommonUniform;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LabourDayUniformCurio extends WraqUniformCurios implements OnKillEffectCurios,
        InCuriosOrEquipSlotAttributesModify, UnCommonUniform {

    public LabourDayUniformCurio(Properties properties) {
        super(properties);
        Utils.xpLevelDefence.put(this, 0.2d);
        Utils.xpLevelManaDefence.put(this, 0.2d);
        Utils.xpLevelDefencePenetration0.put(this, 0.1d);
        Utils.xpLevelManaPenetration0.put(this, 0.1d);
        Utils.coolDownDecrease.put(this, 0.1);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("光荣劳动", hoverMainStyle()));
        components.add(Te.s(" 每日击杀", "5000", ChatFormatting.RED, "只怪物，将为你提供:"));
        components.add(Te.s( " 1.", hoverMainStyle(),
                ModItems.WORLD_SOUL_5.get(), " * 10", ChatFormatting.AQUA));
        components.add(Te.s( " 2.", hoverMainStyle(), "10000VB", CustomStyle.styleOfGold));
        components.add(Te.s(" 3.", hoverMainStyle(), "10%概率额外获得", ChatFormatting.LIGHT_PURPLE,
                ModItems.WORLD_SOUL_5.get(), " * 100", ChatFormatting.AQUA));
        components.add(Te.s(" 并根据", "击杀进度", ChatFormatting.AQUA, "为你至多提供",
                ComponentUtils.AttributeDescription.getElementStrength("75%")));
        components.add(Te.s(" 今天的击杀数额为:", getCount(stack), ChatFormatting.RED));
        components.add(Te.s(" 当前 ", ChatFormatting.AQUA,
                ComponentUtils.AttributeDescription.getElementStrength(
                        String.format("+%.0f%%", Math.min(5000, getCount(stack)) / 5000d * 0.75 * 100))));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfGold;
    }

    @Override
    public Component getFirstPassiveName() {
        return Te.s("最后的斗争", hoverMainStyle());
    }

    @Override
    public double getFinalDamageEnhanceRate() {
        return 0.5;
    }

    public static String COUNT_DATA_KEY = "Count";
    public static String COUNT_DATE_KEY = "CountDate";

    public static int getCount(ItemStack stack) {
        return stack.getOrCreateTagElement(Utils.MOD_ID).getInt(COUNT_DATA_KEY);
    }

    public static void setCount(ItemStack stack, int divineCount) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putInt(COUNT_DATA_KEY, divineCount);
    }

    public static Calendar getCountDate(ItemStack stack) {
        if (!stack.getOrCreateTagElement(Utils.MOD_ID).contains(COUNT_DATE_KEY)) {
            return null;
        }
        try {
            return Compute.StringToCalendar(stack.getOrCreateTagElement(Utils.MOD_ID).getString(COUNT_DATE_KEY));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCountDate(ItemStack stack, Calendar date) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putString(COUNT_DATE_KEY, Compute.CalendarToString(date));
    }

    public static void addCount(ItemStack stack) {
        setCount(stack, getCount(stack) + 1);
        if (getCountDate(stack) == null) {
            setCountDate(stack, Calendar.getInstance());
        }
        if (getCountDate(stack).get(Calendar.DATE) != Calendar.getInstance().get(Calendar.DATE)) {
            setCount(stack, 0);
            setCountDate(stack, Calendar.getInstance());
        }
    }

    @Override
    public void onKill(Player player, Mob mob, ItemStack stack) {
        addCount(stack);
        if (getCount(stack) == 5000) {
            MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
            InventoryOperation.giveItemStackWithMSG(player, ModItems.WORLD_SOUL_5.get(), 10);
            Compute.VBIncomeAndMSGSend(player, 10000);
            if (RandomUtils.nextInt(0, 100) < 10) {
                InventoryOperation.giveItemStackWithMSG(player, ModItems.WORLD_SOUL_5.get(), 100);
                Compute.sendFormatMSG(player, Te.s("劳动节因子", hoverMainStyle()),
                        Te.s("恭喜你额外获得了",
                                ModItems.WORLD_SOUL_5.get(), " * 100", ChatFormatting.AQUA,
                                "!!!", hoverMainStyle()));
            }
        }
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        return List.of(new Attribute(Utils.elementStrength, Math.min(5000, getCount(stack)) / 5000d * 0.75));
    }
}

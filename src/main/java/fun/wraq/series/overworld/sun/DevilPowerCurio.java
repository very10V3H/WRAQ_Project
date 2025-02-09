package fun.wraq.series.overworld.sun;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DevilPowerCurio extends WraqCurios {

    public static int clientTotalKillCount = 0;

    public DevilPowerCurio(Properties properties) {
        super(properties);
        Utils.percentMaxHealthEnhance.put(this, -0.1);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("超凡邪力", hoverMainStyle()));
        components.add(Te.s(" 根据你的", "总击杀数", hoverMainStyle(), "为你提供至多",
                "50%最终伤害加成", hoverMainStyle()));
        components.add(Te.s(" 每", "10000总击杀数", hoverMainStyle(), "提供", "0.5%最终伤害加成", hoverMainStyle()));
        components.add(Te.s(" 当前提供的", "最终伤害加成", hoverMainStyle(), ": ",
                String.format("%.2f%%", Math.min(50, clientTotalKillCount / 20000d)), hoverMainStyle()));
        components.add(Te.s(" 总击杀数: ", String.valueOf(clientTotalKillCount), hoverMainStyle()));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfRed;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSunIsland();
    }

    public static double finalDamageEnhanceRate(Player player) {
        if (Compute.hasCurios(player, SunIslandItems.DEVIL_POWER_CURIO.get())
                && MobSpawn.totalKillCountCache.containsKey(player.getName().getString())) {
            return Math.min(0.5, MobSpawn.totalKillCountCache.get(player.getName().getString()) / 10000d * 0.005);
        }
        return 0;
    }
}

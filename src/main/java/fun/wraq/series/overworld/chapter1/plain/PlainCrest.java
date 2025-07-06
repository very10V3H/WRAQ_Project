package fun.wraq.series.overworld.chapter1.plain;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RepeatableCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.overworld.chapter1.mine.MineCrest;
import fun.wraq.series.overworld.chapter1.snow.SnowCrest;
import fun.wraq.series.overworld.chapter1.volcano.VolcanoCrest;
import fun.wraq.series.overworld.chapter1.waterSystem.crest.LakeCrest;
import fun.wraq.series.overworld.chapter2.evoker.ManaCrest;
import fun.wraq.series.overworld.chapter2.sky.Crest.SkyCrest;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlainCrest extends WraqCurios implements RepeatableCurios {

    public static List<Item> crestList = new ArrayList<>();

    public PlainCrest(Properties properties, int tier) {
        super(properties, 16);
        Utils.healthRecover.put(this, new double[]{1, 3, 5, 7, 14}[tier]);
        Utils.maxHealth.put(this, new double[]{400, 800, 1200, 1600, 6400}[tier]);
        Element.lifeElementValue.put(this, new double[]{0.05, 0.12, 0.2, 0.32, 0.5}[tier]);
        crestList.add(this);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        if (Screen.hasShiftDown()) PlainSuitDescription.SuitDescription(components);
        else {
            ComponentUtils.suitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }

    public static void onKillMob(Player player, Mob mob) {
        Element.Unit unit = Element.entityElementUnit.getOrDefault(mob, null);
        if (unit != null && unit.value() != 0) {
            double rate = 1 + 0.5 * (MobSpawn.getMobXpLevel(mob) * 1d / 300);
            if (unit.type().equals(Element.life)) {
                giveCrest(player, mob, crestList, rate);
            } else if (unit.type().equals(Element.water)) {
                giveCrest(player, mob, LakeCrest.crestList, rate);
            } else if (unit.type().equals(Element.fire)) {
                giveCrest(player, mob, VolcanoCrest.crestList, rate);
            } else if (unit.type().equals(Element.stone)) {
                giveCrest(player, mob, MineCrest.crestList, rate);
            } else if (unit.type().equals(Element.wind)) {
                giveCrest(player, mob, SkyCrest.crestList, rate);
            } else if (unit.type().equals(Element.ice)) {
                giveCrest(player, mob, SnowCrest.crestList, rate);
            } else if (unit.type().equals(Element.lightning)) {
                giveCrest(player, mob, ManaCrest.crestList, rate);
            }
        }
    }

    public static void giveCrest(Player player, Mob mob, List<Item> crestList, double rate) {
        List<ItemAndRate> list = new ArrayList<>();
        double[] rates = new double[]{0.02, 0.005, 0.001, 0.0002};
        double num = 1 + Compute.playerExHarvest(player);
        for (int i = 0; i < 4; i++) {
            list.add(new ItemAndRate(crestList.get(i), rates[i] * rate));
        }
        list.forEach(itemAndRate -> {
            if (Compute.hasCurios(player, NewRuneItems.END_NEW_RUNE.get())) {
                itemAndRate.send(player, num);
            } else {
                itemAndRate.dropWithBounding(mob, num, player);
            }
        });
    }
}

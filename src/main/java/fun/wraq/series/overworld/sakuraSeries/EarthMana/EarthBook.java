package fun.wraq.series.overworld.sakuraSeries.EarthMana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.hud.Mana;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class EarthBook extends WraqOffHandItem {

    public EarthBook(Properties properties) {
        super(properties, Component.literal("魔法书").withStyle(CustomStyle.styleOfMana));
        Utils.manaDamage.put(this, 200d);
        Utils.manaPenetration0.put(this, 6d);
        Utils.maxMana.put(this, 350d);
        Utils.expUp.put(this, 0.65);
        Utils.manaHealthSteal.put(this, 0.04);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfJacaranda;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Te.s("传世禁咒", style));
        components.add(Te.s(" 当你拥有高于", ComponentUtils.AttributeDescription.health("75%"), "时，",
                "若", ComponentUtils.AttributeDescription.manaValue(""), "未达100%"));
        components.add(Te.s(" 则回复的", ComponentUtils.AttributeDescription.health("5%"), "转化为回复的",
                ComponentUtils.AttributeDescription.manaValue("")));
        components.add(Te.s(" buff栏会显示其最近5s为你回复的法力值总额", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        return components;
    }

    public record EarthBookRecoverData(double value, int expiredTick) {}
    public static Map<Player, Queue<EarthBookRecoverData>> near5secondsRecoverValue = new WeakHashMap<>();

    public static boolean onHealthRecover(Player player, double value) {
        if (player.getOffhandItem().getItem() instanceof EarthBook
                && player.getHealth() > player.getMaxHealth() * 0.75) {
            double recoverValue = Math.min(
                    Mana.getPlayerMaxManaNum(player) - Mana.getPlayerCurrentManaNum(player), value * 0.05);
            Mana.addOrCostPlayerMana(player, recoverValue);
            if (!near5secondsRecoverValue.containsKey(player)) {
                near5secondsRecoverValue.put(player, new ArrayDeque<>());
            }
            Queue<EarthBookRecoverData> queue = near5secondsRecoverValue.get(player);
            while (queue.peek() != null && queue.peek().expiredTick < Tick.get()) {
                queue.poll();
            }
            queue.add(new EarthBookRecoverData(recoverValue, Tick.get() + 100));
            return true;
        }
        return false;
    }

    @Override
    public void tick(Player player) {
        if (near5secondsRecoverValue.containsKey(player)) {
            Queue<EarthBookRecoverData> queue = near5secondsRecoverValue.get(player);
            double sum = queue.stream().mapToDouble(data -> data.value).sum();
            Compute.sendEffectLastTime(player, ModItems.EarthBook.get(), (int) sum, true);
        }
        super.tick(player);
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }
}

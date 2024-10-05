package fun.wraq.series.overworld.sakuraSeries.EarthMana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class EarthBook extends WraqOffHandItem {

    public EarthBook(Properties properties) {
        super(properties, Component.literal("魔法书").withStyle(CustomStyle.styleOfMana));
        Utils.manaDamage.put(this, 400d);
        Utils.manaPenetration0.put(this, 6d);
        Utils.maxMana.put(this, 50d);
        Utils.movementSpeedWithoutBattle.put(this, 1d);
        Utils.expUp.put(this, 1.5);
        Utils.manaHealthSteal.put(this, 0.04);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfJacaranda;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("大地之力").withStyle(style));
        components.add(Component.literal(" 当你持续5s").withStyle(ChatFormatting.WHITE).
                append(Component.literal("移动幅度").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("较小时，").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 充满魔力的土地会吸收你的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.maxHealth("50%")));
        components.add(Te.m(" ").
                append(Component.literal("并将之以15%效率转化为").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.maxMana("")).
                append(Component.literal("，加成持续20s。").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" -大地之力仅在你的生命值大于75%时会触发").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }

    public static void earthBookPlayerEffect(Player player) {
        if (player.getItemInHand(InteractionHand.OFF_HAND).is(ModItems.EarthBook.get())) {
            int TickCount = player.getServer().getTickCount();
            if (!Utils.EarthBookPlayerPosMap.containsKey(player))
                Utils.EarthBookPlayerPosMap.put(player, new ArrayDeque<>());
            Queue<Vec3> vec3s = Utils.EarthBookPlayerPosMap.get(player);
            vec3s.add(player.position());
            if (vec3s.size() > 5) {
                if (vec3s.peek().distanceTo(player.position()) < 2) {
                    if (player.getHealth() / player.getMaxHealth() > 0.75) {
                        Utils.EarthBookPlayerEffectMap.put(player, TickCount + 400);
                        player.setHealth(player.getHealth() - player.getMaxHealth() * 0.5f);
                        Compute.sendEffectLastTime(player, ModItems.EarthBook.get().getDefaultInstance(), 400);
                    }
                }
                vec3s.poll();
            }
        }
    }
}

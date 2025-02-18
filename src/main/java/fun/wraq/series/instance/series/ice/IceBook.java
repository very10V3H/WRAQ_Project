package fun.wraq.series.instance.series.ice;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitDamageInfluenceEquip;
import fun.wraq.common.impl.onhit.OnHitEffectEquip;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class IceBook extends WraqOffHandItem implements OnHitEffectEquip, OnHitDamageInfluenceEquip {

    public IceBook(Properties properties) {
        super(properties, Te.s("魔导书", CustomStyle.styleOfMana));
        Utils.manaDamage.put(this, 300d);
        Utils.manaPenetration0.put(this, 8d);
        Utils.maxMana.put(this, 400d);
        Utils.expUp.put(this, 0.7);
        Utils.levelRequire.put(this, 135);
    }

    private final Style style = CustomStyle.styleOfIce;

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("千里冰封").withStyle(style));
        components.add(Component.literal(" 每过5s，你的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("将使目标").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("减速").withStyle(style)));
        components.add(Te.s(" 并使你对其伤害受", ComponentUtils.getCommonDamageEnhance("20%"), "，持续3s"));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfIce();
    }

    private static Map<Player, Integer> coolDownMap = new WeakHashMap<>();
    private static Map<Player, Integer> effectLastTickMap = new WeakHashMap<>();
    private static Map<String, Mob> effectTargetMap = new HashMap<>();
    @Override
    public void onHit(Player player, Mob mob) {
        if (coolDownMap.getOrDefault(player, 0) < Tick.get()) {
            coolDownMap.put(player, Tick.get() + 100);
            effectTargetMap.put(Name.get(player), mob);
            effectLastTickMap.put(player, Tick.get() + 60);
            Compute.sendCoolDownTime(player, ModItems.IceBook.get().getDefaultInstance(), 100);
            Compute.addSlowDownEffect(mob, 60, 3);
            Compute.createIceParticle(mob);
            Compute.sendMobEffectHudToNearPlayer(mob, ModItems.IceBook.get(), "IceBookDamageEnhance", 60, 0, false);
        }
    }

    @Override
    public double onHitDamageInfluence(Player player, Mob mob) {
        if (effectLastTickMap.getOrDefault(player, 0) > Tick.get()
                && effectTargetMap.containsKey(Name.get(player))
                && effectTargetMap.get(Name.get(player)).equals(mob)) return 0.2;
        return 0;
    }
}

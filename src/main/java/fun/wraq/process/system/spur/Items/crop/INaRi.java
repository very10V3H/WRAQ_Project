package fun.wraq.process.system.spur.Items.crop;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.process.func.PersistentRangeEffect;
import fun.wraq.process.func.PersistentRangeEffectOperation;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class INaRi extends WraqBow implements ActiveItem {
    public INaRi(Properties properties) {
        super(properties);
    }

    @Override
    public Style getMainStyle() {
        return null;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionActive(components, Te.s("稻荷神赋", getMainStyle()));
        components.add(Te.s(" 制造一片稻荷领域"));
        components.add(Te.s(" · ", "对范围内的敌方持续造成", "箭矢攻击伤害", CustomStyle.styleOfFlexible));
        components.add(Te.s(" · ", "对范围内的玩家提供", "治疗",
                "与", ComponentUtils.getCommonDamageEnhance("")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void active(Player player) {
        PersistentRangeEffect.addEffect(player, WraqPower.getDefaultTargetPos(player, 32), 8, new PersistentRangeEffectOperation() {
            @Override
            public void operation(PersistentRangeEffect effect) {
                effect.getRangeMob().forEach(mob -> {
                    MyArrow.causeDamage(player, mob, 0.25);
                });
                effect.getRangePlayer().forEach(eachPlayer -> {
                    StableAttributesModifier.addM(eachPlayer,
                            StableAttributesModifier.playerCommonDamageEnhance, "", 0.25, Tick.get() + 20, "");
                    Compute.playerHeal(eachPlayer, eachPlayer.getMaxHealth() * 0.05);
                });
            }
        }, 10, Tick.s(3));
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}

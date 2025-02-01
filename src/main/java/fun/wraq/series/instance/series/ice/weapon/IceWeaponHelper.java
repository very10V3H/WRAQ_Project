package fun.wraq.series.instance.series.ice.weapon;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.List;

public class IceWeaponHelper {
    public static void onHit(Player player, Mob mob) {
        Compute.addSlowDownEffect(mob, 20, 1);
        MySound.soundToNearPlayer(player, SoundEvents.SNOW_BREAK);
    }

    public static void onCritHit(Player player, Mob mob, Item icon, int maxTier, int tier) {
        String tag = "Ice equip passive" + icon;
        if (StableTierAttributeModifier.getAttributeModifierTier(mob, StableTierAttributeModifier.onlyDisplay, tag) == maxTier) {
            Damage.causeAutoAdaptionRateDamageToMob(player, mob, 2, false);
            StableTierAttributeModifier.removeAttributeModifier(mob, StableTierAttributeModifier.onlyDisplay, tag, icon);
            Compute.createIceParticle(mob);
            if (tier > 0) {
                StableAttributesModifier.addM(mob,
                        maxTier == 5 ? StableAttributesModifier.mobPercentDefenceModifier : StableAttributesModifier.mobPercentManaDefenceModifier,
                        tag + "Defence decrease", -0.3, Tick.get() + 100, ModItems.IceHeart.get());
                StableAttributesModifier.addM(player, StableAttributesModifier.playerCritRateModifier,
                        "Ice equip passive crit rate up", 0.3, Tick.get() + 100);
            }
        }
        StableTierAttributeModifier.addM(mob, StableTierAttributeModifier.onlyDisplay, tag, 0, Tick.get() + 100, maxTier, icon);
    }

    public static void description(List<Component> components, int tier) {
        ComponentUtils.descriptionPassive(components, Component.literal("迸晶裂玉").withStyle(CustomStyle.styleOfIce));
        components.add(Component.literal(" 攻击").withStyle(CustomStyle.styleOfPower).
                append(Component.literal("将对目标造成持续1s的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("减速").withStyle(CustomStyle.styleOfIce)));
        Compute.DescriptionPassive(components, Component.literal("凝结爆裂").withStyle(CustomStyle.styleOfIce));
        components.add(Te.m(" 对处于").
                append(Te.m("减速", CustomStyle.styleOfIce)).
                append(Te.m("状态的目标造成")).
                append(Te.m("暴击", ChatFormatting.BLUE)).
                append(Te.m("后，施加一层")).
                append(Te.m("寒冰", CustomStyle.styleOfIce)));
        components.add(Te.m(" 当目标的").
                append(Te.m("寒冰", CustomStyle.styleOfIce)).
                append(Te.m("达到5层后，下次暴击会引爆所有层数")));
        components.add(Te.m(" 对目标造成").
                append(ComponentUtils.getAutoAdaptDamageDescription("200%")));
        if (tier == 0) {
            components.add(Te.s(" 锐化后", CustomStyle.styleOfWorld, "可激活",
                    "穿甲", CustomStyle.styleOfStone, "/", "暴击", CustomStyle.styleOfLucky, "效果"));
        } else {
            components.add(Te.m(" 并击碎目标").
                    append(ComponentUtils.AttributeDescription.defence("25%")).
                    append(Te.m("，持续5s")));
            components.add(Te.s(" 自身获得", ComponentUtils.AttributeDescription.critRate("30%")
                    , "，持续5s"));
        }
    }
}

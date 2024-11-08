package fun.wraq.series.nether.equip.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnPowerCauseDamageEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WitherBook extends WraqOffHandItem implements OnPowerCauseDamageEquip {

    public WitherBook(Properties properties) {
        super(properties, Te.m("魔导书", CustomStyle.styleOfMana));
        Utils.manaDamage.put(this, 200d);
        Utils.manaPenetration0.put(this, 6d);
        Utils.maxMana.put(this, 325d);
        Utils.expUp.put(this, 0.6);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfWither;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("凋零秘术").withStyle(getMainStyle()));
        components.add(Component.literal(" 任意").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法术").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("命中敌人时，击碎敌人").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDefence("5%")));
        components.add(Te.s("至多叠加至4层，每层持续3s"));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfNether();
    }

    @Override
    public void onCauseDamage(Player player, Mob mob) {
        StableTierAttributeModifier.addM(mob, StableTierAttributeModifier.percentManaDefence, "WitherBook Passive",
                -0.05, Tick.get() + 60, 4, this);
    }
}

package fun.wraq.customized.uniform.mana;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.WraqUniformCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class ManaCurios0 extends WraqManaUniformCurios {

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("法术扩频").withStyle(hoverMainStyle());
    }

    public ManaCurios0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("法术调幅").withStyle(style));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.manaDamage("35%总")));
        components.add(Te.s(" 法术的研究者，也是亚瑟王的挚友和导师——梅林，给予新生法师的礼物。", style));
        return components;
    }

    public static WeakHashMap<Player, Boolean> onPlayerMap = new WeakHashMap<>();

    public static boolean IsOn(Player player) {
        return WraqUniformCurios.isOn(ManaCurios0.class, player);
    }

    public static double PlayerFinalManaDamageEnhance(Player player) {
        if (!IsOn(player)) return 0;
        return 0.35;
    }
}

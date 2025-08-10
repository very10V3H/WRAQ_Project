package fun.wraq.customized.uniform.mana.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class ManaCurios0 extends WraqManaUniformCurios {

    @Override
    public Component getFirstPassiveName() {
        return Te.s("法术扩频", hoverMainStyle());
    }

    public ManaCurios0(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Te.s("法术调幅", hoverMainStyle()));
        components.add(Te.s(" 获得", ComponentUtils.AttributeDescription.manaDamage("35%总")));
        components.add(Te.s(" 法术的研究者，也是亚瑟王的挚友和导师——梅林，给予新生法师的礼物。", hoverMainStyle()));
        return components;
    }

    public static WeakHashMap<Player, Boolean> onPlayerMap = new WeakHashMap<>();

    public static boolean isOn(Player player) {
        return WraqCurios.hasCurio(player, UniformItems.MANA_CURIOS_0.get())
                || WraqCurios.hasCurio(player, UniformItems.MANA_ENHANCED_CURIOS_0.get());
    }

    public static double getPlayerFinalManaDamageEnhance(Player player) {
        if (!isOn(player)) {
            return 0;
        }
        if (WraqCurios.hasCurio(player, UniformItems.MANA_CURIOS_0.get())) {
            return 0.35;
        } else {
            return 0.5;
        }
    }
}

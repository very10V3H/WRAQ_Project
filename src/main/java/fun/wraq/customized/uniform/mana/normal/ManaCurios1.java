package fun.wraq.customized.uniform.mana.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.customized.UniformItems;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class ManaCurios1 extends WraqManaUniformCurios {

    public ManaCurios1(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Te.s("解构凡躯", style));
        components.add(Te.s(" 造成的", "魔法伤害", hoverMainStyle(),
                "将额外造成", "35%真实伤害", CustomStyle.styleOfSea, "."));
        components.add(Te.s(" 法术的研究者，也是亚瑟王的挚友和导师——梅林，给予新生法师的礼物。", style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("凌于自然").withStyle(hoverMainStyle());
    }

    public static WeakHashMap<Player, Boolean> onPlayerMap = new WeakHashMap<>();

    public static boolean isOn(Player player) {
        return WraqCurios.hasCurio(player, UniformItems.MANA_CURIOS_1.get())
                || WraqCurios.hasCurio(player, UniformItems.MANA_ENHANCED_CURIOS_0.get());
    }

    public static void getManaDamageExTrueDamage(Player player, Mob mob, double damage) {
        if (!isOn(player)) {
            return;
        }
        Damage.causeTrueDamageToMonster(player, mob, damage
                * ((WraqCurios.hasCurio(player, UniformItems.MANA_CURIOS_1.get())) ? 0.35 : 0.5));
    }
}

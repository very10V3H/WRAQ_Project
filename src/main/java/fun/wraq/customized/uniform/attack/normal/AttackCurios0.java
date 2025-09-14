package fun.wraq.customized.uniform.attack.normal;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.SwordAttribute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.UniformItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AttackCurios0 extends WraqAttackUniformCurios {

    public AttackCurios0(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Te.s("暴政", hoverMainStyle()));
        components.add(Te.s(" 获得", ComponentUtils.AttributeDescription.critDamage("15%总")));
        components.add(Te.s(" 仅近战可用.", ChatFormatting.GRAY, ChatFormatting.ITALIC));
        components.add(Te.s(" 残暴的君主，终将被民众推翻。", hoverMainStyle()));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("君临天下").withStyle(hoverMainStyle());
    }

    public static double getPlayerFinalCritDamageEnhanceRate(Player player) {
        if (SwordAttribute.isHandling(player)) {
            if (WraqCurios.hasCurio(player, UniformItems.ATTACK_CURIOS_0.get())) {
                return 0.15;
            } else if (WraqCurios.hasCurio(player, UniformItems.ATTACK_ENHANCED_CURIOS_0.get())) {
                return 0.2;
            }
        }
        return 0;
    }
}

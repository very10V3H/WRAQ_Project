package fun.wraq.customized.uniform.bow;

import fun.wraq.common.Compute;
import fun.wraq.common.impl.onshoot.OnShootArrowCurios;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BowCurios1 extends WraqBowUniformCurios implements OnShootArrowCurios {

    public BowCurios1(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        Compute.DescriptionPassive(components, Component.literal("精湛弓术").withStyle(style));
        components.add(Component.literal(" 普通箭矢攻击").withStyle(style).
                append(Component.literal("将额外释放一枚具有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%基础伤害").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢").withStyle(style)));
        components.add(Component.literal(" 不仅是敏捷，力量、智慧对在恶劣环境中的猎手同样重要。").withStyle(style));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return Component.literal("优胜劣汰").withStyle(hoverMainStyle());
    }

    @Override
    public void onShoot(Player player) {
        WraqQuiver.batchAddExShoot(player, 1, 1);
    }
}

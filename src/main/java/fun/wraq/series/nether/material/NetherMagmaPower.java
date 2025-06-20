package fun.wraq.series.nether.material;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.mobEffects.ModEffects;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NetherMagmaPower extends Item {

    public NetherMagmaPower(Properties p_41383_) {
        super(p_41383_);
        Display.materialList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.MaterialLevelDescription.Normal(components);
        components.add(Component.literal(" "));
        components.add(Component.literal(" 一团下界熔岩能量。").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))));
        components.add(Component.literal(" 能够放置在背包里已经是个奇迹了。").withStyle(Style.EMPTY.withColor(TextColor.parseColor("#a2001b"))));
        components.add(Component.literal(" "));
        components.add(Te.s(" 当你处于", "失温状态", CustomStyle.styleOfIce,
                "且不处于", "温暖状态", CustomStyle.styleOfPower, "时", "将自动使用."));
        components.add(Component.literal(" 右键使用以获取持续3min的").withStyle(ChatFormatting.WHITE).
                append(Component.literal("'温暖'").withStyle(CustomStyle.styleOfPower)));
        components.add(Te.s(" 温暖", CustomStyle.styleOfPower, "提供", "产热 + 1", CustomStyle.BUNKER_STYLE));
        super.appendHoverText(itemStack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Compute.playerItemUseWithRecord(player);
            player.addEffect(new MobEffectInstance(ModEffects.WARM.get(), 3600));
        }
        return super.use(level, player, interactionHand);
    }
}

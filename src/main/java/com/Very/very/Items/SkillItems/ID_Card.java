package com.Very.very.Items.SkillItems;

import com.Very.very.Render.Gui.TestAndHelper.OpenSkillGui;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ID_Card extends Item{
    public ID_Card(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("维瑞阿契的身份卡。"));
        components.add(Component.literal("右键查看属性、能力与精通信息。"));
        components.add(Component.literal(" "));
        components.add(Component.literal("ID Card").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (level.isClientSide) {
            DistExecutor.safeCallWhenOn(Dist.CLIENT,() -> OpenSkillGui::new);
        }
        return super.use(level, player, interactionHand);
    }
}

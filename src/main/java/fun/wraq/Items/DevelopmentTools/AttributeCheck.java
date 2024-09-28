package fun.wraq.Items.DevelopmentTools;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AttributeCheck extends Item {

    public AttributeCheck(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand useHand) {
        if (!level.isClientSide) {
/*            player.sendSystemMessage(Component.literal("基础攻击:").append(Component.literal(String.valueOf(PlayerAttributes.PlayerAttackDamage(player)))));
            player.sendSystemMessage(Component.literal("防御力:").append(Component.literal(String.valueOf(PlayerAttributes.PlayerDefence(player)))));
            player.sendSystemMessage(Component.literal("移速加成:").append(Component.literal(String.valueOf(player.getAttribute(Attributes.MOVEMENT_SPEED).getValue()))));
            player.sendSystemMessage(Component.literal("经验加成:").append(Component.literal(String.valueOf(Compute.ExpGetImprove(player)))));
            player.sendSystemMessage(Component.literal("生命偷取:").append(Component.literal(String.valueOf(PlayerAttributes.PlayerHealSteal(player)))));
            player.sendSystemMessage(Component.literal("暴击几率:").append(Component.literal(String.valueOf(PlayerAttributes.PlayerCriticalHitRate(player)))));
            player.sendSystemMessage(Component.literal("暴击伤害:").append(Component.literal(String.valueOf(PlayerAttributes.PlayerCriticalHitDamage(player)))));
            player.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(1.0D);*/
            player.sendSystemMessage(Component.literal(player.getBlockStateOn().getBlock().toString().substring(6, 12)));
        }
        return super.use(level, player, useHand);
    }
}

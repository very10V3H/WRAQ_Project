package fun.wraq.Items.MainStory_1.Mission;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Main0_2 extends Item {
    public Main0_2(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("本节将介绍维瑞阿契的").append(Component.literal("战斗要素").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("维瑞阿契保留Minecraft原生战斗方式。"));
        components.add(Component.literal("并且在此基础上新增了多种战斗属性："));
        components.add(Component.literal("·基础攻击").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("·护甲穿透").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("·暴击几率").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("·暴击伤害").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("·生命偷取").withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("等属性，这些属性都将用固定的颜色表示。"));
        components.add(Component.literal("随着内容的增加，维瑞阿契还将引入更多战斗属性。"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Prologue-II").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
/*        ConfigTest.VALUE.set(20);
        level.addParticle(ParticleTypes.FIREWORK,player.getX(),player.getY(),player.getZ(),0,0,0);
        FireBallTest fireBallTest = new FireBallTest(level,player,0,0,0,2);
        fireBallTest.shootFromRotation(player,90,0,0.0d,3.0d,0.0d);
        fireBallTest.moveTo(player.getX(),player.getY(),player.getZ());
        fireBallTest.setDeltaMovement(0,-2,0);
        level.addFreshEntity(fireBallTest);*/
/*        if(!level.isClientSide){
            Iterator<MarketItemInfo> iterator = Utils.MarketInfo.iterator();
            ModNetworking.sendToClient(new MarketDataClearS2CPacket(),(ServerPlayer) player);
            while(iterator.hasNext()){
                MarketItemInfo marketItemInfo = iterator.next();
                ModNetworking.sendToClient(new MarketDataS2CPacket(marketItemInfo.getPlayer(),marketItemInfo.getItemStack(),marketItemInfo.getPrice()),(ServerPlayer) player);
            }
        }*/
        return super.use(level, player, interactionHand);
    }
}

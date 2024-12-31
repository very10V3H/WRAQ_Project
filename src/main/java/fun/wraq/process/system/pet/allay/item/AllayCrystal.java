package fun.wraq.process.system.pet.allay.item;

import fun.wraq.common.fast.Te;
import fun.wraq.process.system.pet.allay.AllayPet;
import fun.wraq.process.system.pet.allay.AllayPetPlayerData;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AllayCrystal extends WraqItem {
    public AllayCrystal(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 使用以召唤/收回", "悦灵", CustomStyle.styleOfWorld));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (AllayPet.playerAllayPetMap.containsKey(player.getName().getString())) {
                AllayPet.playerRemoveAllay(serverPlayer);
                AllayPet.sendMSG(player, Te.s("你收回了",
                        AllayPetPlayerData.getAllayName(player), CustomStyle.styleOfWorld));
            } else {
                AllayPet.playerSpawnAllay(serverPlayer);
                AllayPet.sendMSG(player, Te.s("你召唤了",
                        AllayPetPlayerData.getAllayName(player), CustomStyle.styleOfWorld));
            }
        }
        return super.use(level, player, interactionHand);
    }
}

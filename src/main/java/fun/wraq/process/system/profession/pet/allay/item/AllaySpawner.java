package fun.wraq.process.system.profession.pet.allay.item;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.profession.pet.allay.AllayPet;
import fun.wraq.process.system.profession.pet.allay.AllayPetPlayerData;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AllaySpawner extends WraqItem {
    public AllaySpawner(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" 使用来获得一只专属于你的", "悦灵", CustomStyle.styleOfWorld));
        components.add(Te.s(" 使用后将获得", AllayItems.ALLAY_CRYSTAL, "来召唤/收回", "悦灵", CustomStyle.styleOfWorld));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (AllayPetPlayerData.getAllayXpLevel(player) > 0) {
                AllayPet.sendMSG(player, Te.s("你已经拥有一只", "悦灵", CustomStyle.styleOfWorld, "了"));
            } else {
                Compute.playerItemUseWithRecord(player);
                AllayPetPlayerData.getAllayPetData(player).putInt(AllayPetPlayerData.XP_LEVEL_KEY, 1);
                InventoryOperation.giveItemStackWithMSG(player, AllayItems.ALLAY_CRYSTAL.get());
                AllayPet.sendMSG(player, Te.s("你获得了一只专属于你的", "悦灵", CustomStyle.styleOfWorld,
                        "! 快使用", AllayItems.ALLAY_CRYSTAL, "召唤它!"));
            }
        }
        return super.use(level, player, interactionHand);
    }
}

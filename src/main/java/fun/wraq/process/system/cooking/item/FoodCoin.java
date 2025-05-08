package fun.wraq.process.system.cooking.item;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.system.cooking.CookingPlayerData;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class FoodCoin extends WraqItem {

    public FoodCoin(Properties properties) {
        super(properties, false, true, List.of(
                Te.s("老八的认可", CustomStyle.MUSHROOM_STYLE),
                Te.s("主要通过完成", "烹饪委托", CustomStyle.MUSHROOM_STYLE, "获得"),
                Te.s("右键可消耗获得", "1烹饪经验", CustomStyle.MUSHROOM_STYLE)
        ));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.playerItemUseWithRecord(player);
            int beforeExp = CookingPlayerData.getCookingExp(player);
            CookingPlayerData.incrementTimesCount(player, CookingPlayerData.FOOD_COIN_USED_COUNT_KEY);
            int afterExp = CookingPlayerData.getCookingExp(player);
            CookingPlayerData.sendCookingExpChangeMSG(player, afterExp - beforeExp);
        }
        return super.use(level, player, interactionHand);
    }
}

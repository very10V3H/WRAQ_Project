package fun.wraq.process.system.parkour;

import fun.wraq.Items.KillPaper.KillPaper;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.item.InventoryOperation;
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
import java.util.Random;

public class KillPaperLoot extends Item {

    private final boolean isLarge;
    public KillPaperLoot(Properties properties, boolean isLarge) {
        super(properties);
        this.isLarge = isLarge;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Te.s(" 使用以随机抽取一张" + (isLarge ? "大型" : "") + "征讨券！", ChatFormatting.LIGHT_PURPLE));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            Random rand = new Random();
            String tag = KillPaper.getDropListMap().keySet().stream().toList().get(rand.nextInt(KillPaper.getDropListMap().size()));
            ItemStack itemStack = new ItemStack(isLarge ? ModItems.KILL_PAPER_L.get() : ModItems.KILL_PAPER.get());
            itemStack.getOrCreateTagElement(Utils.MOD_ID).putString(KillPaper.killPaperType, tag);
            InventoryOperation.giveItemStackWithMSG(player, itemStack);
            Compute.playerItemUseWithRecord(player);
        }
        return super.use(level, player, interactionHand);
    }
}

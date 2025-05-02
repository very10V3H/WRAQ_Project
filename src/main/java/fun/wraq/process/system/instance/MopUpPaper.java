package fun.wraq.process.system.instance;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.instance.NoTeamInstance;
import fun.wraq.process.system.teamInstance.NewTeamInstance;
import fun.wraq.series.WraqItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MopUpPaper extends WraqItem {

    public final NoTeamInstance noTeamInstance;
    public final NewTeamInstance newTeamInstance;

    public MopUpPaper(Properties properties, NoTeamInstance noTeamInstance) {
        super(properties);
        this.noTeamInstance = noTeamInstance;
        this.newTeamInstance = null;
    }

    public MopUpPaper(Properties properties, NewTeamInstance newTeamInstance) {
        super(properties);
        this.noTeamInstance = null;
        this.newTeamInstance = newTeamInstance;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            if (noTeamInstance != null) {
                int killCount = MobSpawn.getPlayerKillCount(player, noTeamInstance.name.getString());
                if (killCount < 32) {
                    Compute.sendFormatMSG(player, Te.s("扫荡", ChatFormatting.RED),
                            Te.s("还需要挑战成功", (32 - killCount) + "次", ChatFormatting.RED,
                                    noTeamInstance.name, "才能使用此扫荡券."));
                } else {
                    List<ItemAndRate> rewardList = noTeamInstance.getRewardList();
                    rewardList.forEach(itemAndRate -> {
                        itemAndRate.send(player, 1);
                    });
                }
            } else {
                int killCount = MobSpawn.getPlayerKillCount(player, newTeamInstance.description.getString());
                if (killCount < 32) {
                    Compute.sendFormatMSG(player, Te.s("扫荡", ChatFormatting.RED),
                            Te.s("还需要挑战成功", (32 - killCount) + "次", ChatFormatting.RED,
                                    newTeamInstance.description, "才能使用此扫荡券."));
                } else {
                    List<ItemAndRate> rewardList = newTeamInstance.getRewardList();
                    rewardList.forEach(itemAndRate -> {
                        itemAndRate.send(player, 1);
                    });
                }
            }
            Compute.playerItemUseWithRecord(player);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Te.s(" 在挑战成功对应怪物/团队副本", "32次", ChatFormatting.RED, "后，可使用此扫荡券"));
        if (noTeamInstance != null) {
            components.add(Te.s(" 使用以扫荡领主级怪物: ", ChatFormatting.RED, noTeamInstance.name));
        } else {
            components.add(Te.s(" 使用以扫荡团队副本: ", ChatFormatting.RED, newTeamInstance.description));
        }
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}

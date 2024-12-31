package fun.wraq.Items.MainStory_1.Mission;

import com.simibubi.create.foundation.utility.IInteractionChecker;
import fun.wraq.common.fast.Te;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import org.violetmoon.quark.addons.oddities.inventory.BackpackMenu;

import java.util.List;

public class Main0_1 extends Item implements MenuProvider, IInteractionChecker {
    public Main0_1(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("本节将介绍维瑞阿契的").append(Component.literal("剧情推动方式").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("维瑞阿契将以世界探索为主线，展开剧情。"));
        components.add(Component.literal("大多数任务物品的兑换与获取都是通过").append(Component.literal("村民交易").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("这一Minecraft传统交易方式。"));
        components.add(Component.literal("并以右键信物/奖励/宝箱的方式为辅获取一些主线或其他物品。"));
        components.add(Component.literal("前面提到维瑞阿契将以世界探索作为主线，"));
        components.add(Component.literal("那么，跑图与开放世界是主线剧情推动的主要元素。"));
        components.add(Component.literal("在探索的过程中，感受Minecraft原生环境与"));
        components.add(Component.literal("维瑞阿契附加内容，是作者的本意。"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Prologue-I").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (level.isClientSide) {

        }
        if (!level.isClientSide) {
            NetworkHooks.openScreen((ServerPlayer) player, this);
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public Component getDisplayName() {
        return Te.s("测试宠物菜单");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BackpackMenu(id, player);
    }

    @Override
    public boolean canPlayerUse(Player player) {
        return true;
    }
}

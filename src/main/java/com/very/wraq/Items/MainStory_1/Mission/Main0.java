package com.very.wraq.Items.MainStory_1.Mission;

import com.very.wraq.common.Utils.ClientUtils;
import com.very.wraq.common.Compute;
import com.very.wraq.projectiles.OnCuriosSlotTickEffect;
import com.very.wraq.series.specialevents.summer.SummerEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

import java.sql.SQLException;
import java.util.List;

public class Main0 extends Item {

    public Main0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("欢迎你来到").append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal("这是一个任务物品，你可以看到它有着一个感叹号。"));
        components.add(Component.literal("在维瑞阿契，任务可以引导你探索这个世界。"));
        components.add(Component.literal("如果这个物品属于任务物品，你将可以看到："));
        components.add(Component.literal("在它底部描述有着它所属的任务章节。"));
        components.add(Component.literal("本任务属于维瑞阿契的序章，不是剧情的正式开始。"));
        components.add(Component.literal("维瑞阿契的序章，将会介绍维瑞阿契的各种要素。"));
        components.add(Component.literal("关于其他序章没有提及的要素，请在游戏过程中积极探索。"));
        components.add(Component.literal("希望你能有一个美好的游戏体验！"));
        components.add(Component.literal("本章作者：").append(Component.literal("very_H").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("Prologue-O").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        Compute.suffixOfElement(components);
        super.appendHoverText(stack, p_41422_, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        if (level.isClientSide && !player.isShiftKeyDown()) {
            ClientUtils.effectTimeLasts.forEach(effectTimeLast -> {
                player.sendSystemMessage(Component.literal(effectTimeLast.itemStack + " " + effectTimeLast.lastTick));
            });
/*            XaeroMinimapSession minimapSession = XaeroMinimapSession.getCurrentSession();
            WaypointsManager waypointsManager = minimapSession.getWaypointsManager();
            List<Waypoint> list = waypointsManager.getCurrentWorld().getCurrentSet().getList();
            Waypoint waypoint = new Waypoint(370, 72, 978, "本源塔子子", "本源塔子子", CustomStyle.styleOfSakura.getColor().getValue(), 0, false);
            waypoint.setVisibilityType(1);
            waypoint.setType(0);
            waypoint.setTemporary(false);
            if (!list.contains(waypoint)) list.add(waypoint);
            try {
                minimapSession.getModMain().getSettings().saveWaypoints(waypointsManager.getCurrentWorld());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
        }

        if (level.isClientSide && player.isShiftKeyDown()) {

        }

        if (!level.isClientSide && player.isShiftKeyDown()) {

        }

        if (!level.isClientSide && !player.isShiftKeyDown()) {
            CuriosApi.getCuriosInventory(player).ifPresent(inv -> {
                for (int i = 0; i < inv.getEquippedCurios().getSlots(); i++) {
                    player.sendSystemMessage(Component.literal("" + inv.getEquippedCurios().getStackInSlot(i).getItem().getClass()));
                }
            });
/*            ServerLevel serverLevel = (ServerLevel) level;
            for (Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof ItemEntity) {
                    ItemEntity itemEntity = (ItemEntity) entity;
                    player.sendSystemMessage(Component.literal(itemEntity.getItem() + " " + itemEntity.tickCount));
                }
            }*/


            // 通过tag获取物品
/*            ItemStack itemStack = new ItemStack(ModItems.GoldCoin.get());
            CompoundTag compoundTag = null;
            try {
                player.sendSystemMessage(Component.literal(itemStack.serializeNBT().getAsString()));
                compoundTag = TagParser.parseTag(itemStack.serializeNBT().getAsString());
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }

            ItemStack newItem = ItemStack.of(compoundTag);
            player.addItem(newItem);*/

/*            Entity entity = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation( "cataclysm", "ignis")).
                    spawn((ServerLevel) level, new BlockPos(player.getBlockX(), player.getBlockY(), player.getBlockZ()), MobSpawnType.MOB_SUMMONED);

            // 饰品栏遍历
 */
            /*CuriosApi.getCuriosHelper().getEquippedCurios(player).resolve().get()*/
/*            CuriosApi.getCuriosInventory(player).ifPresent(inventory -> {

            });*/

            // 精妙背包遍历
            /*            if (player.tickCount % 20 == 0) {
                BackpackWrapper backpackWrapper = new BackpackWrapper(player.getMainHandItem());
                InventoryHandler inventoryHandler = backpackWrapper.getInventoryHandler();
                for (int i = 0; i < inventoryHandler.getSlots(); i++) {
                    player.sendSystemMessage(Component.literal("" + inventoryHandler.getSlotStack(i)));
                }
            }*/

            // 获取玩家统计信息
/*            ServerPlayer serverPlayer = (ServerPlayer) player;
            Stats.CUSTOM.forEach(c -> {
                if (c.getValue().equals(Stats.WALK_ONE_CM)) {
                    player.sendSystemMessage(Component.literal(c.getName() + " " + serverPlayer.getStats().getValue(c)));
                }
            });*/
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }


}

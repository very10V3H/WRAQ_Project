package fun.wraq.series.instance.series.mushroom;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import fun.wraq.series.gems.GemItems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UnknownGem extends WraqItem {

    public UnknownGem(Properties properties) {
        super(properties.stacksTo(1), true, true);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" Shift右键", ChatFormatting.AQUA,
                "以尝试将背包中的", "生机元素", CustomStyle.styleOfLife, "吞噬", ChatFormatting.LIGHT_PURPLE));
        components.add(Te.s(" 右键", ChatFormatting.AQUA, "可以获取当前",
                "吞噬", ChatFormatting.LIGHT_PURPLE, "成功概率"));
        components.add(Te.s(" 根据吞噬的物品，将有几率使此物转化为", "菌菇宝石", CustomStyle.MUSHROOM_STYLE));
        components.add(Te.s(" 可被", "吞噬", ChatFormatting.RED, "的物品有:"));
        components.add(Te.s("   必须品:", ChatFormatting.AQUA, GemItems.lifeManaGem, " 20%(1)"));
        components.add(Te.s("   1.", ChatFormatting.AQUA, ModItems.LifeElementPiece0, " 0.02% - 至多20%(1000)"));
        components.add(Te.s("   2.", ChatFormatting.AQUA, ModItems.PlainRune, " 2% - 至多30%(15)"));
        components.add(Te.s("   3.", ChatFormatting.AQUA, ModItems.ForestRune, " 2% - 至多30%(15)"));
        components.add(Te.s(" 吞噬失败将仅消耗", "生机元素物品", CustomStyle.styleOfLife, "，不会消耗此物"));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (level.isClientSide || !interactionHand.equals(InteractionHand.MAIN_HAND)) {
            return super.use(level, player, interactionHand);
        }
        double rate = getCurrentInventoryItemSuccessRate(player);
        if (!player.isShiftKeyDown()) {
            sendMSG(player, Te.s("如按当前背包内物品，",
                    "吞噬成功率", ChatFormatting.RED, "为:", String.format("%.2f%%", rate * 100), ChatFormatting.AQUA));
        } else {
            if (rate == 0) {
                sendMSG(player, Te.s("缺少必须品:", GemItems.lifeManaGem));
            } else {
                removeItems(player);
                if (RandomUtils.nextDouble(0, 1) < rate) {
                    List<Item> itemList = List.of(GemItems.MUSHROOM_SPUTTER_GEM.get(),
                            GemItems.MUSHROOM_PARASITISM_GEM.get(), GemItems.MUSHROOM_SPLIT_GEM.get());
                    Item mushroomGem = itemList.get(RandomUtils.nextInt(0, itemList.size()));
                    Compute.playerItemUseWithRecord(player);
                    Compute.formatBroad(Te.s("吞噬", ChatFormatting.RED),
                            Te.s(player, "通过", " 吞噬", ChatFormatting.RED,
                                    "生机元素 ", CustomStyle.styleOfLife, "获得了 ", mushroomGem));
                    InventoryOperation.itemStackGiveWithMSG(player, mushroomGem);
                } else {
                    sendMSG(player, Te.s("吞噬失败", ChatFormatting.RED,
                            "(" + String.format("%.2f%%", rate * 100) + ")", ChatFormatting.GRAY));
                }
            }
        }
        return super.use(level, player, interactionHand);
    }

    private void removeItems(Player player) {
        InventoryOperation.removeItem(player, GemItems.lifeManaGem.get(), 1);
        int count0 = InventoryOperation.itemStackCount(player, ModItems.LifeElementPiece0.get());
        InventoryOperation.removeItem(player, ModItems.LifeElementPiece0.get(), Math.min(1000, count0));
        int count1 = InventoryOperation.itemStackCount(player, ModItems.PlainRune.get());
        InventoryOperation.removeItem(player, ModItems.PlainRune.get(), Math.min(15, count1));
        int count2 = InventoryOperation.itemStackCount(player, ModItems.ForestRune.get());
        InventoryOperation.removeItem(player, ModItems.ForestRune.get(), Math.min(15, count2));
    }

    private double getCurrentInventoryItemSuccessRate(Player player) {
        if (!InventoryOperation.checkPlayerHasItem(player, GemItems.lifeManaGem.get(), 1)) {
            return 0;
        }
        double rate = 0.2;
        rate += Math.min(0.2, 0.0002 * InventoryOperation.itemStackCount(player, ModItems.LifeElementPiece0.get()));
        rate += Math.min(0.3, 0.02 * InventoryOperation.itemStackCount(player, ModItems.PlainRune.get()));
        rate += Math.min(0.3, 0.02 * InventoryOperation.itemStackCount(player, ModItems.ForestRune.get()));
        return rate;
    }

    private static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("吞噬", ChatFormatting.RED), content);
    }

    public static void handleLevelTick(Level level) {
        Vec3 pos = new BlockPos(2006, 143, -1784).getCenter();
        List<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class,
                AABB.ofSize(pos, 1, 1, 1));
        int redMushroomCount = itemEntities
                .stream().filter(itemEntity -> itemEntity.getItem().is(MushroomItems.RED_MUSHROOM.get()))
                .mapToInt(itemEntity -> itemEntity.getItem().getCount())
                .sum();
        int brownMushroomCount = itemEntities
                .stream().filter(itemEntity -> itemEntity.getItem().is(MushroomItems.BROWN_MUSHROOM.get()))
                .mapToInt(itemEntity -> itemEntity.getItem().getCount())
                .sum();
        int lifeManaGem = itemEntities
                .stream().filter(itemEntity -> itemEntity.getItem().is(GemItems.lifeManaGem.get()))
                .mapToInt(itemEntity -> itemEntity.getItem().getCount())
                .sum();

        if (redMushroomCount >= 16 && brownMushroomCount >= 320 && lifeManaGem >= 1) {
            itemEntities.forEach(itemEntity -> itemEntity.remove(Entity.RemovalReason.KILLED));
            Compute.getNearEntity(level, pos, Player.class, 6)
                    .stream().map(entity -> (Player) entity)
                    .forEach(player -> sendMSG(player, Te.s("炼药锅传来了响动。。")));
            double randomValue = RandomUtils.nextDouble(0, 1);
            if (randomValue < 0.33) {
                ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, level);
                if (randomValue < 0.25) {
                    itemEntity.setItem(new ItemStack(MushroomItems.UNKNOWN_GEM.get()));
                    MySound.soundToNearPlayer(level, pos, SoundEvents.BREWING_STAND_BREW);
                    Compute.getNearEntity(level, pos, Player.class, 6)
                            .stream().map(entity -> (Player) entity)
                            .forEach(player -> sendMSG(player, Te.s("一股诡异的气味从中传来。。(⊙o⊙)…这是？")));
                } else {
                    itemEntity.setItem(new ItemStack(MushroomItems.MUSHROOM_CURIO.get()));
                    MySound.soundToNearPlayer(level, pos, SoundEvents.BREWING_STAND_BREW);
                    Compute.getNearEntity(level, pos, Player.class, 6)
                            .stream().map(entity -> (Player) entity)
                            .forEach(player -> sendMSG(player, Te.s("阵阵香气从炼药锅中传来。。(╯▽╰ )好香~~")));
                }
                itemEntity.setPickUpDelay(0);
                itemEntity.moveTo(pos);
                level.addFreshEntity(itemEntity);
            } else {
                MySound.soundToNearPlayer(level, pos, SoundEvents.FIRE_EXTINGUISH);
                Compute.getNearEntity(level, pos, Player.class, 6)
                        .stream().map(entity -> (Player) entity)
                        .forEach(player -> sendMSG(player, Te.s("一股烧焦的气味从中传来。。好难闻啊")));
            }
        } else {
            if (Tick.get() % 200 == 1 && (redMushroomCount > 0 || brownMushroomCount > 0 || lifeManaGem > 0)) {
                Compute.getNearEntity(level, pos, Player.class, 6)
                        .stream().map(entity -> (Player) entity)
                        .forEach(player -> sendMSG(player, Te.s("炼药锅似乎没有反应。。是缺少了什么物品么？")));
            }
        }
    }
}
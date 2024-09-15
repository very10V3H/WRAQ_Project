package com.very.wraq.Items.KillPaper;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.common.util.Utils;
import com.very.wraq.events.mob.chapter1.*;
import com.very.wraq.events.mob.chapter2.*;
import com.very.wraq.events.mob.chapter3_nether.MagmaSpawnController;
import com.very.wraq.events.mob.chapter3_nether.NetherSkeletonSpawnController;
import com.very.wraq.events.mob.chapter3_nether.PiglinSpawnController;
import com.very.wraq.events.mob.chapter3_nether.WitherSkeletonSpawnController;
import com.very.wraq.events.mob.chapter4_end.EnderManSpawnController;
import com.very.wraq.events.mob.chapter4_end.EndermiteSpawnController;
import com.very.wraq.events.mob.chapter4_end.ShulkerSpawnController;
import com.very.wraq.events.mob.chapter5.BloodManaSpawnController;
import com.very.wraq.events.mob.chapter5.EarthManaSpawnController;
import com.very.wraq.events.mob.chapter5.PillagerSpawnController;
import com.very.wraq.events.mob.chapter5.SakuraMobSpawnController;
import com.very.wraq.events.mob.chapter6_castle.BeaconSpawnController;
import com.very.wraq.events.mob.chapter6_castle.BlazeSpawnController;
import com.very.wraq.events.mob.chapter6_castle.TreeSpawnController;
import com.very.wraq.events.mob.loot.RandomLootEquip;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KillPaper extends Item {

    public KillPaper(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack itemStack = player.getMainHandItem();
            List<ItemAndRate> list = getDropList(itemStack);
            if (!list.isEmpty()) {
                list.forEach(itemAndRate -> {
                    RandomLootEquip.handleItemAndRate(itemAndRate);

                    try {
                        itemAndRate.send(player, 64);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            Compute.playerItemUseWithRecord(player);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        CompoundTag tag = stack.getTagElement(Utils.MOD_ID);
        if (tag != null) {
            if (tag.contains(killPaperType)) {
                String type = tag.getString(killPaperType);
                components.add(Component.literal("使用以征讨64只 ").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(type).withStyle(ChatFormatting.RED)));
            }
        }
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    public static String killPaperType = "killPaperType";

    public static List<ItemAndRate> getDropList(ItemStack itemStack) {
        if (itemStack.is(ModItems.killPaper.get())) {
            CompoundTag tag = itemStack.getTagElement(Utils.MOD_ID);
            if (tag != null) {
                if (tag.contains(killPaperType)) {
                    String type = tag.getString(killPaperType);
                    Map<String, List<ItemAndRate>> map = getDropListMap();
                    return map.get(type);
                }
            }
        }
        return new ArrayList<>();
    }

    public static Map<String, List<ItemAndRate>> getDropListMap() {
        return new HashMap<>() {{
            put("焰芒虫", FireLightSpawnController.getDropList());
            put("森林僵尸", ForestZombieSpawnController.getDropList());
            put("河流故灵", LakeDrownSpawnController.getDropList());
            put("被遗忘的矿工", MineSkeletonSpawnController.getDropList());
            put("平原僵尸", PlainZombieSpawnController.getDropList());
            put("骇狼", DreadHoundSpawnController.getDropList());
            put("唤魔者", EvokerSpawnController.getDropList());
            put("神殿守卫", GuardianSpawnController.getDropList());
            put("脆弱的岩灵", HuskSpawnController.getDropList());
            put("雷光灯塔驻卫", LightningZombieController.getDropList());
            put("伐木工", LumberJackSpawnController.getDropList());
            put("炽魂", SearedSpiritSpawnController.getDropList());
            put("天空城的不速之客", SkyVexSpawnController.getDropList());
            put("雨林蜘蛛", SpiderSpawnController.getDropList());
            put("怀德风骨", WindSkeletonSpawnController.getDropList());
            put("腥月血灵", BloodManaSpawnController.getDropList());
            put("地蕴蓝灵", EarthManaSpawnController.getDropList());
            put("海盗", PillagerSpawnController.getDropList());
            put("樱灵", SakuraMobSpawnController.getDropList());
            put("熔岩聚合物", MagmaSpawnController.getDropList());
            put("下界骷髅", NetherSkeletonSpawnController.getDropList());
            put("猪灵", PiglinSpawnController.getDropList());
            put("凋零骷髅", WitherSkeletonSpawnController.getDropList());
            put("史莱姆", SlimeSpawnController.getDropList());
            put("终界使者", EnderManSpawnController.getDropList());
            put("寂域灵螨", EndermiteSpawnController.getDropList());
            put("寂域遗骸", ShulkerSpawnController.getDropList());
            put(BeaconSpawnController.mobName, BeaconSpawnController.getDropList());
            put(BlazeSpawnController.mobName, BlazeSpawnController.getDropList());
            put(TreeSpawnController.mobName, TreeSpawnController.getDropList());
        }};
    }
}

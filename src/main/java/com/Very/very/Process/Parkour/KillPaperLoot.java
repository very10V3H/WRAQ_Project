package com.Very.very.Process.Parkour;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KillPaperLoot extends Item {
    public KillPaperLoot(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使用以随机抽取一张征讨券！").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            if (KillPaperList.isEmpty()) setKillPaperList();

            try {
                Compute.PlayerItemUseWithRecord(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Random random = new Random();
            ItemStack itemStack = new ItemStack(KillPaperList.get(random.nextInt(KillPaperList.size())));
            Compute.FormatBroad(level,Component.literal("跑酷").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfFlexible),
                    Component.literal("").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal("通过 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(ModItems.KillPaperLoot.get().getDefaultInstance().getDisplayName()).
                            append(Component.literal(" 获得了: ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(itemStack.getDisplayName()));

            try {
                Compute.ItemStackGive(player,itemStack);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return super.use(level, player, interactionHand);
    }

    public static List<Item> KillPaperList = new ArrayList<>();
    public static void setKillPaperList() {
        KillPaperList.add(ModItems.PlainZombieKillPaper.get());
        KillPaperList.add(ModItems.ForestSkeletonKillPaper.get());
        KillPaperList.add(ModItems.ForestZombieKillPaper.get());
        KillPaperList.add(ModItems.FieldZombieKillPaper.get());
        KillPaperList.add(ModItems.LakeDrownedKillPaper.get());
        KillPaperList.add(ModItems.VolcanoBlazeKillPaper.get());
        KillPaperList.add(ModItems.MineZombieKillPaper.get());
        KillPaperList.add(ModItems.MineSkeletonKillPaper.get());
        KillPaperList.add(ModItems.SnowStrayKillPaper.get());
        KillPaperList.add(ModItems.SkyVexKillPaper.get());
        KillPaperList.add(ModItems.EvokerKillPaper.get());
        KillPaperList.add(ModItems.EvokerMasterKillPaper.get());
        KillPaperList.add(ModItems.SeaGuardianKillPaper.get());
        KillPaperList.add(ModItems.LightingZombieKillPaper.get());
        KillPaperList.add(ModItems.HuskKillPaper.get());
        KillPaperList.add(ModItems.KazeZombieKillPaper.get());
        KillPaperList.add(ModItems.SpiderKillPaper.get());
        KillPaperList.add(ModItems.SilverFishKillPaper.get());
        KillPaperList.add(ModItems.WitherSkeletonKillPaper.get());
        KillPaperList.add(ModItems.PiglinKillPaper.get());
        KillPaperList.add(ModItems.NetherSkeletonKillPaper.get());
        KillPaperList.add(ModItems.NetherMagmaKillPaper.get());
        KillPaperList.add(ModItems.EnderManKillPaper.get());
        KillPaperList.add(ModItems.SakuraMobKillPaper.get());
        KillPaperList.add(ModItems.ScarecrowKillPaper.get());
        KillPaperList.add(ModItems.MineWorkerKillPaper.get());
        KillPaperList.add(ModItems.ShipWorkerKillPaper.get());
        KillPaperList.add(ModItems.IceHunterKillPaper.get());
        KillPaperList.add(ModItems.EarthManaKillPaper.get());
        KillPaperList.add(ModItems.BloodManaKillPaper.get());
        KillPaperList.add(ModItems.SlimeKillPaper.get());
    }

}

package com.very.wraq.process.parkour;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.registry.ModItems;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KillPaperLoot extends Item {
    public KillPaperLoot(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使用以随机抽取一张征讨券！").withStyle(ChatFormatting.LIGHT_PURPLE));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            if (killPaperList.isEmpty()) setKillPaperList();

            try {
                Compute.PlayerItemUseWithRecord(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Random random = new Random();
            ItemStack itemStack = new ItemStack(killPaperList.get(random.nextInt(killPaperList.size())));
            Compute.FormatBroad(level,Component.literal("跑酷").withStyle(CustomStyle.styleOfFlexible),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(player.getDisplayName()).
                            append(Component.literal("通过 ").withStyle(ChatFormatting.WHITE)).
                            append(ModItems.KillPaperLoot.get().getDefaultInstance().getDisplayName()).
                            append(Component.literal(" 获得了: ").withStyle(ChatFormatting.WHITE)).
                            append(itemStack.getDisplayName()));

            try {
                Compute.ItemStackGive(player,itemStack);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return super.use(level, player, interactionHand);
    }

    public static List<Item> killPaperList = new ArrayList<>();
    public static void setKillPaperList() {
        Item[] items = {
                ModItems.PlainZombieKillPaper.get(), ModItems.ForestSkeletonKillPaper.get(),
                ModItems.ForestZombieKillPaper.get(), ModItems.FieldZombieKillPaper.get(),
                ModItems.LakeDrownedKillPaper.get(), ModItems.VolcanoBlazeKillPaper.get(),
                ModItems.MineZombieKillPaper.get(), ModItems.MineSkeletonKillPaper.get(),
                ModItems.SnowStrayKillPaper.get(), ModItems.SkyVexKillPaper.get(),
                ModItems.EvokerKillPaper.get(), ModItems.EvokerMasterKillPaper.get(),
                ModItems.SeaGuardianKillPaper.get(), ModItems.LightingZombieKillPaper.get(),
                ModItems.HuskKillPaper.get(), ModItems.KazeZombieKillPaper.get(),
                ModItems.SpiderKillPaper.get(), ModItems.SilverFishKillPaper.get(),
                ModItems.WitherSkeletonKillPaper.get(), ModItems.PiglinKillPaper.get(),
                ModItems.NetherSkeletonKillPaper.get(), ModItems.NetherMagmaKillPaper.get(),
                ModItems.EnderManKillPaper.get(), ModItems.SakuraMobKillPaper.get(),
                ModItems.ScarecrowKillPaper.get(), ModItems.MineWorkerKillPaper.get(),
                ModItems.ShipWorkerKillPaper.get(), ModItems.IceHunterKillPaper.get(),
                ModItems.EarthManaKillPaper.get(), ModItems.BloodManaKillPaper.get(),
                ModItems.SlimeKillPaper.get(), ModItems.BeaconKillPaper.get(),
                ModItems.TreeKillPaper.get(), ModItems.BlazeKillPaper.get(),

                ModItems.StarKillPaper.get(), ModItems.LifeElementKillPaper.get(),
                ModItems.WaterElementKillPaper.get(), ModItems.FireElementKillPaper.get(),
                ModItems.StoneElementKillPaper.get(), ModItems.IceElementKillPaper.get(),
                ModItems.LightningElementKillPaper.get(), ModItems.WindElementKillPaper.get(),
                ModItems.ShulkerKillPaper.get(), ModItems.EnderMiteKillPaper.get()
        };
        killPaperList.addAll(Arrays.asList(items));
    }

}

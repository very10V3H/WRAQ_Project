package com.very.wraq.process.system.instance;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ItemAndRate;
import com.very.wraq.events.mob.instance.instances.*;
import com.very.wraq.process.system.teamInstance.instances.blackCastle.NewCastleInstance;
import com.very.wraq.render.toolTip.CustomStyle;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MopUpPaper extends Item {

    private final String instanceName;

    public MopUpPaper(Properties p_41383_, String instanceName) {
        super(p_41383_);
        this.instanceName = instanceName;
    }

    public static class InstanceName {
        public static final String Castle = "Castle";
        public static final String CastleSecondFloor = "CastleSecondFloor";
        public static final String Devil = "Devil";
        public static final String IceKnight = "IceKnight";
        public static final String Lightning = "Lightning";
        public static final String Main1Boss = "Main1Boss";
        public static final String Moon = "Moon";
        public static final String Nether = "Nether";
        public static final String Plain = "Plain";
        public static final String PurpleIronKnight = "PurpleIronKnight";
        public static final String SakuraBoss = "SakuraBoss";
        public static final String TabooDevil = "TabooDevil";
    }

    private static final Map<String, Component> instanceNameMap = new HashMap<>() {{
        put(InstanceName.Castle, Component.literal("暗黑城堡").withStyle(CustomStyle.styleOfCastle));
        put(InstanceName.CastleSecondFloor, Component.literal("暗黑城堡二层").withStyle(CustomStyle.styleOfCastleCrystal));
        put(InstanceName.Devil, Component.literal("旧世复生魔王").withStyle(CustomStyle.styleOfDemon));
        put(InstanceName.IceKnight, Component.literal("冰霜骑士").withStyle(CustomStyle.styleOfIce));
        put(InstanceName.Lightning, Component.literal("唤雷塔").withStyle(CustomStyle.styleOfLightning));
        put(InstanceName.Main1Boss, Component.literal("四元方块").withStyle(ChatFormatting.RED));
        put(InstanceName.Moon, Component.literal("尘月宫").withStyle(CustomStyle.styleOfMoon));
        put(InstanceName.Nether, Component.literal("下界征讨").withStyle(CustomStyle.styleOfNether));
        put(InstanceName.Plain, Component.literal("普莱尼").withStyle(CustomStyle.styleOfPlain));
        put(InstanceName.PurpleIronKnight, Component.literal("紫晶骑士").withStyle(CustomStyle.styleOfPurpleIron));
        put(InstanceName.SakuraBoss, Component.literal("突见忍").withStyle(CustomStyle.styleOfSakura));
        put(InstanceName.TabooDevil, Component.literal("新世禁法魔物").withStyle(CustomStyle.styleOfDemon));
    }};
    private static final Map<String, Integer> instanceSerialNumMap = new HashMap<>() {{
        put(InstanceName.Castle, 0);
        put(InstanceName.CastleSecondFloor, 1);
        put(InstanceName.Devil, 2);
        put(InstanceName.IceKnight, 3);
        put(InstanceName.Lightning, 4);
        put(InstanceName.Main1Boss, 5);
        put(InstanceName.Moon, 6);
        put(InstanceName.Nether, 7);
        put(InstanceName.Plain, 8);
        put(InstanceName.PurpleIronKnight, 9);
        put(InstanceName.SakuraBoss, 10);
        put(InstanceName.TabooDevil, 11);
    }};

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            List<ItemAndRate> rewardList = new ArrayList<>();
            switch (instanceSerialNumMap.get(instanceName)) {
                case 0 -> rewardList = NewCastleInstance.getRewardList();
                case 2 -> rewardList = DevilInstance.getRewardList();
                case 3 -> rewardList = IceInstance.getRewardList();
                case 6 -> rewardList = MoonInstance.getRewardList();
                case 8 -> rewardList = PlainInstance.getRewardList();
                case 9 -> rewardList = PurpleIronInstance.getRewardList();
                case 10 -> rewardList = SakuraBossInstance.getRewardList();
            }

            rewardList.forEach(itemAndRate -> {
                try {
                    itemAndRate.send(player, 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            Compute.playerItemUseWithRecord(player);

        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal(" 使用以扫荡副本: ").withStyle(ChatFormatting.RED).
                append(instanceNameMap.get(instanceName)));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}

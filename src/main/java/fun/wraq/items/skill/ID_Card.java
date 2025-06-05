package fun.wraq.items.skill;

import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SkillPackets.SkillRequestC2SPacket;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.networking.reputationMission.PlanMissionInfoS2CPacket;
import fun.wraq.process.func.plan.DailySupply;
import fun.wraq.process.func.plan.networking.mission.PlanMission;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.util.List;

public class ID_Card extends Item {
    public ID_Card(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {;
        components.add(Te.s(" 用于使用一些常用功能"));
        components.add(Te.s(" 如", "图鉴/任务/市场/vp商店/点数配置", CustomStyle.styleOfWorld));
        super.appendHoverText(p_41421_, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (level.isClientSide) {
            ModNetworking.sendToServer(new SkillRequestC2SPacket());
        }
        if (!level.isClientSide) {
            String name = player.getName().getString();
            ServerPlayer serverPlayer = (ServerPlayer) player;
            ModNetworking.sendToClient(new ScreenSetS2CPacket(3), serverPlayer);
            ModNetworking.sendToClient(new PlanMissionInfoS2CPacket(
                    PlanMission.planMissionContentMap.getOrDefault(name, Items.AIR.getDefaultInstance()),
                    PlanMission.planMissionContentCountMap.getOrDefault(name, 0),
                    PlanMission.planMissionStartTimeMap.getOrDefault(name, ""),
                    PlanMission.planMissionAllowRequestTimeMap.getOrDefault(name, "")), serverPlayer);
            try {
                DailySupply.sendStatusToClient(player);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}

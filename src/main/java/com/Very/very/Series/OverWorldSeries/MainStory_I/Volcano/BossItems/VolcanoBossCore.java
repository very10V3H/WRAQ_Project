package com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.BossItems;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.EntropyPackets.EntropyS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VolcanoBossCore extends Item {
    public VolcanoBossCore(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.MaterialLevelDescription.Rare(components);
        components.add(Component.literal(" "));
        components.add(Component.literal("熔岩次元能量核心").withStyle(Utils.styleOfVolcano));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-VolcanoBoss").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide) {
            CompoundTag data = player.getPersistentData();
            if (data.contains(StringUtils.Entropy.Volcano)) data.putInt(StringUtils.Entropy.Volcano,data.getInt(StringUtils.Entropy.Volcano) + 1);
            else data.putInt(StringUtils.Entropy.Volcano,1);
            Compute.FormatMSGSend(player,Component.literal("次元能量").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEntropy),
                    Component.literal("你的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                            append(Component.literal("熔岩次元能量").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfVolcano)).
                            append(Component.literal("得到了提升。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("[" + data.getInt(StringUtils.Entropy.Volcano) + "]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
            ModNetworking.sendToClient(new EntropyS2CPacket(data.getInt(StringUtils.Entropy.Forest),data.getInt(StringUtils.Entropy.Volcano)),(ServerPlayer) player);

        }
        return super.use(level, player, interactionHand);
    }
}

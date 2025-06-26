package fun.wraq.events.modules;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.StringUtils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.SkillPackets.SkillImageS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class HurtEventModule {

    public static void SabreDamage(Player player, Mob monster) {
        CompoundTag data = player.getPersistentData();
        if (data.contains("QuartzSabreDamage") && data.getDouble("QuartzSabreDamage") > 0) {
            double QuartzSabreDamage = data.getDouble("QuartzSabreDamage");
            data.putDouble("QuartzSabreDamage", 0);
            monster.getPersistentData().putInt("QuartzSabrePos", -1);
            Compute.playerHeal(player, QuartzSabreDamage);
            if (!data.contains("IgnoreFight") || (!data.getBoolean("IgnoreFight")))
                Compute.sendFormatMSG(player, Component.literal("战斗").withStyle(ChatFormatting.DARK_RED),
                        Component.literal("华尔兹提供了额外伤害").append(Component.literal(String.format("%.2f", QuartzSabreDamage)).withStyle(ChatFormatting.GOLD)));
            data.putInt("QuartzSabreSpeedUp", 40);
        }
    }

    public static void SwordSkill2(CompoundTag data, Player player) {
        int TickCount = Tick.get();
        if (Compute.getSwordSkillLevel(data, 2) > 0) {
            data.putInt(StringUtils.SwordSkillNum.Skill2, TickCount + 200);
            ModNetworking.sendToClient(new SkillImageS2CPacket(3, 10, 10, 0, 0), (ServerPlayer) player);
        }
    }

    public static void BowSkill2(CompoundTag data, Player player) {
        int TickCount = Tick.get();
        if (Compute.getBowSkillLevel(data, 2) > 0) {
            data.putInt(StringUtils.BowSkillNum.Skill2, TickCount + 200);
            ModNetworking.sendToClient(new SkillImageS2CPacket(3, 10, 10, 0, 1), (ServerPlayer) player);

        }
    }

    public static void ManaSkill2(CompoundTag data, Player player) {
        int TickCount = Tick.get();
        if (Compute.getManaSkillLevel(data, 2) > 0) {
            data.putInt(StringUtils.ManaSkillNum.Skill2, TickCount + 200);
            ModNetworking.sendToClient(new SkillImageS2CPacket(3, 10, 10, 0, 2), (ServerPlayer) player);

        }
    }
}

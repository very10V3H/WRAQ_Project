package com.very.wraq.coreAttackModule;

import com.very.wraq.customized.players.sceptre.Black_Feisa_.BlackFeisaCurios1;
import com.very.wraq.customized.players.sceptre.FengXiaoju.FengXiaoJuCurios1;
import com.very.wraq.customized.players.sword.ZuoSI.ZuoSiCurios;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.series.instance.Castle.CastleManaArmor;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaAttackRequestC2SPacket {

    public ManaAttackRequestC2SPacket() {
    }

    public ManaAttackRequestC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            Player player = (Player) serverPlayer;
            int tick = serverPlayer.getServer().getTickCount();
            if (Utils.PlayerManaAttackTime.containsKey(serverPlayer) && tick - Utils.PlayerManaAttackTime.get(serverPlayer) < 9) return;
            if (Utils.LiuLiXian != null && Utils.LiuLiXian.equals(player) && Utils.LiuLiXianCore) {
                Utils.LiuLiXianManaAttackDelay = 3;
            }
            BlackFeisaCurios1.Passive2(player);
            FengXiaoJuCurios1.ExAttackCount(player);
            if (Compute.ManaSkillLevelGet(player.getPersistentData(), 10) == 10) CastleManaArmor.NormalAttack(player); //
            Utils.PlayerManaAttackTime.put(serverPlayer,tick);
            if (ZuoSiCurios.IsPlayer(player)) return;

            Item sceptre = player.getMainHandItem().getItem();
            if (!Utils.SceptreTag.containsKey(sceptre)) return;
            if (sceptre instanceof WraqSceptre wraqSceptre) wraqSceptre.shoot(player);
        });
        return true;
    }
}

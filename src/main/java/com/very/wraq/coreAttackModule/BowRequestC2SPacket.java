package com.very.wraq.coreAttackModule;

import com.very.wraq.customized.players.bow.Hgj.HgjCurios;
import com.very.wraq.customized.players.bow.Shao_Feng.ShaoFengCurios;
import com.very.wraq.customized.players.bow.littleart.LittleartCurios;
import com.very.wraq.customized.players.sword.ZuoSI.ZuoSiCurios;
import com.very.wraq.customized.uniform.bow.BowCurios1;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.series.instance.Castle.CastleSwiftArmor;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BowRequestC2SPacket {

    public BowRequestC2SPacket() {}

    public BowRequestC2SPacket(FriendlyByteBuf buf) {}

    public void toBytes(FriendlyByteBuf buf) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            int tick = serverPlayer.getServer().getTickCount();
            if (Utils.PlayerArrowAttackTime.containsKey(serverPlayer) && tick - Utils.PlayerArrowAttackTime.get(serverPlayer) < 9) return;
            CastleSwiftArmor.NormalAttack(serverPlayer);
            Utils.PlayerArrowAttackTime.put(serverPlayer,tick);
            if (ZuoSiCurios.IsPlayer(serverPlayer) || ShaoFengCurios.isOn(serverPlayer)
                    || LittleartCurios.isOn(serverPlayer)) return;
            HgjCurios.PlayerArrowAttack(serverPlayer);
            BowCurios1.playerShoot(serverPlayer);

            Item bow = serverPlayer.getMainHandItem().getItem();
            if (!Utils.BowTag.containsKey(bow)) return;
            if (bow instanceof WraqBow wraqBow) wraqBow.shoot(serverPlayer);
        });
        return true;
    }
}

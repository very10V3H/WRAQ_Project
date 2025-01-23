package fun.wraq.process.system.skill.skillv2.network;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.system.skill.skillv2.SkillV2;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillV2PlayerTryToChooseProfessionTypeC2SPacket {

    private final int professionType;

    public SkillV2PlayerTryToChooseProfessionTypeC2SPacket(int professionType) {
        this.professionType = professionType;
    }

    public SkillV2PlayerTryToChooseProfessionTypeC2SPacket(FriendlyByteBuf buf) {
        this.professionType = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(professionType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) {
                return;
            }
            if (Compute.playerIsInBattle(player)) {
                SkillV2.sendMSG(player, Te.s("请脱离", "战斗状态", CustomStyle.styleOfRed, "5s后重试"));
                return;
            }
            SkillV2.setProfessionType(player, professionType);
            SkillV2.sendMSG(player, Te.s("你选择了 ", SkillV2.getProfessionName(professionType),
                    " - 技能组", CustomStyle.styleOfWorld));
            SkillV2.sendInfoToClient(player);
        });
        return true;
    }
}

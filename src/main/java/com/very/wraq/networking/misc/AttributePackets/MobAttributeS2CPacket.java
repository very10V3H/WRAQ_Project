package com.very.wraq.networking.misc.AttributePackets;

import com.very.wraq.common.Utils.ClientUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MobAttributeS2CPacket {
    private final int id;
    private final double mobAttack;
    private final double mobDefence;
    private final double mobManaDefence;
    private final double mobCritRate;
    private final double mobDefencePenetration;
    private final double mobDefencePenetration0;
    private final double mobHealthSteal;
    private final double mobCritDamage;
    private final String mobElementType;
    private final double mobElementValue;

    public MobAttributeS2CPacket(int id, double mobAttack, double mobDefence, double mobManaDefence, double mobCritRate,
                                 double mobDefencePenetration, double mobDefencePenetration0, double mobHealthSteal,
                                 double mobCritDamage, String mobElementType, double mobElementValue) {
        this.id = id;
        this.mobAttack = mobAttack;
        this.mobDefence = mobDefence;
        this.mobManaDefence = mobManaDefence;
        this.mobCritRate = mobCritRate;
        this.mobDefencePenetration = mobDefencePenetration;
        this.mobDefencePenetration0 = mobDefencePenetration0;
        this.mobHealthSteal = mobHealthSteal;
        this.mobCritDamage = mobCritDamage;
        this.mobElementType = mobElementType;
        this.mobElementValue = mobElementValue;
    }

    public MobAttributeS2CPacket(FriendlyByteBuf buf) {
        this.id = buf.readInt();
        this.mobAttack = buf.readDouble();
        this.mobDefence = buf.readDouble();
        this.mobManaDefence = buf.readDouble();
        this.mobCritRate = buf.readDouble();
        this.mobDefencePenetration = buf.readDouble();
        this.mobDefencePenetration0 = buf.readDouble();
        this.mobHealthSteal = buf.readDouble();
        this.mobCritDamage = buf.readDouble();
        this.mobElementType = buf.readUtf();
        this.mobElementValue = buf.readDouble();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.id);
        buf.writeDouble(this.mobAttack);
        buf.writeDouble(this.mobDefence);
        buf.writeDouble(this.mobManaDefence);
        buf.writeDouble(this.mobCritRate);
        buf.writeDouble(this.mobDefencePenetration);
        buf.writeDouble(this.mobDefencePenetration0);
        buf.writeDouble(this.mobHealthSteal);
        buf.writeDouble(this.mobCritDamage);
        buf.writeUtf(this.mobElementType);
        buf.writeDouble(this.mobElementValue);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientUtils.mobId = id;
            ClientUtils.mobAttack = mobAttack;
            ClientUtils.mobDefence = mobDefence;
            ClientUtils.mobManaDefence = mobManaDefence;
            ClientUtils.mobCritRate = mobCritRate;
            ClientUtils.mobDefencePenetration = mobDefencePenetration;
            ClientUtils.mobDefencePenetration0 = mobDefencePenetration0;
            ClientUtils.mobHealthSteal = mobHealthSteal;
            ClientUtils.mobCritDamage = mobCritDamage;
            ClientUtils.mobElementType = mobElementType;
            ClientUtils.mobElementValue = mobElementValue;
        });
        return true;
    }
}

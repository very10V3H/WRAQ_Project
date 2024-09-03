package com.very.wraq.networking.misc.TeamPackets;

import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.util.struct.ClientPlayerTeam;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class TeamInfoS2CPacket {


    private final String UUID;
    private final String UUID1;
    private final String UUID2;
    private final String UUID3;
    private final String UUID4;
    private final String UUID5;
    private final String UUID6;
    private final String UUID7;

    private final Component component;
    private final Component component1;
    private final Component component2;
    private final Component component3;
    private final Component component4;
    private final Component component5;
    private final Component component6;
    private final Component component7;

    private final String teamName;
    private final int Num;

    public TeamInfoS2CPacket(String teamName,
                             String uuid, String uuid1, String uuid2, String uuid3,
                             String uuid4, String uuid5, String uuid6, String uuid7,
                             Component component, Component component1, Component component2, Component component3,
                             Component component4, Component component5, Component component6, Component component7,
                             int Num) {
        this.teamName = teamName;
        this.UUID = uuid;
        this.UUID1 = uuid1;
        this.UUID2 = uuid2;
        this.UUID3 = uuid3;
        this.UUID4 = uuid4;
        this.UUID5 = uuid5;
        this.UUID6 = uuid6;
        this.UUID7 = uuid7;
        this.component = component;
        this.component1 = component1;
        this.component2 = component2;
        this.component3 = component3;
        this.component4 = component4;
        this.component5 = component5;
        this.component6 = component6;
        this.component7 = component7;
        this.Num = Num;
    }

    public TeamInfoS2CPacket(FriendlyByteBuf buf) {
        this.teamName = buf.readUtf();
        this.UUID = buf.readUtf();
        this.UUID1 = buf.readUtf();
        this.UUID2 = buf.readUtf();
        this.UUID3 = buf.readUtf();
        this.UUID4 = buf.readUtf();
        this.UUID5 = buf.readUtf();
        this.UUID6 = buf.readUtf();
        this.UUID7 = buf.readUtf();
        this.component = buf.readComponent();
        this.component1 = buf.readComponent();
        this.component2 = buf.readComponent();
        this.component3 = buf.readComponent();
        this.component4 = buf.readComponent();
        this.component5 = buf.readComponent();
        this.component6 = buf.readComponent();
        this.component7 = buf.readComponent();
        this.Num = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(teamName);
        buf.writeUtf(this.UUID);
        buf.writeUtf(this.UUID1);
        buf.writeUtf(this.UUID2);
        buf.writeUtf(this.UUID3);
        buf.writeUtf(this.UUID4);
        buf.writeUtf(this.UUID5);
        buf.writeUtf(this.UUID6);
        buf.writeUtf(this.UUID7);

        buf.writeComponent(this.component);
        buf.writeComponent(this.component1);
        buf.writeComponent(this.component2);
        buf.writeComponent(this.component3);
        buf.writeComponent(this.component4);
        buf.writeComponent(this.component5);
        buf.writeComponent(this.component6);
        buf.writeComponent(this.component7);

        buf.writeInt(Num);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            List<String> playerList = new ArrayList<>() {{
                add(UUID);
            }};
            if (!Objects.equals(UUID1, "")) playerList.add(UUID1);
            if (!Objects.equals(UUID2, "")) playerList.add(UUID2);
            if (!Objects.equals(UUID3, "")) playerList.add(UUID3);
            if (!Objects.equals(UUID4, "")) playerList.add(UUID4);
            if (!Objects.equals(UUID5, "")) playerList.add(UUID5);
            if (!Objects.equals(UUID6, "")) playerList.add(UUID6);
            if (!Objects.equals(UUID7, "")) playerList.add(UUID7);


            List<Component> playerDisplayNameList = new ArrayList<>() {{
                add(component);
            }};
            if (!component1.equals(Component.literal(""))) playerDisplayNameList.add(component1);
            if (!component2.equals(Component.literal(""))) playerDisplayNameList.add(component2);
            if (!component3.equals(Component.literal(""))) playerDisplayNameList.add(component3);
            if (!component4.equals(Component.literal(""))) playerDisplayNameList.add(component4);
            if (!component5.equals(Component.literal(""))) playerDisplayNameList.add(component5);
            if (!component6.equals(Component.literal(""))) playerDisplayNameList.add(component6);
            if (!component7.equals(Component.literal(""))) playerDisplayNameList.add(component7);

            ClientPlayerTeam clientPlayerTeam = new ClientPlayerTeam(this.teamName,
                    playerList, playerDisplayNameList);

            playerList.forEach(s -> {
                ClientUtils.clientPlayerTeamMap.put(s, clientPlayerTeam);
            });

        });
        return true;
    }
}

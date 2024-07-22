package com.very.wraq.common.Utils.Struct;

import net.minecraft.network.chat.Component;

public class PlayerName {

    private String Player;
    private Component DisPlayerName;

    public PlayerName(String PlayerName, Component PlayerDisPlayName) {
        this.Player = PlayerName;
        this.DisPlayerName = PlayerDisPlayName;
    }

    public void setPlayer(String player) {
        Player = player;
    }

    public Component getDisPlayerName() {
        return DisPlayerName;
    }

    public String getPlayer() {
        return Player;
    }

    public void setDisPlayerName(Component disPlayerName) {
        DisPlayerName = disPlayerName;
    }

}

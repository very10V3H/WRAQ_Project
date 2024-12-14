package fun.wraq.common.util.struct;

import fun.wraq.common.fast.Tick;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerTeam {

    private List<String> playerList;
    private String TeamName;

    public PlayerTeam(List<String> playerList, String teamName) {
        this.playerList = playerList;
        this.TeamName = teamName;
    }

    public List<Player> getPlayerList() {
        List<Player> players = new ArrayList<>();
        playerList.forEach(name -> {
            ServerPlayer serverPlayer = Tick.server.getPlayerList().getPlayerByName(name);
            if (serverPlayer != null) {
                players.add(serverPlayer);
            }
        });
        return players;
    }

    public Player getTeamLeader() {
        return getPlayerList().get(0);
    }

    public void removePlayer(Player player) {
        playerList.removeIf(name -> name.equals(player.getName().getString()));
    }

    public void addPlayer(Player player) {
        playerList.add(player.getName().getString());
    }

    public int teamPlayerNum() {
        return playerList.size();
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public boolean containPlayer(Player player) {
        return playerList.contains(player.getName().getString());
    }
}

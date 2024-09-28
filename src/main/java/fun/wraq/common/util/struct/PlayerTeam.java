package fun.wraq.common.util.struct;

import net.minecraft.world.entity.player.Player;

import java.util.List;

public class PlayerTeam {

    private List<Player> playerList;
    private String TeamName;

    public PlayerTeam(List<Player> playerList, String teamName) {
        this.playerList = playerList;
        this.TeamName = teamName;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public Player getTeamLeader() {
        return playerList.get(0);
    }

    public void removePlayer(Player player) {
        playerList.remove(player);
    }

    public void addPlayer(Player player) {
        playerList.add(player);
    }

    public void setTeamLeader(Player player) {
        playerList.set(0, player);
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
        return playerList.contains(player);
    }
}

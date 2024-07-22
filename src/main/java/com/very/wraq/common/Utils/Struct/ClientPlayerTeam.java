package com.very.wraq.common.Utils.Struct;

import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ClientPlayerTeam {
    private List<String> MemberNameList = new ArrayList<>();
    private List<Component> MemberDisplayNameList = new ArrayList<>();
    private String TeamName;

    public ClientPlayerTeam(String teamName, List<String> memberNameList, List<Component> memberDisplayNameList) {
        this.TeamName = teamName;
        this.MemberNameList = memberNameList;
        this.MemberDisplayNameList = memberDisplayNameList;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public List<Component> getMemberDisplayNameList() {
        return MemberDisplayNameList;
    }

    public List<String> getMemberNameList() {
        return MemberNameList;
    }

    public void setMemberDisplayNameList(List<Component> memberDisplayNameList) {
        MemberDisplayNameList = memberDisplayNameList;
    }

    public void setMemberNameList(List<String> memberNameList) {
        MemberNameList = memberNameList;
    }

    public String getLeaderName() {
        return MemberNameList.get(0);
    }

    public Component getLeaderDisplayName() {
        return MemberDisplayNameList.get(0);
    }

    public String getTeamName() {
        return TeamName;
    }
}

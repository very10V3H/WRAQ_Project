package fun.wraq.process.system.missions;

import fun.wraq.process.system.missions.Mission;

import java.util.ArrayList;
import java.util.List;

public class MissionClient {
    public static String missionStatus;

    public static List<fun.wraq.process.system.missions.Mission> getMissionList(char needStatus) {
        List<fun.wraq.process.system.missions.Mission> missionList = new ArrayList<>();
        String status = missionStatus;
        if (status != null) {
            for (int i = 0; i < status.length(); i++) {
                if (status.charAt(i) == needStatus) missionList.add(Mission.missionsMap.get(i));
            }
        }
        return missionList;
    }

    public static char getMissionStatus(int serialNum) {
        if (missionStatus != null) return missionStatus.charAt(serialNum);
        return 'N';
    }


}

package fun.wraq.process.system.estate;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import net.minecraft.core.BlockPos;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public class EstateServerData {

    public final int serial;
    public final String ownerId;
    public final Calendar boughtDate;

    public EstateServerData(int serial, String ownerId, Calendar boughtDate) {
        this.serial = serial;
        this.ownerId = ownerId;
        this.boughtDate = boughtDate;
    }

    @Override
    public String toString() {
        return serial + "#" + ownerId + "#" + Compute.CalendarToString(this.boughtDate);
    }

    public static EstateServerData getDataFromString(String s) {
        String[] split = s.split("#");
        int serial = Integer.parseInt(split[0]);
        String ownerId = split[1];
        Calendar boughtDate = null;
        try {
            boughtDate = Compute.StringToCalendar(split[2]);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new EstateServerData(serial, ownerId, boughtDate);
    }

    public static BlockPos getBlockPosFromString(String s) {
        String[] split = s.split(",");
        return new BlockPos(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }

    public static List<EstateServerData> getEstateServerData() {
        return EstateServerSavedData.getInstance(Tick.server.overworld())
                .getEstateInfoStringList().stream()
                .map(EstateServerData::getDataFromString).toList();
    }

    public static EstateServerData getEstateServerData(int serial) {
        return getEstateServerData().stream().filter(estateServerData -> {
            return estateServerData.serial == serial;
        }).findFirst().orElse(null);
    }

    public static void editEstateServerData(EstateServerData serverData) {
        EstateServerSavedData savedData = EstateServerSavedData.getInstance(Tick.server.overworld());
        List<String> infoStringList = savedData.getEstateInfoStringList();
        infoStringList.removeIf(s -> {
            return getDataFromString(s).serial == serverData.serial;
        });
        infoStringList.add(serverData.toString());
        savedData.setDirty();
    }

    public static void removeEstateServerData(int ordinal) {
        EstateServerSavedData savedData = EstateServerSavedData.getInstance(Tick.server.overworld());
        List<String> infoStringList = savedData.getEstateInfoStringList();
        infoStringList.removeIf(s -> {
            return getDataFromString(s).serial == ordinal;
        });
        savedData.setDirty();
    }

}

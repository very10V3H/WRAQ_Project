import fun.wraq.process.system.bonuschest.BonusChestInfo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(BonusChestInfo.values().length);
        List<BonusChestInfo> list = Arrays.stream(BonusChestInfo.values()).toList();
        System.out.println("tier 0 : " + list.stream().filter(bonusChestInfo -> bonusChestInfo.tier == 0).count());
        System.out.println("tier 1 : " + list.stream().filter(bonusChestInfo -> bonusChestInfo.tier == 1).count());
        System.out.println("tier 2 : " + list.stream().filter(bonusChestInfo -> bonusChestInfo.tier == 2).count());
        System.out.println("tier 3 : " + list.stream().filter(bonusChestInfo -> bonusChestInfo.tier == 3).count());


/*        File directory = new File("");
        String path = directory.getAbsolutePath() + "\\run\\world\\playerdata\\0b2cab89-79a6-3138-9f56-4d366bfc99f2-7547368493861204727.dat";
        File data = new File(path);
        if (data.exists() && data.isFile()) {
            CompoundTag tag = NbtIo.readCompressed(data);
            System.out.println(tag.getCompound("ForgeData"));
        }*/

/*        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(new FileReader(path));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        System.out.println(jsonObject.asMap());*/
    }
}


import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File directory = new File("");
        String path = directory.getAbsolutePath() + "\\run\\world\\playerdata\\0b2cab89-79a6-3138-9f56-4d366bfc99f2-7547368493861204727.dat";
        File data = new File(path);
        if (data.exists() && data.isFile()) {
            CompoundTag tag = NbtIo.readCompressed(data);
            System.out.println(tag.getCompound("ForgeData"));
        }

/*        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(new FileReader(path));
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        System.out.println(jsonObject.asMap());*/
    }
}


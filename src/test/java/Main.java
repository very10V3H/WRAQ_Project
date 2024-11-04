import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://littleskin.cn/api/yggdrasil/sessionserver/session/minecraft/profile" +
                "/ddd94abb438d479ea6f9ce89de0bcbc3?unsigned=false");
        httpGet.addHeader("Accept", "application/json");



        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            System.out.println(result);
        }

/*        Map<Integer, Integer> map = new HashMap<>();
        for (BonusChestInfo value : BonusChestInfo.values()) {
            if (value.zone == BonusChestInfo.Util.MOONTAIN_NUM) {
                map.compute(value.tier, (k, v) -> v == null ? 1 : v + 1);
            }
        }
        System.out.println(map);*/


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


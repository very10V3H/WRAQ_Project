public class Main {

    public static void main(String[] args) throws Exception {

        String string = "very_H#plain_soul*666^1";
        String playerName = "";
        String itemStackName = "";
        String price = "";
        String type = "";
        int index1 = 0; // #
        int index2 = 0; // *
        int index3 = 0; // ^
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '#') {
                playerName = string.substring(0, i);
                index1 = i + 1;
                break;
            }
        }
        for (int i = string.length() - 1; i >= 0; i--) {
            if (string.charAt(i) == '^') {
                type = string.substring(i + 1);
                index3 = i + 1;
                break;
            }
        }
        for (int i = string.length() - 1; i >= 0; i--) {
            if (string.charAt(i) == '*') {
                itemStackName = string.substring(index1, i);
                index2 = i + 1;
                break;
            }
        }
        price = string.substring(index2, index3 - 1);
        System.out.println(playerName + " " + itemStackName + " " + price + " " + type);

        /*System.out.println(Security.getMACAddress(InetAddress.getLocalHost()));*/

/*        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://littleskin.cn/api/yggdrasil/sessionserver/session/minecraft/profile" +
                "/ddd94abb438d479ea6f9ce89de0bcbc3?unsigned=false");
        httpGet.addHeader("Accept", "application/json");



        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            System.out.println(result);
        }*/

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


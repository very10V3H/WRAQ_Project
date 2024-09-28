package fun.wraq.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {
    public static List<String> readFile(String path) throws IOException {
        List<String> content = new ArrayList<>();
        BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);
        String delimiter = ",";
        String line = "";
        while ((line = br.readLine()) != null) {
            String[] columns = line.split(delimiter);
            content.add(Arrays.toString(columns));
        }
        br.close();
        return content;
    }
}

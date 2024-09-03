import com.very.wraq.common.attribute.MobAttributes;
import com.very.wraq.files.CSVReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println(MobAttributes.readAttributes(CSVReader.readFile("C:\\Users\\Administrator\\Desktop\\mobAttributes.csv")));
    }
}


package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.files.CSVReader;
import net.minecraft.commands.CommandSourceStack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ReadMobAttributeFromCSVFileCommand implements Command<CommandSourceStack> {
    public static ReadMobAttributeFromCSVFileCommand instance = new ReadMobAttributeFromCSVFileCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        try {
            if (!Files.exists(Paths.get("C:\\Users\\Administrator\\Desktop\\mobAttributes.csv"))) {
                MobSpawn.MobBaseAttributes.fromCSVAttributes = MobAttributes
                        .readAttributes(CSVReader.readFile("/home/tietou/minecraft/wraq2-test/mobAttributes.csv"));
            } else {
                MobSpawn.MobBaseAttributes.fromCSVAttributes = MobAttributes
                        .readAttributes(CSVReader.readFile("C:\\Users\\Administrator\\Desktop\\mobAttributes.csv"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}

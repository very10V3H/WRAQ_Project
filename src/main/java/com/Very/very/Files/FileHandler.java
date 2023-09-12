package com.Very.very.Files;

import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.world.entity.player.Player;
import org.jline.utils.Log;

import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/*@OnlyIn(Dist.DEDICATED_SERVER)*/
public class FileHandler {
    public static File file;
    public static File file1;
    public static File WRAQLogsOfItemUse;
    public static File WRAWLogsOfItemGet;
    public static File WRAQLogsOfMobKill;
    public static void init() throws IOException
    {
        String dir = "E:\\testserver\\MarketItemInfo.txt";
        file = new File(dir);
        if(!file.exists()) file.createNewFile();

        String dir1 = "E:\\testserver\\MarketPlayerInfo.txt";
        file1 = new File(dir1);
        if(!file1.exists()) file1.createNewFile();
    }
    public static void WARQLogInit() throws IOException {
        Utils.LogFlag = false;
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd-HH.mm.ss");
        String Date = dateTime.format(formatter);

        String dir = "E:\\testserver\\" + "WARQLogsOfItemUse-" + Date + ".txt";
        WRAQLogsOfItemUse = new File(dir);
        if (!WRAQLogsOfItemUse.exists()) WRAQLogsOfItemUse.createNewFile();

        String dir1 = "E:\\testserver\\" + "WARQLogsOfMobKill-" + Date + ".txt";
        WRAQLogsOfMobKill = new File(dir1);
        if (!WRAQLogsOfMobKill.exists()) WRAQLogsOfMobKill.createNewFile();

        String dir2 = "E:\\testserver\\" + "WARQLogsOfItemGet-" + Date + ".txt";
        WRAWLogsOfItemGet = new File(dir2);
        if (!WRAWLogsOfItemGet.exists()) WRAWLogsOfItemGet.createNewFile();
    }
    public static void WARQLogsWrite(Player player, String Type, String Content) throws IOException {
        if (Utils.LogFlag) WARQLogInit();
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd-HH:mm:ss");
        String Date = dateTime.format(formatter);
        String Name = player.getName().getString();
        String Pos = player.level() + player.getPosition(1f).toString();
        if (Type.equals(StringUtils.LogsType.ItemUse)) {
            String LogsOut = "[" + Date + " | " + Name + " | " + Pos + "]" + Content + "_END.";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(WRAQLogsOfItemUse,true));
            bufferedWriter.write(LogsOut);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        if (Type.equals(StringUtils.LogsType.MobKill)) {
            String LogsOut = "[" + Date + " | " + Name + " | " + Pos + "]" + Content + "_END.";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(WRAQLogsOfMobKill,true));
            bufferedWriter.write(LogsOut);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        if (Type.equals(StringUtils.LogsType.ItemGet)) {
            String LogsOut = "[" + Date + " | " + Name + " | " + Pos + "]" + Content + "_END.";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(WRAWLogsOfItemGet,true));
            bufferedWriter.write(LogsOut);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        }
    }
    public static void write(String string) throws IOException {
        if (Utils.FileFlag) {
            Utils.FileFlag = false;
            init();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(string);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        bufferedWriter.close();
    }
    public static void ItemInfoWrite() throws IOException {
        if (Utils.FileFlag) {
            Utils.FileFlag = false;
            init();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        Iterator<MarketItemInfo> iterator = Utils.MarketInfo.iterator();
        while(iterator.hasNext()){
            MarketItemInfo marketItemInfo = iterator.next();
            String string = marketItemInfo.getPlayer()+"#"+marketItemInfo.getItemStack()+"*"+marketItemInfo.getPrice();
            bufferedWriter.write(string);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }
    public static void write1(String string) throws IOException {
        if (Utils.FileFlag) {
            Utils.FileFlag = false;
            init();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file1));
        bufferedWriter.write(string);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        bufferedWriter.close();
    }
    public static void PlayerInfoWrite() throws IOException {
        if (Utils.FileFlag) {
            Utils.FileFlag = false;
            init();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file1));
        Iterator<MarketPlayerInfo> iterator = Utils.MarketPlayerInfo.iterator();
        while(iterator.hasNext()){
            MarketPlayerInfo marketPlayerInfo = iterator.next();
            String string = marketPlayerInfo.getPlayer()+"#"+marketPlayerInfo.getGet();
            bufferedWriter.write(string);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }
    public static void read() throws IOException {
        if (Utils.FileFlag) {
            Utils.FileFlag = false;
            init();
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String strLine = bufferedReader.readLine();
        while(strLine != null){
            System.out.println(strLine);
            strLine = bufferedReader.readLine();
        }
        bufferedReader.close();
    }
    public static void MarketInfoRead() throws IOException {
        if (Utils.FileFlag) {
            Utils.FileFlag = false;
            init();
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String strLine = bufferedReader.readLine();
        while(strLine != null){
            String playerName = "";
            String itemStackName = "";
            String price = "";
            int index1 = 0; // #
            int index2 = 0; // *
            for(int i = 0; i < strLine.length(); i++){
                if (strLine.charAt(i) == '#') {
                    playerName = strLine.substring(0,i);
                    index1 = i+1;
                }
                if (strLine.charAt(i) == '*') {
                    itemStackName = strLine.substring(index1,i);
                    index2 = i+1;
                }
                price = strLine.substring(index2,strLine.length()-1);
            }
            MarketItemInfo marketItemInfo = new MarketItemInfo(playerName,itemStackName,Double.parseDouble(price));
            Utils.MarketInfo.add(marketItemInfo);
            strLine = bufferedReader.readLine();
        }
        bufferedReader.close();
    }
    public static void MarketPlayerInfoRead() throws IOException {
        if (Utils.FileFlag) {
            Utils.FileFlag = false;
            init();
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file1));
        String strLine = bufferedReader.readLine();
        while(strLine != null){
            String playerName = "";
            String get = "";
            for(int i = 0; i < strLine.length(); i++){
                if(strLine.charAt(i) == '#'){
                    playerName = strLine.substring(0,i);
                    get = strLine.substring(i+1);
                }
            }
            MarketPlayerInfo marketPlayerInfo = new MarketPlayerInfo(playerName,Double.parseDouble(get));
            Utils.MarketPlayerInfo.add(marketPlayerInfo);
            strLine = bufferedReader.readLine();
        }
        bufferedReader.close();
    }
}

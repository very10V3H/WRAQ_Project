package com.very.wraq.files;

import com.very.wraq.blocks.blocks.ForgeRecipe;
import com.very.wraq.process.Security.Security;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.world.entity.player.Player;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/*@OnlyIn(Dist.DEDICATED_SERVER)*/
public class FileHandler {
    public static File file;
    public static File file1;
    public static File file2;
    public static File ForgeNum;
    public static File WRAQLogsOfItemUse;
    public static File WRAWLogsOfItemGet;
    public static File WRAQLogsOfMobKill;
    public static File WRAQLogsOfItemPick;
    public static File WRAQLogsOfItemDrop;
    public static File WRAQLogsOfItemDelete;
    public static File WRAQLogsOfTrade;
    public static File WRAQVIPS;


    public static void init() throws IOException
    {
        String dir = "E:\\testserver\\MarketItemInfo.txt";
        file = new File(dir);
        if(!file.exists()) file.createNewFile();

        String dir1 = "E:\\testserver\\MarketPlayerInfo.txt";
        file1 = new File(dir1);
        if(!file1.exists()) file1.createNewFile();

        String dir2 = "E:\\testserver\\UtilInfo.txt";
        file2 = new File(dir2);
        if (!file2.exists()) file2.createNewFile();

        String dir3 = "E:\\testserver\\ForgeNum.txt";
        ForgeNum = new File(dir3);
        if (!ForgeNum.exists()) ForgeNum.createNewFile();

        Security.InitServerFile();
    }

    public static void WARQLogInit() throws IOException {
        Utils.LogFlag = false;
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd-HH.mm.ss");
        String Date = dateTime.format(formatter);

        String dir = "E:\\testserver\\" + "WRAQLogsOfItemUse-" + Date + ".txt";
        WRAQLogsOfItemUse = new File(dir);
        if (!WRAQLogsOfItemUse.exists()) WRAQLogsOfItemUse.createNewFile();

        String dir1 = "E:\\testserver\\" + "WRAQLogsOfMobKill-" + Date + ".txt";
        WRAQLogsOfMobKill = new File(dir1);
        if (!WRAQLogsOfMobKill.exists()) WRAQLogsOfMobKill.createNewFile();

        String dir2 = "E:\\testserver\\" + "WRAQLogsOfItemGet-" + Date + ".txt";
        WRAWLogsOfItemGet = new File(dir2);
        if (!WRAWLogsOfItemGet.exists()) WRAWLogsOfItemGet.createNewFile();

        String dir3 = "E:\\testserver\\" + "WRAQLogsOfItemPick-" + Date + ".txt";
        WRAQLogsOfItemPick = new File(dir3);
        if (!WRAQLogsOfItemPick.exists()) WRAQLogsOfItemPick.createNewFile();

        String dir4 = "E:\\testserver\\" + "WRAQLogsOfItemDrop-" + Date + ".txt";
        WRAQLogsOfItemDrop = new File(dir4);
        if (!WRAQLogsOfItemDrop.exists()) WRAQLogsOfItemDrop.createNewFile();

        String dir5 = "E:\\testserver\\" + "WRAQLogsOfItemDelete-" + Date + ".txt";
        WRAQLogsOfItemDelete = new File(dir5);
        if (!WRAQLogsOfItemDelete.exists()) WRAQLogsOfItemDelete.createNewFile();

        String dir6 = "E:\\testserver\\" + "WRAQLogsOfTrade-" + Date + ".txt";
        WRAQLogsOfTrade = new File(dir6);
        if (!WRAQLogsOfTrade.exists()) WRAQLogsOfTrade.createNewFile();

    }

    public static void WRAQLogWrite(Player player, String Type, String Content) throws IOException {
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
        if (Type.equals(StringUtils.LogsType.ItemPick)) {
            String LogsOut = "[" + Date + " | " + Name + " | " + Pos + "]" + Content + "_END.";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(WRAQLogsOfItemPick,true));
            bufferedWriter.write(LogsOut);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        if (Type.equals(StringUtils.LogsType.ItemDrop)) {
            String LogsOut = "[" + Date + " | " + Name + " | " + Pos + "]" + Content + "_END.";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(WRAQLogsOfItemDrop,true));
            bufferedWriter.write(LogsOut);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        if (Type.equals(StringUtils.LogsType.ItemDelete)) {
            String LogsOut = "[" + Date + " | " + Name + " | " + Pos + "]" + Content + "_END.";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(WRAQLogsOfItemDelete,true));
            bufferedWriter.write(LogsOut);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        if (Type.equals(StringUtils.LogsType.Trade)) {
            String LogsOut = "[" + Date + " | " + Name + " | " + Pos + "]" + Content + "_END.";
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(WRAQLogsOfTrade,true));
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
            if (!playerName.equals("") && !itemStackName.equals("") && !price.equals("")) {
                MarketItemInfo marketItemInfo = new MarketItemInfo(playerName,itemStackName,Double.parseDouble(price));
                Utils.MarketInfo.add(marketItemInfo);
            }
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
            if (!playerName.equals("") && !get.equals("")) {
                MarketPlayerInfo marketPlayerInfo = new MarketPlayerInfo(playerName,Double.parseDouble(get));
                Utils.MarketPlayerInfo.add(marketPlayerInfo);
            }
            strLine = bufferedReader.readLine();
        }
        bufferedReader.close();
    }

    public static void EntropyInfoWrite() throws IOException {
        if (Utils.FileFlag) {
            Utils.FileFlag = false;
            init();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2));
        String[] Info = {
                "WorldEntropyIncreaseSpeed" + " " + Utils.WorldEntropyIncreaseSpeed,
                "MediumNum0" + " " + Utils.WorldEntropyPos.get(0).getMediumNum(),
                "MediumNum1" + " " + Utils.WorldEntropyPos.get(1).getMediumNum(),
                "MediumNum2" + " " + Utils.WorldEntropyPos.get(2).getMediumNum()
        };
        for (String info : Info) {
            bufferedWriter.write(info);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static void EntropyInfoRead() throws IOException {
        if (Utils.FileFlag) {
            Utils.FileFlag = false;
            init();
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
        String strLine = bufferedReader.readLine();
        while(strLine != null){
            String Prefix = "";
            String Num = "";
            for(int i = 0; i < strLine.length(); i++){
                if (strLine.charAt(i) == ' ') {
                    Prefix = strLine.substring(0,i);
                    Num = strLine.substring(i + 1, strLine.length() - 1);
                }
            }
            switch (Prefix) {
                case "WorldEntropyIncreaseSpeed" -> Utils.WorldEntropyIncreaseSpeed = Double.parseDouble(Num);
                case "MediumNum0" -> Utils.WorldEntropyPos.get(0).setMediumNum(Double.parseDouble(Num));
                case "MediumNum1" -> Utils.WorldEntropyPos.get(1).setMediumNum(Double.parseDouble(Num));
                case "MediumNum2" -> Utils.WorldEntropyPos.get(2).setMediumNum(Double.parseDouble(Num));
            }
            strLine = bufferedReader.readLine();
        }
        bufferedReader.close();
    }
    public static void ForgeNumWrite() throws IOException {
        if (Utils.FileFlag) {
            Utils.FileFlag = false;
            init();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(ForgeNum));
        if (ForgeRecipe.ForgedItemList.isEmpty()) ForgeRecipe.setForgedItemList();
        ForgeRecipe.ForgedItemList.forEach(item -> {
            try {
                bufferedWriter.write(Utils.forgeNumMap.get(item).toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                bufferedWriter.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        bufferedWriter.flush();
        bufferedWriter.close();
    }
    public static void ForgeNumRead() throws IOException {
        if (Utils.FileFlag) {
            Utils.FileFlag = false;
            init();
        }
        BufferedReader bufferedReader = new BufferedReader(new FileReader(ForgeNum));
        String strLine = bufferedReader.readLine();
        if (ForgeRecipe.ForgeDrawRecipe.isEmpty()) ForgeRecipe.ForgeDrawRecipeInit();
        if (ForgeRecipe.ForgedItemList.isEmpty()) ForgeRecipe.setForgedItemList();
        int index = 0;
        ForgeRecipe.ForgedItemList.forEach(item -> {
            Utils.forgeNumMap.put(item,0);
        });
        while(strLine != null){
            Utils.forgeNumMap.put(ForgeRecipe.ForgedItemList.get(index),Integer.parseInt(strLine));
            strLine = bufferedReader.readLine();
            index ++;
        }
        bufferedReader.close();
    }
}

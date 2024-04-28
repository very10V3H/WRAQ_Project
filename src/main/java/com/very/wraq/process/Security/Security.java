package com.very.wraq.process.Security;

import com.very.wraq.netWorking.unSorted.ClientLimitWrongC2SPacket;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.valueAndTools.Compute;

import java.io.*;
import java.util.Calendar;

public class Security {

    public static void ClientPropertiesSet(String string) throws IOException {
        WriteName(string);
    }

    public static void ClientPropertiesCheck(String string) throws IOException {
        if (!ReadName().equals(string) && ReadName() != null) ModNetworking.sendToServer(new ClientLimitWrongC2SPacket(ReadName()));
    }

    public static File file;
    public static void Init() throws IOException {
        String dir = System.getProperty("user.home") + "\\McLog.txt";
        file = new File(dir);
        if (file.exists()) file.delete();
        file.createNewFile();
    }

    public static void WriteName(String name) throws IOException {
        Init();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
        bufferedWriter.write(name);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static String ReadName() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String strLine = bufferedReader.readLine();
        bufferedReader.close();
        return strLine;
    }

    public static File serverFile;

    public static void InitServerFile() throws IOException {
        String dir = "E:\\testserver\\WrongLog.txt";
        serverFile = new File(dir);
        if(!serverFile.exists()) serverFile.createNewFile();
    }

    public static void serverWrite(String string) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(serverFile,true));
        bufferedWriter.write(string + " " +Compute.CalendarToString(Calendar.getInstance()));
        bufferedWriter.newLine();
        bufferedWriter.flush();
        bufferedWriter.close();
    }


}

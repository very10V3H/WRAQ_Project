package com.very.wraq.process.func.security;

import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.unSorted.ClientLimitWrongC2SPacket;

import java.io.*;

public class Security {

    public static void ClientPropertiesSet(String string) throws IOException {
        WriteName(string);
    }

    public static void ClientPropertiesCheck(String string) throws IOException {
        if (!ReadName().equals(string) && ReadName() != null)
            ModNetworking.sendToServer(new ClientLimitWrongC2SPacket(ReadName()));
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
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
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
}

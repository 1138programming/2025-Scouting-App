package com.scouting_app_2025.JSON;

import static com.scouting_app_2025.MainActivity.TAG;
import static com.scouting_app_2025.MainActivity.context;

import android.util.Log;

import com.scouting_app_2025.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class UpdateScoutingInfo {

    private final File folderDir = context.getFilesDir();
    private final String fileName = "scouterInfo.txt";
    boolean fileExists = true;

    public UpdateScoutingInfo() {
        if (!folderDir.isDirectory()) {
            if (!folderDir.mkdir()) {
                fileExists = false;
                Log.e(TAG, "File System is Broken");
            }
        }
    }

    public void saveToFile(String text) throws IOException {
        if(!fileExists) return;

        File targetFile = new File(folderDir, fileName);
        if (!targetFile.exists()) {
            targetFile.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(targetFile, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(text);

        bufferedWriter.close();
    }

    public String getDataFromFile() {
        File file = new File(folderDir, fileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
            return "";
        }
        
        InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader((inputStreamReader))) {
            String line = reader.readLine();
            while(line != null) {
                sb.append(line).append('\n');
                line = reader.readLine();
            }
            if(sb.length() > 0) {
                //removes last "\n"
                sb.deleteCharAt(sb.length()-1);
            }
        }
        catch (IOException e) {
            Log.e(TAG, e.toString());
            return "";
        }
        return sb.toString();
    }

    public ArrayList<ArrayList<CharSequence>> getSplitFileData() {
        String fileData = getDataFromFile();
        if (fileData.isEmpty()) {
            return new ArrayList<>();
        }
        String[] listsSplit = fileData.split("\n");
        String[] teamList = listsSplit[1].split(",");
            Arrays.sort(teamList, new Comparator<String>() {
                @Override
                public int compare(String s, String s2) {
                    int i = Integer.parseInt(s);
                    int i2 = Integer.parseInt(s2);
                    return Integer.compare(i, i2);
                }
            });

        String[] scoutersWithNum = listsSplit[0].split(",");
            Arrays.sort(scoutersWithNum);

        ArrayList<ArrayList<CharSequence>> retVal = new ArrayList<>();
            retVal.add(new ArrayList<>());
            retVal.add(new ArrayList<>());
            retVal.add(new ArrayList<>());

        for (String i : scoutersWithNum) {
            String[] split = i.split(":");
            retVal.get(0).add(split[0]);
            retVal.get(1).add(split[1]);
        }
        for (String teamNum : teamList) {
            retVal.get(2).add(teamNum);
        }
        return retVal;
    }
}
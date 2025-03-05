package com.scouting_app_2025.JSON;

import static com.scouting_app_2025.MainActivity.TAG;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class UpdateScoutingInfo {

    private File folderDir = new File("/data/data/com.scouting_app_2025/files/scoutingData");
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

    public void saveToFile(String text) {
        if(!fileExists) return;
        try {
            FileWriter fileWriter = new FileWriter(new File(folderDir, fileName), false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.close();
        }
        catch(IOException e) {
            Log.e(TAG, e.toString());
        }
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
        }
        finally {
            String contents = sb.toString();
            return contents;
        }
    }
}
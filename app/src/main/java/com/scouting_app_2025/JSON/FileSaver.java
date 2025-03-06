package com.scouting_app_2025.JSON;

import static com.scouting_app_2025.MainActivity.TAG;

import android.util.Log;

import com.scouting_app_2025.MainActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class FileSaver {

    public static void saveFile(String fileText, String fileTitle) {
        String path = MainActivity.context.getFilesDir().getPath() + "/scoutingData";
        File folderDir = new File(path);
        //creates the directory if it doesn't exist
        if (!folderDir.isDirectory()) {
            if (!folderDir.mkdir()) {
                Log.e(TAG,"Unable to make directory: \"" + path + "\"");
                return;
            }
        }
        boolean fileExists = true;
        File scoutingFile = new File(folderDir, fileTitle + ".json");
        for (int i = 1; fileExists; i++) {
            fileExists = false;
            for (File j : Objects.requireNonNull(folderDir.listFiles())) {
                if (scoutingFile.getName().equals(j.getName())) {
                    fileExists = true;
                }
            }
            if (fileExists)
                scoutingFile = new File(folderDir, fileTitle + "(" + i + ").json");
        }
        try {
            FileWriter fileWriter = new FileWriter(scoutingFile, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(fileText);
            bufferedWriter.close();
        }
        catch(IOException e) {
            Log.e(TAG, e.toString());
        }
    }
}

package com.scouting_app_2025.JSON;

import static com.scouting_app_2025.MainActivity.TAG;
import static com.scouting_app_2025.MainActivity.context;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class FileSaver {

    public static void saveFile(String fileText, String fileTitle) {
        String path = context.getFilesDir().getPath() + "/scoutingData";
        File folderDir = new File(path);
        //creates the directory if it doesn't exist
        if (!folderDir.isDirectory()) {
            if (!folderDir.mkdir()) {
                Log.e(TAG,"Unable to make directory: \"" + path + "\"");
                return;
            }
        }
        File scoutingFile = getScoutingFile(fileTitle, folderDir);
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

    @NonNull
    private static File getScoutingFile(String fileTitle, File folderDir) {
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
        return scoutingFile;
    }
}

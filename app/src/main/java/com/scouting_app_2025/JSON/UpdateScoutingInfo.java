package com.scouting_app_2025.JSON;

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
        FileWriter fileWriter = new FileWriter(scoutingFile, false);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(userString);
        bufferedWriter.close();
        MainActivity mainActivity = (MainActivity) getActivity();
    }

    public String getDataFromFile() {
        File file = new File(folderDir, fileName);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Log.e(TAG, e);
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
            Log.e(TAG, e);
        }
        finally {
            String contents = sb.toString();
            return contents;
        }
    }
}
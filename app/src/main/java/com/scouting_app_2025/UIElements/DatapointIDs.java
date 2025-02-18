package com.scouting_app_2025.UIElements;

import java.util.HashMap;

public class DatapointIDs {
    public static final HashMap<Integer, String> datapointIDs = new HashMap<Integer,String>();
    public static final HashMap<String, Integer> reverseDatapointIDs = new HashMap<String, Integer>();

    static {
        datapointIDs.put(1, "Robot Start Point"); //String
        datapointIDs.put(2, "Auton Start"); // timestamp
        datapointIDs.put(3, "Leave");
        datapointIDs.put(4, "Coral Score L1");
        datapointIDs.put(5, "Coral Score L2");
        datapointIDs.put(6, "Coral Score L3");
        datapointIDs.put(7, "Coral Score L4");
        datapointIDs.put(8, "Coral Missed");
        datapointIDs.put(9, "Net Score");
        datapointIDs.put(10, "Net Miss");
        datapointIDs.put(11, "Auto Processor Score");
        datapointIDs.put(12, "Teleop Start");
        datapointIDs.put(13, "Teleop Coral Score L1");
        datapointIDs.put(14, "Teleop Coral Score L2");
        datapointIDs.put(15, "Teleop Coral Score L3");
        datapointIDs.put(16, "Teleop Coral Score L4");
        datapointIDs.put(17, "Teleop Coral Pick up");
        datapointIDs.put(18, "Teleop Algae Removed String:(L2 L3)");
        datapointIDs.put(19, "Teleop Net Score");
        datapointIDs.put(20, "Teleop Net Miss");
        datapointIDs.put(21, "Teleop Processor Score");
        datapointIDs.put(22, "Hang Attempted");
        datapointIDs.put(23, "Hang String:(Park Deep Shallow)");
        datapointIDs.put(24, "Hang Failed");
        datapointIDs.put(25, "Disconnect");
        datapointIDs.put(26, "Struck String:(Coral Algae)");
        datapointIDs.put(27, "Defense");


    }
}



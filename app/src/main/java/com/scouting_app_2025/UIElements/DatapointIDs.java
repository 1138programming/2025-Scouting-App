package com.scouting_app_2025.UIElements;

import java.util.EnumMap;
import java.util.HashMap;

public class DatapointIDs {
    public static final HashMap<Integer, String> datapointIDs = new HashMap<Integer,String>();
    public static final EnumMap<NonDataEnum, Integer> nonDataIDs = new EnumMap<>(NonDataEnum.class);

    static {
        datapointIDs.put(1, "Robot Start Point String (1, 2, 3)");
        datapointIDs.put(2, "Auton Start");
        datapointIDs.put(3, "Leave (boolean)");
        datapointIDs.put(4, "Auton Coral Score L1");
        datapointIDs.put(5, "Auton Coral Score L2");
        datapointIDs.put(6, "Auton Coral Score L3");
        datapointIDs.put(7, "Auton Coral Score L4");
        datapointIDs.put(8, "Auton Coral Missed L1");
        datapointIDs.put(9, "Auton Coral Missed L2");
        datapointIDs.put(10, "Auton Coral Missed L3");
        datapointIDs.put(11, "Auton Coral Missed L4");
        datapointIDs.put(12, "Auton Coral Pickup");
        datapointIDs.put(13, "Auton Algae Removed L2");
        datapointIDs.put(14, "Auton Algae Removed L2");
        datapointIDs.put(15, "Auton Algae Removed Failed L2");
        datapointIDs.put(16, "Auton Algae Removed Failed L3");
        datapointIDs.put(17, "Auton Net Score");
        datapointIDs.put(18, "Auton Net Miss");
        datapointIDs.put(19, "Auton Processor Score");
        datapointIDs.put(20, "Auton Processor Missed");
        datapointIDs.put(21, "Teleop Start");
        datapointIDs.put(22, "Teleop Coral Score L1");
        datapointIDs.put(23, "Teleop Coral Score L2");
        datapointIDs.put(24, "Teleop Coral Score L3");
        datapointIDs.put(25, "Teleop Coral Score L4");
        datapointIDs.put(26, "Teleop Coral Missed L1");
        datapointIDs.put(27, "Teleop Coral Missed L2");
        datapointIDs.put(28, "Teleop Coral Missed L3");
        datapointIDs.put(29, "Teleop Coral Missed L4");
        datapointIDs.put(30, "Teleop Coral Pick up");
        datapointIDs.put(31, "Teleop Algae Removed L2");
        datapointIDs.put(32, "Teleop Algae Removed L3");
        datapointIDs.put(33, "Teleop Algae Removed Failed L2");
        datapointIDs.put(34, "Teleop Algae Removed Failed L3");
        datapointIDs.put(35, "Teleop Net Score");
        datapointIDs.put(36, "Teleop Net Miss");
        datapointIDs.put(37, "Teleop Processor Score");
        datapointIDs.put(38, "Teleop Processor Missed");
        datapointIDs.put(39, "Hang Attempted (boolean)");
        datapointIDs.put(40, "Hang String:(Park Deep Shallow)");
        datapointIDs.put(41, "Hang Failed (boolean)");
        datapointIDs.put(42, "Disconnect (boolean)");
        datapointIDs.put(43, "Stuck String:(Coral Algae)");
        datapointIDs.put(44, "Defense Start");
        datapointIDs.put(45, "Defense End");

        nonDataIDs.put(NonDataEnum.ScouterName, -1);
        nonDataIDs.put(NonDataEnum.MatchNumber, -2);
        nonDataIDs.put(NonDataEnum.TeamColor, -3);
        nonDataIDs.put(NonDataEnum.TeamNumber, -4);
        nonDataIDs.put(NonDataEnum.PreAutonNext, -5);
        nonDataIDs.put(NonDataEnum.ArchiveHamburger, -6);
        nonDataIDs.put(NonDataEnum.AutonStartBack, -7);
        nonDataIDs.put(NonDataEnum.AutonStartStart, -8);
        nonDataIDs.put(NonDataEnum.AutonUndo, -9);
        nonDataIDs.put(NonDataEnum.AutonRedo, -10);
        nonDataIDs.put(NonDataEnum.AutonScored, -11);
        nonDataIDs.put(NonDataEnum.AutonMissed, -12);
        nonDataIDs.put(NonDataEnum.AutonBack, -13);
        nonDataIDs.put(NonDataEnum.AutonNext, -14);
        nonDataIDs.put(NonDataEnum.TeleopStartBack, -15);
        nonDataIDs.put(NonDataEnum.TeleopStartStart, -16);
        nonDataIDs.put(NonDataEnum.TeleopUndo, -17);
        nonDataIDs.put(NonDataEnum.TeleopRedo, -18);
        nonDataIDs.put(NonDataEnum.TeleopScored, -19);
        nonDataIDs.put(NonDataEnum.TeleopMissed, -20);
        nonDataIDs.put(NonDataEnum.TeleopBack, -21);
        nonDataIDs.put(NonDataEnum.TeleopNext, -22);
        nonDataIDs.put(NonDataEnum.PostMatchBack, -23);
        nonDataIDs.put(NonDataEnum.PostMatchSubmit, -24);
        nonDataIDs.put(NonDataEnum.ConfirmSubmitCancel, -23);
        nonDataIDs.put(NonDataEnum.ConfirmSubmitSubmit, -22);

    }
}



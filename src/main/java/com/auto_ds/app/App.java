package com.auto_ds.app;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

//TODO
//MIGHT ADD NEW EXCEPTION CLASSES TO INDICATE ERRORS
//VERIFY IF ALL NECESSARY ARGS ARE PRESENT
//VERIFYING IF RESERVATION IS OK!!
//IMPLEMENT C/B Field pick mechanism

public class App {

    public static String getHelpMessage() {
        return "HELP: \n" +
                "  Available arguments: \n" +
                "   -h prints help\n" +
                "   -p <password>\n" +
                "   -e <email>\n" +
                "   -type <TYPE>\n" +
                "   -t <activity_time>\n" +
                "   -d <activity_date\n" +
                "   -rt <run_time>\n\n" +
                "* <TYPE> : {FOOTBALL_B, FOOTBALL_C}\n" +
                "* <*_time> : eg. 07:30\n" +
                "* <*_date> : eg. 14-04-2018\n";

    }

//    public void startApp(ArgumentUtils argumentUtils) throws IOException {
//        Session session = Session.openSession(argumentUtils.getEmail(), argumentUtils.getPassword());
//        FootballReservationHandler frh = new FootballReservationHandler(session);
//
//        boolean success = frh.reserve(LocalTime.of(7, 30), LocalDate.of(2018, 4, 18), FootballReservationHandler.FOOTBALL_C);
//        if (success) {
//            System.out.println("RESERVATION STATUS: OK");
//        } else {
//            System.out.println("RESERVATION STATUS: ERROR " + "(either entered wrong date or not found free spot");
//        }
//    }
}

package com.auto_ds.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static com.auto_ds.app.ArgumentUtils.*;
import static com.auto_ds.app.FootballReservationHandler.FOOTBALL_B;
import static com.auto_ds.app.FootballReservationHandler.FOOTBALL_C;
import static com.auto_ds.handler.ConnectionsHandler.FOOTBALL_RESERVE_ENDPOINT;

@RestController
public class ReservationController {

    @Autowired
    private Logger logger;

    @GetMapping(value = FOOTBALL_RESERVE_ENDPOINT,
            params = {"password", "email", "fireDateTime", "reservationTime", "reservationDate", "side"})
    public String footballReserve(
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("fireDateTime") String fireDateTime,
            @RequestParam("reservationTime") String reservationTime,
            @RequestParam("reservationDate") String reservationDate,
            @RequestParam("side") String side) {
        try {
            Map<String, Object> argsMap = new HashMap<>();
            argsMap.put(PASSWORD, password);
            argsMap.put(EMAIL, email);
            argsMap.put(FIRE_TIME, LocalDateTime.parse(fireDateTime)); //EG 2007-12-03T10:15:30
            argsMap.put(ACT_TIME, LocalTime.parse(reservationTime));   //EG 10:15:30
            argsMap.put(ACT_DATE, LocalDate.parse(reservationDate));   //EG 2007-12-03
            if (side.equals("B")) side = FOOTBALL_B;
            else if (side.equals("C")) side = FOOTBALL_C;
            argsMap.put(FOOTBALL_SIDE, side);                          //EG B | C
            logger.info("ARGS PASSED: " + argsMap.toString());
        } catch (Exception e) {
            String message = "SOME ERROR OCCURRED - CHECK YOUR ARGUMENTS: ( " + e.getMessage() + " )";
            logger.error(message);
            return message;
        }
        return "";
    }


}

package com.auto_ds.controller;

import com.auto_ds.handler.Task;
import com.auto_ds.handler.TaskExecutorHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auto_ds.app.ArgumentUtils.*;
import static com.auto_ds.app.FootballReservationHandler.FOOTBALL_B;
import static com.auto_ds.app.FootballReservationHandler.FOOTBALL_C;
import static com.auto_ds.handler.ConnectionsHandler.*;

@RestController
public class ReservationController {

    public static final String FOOTPBALL_TYPE = "FOOTBALL";

    @Autowired
    private TaskExecutorHandler taskExecutorHandler;

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
            argsMap.put(PASSWORD, encrypt(password));
            argsMap.put(EMAIL, email);
            argsMap.put(FIRE_TIME, LocalDateTime.parse(fireDateTime));
            argsMap.put(ACT_TIME, LocalTime.parse(reservationTime));
            argsMap.put(ACT_DATE, LocalDate.parse(reservationDate));
            if (side.equals("B")) side = FOOTBALL_B;
            else if (side.equals("C")) side = FOOTBALL_C;
            argsMap.put(FOOTBALL_SIDE, side);
            taskExecutorHandler.addTask(Task.of(argsMap, FOOTPBALL_TYPE));

            return "SUCCESSFULLY CREATED TASK FOR FOOTBALL RESERVE: " +
                    "RESERVET TIME - " + argsMap.get(FIRE_TIME)
                    + " FOR FOOTBALL SIDE " + argsMap.get(FOOTBALL_SIDE) + " @ " + argsMap.get(ACT_TIME) + " " + argsMap.get(ACT_DATE);

        } catch (Exception e) {
            String message = "SOME ERROR OCCURRED - CHECK YOUR ARGUMENTS: ( " + e.getMessage() + " )" + "(" + e.getCause() + ")";
            logger.error(message);
            return message;
        }
    }

    @GetMapping(value = RESERVATION_LIST_ENDPOINT)
    public @ResponseBody List<Task> reservList() {
        return taskExecutorHandler.getScheduledTasks();
    }

    @GetMapping(value = CLEAR_RESERVATIONS_ENDPOINT)
    public String clearReservationTasks() {
        taskExecutorHandler.clearTasks();
        return "RESERVATION TASK CLEARED";
    }
}

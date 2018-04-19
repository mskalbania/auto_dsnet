/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.auto_ds.handler;

import com.auto_ds.app.FootballReservationHandler;
import com.auto_ds.app.Session;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;

import static com.auto_ds.app.ArgumentUtils.*;

public class Task implements Callable<Boolean> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String, Object> args;
    private ScheduledFuture<Boolean> finishedTask;
    private String taskType;
    private String isReservationSuccesfull;
    private Duration taskDelay;

    private Task(Map<String, Object> args, String taskType) {
        this.args = args;
        this.taskType = taskType;
        calculateTaskDelay();
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public Boolean getFinishedTask() {
        return finishedTask.isDone();
    }

    public String getTaskType() {
        return taskType;
    }

    public String getReservationSuccesfull() {
        if (isReservationSuccesfull == null) {
            return "unknown";
        }
        return isReservationSuccesfull;
    }

    public Duration getTaskDelay() {
        calculateTaskDelay();
        return taskDelay;
    }

    public static Task of(Map<String, Object> arguments, String taskType) {
        return new Task(arguments, taskType);
    }

    @JsonIgnore
    public long getTaskDelayInSeconds() {
        return taskDelay.get(ChronoUnit.SECONDS);
    }

    public void setFinishedTask(ScheduledFuture<Boolean> finishedTask) {
        this.finishedTask = finishedTask;
    }

    @Override
    public Boolean call() throws Exception {

        Session session = Session.openSession((String) args.get(EMAIL), (String) args.get(PASSWORD));
        logger.info("OPENED NEW SESSION WITH: " + session.getSessionHeader());
        FootballReservationHandler reservationHandler = new FootballReservationHandler(session);
        boolean success =
                reservationHandler.reserve((LocalTime) args.get(ACT_TIME), (LocalDate) args.get(ACT_DATE), (String) args.get(FOOTBALL_SIDE));
        this.isReservationSuccesfull = Boolean.toString(success);
        return success;
    }

    @Override
    public String toString() {
        String basic = taskType + " - " + args.get(FOOTBALL_SIDE) + " | " + FIRE_TIME + " - "
                + args.get(FIRE_TIME) + " | " + ACT_TIME + " - " + args.get(ACT_TIME) + " | "
                + ACT_DATE + " - " + args.get(ACT_DATE);

        if (finishedTask != null) {
            basic += " | COMPLETED " + finishedTask.isDone();
            if (finishedTask.isDone())
                try {
                    basic += " | RESERVATION PERFORMED SUCCESSFULLY: " + finishedTask.get();
                } catch (Exception e) {
                    logger.error("ERROR GETTING RESULTS ---> " + e.getMessage());
                }
        }
        return basic;
    }

    private void calculateTaskDelay() {
        LocalDateTime currentTime = LocalDateTime.now().plus(2, ChronoUnit.HOURS);
        LocalDateTime fireTime = (LocalDateTime) args.get(FIRE_TIME);
        this.taskDelay = Duration.between(currentTime, fireTime);
    }
}

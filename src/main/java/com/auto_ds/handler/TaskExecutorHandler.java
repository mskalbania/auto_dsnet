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

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

//TODO ADD LOGGERS!!!!!!!
//MIGHT ADD NEW EXCEPTION CLASSES TO INDICATE ERRORS
//VERIFY IF ALL NECESSARY ARGS ARE PRESENT
//VERIFYING IF RESERVATION IS OK!!
//IMPLEMENT C/B Field pick mechanism

@Component
public class TaskExecutorHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private KeepAliveHandler keepAliveHandler;

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);
    private List<Task> scheduledTasks = new ArrayList<>();

    public void addTask(Task task) {
        logger.info("SCHEDULING TASK: " + task
                + " ETA: " + task.getTaskDelayInSeconds() + " (s) " + task.getTaskDelayInSeconds()/60 + " (m)");
        ScheduledFuture<Boolean> result = executor.schedule(task, task.getTaskDelayInSeconds(), TimeUnit.SECONDS);
        task.setFinishedTask(result);
        scheduledTasks.add(task);
        keepAliveHandler.keepAlive();
    }

    public List<Task> getScheduledTasks() {
        return this.scheduledTasks;
    }

    public void clearTasks() {
        executor.shutdownNow();
        executor = Executors.newScheduledThreadPool(4);
        this.scheduledTasks.clear();
    }
}

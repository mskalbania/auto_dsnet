package com.auto_ds.handler;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class KeepAliveHandler {

    public static final String RUNNING = "running";
    public static final String STOPPED = "stopped";

    private final String herokuUrl = "";
    private String operationalStatus = STOPPED;

    //private int INTERVALS = 300000;

    private int INTERVALS = 10000;

    private Executor executor = Executors.newSingleThreadExecutor();

    @Autowired
    private Logger logger;

    public void keepAlive() {
        logger.info("STARTING TO KEEP ALIVE...");
        operationalStatus = RUNNING;

        Runnable task = () -> {
            while (operationalStatus.equals(RUNNING)){
                logger.info("TEST");
                try {
                    logger.info("WAITING FOR: " + INTERVALS/1000/60 + "(min)");
                    Thread.sleep(INTERVALS);
                } catch (InterruptedException e) {
                    logger.error("\n\n --- INTERVAL THREAD INTERRUPTED --- \n\n");
                }
            }
        };
        executor.execute(task);
    }

    public void shutDown() {
        logger.info("SHUTTING DOWN");
        operationalStatus = STOPPED;
    }
}

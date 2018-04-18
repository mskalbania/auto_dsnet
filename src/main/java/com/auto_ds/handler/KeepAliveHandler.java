package com.auto_ds.handler;


import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.auto_ds.handler.ConnectionsHandler.FAKE_ENDPOINT;
import static com.auto_ds.handler.ConnectionsHandler.HEROKU_DOMAIN;

@Component
public class KeepAliveHandler {

    public static final String RUNNING = "running";
    public static final String STOPPED = "stopped";

    private String operationalStatus = STOPPED;

    private int INTERVALS = 300000;

    private Executor executor = Executors.newSingleThreadExecutor();

    @Autowired
    private Logger logger;

    @Autowired
    private ConnectionsHandler connectionsHandler;

    public void keepAlive() {
        logger.info("STARTING TO KEEP ALIVE...");
        operationalStatus = RUNNING;
        Runnable task = () -> {
            while (operationalStatus.equals(RUNNING)) {
                try {
                    executeGet();
                    logger.info("WAITING FOR: " + INTERVALS / 1000 / 60 + " (min)");
                    Thread.sleep(INTERVALS);
                } catch (InterruptedException e) {
                    logger.error("\n\n --- INTERVAL THREAD INTERRUPTED --- \n\n");
                } catch (IOException e) {
                    logger.error("\n\n --- GET ERROR --- \n\n");

                }
            }
        };
        executor.execute(task);
    }

    public void shutDown() {
        logger.info("SHUTTING DOWN");
        operationalStatus = STOPPED;
    }

    private void executeGet() throws IOException {
        HttpResponse response = connectionsHandler.executeGetRequest(HEROKU_DOMAIN + FAKE_ENDPOINT, false);
        logger.info("GET EXECUTED: " + response.getStatusLine().getStatusCode());
    }

}

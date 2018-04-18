package com.auto_ds.controller;

import com.auto_ds.handler.KeepAliveHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.auto_ds.handler.ConnectionsHandler.*;

@RestController
public class UtilController {

    @Autowired
    private KeepAliveHandler keepAliveHandler;

    @GetMapping(value = KEEP_ALIVE_ENDPOINT)
    public String keepAlive() {
        keepAliveHandler.keepAlive();
        return "App is now being kept alive";
    }

    @GetMapping(value = SHUT_DOWN_ENDPOINT)
    public String shutDown() {
        keepAliveHandler.shutDown();
        return "App will be shut down in aprox. 20 min";
    }

    @GetMapping(value = FAKE_ENDPOINT)
    public ResponseEntity<String> fakeEndPoint() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping(value = CURR_TIME_ENDPOINT)
    public String currentTime() {
        return LocalDateTime.now().toString();
    }
}

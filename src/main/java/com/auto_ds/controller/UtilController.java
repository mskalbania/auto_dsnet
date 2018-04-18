package com.auto_ds.controller;

import com.auto_ds.handler.KeepAliveHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UtilController {

    @Autowired
    private KeepAliveHandler keepAliveHandler;

    @GetMapping(value = "/keepAlive")
    public String keepAlive() {
        keepAliveHandler.keepAlive();
        return "App is now being kept alive";
    }

    @GetMapping(value = "/shutDown")
    public String shutDown() {
        keepAliveHandler.shutDown();
        return "App will be shut down in aprox. 20 min";
    }

}

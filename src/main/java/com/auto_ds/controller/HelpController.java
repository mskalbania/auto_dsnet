package com.auto_ds.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.auto_ds.handler.ConnectionsHandler.HELP_ENDPOINT;

@Controller
public class HelpController {

    @GetMapping(value = HELP_ENDPOINT)
    public String help() {
        return "help";
    }

}

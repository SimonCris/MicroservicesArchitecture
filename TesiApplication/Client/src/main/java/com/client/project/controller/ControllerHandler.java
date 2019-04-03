package com.client.project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControllerHandler {

    private Logger log = LoggerFactory.getLogger(ControllerHandler.class);

    @RequestMapping("/welcome")
    public String welcome(){

        return "welcome";

    }

}

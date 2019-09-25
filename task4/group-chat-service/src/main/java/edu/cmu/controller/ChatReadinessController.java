package edu.cmu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Chat controller for readiness check.
 *
 * @author lucas
 */
@Controller
public class ChatReadinessController {

    /**
     * logger for chat controller.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatReadinessController.class);

    /**
     * Map to "/", used for readiness/health check.
     *
     * @return plain text
     */
    @RequestMapping(value = "/health", produces = "text/plain")
    @ResponseBody
    public String home() {
        LOGGER.info("*** chat instance health check ***");
        return "Healthy chat instance!";
    }

}

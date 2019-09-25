package edu.cmu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Login controller for readiness check.
 *
 * @author lucas
 */
@Controller
public class LoginReadinessController {

    /**
     * logger for login controller.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginReadinessController.class);

    /**
     * Map to "/", used for readiness/health check.
     *
     * @return plain text
     */
    @RequestMapping(value = "/", produces = "text/plain")
    @ResponseBody
    public String home() {
        LOGGER.info("*** login instance health check ***");
        return "Healthy login instance!";
    }
}

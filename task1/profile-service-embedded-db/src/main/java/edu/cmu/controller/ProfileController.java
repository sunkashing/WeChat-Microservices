package edu.cmu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cmu.model.Profile;
import edu.cmu.model.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Profile controller.
 * Handle requests from group chat service
 * Fetch profile data from database
 *
 * @author lucas
 */
@Controller
public class ProfileController {

    /**
     * JPA repo for profile bean.
     */
    @Autowired
    private ProfileRepository repository;

    /**
     * logger for profile controller.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    /**
     * Map to "/profile", handling http requests.
     *
     * @param username username
     * @return JSON string of profile
     */
    @RequestMapping(value = "/profile", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public String getProfile(@RequestParam String username) {
        Profile profile = repository.findByUsername(username);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(profile);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Map to "/getallprofiles" HTTP requests.
     * get all the profile beans in MySQLs
     *
     * @return a list of profile beans
     */
    @RequestMapping(value = "/getallprofiles", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public List<Profile> getAllChatMessages(Model model) {
        return repository.findAll();
    }

    /**
     * Map to "/", used for Ribbon health check.
     *
     * @return plain text
     */
    @RequestMapping(value = "/", produces = "text/plain")
    @ResponseBody
    public String home() {
        LOGGER.info("*** profile instance health check ***");
        return "Healthy profile instance!";
    }
}

package edu.cmu.controller;

import edu.cmu.config.ProfileLoadBalanceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Controller for profile service related requests.
 * the [show details] button on the group chat UI will send a request to "/profile"
 *
 * @author lucas
 */
@Controller
@RibbonClient(name = "profile", configuration = ProfileLoadBalanceConfig.class)
public class ProfileController {

    /**
     * REST template for handling HTTP requests, applying Ribbon load balancing here.
     *
     * @return template
     */
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * REST template.
     */
    @Autowired
    RestTemplate restTemplate;

    /**
     * Map to "/profile" HTTP requests.
     *
     * @param username parameter
     * @return JSON string (profile result)
     */
    @RequestMapping(value = "/profile", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public String getProfile(@RequestParam String username) {

        String url = "http://profile/profile";
        HttpHeaders headers = new HttpHeaders();
        // add parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("username", username);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        // send get request
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        return response.getBody();
    }

    /**
     * Map to "/getallprofiles" HTTP requests.
     *
     * @return JSON string (profile result)
     */
    @RequestMapping(value = "/getallprofiles", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public String getAllProfiles() {

        String url = "http://profile/getallprofiles";
        HttpHeaders headers = new HttpHeaders();
        // add parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        // send get request
        HttpEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class);

        return response.getBody();
    }
}

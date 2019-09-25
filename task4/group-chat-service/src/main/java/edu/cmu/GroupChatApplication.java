package edu.cmu;

import edu.cmu.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spring boot main class to start the application.
 *
 * @author lucas
 */
@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
@EnableConfigurationProperties(ApplicationProperties.class)
public class GroupChatApplication {

    /**
     * Main method.
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(GroupChatApplication.class, args);
    }

}

package edu.cmu;

import edu.cmu.model.Profile;
import edu.cmu.model.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Spring boot starter for profile service.
 *
 * @author lucas
 */
@SpringBootApplication
public class ProfileApplication {

    /**
     * Main method.
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(ProfileApplication.class, args);
    }

    /**
     * logger for profile application.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileApplication.class);

    /**
     * Invoked on application start.
     * Save some data to database
     *
     * @param repository profile repo
     * @return cmd runner
     */
    @Bean
    public CommandLineRunner loadData(ProfileRepository repository) {
        return (args) -> {
            // save a couple of profiles
            repository.save(new Profile("lucas", "Lucas Liu", "Male", 24, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("majd", "Majd Sakr", "Male", 30, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("marshall", "Marshall An", "Male", 26, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("punit", "Punit Singh Koura", "Male", 26, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("eric", "Eric Song", "Male", 23, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("zexian", "Zexian Wang", "Male", 24, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("xuannan", "Xuannan Su", "Male", 23, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("weitian", "Weitian Ding", "Male", 23, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("zhiyang", "Zhiyang Liu", "Female", 24, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("sriharsha", "Sriharsha Bandaru", "Male", 26, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("shaleen", "Shaleen Kumar Gupta", "Male", 25, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("siddharth", "Siddharth Kandimalla", "Male", 24, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("joey", "Joey Pinto", "Male", 24, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("siang", "Siang Gao", "Male", 23, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("chang", "Chang Xu", "Male", 24, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("ye", "Ye Li", "Male", 25, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("apoorv", "Apoorv Gupta", "Male", 24, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("dachi", "Dachi Chen", "Male", 23, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("sai", "Sai Kiriti Badam", "Male", 26, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("yan", "Yan Shen", "Male", 26, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("peiyao", "Peiyao Zhou", "Female", 24, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("rui", "Rui Chen", "Male", 23, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));
            repository.save(new Profile("sahil", "Sahil Hasan", "Male", 23, "https://avatars1.githubusercontent.com/u/9340013?s=400&u=8b0dc47e48df6b47b8f95c547b1f0ade25376127&v=4"));

            // fetch all profiles
            LOGGER.info("Profiles found with findAll():");
            LOGGER.info("-------------------------------");
            for (Profile profile : repository.findAll()) {
                LOGGER.info(profile.toString());
            }
            LOGGER.info("");
        };
    }
}

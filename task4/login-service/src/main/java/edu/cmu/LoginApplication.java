package edu.cmu;

import edu.cmu.model.UserCredential;
import edu.cmu.model.UserCredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring boot starter.
 *
 * @author lucas
 */
@SpringBootApplication
public class LoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

    /**
     * logger for login service.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginApplication.class);

    /**
     * Invoked on application start.
     * Save some data to database
     *
     * @param repository user credential repo
     * @return cmd runner
     */
    @Bean
    public CommandLineRunner loadData(UserCredentialRepository repository) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return (args) -> {
            // save a couple of users (encode with BCryptPasswordEncoder)
            repository.save(new UserCredential("lucas", passwordEncoder.encode("lucas")));
            repository.save(new UserCredential("majd", passwordEncoder.encode("majd")));
            repository.save(new UserCredential("marshall", passwordEncoder.encode("marshall")));
            repository.save(new UserCredential("punit", passwordEncoder.encode("punit")));
            repository.save(new UserCredential("eric", passwordEncoder.encode("eric")));
            repository.save(new UserCredential("zexian", passwordEncoder.encode("zexian")));
            repository.save(new UserCredential("xuannan", passwordEncoder.encode("xuannan")));
            repository.save(new UserCredential("weitian", passwordEncoder.encode("weitian")));
            repository.save(new UserCredential("zhiyang", passwordEncoder.encode("zhiyang")));
            repository.save(new UserCredential("sriharsha", passwordEncoder.encode("sriharsha")));
            repository.save(new UserCredential("shaleen", passwordEncoder.encode("shaleen")));
            repository.save(new UserCredential("siddharth", passwordEncoder.encode("siddharth")));
            repository.save(new UserCredential("joey", passwordEncoder.encode("joey")));
            repository.save(new UserCredential("siang", passwordEncoder.encode("siang")));
            repository.save(new UserCredential("chang", passwordEncoder.encode("chang")));
            repository.save(new UserCredential("ye", passwordEncoder.encode("ye")));
            repository.save(new UserCredential("apoorv", passwordEncoder.encode("apoorv")));
            repository.save(new UserCredential("dachi", passwordEncoder.encode("dachi")));
            repository.save(new UserCredential("sai", passwordEncoder.encode("sai")));
            repository.save(new UserCredential("yan", passwordEncoder.encode("yan")));
            repository.save(new UserCredential("peiyao", passwordEncoder.encode("peiyao")));
            repository.save(new UserCredential("rui", passwordEncoder.encode("rui")));
            repository.save(new UserCredential("sahil", passwordEncoder.encode("sahil")));

            // fetch all users
            LOGGER.info("UserCredentials found with findAll():");
            LOGGER.info("-------------------------------");
            for (UserCredential userCredential : repository.findAll()) {
                LOGGER.info(userCredential.toString());
            }
            LOGGER.info("");
        };
    }
}

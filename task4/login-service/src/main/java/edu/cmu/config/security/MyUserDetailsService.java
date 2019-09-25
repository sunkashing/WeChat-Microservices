package edu.cmu.config.security;

import edu.cmu.model.UserCredential;
import edu.cmu.model.UserCredentialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * User Details, required by Spring Security.
 * it's used to authenticate the user
 *
 * @author lucas
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    /**
     * user credential repo
     */
    @Autowired
    private UserCredentialRepository userCredentialRepository;
    /**
     * logger for MyUserDetailsService
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);

    /**
     * Get user details by username.
     *
     * @param username username
     * @return user details
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserCredential user = userCredentialRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        LOGGER.info(user.getUsername() + ":" + user.getPassword());
        // User is a Spring Security defined class
        return new User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, true, Collections.singletonList(new SimpleGrantedAuthority("User")));
    }
}
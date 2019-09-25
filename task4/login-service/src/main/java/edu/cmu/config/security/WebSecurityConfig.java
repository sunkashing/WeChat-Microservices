package edu.cmu.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

/**
 * Config for spring security.
 * authenticate users
 *
 * @author lucas
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * custom user details
     */
    @Autowired
    private MyUserDetailsService userDetailsService;
    /**
     * JPA data source
     */
    @Autowired
    private DataSource dataSource;

    /**
     * Config requests that need to be authenticated, login, logout etc.
     *
     * @param http http
     * @throws Exception exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .successHandler(myAuthenticationSuccessHandler())
                .and()
            .logout()
                .permitAll();
        http.csrf().disable();
    }

    /**
     * Config auth methods.
     * include user details, add auth data source etc.
     *
     * @param auth auth
     * @throws Exception exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder())
                .and()
                .jdbcAuthentication()
                .dataSource(dataSource)
                // dummy authority handle function (we don't distinguish users by authorities in this project)
                .authoritiesByUsernameQuery("select username, 'ROLE_USER' from users where username=?");
    }

    /**
     * Encode the password.
     *
     * @return encoder
     */
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Get success handler.
     *
     * @return success handler
     */
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
}
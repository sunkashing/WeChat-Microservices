package edu.cmu.config.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Process on Spring security auth success.
 * forward to group chat service with username as parameter
 * using parameter is for simplicity, may consider using attributes or some other approaches
 *
 * @auther lucas
 */
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    /**
     * logger for handler.
     */
    protected final Log LOGGER = LogFactory.getLog(this.getClass());
    /**
     * define redirect strategy.
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public MySimpleUrlAuthenticationSuccessHandler() {
        super();
    }

    /**
     * get chat endpoint from application.properties file.
     */
    @Value("${groupchat.service.endpoint}")
    private String chatEndpoint;

    /**
     * Invoked on auth success.
     *
     * @param request        request
     * @param response       response
     * @param authentication auth
     * @throws IOException exception
     */
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    /**
     * Core method for handling onSuccess.
     * redirect to group chat service
     *
     * @param request        request
     * @param response       response
     * @param authentication auth
     * @throws IOException exception
     */
    protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        final String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            LOGGER.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    /**
     * Determine the target URL.
     *
     * @param authentication auth
     * @return URL as a string
     */
    protected String determineTargetUrl(final Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        LOGGER.info(user.getUsername());
        return "http://" + chatEndpoint + "?username=" + user.getUsername();
    }

    /**
     * Removes temporary authentication-related data.
     * It may have been stored in the session during the authentication process.
     *
     * @param request request
     */
    protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}

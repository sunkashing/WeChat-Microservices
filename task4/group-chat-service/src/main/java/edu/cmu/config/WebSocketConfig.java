package edu.cmu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;


/**
 * Config web socket.
 * Using Stomp | WebSocket | TCP
 * <p>
 * For more information about stomp, refer to: https://spring.io/guides/gs/messaging-stomp-websocket/
 *
 * @author lucas
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * logger for web socket config.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketConfig.class);

    /**
     * username parameter name.
     */
    private static final String USERNAME_PARAMETER = "username";

    /**
     * Handshake interceptor.
     * Log some web socket connection information
     * Potentially it's possible to extract some request/response information
     */
    class SessionAuthHandshakeInterceptor implements HandshakeInterceptor {

        /**
         * logger for interceptor.
         */
        private Logger logger = LoggerFactory.getLogger(this.getClass());

        /**
         * Invoked before the handshake is processed.
         *
         * @param request    request
         * @param response   response
         * @param wsHandler  web socket handler
         * @param attributes attributes
         * @return whether to proceed with the handshake (true) or abort (false)
         */
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
            LOGGER.info("Before Handshake!");
            LOGGER.info(String.valueOf(request.getRemoteAddress()));
            LOGGER.info(String.valueOf(request.getURI()));
            LOGGER.info(String.valueOf(request.getHeaders()));
            if (request instanceof ServletServerHttpRequest) {
                ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
                LOGGER.info(serverRequest.getServletRequest().getParameter(USERNAME_PARAMETER));
            }
            return true;
        }

        /**
         * Invoked after the handshake is done.
         * The response status and headers indicate the results of the handshake, i.e. whether it was successful or not.
         *
         * @param request   request
         * @param response  response
         * @param wsHandler web socket handler
         * @param exception exception
         */
        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

        }
    }

    /**
     * Config stomp registry.
     * set endpoint, interceptor etc.
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/redis-chat")
                .addInterceptors(new SessionAuthHandshakeInterceptor())
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * Config message broker.
     *
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Log on stomp socket connected.
     *
     * @param event Session Connected Event
     */
    @EventListener
    public void onSocketConnected(SessionConnectedEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        LOGGER.info("Username: " + sha.getNativeHeader("username"));
        LOGGER.info("WebSocket Session Connected: {}", event.getMessage().getHeaders().get("username"));
        LOGGER.info("Connect event [sessionId: {} ]", sha.getSessionId());
    }

    /**
     * Log on stomp socket disconnected.
     *
     * @param event Session Disconnect Event
     */
    @EventListener
    public void onSocketDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        LOGGER.info("WebSocket Session Disconnected: {}", event.getMessage());
        LOGGER.info("DisConnect event [sessionId: {}]", sha.getSessionId());
    }

}

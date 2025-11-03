package com.siduska.ehealthwallet.mesaging.conf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, ApplicationListener<SessionDisconnectEvent> {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //topic
        config.enableSimpleBroker("/topic");
        //app
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins(
                        "http://localhost:4200",
                        "http://localhost:8080",
                        "https://e-health-wallet.netlify.app",
                        "https://e-health-wallet.up.railway.app"
                );
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        LOGGER.info("WebSocket client disconnected: {}", event.getSessionId());
    }
}

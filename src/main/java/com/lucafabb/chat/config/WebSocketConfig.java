/*In sintesi, questo codice configura un'applicazione Spring per utilizzare i WebSocket e il protocollo STOMP per abilitare la comunicazione in tempo reale tra il client e il server. L'endpoint WebSocket è registrato su "/ws" e il message broker è configurato per utilizzare i prefissi "/app" per le richieste dei client e "/topic" per inoltrare i messaggi ai client.*/



package com.lucafabb.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/*Queste annotazioni indicano a Spring che questa classe è una configurazione e che deve essere abilitata per gestire i WebSocket e il message broker.*/
@Configuration
@EnableWebSocketMessageBroker
/*La classe WebSocketConfig implementa l'interfaccia WebSocketMessageBrokerConfigurer, che fornisce metodi per configurare i WebSocket e il message broker.*/
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /*Questo metodo registra un'endpoint WebSocket "/ws" che i client possono utilizzare per stabilire una connessione WebSocket. Il metodo withSockJS() abilita la compatibilità con SockJS, una libreria JavaScript che fornisce un'astrazione per la comunicazione WebSocket e che è utile per gestire situazioni in cui WebSocket potrebbe non essere supportato.*/
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }
    /*questo metodo configura il message broker utilizzato per instradare i messaggi tra client e server. In questo caso, setApplicationDestinationPrefixes("/app") imposta il prefisso degli indirizzi destinazione delle richieste client che saranno instradate verso metodi di gestione nel server. enableSimpleBroker("/topic") abilita un message broker semplice che inoltrerà i messaggi ai client tramite i topic che iniziano con "/topic".*/
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}

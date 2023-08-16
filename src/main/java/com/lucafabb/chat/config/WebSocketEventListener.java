/*In sintesi, questo componente gestisce l'evento di disconnessione dei client WebSocket, registra il fatto che un utente è stato disconnesso e invia un messaggio a tutti i client per informarli della disconnessione.*/


package com.lucafabb.chat.config;

import com.lucafabb.chat.chat.ChatMessage;
import com.lucafabb.chat.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/*che indica che è un componente gestito da Spring*/
@Component
 /*è fornita dalla libreria Lombok e genera automaticamente un costruttore con tutti i campi finali richiesti.*/
@RequiredArgsConstructor
/*@Slf4j semplifica l'aggiunta di un logger alle tue classi, contribuendo a una migliore gestione dei messaggi di log per il debugging e il monitoraggio delle tue applicazioni.*/
@Slf4j
public class WebSocketEventListener {

   /* L'oggetto SimpMessageSendingOperations è iniettato nel costruttore attraverso l'annotazione @RequiredArgsConstructor. Questo oggetto consente di inviare messaggi ai client tramite il broker dei messaggi.*/
    private final SimpMessageSendingOperations messageTemplate;

   /* Metodo handleWebSocketDisconnectListener: Questo metodo è annotato con @EventListener e gestisce gli eventi di disconnessione dei client WebSocket. Prende in ingresso un oggetto SessionDisconnectEvent che rappresenta l'evento di disconnessione di una sessione WebSocket.*/
   /*Viene utilizzato StompHeaderAccessor.wrap(event.getMessage()) per ottenere un oggetto StompHeaderAccessor che consente di accedere agli header e agli attributi del messaggio.*/
    /*Viene recuperato il nome utente dall'attributo di sessione "username" usando headerAccessor.getSessionAttributes().get("username").
    Se il nome utente non è nullo, viene registrato un messaggio di log che indica che l'utente è stato disconnesso.
    Viene creato un oggetto ChatMessage che rappresenta un messaggio di chat di tipo "LEAVE" (indicante la disconnessione) e con il mittente (sender) impostato sul nome utente.
    Infine, il messaggio viene inviato a tutti i client tramite il metodo convertAndSend dell'oggetto SimpMessageSendingOperations, utilizzando il topic "/topic/public" come destinazione.*/
    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}

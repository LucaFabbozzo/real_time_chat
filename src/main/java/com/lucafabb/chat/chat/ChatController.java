/*questo controller gestisce le richieste dei client WebSocket per inviare messaggi e per aggiungere nuovi utenti alla chat. I messaggi inviati e ricevuti sono trasmessi ai client attraverso i topic "/topic/public"*/


package com.lucafabb.chat.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

/*Questa classe è annotata con @Controller, che indica che è un controller di Spring che gestisce le richieste e produce risposte.*/
@Controller
public class ChatController {

    /*Questa annotazione mappa il metodo alla destinazione di messaggio "/chat.sendMessage" quando un messaggio è inviato da un client*/
    @MessageMapping("/chat.sendMessage")
    /*Indica che il messaggio inviato dal metodo sarà trasmesso a tutti i client con il topic "/topic/public".*/
    @SendTo("/topic/public")

    public ChatMessage sendMessage(
            /*Questo parametro rappresenta il payload del messaggio ricevuto dal client. È un oggetto di tipo ChatMessage che contiene i dettagli del messaggio.*/
           @Payload ChatMessage chatMessage
    ) {
       /* Il metodo restituisce semplicemente il messaggio ricevuto, che sarà poi inviato a tutti i client tramite il broker dei messaggi.*/
        return chatMessage;
    }

    /*Questa annotazione mappa il metodo alla destinazione di messaggio "/chat.addUser" quando un nuovo utente si unisce alla chat.*/
    @MessageMapping("/chat.addUser")
    /*indica che il messaggio inviato dal metodo sarà trasmesso a tutti i client con il topic "/topic/public*/
    @SendTo("/topic/public")
    public ChatMessage addUser(
            /*Questo parametro rappresenta il payload del messaggio ricevuto dal client quando si unisce alla chat*/
            @Payload ChatMessage chatMessage,
            /*Questo parametro consente di accedere agli header e agli attributi del messaggio.*/
            SimpMessageHeaderAccessor headerAccessor
    ) {
        //add username in a web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}

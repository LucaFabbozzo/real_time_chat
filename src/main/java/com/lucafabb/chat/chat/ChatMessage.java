/*questa classe è un modello di dati che rappresenta un messaggio all'interno di una chat WebSocket. Grazie alle annotazioni di Lombok, la creazione di costruttori, getter e setter è semplificata, consentendo di scrivere codice più pulito e conciso*/

package com.lucafabb.chat.chat;

import lombok.*;

/*Queste annotazioni sono fornite dalla libreria Lombok. @Getter genera automaticamente i metodi di accesso (getter) per i campi della classe, mentre @Setter genera automaticamente i metodi di modifica (setter) per i campi della classe.*/
@Getter
@Setter
/*Questa annotazione di Lombok genera automaticamente un costruttore con tutti i campi della classe come parametri.*/
@AllArgsConstructor
/* Questa annotazione di Lombok genera automaticamente un costruttore senza argomenti.*/
@NoArgsConstructor
/*Questa annotazione di Lombok fornisce un costruttore di tipo builder che consente di costruire oggetti ChatMessage con un approccio fluente e leggibile*/
@Builder
public class ChatMessage {
    /*Rappresenta il contenuto del messaggio*/
    private String content;
    /*Rappresenta il mittente del messaggio*/
    private String sender;
   /* Rappresenta il tipo di messaggio (ad esempio, "JOIN" per un utente che si unisce, "LEAVE" per un utente che lascia, ecc*/
    private MessageType type;
}

/*
L'uso di un'enumerazione come MessageType aiuta a rendere il codice più leggibile e organizzato, poiché consente di avere un elenco chiaro dei possibili tipi di messaggio e rende più facile la gestione dei diversi comportamenti associati a ciascun tipo di messaggio all'interno dell'applicazione.
    Questi tipi di messaggio sono utilizzati per identificare la natura del messaggio all'interno dell'applicazione WebSocket. Ad esempio, quando un client invia un messaggio, può specificare se si tratta di un messaggio di chat normale, di un messaggio che indica che l'utente si sta unendo alla chat o di un messaggio che indica che l'utente sta lasciando la cha
*/


package com.lucafabb.chat.chat;

public enum MessageType {
    //Rappresenta un messaggio di chat standard inviato da un utente
    CHAT,
    //Rappresenta un messaggio inviato quando un utente si unisce alla chat
    JOIN,
    //Rappresenta un messaggio inviato quando un utente lascia la chat
    LEAVE
}

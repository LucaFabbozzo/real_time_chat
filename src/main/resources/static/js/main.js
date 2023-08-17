/* definisce le funzioni e gli event listeners per gestire l'interfaccia utente e la comunicazione con il server WebSocket. Quando l'utente invia il nome utente attraverso il form, la connessione WebSocket viene stabilita e l'utente può inviare e ricevere messaggi nella chat. Gli avatar sono colorati in base al nome utente. Le funzioni onConnected, onError, sendMessage e onMessageReceived sono chiamate in risposta a eventi di connessione, errore, invio di messaggio e ricezione di messaggio rispettivamente. */

'use strict';

//Selezionare gli elementi dell'interfaccia utente
var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

//Variabili per gestire la connessione WebSocket
var stompClient = null;
var username = null;

// Lista di colori per gli avatar
var colors = [
               '#2196F3', '#32c787', '#00BCD4', '#ff5652',
               '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
             ];


// Funzione per connettersi al server WebSocket
function connect(event) {
    username = document.querySelector('#name').value.trim();

    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

// Funzione chiamata quando la connessione WebSocket è stabilita
function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}

// Funzione chiamata se si verifica un errore di connessione
function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

// Funzione per inviare un messaggio
function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


// Funzione chiamata quando viene ricevuto un messaggio dal server
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

// Funzione per ottenere un colore casuale per l'avatar
function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

// Aggiungere gli event listeners per la pagina di username e per l'invio dei messaggi
usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
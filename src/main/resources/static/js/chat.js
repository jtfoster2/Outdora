// import { Client } from '@stomp/stompjs';

// Create a new SockJS instance
const socket = new SockJS('http://127.0.0.1:8080/ws');

// Create a STOMP client over the SockJS instance
// const client = new Client({
//     webSocketFactory: () => socket
// });
const client = Stomp.over(socket);

// client.onConnect = () => {
//     console.log('Connected');
// };

// Assuming Stomp.over(socket) is correct for your version
client.connect({}, function(frame) {
    console.log('Connected: ' + frame);

    client.subscribe(`/topic/chat/${userId}/${recipientId}`, function(message) {
        if (message && message.body) {
            try {
                console.log("GETTING MESSAGE: " + message)
                let parsedMessage = JSON.parse(message.body);
                displayMessage(parsedMessage);
            } catch (error) {
                console.error('Error parsing message body:', error);
            }
        } else {
            console.log('Received an empty or invalid message');
        }
    });
});

function handleSendMessage() {
    sendMessageWithParams(userId, recipientId, document.getElementById('messageInput').value);
    document.getElementById('messageInput').value = '';
    document.getElementById('messageInput').focus();
}

function sendMessageWithParams(senderId, recipientId, messageContent) {
    if(messageContent) {
        client.send(`/app/chat/${senderId}/${recipientId}`, {}, messageContent);
    }
}

function displayMessage(message) {
    var messageContainer = document.getElementById('messageContainer');
    var messageElement = document.createElement('div');
    messageElement.classList.add('message');
    if (message.senderId == userId) {
        messageElement.innerText = message.timestamp + " - " + "Me" + ": " + message.content;
    } else {
        messageElement.innerText = message.timestamp + " - " + recipientName + ": " + message.content;
    }
    messageContainer.appendChild(messageElement);
}
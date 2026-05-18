// public/javascripts/chat.js

document.addEventListener("DOMContentLoaded", () => {
    // 1. Recupero l'ID del film dall'HTML (che Handlebars ha inserito)
    const chatContainer = document.getElementById('chat-container');
    const movieId = chatContainer.getAttribute('data-movie-id');

    if (!movieId) {
        console.error("Errore: Movie ID non trovato per la chat.");
        return;
    }

    // 2. Inizializzo Socket.io
    const socket = io(window.location.origin, { transports: ['websocket', 'polling'] });
    const room = `room_film_${movieId}`;
    const user = 'User_' + Math.floor(Math.random() * 1000);

    socket.on('connect', () => {
        console.log("✅ [Socket] Connesso al server con ID:", socket.id);
        socket.emit('joinRoom', room);
    });

    socket.on('message', (data) => {
        console.log("📩 [Socket] Messaggio ricevuto:", data);
        const box = document.getElementById('chat-box');
        box.innerHTML += `<div><strong class="text-orange">${data.user}:</strong> <span>${data.text}</span></div>`;
        box.scrollTop = box.scrollHeight;
    });

    // 3. Rendo la funzione globale per poterla chiamare dal bottone HTML
    window.sendChatMessage = function() {
        const input = document.getElementById('chat-input');
        const message = input.value.trim();

        if (message) {
            socket.emit('sendMessage', {
                roomName: room,
                message: message,
                userId: user
            });
            input.value = '';
        }
    };
});
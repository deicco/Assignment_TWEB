/**
 * @file chat.js
 * @description Client-side script managing real-time chat communication via Socket.io.
 * It handles automatic room assignment based on the active movie identifier and
 * dynamically updates the chat interface without requiring page reloads.
 * @version 1.1.0
 */

document.addEventListener("DOMContentLoaded", () => {
    /**
     * The HTML element wrapper containing the chat components and metadata.
     * @type {HTMLElement|null}
     */
    const chatContainer = document.getElementById('chat-container');

    /**
     * The unique identifier of the current movie, extracted from the container's custom data attribute.
     * @type {string|null}
     */
    const movieId = chatContainer ? chatContainer.getAttribute('data-movie-id') : null;

    if (!movieId) {
        console.error("[Chat Error] Movie ID not found. Chat initialization aborted.");
        return;
    }

    /**
     * Socket.io client instance established with the server origin, configured with standard protocols.
     * @type {Object}
     */
    const socket = io(window.location.origin, { transports: ['websocket', 'polling'] });

    /**
     * The dedicated message room name unique to the current movie entity.
     * @type {string}
     */
    const room = `room_film_${movieId}`;

    /**
     * A temporary, randomly generated username to represent the anonymous session member.
     * @type {string}
     */
    const user = 'User_' + Math.floor(Math.random() * 1000);

    /**
     * Handles the 'connect' event from Socket.io.
     * Dispatches a request to the server to join the isolated chat room for this movie.
     */
    socket.on('connect', () => {
        console.log(`✅ [Socket] Successfully connected to server with ID: ${socket.id}`);
        socket.emit('joinRoom', room);
    });

    /**
     * Handles the 'message' event broadcasted from the server room.
     * Appends the chat payload content into the view's conversation container and adjusts the scrollbar.
     * @param {Object} data - The message payload transmitted by the server.
     * @param {string} data.user - The identification string of the message author.
     * @param {string} data.text - The descriptive text content of the message.
     */
    socket.on('message', (data) => {
        console.log("📩 [Socket] New message received:", data);
        const box = document.getElementById('chat-box');

        if (box) {
            // 1. Create the main wrapper div for the message
            const messageDiv = document.createElement('div');
            messageDiv.className = "mb-2";

            // 2. Create the strong tag for the author's username
            const authorStrong = document.createElement('strong');
            authorStrong.className = "text-orange";
            authorStrong.textContent = `${data.user}: `;

            // 3. Create the span tag for the message text (textContent prevents XSS)
            const textSpan = document.createElement('span');
            textSpan.className = "text-light";
            textSpan.textContent = data.text;

            // 4. Assemble the elements by appending them to the main div
            messageDiv.appendChild(authorStrong);
            messageDiv.appendChild(textSpan);

            // 5. Append the fully constructed message div to the chat box
            box.appendChild(messageDiv);

            // Automatically scroll to the bottom of the chat
            box.scrollTop = box.scrollHeight;
        }
    });

    /**
     * DOM references to layout controls for message transmission.
     */
    const sendButton = document.getElementById('chat-submit-btn');
    const input = document.getElementById('chat-input');

    if (sendButton && input) {
        /**
         * Triggers message sending when the visual submission button is clicked.
         */
        sendButton.addEventListener('click', (e) => {
            e.preventDefault();
            sendMessage();
        });

        /**
         * Triggers message sending when the 'Enter' key is pressed inside the input field.
         */
        input.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                e.preventDefault();
                sendMessage();
            }
        });

        /**
         * Extracts text from the client input element and transmits the payload to the server-side room.
         * Clears the input field upon successful transmission.
         * @function sendMessage
         * @returns {void}
         */
        function sendMessage() {
            const message = input.value.trim();
            if (message) {
                socket.emit('sendMessage', {
                    message: message,
                    userId: user,
                    roomName: room // Kept for server-side routing compatibility
                });
                input.value = '';
            }
        }
    }
});
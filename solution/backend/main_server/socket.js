/**
 * @file socket.js
 * @description Module for handling real-time communication via Socket.io.
 * It manages room-based chat sessions and event broadcasting between clients.
 * @version 1.0.0
 */

const { Server } = require('socket.io');

/**
 * Initializes the Socket.io server and defines event listeners for real-time chat.
 * * @param {Object} server - The HTTP server instance created in bin/www.
 *
 */
function initSocket(server) {
    /**
     * @description Configures the Socket.io server with CORS support
     * to allow connections from the WebStorm static server.
     */
    const io = new Server(server, {
        cors: {
            origin: "http://localhost:63344",
            methods: ["GET", "POST"]
        }
    });

    /**
     * Event listener for new client connections.
     * @param {Object} socket - The individual socket object representing the client.
     */
    io.on('connection', (socket) => {
        console.log(`[Socket] New connection: ${socket.id}`);

        /**
         * @event joinRoom
         * @description Allows a user to join a specific movie's chat room.
         * The room name is typically formatted as 'room_film_{movieId}'.
         *
         * @param {string} roomName - The unique identifier for the movie chat room.
         */
        socket.on('joinRoom', (roomName) => {
            socket.join(roomName);
            console.log(`[Socket] User ${socket.id} joined: ${roomName}`);
        });

        /**
         * @event sendMessage
         * @description Receives a message from a client and broadcasts it to everyone in the same room.
         *
         * @param {Object} data - The message payload.
         * @param {string} data.roomName - The room where the message should be broadcasted.
         * @param {string} data.message - The text content of the message.
         * @param {string} data.userId - The username or ID of the sender.
         */
        socket.on('sendMessage', (data) => {
            io.to(data.roomName).emit('message', {
                user: data.userId,
                text: data.message
            });
            console.log(`[Socket] Message sent to ${data.roomName} by ${data.userId}`);
        });

        /**
         * Event listener for client disconnection.
         */
        socket.on('disconnect', () => {
            console.log(`[Socket] User disconnected: ${socket.id}`);
        });
    });
}

module.exports = { initSocket };
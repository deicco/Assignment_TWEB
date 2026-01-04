/**
 * app.js
 * Main Gateway Server for the Cinema Application.
 * * Responsibilities:
 * 1. Acts as a reverse proxy for the Java Spring Boot Backend (Static Data).
 * 2. Acts as a reverse proxy for the Node.js MongoDB Microservice (Dynamic Data/Reviews).
 * 3. Manages Real-Time Chat communication via Socket.io.
 * * @module MainGatewayServer
 */

const express = require('express');
const cors = require('cors');
const axios = require('axios');
const http = require('http');
const { Server } = require('socket.io');

const app = express();
const PORT = 3000;

// --- Backend Microservices URLs ---
const JAVA_URL = 'http://localhost:8082'; // Spring Boot Server (Static Data: Movies, Actors, etc.)
const MONGO_URL = 'http://localhost:3001'; // MongoDB Review Server (Dynamic Data: Reviews)

// --- Middleware Configuration ---
app.use(cors());
app.use(express.json());

// --- Socket.io Initialization ---
const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: "*", // Allow connections from any origin (Client)
        methods: ["GET", "POST"]
    }
});

// ----------------------------------------------------
// A. DYNAMIC DATA ENDPOINT (Reviews)
// ----------------------------------------------------

/**
 * Routes requests for reviews to the MongoDB Microservice.
 * * @route GET /reviews/movie/:title
 * @param {string} title - The title of the movie to search for.
 * @param {number} [limit=20] - Query parameter to limit the number of results.
 */
app.get('/reviews/movie/:title', async (req, res) => {
    const { title } = req.params;
    const limit = req.query.limit || 20;
    const fullUrl = `${MONGO_URL}/reviews/movie/${title}?limit=${limit}`;

    try {
        console.log(`[GATEWAY] Forwarding to Reviews Server: ${fullUrl}`);
        const response = await axios.get(fullUrl);
        res.status(response.status).json(response.data);
    } catch (error) {
        if (error.response) {
            return res.status(error.response.status).json(error.response.data);
        }
        res.status(503).json({ error: 'Service Unavailable', details: 'Cannot reach Reviews server (Port 3001).' });
    }
});


// ----------------------------------------------------
// B. STATIC DATA ENDPOINT (Gateway to Spring Boot)
// ----------------------------------------------------

/**
 * General Gateway for all requests directed to the Java Spring Boot Backend.
 * Intercepts all calls starting with /api.
 * * @route ALL /api/*
 */
app.use('/api', async (req, res) => {

    // Note: We use req.path instead of req.url.
    // req.url includes the query string (e.g., "/movies?page=0"), causing duplication when Axios appends params again.
    // req.path provides the clean path (e.g., "/movies").
    const javaPath = req.path;
    const fullUrl = `${JAVA_URL}${javaPath}`;

    // --- Parameter Sanitization ---
    // Clones the query object and trims string parameters (e.g., removes whitespace from search queries).
    const javaQuery = { ...req.query };
    if (javaQuery.name && typeof javaQuery.name === 'string') {
        javaQuery.name = javaQuery.name.trim();
        if (javaQuery.name === "") delete javaQuery.name;
    }

    console.log(`[GATEWAY] Forwarding to Java Server: ${fullUrl} with params:`, javaQuery);

    try {
        // Forward the request using Axios
        const response = await axios({
            method: req.method,
            url: fullUrl,
            params: javaQuery, // Axios correctly appends params here
            data: req.body
        });

        // --- Anti-caching Headers ---
        // Ensures the client always fetches fresh data from the backend.
        res.set('Cache-Control', 'no-store, no-cache, must-revalidate, proxy-revalidate');
        res.set('Pragma', 'no-cache');
        res.set('Expires', '0');

        res.status(response.status).json(response.data);

    } catch (error) {
        if (error.response) {
            return res.status(error.response.status).json(error.response.data);
        }
        res.status(503).json({ error: 'Service Unavailable', details: 'Cannot reach Java server (Port 8082).' });
    }
});

// ----------------------------------------------------
// C. CHAT LOGIC (Socket.io)
// ----------------------------------------------------

/**
 * Handles real-time websocket connections.
 * Manages chat rooms based on movie/actor topics.
 */
io.on('connection', (socket) => {
    console.log(`Socket.io User Connected: ${socket.id}`);

    /**
     * Event: joinRoom
     * Adds the user to a specific topic room (e.g., a specific Movie ID or Actor Name).
     * @param {string} roomName - The identifier for the room.
     */
    socket.on('joinRoom', (roomName) => {
        socket.join(roomName);
        console.log(`User ${socket.id} joined room: ${roomName}`);
        // Notify others in the room
        socket.to(roomName).emit('message', { user: 'System', text: `A new user has joined the discussion on ${roomName}.` });
    });

    /**
     * Event: sendMessage
     * Broadcasts a message to all users in the specified room.
     * @param {Object} payload - Contains roomName, message text, and userId.
     */
    socket.on('sendMessage', ({ roomName, message, userId }) => {
        io.to(roomName).emit('message', { user: userId, text: message, timestamp: new Date().toLocaleTimeString() });
    });

    socket.on('disconnect', () => {
        console.log(`Socket.io User Disconnected: ${socket.id}`);
    });
});

// ----------------------------------------------------
// D. SERVER STARTUP
// ----------------------------------------------------
server.listen(PORT, () => {
    console.log(`✨ Main Server Gateway & Chat running on http://localhost:${PORT}`);
});
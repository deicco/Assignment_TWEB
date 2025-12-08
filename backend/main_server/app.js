// app.js - Versione Finale e Completa

const express = require('express');
const cors = require('cors');
const axios = require('axios');
const http = require('http');
const { Server } = require('socket.io');

const app = express();
const PORT = 3000;

// URL dei Backend
const JAVA_URL = 'http://localhost:8082'; // Server Spring Boot
const MONGO_URL = 'http://localhost:3001'; // Server Reviews (Express/In-Memory)

app.use(cors());
app.use(express.json()); // Per leggere il JSON nei metodi POST/PUT

// --- Inizializzazione Socket.io ---
const server = http.createServer(app); // Il server HTTP ora gestisce Express E Socket.io
const io = new Server(server, {
    cors: {
        origin: "*", // Permette chiamate da qualsiasi origine (per testing frontend)
        methods: ["GET", "POST"]
    }
});

// ----------------------------------------------------
// A. ENDPOINT: Dati Dinamici (Recensioni/Reviews)
// ----------------------------------------------------

/**
 * GET /reviews/movie/:title
 * Endpoint specifico che chiama il server Node.js/Reviews (3001).
 * DEVE ESSERE DEFINITO PRIMA DEL CATCH-ALL.
 */
app.get('/reviews/movie/:title', async (req, res) => {
    const { title } = req.params;
    const limit = req.query.limit || 20;
    const fullUrl = `${MONGO_URL}/reviews/movie/${title}?limit=${limit}`;

    try {
        console.log(`[GATEWAY] Chiamata a Reviews Server: ${fullUrl}`);
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
// B. ENDPOINT: Dati Statici (Gateway verso Spring Boot/Java)
// ----------------------------------------------------

/**
 * app.all('*')
 * Endpoint CATCH-ALL: Cattura TUTTO il traffico rimanente (/movies, /actors, /releases, ecc.)
 * e lo inoltra al server Java (8082).
 * Nota: Questo è l'ultimo endpoint REST definito.
 */
app.use(async (req, res) => {
    // req.url cattura l'intero percorso (es. /movies/1?page=0)
    const javaPath = req.url;
    const fullUrl = `${JAVA_URL}${javaPath}`;

    // Safety check: Esclude il traffico di Socket.io se per caso sfugge
    if (javaPath.startsWith('/socket.io')) {
        return;
    }

    console.log(`[GATEWAY] Inoltro richiesta a Java: ${fullUrl} [${req.method}]`);

    try {
        // Inoltra la richiesta, mantenendo il metodo originale (GET, POST, PUT, DELETE)
        const response = await axios({
            method: req.method,
            url: fullUrl,
            data: req.body // Inoltra il body per POST/PUT
        });

        res.status(response.status).json(response.data);

    } catch (error) {
        // Gestione degli errori dal server Java
        if (error.response) {
            return res.status(error.response.status).json(error.response.data);
        }
        res.status(503).json({ error: 'Service Unavailable', details: 'Cannot reach Java server (Port 8082).' });
    }
});


// ----------------------------------------------------
// C. LOGICA CHAT (SOCKET.IO)
// ----------------------------------------------------

io.on('connection', (socket) => {
    console.log(`Utente Socket.io connesso: ${socket.id}`);

    // Permette all'utente di entrare in una stanza (es. film/attore)
    socket.on('joinRoom', (roomName) => {
        socket.join(roomName);
        console.log(`Utente ${socket.id} entrato nella stanza: ${roomName}`);
        socket.to(roomName).emit('message', { user: 'System', text: `Un nuovo utente si è unito alla discussione su ${roomName}.` });
    });

    // Gestione dell'invio del messaggio
    socket.on('sendMessage', ({ roomName, message, userId }) => {
        io.to(roomName).emit('message', { user: userId, text: message, timestamp: new Date().toLocaleTimeString() });
    });

    socket.on('disconnect', () => {
        console.log(`Utente Socket.io disconnesso: ${socket.id}`);
    });
});


// ----------------------------------------------------
// D. AVVIO DEL SERVER
// ----------------------------------------------------

server.listen(PORT, () => {
    console.log(`✨ Main Server Gateway e Chat attivi su http://localhost:${PORT}`);
});
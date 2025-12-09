// app.js - Versione FINALE (Fix Doppia Query String)

const express = require('express');
const cors = require('cors');
const axios = require('axios');
const http = require('http');
const { Server } = require('socket.io');

const app = express();
const PORT = 3000;

// URL dei Backend
const JAVA_URL = 'http://localhost:8082'; // Server Spring Boot (Static Data)
const MONGO_URL = 'http://localhost:3001'; // Server Reviews (Dynamic Data)

app.use(cors());
app.use(express.json());

// --- Inizializzazione Socket.io ---
const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: "*",
        methods: ["GET", "POST"]
    }
});

// ----------------------------------------------------
// A. ENDPOINT: Dati Dinamici (Recensioni/Reviews)
// ----------------------------------------------------
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

app.use('/api', async (req, res) => {

    // Usiamo req.path invece di req.url.
    // req.url  = "/movies?page=0&size=20&name=Barbie" (Include già la query string)
    // req.path = "/movies" (Solo il percorso pulito)

    const javaPath = req.path;
    const fullUrl = `${JAVA_URL}${javaPath}`;

    // Pulizia Parametri (Trim)
    const javaQuery = { ...req.query };
    if (javaQuery.name && typeof javaQuery.name === 'string') {
        javaQuery.name = javaQuery.name.trim();
        if (javaQuery.name === "") delete javaQuery.name;
    }

    console.log(`[GATEWAY] Inoltro richiesta a Java: ${fullUrl} con params:`, javaQuery);

    try {
        const response = await axios({
            method: req.method,
            url: fullUrl,
            params: javaQuery, // Axios aggiungerà ?page=0&size=20&name=Barbie CORRETTAMENTE una volta sola
            data: req.body
        });

        // Header anti-cache
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
// C. LOGICA CHAT (SOCKET.IO)
// ----------------------------------------------------
io.on('connection', (socket) => {
    console.log(`Utente Socket.io connesso: ${socket.id}`);

    socket.on('joinRoom', (roomName) => {
        socket.join(roomName);
        console.log(`Utente ${socket.id} entrato nella stanza: ${roomName}`);
        socket.to(roomName).emit('message', { user: 'System', text: `Un nuovo utente si è unito alla discussione su ${roomName}.` });
    });

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
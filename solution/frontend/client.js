/**
 * client.js
 * Gestisce tutte le comunicazioni di rete con il Gateway (Porta 3000).
 */

const GATEWAY_URL = 'http://localhost:3000';

// Helper per pulire i parametri (rimuove null/undefined/stringhe vuote)
function cleanParams(params) {
    const cleaned = {};
    for (const key in params) {
        if (params[key] !== null && params[key] !== undefined && params[key] !== '') {
            cleaned[key] = params[key];
        }
    }
    return cleaned;
}

/**
 * 1. Recupera la lista paginata di film (da Java).
 * Endpoint: /api/movies
 */
async function getMoviesList(page, size, filters = {}) {
    const rawParams = {
        page: page,
        size: size,
        name: filters.name,
        startYear: filters.startYear,
        endYear: filters.endYear
    };

    const params = cleanParams(rawParams);

    try {
        // Usa /api/movies per andare su Java
        const response = await axios.get(`${GATEWAY_URL}/api/movies`, { params });
        return response.data;
    } catch (error) {
        console.error("Errore getMoviesList:", error);
        return { content: [], totalPages: 0, totalElements: 0 };
    }
}

/**
 * 2. Recupera dettaglio film (da Java).
 * Endpoint: /api/movies/{id}
 */
async function fetchMovieDetail(movieId) {
    try {
        const response = await axios.get(`${GATEWAY_URL}/api/movies/${movieId}`);
        return response.data;
    } catch (error) {
        console.error(`Errore fetchMovieDetail (${movieId}):`, error);
        return null;
    }
}

/**
 * 3. Recupera dati correlati come attori (da Java).
 * Endpoint: /api/actors/movie/{id}
 */
async function fetchRelatedPeople(movieId, endpoint) {
    try {
        const response = await axios.get(`${GATEWAY_URL}/api/${endpoint}/${movieId}`);
        return response.data;
    } catch (error) {
        console.error(`Errore fetchRelatedPeople (${endpoint}):`, error);
        return [];
    }
}

/**
 * 4. Recupera RECENSIONI (da MongoDB via Gateway).
 * CORREZIONE: NON usare /api/. Usa la rotta dedicata del Gateway.
 * Endpoint: /reviews/movie/{title}
 */
async function fetchReviews(movieTitle) {
    if (!movieTitle) return [];

    try {
        // Encode del titolo per gestire spazi e caratteri speciali (es. Percy Jackson & ...)
        const encodedTitle = encodeURIComponent(movieTitle.trim());

        // 🛠️ NOTA BENE: Qui NON c'è '/api'. Chiamiamo direttamente /reviews/movie/...
        const url = `${GATEWAY_URL}/reviews/movie/${encodedTitle}`;

        console.log(`[Client] Richiedo recensioni a: ${url}`); // Debug Log

        const response = await axios.get(url);
        return response.data;
    } catch (error) {
        // È normale avere 404 se non ci sono recensioni, o se il film non esiste su Mongo
        console.warn(`[Client] Nessuna recensione trovata per "${movieTitle}" (o errore server).`);
        return [];
    }
}
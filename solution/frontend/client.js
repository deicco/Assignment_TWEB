/**
 * @file client.js
 * @description Frontend network handler. Connects the WebStorm static server (Port 63343)
 * to the Express Gateway (Port 3000).
 * @version 1.3.0
 */

/**
 * @description The base URL points to the Gateway server.
 * This is required because the frontend is running on a different port than the backend.
 */
const BASE_URL = 'http://localhost:3000';

/**
 * @description Helper to remove null, undefined, or empty string parameters from requests.
 * @param {Object} params - The raw parameters object.
 * @returns {Object} A cleaned object with only valid parameters.
 */
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
 * @description 1. Fetches a paginated list of movies from the Gateway.
 * Aligned with Spring Boot Page object structure.
 *
 * @param {number} page - Current page index.
 * @param {number} size - Items per page.
 * @param {Object} filters - Search criteria (e.g., { name: '...' }).
 * @returns {Promise<Object>} The Spring Page object { content: [], totalPages: ... }.
 */
async function getMoviesList(page, size, filters = {}) {
    const rawParams = {
        page: page,
        size: size,
        name: filters.name
    };

    const params = cleanParams(rawParams);

    try {
        /**
         * Hits the Express Gateway (3000), which proxies to Spring Boot (8082).
         *
         */
        const response = await axios.get(`${BASE_URL}/movies`, { params });
        return response.data;
    } catch (error) {
        console.error("[Client] getMoviesList Error:", error.message);
        return { content: [], totalPages: 0, totalElements: 0 };
    }
}

/**
 * @description 2. Fetches full movie details and reviews.
 * Uses the PathVariable pattern required by the Spring Controller.
 *
 * @param {string|number} movieId - The unique ID of the movie.
 * @returns {Promise<Object|null>} Combined data from movie and review services.
 */
async function fetchMovieDetail(movieId) {
    try {
        /**
         * Endpoint mapped to @GetMapping("/{id}") in Spring Boot via Express proxy.
         *
         */
        const response = await axios.get(`${BASE_URL}/movies/${movieId}`);
        return response.data;
    } catch (error) {
        console.error(`[Client] fetchMovieDetail Error (ID: ${movieId}):`, error.message);
        return null;
    }
}

/**
 * @description 3. Submits a new review to the Review Server via the Gateway.
 * @param {Object} reviewData - Review details { movie_title, critic_name, review_content }.
 * @returns {Promise<Object|null>} The saved review response.
 */
async function postReview(reviewData) {
    try {
        const response = await axios.post(`${BASE_URL}/movies/details`, reviewData);
        return response.data;
    } catch (error) {
        console.error("[Client] postReview Error:", error.message);
        return null;
    }
}
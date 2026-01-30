/**
 * @file movie.js
 * @description Proxy router for movie operations.
 * Orchestrates data between the Gateway, Spring Boot (8082), and Review Server (3001).
 * @version 1.5.0
 */

const express = require('express');
const axios = require("axios");
const router = express.Router();

/**
 * @route GET /movies
 * @description Fetches a paginated list of movies from the Spring Boot server.
 * Maps to the base /movies route in app.js.
 *
 */
router.get('/', async (req, res) => {
    try {
        console.log("[Proxy] Fetching movie list from Spring Boot...");
        // Forwards query parameters (page, size, name) to Spring Boot
        const response = await axios.get('http://localhost:8082/movies', {
            params: req.query
        });
        res.json(response.data);
    } catch (error) {
        console.error("[Proxy Error] Movie List:", error.message);
        res.status(500).json({ error: "Spring Boot server unreachable" });
    }
});

/**
 * @route GET /movies/:id
 * @description Fetches full movie details from Spring Boot and reviews from MongoDB service.
 *
 */
router.get('/:id', async function (req, res) {
    try {
        const movieId = req.params.id;

        // 1. Fetch movie details from Spring Boot (8082)
        const springRes = await axios.get(`http://localhost:8082/movies/${movieId}`);
        const movieData = springRes.data;

        // 2. Fetch reviews from Review Server (3001) using the movie name
        let reviewData = [];
        try {
            // Encode the title to handle special characters or spaces
            const movieTitle = encodeURIComponent(movieData.movie.name);
            const reviewUrl = `http://localhost:3001/reviews/movie/${movieTitle}`;

            console.log(`[Proxy] Fetching reviews from: ${reviewUrl}`);
            const reviewRes = await axios.get(reviewUrl);
            reviewData = reviewRes.data;
        } catch (e) {
            console.warn(`[Proxy] No reviews found for title: ${movieData.movie.name}`);
        }

        // 3. Return combined data object to the frontend
        res.json({
            ...movieData,
            reviews: reviewData
        });

    } catch (error) {
        console.error("[Proxy Error] Movie Details:", error.message);
        const status = error.response?.status || 500;
        res.status(status).json({ error: "Could not retrieve movie details" });
    }
});

module.exports = router;
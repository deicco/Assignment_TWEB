/**
 * @file movie.js
 * @description Proxy router for movie-related operations.
 * Bridges the frontend with Spring Boot (8082) and Review Server (3001).
 * @version 1.4.0
 */

const express = require('express');
const axios = require("axios");
const router = express.Router();

/**
 * @route GET /movies
 * @description Fetches paginated movies from Spring Boot.
 *
 */
router.get('/', async function (req, res) {
    const page = req.query.page || 0;
    const size = req.query.size || 10;
    const name = req.query.name || "";

    try {
        const response = await axios.get(`http://localhost:8082/movies`, {
            params: { page, size, name }
        });
        res.json(response.data);
    } catch (error) {
        console.error("[Proxy Error] List:", error.message);
        res.status(500).json({ error: "Spring Boot unreachable" });
    }
});

/**
 * @route GET /movies/:id
 * @description Fetches full details for a specific movie ID.
 * Maps to @GetMapping("/{id}") in Spring Controller.
 *
 */
router.get('/:id', async function (req, res) {
    try {
        const movieId = req.params.id;

        // FIXED: Using path variable as required by MoviesController.java
        const springUrl = `http://localhost:8082/movies/${movieId}`;
        const movieResponse = await axios.get(springUrl);

        // Fetch reviews from 3001 using the movie name from Spring's response
        let reviews = [];
        try {
            const movieName = movieResponse.data.movie.name;
            const reviewRes = await axios.get(`http://localhost:3001/reviews/${encodeURIComponent(movieName)}`);
            reviews = reviewRes.data;
        } catch (e) {
            console.warn(`[Proxy] No reviews for ID: ${movieId}`);
        }

        res.json({ ...movieResponse.data, reviews });
    } catch (error) {
        console.error("[Proxy Error] Details:", error.message);
        res.status(error.response?.status || 500).json({ error: "Failed to fetch movie details" });
    }
});

module.exports = router;
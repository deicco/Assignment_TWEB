/**
 * @file index.js
 * @description Core routing module for the API Gateway.
 * Uses Axios to aggregate data from the Spring Boot and Express microservices,
 * then renders Handlebars views (Server-Side Rendering).
 */

const express = require('express');
const axios = require('axios');
const router = express.Router();

/** Base URL for the Spring Boot microservice (SQL). */
const SPRING_BOOT_URL = 'http://localhost:8082';

/** Base URL for the Express microservice (MongoDB). */
const EXPRESS_DATA_URL = 'http://localhost:3001';

/**
 * @route GET /
 * @description Fetches a paginated list of movies from the Spring Boot server
 * and renders the main index page.
 */
router.get('/', async (req, res) => {
    try {
        const response = await axios.get(`${SPRING_BOOT_URL}/movies`, {
            params: {
                page: req.query.page || 0,
                size: 10,
                name: req.query.name || ''
            }
        });

        res.render('pages/index', {
            title: 'Archivio Completo Film',
            moviesData: response.data
        });
    } catch (error) {
        console.error("[Gateway Error] Failed to fetch movie list:", error.message);
        res.render('pages/index', {
            title: 'Error',
            error: 'Unable to contact the main server.'
        });
    }
});

/**
 * @route GET /movie_detail
 * @description Aggregates full movie details from Spring Boot and reviews from Express,
 * then renders the movie detail page.
 */
router.get('/movie_detail', async (req, res) => {
    try {
        const movieId = req.query.id;

        // Redirect to homepage if no ID is provided in the URL
        if (!movieId) {
            return res.redirect('/');
        }

        // 1. Fetch technical details and relationships from Spring Boot (SQL)
        const springRes = await axios.get(`${SPRING_BOOT_URL}/movies/${movieId}`);
        const movieData = springRes.data;

        // 2. Fetch reviews from Express (MongoDB) using the movie title
        let reviewsData = [];
        try {
            const movieTitle = encodeURIComponent(movieData.movie.name);
            const reviewRes = await axios.get(`${EXPRESS_DATA_URL}/reviews/movie/${movieTitle}`);
            reviewsData = reviewRes.data;
        } catch (e) {
            console.warn(`[Gateway Warning] No reviews found for title: ${movieData.movie.name}`);
        }

        // 3. Render the aggregated data into the Handlebars view
        res.render('pages/movie_detail', {
            title: movieData.movie.name,
            movie: movieData.movie,
            posters: movieData.posters,
            cast: movieData.cast,
            reviews: reviewsData
        });

    } catch (error) {
        console.error("[Gateway Error] Failed to fetch movie details:", error.message);
        res.render('pages/index', {
            title: 'Error',
            error: 'Unable to load movie details.'
        });
    }
});

module.exports = router;
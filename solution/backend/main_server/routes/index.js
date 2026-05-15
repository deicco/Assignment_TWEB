const express = require('express');
const router = express.Router();
const axios = require('axios');

router.get('/', async (req, res) => {
    try {
        const page = req.query.page || 0;
        const queryName = req.query.name || "";

        let url = `http://localhost:8082/movies?page=${page}&size=20`;
        if (queryName) url += `&name=${encodeURIComponent(queryName)}`;

        const response = await axios.get(url);

        res.render('pages/index', {
            title: "Home",
            moviesData: response.data,
            queryName: queryName
        });
    } catch (error) {
        res.status(500).send("Errore caricamento film.");
    }
});

/**
 * GET movie details page.
 * Aggregates movie metadata, reviews, genres, and languages from different microservices.
 * * @param {Object} req - The Express request object, expecting 'id' in the query string.
 * @param {Object} res - The Express response object used to render the Handlebars view.
 */
router.get('/movie_detail', async (req, res) => {
    try {
        const movieId = req.query.id;

        /**
         * 1. Fetch core movie data from Spring Boot.
         * Includes movie details, cast, and posters.
         */
        const movieRes = await axios.get(`http://localhost:8082/movies/${movieId}`);
        const data = movieRes.data;

        /**
         * 2. Fetch reviews from the MongoDB/Node.js microservice.
         * Uses the movie name for querying.
         */
        let reviews = [];
        try {
            const reviewRes = await axios.get(`http://localhost:3001/reviews/movie/${encodeURIComponent(data.movie.name)}`);
            reviews = reviewRes.data;
        } catch (e) {
            console.warn("[Gateway] No reviews found for: " + data.movie.name);
        }

        /**
         * 3. Fetch genres from Spring Boot.
         * Maps the array of genre objects to a comma-separated string.
         */
        let displayGenres = "N/A";
        try {
            const genreRes = await axios.get(`http://localhost:8082/genres/movie/${movieId}`);
            if (genreRes.data && genreRes.data.length > 0) {
                displayGenres = genreRes.data.map(g => g.genre).join(', ');
            }
        } catch (e) {
            console.warn("[Gateway] No genres found for: " + data.movie.name);
        }

        /**
         * 4. Format languages from the main Spring Boot response.
         * Maps the array of language objects to a comma-separated string.
         */
        const displayLanguages = (data.languages && data.languages.length > 0)
            ? data.languages.map(l => l.language).join(', ')
            : "N/A";

        /**
         * Render the view with the aggregated data.
         */
        res.render('pages/movie_detail', {
            title: data.movie.name,
            movie: data.movie,
            cast: data.cast || [],
            posters: data.posters || [],
            displayLanguages: displayLanguages,
            displayGenres: displayGenres,
            reviews: reviews
        });
    } catch (error) {
        console.error("[Gateway Error]: Movie detail route failed.", error.message);
        res.status(404).send("Film non trovato.");
    }
});
module.exports = router;
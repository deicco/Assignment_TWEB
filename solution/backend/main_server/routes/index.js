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
 * Aggregates movie metadata, genres, and languages from Spring Boot.
 * Retrieves reviews from MongoDB and performs server-side pagination (5 reviews per page).
 *
 * @param {Object} req - The Express request object containing the 'id' and optional 'revPage'.
 * @param {Object} res - The Express response object for rendering the view.
 */
router.get('/movie_detail', async (req, res) => {
    try {
        const movieId = req.query.id;
        // Current review page index (default to 0)
        const revPage = parseInt(req.query.revPage) || 0;
        const revSize = 5;

        /**
         * 1. Fetch main movie data from Spring Boot service.
         */
        const movieRes = await axios.get(`http://localhost:8082/movies/${movieId}`);
        const data = movieRes.data;

        /**
         * 2. Fetch ALL reviews for the movie from the MongoDB service.
         */
        let allReviews = [];
        try {
            const reviewRes = await axios.get(`http://localhost:3001/reviews/movie/${encodeURIComponent(data.movie.name)}`);
            allReviews = reviewRes.data;
        } catch (e) {
            console.warn("[Gateway] Review service unreachable or no reviews found.");
        }

        /**
         * 3. Calculate pagination metadata for reviews.
         * Math.ceil ensures we have enough pages to contain all reviews.
         */
        const totalReviews = allReviews.length;
        const totalRevPages = Math.ceil(totalReviews / revSize);

        // Slice the array to get only the reviews for the requested page
        const paginatedReviews = allReviews.slice(revPage * revSize, (revPage + 1) * revSize);

        /**
         * 4. Fetch movie genres from Spring Boot.
         */
        let displayGenres = "N/A";
        try {
            const genreRes = await axios.get(`http://localhost:8082/genres/movie/${movieId}`);
            if (genreRes.data && genreRes.data.length > 0) {
                displayGenres = genreRes.data.map(g => g.genre).join(', ');
            }
        } catch (e) {
            console.warn("[Gateway] Genres fetch failed.");
        }

        /**
         * 5. Format display languages from the Java DTO response.
         */
        const displayLanguages = (data.languages && data.languages.length > 0)
            ? data.languages.map(l => l.language).join(', ')
            : "N/A";

        /**
         * Render the movie_detail view with paginated reviews and metadata.
         */
        res.render('pages/movie_detail', {
            title: data.movie.name,
            movie: data.movie,
            cast: data.cast || [],
            posters: data.posters || [],
            displayLanguages: displayLanguages,
            displayGenres: displayGenres,
            reviews: paginatedReviews,
            // Pagination metadata for Handlebars helpers
            revPagination: {
                current: revPage,
                total: totalRevPages,
                totalItems: totalReviews
            }
        });
    } catch (error) {
        console.error("[Gateway Error]: Detail route processing failed.", error.message);
        res.status(404).send("Movie not found.");
    }
});
module.exports = router;
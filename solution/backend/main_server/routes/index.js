const express = require('express');
const router = express.Router();
const axios = require('axios');

/**
 * @swagger
 * /:
 * get:
 * summary: Retrieve the home page with a paginated list of movies
 * tags: [Movies]
 * description: Fetches paginated movie data from the Spring Boot server and allows optional filtering by movie name.
 * parameters:
 * - in: query
 * name: page
 * schema:
 * type: integer
 * description: The index of the page to retrieve (defaults to 0)
 * - in: query
 * name: name
 * schema:
 * type: string
 * description: Optional filter to search for movies by name
 * responses:
 * 200:
 * description: Successfully rendered the index view with movie data
 * 500:
 * description: Internal gateway error while fetching movies
 */
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
        res.status(500).send("Error loading movies.");
    }
});

/**
 * @swagger
 * /movie_detail:
 * get:
 * summary: Retrieve movie details and initial reviews
 * tags: [Movies, Reviews]
 * description: Aggregates core movie data and genres from Spring Boot and initial reviews from MongoDB.
 * parameters:
 * - in: query
 * name: id
 * required: true
 * schema:
 * type: integer
 * description: The unique identifier of the movie
 * responses:
 * 200:
 * description: Successfully rendered the movie detail view
 * 404:
 * description: Movie not found or gateway aggregation error
 */
router.get('/movie_detail', async (req, res) => {
    try {
        const movieId = req.query.id;
        const revPage = 0; // Default to first page initially
        const revSize = 10;

        const movieRes = await axios.get(`http://localhost:8082/movies/${movieId}`);
        const data = movieRes.data;

        let paginatedReviews = [];
        let totalRevPages = 0;
        let totalReviews = 0;

        try {
            const reviewRes = await axios.get(`http://localhost:3001/reviews/movie/${encodeURIComponent(data.movie.name)}?page=${revPage}&size=${revSize}`);

            if (reviewRes.data && reviewRes.data.content) {
                paginatedReviews = reviewRes.data.content;
                totalRevPages = reviewRes.data.totalPages;
                totalReviews = reviewRes.data.totalElements;
            } else if (Array.isArray(reviewRes.data)) {
                totalReviews = reviewRes.data.length;
                totalRevPages = Math.ceil(totalReviews / revSize);
                paginatedReviews = reviewRes.data.slice(0, revSize);
            }
        } catch (e) {
            console.warn("[Gateway] Review service unreachable or no reviews found.");
        }

        let displayGenres = "N/A";
        try {
            const genreRes = await axios.get(`http://localhost:8082/genres/movie/${movieId}`);
            if (genreRes.data && genreRes.data.length > 0) {
                displayGenres = genreRes.data.map(g => g.genre).join(', ');
            }
        } catch (e) {
            console.warn("[Gateway] Genres fetch failed.");
        }

        const displayLanguages = (data.languages && data.languages.length > 0)
            ? data.languages.map(l => l.language).join(', ')
            : "N/A";

        res.render('pages/movie_detail', {
            title: data.movie.name,
            movie: data.movie,
            cast: data.cast || [],
            posters: data.posters || [],
            displayLanguages: displayLanguages,
            displayGenres: displayGenres,
            reviews: paginatedReviews,
            revPagination: {
                current: revPage,
                total: totalRevPages,
                totalItems: totalReviews,
                hasPrevPage: false,
                hasNextPage: revPage + 1 < totalRevPages,
                showPagination: totalRevPages > 1
            }
        });
    } catch (error) {
        console.error("[Gateway Error]: Detail route failed.", error.message);
        res.status(404).send("Movie not found.");
    }
});

/**
 * @swagger
 * /api/reviews:
 * get:
 * summary: Get paginated reviews for a specific movie as JSON
 * tags: [Reviews]
 * description: Asynchronously fetches a page of reviews from the MongoDB service to allow non-blocking client-side updates via Axios.
 * parameters:
 * - in: query
 * name: movieName
 * required: true
 * schema:
 * type: string
 * description: The name of the movie
 * - in: query
 * name: page
 * schema:
 * type: integer
 * description: The review page index to retrieve
 * responses:
 * 200:
 * description: A JSON payload containing the text reviews array and pagination metadata
 * 500:
 * description: Failed to communicate with MongoDB microservice
 */
router.get('/api/reviews', async (req, res) => {
    try {
        const movieName = req.query.movieName;
        const revPage = parseInt(req.query.page) || 0;
        const revSize = 10;

        const reviewRes = await axios.get(`http://localhost:3001/reviews/movie/${encodeURIComponent(movieName)}?page=${revPage}&size=${revSize}`);

        let paginatedReviews = [];
        let totalRevPages = 0;

        if (reviewRes.data && reviewRes.data.content) {
            paginatedReviews = reviewRes.data.content;
            totalRevPages = reviewRes.data.totalPages;
        } else if (Array.isArray(reviewRes.data)) {
            const totalReviews = reviewRes.data.length;
            totalRevPages = Math.ceil(totalReviews / revSize);
            paginatedReviews = reviewRes.data.slice(revPage * revSize, (revPage + 1) * revSize);
        }

        res.json({
            reviews: paginatedReviews,
            current: revPage,
            total: totalRevPages,
            hasPrevPage: revPage > 0,
            hasNextPage: (revPage + 1) < totalRevPages
        });
    } catch (error) {
        res.status(500).json({ error: "Error fetching reviews asynchronously." });
    }
});

module.exports = router;
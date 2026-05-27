const express = require('express');
const router = express.Router();
const axios = require('axios');

/**
 * @swagger
 * /:
 * get:
 * summary: Home page movies
 * tags:
 * - Movies
 * parameters:
 * - in: query
 * name: page
 * schema:
 * type: integer
 * - in: query
 * name: name
 * schema:
 * type: string
 * responses:
 * 200:
 * description: Success
 * 500:
 * description: Error
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
 * summary: Movie details
 * tags:
 * - Movies
 * parameters:
 * - in: query
 * name: id
 * required: true
 * schema:
 * type: integer
 * responses:
 * 200:
 * description: Success
 * 404:
 * description: Not found
 */
router.get('/movie_detail', async (req, res) => {
    try {
        const movieId = req.query.id;
        const revPage = 0;
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
 * summary: Paginated reviews
 * tags:
 * - Reviews
 * parameters:
 * - in: query
 * name: movieName
 * required: true
 * schema:
 * type: string
 * - in: query
 * name: page
 * schema:
 * type: integer
 * responses:
 * 200:
 * description: Success
 * 500:
 * description: Error
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
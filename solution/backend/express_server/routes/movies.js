/**
 * Routes for handling movie-related API endpoints.
 */

const express = require('express');
const router = express.Router();
const movieController = require('../controllers/movies');

/**
 * @swagger
 * /movies/get_all:
 *   get:
 *     summary: Retrieve a list of movies
 *     description: Returns a list of up to 10 movies from the database
 *     responses:
 *       200:
 *         description: A list of movies was successfully retrieved
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 $ref: '#/components/schemas/Movie'
 */

router.get('/get_all', movieController.GetAllMovies);

module.exports = router;
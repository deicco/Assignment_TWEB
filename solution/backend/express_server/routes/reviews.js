/**
 * Routes for handling review-related API endpoints.
 */

const express = require('express');
const router = express.Router();
const reviewController = require('../controllers/reviews');

/**
 * @swagger
 * /reviews/movie/{title}:
 * get:
 * summary: Get reviews by movie title
 * description: Retrieves all reviews associated with a specific movie title
 * parameters:
 * - in: path
 * name: title
 * required: true
 * schema:
 * type: string
 * description: The title of the movie
 * responses:
 * 200:
 * description: List of reviews retrieved successfully
 * content:
 * application/json:
 * schema:
 * type: array
 * items:
 * $ref: '#/components/schemas/Review'
 * 404:
 * description: No reviews found for the given title
 */
router.get('/movie/:title', reviewController.getReviewsByMovieTitle);

/**
 * @swagger
 * /reviews:
 * post:
 * summary: Submit a new review
 * description: Adds a new review for a given movie
 * requestBody:
 * required: true
 * content:
 * application/json:
 * schema:
 * $ref: '#/components/schemas/Review'
 * responses:
 * 201:
 * description: Review successfully created
 * 400:
 * description: Invalid request body
 */
router.post('/', reviewController.insertReviewForMovie);

module.exports = router;
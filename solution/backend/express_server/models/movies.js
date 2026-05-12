const mongoose = require('mongoose');

/**
 * @swagger
 * components:
 *   schemas:
 *     Movie:
 *       type: object
 *       required:
 *         - name
 *       properties:
 *         name:
 *           type: string
 *           maxLength: 50
 *           description: Title of the movie
 *         year:
 *           type: integer
 *           description: Release year of the movie
 *         tagline:
 *           type: string
 *           maxLength: 100
 *           description: Promotional tagline
 *         description:
 *           type: string
 *           maxLength: 300
 *           description: Description of the movie
 *         minute:
 *           type: integer
 *           description: Duration of the movie in minutes
 *         rating:
 *           type: number
 *           description: Rating score of the movie
 */

/**
 * Mongoose schema for Movie documents.
 * Defines the structure of movie documents stored in MongoDB.
 */
const Movie = new mongoose.Schema({

    /** Unique identifier matching the CSV and Spring Boot DB (Long/Integer in SQL). */
    id: { type: Number, required: true, unique: true },

    /** Title of the movie (required, max length 50). */
    name: { type: String, required: true},

    /** Release year of the movie. */
    year: { type: Number },

    /** Promotional tagline (max length 100). */
    tagline: { type: String, max: 100 },

    /** Description of the movie (max length 300). */
    description: { type: String },

    /** Duration of the movie in minutes. */
    minute: { type: Number },

    /** Rating score of the movie. */
    rating: { type: Number }
});

/** Exports the Movie model based on the defined schema. */
module.exports = mongoose.model('movie', Movie);

const mongoose = require('mongoose');

/**
 * @swagger
 * components:
 *   schemas:
 *     Review:
 *       type: object
 *       required:
 *         - movie_title
 *         - critic_name
 *         - review_date
 *         - review_content
 *       properties:
 *         movie_title:
 *           type: string
 *           maxLength: 50
 *           description: Title of the movie being reviewed
 *         critic_name:
 *           type: string
 *           maxLength: 50
 *           description: Name of the critic
 *         review_date:
 *           type: string
 *           format: date
 *           description: Date the review was written
 *         review_content:
 *           type: string
 *           maxLength: 300
 *           description: Content of the review
 */

/**
 * Mongoose schema for Review documents.
 * Defines the structure of review documents stored in MongoDB.
 */
const Review = new mongoose.Schema({

    /** Title of the movie being reviewed (required, max length 50). */
    movie_title: { type: String, required: true, max: 50 },

    /** Name of the critic who wrote the review (required, max length 50). */
    critic_name: { type: String, required: true, max: 50 },

    /** Date when the review was written (required). */
    review_date: { type: Date, required: true },

    /** Content of the review (required, max length 300). */
    review_content: { type: String, required: true, max: 300 },});

/** Exports the Review model based on the defined schema. */
module.exports = mongoose.model('review', Review);
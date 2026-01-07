/**
 * Review.js
 * Mongoose Schema definition for the 'Reviews' collection.
 * This model maps the CSV data structure to a MongoDB document.
 *
 * @module ReviewsModel
 */

const mongoose = require('mongoose');

/**
 * Schema for Rotten Tomatoes Reviews.
 * Defines the structure and data types for storing review information.
 * @type {mongoose.Schema}
 */
const reviewSchema = new mongoose.Schema({
    /**
     * The title of the movie.
     * This field is indexed for fast lookup performance.
     * It serves as the foreign key/link to the Java Server movie entities.
     * @type {String}
     */
    movie_title: {
        type: String,
        index: true
    },

    /**
     * URL link to the original review on Rotten Tomatoes.
     * @type {String}
     */
    rotten_tomatoes_link: String,

    /**
     * Name of the critic who wrote the review.
     * @type {String}
     */
    critic_name: String,

    /**
     * Indicates if the critic is considered a "Top Critic".
     * Converted from 'True'/'False' string in CSV to Boolean.
     * @type {Boolean}
     */
    top_critic: Boolean,

    /**
     * Name of the publisher or media outlet.
     * @type {String}
     */
    publisher_name: String,

    /**
     * The sentiment type of the review (e.g., "Fresh", "Rotten").
     * @type {String}
     */
    review_type: String,

    /**
     * The score given by the critic (e.g., "3/5", "A", "80").
     * Stored as String to preserve original formatting.
     * @type {String}
     */
    review_score: String,

    /**
     * The date the review was published.
     * @type {Date}
     */
    review_date: Date,

    /**
     * The text content/snippet of the review.
     * @type {String}
     */
    review_content: String
});

/**
 * Mongoose Model for 'Reviews'.
 * @exports mongoose.model
 */
module.exports = mongoose.model('Reviews', reviewSchema);
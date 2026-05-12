const mongoose = require('mongoose');

/**
 * @swagger
 * components:
 * schemas:
 * Review:
 * type: object
 * required:
 * - movie_title
 * - review_content
 * properties:
 * movie_title:
 * type: string
 * description: Title of the movie being reviewed
 * rotten_tomatoes_link:
 * type: string
 * description: URL link to the original review on Rotten Tomatoes
 * critic_name:
 * type: string
 * description: Name of the critic
 * top_critic:
 * type: boolean
 * description: Indicates if the critic is considered a top critic
 * publisher_name:
 * type: string
 * description: Name of the publisher or media outlet
 * review_type:
 * type: string
 * description: The sentiment type of the review (e.g., Fresh, Rotten)
 * review_score:
 * type: string
 * description: The score given by the critic
 * review_date:
 * type: string
 * format: date
 * description: Date the review was written
 * review_content:
 * type: string
 * description: Content of the review
 */

/**
 * Mongoose schema for Review documents.
 * Defines the structure of review documents stored in MongoDB,
 * mapping exactly to the rotten_tomatoes_reviews.csv dataset.
 */
const reviewSchema = new mongoose.Schema({

    /** Title of the movie being reviewed. Indexed for fast searches. */
    movie_title: { type: String, required: true, index: true },

    /** URL link to the original review on Rotten Tomatoes. */
    rotten_tomatoes_link: { type: String },

    /** Name of the critic who wrote the review. */
    critic_name: { type: String },

    /** Indicates if the critic is considered a "Top Critic". */
    top_critic: { type: Boolean },

    /** Name of the publisher or media outlet. */
    publisher_name: { type: String },

    /** The sentiment type of the review (e.g., "Fresh", "Rotten"). */
    review_type: { type: String },

    /** The score given by the critic (kept as String to preserve original formatting). */
    review_score: { type: String },

    /** Date when the review was published. */
    review_date: { type: Date },

    /** Text content/snippet of the review. */
    review_content: { type: String, required: true }
});

/** Exports the Review model based on the defined schema. */
module.exports = mongoose.model('Review', reviewSchema);
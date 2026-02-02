const Review = require('../models/reviews');
const { ObjectId } = require('mongodb');


/**
 * Retrieves reviews for a specific movie title.
 *
 * @param {Object} req - The request object containing the movie title as a route parameter.
 * @param {Object} res - The response object.
 */
exports.getReviewsByMovieTitle = async (req, res) => {
    try {
        const rawTitle = decodeURIComponent(req.params.title).trim();
        const reviews = await Review.find({movie_title: rawTitle});
        console.log(reviews);
        if (reviews.length === 0) {
            return res.status(404).json({ message: `No reviews found for "${rawTitle}".` });
        }
        res.status(200).json(reviews);
    } catch (error) {
        console.error('Error fetching reviews:', error);
        res.status(500).json({ message: 'Server error while retrieving reviews.' });
    }
};


exports.insertReviewForMovie = async (req, res) => {
    try {
        const movie_title = decodeURIComponent(req.body.movie_title).trim();
        const critic_name = decodeURIComponent(req.body.critic_name).trim();
        const review_content = decodeURIComponent(req.body.review_content).trim();
        const review_date = new Date();

        const review = await Review.create({
            movie_title,
            critic_name,
            review_content,
            review_date
        });

        res.status(201).json(review); // Send back inserted review
    } catch (err) {
        console.error(err);
        res.status(500).json({ error: "Internal server error." });
    }
};





const Movies = require('../models/movies');

/**
 * Retrieves a list of movies (limited to 10).
 */
exports.GetAllMovies = async (req, res) => {
    try {
        const movies = await Movies.find().limit(10);
        res.status(200).json(movies);
    } catch (error) {
        console.error('Error fetching movies:', error);
        res.status(500).json({ message: 'Server error while retrieving movies.' });
    }
};
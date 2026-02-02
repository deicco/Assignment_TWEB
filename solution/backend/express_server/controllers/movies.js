const Movies = require('../models/movies');

/**
 * Retrieves a list of movies (limited to 10).
 *
 * @param {Object} req - The request object.
 * @param {Object} res - The response object.
 */
exports.GetAllMovies = async (req, res) => {
    try {
        const movies = await Movies.find().limit(10);
        if (movies.length === 0) {
            return res.status(404).json({ message: `No movies found".` });
        }
        res.status(200).json(movies);
    } catch (error) {
        console.error('Error fetching movies:', error);
        res.status(500).json({ message: 'Server error while retrieving movies.' });
    }
};


const Review = require('../models/reviews');

/**
 * Retrieves reviews for a specific movie title using partial/case-insensitive match.
 */
exports.getReviewsByMovieTitle = async (req, res) => {
    try {
        const rawTitle = decodeURIComponent(req.params.title).trim();

        // Uso $regex per ricerca parziale (es. "batman" trova "The Batman") e "i" per case-insensitive
        const reviews = await Review.find({ movie_title: { $regex: new RegExp(rawTitle, 'i') } }).limit(50);

        // Best practice API: un array vuoto [] con status 200 è meglio di un 404 per le liste.
        res.status(200).json(reviews);
    } catch (error) {
        console.error('Error fetching reviews:', error);
        res.status(500).json({ message: 'Server error while retrieving reviews.' });
    }
};

/**
 * Inserts a new review after validating input data.
 */
exports.insertReviewForMovie = async (req, res) => {
    try {
        /* Check field existence before calling trim  */
        if (!req.body.movie_title || !req.body.review_content) {
            return res.status(400).json({ error: "I campi 'movie_title' e 'review_content' sono obbligatori." });
        }

        const movie_title = decodeURIComponent(req.body.movie_title).trim();
        const critic_name = req.body.critic_name ? decodeURIComponent(req.body.critic_name).trim() : "Anonymous Fan";
        const review_content = decodeURIComponent(req.body.review_content).trim();

        const review = await Review.create({
            movie_title,
            critic_name,
            review_content,
            review_date: new Date()
        });

        res.status(201).json(review);
    } catch (err) {
        console.error("Error inserting review:", err);
        res.status(500).json({ error: "Internal server error." });
    }
};
const express = require('express');
const router = express.Router();
const axios = require('axios');

router.get('/', async (req, res) => {
    try {
        const page = req.query.page || 0;
        const queryName = req.query.name || "";

        let url = `http://localhost:8082/movies?page=${page}&size=20`;
        if (queryName) url += `&name=${encodeURIComponent(queryName)}`;

        const response = await axios.get(url);

        res.render('pages/index', {
            title: "Home",
            moviesData: response.data,
            queryName: queryName
        });
    } catch (error) {
        res.status(500).send("Errore caricamento film.");
    }
});

router.get('/movie_detail', async (req, res) => {
    try {
        const movieId = req.query.id;
        const movieRes = await axios.get(`http://localhost:8082/movies/${movieId}`);
        const data = movieRes.data;

        let reviews = [];
        try {
            const reviewRes = await axios.get(`http://localhost:3001/reviews/movie/${encodeURIComponent(data.movie.name)}`);
            reviews = reviewRes.data;
        } catch (e) { console.log("Nessuna recensione"); }

        const displayLanguages = (data.languages && data.languages.length > 0)
            ? data.languages.map(l => l.language).join(', ')
            : "Lingue non specificate";

        res.render('pages/movie_detail', {
            title: data.movie.name,
            movie: data.movie,
            cast: data.cast,
            posters: data.posters,
            displayLanguages: displayLanguages, // <-- Passata al template
            reviews: reviews
        });
    } catch (error) {
        res.status(404).send("Film non trovato.");
    }
});

module.exports = router;
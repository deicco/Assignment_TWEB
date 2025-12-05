// Review.js
const mongoose = require('mongoose');

// Definizione dello schema per mappare le colonne del CSV
const reviewSchema = new mongoose.Schema({
    // movie_title è l'indice per la ricerca rapida e il collegamento al server Java
    movie_title: {
        type: String,
        index: true
    },
    rotten_tomatoes_link: String,
    critic_name: String,
    top_critic: Boolean,
    publisher_name: String,
    review_type: String,
    review_score: String,
    review_date: Date,
    review_content: String
});

module.exports = mongoose.model('Review', reviewSchema);
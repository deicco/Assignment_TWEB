// server.js
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const fs = require('fs');
const csv = require('csv-parser');
const Review = require('./Review'); // Importa il modello appena creato

const app = express();
const PORT = 3001; // Porta dedicata per il server delle recensioni (Dati Dinamici)

// Abilita CORS e parsing JSON
app.use(cors());
app.use(express.json());

// Connessione a MongoDB
// Assicurati che il tuo servizio MongoDB sia attivo (mongod)
mongoose.connect('mongodb://localhost:27017/ium_reviews')
    .then(() => {
        console.log('✅ MongoDB Connesso');
        checkAndImportData(); // Avvia il controllo e l'importazione
    })
    .catch(err => console.error('❌ Errore connessione Mongo:', err));

// --- API ---

/**
 * GET /reviews/movie/:title
 * Trova recensioni per nome del film. Questo è l'endpoint che il Main Server chiamerà.
 */
app.get('/reviews/movie/:title', async (req, res) => {
    try {
        const title = req.params.title;
        // Limite di default per la paginazione / quantità di dati richiesti
        const limit = parseInt(req.query.limit) || 20;

        // Ricerca per titolo esatto, case insensitive (sfrutta l'indice creato in Review.js)
        const reviews = await Review.find({
            movie_title: { $regex: new RegExp(`^${title}$`, 'i') }
        }).limit(limit);

        res.json(reviews);
    } catch (err) {
        res.status(500).json({ message: 'Error retrieving reviews: ' + err.message });
    }
});

app.listen(PORT, () => {
    console.log(`🚀 Reviews Server attivo su http://localhost:${PORT}`);
});

// --- Logica di Importazione CSV ---
async function checkAndImportData() {
    try {
        const count = await Review.countDocuments();
        if (count === 0) {
            console.log('⚠️ Database MongoDB vuoto. Inizio importazione CSV (potrebbe richiedere tempo)...');

            const reviews = [];
            const BATCH_SIZE = 5000; // Inserimento a blocchi per gestire la RAM

            fs.createReadStream('rotten_tomatoes_reviews.csv')
                .pipe(csv())
                .on('data', (data) => {
                    reviews.push({
                        rotten_tomatoes_link: "https://www.rottentomatoes.com/" + data.rotten_tomatoes_link,
                        movie_title: data.movie_title,
                        critic_name: data.critic_name,
                        top_critic: data.top_critic === 'True', // Conversione da stringa a booleano
                        publisher_name: data.publisher_name,
                        review_type: data.review_type,
                        review_score: data.review_score,
                        review_date: new Date(data.review_date),
                        review_content: data.review_content
                    });

                    // Salva i documenti a blocchi
                    if (reviews.length >= BATCH_SIZE) {
                        Review.insertMany(reviews.splice(0, BATCH_SIZE), { ordered: false })
                            .catch(err => console.error("Errore insert blocco:", err.message));
                    }
                })
                .on('end', async () => {
                    // Inserisci l'ultimo blocco
                    if (reviews.length > 0) {
                        await Review.insertMany(reviews, { ordered: false });
                    }
                    console.log('🎉 Importazione completata!');
                })
                .on('error', (err) => {
                    console.error('Errore nella lettura del file CSV:', err.message);
                });
        } else {
            console.log(`✅ Database già popolato con ${count} recensioni.`);
        }
    } catch (error) {
        console.error("Errore generico:", error);
    }
}
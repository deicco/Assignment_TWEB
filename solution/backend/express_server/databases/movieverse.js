/**
 * MongoDB connection setup and Data Importer.
 */
const mongoose = require('mongoose');
const fs = require('fs');
const path = require('path');
const csv = require('csv-parser');
const Review = require('../models/reviews');

const mongoDB = 'mongodb://localhost:27017/IUM_TWEB';

mongoose.connect(mongoDB, {
    useNewUrlParser: true,
    useUnifiedTopology: true
}).then(() => {
    console.log('✅ Connessione a MongoDB (Express Server) stabilita con successo!');
    checkAndImportReviews(); // Launch check on start
}).catch((error) => {
    console.error('❌ Errore di connessione a MongoDB:', error);
});

/* *
 * Check if there are any reviews; if it's empty, import the CSV in batches.
 * */
async function checkAndImportReviews() {
    try {
        const count = await Review.countDocuments();
        if (count > 0) {
            console.log(`✅ Database già popolato (${count} recensioni presenti).`);
            return;
        }

        /* CSV Path */
        const csvPath = path.join(__dirname, '../../../../../Assignment_IUM_TWEB/solution/data/rotten_tomatoes_reviews.csv');
        if (!fs.existsSync(csvPath)) {
            console.error(`⚠️ File CSV non trovato in: ${csvPath}`);
            return;
        }

        console.log('⏳ MongoDB vuoto. Inizio importazione massiva recensioni...');
        const buffer = [];
        const BATCH_SIZE = 5000;

        fs.createReadStream(csvPath)
            .pipe(csv())
            .on('data', (data) => {
                buffer.push({
                    movie_title: data.movie_title,
                    rotten_tomatoes_link: "https://www.rottentomatoes.com/" + data.rotten_tomatoes_link,
                    critic_name: data.critic_name,
                    top_critic: data.top_critic === 'True',
                    publisher_name: data.publisher_name,
                    review_type: data.review_type,
                    review_score: data.review_score,
                    review_date: new Date(data.review_date),
                    review_content: data.review_content
                });

                if (buffer.length >= BATCH_SIZE) {
                    const chunk = buffer.splice(0, BATCH_SIZE);
                    Review.insertMany(chunk, { ordered: false }).catch(() => {});
                }
            })
            .on('end', async () => {
                if (buffer.length > 0) await Review.insertMany(buffer, { ordered: false });
                console.log('🎉 Importazione recensioni completata con successo!');
            });
    } catch (err) {
        console.error("❌ Errore durante l'importazione:", err);
    }
}
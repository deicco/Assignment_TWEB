/**
 * Server.js
 * Entry point for the Reviews Microservice (Node.js/Express).
 * Handles connection to MongoDB and serves dynamic data (Rotten Tomatoes Reviews).
 * @module ReviewsServer
 */

const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const fs = require('fs');
const path = require('path');
const csv = require('csv-parser');
const Review = require('./Reviews'); // Mongoose Model

const app = express();
const PORT = 3001; // Specific port for this microservice

// Middleware configuration
app.use(cors());
app.use(express.json());

// --- SWAGGER CONFIGURATION (METODO BLINDATO JS) ---
const swaggerUi = require('swagger-ui-express');
const swaggerJsDoc = require('swagger-jsdoc');

const swaggerOptions = {
    definition: {
        openapi: '3.0.0',
        info: {
            title: 'Reviews Microservice API',
            version: '1.0.0',
            description: 'API per le recensioni MongoDB'
        },
        servers: [
            { url: 'http://localhost:3001' }
        ],

        paths: {
            '/reviews/movie/{title}': {
                get: {
                    summary: 'Cerca recensioni per titolo del film',
                    description: 'Restituisce una lista di recensioni cercando per titolo esatto o parziale.',
                    parameters: [
                        {
                            name: 'title',
                            in: 'path',
                            required: true,
                            description: 'Il titolo del film',
                            schema: {
                                type: 'string'
                            }
                        }
                    ],
                    responses: {
                        200: {
                            description: 'Lista delle recensioni trovate',
                            content: {
                                'application/json': {
                                    schema: {
                                        type: 'array',
                                        items: {
                                            type: 'object'
                                        }
                                    }
                                }
                            }
                        },
                        500: {
                            description: 'Errore del server'
                        }
                    }
                }
            }
        }
    },
    apis: [],
};

const swaggerDocs = swaggerJsDoc(swaggerOptions);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocs));
// --------------------------------------------------


// --- 1. SMART PATH CONFIGURATION ---

const submissionPath = path.join(__dirname, '..', '..', '..', 'data', 'rotten_tomatoes_reviews.csv');
const devPath = path.join(__dirname, '..', '..', '..', '..', 'Assignment_IUM_TWEB', 'solution', 'data', 'rotten_tomatoes_reviews.csv');

let csvPath = null;

if (fs.existsSync(submissionPath)) {
    console.log("📂 Deployment Mode: Internal dataset found.");
    csvPath = submissionPath;
} else if (fs.existsSync(devPath)) {
    console.log("🛠️ Development Mode: External dataset found.");
    csvPath = devPath;
} else {
    console.error("❌ FATAL ERROR: Data file 'rotten_tomatoes_reviews.csv' not found!");
}

// --- 2. DATABASE CONNECTION ---

mongoose.connect('mongodb://localhost:27017/mydatabase')
    .then(() => {
        console.log('✅ MongoDB Connected');
        if (csvPath) {
            checkAndImportData();
        }
    })
    .catch(err => console.error('❌ MongoDB Connection Error:', err));


// --- 3. API ENDPOINTS ---

/**
 * GET /reviews/movie/:title
 * Retrieves reviews for a specific movie by title.
 */
app.get('/reviews/movie/:title', async (req, res) => {
    try {
        const title = req.params.title;
        const limit = parseInt(req.query.limit) || 20;

        console.log(`🔍 [ReviewServer] Searching for: '${title}'`);

        // Strategy A: Exact Match
        let query = {
            movie_title: { $regex: new RegExp(`^${title}$`, 'i') }
        };

        let reviews = await Review.find(query).limit(limit);
        console.log(`   👉 Exact Match Results: ${reviews.length}`);

        // Strategy B: Fallback to Partial Match
        if (reviews.length === 0) {
            console.log(`   ⚠️ No exact match. Attempting partial search...`);
            query = {
                movie_title: { $regex: new RegExp(`${title}`, 'i') }
            };
            reviews = await Review.find(query).limit(limit);
            console.log(`   👉 Partial Match Results: ${reviews.length}`);
        }

        res.json(reviews);

    } catch (err) {
        console.error("❌ Server Error:", err);
        res.status(500).json({ message: 'Error retrieving reviews: ' + err.message });
    }
});


// Start the server
app.listen(PORT, () => {
    console.log(`🚀 Reviews Server running on http://localhost:${PORT}`);
});


// --- 4. DATA IMPORT LOGIC ---

async function checkAndImportData() {
    try {
        const count = await Review.countDocuments();

        if (count === 0) {
            console.log('⚠️ MongoDB is empty. Starting CSV import (this may take a while)...');
            console.log(`   Reading from: ${csvPath}`);

            const reviewsBuffer = [];
            const BATCH_SIZE = 5000;

            fs.createReadStream(csvPath)
                .pipe(csv())
                .on('data', (data) => {
                    reviewsBuffer.push({
                        rotten_tomatoes_link: "https://www.rottentomatoes.com/" + data.rotten_tomatoes_link,
                        movie_title: data.movie_title,
                        critic_name: data.critic_name,
                        top_critic: data.top_critic === 'True',
                        publisher_name: data.publisher_name,
                        review_type: data.review_type,
                        review_score: data.review_score,
                        review_date: new Date(data.review_date),
                        review_content: data.review_content
                    });

                    if (reviewsBuffer.length >= BATCH_SIZE) {
                        const chunk = reviewsBuffer.splice(0, BATCH_SIZE);
                        Review.insertMany(chunk, { ordered: false })
                            .catch(err => console.error("   Warning: Batch insert error (duplicates ignored)."));
                    }
                })
                .on('end', async () => {
                    if (reviewsBuffer.length > 0) {
                        await Review.insertMany(reviewsBuffer, { ordered: false });
                    }
                    console.log('🎉 Data Import Completed Successfully!');
                })
                .on('error', (err) => {
                    console.error('❌ CSV Reading Error:', err.message);
                });
        } else {
            console.log(`✅ Database already populated with ${count} reviews.`);
        }
    } catch (error) {
        console.error("❌ Generic Import Error:", error);
    }
}
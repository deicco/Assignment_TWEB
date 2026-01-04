/**
 * Server.js
 * * Entry point for the Reviews Microservice (Node.js/Express).
 * Handles connection to MongoDB and serves dynamic data (Rotten Tomatoes Reviews).
 * * @module ReviewsServer
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

// --- 1. SMART PATH CONFIGURATION ---

/*
 * Path Strategy:
 * 1. submissionPath: Looks for data inside the final 'solution' folder structure (Relative path for the exam).
 * Logic: Up 2 levels from 'backend/express_server' to reach 'solution', then into 'data'.
 * * 2. devPath: Looks for data in your current separate folder on Desktop (Absolute/Relative path for development).
 * Logic: Up 3 levels to Desktop, then into 'Assignment_IUM_TWEB/solution/data'.
 */

const submissionPath = path.join(__dirname, '..', '..', 'data', 'rotten_tomatoes_reviews.csv');
const devPath = path.join(__dirname, '..', '..', '..', 'Assignment_IUM_TWEB', 'solution', 'data', 'rotten_tomatoes_reviews.csv');

let csvPath = null;

if (fs.existsSync(submissionPath)) {
    console.log("📂 Deployment Mode: Internal dataset found.");
    csvPath = submissionPath;
} else if (fs.existsSync(devPath)) {
    console.log("🛠️ Development Mode: External dataset found.");
    csvPath = devPath;
} else {
    console.error("❌ FATAL ERROR: Data file 'rotten_tomatoes_reviews.csv' not found!");
    console.error("   - Checked Submission Path: " + submissionPath);
    console.error("   - Checked Development Path: " + devPath);
    // We do not exit process here to allow the server to start, but import will fail.
}

// --- 2. DATABASE CONNECTION ---

/**
 * Connects to the local MongoDB instance.
 * Upon successful connection, checks if data import is required.
 */
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
 * * Retrieves reviews for a specific movie by title.
 * It uses a two-step search strategy: Exact Match followed by Partial Match.
 * * @route GET /reviews/movie/:title
 * @param {string} title - The title of the movie to search for.
 * @param {number} [limit=20] - Optional query param to limit results.
 * @returns {Array} List of review objects.
 */
app.get('/reviews/movie/:title', async (req, res) => {
    try {
        const title = req.params.title;
        const limit = parseInt(req.query.limit) || 20;

        console.log(`🔍 [ReviewServer] Searching for: '${title}'`);

        // Strategy A: Exact Match (Case Insensitive)
        // Uses regex start (^) and end ($) anchors
        let query = {
            movie_title: { $regex: new RegExp(`^${title}$`, 'i') }
        };

        let reviews = await Review.find(query).limit(limit);
        console.log(`   👉 Exact Match Results: ${reviews.length}`);

        // Strategy B: Fallback to Partial Match if no exact match found
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

/**
 * Checks if the 'Reviews' collection is empty.
 * If empty, it streams the CSV file and performs a bulk insert into MongoDB.
 * Uses batch processing to manage memory usage efficiently.
 * * @async
 * @function checkAndImportData
 */
async function checkAndImportData() {
    try {
        const count = await Review.countDocuments();

        if (count === 0) {
            console.log('⚠️ MongoDB is empty. Starting CSV import (this may take a while)...');
            console.log(`   Reading from: ${csvPath}`);

            const reviewsBuffer = [];
            const BATCH_SIZE = 5000; // Batch size for bulk insertion

            fs.createReadStream(csvPath)
                .pipe(csv())
                .on('data', (data) => {
                    // Normalize and map CSV data to Schema
                    reviewsBuffer.push({
                        rotten_tomatoes_link: "https://www.rottentomatoes.com/" + data.rotten_tomatoes_link,
                        movie_title: data.movie_title,
                        critic_name: data.critic_name,
                        top_critic: data.top_critic === 'True', // Convert string "True" to boolean
                        publisher_name: data.publisher_name,
                        review_type: data.review_type,
                        review_score: data.review_score,
                        review_date: new Date(data.review_date),
                        review_content: data.review_content
                    });

                    // Perform Batch Insert when buffer is full
                    if (reviewsBuffer.length >= BATCH_SIZE) {
                        const chunk = reviewsBuffer.splice(0, BATCH_SIZE);
                        Review.insertMany(chunk, { ordered: false })
                            .catch(err => console.error("   Warning: Batch insert error (duplicates ignored)."));
                    }
                })
                .on('end', async () => {
                    // Insert remaining documents
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